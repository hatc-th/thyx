//***************************************************************************
/**
 * @������:ChangeLine.java
 * @��������:�����ݿ��еĻس���ת����ҳ��Ļس���
 * @author ZhaoBin
 * @version 1.0��2010-5-24
 */
// ***************************************************************************
package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ChangeLine extends TagSupport {

	private static final long serialVersionUID = -1083445515103096168L;

	private String value = "";

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
				// ת���ɻس���
				String changeStr = value.replaceAll("\n", "<br />");
				out.println(changeStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (SKIP_BODY);
	}

	/**
	 * @param inValue
	 *            Ҫ���õ� inValue
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
