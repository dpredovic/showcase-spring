package showcase.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.addressresolver.AddressResolver;
import showcase.addressresolver.AsyncAddressResolverImpl;
import showcase.common.cache.CachingConfig;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.ContactType;
import showcase.service.core.cache.CacheSyncImpl;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("standalone")
@ContextConfiguration(classes = CachingServiceTest.class, loader = AnnotationConfigContextLoader.class)
@Import(value = {CachingConfig.class, ContactServiceImpl.class, MapperFactoryBean.class, CacheSyncImpl.class, MockConfig.class, AsyncAddressResolverImpl.class})
public class CachingServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressResolver addressResolver;

    @Autowired
    private ContactRepository contactRepository;

    @Bean
    public ContactRepository contactRepository() {
        return mock(ContactRepository.class, RETURNS_SMART_NULLS);
    }

    @Test
    public void getContact() {
        long customerId = 1L;
        long contactId = 2L;
        ContactType type = ContactType.STANDARD;

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setContactType(type.toString());

        when(contactRepository.findByCustomerIdAndContactType(customerId, type.toString()))
                .thenReturn(contact)
                .thenThrow(new RuntimeException("allowed only once"));
        when(addressResolver.resolveCountry(anyString())).thenReturn("dummy");
        when(addressResolver.resolveCity(anyString(), anyString())).thenReturn("dummy");

        {
            ContactDto contactDto = contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
            assertThat(contactDto).isNotNull();
            assertThat(contactDto.getId()).isEqualTo(contactId);
            assertThat(contactDto.getContactType()).isEqualTo(type.toString());
        }

        {
            ContactDto contactDto = contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
            assertThat(contactDto).isNotNull();
            assertThat(contactDto.getId()).isEqualTo(contactId);
            assertThat(contactDto.getContactType()).isEqualTo(type.toString());
        }

        {
            ContactDto contactDto = contactService.getContact(contactId);
            assertThat(contactDto).isNotNull();
            assertThat(contactDto.getId()).isEqualTo(contactId);
            assertThat(contactDto.getContactType()).isEqualTo(type.toString());
        }

    }

}
