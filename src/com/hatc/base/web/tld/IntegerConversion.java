/**
 * <ul>
 * <li><b>system：</b>       TTIMS</li>
 * <li><b>description：</b>  自定义Tag：进制转换</li>
 * <li><b>author：</b>       黄承</li>
 * <li><b>copyright：</b>    北京华安天诚科技有限公司</li>
 * <li><b>version：</b>      Ver1.00 2010.11.17</li>
 * </ul>
 */

package com.hatc.base.web.tld;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class IntegerConversion extends TagSupport {
    
    private static final long serialVersionUID = 4036059882301761985L;
    private Integer value; // 待转换整数
    private String split; // 分隔符(默认为":")
    private Integer param; // 转换进制(默认为60)
    private Integer num; // 叠代转换次数(默认为1)
    private boolean force; // 是否强制迭代(默认为false)
    
    @Override
	public int doStartTag() {
        try {
            // 获取输入流
            JspWriter out = pageContext.getOut();
            split = split == null ? ":" : split;
            param = param == null ? 60 : param;
            num = num == null ? 1 : num;
            force = force != true ? false : force;
            int quotient = value; // 商数
            int residual; // 余数
            String temp_residual = ""; // 临时余数
            String temp_prefix = ""; // 前缀补白
            NumberFormat nf = new DecimalFormat("00"); // 强制格式化(如果不存在则显示为 0)
            if (quotient < param) {
                if (force == false) {
                    // 不强制迭代, 不转换
                    out.println(quotient);
                } else {
                    // 强制迭代, 不转换
                    for (int i = 0; i < num; i++) {
                        temp_prefix = "00" + split;
                    }
                    out.println(temp_prefix + nf.format(quotient));
                }
            } else {
                if (force == false) {
                    // 不强制迭代, 转换
                    for (int i = 0; i < num; i++) {
                        if (quotient >= param) {
                            residual = quotient % param;
                            quotient = quotient / param;
                            temp_residual = "" + nf.format(residual) + split + temp_residual;
                        }
                    }
                    temp_residual = "" + quotient + split + temp_residual;
                    out.println(temp_residual.substring(0, temp_residual.length() - 1));
                } else {
                    // 强制迭代, 转换
                    for (int i = 0; i < num; i++) {
                        if (quotient >= param) {
                            residual = quotient % param;
                            quotient = quotient / param;
                            temp_residual = "" + nf.format(residual) + split + temp_residual;
                        } else {
                            temp_prefix = "00" + split + temp_prefix;
                        }
                    }
                    temp_residual = temp_prefix + nf.format(quotient) + split + temp_residual;
                    out.println(temp_residual.substring(0, temp_residual.length() - 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(SKIP_BODY);
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getParam() {
        return param;
    }

    public void setParam(Integer param) {
        this.param = param;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean getForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
