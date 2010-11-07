package org.asettings.tests.namingstrategies;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	FullClassFieldSettingNamerTest.class,
	ShortClassFieldSettingNamerTest.class})
public class TestNamingStrategies {}
