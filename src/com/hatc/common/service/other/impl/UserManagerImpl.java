package com.hatc.common.service.other.impl;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.contants.ProjectParameters;
import com.hatc.common.hibernate.pojo.TbParameterSetup;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.service.other.UserManager;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.web.config.ProjectConfig;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û��������ء��˳�ϵͳʵ����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class UserManagerImpl extends ProjectManagerImpl implements UserManager {

	//private BeanValue aBeanValue = new BeanValue(); // ҵ������ɺ�Ľ����

	//private ReqIdentity aReqIdentity = new ReqIdentity(); // �û���ݱ�ʶ

	/**
	 * �޸�����(��ʼ��)
	 */
	public BeanValue setPwd(RequestMap aRequestMap) throws Exception {

		BeanValue aBeanValue = new BeanValue(); 
		// ��ȡ��¼�û��Ự��ʶ
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);
		// �ͷŵ�ǰ��¼�Ự�����ж�����
		releaseObjectLock(aReqIdentity);
 		
	    //TODO ningliyu 2011-12-10
	 
		aReqIdentity.setFunctionId("BA00007");
 		
		//���ƣ����ƣ�PWD_SECRET_LEVEL ���ݣ�����ǿ�ȵȼ���1-5��
		TbParameterSetup par = ProjectParameters.getParameter(ProjectConstants.PWD_SECRET_LEVEL);
		aBeanValue.addRequestMap(ProjectConstants.PWD_SECRET_LEVEL, par.getLower());
		//���ƣ�MODI_PWD_INTERVAL ���ݣ�ǿ���޸�����ʱ����
		par = ProjectParameters.getParameter(ProjectConstants.MODI_PWD_INTERVAL);
		aBeanValue.addRequestMap(ProjectConstants.MODI_PWD_INTERVAL, par.getLower());
		//���ƣ�PASSWORD_LENGTH ���ݣ����볤��
		par = ProjectParameters.getParameter(ProjectConstants.PASSWORD_LENGTH);
		aBeanValue.addRequestMap(ProjectConstants.PASSWORD_LENGTH, par.getLower());

		// ���Ʒ�
		String ctrl = aRequestMap.getString("ctrl");
		aBeanValue.addRequestMap("ctrl", ctrl);
		
		return aBeanValue;
	}

	/**
	 * �޸�����(�ύ)
	 */
	public BeanValue updatePwd(RequestMap aRequestMap) throws Exception 
	{
	
		BeanValue aBeanValue = new BeanValue(); 
		// ��ȡ��¼�û��Ự��ʶ
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);
		aReqIdentity.setFunctionId("BA00007");

		/* WEB ������� */
		String origPwd = aRequestMap.getString("origPwd").trim(); // ԭ����
		String newPwd = aRequestMap.getString("newPwd").trim(); // ������
		
		//TODO /* ҵ���߼� - �޸����� */

		return aBeanValue;
	}

	/**
	 * �˳�ϵͳ
	 */
	public BeanValue logoutSystem(RequestMap aRequestMap) throws Exception {

		
		BeanValue aBeanValue = new BeanValue(); 
		// ��ȡ��¼�û��Ự��ʶ
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);

		aReqIdentity.setFunctionId("BA00009");
		// �� SESSION �õ��û����� ID
		String userId = aReqIdentity.getUserId();     

		//TODO/* ҵ���߼� - �˳�ϵͳ */

		return aBeanValue;
	}

	/**
	 * ����JAVA JDK
	 * 
	 * @param RequestMap
	 *            ������� Map
	 * @return BeanValue ҵ������ɺ�Ľ����
	 */
	public BeanValue downloadJDK(RequestMap map) throws Exception {
   
		BeanValue aBeanValue = new BeanValue(); 
		// ��ȡ��¼�û��Ự��ʶ
		ReqIdentity aReqIdentity = getReqIdentity(map);
		 
		BeanValue value = new BeanValue();
		String appVersion = map.getString("appVersion"); // �õ��û����ò���ϵͳ����

		String filename = null; // �������ļ���
		String filesname = null; // �ļ�������
		String filetype = null; // �������ļ�����

		String syspath = ProjectConfig.getProjectConfig("downClient"); // ϵͳ���ؿͻ������ò���
		String filepath = syspath + "/"; // �������ļ�·��
		
		if (appVersion.equals("windows")) {
			filename = ProjectConfig.getProjectConfig("JDKFileName_windows");
			filetype = ProjectConfig.getProjectConfig("JDKFilePostfix_windows");
		} else if (appVersion.equals("linux")) {
			filename = ProjectConfig.getProjectConfig("JDKFileName_linux");
			filetype = ProjectConfig.getProjectConfig("JDKFilePostfix_linux");
		} else {
			// ������
		}

		map.addParameter("DOC_NAME", filename);
		map.addParameter("DOC_SNAME", filesname);
		map.addParameter("DOC_TYPE", filetype);
		map.addParameter("DOC_PATH", filepath);
		value = this.downFiles(map);

		return value;
	}

	public BeanValue saveDefaultRole(RequestMap aRequestMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public BeanValue saveDefaultFirstPage(RequestMap aRequestMap)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public BeanValue downloadClient(RequestMap map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
