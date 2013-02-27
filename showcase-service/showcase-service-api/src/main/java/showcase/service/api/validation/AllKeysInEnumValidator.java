package showcase.service.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class AllKeysInEnumValidator implements ConstraintValidator<AllKeysInEnum, Map<String, ?>> {

	private Enum<?>[] enumConstants;

	@Override
	public void initialize(AllKeysInEnum constraintAnnotation) {
		Class<? extends Enum<?>> enumClass = constraintAnnotation.value();
		enumConstants = enumClass.getEnumConstants();
	}

	@Override
	public boolean isValid(Map<String, ?> values, ConstraintValidatorContext context) {
		for (String value : values.keySet()) {
			if (value == null) {
				continue;
			}

			boolean found = false;
			for (Enum<?> enumConstant : enumConstants) {
				if (enumConstant.toString().equals(value)) {
					found = true;
					break;
				}
			}

			if (!found) {
				return false;
			}
		}
		return true;
	}
}
