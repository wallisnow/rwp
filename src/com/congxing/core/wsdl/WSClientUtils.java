package com.congxing.core.wsdl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.apache.log4j.Logger;

import com.congxing.core.utils.JsonUtils;


public class WSClientUtils {
	
	public static final Logger logger = Logger.getLogger(WSClientUtils.class);
	

	public static Map<String, Map<String,Class<?>>> getMethodAndParam(String wsdllocation) throws Exception {
		
		@SuppressWarnings("unused")
		Map<String, Map<String, Class<?>>> allMethodInfoMap = new HashMap<String, Map<String,Class<?>>>();
		@SuppressWarnings("unused")
		Map<String, Class<?>> methodParamMap =null;
		
		WSDLFactory wsdlFactory = WSDLFactory.newInstance();
		
		WSDLReader reader = wsdlFactory.newWSDLReader();
		
		reader.setFeature("javax.wsdl.verbose", true);
		
		reader.setFeature("javax.wsdl.importDocuments", true);
		
		Definition defintion = reader.readWSDL(wsdllocation);
		
		System.out.println(JsonUtils.toJson(defintion.getNamespaces()));
		
		Map services = defintion.getServices();
		
		for(Iterator it = services.entrySet().iterator(); it.hasNext();){
			System.out.println("=================================");
			System.out.println(it.next());
			System.out.println("=================================");
		}
		
		
		return null;
	}
	
	public static void main(String []args) throws Exception{
		WSClientUtils.getMethodAndParam("http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl");
	}

}
