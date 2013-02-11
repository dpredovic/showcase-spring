package showcase.service.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
@Documented
public @interface InEnum {

    String message() default "value not in enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> value();

}
