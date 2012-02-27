package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateCustomerRequestDto implements Serializable {

    @Valid
    @NotNull
    private CustomerDto customer;

    @Valid
    @NotNull
    @Size(min = 1)
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
