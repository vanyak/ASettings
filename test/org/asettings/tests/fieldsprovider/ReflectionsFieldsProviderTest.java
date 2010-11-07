package org.asettings.tests.fieldsprovider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.asettings.fieldsproviders.FieldsProvider;
import org.asettings.fieldsproviders.ReflectionsFieldsProvider;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ReflectionsFieldsProviderTest {

	@Test
	public void baseFields(){
		FieldsProvider fieldsProvider = new ReflectionsFieldsProvider("org.asettings.tests.fieldsprovider.fieldclasses.base");
		Iterator<Field> fieldsIterator = fieldsProvider.provideFields();
		
		int counter = 0;
		List<String> fieldNames = new ArrayList<String>();
		
		while(fieldsIterator.hasNext()){
			Field currentField = fieldsIterator.next();
			fieldNames.add(currentField.getName());
			counter++;
		}
		
		assertThat(counter, is(16));
		
		checkBaseFieldNames(fieldNames);
	}

	private void checkBaseFieldNames(List<String> fieldNames) {
		assertTrue(fieldNames.contains("publicInteger"));
		assertTrue(fieldNames.contains("privateInteger"));
		assertTrue(fieldNames.contains("protectedInteger"));
		assertTrue(fieldNames.contains("packageInteger"));
		assertTrue(fieldNames.contains("publicStaticInteger"));
		assertTrue(fieldNames.contains("privateStaticInteger"));
		assertTrue(fieldNames.contains("protectedStaticInteger"));
		assertTrue(fieldNames.contains("packageStaticInteger"));
		assertTrue(fieldNames.contains("publicStaticFinalInteger"));
		assertTrue(fieldNames.contains("privateStaticFinalInteger"));
		assertTrue(fieldNames.contains("protectedStaticFinalInteger"));
		assertTrue(fieldNames.contains("packageStaticFinalInteger"));
		assertTrue(fieldNames.contains("publicFinalInteger"));
		assertTrue(fieldNames.contains("privateFinalInteger"));
		assertTrue(fieldNames.contains("protectedFinalInteger"));
		assertTrue(fieldNames.contains("packageFinalInteger"));
	}
	
	@Test
	public void inheritedFields(){
		FieldsProvider fieldsProvider = new ReflectionsFieldsProvider("org.asettings.tests.fieldsprovider.fieldclasses.inherited");
		Iterator<Field> fieldsIterator = fieldsProvider.provideFields();
		
		int counter = 0;
		List<String> fieldNames = new ArrayList<String>();
		
		while(fieldsIterator.hasNext()){
			Field currentField = fieldsIterator.next();
			fieldNames.add(currentField.getName());
			counter++;
		}
		
		assertThat(counter, is(1));
		
		checkInheritedFieldNames(fieldNames);
	}

	private void checkInheritedFieldNames(List<String> fieldNames) {
		assertTrue(fieldNames.contains("newSetting"));
	}

}
