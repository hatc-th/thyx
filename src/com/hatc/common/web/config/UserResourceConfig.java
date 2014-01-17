package com.hatc.common.web.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ��ȡ��Դ�ļ�:
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
			// ����Դ�ļ����ص���ǰ��
			InputStream objStream = null;
			try {
				objStream = ProjectInit.class.getClassLoader().getResourceAsStream(propertiesName);
				prop.load(objStream);
				 
			} catch (IOException e) {
				logger.error("��ȡ��Դ�ļ�ʱ�����쳣", e);
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
	 * ��ȡ��Դ�ļ�����(��ȡprojectConfig.xml������Ϊ"propertiesName"��ֵ)
	 * 
	 * @param key��Ҫ��ȡ����Դ�ļ�
	 * @return ��Ӧ����ֵ
	 */
	public String getValue(String key)
	{		 
		String value = prop.getProperty(key);
		if(null == value || "".equals(value)){
			value = key + "δ����";
		}
		
		//value.replace('nbsp',' ');
		 
		return value;
	}
	
	/**
	 * ��ȡ��Դ�ļ�����(��ȡprojectConfig.xml������Ϊ"propertiesName"��ֵ)
	 * ningliyu 2012-1-3 
	 * ������Դ�ļ��е�&nbsp;  ת��Ϊ�ո� 
	 * 
	 * @param key��Ҫ��ȡ����Դ�ļ�
	 * @return ��Ӧ����ֵ
	 */
	public String getHtmlToDataValue(String key)
	{		 
		String value = prop.getProperty(key);
		if(null == value || "".equals(value)){
			value = key + "δ����";
		}
		value = value.replace("&nbsp;", "");
		 
		return value;
	}
}
