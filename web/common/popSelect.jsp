<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title>����ѡ���</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="WangYang" />
        <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
        <meta name="description" content="popSelect for XTMS" />
        <script src="<c:url value="/js/popWindows.js" />"></script>
        <!--
                ������Ŀ������� 10~19
                ������� 20~29
                ��Ա��� 30~39
                ��λ��� 40~49
                ������� 50-59
             -->
        <script>
            function switchWin(index, winName, params) {
            	params = (params != null && params != "") ? params : "";
                switch (index) {
                    case 10:
                        // ��Ŀ����(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=radio"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 11:
                        // ��Ŀ����(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=chkbox"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 12:
                        // ��Ŀ����(��������ѡ)
                        params = (params != null && params != "") ? params : "&slctLevel=0,0,3,4";
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=radio"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 21:
                        // ���ͷ���(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popPlaneType&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 22:
                        // ���ͷ���(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popPlaneType&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 23:
                        // ����(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popPlane&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 24:
                        // ����(��������ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popPlane&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 30:
                        // ������Ա(��ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popPilot&option=s"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 32:
                        // �ʼ칤��ʦ(��ѡ)
                        newCustWin('/ftpms/popselect/engineerRadio.jsp' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 39: 
                        // ͨ����Ա(��ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=radio" />' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 40:
                        // ��֯�ṹ(��ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popDepartment&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 41:
                        // ��֯�ṹ(��ѡ)
                        newCustWin('<c:url value="/dictAction.do?method=popDepartment&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 50:
                        // ����Ŀ¼(�б�)
                        newCustWin('<c:url value="/dictAction.do?method=popMaterial"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 52:
                        // ����Ŀ¼(����)
                        newCustWin('<c:url value="/materialAction.do?method=queryMaterialList&isPop=isPop" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
                    case 55:
                        // ����������(�б� for �������ĵ�)
                        newCustWin('<c:url value="/dictAction.do?method=popMaterialIV" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
                    case 60:
                        // ����ƻ�(����)
                        newCustWin('<c:url value="/materialApplicationAction.do?method=setMaterialApplicationList&moduleCtrl=get&isPop=isPop&sqlState=51" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
		            case 70:
		                // �ʼ���Ŀ״̬(��������ѡ)
		                newCustWin('<c:url value="testTaskAction.do?method=popTestTaskState&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
		                break;
                    case 90:
                        // ��Ա(������λ����ѡ) - R99
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=radio&role=R99"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 91:
                        // ��Ա(������λ����ѡ) - R99
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=chkbox&role=R99"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                }
            }
        </script>
    </head>
</html>

