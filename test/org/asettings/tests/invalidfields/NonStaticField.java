package org.asettings.tests.invalidfields;

import java.util.Arrays;
import java.util.List;

import org.asettings.Setting;

public class NonStaticField
{
	@Setting(name="static setting")
	public List<String> wrongSetting = Arrays.asList("asdf","zxcv"); 

}
