package com.congxing.core.utils;

import java.security.MessageDigest;

public class MD5Encrypt {
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String getMD5Password(String orginPassword) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(orginPassword.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        byte[] byteArray = messageDigest.digest();
        
        return byteArrayToHexString(byteArray);
    }
	
	public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
	
	/** 将一个字节转化成十六进制形式的字符串     */
	private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
	
	public static void main(String[]args){
		System.out.println(MD5Encrypt.getMD5Password("1"));
	}

}
