package com.hatc.base.hibernate.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import org.hibernate.type.Type;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.RollPage;
import com.hatc.base.hibernate.util.Condition;

/**
 * 
 * <b>system��</b> Эͬ�칫ƽ̨<br/> 
 * <b>description��</b> ���ݿ�ͳһ���ʽӿ�<br/> 
 * <b>author��</b>����<br/> 
 * <b>copyright��</b> ����������ϿƼ����޹�˾<br/> 
 * <b>version��</b> VER1.00 2010-04-06<br/>
 * <b>version��</b> VER1.01 2011-12-09 ���� �޸Ĳ���ע��<br/>
 * 
 * <b>version��</b> VER1.02 2011-12-09 NLY Ϊ֧��ͳһ�����������˼�������<br/>
 * 
 * 
 */
public interface Dao {
	


	/******��ѯȫ��������Ķ��ַ���************************/
	
	/**
	 * ����ָ��Class��ѯȫ�������
	 * 
	 * @param clazz
	 *            ����Class
	 * @return List �����б�
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Class clazz) throws BaseException;
	
	/**
	 * �������������ѯȫ�������
	 * 
	 * @param condition
	 *            ��������
	 * @return List �����б�
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Condition condition) throws BaseException;
	
	/**
	 * ִ��hql��ѯ����ȫ���������hql��䣩
	 * 
	 * @param hql
	 * @return List �����б�
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql) throws BaseException;

	/**
	 * ִ��SQL����ѯȫ��ָ���еĽ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param column
	 *            ����
	 * @return List �����
	 * @throws BaseException
	 */
	public List<String[]> getObjects(String sql, String[] column)
			throws BaseException;

	/**
	 * ִ�д�����hql��ѯ����ָ���е�ȫ���������hql��䣩
	 * (�˷����е�hql�����Ǵ��ʺŲ�����ʽ�ģ��磺from tb_a ta where ta.id=?)
	 * @param hql��ִ�е�hql���
	 * @param params hql�����"?"�Ĳ���List
	 * @return ʵ�弯��
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql, List params) throws BaseException;
	

	/******��ѯ��������ķ���********/
	/**
	 * ���ݶ���ID��ѯ����ָ������
	 * 
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
	 * @return Object ����
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id) throws BaseException;
	
	/******��ѯ��ҳ������Ķ������**************************************/
	/**
	 * ִ��SQL��ѯ��ҳ�����(SQL���)
	 * 
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
			String[] column) throws BaseException;

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
			throws BaseException;

	/**
	 * ִ��hql��ѯ���ط�ҳ�������hql��䣩
	 * 
	 * @param rollPage ��ҳ����
	 * @param hql      hql���
	 * @return BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql) throws BaseException;

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
			throws BaseException;

	/******�½�����ID����ط���***********************************************/
	/**
	 * ����½�ָ������ΨһID�Ƿ��Ѿ����ڣ�����ID��
	 * 
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
	 * @return boolean ��/��
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public boolean getObjectUnique(Class clazz, Serializable id)
			throws BaseException;
	
	/**
	 * ȡ��ORACLE���У�Long��
	 * 
	 * @param seq
	 *            oracle��������
	 * @return Long ����ֵ
	 * @throws BaseException
	 */
	public String getOracleSeq(String seq) throws BaseException;
	
	/******ɾ���������ط���************************************************/

	/**
	 * ������������ɾ������
	 * 
	 * @param clazz
	 *            ����Class
	 * @param id  
	 *            ��ɾ������ID����
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeBatchObject(Class clazz, Serializable[] id)
			throws BaseException;
	
	/**
	 * �����Ƴ�����
	 * 2012-04-20
	 * @param o
	 *            ����List
	 * @throws BaseException
	 */
	public void removeUpdateBatchObject(Class clazz, List list) throws BaseException;
	
	/**
	 * ɾ����������
	 * 
	 * @param clazz
	 *            ����Class
	 * @param id
	 *            ����ID
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id) throws BaseException;


	/**
	 * ���浥������
	 * 
	 * @param o
	 *            ����
	 * @throws BaseException
	 */
	public void saveObject(Object o) throws BaseException;

	/**
	 * ���µ������󣨲�ͬ������ͬ������
	 * 
	 * @param o
	 *            ����
	 * @throws BaseException
	 */
	public void updateObject(Object o) throws BaseException;
	

	
	/**
	 * �������µ�������
	 * 2012-04-17
	 * @param o
	 *            ����
	 * @throws BaseException
	 */
	public void saveOrUpdateObject(Object o) throws BaseException;
	
	/**
	 * �����������¶���
	 * 2012-04-17
	 * @param o
	 *  ����List
	 * @throws BaseException
	 */
	public void saveOrUpdateBatchObject(List list) throws BaseException ;
	
  

	/**
   * ����Oracle�еĺ���
   * @param functionName ��ִ�еĺ�������
   * @param funParams �����в�����ֵ(��Ҫ�ͺ����еĲ���˳����ͬ)
   * @param returnValueType �����ķ���ֵ����
   *        ���磺java.sql.Types.VARCHAR��java.sql.Types.DATE��(����޷���ֵ���Դ���java.sql.Types.NULL)
   * @return ��������ֵList
   * @throws BaseException
   */
  @SuppressWarnings("unchecked")
	public List transferOracleFun(String functionName, List funParams,  int returnValueType) throws BaseException;

	/**
   * ִ��oracle�洢����
   *  ˵�����������JD û��ʹ�� ��  ��Ҫ�����Ƿ��޸�ʵ��
	 * @param sql
	 *            �洢����sql��䣿��������������
	 * @throws BaseException
   */
  public void updateProced(String sql) throws BaseException;

  /**
   * ִ��sql
  	 * @param sql
  	 * @throws BaseException
   */
  public void updateBySql(String sql) throws BaseException;
}
