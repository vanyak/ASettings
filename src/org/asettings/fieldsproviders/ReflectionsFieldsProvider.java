package org.asettings.fieldsproviders;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.asettings.Setting;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ReflectionsFieldsProvider implements FieldsProvider{

	private String scanPath;
	
	public ReflectionsFieldsProvider(String scanPath) {
		super();
		this.scanPath = scanPath;
	}

	public String getScanPath() {
		return scanPath;
	}

	public void setScanPath(String scanPath) {
		this.scanPath = scanPath;
	}

	@Override
	public Iterator<Field> provideFields() {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
	    	.filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(scanPath)))
	    	.setUrls(ClasspathHelper.getUrlsForPackagePrefix(scanPath))
	    	.setScanners(new SubTypesScanner(),
	                  new FieldAnnotationsScanner(),
	                  new ResourcesScanner()));
			
		return reflections.getFieldsAnnotatedWith(Setting.class).iterator();
	}

}
