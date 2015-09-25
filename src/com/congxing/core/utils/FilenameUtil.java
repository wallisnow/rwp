package com.congxing.core.utils;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * <p>
 * Title: FilenameUtil
 * </p>
 *
 * <p>
 * Description: FilenameUtil
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) Revenco
 * </p>
 *
 * <p>
 * Company: Sunrise Tech Ltd.
 * </p>
 *
 * @author Lai Weilong
 * @version 1.0
 * @since 2011-1-11
 *
 */
public class FilenameUtil implements Serializable {

	private static final long serialVersionUID = -7173851453887475123L;

	public static String NAMESTYLE_UNCHANGE = "unchange";

	public static String NAMESTYLE_RENAME = "rename";

	public static String NAMESTYLE_UNION = "union";

	/**
	 * 文件重命名
	 * @param prefix			文件前缀
	 * @param nameStyle			文件重命名策略(unchange|rename|union)
	 * @param oldFilename		原始文件名
	 * @param needFileSuffix	是否需要文件后缀名
	 * @return  
	 * @throws Exception
	 */
	public static String nameByStyle(String prefix, String nameStyle, String oldFilename, boolean needFileSuffix) throws Exception {

		// 默认是rename下的设置
		boolean needCreatedName = true;

		boolean needOldFileName = false;

		if (nameStyle != null) {
			if (nameStyle.toLowerCase().compareTo(NAMESTYLE_UNCHANGE) == 0) {
				needCreatedName = false;
				needOldFileName = true;
			} else if (nameStyle.toLowerCase().compareTo(NAMESTYLE_UNION) == 0) {
				needOldFileName = true;
			}
		}

		if (needOldFileName
				&& (oldFilename == null || oldFilename.trim().length() < 1)) {
			// 当需要旧文件名，但是给定的旧文件名又无效时，也等同于rename
			needCreatedName = true;
			needOldFileName = false;
		}

		StringBuffer filename = new StringBuffer();

		if (needCreatedName) {
			filename.append(nameByTiming(prefix));
		}
		if (needCreatedName && needOldFileName) {
			filename.append("_");
		}
		if (needOldFileName) {
			filename.append(oldFilename);
		}

		if(needFileSuffix){
			int lastIndex = StringUtils.lastIndexOf(oldFilename, ".");
			String suffix = "";
			if(lastIndex > -1){
				suffix = StringUtils.substring(oldFilename, lastIndex);
			}
			filename.append(suffix);
		}
		
		return filename.toString();
	}

	public static String nameByTiming(String prefix) {

		StringBuffer filename = new StringBuffer();

		if (prefix == null || prefix.trim().length() < 1) {
			filename.append("DEF_");
		} else {
			filename.append(prefix).append("_");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		filename.append(sdf.format(new Date(System.currentTimeMillis()))).append((new java.util.Random()).nextInt(99));

		return filename.toString();
	}

	/**
	 * 获取应用相关上传目录位置
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getWebAppRelatedUploadLocation() throws Exception {

		String location = ServletActionContext.getServletContext().getInitParameter("uploadlocation");

		if (location == null || location.equals("")) {
			throw new Exception("文件路径没有设置，请检阅web.xml中uploadlocation的配置!");
		}

		location = ServletActionContext.getServletContext().getRealPath(location);

		location = location + System.getProperty("file.separator");

		File file = new File(location);

		if (file.exists() && file.isDirectory()) {
			return location;
		} else {
			throw new Exception("存放文件路径错误,请检阅web.xml中uploadlocation的配置!");
		}
	}

}
