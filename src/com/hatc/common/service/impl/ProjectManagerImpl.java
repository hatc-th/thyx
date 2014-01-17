package com.hatc.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.service.impl.BaseManager;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.common.businessdata.RoleFunction;
import com.hatc.common.businessdata.Site;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.hibernate.pojo.TbLocal;
import com.hatc.common.hibernate.pojo.TbOnline;
import com.hatc.common.hibernate.pojo.TbOnlineId;
import com.hatc.common.service.LogService;
import com.hatc.common.service.ProjectManager;
import com.hatc.common.service.model.Menus;
import com.hatc.common.service.security.SecurityService;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.servicestub.util.FilterCmd;
import com.hatc.common.servicestub.util.SqlCondition;
import com.hatc.common.web.config.UserResourceConfig;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ͨ��ģ��Manager�ӿ�ʵ��<br/>
* <b>author��</b>      ����<br/>
* <b>modify��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectManagerImpl extends BaseManager implements ProjectManager {
	
	
	protected SecurityService secuServ;
	
	public void setSecuServ(SecurityService secuServ) {
		this.secuServ = secuServ;
	}
	
	@Autowired
	protected LogService logServ;
	
	public void setLogServ(LogService logServ) {
		this.logServ = logServ;
	}
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * ���ɱ���ReportDefine����
	 * 
	 * @param name
	 *            ��������
	 * @return
	 */
	protected String buildReportDefineName(String name) {
		return name + new Date().getTime();
	}

	/**
	 * �������ݣ���ȡ�û���ʶ
	 * �����쳣: session ʧЧ
	 * 2012-1-1 ningliyu 
	 * @param map
	 *            ����ֵ����
	 * @return ReqIdentity �û���ʶ
	 * 
	 * 
	 */
	protected ReqIdentity getReqIdentity(RequestMap map) throws Exception
	{
		
		// ��ȡ���÷�ʽ(���ڿͻ�����Ҫ����Web���еĹ��ܣ������ͻ��˵���Web�˵�ʱ������Web�˿���û��½identity��Ϊ�յģ�������Ҫ���������һ�����ݵķ�װ)
		// ���tModeΪ��д��C��˵���ǿͻ��˵��õ�Web��
		String tMode = map.getString("tMode");
		  
		ReqIdentity identity = null;
		if(null != tMode && tMode.equals("C")){
			identity = this.communicationInit(map);
		} else {
			identity = (ReqIdentity) map.getObject(ProjectConstants.SESSION_REQIDENTITY);
//log.debug("into getReqIdentity��" + identity.getFunctionId() + ",sessionId: " + identity.getSessionId() + ",user:" + identity.getUserId() );

		}  
		
		// ����ط���ҪΪ�ͻ��˵���Web��fIdentity�ڹ������д���	 
		if(null == identity) 
			identity=(ReqIdentity)map.getObject("fIdentity");  
		
		//TODO ningliyu 2012-1-3
		//�ɿ���������쳣���Ự����getReqIdentityΪ��ʱ���׳��쳣������Ҫÿ��ҵ��ӿڴ���
		
		if(null == identity) 
		{
			//log.error("�޷�����identity ����...");
			logger.error("user.session.isNull, set 99999.16");
			BaseException bEx = new BaseException(); 
			bEx.setExceptionCode("99999.16");  
			bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
			throw bEx;
		}  

		/**
		if(identity.getLogonType().compareTo("C")!=0)
		{
			  
			if(map.getObject("request")==null)
			{  
				log.error("web ���ã��޷���ȡ�Ự�е��û���Ϣ��SESSION_USER");
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}   
			
			if(map.getObject(ProjectConstants.SESSION_USER)==null)
			{  
				log.error("web ���ã��޷���ȡ�Ự�е��û���Ϣ��SESSION_USER");
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}  
	
			 
			if(map.getObject(ProjectConstants.SESSION_USER_ROLE)==null)
			{
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode(ProjectCode.SERVICE_NOT_LOGON);  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
			//bEx.setMessage(UserResourceConfig.getInstance().getValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}
			 
			 
			if(map.getObject(ProjectConstants.SESSION_REQIDENTITY)==null)
			{
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode(ProjectCode.SERVICE_NOT_LOGON);  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				//bEx.setMessage(UserResourceConfig.getInstance().getValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}
		}
		
	    **/
		 
		
		return identity;
		
	}

	/**
	 * �������ݣ�����������˵�
	 * 
	 * @param list
	 *            ��ɫȨ�޼���
	 * @return List<Menus> �˵�����
	 * 
	 */
	protected List<Menus> function(List<RoleFunction> list) {
		// �ҳ�����web�����˵���
		ArrayList<RoleFunction> roleList = new ArrayList<RoleFunction>();
		String id = "";
		for (RoleFunction function : list) {
			id = function.getFunctionId();
			if (id.substring(0, 2).equals("BM")) {
				roleList.add(function);
			}
		}

		// �ҳ���һ��˵���
		List<Menus> menusList = new ArrayList<Menus>();
		List<RoleFunction> list1 = new ArrayList<RoleFunction>();
		list1.addAll(roleList);
		for (RoleFunction function : roleList) {
			id = function.getOrderId();
			if (id.substring(3, 7).equals("0000")) {
				Menus menu = new Menus();
				menu.setFunction(function);
				menu.setMark(id.substring(2, 3));
				menu.setSid(id.substring(2, 7));
				menusList.add(menu);
				list1.remove(function);
			}
		}
		// �ҳ��ڶ���˵���
		List<RoleFunction> list2 = new ArrayList<RoleFunction>();
		list2.addAll(list1);
		// BM80100 BM80200
		List<Menus> listTwo = null;
		Menus mu = null;
		for (Menus menu : menusList) {
			listTwo = new ArrayList<Menus>();
			for (RoleFunction function : list1) {
				id = function.getOrderId();
				if (id.substring(2, 3).equals(menu.getMark()) && !id.subSequence(3, 5).equals("00")
						&& id.subSequence(5, 7).equals("00")) {
					mu = new Menus();
					mu.setFunction(function);
					mu.setMark(id.substring(3, 5));
					mu.setSid(id.substring(2, 7));
					listTwo.add(mu);
					list2.remove(function);
				}
			}
			menu.setList(listTwo);
		}
		// �ҳ�������˵���
		List<Menus> listThere = null;
		for (Menus menu : menusList) {
			if (menu.getList() != null && menu.getList().size() > 0) {
				for (Menus menuThere : menu.getList()) {
				    listThere = new ArrayList<Menus>();
					for (RoleFunction function : list2) {
						id = function.getOrderId();
						if (id.substring(2, 3).equals(menu.getMark()) && id.substring(3, 5).equals(menuThere.getMark())
								&& !id.subSequence(5, 7).equals("00")) {
							mu = new Menus();
							mu.setFunction(function);
							mu.setMark(id.substring(5, 7));
							mu.setSid(id.substring(2, 7));
							listThere.add(mu);
						}
					}
					menuThere.setList(listThere.size() > 0 ? listThere : null);
				}
			}
		}
		return menusList;
	}

	/**
	 * �������ݣ��ͷŵ�ǰ��¼�Ự�����ж�����
	 * 
	 * @param identity
	 *            �������ݱ�ʶ
	 * @param map
	 *            ID�Ծ͵�������Map
	 * 
	 */
	protected void releaseObjectLock(ReqIdentity identity) throws Exception {

		try {
			secuServ.releaseObjectLockBySession(identity);
		} catch (Exception e) {
			log.error("�����û��Ự����ʧ��!",e);
			throw e;
			
		}
	}

	/**
	 * Ȩ�޼��
	 * @param requestMap ����ֵ����
	 * @param functionId Ȩ��ID
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean checkUserAuth(RequestMap requestMap, String functionId) throws Exception {
		String str_auth = requestMap.getString(ProjectConstants.SESSION_USER_ROLE_FUNCTION);
		return str_auth.indexOf(functionId) > -1;
	}

	/**
	 * ��¼��־
	 * @param aRequestMap	����ֵ����
	 * @param functionId	Ȩ��ID
	 * @param logText		��־����
	 * @throws Exception
	 */
	protected void writeLog(RequestMap aRequestMap, String functionId, String logText) throws Exception {

		logServ.log(logText);
	}
	
	/**
	 * �ͻ��˵���Web��ʱ����Ϣ��ʼ��
	 * @param identity �û���Ϣ
	 * @param map ���������Ϣ
	 */
	@SuppressWarnings("unchecked")
	private ReqIdentity communicationInit(RequestMap map) throws Exception
	{
		return null;
	}
	

	/**
	 * ͨ���ļ�����
	 * 
	 * @param RequestMap
	 *            ������� Map
	 * @return BeanValue ҵ������ɺ�Ľ����
	 */
	public BeanValue downFiles(RequestMap map) throws Exception
	{
		//TODO ningliyu 2012-1-3
		//�޸ĵ��Ựʧ�ܣ���ʱ��������������ʱ����Ȼ�ܹ������ļ�
		
		ReqIdentity aReqIdentity = getReqIdentity(map);
		   
		BeanValue value = new BeanValue();

		String filename = map.getString("DOC_NAME"); // �����ļ��� eg. "��Բ�㷨"
		String filetype = map.getString("DOC_TYPE"); // �����ļ���չ�� eg. "xls"
		
		String filesname = map.getString("DOC_SNAME"); // �ļ�������
		String filestype = map.getString("DOC_STYPE"); // �ļ�������չ��
		
		String filepath = map.getString("DOC_PATH"); // ����·�� "f:\\��ϸ���\\"

		if (filesname == null || filesname.equals("")) { // Ĭ�ϱ����ļ���ͬ�����ļ���
			filesname = filename;
		}
		
		if (filepath.indexOf("/usr/local/upload") == -1 && filepath.indexOf("/sdb/upload") == -1) {
			filepath = filepath.replaceAll("/", "\\\\\\\\");
		}
		
		if (filetype == null){
			filetype = filestype;
		}
		
		if (filestype == null){
			filestype = filetype;
		}

		String content_type = "";
		if (filetype.equalsIgnoreCase("DOC")) { // Microsoft Word �ĵ�
			content_type = "application/msword";
		} else if (filetype.equalsIgnoreCase("XLS")) { // Excel ���ӱ��
			content_type = "application/vnd.ms-excel";
		} else if (filetype.equalsIgnoreCase("PPT")) { // PowerPoint ��ʾ�ĸ�
			content_type = "application/vnd.ms-powerpoint";
		} else if (filetype.equalsIgnoreCase("PDF")) { // Acrobat(.pdf) �ļ�
			content_type = "application/pdf";
		} else if (filetype.equalsIgnoreCase("ZIP")) { // Zip ����
			content_type = "application/zip";
		} else if (filetype.equalsIgnoreCase(".TAR.GZ")) { // Gzip ����
			content_type = "application/x-gzip";
		} else if (filetype.equalsIgnoreCase("BMP")) { // X windows λͼͼ��
			content_type = "image/x-xbitmap";
		} else if (filetype.equalsIgnoreCase("GIF")) { // GIF ͼ��
			content_type = "image/gif";
		} else if (filetype.equalsIgnoreCase("JPEG")) { // JPEG ͼ��
			content_type = "image/jpeg";
		} else if (filetype.equalsIgnoreCase("PNG")) { // PNG ͼ��
			content_type = "image/png";
		} else if (filetype.equalsIgnoreCase("TIFF")) { // TIFF ͼ��
			content_type = "image/tiff";
		} else if (filetype.equalsIgnoreCase("TXT")) { // TXT �ı�
			content_type = "text/plain";
		} else if (filetype.equalsIgnoreCase("XML")) { // XML
			content_type = "text/xml";
		} else { // δʶ��
			content_type = "application/octet-stream";
		}

		value.addRequestMap("filename", filename +  "." + filetype);
		value.addRequestMap("filesname", filesname + "." + filestype);
		value.addRequestMap("filetype", content_type);
		value.addRequestMap("filepath", filepath);

		String function_id = map.getString("function_id");

		if (function_id != null && !function_id.equals("")) {
			logServ.sysLog(aReqIdentity.getUserId() + "�����ļ���" +filename +  "." + filetype);
		}

		return value;
	}
	/*
	 * �������¼����
	 * @auth chenzj
	 * @param ��¼�����ֶ���
	 * @return ����¼����
	 * **/
	public String getRelogonTimes()throws Exception{
		String sql= "select  t.upper  from TB_PARAMETER_SETUP t where t.para_name ='RELOGON_TIMES' ";
		String[] str = {"upper"};
		List<String[]> param =  dao.getObjects(sql,str);
		String pwdErrNum = param.get(0)[0];
		return pwdErrNum;
	}
	
}
