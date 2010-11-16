package org.asettings.converters;

public class DoubleSettingConverter extends SettingConverter
{

	@Override
	protected boolean isConvertable(Object source, Class<?> clazz)
	{
		if((source instanceof Double) && (clazz.getName().equals("java.lang.Double") || clazz.getName().equals("double"))){
			return true;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Double") || clazz.getName().equals("double"))){
			try{
				Double.parseDouble((String)source);
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
		if(source instanceof Double && (clazz.getName().equals("java.lang.Double") || clazz.getName().equals("double"))){
			return source;
		}
		if(source instanceof String && (clazz.getName().equals("java.lang.Double") || clazz.getName().equals("double"))){
				return Double.parseDouble((String)source);
		}
		else {
			return null;
		}
	}

}
