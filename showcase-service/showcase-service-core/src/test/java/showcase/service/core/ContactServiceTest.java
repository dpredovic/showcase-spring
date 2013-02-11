package showcase.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import showcase.addressresolver.AddressResolver;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;

import javax.inject.Inject;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles("junit")
public class ContactServiceTest {

    @Inject
    private ContactService contactService;
    @Inject
    private TestCustomerCreator customerCreator;
    @Inject
    private AddressResolver addressResolver;

    @Test
    public void getContact() {
        Long customerId = customerCreator.createCustomer().getId();

        ArgumentCaptor<String> ccCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> zipCaptor = ArgumentCaptor.forClass(String.class);
        when(addressResolver.resolveCity(ccCaptor.capture(), zipCaptor.capture())).thenReturn("MockCity");
        when(addressResolver.resolveCountry(ccCaptor.capture())).thenReturn("MockCountry");

        ContactDto standardContact = contactService
            .getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
        assertThat(standardContact).isNotNull();
        assertThat(standardContact.getCustomerId()).isEqualTo(customerId);
        assertThat(standardContact.getContactType()).isEqualTo(ContactType.STANDARD.toString());
        assertThat(standardContact.getCommunications()).hasSize(1);
        assertThat(standardContact.getCommunications().get(CommunicationType.EMAIL.toString()))
            .isEqualTo("test@mail.com");
        assertThat(standardContact.getCity()).isEqualTo("MockCity");
        assertThat(standardContact.getCountryCode()).isEqualTo(ccCaptor.getValue());
        assertThat(standardContact.getCountryName()).isEqualTo("MockCountry");
        assertThat(standardContact.getZipCode()).isEqualTo(zipCaptor.getValue());

        ContactDto invoicingContact = contactService
            .getContactByCustomerAndType(customerId, ContactType.INVOICING.toString());
        assertThat(invoicingContact).isNotNull();
        assertThat(invoicingContact.getCustomerId()).isEqualTo(customerId);
        assertThat(invoicingContact.getContactType()).isEqualTo(ContactType.INVOICING.toString());
        assertThat(invoicingContact.getCity()).isEqualTo("MockCity");
        assertThat(invoicingContact.getCountryCode()).isEqualTo(ccCaptor.getValue());
        assertThat(invoicingContact.getCountryName()).isEqualTo("MockCountry");
        assertThat(invoicingContact.getZipCode()).isEqualTo(zipCaptor.getValue());

        List<ContactDto> contacts = contactService.getContactsByCustomer(customerId);
        assertThat(contacts).hasSize(4);
        assertThat(contacts).contains(standardContact, invoicingContact);

    }
}
