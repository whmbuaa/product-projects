package com.quick.framework.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateTable extends AbstractDBTable {
	
	
	// single instance
	private static TemplateTable sInstance = new TemplateTable();
	private static final String TABLE_NAME = "template_table";
	private TemplateTable(){
		mName = TABLE_NAME;
	}
	public static TemplateTable getInstance(){
		return sInstance;
	}
	
	// define the columns
	//common columns
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_USER_ID = "user_id";
	
	
	//specific columns
	
	
	
	
	private static final Map<String, String> sColumns = new LinkedHashMap<String,String>(){
		{
			put(COLUMN_ID,DBColumnType.INTEGER + DBColumnType.PRIMARY_KEY);
			put(COLUMN_USER_ID , DBColumnType.TEXT);
			//specific columns
			
			
		}
	};
	
	@Override
	protected Map<String, String> getColumns() {
		// TODO Auto-generated method stub
		return sColumns;
	}

	
	
}
