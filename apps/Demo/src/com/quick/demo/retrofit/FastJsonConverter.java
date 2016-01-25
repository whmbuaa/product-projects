package com.quick.demo.retrofit;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by wanghaiming on 2016/1/14.
 */
public class FastJsonConverter implements Converter {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public FastJsonConverter(){}
    @Override public Object fromBody(TypedInput body, Type type) throws ConversionException {

        String charset = null;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), DEFAULT_CHARSET);
        }

        InputStream inputStream = null;
        try {

            inputStream = body.in();
            return JSON.parseObject(convertStream2Json(inputStream,charset),type);
        } catch (IOException e) {
            throw new ConversionException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(JSON.toJSONString(object).getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
    private static String convertStream2Json(InputStream inputStream,String charset) throws IOException
    {
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中

        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
        {
            out.write(buffer, 0, len);
        }
        // 将内存流转换为字符串
        return new String(out.toByteArray(),charset);
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override public String fileName() {
            return null;
        }

        @Override public String mimeType() {
            return mimeType;
        }

        @Override public long length() {
            return jsonBytes.length;
        }

        @Override public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }


}
