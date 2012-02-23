package showcase.service.core;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.common.CommunicationType;
import showcase.common.ContactType;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.core.config.ServiceConfig;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = ServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private TestCustomerCreator customerCreator;

    @Test
    public void getContact() {
        Long id = customerCreator.createCustomer().getId();

        ContactDto standardContact = contactService.getContactByCustomerAndType(id, ContactType.STANDARD);
        assertThat(standardContact).isNotNull();
        assertThat(standardContact.getContactType()).isEqualTo(ContactType.STANDARD);
        assertThat(standardContact.getCommunications()).hasSize(1);
        assertThat(standardContact.getCommunications().get(CommunicationType.EMAIL)).isEqualTo("test@mail.com");

        ContactDto invoicingContact = contactService.getContactByCustomerAndType(id, ContactType.INVOICING);
        assertThat(invoicingContact).isNotNull();
        assertThat(invoicingContact.getContactType()).isEqualTo(ContactType.INVOICING);

        List<ContactDto> contacts = contactService.getContactsByCustomer(id);
        assertThat(contacts).hasSize(4);
        assertThat(contacts).contains(standardContact, invoicingContact);

    }
}
