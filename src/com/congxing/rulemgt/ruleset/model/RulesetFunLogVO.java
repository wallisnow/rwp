/**
 * auto-generated code
 * 2013-06-06 08:26:52
 */
package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RulesetFunLogVO <br/>
 * Description: Mapping Object for table RULESET_FUN_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-06
 */
public class RulesetFunLogVO implements Serializable {

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
	
    /** identifier field */
    private java.lang.Long logId;
    
    /** nullable persistent field */
    private java.lang.String oprCode;
    
    /** nullable persistent field */
    private java.util.Date oprTime;
    
    /** nullable persistent field */
    private java.lang.String oprType;
    
    /** nullable persistent field */
    private java.lang.Long funId;
    
    /** nullable persistent field */
    private java.lang.Long rulesetId;
    
    /** nullable persistent field */
    private java.lang.String funName;
    
    /** nullable persistent field */
    private java.lang.String funDesc;
    
    private String rulesetVersion;

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
	}
    
    
    /** full constructor */
    public RulesetFunLogVO(java.lang.Long logId,java.lang.String oprCode,java.util.Date oprTime,java.lang.String oprType,java.lang.Long funId,java.lang.Long rulesetId,java.lang.String funName,java.lang.String funDesc) {
        this.logId = logId;
        this.oprCode = oprCode;
        this.oprTime = oprTime;
        this.oprType = oprType;
        this.funId = funId;
        this.rulesetId = rulesetId;
        this.funName = funName;
        this.funDesc = funDesc;
    }

    /** minimal constructor */
    public RulesetFunLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public RulesetFunLogVO() {
    }
    
    public java.lang.Long getLogId() {
        return this.logId;
    }

    public void setLogId(java.lang.Long logId) {
        this.logId = logId;
    }
    
    public java.lang.String getOprCode() {
        return this.oprCode;
    }

    public void setOprCode(java.lang.String oprCode) {
        this.oprCode = oprCode;
    }
    
    public java.util.Date getOprTime() {
        return this.oprTime;
    }

    public void setOprTime(java.util.Date oprTime) {
        this.oprTime = oprTime;
    }
    
    public java.lang.String getOprType() {
        return this.oprType;
    }

    public void setOprType(java.lang.String oprType) {
        this.oprType = oprType;
    }
    
    public java.lang.Long getFunId() {
        return this.funId;
    }

    public void setFunId(java.lang.Long funId) {
        this.funId = funId;
    }
    
    public java.lang.Long getRulesetId() {
        return this.rulesetId;
    }

    public void setRulesetId(java.lang.Long rulesetId) {
        this.rulesetId = rulesetId;
    }
    
    public java.lang.String getFunName() {
        return this.funName;
    }

    public void setFunName(java.lang.String funName) {
        this.funName = funName;
    }
    
    public java.lang.String getFunDesc() {
        return this.funDesc;
    }

    public void setFunDesc(java.lang.String funDesc) {
        this.funDesc = funDesc;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RulesetFunLogVO) ) return false;
        RulesetFunLogVO castOther = (RulesetFunLogVO) other;
        return new EqualsBuilder()
            .append(this.getLogId(), castOther.getLogId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLogId())
            .toHashCode();
    }

}

