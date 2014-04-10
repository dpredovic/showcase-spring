package showcase.persistence.repository;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import showcase.persistence.unit.Contact;
import showcase.persistence.unit.Customer;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;
import showcase.service.api.type.CustomerType;
import showcase.service.api.type.DispatchType;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = RepositoryConfig.class)
public class RepositoryTest {

	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private ContactRepository contactRepository;
	@Inject
	private PlatformTransactionManager transactionManager;

	@Test
	@Transactional(readOnly = true)
	public void createCustomer() throws Exception {

		Long id = saveCustomer();

		Customer customer = customerRepository.findOne(id);

		assertThat(customer).isNotNull();
		assertThat(customer.getId()).isEqualTo(id);

		assertThat(customer.getProperties()).hasSize(1);

		Collection<Contact> contacts = contactRepository.findByCustomerId(id);
        assertThat(contacts).extracting("communications")
                            .extractingResultOf("size", Integer.class)
                            .containsExactly(1, 1, 1);

		{
			Iterable<Contact> foundByEmail =
				contactRepository.findAll(ContactPredicates.containsCommunication(CommunicationType.EMAIL.toString(),
																				  "test1@test"));
			assertThat(foundByEmail).hasSize(1);
		}

		{
			Iterable<Contact> foundByEmail =
				contactRepository.findAll(ContactPredicates.containsCommunication(CommunicationType.EMAIL.toString(),
																				  "notexisting"));
			assertThat(foundByEmail).isEmpty();
		}
	}

	private Long saveCustomer() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager,
																		  new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
		return transactionTemplate.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {

				Customer customer = new Customer();
				customer.setCustomerType(CustomerType.PERSON.toString());
				customer.setDispatchType(DispatchType.EMAIL.toString());
				customer.setRegistrationDate(new Date());
				customer.setCooperationPartnerId(1L);
				customer.getProperties().put("platinum", "true");

				customer = customerRepository.save(customer);

				Contact c1 = createContact(1, ContactType.STANDARD, customer);
				Contact c2 = createContact(2, ContactType.CONTRACT, customer);
				Contact c3 = createContact(3, null, customer);

				contactRepository.save(ImmutableList.of(c1, c2, c3));

				return customer.getId();
			}
		});
	}

	private static Contact createContact(int i, ContactType type, Customer customer) {
		Contact contact = new Contact();
		contact.setFirstName("fn" + i);
		contact.setLastName("ln" + i);
		contact.setStreet("str" + i);
		contact.setZipCode("zip" + i);
		contact.setContactType(type == null ? null : type.toString());

		contact.getCommunications().put(CommunicationType.EMAIL.toString(), "test" + i + "@test");

		contact.setCustomer(customer);
		return contact;
	}
}
