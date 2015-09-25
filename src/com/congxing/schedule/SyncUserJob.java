package com.congxing.schedule;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.SpringContextManager;

public class SyncUserJob extends QuartzJobBean{
	
	public static Logger logger = Logger.getLogger(SyncUserJob.class);
	
	private CommonService service;
	
	public SyncUserJob(){
		service = SpringContextManager.getBean("commonServiceImpl");
	}

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("Call SyncUserJob BEGIN: " + DateFormatFactory.getDefaultFormat().format(new java.util.Date()));
		try {
	
		} catch (Exception ex) {
			logger.info("SyncUserJob Exception[" + ex.getMessage() + "]", ex); 
			ex.printStackTrace();
		}
		logger.info("Call SyncUserJob END: " + DateFormatFactory.getDefaultFormat().format(new java.util.Date()));
	}

	public CommonService getService() {
		return service;
	}
	
	public void setService(CommonService service) {
		this.service = service;
	}

}
