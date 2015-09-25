/**
 * auto-generated code
 * 2013-06-05 03:21:51
 */
package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RulesetBoVO <br/>
 * Description: Mapping Object for table RULESET_BO <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-05
 */
public class RulesetBoVO implements Serializable {

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
    
    
    public RulesetBoVO(Long boId, Long rulesetId, String rulesetVersion,
			String boName, String boDesc, String dataType) {
		super();
		this.boId = boId;
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
		this.boName = boName;
		this.boDesc = boDesc;
		this.dataType = dataType;
	}

	/** minimal constructor */
    public RulesetBoVO(java.lang.Long boId) {
        this.boId = boId;
    }
    
    /** default constructor */
    public RulesetBoVO() {
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

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
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
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("boId", getBoId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RulesetBoVO) ) return false;
        RulesetBoVO castOther = (RulesetBoVO) other;
        return new EqualsBuilder()
            .append(this.getBoId(), castOther.getBoId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getBoId())
            .toHashCode();
    }

	public java.lang.String getDataType() {
		return dataType;
	}

	public void setDataType(java.lang.String dataType) {
		this.dataType = dataType;
	}

}

