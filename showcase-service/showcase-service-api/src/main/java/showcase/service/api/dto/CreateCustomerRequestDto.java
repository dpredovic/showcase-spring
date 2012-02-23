package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Collection;

public class CreateCustomerRequestDto implements Serializable {

    private CustomerDto customer;

    private Collection<ContactDto> contacts;

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public Collection<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<ContactDto> contacts) {
        this.contacts = contacts;
    }
}
