/**
 * auto-generated code
 * 2013-06-07 01:13:28
 */
package com.congxing.rulemgt.ruleset.model;

import com.congxing.core.web.struts2.BaseListVO;


/**
 * <p>
 * Title: RuleListVO <br/>
 * Description: Query Params Object for RuleVO <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-07
 */
public class RuleListVO extends BaseListVO {
	
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
    
    private String _leq_rulesetId;
    
    private String _seq_rulesetVersion;

	public String get_seq_rulesetVersion() {
		return _seq_rulesetVersion;
	}

	public void set_seq_rulesetVersion(String seqRulesetVersion) {
		_seq_rulesetVersion = seqRulesetVersion;
	}
    
    public String get_leq_rulesetId() {
		return _leq_rulesetId;
	}

	public void set_leq_rulesetId(String _leq_rulesetId) {
		this._leq_rulesetId = _leq_rulesetId;
	}
    
    public String getVoClassName() {
        return com.congxing.rulemgt.ruleset.model.RuleVO.class.getName();
    }
    
}

