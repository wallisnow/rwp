/**  

* @Title: AccordionXMLBuilder.java

* @Package com.congxing.core.web.tree

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2013-12-30 下午04:19:46

* @version V1.0  

*/

package com.congxing.core.web.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>@Description: </p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2013-12-30<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
public class AccordionXMLBuilder {
	
	/**
	 * @Title: buildXML
	 * @Description: 生成AccordionXML文件类
	 * @param datas 原始数据集
	 * @param id_p 记录值
	 * @param name_p 记录名称
	 * @param p_id_p 父记录标识
	 * @param pk_p 主键标识
	 * @param s_p 特殊属性
	 * @param rootIdValue 根记录标识值
	 * @throws Exception
	 */
	public static String buildXML(List<?> datas, String id_p, String name_p, String p_id_p, String pk_p, String s_p, String rootIdValue) throws Exception{
		StringBuffer xml = new StringBuffer();
		RelationMap<String, String> relationMap = new RelationMap<String, String>();
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		
		for(Object target : datas){
			String id_val = BeanUtils.getProperty(target, id_p);
			String p_id_val = BeanUtils.getProperty(target, p_id_p);
			if(StringUtils.isNotBlank(p_id_val)){
				relationMap.put(id_val, p_id_val);
			}
			cacheMap.put(id_val, target);
		}
		
		List<String> firDatas = relationMap.getKeysByValue(rootIdValue);
		for(String id_val : firDatas){
			
			Object target = cacheMap.get(id_val);
			String target_id_val = BeanUtils.getProperty(target, id_p);
			String target_name_val = BeanUtils.getProperty(target, name_p);
			
			xml.append("<div class='accordionHeader'>");
			xml.append("<h2><span>Folder</span>" + target_name_val + "</h2>");
			xml.append("</div>");
			
			xml.append("<div class='accordionContent'>");
			AccordionXMLBuilder.buildXML(xml, cacheMap, relationMap, id_p, name_p, p_id_p, pk_p, s_p, target_id_val);
			xml.append("</div>");
		}
		
		return xml.toString();
	}
	
	public static void buildXML(StringBuffer xml, Map<String, Object> cacheMap, RelationMap<String, String> relationMap, String id_p, String name_p, String p_id_p, String pk_p, String s_p, String p_id_val) throws Exception{
		
		List<String> childrens = relationMap.getKeysByValue(p_id_val);
		
		if(childrens.size() > 0){
			StringBuffer treeXML = new StringBuffer();
			treeXML.append("<ul>");
			
			for(String id_val : childrens){
				Object target = cacheMap.get(id_val);
				String n_val = BeanUtils.getProperty(target, name_p);
				String pk_val = BeanUtils.getProperty(target, pk_p);
				
				treeXML.append("<li>");
				treeXML.append("<a ");
				treeXML.append("PK='" + pk_val + "' ");
				treeXML.append("ref='REF_" +id_val + "' ");
				treeXML.append("value='" + id_val + "' ");
				
				if(StringUtils.isNotBlank(s_p)){
					String s_val = BeanUtils.getProperty(target, s_p);
					treeXML.append(s_p + "='" + s_val + "' ");
				}
				treeXML.append(">");
				treeXML.append(n_val);
				treeXML.append("</a>");
				
				if(relationMap.values().contains(id_val)){
					AccordionXMLBuilder.buildXML(treeXML, cacheMap, relationMap, id_p, name_p, p_id_p, pk_p, s_p, id_val);
				}
				treeXML.append("</li>");
			}
			treeXML.append("</ul>");
			
			xml.append(treeXML.toString());
		}
	}

}
