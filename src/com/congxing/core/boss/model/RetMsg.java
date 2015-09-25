package com.congxing.core.boss.model;

import java.io.Serializable;

/**
 * <p>
 * Title: 接口回送基类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RetMsg implements Serializable {
	protected String ret; // 返回代码
	protected String desc; // 说明
	protected String[][] datatrans;

	public RetMsg(String datas) throws Exception {
		try {
			String[] rows = datas.split(Constant.REC_SEPARATOR_REGEX);
			datatrans = new String[rows.length][];
			for (int i = 0; i < rows.length; i++) {
				String[] cols = rows[i].split(Constant.FIELD_SEPARATOR_REGEX);
				datatrans[i] = cols;
			}
			ret = datatrans[0][0];
			desc = datatrans[0][1];
		} catch (Exception ex) {
		}
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	protected String[] getDatatrans(int i) {
		return datatrans[i];
	}
}
