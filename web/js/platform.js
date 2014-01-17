var callAjax = "init"; // 全局变量，用于判定Ajax 验证是否通过
var timeoutAjax = 1500; // 全局变量，用于Ajax 验证延迟

/* 表单提交 */
function doAction(formAction) {
    var form = document.forms[0];
    form.action = formAction;
    form.submit();
}

/* 表单提交(AJAX) */
function doActionAjax(formAction) {
    if (callAjax == 'false') {
        callAjax = 'init';
        return false;
    }
    callAjax = 'init';
    doAction(formAction);
}

/* 复选框全选 */
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

/* 获得去除字符串首尾空格后的新字符串值 */
String.prototype.trim = function trim() {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

/* 获得字符串长度(一个双字节字符计2, ASCII字符计1) */
String.prototype.len = function len() {
    return this.trim().replace(/[^\x00-\xff]/g, 'aa').length;
};

/* 验证空值, 空/纯空字串返回 false, 非空返回 true, 数值去除首尾空格 */
function isFilled(elm, alertText) {
    // [非] select 多选
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

/* 验证长度范围 */
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

/* 验证相等 */
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

/* 验证正整数, 允许为空 */
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

/* 验证小数, 小数点前N 位数字, 小数点后M 位数字, 不含'-'号 */
function isDouble(elm, n, m) {
    var elmNum = elm.value.trim();
    if (isNaN(parseFloat(elmNum)) == true) {
        return false;
    }
    var pattern = /[^\d\.]+/;
    if (pattern.test(elmNum) == true) {
        // 含有非法字符
        return false;
    } else if (elmNum.split('.').length > 2) {
        // 含有多余小数点
        return false;
    } else if (elmNum.indexOf('.') != -1
        && (elmNum.split('.')[0].length == 0 || elmNum.split('.')[0].length > n)) {
        // 小数点前无数字, 或数字位数 > n
        return false;
    } else if (elmNum.indexOf('.') != -1
        && (elmNum.split('.')[1].length == 0 || elmNum.split('.')[1].length > m)) {
        // 小数点后无数字, 或数字位数 > m
        return false;
    } else if (elmNum.indexOf('.') == -1
        && elmNum.split('.')[0].length > n) {
        // 无小数点
        return false;
    }
    return true;
}

/* 验证小数, 小数点前N 位数字, 小数点后M 位数字, 含'-'号 */
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

/*-------------------------------------日期类型[编号 2.x]-------------------------------------*/

/* 验证四位年份 */
function isYear(yearStr, fieldName) {
    yearStr = yearStr.trim();
    var pattern = /^[0-9]{4}$/;
    if (pattern.test(yearStr) == false) {
        alert(fieldName + "：年份输入有误!请输入正确格式!如:2000");
        return false;
    }
    return true;
}

/* 
* 2.2 检查月份是否合法
* 
* 参数说明
* @param month 月
* @param fieldName 提示信息 字段名称
*/
function isMonth(month, fieldName){
    month = month.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(month) || month < 1 || month > 12){
        alert(fieldName + "：月份输入有误!请输入正确格式!如:08 或 8");
        return false;
    }
    return true;
}

/* 
* 2.3 检查天数是否合法
* 
* 参数说明
* @param day 天数
* @param fieldName 提示信息 字段名称
*/
function isDay(year, month, day, fieldName){
    year = year.trim();
    month = month.trim();
    day = day.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(day) || day < 1 || day > 31){
        alert(fieldName + "：天数输入有误!请输入正确格式!如:08 或 8");
        return false;
    }   
    if(month==4||month==6||month==9||month==11) {
          if(day==31) {
              alert(fieldName + "：" + parseInt(month) + "月没有31日!");
              return false;
          }
    }
    if(month==2) {
          if(year%4==0 && year%100!=0 || year%400==0) {   /*闰年 */
              if(day > 29) {
                  alert(fieldName + "：" + year + "年"+ parseInt(month) + "月没有"+day+"日!");
                  return false;
              }
          }
          else {/*不是闰年*/
              if(day > 28) {
                  alert(fieldName + "：" + year + "年"+ parseInt(month) + "月没有"+day+"日!");
                  return false;
              }
          }
    }
    return true;
}

/* 
* 2.4 检查小时是否合法
* 
* 参数说明
* @param hour 小时
* @param fieldName 提示信息 字段名称
*/
function isHour(hour, fieldName){
    hour = hour.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(hour) || hour < 0 || hour > 24){
        alert(fieldName + "：小时输入有误!请输入正确格式!如:08 或 8");
        return false;
    }   
    return true;
}

/* 
* 2.5 检查分钟是否合法
* 
* 参数说明
* @param minute 分钟
* @param fieldName 提示信息 字段名称
*/
function isMinute(minute, fieldName){
    minute = minute.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(minute) || minute < 0 || minute > 60){
        alert(fieldName + "：分钟输入有误!请输入正确格式!如:08 或 8");
        return false;
    }   
    return true;
}

/* 
* 2.6 检查秒是否合法
* 
* 参数说明
* @param second 秒
* @param fieldName 提示信息 字段名称
*/
function isSecond(second, fieldName){
    second = second.trim();
    var reg = /^[0-9]{1,2}$/;
    if(!reg.test(second) || second < 0 || second > 60){
        alert(fieldName + "：秒输入有误!请输入正确格式!如:08 或 8");
        return false;
    }   
    return true;
}

/* 
* 2.7 检查毫秒是否合法
* 
* 参数说明
* @param millisecond 毫秒
* @param fieldName 提示信息 字段名称
*/
function isMillisecond(millisecond, fieldName){
    millisecond = millisecond.trim();
    var reg = /^[0-9]{1,4}$/;
    if(!reg.test(millisecond) || millisecond < 0 || millisecond > 1000){
        alert(fieldName + "：毫秒输入有误!请输入正确格式!如:0008 或 8");
        return false;
    }   
    return true;
}

/* 
* 2.8 检查日期是否合法 yyyy-MM-dd
* 
* 参数说明
* @param ele 日期元素
* @param splitStr 分割符
* @param fieldName 提示信息 字段名称
*/
function isShortDate(ele, splitStr, fieldName, msgType){
    var date = ele.value.trim();
    var date_a = date.split(splitStr);
    var reg = new RegExp('^[0-9]{4}\\' + splitStr + '[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}$');
    if (date_a.length != 3 || !reg.exec(date)){     
        var msg = fieldName + "：日期格式输入有误！请输入正确格式！\n如:2008" + splitStr + "08" + splitStr + "08";
        if(msgType == null || msgType == 'l'){
            msg +=  " 或 " + "2008" + splitStr + "8" + splitStr + "8";
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
        var msg = fieldName + "：日期格式输入有误！请输入正确格式！\n如:2008" + splitStr + "08";
        if(msgType == null || msgType == 'l'){
            msg +=  " 或 " + "2008" + splitStr + "8";
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
* 2.8 检查日期是否合法 MM-dd
* 
* 参数说明
* @param ele 日期元素
* @param splitStr 分割符
* @param fieldName 提示信息 字段名称
*/
function isShortDateMD(ele, splitStr, fieldName, msgType) {
    var date = ele.value.trim();
    var date_a = date.split(splitStr);
    var reg = new RegExp('^[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}$');
    if (date_a.length != 2 || !reg.exec(date)) {     
        var msg = fieldName + "：日期格式输入有误！请输入正确格式！\n如:08" + splitStr + "08";
        if (msgType == null || msgType == 'l') {
            msg +=  " 或 " + "8" + splitStr + "8";
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
* 2.9 检查日期是否合法 yyyy-MM-dd hh:mm:ss
* 
* 参数说明
* @param ele 日期元素
* @param splitStr 分割符
* @param fieldName 提示信息 字段名称
*/
function isLongDate(ele, splitStr, fieldName, msgType){
    var date = ele.value.trim();
    var sIndex = date.indexOf(':');
    var date_temp = date.substr(0,sIndex - 2);  
    var time_temp = date.substr(sIndex - 2,date.length);
    var time_a = time_temp.split(':');
    var reg = new RegExp('^[0-9]{4}\\' + splitStr + '[0-9]{1,2}\\' + splitStr + '[0-9]{1,2}\\s[0-9]{1,2}\\:[0-9]{1,2}\\:[0-9]{1,2}$');  
    if(time_a.length != 3 || !reg.exec(date)){
        var msg = fieldName + "：日期格式输入有误!!请输入正确格式!\n如:2008" + splitStr + "08" + splitStr + "08 09:09:09";
        if(msgType == null || msgType == 'l'){
            msg +=  " 或 " + "2008" + splitStr + "8" + splitStr + "8 9:9:9";
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
* 2.10 比较日期 yyyy-MM-dd
* 
* 参数说明
* @param startDate 前一日期
* @param endDate 后一日期
* @param splitStr 分割符
* @param fieldName_s 提示信息 前一字段名称
* @param fieldName_s 提示信息 后一字段名称
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
        alert(fieldName_s + "的年份必须早于" + fieldName_e + "的年份！");
        return false;
    } else {
        if (parseInt(date_s[0],10) == parseInt(date_e[0],10)) {
            if (date_s[1] - date_e[1]>0) {
                alert(fieldName_s + "的月份必须早于" + fieldName_e + "的月份！");
                return false;
            } else {
                if (parseInt(date_s[1],10) == parseInt(date_e[1],10)) {
                    if (date_s[2] - date_e[2]>0) {
                        alert(fieldName_s + "的天数必须早于" + fieldName_e + "的天数！");
                        return false;
                    }
                }
            }
        }
    }
    return true;
}


/* 
* 2.11 比较日期 yyyy-MM-dd hh:mm:ss
* 
* 参数说明
* @param startDate 前一日期
* @param endDate 后一日期
* @param splitStr 分割符
* @param fieldName_s 提示信息 前一字段名称
* @param fieldName_s 提示信息 后一字段名称
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
        alert(fieldName_s + '必须小于' + fieldName_e + '!');
        return false;
    }
    return true;
}

/*-------------------------------------日期类型[编号 2.x]-------------------------------------*/

/**
 * project:  Foms 飞行组织管理系统
 * brief:    通知管理 - 用户组设定 - select 菜单选择
 * author:   Chenghuang@hotmail.com
 * version:  Ver1.00 2007.09.10
 *
 * function: insertSelected(elm_a, elm_b, elm) 插入操作
 * function: removeSelected(elm_a, elm_b, elm) 移除操作
 * param:    elm_a 被选 select
 * param:    elm_b 目标 select
 * param:    elm   操作选择参数, selectall/null
 */
 
/**
 * 插入操作
 */
function insertSelected(elm_a, elm_b, elm) {
    var slct_a = elm_a;
    var slct_b = elm_b;
    if (elm == "selectall") {
        var n = slct_b.options.length;
        for (var i = 0; i < slct_a.options.length; i++) {
            var mark = 0;
            /* 不可进行重复插入 */
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
            /* 不可进行重复插入 */
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
 * 移除操作
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

/* 重置 Form InputText */
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
 * 验证邮政编码
 * param ele: 需验证的元素对象
 * param msg: 需要显示的错误的提示信息
 */
 function postCodeValidate(ele, msg){
 	var postCode = ele.value.trim();
 	if(postCode.length != 6){
 		alert("邮政编码长度应为6个数字！");
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
  * 验证身份证号
  * param ele: 需验证的元素对象
  * param msg: 需要显示的错误的提示信息
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
  * 电话号码或传真号码
  * param ele: 需验证的元素对象
  * param msg: 需要显示的错误的提示信息
  */
  function telephoneOrFaxValidate(ele, msg){
  	var telephoneOrFax = ele.value.trim();
  	var allowCharacter = new Array("1","2","3","4","5","6","7","8","9","0","-","(",")");
  	var flag = true;
  	var flag1 = 0;                                      // 记录"-"出现的次数
  	var flag2 = 0;                                      // 记录"("出现的次数
  	var flag3 = 0;                                      // 记录")"出现的次数
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

/* 验证长度范围 */
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
/***********************************元素闪烁开始*****************************************/
	var begin = 100;                        // 设置元素从百分之多少开始闪烁
	var time1;                              // 时间函数(从有到无的时间函数)
	var time2;                              // 时间函数(从无到有的时间函数)
	var step = 10;                          // 渐变的跨度
	var times = 0;                          // 已经闪烁的次数
	var ele;                                // 需要进行闪烁的元素
	var flashTimes = 5;                    // 需要闪烁的次数
	var flashFrequency = 10;                // 闪烁频率
	var defaultBorderColor = "#FF0000";     // 默认闪烁边框颜色
	var frequencyUpperLimit = 2000;         // 闪烁频率上限
		
	/**
	 * 元素调用此函数进行渐变特效
	 * param element 待渐变的元素(此参数是必须有的参数)
	 * param flashTime 需要闪烁的次数
	 * param frequency 闪烁频率(越大越慢)
	 * param arguments[3] 闪烁时元素边框的颜色
	 */
	function speciallyGoodEffect(element, flashTime, frequency){
		if(null == element){
			alert("元素闪烁需要传入待闪烁的元素！");
		}else{
			if(null != arguments[3]){
				initFlash(element, flashTime, frequency, arguments[3]);                         // 进行闪烁函数初始化
			}else{
				initFlash(element, flashTime, frequency,defaultBorderColor);                    // 进行闪烁函数初始化
			}
			blockToHidden();                                                                    // 开始函数闪烁
		}
	}
		
	/**
	 * 元素渐变方法(从有到无)
	 */
	function blockToHidden(){
		ele.style.filter="alpha(opacity="+(begin - step)+")";   // 设置滤镜
		begin = begin - step;                                   // 更改初始滤镜的百分比
		time1 = setTimeout("blockToHidden();",flashFrequency);  // 使用时间函数渐变元素
		if(begin <= 0){
			window.clearTimeout(time1);
		}
		if(begin <= 0){
			hiddenToBlock();
		}
	}
	
	/**
	 * 元素渐变方法(从无到有)
	 */
	function hiddenToBlock(){
		ele.style.filter="alpha(opacity="+(begin + step)+")";     // 设置滤镜
		begin = begin + step;                                     // 更改初始滤镜的百分比
		time2 = setTimeout("hiddenToBlock()",flashFrequency);     // 使用时间函数渐变元素
		if(begin >= 100){
			window.clearTimeout(time2);
			times++;                                              // 已经渐变次数(隐藏+显现为一次)
			if(times <= (parseInt(flashTimes) - 1)){             // 是否需要进行下一次的闪烁
				blockToHidden();
			}else{
				ele.style.borderColor = ""; 
			}
		}
	}
	
	/**
    * 检查传入的对象的值是否全是数字
    *
    * @param ele 页面传入的需要校验的field对象
    * @param msg 校验失败时的提示信息
    * @return boolean 返回校验是否通过
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


	//根据得到的经纬度信息转换成地图可识别的数值经纬度信息
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
    * 判断输入的是否全是数字。该函数一般不直接提供外部调用，而是由其它函数调用
    *
    * @param str为要校验的对象的值
    * @return boolean 返回校验的字符串是否全是数字    
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
	 * 函数初始化
	 * param element 待渐变的元素
	 * param flashTime 需要闪烁的次数
	 * param flashFrequency 闪烁频率(越大越慢)
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
			ele.style.borderColor = arguments[3];                               // 设置闪烁时元素的边框颜色
		}
	 }
	 /***********************************元素闪烁结束*****************************************/