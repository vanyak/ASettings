package org.asettings.tests.namingstrategies;

import org.asettings.namingstrategies.FullClassFieldSettingNamer;
import org.asettings.namingstrategies.NamingException;
import org.asettings.namingstrategies.NamingStrategy;
import org.asettings.tests.namingstrategies.fieldsclasses.NamingStrategyTestFieldsClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class FullClassFieldSettingNamerTest {

	@Test
	public void annotatedFiledsTest() throws SecurityException, NamingException, NoSuchFieldException{
		NamingStrategy namingStrategy = new FullClassFieldSettingNamer();
		
		String fieldName = namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("annotatedIntegerField"));
		assertThat(fieldName, is("org.asettings.tests.namingstrategies.fieldsclasses.NamingStrategyTestFieldsClass.annotatedIntegerField"));

		fieldName = namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("annotatedNamedIntegerField"));
		assertThat(fieldName, is("anotherName"));

	}
	
	@Test(expected = NamingException.class)
	public void normalFiledTest() throws SecurityException, NamingException, NoSuchFieldException{
		NamingStrategy namingStrategy = new FullClassFieldSettingNamer();
		
		namingStrategy.nameSetting(NamingStrategyTestFieldsClass.class.getDeclaredField("integerField"));
	
	}
	

}
