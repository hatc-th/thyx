package com.hatc.common.web.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 解析系统参数<br/>
* <b>author：</b>      王洋<br/>
* <b>modify：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectInit {
	protected static Logger log = Logger.getLogger("ProjectInit"); 
		 
	    
	public synchronized static void initFileSavePath(String file) throws Exception {
		ProjectConfigLoader.getInstance(file); 
	} 
	
	@SuppressWarnings("unchecked")
	public static void initFileSavePathOld(String file) throws JDOMException {

		String os = System.getProperty("os.name");
		log.info("    os.name:" + os ); 
		       
		try{
		SAXBuilder builder = new SAXBuilder();

		Document read_doc = builder.build(file);

		Element stu = read_doc.getRootElement(); 

		List list = stu.getChildren("item");

		String feild = "windows";

		if (os.indexOf("Win") < 0) {
			feild = "linux";
		}

		for (int i = 0; i < list.size(); i++) {

			Element e = (Element) list.get(i);

			String parameter = e.getChildText("parameter");

			String value = e.getChildText(feild);

			if (value == null) {
				value = e.getChildText("value");
			}
			   
			log.debug("   param:" + i + " :   " +  parameter + "  =  " + value  );
			  
			ProjectConfig.setProjectConfig(parameter, value);
		}
		
		}catch(Exception e){
			throw new JDOMException(e.getMessage());
		}
	}
	
	
	/**
	 * 读取资源文件
	 * 
	 * @param propertiesName资源文件名称
	 * @param key需要获取的值对应的键
	 * @return 资源文件里的值
	 */
	private static String getValueOOOOO(String propertiesName, String key){
		
		return UserResourceConfig.getInstance().getValue(key);
		 
	}
	/**
	 * 读取资源文件
	 * 
	 * @param propertiesName资源文件名称
	 * @param key需要获取的值对应的键
	 * @return 资源文件里的值
	 */
	private static String getValueOld(String propertiesName, String key){
		Properties pro = new Properties();
		Logger objLog = Logger.getLogger(ProjectInit.class);
		String value = "";
		// 将资源文件加载到当前类
		InputStream objStream = ProjectInit.class.getClassLoader().getResourceAsStream(propertiesName);
		try {
			pro.load(objStream);
			value = pro.getProperty(key);
		} catch (IOException e) {
			objLog.error("读取资源文件时发生异常", e);
			e.printStackTrace();
		} finally{
			try {
				objStream.close();
			} catch (IOException e) {
				objLog.error(e);
			}
		}
		return value;
	}
	
	/**
	 * 读取资源文件方法(读取projectConfig.xml中名称为"propertiesName"的值)
	 * 
	 * @param key需要读取的资源的键
	 * @return 对应键的值
	 */
	public static String getValue(String key){
		 
		String value = UserResourceConfig.getInstance().getValue(key);
		
		 
		if(null == value || "".equals(value)){
			value = key + "未定义";
		}
		return value;
	}
	
	
	/**
	 * 读取资源文件方法(读取projectConfig.xml中名称为"propertiesName"的值)
	 * 
	 * @param key需要读取的资源的键
	 * @return 对应键的值
	
	public static String getValueOld(String key){
		// 资源文件的名称
		String propertiesName = ProjectConfig.getProjectConfig("propertiesName");
		if(null == propertiesName || propertiesName.equals("")){
			propertiesName = "User_Resource_zh_CN.properties";
		}
		String value = getValue(propertiesName, key);
		if(null == value || "".equals(value)){
			value = key + "未定义";
		}
		return value;
	} */
	
}
