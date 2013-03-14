package showcase.service.core;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import showcase.addressresolver.AsyncAddressResolver;
import showcase.persistence.repository.ContactPredicates;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.CommunicationType;
import showcase.service.core.cache.CacheSync;
import showcase.service.core.exceptionmapping.ExceptionsMapped;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.Future;

@Named
@Transactional
@Validated
@ExceptionsMapped
public class ContactServiceImpl implements ContactService {

	private final MapFunction mapFunction = new MapFunction();
	@Inject
	private ContactRepository contactRepository;
	@Inject
	private MapperFacade mapper;
	@Inject
	private AsyncAddressResolver addressResolver;
	@Inject
	private CacheSync cacheSync;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "contact", key = "'id='+#contactId", unless = "#result==null")
	public ContactDto getContact(long contactId) {
		return mapFunction.apply(contactRepository.findOne(contactId));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "contact", key = "'customerId='+#customerId+',type='+#type", unless = "#result==null")
	public ContactDto getContactByCustomerAndType(long customerId, String type) {
		return mapFunction.apply(contactRepository.findByCustomerIdAndContactType(customerId, type));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "contactList", unless = "#result.isEmpty()")
	public List<ContactDto> getContactsByCustomer(long customerId) {
		return FluentIterable.from(contactRepository.findByCustomerId(customerId)).transform(mapFunction).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContactDto> getByEmail(String email) {

		return FluentIterable.from(contactRepository.findAll(ContactPredicates.containsCommunication(CommunicationType.EMAIL
																													  .toString(),
																									 email)))
							 .transform(mapFunction)
							 .toList();

	}

	private class MapFunction implements Function<Contact, ContactDto> {
		@Nullable
		@Override
		public ContactDto apply(@Nullable Contact contact) {
			if (contact == null) {
				return null;
			}
			ContactDto contactDto = mapper.map(contact, ContactDto.class);
			Future<String> city = addressResolver.resolveCity(contactDto.getCountryCode(), contactDto.getZipCode());
			Future<String> country = addressResolver.resolveCountry(contactDto.getCountryCode());
			try {
				contactDto.setCity(city.get());
				contactDto.setCountryName(country.get());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			cacheSync.put(contactDto);
			return contactDto;
		}
	}
}
