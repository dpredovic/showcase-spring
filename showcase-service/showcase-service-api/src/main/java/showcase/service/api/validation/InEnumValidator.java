package showcase.service.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InEnumValidator implements ConstraintValidator<InEnum, String> {

	private Enum<?>[] enumConstants;

	@Override
	public void initialize(InEnum constraintAnnotation) {
		Class<? extends Enum<?>> enumClass = constraintAnnotation.value();
		enumConstants = enumClass.getEnumConstants();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		for (Enum<?> enumConstant : enumConstants) {
			if (enumConstant.toString().equals(value)) {
				return true;
			}
		}
		return false;
	}
}
