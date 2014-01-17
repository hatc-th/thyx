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
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 异常通知AOP<br/>
* <b>author：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/

public class ProjectExceptionAdvisor implements ThrowsAdvice {
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception e) {
		Logger log = Logger.getLogger(getClass());
		if(args==null)
			return; 
		// 获取方法参数
//		RequestMap map = (RequestMap) args[0];
//		Map<String, Object> paramMap = map.getMap();
//		StringBuffer paramMessage = new StringBuffer("");
//		for (String item : paramMap.keySet()) {
//			paramMessage.append(item + "=" + paramMap.get(item) + ";");
//		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("方法：");
		sb.append(method.getName());
		sb.append("发生异常。");		
		sb.append(SystemConfig.CHAR_LINE);
		sb.append("方法参数列表：");
		//sb.append(paramMessage.toString());
		sb.append(SystemConfig.CHAR_LINE);
		sb.append("异常信息：");
		sb.append(e.getClass());
		sb.append(ExceptionUtil.getExceptionMessage("",e));
		log.error(sb.toString()); 
		
		/**
		log.error("方法：" + method.getName() + "发生异常。\n  方法参数列表："
				+ paramMessage.toString() + "\n" + "  异常信息：" + e.getMessage()
				+ "\n" + "  堆栈信息：" + e.getStackTrace());
				**/
		
		 
	}
}
