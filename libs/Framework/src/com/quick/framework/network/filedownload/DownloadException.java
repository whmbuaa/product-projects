package com.quick.framework.network.filedownload;

public class DownloadException extends Exception {
	private int mCode;
	public DownloadException(int code, String description){
		super(description);
		mCode = code;
		
	}
	public int getCode(){
		return mCode;
	}
}
