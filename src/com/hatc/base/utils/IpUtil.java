package com.hatc.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ȡ�ͻ���IP��ַ<br/>
* <b>author��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2011-08-08<br/>
* <b>version��</b>     VER1.01 2012-04-06<br/>
**/

public class IpUtil {
	
	/**
	 * ��ȡ�ͻ�����ʵIP��ַ
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
			// ����ȡ������ʵip chenzj 2012.4.6
			if(ip.equals("127.0.0.1")|| ip.equals("0:0:0:0:0:0:0:1")){
				
			   ip  = InetAddress.getLocalHost().getHostAddress();
			}
			} catch (UnknownHostException e) {
					
					e.printStackTrace();}
		
		return ip;
	} 
}
