package org.asettings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javassist.Modifier;

import org.apache.log4j.Logger;
import org.asettings.fieldsproviders.FieldsProviderStrategy;
import org.asettings.namingstrategies.NamingException;
import org.asettings.namingstrategies.NamingStrategy;

public class SettingsServiceImpl
{
	private static Logger logger = Logger.getLogger(SettingsServiceImpl.class);
	private static HashMap<String, SettingObject> settingsValuesStorage;

	public SettingsServiceImpl(SettingsValuesSource source, FieldsProviderStrategy fieldsProvider, NamingStrategy namer)
	{
		settingsValuesStorage = new HashMap<String, SettingObject>();
		ArrayList<String> errors = new ArrayList<String>();

		Iterator<Field> fieldsIterator = fieldsProvider.provideFields();
		
		while(fieldsIterator.hasNext())
		{
			Field currentField = fieldsIterator.next();
			String error = handleSetting(source, namer, currentField);
			if(error!=null){
				errors.add(error);
			}
			
		}
		if(errors.size()>0)
		{
			throw new InstantiationError();
		}
	}

	private String handleSetting(SettingsValuesSource source, NamingStrategy namer, Field field) 
	{
		logger.debug("Handling field "+formFieldName(field));
		String error = null;
		try
		{
			Setting setting = field.getAnnotation(Setting.class);
			String settingName = namer.nameSetting(field);
			validateSetting(field, setting);
			saveSetting(settingName ,field, setting, source.getValue(settingName));
		}
		catch (IllegalAccessException e)
		{
			error = new StringBuilder().append("Setting variable should be non-final public static: ")
				.append(formFieldName(field)).toString();
			logger.error(error.toString(), e);
			
		}
		catch (SettingValidationException e)
		{
			error = e.getMessage();
			logger.error(e);
		} catch (NamingException e) {
			error = e.getMessage();
			logger.error(e);
		}
		return error;
	}

	private void saveSetting(String settingName, Field field, Setting setting, Object settingValue)	throws IllegalAccessException, IllegalArgumentException, SettingValidationException {
		SettingObject settingObject = createSettingObject(field,setting,settingValue);
		if(settingsValuesStorage.containsKey(settingObject.getName()))
		{
			StringBuilder warning = new StringBuilder().append("Setting ").append(settingName).append(" already defined in ")
			.append(formFieldName(settingsValuesStorage.get(settingObject.getName()).getField())).append(" - new value ignored.");
		
			logger.warn(warning.toString());
		}
		else
		{
			settingsValuesStorage.put(settingObject.getName(), settingObject);
		}					
		
	}

	private SettingObject createSettingObject(Field field, Setting setting, Object settingValue) throws IllegalArgumentException, IllegalAccessException, SettingValidationException
	{
		SettingObject settingObject = new SettingObjectImpl();
		settingObject.setDefaultValue(field.get(null));
		settingObject.setMandatory(setting.mandatory());
		if(setting.name()==null || setting.name().equals(""))
		{
			settingObject.setName(formFieldName(field));
		}
		else
		{
			settingObject.setName(setting.name());
		}
		if(setting.description()==null || setting.description().equals(""))
		{
			settingObject.setDescription(setting.name());
		}
		else
		{
			settingObject.setDescription(setting.description());
		}
		if(settingValue==null && settingObject.getDefaultValue()==null)
		{
			throw new SettingValidationException("Setting " + settingObject.getName() + " has no default or defined value");
		}
		else if (settingValue==null && settingObject.isMandatory())
		{
			throw new SettingValidationException("Mandatory Setting " + settingObject.getName() + " has no defined value");
		}
		else if(settingValue!=null)
		{
			settingObject.setValue(settingValue);
		}
		else if(settingObject.getDefaultValue()!=null)
		{
			settingObject.setValue(settingObject.getDefaultValue());
		}
		else
		{
			//WTF????
			throw new IllegalStateException("");
		}
		field.set(null, settingObject.getValue());
		return settingObject;
	}

	private void validateSetting(Field field, Setting setting) throws SettingValidationException
	{
		if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isPublic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
		{
			StringBuilder message = new StringBuilder();
			message.append("Field: ").append(formFieldName(field)).append(" for setting: ")
												.append(setting.name()).append(" should be static public and non-final");
			
			throw new SettingValidationException(message.toString());
		}
	}
	
	public SettingObject getSetting(String name)
	{
		return settingsValuesStorage.get(name);
	}
	
	public void setSetting(SettingObject setting, String name)
	{
		settingsValuesStorage.put(null, setting);
	}
	
	public Object getSettingValue(String name)
	{
		SettingObject setting = getSetting(name);
		return setting==null? null:setting.getValue();
	}
	
	public void setSettingValue(Object value, String name)
	{
		SettingObject setting = getSetting(name);
		if(setting == null)
		{
			throw new IllegalStateException("Setting "+name+" was not initialized in the system.");
		}
		else
		{
			setting.setValue(value);
		}
	}
	

	@SuppressWarnings("serial")
	private class SettingValidationException extends Exception
	{

		public SettingValidationException(String message)
		{
			super(message);
		}
		
	}
	
	/**
	 * Use to reload setting values from the source to settings fields
	 * Any previous changes to fields will be lost 
	 */
	public void refetch(){}
	
	/**
	 * Use to push changes from settings fields to setting source 
	 * Opposite to refetch()
	 */
	public void flush(){}
	
	private static String formFieldName(Field field){
		StringBuilder name = new StringBuilder().append(field.getDeclaringClass().getName()).append(".").append(field.getName());
		return name.toString();
	}
}
