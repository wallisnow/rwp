/**
 * auto-generated code
 * 2013-12-23 20:12:04
 */
package com.congxing.rulemgt.storage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.rulemgt.ruleset.model.RulesetBoVO;

/**
 * <p>
 * Title: RuleStorageInfoVO <br/>
 * Description: Mapping Object for table ST_RULE_STORAGE_INFO <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author jinjiebin
 * @version 1.0
 * @since 2013-12-23
 */
public class StorageVO implements Serializable {

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
    private java.lang.Long storageId;
    
    /** nullable persistent field */
    private java.lang.Long rulesetId;
    
    /** nullable persistent field */
    private java.lang.String rulesetVersion;
    
    /** nullable persistent field */
    private java.lang.String storageName;
    
    /** nullable persistent field */
    private java.lang.String storageDesc;
    
    /** nullable persistent field */
    private java.lang.Long dbId;
    
    /** nullable persistent field */
    private java.lang.String tableName;
    
    /** nullable persistent field */
    private java.lang.String subCode;
    
    /** nullable persistent field */
    private java.util.Date subDate;

    public StorageVO(Long storageId, Long rulesetId, String rulesetVersion,
			String storageName, String storageDesc, Long dbId,
			String tableName, String subCode, Date subDate) {
		super();
		this.storageId = storageId;
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
		this.storageName = storageName;
		this.storageDesc = storageDesc;
		this.dbId = dbId;
		this.tableName = tableName;
		this.subCode = subCode;
		this.subDate = subDate;
	}

	/** minimal constructor */
    public StorageVO(java.lang.Long storageId) {
        this.storageId = storageId;
    }
    
    /** default constructor */
    public StorageVO() {
    }
    
    public java.lang.Long getStorageId() {
        return this.storageId;
    }

    public void setStorageId(java.lang.Long storageId) {
        this.storageId = storageId;
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
    
    public java.lang.String getStorageName() {
        return this.storageName;
    }

    public void setStorageName(java.lang.String storageName) {
        this.storageName = storageName;
    }
    
    public java.lang.String getStorageDesc() {
        return this.storageDesc;
    }

    public void setStorageDesc(java.lang.String storageDesc) {
        this.storageDesc = storageDesc;
    }

	public java.lang.Long getDbId() {
		return dbId;
	}

	public void setDbId(java.lang.Long dbId) {
		this.dbId = dbId;
	}
    
    public java.lang.String getTableName() {
        return this.tableName;
    }

    public void setTableName(java.lang.String tableName) {
        this.tableName = tableName;
    }
    
    public java.lang.String getSubCode() {
        return this.subCode;
    }

    public void setSubCode(java.lang.String subCode) {
        this.subCode = subCode;
    }
    
    public java.util.Date getSubDate() {
        return this.subDate;
    }

    public void setSubDate(java.util.Date subDate) {
        this.subDate = subDate;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("storageId", getStorageId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StorageVO) ) return false;
        StorageVO castOther = (StorageVO) other;
        return new EqualsBuilder()
            .append(this.getStorageId(), castOther.getStorageId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getStorageId())
            .toHashCode();
    }
    

    
    
    private List<StorageParamVO> paramDatas;
    private List<RulesetBoVO> boDatas;

	public List<StorageParamVO> getParamDatas() {
		return paramDatas;
	}

	public void setParamDatas(List<StorageParamVO> paramDatas) {
		this.paramDatas = paramDatas;
	}

	public List<RulesetBoVO> getBoDatas() {
		return boDatas;
	}

	public void setBoDatas(List<RulesetBoVO> boDatas) {
		this.boDatas = boDatas;
	}


}

