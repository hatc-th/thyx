package com.hatc.base.hibernate.dao.hibernate;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.sql.BLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.RollPage;
import com.hatc.base.hibernate.dao.Dao;
import com.hatc.base.hibernate.util.Aggregate;
import com.hatc.base.hibernate.util.Column;
import com.hatc.base.hibernate.util.Condition;
import com.hatc.base.hibernate.util.Group;
import com.hatc.base.hibernate.util.Operator;
import com.hatc.base.hibernate.util.Parameter;
import com.hatc.base.utils.BeanUtil;
import com.hatc.common.web.config.UserResourceConfig;
/**
 * 
 * <b>system：</b> 通航运行管理与服务保障系统<br/> <b>description：</b> 数据库统一访问接口实现类<br/>
 * 
 */


public class BaseDaoHibernate extends HibernateDaoSupport implements Dao 

{
    
	protected final Log log = LogFactory.getLog(getClass());
	

	// #直连数据库的对象
	private DataSource dataSource = null;
	private LobHandler lobHandler = null;
	  
	  
	/******新建对象ID的相关方法***********************************************/
	/**
	 * 检查新建指定对象唯一ID是否已经存在（对象ID）
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @return boolean 是/否
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public boolean getObjectUnique(Class clazz, Serializable id)
			throws BaseException {
		try {
			Object o = getHibernateTemplate().get(clazz, id);
			return o == null ? false : true;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}
 
	/******查询单个对象的方法********/
	/**
	 * 根据对象ID查询单个指定对象 
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @return Object 对象
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id) throws BaseException {
		try {
			Object o = getHibernateTemplate().get(clazz, id);
			return o;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		 
		}
	}

 
	/**
	 * 根据指定Class查询全部结果集	 * 
	 * @param clazz
	 *            对象Class
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Class clazz) throws BaseException {
		try {
			return getHibernateTemplate().loadAll(clazz);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}

 
	/**
	 * 根据条件对象查询分页指定列的字符串结果集
	 * 
	 * @param rollPage
	 *            翻页参数
	 * @param condition
	 *            条件集合
	 * @return List 字符串结果集
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, Condition condition)
			throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			
			List<Group> groupList = condition.getGroup();
			if (groupList.size() < 1) {
				// 解析参数
				Criteria crt = parseCriteriaCondition(condition, session);
				// 解析参数
				Criteria crtList = parseCriteriaCondition(condition, session);
				// 设置翻页
				settingRollPage(rollPage, crt);
				// 设置查找记录的起始位置
				crtList.setFirstResult(rollPage.getPageFirst());
				// 设置查找记录的最大条数
				crtList.setMaxResults(rollPage.getPagePer());
				List list = crtList.list();
				if (list != null) {
					rollPage.setCurrentlyPagePer(list.size());
				}
				return list != null && list.size() > 0 ? list : null;
			} else {
				// 解析参数
				Query query = parseQueryCondition(condition, session);
				// 设置翻页
				settingRollPage(rollPage, query);
				
				List list = query.list();

				if (list != null) {
					rollPage.setCurrentlyPagePer(list.size());
				}
				return list != null && list.size() > 0 ? list : null;
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
 
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_ROLLPAGE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		} 
	}


	/**
	 * 执行hql查询返回分页结果集（hql语句）
	 * 
	 * @param rollPage 分页参数
	 * @param hql      hql语句
	 * @return BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql) throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query crt = session.createQuery(hql);
			// 翻页设置
			settingRollPage(rollPage, crt);
			List list = crt.list();
			if (list != null) {
				rollPage.setCurrentlyPagePer(list.size());
			}
			return list != null && list.size() > 0 ? list : null;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException e = new BaseException(UserResourceConfig.getInstance().getValue("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION"));
			e.setRootCause(ex);
			throw e;
		}
	}


	/**
	 * 执行带参数hql查询并返回分页结果集（hql语句）
	 * (此方法中的hql必须是带问号参数形式的，如：from tb_a ta where ta.id=?)
	 * @param rollPage 分页对象
	 * @param hql 待执行的带参数hql语句
	 * @param params hql语句中"?"的参数List
	 * @return List 对象结果集
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql, List params)
			throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query crt = session.createQuery(hql);
			// 设置hql参数
			if (null != params && params.size() != 0) {
				for (int i = 0, j = params.size(); i < j; i++) {
					crt.setParameter(i, params.get(i));
				}
			}
			// 翻页设置
			settingRollPage(rollPage, crt);
			List list = crt.list();
			if (list != null && null != rollPage) {
				rollPage.setCurrentlyPagePer(list.size());
			}
			return list != null && list.size() > 0 ? list : null;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		} 
	}
	

	/**
	 * 执行hql查询返回全部结果集（hql语句）
	 * 2012-1-7 采用 Hibernate 管理的事务. 
	 *     复制试券: 如果采用 spring 管理的事务,会出现问题....
	 * @param hql
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql) throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query crt = session.createQuery(hql);
			List list = crt.list();
			return list != null && list.size() > 0 ? list : null;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}
	
 
	/**
	 * 执行带参数hql查询返回指定列的全部结果集（hql语句）
	 * (此方法中的hql必须是带问号参数形式的，如：from tb_a ta where ta.id=?)
	 * @param hql待执行的hql语句
	 * @param params hql语句中"?"的参数List
	 * @return 实体集合
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql, List params) throws BaseException {
		return this.getObjects(null, hql, params);
	}

 
	/******查询分页结果集的多个方法**************************************/
	/**
	 * 执行SQL查询分页结果集(SQL语句)
	 *  说明: 不建议使用. 采用 datasource 获取连接. 存在事务处理问题.
	 * @param rollPage
	 *            翻页参数
	 * @param sql
	 *            SQL语句
	 * @param column
	 *            列名
	 * @return List
	 * @throws BaseException
	 */
	
	public List<String[]> getObjects(RollPage rollPage, String sql,
			String[] column) throws BaseException {
		List<String[]> list = new ArrayList<String[]>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 获取连接
			conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
			// TYPE_SCROLL_SENSITIVE 可滚动并且通常受其他的更改影响的 ResultSet 对象的类型
			// CONCUR_READ_ONLY 不可以更新的 ResultSet 对象的并发模式
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = settingRollPage(rollPage, sql, conn, stmt);
			// 获取 ResultSet 对象的列的编号、类型和属性
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] tempList = null;
			int i;
			int type = 0;
			DateFormat df = null;
			Date date = null;
			Timestamp time = null;
			while (rs.next()) {
				tempList = new String[column.length];
				for (i = 0; i < column.length; i++) {
					type = rsmd.getColumnType(i + 1);
					if (type == 91) {
						// for Date
						df = new SimpleDateFormat("yyyy-MM-dd");
						date = rs.getDate(column[i]);
						if (date != null) {
							tempList[i] = df.format(date);
						}
					} else if (type == 93) {
						// for TimeStamp
						df = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						time = rs.getTimestamp(column[i]);
						if (time != null) {
							tempList[i] = df.format(time);
						}
					} else {
						tempList[i] = rs.getString(column[i]);
					}
				}
				if (tempList != null) {
					list.add(tempList);
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}finally{
			if (list != null) {
				rollPage.setCurrentlyPagePer(list.size());
			}
			if(rs!=null)
			{
				try{
					rs.close();
				}catch(Exception e){
					
				}
			}
			if(stmt!=null)
			{
				try{
					stmt.close();
				}catch(Exception e){
				}
			}
		}
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据条件对象查询全部结果集
	 * 2012-1-7 采用SPRING 管理的事务, 支持高并发. 
	 * @param condition
	 *            条件对象
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Condition condition) throws BaseException {
		List<Group> groupList = condition.getGroup();
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();  
			if (groupList.size() < 1) {
				// 解析参数
				Criteria crt = parseCriteriaCondition(condition, session);
				List list = crt.list();
				return list != null && list.size() > 0 ? list : null;
			} else {
				// 解析参数
				Query query = parseQueryCondition(condition, session);
				List list = query.list();
				return list != null && list.size() > 0 ? list : null;
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_ROLLPAGE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
			
		}
	}
 
	/**
	 * 执行SQL语句查询全部指定列的结果集
	 * @param sql
	 *            SQL语句
	 * @param column
	 *            列名
	 * @return List 结果集
	 * @throws BaseException
	 */
	
	public List<String[]> getObjects(String sql, String[] column)
			throws BaseException {
		List<String[]> list = new ArrayList<String[]>();
		@SuppressWarnings("unused")
		int xx = 0;
		String colName = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 获取连接
			conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = settingRollPage(null, sql, conn, stmt);
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] tempList = null;
			int i;
			int type ;
			while (rs.next()) {
				tempList = new String[column.length];
				for (i = 0; i < column.length; i++) {
					type = rsmd.getColumnType(i + 1);
					tempList[i] = this.conversionString(type, rs, column[i]);
				}
				if (tempList != null) {
					list.add(tempList);
				}
			}
		} catch (Exception ex) {
			String errMessage = " column size: ";
			int columnSize = 0;
			if(column!=null)
				columnSize = column.length;
			int i;
			errMessage +=  columnSize;
			for(i=0;i<columnSize;i++)
			{
				errMessage += " " + i  + " : "  + column[i] + " , ";
			}
			
			log.error("DB.ERROR.COLUMN NAME:" + colName + ",SQL: " + sql + errMessage);
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}finally{
			if(rs!=null)
			{
				try{
					rs.close();
				}catch(Exception e){
					
				}
			}
			if(stmt!=null)
			{
				try{
					stmt.close();
				}catch(Exception e){
				}
			}
		}
		return list.size() > 0 ? list : null;

	}

 
	/**
	 * 取得ORACLE序列（Long）
	 * 说明：不建议使用，建议通过新的方法实现。
	 * @param seq
	 *            oracle序列名称
	 * @return Long 序列值
	 * @throws BaseException
	 */
	
	public String getOracleSeq(String seq) throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String id = (String) session.createSQLQuery(
					"SELECT " + seq + ".Nextval as s FROM DUAL").addScalar("s",
					Hibernate.STRING).uniqueResult();
			return id;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_SEQ");
			be.setRootCause(ex);
			throw be;
		}
	}

	/** 
	 * 说明：目前未使用
	 * @return
	 */
	public LobHandler getLobHandler() {
		return lobHandler;
	}

 
	/**
	 * 删除单个对象
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id) throws BaseException {
		try {
			getHibernateTemplate().delete(getObject(clazz, id));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_DELETE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}


	/**
	 * 根据数组批量删除对象
	 * 
	 * @param clazz
	 *            对象Class
	 * @param id  
	 *            待删除对象ID数组
	 * @throws BaseException
	 * 
	 * 说明：为支持业务层统一管理事务，使用统一的currentSession,此方法保留暂不使用,留作测试.--update by jicheng
	 */
	@SuppressWarnings("unchecked")
	public void removeBatchObject(Class clazz, Serializable[] id)
			throws BaseException {
		Session currentSession = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		try {
			for (int i = 0; i < id.length; i++) {
				currentSession.delete(getObject(clazz, id[i]));
				// 当记录数达到20条时，将数据写入数据库，并清理内存
				if (i != 0 && i + 1 % 20 == 0) {
					currentSession.flush();
					currentSession.clear();
				}
			}

			currentSession.flush();
			currentSession.clear();
 
			
		} catch (Exception ex) {
			 
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_BATCH_DELETE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}

 
	/**
	 * 保存单个对象

	 * @param o
	 *            对象
	 * @throws BaseException
	 */
	public void saveObject(Object o) throws BaseException {
		try {
			getHibernateTemplate().save(o);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			//TODO ningliyu 2011-12-15
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_SAVE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}

	/**设置数据源
	 * 说明：不建议使用
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 目前未使用
	 * @param lobHandler
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

 
	
	/**
	   * 调用Oracle中的函数
	   * 说明：不建议使用
	   * @param functionName 待执行的函数名称
	   * @param funParams 函数中参数的值(需要和函数中的参数顺序相同)
	   * @param returnValueType 函数的返回值类型
	   *        例如：java.sql.Types.VARCHAR、java.sql.Types.DATE等(如果无返回值可以传入java.sql.Types.NULL)
	   * @return 函数返回值List
	   * @throws BaseException
	   */
	@SuppressWarnings("unchecked")
	public List transferOracleFun(String functionName, List funParams, int returnValueType) throws BaseException {
		StringBuffer param = new StringBuffer("");
		String temp = "";
		// 循环设置转义语句参数
		if(null != funParams && funParams.size() != 0){
			param.append("(");
			for(int i = 0, j = funParams.size(); i < j; i++){
				param.append("?,");
			}
			temp = param.substring(0, param.length() - 1);
			temp += ")";
		}
		// 定义转义字符串
		StringBuffer transferString = new StringBuffer("");
		CallableStatement cs = null;
		List result = new ArrayList();
		if(returnValueType != Types.NULL){
			transferString.append("{?= call ").append(functionName).append(temp).append("}") ;
		}else{
			transferString.append("{call ").append(functionName).append(temp).append("}") ;
		}
		
		Connection con = null;
		try {
			con = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
			cs = con.prepareCall(transferString.toString());
			if(returnValueType != Types.NULL){
				// 注册返回值类型
				cs.registerOutParameter(1, returnValueType);
				// 设置参数
				if(null != funParams && funParams.size() != 0){
					for(int i = 0, j = funParams.size(); i < j; i++){
						// 获取当前参数类型名称
						String typeName = funParams.get(i).getClass().getName();
						if(typeName.equals("java.lang.Integer")){
							cs.setInt(i + 2, Integer.parseInt(funParams.get(i).toString()));
						}else if(typeName.equals("java.sql.Date")){
							cs.setDate(i + 2, (java.sql.Date)funParams.get(i));
						}else if(typeName.equals("java.util.Date")){
							cs.setDate(i + 2, new java.sql.Date(((java.util.Date)funParams.get(i)).getTime()));
						}else if(typeName.equals("java.lang.Double")){
							cs.setDouble(i + 2, (java.lang.Double)funParams.get(i));
						}else if(typeName.equals("java.lang.Float")){
							cs.setFloat(i + 2, (java.lang.Float)funParams.get(i));
						}else if(typeName.equals("java.sql.Timestamp")){
							cs.setDate(i + 2, new java.sql.Date(((java.sql.Timestamp)funParams.get(i)).getTime()));
						}else{
							cs.setString(i + 2, funParams.get(i).toString());
						}
					}
				}
			}else{
				// 设置参数
				if(null != funParams && funParams.size() != 0){
					for(int i = 0, j = funParams.size(); i < j; i++){
						// 获取当前参数类型名称
						String typeName = funParams.get(i).getClass().getName();
						if(typeName.equals("java.lang.Integer")){
							cs.setInt(i + 1, Integer.parseInt(funParams.get(i).toString()));
						}else if(typeName.equals("java.sql.Date")){
							cs.setDate(i + 1, (java.sql.Date)funParams.get(i));
						}else if(typeName.equals("java.util.Date")){
							cs.setDate(i + 1, new java.sql.Date(((java.util.Date)funParams.get(i)).getTime()));
						}else if(typeName.equals("java.lang.Double")){
							cs.setDouble(i + 1, (java.lang.Double)funParams.get(i));
						}else if(typeName.equals("java.lang.Float")){
							cs.setFloat(i + 1, (java.lang.Float)funParams.get(i));
						}else if(typeName.equals("java.sql.Timestamp")){
							cs.setDate(i + 1, new java.sql.Date(((java.sql.Timestamp)funParams.get(i)).getTime()));
						}else{
							cs.setString(i + 1, funParams.get(i).toString());
						}
					}
				}
			}
			cs.execute();
			result.add(cs.getObject(1));
		} catch (SQLException e) {
			logger.error(e);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_PROCEDURES_EXCEPTION");
			be.setRootCause(e);
			throw be;
			
		}catch(Exception e){
			logger.error(e);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_PROCEDURES_EXCEPTION");
			be.setRootCause(e);
			throw be;
			
		}finally{
			
			if(cs!=null)
			{
				
				try{
					cs.close();
				}catch(Exception e){
					
				}
			}
		}
		return result;
	}

 
	/**
	 * 更新单个对象（不同名的相同方法）
	 * @param o
	 *            对象
	 * @throws BaseException
	 */
	public void updateObject(Object o) throws BaseException {
		 
		try {
			getHibernateTemplate().update(o);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_UPDATE_EXCEPTION");
			be.setRootCause(e);
			throw be;
		}  
	}
 
	

	/**
	 * 执行oracle存储过程
	 * 说明：这个方法JD 没有使用 。  需要考虑是否修改实现
	 *  * @param sql
	 *            存储过程sql语句？？？？？？？？
	 *  JD 未使用,HFX 使用.          
	 * @throws BaseException
	 */
	public void updateProced(String sql) throws BaseException {
		java.sql.CallableStatement cstmt = null;
		java.sql.Connection con = null;
		try {
			 
			con = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
			cstmt = con.prepareCall(sql);
			cstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_PROCEDURES_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}finally{
			if(cstmt!=null)
			{
				try{
					cstmt.close();
				}catch(Exception e){
					
				}
			}
			
		}
	}


	/**
	 * 用Criteria实现翻页(Hql)
	 * 
	 * @param rollPage
	 *            翻页类
	 * @param Criteria
	 */
	@SuppressWarnings("unchecked")
	protected void settingRollPage(RollPage rollPage, Criteria crt) {
		// 翻页设置
		if (rollPage != null) {
			Criteria crtCount = crt;
			crtCount.setProjection(Projections.rowCount());
			List list = crtCount.list();
			// 设置记录总数
			rollPage.setRowCount((Integer) list.get(0));
			// 初始化
			rollPage.init();
		}
	}

	/**
	 * 用Query实现翻页(Hql)
	 * 
	 * @param rollPage
	 *            翻页类
	 * @param Query
	 */
	@SuppressWarnings("unchecked")
	protected void settingRollPage(RollPage rollPage, Query crt) {
		// 翻页设置
		if (rollPage != null) {
			List list = crt.list();
			// 设置记录总数
			rollPage.setRowCount(list.size());
			// 初始化
			rollPage.init();
			// 设置查找记录的起始位置
			crt.setFirstResult(rollPage.getPageFirst());
			// 设置查找记录的最大条数
			crt.setMaxResults(rollPage.getPagePer());
		}
	}

	/**
	 * 用java.sql实现翻页(sql)
	 * 
	 * @param rollPage
	 *            翻页类
	 * @param sql
	 *            查询语句
	 */
	protected ResultSet settingRollPage(RollPage rollPage, String sql,
			Connection conn, Statement stat) throws BaseException {
		try {
			ResultSet res = stat.executeQuery(sql);
			if (rollPage != null) {
				int i = 0;
				while (res.next()) {
					i++;
				}
				// 设置总页数
				rollPage.setRowCount(i);
				// 初始化
				rollPage.init();
				// 设置翻页
				String hsql = getRollPageString(rollPage, sql);

				res = stat.executeQuery(hsql);
			} else {
			}
			return res;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_QUERY_ROLLPAGE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}

	/**
	 * clob类型转换成string
	 * 
	 * @param typeCode类型代码
	 * @param rs数据集
	 * @param colName对应于数据库中的列名
	 * @return 对应字符串
	 * @throws Exception
	 */
	private String conversionString(int typeCode, ResultSet rs, String colName)
			throws Exception {
		DateFormat df;
		switch (typeCode) {
		case Types.CLOB:
			Clob clob = rs.getClob(colName);
			return clob == null ? "" : clob
					.getSubString(1, (int) clob.length());
		case Types.DATE:
			df = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp timestamp = rs.getTimestamp(colName);
			String objTime = (null == rs.getTime(colName) ? "00:00:00" : rs
					.getTime(colName).toString());
			String afterFormat = "";
			// 这样做是因为数据库中的Date类型的数据的格式不统一,有yyyy-MM-dd和yyyy-MM-dd hh:mm:ss。
			if (objTime.equals("00:00:00")) {
				afterFormat = (null == timestamp ? "" : df.format(timestamp));
			} else {
				afterFormat = (null == timestamp ? "" : new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(timestamp));
			}
			return afterFormat;
		case Types.TIMESTAMP:
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp time = rs.getTimestamp(colName);
			return time == null ? "" : df.format(time);
		default:
			return rs.getString(colName);
		}
	}

	/**
	 * 解析查询条件
	 * 
	 * @param par
	 *            条件参数
	 * 
	 * @return Criteria
	 */
	private Criterion getCriterion(Parameter par) {
		String column = par.getColumn();
		int oper = par.getOperator();
		Object[] objArray = par.getValue();

		switch (oper) {
		// 等于 =
		case Operator.SQL_EQ:
			if (objArray.length > 1) {
				return Restrictions.in(column, objArray);
			} else if (objArray.length == 1) {
				return Restrictions.eq(column, objArray[0]);
			}
			// 不等于 <>
		case Operator.SQL_NE:
			if (objArray.length > 1) {
				return Restrictions.not(Restrictions.in(column, objArray));
			} else if (objArray.length == 1) {
				return Restrictions.ne(column, objArray[0]);
			}
			// 大于 >
		case Operator.SQL_GT:
			return Restrictions.gt(column, objArray[0]);
			// 小于 <
		case Operator.SQL_LT:
			return Restrictions.lt(column, objArray[0]);
			// 大于等于 >=
		case Operator.SQL_GE:
			return Restrictions.ge(column, objArray[0]);
			// 小于等于 <=
		case Operator.SQL_LE:
			return Restrictions.le(column, objArray[0]);
			// 区间 between
		case Operator.SQL_BETWEEN:
			return Restrictions.between(column, objArray[0], objArray[1]);
			// 非区间 not between
		case Operator.SQL_NOT_BETWEEN:
			return Restrictions.not(Restrictions.between(column, objArray[0],
					objArray[1]));
			// 模糊查询
		case Operator.SQL_LIKE:
			return Restrictions.like(column, objArray[0]);
			// 模糊查询,不区分大小写
		case Operator.SQL_I_LIKE:
			return Restrictions.ilike(column, objArray[0]);
			// 字段为空 is null
		case Operator.SQL_NULL:
			return Restrictions.isNull(column);
			// 字段不为空 is not null
		case Operator.SQL_NOT_NULL:
			return Restrictions.isNotNull(column);
		default:
			return null;
		}
	}

	/**
	 * 解析排序条件
	 * 
	 * @param crt
	 *            Criteria
	 * @param it
	 *            Iterator
	 * @param orSize
	 *            条件个数
	 * @param i
	 *            当前个数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LogicalExpression getCriterionOr(Criteria crt, Iterator it,
			int orSize, int i) {
		if (i + 2 == orSize) {
			return Restrictions.or(getCriterion((Parameter) it.next()),
					getCriterion((Parameter) it.next()));
		} else {
			i++;
			return Restrictions.or(getCriterion((Parameter) it.next()),
					getCriterionOr(crt, it, orSize, i));
		}
	}
	
	

	/**
	 * 解析查询条件
	 * 
	 * @param crt
	 *            Criteria
	 * @param par
	 *            条件参数
	 * @return Criteria
	 */
	private String getCriterionString(Parameter par) {
		String column = par.getColumn();
		int oper = par.getOperator();
		Object[] objArray = par.getValue();

		switch (oper) {
		// 等于 =
		case Operator.SQL_EQ:
			if (objArray.length > 1) {
				StringBuffer sql = new StringBuffer(column + " in (");
				int aSize = objArray.length;
				for (int i = 0; i < aSize; i++) {
					sql.append("?");
					if (i + 1 < aSize) {
						sql.append(",");
					}
				}
				sql.append(")");
				return sql.toString();
			} else if (objArray.length == 1) {
				return new String(column + " = ?");
			}
			// 不等于 <>
		case Operator.SQL_NE:
			if (objArray.length > 1) {
				StringBuffer sql = new StringBuffer(column + " not in (");
				int aSize = objArray.length;
				for (int i = 0; i < aSize; i++) {
					sql.append("?");
					if (i + 1 < aSize) {
						sql.append(",");
					}
				}
				sql.append(")");
				return sql.toString();
			} else if (objArray.length == 1) {
				return new String(column + " <> ?");
			}
			// 大于 >
		case Operator.SQL_GT:
			return new String(column + " > ?");
			// 小于 <
		case Operator.SQL_LT:
			return new String(column + " < ?");
			// 大于等于 >=
		case Operator.SQL_GE:
			return new String(column + " >= ?");
			// 小于等于 <=
		case Operator.SQL_LE:
			return new String(column + " <= ?");
			// 区间 between
		case Operator.SQL_BETWEEN:
			return new String(column + " between (? and ?)");
			// 非区间 not between
		case Operator.SQL_NOT_BETWEEN:
			return new String(column + " not between (? and ?)");
			// 模糊查询
		case Operator.SQL_LIKE:
			return new String(column + " like ?");
			// 字段为空 is null
		case Operator.SQL_NULL:
			return new String(column + " is Null ?");
			// 字段不为空 is not null
		case Operator.SQL_NOT_NULL:
			return new String(column + " is not Null ?");
		default:
			return "";
		}
	}

	/**
	 * 解析分组条件
	 * 
	 * @param col
	 *            分组条件
	 * @return String
	 */
	private String getGroupColumnString(Column col) {
		String columnName = col.getColumn();
		String alias = col.getAlias();
		int oper = col.getOperator();
		switch (oper) {
		// 分组 avg
		case Group.SQL_GROUP_AVG:
			if (alias != null && !alias.equals("")) {
				return new String("avg(" + columnName + ") AS " + alias);
			} else {
				return new String("avg(" + columnName + ")");
			}
			// 分组 count
		case Group.SQL_GROUP_COUNT:
			if (alias != null && !alias.equals("")) {
				return new String("count(" + columnName + ") AS " + alias);
			} else {
				return new String("count(" + columnName + ")");
			}
			// 分组 count distinct
		case Group.SQL_GROUP_COUNT_DISTINCT:
			if (alias != null && !alias.equals("")) {
				return new String("count(distinct " + columnName + ") AS "
						+ alias);
			} else {
				return new String("count(distinct " + columnName + ")");
			}
			// 分组 max
		case Group.SQL_GROUP_MAX:
			if (alias != null && !alias.equals("")) {
				return new String("max(" + columnName + ") AS " + alias);
			} else {
				return new String("max(" + columnName + ")");
			}
			// 分组 min
		case Group.SQL_GROUP_MIN:
			if (alias != null && !alias.equals("")) {
				return new String("min(" + columnName + ") AS " + alias);
			} else {
				return new String("min(" + columnName + ")");
			}
			// 分组 sum
		case Group.SQL_GROUP_SUM:
			if (alias != null && !alias.equals("")) {
				return new String("sum(" + columnName + ") AS " + alias);
			} else {
				return new String("sum(" + columnName + ")");
			}
		default:
			return "";
		}
	}

	// 进行SQL分页设置
	private String getRollPageString(RollPage rollPage, String sql) {
		StringBuffer pagingSelect = new StringBuffer();

		pagingSelect
				.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= ");
		pagingSelect
				.append(rollPage.getPagePer() * (rollPage.getPageCur() + 1));
		pagingSelect.append(") where rownum_ > ");
		pagingSelect.append(rollPage.getPageFirst());

		return pagingSelect.toString();
	}

	// ***************************** Criteria *******************************//

	@SuppressWarnings("unchecked")
	private Criteria parseCriteriaCondition(Condition condition, Session session)
			throws BaseException {
		try {
			// 待查询的表对象（对应数据库中的表）
			Class objClass = condition.getObjectClass();
			// 查询条件以And做连接（既满足所有条件）
			List<Parameter> and = condition.getParameterAnd();
			// 查询条件以Or做连接（既满足所有条件中的任意一个）
			List<Parameter> or = condition.getParameterOr();
			// 排序条件
			List<com.hatc.base.hibernate.util.Order> orderList = condition
					.getOrder();

			Criteria crt = session.createCriteria(objClass);
			String className = "";
			List<Parameter> andTemp = null;
			Parameter par = null;
			
			for (Iterator iterator = condition.getAliasMap().keySet()
					.iterator(); iterator.hasNext();) {
				className = (String) iterator.next();
				crt.createAlias(className, condition.getAlias(className));
			}

			List<Aggregate> aggregateList = condition.getParameterList();
			if (aggregateList != null && aggregateList.size() > 0) {
				for (Aggregate aggregate : aggregateList) {
					andTemp = aggregate.getParameterAnd();
					// 解析查询条件 And
					if (andTemp != null && andTemp.size() > 0) {
						for (Iterator it = andTemp.iterator(); it.hasNext();) {
							par = (Parameter) it.next();
							crt.add(getCriterion(par));
						}
					}
					List<Parameter> andOr = aggregate.getParameterOr();
					// 解析查询条件 Or
					if (andOr != null && andOr.size() > 0) {
						if (andOr.size() >= 2) {
							crt.add(getCriterionOr(crt, andOr.iterator(), andOr
									.size(), 0));
						}
					}
				}
			}

			// 解析查询条件 And
			if (and != null && and.size() > 0) {
				for (Iterator it = and.iterator(); it.hasNext();) {
					par = (Parameter) it.next();
					crt.add(getCriterion(par));
				}
			}

			// 解析查询条件 Or
			int orSize = or.size();
			if (or != null && orSize > 0) {
				if (orSize >= 2) {
					crt.add(getCriterionOr(crt, or.iterator(), orSize, 0));
				}
			}
			
			Column o = null;
			List column = null;
			// 排序参数据的解析
			for (com.hatc.base.hibernate.util.Order order : orderList) {
				column = order.getColumn();
				for (Iterator it = column.iterator(); it.hasNext();) {
					o = (Column) it.next();
					if (o.getOperator() == com.hatc.base.hibernate.util.Order.SQL_ORDER_ASC) {
						crt.addOrder(Order.asc(o.getColumn()));
					} else if (o.getOperator() == com.hatc.base.hibernate.util.Order.SQL_ORDER_DESC) {
						crt.addOrder(Order.desc(o.getColumn()));
					}
				}
			}
			return crt;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException e = new BaseException(UserResourceConfig.getInstance().getValue("msg.base.common.exception.HIBERNATE_QUERY_ROLLPAGE_EXCEPTION"));
			e.setRootCause(ex);
			throw e;
		}

	}

	// ***************************** Query *******************************//
	@SuppressWarnings("unchecked")
	private Query parseQueryCondition(Condition con, Session session)
			throws BaseException {
		try {
			// 查询条件以And做连接（既满足所有条件）
			List<Parameter> and = con.getParameterAnd();
			// 查询条件以Or做连接（既满足所有条件中的任意一个）
			List<Parameter> or = con.getParameterOr();
			// 分组查询类
			Class obj = con.getObjectClass();
			// 分组参数
			List<Group> groupList = con.getGroup();
			// 排序参数
			List<com.hatc.base.hibernate.util.Order> orderList = con.getOrder();

			// 查询的HQL
			StringBuffer hql = new StringBuffer();
			// 临时的SQL
			StringBuffer col = new StringBuffer();
			// 迭代器
			Iterator it = null;
			List column = null;
			if (groupList.size() > 0) {
				for (Group group : groupList) {
					// 查询列
					column = group.getColumn();
					if (column.size() > 0) {
						it = column.iterator();
						col = new StringBuffer();
						while (it.hasNext()) {
							col
									.append(getGroupColumnString((Column) it
											.next()));
							if (it.hasNext()) {
								col.append(",");
							}
						}
						hql.append("SELECT " + col + " FROM " + obj.getName());
					}
				}
			} else {
				hql.append("FROM " + obj.getName());
			}

			// 分组条件
			boolean boolAnd = false;
			boolean boolOr = false;
			// 条件AND
			col = new StringBuffer();
			
			Parameter para = null;
			if (and.size() > 0) {
				boolAnd = true;
				it = and.iterator();
				while (it.hasNext()) {
					para = (Parameter) it.next();
					col.append(getCriterionString(para));
					if (it.hasNext()) {
						col.append(" AND ");
					}
				}
			}
			
			Parameter paro = null;
			// 条件OR
			if (or.size() > 0) {
				boolOr = true;
				it = or.iterator();
				if (boolAnd) {
					col.append(" OR ");
				}
				while (it.hasNext()) {
					paro = (Parameter) it.next();
					col.append(getCriterionString(paro));
					if (it.hasNext()) {
						col.append(" OR ");
					}

				}
			}

			if (boolAnd || boolOr) {
				hql.append(" WHERE " + col);
			}

			List groupAnd = null;
			List groupOr = null;
			
			if (groupList.size() > 0) {
				for (Group group : groupList) {
					// 分组查询条件以And做连接（既满足所有条件）
					groupAnd = group.getParameterAnd();
					// 分组查询条件以Or做连接（既满足所有条件中的任意一个）
					groupOr = group.getParameterOr();

					// 分组列
					List par = group.getGroupColumn();
					if (par.size() > 0) {
						it = par.iterator();
						col = new StringBuffer();
						while (it.hasNext()) {
							col.append((String) it.next());
							if (it.hasNext()) {
								col.append(",");
							}
						}
						hql.append(" GROUP BY " + col);
					}

					// 分组条件
					boolAnd = false;
					boolOr = false;

					// 条件AND
					col = new StringBuffer();
					if (groupAnd.size() > 0) {
						boolAnd = true;
						it = groupAnd.iterator();
						while (it.hasNext()) {
							para = (Parameter) it.next();
							col.append(getCriterionString(para));
							if (it.hasNext()) {
								col.append(" AND ");
							}
						}
					}
					// 条件OR
					if (groupOr.size() > 0) {
						boolOr = true;
						it = groupOr.iterator();
						if (boolAnd) {
							col.append(" OR ");
						}
						while (it.hasNext()) {
							paro = (Parameter) it.next();
							col.append(getCriterionString(paro));
							if (it.hasNext()) {
								col.append(" OR ");
							}

						}
					}

					if (boolAnd || boolOr) {
						hql.append(" HAVING " + col);
					}
				}

			}
			// 排序
			List corder = null;
			Column o  = null;
			if (orderList.size() > 0) {
				for (com.hatc.base.hibernate.util.Order order : orderList) {
					corder = order.getColumn();
					if (corder.size() > 0) {
						it = corder.iterator();
						col = new StringBuffer();
						while (it.hasNext()) {
							o = (Column) it.next();
							col.append(o.getColumn());
							if (o.getOperator() == com.hatc.base.hibernate.util.Order.SQL_ORDER_ASC) {
								col.append(" ASC ");
							} else if (o.getOperator() == com.hatc.base.hibernate.util.Order.SQL_ORDER_DESC) {
								col.append(" DESC ");
							}
							if (it.hasNext()) {
								col.append(",");
							}
						}

						hql.append(" ORDER BY " + col);
					}
				}
			}

			log.info(hql);

			Query query = session.createQuery(hql.toString());
			Object[] obja = null;
			int j;
			if (and.size() > 0) {
				boolAnd = true;
				it = and.iterator();
				int i = 0;
				while (it.hasNext()) {
					para = (Parameter) it.next();
					 obja = para.getValue();
					for (j = 0; j < obja.length; j++, i++) {
						query.setParameter(i, obja[j]);
					}
				}
			}
			// 条件OR
			if (or.size() > 0) {
				boolOr = true;
				it = or.iterator();
				int i = 0;
				if (boolAnd) {
					col.append(" OR ");
				}
				while (it.hasNext()) {
					paro = (Parameter) it.next();
					obja = paro.getValue();
					for (j = 0; j < obja.length; j++, i++) {
						query.setParameter(i, obja[j]);
					}
				}
			}

			if (groupList.size() > 0) {
				for (Group group : groupList) {
					// 分组查询条件以And做连接（既满足所有条件）
					groupAnd = group.getParameterAnd();
					// 分组查询条件以Or做连接（既满足所有条件中的任意一个）
					groupOr = group.getParameterOr();
					// 条件AND
					if (groupAnd.size() > 0) {
						it = groupAnd.iterator();
						int i = 0;
						while (it.hasNext()) {
							para = (Parameter) it.next();
							obja = para.getValue();
							for (j = 0; j < obja.length; j++, i++) {
								query.setParameter(i, obja[j]);
							}
						}
					}
					// 条件OR
					if (groupOr.size() > 0) {
						it = groupOr.iterator();
						int i = 0;
						while (it.hasNext()) {
							paro = (Parameter) it.next();
							col.append(getCriterionString(paro));
							obja = paro.getValue();
							for ( j = 0; j < obja.length; j++, i++) {
								query.setParameter(i, obja[j]);
							}
						}
					}
				}
			}

			return query;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException e = new BaseException(UserResourceConfig.getInstance().getValue("msg.base.common.exception.HIBERNATE_QUERY_ROLLPAGE_EXCEPTION"));
			e.setRootCause(ex);
			throw e;
		}
	}

	//  2012-04-17   修改或更新单个实例对象
	public void saveOrUpdateObject(Object o) throws BaseException {
		try {
			getHibernateTemplate().saveOrUpdate(o);
			getHibernateTemplate().flush();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_SAVE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
		
	}

	//  2012-04-17   批量修改或更新实例对象
	public void saveOrUpdateBatchObject(List list) throws BaseException {

		try {
			int i = 0;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				getHibernateTemplate().saveOrUpdate(it.next());
//				// 当记录数达到20条时，将数据写入数据库，并清理内存
				if (i != 0 && i + 1 % 20 == 0) {
					getHibernateTemplate().flush();
				}
			}
			getHibernateTemplate().flush();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_BATCH_SAVE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	
	}
	
	
	/**
	 * 2012-04-17   根据对象List批量删除对象
	 * 
	 * @param clazz
	 *            对象Class
	 * @param list
	 *            对象List
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeUpdateBatchObject(Class clazz, List list) throws BaseException {
		try {
			for (int i = 0; i < list.size(); i++) {
				getHibernateTemplate().delete(list.get(i));
				// 当记录数达到20条时，将数据写入数据库，并清理内存
				if (i != 0 && i + 1 % 20 == 0) {
					getHibernateTemplate().flush();
				}
			}
			getHibernateTemplate().flush();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_BATCH_DELETE_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}
	
	/**
	 * 2013-07-08   执行sql更新数据
	 * 
	 * @param String
	 *            sql
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void updateBySql(String sql) throws BaseException {
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Connection con = session.connection();
			Statement stmt = con.createStatement();
			stmt.execute(sql);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			BaseException be = new BaseException();
			be.setExceptionCode("msg.base.common.exception.HIBERNATE_SQL_EXCEPTION");
			be.setRootCause(ex);
			throw be;
		}
	}


}