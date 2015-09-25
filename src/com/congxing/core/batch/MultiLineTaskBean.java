/**
 *
 */
package com.congxing.core.batch;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * Title: 多行批处理基类
 * </p>
 *
 * <p>
 * Description: 多行记录归并为一条待处理数据后再做批处理的基类
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
 * @author laiweilong
 *
 * @version 1.0
 *
 * @since 2010-5-10
 */
public abstract class MultiLineTaskBean extends BaseIteratorTaskBean {

	private static final long serialVersionUID = 8658113598089575765L;

	protected Map records;

	public MultiLineTaskBean() {
		super();
		records = new HashMap();
	}

	public void setCountRecord() {
		RandomAccessFile rin = null;
		try {
			rin = new RandomAccessFile(filename, "r");
			long length = rin.length();
			long filePointer = rin.getFilePointer();
			String line;

			int count = 0;

			while (filePointer < length) {
				line = rin.readLine();
				filePointer = rin.getFilePointer();
				if (null != line) {
					// 接受空行，因为空行有可能会做为行分隔符
					line2record(line, ++count);
				}
			}

			this.countrecord = records.size();
		} catch (Exception ex) {
			records = new HashMap();
			this.countrecord = 0;
		} finally {
			if(rin != null){
				try {
					rin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected Iterator getRecordIterator() {
		return records.values().iterator();
	}

	protected String getResultFileName() {
		String resultName = filename.replaceFirst("\\.txt$", "_result.txt");

	    if(resultName == null || resultName.compareTo(filename) == 0){
	    	resultName = filename + "_result.txt";
	    }
		return resultName;
	}

	/**
	 * 分析行记录并将之转换成待处理的数据存入records
	 *
	 * @param line
	 * @param count
	 * @throws Exception
	 */
	protected abstract void line2record(String line, int count) throws Exception;

}
