package com.hatc.common.contants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hatc.base.hibernate.dao.Dao;
import com.hatc.common.hibernate.pojo.TbParameterSetup;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ϵͳ����ʵ����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/

public class ProjectParameters  implements java.io.Serializable {

		private static final long serialVersionUID = -105038452255205305L;

		private static Map<String, TbParameterSetup> parameterMap = new HashMap<String, TbParameterSetup>();

		/**
		 * ϵͳ������MAP�����ʼ��
		 * @param identity Ψһ��ʶ
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public static void init(Dao dao) throws Exception 
		{
			// ��ѯϵͳ������
			List<TbParameterSetup> parameters = dao.getObjects(TbParameterSetup.class);
			// Ӧ���Ȳ�ѯ,�����.....
			// ningliyu 2011-12-26
			// ���ϵͳ����MAP
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
		 * ��ȡϵͳ��������MAP
		 * @return ϵͳ��������MAP
		 */
		public static Map<String, TbParameterSetup> getParameterMap() {
			return parameterMap;
		}
		
		/**
		 * ����ϵͳ�������ƻ�ȡϵͳ��������
		 * @param paraName ϵͳ��������
		 * @return ϵͳ��������
		 */
		public static TbParameterSetup getParameter(String paraName){
			return parameterMap.get(paraName);
		}
}
