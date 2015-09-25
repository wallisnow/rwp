package com.congxing.rulemgt.ruleset.model;

import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class RulesetFunListVO extends BaseListVO {
	
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

}
