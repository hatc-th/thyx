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
	 
/**
* 得到年,季,月,周日期范围
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
* 通过年,月得到日
* param year 年
* param month 月
* return day 日
*/      
function getDay(year, month) { 
    if (month == "04" || month == "06" || month == "09" || month == "11") {
		day = "30";	
    } else if (month == "02" || month == "2") {
    	if(parseInt(year)%4===0 && parseInt(year)%100!==0 || parseInt(year)%400===0) {   /*闰年 */
    		day = "29";
    	} else {   /*不是闰年*/
    		day = "28";
    	}
    } else {
    	day = "31";	
    }
    return day;
}
    
/**
* 通过标记得到周的日期
* param flag 上周,本周,下周标记
* param se 开始,结束标记
* return week 周的日期
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

/* 获得去除字符串首尾空格后的新字符串值 */
String.prototype.trim = function trim() {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

/* 获得字符串长度(一个双字节字符计2, ASCII字符计1) */
String.prototype.len = function len() {
    return this.trim().replace(/[^\x00-\xff]/g, 'aa').length;
};

/*-------------------------------------日期类型[编号 2.x]-------------------------------------*/

/* 2.1  验证四位年份 */
function isYear(yearStr, fieldName) {
    yearStr = yearStr.trim();
    var pattern = /^[0-9]{4}$/;
    if (pattern.test(yearStr) == false ) {
        //alert(fieldName + "：年份输入有误!请输入正确格式!如:2000");
        showConfirmDiv(4,fieldName + '：年份输入有误!请输入正确格式!如:2000','提示信息');
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
        //alert(fieldName + "：月份输入有误!请输入正确格式!如:08 或 8");
        showConfirmDiv(4,fieldName + '：月份输入有误!请输入正确格式!如:08 或 8','提示信息');
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
        //alert(fieldName + "：天数输入有误!请输入正确格式!如:08 或 8");
        showConfirmDiv(4,fieldName + '：天数输入有误!请输入正确格式!如:08 或 8','提示信息');
        return false;
    }   
    if(month==4||month==6||month==9||month==11) {
          if(day==31) {
              //alert(fieldName + "：" + month + "月没有31日!");
              showConfirmDiv(4,fieldName + '：' + month + '月没有31日!','提示信息');
              return false;
          }
    }
    if(month==2) {
          if(year%4==0 && year%100!=0 || year%400==0) {   /*闰年 */
              if(day > 29) {
                  //alert(fieldName + "：" + year + "年"+ month + "月没有"+day+"日!");
                  showConfirmDiv(4,fieldName + '：' + year + '年' + month + '月没有'+day+'日!','提示信息');
                  return false;
              }
          }
          else {/*不是闰年*/
              if(day > 28) {
                  //alert(fieldName + "：" + year + "年"+ month + "月没有"+day+"日!");
                  showConfirmDiv(4,fieldName + '：' + year + '年' + month + '月没有'+day+'日!','提示信息');
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
    if(!reg.test(hour) || hour < 0 || hour >= 24){
        //alert(fieldName + "：小时输入有误!请输入正确格式!如:08 或 8");
        showConfirmDiv(4,fieldName + '：小时输入有误!请输入正确格式!如:08 或 8','提示信息');
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
    if(!reg.test(minute) || minute < 0 || minute >= 60){
        //alert(fieldName + "：分钟输入有误!请输入正确格式!如:08 或 8");
        showConfirmDiv(4,fieldName + '：分钟输入有误!请输入正确格式!如:08 或 8','提示信息');
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
    if(!reg.test(second) || second < 0 || second >= 60){
        //alert(fieldName + "：秒输入有误!请输入正确格式!如:08 或 8");
        showConfirmDiv(4,fieldName + '：秒输入有误!请输入正确格式!如:08 或 8','提示信息');
        return false;
    }   
    return true;
}


/* 
*  检查日期是否合法 yyyy-MM-dd
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
		var msg = fieldName + "：日期格式输入有误!请输入正确格式!\n如:2008" + splitStr + "08" + splitStr + "08";
		if(msgType == null || msgType == 'l'){
			msg +=  " 或 " + "2008" + splitStr + "8" + splitStr + "8";
		}
		showConfirmDiv(1,msg,'提示信息');
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
*  检查时刻 hh:mm:ss 或 hh:mm
*  参数说明
*  @param ele 时刻元素
*  @param fieldName 提示信息 字段名称
*  @param timeType 时刻类型 	
			1 表示校验格式 hh:mm:ss 
			2 表示校验格式 hh:mm
			不传时默认按 hh:mm:ss  进行校验
*/
function isTime(ele ,fieldName, timeType){
    var date = ele.value.trim();
    var time_a = date.split(':');
    if(timeType==null || timeType==undefined){
    	timeType = 1;
    }
    var reg = new RegExp('^[0-9]{1,2}\:[0-9]{1,2}\:[0-9]{1,2}$');  
    var msg = fieldName + "：格式输入有误!请输入正确格式!\n如: 08:08:08" ;
    if(timeType == 2){
        reg = new RegExp('^[0-9]{1,2}\:[0-9]{1,2}$'); 
        msg = fieldName + "：格式输入有误!请输入正确格式!\n如: 08:08"
    }
    if(!reg.exec(date)){
    	showConfirmDiv(1, msg ,'提示信息');
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

/* 验证空值, 空/纯空字串返回 false, 非空返回 true, 数值去除首尾空格 */
/**
 * param elm 待确认是否为空的表单元素
 * param alertText提示信息
 */
function isFilled(elm, alertText) {
    // [非] select 多选
    if ((elm.type).indexOf('select-multiple') == -1) {
        elm.value = (elm.value).trim();
    }
    var elmValue = elm.value;
    if (elmValue.length == 0) {
        if (alertText != null) {
        	showConfirmDiv(0,alertText,'提示信息');
            if ((elm.type).indexOf('hidden') == -1) {
             	elm.focus();
            	speciallyGoodEffect(elm);
            }
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
var flashTimes = 3;                    // 需要闪烁的次数
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
		showConfirmDiv(0,"元素闪烁需要传入待闪烁的元素！",'提示信息');
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
 /***********************************元素闪烁结束*****************************************/
/**
 * 按字节截取指定长度的字符串，不够指定长度的，直接返回原字符串
 *
 * @param str		需截取的字符串对象
 * @param ilen 限定的长度
*/
function getStringTruncation(str,ilen) {
		
	var temp="";
	var l=0;
	var schar;
	if(str.len() > ilen) {
		
		for(var i = 0; schar=str.charAt(i);i++) {
			
			temp+=schar;
			l +=(schar.match(/[^\x00-\xff]/)!=null?2:1);
			if(l>=ilen){
				break;
			}
		}
		if(temp.len()>ilen){
			temp = temp.substr(0,temp.length-1);
		}
		return temp+"...";	
	} else {
		return 	str;	
	}
}