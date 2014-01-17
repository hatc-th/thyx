package com.hatc.common.web.servlet;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hatc.common.manager.SysDataLoadManager;
import com.hatc.common.web.config.ProjectConfig;
import com.hatc.common.web.config.ProjectFunctionConfig;
import com.hatc.common.web.config.ProjectInit;
/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统自动初始化Servlet<br/>
* <b>author：</b>      王洋<br/>
* <b>modify：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectInitServlet extends HttpServlet 
{
    
	private Log logger = LogFactory.getLog(getClass());

	private static final long serialVersionUID = -2768177233501855177L;

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public String getAppName(String inPath)
	{
		
		int size = 0;
		
		 //WebAppName: \tti
		
		String webAppName = inPath;
		size = webAppName.length();
		if(inPath.charAt(size-1)=='\\') 
		{
			webAppName = webAppName.substring(0,size-1);
		}
		
		int index = 0;
		index=webAppName.lastIndexOf("\\");
	 
		if(index>=0)
			webAppName=webAppName.substring(index+1,webAppName.length());
		
		return webAppName;
	}
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			logger.info("ttims init start...");
			// 获得系统配置文件在项目中的路径
			String path = config.getServletContext().getRealPath("/");
			WebRunConfig.WebHomeDIR = path;
	 
			logger.info("    WebHomeDIR:" + path );
			
			WebRunConfig.WebAppName = getAppName(path);
			
			logger.info("    WebAppName: " + WebRunConfig.WebAppName  );
	         
			//////////////////////////////////////////////////////////
			//第一步: 初始化 WebApplicationContext
			WebRunConfig.webac  = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
			
			//////////////////////////////////////////////////////////
			//第二步: 初始化 WprojectConfig.xml

			// 系统配置文件
			String filePath = path + "WEB-INF/projectConfig.xml";
			// 文件保存路径初始化
			ProjectInit.initFileSavePath(filePath);
			
		
			//////////////////////////////////////////////////////////
			//第三步: 处理上传路径

			// 上传路径的建立
			String rootPath = ProjectConfig.getProjectConfigMap().get("fileSavePath");
			// 判断上传路径是否已经设置
			rootPath = rootPath != null ? rootPath : "";
			// 文件保存路径
			String[] pathArray  = {};
			// 文件上传路径的创建过程(如果路径不存在则创建)
			
			String savePath = "";
			File file = null;
			for (String string : pathArray) {
				savePath = rootPath + ProjectConfig.getProjectConfigMap().get(string);
				file = new File(savePath);
				if (!file.exists() && !file.isDirectory()) {
					boolean mkdirFlag = file.mkdirs();
					if(mkdirFlag==true)
					    logger.info("    make dir:" + savePath + " success.");
					else
						logger.error("    make dir:" + savePath + " failed.");
					
				}
			}
			 
			//////////////////////////////////////////////////////////
			//第四步: 获取定时加载代码数据的sleep time
			/**			
			String SysDataLoadSleepTime = ProjectConfig.getProjectConfigMap().get("SysDataLoadSleepTime");			 
			int intSysDataLoadSleepTime = 0;
			if(SysDataLoadSleepTime!=null) 
			{
				try{
					intSysDataLoadSleepTime = (new Integer(SysDataLoadSleepTime)).intValue();					 
				}catch(Exception e){	
					e.printStackTrace();     
					intSysDataLoadSleepTime=1;
					logger.info("    intSysDataLoadSleepTime: default : " + intSysDataLoadSleepTime );
				}
			}
			
			
			WebRunConfig.sysDataLoadSleepTime = intSysDataLoadSleepTime * 1000; 
			logger.info("    sysDataLoadSleepTime:" + WebRunConfig.sysDataLoadSleepTime );
			**/
			
			logger.info("    initFunctionConfig...");
			
			/////////////////////////////////////////////////////////
			//第五步: 初始化 方法的Function配置
		
			// 读取方法的Function配置
			initFunctionConfig(path);
			
			/////////////////////////////////////////////////////////
			//第六步: 初始化 字典表
			/**
			logger.info("init code data begin......");
			// 初始化字典表
			try {
				
				ReqIdentity identity = new ReqIdentity();
		    	identity.setUserId("Admin");
		    	ProjectItemCode.init(identity);
		    	
				//ProjectItemCode.init(new ReqIdentity());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}
			logger.info("init code data end......");
			**/
			// 获取工程绝对路径
//			String realPath = config.getServletContext().getRealPath("");
			// 读取spring文件获取上下文
//			ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{realPath + "/WEB-INF/applicationContext-DataSource.xml", realPath + "/WEB-INF/applicationContext-Hibernate.xml"});
			
//			///////////////////////////////////////////////////////
			//第七步: 初始化 系统参数表 
			 
			
			SysDataLoadManager manager = (SysDataLoadManager) WebRunConfig.webac.getBean("sysDataLoadManager");
			
			manager.process();  
			    
		 /**
			logger.info("init ProjectParameters...");
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			// 初始系统参数表
			try {
				Dao dao = (Dao)context.getBean("dao");
				ProjectParameters.init(dao);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}finally{
				 
			}   
			**/   
//			/////////////////////////////////////////////////////
			//第八步: 启动定时获取代码数据的线程
			/**
			logger.info("    start SysDataLoadThread......");
			SysDataLoadThread sdt = new SysDataLoadThread();
			sdt.start();
			**/
		 
			
			//第九步: 获取Stub Server 版本号
			//logger.info("    get Stub Server begin......");
/*			try {
				SecurityStub secuStub = new SecurityStub();
				String serverVersion = "";
				secuStub.getServerVersion(serverVersion); 
				logger.info("        StubServerVersion: " + StubServerConfig.StubServerVersion );
				 
			} catch (Exception e) {
				logger.error("    get Stub Server   failed......");
				logger.error(e);
			}*/
		 
			
			/////////////////////////////////////////////////////
			//第十步: 记录启动日志
			//logger.info("    write webserver start log  begin......");
			try {
//				LogStub lStub = new LogStub();				
//				String message = UserResourceConfig.getInstance().getValue("log.webserver.start");
//				String functionID = SystemConfig.FunctionID_WebServer_Start;
//				lStub.writeLog("", functionID, "1", message, "");	
			} catch (Exception e) {
				logger.error("    write webserver start log  failed......");
				logger.error(e);
			}
		    //logger.info("    write webserver start log  end......");
		        
		    
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			 
		}finally{
			logger.info("ttims init end...");
		}
	}
	
	private void initFunctionConfig(String path)
	{
        /////////////////////////////////////////////////////////////////
		/// ningliyu add 2011-11-16 
		/// 关闭连接
		////////////////////////////////////////////////////////////////
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path + "/WEB-INF/classes/MethodFunction.properties");
			ProjectFunctionConfig.getFunctionProperties().load(fis);
			//ProjectFunctionConfig.getFunctionProperties().load(new FileInputStream(path + "/WEB-INF/classes/MethodFunction.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(fis!=null)
			{
				try{
					fis.close();
				}catch(Exception e){
					 
				}
			}
		}
	}
	
	
}
