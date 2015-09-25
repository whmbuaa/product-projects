package com.quick.framework.network.fileupload;

import java.io.File;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quick.framework.util.log.QLog;

public class FileUploaderUtil {

	
	private static final int TIME_OUT = 20000 ; // 20 seconds
	
	
	public static String uplopadFile(String uploadUrl,String token, String srcFilePath) throws FileUploadException {

			// check arguments
			if (srcFilePath == null || srcFilePath.equals("")) {
				QLog.e("srcFilePath is null or empty");
				throw new FileUploadException("source file not exist: file path is null");
			}
		
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,TIME_OUT);
			
			HttpPost httppost = new HttpPost(uploadUrl);
			String returnUrl = null;
			try {

				SimpleMultipartEntity sme = new SimpleMultipartEntity();
				
				sme.addPart("token", token);
				
				File src = new File(srcFilePath);
				if(!src.exists()){
					QLog.e("file doesn't exist");
					throw new FileUploadException("source file not exist");
				}
				else {
					sme.addPart("fileName",src);
				}
			
				
				httppost.setEntity(sme);
				QLog.i("TimeStamp: "+System.currentTimeMillis()/1000+" before file upload");
				HttpResponse response = httpclient.execute(httppost);
				QLog.i("TimeStamp: "+System.currentTimeMillis()/1000+" after file upload");
				
				StatusLine statusLine = response.getStatusLine();
				int code = statusLine.getStatusCode();
				if (code != HttpStatus.SC_OK) {
					throw new FileUploadException("http error,http code: "+code);
				} else {
					String str = EntityUtils.toString(response.getEntity());
					JSONObject obj = JSON.parseObject(str);
					int statusCode = obj.getJSONObject("status").getInteger("code");
					if(statusCode == 0 ){
						returnUrl = obj.getJSONObject("data").getString("uploadUrl");
					}
					else{
						throw new FileUploadException("服务器返回错 ,status code:"+statusCode);
					}
				}

			}
			catch(HttpHostConnectException e){
				QLog.e("no network connection");
				throw new FileUploadException("no network connection.");
			}
			catch(ConnectTimeoutException e){
				QLog.i("connection to server time out");
				throw new FileUploadException("connection to server time out");
			}
			catch(SocketTimeoutException e){
				QLog.e("read data from server time out");
				throw new FileUploadException("read data from server time out.");
			}
			catch (Exception e) {
				QLog.e(e.toString());
				throw new FileUploadException(e.toString());
			} 

			return returnUrl;
		}
	
		
	}

