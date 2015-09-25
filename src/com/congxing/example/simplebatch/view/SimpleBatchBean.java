/**
 *
 */
package com.congxing.example.simplebatch.view;

import com.congxing.core.batch.BaseBatchTaskBean;
import com.congxing.core.model.CommonResultObject;

/**
 * <p>Title: SimpleBatchBean</p>
 *
 * <p>Description: SimpleBatchBean</p>
 *
 * <p>Copyright: Copyright (c) Revenco</p>
 *
 * <p>Company: Sunrise Tech Ltd.</p>
 *
 * @author Lai Weilong
 *
 * @version 1.0
 *
 * @since 2011-10-21
 */
public class SimpleBatchBean extends BaseBatchTaskBean {

	private static final long serialVersionUID = -8796870903766533010L;

	public SimpleBatchBean() {
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
		SimpleBatchParam listVO = (SimpleBatchParam)this.queryParam;

		if(listVO.get_se_condition1() != null && listVO.get_se_condition1().compareTo("abc") == 0){
			return new CommonResultObject(CommonResultObject.SUCCESS, "good", line);
		}

		return new CommonResultObject(CommonResultObject.SUCCESS, "bad", line);
	}

}
