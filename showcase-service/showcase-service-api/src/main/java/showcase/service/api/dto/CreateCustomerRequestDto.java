package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateCustomerRequestDto implements Serializable {

    @Valid
    @NotNull
    private CustomerDto customer;

    @Valid
    @NotNull
    @Size(min = 1)
    private Collection<ContactDto> contacts;

}
