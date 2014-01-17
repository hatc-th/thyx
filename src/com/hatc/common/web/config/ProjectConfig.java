package com.hatc.common.web.config;

import java.util.HashMap;
import java.util.Map;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统参数MAP<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
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
