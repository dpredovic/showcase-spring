package showcase.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequestDto implements Serializable {

	@Valid
	@NotNull
	private CustomerDto customer;
	@Valid
	@NotNull
	@Size(min = 1)
	private Collection<ContactDto> contacts;

}
