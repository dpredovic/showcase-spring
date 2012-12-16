package showcase.service.core;

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

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Named
@Transactional
@Validated
@ExceptionsMapped
public class ContactServiceImpl implements ContactService {

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
    @Cacheable(value = "contact", key = "'id='+#contactId")
    public ContactDto getContact(long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return null;
        }
        ContactDto contactDto = mapAndEnrichAddress(contact);
        cacheSync.put(contactDto);
        return contactDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "contact", key = "'customerId='+#customerId+',type='+#type")
    public ContactDto getContactByCustomerAndType(long customerId, String type) {
        Contact contact = contactRepository.findByCustomerIdAndContactType(customerId, type);
        if (contact == null) {
            return null;
        }

        ContactDto contactDto = mapAndEnrichAddress(contact);
        cacheSync.put(contactDto);
        return contactDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("contactList")
    public List<ContactDto> getContactsByCustomer(long customerId) {
        List<Contact> contacts = contactRepository.findByCustomerId(customerId);

        List<ContactDto> contactDtos = new ArrayList<ContactDto>(contacts.size());
        for (Contact contact : contacts) {
            ContactDto contactDto = mapAndEnrichAddress(contact);
            contactDtos.add(contactDto);
            cacheSync.put(contactDto);
        }
        return contactDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> getByEmail(String email) {
        Iterable<Contact> contacts = contactRepository.findAll(ContactPredicates.containsCommunication(CommunicationType
                                                                                                               .EMAIL
                                                                                                               .toString(),
                                                                                                       email));

        List<ContactDto> contactDtos = new ArrayList<ContactDto>();
        for (Contact contact : contacts) {
            ContactDto contactDto = mapAndEnrichAddress(contact);
            contactDtos.add(contactDto);
            cacheSync.put(contactDto);
        }
        return contactDtos;
    }

    private ContactDto mapAndEnrichAddress(Contact contact) {
        ContactDto contactDto = mapper.map(contact, ContactDto.class);
        Future<String> city = addressResolver.resolveCity(contactDto.getCountryCode(), contactDto.getZipCode());
        Future<String> country = addressResolver.resolveCountry(contactDto.getCountryCode());
        try {
            contactDto.setCity(city.get());
            contactDto.setCountryName(country.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return contactDto;
    }
}
