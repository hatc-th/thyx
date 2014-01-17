package com.hatc.base.web.tld;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
* 
* <b>system：</b>      ttims<br/>
* <b>description：</b> 字符创截取TLD类<br/>
* <b>author：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-05-13<br/>
*
**/
public class StringTruncation extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String value = "";
	private String length = "";

	/**
	 * 需要保留的字符串的长度(一个中文两个长度，专为前台页面展示使用。截断之后需要加省略号)
	 * 
	 * @throws IOException
	 * 
	 */
	@Override
	public int doStartTag() {
		// 获取输入流
		JspWriter out = pageContext.getOut();
		if (null == value || "".equals(value)) {
			return 0;
		} else {
			StringBuffer stringTemp = new StringBuffer(); // 存放临时字符串
			int length1 = 0; // 记录已经截取的字符串长度
			byte[] temp;
			for (int i = 0, j = value.length(); i < j; i++) {
				temp = value.substring(i, i + 1).getBytes(); // 获取当前字符的长度(一个中文字符两个长度)
				if (length1 < Integer.parseInt(length)) {
					length1 += temp.length;
					stringTemp.append(value.substring(i, i + 1));
				}
				// 当长度超过给定长度的时候，截掉最后一个字符
				if (length1 > Integer.parseInt(length)) {
					stringTemp.delete((stringTemp.length() - 1), stringTemp
							.length());
					break;
				}
			}
			// 如果待截取字符串的长度大于需截取的字符串的长度则在截取后的字符串后面加省略号
			if(value.getBytes().length > Integer.parseInt(length)){
				stringTemp.append("...");
			}
			// 将"解析成&quot;(&#034;),'解析成&#039;
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
