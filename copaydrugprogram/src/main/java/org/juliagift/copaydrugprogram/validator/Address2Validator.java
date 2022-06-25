package org.juliagift.copaydrugprogram.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Address2Validator implements ConstraintValidator<Address2, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		
//		if(value == null) {
//			return true;
//		}
		
		if(value.equals("")) {
			return true;
		}
		
		if(value.matches("^[a-zA-Z0-9 .]+$")) {
			return true;
		}
		
		return false;
	}

	

}
