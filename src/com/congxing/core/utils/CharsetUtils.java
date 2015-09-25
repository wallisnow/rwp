package com.congxing.core.utils;

import java.io.Serializable;

public class CharsetUtils implements Serializable {

	/**
	 * 序列化接口唯一标识
	 */
	private static final long serialVersionUID = 8494717902135597609L;

	// 应用内编码
	public static final String USER_DEFAULT_CHARSET = "UTF-8";

	// 单字节形式的编码，可以用于保存多字节编码的String的byte[]形式
	public static final String SINGLE_BYTE_CHARSET = "ISO-8859-1";

	private CharsetUtils() {
		super();
	}

	/**
	 * 将字符串以指定的oldCharsetName解码成对应的byte[]数组，
	 *
	 * 然后再以newCharsetName编码为新的String对象
	 *
	 * @param changeString
	 * @param oldCharsetName
	 * @param newCharsetName
	 * @return
	 * @throws Exception
	 */
	public static String encode(String changeString, String oldCharsetName, String newCharsetName) throws Exception {
		if (changeString == null)
			return null;
		return new String(changeString.getBytes(oldCharsetName), newCharsetName);
	}

	/**
	 * 将字符串从应用内部编码转成其实际字节数组形式.
	 *
	 * @param changeString
	 * @return
	 * @throws Exception
	 */
	public static String toByteBasedString(String changeString) throws Exception {
		return encode(changeString, USER_DEFAULT_CHARSET, SINGLE_BYTE_CHARSET);
	}

	/**
	 * 将字符串从字节数组形式编码转换成应用内部编码.
	 *
	 * @param changeString
	 * @return
	 * @throws Exception
	 */
	public static String fromByteBasedString(String changeString) throws Exception {
		return encode(changeString, SINGLE_BYTE_CHARSET, USER_DEFAULT_CHARSET);
	}

	/**
	 * 将应用内部编码字符串转换为http参数格式
	 *
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String toHttpParameter(String parameter) throws Exception {
		return toByteBasedString(parameter);
	}

	/**
	 * 将从客户端以http参数形式传过来的字符串还原为应用内部编码格式
	 *
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String fromHttpParameter(String parameter) throws Exception {
		return fromByteBasedString(parameter);
	}

}
