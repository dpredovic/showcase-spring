package showcase.service.core;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
import showcase.zipresolver.ZipResolver;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = ServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private TestCustomerCreator customerCreator;

    @Autowired
    private ZipResolver zipResolver;

    @Test
    public void getContact() {
        Long customerId = customerCreator.createCustomer().getId();

        ArgumentCaptor<String> ccCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> zipCaptor = ArgumentCaptor.forClass(String.class);
        when(zipResolver.resolveCity(ccCaptor.capture(), zipCaptor.capture())).thenReturn("MockCity");

        ContactDto standardContact = contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD);
        assertThat(standardContact).isNotNull();
        assertThat(standardContact.getCustomerId()).isEqualTo(customerId);
        assertThat(standardContact.getContactType()).isEqualTo(ContactType.STANDARD);
        assertThat(standardContact.getCommunications()).hasSize(1);
        assertThat(standardContact.getCommunications().get(CommunicationType.EMAIL)).isEqualTo("test@mail.com");
        assertThat(standardContact.getCity()).isEqualTo("MockCity");
        assertThat(standardContact.getCountryCode()).isEqualTo(ccCaptor.getValue());
        assertThat(standardContact.getZipCode()).isEqualTo(zipCaptor.getValue());

        ContactDto invoicingContact = contactService.getContactByCustomerAndType(customerId, ContactType.INVOICING);
        assertThat(invoicingContact).isNotNull();
        assertThat(invoicingContact.getCustomerId()).isEqualTo(customerId);
        assertThat(invoicingContact.getContactType()).isEqualTo(ContactType.INVOICING);
        assertThat(invoicingContact.getCity()).isEqualTo("MockCity");
        assertThat(invoicingContact.getCountryCode()).isEqualTo(ccCaptor.getValue());
        assertThat(invoicingContact.getZipCode()).isEqualTo(zipCaptor.getValue());

        List<ContactDto> contacts = contactService.getContactsByCustomer(customerId);
        assertThat(contacts).hasSize(4);
        assertThat(contacts).contains(standardContact, invoicingContact);

    }
}
