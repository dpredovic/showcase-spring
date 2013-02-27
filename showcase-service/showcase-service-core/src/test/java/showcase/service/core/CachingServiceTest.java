package showcase.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import showcase.addressresolver.AddressResolver;
import showcase.addressresolver.AsyncAddressResolver;
import showcase.addressresolver.AsyncAddressResolverImpl;
import showcase.common.cache.CachingConfig;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.ContactType;
import showcase.service.core.cache.CacheSync;
import showcase.service.core.cache.CacheSyncImpl;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CachingServiceTest.class)
@Import(value = {CachingConfig.class, MapperConfig.class, MockConfig.class})
@ActiveProfiles("standalone")
public class CachingServiceTest {

	@Inject
	private ContactService contactService;

	@Inject
	private AddressResolver addressResolver;

	@Inject
	private ContactRepository contactRepository;

	@Bean
	public ContactRepository contactRepository() {
		return mock(ContactRepository.class, RETURNS_SMART_NULLS);
	}

	@Bean
	public ContactService contactService() {
		return new ContactServiceImpl();
	}

	@Bean
	public CacheSync cacheSync() {
		return new CacheSyncImpl();
	}

	@Bean
	public AsyncAddressResolver asyncAddressResolver() {
		return new AsyncAddressResolverImpl();
	}

	@Test
	public void getContact() {
		long customerId = 1L;
		long contactId = 2L;
		ContactType type = ContactType.STANDARD;

		Contact contact = new Contact();
		contact.setId(contactId);
		contact.setContactType(type.toString());

		when(contactRepository.findByCustomerIdAndContactType(customerId, type.toString())).thenReturn(contact)
			.thenThrow(new RuntimeException("allowed only once"));
		when(addressResolver.resolveCountry(anyString())).thenReturn("dummy");
		when(addressResolver.resolveCity(anyString(), anyString())).thenReturn("dummy");

		{
			ContactDto contactDto =
				contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
			assertThat(contactDto).isNotNull();
			assertThat(contactDto.getId()).isEqualTo(contactId);
			assertThat(contactDto.getContactType()).isEqualTo(type.toString());
		}

		{
			ContactDto contactDto =
				contactService.getContactByCustomerAndType(customerId, ContactType.STANDARD.toString());
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
