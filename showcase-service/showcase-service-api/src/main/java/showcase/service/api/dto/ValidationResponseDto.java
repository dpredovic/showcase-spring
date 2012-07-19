package showcase.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDto implements Serializable {

    private Collection<ValidationErrorDto> validationErrors;

}
