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
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ϵͳ�Զ���ʼ��Servlet<br/>
* <b>author��</b>      ����<br/>
* <b>modify��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
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
			// ���ϵͳ�����ļ�����Ŀ�е�·��
			String path = config.getServletContext().getRealPath("/");
			WebRunConfig.WebHomeDIR = path;
	 
			logger.info("    WebHomeDIR:" + path );
			
			WebRunConfig.WebAppName = getAppName(path);
			
			logger.info("    WebAppName: " + WebRunConfig.WebAppName  );
	         
			//////////////////////////////////////////////////////////
			//��һ��: ��ʼ�� WebApplicationContext
			WebRunConfig.webac  = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
			
			//////////////////////////////////////////////////////////
			//�ڶ���: ��ʼ�� WprojectConfig.xml

			// ϵͳ�����ļ�
			String filePath = path + "WEB-INF/projectConfig.xml";
			// �ļ�����·����ʼ��
			ProjectInit.initFileSavePath(filePath);
			
		
			//////////////////////////////////////////////////////////
			//������: �����ϴ�·��

			// �ϴ�·���Ľ���
			String rootPath = ProjectConfig.getProjectConfigMap().get("fileSavePath");
			// �ж��ϴ�·���Ƿ��Ѿ�����
			rootPath = rootPath != null ? rootPath : "";
			// �ļ�����·��
			String[] pathArray  = {};
			// �ļ��ϴ�·���Ĵ�������(���·���������򴴽�)
			
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
			//���Ĳ�: ��ȡ��ʱ���ش������ݵ�sleep time
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
			//���岽: ��ʼ�� ������Function����
		
			// ��ȡ������Function����
			initFunctionConfig(path);
			
			/////////////////////////////////////////////////////////
			//������: ��ʼ�� �ֵ��
			/**
			logger.info("init code data begin......");
			// ��ʼ���ֵ��
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
			// ��ȡ���̾���·��
//			String realPath = config.getServletContext().getRealPath("");
			// ��ȡspring�ļ���ȡ������
//			ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{realPath + "/WEB-INF/applicationContext-DataSource.xml", realPath + "/WEB-INF/applicationContext-Hibernate.xml"});
			
//			///////////////////////////////////////////////////////
			//���߲�: ��ʼ�� ϵͳ������ 
			 
			
			SysDataLoadManager manager = (SysDataLoadManager) WebRunConfig.webac.getBean("sysDataLoadManager");
			
			manager.process();  
			    
		 /**
			logger.info("init ProjectParameters...");
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			// ��ʼϵͳ������
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
			//�ڰ˲�: ������ʱ��ȡ�������ݵ��߳�
			/**
			logger.info("    start SysDataLoadThread......");
			SysDataLoadThread sdt = new SysDataLoadThread();
			sdt.start();
			**/
		 
			
			//�ھŲ�: ��ȡStub Server �汾��
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
			//��ʮ��: ��¼������־
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
		/// �ر�����
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
