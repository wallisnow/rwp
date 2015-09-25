/**
 * auto-generated code
 * 2011-10-17 00:51:05
 */
package com.congxing.system.sysdp.view;

import com.congxing.core.web.struts2.BaseListVO;

/**
 * <p>
 * Title: SysDpListVO <br/>
 * Description: Query Params Object for SysDpVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-10-17
 */
@SuppressWarnings("serial")
public class SysDpListVO extends BaseListVO {
	
	private String _seq_dpId;
	
	private String _sk_dpName;
	
	private String _seq_dpCode;
	
	private String _sk_parentDpId;
    
    public String getVoClassName() {
        return "com.congxing.system.sysdp.model.SysDpVO";
    }

	public String get_seq_dpId() {
		return _seq_dpId;
	}



	public void set_seq_dpId(String seqDpId) {
		_seq_dpId = seqDpId;
	}



	public String get_sk_dpName() {
		return _sk_dpName;
	}



	public void set_sk_dpName(String skDpName) {
		_sk_dpName = skDpName;
	}



	public String get_seq_dpCode() {
		return _seq_dpCode;
	}



	public void set_seq_dpCode(String seqDpCode) {
		_seq_dpCode = seqDpCode;
	}



	public String get_sk_parentDpId() {
		return _sk_parentDpId;
	}



	public void set_sk_parentDpId(String skParentDpId) {
		_sk_parentDpId = skParentDpId;
	}
    
}

