package com.congxing.rulemgt.reader.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.rulemgt.ruleset.model.RulesetBoVO;

/**
 * <p>
 * Title: rulesetVO <br/>
 * Description: Mapping Object for table RULOBJECT_READER <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-16
 */
public class ReaderVO implements Serializable {

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
    private java.lang.Long readerId;
    
    /** nullable persistent field */
    private java.lang.Long rulesetId;
    
    /** nullable persistent field */
    private java.lang.String rulesetVersion;
    
    /** nullable persistent field */
    private java.lang.String readerName;
    
    /** nullable persistent field */
    private java.lang.String readerDesc;
    
    /** nullable persistent field */
    private java.lang.Long dbId;
    
    /** nullable persistent field */
    private java.lang.String readerSql;
    
    private java.lang.Integer readerPriority;
	

    public ReaderVO(Long readerId, Long rulesetId, String rulesetVersion,
			String readerName, String readerDesc, Long dbId,
			String readerSql, Integer readerPriority) {
		super();
		this.readerId = readerId;
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
		this.readerName = readerName;
		this.readerDesc = readerDesc;
		this.dbId = dbId;
		this.readerSql = readerSql;
		this.readerPriority = readerPriority;
	}

	/** minimal constructor */
    public ReaderVO(java.lang.Long readerId) {
        this.readerId = readerId;
    }
    
    /** default constructor */
    public ReaderVO() {
    }
    
    public java.lang.Long getReaderId() {
        return this.readerId;
    }

    public void setReaderId(java.lang.Long readerId) {
        this.readerId = readerId;
    }
    
    public java.lang.Long getRulesetId() {
        return this.rulesetId;
    }

    public void setRulesetId(java.lang.Long rulesetId) {
        this.rulesetId = rulesetId;
    }
    
    public java.lang.String getRulesetVersion() {
        return this.rulesetVersion;
    }

    public void setRulesetVersion(java.lang.String rulesetVersion) {
        this.rulesetVersion = rulesetVersion;
    }
    
    public java.lang.String getReaderName() {
        return this.readerName;
    }

    public void setReaderName(java.lang.String readerName) {
        this.readerName = readerName;
    }
    
    public java.lang.String getReaderDesc() {
        return this.readerDesc;
    }

    public void setReaderDesc(java.lang.String readerDesc) {
        this.readerDesc = readerDesc;
    }

	public java.lang.Long getDbId() {
		return dbId;
	}

	public void setDbId(java.lang.Long dbId) {
		this.dbId = dbId;
	}
    
    public java.lang.String getReaderSql() {
        return this.readerSql;
    }

    public void setReaderSql(java.lang.String readerSql) {
        this.readerSql = readerSql;
    }

	public java.lang.Integer getReaderPriority() {
		return readerPriority;
	}

	public void setReaderPriority(java.lang.Integer readerPriority) {
		this.readerPriority = readerPriority;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("readerId", getReaderId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ReaderVO) ) return false;
        ReaderVO castOther = (ReaderVO) other;
        return new EqualsBuilder()
            .append(this.getReaderId(), castOther.getReaderId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getReaderId())
            .toHashCode();
    }
    
    private List<ReaderParamVO> paramDatas;
    
    private List<ReaderValueVO> valueDatas;
    
    private List<RulesetBoVO> boDatas;

	public List<ReaderParamVO> getParamDatas() {
		return paramDatas;
	}

	public void setParamDatas(List<ReaderParamVO> paramDatas) {
		this.paramDatas = paramDatas;
	}

	public List<ReaderValueVO> getValueDatas() {
		return valueDatas;
	}

	public void setValueDatas(List<ReaderValueVO> valueDatas) {
		this.valueDatas = valueDatas;
	}

	public List<RulesetBoVO> getBoDatas() {
		return boDatas;
	}

	public void setBoDatas(List<RulesetBoVO> boDatas) {
		this.boDatas = boDatas;
	}

}

