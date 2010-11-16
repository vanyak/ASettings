package org.asettings.converters;

public abstract class SettingConverter
{
	private SettingConverter successor=null;
	protected SettingConverter(){
		
	}
	protected abstract boolean isConvertable(Object source, Class<?> clazz);
	protected abstract Object performConversion(Object source, Class<?> clazz);

	public final SettingConverter registerConverter(SettingConverter converter){
		converter.successor=this;
		return converter;
	}
	
	public final Object convert(Object source ,Class<?> clazz){
		if(isConvertable(source, clazz)){
			return performConversion(source, clazz);
		}
		else if(successor!=null){
			return successor.convert(source, clazz);
		}
		else {
			return null;
		}
	}
	
	private static SettingConverter chain;
	
	public static SettingConverter instance(){
		if(chain==null){
			initialize();
		}
		return chain;
	}
	
	private static void initialize(){
		chain = new StringSettingConverter();
		chain = chain.registerConverter(new BooleanSettingConverter());
		chain = chain.registerConverter(new IntegerSettingConverter());
		chain = chain.registerConverter(new DoubleSettingConverter());
	}
	
}
