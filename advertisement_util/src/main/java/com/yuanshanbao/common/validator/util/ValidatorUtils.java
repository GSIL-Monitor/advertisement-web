package com.yuanshanbao.common.validator.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;

import com.yuanshanbao.common.util.LoggerUtil;

public class ValidatorUtils {

	private static Map<Class<?>, List<ValidatorModel>> validatorMap = new HashMap<Class<?>, List<ValidatorModel>>();

	public static ValidatorModel validate(Object object) {
		List<ValidatorModel> validateModelList = validatorMap.get(object.getClass());
		if (validateModelList == null) {
			try {
				validateModelList = buildValidateModel(object);
				validatorMap.put(object.getClass(), validateModelList);
			} catch (Exception e) {
				LoggerUtil.error("[Error build validate model: ]", e);
			}
		}
		for (ValidatorModel model : validateModelList) {
			try {
				model.getField().setAccessible(true);
				Object value = model.getField().get(object);
				if (model.getValidator() != null && !model.getValidator().isValid(value, null)) {
					return model;
				}
			} catch (Exception e) {
				LoggerUtil.error("[Error validate parameters: ]", e);
				return model;
			}
		}
		return null;
	}

	private static List<ValidatorModel> buildValidateModel(Object object) throws Exception {
		List<ValidatorModel> list = new ArrayList<ValidatorModel>();
		Class<? extends Object> clazz = object.getClass();
		Method[] methods = clazz.getMethods();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				methods = annotation.annotationType().getMethods();
				int messageCode = 0;
				ConstraintValidator<Annotation, Object> validator = null;
				for (Method method : methods) {
					if (method.getName().equals("messageCode")) {
						messageCode = (int) method.invoke(annotation, null);
					}
					if (method.getName().equals("validator")) {
						Class<?> validatorClass = (Class<?>) method.invoke(annotation, null);
						validator = (ConstraintValidator<Annotation, Object>) validatorClass.newInstance();
						validator.initialize(annotation);
					}
				}
				list.add(new ValidatorModel(field, validator, messageCode));
			}
		}
		return list;
	}
}
