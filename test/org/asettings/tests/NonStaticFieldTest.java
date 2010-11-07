package org.asettings.tests;

import java.util.HashMap;


import org.asettings.MapSettingsValuesSource;
import org.asettings.SettingsServiceImpl;
import org.asettings.fieldsproviders.FieldsProvider;
import org.asettings.fieldsproviders.ReflectionsFieldsProvider;
import org.asettings.namingstrategies.FullClassFieldSettingNamer;
import org.asettings.namingstrategies.NamingStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

public class NonStaticFieldTest
{
@Test
public void nonStaticFieldTest()
{
	NamingStrategy namer = new FullClassFieldSettingNamer();
	FieldsProvider fieldsProvider = new ReflectionsFieldsProvider("org.asettings.tests.invalidfields");
	SettingsServiceImpl settingsService = new SettingsServiceImpl(new MapSettingsValuesSource(new HashMap<String,Object>()),fieldsProvider,namer);
	assertFalse(settingsService.initialize());
}
}
