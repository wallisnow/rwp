package com.congxing.core.utils;

import java.io.Serializable;

import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class BeanNameUtil implements Serializable {

	private static final Logger log = Logger.getLogger(BeanNameUtil.class);

	public static final int TYPE_VOCLASS = 1;

	public static final int TYPE_FORMCLASS = 2;

	public static final int TYPE_FITCLASS = 2;

	public BeanNameUtil() {
	}

	/**
	 * 根据VOClass猜测其对应的ListVO的全路径名称
	 *
	 * @param voClass
	 * @return
	 */
	public static String getListVOClassName(Class<?> voClass) {
		String voName = voClass.getName();
		String listVoName = voName.substring(0, voName.length() - 2) + "ListVO";
		return listVoName;
	}

	/**
	 * 根据VOClass猜测其对应的处理Strategy类的全路径名称
	 *
	 * @param voClass
	 * @return
	 */
	public static String getStrategyClassName(Class<?> voClass) {
		String voName = voClass.getName();
		voName = voName.replaceFirst("\\.model\\.", ".control.");
		String strategyName = voName.substring(0, voName.length() - 2)
				+ "Strategy";
		return strategyName;
	}

	/**
	 * 根据指定的class全称猜测其i18n文件的位置
	 *
	 * @param className
	 * @return
	 */
	public static String guessBundleNameByClass(Class<?> tgtClass) {
		if (tgtClass != null) {
			String className = tgtClass.getName();
			String[] path = className.split("\\.");

			StringBuffer buffer = new StringBuffer();

			int i = 0;

			String entityName = null;

			for (; i < path.length; i++) {
				String pathItem = path[i];
				if (pathItem.compareTo("control") == 0
						|| pathItem.compareTo("model") == 0
						|| pathItem.compareTo("view") == 0) {
					buffer.append("view.");
					break;
				}
				buffer.append(pathItem).append(".");
				entityName = path[i];
			}

			if (i != path.length) {
				buffer.append(entityName.substring(0, 1).toUpperCase()).append(
						entityName.substring(1)).append("Action");

				if (log.isDebugEnabled()) {
					log.debug("Guess bundle name is " + buffer.toString());
				}

				return buffer.toString();
			}

		}

		if (log.isDebugEnabled()) {
			log.debug("Can't guess bundle name");
		}

		return "";
	}

}
