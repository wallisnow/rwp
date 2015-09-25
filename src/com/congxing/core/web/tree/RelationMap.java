/**  

* @Title: RelationMap.java

* @Package com.congxing.core.web.tree

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2013-12-30 下午05:17:04

* @version V1.0  

*/

package com.congxing.core.web.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@SuppressWarnings("serial")
public class RelationMap<E, V> extends LinkedHashMap<E, V>{
	
	public List<E> getKeysByValue(V val){
		List<E> datas = new ArrayList<E>();
		for(Map.Entry<E, V> entry :  this.entrySet()){
			E this_k = entry.getKey();
			V this_v = entry.getValue();
			if(this_v.equals(val)){
				datas.add(this_k);
			}
		}
		return datas;
	}

}
