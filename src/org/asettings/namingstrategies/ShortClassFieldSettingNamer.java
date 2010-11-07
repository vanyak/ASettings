package org.asettings.namingstrategies;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import org.asettings.Setting;

public class ShortClassFieldSettingNamer implements NamingStrategy{

	private static final String EXCEPTION_MESSAGE = "Field ''{0}'' in class {1} is not @Setting";

	@Override
	public String nameSetting(Field field) throws NamingException {
		Setting settingAnnotation = field.getAnnotation(Setting.class);
		
		if(settingAnnotation == null){
			throw new NamingException(MessageFormat.format(EXCEPTION_MESSAGE, field.getName(), field.getDeclaringClass().getName()));
		}
		
		if( settingAnnotation.name()==null || settingAnnotation.name().trim().equals("")){
			StringBuilder name = new StringBuilder().append(field.getDeclaringClass().getSimpleName()).append(".").append(field.getName());
			return name.toString();
		}
		return settingAnnotation.name();
	}

}
