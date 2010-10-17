package org.asettings;

import java.util.Map;

public class MapSettingsValuesSource implements SettingsValuesSource
{
	private Map<String, Object> mapSource;
	
	public MapSettingsValuesSource(Map<String, Object> mapSource)
	{
		super();
		this.mapSource = mapSource;
	}

	@Override
	public Object getValue(String name)
	{
		return mapSource.get(name);
	}

	@Override
	public void setValue(String name, Object value)
	{
		mapSource.put(name,value);
	}

	@Override
	public void remove(String name)
	{
		mapSource.remove(name);
	}

	@Override
	public boolean contains(String name)
	{
		return mapSource.containsKey(name);
	}
	

	
}
