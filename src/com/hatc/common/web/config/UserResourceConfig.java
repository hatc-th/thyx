package com.hatc.common.web.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取资源文件:
 * @author ningliyu
 *
 */

public class UserResourceConfig 
{
	Logger logger = Logger.getLogger(UserResourceConfig.class);
	
	private static UserResourceConfig instance = null;
	private static String propertiesName = "";
	private static Properties prop = null;
	private UserResourceConfig()   
	{
		propertiesName = ProjectConfig.getProjectConfig("propertiesName");
		if(null == propertiesName || propertiesName.equals("")){
			propertiesName = "Resource_zh_CN.properties";		
		}
		loadConfigFile();
	}
	
	public static UserResourceConfig getInstance()  
	{
		if(instance == null )
			instance = new UserResourceConfig();
		return instance;
	}

	private void loadConfigFile()  
	{	 
		    prop = new Properties();
			
			String value = "";
			// 将资源文件加载到当前类
			InputStream objStream = null;
			try {
				objStream = ProjectInit.class.getClassLoader().getResourceAsStream(propertiesName);
				prop.load(objStream);
				 
			} catch (IOException e) {
				logger.error("读取资源文件时发生异常", e);
				e.printStackTrace();
			} finally{
				try {
					objStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			 
		}
	}
	
	/**
	 * 读取资源文件方法(读取projectConfig.xml中名称为"propertiesName"的值)
	 * 
	 * @param key需要读取的资源的键
	 * @return 对应键的值
	 */
	public String getValue(String key)
	{		 
		String value = prop.getProperty(key);
		if(null == value || "".equals(value)){
			value = key + "未定义";
		}
		
		//value.replace('nbsp',' ');
		 
		return value;
	}
	
	/**
	 * 读取资源文件方法(读取projectConfig.xml中名称为"propertiesName"的值)
	 * ningliyu 2012-1-3 
	 * 处理资源文件中的&nbsp;  转换为空格。 
	 * 
	 * @param key需要读取的资源的键
	 * @return 对应键的值
	 */
	public String getHtmlToDataValue(String key)
	{		 
		String value = prop.getProperty(key);
		if(null == value || "".equals(value)){
			value = key + "未定义";
		}
		value = value.replace("&nbsp;", "");
		 
		return value;
	}
}
