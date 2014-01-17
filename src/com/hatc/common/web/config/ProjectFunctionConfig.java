package com.hatc.common.web.config;

import java.util.Properties;

public class ProjectFunctionConfig {
	
	private static Properties functionProperties = new Properties();

	public static String getFunctionProperties(String key) {
		return functionProperties.getProperty(key);
	}

	public static void setFunctionProperties(String key, String value) {
		functionProperties.put(key, value);
	}

	public static Properties getFunctionProperties() {
		return functionProperties;
	}
}
