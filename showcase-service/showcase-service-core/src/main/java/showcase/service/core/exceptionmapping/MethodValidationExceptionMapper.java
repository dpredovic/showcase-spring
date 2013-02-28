package showcase.service.core.exceptionmapping;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.BeanUtils;
import showcase.service.api.dto.ValidationErrorDto;
import showcase.service.api.dto.ValidationResponseDto;

import javax.inject.Named;
import java.util.Collection;
import java.util.regex.Pattern;

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
		private static final Pattern PATTERN = Pattern.compile("\\)\\.");

		@Override
		public ValidationErrorDto apply(MethodConstraintViolation<?> cv) {
			String propertyPath = cv.getPropertyPath().toString();
			String[] pathParts = PATTERN.split(propertyPath);
			String pp = null;
			if (pathParts.length == 2) {
				pp = pathParts[1];
			}
			return new ValidationErrorDto(cv.getParameterIndex(), cv.getParameterName(), pp, cv.getMessage());
		}
	}
}
