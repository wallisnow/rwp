/**
 *
 */
package com.congxing.example.simplebatch.view;

import com.congxing.core.web.struts2.BatchParam;

/**
 * <p>Title: SimpleBatchParam</p>
 *
 * <p>Description: SimpleBatchParam</p>
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
public class SimpleBatchParam implements BatchParam {

	private static final long serialVersionUID = -7626424282191011399L;

	private String _se_condition1;

	private String _dle_condition2;

	private String _ngt_condition3;

	public SimpleBatchParam() {
	}

	public String get_se_condition1() {
		return _se_condition1;
	}

	public void set_se_condition1(String seCondition1) {
		_se_condition1 = seCondition1;
	}

	public String get_dle_condition2() {
		return _dle_condition2;
	}

	public void set_dle_condition2(String dleCondition2) {
		_dle_condition2 = dleCondition2;
	}

	public String get_ngt_condition3() {
		return _ngt_condition3;
	}

	public void set_ngt_condition3(String ngtCondition3) {
		_ngt_condition3 = ngtCondition3;
	}

}
