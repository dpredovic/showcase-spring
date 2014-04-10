package showcase.service.core.exceptionmapping;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.BeanUtils;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.dto.ValidationResponseDto;

import java.util.Collection;
import java.util.Iterator;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

@Named
public class MethodValidationExceptionMapper
    extends AbstractExceptionMapper<ConstraintViolationException, ValidationResponseDto> {

    @Override
    public ValidationResponseDto map(
        ConstraintViolationException throwable, Class<? extends ValidationResponseDto> returnType) {
        Collection<ValidationErrorDto> errors =
            FluentIterable.from(throwable.getConstraintViolations()).transform(new MapFunction()).toList();

        ValidationResponseDto returnValue = BeanUtils.instantiate(returnType);
        returnValue.setValidationErrors(errors);
        return returnValue;
    }

    private static class MapFunction implements Function<ConstraintViolation<?>, ValidationErrorDto> {

        @Override
        public ValidationErrorDto apply(ConstraintViolation<?> input) {
            Iterator<Path.Node> propertyPath = input.getPropertyPath().iterator();
            propertyPath.next(); // skip past the method
            Path.ParameterNode parameter = propertyPath.next().as(Path.ParameterNode.class);
            StringBuilder sb = new StringBuilder();
            while (propertyPath.hasNext()) {
                Path.PropertyNode property = propertyPath.next().as(Path.PropertyNode.class);
                sb.append(property);
                if (propertyPath.hasNext()) {
                    sb.append(".");
                }
            }
            return new ValidationErrorDto(parameter.getParameterIndex(), parameter.getName(), sb.toString(),
                                          input.getMessage());
        }
    }
}
