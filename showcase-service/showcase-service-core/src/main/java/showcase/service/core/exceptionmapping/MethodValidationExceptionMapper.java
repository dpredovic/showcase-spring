package showcase.service.core.exceptionmapping;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.BeanUtils;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.dto.ValidationResponseDto;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Collection;

@Named
public class MethodValidationExceptionMapper
	extends AbstractExceptionMapper<MethodConstraintViolationException, ValidationResponseDto> {

	@Override
	public ValidationResponseDto map(MethodConstraintViolationException throwable,
									 Class<? extends ValidationResponseDto> returnType) {
		Collection<ValidationErrorDto> errors =
			FluentIterable.from(throwable.getConstraintViolations()).transform(new MapFunction()).toList();

		ValidationResponseDto returnValue = BeanUtils.instantiate(returnType);
		returnValue.setValidationErrors(errors);
		return returnValue;
	}

	private static class MapFunction implements Function<MethodConstraintViolation<?>, ValidationErrorDto> {
		@Nullable
		@Override
		public ValidationErrorDto apply(@Nullable MethodConstraintViolation<?> cv) {
			String propertyPath = cv.getPropertyPath().toString();
			String[] pathParts = propertyPath.split("\\)\\.");
			String pp = null;
			if (pathParts.length == 2) {
				pp = pathParts[1];
			}
			return new ValidationErrorDto(cv.getParameterIndex(), cv.getParameterName(), pp, cv.getMessage());
		}
	}
}
