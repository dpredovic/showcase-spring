package showcase.service.core;

import org.joda.time.LocalDate;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;
import showcase.service.api.type.CustomerType;
import showcase.service.api.type.DispatchType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

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

        CustomerDto customer = new CustomerDto();
        customer.setCooperationPartnerId(1L);
        customer.setCustomerType(CustomerType.PERSON.toString());
        customer.setDispatchType(DispatchType.EMAIL.toString());
        customer.setRegistrationDate(LocalDate.now().toDate());
        customer.getProperties().put("platinum", "true");

        ContactDto standardContact = createContact(suffix, ContactType.STANDARD);
        ContactDto invoicingContact = createContact(suffix, ContactType.INVOICING);
        ContactDto otherContact1 = createContact(suffix, null);
        ContactDto otherContact2 = createContact(suffix, null);

        return new CreateCustomerRequestDto(customer,
                                            Arrays.asList(standardContact,
                                                          invoicingContact,
                                                          otherContact1,
                                                          otherContact2));
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
