package org.asettings;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javassist.Modifier;

import org.apache.log4j.Logger;
import org.asettings.SettingObject;
import org.asettings.SettingsValuesSource;
import org.asettings.fieldsproviders.FieldsProvider;
import org.asettings.namingstrategies.NamingException;
import org.asettings.namingstrategies.NamingStrategy;

public class SettingsServiceImpl {
	private static final String SETTING_ALREADY_DEFINED = "Setting {0} already defined in {1} - new value ignored.";
	private static final String SERVICE_NOT_INITIALIZED = "Settings service is not initialized properly";
	private static final String NOT_A_SETTING_EXCEPTION = "Field {0} is not @Setting";
	private static final String UNACCESSIBLE_FIELD_EXCEPTION = "Field: {0} should be static public and non-final";
	private static Logger logger = Logger.getLogger(SettingsServiceImpl.class);
	private HashMap<String, SettingObject> settingsValuesStorage;
	private SettingsValuesSource settingsValuesSource;
	private FieldsProvider fieldsProvider;
	private NamingStrategy namingStrategy;

	public SettingsServiceImpl(SettingsValuesSource settingsValuesSource,
			FieldsProvider fieldsProvider, NamingStrategy namingStrategy) {
		this.settingsValuesSource = settingsValuesSource;
		this.fieldsProvider = fieldsProvider;
		this.namingStrategy = namingStrategy;

		settingsValuesStorage = new HashMap<String, SettingObject>();

	}

	public boolean initialize() {
		boolean hasErrors = false;

		Iterator<Field> fieldsIterator = fieldsProvider.provideFields();

		while (fieldsIterator.hasNext()) {
			Field currentField = fieldsIterator.next();
			hasErrors = hasErrors || handleSetting(settingsValuesSource, namingStrategy, currentField);
		}
		return !hasErrors;

	}

	private boolean handleSetting(SettingsValuesSource source, NamingStrategy namer, Field field) {
		logger.debug("Handling field " + formFieldName(field));
		boolean hasError = false;
		try {
			Setting setting = field.getAnnotation(Setting.class);
			String settingName = namer.nameSetting(field);
			validateSetting(field, setting);
			saveSetting(settingName, field, setting, source.getValue(settingName));
		}
		catch (IllegalAccessException e) {
			hasError = true;
			logger.error(MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION,	formFieldName(field)), e);
		} 
		catch (SettingValidationException e) {
			hasError = true;
			logger.error(e);
		}
		catch (NamingException e) {
			hasError = true;
			logger.error(e);
		}
		return hasError;
	}

	
	private void saveSetting(String settingName, Field field, Setting setting, Object settingValue) throws IllegalAccessException, IllegalArgumentException, SettingValidationException {
		SettingObject settingObject = createSettingObject(field, setting,	settingValue);
		
		if (settingsValuesStorage.containsKey(settingObject.getName())) {
			logger.warn(MessageFormat.format(
					SETTING_ALREADY_DEFINED,
					settingName, formFieldName(settingsValuesStorage.get(settingObject.getName()).getField())));
		}
		else {
			settingsValuesStorage.put(settingObject.getName(), settingObject);
		}

	}

	private SettingObject createSettingObject(Field field, Setting setting,	Object settingValue) throws SettingValidationException {
		//TODO: factory it
		SettingObject settingObject = null;
		
		try {
			Object defaultValue = field.get(null);
			String settingName = namingStrategy.nameSetting(field);
			
			if (settingValue == null && defaultValue == null) {
				throw new SettingValidationException("Setting " + settingName + " has no default or defined value");
			}
			else if (settingValue == null && setting.mandatory()) {
				throw new SettingValidationException("Mandatory Setting " + settingName + " has no defined value");
			}

			settingObject =  new SettingObjectImpl();
			
			settingObject.setDefaultValue(defaultValue);
			settingObject.setMandatory(setting.mandatory());
			settingObject.setName(settingName);
			
			if (setting.description() == null || setting.description().equals("")) {
				settingObject.setDescription(setting.name());
			} else {
				settingObject.setDescription(setting.description());
			}
			if (settingValue != null) {
				settingObject.setValue(settingValue);
			}
			else {
				settingObject.setValue(settingObject.getDefaultValue());
			}

			field.set(null, settingObject.getValue());
		} 
		catch (IllegalAccessException e) {
			String errorMessage = MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION, formFieldName(field));
			logger.error(errorMessage, e);
			throw new SettingValidationException(errorMessage);
		} catch (NamingException e) {

			e.printStackTrace();
		}
		return settingObject;
	}

	private void validateSetting(Field field, Setting setting) throws SettingValidationException {
		logger.debug("Validting field " + formFieldName(field));
		if(setting==null){
			throw new SettingValidationException(MessageFormat.format(NOT_A_SETTING_EXCEPTION, formFieldName(field)));
		}
		if (isFieldAccessibleAndStatic(field)) {
			throw new SettingValidationException(MessageFormat.format(UNACCESSIBLE_FIELD_EXCEPTION,formFieldName(field)));
		}
	}

	private boolean isFieldAccessibleAndStatic(Field field) {
		return !Modifier.isStatic(field.getModifiers())
				|| !Modifier.isPublic(field.getModifiers())
				|| Modifier.isFinal(field.getModifiers());
	}

	public SettingObject getSetting(String name) {
		if(settingsValuesStorage==null){
			throw new IllegalStateException(SERVICE_NOT_INITIALIZED);
		}
		return settingsValuesStorage.get(name);
	}

	public void setSetting(SettingObject setting, String name) {
		if(settingsValuesStorage==null){
			throw new IllegalStateException(SERVICE_NOT_INITIALIZED);
		}
		settingsValuesStorage.put(null, setting);
	}

	
	public Object getSettingValue(String name) {
		SettingObject setting = getSetting(name);
		return setting == null ? null : setting.getValue();
	}

	public void setSettingValue(Object value, String name) {
		SettingObject setting = getSetting(name);
		if(setting == null)	{
			logger.warn("Trying to save non-existing setting. Ignoring");
		}
		else {
			setting.setValue(value);
		}
	}

	@SuppressWarnings("serial")
	private class SettingValidationException extends Exception {

		public SettingValidationException(String message) {
			super(message);
		}

	}

	/**
	 * Use to reload setting values from the source to settings fields Any
	 * previous changes to fields will be lost
	 */
	public void refetch() {
	}

	/**
	 * Use to push changes from settings fields to setting source Opposite to
	 * refetch()
	 */
	public void flush() {
		
	}
	

	
	private static String formFieldName(Field field) {
		StringBuilder name = new StringBuilder()
				.append(field.getDeclaringClass().getName()).append(".")
				.append(field.getName());
		return name.toString();
	}
}
