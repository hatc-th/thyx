package com.hatc.common.web.filter;

import java.util.HashMap;

/**
 * 在线用户管理
 * @author ningliyu
 *
 */

public class UserOnlineManager 
{
    private static UserOnlineManager instance = null;
    private static HashMap userMap;
    private UserOnlineManager()
    {
    	userMap = new HashMap();
    }
    
    public static UserOnlineManager getInstance()
    {
    	if(instance == null)
    		instance = new UserOnlineManager();
    	return instance; 
    }
    
    public void putUser(String inUserID,Object inData)
    {
    	userMap.put(inUserID,inData);
    }
    
    public Object getUser(String inUserID)
    {
    	return userMap.get(inUserID);
    }
    
    public boolean containsUser(String inUserID)
    {
    	return userMap.containsKey(inUserID);
    }
    
}
