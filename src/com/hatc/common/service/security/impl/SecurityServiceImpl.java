package com.hatc.common.service.security.impl;

import java.util.ArrayList;
import java.util.List;

import com.hatc.base.hibernate.util.Condition;
import com.hatc.base.hibernate.util.Operator;
import com.hatc.base.hibernate.util.Parameter;
import com.hatc.base.service.impl.BaseManager;
import com.hatc.common.businessdata.RoleFunction;
import com.hatc.common.businessdata.User;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.hibernate.pojo.TbUser;
import com.hatc.common.service.security.SecurityService;
import com.hatc.common.servicestub.ReqIdentity;

public class SecurityServiceImpl extends BaseManager implements SecurityService {
	
	/**
	 * ÓÃ»§µÇÂ¼
	 * Ð´Online±í
	 */
	public void login(User user,ReqIdentity identity) throws Exception {
		// TODO Auto-generated method stub

	}

	public ArrayList<UserRole> getUserRoles(User user) throws Exception {
		
		ArrayList<UserRole> ret =new ArrayList<UserRole>();
		StringBuffer sb = new StringBuffer("select t2.dept_id  , t4.name as deptName , t1.default_role_sign ,t1.role_id ,t3.name as roleName ")
		.append(" from TB_USER_AUTHORIZATION t1 join tb_user t2 on t2.USER_ID =  t1.USER_ID ") 
		.append(" join tb_role t3 on  t1.ROLE_ID = t3.ROLE_ID")
		.append(" join tb_department t4 on t4.DEPT_ID= t2.DEPT_ID") 
		.append(" where t2.login_Id ='").append(user.getLoginId()).append("'");
		
		List<String[]> l = dao.getObjects(sb.toString(), new String[]{"dept_id"  , "deptName" , "default_role_sign"
			,"role_id" ,"roleName"});
		if(l==null){
			return null;
		}
		for (String [] s :l){
			UserRole ur = new UserRole();
			
			ur.setDeptId(s[0]);
			ur.setDeptName(s[1]);
			ur.setIsDefaultRole("1".equals(s[2]));
			ur.setRoleId(s[3]);
			ur.setRoleName(s[4]);

			ret.add(ur);
		}
		
		return ret;
	}
	
	public ArrayList<RoleFunction> getRoleFunctions(UserRole role) throws Exception {
		
		ArrayList<RoleFunction> ret =new ArrayList<RoleFunction>();
		StringBuffer sb = new StringBuffer("select t2.function_id , t2.link, t2.name , t2.order_id ")
		.append(" from TB_ROLE_AUTHORIZATION t1  join tb_function t2  on  t1.function_id = t2.function_id  ") 
		.append(" where t2.validity = '1' and ")
		.append(" t1.role_id ='").append(role.getRoleId()).append("'");
		
		List<String[]> l = dao.getObjects(sb.toString(), new String[]{"function_id"  ,"link", "name" , "order_id"
			});
		if(l==null){
			return null;
		}
		for (String [] s :l){
			RoleFunction o = new RoleFunction();
			
			o.setFunctionId(s[0]);
			o.setFunctionDescription(s[1]);
			o.setFunctionName(s[2]);
			o.setOrderId(s[3]);

			ret.add(o);
		}
		
		return ret;
	}

	public void logout(String loginId) throws Exception {
		// TODO Auto-generated method stub

	}

	public void releaseObjectLockBySession(ReqIdentity identity)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public User getUserInfo(String loginId) throws Exception {
		Condition c = new Condition(TbUser.class); 
		
		c.addParameterAnd(new Parameter("loginId",Operator.SQL_EQ,new Object[]{loginId}));
		
		TbUser tu= (TbUser)dao.getObjects(c).get(0);
		if(tu==null){
			return null;
		}else{
			return new User(tu);
		}
		
	}

}
