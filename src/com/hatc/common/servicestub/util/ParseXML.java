package com.hatc.common.servicestub.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
import java.net.URLDecoder;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseXML {
    //定义一个Properties 用来存放属性值
    private Properties props;

    public Properties getProps() {
        return this.props;
    }

    public void parse(String filename) throws Exception {
    	String url = URLDecoder.decode((this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()),"UTF-8");
		String classPath = "/WEB-INF";
		int index = url.indexOf(classPath);
		if(index > 0){
			url = url.substring(0,index + classPath.length() + 1);
		}
		//System.out.println(url + filename);
        //将我们的解析器对象化
        ConfigParser handler = new ConfigParser();
        //获取SAX工厂对象
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        //获取SAX解析
        SAXParser parser = factory.newSAXParser();
        try {
            //将解析器和解析对象xml联系起来,开始解析
            parser.parse(url + filename, handler);
            Properties prop = System.getProperties();
            String username = prop.getProperty("user.name");
            String userhome = prop.getProperty("user.home");
            String userdir = prop.getProperty("user.dir");
            //System.out.println(username + ":" + userhome + ":" + userdir);
            //获取解析成功后的属性
            props = handler.getProps();
        } finally {
            factory = null;
            parser = null;
            handler = null;
        }
    }

    /**
     * main
     *
     * @param args String
     */
    public static void main(String[] args) {
        Properties props;
        ParseXML myRead = new ParseXML();
        try {
            myRead.parse("AppSrvConfig.xml");
            //props = new Properties();
            props = myRead.getProps();
            String address = props.getProperty("address");
            String port = props.getProperty("port");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
