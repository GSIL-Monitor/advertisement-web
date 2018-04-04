package com.yuanshanbao.common.validator.constraints;

import java.lang.annotation.Annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;

/**
 * Defines the logic to validate a given constraint {@code A}
 * for a given object type {@code T}.
 * <p/>
 * Implementations must comply to the following restriction:
 * <ul>
 *     <li>{@code T} must resolve to a non parameterized type</li>
 *     <li>or generic parameters of {@code T} must be unbounded
 *     wildcard types</li>
 * </ul>
 * <p/>
 * The annotation {@link SupportedValidationTarget} can be put on a
 * {@code ConstraintValidator} implementation to mark it as supporting
 * cross-parameter constraints. Check out {@link SupportedValidationTarget}
 * and {@link Constraint} for more information.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ConstraintValidator<T> {

	/**
	 * Initializes the validator in preparation for
	 * {@link #isValid(Object, ConstraintValidatorContext)} calls.
	 * The constraint annotation for a given constraint declaration
	 * is passed.
	 * <p/>
	 * This method is guaranteed to be called before any use of this instance for
	 * validation.
	 *
	 * @param constraintAnnotation annotation instance for a given constraint declaration
	 */
	void initialize(Annotation constraintAnnotation);

	/**
	 * Implements the validation logic.
	 * The state of {@code value} must not be altered.
	 * <p/>
	 * This method can be accessed concurrently, thread-safety must be ensured
	 * by the implementation.
	 *
	 * @param value object to validate
	 * @param context context in which the constraint is evaluated
	 *
	 * @return {@code false} if {@code value} does not pass the constraint
	 */
	boolean isValid(T value);
}