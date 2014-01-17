package com.hatc.common.manager;

import com.hatc.common.contants.ProjectItemCode;
import com.hatc.common.contants.ProjectParameters;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.servicestub.ReqIdentity;

public class SysDataLoadManagerImpl extends ProjectManagerImpl implements SysDataLoadManager
{

    private static 	SysDataLoadManagerImpl instance = null;
    private static 	ReqIdentity currentReqIdentity = null;
    
    private ProjectItemCode projectItemCode;
 
    public void setProjectItemCode(ProjectItemCode projectItemCode) {
		this.projectItemCode = projectItemCode;
	}
	private SysDataLoadManagerImpl()
    {
    	init();
    }
    public static SysDataLoadManagerImpl getInstance()
    {
    	if(instance==null)
    		instance = new SysDataLoadManagerImpl();
    	return instance;
    }
    
    public void init()
    {
    	if(currentReqIdentity==null)
    	{
    		currentReqIdentity = new ReqIdentity();
    		currentReqIdentity.setUserId("Admin");
    		currentReqIdentity.setFunctionId("S999999");
    	}
    }
    
    public void process() throws Exception
    {
    	init(); 
    	// 初始化字典表
    	projectItemCode.init(currentReqIdentity); 
	
    	// BaseDaoHibernate cdao = new BaseDaoHibernate();
    	// 初始系统参数表
    	ProjectParameters.init(dao);
    }
}
