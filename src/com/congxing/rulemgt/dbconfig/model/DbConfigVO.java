/**  

* @Title: DbConfigVO.java

* @Package com.congxing.rulemgt.dbconfig.model

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2014-1-24 上午10:58:01

* @version V1.0  

*/

package com.congxing.rulemgt.dbconfig.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
public class DbConfigVO implements Serializable {
	
	private Long dbId;
	
	private String dbName;
	
	private String driverClass;
	
	private String jdbcUrl;
	
	private String user;
	
	private String password;
	
	private String dbDesc;

	public DbConfigVO(Long dbId, String dbName, String driverClass,
			String jdbcUrl, String user, String password, String dbDesc) {
		super();
		this.dbId = dbId;
		this.dbName = dbName;
		this.driverClass = driverClass;
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.dbDesc = dbDesc;
	}

	public DbConfigVO(Long dbId) {
		super();
		this.dbId = dbId;
	}

	public DbConfigVO() {
		super();
	}

	public Long getDbId() {
		return dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbDesc() {
		return dbDesc;
	}

	public void setDbDesc(String dbDesc) {
		this.dbDesc = dbDesc;
	}

	public boolean equals(Object other) {
        if ( !(other instanceof DbConfigVO) ) return false;
        DbConfigVO castOther = (DbConfigVO) other;
        return new EqualsBuilder()
            .append(this.getDbId(), castOther.getDbId())
            .isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDbId())
            .toHashCode();
    }
}
