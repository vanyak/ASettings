package org.asettings.fieldsproviders;

import java.lang.reflect.Field;
import java.util.Iterator;


public interface FieldsProviderStrategy {
	public Iterator<Field> provideFields();
}
