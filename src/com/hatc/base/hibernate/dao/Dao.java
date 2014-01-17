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
 * <b>system：</b> 协同办公平台<br/> 
 * <b>description：</b> 数据库统一访问接口<br/> 
 * <b>author：</b>王洋<br/> 
 * <b>copyright：</b> 北京华安天诚科技有限公司<br/> 
 * <b>version：</b> VER1.00 2010-04-06<br/>
 * <b>version：</b> VER1.01 2011-12-09 余涛 修改补充注释<br/>
 * 
 * <b>version：</b> VER1.02 2011-12-09 NLY 为支持统一事务处理，增加了几个方法<br/>
 * 
 * 
 */
public interface Dao {
	


	/******查询全部结果集的多种方法************************/
	
	/**
	 * 根据指定Class查询全部结果集
	 * 
	 * @param clazz
	 *            对象Class
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Class clazz) throws BaseException;
	
	/**
	 * 根据条件对象查询全部结果集
	 * 
	 * @param condition
	 *            条件对象
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(Condition condition) throws BaseException;
	
	/**
	 * 执行hql查询返回全部结果集（hql语句）
	 * 
	 * @param hql
	 * @return List 对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql) throws BaseException;

	/**
	 * 执行SQL语句查询全部指定列的结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param column
	 *            列名
	 * @return List 结果集
	 * @throws BaseException
	 */
	public List<String[]> getObjects(String sql, String[] column)
			throws BaseException;

	/**
	 * 执行带参数hql查询返回指定列的全部结果集（hql语句）
	 * (此方法中的hql必须是带问号参数形式的，如：from tb_a ta where ta.id=?)
	 * @param hql待执行的hql语句
	 * @param params hql语句中"?"的参数List
	 * @return 实体集合
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(String hql, List params) throws BaseException;
	

	/******查询单个对象的方法********/
	/**
	 * 根据对象ID查询单个指定对象
	 * 
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @return Object 对象
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id) throws BaseException;
	
	/******查询分页结果集的多个方法**************************************/
	/**
	 * 执行SQL查询分页结果集(SQL语句)
	 * 
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
			String[] column) throws BaseException;

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
			throws BaseException;

	/**
	 * 执行hql查询返回分页结果集（hql语句）
	 * 
	 * @param rollPage 分页参数
	 * @param hql      hql语句
	 * @return BaseException
	 */
	@SuppressWarnings("unchecked")
	public List getObjects(RollPage rollPage, String hql) throws BaseException;

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
			throws BaseException;

	/******新建对象ID的相关方法***********************************************/
	/**
	 * 检查新建指定对象唯一ID是否已经存在（对象ID）
	 * 
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @return boolean 是/否
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public boolean getObjectUnique(Class clazz, Serializable id)
			throws BaseException;
	
	/**
	 * 取得ORACLE序列（Long）
	 * 
	 * @param seq
	 *            oracle序列名称
	 * @return Long 序列值
	 * @throws BaseException
	 */
	public String getOracleSeq(String seq) throws BaseException;
	
	/******删除对象的相关方法************************************************/

	/**
	 * 根据数组批量删除对象
	 * 
	 * @param clazz
	 *            对象Class
	 * @param id  
	 *            待删除对象ID数组
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeBatchObject(Class clazz, Serializable[] id)
			throws BaseException;
	
	/**
	 * 批量移除对象
	 * 2012-04-20
	 * @param o
	 *            对象List
	 * @throws BaseException
	 */
	public void removeUpdateBatchObject(Class clazz, List list) throws BaseException;
	
	/**
	 * 删除单个对象
	 * 
	 * @param clazz
	 *            对象Class
	 * @param id
	 *            对象ID
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public void removeObject(Class clazz, Serializable id) throws BaseException;


	/**
	 * 保存单个对象
	 * 
	 * @param o
	 *            对象
	 * @throws BaseException
	 */
	public void saveObject(Object o) throws BaseException;

	/**
	 * 更新单个对象（不同名的相同方法）
	 * 
	 * @param o
	 *            对象
	 * @throws BaseException
	 */
	public void updateObject(Object o) throws BaseException;
	

	
	/**
	 * 保存或更新单个对象
	 * 2012-04-17
	 * @param o
	 *            对象
	 * @throws BaseException
	 */
	public void saveOrUpdateObject(Object o) throws BaseException;
	
	/**
	 * 批量保存或更新对象
	 * 2012-04-17
	 * @param o
	 *  对象List
	 * @throws BaseException
	 */
	public void saveOrUpdateBatchObject(List list) throws BaseException ;
	
  

	/**
   * 调用Oracle中的函数
   * @param functionName 待执行的函数名称
   * @param funParams 函数中参数的值(需要和函数中的参数顺序相同)
   * @param returnValueType 函数的返回值类型
   *        例如：java.sql.Types.VARCHAR、java.sql.Types.DATE等(如果无返回值可以传入java.sql.Types.NULL)
   * @return 函数返回值List
   * @throws BaseException
   */
  @SuppressWarnings("unchecked")
	public List transferOracleFun(String functionName, List funParams,  int returnValueType) throws BaseException;

	/**
   * 执行oracle存储过程
   *  说明：这个方法JD 没有使用 。  需要考虑是否修改实现
	 * @param sql
	 *            存储过程sql语句？？？？？？？？
	 * @throws BaseException
   */
  public void updateProced(String sql) throws BaseException;

  /**
   * 执行sql
  	 * @param sql
  	 * @throws BaseException
   */
  public void updateBySql(String sql) throws BaseException;
}
