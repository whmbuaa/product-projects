package com.quick.framework.util.file;

import java.io.File;
import java.util.UUID;

import android.text.TextUtils;

/**
 * file utilities
 *
 * @author Kevin
 */
public class FileUtil {

    /**
     * get file name from url
     *
     * @param url
     * @return
     */
    public static String getFileNameByUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        int index = url.lastIndexOf('?');
        int index2 = url.lastIndexOf("/");
        if (index > 0 && index2 >= index) {
            return UUID.randomUUID().toString();
        }
        return url.substring(index2 + 1, index < 0 ? url.length() : index);
    }

    /**
     * get file extend name
     *
     * @param fileName
     * @return
     */
    public static String getFileExtendName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf('.');
        if (index < 0) {
            return "unknown";
        } else {
            return fileName.substring(index + 1);
        }
    }

    public static boolean isFileExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        return new File(filePath).exists();
    }
}
