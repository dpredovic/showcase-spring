package showcase.service.core.exceptionmapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.dto.ValidationResponseDto;

@Component
public class MethodValidationExceptionMapper extends AbstractExceptionMapper<MethodConstraintViolationException, ValidationResponseDto> {

    @Override
    public ValidationResponseDto map(MethodConstraintViolationException throwable, Class<? extends ValidationResponseDto> returnType) {
        Set<MethodConstraintViolation<?>> constraintViolations = throwable.getConstraintViolations();

        Collection<ValidationErrorDto> errors = new ArrayList<ValidationErrorDto>(constraintViolations.size());
        for (MethodConstraintViolation<?> constraintViolation : constraintViolations) {
            ValidationErrorDto error = new ValidationErrorDto();
            error.setMessage(constraintViolation.getMessage());
            error.setParamIndex(constraintViolation.getParameterIndex());
            error.setParamName(constraintViolation.getParameterName());
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String[] pathParts = propertyPath.split("\\)\\.");
            if (pathParts.length == 2) {
                error.setPropertyPath(pathParts[1]);
            }
            errors.add(error);
        }

        ValidationResponseDto returnValue = BeanUtils.instantiate(returnType);
        returnValue.setValidationErrors(errors);
        return returnValue;
    }

}
