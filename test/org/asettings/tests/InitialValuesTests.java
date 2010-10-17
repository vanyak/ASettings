package org.asettings.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.asettings.tests.validfields.SimpleFieldAnnotatedClass;
import org.junit.Test;


public class InitialValuesTests
{
	@Test
	public void simpleFieldsTests()
	{
		assertThat(SimpleFieldAnnotatedClass.stringSystemSetting, is(SimpleFieldAnnotatedClass.STRING_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.integerSystemSetting, is(SimpleFieldAnnotatedClass.INTEGER_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.doubleSystemSetting, is(SimpleFieldAnnotatedClass.DOUBLE_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.booleanSystemSetting, is(SimpleFieldAnnotatedClass.BOOLEAN_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.primitiveIntSystmeSetting, is(SimpleFieldAnnotatedClass.PRIMITIVE_INTEGER_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.primitiveDoubleSystemSetting, is(SimpleFieldAnnotatedClass.PRIMITIVE_DOUBLE_SETTING_VALUE) );
		assertThat(SimpleFieldAnnotatedClass.primitiveBooleanSystemSetting, is(SimpleFieldAnnotatedClass.PRIMITIVE_BOOLEAN_SETTING_VALUE) );
		
	}
}
