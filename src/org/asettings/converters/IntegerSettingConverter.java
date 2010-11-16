package org.asettings.converters;

public class IntegerSettingConverter extends SettingConverter
{

	@Override
	protected boolean isConvertable(Object source, Class<?> clazz)
	{
		if((source instanceof Integer) && (clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int"))){
			return true;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int"))){
			try{
				Integer.parseInt((String)source);
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
		if(source instanceof Integer && (clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int"))){
			return source;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int"))){
				return Integer.parseInt((String)source);
		}
		else {
			return null;
		}
	}

}
