package org.asettings.tests;

import org.asettings.tests.fieldsprovider.TestFieldsProvider;
import org.asettings.tests.namingstrategies.TestNamingStrategies;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestFieldsProvider.class,
	TestNamingStrategies.class,
	NonStaticFieldTest.class,
	InitialValuesTests.class,
	SettingsServiceTests.class})
public class TestAll
{
}
