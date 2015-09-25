/**
 * auto-generated code
 * 2013-06-05 03:16:38
 */
package com.congxing.rulemgt.ruleset.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: RulesetListVO <br/>
 * Description: Query Params Object for RulesetVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-05
 */
public class RulesetListVO extends BaseListVO {
	
   /**
    * Determines if a de-serialized file is compatible with this class.
    *
    * Maintainers must change this value if and only if the new version
    * of this class is not compatible with old versions. See Sun docs
    * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
    * /serialization/spec/version.doc.html> details. </a>
    *
    * Not necessary to include in first version of the class, but
    * included here as a reminder of its importance.
    */	 
    private static final long serialVersionUID = 1L;
    
    private String _sk_rulesetName;
    
    private String _seq_status;
    
    private String _seq_rulesetVersion;
    
    private String _sk_rulesetVersion;
    
    private String _leq_rulesetId;
    
    private String _dge_createTime;
    
    private String _dle_createTime;
    
    private String _sk_creator;
    
    private String _seq_rulesetType;
    
    public String get_sk_rulesetName() {
        return _sk_rulesetName;
    }

    public void set_sk_rulesetName(String _sk_rulesetName) {
        this._sk_rulesetName = _sk_rulesetName;
    }
    
    public String get_seq_status() {
		return _seq_status;
	}

	public void set_seq_status(String seqStatus) {
		_seq_status = seqStatus;
	}

	public String get_seq_rulesetVersion() {
		return _seq_rulesetVersion;
	}

	public void set_seq_rulesetVersion(String seqRulesetVersion) {
		_seq_rulesetVersion = seqRulesetVersion;
	}

	public String get_sk_rulesetVersion() {
		return _sk_rulesetVersion;
	}

	public void set_sk_rulesetVersion(String skRulesetVersion) {
		_sk_rulesetVersion = skRulesetVersion;
	}

	public String get_leq_rulesetId() {
		return _leq_rulesetId;
	}

	public void set_leq_rulesetId(String leqRulesetId) {
		_leq_rulesetId = leqRulesetId;
	}


	public String get_dge_createTime() {
		return _dge_createTime;
	}

	public void set_dge_createTime(String dgeCreateTime) {
		_dge_createTime = dgeCreateTime;
	}

	public String get_dle_createTime() {
		return _dle_createTime;
	}

	public void set_dle_createTime(String dleCreateTime) {
		_dle_createTime = dleCreateTime;
	}

	public String get_sk_creator() {
		return _sk_creator;
	}

	public void set_sk_creator(String skCreator) {
		_sk_creator = skCreator;
	}
	
	public String get_seq_rulesetType() {
		return _seq_rulesetType;
	}

	public void set_seq_rulesetType(String seqRulesetType) {
		_seq_rulesetType = seqRulesetType;
	}

	public String getVoClassName() {
        return RulesetVO.class.getName();
    }

    
}

