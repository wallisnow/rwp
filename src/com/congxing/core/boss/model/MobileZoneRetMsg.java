package com.congxing.core.boss.model;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("serial")
public class MobileZoneRetMsg implements Serializable {
	
	private static final String REC_SEPARATOR_REGEX = "\\x3b";// ';'

	private static final String FIELD_SEPARATOR_REGEX = "\\x7e";// '~'

	private static final String[] FIELD_NAMES = { "mobileno", "brand", "zoneName", "zoneCode" };

	// 手机号码~品牌~归属地名称~归属地区号
	private String mobileno;
	private String brand;
	private String zoneName;
	private String zoneCode;
	
	private String ret; // 返回代码
	private String desc; // 说明
	private String[][] datatrans;

	public MobileZoneRetMsg(String datas) throws Exception {
		try {
			String[] rows = datas.split(REC_SEPARATOR_REGEX);
			datatrans = new String[rows.length][];
			for (int i = 0; i < rows.length; i++) {
				String[] cols = rows[i].split(FIELD_SEPARATOR_REGEX);
				datatrans[i] = cols;
			}
			ret = datatrans[0][0];
			desc = datatrans[0][1];
		} catch (Exception ex) {
		}
		if (RetCode.SUCCESS.equals(getRet())) {
			String[] buff = getDatatrans(1);
			for (int i = 0; i < FIELD_NAMES.length; i++) {
				PropertyUtils.setProperty(this, FIELD_NAMES[i], buff[i]);
			}
		}
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
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

	public String[][] getDatatrans() {
		return datatrans;
	}

	public void setDatatrans(String[][] datatrans) {
		this.datatrans = datatrans;
	}
	
	public String[] getDatatrans(int i) {
		return datatrans[i];
	}
}
