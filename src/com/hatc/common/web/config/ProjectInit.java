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
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ����ϵͳ����<br/>
* <b>author��</b>      ����<br/>
* <b>modify��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
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
	 * ��ȡ��Դ�ļ�
	 * 
	 * @param propertiesName��Դ�ļ�����
	 * @param key��Ҫ��ȡ��ֵ��Ӧ�ļ�
	 * @return ��Դ�ļ����ֵ
	 */
	private static String getValueOOOOO(String propertiesName, String key){
		
		return UserResourceConfig.getInstance().getValue(key);
		 
	}
	/**
	 * ��ȡ��Դ�ļ�
	 * 
	 * @param propertiesName��Դ�ļ�����
	 * @param key��Ҫ��ȡ��ֵ��Ӧ�ļ�
	 * @return ��Դ�ļ����ֵ
	 */
	private static String getValueOld(String propertiesName, String key){
		Properties pro = new Properties();
		Logger objLog = Logger.getLogger(ProjectInit.class);
		String value = "";
		// ����Դ�ļ����ص���ǰ��
		InputStream objStream = ProjectInit.class.getClassLoader().getResourceAsStream(propertiesName);
		try {
			pro.load(objStream);
			value = pro.getProperty(key);
		} catch (IOException e) {
			objLog.error("��ȡ��Դ�ļ�ʱ�����쳣", e);
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
	 * ��ȡ��Դ�ļ�����(��ȡprojectConfig.xml������Ϊ"propertiesName"��ֵ)
	 * 
	 * @param key��Ҫ��ȡ����Դ�ļ�
	 * @return ��Ӧ����ֵ
	 */
	public static String getValue(String key){
		 
		String value = UserResourceConfig.getInstance().getValue(key);
		
		 
		if(null == value || "".equals(value)){
			value = key + "δ����";
		}
		return value;
	}
	
	
	/**
	 * ��ȡ��Դ�ļ�����(��ȡprojectConfig.xml������Ϊ"propertiesName"��ֵ)
	 * 
	 * @param key��Ҫ��ȡ����Դ�ļ�
	 * @return ��Ӧ����ֵ
	
	public static String getValueOld(String key){
		// ��Դ�ļ�������
		String propertiesName = ProjectConfig.getProjectConfig("propertiesName");
		if(null == propertiesName || propertiesName.equals("")){
			propertiesName = "User_Resource_zh_CN.properties";
		}
		String value = getValue(propertiesName, key);
		if(null == value || "".equals(value)){
			value = key + "δ����";
		}
		return value;
	} */
	
}
