/**
 * 
 */
package com.quick.framework.db.service;

import com.quick.framework.BaseApplication;
import com.quick.framework.db.DBOpenHelper;


/**
 * @author wanghaiming
 *
 */
public class BaseDbService {

	protected static DBOpenHelper sDbOpenHelper;
	
	static
	{
		sDbOpenHelper = new DBOpenHelper(BaseApplication.getApplication());
	}
}
