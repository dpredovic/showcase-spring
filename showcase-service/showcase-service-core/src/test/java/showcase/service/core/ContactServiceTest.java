package showcase.service.core;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = {ServiceConfig.class, ContactServiceTest.class}, loader = AnnotationConfigContextLoader.class)
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private TestCustomerCreator customerCreator;

    @Autowired
    private ZipResolver zipResolver;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getContact() {
        Long id = customerCreator.createCustomer().getId();

        ArgumentCaptor<String> ccCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> zipCaptor = ArgumentCaptor.forClass(String.class);
        when(zipResolver.resolveCity(ccCaptor.capture(), zipCaptor.capture())).thenReturn("MockCity");

        ContactDto standardContact = contactService.getContactByCustomerAndType(id, ContactType.STANDARD);
        assertThat(standardContact).isNotNull();
        assertThat(standardContact.getContactType()).isEqualTo(ContactType.STANDARD);
        assertThat(standardContact.getCommunications()).hasSize(1);
        assertThat(standardContact.getCommunications().get(CommunicationType.EMAIL)).isEqualTo("test@mail.com");
        assertThat(standardContact.getCity()).isEqualTo("MockCity");
        assertThat(standardContact.getCountryCode()).isEqualTo(ccCaptor.getValue());
        assertThat(standardContact.getZipCode()).isEqualTo(zipCaptor.getValue());

        ContactDto invoicingContact = contactService.getContactByCustomerAndType(id, ContactType.INVOICING);
        assertThat(invoicingContact).isNotNull();
        assertThat(invoicingContact.getContactType()).isEqualTo(ContactType.INVOICING);

        List<ContactDto> contacts = contactService.getContactsByCustomer(id);
        assertThat(contacts).hasSize(4);
        assertThat(contacts).contains(standardContact, invoicingContact);

    }

    @Bean
    public ZipResolver zipResolver() {
        return Mockito.mock(ZipResolver.class, RETURNS_SMART_NULLS);
    }

}
