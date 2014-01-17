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
	 
/**
* �õ���,��,��,�����ڷ�Χ
*/
function changePeriod() {
	var startDate = document.getElementById("startDate");
	var endDate = document.getElementById("endDate");
	var qdate = document.getElementById("date");
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth()+1;
	var startMonth;
	var endMonth;
	if (1 <= month && month <= 3) {
		startMonth = "01";
		endMonth = "03";
	} else if (4 <= month && month <= 6){
		startMonth = "04";
		endMonth = "06";
	} else if (7 <= month && month <= 9){
		startMonth = "07";
		endMonth = "09";
	} else if (10 <= month && month <= 12){
		startMonth = "10";
		endMonth = "12";
	}
	if (qdate.value == "LYEAR") {
		startDate.value = (year-1)+"-01"+"-01";
		endDate.value = (year-1)+"-12"+"-31";
	} else if (qdate.value == "NYEAR") {
		startDate.value = (year+1)+"-01"+"-01";
		endDate.value = (year+1)+"-12"+"-31";
	} else if (qdate.value == "R3YEAR") {
		startDate.value = (year-2)+"-01"+"-01";
		endDate.value = (year)+"-12"+"-31";
	} else if (qdate.value == "TYEAR") {
		startDate.value = year+"-01"+"-01";
  		endDate.value = year+"-12"+"-31";
	} else if (qdate.value == "LSEASON") {
		if (startMonth == "01" && endMonth == "03") {
			startDate.value = (year-1)+"-10"+"-01";
			endDate.value = (year-1)+"-12"+"-31";
		} else {
			startDate.value = year+"-0"+(parseInt(startMonth,10)-3)+"-01";
			endDate.value = year+"-0"+(parseInt(endMonth,10)-3)+"-"+getDay(""+year, "0"+(parseInt(endMonth,10)-3));
		}
	} else if (qdate.value == "NSEASON") {
		if (startMonth == "10" && endMonth == "12") {
			startDate.value = (year+1)+"-01"+"-01";
			endDate.value = (year+1)+"-03"+"-31";
		} else {
			if (startMonth == "07" && endMonth == "09") {
				startDate.value = year+"-10"+"-01";
				endDate.value = year+"-12"+"-31";
			} else {
				startDate.value = year+"-0"+(parseInt(startMonth,10)+3)+"-01";
 				endDate.value = year+"-0"+(parseInt(endMonth,10)+3)+"-"+getDay(""+year, "0"+(parseInt(endMonth,10)+3));
			}
		}
	} else if (qdate.value == "TSEASON") {
		startDate.value = year+"-"+startMonth+"-01";
 		endDate.value = year+"-"+endMonth+"-"+getDay(""+year, ""+endMonth);
	} else if (qdate.value == "LMONTH") {
		if (month == 1) {
			startDate.value = (year-1)+"-12"+"-01";
			endDate.value = (year-1)+"-12"+"-31";
		} else {
			if (new String(month-1).length == 1) {
				startDate.value = year+"-0"+(month-1)+"-01";
				endDate.value = year+"-0"+(month-1)+"-"+getDay(""+year, "0"+(month-1));
			} else if (new String(month-1).length == 2) {
				startDate.value = year+"-"+(month-1)+"-01";
				endDate.value = year+"-"+(month-1)+"-"+getDay(""+year, ""+(month-1));
			}
		}
	} else if (qdate.value == "NMONTH") {
		if (month == 12) {
			startDate.value = (year+1)+"-01"+"-01";
			endDate.value = (year+1)+"-01"+"-31";
		} else {
			if (new String(month+1).length == 1) {
				startDate.value = year+"-0"+(month+1)+"-01";
				endDate.value = year+"-0"+(month+1)+"-"+getDay(""+year, "0"+(month+1));
			} else if (new String(month+1).length == 2) {
				startDate.value = year+"-"+(month+1)+"-01";
				endDate.value = year+"-"+(month+1)+"-"+getDay(""+year, ""+(month+1));
			}
		}
	} else if (qdate.value == "R3MONTH") {
		if(parseInt(month) <= 2){
			var temp = parseInt(month) - 2;
			var startMonth = ((12 + temp + "").len() == 1) ? ("0" + (12 + temp)) : (12 + temp);
			startDate.value = (year-1)+"-"+startMonth+"-01";
 			endDate.value = year+"-"+((month + "").len() == 1 ? ("0" + month) : month)+"-"+getDay(""+year, ("0" + month));
		}else{
			var temp = (parseInt(month) - 2) + "";
			temp = (temp == "0" ? "1" : temp);
     		var endMonth = (parseInt(month) + "").len() == 1 ? "0" + (parseInt(month) + "") : (parseInt(month) + "");
			startDate.value = (year)+"-" + (temp.len() == 1 ? "0" + temp : temp) +"-01";
			endDate.value = year+"-"+ endMonth +"-"+getDay(""+year, endMonth);
		}
           
	} else if (qdate.value == "TMONTH") {
		if (new String(month).length == 1) {
			startDate.value = year+"-0"+month+"-01";
			endDate.value = year+"-0"+month+"-"+getDay(""+year, "0"+month);
		} else if (new String(month).length == 2) {
			startDate.value = year+"-"+month+"-01";
			endDate.value = year+"-"+month+"-"+getDay(""+year, ""+month);
		}
	} else if (qdate.value == "LWEEK") {
		startDate.value = getWeek("last", "start");
		endDate.value = getWeek("last", "end");
	} else if (qdate.value == "NWEEK") {
		startDate.value = getWeek("next", "start");
		endDate.value = getWeek("next", "end");
	} else if (qdate.value == "TWEEK") {
		startDate.value = getWeek("loc", "start");
		endDate.value = getWeek("loc", "end");
	} else {
		startDate.value = "";
		endDate.value = "";
	}
}

/**
* ͨ����,�µõ���
* param year ��
* param month ��
* return day ��
*/      
function getDay(year, month) { 
    if (month == "04" || month == "06" || month == "09" || month == "11") {
		day = "30";	
    } else if (month == "02" || month == "2") {
    	if(parseInt(year)%4===0 && parseInt(year)%100!==0 || parseInt(year)%400===0) {   /*���� */
    		day = "29";
    	} else {   /*��������*/
    		day = "28";
    	}
    } else {
    	day = "31";	
    }
    return day;
}
    
/**
* ͨ����ǵõ��ܵ�����
* param flag ����,����,���ܱ��
* param se ��ʼ,�������
* return week �ܵ�����
*/     
function getWeek(flag, se) {
	var sDates = new Date();
   	var sDate = sDates.getDate();
   	var sDay = sDates.getDay();
   	if (sDay == 0) {
    	sDay = 7;
   	}
        
	var eDates = new Date();
	var eDate = eDates.getDate();
   	var eDay = eDates.getDay();
   	if (eDay == 0) {
    	eDay = 7;
    }
        
   	var week;
        
 	if (flag == "loc") {
       sDates.setDate(sDate-(sDay-1));
       eDates.setDate(eDate+(6-(eDay-1)));
    } else if (flag == "last") {
       sDates.setDate(sDate+(-7-(sDay-1)));
       eDates.setDate(eDate+(-1-(eDay-1)));
    } else if (flag == "next") {
       sDates.setDate(sDate+(7-(sDay-1)));
       eDates.setDate(eDate+(13-(eDay-1)));
   	}
    if (se == "start") {
       if (new String(sDates.getMonth()+1).length == 1 && new String(sDates.getDate()).length == 1) {
           week = new String(sDates.getYear())+"-"+new String("0"+(sDates.getMonth()+1))+"-"+new String("0"+sDates.getDate());
       } else if(new String(sDates.getMonth()+1).length == 2 && new String(sDates.getDate()).length == 2) {
           week = new String(sDates.getYear())+"-"+new String(sDates.getMonth()+1)+"-"+new String(sDates.getDate());
       } else if(new String(sDates.getMonth()+1).length == 1 && new String(sDates.getDate()).length == 2) {
           week = new String(sDates.getYear())+"-"+new String("0"+(sDates.getMonth()+1))+"-"+new String(sDates.getDate());
       } else if(new String(sDates.getMonth()+1).length == 2 && new String(sDates.getDate()).length == 1) {
           week = new String(sDates.getYear())+"-"+new String(sDates.getMonth()+1)+"-"+new String("0"+sDates.getDate());
       }
    } else if (se == "end") {
       if (new String(eDates.getMonth()+1).length == 1 && new String(eDates.getDate()).length == 1) {
           week = new String(eDates.getYear())+"-"+new String("0"+(eDates.getMonth()+1))+"-"+new String("0"+eDates.getDate());
        } else if(new String(eDates.getMonth()+1).length == 2 && new String(eDates.getDate()).length == 2) {
           	week = new String(eDates.getYear())+"-"+new String(eDates.getMonth()+1)+"-"+new String(eDates.getDate());
        } else if(new String(eDates.getMonth()+1).length == 1 && new String(eDates.getDate()).length == 2) {
           	week = new String(eDates.getYear())+"-"+new String("0"+(eDates.getMonth()+1))+"-"+new String(eDates.getDate());
        } else if(new String(eDates.getMonth()+1).length == 2 && new String(eDates.getDate()).length == 1) {
           	week = new String(eDates.getYear())+"-"+new String(eDates.getMonth()+1)+"-"+new String("0"+eDates.getDate());
        }
    }
    return week;
}

/* ���ȥ���ַ�����β�ո������ַ���ֵ */
String.prototype.trim = function trim() {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

/* ����ַ�������(һ��˫�ֽ��ַ���2, ASCII�ַ���1) */
String.prototype.len = function len() {
    return this.trim().replace(/[^\x00-\xff]/g, 'aa').length;
};

/*-------------------------------------��������[��� 2.x]-------------------------------------*/

/* 2.1  ��֤��λ��� */
function isYear(yearStr, fieldName) {
    yearStr = yearStr.trim();
    var pattern = /^[0-9]{4}$/;
    if (pattern.test(yearStr) == false ) {
        //alert(fieldName + "�������������!��������ȷ��ʽ!��:2000");
        showConfirmDiv(4,fieldName + '�������������!��������ȷ��ʽ!��:2000','��ʾ��Ϣ');
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
        //alert(fieldName + "���·���������!��������ȷ��ʽ!��:08 �� 8");
        showConfirmDiv(4,fieldName + '���·���������!��������ȷ��ʽ!��:08 �� 8','��ʾ��Ϣ');
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
        //alert(fieldName + "��������������!��������ȷ��ʽ!��:08 �� 8");
        showConfirmDiv(4,fieldName + '��������������!��������ȷ��ʽ!��:08 �� 8','��ʾ��Ϣ');
        return false;
    }   
    if(month==4||month==6||month==9||month==11) {
          if(day==31) {
              //alert(fieldName + "��" + month + "��û��31��!");
              showConfirmDiv(4,fieldName + '��' + month + '��û��31��!','��ʾ��Ϣ');
              return false;
          }
    }
    if(month==2) {
          if(year%4==0 && year%100!=0 || year%400==0) {   /*���� */
              if(day > 29) {
                  //alert(fieldName + "��" + year + "��"+ month + "��û��"+day+"��!");
                  showConfirmDiv(4,fieldName + '��' + year + '��' + month + '��û��'+day+'��!','��ʾ��Ϣ');
                  return false;
              }
          }
          else {/*��������*/
              if(day > 28) {
                  //alert(fieldName + "��" + year + "��"+ month + "��û��"+day+"��!");
                  showConfirmDiv(4,fieldName + '��' + year + '��' + month + '��û��'+day+'��!','��ʾ��Ϣ');
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
    if(!reg.test(hour) || hour < 0 || hour >= 24){
        //alert(fieldName + "��Сʱ��������!��������ȷ��ʽ!��:08 �� 8");
        showConfirmDiv(4,fieldName + '��Сʱ��������!��������ȷ��ʽ!��:08 �� 8','��ʾ��Ϣ');
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
    if(!reg.test(minute) || minute < 0 || minute >= 60){
        //alert(fieldName + "��������������!��������ȷ��ʽ!��:08 �� 8");
        showConfirmDiv(4,fieldName + '��������������!��������ȷ��ʽ!��:08 �� 8','��ʾ��Ϣ');
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
    if(!reg.test(second) || second < 0 || second >= 60){
        //alert(fieldName + "������������!��������ȷ��ʽ!��:08 �� 8");
        showConfirmDiv(4,fieldName + '������������!��������ȷ��ʽ!��:08 �� 8','��ʾ��Ϣ');
        return false;
    }   
    return true;
}


/* 
*  ��������Ƿ�Ϸ� yyyy-MM-dd
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
		var msg = fieldName + "�����ڸ�ʽ��������!��������ȷ��ʽ!\n��:2008" + splitStr + "08" + splitStr + "08";
		if(msgType == null || msgType == 'l'){
			msg +=  " �� " + "2008" + splitStr + "8" + splitStr + "8";
		}
		showConfirmDiv(1,msg,'��ʾ��Ϣ');
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

/* 
*  ���ʱ�� hh:mm:ss �� hh:mm
*  ����˵��
*  @param ele ʱ��Ԫ��
*  @param fieldName ��ʾ��Ϣ �ֶ�����
*  @param timeType ʱ������ 	
			1 ��ʾУ���ʽ hh:mm:ss 
			2 ��ʾУ���ʽ hh:mm
			����ʱĬ�ϰ� hh:mm:ss  ����У��
*/
function isTime(ele ,fieldName, timeType){
    var date = ele.value.trim();
    var time_a = date.split(':');
    if(timeType==null || timeType==undefined){
    	timeType = 1;
    }
    var reg = new RegExp('^[0-9]{1,2}\:[0-9]{1,2}\:[0-9]{1,2}$');  
    var msg = fieldName + "����ʽ��������!��������ȷ��ʽ!\n��: 08:08:08" ;
    if(timeType == 2){
        reg = new RegExp('^[0-9]{1,2}\:[0-9]{1,2}$'); 
        msg = fieldName + "����ʽ��������!��������ȷ��ʽ!\n��: 08:08"
    }
    if(!reg.exec(date)){
    	showConfirmDiv(1, msg ,'��ʾ��Ϣ');
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    
    if(!isHour(time_a[0],fieldName)){
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    if(!isMinute(time_a[1],fieldName)){
        ele.focus();
        speciallyGoodEffect(ele);
        return false;
    }
    if(time_a[2]){
    	if(!isSecond(time_a[2],fieldName)){
	        ele.focus();
	        speciallyGoodEffect(ele);
	        return false;
	    }
    }
    
    return true;
}

/* ��֤��ֵ, ��/�����ִ����� false, �ǿշ��� true, ��ֵȥ����β�ո� */
/**
 * param elm ��ȷ���Ƿ�Ϊ�յı�Ԫ��
 * param alertText��ʾ��Ϣ
 */
function isFilled(elm, alertText) {
    // [��] select ��ѡ
    if ((elm.type).indexOf('select-multiple') == -1) {
        elm.value = (elm.value).trim();
    }
    var elmValue = elm.value;
    if (elmValue.length == 0) {
        if (alertText != null) {
        	showConfirmDiv(0,alertText,'��ʾ��Ϣ');
            if ((elm.type).indexOf('hidden') == -1) {
             	elm.focus();
            	speciallyGoodEffect(elm);
            }
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
var flashTimes = 3;                    // ��Ҫ��˸�Ĵ���
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
		showConfirmDiv(0,"Ԫ����˸��Ҫ�������˸��Ԫ�أ�",'��ʾ��Ϣ');
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
 /***********************************Ԫ����˸����*****************************************/
