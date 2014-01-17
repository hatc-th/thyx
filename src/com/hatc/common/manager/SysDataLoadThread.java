package com.hatc.common.manager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hatc.common.web.servlet.WebRunConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SysDataLoadThread extends Thread
{
	private Log logger  = LogFactory.getLog(this.getClass());
	
    public int sleepTime = 0;
    
    @Override
	public void run()
    {
    	int i=0;
    	HashMap map = null;
    	Set keySet = null;
    	Iterator iter = null;
    	String key = "";
    	int count=0;
    	while(true)
    	{
    		try{
    			Thread.sleep( WebRunConfig.sysDataLoadSleepTime  ); // 30s
    		}catch(Exception e){
				logger.error(e.getMessage());
				e.printStackTrace();
			}
    		  
    		count=0;
    		map = SysDataLoadCommander.getInstance().getCommandMap();
    		keySet = map.keySet();
    		iter = keySet.iterator();
    		while(iter.hasNext())
    		{
    			count++;
    			key = (String)iter.next();    			
    		}
    		////////////////////////////////////////////////
    		// 需要加载配置文件
    		if(count>0)
    		{
    			if(logger.isDebugEnabled())
    			    logger.debug("    sysdataloadthread: load sys config data..."  );
    			  
    			try{
    				
    				
    				SysDataLoadManager manager = (SysDataLoadManager) WebRunConfig.webac.getBean("sysDataLoadManager");
    				
    				manager.process();
    				
    				////////////////////////////////////////////////////////////////////////////////
    				// 加载成功后从缓存中移走
    				////////////////////////////////////////////////////////////////////////////////
    				
    				keySet = map.keySet();
    	    		iter = keySet.iterator();
    	    		while(iter.hasNext())
    	    		{
    	    			count++;
    	    			key = (String)iter.next();
    	    			SysDataLoadCommander.getInstance().removeCommandMap(key); 
    	    		}
    				
    			}catch(Exception e){
    				logger.error(e.getMessage());
    				e.printStackTrace();
    			}
    		}
    		
    	}
    	
    	
    }
}
