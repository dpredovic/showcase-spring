package showcase.service.core;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import showcase.common.ContactType;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.core.cache.CacheSync;
import showcase.zipresolver.ZipResolver;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ZipResolver zipResolver;

    @Autowired
    private CacheSync cacheSync;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "contact", key = "'id='+#contactId")
    public ContactDto getContact(long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return null;
        }
        ContactDto contactDto = mapAndEnrichCity(contact);
        cacheSync.put(contactDto);
        return contactDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "contact", key = "'customerId='+#customerId+',type='+#type")
    public ContactDto getContactByCustomerAndType(long customerId, ContactType type) {
        Contact contact = contactRepository.findByCustomerIdAndContactType(customerId, type);
        if (contact == null) {
            return null;
        }

        ContactDto contactDto = mapAndEnrichCity(contact);
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
            ContactDto contactDto = mapAndEnrichCity(contact);
            contactDtos.add(contactDto);
            cacheSync.put(contactDto);
        }
        return contactDtos;
    }

    private ContactDto mapAndEnrichCity(Contact contact) {
        ContactDto contactDto = mapper.map(contact, ContactDto.class);
        String city = zipResolver.resolveCity(contactDto.getCountryCode(), contactDto.getZipCode());
        contactDto.setCity(city);
        return contactDto;
    }
}
