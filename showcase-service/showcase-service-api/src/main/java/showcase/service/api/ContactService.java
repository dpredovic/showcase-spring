package showcase.service.api;

import java.util.List;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;

import showcase.service.api.dto.ContactDto;

@WebService
public interface ContactService {

    String JNDI_NAME = VersionData.jndiName(ContactService.class);

    ContactDto getContact(long contactId);

    ContactDto getContactByCustomerAndType(
            long customerId,
            @NotNull
            String type);

    List<ContactDto> getContactsByCustomer(long customerId);

    List<ContactDto> getByEmail(
            @NotNull
            String email);
}
