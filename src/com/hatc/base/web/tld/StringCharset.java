package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 字符串转码TLD类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class StringCharset extends TagSupport {

	private static final long serialVersionUID = -1083445515103096168L;

	private String value = "";

	private String inCharset = "";

	private String outCharset = "";

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
				// 进行转码
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
	 *            要设置的 value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param inCharset
	 *            要设置的 inCharset
	 */
	public void setInCharset(String inCharset) {
		this.inCharset = inCharset;
	}

	/**
	 * @param outCharset
	 *            要设置的 outCharset
	 */
	public void setOutCharset(String outCharset) {
		this.outCharset = outCharset;
	}

}
