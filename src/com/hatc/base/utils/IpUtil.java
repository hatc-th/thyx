package com.hatc.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 获取客户端IP地址<br/>
* <b>author：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2011-08-08<br/>
* <b>version：</b>     VER1.01 2012-04-06<br/>
**/

public class IpUtil {
	
	/**
	 * 获取客户端真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		try {
			// 增加取本地真实ip chenzj 2012.4.6
			if(ip.equals("127.0.0.1")|| ip.equals("0:0:0:0:0:0:0:1")){
				
			   ip  = InetAddress.getLocalHost().getHostAddress();
			}
			} catch (UnknownHostException e) {
					
					e.printStackTrace();}
		
		return ip;
	} 
}
