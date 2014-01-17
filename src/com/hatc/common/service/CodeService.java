package com.hatc.common.service;

import java.util.ArrayList;

import com.hatc.common.businessdata.Code;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.servicestub.util.SqlCondition;

public interface CodeService {
	public boolean getCodes(ReqIdentity identity, SqlCondition condition,
			ArrayList<Code> codes) throws Exception;
}
