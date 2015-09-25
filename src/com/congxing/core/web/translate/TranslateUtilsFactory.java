package com.congxing.core.web.translate;

import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.translate.TranslateUtils;

public class TranslateUtilsFactory {
	
	public static TranslateUtils instance;
	
	public static TranslateUtils getInstance(){
		if(null == instance){
			instance = SpringContextManager.getBean("translateUtils");
		}
		return instance;
	}

}
