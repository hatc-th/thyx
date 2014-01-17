package com.hatc.common.contants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hatc.base.hibernate.dao.Dao;
import com.hatc.common.hibernate.pojo.TbParameterSetup;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统参数实现类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/

public class ProjectParameters  implements java.io.Serializable {

		private static final long serialVersionUID = -105038452255205305L;

		private static Map<String, TbParameterSetup> parameterMap = new HashMap<String, TbParameterSetup>();

		/**
		 * 系统参数表MAP对象初始化
		 * @param identity 唯一标识
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public static void init(Dao dao) throws Exception 
		{
			// 查询系统参数表
			List<TbParameterSetup> parameters = dao.getObjects(TbParameterSetup.class);
			// 应该先查询,后清除.....
			// ningliyu 2011-12-26
			// 清除系统参数MAP
			if(parameters == null)
				return;
			if(parameters.size()==0)
				return;
			parameterMap.clear();
			for (TbParameterSetup par : parameters) {
				parameterMap.put(par.getParaName(), par);
			}
		}

		/**
		 * 获取系统参数对象MAP
		 * @return 系统参数对象MAP
		 */
		public static Map<String, TbParameterSetup> getParameterMap() {
			return parameterMap;
		}
		
		/**
		 * 根据系统参数名称获取系统参数对象
		 * @param paraName 系统参数名称
		 * @return 系统参数对象
		 */
		public static TbParameterSetup getParameter(String paraName){
			return parameterMap.get(paraName);
		}
}
