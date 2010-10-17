package org.asettings.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	NonStaticFieldTest.class,
	InitialValuesTests.class,
	HashMapSettingsInitializationTests.class})
public class TestAll
{
}
