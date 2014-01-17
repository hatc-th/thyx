package com.hatc.base.contants;


public class ExceptionCode {

	/* ------------------------------�쳣��:����˴���------------------------------- */
	/** δ֪���� */
	public static final String SERVICE_UNKNOWN_ERR = "00000.00000";

	/** ������� */
	public static final String SERVICE_NETWORK_ERR = "00000.00010";

	/** ���ݿ����Ӵ��� */
	public static final String SERVICE_DB_ERR = "00000.00012";

	/** ����δ��Ȩ���� */
	public static final String SERVICE_AUTH_ERR = "00000.00013";

	/** ��ƥ��Ŀͻ���IP */
	public static final String SERVICE_BAD_IP_CONF = "00000.00014";

	/** �û�����һ����¼ */
	public static final String SERVICE_KICK_OUT = "00000.00015";

	/** �û�δ��¼,�����һ����¼,���¼��ʱ */
	public static final String SERVICE_NOT_LOGON = "00000.00016";

	/** �������ʧ�� */
	public static final String SERVICE_LOCK_FAIL = "00000.00017";

	/** �û��ѵ�¼ */
	public static final String SERVICE_ALREADY_LOGON = "00000.00018";

	/** ������ĵ�¼ģʽ */
	public static final String BAD_LOGIN_MODE = "00000.00023";

	/** ��Ч�û� */
	public static final String SERVICE_INVALID_USER = "00000.00024";

	/** ���䱾�ķ������汾 */
	public static final String INCOMPATIBLE_VER = "00000.00027";

	/*--------------------------------�쳣��:WEB�쳣��-----------------------------*/
	/* δ֪���� */
	public static final String WEB_UNKNOWN_ERR = "00001.00000";

	/* �û���¼ */
	/** �û���¼_�û������������ */
	public static final String USER_LOGIN_U_P_ERROR = "00001.00001";

	/** �û���¼_��ѯ�û������� */
	public static final String USER_LOGIN_QUERY_ERROR = "00001.00002";

	/** �û���¼ʧ�ܴ��������޶����� */
	public static final String USER_LOGIN_COUNT_ERROR = "00001.00003";

	/** �û���¼�����г��ִ��� */
	public static final String USER_LOGIN_ERROR = "00001.00004";

	/** ��ҳ - �޸������ʼ���쳣 * */
	public static final String USER_INITPWD_FAIL = "00001.00010";

	/** ��ҳ - �޸�����ʧ�� * */
	public static final String USER_EDITPWD_FAIL = "00001.00011";

	/** ��ҳ - �˳�ϵͳʧ�� */
	public static final String SYS_LOGOUT_FAIL = "00001.00099";

	/** �û���ɫ�л����ִ��� * */
	public static final String USER_SWICTH_ROLE_ERROR = "00001.00012";

	/** �л�����ҳ���ִ��� * */
	public static final String SWICTH_FIRSTPAGE_ERROR = "00001.00013";

	/** ԰������¼���ִ��� * */
	public static final String SSO_LOGIN_ERROR = "00001.00014";
	
	/** FireFox �����ֻ�����¼һ���û� * */
	public static final String NO_IE_LOGIN_ERROR = "00001.00015";
	
	/**  Ҫ��ӡ�ļƻ������� */
	public static final String PRINT_FP_FILE_ERROR = "00001.00100";
	
	/**  ��ӡ���μƻ����� */
	public static final String PRINT_FP_ERROR = "00001.00101";

	/* �ƻ����� - ��ȼƻ� */
	/** �ƻ����� - ��ȼƻ� - �༭��ȼƻ��б�ʧ�� */
	public static final String YEARPROJECT_EDITLIST_FAIL = "10100.00001";
	
	/** �ƻ����� - ��ȼƻ� - ��ѯ��ȼƻ��б�ʧ�� */
	public static final String YEARPROJECT_GETLIST_FAIL = "10100.00002";
	
	/** �ƻ����� - ��ȼƻ� - �༭/�½���ȼƻ�ʧ�� */
	public static final String YEARPROJECT_SET_FAIL = "10100.00003";

	/* �ƻ����� - �����Է��¼ƻ� */
	/** �����Է��¼ƻ��༭ - ɾ�������Է��¼ƻ�ʧ�� */
	
	/* �ƻ�����.�ճ��μƻ� */
	/** ��ѯ�ճ��μƻ��б���Ϣ�쳣 */
	public static final String DAY_PLAN_LIST_QUERY_ERROR = "10500.00001";

	/** ��ѯ�ճ��μƻ���Ϣ�쳣 */
	public static final String DAY_PLAN_INFO_QUERY_ERROR = "10500.00002";

	/** �����ճ��μƻ�ͼ����Ϣ�쳣 */
	public static final String DAY_PLAN_REPORT_ERROR = "10500.00003";

	/** ��ѯ�ܴμƻ���Ϣ�쳣 */
	public static final String QUERY_EVERY_PLAN_ERROR = "10500.00004";

	/* ��Դ����.�ɻ���Դ.�ɻ���Դ���� */
	/** ��ѯ�ɻ��б���Ϣ�쳣 */
	public static final String PLANE_LIST_QUERY_ERROR = "30101.00001";

	/** ��ѯ�ɻ���Ϣ�쳣 */
	public static final String PLANE_INFO_QUERY_ERROR = "30101.00002";

	/** ����ɻ���Ϣ�쳣 */
	public static final String PLANE_SAVE_ERROR = "30101.00003";

	/** ���·ɻ���Ϣ�쳣 */
	public static final String PLANE_UPDATE_ERROR = "30101.00004";

	/** ɾ���ɻ���Ϣ�쳣 */
	public static final String PLANE_DELETE_ERROR = "30101.00005";

	/** �½��ɻ���Ϣ��ʼ���쳣 */
	public static final String PLANE_CREATE_INIT_ERROR = "30101.00006";

	/** �ɻ����ظ��쳣 */
	public static final String PLANE_CODE_ERROR = "30101.00007";

	/** ɾ���ɻ���Ϣ�쳣 -- �ɻ��Ѿ���ʹ�� */
	public static final String PLANE_DELETE_Fk_ERROR = "30101.00008";

	/* ��Դ����.�ɻ���Դ.�ɻ������Դ��ѯ */
	/** ��ѯ�ɻ����ټ���Ϣ�쳣 */
	public static final String PLANE_PART_QUERY_ERROR = "30102.00001";

	/** ��ѯ�ɻ�������Ϣ�쳣 */
	public static final String PLANE_EFFECT_QUERY_ERROR = "30102.00002";

	/* ��Դ���� - �����豸 - �����豸��Ƶ����� */
	/** �����豸��Ƶ����� - ��ѯ�б��쳣 * */
	public static final String AeroEquipList_QRY_FAIL = "30201.00001";

	/** �����豸��Ƶ����� - ��ѯ�����쳣 * */
	public static final String AeroEquipInfo_QRY_FAIL = "30201.00002";

	/** �����豸��Ƶ����� - ���������쳣 * */
	public static final String AeroEquipInfo_SET_FAIL = "30201.00003";

	/* ��Դ���� - ������Դ */
	/** ������Դ - ������Ա�������� - ��ʼ���쳣 * */
	public static final String PILOT_UPLOAD_FAIL_INIT = "30401.00001";

	/** ������Դ - ������Ա�������� - �����쳣 * */
	public static final String PILOT_UPLOAD_FAIL = "30401.00002";

	/** ������Դ - ������Ա��ѯ�б��쳣 * */
	public static final String PILOTLIST_QRY_FAIL = "30402.00001";

	/** ������Դ - ������Ա��ѯ�����쳣 * */
	public static final String PILOTINFO_QRY_FAIL = "30402.00002";

	/** ������Դ - ������Աɾ���쳣 * */
	public static final String PILOTLIST_DEL_FAIL = "30402.00003";

	/** ������Դ - ������Ա�½� - ��ʼ���쳣 * */
	public static final String PILOT_SET_FAIL_INIT = "30402.00004";

	/** ������Դ - ������Ա�½��쳣 * */
	public static final String PILOT_SET_FAIL = "30402.00005";

	/** ������Դ - ������Ա�༭�����쳣 * */
	public static final String PILOT_EDIT_FAIL = "30402.00006";

	/** ������Դ - ������Աɾ���쳣 - ��ѡ������Ա�ѱ�ʹ�ã�������ɾ���� */
	public static final String PILOTLIST_DEL_FAIL_XML = "30402.00007";

	/** ������Դ - ������Ա�½��쳣 - ������Ա�����ظ� * */
	public static final String PILOT_SET_FAIL_UC = "30402.00008";
	
	/** ������Դ - ������Ա�½��쳣 - ������Ա�������� * */
	public static final String PILOT_UPDATE_ABILITY_FAIL = "30402.00009";

	/* ��������(���ⱨ��) */
	/** ��ѯ���ⱨ���б��쳣 */
	public static final String DEFECT_LIST_QUERY_ERROR = "40100.00001";

	/** ��ѯ���ⱨ����Ϣ�쳣 */
	public static final String DEFECT_INFO_QUERY_ERROR = "40100.00002";

	/** �������ⱨ���쳣 */
	public static final String DEFECT_INFO_SAVE_ERROR = "40100.00003";

	/** �������ⱨ���쳣 */
	public static final String DEFECT_INFO_UPDATE_ERROR = "40100.00004";

	/** �ύ���ⱨ���쳣 */
	public static final String DEFECT_INFO_SUBMIT_ERROR = "40100.00005";

	/** ���洦�����ⱨ���쳣 */
	public static final String DEFECT_INFO_DISPOSE_ERROR = "40100.00006";

	/** �ر����ⱨ���쳣 */
	public static final String DEFECT_INFO_DONE_ERROR = "40100.00007";

	/** �������½����ⱨ���쳣 */
	public static final String FROWARD_NEW_DEFECT_ERROR = "40100.00008";

	/** �������������ⱨ���쳣 */
	public static final String FROWARD_UPDATE_DEFECT_ERROR = "40100.00009";

	/** ���������������ⱨ���쳣 */
	public static final String FROWARD_DISPOSE_DEFECT_ERROR = "40100.00010";

	/** ���ⱨ�汻���� */
	public static final String FROWARD_DISPOSE_DEFECT_LOCK = "40100.00011";

	/** ɾ�����ⱨ�� */
	public static final String DEFECT_INFO_DELETE_ERROR = "40100.00012";

	/* ��Ϣά�� */
	/** ��Ϣά�� - ������Ŀ�����ڳ������б��ѯ�쳣 * */
	public static final String RESEARCH_QRY_FAIL_LIST = "60300.00001";

	/** ��Ϣά�� - ������Ŀ�����ڳ����������ѯ�쳣 * */
	public static final String RESEARCH_QRY_FAIL_INFO = "60300.00002";

	/** ��Ϣά�� - ������Ŀ�����ڳ����ȸ����쳣 * */
	public static final String RESEARCH_SET_FAIL = "60300.00003";
	
	/** ��Ϣά�� - ָ������ϵͳ��Ϣ�����쳣 */
	public static final String INFO_UPLOADDOC_FAIL = "60500.00001";

	/* ֪ͨ���� */
	/** δ֪�쳣 */
	public static final String UNKNOW_EX = "70100.00000";

	/** �½���Ϣ - ��ʼ���쳣 */
	public static final String MSG_SET_INIT_FAIL = "70100.00001";

	/** ֪ͨ��Ϣ - ֪ͨ��Ϣ�����쳣 */
	public static final String MSG_INSERT_FAIL = "70100.00002";

	/** ����֪ͨ - ����֪ͨ�����쳣 */
	public static final String MSG_INSERT_FAIL_FLY = "70100.00003";

	/** �������� - ���������´��쳣 */
	public static final String MSG_INSERT_FAIL_EX = "70100.00004";

	/** ֪ͨ��Ϣ - ֪ͨ��Ϣ�����쳣 */
	public static final String MSG_BCKUP_FAIL = "70100.00005";

	/** ����֪ͨ - ����֪ͨ�����쳣 */
	public static final String MSG_BCKUP_FAIL_FLY = "70100.00006";

	/** �������� - �������������쳣 */
	public static final String MSG_BCKUP_FAIL_EX = "70100.00007";

	/** ��ѯ�б��쳣 */
	public static final String MSG_GETLIST_FAIL = "70100.00010";

	/** ��ѯ�����쳣 */
	public static final String MSG_GETINFO_FAIL = "70100.00011";

	/** ɾ����Ϣ�쳣 */
	public static final String MSG_DEL_FAIL = "70100.00012";

	/** ����֪ͨ��Ϣ - �༭������Ϣ�쳣 */
	public static final String MSG_UPDATE_FAIL = "70100.00013";

	/** �ظ���Ϣ - ��ʼ���쳣 */
	public static final String MSG_REMSG_INIT_FAIL = "70100.00020";

	/** �ظ���Ϣ - �ظ��쳣 */
	public static final String MSG_INSERT_FAIL_RE = "70100.00021";

	/** �û����趨 - ��ʼ���쳣 */
	public static final String MSG_GROUP_INIT_FAIL = "70500.00001";

	/** �û����趨 - �½��û����쳣 */
	public static final String MSG_GROUP_INSERT_FAIL = "70500.00002";

	/** �û����趨 - ɾ���û����쳣 */
	public static final String MSG_GROUP_DELETE_FAIL = "70500.00003";

	/** �û����趨 - �����û����쳣 */
	public static final String MSG_GROUP_UPDATE_FAIL = "70500.00004";

	/* �ƻ�����.�ƻ����� */
	/** ��ѯ�����б���Ϣ�쳣 */
	public static final String POST_EDIT_LIST_QUERY_ERROR = "10700.00001";

	/** ����ƻ���Ϣ�쳣 */
	public static final String POST_PLAN_SAVE_ERROR = "10700.00002";

	/** �����ƻ���Ϣ�쳣 */
	public static final String POST_PLAN_SUBMIT_ERROR = "10700.00003";

	/** ��ѯ�����б���Ϣ�쳣 */
	public static final String POST_PLAN_LIST_QUERY_ERROR = "10700.00004";

	/** ��¼��ɺ�ļƻ����ܱ��� */
	public static final String POST_PLAN_ERROR = "10700.00005";

	/* �Է����� */
	/** ȡ���Է������쳣 */
	public static final String TASK_ARILINE_ERROR = "10400.00000";
	/** ȡ���Է������쳣 */
	public static final String TASK_CANCEL_ERROR = "10400.00001";

	/** ȷ���Է������쳣 */
	public static final String TASK_CONFIRM_ERROR = "10400.00002";

	/** �����Է������쳣 */
	public static final String TASK_CREATE_ERROR = "10400.00003";

	/** ɾ���Է������ĵ��쳣 */
	public static final String TASK_DELDOC_ERROR = "10400.00004";

	/** ɾ���Է������쳣 */
	public static final String TASK_DELTASK_ERROR = "10400.00005";

	/** ��ʼ�������ĵ��쳣 */
	public static final String TASK_INIT_UPLOADDOC_ERROR = "10400.00008";

	/** �޸��Է������쳣 */
	public static final String TASK_MODIFY_ERROR = "10400.00009";

	/** ��ѯ�Է������쳣 */
	public static final String TASK_QUERY_ERROR = "10400.00010";

	/** ��ʼ���޸��Է������쳣 */
	public static final String TASK_INIT_MODIFY_ERROR = "10400.00011";

	/** ��ѯ�Է�������Ϣ�����쳣 */
	public static final String TASK_SEARCH_DETAIL_ERROR = "10400.00012";

	/** �ϴ��Է���������ĵ��쳣 */
	public static final String TASK_UPLOAD_DOC_ERROR = "10400.00013";

	/** �ϴ��Է���������ĵ�����5M */
	public static final String TASK_UPLOAD_DOC_2BIG = "10400.00014";

	/** �����ϻ��Էɹ���ʦ�Ǽǲ�ѯ�б� */
	public static final String TASK_ENGINEER_LIST = "10400.00015";

	/** �ϻ��Էɹ���ʦ�Ǽ� */
	public static final String TASK_ENGINEER_QUERY = "10400.00016";

	/** �ϻ��Էɹ���ʦ�Ǽ� */
	public static final String TASK_ENGINEER_CREATE = "10400.00017";

	/* ���п���.�ճ��μƻ�ʵʩ */
	/** ��ѯ�ճ���ʵʩ�ƻ��б���Ϣ�쳣 */
	public static final String ACT_DAY_PLAN_LIST_QUERY_ERROR = "20100.00001";

	/** ��ѯ�ճ���ʵʩ�ƻ���Ϣ�쳣 */
	public static final String ACT_DAY_PLAN_INFO_QUERY_ERROR = "20100.00002";

	/* ���п���.���п�����Ч�� */
	/** ��ѯ���п�����Ч���б� */
	public static final String TASK_VALIDITY_LIST_QUERY_ERROR = "20300.00001";

	/** ������Ч��(��Ч) */
	public static final String TASK_VALIDITY_YES_ERROR = "20300.00002";

	/** ������Ч��(��Ч) */
	public static final String TASK_VALIDITY_NO_ERROR = "20300.00003";

	/* ��Ȼ����ʱ�̱� */
	/** ��ѯ��Ȼ����ʱ�̱��ĵ��쳣 */
	public static final String DNTIME_SEARCH_ERROR = "60600.00001";

	/** ������Ȼ����ʱ�̱��ĵ��쳣 */
	public static final String DNTIME_UPLOAD_ERROR = "60600.00002";

	/* ����ѡ��� */
	/** ��Ϣ��ѯ�쳣 */
	public static final String INFO_QUERY_ERROR = "00011.00099";

	/** ��ѯ�����쳣 */
	public static final String DEPARTMENT_QUERY_ERROR = "00011.00001";

	/** ��ѯ��Ŀ/�����쳣 */
	public static final String RESEARCH_QUERY_ERROR = "00011.00002";

	/** ��ѯ���е�λ�쳣 */
	public static final String FUNIT_QUERY_ERROR = "00011.00002";

	/* ������Ŀ���� */
	/** ��ȡ������Ŀ�����б��쳣 */
	public static final String RESEARCH_LIST_SEARCH_ERROR = "62000.00001";

	/** ɾ��������Ŀ�����쳣 */
	public static final String RESEARCH_DEL_ERROR = "62000.00002";

	/** ɾ��������Ŀ�����ĵ��쳣 */
	public static final String RESEARCH_DOC_DEL_ERROR = "62000.00003";

	/** ��ʼ����Ŀ������ҳ���쳣 */
	public static final String RESEARCH_EDIT_INIT_ERROR = "62000.00004";

	/** ��ʼ���½����п�Ŀҳ���쳣 */
	public static final String RESEARCH_CREATE_INIT_ERROR = "62000.00005";

	/** ��ʼ���޸Ŀ��п�Ŀҳ���쳣 */
	public static final String RESEARCH_MODIFY_INIT_ERROR = "62000.00006";

	/** ���ؿ��п�Ŀ�ĵ���ʼ���쳣 */
	public static final String RESEARCH_DOC_UPLOAD_INIT_ERROR = "62000.00007";

	/** ���п�Ŀ�ĵ��б��ʼ���쳣 */
	public static final String RESEARCH_DOC_LIST_INIT_ERROR = "62000.00008";

	/** ��ѯ���п�Ŀ��ϸ�����쳣 */
	public static final String RESEARCH_SEARCH_DETAIL_ERROR = "62000.00009";

	/** �޸Ŀ��п�Ŀ�쳣 */
	public static final String RESEARCH_MODIFY_ERROR = "62000.000010";

	/** ���ؿ��п�Ŀ�ĵ��쳣 */
	public static final String RESEARCH_DOC_UPLOAD_ERROR = "62000.000011";

	/** ά��������Ŀ�����б��쳣 */
	public static final String RESEARCH_EDIT_LIST_ERROR = "62000.000012";

	/** ����������Ŀ�����쳣 */
	public static final String RESEARCH_CREATE_ERROR = "62000.000013";

	/** �޸Ŀ��п�Ŀ״̬�쳣 */
	public static final String RESEARCH_MODIFY_STATE_ERROR = "62000.000014";

	/* �����쳣 */
	/** ��ʼ����ѯ�����쳣 */
	public static final String REPORT_PARAM_ERROR = "53000.00001";

	public static final String REPORT_BUILD_ERROR = "53000.00002";

	/* ����Ԥ�� */
	/** ��ѯ����Ԥ���ݸ��б��쳣 */
	public static final String WEATHER_QUERY_DRAFT_ERROR = "35000.00001";

	/** ����������Ԥ�������б��쳣 */
	public static final String WEATHER_CREATE_INIT_ERROR = "35000.00002";

	/* ͳ�Ʒ��� */
	/** ��ѯ�׶η���ͳ�Ʊ��쳣 */
	public static final String PERIOD_REPORT_ERROR = "51200.00001";

	/* ����Ԥ�� */
	/* ��ѯ�༭�б����(�ݸ�) */
	public static final String WEATHER_QUERY_EDIT_LIST = "35100.00001";

	/* �����½�ҳ����� */
	public static final String WEATHER_CREATE_ERROR = "35100.00002";

	/* ����༭ҳ����� */
	public static final String WEATHER_CREATE_UPDATE_ERROR = "35100.00003";

	/* ��������Ԥ������ */
	public static final String WEATHER_SAVE_ERROR = "35100.00004";

	/* �޸�����Ԥ������ */
	public static final String WEATHER_UPDATE_ERROR = "35100.00005";

	/* �ύ����Ԥ������ */
	public static final String WEATHER_SUBMIT_ERROR = "35100.00006";

	/* ɾ������Ԥ������ */
	public static final String WEATHER_DELETE_ERROR = "35100.00007";

	/* ��ѯ�б���� */
	public static final String WEATHER_QUERY_LIST_ERROR = "35100.00008";

	/* ��ѯ������Ϣ��ϸ��Ϣ���� */
	public static final String WEATHER_QUERY_INFO_ERROR = "35100.00009";

	/* ��ѯ��״���� */
	public static final String QUERY_CLOUD_SHPAE_ERROR = "35100.00099";

	/* ת�����мƻ� */
	/** �����ת�����мƻ������쳣 */
	public static final String TRANSFER_FARGO_CREATE_ERROR = "16000.00001";

	/** ս����ת�����мƻ������쳣 */
	public static final String TRANSFER_FIGHT_CREATE_ERROR = "16000.00002";

	/** �����ת�����мƻ������쳣 */
	public static final String TRANSFER_FARGO_MODIFY_ERROR = "16000.00003";

	/** ս����ת�����мƻ������쳣 */
	public static final String TRANSFER_FIGHT_MODIFY_ERROR = "16000.00004";

	/** ��ѯת�����мƻ������쳣 */
	public static final String TRANSFER_FIGHT_QUERY_ERROR = "16000.00005";

	/** ɾ��ת�����мƻ������쳣 */
	public static final String TRANSFER_PLAN_DELETE_ERROR = "16000.00006";

	/** �����ת�����мƻ������쳣 */
	public static final String TRANSFER_FARGO_HANDLE_ERROR = "16000.00007";

	/** ս����ת�����мƻ������쳣 */
	public static final String TRANSFER_FIGHT_HANDLE_ERROR = "16000.00008";

	/** ս����ת�����мƻ������쳣 */
	public static final String TRANSFER_IMPL_REPORT_ERROR = "16000.00009";
	
	

	/* �ļ����� */
	/** �ļ���С���� */
	public static final String FILE_SIZE_ERROR = "00001.00070";

	/** �ļ����ʹ��� */
	public static final String FILE_EXT_ERROR = "00001.00071";

	/** �ļ�Ŀ¼������ */
	public static final String FILE_DISP_ERROR = "00001.00072";

	/** �ļ������� */
	public static final String FILE_NULL_ERROR = "00001.00073";
	
	/** �ļ�����ʧ�� */
	public static final String FILE_UPLOAD_ERROR = "00001.00074";
	
	/** �ļ�����ʧ�� */
	public static final String FILE_IMPORT_ERROR = "00001.00075";
	
	/* �ͺ�ϵ�� */
	/** �ͺ�ϵ��ɾ���쳣 */
	public static final String SERIES_DELETE_ERROR = "60100.00001";

	/** �ͺ�ϵ�д����쳣 */
	public static final String SERIES_CREATE_ERROR = "60100.00002";

	/** �ͺ�ϵ���޸��쳣 */
	public static final String SERIES_MODIFY_ERROR = "60100.00003";
	
	/* �����ĵ� */
	/** �����ĵ���ѯ�쳣 */
	public static final String HGDOC_SEARCH_ERROR = "60700.00001";
	/** �����ĵ������쳣 */
	public static final String HGDOC_UPLOAD_ERROR = "60700.00002";
	/** �����ĵ�ɾ���쳣 */
	public static final String HGDOC_DELETE_ERROR = "60700.00003";
	/** �����ĵ��޸��쳣 */
	public static final String HGDOC_MODIFY_ERROR = "60700.00004";
	
	/* ��������豸&������Դ */
	/** ��������豸��ѯ�쳣 */
	public static final String GRESOURCE_QUERY_ERROR = "30300.00001";
	/** ������Դ��ѯ�쳣 */
	public static final String RESOURCE_QUERY_ERROR = "30300.00002";
	
	/** ��������豸�����쳣 */
	public static final String GRESOURCE_SEARCH_ERROR = "30300.00003";
	/** ������Դ�����쳣 */
	public static final String RESOURCE_SEARCH_ERROR = "30300.00004";
	
	/** ��������豸�����쳣 */
	public static final String RESOURCE_CREATE_ERROR = "30300.00005";
	/** ������Դ�����쳣 */
	public static final String GRESOURCE_CREATE_ERROR = "30300.00006";
	
	/** ��������豸ɾ���쳣 */
	public static final String GRESOURCE_DELETE_ERROR = "30300.00007";
	/** ������Դɾ���쳣 */
	public static final String RESOURCE_DELETE_ERROR = "30300.00008";
	
	/** ��������豸�޸��쳣 */
	public static final String GRESOURCE_MODIFY_ERROR = "30300.00009";
	/** ������Դ�޸��쳣 */
	public static final String RESOURCE_MODIFY_ERROR = "30300.00010";
	
	/** ��������豸������ʼ���쳣 */
	public static final String GRESOURCE_INITCREATE_ERROR = "30300.00011";
	/** ������Դ������ʼ���쳣 */
	public static final String RESOURCE_INITCREATE_ERROR = "30300.00012";
	
	/** ��������豸�޸ĳ�ʼ���쳣 */
	public static final String GRESOURCE_INITMODIFY_ERROR = "30300.00013";
	/** ������Դ�޸ĳ�ʼ���쳣 */
	public static final String RESOURCE_INITMODIFY_ERROR = "30300.00014";
	//���û�û�н�ɫ��Ȩ!
	public static final String USER_ROLE_ERROR = "99999.28";
	

	
}
