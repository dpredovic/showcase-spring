package showcase.service.api;

import showcase.service.api.dto.CreateCustomerRequestDto;
import showcase.service.api.dto.CreateCustomerResponseDto;
import showcase.service.api.dto.CustomerDto;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@WebService(serviceName = "CustomerService")
public interface CustomerService {

	String JNDI_NAME = VersionData.jndiName(CustomerService.class);

	CreateCustomerResponseDto createCustomer(@NotNull @Valid CreateCustomerRequestDto requestDto);

	CustomerDto getById(long id);

}
