package org.asettings;

import java.lang.reflect.Field;

public interface SettingObject {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract Object getValue() throws IllegalArgumentException, IllegalAccessException;

	public abstract void setValue(Object value) throws IllegalArgumentException, IllegalAccessException;

	public abstract Object getDefaultValue();

	public abstract void setDefaultValue(Object defaultValue);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract Boolean isMandatory();

	public abstract void setMandatory(Boolean mandatory);

	public abstract Field getField();

	public abstract void setField(Field field);

}