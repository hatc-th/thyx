package com.hatc.common.web.config;

import java.util.HashMap;
import java.util.Map;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ϵͳ����MAP<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectConfig {

	private static Map<String, String> projectConfigMap = new HashMap<String, String>();

	public static String getProjectConfig(String key) {
		return projectConfigMap.get(key);
	}

	public static void setProjectConfig(String key, String value) {
		projectConfigMap.put(key, value);
	}

	public static Map<String, String> getProjectConfigMap() {
		return projectConfigMap;
	}
}
