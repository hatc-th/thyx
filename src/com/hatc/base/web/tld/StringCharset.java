package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ַ���ת��TLD��<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class StringCharset extends TagSupport {

	private static final long serialVersionUID = -1083445515103096168L;

	private String value = "";

	private String inCharset = "";

	private String outCharset = "";

	/*
	 * ���� Javadoc��
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			// �ж�value����Ϊ��
			if (this.value != null && !this.value.equals("")) {
				// ����ת��
				out.println(new String(this.value.getBytes(this.inCharset),
						this.outCharset));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (SKIP_BODY);
	}

	/**
	 * @param value
	 *            Ҫ���õ� value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param inCharset
	 *            Ҫ���õ� inCharset
	 */
	public void setInCharset(String inCharset) {
		this.inCharset = inCharset;
	}

	/**
	 * @param outCharset
	 *            Ҫ���õ� outCharset
	 */
	public void setOutCharset(String outCharset) {
		this.outCharset = outCharset;
	}

}
