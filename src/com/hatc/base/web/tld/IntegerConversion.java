/**
 * <ul>
 * <li><b>system��</b>       TTIMS</li>
 * <li><b>description��</b>  �Զ���Tag������ת��</li>
 * <li><b>author��</b>       �Ƴ�</li>
 * <li><b>copyright��</b>    ����������ϿƼ����޹�˾</li>
 * <li><b>version��</b>      Ver1.00 2010.11.17</li>
 * </ul>
 */

package com.hatc.base.web.tld;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class IntegerConversion extends TagSupport {
    
    private static final long serialVersionUID = 4036059882301761985L;
    private Integer value; // ��ת������
    private String split; // �ָ���(Ĭ��Ϊ":")
    private Integer param; // ת������(Ĭ��Ϊ60)
    private Integer num; // ����ת������(Ĭ��Ϊ1)
    private boolean force; // �Ƿ�ǿ�Ƶ���(Ĭ��Ϊfalse)
    
    @Override
	public int doStartTag() {
        try {
            // ��ȡ������
            JspWriter out = pageContext.getOut();
            split = split == null ? ":" : split;
            param = param == null ? 60 : param;
            num = num == null ? 1 : num;
            force = force != true ? false : force;
            int quotient = value; // ����
            int residual; // ����
            String temp_residual = ""; // ��ʱ����
            String temp_prefix = ""; // ǰ׺����
            NumberFormat nf = new DecimalFormat("00"); // ǿ�Ƹ�ʽ��(�������������ʾΪ 0)
            if (quotient < param) {
                if (force == false) {
                    // ��ǿ�Ƶ���, ��ת��
                    out.println(quotient);
                } else {
                    // ǿ�Ƶ���, ��ת��
                    for (int i = 0; i < num; i++) {
                        temp_prefix = "00" + split;
                    }
                    out.println(temp_prefix + nf.format(quotient));
                }
            } else {
                if (force == false) {
                    // ��ǿ�Ƶ���, ת��
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
                    // ǿ�Ƶ���, ת��
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
