package com.quick.demo.network.http;

import java.io.Serializable;

import com.android.volley.NetworkResponse;
import com.android.volley.ServerError;

public class DDServerError extends ServerError implements Serializable {

	private int mCode;
	private String mDescription;
	
	public DDServerError(int code, String description,NetworkResponse networkResponse){
		super(networkResponse);
		mCode = code;
		mDescription = description;
	}

	public DDServerError(){
		
	}
	public int getCode() {
		return mCode;
	}

	public void setCode(int mCode) {
		this.mCode = mCode;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	
}
