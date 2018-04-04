package com.yuanshanbao.common.validator.util;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;

public class ValidatorModel {

	private Field field;
	private ConstraintValidator<?, Object> validator;
	private int messageCode;

	public ValidatorModel(Field field, ConstraintValidator<?, Object> validator, int messageCode) {
		super();
		this.field = field;
		this.validator = validator;
		this.messageCode = messageCode;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ConstraintValidator<?, Object> getValidator() {
		return validator;
	}

	public void setValidator(ConstraintValidator<?, Object> validator) {
		this.validator = validator;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

}
