/**
 *
 */
package com.congxing.example.simplebatch.view;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.model.CommonResultObject;

/**
 * <p>Title: NoneAdditionBatchBean</p>
 *
 * <p>Description: NoneAdditionBatchBean</p>
 *
 * <p>Copyright: Copyright (c) Revenco</p>
 *
 * <p>Company: Sunrise Tech Ltd.</p>
 *
 * @author Lai Weilong
 *
 * @version 1.0
 *
 * @since 2011-10-20
 */
public class NoneAdditionBatchBean extends BaseBatchTaskBean {

	private static final long serialVersionUID = -2652567319462421083L;

	/**
	 *
	 */
	public NoneAdditionBatchBean() {
	}

	/* (non-Javadoc)
	 * @see com.congxing.core.batch.BaseBatchTaskBean#getTitle()
	 */
	@Override
	protected String getTitle() {
		return "line | Title";
	}

	/* (non-Javadoc)
	 * @see com.congxing.core.batch.BaseBatchTaskBean#makeStr(com.congxing.core.model.CommonResultObject, java.lang.Object, int)
	 */
	@Override
	protected String makeStr(CommonResultObject resultVO, Object line, int i) {
		return line.toString() + "|" + resultVO.getResultString();
	}

	/* (non-Javadoc)
	 * @see com.congxing.core.batch.BaseBatchTaskBean#updateOneRecord(java.lang.Object)
	 */
	@Override
	protected CommonResultObject updateOneRecord(Object line) {
		if(queryParam != null){
			return new CommonResultObject(CommonResultObject.SUCCESS, "good", line);
		} else {
			return new CommonResultObject(CommonResultObject.SUCCESS, "bad", line);
		}
	}

}
