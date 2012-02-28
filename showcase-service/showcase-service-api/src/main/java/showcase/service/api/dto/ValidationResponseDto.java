package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Collection;

public class ValidationResponseDto implements Serializable {

    private Collection<ValidationErrorDto> validationErrors;

    public Collection<ValidationErrorDto> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Collection<ValidationErrorDto> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ValidationResponseDto");
        sb.append("{validationErrors=").append(validationErrors);
        sb.append('}');
        return sb.toString();
    }

}
