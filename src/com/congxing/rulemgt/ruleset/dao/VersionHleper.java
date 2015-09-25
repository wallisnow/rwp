package com.congxing.rulemgt.ruleset.dao;

import org.apache.commons.lang3.StringUtils;

import com.congxing.core.service.CommonService;
import com.congxing.core.utils.MonthUtils;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.rulemgt.ruleset.model.RulesetVO;

public class VersionHleper {
	
	public final static String VERSION_PRE = "V.";
	public final static String DEF_VERSION_SUF = ".001";
	
	public static void copyVersion(){
		
	}
	
	public static void isEditAble(){
		
	}
	
	public static void checkEditAble(){
		
	}
	
	public static synchronized String getNewVersion(Long rulesetId){
		return getNewVersion(MonthUtils.getCurrentMonth(), rulesetId);
	}
	
	private static String getNewVersion(String month, Long rulesetId){
		if(null == rulesetId){								//本月没有使用版本号时
			return VERSION_PRE + month + DEF_VERSION_SUF;
		}
		
		CommonService service = SpringContextManager.getBean("commonServiceImpl");
		String hql = "select max(rulesetVersion) from " + RulesetVO.class.getName() + " where rulesetVersion like ? and rulesetId=?";
		String version = (String)service.doFindUniqueByHQL(hql, new Object[]{VERSION_PRE + month + "%", rulesetId});
		if(StringUtils.isBlank(version)){								//本月没有使用版本号时
			return VERSION_PRE + month + DEF_VERSION_SUF;
		}
		
		String no = version.substring(version.lastIndexOf(".") + 1);	//取出该月的后缀号码并递增
		int intNo = Integer.valueOf(no);
		intNo ++;
		String versionSuf = "";
		if(intNo < 10){
			versionSuf = ".00" + intNo;
		}else if(intNo < 100){
			versionSuf = ".0" + intNo;
		}else if(intNo < 1000){
			versionSuf = "." + intNo;
		}else{
			return getNewVersion(MonthUtils.getOffsetMonth(month, 1), rulesetId);	//序号溢出时则下个月的号使用以备万一
		}
		
		return VERSION_PRE + month + versionSuf;
	}

}
