package com.congxing.core.web.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 树结构逻辑：
 * 第一步：查找父节点,当父节点为空采用默认父节点,循环查找当前父节点名没有上级结点为止，认为当前节点为根级父节点
 * 
 * 
 * @author Administrator
 *
 */
public class TreeBuilder {
	
	private static final String defaultRootId = "root";
	
	
	/**
	 * 获取树对象的根节点以及节点初始树结构
	 * @param datas	原始记录集
	 * @param nodeIdProperty	子节点标识属性名称
	 * @param nodeNameProperty	子节点名称属性名称
	 * @param parentNodeIdProperty	父节点标识属性名称
	 * @param pkNameProperty	主键属性名称
	 * @param specialProperty	需要记录的特殊属性
	 * @throws Exception
	 */
	public static StringBuffer buildTreeXML(List<?> datas, String nodeIdProperty, String nodeNameProperty, String parentNodeIdProperty, String pkNameProperty, String specialProperty) throws Exception{
		Map<Object, List<Object>> parentChildMap = new HashMap<Object, List<Object>>();
		Map<Object, Object> parentNodeIdMap = new HashMap<Object, Object>();
		Map<Object, Object> childNodeIdMap = new HashMap<Object, Object>();
		for(Iterator<?> it = datas.iterator(); it.hasNext(); ){
			Object target = it.next();
			Object nodeId = BeanUtils.getProperty(target, nodeIdProperty);
			Object parentNodeId = BeanUtils.getProperty(target, parentNodeIdProperty);
			if(null == parentNodeId || StringUtils.isBlank(String.valueOf(parentNodeId))){
				//父节点为空默认为父节点
				parentNodeId = defaultRootId;
			}
			
			if(parentChildMap.containsKey(parentNodeId)){
				parentChildMap.get(parentNodeId).add(target);
			}else{
				List<Object> childList = new ArrayList<Object>();
				childList.add(target);
				parentChildMap.put(parentNodeId, childList);
			}
			parentNodeIdMap.put(parentNodeId, parentNodeId);
			childNodeIdMap.put(nodeId, nodeId);
		}
		for(Map.Entry<Object, Object> entry : childNodeIdMap.entrySet()){
			Object key = entry.getKey();
			if(parentNodeIdMap.containsKey(key)){
				parentNodeIdMap.remove(key);
			}
		}
		List<Object> rootIdList = new ArrayList<Object>();
		rootIdList.addAll(parentNodeIdMap.values());
		String refPrefix = "treePage";
		
		return buildTreeXML(rootIdList, parentChildMap, nodeIdProperty, nodeNameProperty, pkNameProperty, specialProperty, refPrefix);
		
	}
	
	/**
	 * parentChildMap为二层结构
	 * @param rootList
	 * @param parentChildMap
	 * @param nodeIdProperty
	 * @param nodeNameProperty
	 * @param pkNameProperty
	 * @param specialProperty
	 * @param refPrefix 引用页标识
	 * @return
	 * @throws Exception
	 */
	private static StringBuffer buildTreeXML(List<Object> rootList, Map<Object, List<Object>> parentChildMap, String nodeIdProperty, String nodeNameProperty, String pkNameProperty, String specialProperty, String refPrefix) throws Exception {
		StringBuffer treeXML = new StringBuffer();
		treeXML.append("<ul oncheck='oncheck'>");
		for(Object rootId : rootList){
			List<Object> childList = parentChildMap.get(rootId);
			buildTreeXML(treeXML, childList, parentChildMap, nodeIdProperty, nodeNameProperty, pkNameProperty, specialProperty, refPrefix);
		}
		treeXML.append("</ul>");
		return treeXML;
	}
	/**
	 * 先打印根节点属性
	 * @param treeXML
	 * @param treeNode
	 * @param parentChildMap
	 * @param nodeIdProperty
	 * @param nodeNameProperty
	 * @param refPrefix
	 * @throws Exception
	 */
	private static void buildTreeXML(StringBuffer treeXML, List<Object> childList, Map<Object, List<Object>> parentChildMap, String nodeIdProperty, String nodeNameProperty, String pkNameProperty, String specialProperty, String refPrefix) throws Exception {
		for(Object target : childList){
			Object targetId = BeanUtils.getProperty(target, nodeIdProperty);
			Object targetName = BeanUtils.getProperty(target, nodeNameProperty);
			
			treeXML.append("<li>");
			treeXML.append("<a ");
			if(StringUtils.isNotBlank(specialProperty)){
				treeXML.append(specialProperty + "='" + BeanUtils.getProperty(target, specialProperty) + "'");
			}
			if(StringUtils.isNotBlank(pkNameProperty)){
				treeXML.append(" PK='" + BeanUtils.getProperty(target, pkNameProperty) + "'");
			}
			treeXML.append(" ref='" + refPrefix + "_" + targetId + "'");
			treeXML.append(" name='" + targetName + "'");
			treeXML.append(" value='" + targetId + "'");
			treeXML.append(">");
			treeXML.append(targetName);
			treeXML.append("</a>");
			
			if(parentChildMap.containsKey(targetId)){
				List<Object> clist = parentChildMap.get(targetId);
				treeXML.append("<ul>");
				buildTreeXML(treeXML, clist, parentChildMap, nodeIdProperty, nodeNameProperty, pkNameProperty, specialProperty, refPrefix);
				treeXML.append("</ul>");
			}
			treeXML.append("</li>");
		}
	}
}
