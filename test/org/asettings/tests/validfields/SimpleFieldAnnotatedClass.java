package org.asettings.tests.validfields;

import org.asettings.Setting;

public class SimpleFieldAnnotatedClass
{

	public static final String STRING_SETTING_NAME = "annotation.settings.test.stringSetting";
	public static final String INTEGER_SETTING_NAME = "annotation.settings.test.integerSetting";
	public static final String BOOLEAN_SETTING_NAME = "annotation.settings.test.booleanSetting";
	public static final String DOUBLE_SETTING_NAME = "annotation.settings.test.doubleSetting";

	public static final String PRIMITIVE_INTEGER_SETTING_NAME = "annotation.settings.test.primitiveIntegerSetting";
	public static final String PRIMITIVE_BOOLEAN_SETTING_NAME = "annotation.settings.test.primitiveBooleanSetting";
	public static final String PRIMITIVE_DOUBLE_SETTING_NAME = "annotation.settings.test.primitiveDoubleSetting";
	
	public static final String STRING_SETTING_VALUE = "stringSettingDefaultValue";
	public static final Integer INTEGER_SETTING_VALUE = 0;
	public static final Double DOUBLE_SETTING_VALUE = 1111.11;
	public static final Boolean BOOLEAN_SETTING_VALUE = true;
	public static final int PRIMITIVE_INTEGER_SETTING_VALUE = 0;
	public static final double PRIMITIVE_DOUBLE_SETTING_VALUE = 0.11;
	public static final boolean PRIMITIVE_BOOLEAN_SETTING_VALUE = false;
	
	@Setting(name=STRING_SETTING_NAME)
	public static String stringSystemSetting = STRING_SETTING_VALUE;
	@Setting(name=INTEGER_SETTING_NAME)
	public static Integer integerSystemSetting = INTEGER_SETTING_VALUE;
	
	@Setting(name=BOOLEAN_SETTING_NAME)
	public static Boolean booleanSystemSetting = BOOLEAN_SETTING_VALUE;
	@Setting(name=DOUBLE_SETTING_NAME )
	public static Double doubleSystemSetting = DOUBLE_SETTING_VALUE;
	
	@Setting(name=PRIMITIVE_INTEGER_SETTING_NAME )
	public static int primitiveIntSystmeSetting = PRIMITIVE_INTEGER_SETTING_VALUE;
	@Setting(name=PRIMITIVE_DOUBLE_SETTING_NAME)
	public static double primitiveDoubleSystemSetting = PRIMITIVE_DOUBLE_SETTING_VALUE;
	@Setting(name=PRIMITIVE_BOOLEAN_SETTING_NAME)
	public static boolean primitiveBooleanSystemSetting = PRIMITIVE_BOOLEAN_SETTING_VALUE;
	
}
