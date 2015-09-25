/**
 * auto-generated code
 * 2013-05-30 07:26:02
 */
package com.congxing.system.menumonitorconfig.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.core.web.struts2.Business;

/**
 * <p>
 * Title: MenuMonitorConfigVO <br/>
 * Description: Mapping Object for table SYS_MENU_MONITOR_CONFIG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuMonitorConfigVO extends Business implements Serializable {

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
    private java.lang.Long monitorId;
    
    /** nullable persistent field */
    private java.lang.String monitorUrl;
    
    /** nullable persistent field */
    private java.lang.String monitorName;
    
    /** nullable persistent field */
    private java.lang.String memo;
    
    /** full constructor */
    public MenuMonitorConfigVO(java.lang.Long monitorId,java.lang.String monitorUrl,java.lang.String monitorName,java.lang.String memo) {
        this.monitorId = monitorId;
        this.monitorUrl = monitorUrl;
        this.monitorName = monitorName;
        this.memo = memo;
    }

    /** minimal constructor */
    public MenuMonitorConfigVO(java.lang.Long monitorId) {
        this.monitorId = monitorId;
    }
    
    /** default constructor */
    public MenuMonitorConfigVO() {
    }
    
    public java.lang.Long getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(java.lang.Long monitorId) {
        this.monitorId = monitorId;
    }
    
    public java.lang.String getMonitorUrl() {
        return this.monitorUrl;
    }

    public void setMonitorUrl(java.lang.String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }
    
    public java.lang.String getMonitorName() {
        return this.monitorName;
    }

    public void setMonitorName(java.lang.String monitorName) {
        this.monitorName = monitorName;
    }
    
    public java.lang.String getMemo() {
        return this.memo;
    }

    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("monitorId", getMonitorId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuMonitorConfigVO) ) return false;
        MenuMonitorConfigVO castOther = (MenuMonitorConfigVO) other;
        return new EqualsBuilder()
            .append(this.getMonitorId(), castOther.getMonitorId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getMonitorId())
            .toHashCode();
    }

}

