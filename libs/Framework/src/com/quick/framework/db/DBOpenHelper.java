package com.quick.framework.db;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sociality.db";
   
    
	public DBOpenHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		AbstractDBTable.setOpenHelper(this);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	
//		MessageTable.getInstance().onCreate(db);
//		CommentMessageTable.getInstance().onCreate(db);
		
		
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
//		MessageTable.getInstance().onUpgrade(db, oldVersion, newVersion);
//		CommentMessageTable.getInstance().onUpgrade(db, oldVersion, newVersion);
		
	}

}
