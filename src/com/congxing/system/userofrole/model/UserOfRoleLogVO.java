/**
 * auto-generated code
 * 2013-05-30 07:50:54
 */
package com.congxing.system.userofrole.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: UserOfRoleLogVO <br/>
 * Description: Mapping Object for table SYS_USER_OF_ROLE_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class UserOfRoleLogVO implements Serializable {

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
    private java.lang.Long urId;
    
    /** nullable persistent field */
    private java.lang.String userId;
    
    /** nullable persistent field */
    private java.lang.String roleId;
    
    /** nullable persistent field */
    private java.lang.String status;
    
    /** nullable persistent field */
    private java.util.Date beginTime;
    
    /** nullable persistent field */
    private java.util.Date endTime;
    
    /** full constructor */
    public UserOfRoleLogVO(java.lang.Long logId,java.lang.String oprCode,java.util.Date oprTime,java.lang.String oprType,java.lang.Long urId,java.lang.String userId,java.lang.String roleId,java.lang.String status,java.util.Date beginTime,java.util.Date endTime) {
        this.logId = logId;
        this.oprCode = oprCode;
        this.oprTime = oprTime;
        this.oprType = oprType;
        this.urId = urId;
        this.userId = userId;
        this.roleId = roleId;
        this.status = status;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /** minimal constructor */
    public UserOfRoleLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public UserOfRoleLogVO() {
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
    
    public java.lang.Long getUrId() {
        return this.urId;
    }

    public void setUrId(java.lang.Long urId) {
        this.urId = urId;
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }
    
    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    public java.util.Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(java.util.Date beginTime) {
        this.beginTime = beginTime;
    }
    
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserOfRoleLogVO) ) return false;
        UserOfRoleLogVO castOther = (UserOfRoleLogVO) other;
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

