package com.hatc.common.contants;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ͨ��ģ���쳣����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectCode {
	/* ------------------------------�쳣��:����˴���------------------------------- */
	/** δ֪���� */
	public static final String SERVICE_UNKNOWN_ERR = "99999.-1";

	/** ������� */
	public static final String SERVICE_NETWORK_ERR = "99999.10";

	/** �û������������ */
	public static final String SERVICE_BAD_PWD = "99999.11";

	/** ���ݿ����Ӵ��� */
	public static final String SERVICE_DB_ERR = "99999.12";

	/** ����δ��Ȩ���� */
	public static final String SERVICE_AUTH_ERR = "99999.13";

	/** ��ƥ��Ŀͻ���IP */
	public static final String SERVICE_BAD_IP_CONF = "99999.14";

	/** �û�����һ����¼ */
	public static final String SERVICE_KICK_OUT = "99999.15";

	/** �û�δ��¼,�����һ����¼,���¼��ʱ */
	public static final String SERVICE_NOT_LOGON = "99999.16";

	/** �������ʧ�� */
	public static final String SERVICE_LOCK_FAIL = "99999.17";

	/** �û��ѵ�¼ */
	public static final String SERVICE_ALREADY_LOGON = "99999.18";

	/** ����ĵ�¼ģʽ */
	public static final String BAD_LOGIN_MODE = "99999.23";

	/** ��Ч�û� */
	public static final String SERVICE_INVALID_USER = "99999.24";
	
	/** ���䱾�ķ������汾 */
	public static final String INCOMPATIBLE_VER = "99999.27";
	
	/** �û���ʧЧ �����µ�¼ */
	public static final String USER_INVALID_RELOGON = "99999.30";

	/** �û��ѹ��� */
	public static final String USER_INVALID = "99999.38";
	
	/** ��Ч��λ���û� */
	public static final String SERVICE_INVALID_DEPARTMENT = "99999.39";

	/*--------------------------------�쳣��:WEB�쳣��-----------------------------*/
	/* δ֪���� */
	public static final String UNKNOWN_ERROR = "00000.00000";

	/* ������δ�ҵ� */
	public static final String CLASS_NOT_FOUND_ERROR = "00000.00001";

	/* ����������� */
	public static final String IO_ERROR = "00000.00002";

	/* �ļ�δ�ҵ� */
	public static final String FILE_NOT_FOUND_ERROR = "00000.00003";

	/* �û���¼ */
	/** �û���¼_�û������� */
	public static final String USER_LOGIN_NAME_ERROR = "00001.00001";

	/** �û���¼_������� */
	public static final String USER_LOGIN_PASSWORD_ERROR = "00001.00002";

	/** �û���¼ʧ�ܴ��������޶����� */
	public static final String USER_LOGIN_NUMBER_ERROR = "00001.00003";

	/** �û���¼�����г��ִ��� */
	public static final String USER_LOGIN_ERROR = "00001.00004";

	/** �û���ɫ�л����ִ��� * */
	public static final String USER_SWICTH_ROLE_ERROR = "00001.00012";

	/** �л�����ҳ���ִ��� * */
	public static final String SWICTH_FIRSTPAGE_ERROR = "00001.00013";

	/** ԰������¼���ִ��� * */
	public static final String SSO_LOGIN_ERROR = "00001.00014";

	/** FireFox �����ֻ�����¼һ���û� * */
	public static final String NO_IE_LOGIN_ERROR = "00001.00015";

	/** �û��ӿͻ��˵�¼ʧ�� * */
	public static final String CS_LOGIN_ERROR = "00001.00016";


	/** ��ҳ */
	/* ��ҳ - �޸����� - �����ɹ� */
	public static final String USER_PASSWORD_SUCCEED = "00000.00101";

	/* ��ҳ - �˳� - �����ɹ� */
	public static final String USER_LOGOUT_SUCCEED = "00000.00201";

	/* ��ҳ - �޸����� - ����ʧ�� */
	public static final String USER_PASSWORD_FAIL = "00000.10101";

	/* ��ҳ - �޸����� - ��ʼ��ʧ�� */
	public static final String USER_INITPASSWORD_FAIL = "00000.10102";

	/* ��ҳ - �˳� - ����ʧ�� */
	public static final String USER_LOGOUT_FAIL = "00000.10201";

	/* �ļ������쳣 */
	public static final String FILE_DOWN_ERROR = "00000.90001";

	
	/** ֪ͨ���� */
	/* �½�֪ͨ��Ϣ - ����ɹ� */
	public static final String MSG_NEWSAVE_SUCCEED = "Z0000.00001";

	/* �½�֪ͨ��Ϣ - ���ͳɹ� */
	public static final String MSG_NEWINSERT_SUCCEED = "Z0000.00002";

	/* ����֪ͨ��Ϣ - �༭���� - ����ɹ� */
	public static final String MSG_EDITSAVE_SUCCEED = "Z0000.00101";

	/* ����֪ͨ��Ϣ - �༭���� - ���ͳɹ� */
	public static final String MSG_EDITUPDATE_SUCCEED = "Z0000.00102";

	/* ����֪ͨ��Ϣ - ɾ���ɹ� */
	public static final String MSG_DELETESAVE_SUCCEED = "Z0000.00103";

	/* �½��ظ���Ϣ - ���ͳɹ� */
	public static final String MSG_REPLYINSERT_SUCCEED = "Z0000.00201";

	/* ����֪ͨ��Ϣ - ɾ���ɹ� */
	public static final String MSG_DELETEGOT_SUCCEED = "Z0000.00202";

	/* ����֪ͨ��Ϣ - ����ɾ���ɹ� */
	public static final String MSG_DELETEGOT_QUERY_SUCCEED = "Z0000.00203";

	/* ���ջظ���Ϣ - ɾ���ɹ� */
	public static final String MSG_DELETERE_SUCCEED = "Z0000.00204";

	/* ���ջظ���Ϣ - ����ɾ���ɹ� */
	public static final String MSG_DELETERE_QUERY_SUCCEED = "Z0000.00205";

	/* �ѷ�֪ͨ��Ϣ - ɾ���ɹ� */
	public static final String MSG_DELETESENT_SUCCEED = "Z0000.00301";

	/* �ѷ�֪ͨ��Ϣ - ����ɾ���ɹ� */
	public static final String MSG_DELETESENT_QUERY_SUCCEED = "Z0000.00302";

	/* ԭʼ��Ϣ - ɾ���ɹ� */
	public static final String MSG_DELETEORIG_SUCCEED = "Z0000.00401";

	/* ��Ϣת�� - ����ɹ� */
	public static final String MSG_TRANSSAVE_SUCCEED = "Z0000.00501";

	/* ��Ϣת�� - ���ͳɹ� */
	public static final String MSG_TRANSINSERT_SUCCEED = "Z0000.00502";

	/* ��Ϣ���� - ����ɹ� */
	public static final String MSG_COPYSAVE_SUCCEED = "Z0000.00503";

	/* ��Ϣ���� - ���ͳɹ� */
	public static final String MSG_COPYINSERT_SUCCEED = "Z0000.00504";

	/* �û����趨 - �½��û���ɹ� */
	public static final String MSGGROUP_INSERT_SUCCEED = "Z0000.00601";

	/* �û����趨 - �༭�û���ɹ� */
	public static final String MSGGROUP_UPDATE_SUCCEED = "Z0000.00602";

	/* �û����趨 - ɾ���û���ɹ� */
	public static final String MSGGROUP_DELETE_SUCCEED = "Z0000.00603";

	/* ****** ****** ****** ****** ****** ****** */
	
	/* �½�֪ͨ��Ϣ - ����ʧ�� */
	public static final String MSG_NEWSAVE_FAIL = "Z0000.10001";

	/* �½�֪ͨ��Ϣ - ����ʧ�� */
	public static final String MSG_NEWINSERT_FAIL = "Z0000.10002";

	/* ����֪ͨ��Ϣ - �༭���� - ����ʧ�� */
	public static final String MSG_EDITSAVE_FAIL = "Z0000.10101";

	/* ����֪ͨ��Ϣ - �༭���� - ����ʧ�� */
	public static final String MSG_EDITUPDATE_FAIL = "Z0000.10102";

	/* ����֪ͨ��Ϣ - ɾ��ʧ�� */
	public static final String MSG_DELETESAVE_FAIL = "Z0000.10103";
	
	/* ����֪ͨ��Ϣ - �б� - ��ѯʧ�� */
	public static final String MSG_BCKLIST_FAIL = "Z0000.10711";

	/* ����֪ͨ��Ϣ - �༭���� - ��ʼ��ʧ�� */
	public static final String MSG_INITEDITSAVE_FAIL = "Z0000.10712";
	
	/* �½��ظ���Ϣ - ����ʧ�� */
	public static final String MSG_REPLYINSERT_FAIL = "Z0000.10201";


	/* ԭʼ��Ϣ - ɾ��ʧ�� */
	public static final String MSG_DELETEORIG_FAIL = "Z0000.10401";

	/* ��Ϣת�� - ����ʧ�� */
	public static final String MSG_TRANSSAVE_FAIL = "Z0000.10501";

	/* ��Ϣת�� - ����ʧ�� */
	public static final String MSG_TRANSINSERT_FAIL = "Z0000.10502";

	/* ��Ϣ���� - ����ʧ�� */
	public static final String MSG_COPYSAVE_FAIL = "Z0000.10503";

	/* ��Ϣ���� - ����ʧ�� */
	public static final String MSG_COPYINSERT_FAIL = "Z0000.10504";

	/* �û����趨 - �½��û���ʧ�� */
	public static final String MSGGROUP_INSERT_FAIL = "Z0000.10601";

	/* �û����趨 - �༭�û���ʧ�� */
	public static final String MSGGROUP_UPDATE_FAIL = "Z0000.10602";

	/* �û����趨 - ɾ���û���ʧ�� */
	public static final String MSGGROUP_DELETE_FAIL = "Z0000.10603";

	/* �½�֪ͨ��Ϣ - ��ʼ��ʧ�� */
	public static final String MSG_INITNEW_FAIL = "Z0000.10701";
	

	/* ����֪ͨ��Ϣ - ɾ��ʧ�� */
	public static final String MSG_DELETEGOT_FAIL = "Z0000.10202";

	/* ����֪ͨ��Ϣ - ����ɾ��ʧ�� */
	public static final String MSG_DELETEGOT_QUERY_FAIL = "Z0000.10203";

	/* ����֪ͨ��Ϣ - �б� - ��ѯʧ�� */
	public static final String MSG_GOTLIST_FAIL = "Z0000.10721";

	/* ����֪ͨ��Ϣ - �鿴��ʼ��ʧ�� */
	public static final String MSG_INITGOT_FAIL = "Z0000.10722";

	/* ����֪ͨ��Ϣ - �ظ���ʼ��ʧ�� */
	public static final String MSG_INITREPLY_FAIL = "Z0000.10723";
	
	/* �ѷ�֪ͨ��Ϣ - ɾ��ʧ�� */
	public static final String MSG_DELETESENT_FAIL = "Z0000.10301";

	/* �ѷ�֪ͨ��Ϣ - ����ɾ��ʧ�� */
	public static final String MSG_DELETESENT_QUERY_FAIL = "Z0000.10302";
	

	/* ���ջظ���Ϣ - �б� - ��ѯʧ�� */
	public static final String MSG_RELIST_FAIL = "Z0000.10724";

	/* ���ջظ���Ϣ - �鿴��ʼ��ʧ�� */
	public static final String MSG_INITRE_FAIL = "Z0000.10725";
	
	/* ���ջظ���Ϣ - ɾ��ʧ�� */
	public static final String MSG_DELETERE_FAIL = "Z0000.10204";

	/* ���ջظ���Ϣ - ����ɾ��ʧ�� */
	public static final String MSG_DELETERE_QUERY_FAIL = "Z0000.10205";
	

	/* �ѷ�֪ͨ��Ϣ - �б� - ��ѯʧ�� */
	public static final String MSG_SENTLIST_FAIL = "Z0000.10731";

	/* �ѷ�֪ͨ��Ϣ - �鿴��ʼ��ʧ�� */
	public static final String MSG_INITSENT_FAIL = "Z0000.10732";
	

	/* ԭʼ��Ϣ - �鿴��ʼ��ʧ�� */
	public static final String MSG_INITORIG_FAIL = "Z0000.10741";

	/* ��Ϣת�� - ��ʼ��ʧ�� */
	public static final String MSG_INITTRANS_FAIL = "Z0000.10751";

	/* ��Ϣ���� - ��ʼ��ʧ�� */
	public static final String MSG_INITCOPY_FAIL = "Z0000.10752";

	/* �û����趨 - ��ʼ��ʧ�� */
	public static final String MSGGROUP_INIT_FAIL = "Z0000.10761";
	
	
	/*------------------------�û��Զ��屨��----------------------*/
	/** �û��Զ��屨����ɹ� */
	public static final String USER_REPORT_SAVE_SUCCEED = "Z0001.00001";
	
	/** �û��Զ��屨�����ɹ� */
	public static final String USER_REPORT_PUBLISH_SUCCEED = "Z0001.00003";
	
	/** �û��Զ��屨����ʧ�� */
	public static final String USER_REPORT_SAVE_FAIL = "Z0001.10001";
	
	/** �û��Զ��屨��ɾ���ɹ� */
	public static final String USER_REPORT_DELETE_SUCCEED = "Z0001.00002";
	
	/** �û��Զ��屨��ɾ��ʧ�� */
	public static final String USER_REPORT_DELETE_FAIL = "Z0001.10002";
	
	/** �û��Զ��屨���б��ѯʧ�� */
	public static final String USER_REPORT_LIST_FAIL = "Z0001.10003";
	
	/** �û��Զ��屨������Ԥ��ʧ�� */
	public static final String USER_REPORT_PREVIEW_FAIL = "Z0001.10004";
	
	/** �û��Զ��屨���򵼳�ʼ��ʧ�� */
	public static final String USER_REPORT_INIT_FAIL = "Z0001.10005";
	
	/*------------------------�û��Զ��屨��----------------------*/
	
	/* ��Ŀ������� - �½����³ɹ� */
	public static final String UPDATE_SAVE_SUCCEED = "30000.00001";
	
	/* ��Ŀ������� - �½�����ʧ�� */
	public static final String UPDATE_SAVE_ERROR = "30000.10001";
	
	/* ��Ŀ������� - ��ѯ����ʧ�� */
	public static final String UPDATE_SEARCH_ERROR = "30000.10002";
	
	/* ��Ŀ������� - ��ѯ�����б�ʧ�� */
	public static final String UPDATE_SEARCHLIST_ERROR = "30000.10003";

	
	/* ������������ - �½����̴���ɹ� */
	public static final String FLOW_SAVE_SUCCEED = "30000.00011";
	
	/* ������������ - �½����̴���ʧ�� */
	public static final String FLOW_SAVE_ERROR = "30000.10011";
	
	/* ��Ŀ������������ - ��ѯ��������ʧ�� */
	public static final String FLOW_SEARCH_ERROR = "30000.10012";
	
	/* ��Ŀ������������ - ��ѯ���������б�ʧ�� */
	public static final String FLOW_SEARCHLIST_ERROR = "30000.10013";

	
	/* �ĵ����� - �½��ĵ��ɹ� */
	public static final String DOCUMENT_SAVE_SUCCEED = "30000.00021";
	
	/* �ĵ����� - �½��ĵ�ʧ�� */
	public static final String DOCUMENT_SAVE_ERROR = "30000.10021";
	
	/* �ĵ����� - ��ѯ�ĵ�ʧ�� */
	public static final String DOCUMENT_SEARCH_ERROR = "30000.10022";
	
	/* �ĵ����� - ��ѯ�ĵ��б�ʧ�� */
	public static final String DOCUMENT_SEARCHLIST_ERROR = "30000.10023";
	
	/* �ĵ����� - ɾ���ĵ��ɹ� */
	public static final String DOCUMENT_DELETE_SUCCEED = "30000.00024";
	
	/* �ĵ����� - ɾ���ĵ�ʧ�� */
	public static final String DOCUMENT_DELETE_ERROR = "30000.10024";
	/* �ĵ����� - �����ĵ��ɹ� */
	public static final String DOCUMENT_UPDATE_SUCCEED = "30000.00025";

	
}
