package com.quick.framework.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract public class AbstractDBTable {
	
	protected String mName;
	protected static SQLiteOpenHelper sDBHelper;
	
	abstract  protected Map<String, String> getColumns();
	
	
	public static void setOpenHelper(SQLiteOpenHelper openHelper){
		sDBHelper = openHelper;
	}
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(getSqlCreateEntries());
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(getSqlDeleteEntries());
        onCreate(db);
    }
    
    private String getSqlCreateEntries(){
		String result =  "CREATE TABLE " + mName +" ( "; 
		
		List<String> columnNames = new ArrayList<String>(getColumns().keySet());
		for(int i = 0 ; i < columnNames.size(); i++ ){
			String columnName = columnNames.get(i);
			result = result+" "+columnName + " "+getColumns().get(columnName);
			if(i < columnNames.size()-1){
				result = result + DBColumnType.COMMA_SEP;
			}
		}

		result = result + " )";
		
		return result;
		
	}
	
	private String getSqlDeleteEntries(){
		return "DROP TABLE IF EXISTS " + mName;
	}
    
	protected void closeDb(SQLiteDatabase db) {
		try {
			if (db != null) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void closeCursor(Cursor cursor) {
		try {
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
