package org.asettings.namingstrategies;

import java.lang.reflect.Field;

import org.asettings.Setting;

public class FullClassFieldSettingNamer implements NamingStrategy{

	@Override
	public String nameSetting(Field field) {
		Setting settingAnnotation = field.getAnnotation(Setting.class);
		if(settingAnnotation==null || settingAnnotation.name()==null || settingAnnotation.name().trim().equals("")){
			StringBuilder name = new StringBuilder().append(field.getDeclaringClass().getName()).append(".").append(field.getName());
			return name.toString();
		}
		return settingAnnotation.name();
	}

}
