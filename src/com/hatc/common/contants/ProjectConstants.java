package com.hatc.common.contants;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ͨ��ģ�鳣������<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
* <b>version��</b>     VER1.00 2011-12-10<br/>
*        �޸���: ProjectConstants.FRAME_WINDOW ֵΪ:noButton.  �����ǲ���ʾ���ذ�ť.
	     ��:
	     processException(map, value, ex, ProjectConstants.FRAME_WINDOW, TtimsCode.AIRSTATE_SELECT_ERROR);	
**/
public class ProjectConstants {

	/**
	 * ��Ϣҳ��
	 */
	public static final String SYSTEM_RESULT = "result";

	/**
	 * ��¼����(Web��¼)
	 */
	public static final String WEB_LOGIN_TYPE = "W";

	/**
	 * �����¼����(Web��¼)
	 */
	public static final String SSO_LOGIN_TYPE = "SS0";

	/**
	 * ��¼��Ա��Ϣ������Session�еı�ʶ
	 */
	public static final String SESSION_REQIDENTITY = "session_reqidentity";

	/**
	 * ��¼��Ա��ǰ��ɫ������Session�еı�ʶ
	 */
	public static final String SESSION_USER_ROLE = "session_user_role";

	/**
	 * ��������ͱ�����Session�еı�ʶ
	 */
	public static final String SESSION_WINDOW_VER = "session_window_ver";

	/**
	 * ��¼��Ա��ǰ��ɫ��Ȩ��ע��Session�еı�ʶ
	 */
	public static final String SESSION_USER_ROLE_FUNCTION = "session_user_role_function";
	public static final String SESSION_USER_ROLE_FUNCTIONLIST = "session_user_role_functionlist";

	
	/**
	 * ��¼��Ա��ǰ��ɫ�б�����Session�еı�ʶ
	 */
	public static final String SESSION_USER_ROLE_LIST = "session_user_role_list";
	
	 
	/**
	 * ��¼��Ա��Ϣ������Session�еı�ʶ
	 */
	public static final String SESSION_USER = "session_user";

	/**
	 * �ص���Ϣ������Session�еı�ʶ
	 */
	public static final String SESSION_LOCAL = "session_local";

	/**
	 * ��¼��Ա������Session�еı�ʶ
	 */
	public static final String SESSION_USER_DEPT = "session_user_dept";

	/**
	 * �û�����������Session�еı�ʶ
	 */
	public static final String SESSION_SOUND = "session_sound";

	/**
	 * ����ҳ���ʶ
	 */
	public static final String POP_UP_WINDOW = "close";

	/**
	 * �����ҳ���ʶ
	 * TODO ningliyu 2011-12-10
	 */	
	public static final String FRAME_WINDOW = "noButton";
	//public static final String FRAME_WINDOW = "null";

	/*---------------------------ϵͳ����---------------------------------*/
	/**
	 * ���ƣ�MODI_PWD_INTERVAL         ���ͣ�I  ��λ����  ���ݣ�ǿ���޸�����ʱ����
	 */
	public static final String MODI_PWD_INTERVAL = "MODI_PWD_INTERVAL";
	
	/**
	 * ���ƣ�PASSWORD_LENGTH           ���ͣ�I  ��λ��λ  ���ݣ����볤��
	 */
	public static final String PASSWORD_LENGTH = "PASSWORD_LENGTH";
	
	/**
	 * ���ƣ����ƣ�PWD_SECRET_LEVEL     ���ͣ�O  ��λ����  ���ݣ�����ǿ�ȵȼ���1-5��
	 */
	public static final String PWD_SECRET_LEVEL = "PWD_SECRET_LEVEL";
	
	/**
	 * ���ƣ�RELOGON_TIMES             ���ͣ�I  ��λ����  ���ݣ���������½ʧ�ܴ���
	 */
	public static final String RELOGON_TIMES = "RELOGON_TIMES";
	
	/*--------------------------Ȩ����֤--------------------------------*/
	/**
	 * ���ƣ�LOG_FUNCTION_ID         ���ͣ�S  ���ݣ���¼��־�Ĺ���ID
	 */
	public static final String LOG_FUNCTION_ID = "LOG_FUNCTION_ID";
	
	/**
	 * ���ƣ�LOG_TEXT         ���ͣ�S  ���ݣ���־��ע����
	 */
	public static final String LOG_TEXT = "LOG_TEXT";
	
	/**
	 * ���ƣ�OPERATE_FUNCTION        ���ͣ�S  ���ݣ���������
	 */
	public static final String OPERATE_FUNCTION = "OPERATE_FUNCTION";
	
	/**
	 * ���ƣ�OPERATE_QUERY        ���ͣ�S  ���ݣ���ѯ����
	 */
	public static final String OPERATE_QUERY = "query";
	
	/**
	 * ���ƣ�OPERATE_NEW        ���ͣ�S  ���ݣ��½�����
	 */
	public static final String OPERATE_NEW = "new";
	
	/**
	 * ���ƣ�OPERATE_EDIT        ���ͣ�S  ���ݣ��༭����
	 */
	public static final String OPERATE_EDIT = "edit";
	
	/**
	 * ���ƣ�OPERATE_DELETE        ���ͣ�S  ���ݣ�ɾ������
	 */
	public static final String OPERATE_DELETE = "delete";
	
	/**
	 * ���ƣ�OPERATE_SAVE        ���ͣ�S  ���ݣ��������
	 */
	public static final String OPERATE_SAVE = "save";
	
	/**
     * ���ƣ�OPERATE_COPY        ���ͣ�S  ���ݣ����Ʋ���
     */
    public static final String OPERATE_COPY = "copy";
    
    /**
     * ���ƣ�SESSION_USER_UI_CODE        ���ͣ�S  ���ݣ�1 ����վ  2 ����Ա  3 �վ�
     */
    public static final String SESSION_USER_UI_CODE = "sessionUserUICode";
    
}
