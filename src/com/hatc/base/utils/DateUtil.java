package com.hatc.base.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public static final long ONE_DAY_TIMES = 24*60*60*1000;

	/**
	 * ��ʽ��������
	 */
	public static final String format(String strYear, String strMonth,
			String strDay) {
		// ��ʽ��������� yyyy-MM-dd
		StringBuffer sbDate = new StringBuffer();
		// ��
		int intYear = Integer.parseInt(strYear);
		// ��
		int intMonth = Integer.parseInt(strMonth);
		// ��
		int intDay = Integer.parseInt(strDay);

		// ���������
		int intMaxDay = maxDayOfMonth(intYear, intMonth);

		// �жϵ�ǰ�����Ƿ�������������
		if (intDay > intMaxDay) {
			intMonth += 1;
			intDay = 1;
		}
		// �жϵ�ǰ�����Ƿ�������Ǵ�����
		if (intMonth > 12) {
			intYear += 1;
			intMonth = 1;
		}

		return sbDate.append(intYear).append("-").append(
				parseDateStyle(intMonth)).append("-").append(
				parseDateStyle(intDay)).toString();
	}

	/**
	 * �ж��Ƿ�������
	 */
	public static final boolean isLeapYear(int intYear) {
		return intYear % 4 == 0 && intYear % 100 != 0 || intYear % 400 == 0;
	}

	/**
	 * �õ������������
	 */
	public static final int maxDayOfMonth(int intYear, int intMonth) {
		int intMaxDay = 31;

		if (intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) {
			// С��
			intMaxDay = 30;
		} else if (intMonth == 2) {
			// 2��
			if (isLeapYear(intYear)) {
				// ����
				intMaxDay = 29;
			} else {
				// ��������
				intMaxDay = 28;
			}
		}

		return intMaxDay;
	}

	/**
	 * �������ڸ�ʽ���� (�����ڻ��·�С��10��ʱ���Զ�����)
	 */
	public static final String parseDateStyle(int value) {
		return value > 10 ? String.valueOf(value) : "0" + value;
	}

	/**
	 * �ó������·�
	 */
	public static int[] parseQuarter(int intMonth) {
		int[] tempMonth = new int[3];
		if (intMonth / 3 > 3) {
			tempMonth = new int[] { 10, 11, 12 };
		} else if (intMonth / 3 > 2) {
			tempMonth = new int[] { 7, 8, 9 };
		} else if (intMonth / 3 > 1) {
			tempMonth = new int[] { 4, 5, 6 };
		} else {
			tempMonth = new int[] { 1, 2, 3 };
		}
		return tempMonth;
	}
	
	/**
	 * �ó���������֧������������ֵ
	 */
	public static Date[] parseWeek(int intShift, char zone) {
		Date[] tempWeek = new Date[2];
		
		Calendar cal = new GregorianCalendar();
		
		int intWeekRemark = 0;
		switch (zone) {
			case 'Z':
				intWeekRemark = cal.get(Calendar.DAY_OF_WEEK) - 1;
				break;
			default:
				intWeekRemark = cal.get(Calendar.DAY_OF_WEEK);
				break;
		}
		
		tempWeek[0] = new Date(new Date().getTime() - ((intWeekRemark - 1) * ONE_DAY_TIMES) + (intShift * 7 * ONE_DAY_TIMES));
		tempWeek[1] = new Date(new Date().getTime() + ((7 - intWeekRemark) * ONE_DAY_TIMES) + (intShift * 7 * ONE_DAY_TIMES));
		
		return tempWeek;
	}
	
	/**
	 * �ó���������֧������������ֵ
	 */
	public static Date[] parseWeek(int intShift) {
		return parseWeek(intShift, 'Z');
	}
	
	/**
	 * �ó��������ڵĺ�һ��
	 */
	public static Date getTomorrow(Date today) {
		String[] dateString = DateFormat.getDateInstance().format(today).split("-");
		int day = Integer.parseInt(dateString[2]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1])-1, day+1);
		return calendar.getTime();
	}
	
	/**
	 * �ó��������ڵ�ǰһ��
	 */
	public static Date getYesterday(Date today) {
		String[] dateString = DateFormat.getDateInstance().format(today).split("-");
		int day = Integer.parseInt(dateString[2]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1])-1, day-1);
		return calendar.getTime();
	}
	/**
	 * ��ȡ��ǰʱ���ַ���
	 * @param inDate
	 * @return
	 */
	public static String getDateTimeString(Date inDate)
	{		 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(inDate);
	}
}
