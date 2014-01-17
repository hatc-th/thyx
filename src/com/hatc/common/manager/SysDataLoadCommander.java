package com.hatc.common.manager;

import java.util.HashMap;
import java.util.Date;
import com.hatc.base.utils.DateUtil;
/**
 * 系统数据定时加载管理接口: 
 * 
 *    管理接口:只是对外提供事件接口. 
 *            内部处理的规则: 接收到事件后,加入到指令缓存队列. 记录当前时间.requestTime 
 *                         定时扫描指令缓存队列, 过滤重复的指令(目前只有一种指令), 执行指令
 *                            如果执行指令成功,则将该指令从缓存中清除. 
 *                            如果执行失败,记录到日志.  下次会重新读取. 
 * @author ningliyu
 *
 */
public class SysDataLoadCommander
{
    private static 	SysDataLoadCommander instance = null;
    private static  HashMap commandMap = new HashMap();
    //通过记数来防止 key 重复
    private static  int count = 0;
    private SysDataLoadCommander()
    {
    	
    }
    public static SysDataLoadCommander getInstance()
    {
    	if(instance==null)
    		instance = new SysDataLoadCommander();
    	return instance;
    }
    
    public void sendCommand()    		
    {
    	String command="loadConfig";
    	Date date = new Date();
    	String times = DateUtil.getDateTimeString(date);
    	String key = command+ "_" + times + "_" + count;
    	count++;
    	commandMap.put(key, date);    	
    }
    
    public HashMap getCommandMap()    		
    {
    	return commandMap;
    }
    
    public void removeCommandMap(String inKey)    		
    {
    	commandMap.remove(inKey);
    } 
}
