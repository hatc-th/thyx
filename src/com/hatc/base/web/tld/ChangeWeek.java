//***************************************************************************
/**
 * @类名称:ChangeWeek.java
 * @处理内容:第几周转换为日期范围TLD类
 * @author ZhaoBin
 * @version 1.0，2010-5-11
 */
// ***************************************************************************
package com.hatc.base.web.tld;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ChangeWeek extends TagSupport {

	private static final long serialVersionUID = -1083445515103096168L;

	private String value = "";

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			// 判断value不能为空
			if (this.value != null && !this.value.equals("")) {
				// 进行转换
				String weekPlan = value;
				int year = Integer.parseInt(weekPlan.substring(0, 4));
				int month = Integer.parseInt(weekPlan.substring(4, 6));
				int week = Integer.parseInt(weekPlan.substring(6, 7));
				Calendar cal = Calendar.getInstance();
				cal.set(year, month-1, week*7);
				Calendar startCal = Calendar.getInstance();
				startCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+1+1);
				String startDate = "";
				if (String.valueOf(startCal.get(Calendar.MONTH)+1).length() == 1 && String.valueOf(startCal.get(Calendar.DAY_OF_MONTH)).length() == 1) {
					startDate = startCal.get(Calendar.YEAR)+"-0"+(startCal.get(Calendar.MONTH)+1)+"-0"+startCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(startCal.get(Calendar.MONTH)+1).length() == 2 && String.valueOf(startCal.get(Calendar.DAY_OF_MONTH)).length() == 2) {
		       		startDate = startCal.get(Calendar.YEAR)+"-"+(startCal.get(Calendar.MONTH)+1)+"-"+startCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(startCal.get(Calendar.MONTH)+1).length() == 1 && String.valueOf(startCal.get(Calendar.DAY_OF_MONTH)).length() == 2) {
		       		startDate = startCal.get(Calendar.YEAR)+"-0"+(startCal.get(Calendar.MONTH)+1)+"-"+startCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(startCal.get(Calendar.MONTH)+1).length() == 2 && String.valueOf(startCal.get(Calendar.DAY_OF_MONTH)).length() == 1) {
		       		startDate = startCal.get(Calendar.YEAR)+"-"+(startCal.get(Calendar.MONTH)+1)+"-0"+startCal.get(Calendar.DAY_OF_MONTH);
		       	}
				Calendar endCal = Calendar.getInstance();
				endCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_WEEK)+7+1);
				String endDate = "";
				if (String.valueOf(endCal.get(Calendar.MONTH)+1).length() == 1 && String.valueOf(endCal.get(Calendar.DAY_OF_MONTH)).length() == 1) {
					endDate = endCal.get(Calendar.YEAR)+"-0"+(endCal.get(Calendar.MONTH)+1)+"-0"+endCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(endCal.get(Calendar.MONTH)+1).length() == 2 && String.valueOf(endCal.get(Calendar.DAY_OF_MONTH)).length() == 2) {
		       		endDate = endCal.get(Calendar.YEAR)+"-"+(endCal.get(Calendar.MONTH)+1)+"-"+endCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(endCal.get(Calendar.MONTH)+1).length() == 1 && String.valueOf(endCal.get(Calendar.DAY_OF_MONTH)).length() == 2) {
		       		endDate = endCal.get(Calendar.YEAR)+"-0"+(endCal.get(Calendar.MONTH)+1)+"-"+endCal.get(Calendar.DAY_OF_MONTH);
		       	} else if (String.valueOf(endCal.get(Calendar.MONTH)+1).length() == 2 && String.valueOf(endCal.get(Calendar.DAY_OF_MONTH)).length() == 1) {
		       		endDate = endCal.get(Calendar.YEAR)+"-"+(endCal.get(Calendar.MONTH)+1)+"-0"+endCal.get(Calendar.DAY_OF_MONTH);
		       	}
				out.println(startDate+"至"+endDate+weekPlan.substring(7));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (SKIP_BODY);
	}

	/**
	 * @param inValue
	 *            要设置的 inValue
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
