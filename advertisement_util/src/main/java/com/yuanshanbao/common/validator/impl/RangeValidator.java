/*
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuanshanbao.common.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yuanshanbao.common.validator.constraints.Range;

/**
 * Check that a character sequence's (e.g. string) trimmed length is not empty.
 *
 * @author Hardy Ferentschik
 */
public class RangeValidator implements ConstraintValidator<Range, Object> {

	private Range annotation;

	public void initialize(Range annotation) {
		this.annotation = annotation;
	}

	/**
	 * Checks that the trimmed string is not empty.
	 *
	 * @param charSequence
	 *            The character sequence to validate.
	 * @param constraintValidatorContext
	 *            context in which the constraint is evaluated.
	 *
	 * @return Returns <code>true</code> if the string is <code>null</code> or
	 *         the length of <code>charSequence</code> between the specified
	 *         <code>min</code> and <code>max</code> values (inclusive),
	 *         <code>false</code> otherwise.
	 */
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return false;
		}
		if (value instanceof Long) {
			return (Long) value <= annotation.max() && (Long) value >= annotation.min();
		}
		if (value instanceof Integer) {
			return (Integer) value <= annotation.max() && (Integer) value >= annotation.min();
		}
		if (value instanceof Double) {
			return (Double) value <= annotation.max() && (Double) value >= annotation.min();
		}
		if (value instanceof Float) {
			return (Float) value <= annotation.max() && (Float) value >= annotation.min();
		}
		return false;
	}
}
