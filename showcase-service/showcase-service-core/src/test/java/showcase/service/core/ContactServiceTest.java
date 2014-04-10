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

import java.util.List;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
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

        ContactDto standardContact =
            contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
        ContactDto expectedStandardContact =
            new ContactDto(null, customerId, null, null, null, zipCaptor.getValue(), "MockCity", ccCaptor.getValue(),
                           "MockCountry", ContactType.STANDARD.toString(), null);
        assertThat(standardContact).isNotNull().isEqualToIgnoringNullFields(expectedStandardContact);
        assertThat(standardContact.getCommunications()).isNotNull()
                                                       .containsOnly(
                                                           entry(CommunicationType.EMAIL.toString(), "test@mail.com"));

        ContactDto invoicingContact =
            contactService.getContactByCustomerAndType(customerId, ContactType.INVOICING.toString());

        ContactDto expectedInvoicingContact =
            new ContactDto(null, customerId, null, null, null, zipCaptor.getValue(), "MockCity", ccCaptor.getValue(),
                           "MockCountry", ContactType.INVOICING.toString(), null);
        assertThat(invoicingContact).isNotNull().isEqualToIgnoringNullFields(expectedInvoicingContact);

        List<ContactDto> contacts = contactService.getContactsByCustomer(customerId);
        assertThat(contacts).hasSize(4).contains(standardContact, invoicingContact);
    }
}
