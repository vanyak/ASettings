package org.asettings.namingstrategies;

import java.lang.reflect.Field;

public interface NamingStrategy {
	public String nameSetting(Field field) throws NamingException;
}
