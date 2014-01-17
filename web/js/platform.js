var callAjax = "init"; // ȫ�ֱ����������ж�Ajax ��֤�Ƿ�ͨ��
var timeoutAjax = 1500; // ȫ�ֱ���������Ajax ��֤�ӳ�

/* ���ύ */
function doAction(formAction) {
    var form = document.forms[0];
    form.action = formAction;
    form.submit();
}

/* ���ύ(AJAX) */
function doActionAjax(formAction) {
    if (callAjax == 'false') {
        callAjax = 'init';
        return false;
    }
    callAjax = 'init';
    doAction(formAction);
}

/* ��ѡ��ȫѡ */
function checkedAll(form ,checkAllId, checkboxName) {
    var chkAll_str = checkAllId == null ? 'chkAll' : checkAllId;
    var chkbox_str = checkboxName == null ? 'chkbox' : checkboxName;
    var chkAll = document.getElementById(chkAll_str);
    var chkbox = document.getElementsByName(chkbox_str);
    var chkboxNum = chkbox.length;
    if (chkboxNum == 1) {
        if(chkbox[0]){
	  		chkbox[0].checked = chkAll.checked;
        }else{
        	chkbox.checked = chkAll.checked;
        }
    } else if (chkboxNum > 1) {
        for (var i = 0; i < chkboxNum; i++) {
            chkbox[i].checked = chkAll.checked;
        }
    }
}

/* ���ȥ���ַ�����β�ո������ַ���ֵ */
String.prototype.trim = function trim() {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

/* ����ַ�������(һ��˫�ֽ��ַ���2, ASCII�ַ���1) */
String.prototype.len = function len() {
    return this.trim().replace(/[^\x00-\xff]/g, 'aa').length;
};

/* ��֤��ֵ, ��/�����ִ����� false, �ǿշ��� true, ��ֵȥ����β�ո� */
function isFilled(elm, alertText) {
    // [��] select ��ѡ
    if ((elm.type).indexOf('select-multiple') == -1) {
        elm.value = (elm.value).trim();
    }
    var elmValue = elm.value;
    if (elmValue.length == 0) {
        if (alertText != null) {
            alert(alertText);
            elm.focus();
            speciallyGoodEffect(elm);
        }
        return false;
    }
    return true;
}

/* ��֤���ȷ�Χ */
function isLimited(elm, upper, lower, alertText) {
    elm.value = (elm.value).trim();
    var elmValueLen = (elm.value).len();
    if (elmValueLen > upper || elmValueLen < lower) {
        if (alertText != null) {
            alert(alertText);
            elm.focus();
            speciallyGoodEffect(elm);
        }
        return false;
    }
    return true;
}

/* ��֤��� */
function isEqual(elm_a, elm_b, alertText) {
    if (elm_a.value.trim() != elm_b.value.trim()) {
        if (alertText != null) {
            alert(alertText);
            elm_a.focus();
            speciallyGoodEffect(elm_a);
        }
        return false;
    }
    return true;
}

/* ��֤������, ����Ϊ�� */
function isInt(elm, alertText) {
    var pattern = /^\d*$/;
    if (pattern.test(elm.value) == false) {
        if (alertText != null) {
            alert(alertText);
            elm.focus();
            speciallyGoodEffect(elm);
        }
        return false;
    }
    return true;
}

/* ��֤С��, С����ǰN λ����, С�����M λ����, ����'-'�� */
function isDouble(elm, n, m) {
    var elmNum = elm.value.trim();
    if (isNaN(parseFloat(elmNum)) == true) {
        return false;
    }
    var pattern = /[^\d\.]+/;
    if (pattern.test(elmNum) == true) {
        // ���зǷ��ַ�
        return false;
    } else if (elmNum.split('.').length > 2) {
        // ���ж���С����
        return false;
    } else if (elmNum.indexOf('.') != -1
        && (elmNum.split('.')[0].length == 0 || elmNum.split('.')[0].length > n)) {
        // С����ǰ������, ������λ�� > n
        return false;
    } else if (elmNum.indexOf('.') != -1
        && (elmNum.split('.')[1].length == 0 || elmNum.split('.')[1].length > m)) {
        // С�����������, ������λ�� > m
        return false;
    } else if (elmNum.indexOf('.') == -1
        && elmNum.split('.')[0].length > n) {
        // ��С����
        return false;
    }
    return true;
}

/* ��֤С��, С����ǰN λ����, С�����M λ����, ��'-'�� */
function isStrictDouble(elm, n, m) {
    var elmNum = elm.value.trim();
    if (isNaN(parseFloat(elmNum)) == true) {
        return false;
    }
    var isMinus = '';
    if (elmValue.charAt(0) == '-') {
        elm.value = elmValue.substring(1);
        isMinus = '-';
    } else if (elmValue.charAt(0) == '+') {
        elm.value = elmValue.substring(1);
    }
    if (isDouble(elm, n, m) == false) {
        return false;
    }
    var elmValueNew = elm.value;
    elm.value = isMinus + elmValueNew;
    return true;
}

/*-------------------------------------��������[��� 2.x]-------------------------------------*/

/* ��֤��λ��� */
function isYear(yearStr, fieldName) {
    yearStr = yearStr.trim();
    var pattern = /^[0-9]{4}$/;
    if (pattern.test(yearStr) == false) {
        alert(fieldName + "�������������!��������ȷ��ʽ!��:2000");
        return false;
    }
    return true;
}

/* 
* 2.2 ����·��Ƿ�Ϸ�
* 
* ����˵��
* @param month ��
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isMonth(month, fieldName){
    month = month.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(month) || month < 1 || month > 12){
        alert(fieldName + "���·���������!��������ȷ��ʽ!��:08 �� 8");
        return false;
    }
    return true;
}

/* 
* 2.3 ��������Ƿ�Ϸ�
* 
* ����˵��
* @param day ����
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isDay(year, month, day, fieldName){
    year = year.trim();
    month = month.trim();
    day = day.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(day) || day < 1 || day > 31){
        alert(fieldName + "��������������!��������ȷ��ʽ!��:08 �� 8");
        return false;
    }   
    if(month==4||month==6||month==9||month==11) {
          if(day==31) {
              alert(fieldName + "��" + parseInt(month) + "��û��31��!");
              return false;
          }
    }
    if(month==2) {
          if(year%4==0 && year%100!=0 || year%400==0) {   /*���� */
              if(day > 29) {
                  alert(fieldName + "��" + year + "��"+ parseInt(month) + "��û��"+day+"��!");
                  return false;
              }
          }
          else {/*��������*/
              if(day > 28) {
                  alert(fieldName + "��" + year + "��"+ parseInt(month) + "��û��"+day+"��!");
                  return false;
              }
          }
    }
    return true;
}

/* 
* 2.4 ���Сʱ�Ƿ�Ϸ�
* 
* ����˵��
* @param hour Сʱ
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isHour(hour, fieldName){
    hour = hour.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(hour) || hour < 0 || hour > 24){
        alert(fieldName + "��Сʱ��������!��������ȷ��ʽ!��:08 �� 8");
        return false;
    }   
    return true;
}

/* 
* 2.5 �������Ƿ�Ϸ�
* 
* ����˵��
* @param minute ����
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isMinute(minute, fieldName){
    minute = minute.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(minute) || minute < 0 || minute > 60){
        alert(fieldName + "��������������!��������ȷ��ʽ!��:08 �� 8");
        return false;
    }   
    return true;
}

/* 
* 2.6 ������Ƿ�Ϸ�
* 
* ����˵��
* @param second ��
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isSecond(second, fieldName){
    second = second.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(second) || second < 0 || second > 60){
        alert(fieldName + "������������!��������ȷ��ʽ!��:08 �� 8");
        return false;
    }   
    return true;
}

/* 
* 2.7 �������Ƿ�Ϸ�
* 
* ����˵��
* @param millisecond ����
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isMillisecond(millisecond, fieldName){
    millisecond = millisecond.trim();
    var reg = /^[0-9]{1,4}$/;
    if(!reg.test(millisecond) || millisecond < 0 || millisecond > 1000){
        alert(fieldName + "��������������!��������ȷ��ʽ!��:0008 �� 8");
        return false;
    }   
    return true;
}

/* 
* 2.8 ��������Ƿ�Ϸ� yyyy-MM-dd
* 
* ����˵��
* @param ele ����Ԫ��
* @param splitStr �ָ��
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isShortDate(ele, splitStr, fieldName, msgType){
    var date = ele.value.trim();
    var date_a = date.split(splitStr);
    var reg = new RegExp('^[0-9]{4}\\' + splitStr + '[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}$');
    if (date_a.length != 3 || !reg.exec(date)){     
        var msg = fieldName + "�����ڸ�ʽ����������������ȷ��ʽ��\n��:2008" + splitStr + "08" + splitStr + "08";
        if(msgType == null || msgType == 'l'){
            msg +=  " �� " + "2008" + splitStr + "8" + splitStr + "8";
        }
        alert(msg);
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    if (!isYear(date_a[0], fieldName)) {
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    if (!isMonth(date_a[1], fieldName)) {
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    if (!isDay(date_a[0], date_a[1], date_a[2], fieldName)) {
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    return true;
}

function isShortDateYM(ele, splitStr, fieldName, msgType) {
    var date = ele.value.trim();
    var date_a = date.split(splitStr);
    var reg = new RegExp('^[0-9]{4}\\' + splitStr + '[0-9]{1,2}$');
    if (date_a.length != 2 || !reg.exec(date)){     
        var msg = fieldName + "�����ڸ�ʽ����������������ȷ��ʽ��\n��:2008" + splitStr + "08";
        if(msgType == null || msgType == 'l'){
            msg +=  " �� " + "2008" + splitStr + "8";
        }
        alert(msg);
        ele.focus();
        return false;
    }
    if (!isYear(date_a[0], fieldName)) {
        ele.focus();
        return false;
    }
    if (!isMonth(date_a[1], fieldName)) {
        ele.focus();
        return false;
    }
    return true;
}

function formatShortDate(elmName, alertText) {
    var objDate = document.getElementById(elmName);
    if (isShortDate(objDate, "-", alertText) == false) {
        return false;
    } else {
        var strDate = objDate.value;
        var strYear = strDate.split("-")[0];
        var strMonth = strDate.split("-")[1];
        var strDay = strDate.split("-")[2];
        if (parseInt(strMonth, 10) < 10) {
            strMonth = "0" + parseInt(strMonth, 10);
        }
        if (parseInt(strDay, 10) < 10) {
            strDay = "0" + parseInt(strDay, 10);
        }
        objDate.value = strYear + "-" + strMonth + "-" + strDay;
    }
}

function formatShortDateObj(objDate, alertText) {
    if (isShortDate(objDate, "-", alertText) == false) {
        return false;
    } else {
        var strDate = objDate.value;
        var strYear = strDate.split("-")[0];
        var strMonth = strDate.split("-")[1];
        var strDay = strDate.split("-")[2];
        if (parseInt(strMonth, 10) < 10) {
            strMonth = "0" + parseInt(strMonth, 10);
        }
        if (parseInt(strDay, 10) < 10) {
            strDay = "0" + parseInt(strDay, 10);
        }
        objDate.value = strYear + "-" + strMonth + "-" + strDay;
    }
}

function formatYmDate(elmName, alertText) {
    var objDate = document.getElementById(elmName);
    if (isShortDateYM(objDate, "-", alertText) == false) {
        return false;
    } else {
        var strDate = objDate.value;
        var strYear = strDate.split("-")[0];
        var strMonth = strDate.split("-")[1];
        if (parseInt(strMonth, 10) < 10) {
            strMonth = "0" + parseInt(strMonth, 10);
        }
        objDate.value = strYear + "-" + strMonth;
    }
}

/* 
* 2.8 ��������Ƿ�Ϸ� MM-dd
* 
* ����˵��
* @param ele ����Ԫ��
* @param splitStr �ָ��
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isShortDateMD(ele, splitStr, fieldName, msgType) {
    var date = ele.value.trim();
    var date_a = date.split(splitStr);
    var reg = new RegExp('^[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}$');
    if (date_a.length != 2 || !reg.exec(date)) {     
        var msg = fieldName + "�����ڸ�ʽ����������������ȷ��ʽ��\n��:08" + splitStr + "08";
        if (msgType == null || msgType == 'l') {
            msg +=  " �� " + "8" + splitStr + "8";
        }
        alert(msg);
        ele.focus();
        return false;
    }
    if (!isMonth(date_a[0], fieldName)) {
        ele.focus();
        return false;
    }
    if (!isDay("2000", date_a[0], date_a[1], fieldName)) {
        ele.focus();
        return false;
    }
    return true;
}

/* 
* 2.9 ��������Ƿ�Ϸ� yyyy-MM-dd hh:mm:ss
* 
* ����˵��
* @param ele ����Ԫ��
* @param splitStr �ָ��
* @param fieldName ��ʾ��Ϣ �ֶ�����
*/
function isLongDate(ele, splitStr, fieldName, msgType){
    var date = ele.value.trim();
    var sIndex = date.indexOf(':');
    var date_temp = date.substr(0,sIndex - 2);  
    var time_temp = date.substr(sIndex - 2,date.length);
    var time_a = time_temp.split(':');
    var reg = new RegExp('^[0-9]{4}\\' + splitStr + '[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}\\s[0-9]{1,2}\\:[0-9]{1,2}\\:[0-9]{1,2}$');  
    if(time_a.length != 3 || !reg.exec(date)){
        var msg = fieldName + "�����ڸ�ʽ��������!!��������ȷ��ʽ!\n��:2008" + splitStr + "08" + splitStr + "08 09:09:09";
        if(msgType == null || msgType == 'l'){
            msg +=  " �� " + "2008" + splitStr + "8" + splitStr + "8 9:9:9";
        }
        alert(msg);
        ele.focus();
        return false;
    }
    
    if(!isShortDate(date_temp, splitStr, fieldName)){
        ele.focus();
        return false;
    }
    
    if(!isHour(time_a[0],fieldName)){
        ele.focus();
        return false;
    }
    if(!isMinute(time_a[1],fieldName)){
        ele.focus();
        return false;
    }
    if(!isSecond(time_a[2],fieldName)){
        ele.focus();
        return false;
    }
    return true;
}

/* 
* 2.10 �Ƚ����� yyyy-MM-dd
* 
* ����˵��
* @param startDate ǰһ����
* @param endDate ��һ����
* @param splitStr �ָ��
* @param fieldName_s ��ʾ��Ϣ ǰһ�ֶ�����
* @param fieldName_s ��ʾ��Ϣ ��һ�ֶ�����
*/
function compareShortDate(startDate, endDate, splitStr, fieldName_s, fieldName_e) {

    if (!isShortDate(startDate, splitStr, fieldName_s)) {
        return false;
    }
    if (!isShortDate(endDate, splitStr, fieldName_e)) {
        return false;
    }
    var date_s = startDate.value.split(splitStr);
    var date_e = endDate.value.split(splitStr);
    if (parseInt(date_s[0],10) > parseInt(date_e[0],10)) {
        alert(fieldName_s + "����ݱ�������" + fieldName_e + "����ݣ�");
        return false;
    } else {
        if (parseInt(date_s[0],10) == parseInt(date_e[0],10)) {
            if (date_s[1] - date_e[1]>0) {
                alert(fieldName_s + "���·ݱ�������" + fieldName_e + "���·ݣ�");
                return false;
            } else {
                if (parseInt(date_s[1],10) == parseInt(date_e[1],10)) {
                    if (date_s[2] - date_e[2]>0) {
                        alert(fieldName_s + "��������������" + fieldName_e + "��������");
                        return false;
                    }
                }
            }
        }
    }
    return true;
}


/* 
* 2.11 �Ƚ����� yyyy-MM-dd hh:mm:ss
* 
* ����˵��
* @param startDate ǰһ����
* @param endDate ��һ����
* @param splitStr �ָ��
* @param fieldName_s ��ʾ��Ϣ ǰһ�ֶ�����
* @param fieldName_s ��ʾ��Ϣ ��һ�ֶ�����
*/
function compareLongDate(startDate, endDate, splitStr, fieldName_s, fieldName_e){
    if(!isLongDate(startDate, splitStr, fieldName_s)){
        return false;
    }
    if(!isLongDate(endDate, splitStr, fieldName_e)){
        return false;
    }
    
    var sIndex = date.indexOf(':');
    var date_temp = date.substr(0,sIndex - 2);  
    var time_temp = date.substr(sIndex - 2,date.length);
    var time_a = time_temp.split(':');
    
    var rex = new RegExp(splitStr,'g');
    var date_s = Date.parse(splitStr != ' ' ? startDate.replace(rex,' ') : startDate);
    var date_e = Date.parse(splitStr != ' ' ? endDate.replace(rex,' ') : endDate);
    alert(splitStr != ' ' ? startDate.replace(rex,' ') : startDate);
    alert(splitStr != ' ' ? endDate.replace(rex,' ') : endDate);
    if(date_s > date_e){
        alert(fieldName_s + '����С��' + fieldName_e + '!');
        return false;
    }
    return true;
}

/*-------------------------------------��������[��� 2.x]-------------------------------------*/

/**
 * project:  Foms ������֯����ϵͳ
 * brief:    ֪ͨ���� - �û����趨 - select �˵�ѡ��
 * author:   Chenghuang@hotmail.com
 * version:  Ver1.00 2007.09.10
 *
 * function: insertSelected(elm_a, elm_b, elm) �������
 * function: removeSelected(elm_a, elm_b, elm) �Ƴ�����
 * param:    elm_a ��ѡ select
 * param:    elm_b Ŀ�� select
 * param:    elm   ����ѡ�����, selectall/null
 */
 
/**
 * �������
 */
function insertSelected(elm_a, elm_b, elm) {
    var slct_a = elm_a;
    var slct_b = elm_b;
    if (elm == "selectall") {
        var n = slct_b.options.length;
        for (var i = 0; i < slct_a.options.length; i++) {
            var mark = 0;
            /* ���ɽ����ظ����� */
            for (var j = 0; j < n; j++) {
                if (slct_a.options[i].value == slct_b.options[j].value) {
                    mark = 1;
                    break;
                }
            }
            if (mark == 0) {
                slct_b.options[n] = new Option();
                slct_b.options[n].text = slct_a.options[i].text;
                slct_b.options[n].value = slct_a.options[i].value;
                n++;
            }
        }
        //for (var k = 0; k < n; k++) {
        //  slct_b.options[k].selected = true;
        //}
        for (var k = 0; k < slct_a.options.length; k++) {
            slct_a.options[k].selected = false;
        }
    } else {
        var n = slct_b.options.length;
        for (var i = 0; i < slct_a.options.length; i++) {
            var mark = 0;
            /* ���ɽ����ظ����� */
            for (var j = 0; j < n; j++) {
                if (slct_a.options[i].value == slct_b.options[j].value) {
                    mark = 1;
                    break;
                }
            }
            if (mark == 0 && slct_a.options[i].selected == true) {
                slct_b.options[n] = new Option();
                slct_b.options[n].text = slct_a.options[i].text;
                slct_b.options[n].value = slct_a.options[i].value;
                n++;
            }
        }
        //for (var k = 0; k < n; k++) {
        //  slct_b.options[k].selected = true;
        //}
        for (var k = 0; k < slct_a.options.length; k++) {
            slct_a.options[k].selected = false;
        }
    }
}

/**
 * �Ƴ�����
 */
function removeSelected(elm_a, elm_b, elm) {
    var slct_a = elm_a;
    var slct_b = elm_b;
    if (elm == "selectall") {
        slct_a.options.length = 0;
    } else {
        var n = slct_a.options.length;
        var arr_value = new Array();
        var arr_text = new Array();
        var arr_length = 0;
        for (var k = 0; k < n; k++) {
            if (slct_a.options[k].selected != true) {
                arr_value[arr_length] = slct_a.options[k].value;
                arr_text[arr_length] = slct_a.options[k].text;
                arr_length++;
            }
        }
        slct_a.options.length = 0;
        for (var i = 0; i < arr_length; i++) {
            slct_a.options[i] = new Option();
            slct_a.options[i].value = arr_value[i];
            slct_a.options[i].text = arr_text[i];
            //slct_a.options[i].selected = true;
        }
    }
}

/* ���� Form InputText */
function resetInput(param, splitMark) {
    var paramValue = param.split(splitMark);
    for (var i = 0; i < paramValue.length; i++) {
        (window.document.getElementById(paramValue[i])).value = '';
    }
}

function resetInputObj(elmA, elmB, elmC) {
    if (elmA != null) {
        elmA.value = '';
    }
    if (elmB != null) {
        elmB.value = '';
    }
    if (elmC != null) {
        elmC.value = '';
    }
}

/**
 * ��֤��������
 * param ele: ����֤��Ԫ�ض���
 * param msg: ��Ҫ��ʾ�Ĵ������ʾ��Ϣ
 */
 function postCodeValidate(ele, msg){
 	var postCode = ele.value.trim();
 	if(postCode.length != 6){
 		alert("�������볤��ӦΪ6�����֣�");
 		ele.focus();
	 	speciallyGoodEffect(ele);
 		return false;
 	}else{
 		var flag = false;
		for(var i = 0; i < postCode.length; i++){
			if(isNaN(postCode.charAt(i))){
				flag = false;
				break;
			}else{
				flag = true;
			}
		}
	 	if(!flag){
	 		alert(msg);
	 		ele.focus();
	 		speciallyGoodEffect(ele);
	 		return false;
	 	}else{
	 		return true;
	 	}
 	}
 }
 
 /**
  * ��֤���֤��
  * param ele: ����֤��Ԫ�ض���
  * param msg: ��Ҫ��ʾ�Ĵ������ʾ��Ϣ
  */
  function idCardValidate(ele, msg){
  	var reg = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
  	var idCard = ele.value.trim();
  	if(!(reg.test(idCard))){
 		alert(msg);
 		ele.focus();
 		return false;
 	}else{
 		return true;
 	}
 }
 /**
  * �绰����������
  * param ele: ����֤��Ԫ�ض���
  * param msg: ��Ҫ��ʾ�Ĵ������ʾ��Ϣ
  */
  function telephoneOrFaxValidate(ele, msg){
  	var telephoneOrFax = ele.value.trim();
  	var allowCharacter = new Array("1","2","3","4","5","6","7","8","9","0","-","(",")");
  	var flag = true;
  	var flag1 = 0;                                      // ��¼"-"���ֵĴ���
  	var flag2 = 0;                                      // ��¼"("���ֵĴ���
  	var flag3 = 0;                                      // ��¼")"���ֵĴ���
	for(var i = 0, j = telephoneOrFax.length; i < j; i++){
		for(var k = 0, l = allowCharacter.length; k < l; k++){
			if(telephoneOrFax.charAt(i) != allowCharacter[k]){
				flag = false;
			}else{
				flag = true;
				break;
			}
		}
		if(!flag){
			break;
		}
	}
	if(((telephoneOrFax).indexOf("-")) != -1 && ((telephoneOrFax).indexOf("-")) < 3){
		flag = false;
	}
	if(flag == true){
		for(var m = 0, n = telephoneOrFax.length; m < n; m++){
			if(telephoneOrFax.charAt(m) == "-"){
				flag1++;
			}
			if(telephoneOrFax.charAt(m) == "("){
				flag2++;
			}
			if(telephoneOrFax.charAt(m) == ")"){
				flag3++;
			}
		}
	}
	if(flag1 > 4 || flag2 != flag3){
		flag = false;
	}
	if(!flag){
		alert(msg);
	 	ele.focus();
	 	speciallyGoodEffect(ele);
	 	return false;
	}else{
	 	return true;
	}
  }

/* ��֤���ȷ�Χ */
function isAscllLimited(elm, upper, lower, alertText) {
    elm.value = (elm.value).trim();
    var elmValueLen = (elm.value).len();
    if (elmValueLen > upper || elmValueLen < lower) {
        if (alertText != null) {
            alert(alertText);
            elm.focus();
        }
        return false;
    }
    return true;
}
/***********************************Ԫ����˸��ʼ*****************************************/
	var begin = 100;                        // ����Ԫ�شӰٷ�֮���ٿ�ʼ��˸
	var time1;                              // ʱ�亯��(���е��޵�ʱ�亯��)
	var time2;                              // ʱ�亯��(���޵��е�ʱ�亯��)
	var step = 10;                          // ����Ŀ��
	var times = 0;                          // �Ѿ���˸�Ĵ���
	var ele;                                // ��Ҫ������˸��Ԫ��
	var flashTimes = 5;                    // ��Ҫ��˸�Ĵ���
	var flashFrequency = 10;                // ��˸Ƶ��
	var defaultBorderColor = "#FF0000";     // Ĭ����˸�߿���ɫ
	var frequencyUpperLimit = 2000;         // ��˸Ƶ������
		
	/**
	 * Ԫ�ص��ô˺������н�����Ч
	 * param element �������Ԫ��(�˲����Ǳ����еĲ���)
	 * param flashTime ��Ҫ��˸�Ĵ���
	 * param frequency ��˸Ƶ��(Խ��Խ��)
	 * param arguments[3] ��˸ʱԪ�ر߿����ɫ
	 */
	function speciallyGoodEffect(element, flashTime, frequency){
		if(null == element){
			alert("Ԫ����˸��Ҫ�������˸��Ԫ�أ�");
		}else{
			if(null != arguments[3]){
				initFlash(element, flashTime, frequency, arguments[3]);                         // ������˸������ʼ��
			}else{
				initFlash(element, flashTime, frequency,defaultBorderColor);                    // ������˸������ʼ��
			}
			blockToHidden();                                                                    // ��ʼ������˸
		}
	}
		
	/**
	 * Ԫ�ؽ��䷽��(���е���)
	 */
	function blockToHidden(){
		ele.style.filter="alpha(opacity="+(begin - step)+")";   // �����˾�
		begin = begin - step;                                   // ���ĳ�ʼ�˾��İٷֱ�
		time1 = setTimeout("blockToHidden();",flashFrequency);  // ʹ��ʱ�亯������Ԫ��
		if(begin <= 0){
			window.clearTimeout(time1);
		}
		if(begin <= 0){
			hiddenToBlock();
		}
	}
	
	/**
	 * Ԫ�ؽ��䷽��(���޵���)
	 */
	function hiddenToBlock(){
		ele.style.filter="alpha(opacity="+(begin + step)+")";     // �����˾�
		begin = begin + step;                                     // ���ĳ�ʼ�˾��İٷֱ�
		time2 = setTimeout("hiddenToBlock()",flashFrequency);     // ʹ��ʱ�亯������Ԫ��
		if(begin >= 100){
			window.clearTimeout(time2);
			times++;                                              // �Ѿ��������(����+����Ϊһ��)
			if(times <= (parseInt(flashTimes) - 1)){             // �Ƿ���Ҫ������һ�ε���˸
				blockToHidden();
			}else{
				ele.style.borderColor = ""; 
			}
		}
	}
	
	/**
    * ��鴫��Ķ����ֵ�Ƿ�ȫ������
    *
    * @param ele ҳ�洫�����ҪУ���field����
    * @param msg У��ʧ��ʱ����ʾ��Ϣ
    * @return boolean ����У���Ƿ�ͨ��
    */

	function CheckNumber(ele,msg) {
		var str=ele.value;
		if(str=='')
		{
		    alert("" + msg);
		    ele.focus();
		    return false;
		}	
	        if(!isNumber(str))
	        {
		    alert("" + msg);
		    ele.focus();
		    return false;
		}
		return true;
	}


	//���ݵõ��ľ�γ����Ϣת���ɵ�ͼ��ʶ�����ֵ��γ����Ϣ
	function getPointNumInfo(rsPPos){
		pPos=rsPPos.split('E');
		temp1=parseFloat(pPos[0].substr(1,2));
		temp2=parseFloat(pPos[0].substr(3,2))/60;
		temp3=parseFloat(pPos[0].substr(5,2))/(60*60);
		temp4=parseFloat(pPos[0].substr(8,3))/(60*60*60);
		
		temp5=parseFloat(pPos[1].substr(0,3));
		temp6=parseFloat(pPos[1].substr(3,2))/60;
		temp7=parseFloat(pPos[1].substr(5,2))/(60*60);
		temp8=parseFloat(pPos[1].substr(8,3))/(60*60*60);
		
		wd=temp1+temp2+temp3+temp4;
		jd=temp5+temp6+temp7+temp8;
		
		return '['+jd+','+wd+']';
	} 
	
	/**
    * �ж�������Ƿ�ȫ�����֡��ú���һ�㲻ֱ���ṩ�ⲿ���ã�������������������
    *
    * @param strΪҪУ��Ķ����ֵ
    * @return boolean ����У����ַ����Ƿ�ȫ������    
    */
	function isNumber(str){
		for(var i=0;i<str.length;i++) {
			var ch=str.charCodeAt(i);
			if(i!=0 || ch!=45){
				if(ch<48 || ch>57) {			
					return false;			
				}
			}
			
		}
		return true;
	}
	
	/**
	 * ������ʼ��
	 * param element �������Ԫ��
	 * param flashTime ��Ҫ��˸�Ĵ���
	 * param flashFrequency ��˸Ƶ��(Խ��Խ��)
	 */
	 function initFlash(element, flashTime, frequency){
	 	if(!(null == flashTime) && !(flashTime == "") && !(flashTime < 0) && !(isNaN(flashTime))){
			flashTimes = parseInt(flashTime);
		}
		if(!(null == frequency) && !(frequency == "") && !(frequency < 0) && !(isNaN(frequency)) && !(parseInt(frequency) > frequencyUpperLimit)){
			flashFrequency = parseInt(frequency);
		}
		times = 0;
		window.clearTimeout(time1);
		window.clearTimeout(time2);
		ele = element;
		if(null != arguments[3]){
			ele.style.borderColor = arguments[3];                               // ������˸ʱԪ�صı߿���ɫ
		}
	 }
	 /***********************************Ԫ����˸����*****************************************/