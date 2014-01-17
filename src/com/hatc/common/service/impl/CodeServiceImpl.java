package com.hatc.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hatc.base.service.impl.BaseManager;
import com.hatc.common.businessdata.Code;
import com.hatc.common.service.CodeService;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.servicestub.util.SqlCondition;

public class CodeServiceImpl extends BaseManager implements CodeService {

	public boolean getCodes(ReqIdentity identity, SqlCondition condition,
			ArrayList<Code> codes) throws Exception {

		StringBuffer sb = new StringBuffer(
				"select c.class_id,c.code_id,c.code_detail ,c.name ,c.superior_id ,c.export_flag ,c.sys_flag,c.order_id,c.nameenc ,cc.name as className")
				.append(" from tb_code c  join tb_code_class cc on c.class_id = cc.class_id ")
				.append(" order by c.class_id, c.order_id");
		List<String[]> result = dao.getObjects(sb.toString(), new String[] {"class_id","code_id","code_detail","name",
			"superior_id","export_flag","sys_flag","order_id","nameenc","className"});

		for (String[] s : result) {
			Code c = new Code();
			c.setClass_id(s[0]);
			c.setCode_id(s[1]);
			c.setCode_detail(s[2]);
			c.setClass_name(s[9]);
			c.setName(s[3]);
			// c.setClass_name(tc.getc)
			codes.add(c);
		}
		return true;
	}
}
