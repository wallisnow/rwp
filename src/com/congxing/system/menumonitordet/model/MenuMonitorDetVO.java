/**
 * auto-generated code
 * 2013-05-30 07:28:46
 */
package com.congxing.system.menumonitordet.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: MenuMonitorDetVO <br/>
 * Description: Mapping Object for table SYS_MENU_MONITOR_DET <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
public class MenuMonitorDetVO implements Serializable {

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
    private java.lang.Long monitorId;
    
    /** nullable persistent field */
    private java.lang.String oprParams;
    
    /** nullable persistent field */
    private java.util.Date beginTime;
    
    /** nullable persistent field */
    private java.util.Date endTime;
    
    /** full constructor */
    public MenuMonitorDetVO(java.lang.Long seqId,java.lang.String userId,java.lang.String userIp,java.lang.Long monitorId,java.lang.String oprParams,java.util.Date beginTime,java.util.Date endTime) {
        this.seqId = seqId;
        this.userId = userId;
        this.userIp = userIp;
        this.monitorId = monitorId;
        this.oprParams = oprParams;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /** minimal constructor */
    public MenuMonitorDetVO(java.lang.Long seqId) {
        this.seqId = seqId;
    }
    
    /** default constructor */
    public MenuMonitorDetVO() {
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
    
    public java.lang.Long getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(java.lang.Long monitorId) {
        this.monitorId = monitorId;
    }
    
    public java.lang.String getOprParams() {
        return this.oprParams;
    }

    public void setOprParams(java.lang.String oprParams) {
        this.oprParams = oprParams;
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
            .append("seqId", getSeqId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MenuMonitorDetVO) ) return false;
        MenuMonitorDetVO castOther = (MenuMonitorDetVO) other;
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

