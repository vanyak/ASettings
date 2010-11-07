package org.asettings;

import java.lang.reflect.Field;

public class SettingObjectImpl implements SettingObject {

	private String name;
	private Object value;
	private Object defaultValue;
	private String description;
	private Boolean mandatory;
	private Field field;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Boolean isMandatory() {
		return mandatory;
	}

	@Override
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public void setField(Field field) {
		this.field = field;
	}

}
