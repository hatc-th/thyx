//***************************************************************************
/**
 * @������:GetCharacters.java
 * @��������:��ȡ��Ŀ�ĳ���
 * @author ZhaoBin
 * @version 1.0��2010-5-12
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
				// ��ȡ��Ŀ�ĳ���
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
	 *            Ҫ���õ� inValue
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @param length
	 *            Ҫ���õ� length
	 */
	public void setLength(String length) {
		this.length = length;
	}

}
