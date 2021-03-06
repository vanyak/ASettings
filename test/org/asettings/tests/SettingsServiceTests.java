package org.asettings.tests;

import java.util.HashMap;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.asettings.MapSettingsValuesSource;
import org.asettings.SettingsServiceImpl;
import org.asettings.fieldsproviders.FieldsProvider;
import org.asettings.fieldsproviders.ReflectionsFieldsProvider;
import org.asettings.namingstrategies.FullClassFieldSettingNamer;
import org.asettings.namingstrategies.NamingStrategy;
import org.asettings.tests.validfields.SimpleFieldAnnotatedClass;
import org.junit.Test;


public class SettingsServiceTests
{

	@Test
	public void idealScenario()
	{
		HashMap<String, Object> settingsValues = new HashMap<String, Object>();
		settingsValues.put(SimpleFieldAnnotatedClass.BOOLEAN_SETTING_NAME, !SimpleFieldAnnotatedClass.BOOLEAN_SETTING_VALUE);
		settingsValues.put(SimpleFieldAnnotatedClass.PRIMITIVE_BOOLEAN_SETTING_NAME, !SimpleFieldAnnotatedClass.PRIMITIVE_BOOLEAN_SETTING_VALUE);

		NamingStrategy namer = new FullClassFieldSettingNamer();
		FieldsProvider fieldsProvider = new ReflectionsFieldsProvider("org.asettings.tests.validfields");
		SettingsServiceImpl settingsService = new SettingsServiceImpl(new MapSettingsValuesSource(settingsValues),fieldsProvider,namer);
		assertTrue(settingsService.initialize());
		
		assertThat(SimpleFieldAnnotatedClass.booleanSystemSetting, is(!SimpleFieldAnnotatedClass.BOOLEAN_SETTING_VALUE));
		assertThat(SimpleFieldAnnotatedClass.primitiveBooleanSystemSetting, is(!SimpleFieldAnnotatedClass.PRIMITIVE_BOOLEAN_SETTING_VALUE));
	}
	
}
