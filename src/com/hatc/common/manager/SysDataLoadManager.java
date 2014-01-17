package com.hatc.common.manager;

import com.hatc.common.service.ProjectManager;

public interface SysDataLoadManager extends ProjectManager 
{
     
    public void process() throws Exception;
    
}
