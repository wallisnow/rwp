/**
 * auto-generated code
 * 2013-05-30 07:14:04
 */
package com.congxing.system.logindetstat.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: LoginDetVO <br/>
 * Description: Mapping Object for table SYS_LOGIN_DET <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class LoginDetVO implements Serializable {

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
    private java.lang.Long seqId;
    
    /** nullable persistent field */
    private java.lang.String userId;
    
    /** nullable persistent field */
    private java.lang.String userIp;
    
    /** nullable persistent field */
    private java.util.Date loginInTime;
    
    /** nullable persistent field */
    private java.util.Date loginOutTime;
    
    /** full constructor */
    public LoginDetVO(java.lang.Long seqId,java.lang.String userId,java.lang.String userIp,java.util.Date loginInTime,java.util.Date loginOutTime) {
        this.seqId = seqId;
        this.userId = userId;
        this.userIp = userIp;
        this.loginInTime = loginInTime;
        this.loginOutTime = loginOutTime;
    }

    /** minimal constructor */
    public LoginDetVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public LoginDetVO() {
    }
    
    public java.lang.Long getSeqId() {
        return this.seqId;
    }

    public void setSeqId(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(java.lang.String userIp) {
        this.userIp = userIp;
    }
    
    public java.util.Date getLoginInTime() {
        return this.loginInTime;
    }

    public void setLoginInTime(java.util.Date loginInTime) {
        this.loginInTime = loginInTime;
    }
    
    public java.util.Date getLoginOutTime() {
        return this.loginOutTime;
    }

    public void setLoginOutTime(java.util.Date loginOutTime) {
        this.loginOutTime = loginOutTime;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LoginDetVO) ) return false;
        LoginDetVO castOther = (LoginDetVO) other;
        return new EqualsBuilder()
            .append(this.getSeqId(), castOther.getSeqId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSeqId())
            .toHashCode();
    }

}

