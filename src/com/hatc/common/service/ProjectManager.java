package com.hatc.common.service;

import com.hatc.base.service.Manager;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 通用模块Manager接口定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public interface ProjectManager extends Manager{
	//获得最大登录次数
	public String getRelogonTimes()throws Exception;

}
