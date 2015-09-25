/**
 * auto-generated code
 * 2013-05-30 07:49:12
 */
package com.congxing.system.user.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: UserHisPwdVO <br/>
 * Description: Mapping Object for table SYS_USER_HIS_PWD <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class UserHisPwdVO implements Serializable {

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
    private java.lang.String userId;
    
    /** identifier field */
    private java.lang.String password;
    
    /** nullable persistent field */
    private java.util.Date modifyTime;
    
    /** full constructor */
    public UserHisPwdVO(java.lang.String userId,java.lang.String password,java.util.Date modifyTime) {
        this.userId = userId;
        this.password = password;
        this.modifyTime = modifyTime;
    }

    /** minimal constructor */
    public UserHisPwdVO(java.lang.String userId,java.lang.String password) {
        this.userId = userId;
        this.password = password;
    }
    
    /** default constructor */
    public UserHisPwdVO() {
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
    public java.util.Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .append("password", getPassword())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserHisPwdVO) ) return false;
        UserHisPwdVO castOther = (UserHisPwdVO) other;
        return new EqualsBuilder()
            .append(this.getUserId(), castOther.getUserId())
            .append(this.getPassword(), castOther.getPassword())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserId())
            .append(getPassword())
            .toHashCode();
    }

}

