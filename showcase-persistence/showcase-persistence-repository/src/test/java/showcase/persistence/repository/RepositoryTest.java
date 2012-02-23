package showcase.persistence.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import showcase.common.CommunicationType;
import showcase.common.ContactType;
import showcase.common.CustomerType;
import showcase.persistence.repository.config.RepositoryConfig;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = RepositoryConfig.class, loader = AnnotationConfigContextLoader.class)
public class RepositoryTest {

    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private ContactRepository contactDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @Transactional(readOnly = true)
    public void createCustomer() throws Exception {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        Long id = transactionTemplate.execute(new TransactionCallback<Long>() {
            public Long doInTransaction(TransactionStatus status) {

                Customer customer = new Customer();
                customer.setCustomerType(CustomerType.PERSON);
                customer.setRegistrationDate(new Date());
                customer.setCooperationPartnerId(1L);
                customer.getProperties().put("platinum", "true");

                customer = customerDao.save(customer);

                Contact c1 = createContact(1, ContactType.STANDARD, customer);
                Contact c2 = createContact(2, ContactType.CONTRACT, customer);
                Contact c3 = createContact(3, null, customer);

                contactDao.save(Arrays.asList(c1, c2, c3));

                return customer.getId();
            }
        });

        Customer customer = customerDao.findOne(id);

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);

        assertThat(customer.getProperties()).hasSize(1);

        Collection<Contact> contacts = contactDao.findByCustomerId(id);
        assertThat(contacts).hasSize(3);
        for (Contact contact : contacts) {
            assertThat(contact.getCommunications()).hasSize(1);
        }

    }

    private Contact createContact(int i, ContactType type, Customer customer) {
        Contact contact = new Contact();
        contact.setFirstName("fn" + i);
        contact.setLastName("ln" + i);
        contact.setStreet("str" + i);
        contact.setZipCode("zip" + i);
        contact.setContactType(type);

        contact.getCommunications().put(CommunicationType.EMAIL, "test" + i + "@test");

        contact.setCustomer(customer);
        return contact;
    }
}
