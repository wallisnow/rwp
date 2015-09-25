package com.congxing.core.web.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ActionNamespaceMap {
	
	private static Map<String, String> namespaces = new HashMap<String, String>();
	private static final String BASE_PACKAGE = "com.congxing.";
	private static final String JOIN_PACKAGE_AND_ACTION = ".view.";
	
	
	public static void addNamespaceByActionName(String actionName){
		String moduleName = StringUtils.substringBetween(actionName, BASE_PACKAGE, JOIN_PACKAGE_AND_ACTION);
		if(StringUtils.isNotBlank(moduleName)){
			String namespaceName = "/" + StringUtils.replace(moduleName, ".", "/");
			namespaces.put(namespaceName, actionName);
		}
	}
	
	public static String getActionNameByNamespace(String namespace){
		return namespaces.get(namespace);
	}
	
	public static boolean hasNamespace(String namespace){
		return namespaces.containsKey(namespace);
	}

}
