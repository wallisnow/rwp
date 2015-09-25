/**  

* @Title: DbConfigListVO.java

* @Package com.congxing.rulemgt.dbconfig.model

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2014-1-24 上午11:05:27

* @version V1.0  

*/

package com.congxing.rulemgt.dbconfig.model;

import com.congxing.core.web.struts2.BaseListVO;

/**
 * <p>@Description: </p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2014-1-24<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
@SuppressWarnings("serial")
public class DbConfigListVO extends BaseListVO {
	
	private String _sk_dbName;

	public String get_sk_dbName() {
		return _sk_dbName;
	}

	public void set_sk_dbName(String skDbName) {
		_sk_dbName = skDbName;
	}

}
