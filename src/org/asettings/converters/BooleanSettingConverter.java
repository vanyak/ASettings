package org.asettings.converters;

public class BooleanSettingConverter extends SettingConverter
{

	@Override
	protected boolean isConvertable(Object source, Class<?> clazz)
	{
		if((source instanceof Boolean) && (clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean"))){
			return true;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean"))){
			try{
				Boolean.parseBoolean((String)source);
				return true;
			}
			catch(Exception e){
				return false;
			}
		}
		return false;
	}

	@Override
	protected Object performConversion(Object source, Class<?> clazz)
	{
		if(source instanceof Boolean && (clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean"))){
			return source;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean"))){
				return Boolean.parseBoolean((String)source);
		}
		else {
			return null;
		}
	}

}
