package com.congxing.core.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.congxing.core.model.Message;


public class I18nMessage implements Serializable {

	private static final long serialVersionUID = 5113458652698617266L;

	public static final String I18N_SRC_PREFIX = "com.congxing";

	public static final String PUBLIC_RESOURCE = "GlobalMessageResources";

	/**
	 * 尝试从更多的途径去国际化key代表的实际消息
	 *
	 * @param bundleName
	 * @param locale
	 * @param key
	 * @param arguments
	 * @return
	 */
	public static String message(String bundleName, Locale locale, String key,
			Object[] arguments) {
		String i18nResult = key;
		// 判断key是否由单词字符组成，如果不是（比如包含中文字符）则无需做国际化处理尝试
		if (key != null && key.matches("^[\\w\\.]+$")) {
			if (bundleName != null) {
				// 先尝试从模块关联的资源文件中获取信息
				i18nResult = getMessage(bundleName, locale, key, arguments);
			}
			if (key.compareTo(i18nResult) == 0) {
				// 再尝试从公共资源文件中获取信息
				i18nResult = getPublicMessage(locale, key, arguments);
			}
		}
		return i18nResult;
	}

	/**
	 * 尝试从更多的途径去国际化key代表的实际消息
	 *
	 * @param bundleName
	 * @param locale
	 * @param key
	 * @return
	 */
	public static String message(String bundleName, Locale locale, String key) {
		return message(bundleName, locale, key, null);
	}

	/**
	 * 解析封装好的待国际化消息对象
	 *
	 * @param bundleName
	 *            资源库名称
	 * @param locale
	 *            地区信息
	 * @param message
	 *            待国际化消息对象
	 * @return
	 */
	public static String message(String bundleName, Locale locale,
			Message message) {
		return message(bundleName, locale, message.getKey(), message
				.getArguments());
	}

	/**
	 * 取指定地区的语言信息，并以指定参数格式化
	 *
	 * @param bundleName
	 *            资源库名称
	 * @param locale
	 *            地区信息
	 * @param key
	 *            关键字
	 * @param arguments
	 *            格式化参数
	 * @return
	 */
	public static String getMessage(String bundleName, Locale locale,
			String key, Object[] arguments) {
		ResourceBundle rb = getResourceBundle(bundleName, locale);

		if (rb == null)
			return key;

		String result = key;

		if (key != null && key.trim().length() != 0) {
			try {
				result = rb.getString(key);

				if (result != null && arguments != null && arguments.length > 0) {
					result = MessageFormat.format(result, arguments);
				}
			} catch (Throwable e) {
				result = key;
			}
		}

		return result;
	}

	/**
	 * 取指定地区的语言信息
	 *
	 * @param bundleName
	 *            资源库名称
	 * @param locale
	 *            地区信息
	 * @param key
	 *            关键字
	 * @return
	 */
	public static String getMessage(String bundleName, Locale locale, String key) {
		return getMessage(bundleName, locale, key, null);
	}

	/**
	 * 取当前运行JVM的默认地区的语言信息
	 *
	 * @param bundleName
	 *            资源库名称
	 * @param key
	 *            关键字
	 * @return
	 */
	public static String getMessage(String bundleName, String key) {
		return getMessage(bundleName, null, key, null);
	}

	/**
	 * 从public资源中取指定地区的信息，并以指定参数格式化
	 *
	 * @param locale
	 *            地区信息
	 * @param key
	 *            关键字
	 * @param arguments
	 *            格式化参数
	 * @return
	 */
	public static String getPublicMessage(Locale locale, String key,
			Object[] arguments) {
		return getMessage(PUBLIC_RESOURCE, locale, key, arguments);
	}

	/**
	 * 从public资源中取指定地区的信息
	 *
	 * @param locale
	 *            地区信息
	 * @param key
	 *            关键字
	 * @return
	 */
	public static String getPublicMessage(Locale locale, String key) {
		return getMessage(PUBLIC_RESOURCE, locale, key, null);
	}

	/**
	 * 从public资源中取当前运行JVM的默认地区的信息
	 *
	 * @param key
	 *            关键字
	 * @return
	 */
	public static String getPublicMessage(String key) {
		return getMessage(PUBLIC_RESOURCE, null, key, null);
	}

	/**
	 * 获取指定地区的资源文件对象
	 *
	 * @param bundleName
	 * @param locale
	 * @return
	 */
	private static ResourceBundle getResourceBundle(String bundleName,
			Locale locale) {
		if (bundleName == null) {
			return null;
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		try {
			return ResourceBundle.getBundle(bundleName, locale);
		} catch (Throwable e) {
			return null;
		}
	}

}
