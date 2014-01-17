package com.hatc.base.service.impl;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.hatc.base.hibernate.dao.Dao;
import com.hatc.base.service.Manager;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> Spring基础Manager<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class BaseManager implements Manager {

	/** 日志对象 */
	protected Logger log = Logger.getLogger(getClass());
	
	/** 数据访问接口 */
	protected Dao dao = null;

	/** 数据源 */
	protected DataSource dataSource = null;
	
	public BaseManager(){
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	/**
	 * 获取数据库直连对象
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}
}
