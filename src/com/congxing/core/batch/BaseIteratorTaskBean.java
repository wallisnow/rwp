/**
 *
 */
package com.congxing.core.batch;

import java.io.RandomAccessFile;
import java.util.Iterator;

import com.congxing.core.model.CommonResultObject;
import com.congxing.core.utils.CharsetUtils;

/**
 * <p>
 * Title: BaseIteratorTaskBean
 * </p>
 *
 * <p>
 * Description: BaseIteratorTaskBean
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
 * @since 2010-5-11
 */
public abstract class BaseIteratorTaskBean extends BaseBatchTaskBean {

	private static final long serialVersionUID = -8044919318560622487L;

	public BaseIteratorTaskBean() {
		super();
	}

	public void doProcessFile() throws Exception {
		String resultName = getResultFileName();

		RandomAccessFile resultFile = new RandomAccessFile(new java.io.File(
				resultName), "rw");

		try {
			Object item;
			int ok_count = 0, fail_count = 0;
			CommonResultObject result = new CommonResultObject();

			Iterator<?> it = getRecordIterator();

			// 写结果处理文件的标头
			resultFile.writeBytes(CharsetUtils.toByteBasedString(getTitle()));
			resultFile.writeBytes("\r\n");

			int count = 0;
			while (it.hasNext()) {
				item = it.next();
				if (null != item) {
					result = updateOneRecord(item); // 关键处理，处理一条记录
					++count;
					if (result.isSuccess()) {
						++ok_count;
					} else {
						++fail_count;
					}
					// 组装并输出该行记录的处理结果
					String resultStr = makeStr(result, item, count);
					resultFile.writeBytes(CharsetUtils.toByteBasedString(resultStr));
					resultFile.writeBytes("\r\n");
					currentrecord++;
				}
			}

			this.resultFile = resultName;
			this.fail = fail_count;
			this.ok = ok_count;
		} catch (Exception ex) {
			throw ex;
		} finally {
			resultFile.close();
		}
	}

	/**
	 * 把setCountRecord置为虚拟，主要是因为在此基类中
	 *
	 * 获取待处理对象总数的方式无法统一，只能强制由子类实现
	 */
	public abstract void setCountRecord();

	/**
	 * 新增子类拓展点，用于生成结果文件名
	 *
	 * @return
	 */
	protected abstract String getResultFileName();

	/**
	 * 新增子类拓展点，用于获取待处理对象集合的遍历器
	 *
	 * @return
	 */
	protected abstract Iterator<?> getRecordIterator();

}
