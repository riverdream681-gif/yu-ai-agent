//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yupi.yuaiagent.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(String input) {
        if (input == null) {
            return null;
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] inputByteArray = input.getBytes();
                messageDigest.update(inputByteArray);
                byte[] resultByteArray = messageDigest.digest();
                return byteArrayToHex(resultByteArray);
            } catch (NoSuchAlgorithmException var4) {
                return null;
            }
        }
    }

    public static String md5(File file) {
        try {
            if (!file.isFile()) {
                System.err.println("文件" + file.getAbsolutePath() + "不存在或者不是文件");
                return null;
            }

            FileInputStream in = new FileInputStream(file);
            String result = md5((InputStream)in);
            in.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String md5(InputStream in) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read = 0;

            while((read = in.read(buffer)) != -1) {
                messagedigest.update(buffer, 0, read);
            }

            in.close();
            String result = byteArrayToHex(messagedigest.digest());
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;

        for(byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 15];
            resultCharArray[index++] = hexDigits[b & 15];
        }

        return new String(resultCharArray);
    }
}
