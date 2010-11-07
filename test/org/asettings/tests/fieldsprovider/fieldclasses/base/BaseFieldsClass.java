package org.asettings.tests.fieldsprovider.fieldclasses.base;

import org.asettings.Setting;

public class BaseFieldsClass {

	@Setting
	public Integer publicInteger = 1;
	@SuppressWarnings("unused")
	@Setting
	private Integer privateInteger = 1;
	@Setting
	protected Integer protectedInteger = 1;
	@Setting
	Integer packageInteger = 1;
	
	@Setting
	public static Integer publicStaticInteger = 1;
	@SuppressWarnings("unused")
	@Setting
	private static Integer privateStaticInteger = 1;
	@Setting
	protected static Integer protectedStaticInteger = 1;
	@Setting
	static Integer packageStaticInteger = 1;
	
	@Setting
	public static final Integer publicStaticFinalInteger = 1;
	@SuppressWarnings("unused")
	@Setting
	private static final Integer privateStaticFinalInteger = 1;
	@Setting
	protected static final Integer protectedStaticFinalInteger = 1;
	@Setting
	static final Integer packageStaticFinalInteger = 1;
	
	@Setting
	public final Integer publicFinalInteger = 1;
	@SuppressWarnings("unused")
	@Setting
	private final Integer privateFinalInteger = 1;
	@Setting
	protected final Integer protectedFinalInteger = 1;
	@Setting
	final Integer packageFinalInteger = 1;
	
	
}
