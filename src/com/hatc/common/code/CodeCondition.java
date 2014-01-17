package com.hatc.common.code;

import java.util.ArrayList;
 
import com.hatc.common.servicestub.util.SortCmd;
import com.hatc.common.servicestub.util.SqlCondition;

public class CodeCondition 
{
	private static CodeCondition instance = null;
	private static SqlCondition condition = null;
	private CodeCondition()
	{
		//ArrayList<Code> codes = new ArrayList<Code>();
		condition = new SqlCondition();
		ArrayList<SortCmd> orders = new ArrayList<SortCmd>();
		SortCmd order = new SortCmd();
		order.fieldName = "CLASS_ID";
		order.order = "ASC";
		orders.add(order);
		condition.setOrders(orders);
		order = new SortCmd();
		order.fieldName = "ORDER_ID";
		order.order = "ASC";
		orders.add(order);
		condition.setOrders(orders);  
	}
	public static CodeCondition getInstance()
	{
		if(instance==null)
			instance = new CodeCondition();
		return instance;
	}
	
	public SqlCondition getSqlCondition()
	{
		
		return condition;
	}

}
