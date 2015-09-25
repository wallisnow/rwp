package com.congxing.core.utils;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.web.sequence.Sequence;

public class LogUtils {

	public static Object createLogs(Object vo, Class<?> logClass, String oprType, String oprCode ) throws Exception{
		Object logVO = logClass.newInstance();
		
		LogProperties logProperties = new LogProperties();
		logProperties.setLogId(new Long(Sequence.getSequence()));
		logProperties.setOprCode(oprCode);
		logProperties.setOprTime(new Date());
		logProperties.setOprType(oprType);
		
		BeanUtils.copyProperties(logVO, vo);
		BeanUtils.copyProperties(logVO, logProperties);
		
		return logVO;
	}
	
	public static class LogProperties {
		private java.lang.Long logId;
	    
	    private java.lang.String oprCode;
	    
	    private java.util.Date oprTime;
	    
	    private java.lang.String oprType;

		public java.lang.Long getLogId() {
			return logId;
		}

		public void setLogId(java.lang.Long logId) {
			this.logId = logId;
		}

		public java.lang.String getOprCode() {
			return oprCode;
		}

		public void setOprCode(java.lang.String oprCode) {
			this.oprCode = oprCode;
		}

		public java.util.Date getOprTime() {
			return oprTime;
		}

		public void setOprTime(java.util.Date oprTime) {
			this.oprTime = oprTime;
		}

		public java.lang.String getOprType() {
			return oprType;
		}

		public void setOprType(java.lang.String oprType) {
			this.oprType = oprType;
		}
	    
	    
	}
}
