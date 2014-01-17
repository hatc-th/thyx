package com.hatc.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 日期及HTML符号转换工具类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ConvertLang {

	public static final long ONE_DAY = 24 * 60 * 60 * 1000;

	/**
	 * 字符串转换成日期数据（用于比较）向后
	 * 
	 * @throws CommonException
	 */
	public static final String[] convertBackwardsDate(String inDate) {
		int in = convertint(inDate);
		String[] str = new String[2];
		Date date = new Date();
		str[0] = convertDate(date);
		str[1] = convertDate(new Date(date.getTime() + in * ConvertLang.ONE_DAY));
		return str;
	}

	/**
	 * 字符串转换成日期数据（用于比较）向前
	 * 
	 * @throws CommonException
	 */
	public static final String[] convertAlongDate(String inDate) {
		int in = convertint(inDate);
		String[] str = new String[2];
		Date date = new Date();
		str[0] = convertDate(new Date(date.getTime() - in * ConvertLang.ONE_DAY));
		str[1] = convertDate(date);
		return str;
	}

	/**
	 * 字符串类型转换成为Integer类型
	 * 
	 * @throws CommonException
	 */
	public static final Integer convertInteger(String str) {
		try {
			return str != null && !str.equals("") ? Integer.valueOf(str) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 字符串类型转换成为int类型
	 * 
	 * @throws CommonException
	 */
	public static final int convertint(String str) {
		if (convertInteger(str) == null) {

		}
		return convertInteger(str).intValue();
	}

	/**
	 * 字符串类型转换成为long类型
	 * 
	 * @throws CommonException
	 */
	public static final long convertlong(String str) {
		return Long.parseLong(str);
	}

	/**
	 * 字符串类型转换成为Double类型
	 * 
	 * @throws CommonException
	 */
	public static final Double convertDouble(String str) {
		try {
			return str != null && !str.equals("") ? Double.valueOf(str) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 字符串类型转换成为double类型
	 * 
	 * @throws CommonException
	 */
	public static final double convertdouble(String str) {
		if (convertDouble(str) == null) {

		}
		return convertDouble(str).doubleValue();
	}

	/**
	 * 将日期型转换成为字符串类型 yyyyMMdd
	 * 
	 * @throws CommonException
	 */
	public static final String convertDate(Date aDate) {
		return convertDate(aDate, "yyyyMMdd");
	}

	/**
	 * 将时间型转换成为字符串类型 HH:mm:ss
	 * 
	 * @throws CommonException
	 */
	public static final String convertTime(Date aDate) {
		return convertDate(aDate, "HH:mm:ss");
	}

	/**
	 * 将日期型转换成为字符串类型
	 * 
	 * @throws CommonException
	 */
	public static final String convertDate(Date aDate, String dataFormat) {
		try {
			SimpleDateFormat df = null;
			String returnValue = "";

			if (aDate != null) {
				df = new SimpleDateFormat(dataFormat);
				returnValue = df.format(aDate);
			}
			return returnValue;
		} catch (RuntimeException e) {
			return null;
		}

	}

	/**
	 * 将日期型转换成为字符串类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateTimeYMDHMS(Date aDate) {
		return convertDate(aDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期+时间型转换成为字符串类型 yyyyMMddHHmmssS
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateTime(Date aDate) {
		return convertDate(aDate, "yyyyMMddHHmmssS");
	}

	/**
	 * 将字符串型式的日期转换成为指定的格式(转换前：06_11_03 转换后：6年11个月3天)
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateFormat(String date, String separator) {
		try {
			String[] s = date.split(separator);
			StringBuffer strb = new StringBuffer();
			for (int i = 0; i < s.length; i++) {
				if (!s[i].equals("00")) {
					if (s[i].startsWith("0")) {
						s[i] = s[i].substring(1, 2);
					}
					if (i == 0) {
						strb.append(s[i] + "年");
					}
					if (i == 1) {
						strb.append(s[i] + "个月");
					}
					if (i == 2) {
						strb.append(s[i] + "天");
					}
				}
			}
			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 将字符串型式的日期转换成为指定的格式(转换前：061103转换后6年11个月3天)
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateFormat(String date) {
		try {
			StringBuffer strb = new StringBuffer();
			for (int i = 0; i < date.length(); i = i + 2) {
				String s = date.substring(i, i + 2);
				if (!s.equals("00")) {
					if (s.startsWith("0")) {
						s = s.substring(1, 2);
					}
					if (i == 0 || i == 1) {
						strb.append(s + "年");
					}
					if (i == 2 || i == 3) {
						strb.append(s + "个月");
					}
					if (i == 4 || i == 5) {
						strb.append(s + "天");
					}
				}
			}
			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 将字符串型式的日期转换成为指定的格式(转换前：2006-11-03 2006/11/03 转换后：20061103)
	 * 
	 * @throws CommonException
	 */
	public static final String convertDate(String date, String separator) {
		try {
			String[] s = date.split(separator);
			StringBuffer strb = new StringBuffer();
			for (int i = 0; i < s.length; i++) {
				strb.append(s[i]);
			}
			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 将字符串型式的日期转换成为指定的格式(转换前：20061103 转换后：2006-11-03)
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateSeparator(String date,
			String separator) {

		try {
			StringBuffer strb = new StringBuffer(date.subSequence(0, 4)
					+ separator + date.substring(4, 6) + separator
					+ date.substring(6, 8));

			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 取得系统时间的上一个月
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateLastMonnth() {

		try {
			Calendar cal = Calendar.getInstance();

			StringBuffer strb = new StringBuffer();

			strb.append(String.valueOf(cal.get(Calendar.YEAR)));

			strb.append(String.valueOf(cal.get(Calendar.MONTH)));

			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param doubleAdd
	 * @return
	 * @throws CommonException
	 */
	public static final Double convertDoubleAdd(Double[] doubleAdd) {

		try {
			double d = 0;

			for (int i = 0; i < doubleAdd.length; i++) {

				d = doubleAdd[i].doubleValue() + d;

			}

			return new Double(d);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param doubleAdd
	 * @return
	 * @throws CommonException
	 */

	public static final Double convertDoubleProduct(Double[] doubleAdd) {

		try {
			double d = 1;

			for (int i = 0; i < doubleAdd.length; i++) {

				d = doubleAdd[i].doubleValue() * d;

			}

			return new Double(d);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param args
	 * @throws CommonException
	 */
	public static final Double convertDoubleMinus(Double[] doubleAdd) {
		try {
			double d = 0;

			for (int i = 0; i < doubleAdd.length; i++) {

				d = doubleAdd[i].doubleValue() - d;

			}

			return new Double(d);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static final String toCodingString(String s, String code) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes(code);
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	// ***************************************************************************
	/**
	 * 处理内容：String字符集转换
	 * 
	 * @param str
	 *            字符串
	 * @param inCharset
	 *            字符串当前字符集
	 * @param outCharset
	 *            转换后的字符集
	 * @return 返回转换后的字符集
	 * 
	 */
	// ***************************************************************************
	public static final String stringCharset(String str, String inCharset,
			String outCharset) {
		String value = null;
		// 字符串不能为空
		if (str != null && !str.equals("")) {
			try {
				// 字符串转换
				value = new String(str.getBytes(inCharset), outCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	// ***************************************************************************
	/**
	 * 处理内容：HTML特殊字符集转换
	 * 
	 */
	// ***************************************************************************
	public static final String htmlSpecialChars(String str) {
		StringBuffer value = new StringBuffer();
		String[][] spacial = new String[4][2];
		spacial[0] = new String[] { "<", "&lt;" };
		spacial[1] = new String[] { ">", "&gt;" };
		spacial[2] = new String[] { "\"", "&quot;" };
		spacial[3] = new String[] { "&", "&amp;" };
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '<':
				value.append("&lt;");
				break;
			case '>':
				value.append("&gt;");
				break;
			case '"':
				value.append("&quot;");
				break;
			case '&':
				value.append("&amp;");
				break;
			default:
				value.append(c);
			}
		}
		return value.toString();
	}

	public static void main(String[] args) {
		// System.out.println(ConvertLang.convertint("1"));
		// System.out.println(ConvertLang.convertDate(new Date()));
		// System.out.println(ConvertLang.convertDateTime(new Date()));
		// System.out.println(ConvertLang.convertDate("2006-11-03", "-"));
		// System.out.println(ConvertLang.convertDateSeparator("20061103",
		// "-"));
		// System.out.println(ConvertLang.convertDateFormat("01_00_01", "_"));
		// System.out.println(ConvertLang.convertBackwardsDate("20")[0]);
		// System.out.println(ConvertLang.convertTime(new Date()));
		// System.out.println(ConvertLang.convertDate(new Date(),
		// "yyyy-MM-dd"));
		System.out.println(ConvertLang.convertDateFormat("010601"));

	}
}