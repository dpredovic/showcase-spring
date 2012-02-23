package showcase.service.core;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import showcase.common.ContactType;
import showcase.persistence.repository.ContactRepository;
import showcase.persistence.unit.Contact;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactDao;

    @Autowired
    private Mapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContact(long contactId) {
        Contact contact = contactDao.findOne(contactId);
        if (contact == null) {
            return null;
        }

        return mapper.map(contact, ContactDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContactByCustomerAndType(long customerId, ContactType type) {
        Contact contact = contactDao.findByCustomerIdAndContactType(customerId, type);
        if (contact == null) {
            return null;
        }

        return mapper.map(contact, ContactDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> getContactsByCustomer(long customerId) {
        List<Contact> contacts = contactDao.findByCustomerId(customerId);

        List<ContactDto> contactDtos = new ArrayList<ContactDto>(contacts.size());
        for (Contact contact : contacts) {
            ContactDto contactDto = mapper.map(contact, ContactDto.class);
            contactDtos.add(contactDto);
        }
        return contactDtos;
    }
}
