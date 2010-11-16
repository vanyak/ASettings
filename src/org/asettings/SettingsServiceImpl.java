package org.asettings;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.asettings.SettingObject;
import org.asettings.SettingsValuesSource;
import org.asettings.fieldsproviders.FieldsProvider;
import org.asettings.namingstrategies.NamingException;
import org.asettings.namingstrategies.NamingStrategy;

public class SettingsServiceImpl {
	private static final String SETTING_ALREADY_DEFINED = "Setting {0} already defined in {1} - new value ignored.";
	private static final String SERVICE_NOT_INITIALIZED = "Settings service is not initialized properly";
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

		SettingValidator validator = new SettingValidator();
		Iterator<Field> fieldsIterator = fieldsProvider.provideFields();
		
		while (fieldsIterator.hasNext()) {
			Field currentField = fieldsIterator.next();
			hasErrors = hasErrors || handleSetting(settingsValuesSource, namingStrategy, currentField, validator);
		}
		return !hasErrors;

	}

	private boolean handleSetting(SettingsValuesSource source, NamingStrategy namer, Field field, SettingValidator validator) {
		logger.debug("Handling field " + formFieldName(field));
		boolean hasError = false;
		try {
			Setting setting = field.getAnnotation(Setting.class);
			String settingName = namer.nameSetting(field);
			Object settingValue = source.getValue(settingName);
			validator.validateSetting(field, setting, settingValue);
			saveSetting(settingName, field, setting, settingValue);
		}
		catch (IllegalAccessException e) {
			hasError = true;
			logger.error(MessageFormat.format(SettingValidator.UNACCESSIBLE_FIELD_EXCEPTION,	formFieldName(field)), e);
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

	
	private void saveSetting(String settingName, Field field, Setting setting, Object settingValue) throws IllegalAccessException, IllegalArgumentException, SettingValidationException, NamingException {
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

	private SettingObject createSettingObject(Field field, Setting setting,	Object settingValue) throws SettingValidationException, IllegalArgumentException, IllegalAccessException, NamingException {
		Object defaultValue = field.get(null);
		String settingName = namingStrategy.nameSetting(field);
		
		SettingObject settingObject =  newSettingObject();
		
		settingObject.setField(field);
		fillSettingValues(setting, settingValue, settingObject, defaultValue, settingName);
		
		return settingObject;
	}

	protected SettingObject newSettingObject()
	{
		return new SettingObjectImpl();
	}

	private void fillSettingValues(Setting setting, Object settingValue, SettingObject settingObject, Object defaultValue, String settingName) throws IllegalArgumentException, IllegalAccessException
	{
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
		try
		{
			return setting == null ? null : setting.getValue();
		}
		catch (IllegalArgumentException e)
		{
			logger.error("Unexpectedly unaccessiable setting " + name);
			throw new IllegalStateException(e);
		}
		catch (IllegalAccessException e)
		{
			logger.error("Unexpectedly unaccessiable setting " + name);
			throw new IllegalStateException(e);
		}
	}

	public void setSettingValue(Object value, String name) {
		SettingObject setting = getSetting(name);
		if(setting == null)	{
			logger.warn("Trying to save non-existing setting. Ignoring");
		}
		else {
			try
			{
				setting.setValue(value);
			}
			catch (IllegalArgumentException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + name);
				throw new IllegalStateException(e);
			}
			catch (IllegalAccessException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + name);
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Use to reload setting values from the source to settings fields Any
	 * previous changes to fields will be lost
	 */
	public void refetch() {
		for(SettingObject setting:settingsValuesStorage.values()){
			try
			{
				setting.setValue(settingsValuesSource.getValue(setting.getName()));
			}
			catch (IllegalArgumentException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + setting.getName());
				throw new IllegalStateException(e);
			}
			catch (IllegalAccessException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + setting.getName());
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Use to push changes from settings fields to setting source Opposite to
	 * refetch()
	 */
	public void flush() {
		for(SettingObject setting:settingsValuesStorage.values()){
			try
			{
				settingsValuesSource.setValue(setting.getName(), setting.getValue());
			}
			catch (IllegalArgumentException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + setting.getName());
				throw new IllegalStateException(e);
			}
			catch (IllegalAccessException e)
			{
				logger.error("Unexpectedly unaccessiable setting " + setting.getName());
				throw new IllegalStateException(e);
			}
		}
	}
	
	private static String formFieldName(Field field) {
		StringBuilder name = new StringBuilder()
				.append(field.getDeclaringClass().getName()).append(".")
				.append(field.getName());
		return name.toString();
	}
}
