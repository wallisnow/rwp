/**
 * auto-generated code
 * 2013-05-30 07:17:18
 */
package com.congxing.system.logindetstat.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.system.user.model.UserVO;

/**
 * <p>
 * Title: LoginDetStatVO <br/>
 * Description: Mapping Object for table SYS_LOGIN_DET_STAT <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class LoginDetStatVO implements Serializable {

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
    private java.lang.String statDay;
    
    /** nullable persistent field */
    private java.lang.Integer loginNum;
    
    /** full constructor */
    public LoginDetStatVO(java.lang.String userId,java.lang.String statDay,java.lang.Integer loginNum) {
        this.userId = userId;
        this.statDay = statDay;
        this.loginNum = loginNum;
    }

    /** minimal constructor */
    public LoginDetStatVO(java.lang.String userId,java.lang.String statDay) {
        this.userId = userId;
        this.statDay = statDay;
    }
    
    /** default constructor */
    public LoginDetStatVO() {
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getStatDay() {
        return this.statDay;
    }

    public void setStatDay(java.lang.String statDay) {
        this.statDay = statDay;
    }
    
    public java.lang.Integer getLoginNum() {
        return this.loginNum;
    }

    public void setLoginNum(java.lang.Integer loginNum) {
        this.loginNum = loginNum;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .append("statDay", getStatDay())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LoginDetStatVO) ) return false;
        LoginDetStatVO castOther = (LoginDetStatVO) other;
        return new EqualsBuilder()
            .append(this.getUserId(), castOther.getUserId())
            .append(this.getStatDay(), castOther.getStatDay())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserId())
            .append(getStatDay())
            .toHashCode();
    }
    
    private UserVO userVO;

    private Integer visitNum;
    
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

}

