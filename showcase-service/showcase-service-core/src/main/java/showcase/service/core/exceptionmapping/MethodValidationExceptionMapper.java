package showcase.service.core.exceptionmapping;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.BeanUtils;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.dto.ValidationResponseDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.inject.Named;

@Named
public class MethodValidationExceptionMapper extends AbstractExceptionMapper<MethodConstraintViolationException, ValidationResponseDto> {

    @Override
    public ValidationResponseDto map(MethodConstraintViolationException throwable,
                                     Class<? extends ValidationResponseDto> returnType) {
        Set<MethodConstraintViolation<?>> constraintViolations = throwable.getConstraintViolations();

        Collection<ValidationErrorDto> errors = new ArrayList<ValidationErrorDto>(constraintViolations.size());
        for (MethodConstraintViolation<?> cv : constraintViolations) {
            String propertyPath = cv.getPropertyPath().toString();
            String[] pathParts = propertyPath.split("\\)\\.");
            String pp = null;
            if (pathParts.length == 2) {
                pp = pathParts[1];
            }

            errors.add(new ValidationErrorDto(cv.getParameterIndex(), cv.getParameterName(), pp, cv.getMessage()));
        }

        ValidationResponseDto returnValue = BeanUtils.instantiate(returnType);
        returnValue.setValidationErrors(errors);
        return returnValue;
    }

}
