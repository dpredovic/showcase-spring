package showcase.service.api;

import java.util.List;
import javax.jws.WebService;

import showcase.common.ContactType;
import showcase.service.api.dto.ContactDto;

@WebService
public interface ContactService {

    String JNDI_NAME = VersionData.jndiName(ContactService.class);

    ContactDto getContact(long contactId);

    ContactDto getContactByCustomerAndType(long customerId, ContactType type);

    List<ContactDto> getContactsByCustomer(long customerId);
}
