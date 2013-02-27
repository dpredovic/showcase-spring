package showcase.service.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;

public class InEnumValidator implements ConstraintValidator<InEnum, String> {

	private EnumSet<?> enumSet;

	@Override
	public void initialize(InEnum constraintAnnotation) {
		initSet(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		for (Enum<?> enumConstant : enumSet) {
			if (enumConstant.toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

	private <E extends Enum<E>> void initSet(InEnum constraintAnnotation) {
		@SuppressWarnings("unchecked")
		Class<E> enumClass = (Class<E>) constraintAnnotation.value();
		enumSet = EnumSet.allOf(enumClass);
	}
}
