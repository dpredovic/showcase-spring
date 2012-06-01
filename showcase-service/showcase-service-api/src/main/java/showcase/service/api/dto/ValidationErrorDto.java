package showcase.service.api.dto;

import lombok.Data;

@Data
public class ValidationErrorDto {

    private int paramIndex;
    private String paramName;

    private String propertyPath;

    private String message;

}
