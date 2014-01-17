//***************************************************************************
/**
 * @类名称:GetCharacters.java
 * @处理内容:截取项目的长度
 * @author ZhaoBin
 * @version 1.0，2010-5-12
 */
// ***************************************************************************
package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class GetCharacters extends TagSupport {

	private static final long serialVersionUID = -1083445515103096168L;

	private String value = "";
	
	private String length = "";

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
				// 截取项目的长度
				int totalCount = 0;
				String temStr = "";
				for (int i = 0;i < value.length(); i++) {
					char temChar = value.charAt(i);
					if((temChar>=0x0001 && temChar<=0x007e) || (temChar>=0xff60 && temChar<=0xff9f)){
						totalCount++;
					}else{
						totalCount +=2;
					}
					if(totalCount <= Integer.parseInt(length)){
						temStr = value.substring(0,i+1);
					}
					if(totalCount == Integer.parseInt(length) || totalCount == Integer.parseInt(length)-1){
						temStr = temStr + "...";
					}
				}

				out.println(temStr);
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
	
	/**
	 * @param length
	 *            要设置的 length
	 */
	public void setLength(String length) {
		this.length = length;
	}

}
