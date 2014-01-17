package com.hatc.common.service.aop;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;
import com.hatc.base.contants.SystemConfig;
import com.hatc.base.common.RequestMap;
import com.hatc.base.exception.ExceptionUtil;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �쳣֪ͨAOP<br/>
* <b>author��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/

public class ProjectExceptionAdvisor implements ThrowsAdvice {
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception e) {
		Logger log = Logger.getLogger(getClass());
		if(args==null)
			return; 
		// ��ȡ��������
//		RequestMap map = (RequestMap) args[0];
//		Map<String, Object> paramMap = map.getMap();
//		StringBuffer paramMessage = new StringBuffer("");
//		for (String item : paramMap.keySet()) {
//			paramMessage.append(item + "=" + paramMap.get(item) + ";");
//		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("������");
		sb.append(method.getName());
		sb.append("�����쳣��");		
		sb.append(SystemConfig.CHAR_LINE);
		sb.append("���������б�");
		//sb.append(paramMessage.toString());
		sb.append(SystemConfig.CHAR_LINE);
		sb.append("�쳣��Ϣ��");
		sb.append(e.getClass());
		sb.append(ExceptionUtil.getExceptionMessage("",e));
		log.error(sb.toString()); 
		
		/**
		log.error("������" + method.getName() + "�����쳣��\n  ���������б�"
				+ paramMessage.toString() + "\n" + "  �쳣��Ϣ��" + e.getMessage()
				+ "\n" + "  ��ջ��Ϣ��" + e.getStackTrace());
				**/
		
		 
	}
}
