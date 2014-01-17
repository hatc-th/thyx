package com.hatc.base.service.impl;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.hatc.base.hibernate.dao.Dao;
import com.hatc.base.service.Manager;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> Spring����Manager<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class BaseManager implements Manager {

	/** ��־���� */
	protected Logger log = Logger.getLogger(getClass());
	
	/** ���ݷ��ʽӿ� */
	protected Dao dao = null;

	/** ����Դ */
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
	 * ��ȡ���ݿ�ֱ������
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
