package showcase.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import showcase.addressresolver.AddressResolverConfig;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.ContactType;
import showcase.service.core.cache.CacheSync;
import showcase.service.core.cache.CacheSyncImpl;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AddressResolverConfig.class, MapperConfig.class, CachingServiceTest.Config.class})
@ActiveProfiles("standalone")
public class CachingServiceTest {

	@Inject
	private ContactService contactService;
	@Inject
	private ContactRepository contactRepository;

	@Test
	public void getContact() {
		long contactId = 2L;

		Contact contact = new Contact();
		contact.setId(contactId);
		ContactType type = ContactType.STANDARD;
		contact.setContactType(type.toString());

		long customerId = 1L;
		when(contactRepository.findByCustomerIdAndContactType(customerId, type.toString())).thenReturn(contact)
			.thenThrow(new RuntimeException("allowed only once"));

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

	static class Config {
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
	}

}
