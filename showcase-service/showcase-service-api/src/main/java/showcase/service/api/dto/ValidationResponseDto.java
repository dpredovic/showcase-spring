package showcase.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDto implements Serializable {

	private Collection<ValidationErrorDto> validationErrors;

}
