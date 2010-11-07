package org.asettings.tests.namingstrategies;

import org.asettings.namingstrategies.ShortClassFieldSettingNamer;
import org.asettings.namingstrategies.NamingException;
import org.asettings.namingstrategies.NamingStrategy;
import org.asettings.tests.namingstrategies.fieldsclasses.NamingStrategyTestFieldsClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ShortClassFieldSettingNamerTest {

	@Test
	public void annotatedFiledsTest() throws SecurityException, NamingException, NoSuchFieldException{
		NamingStrategy namingStrategy = new ShortClassFieldSettingNamer();
		
		String fieldName = namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("annotatedIntegerField"));
		assertThat(fieldName, is("NamingStrategyTestFieldsClass.annotatedIntegerField"));

		fieldName = namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("annotatedNamedIntegerField"));
		assertThat(fieldName, is("anotherName"));

	}
	
	@Test(expected = NamingException.class)
	public void normalFiledTest() throws SecurityException, NamingException, NoSuchFieldException{
		NamingStrategy namingStrategy = new ShortClassFieldSettingNamer();
		
		namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("integerField"));
	
	}
	

}
