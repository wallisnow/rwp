/**
 * auto-generated code
 * 2013-05-30 07:47:56
 */
package com.congxing.system.user.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: UserLogVO <br/>
 * Description: Mapping Object for table SYS_USER_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class UserLogVO implements Serializable {

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
    private java.lang.String userId;
    
    /** nullable persistent field */
    private java.lang.String userName;
    
    /** nullable persistent field */
    private java.lang.String email;
    
    /** nullable persistent field */
    private java.lang.String mobileno;
    
    /** nullable persistent field */
    private java.lang.String telNumber;
    
    /** nullable persistent field */
    private java.lang.String birthday;
    
    /** nullable persistent field */
    private java.lang.String dpId;
    
    /** nullable persistent field */
    private java.lang.String dpName;
    
    /** nullable persistent field */
    private java.lang.String dpCode;
    
    /** nullable persistent field */
    private java.lang.String parentDpId;
    
    /** nullable persistent field */
    private java.lang.String dpFullName;
    
    /** nullable persistent field */
    private java.lang.String jobTypeId;
    
    /** nullable persistent field */
    private java.lang.String jobTypeDesc;
    
    /** nullable persistent field */
    private java.lang.String empClassId;
    
    /** nullable persistent field */
    private java.lang.String empClassDesc;
    
    /** nullable persistent field */
    private java.lang.String sexId;
    
    /** nullable persistent field */
    private java.lang.String orderNo;
    
    /** nullable persistent field */
    private java.lang.String userRole;
    
    /** nullable persistent field */
    private java.lang.String userLigion;
    
    /** nullable persistent field */
    private java.lang.String isTmpUser;
    
    /** nullable persistent field */
    private java.lang.String userType;
    
    /** nullable persistent field */
    private java.lang.String status;
    
    /** nullable persistent field */
    private java.lang.String password;
    
    /** full constructor */
    public UserLogVO(java.lang.Long logId,java.lang.String oprCode,java.util.Date oprTime,java.lang.String oprType,java.lang.String userId,java.lang.String userName,java.lang.String email,java.lang.String mobileno,java.lang.String telNumber,java.lang.String birthday,java.lang.String dpId,java.lang.String dpName,java.lang.String dpCode,java.lang.String parentDpId,java.lang.String dpFullName,java.lang.String jobTypeId,java.lang.String jobTypeDesc,java.lang.String empClassId,java.lang.String empClassDesc,java.lang.String sexId,java.lang.String orderNo,java.lang.String userRole,java.lang.String userLigion,java.lang.String isTmpUser,java.lang.String userType,java.lang.String status,java.lang.String password) {
        this.logId = logId;
        this.oprCode = oprCode;
        this.oprTime = oprTime;
        this.oprType = oprType;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.mobileno = mobileno;
        this.telNumber = telNumber;
        this.birthday = birthday;
        this.dpId = dpId;
        this.dpName = dpName;
        this.dpCode = dpCode;
        this.parentDpId = parentDpId;
        this.dpFullName = dpFullName;
        this.jobTypeId = jobTypeId;
        this.jobTypeDesc = jobTypeDesc;
        this.empClassId = empClassId;
        this.empClassDesc = empClassDesc;
        this.sexId = sexId;
        this.orderNo = orderNo;
        this.userRole = userRole;
        this.userLigion = userLigion;
        this.isTmpUser = isTmpUser;
        this.userType = userType;
        this.status = status;
        this.password = password;
    }

    /** minimal constructor */
    public UserLogVO(java.lang.Long logId) {
        this.logId = logId;
    }
    
    /** default constructor */
    public UserLogVO() {
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
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getUserName() {
        return this.userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }
    
    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    public java.lang.String getMobileno() {
        return this.mobileno;
    }

    public void setMobileno(java.lang.String mobileno) {
        this.mobileno = mobileno;
    }
    
    public java.lang.String getTelNumber() {
        return this.telNumber;
    }

    public void setTelNumber(java.lang.String telNumber) {
        this.telNumber = telNumber;
    }
    
    public java.lang.String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(java.lang.String birthday) {
        this.birthday = birthday;
    }
    
    public java.lang.String getDpId() {
        return this.dpId;
    }

    public void setDpId(java.lang.String dpId) {
        this.dpId = dpId;
    }
    
    public java.lang.String getDpName() {
        return this.dpName;
    }

    public void setDpName(java.lang.String dpName) {
        this.dpName = dpName;
    }
    
    public java.lang.String getDpCode() {
        return this.dpCode;
    }

    public void setDpCode(java.lang.String dpCode) {
        this.dpCode = dpCode;
    }
    
    public java.lang.String getParentDpId() {
        return this.parentDpId;
    }

    public void setParentDpId(java.lang.String parentDpId) {
        this.parentDpId = parentDpId;
    }
    
    public java.lang.String getDpFullName() {
        return this.dpFullName;
    }

    public void setDpFullName(java.lang.String dpFullName) {
        this.dpFullName = dpFullName;
    }
    
    public java.lang.String getJobTypeId() {
        return this.jobTypeId;
    }

    public void setJobTypeId(java.lang.String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
    
    public java.lang.String getJobTypeDesc() {
        return this.jobTypeDesc;
    }

    public void setJobTypeDesc(java.lang.String jobTypeDesc) {
        this.jobTypeDesc = jobTypeDesc;
    }
    
    public java.lang.String getEmpClassId() {
        return this.empClassId;
    }

    public void setEmpClassId(java.lang.String empClassId) {
        this.empClassId = empClassId;
    }
    
    public java.lang.String getEmpClassDesc() {
        return this.empClassDesc;
    }

    public void setEmpClassDesc(java.lang.String empClassDesc) {
        this.empClassDesc = empClassDesc;
    }
    
    public java.lang.String getSexId() {
        return this.sexId;
    }

    public void setSexId(java.lang.String sexId) {
        this.sexId = sexId;
    }
    
    public java.lang.String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(java.lang.String orderNo) {
        this.orderNo = orderNo;
    }
    
    public java.lang.String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(java.lang.String userRole) {
        this.userRole = userRole;
    }
    
    public java.lang.String getUserLigion() {
        return this.userLigion;
    }

    public void setUserLigion(java.lang.String userLigion) {
        this.userLigion = userLigion;
    }
    
    public java.lang.String getIsTmpUser() {
        return this.isTmpUser;
    }

    public void setIsTmpUser(java.lang.String isTmpUser) {
        this.isTmpUser = isTmpUser;
    }
    
    public java.lang.String getUserType() {
        return this.userType;
    }

    public void setUserType(java.lang.String userType) {
        this.userType = userType;
    }
    
    public java.lang.String getStatus() {
        return this.status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("logId", getLogId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserLogVO) ) return false;
        UserLogVO castOther = (UserLogVO) other;
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

