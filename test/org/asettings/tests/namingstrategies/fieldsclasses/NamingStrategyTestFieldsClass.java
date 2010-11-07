package org.asettings.tests.namingstrategies.fieldsclasses;

import org.asettings.Setting;

public class NamingStrategyTestFieldsClass {

	public Integer integerField = 1;

	@Setting
	public Integer annotatedIntegerField = 1;
	
	@Setting(name="anotherName")
	public Integer annotatedNamedIntegerField = 1;
	
}
