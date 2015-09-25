package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class RuleExchangeVO implements Serializable {
	
	private Long targetId;
	
	private String version;
	
	private Map<String, Object> factor;

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}


	public Map<String, Object> getFactor() {
		return factor;
	}

	public void setFactor(Map<String, Object> factor) {
		this.factor = factor;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
