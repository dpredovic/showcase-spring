package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Collection;

import lombok.Data;

@Data
public class ValidationResponseDto implements Serializable {

    private Collection<ValidationErrorDto> validationErrors;

}
