package com.congxing.system.businesslog.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.Business;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class BusinessLogVO implements Serializable {

	private Long logId;
	
	private String oprCode;
	
	private Date oprTime;
	
	private String oprType;
	
	private String busiName;
	
	private String busiValue;

	public BusinessLogVO(Long logId, String oprCode, java.util.Date oprTime,
			String oprType, String busiName, String busiValue) {
		super();
		this.logId = logId;
		this.oprCode = oprCode;
		this.oprTime = oprTime;
		this.oprType = oprType;
		this.busiName = busiName;
		this.busiValue = busiValue;
	}
	
	public BusinessLogVO() {
		super();
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getOprCode() {
		return oprCode;
	}

	public void setOprCode(String oprCode) {
		this.oprCode = oprCode;
	}

	public java.util.Date getOprTime() {
		return oprTime;
	}

	public void setOprTime(java.util.Date oprTime) {
		this.oprTime = oprTime;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public String getBusiValue() {
		return busiValue;
	}

	public void setBusiValue(String busiValue) {
		this.busiValue = busiValue;
	}
	
	public boolean equals(Object other) {
        if ( !(other instanceof BusinessLogVO) ) return false;
        BusinessLogVO castOther = (BusinessLogVO) other;
        return new EqualsBuilder()
            .append(this.getLogId(), castOther.getLogId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLogId())
            .toHashCode();
    }


	public static BusinessLogVO create(UserVO userVO, String oprType, Business business){
		BusinessLogVO logVO = new BusinessLogVO();
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new java.util.Date());
		logVO.setOprType(oprType);
		logVO.setBusiName(business.getBusiName());
		logVO.setBusiValue(business.getBusiValue());
		return logVO;
	}
	
}
