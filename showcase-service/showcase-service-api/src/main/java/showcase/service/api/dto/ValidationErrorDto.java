package showcase.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDto implements Serializable {

	private int paramIndex;
	private String paramName;
	private String propertyPath;
	private String message;

}
