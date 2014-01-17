//***************************************************************************
/**
 * @类名称:ChangeLine.java
 * @处理内容:将数据库中的回车符转换成页面的回车符
 * @author ZhaoBin
 * @version 1.0，2010-5-24
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
				// 转换成回车符
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
	 *            要设置的 inValue
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
