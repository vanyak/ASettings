package org.asettings;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import javassist.Modifier;

import org.apache.log4j.Logger;
import org.asettings.SettingValidationException;

public class SettingValidator
{
	private static final Logger logger = Logger.getLogger(SettingValidator.class);
	public static final String NOT_A_SETTING_EXCEPTION = "Field {0} is not @Setting";
	public static final String UNACCESSIBLE_FIELD_EXCEPTION = "Field: {0} should be static public and non-final";

	public void validateSetting(Field field, Setting setting, Object settingValue) throws SettingValidationException {
		String fieldName = formFieldName(field);

		logger.debug("Validting field " + fieldName);
		if(setting==null){
			throw new SettingValidationException(MessageFormat.format(NOT_A_SETTING_EXCEPTION, fieldName));
		}
		if (isFieldAccessibleAndStatic(field)) {
			throw new SettingValidationException(MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION,fieldName));
		}
		if (settingValue == null && getDefaultValue(field) == null) {
			throw new SettingValidationException("Setting " + fieldName + " has no default or defined value");
		}
		if (settingValue == null && setting.mandatory()) {
			throw new SettingValidationException("Mandatory Setting " + fieldName + " has no defined value");
		}
	}

	private Object getDefaultValue(Field field) throws SettingValidationException
	{
		String fieldName = formFieldName(field);
		try
		{
			return field.get(null);
		}
		catch (IllegalArgumentException e)
		{
			throw new SettingValidationException(MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION,fieldName));
		}
		catch (IllegalAccessException e)
		{
			throw new SettingValidationException(MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION,fieldName));
		}
	}

	private boolean isFieldAccessibleAndStatic(Field field) {
		return !Modifier.isStatic(field.getModifiers())
				|| !Modifier.isPublic(field.getModifiers())
				|| Modifier.isFinal(field.getModifiers());
	}

	private static String formFieldName(Field field) {
		StringBuilder name = new StringBuilder()
				.append(field.getDeclaringClass().getName()).append(".")
				.append(field.getName());
		return name.toString();
	}

}
