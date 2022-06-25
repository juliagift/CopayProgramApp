package org.juliagift.copaydrugprogram.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
//create a new @interface to define our new custom constraint
public @interface BirthDate {

	// When validation fails, we return the error message mentioned for
	// @BirthDateValidation annotation.
//	String message() default "You must be 18 years or older";
	String message() default "{constraints.birthdate}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	


}
