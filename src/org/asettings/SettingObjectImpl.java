package org.asettings;

import java.lang.reflect.Field;

public class SettingObjectImpl implements SettingObject
{

	private String name;
	private Object value;
	private Object defaultValue;
	private String description;
	private Boolean mandatory;
	private Field field;
	
	
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#getValue()
	 */
	@Override
	public Object getValue()
	{
		return value;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value)
	{
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#getDefaultValue()
	 */
	@Override
	public Object getDefaultValue()
	{
		return defaultValue;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setDefaultValue(java.lang.Object)
	 */
	@Override
	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return description;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#isMandatory()
	 */
	@Override
	public Boolean isMandatory()
	{
		return mandatory;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setMandatory(java.lang.Boolean)
	 */
	@Override
	public void setMandatory(Boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#getField()
	 */
	@Override
	public Field getField()
	{
		return field;
	}
	/* (non-Javadoc)
	 * @see org.asettings.SettingsObjects#setField(java.lang.reflect.Field)
	 */
	@Override
	public void setField(Field field)
	{
		this.field = field;
	}
	
	
	
}
