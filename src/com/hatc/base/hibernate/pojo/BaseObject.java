package com.hatc.base.hibernate.pojo;

import java.io.Serializable;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统基础类<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public abstract class BaseObject implements Serializable {

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

}
