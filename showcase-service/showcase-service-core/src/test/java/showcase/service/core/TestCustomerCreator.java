package showcase.service.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.LocalDate;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;
import showcase.service.api.type.CustomerType;
import showcase.service.api.type.DispatchType;

@Named
public class TestCustomerCreator {

    @Inject
    private CustomerService customerService;

    public CustomerDto createCustomer() {
        return createCustomer("");
    }

    public CustomerDto createCustomer(String suffix) {
        CreateCustomerRequestDto requestDto = createRequest(suffix);

        Long id = customerService.createCustomer(requestDto).getId();
        requestDto.getCustomer().setId(id);

        return requestDto.getCustomer();
    }

    public CreateCustomerRequestDto createRequest(String suffix) {
        ContactDto standardContact = createContact(suffix, ContactType.STANDARD);
        ContactDto invoicingContact = createContact(suffix, ContactType.INVOICING);
        ContactDto otherContact1 = createContact(suffix, null);
        ContactDto otherContact2 = createContact(suffix, null);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("platinum", "true");

        CustomerDto customer = new CustomerDto();
        customer.setCooperationPartnerId(1L);
        customer.setCustomerType(CustomerType.PERSON.toString());
        customer.setDispatchType(DispatchType.EMAIL.toString());
        customer.setRegistrationDate(LocalDate.now().toDate());
        customer.setProperties(properties);

        CreateCustomerRequestDto requestDto = new CreateCustomerRequestDto();
        requestDto.setCustomer(customer);
        requestDto.setContacts(Arrays.asList(standardContact, invoicingContact, otherContact1, otherContact2));
        return requestDto;
    }

    private ContactDto createContact(String suffix, ContactType contactType) {
        ContactDto contact = new ContactDto();
        contact.setFirstName("stfn" + suffix);
        contact.setLastName("stln" + suffix);
        if (contactType != null) {
            contact.setContactType(contactType.toString());
        }
        contact.setStreet("street" + suffix);
        contact.setZipCode("zip" + suffix);
        contact.setCountryCode("cc" + suffix);
        contact.getCommunications().put(CommunicationType.EMAIL.toString(), "test" + suffix + "@mail.com");
        return contact;
    }

}
