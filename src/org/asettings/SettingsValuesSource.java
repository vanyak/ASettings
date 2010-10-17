package org.asettings;

public interface SettingsValuesSource
{
	/**
	 * Gets setting value from the source
	 * @param setting name
	 * @return setting value
	 */
	public Object getValue(String name);
	
	/**
	 * Sets setting value in the source
	 * @param setting name
	 * @param setting value
	 */
	public void setValue(String name, Object value);
	
	/**
	 * Removes particular setting from source. It means that next 
	 * time (if source supports saving and will be saved) setting won't be read and 
	 * setting field will remain its default value defined in code
	 * @param setting name
	 */
	public void remove(String name);
	
	/**
	 * Checks whether setting is provided by source
	 * @param name
	 * @return
	 */
	public boolean contains(String name);
	

}
