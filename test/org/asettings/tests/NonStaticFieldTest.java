package org.asettings.tests;

import java.util.HashMap;

import org.asettings.MapSettingsValuesSource;
import org.asettings.SettingsServiceImpl;
import org.asettings.fieldsproviders.FieldsProviderStrategy;
import org.asettings.fieldsproviders.ReflectionsFieldsProvider;
import org.asettings.namingstrategies.FullClassFieldSettingNamer;
import org.asettings.namingstrategies.NamingStrategy;
import org.junit.Test;


public class NonStaticFieldTest
{
@Test(expected=InstantiationError.class)
public void nonStaticFieldTest()
{
	NamingStrategy namer = new FullClassFieldSettingNamer();
	FieldsProviderStrategy fieldsProvider = new ReflectionsFieldsProvider("org.asettings.tests.invalidfields");
	new SettingsServiceImpl(new MapSettingsValuesSource(new HashMap<String,Object>()),fieldsProvider,namer); 
}
}
