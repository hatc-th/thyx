<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title>弹出选择框</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="WangYang" />
        <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
        <meta name="description" content="popSelect for XTMS" />
        <script src="<c:url value="/js/popWindows.js" />"></script>
        <!--
                科研项目课题相关 10~19
                赛车相关 20~29
                人员相关 30~39
                单位相关 40~49
                物资相关 50-59
             -->
        <script>
            function switchWin(index, winName, params) {
            	params = (params != null && params != "") ? params : "";
                switch (index) {
                    case 10:
                        // 项目课题(级联、单选)
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=radio"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 11:
                        // 项目课题(级联、复选)
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=chkbox"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 12:
                        // 项目课题(级联、单选)
                        params = (params != null && params != "") ? params : "&slctLevel=0,0,3,4";
                        newCustWin('<c:url value="/dictAction.do?method=popResearch&option=radio"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 21:
                        // 车型分类(级联、单选)
                        newCustWin('<c:url value="/dictAction.do?method=popPlaneType&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 22:
                        // 车型分类(级联、复选)
                        newCustWin('<c:url value="/dictAction.do?method=popPlaneType&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 23:
                        // 车号(级联、单选)
                        newCustWin('<c:url value="/dictAction.do?method=popPlane&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 24:
                        // 车号(级联、复选)
                        newCustWin('<c:url value="/dictAction.do?method=popPlane&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 30:
                        // 车上人员(单选)
                        newCustWin('<c:url value="/dictAction.do?method=popPilot&option=s"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 32:
                        // 质检工程师(单选)
                        newCustWin('/ftpms/popselect/engineerRadio.jsp' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 39: 
                        // 通用人员(单选)
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=radio" />' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 40:
                        // 组织结构(单选)
                        newCustWin('<c:url value="/dictAction.do?method=popDepartment&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 41:
                        // 组织结构(复选)
                        newCustWin('<c:url value="/dictAction.do?method=popDepartment&option=chkbox"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 50:
                        // 物资目录(列表)
                        newCustWin('<c:url value="/dictAction.do?method=popMaterial"/>' + params, winName, 640, 420, '', '', 'ynnnn');
                        break;
                    case 52:
                        // 物资目录(调用)
                        newCustWin('<c:url value="/materialAction.do?method=queryMaterialList&isPop=isPop" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
                    case 55:
                        // 发付单物资(列表 for 物资消耗单)
                        newCustWin('<c:url value="/dictAction.do?method=popMaterialIV" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
                    case 60:
                        // 申领计划(调用)
                        newCustWin('<c:url value="/materialApplicationAction.do?method=setMaterialApplicationList&moduleCtrl=get&isPop=isPop&sqlState=51" />' + params, winName, 835, 575, '', '', 'ynnnn');
                        break;
		            case 70:
		                // 质检项目状态(级联、单选)
		                newCustWin('<c:url value="testTaskAction.do?method=popTestTaskState&option=radio"/>' + params, winName, 320, 420, '', '', 'ynnnn');
		                break;
                    case 90:
                        // 人员(级联单位、单选) - R99
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=radio&role=R99"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                    case 91:
                        // 人员(级联单位、复选) - R99
                        newCustWin('<c:url value="/dictAction.do?method=popUser&option=chkbox&role=R99"/>' + params, winName, 320, 420, '', '', 'ynnnn');
                        break;
                }
            }
        </script>
    </head>
</html>

