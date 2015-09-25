package com.congxing.core.web.translate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.congxing.core.utils.ConvertUtils;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.system.syscode.model.SysCodeVO;
import com.congxing.system.syscode.service.SysCodeService;

@Component
@SuppressWarnings("serial")
public class TranslateUtils implements Serializable {
	
	static Logger logger = Logger.getLogger(TranslateUtils.class);

	public static final String SYSCODE_PREFIX = "$"; // 调用SYSCODE的翻译
	
	public static final String REFLECT_PREFIX = "!"; // 以java类反射机制取翻译类
	
	public static final String DATE_PREFIX = "#"; //日期类型


	public static final String SEPARATOR = "*";
	
	public static final String BRACES = "{";
	
	private SysCodeService sysCodeService;
	
	public SysCodeService getSysCodeService() {
		return sysCodeService;
	}
	
	@Autowired @Qualifier("sysCodeServiceImpl")
	public void setSysCodeService(SysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	
	/**
	 * 页面翻译使用(无附加参数时使用)
	 * <pre>
	 * <strong>注意：当翻译值不存在时默认返回原始值</strong>
	 * </pre>
	 * @param definition
	 * @param value
	 * @param params
	 * @return
	 */
	public String getName(String definition, Object value){
		return this.getName(definition, value, null, true);
	}
	
	/**
	 * 页面翻译使用(有附加参数时使用)
	 * <pre>
	 * <strong>注意：当翻译值不存在时默认返回原始值</strong>
	 * </pre>
	 * @param definition
	 * @param value
	 * @param params
	 * @return
	 */
	public String getName(String definition, Object value, Map<String, Object> params){
		return this.getName(definition, value, params, true);
	}
	
	/**
	 * 通过definition进行翻译取值
	 * @param definition 翻译类型
	 * @param value	待翻译值
	 * @param params 附加参数
	 * @param isReturnOrginValue 是否返回原始值,
	 * isReturnOrginValue = true 	代表当翻译无值时,返回原值
	 * isReturnOrginValue = false	代表当翻译无值时,返回""
	 * @return String
	 * @throws
	 */
	public String getName(String definition, Object value, Map<String, Object> params, boolean isReturnOrginValue){
		if(null == value || "".equals(value))return "";
		try {
			if(StringUtils.isBlank(definition)){
				if(isReturnOrginValue){
					return value.toString().trim();
				}else{
					return "";
				}
			}
			definition = definition.trim();
			if (definition.startsWith(SYSCODE_PREFIX)){
				/**
				 * $DISCMAN
				 * $DISCMAN*kind*kindname
				 */
				String transDef = definition.substring(SYSCODE_PREFIX.length());
				String type = transDef;
				String propertyName = null;
				if (transDef.indexOf(SEPARATOR) > 0) {
					type = transDef.substring(0, transDef.indexOf(SEPARATOR));
					propertyName = transDef.substring(transDef.indexOf(SEPARATOR) + 1);
					String code = "";
					String name = "";
					if(propertyName.indexOf(SEPARATOR) > 0){
						code = propertyName.substring(0, propertyName.indexOf(SEPARATOR));
						name = propertyName.substring(propertyName.indexOf(SEPARATOR) + 1);
						return this.getSyscodeName(type, (String)value, code, name, params, isReturnOrginValue);
					}else{
						throw new Exception("标签配置有误[" + definition + "]");
					}
				}else{
					return this.getSyscodeName(type, String.valueOf(value), "kind", "kindname", params, isReturnOrginValue);
				}
			}else if(definition.startsWith(REFLECT_PREFIX)){
				/**
				 * !com.maywide.system.user.model.UserVO*userId*userName
				 */
				String descInfo = definition.substring(REFLECT_PREFIX.length());
				
				if (descInfo.indexOf(SEPARATOR) > 0) {
					String className = descInfo.substring(0, descInfo.indexOf(SEPARATOR));
					String propertyCluster = descInfo.substring(descInfo.indexOf(SEPARATOR) + 1);
					if(propertyCluster.indexOf(SEPARATOR) > 0){
						String propertyCode = propertyCluster.substring(0, propertyCluster.indexOf(SEPARATOR));
						String propertyName = propertyCluster.substring(propertyCluster.indexOf(SEPARATOR) + 1);
						return this.getReflectName(className, String.valueOf(value), propertyCode, propertyName, params, isReturnOrginValue);
					}else{
						throw new Exception("标签配置有误[" + definition + "]");
					}
				}else{
					throw new Exception("标签配置有误[" + definition + "]");
				}
			} else if(definition.startsWith(DATE_PREFIX)){
				try{
					String format = definition.substring(DATE_PREFIX.length());
					return DateFormatFactory.getInstance(format).format(ConvertUtils.convert(value, java.util.Date.class));
				}catch(Exception ex){
					return value.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isReturnOrginValue){
			return value.toString();
		}else{
			return "";
		}
	}
	
	public List<TranslateVO> getCollection(String definition) {
		return this.getCollection(definition, null);
	}
	
	/**
	 * 获取页面列表信息
	 * @param @param definition
	 * @param @param listKey
	 * @param @param listValue
	 * @param @param needSpace
	 * @return List<TranslateVO>
	 * @throws
	 */
	public List<TranslateVO> getCollection(String definition, Map<String, Object> params) {
		if (StringUtils.isBlank(definition)){
			return new ArrayList<TranslateVO>();
		}
		try {
			if (definition.startsWith(SYSCODE_PREFIX)){
				/**
				 * 调用SYSCODE的列表生成方式
				 * $DISCMAN
				 * $DISCMAN*data1*dataname1
				 */
				String transDef = definition.substring(SYSCODE_PREFIX.length());
				String type = transDef;				
				if (transDef.indexOf(SEPARATOR) > 0) {
					type = transDef.substring(0, transDef.indexOf(SEPARATOR));
					String propertyName = transDef.substring(transDef.indexOf(SEPARATOR) + 1);
					String listKey = "";
					String listValue = "";
					if(propertyName.indexOf(SEPARATOR) > 0){
						listKey = propertyName.substring(0, propertyName.indexOf(SEPARATOR));
						listValue = propertyName.substring(propertyName.indexOf(SEPARATOR) + 1);
						return this.getSyscodeTranslateCollection(type, listKey, listValue, params);
					}
				}else{
					return this.getSyscodeTranslateCollection(type, "kind", "kindname", params);
				}
			}else if(definition.startsWith(REFLECT_PREFIX)){
				/**
				 * 反射机制进行列表生成,反射机制要求写全标签信息
				 * !com.congxing.xx.xxVO*code*name
				 */
				String descInfo = definition.substring(SYSCODE_PREFIX.length());
				String []array = StringUtils.split(descInfo, SEPARATOR);
				if(array.length >= 3){
					String className = array[0];
					String code = array[1];
					String name = array[2];
					return this.getRelectTranslateCollection(className, code, name, params);
				}else{
					throw new Exception("标签配置有误[" + definition + "]");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new ArrayList<TranslateVO>();
	}
	
	
	
	public List<TranslateVO> getSyscodeTranslateCollection(String type, String listKey, String listValue, Map<String, Object>properties) throws Exception{
		List<SysCodeVO> list = this.getSysCodeVOCollectionByType(type, properties);
		return this.getTranslateVOList(list, listKey, listValue);
	}
	
	public List<SysCodeVO> getSysCodeVOCollectionByType(String type, Map<String, Object>properties) throws Exception{
		return this.getSysCodeService().doFindSysCodeVOByType(type, properties);
	}
	
	public List<TranslateVO> getRelectTranslateCollection(String className, String listKey, String listValue, Map<String, Object>properties) throws Exception{
		List<?> list = this.getReflectVOCollection(className, properties);
		return this.getTranslateVOList(list, listKey, listValue);
	}
	

	public List<?> getReflectVOCollection(String className, Map<String, Object>properties) throws Exception{
		Class<?> entityClass = Class.forName(className);
		return this.getSysCodeService().doFindByProperties(entityClass, properties);
	}
	

	public List<TranslateVO> getTranslateVOList(List<?> targetList, String listKey, String listValue) throws Exception{
		List<TranslateVO> list = new ArrayList<TranslateVO>();
		if(null != targetList && targetList.size() > 0){
			for (Iterator<?> iter = targetList.iterator(); iter.hasNext();) {
				Object targetVO = iter.next();
				TranslateVO translateVO = new TranslateVO();
				translateVO.setCode(BeanUtils.getProperty(targetVO, listKey));
				translateVO.setName(BeanUtils.getProperty(targetVO, listValue));
				list.add(translateVO);
			}
		}
		return list;
	}

	
	/**
	 * 字典表翻译方法
	 * @param type					字典表type类型值
	 * @param value					页面传入的参数值
	 * @param propertyCode			页面传入参数值对应属性名
	 * @param propertyName			需要转换后的属性名
	 * @param params				附加参数
	 * @param isReturnOrginValue	当转换值为空时是否返回原值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getSyscodeName(String type, String value, String propertyCode, String propertyName, Map<String, Object> params, boolean isReturnOrginValue) throws Exception{
		SysCodeVO syscodeVO = new SysCodeVO();
		syscodeVO.setType(type);
		BeanUtils.setProperty(syscodeVO, propertyCode, value);
		if(null != params && params.size() > 0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				BeanUtils.setProperty(syscodeVO, entry.getKey(), entry.getValue());
			}
		}
		
		List list = this.getSysCodeService().doFindByExampleVO(SysCodeVO.class, syscodeVO);
		if(null != list && list.size() > 0){
			syscodeVO = (SysCodeVO) list.get(0);
			if(StringUtils.isNotBlank(propertyName)){
				return BeanUtils.getProperty(syscodeVO, propertyName.trim());
			}else{
				return syscodeVO.getKindname();
			}
		}
		if(isReturnOrginValue){
			return value.toString();
		}else{
			return "";
		}
	}
	
	
	/**
	 * 反射翻译方法
	 * @param className				反射VO名称
	 * @param value					页面传入值
	 * @param propertyCode			页面传入值对应的属性名
	 * @param propertyName			需要转换后的属性名
	 * @param params				附加参数
	 * @param isReturnOrginValue	当转换值为空时是否返回原值
	 * @return
	 * @throws Exception
	 */
	public String getReflectName(String className, String value, String propertyCode, String propertyName, Map<String, Object> params, boolean isReturnOrginValue) throws Exception{
		if (value == null ) return "";
		Class<?> reflectClass = Class.forName(className);
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(propertyCode, value);
		
		if(null != params && params.size() > 0){
			properties.putAll(params);
		}
		
		List<?> list = this.getSysCodeService().doFindByProperties(reflectClass, properties);
		
		if(null != list && list.size() > 0){
			Object example = list.get(0);
			if(StringUtils.isNotBlank(propertyName)){
				return BeanUtils.getProperty(example, propertyName.trim());
			}
		}
		if(isReturnOrginValue){
			return value.toString();
		}else{
			return "";
		}
	}

	
	/**
	 * 翻译类型(提供批量导入模块使用)
	 * 在后台批量导入模块中，如果以字典表进行翻译, 使用的是逆向翻译
	 * @param value
	 * @param definition
	 * @return
	 * @throws Exception 
	 */
	public String format(String definition, String value) throws Exception{
		return this.format(definition, value, false);
	}
	
	public String format(String definition, String value, boolean isReturnOrginValue) throws Exception{
		if(definition.startsWith(SYSCODE_PREFIX) && definition.indexOf(SEPARATOR) < 0){
			definition = definition + "*kindname*kind";
		}
		return this.getName(definition, value, null, isReturnOrginValue);
	}
	
}
