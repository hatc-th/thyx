package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
* 
* <b>system��</b>      ttims<br/>
* <b>description��</b> �ַ�����ȡTLD��<br/>
* <b>author��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-05-13<br/>
*
**/
public class StringTruncation extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String value = "";
	private String length = "";

	/**
	 * ��Ҫ�������ַ����ĳ���(һ�������������ȣ�רΪǰ̨ҳ��չʾʹ�á��ض�֮����Ҫ��ʡ�Ժ�)
	 * 
	 * @throws IOException
	 * 
	 */
	@Override
	public int doStartTag() {
		// ��ȡ������
		JspWriter out = pageContext.getOut();
		if (null == value || "".equals(value)) {
			return 0;
		} else {
			StringBuffer stringTemp = new StringBuffer(); // �����ʱ�ַ���
			int length1 = 0; // ��¼�Ѿ���ȡ���ַ�������
			byte[] temp;
			for (int i = 0, j = value.length(); i < j; i++) {
				temp = value.substring(i, i + 1).getBytes(); // ��ȡ��ǰ�ַ��ĳ���(һ�������ַ���������)
				if (length1 < Integer.parseInt(length)) {
					length1 += temp.length;
					stringTemp.append(value.substring(i, i + 1));
				}
				// �����ȳ����������ȵ�ʱ�򣬽ص����һ���ַ�
				if (length1 > Integer.parseInt(length)) {
					stringTemp.delete((stringTemp.length() - 1), stringTemp
							.length());
					break;
				}
			}
			// �������ȡ�ַ����ĳ��ȴ������ȡ���ַ����ĳ������ڽ�ȡ����ַ��������ʡ�Ժ�
			if(value.getBytes().length > Integer.parseInt(length)){
				stringTemp.append("...");
			}
			// ��"������&quot;(&#034;),'������&#039;
			try {
				for(int i = 0; i < stringTemp.length(); i++){
					if(stringTemp.charAt(i) == '"'){
						stringTemp.replace(i, i + 1, "&#034;");
					}
					if(stringTemp.charAt(i) == '\''){
						stringTemp.replace(i, i + 1, "&#039;");
					}
				}
				out.println(stringTemp.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
