package com.hatc.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ���ڼ�HTML����ת��������<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class ConvertLang {

	public static final long ONE_DAY = 24 * 60 * 60 * 1000;

	/**
	 * �ַ���ת�����������ݣ����ڱȽϣ����
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
	 * �ַ���ת�����������ݣ����ڱȽϣ���ǰ
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
	 * �ַ�������ת����ΪInteger����
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
	 * �ַ�������ת����Ϊint����
	 * 
	 * @throws CommonException
	 */
	public static final int convertint(String str) {
		if (convertInteger(str) == null) {

		}
		return convertInteger(str).intValue();
	}

	/**
	 * �ַ�������ת����Ϊlong����
	 * 
	 * @throws CommonException
	 */
	public static final long convertlong(String str) {
		return Long.parseLong(str);
	}

	/**
	 * �ַ�������ת����ΪDouble����
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
	 * �ַ�������ת����Ϊdouble����
	 * 
	 * @throws CommonException
	 */
	public static final double convertdouble(String str) {
		if (convertDouble(str) == null) {

		}
		return convertDouble(str).doubleValue();
	}

	/**
	 * ��������ת����Ϊ�ַ������� yyyyMMdd
	 * 
	 * @throws CommonException
	 */
	public static final String convertDate(Date aDate) {
		return convertDate(aDate, "yyyyMMdd");
	}

	/**
	 * ��ʱ����ת����Ϊ�ַ������� HH:mm:ss
	 * 
	 * @throws CommonException
	 */
	public static final String convertTime(Date aDate) {
		return convertDate(aDate, "HH:mm:ss");
	}

	/**
	 * ��������ת����Ϊ�ַ�������
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
	 * ��������ת����Ϊ�ַ������� yyyy-MM-dd HH:mm:ss
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateTimeYMDHMS(Date aDate) {
		return convertDate(aDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ������+ʱ����ת����Ϊ�ַ������� yyyyMMddHHmmssS
	 * 
	 * @throws CommonException
	 */
	public static final String convertDateTime(Date aDate) {
		return convertDate(aDate, "yyyyMMddHHmmssS");
	}

	/**
	 * ���ַ�����ʽ������ת����Ϊָ���ĸ�ʽ(ת��ǰ��06_11_03 ת����6��11����3��)
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
						strb.append(s[i] + "��");
					}
					if (i == 1) {
						strb.append(s[i] + "����");
					}
					if (i == 2) {
						strb.append(s[i] + "��");
					}
				}
			}
			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * ���ַ�����ʽ������ת����Ϊָ���ĸ�ʽ(ת��ǰ��061103ת����6��11����3��)
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
						strb.append(s + "��");
					}
					if (i == 2 || i == 3) {
						strb.append(s + "����");
					}
					if (i == 4 || i == 5) {
						strb.append(s + "��");
					}
				}
			}
			return strb.toString();
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * ���ַ�����ʽ������ת����Ϊָ���ĸ�ʽ(ת��ǰ��2006-11-03 2006/11/03 ת����20061103)
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
	 * ���ַ�����ʽ������ת����Ϊָ���ĸ�ʽ(ת��ǰ��20061103 ת����2006-11-03)
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
	 * ȡ��ϵͳʱ�����һ����
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
	 * ���ļ����еĺ���תΪUTF8����Ĵ�,�Ա�����ʱ����ȷ��ʾ�����ļ���.
	 * 
	 * @param s
	 *            ԭ�ļ���
	 * @return ���±������ļ���
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
	 * �������ݣ�String�ַ���ת��
	 * 
	 * @param str
	 *            �ַ���
	 * @param inCharset
	 *            �ַ�����ǰ�ַ���
	 * @param outCharset
	 *            ת������ַ���
	 * @return ����ת������ַ���
	 * 
	 */
	// ***************************************************************************
	public static final String stringCharset(String str, String inCharset,
			String outCharset) {
		String value = null;
		// �ַ�������Ϊ��
		if (str != null && !str.equals("")) {
			try {
				// �ַ���ת��
				value = new String(str.getBytes(inCharset), outCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	// ***************************************************************************
	/**
	 * �������ݣ�HTML�����ַ���ת��
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