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
 * <b>system��</b> ͨ�����й����������ϵͳ<br/> <b>description��</b> ���ݿ�ͳһ���ʽӿ�ʵ����<br/>
 * 
 */


public class BaseDaoHibernate extends HibernateDaoSupport implements Dao 

{
    
	protected final Log log = LogFactory.getLog(getClass());
	

	// #ֱ�����ݿ�Ķ���
	private DataSource dataSource = null;
	private LobHandler lobHandler = null;
	  
	  
	/******�½�����ID����ط���***********************************************/
	/**
	 * ����½�ָ������ΨһID�Ƿ��Ѿ����ڣ�����ID��
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
	 * @return boolean ��/��
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
 
	/******��ѯ��������ķ���********/
	/**
	 * ���ݶ���ID��ѯ����ָ������ 
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
	 * @return Object ����
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
	 * ����ָ��Class��ѯȫ�������	 * 
	 * @param clazz
	 *            ����Class
	 * @return List �����б�
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
	 * �������������ѯ��ҳָ���е��ַ��������
	 * 
	 * @param rollPage
	 *            ��ҳ����
	 * @param condition
	 *            ��������
	 * @return List �ַ��������
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
				// ��������
				Criteria crt = parseCriteriaCondition(condition, session);
				// ��������
				Criteria crtList = parseCriteriaCondition(condition, session);
				// ���÷�ҳ
				settingRollPage(rollPage, crt);
				// ���ò��Ҽ�¼����ʼλ��
				crtList.setFirstResult(rollPage.getPageFirst());
				// ���ò��Ҽ�¼���������
				crtList.setMaxResults(rollPage.getPagePer());
				List list = crtList.list();
				if (list != null) {
					rollPage.setCurrentlyPagePer(list.size());
				}
				return list != null && list.size() > 0 ? list : null;
			} else {
				// ��������
				Query query = parseQueryCondition(condition, session);
				// ���÷�ҳ
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
	 * ִ��hql��ѯ���ط�ҳ�������hql��䣩
	 * 
	 * @param rollPage ��ҳ����
	 * @param hql      hql���
	 * @return BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql) throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query crt = session.createQuery(hql);
			// ��ҳ����
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
	 * ִ�д�����hql��ѯ�����ط�ҳ�������hql��䣩
	 * (�˷����е�hql�����Ǵ��ʺŲ�����ʽ�ģ��磺from tb_a ta where ta.id=?)
	 * @param rollPage ��ҳ����
	 * @param hql ��ִ�еĴ�����hql���
	 * @param params hql�����"?"�Ĳ���List
	 * @return List ��������
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql, List params)
			throws BaseException {
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query crt = session.createQuery(hql);
			// ����hql����
			if (null != params && params.size() != 0) {
				for (int i = 0, j = params.size(); i < j; i++) {
					crt.setParameter(i, params.get(i));
				}
			}
			// ��ҳ����
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
	 * ִ��hql��ѯ����ȫ���������hql��䣩
	 * 2012-1-7 ���� Hibernate ���������. 
	 *     ������ȯ: ������� spring ���������,���������....
	 * @param hql
	 * @return List �����б�
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
	 * ִ�д�����hql��ѯ����ָ���е�ȫ���������hql��䣩
	 * (�˷����е�hql�����Ǵ��ʺŲ�����ʽ�ģ��磺from tb_a ta where ta.id=?)
	 * @param hql��ִ�е�hql���
	 * @param params hql�����"?"�Ĳ���List
	 * @return ʵ�弯��
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql, List params) throws BaseException {
		return this.getObjects(null, hql, params);
	}

 
	/******��ѯ��ҳ������Ķ������**************************************/
	/**
	 * ִ��SQL��ѯ��ҳ�����(SQL���)
	 *  ˵��: ������ʹ��. ���� datasource ��ȡ����. ��������������.
	 * @param rollPage
	 *            ��ҳ����
	 * @param sql
	 *            SQL���
	 * @param column
	 *            ����
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
			// ��ȡ����
			conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
			// TYPE_SCROLL_SENSITIVE �ɹ�������ͨ���������ĸ���Ӱ��� ResultSet ���������
			// CONCUR_READ_ONLY �����Ը��µ� ResultSet ����Ĳ���ģʽ
			stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = settingRollPage(rollPage, sql, conn, stmt);
			// ��ȡ ResultSet ������еı�š����ͺ�����
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
	 * �������������ѯȫ�������
	 * 2012-1-7 ����SPRING ���������, ֧�ָ߲���. 
	 * @param condition
	 *            ��������
	 * @return List �����б�
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Condition condition) throws BaseException {
		List<Group> groupList = condition.getGroup();
		Session session = null;
		try {
			session =  this.getHibernateTemplate().getSessionFactory().getCurrentSession();  
			if (groupList.size() < 1) {
				// ��������
				Criteria crt = parseCriteriaCondition(condition, session);
				List list = crt.list();
				return list != null && list.size() > 0 ? list : null;
			} else {
				// ��������
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
	 * ִ��SQL����ѯȫ��ָ���еĽ����
	 * @param sql
	 *            SQL���
	 * @param column
	 *            ����
	 * @return List �����
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
			// ��ȡ����
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
	 * ȡ��ORACLE���У�Long��
	 * ˵����������ʹ�ã�����ͨ���µķ���ʵ�֡�
	 * @param seq
	 *            oracle��������
	 * @return Long ����ֵ
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
	 * ˵����Ŀǰδʹ��
	 * @return
	 */
	public LobHandler getLobHandler() {
		return lobHandler;
	}

 
	/**
	 * ɾ����������
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
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
	 * ������������ɾ������
	 * 
	 * @param clazz
	 *            ����Class
	 * @param id  
	 *            ��ɾ������ID����
	 * @throws BaseException
	 * 
	 * ˵����Ϊ֧��ҵ���ͳһ��������ʹ��ͳһ��currentSession,�˷��������ݲ�ʹ��,��������.--update by jicheng
	 */
	@SuppressWarnings("unchecked")
	public void removeBatchObject(Class clazz, Serializable[] id)
			throws BaseException {
		Session currentSession = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		try {
			for (int i = 0; i < id.length; i++) {
				currentSession.delete(getObject(clazz, id[i]));
				// ����¼���ﵽ20��ʱ��������д�����ݿ⣬�������ڴ�
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
	 * ���浥������

	 * @param o
	 *            ����
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

	/**��������Դ
	 * ˵����������ʹ��
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Ŀǰδʹ��
	 * @param lobHandler
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

 
	
	/**
	   * ����Oracle�еĺ���
	   * ˵����������ʹ��
	   * @param functionName ��ִ�еĺ�������
	   * @param funParams �����в�����ֵ(��Ҫ�ͺ����еĲ���˳����ͬ)
	   * @param returnValueType �����ķ���ֵ����
	   *        ���磺java.sql.Types.VARCHAR��java.sql.Types.DATE��(����޷���ֵ���Դ���java.sql.Types.NULL)
	   * @return ��������ֵList
	   * @throws BaseException
	   */
	@SuppressWarnings("unchecked")
	public List transferOracleFun(String functionName, List funParams, int returnValueType) throws BaseException {
		StringBuffer param = new StringBuffer("");
		String temp = "";
		// ѭ������ת��������
		if(null != funParams && funParams.size() != 0){
			param.append("(");
			for(int i = 0, j = funParams.size(); i < j; i++){
				param.append("?,");
			}
			temp = param.substring(0, param.length() - 1);
			temp += ")";
		}
		// ����ת���ַ���
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
				// ע�᷵��ֵ����
				cs.registerOutParameter(1, returnValueType);
				// ���ò���
				if(null != funParams && funParams.size() != 0){
					for(int i = 0, j = funParams.size(); i < j; i++){
						// ��ȡ��ǰ������������
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
				// ���ò���
				if(null != funParams && funParams.size() != 0){
					for(int i = 0, j = funParams.size(); i < j; i++){
						// ��ȡ��ǰ������������
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
	 * ���µ������󣨲�ͬ������ͬ������
	 * @param o
	 *            ����
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
	 * ִ��oracle�洢����
	 * ˵�����������JD û��ʹ�� ��  ��Ҫ�����Ƿ��޸�ʵ��
	 *  * @param sql
	 *            �洢����sql��䣿��������������
	 *  JD δʹ��,HFX ʹ��.          
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
	 * ��Criteriaʵ�ַ�ҳ(Hql)
	 * 
	 * @param rollPage
	 *            ��ҳ��
	 * @param Criteria
	 */
	@SuppressWarnings("unchecked")
	protected void settingRollPage(RollPage rollPage, Criteria crt) {
		// ��ҳ����
		if (rollPage != null) {
			Criteria crtCount = crt;
			crtCount.setProjection(Projections.rowCount());
			List list = crtCount.list();
			// ���ü�¼����
			rollPage.setRowCount((Integer) list.get(0));
			// ��ʼ��
			rollPage.init();
		}
	}

	/**
	 * ��Queryʵ�ַ�ҳ(Hql)
	 * 
	 * @param rollPage
	 *            ��ҳ��
	 * @param Query
	 */
	@SuppressWarnings("unchecked")
	protected void settingRollPage(RollPage rollPage, Query crt) {
		// ��ҳ����
		if (rollPage != null) {
			List list = crt.list();
			// ���ü�¼����
			rollPage.setRowCount(list.size());
			// ��ʼ��
			rollPage.init();
			// ���ò��Ҽ�¼����ʼλ��
			crt.setFirstResult(rollPage.getPageFirst());
			// ���ò��Ҽ�¼���������
			crt.setMaxResults(rollPage.getPagePer());
		}
	}

	/**
	 * ��java.sqlʵ�ַ�ҳ(sql)
	 * 
	 * @param rollPage
	 *            ��ҳ��
	 * @param sql
	 *            ��ѯ���
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
				// ������ҳ��
				rollPage.setRowCount(i);
				// ��ʼ��
				rollPage.init();
				// ���÷�ҳ
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
	 * clob����ת����string
	 * 
	 * @param typeCode���ʹ���
	 * @param rs���ݼ�
	 * @param colName��Ӧ�����ݿ��е�����
	 * @return ��Ӧ�ַ���
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
			// ����������Ϊ���ݿ��е�Date���͵����ݵĸ�ʽ��ͳһ,��yyyy-MM-dd��yyyy-MM-dd hh:mm:ss��
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
	 * ������ѯ����
	 * 
	 * @param par
	 *            ��������
	 * 
	 * @return Criteria
	 */
	private Criterion getCriterion(Parameter par) {
		String column = par.getColumn();
		int oper = par.getOperator();
		Object[] objArray = par.getValue();

		switch (oper) {
		// ���� =
		case Operator.SQL_EQ:
			if (objArray.length > 1) {
				return Restrictions.in(column, objArray);
			} else if (objArray.length == 1) {
				return Restrictions.eq(column, objArray[0]);
			}
			// ������ <>
		case Operator.SQL_NE:
			if (objArray.length > 1) {
				return Restrictions.not(Restrictions.in(column, objArray));
			} else if (objArray.length == 1) {
				return Restrictions.ne(column, objArray[0]);
			}
			// ���� >
		case Operator.SQL_GT:
			return Restrictions.gt(column, objArray[0]);
			// С�� <
		case Operator.SQL_LT:
			return Restrictions.lt(column, objArray[0]);
			// ���ڵ��� >=
		case Operator.SQL_GE:
			return Restrictions.ge(column, objArray[0]);
			// С�ڵ��� <=
		case Operator.SQL_LE:
			return Restrictions.le(column, objArray[0]);
			// ���� between
		case Operator.SQL_BETWEEN:
			return Restrictions.between(column, objArray[0], objArray[1]);
			// ������ not between
		case Operator.SQL_NOT_BETWEEN:
			return Restrictions.not(Restrictions.between(column, objArray[0],
					objArray[1]));
			// ģ����ѯ
		case Operator.SQL_LIKE:
			return Restrictions.like(column, objArray[0]);
			// ģ����ѯ,�����ִ�Сд
		case Operator.SQL_I_LIKE:
			return Restrictions.ilike(column, objArray[0]);
			// �ֶ�Ϊ�� is null
		case Operator.SQL_NULL:
			return Restrictions.isNull(column);
			// �ֶβ�Ϊ�� is not null
		case Operator.SQL_NOT_NULL:
			return Restrictions.isNotNull(column);
		default:
			return null;
		}
	}

	/**
	 * ������������
	 * 
	 * @param crt
	 *            Criteria
	 * @param it
	 *            Iterator
	 * @param orSize
	 *            ��������
	 * @param i
	 *            ��ǰ����
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
	 * ������ѯ����
	 * 
	 * @param crt
	 *            Criteria
	 * @param par
	 *            ��������
	 * @return Criteria
	 */
	private String getCriterionString(Parameter par) {
		String column = par.getColumn();
		int oper = par.getOperator();
		Object[] objArray = par.getValue();

		switch (oper) {
		// ���� =
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
			// ������ <>
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
			// ���� >
		case Operator.SQL_GT:
			return new String(column + " > ?");
			// С�� <
		case Operator.SQL_LT:
			return new String(column + " < ?");
			// ���ڵ��� >=
		case Operator.SQL_GE:
			return new String(column + " >= ?");
			// С�ڵ��� <=
		case Operator.SQL_LE:
			return new String(column + " <= ?");
			// ���� between
		case Operator.SQL_BETWEEN:
			return new String(column + " between (? and ?)");
			// ������ not between
		case Operator.SQL_NOT_BETWEEN:
			return new String(column + " not between (? and ?)");
			// ģ����ѯ
		case Operator.SQL_LIKE:
			return new String(column + " like ?");
			// �ֶ�Ϊ�� is null
		case Operator.SQL_NULL:
			return new String(column + " is Null ?");
			// �ֶβ�Ϊ�� is not null
		case Operator.SQL_NOT_NULL:
			return new String(column + " is not Null ?");
		default:
			return "";
		}
	}

	/**
	 * ������������
	 * 
	 * @param col
	 *            ��������
	 * @return String
	 */
	private String getGroupColumnString(Column col) {
		String columnName = col.getColumn();
		String alias = col.getAlias();
		int oper = col.getOperator();
		switch (oper) {
		// ���� avg
		case Group.SQL_GROUP_AVG:
			if (alias != null && !alias.equals("")) {
				return new String("avg(" + columnName + ") AS " + alias);
			} else {
				return new String("avg(" + columnName + ")");
			}
			// ���� count
		case Group.SQL_GROUP_COUNT:
			if (alias != null && !alias.equals("")) {
				return new String("count(" + columnName + ") AS " + alias);
			} else {
				return new String("count(" + columnName + ")");
			}
			// ���� count distinct
		case Group.SQL_GROUP_COUNT_DISTINCT:
			if (alias != null && !alias.equals("")) {
				return new String("count(distinct " + columnName + ") AS "
						+ alias);
			} else {
				return new String("count(distinct " + columnName + ")");
			}
			// ���� max
		case Group.SQL_GROUP_MAX:
			if (alias != null && !alias.equals("")) {
				return new String("max(" + columnName + ") AS " + alias);
			} else {
				return new String("max(" + columnName + ")");
			}
			// ���� min
		case Group.SQL_GROUP_MIN:
			if (alias != null && !alias.equals("")) {
				return new String("min(" + columnName + ") AS " + alias);
			} else {
				return new String("min(" + columnName + ")");
			}
			// ���� sum
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

	// ����SQL��ҳ����
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
			// ����ѯ�ı���󣨶�Ӧ���ݿ��еı�
			Class objClass = condition.getObjectClass();
			// ��ѯ������And�����ӣ�����������������
			List<Parameter> and = condition.getParameterAnd();
			// ��ѯ������Or�����ӣ����������������е�����һ����
			List<Parameter> or = condition.getParameterOr();
			// ��������
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
					// ������ѯ���� And
					if (andTemp != null && andTemp.size() > 0) {
						for (Iterator it = andTemp.iterator(); it.hasNext();) {
							par = (Parameter) it.next();
							crt.add(getCriterion(par));
						}
					}
					List<Parameter> andOr = aggregate.getParameterOr();
					// ������ѯ���� Or
					if (andOr != null && andOr.size() > 0) {
						if (andOr.size() >= 2) {
							crt.add(getCriterionOr(crt, andOr.iterator(), andOr
									.size(), 0));
						}
					}
				}
			}

			// ������ѯ���� And
			if (and != null && and.size() > 0) {
				for (Iterator it = and.iterator(); it.hasNext();) {
					par = (Parameter) it.next();
					crt.add(getCriterion(par));
				}
			}

			// ������ѯ���� Or
			int orSize = or.size();
			if (or != null && orSize > 0) {
				if (orSize >= 2) {
					crt.add(getCriterionOr(crt, or.iterator(), orSize, 0));
				}
			}
			
			Column o = null;
			List column = null;
			// ��������ݵĽ���
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
			// ��ѯ������And�����ӣ�����������������
			List<Parameter> and = con.getParameterAnd();
			// ��ѯ������Or�����ӣ����������������е�����һ����
			List<Parameter> or = con.getParameterOr();
			// �����ѯ��
			Class obj = con.getObjectClass();
			// �������
			List<Group> groupList = con.getGroup();
			// �������
			List<com.hatc.base.hibernate.util.Order> orderList = con.getOrder();

			// ��ѯ��HQL
			StringBuffer hql = new StringBuffer();
			// ��ʱ��SQL
			StringBuffer col = new StringBuffer();
			// ������
			Iterator it = null;
			List column = null;
			if (groupList.size() > 0) {
				for (Group group : groupList) {
					// ��ѯ��
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

			// ��������
			boolean boolAnd = false;
			boolean boolOr = false;
			// ����AND
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
			// ����OR
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
					// �����ѯ������And�����ӣ�����������������
					groupAnd = group.getParameterAnd();
					// �����ѯ������Or�����ӣ����������������е�����һ����
					groupOr = group.getParameterOr();

					// ������
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

					// ��������
					boolAnd = false;
					boolOr = false;

					// ����AND
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
					// ����OR
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
			// ����
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
			// ����OR
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
					// �����ѯ������And�����ӣ�����������������
					groupAnd = group.getParameterAnd();
					// �����ѯ������Or�����ӣ����������������е�����һ����
					groupOr = group.getParameterOr();
					// ����AND
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
					// ����OR
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

	//  2012-04-17   �޸Ļ���µ���ʵ������
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

	//  2012-04-17   �����޸Ļ����ʵ������
	public void saveOrUpdateBatchObject(List list) throws BaseException {

		try {
			int i = 0;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				getHibernateTemplate().saveOrUpdate(it.next());
//				// ����¼���ﵽ20��ʱ��������д�����ݿ⣬�������ڴ�
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
	 * 2012-04-17   ���ݶ���List����ɾ������
	 * 
	 * @param clazz
	 *            ����Class
	 * @param list
	 *            ����List
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeUpdateBatchObject(Class clazz, List list) throws BaseException {
		try {
			for (int i = 0; i < list.size(); i++) {
				getHibernateTemplate().delete(list.get(i));
				// ����¼���ﵽ20��ʱ��������д�����ݿ⣬�������ڴ�
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
	 * 2013-07-08   ִ��sql��������
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