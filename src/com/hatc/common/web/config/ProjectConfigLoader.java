package com.hatc.common.web.config;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
 

public class ProjectConfigLoader 
{
	
	protected static Logger log = Logger.getLogger("ProjectConfigLoader"); 
	
	
	private static ProjectConfigLoader instance = null;
	
	private static String projectConfigFile = "";
	
	private ProjectConfigLoader(String file) throws Exception
	{
		
		projectConfigFile = file;
		initFileSavePath(projectConfigFile);

	}

	public static ProjectConfigLoader getInstance(String file) throws Exception
	{
		if (instance == null)
			instance = new ProjectConfigLoader(file);
		return instance;

	}
	
	private static void initFileSavePath(String file) throws JDOMException {

		String os = System.getProperty("os.name");
		log.info("    os.name:" + os ); 
		     

		SAXBuilder builder = new SAXBuilder();
try{
		Document read_doc = builder.build(new File(file));

		Element stu = read_doc.getRootElement();

		List list = stu.getChildren("item");
		
		//log.info("    item size:" + list.size());   

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

}
