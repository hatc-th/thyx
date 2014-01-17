package com.hatc.common.service.security;

import java.util.ArrayList;

import com.hatc.common.businessdata.RoleFunction;
import com.hatc.common.businessdata.User;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.servicestub.ReqIdentity;

public interface SecurityService {

	User getUserInfo(String loginId) throws Exception;
	
	ArrayList<UserRole> getUserRoles(User user) throws Exception;
	
	void login(User user,ReqIdentity identity) throws Exception;
	
	void logout(String loginId) throws Exception;
	
	void releaseObjectLockBySession(ReqIdentity identity) throws Exception;
	
	public ArrayList<RoleFunction> getRoleFunctions(UserRole role) throws Exception ;
}
