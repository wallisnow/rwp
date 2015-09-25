package com.congxing.core.web.control;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsException;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.json.JSONResult;

import com.congxing.core.utils.AnnotationTools;
import com.congxing.core.web.control.annotation.InterceptorRef;
import com.congxing.core.web.control.annotation.InterceptorRefs;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.InterceptorStackConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.config.providers.InterceptorBuilder;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


public class DefaultUnknownHandler implements UnknownHandler {
	
	static Logger logger = LoggerFactory.getLogger(DefaultUnknownHandler.class);
	
	private static Map<String, PackageConfig> packageConfigCacheMap = new HashMap<String, PackageConfig>();
	
    protected Configuration configuration;
    protected ObjectFactory objectFactory;
    protected String packageNamePrefix;
    protected String parentPackageName;
    protected PackageConfig parentPackage;
    
    protected final String DEFAULT_METHOD_NAME = "execute";
    protected final String DEFAULT_METHOD_RTN_SUCCESS = "success";
    protected final String DEFAULT_METHOD_RTN_ERROR = "error";
    
    public static Map<String, String> excludeMethodNames = null;;
    static{
    	if(null == excludeMethodNames){
    		excludeMethodNames = new HashMap<String, String>();
    		excludeMethodNames.put("toString", "1");
    		excludeMethodNames.put("doDefault", "1");
    		excludeMethodNames.put("input", "1");
    	}
    }

    
    @Inject
    public DefaultUnknownHandler(Configuration configuration, ObjectFactory objectFactory, 
            @Inject("struts.unknownhandler.packageNamePrefix") String packageNamePrefix,
            @Inject("struts.unknownhandler.parentPackageName") String parentPackageName) {
        this.configuration = configuration;
        this.objectFactory = objectFactory;
        this.packageNamePrefix = packageNamePrefix;
        this.parentPackageName = parentPackageName;
        this.parentPackage = configuration.getPackageConfig(parentPackageName);
        if (parentPackage == null) {
            throw new ConfigurationException("Unknown default parent package [" + parentPackageName + "]");
        }
    }
    /**
     * 该方法中namespaceName是以"/"开始,不会为空
     */
	public ActionConfig handleUnknownAction(String namespaceName, String methodName) throws XWorkException {
		logger.info(namespaceName + "|" + methodName);
		ActionConfig rtnConfig = this.getActionConfig(namespaceName, methodName);
		if(rtnConfig != null) return rtnConfig;
		
		String actionUrl = namespaceName + "/" + methodName;
		/**
		 * 把actionUrl中的"//"替换成"/"
		 */
		actionUrl = actionUrl.replaceAll("//", "/");
		/**
		 * lastIndex只有两种情况
		 * 执行Action方法中的execute方法	lastIndex = 0
		 * 执行Action方法中的其他方法		astIndex > 0
		 */
		int lastIndex = StringUtils.lastIndexOf(actionUrl, "/");
		PackageConfig packageConfig = null;
		/**
		 * 完全按执行Action方法中的execute方法进行查找
		 */
		String actionNamespaceName = actionUrl;
		String actionMethodName = DEFAULT_METHOD_NAME;
		
		/**
		 * 第一次获取PackageConfig信息
		 * 路径信息全部认为是Namespace
		 */
		packageConfigCacheMap.clear();
		packageConfig = packageConfigCacheMap.get(actionNamespaceName);
		
		if(packageConfig == null){
			String actionName = ActionNamespaceMap.getActionNameByNamespace(actionNamespaceName);
			if(StringUtils.isNotBlank(actionName)){
				if(logger.isDebugEnabled()){
					logger.debug("Loading Action: " + actionName);
				}
				packageConfig = this.buildPackageConfig(actionNamespaceName, actionName);
				if(null != packageConfig){
					packageConfigCacheMap.put(actionNamespaceName, packageConfig);
				}
			}
		}
		
		if(packageConfig == null && lastIndex == 0){
			throw new StrutsException("Unable to find a location for [" + actionUrl + "]");
		}
		
		if(packageConfig == null){
			actionNamespaceName = StringUtils.substring(actionUrl, 0, lastIndex);
			actionMethodName = StringUtils.substring(actionUrl, lastIndex + 1);
			/**
			 * 第二次获取PackageConfig信息
			 */
			packageConfig = packageConfigCacheMap.get(actionNamespaceName);
			if(packageConfig == null){
				String actionName = ActionNamespaceMap.getActionNameByNamespace(actionNamespaceName);
				if(StringUtils.isNotBlank(actionName)){
					if(logger.isDebugEnabled()){
						logger.debug("Loading Action: " + actionName);
					}
					packageConfig = this.buildPackageConfig(actionNamespaceName, actionName);
					if(null != packageConfig){
						packageConfigCacheMap.put(actionNamespaceName, packageConfig);
					}
				}
			}
		}
		
		if(packageConfig == null){
			throw new StrutsException("Unable to find a location for [" + actionUrl + "]");
		}
		return packageConfig.getActionConfigs().get(actionMethodName);
	}	

	public Object handleUnknownActionMethod(Object arg0, String arg1) throws NoSuchMethodException {
		//System.out.println("handleUnknownActionMethod : " + arg0 + " | " + arg1);
		return null;
	}

	public Result handleUnknownResult(ActionContext actionContext, String methodName, ActionConfig actionConfig, String result) throws XWorkException {
		//System.out.println("handleUnknownResult : " + methodName + " | " + result);
		return null;
	}
	
	
	/**
	 * 取得方法注解的拦截器信息
	 * @param method
	 * @return
	 */
	public List<InterceptorRef> findInterceptorAnnotationByMethod(Method method){
		InterceptorRefs interceptorRefs = AnnotationTools.findMethodAnnotation(method, InterceptorRefs.class);
		InterceptorRef interceptorRef = AnnotationTools.findMethodAnnotation(method, InterceptorRef.class);
		List<InterceptorRef> list = new ArrayList<InterceptorRef>();
		if(interceptorRefs != null && interceptorRefs.value().length > 0){
			for (int index = 0; index < interceptorRefs.value().length; index++) {
				list.add((interceptorRefs.value())[index]);
			}
		}
		if(interceptorRef != null){
			list.add(interceptorRef);
		}
		return list;
	}
	
	/**
	 * 获取方法注解的ResultType信息
	 * @param method
	 * @return
	 */
	public List<com.congxing.core.web.control.annotation.Result> findResultAnnotationByMethod(Method method){
		com.congxing.core.web.control.annotation.Results results = AnnotationTools.findMethodAnnotation(method, com.congxing.core.web.control.annotation.Results.class);
		com.congxing.core.web.control.annotation.Result result = AnnotationTools.findMethodAnnotation(method, com.congxing.core.web.control.annotation.Result.class);
		List<com.congxing.core.web.control.annotation.Result> resultList = new ArrayList<com.congxing.core.web.control.annotation.Result>();
		if(results != null && results.value().length > 0){
			for (int index = 0; index < results.value().length; index++) {
				resultList.add((results.value())[index]);
			}
		}
		if(result != null){
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 由AactionName获取相关方法信息
	 * @param actionName
	 * @return
	 * @throws XWorkException
	 */
	public List<MethodInfo> findMethodInClass(String actionName) throws XWorkException{
		List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
		try {
			Class<?> clazz = Class.forName(actionName);
			Method []methods = clazz.getMethods();
			
			for (int index = 0; index < methods.length; index++) {
				Method method = methods[index];
				
				/** 对于参数个数为0的方法和已预定排除的方法进行排除 **/
				if(method.getParameterTypes().length == 0 && !excludeMethodNames.containsKey(method.getName())){
					String methodName = method.getName();//方法名
					String methodPageInfo = this.findMethodPageInfo(actionName, method);//理想方法所对应的JSP信息
					List<InterceptorRef> interceptorRefs = this.findInterceptorAnnotationByMethod(method);//方法注解拦截器信息
					List<com.congxing.core.web.control.annotation.Result> results = this.findResultAnnotationByMethod(method);//方法注解Result信息
					methodInfos.add(new MethodInfo(methodName, methodPageInfo, interceptorRefs, results));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new StrutsException("");
		}
		return methodInfos;
	}
	
	/**
	 * 由Method猜测最终到达JSP页面
	 * <pre>
	 * 猜测规则(依赖规则页面名称全部小写):
	 * *show*.action || *list*.action || *delete*.action || *select*.action || *txt*.action || *excel*.action = *list*.jsp
	 * *add*.action || *view*.action || *edit*.action = *content*.jsp
	 * *XX*.action = *XX*.jsp
	 * 
	 * delete方法需要特殊处理:
	 * 1、delete成功时: 方法应该跳转至list.action方法
	 * 2、delete失败时: 方法应跳转至list.jsp
	 * </pre>
	 * @param actionName Action名称(带包信息)
	 * @param method
	 * @return
	 */
	public String findMethodPageInfo(String actionName, Method method){
		String jspName = "";
		String methodName = method.getName().toLowerCase();
		if(methodName.indexOf("add") >= 0){
			jspName = methodName.replaceAll("add", "content");
		}else if(methodName.indexOf("show") >= 0){
			jspName = methodName.replaceAll("show", "list");
		}else if(methodName.indexOf("list") >= 0){
			jspName = methodName;
		}else if(methodName.indexOf("delete") >= 0){
			jspName = methodName.replaceAll("delete", "list");
		}else if(methodName.indexOf("edit") >= 0){
			jspName = methodName.replaceAll("edit", "content");
		}else if(methodName.indexOf("view") >= 0){
			jspName = methodName.replaceAll("view", "content");
		}else if(methodName.indexOf("save") >= 0){
			jspName = methodName.replaceAll("save", "content");
		}else if(methodName.indexOf("txt") >= 0){
			jspName = methodName.replaceAll("txt", "list");
		}else if(methodName.indexOf("excel") >= 0){
			jspName = methodName.replaceAll("excel", "excel");
		}else if(methodName.indexOf(DEFAULT_METHOD_NAME) >= 0){
			jspName = StringUtils.substring(actionName, StringUtils.lastIndexOf(actionName, ".") + 1, actionName.length() - 6).toLowerCase();
		}else{
			jspName = methodName.toLowerCase();
		}
		return jspName + ".jsp";
	}
	
	/**
	 * 由ActionName和NamespceName构建PackageConfig
	 * @param namespaceName
	 * @param actionName
	 * @return
	 */
	public PackageConfig buildPackageConfig(String namespaceName, String actionName){
		/** 取Action所在包名 **/
		String packageName = StringUtils.substringBeforeLast(actionName, ".");
		
		List<MethodInfo> methodInfos = this.findMethodInClass(actionName);
		/** 生成PackageConfig信息 **/
		PackageConfig.Builder pkgConfig = new PackageConfig.Builder(packageName).namespace(namespaceName).addParent(parentPackage);
		
		InterceptorStackConfig interceptorStackConfig = (InterceptorStackConfig) parentPackage.getInterceptorConfig(parentPackage.getDefaultInterceptorRef());
		pkgConfig.addInterceptorStackConfig(interceptorStackConfig);
		
		for(MethodInfo methodInfo : methodInfos) {
			if(logger.isDebugEnabled()){
				logger.debug("Loading Method: " + methodInfo.getMethodName());
			}
			//生成ActionConfig信息
			ActionConfig.Builder actionConfig = new ActionConfig.Builder(pkgConfig.getName(), methodInfo.getMethodName(), actionName);
			actionConfig.methodName(methodInfo.getMethodName());
			
			//增加拦截器
			if(interceptorStackConfig.getInterceptors().size() > 0){
				actionConfig.addInterceptors((List<InterceptorMapping>) interceptorStackConfig.getInterceptors());
			}
			List<InterceptorMapping> interceptorMappingList = this.buildInterceptorMappings(pkgConfig, actionConfig, methodInfo);
			if(interceptorMappingList.size() > 0){
				actionConfig.addInterceptors(interceptorMappingList);
			}
	        
	        //生成ResultConfig信息
	        Map<String, ResultConfig> resultConfigs = this.buildResultConfigs(methodInfo, namespaceName, pkgConfig);
	        if(resultConfigs.size() > 0){
	        	actionConfig.addResultConfigs(resultConfigs);
	        }
			
			//注意此处KEY必须为包页面方法名
			pkgConfig.addActionConfig(methodInfo.getMethodName(), actionConfig.build());
		}
		return pkgConfig.build();
	}
	
	public List<InterceptorMapping> buildInterceptorMappings(PackageConfig.Builder pkgConfig, ActionConfig.Builder actionConfig, MethodInfo methodInfo){
		List<InterceptorMapping> list = new ArrayList<InterceptorMapping>();
		List<InterceptorRef> interceptorRefs = methodInfo.getInterceptorRefs();
		if(interceptorRefs != null && interceptorRefs.size() > 0){
			for(InterceptorRef interceptorRef : interceptorRefs){
				Map<String, String> params = this.buildParameterMap(interceptorRef.params());
				list.addAll(InterceptorBuilder.constructInterceptorReference(pkgConfig, interceptorRef.value(), params, actionConfig.build().getLocation(), objectFactory));
			}
		}
		return list;
	}
	
	/**
	 * 构建方法对应的ResultConfig信息
	 * @param methodInfo
	 * @param namespaceName
	 * @param pkgConfig
	 * @return
	 */
	public Map<String, ResultConfig> buildResultConfigs(MethodInfo methodInfo, String namespaceName, PackageConfig.Builder pkgConfig){        
        /** 生成ResultConfig信息 **/
        /** 优化判断方法是否有注解有Result **/
		if(methodInfo.getResults().size() > 0){
			return this.buildAnnoResultConfigs(methodInfo, namespaceName, pkgConfig);
		}
		
		/**
		 * 非注解Result方法,手工添加result信息
		 */
		if(StringUtils.endsWith(methodInfo.getMethodName(), "Json")){//Json方法
			return this.buildJsonResultConfigs(methodInfo, namespaceName, pkgConfig);
		}else if(methodInfo.getMethodName().toLowerCase().indexOf("delete") >= 0){//redirectAction方法
			return this.buildRedirectResultConfigs(methodInfo, namespaceName, pkgConfig);
		}else{
			return this.buildDefaultResultConfigs(methodInfo, namespaceName, pkgConfig);//普通方法
		}
	}
	
	/**
	 * 构建注解的ResultConfig信息
	 * @param methodInfo
	 * @param namespaceName
	 * @param pkgConfig
	 * @return
	 */
	public Map<String, ResultConfig> buildAnnoResultConfigs(MethodInfo methodInfo, String namespaceName, PackageConfig.Builder pkgConfig){
		Map<String, ResultConfig> resultConfigs = new HashMap<String, ResultConfig>();
		
		for(com.congxing.core.web.control.annotation.Result result : methodInfo.getResults()){
			String resultName = result.name();
			String resultType = result.type();
			
			if (StringUtils.isEmpty(resultName) || StringUtils.isBlank(resultName)) {
                resultName = Action.SUCCESS;
            }
			if (StringUtils.isEmpty(resultType) || StringUtils.isBlank(resultType)) {
				resultType = pkgConfig.getFullDefaultResultType();
                if (StringUtils.isEmpty(resultType)) {
                    throw new ConfigurationException("No result type specified for [" + resultName + " with location [" + result.location() + "]");
                }
            }
			ResultTypeConfig resultTypeConfig = pkgConfig.getResultType(resultType);

            if (resultTypeConfig == null) {
            	throw new ConfigurationException("No result type specified for [" + resultName + " with location [" + result.location() + "]");
            }
			
			Map<String, String> params = new HashMap<String, String>();
			if(resultTypeConfig.getParams() != null){
				params.putAll(resultTypeConfig.getParams());
			}
			
			if (result.params() != null) {
	            params.putAll(this.buildParameterMap(result.params()));
	        }
			if (result.location() != null) {
				String defaultParamName = resultTypeConfig.getDefaultResultParam();
				if (!params.containsKey(defaultParamName)) {
	                params.put(defaultParamName, result.location());
	            }
			}
			resultConfigs.put(result.name(), new ResultConfig.Builder(result.name(), resultTypeConfig.getClassName()).addParams(params).build());
		}
		return resultConfigs;
	}
	
	/**
	 * 构建普通的ResultConfig信息
	 * @param methodInfo
	 * @param namespaceName
	 * @param pkgConfig
	 * @return
	 */
	public Map<String, ResultConfig> buildDefaultResultConfigs(MethodInfo methodInfo, String namespaceName, PackageConfig.Builder pkgConfig){
		Map<String, ResultConfig> resultConfigs = new HashMap<String, ResultConfig>();
		String location = namespaceName + "/" + methodInfo.getLocation();
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("location", location);
		
		ResultConfig succResultConfig = new ResultConfig.Builder(DEFAULT_METHOD_RTN_SUCCESS, ServletDispatcherResult.class.getName()).addParams(params).build();
		ResultConfig failResultConfig = new ResultConfig.Builder(DEFAULT_METHOD_RTN_ERROR, ServletDispatcherResult.class.getName()).addParams(params).build();
		
		resultConfigs.put(succResultConfig.getName(), succResultConfig);
		resultConfigs.put(failResultConfig.getName(), failResultConfig);
		
		return resultConfigs;
	}
	
	/**
	 * 构建Redirect的ResultConfig信息
	 * @param methodInfo
	 * @param namespaceName
	 * @param pkgConfig
	 * @return
	 */
	public Map<String, ResultConfig> buildRedirectResultConfigs(MethodInfo methodInfo, String namespaceName, PackageConfig.Builder pkgConfig){
		Map<String, ResultConfig> resultConfigs = new HashMap<String, ResultConfig>();
		
		String location = methodInfo.getLocation();
		String sucLocation = namespaceName + "/" + StringUtils.replace(location, ".jsp", ".action");
		String errLocation = namespaceName + "/" + location;
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("location", sucLocation);
		
		ResultConfig succResultConfig = new ResultConfig.Builder(DEFAULT_METHOD_RTN_SUCCESS, ServletRedirectResult.class.getName()).addParams(params).build();

		params.put("location", errLocation);
		ResultConfig failResultConfig = new ResultConfig.Builder(DEFAULT_METHOD_RTN_ERROR, ServletDispatcherResult.class.getName()).addParams(params).build();
		
		resultConfigs.put(succResultConfig.getName(), succResultConfig);
		resultConfigs.put(failResultConfig.getName(), failResultConfig);
		
		return resultConfigs;
	}
	
	/**
	 * 构建Json方法的ResultConfig信息
	 * @param methodInfo
	 * @param namespaceName
	 * @param pkgConfig
	 * @return
	 */
	public Map<String, ResultConfig> buildJsonResultConfigs(MethodInfo methodInfo, String namespaceName, PackageConfig.Builder pkgConfig){
		Map<String, ResultConfig> resultConfigs = new HashMap<String, ResultConfig>();
		
		ResultConfig defaultResultConfig = new ResultConfig.Builder(DEFAULT_METHOD_RTN_SUCCESS, JSONResult.class.getName()).build();
		resultConfigs.put(defaultResultConfig.getName(), defaultResultConfig);
		
		return resultConfigs;
	}
    
    /**
     * 参数转换
     * @param parms
     * @return
     */
    public Map<String, String> buildParameterMap(String[] parms) {
        Map<String, String> map = new HashMap<String, String>();
        if(null == parms)return map;
        int subtract = parms.length % 2;
        if (subtract != 0) {
            throw new ConfigurationException("参数设置错误");
        }
        for (int i = 0; i < parms.length; i = i + 2) {
            String key = parms[i];
            String value = parms[i + 1];
            map.put(key, value);
        }
        return map;
    }
    
    
    public ActionConfig getActionConfig(String namespaceName, String methodName) {
    	PackageConfig packageConfig = null;
    	Map<String, ActionConfig> actionConfigs = null;
    	
    	packageConfig = configuration.getPackageConfig(namespaceName);
    	if(packageConfig != null){
    		actionConfigs = packageConfig.getActionConfigs();
    		if(actionConfigs != null){
    			return actionConfigs.get(methodName);
    		}
    	}

    	String actionUrl = namespaceName + "/" + methodName;
    	actionUrl = actionUrl.replaceAll("//", "/");
    	packageConfig = configuration.getPackageConfig(actionUrl);
    	if(packageConfig != null){
    		actionConfigs = packageConfig.getActionConfigs();
    		if(actionConfigs != null){
    			return actionConfigs.get(DEFAULT_METHOD_NAME);
    		}
    	}
    	return null;
    }

	protected class MethodInfo{
		private String methodName;
		private String location;
		private List<InterceptorRef> interceptorRefs;
		private List<com.congxing.core.web.control.annotation.Result> results;
		
		public MethodInfo(String methodName, String location, List<InterceptorRef> interceptorRefs, List<com.congxing.core.web.control.annotation.Result> results){
			this.methodName = methodName;
			this.location = location;
			this.interceptorRefs = interceptorRefs;
			this.results = results;
		}
		
		public MethodInfo(){
		}				
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public List<InterceptorRef> getInterceptorRefs() {
			return interceptorRefs;
		}
		public void setInterceptorRefs(List<InterceptorRef> interceptorRefs) {
			this.interceptorRefs = interceptorRefs;
		}
		public List<com.congxing.core.web.control.annotation.Result> getResults() {
			return results;
		}
		public void setResults(
				List<com.congxing.core.web.control.annotation.Result> results) {
			this.results = results;
		}
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
	}
	
}
