/**
 * auto-generated code
 * 2013-05-30 07:34:50
 */
package com.congxing.system.menuvisitdetstat.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: MenuVisitDetStatVO <br/>
 * Description: Mapping Object for table SYS_MENU_VISIT_DET_STAT <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuVisitDetStatVO implements Serializable {

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
    private java.lang.String menuId;
    
    /** nullable persistent field */
    private java.lang.Integer visitNum;
    
    /** identifier field */
    private java.lang.String statDay;
    
    /** full constructor */
    public MenuVisitDetStatVO(java.lang.String userId,java.lang.String menuId,java.lang.Integer visitNum,java.lang.String statDay) {
        this.userId = userId;
        this.menuId = menuId;
        this.visitNum = visitNum;
        this.statDay = statDay;
    }

    /** minimal constructor */
    public MenuVisitDetStatVO(java.lang.String userId,java.lang.String menuId,java.lang.String statDay) {
        this.userId = userId;
        this.menuId = menuId;
        this.statDay = statDay;
    }
    
    /** default constructor */
    public MenuVisitDetStatVO() {
    }
    
    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public java.lang.String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(java.lang.String menuId) {
        this.menuId = menuId;
    }
    
    public java.lang.Integer getVisitNum() {
        return this.visitNum;
    }

    public void setVisitNum(java.lang.Integer visitNum) {
        this.visitNum = visitNum;
    }
    
    public java.lang.String getStatDay() {
        return this.statDay;
    }

    public void setStatDay(java.lang.String statDay) {
        this.statDay = statDay;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .append("menuId", getMenuId())
            .append("statDay", getStatDay())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuVisitDetStatVO) ) return false;
        MenuVisitDetStatVO castOther = (MenuVisitDetStatVO) other;
        return new EqualsBuilder()
            .append(this.getUserId(), castOther.getUserId())
            .append(this.getMenuId(), castOther.getMenuId())
            .append(this.getStatDay(), castOther.getStatDay())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserId())
            .append(getMenuId())
            .append(getStatDay())
            .toHashCode();
    }

}

