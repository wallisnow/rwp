/**
 *
 */
package com.congxing.core.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description: BaseDataTaskBean
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
 * @since 2009-10-28
 */
public abstract class DataBatchTaskBean extends BaseIteratorTaskBean {

	private static final long serialVersionUID = -7748762918436431572L;

	protected List<?> records;

	public DataBatchTaskBean() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public void setCountRecord() {
		try {
			/* 优先考虑外部传入的待处理记录集合，
			 *
			 * 若其为空，才调用程序自身方法来生成待处理记录的集合
			 *
			 */
			if (records == null || records.size() == 0) {
				records = buildRecords();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(records == null){
			records = new ArrayList();
		}
		this.countrecord = records.size();
	}

	protected Iterator<?> getRecordIterator() {
		return records.iterator();
	}

	protected String getResultFileName(){
		return this.filename;
	}

	/**
	 * 新增子类拓展点，用于生成待处理对象的集合
	 *
	 * @throws Exception
	 */
	protected abstract List<?> buildRecords() throws Exception;

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

}
