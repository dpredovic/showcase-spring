package showcase.service.api;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CustomerDto;

@WebService
public interface CustomerService {

    String JNDI_NAME = VersionData.jndiName(CustomerService.class);

    Long createCustomer(
            @NotNull
            @Valid
            CreateCustomerRequestDto requestDto
    );

    CustomerDto getById(long id);

}
