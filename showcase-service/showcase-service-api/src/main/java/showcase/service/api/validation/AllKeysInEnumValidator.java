package showcase.service.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.Map;

public class AllKeysInEnumValidator implements ConstraintValidator<AllKeysInEnum, Map<String, ?>> {

	private EnumSet<?> enumSet;

	@Override
	public void initialize(AllKeysInEnum constraintAnnotation) {
		initSet(constraintAnnotation);
	}

	@Override
	public boolean isValid(Map<String, ?> values, ConstraintValidatorContext context) {
		for (String value : values.keySet()) {
			if (value != null) {
				boolean found = false;
				for (Enum<?> enumConstant : enumSet) {
					if (enumConstant.toString().equals(value)) {
						found = true;
						break;
					}
				}

				if (!found) {
					return false;
				}
			}
		}
		return true;
	}

	private <E extends Enum<E>> void initSet(AllKeysInEnum constraintAnnotation) {
		@SuppressWarnings("unchecked")
		Class<E> enumClass = (Class<E>) constraintAnnotation.value();
		enumSet = EnumSet.allOf(enumClass);
	}
}
