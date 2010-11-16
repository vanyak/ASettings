package org.asettings.converters;

public class StringSettingConverter extends SettingConverter
{

	@Override
	protected boolean isConvertable(Object source, Class<?> clazz)
	{
		return true;
	}

	@Override
	protected Object performConversion(Object source, Class<?> clazz)
	{
		if(source instanceof String){
			return source;
		}
		else {
			return source.toString();
		}
	}

}
