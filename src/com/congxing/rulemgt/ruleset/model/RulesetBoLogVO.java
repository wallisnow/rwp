/**
 * auto-generated code
 * 2013-06-06 08:27:34
 */
package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RulesetBoLogVO <br/>
 * Description: Mapping Object for table RULESET_BO_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-06
 */
public class RulesetBoLogVO implements Serializable {

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
    private java.lang.Long boId;
    
    /** nullable persistent field */
    private java.lang.Long rulesetId;
    
    private String rulesetVersion;
    
    /** nullable persistent field */
    private java.lang.String boName;
    
    /** nullable persistent field */
    private java.lang.String boDesc;
    
    /** nullable persistent field */
    private java.lang.String dataType;

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
	}
    
    /** full constructor */
    public RulesetBoLogVO(java.lang.Long logId,java.lang.String oprCode,java.util.Date oprTime,java.lang.String oprType,java.lang.Long boId,java.lang.Long rulesetId,java.lang.String boName,java.lang.String boDesc,java.lang.String dataType) {
        this.logId = logId;
        this.oprCode = oprCode;
        this.oprTime = oprTime;
        this.oprType = oprType;
        this.boId = boId;
        this.rulesetId = rulesetId;
        this.boName = boName;
        this.boDesc = boDesc;
        this.dataType = dataType;
    }

    /** minimal constructor */
    public RulesetBoLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public RulesetBoLogVO() {
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
    
    public java.lang.Long getBoId() {
        return this.boId;
    }

    public void setBoId(java.lang.Long boId) {
        this.boId = boId;
    }
    
    public java.lang.Long getRulesetId() {
        return this.rulesetId;
    }

    public void setRulesetId(java.lang.Long rulesetId) {
        this.rulesetId = rulesetId;
    }
    
    public java.lang.String getBoName() {
        return this.boName;
    }

    public void setBoName(java.lang.String boName) {
        this.boName = boName;
    }
    
    public java.lang.String getBoDesc() {
        return this.boDesc;
    }

    public void setBoDesc(java.lang.String boDesc) {
        this.boDesc = boDesc;
    }

	public java.lang.String getDataType() {
		return dataType;
	}

	public void setDataType(java.lang.String dataType) {
		this.dataType = dataType;
	}
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RulesetBoLogVO) ) return false;
        RulesetBoLogVO castOther = (RulesetBoLogVO) other;
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

