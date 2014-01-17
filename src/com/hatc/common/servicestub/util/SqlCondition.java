/// 飞行组织管理服务普通工具包
package com.hatc.common.servicestub.util;

import java.util.ArrayList;

/**
 * 查询条件类
 * @code
 * 		FilterCmd cmd = new FilterCmd();
		SortCmd order = new SortCmd();
		KeyCmd key = new KeyCmd();
		ArrayList cmds = new ArrayList();		
		
		cmd.fieldName="A.CLASS_ID";
		cmd.lowerLimit="%TYPE%";
		cmd.operater="LIKE";
		cmds.add(cmd);
		
		
		cmd = new FilterCmd();
		cmd.fieldName="A.CODE_ID";
		cmd.lowerLimit="1";
		cmd.operater="=";
		cmd.fieldType="C";
		cmds.add(cmd);
		condition.setCondictionList(cmds);
		
		ArrayList orders = new ArrayList();
		order.fieldName="CLASS_ID";
		order.order="ASC";
		orders.add(order);
		condition.setOrders(orders);	
	@endcode
 */
public class SqlCondition {
	public SqlCondition() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList getCondictionList() {
		return condictionList;
	}

	public void setCondictionList(ArrayList condictionList) {
		this.condictionList = condictionList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public ArrayList getKeys() {
		return keys;
	}

	public void setKeys(ArrayList keys) {
		this.keys = keys;
        this.keyss.add(keys);
	}

	public ArrayList getOrders() {
		return orders;
	}

	public void setOrders(ArrayList orders) {
		this.orders = orders;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	

	private ArrayList<FilterCmd> condictionList = new ArrayList<FilterCmd>(); 	///< 条件列表

    private ArrayList<KeyCmd> keys = new ArrayList<KeyCmd>();       ///< 条件列表(OR关系)

    private ArrayList<ArrayList<KeyCmd> > keyss = new ArrayList<ArrayList<KeyCmd> >();   ///< (多个OR关系相与)

	private String expression = new String(); 		///< 条件这间关系表达式 （目前先考虑And）

	private ArrayList<SortCmd> orders = new ArrayList<SortCmd>(); 	///< 排序方式
    private boolean isCustomSort = false;                   ///< 是否排序
	private String priorSql = "";	///< 不为空串则优先使用(前导语句以 "where (1=1)"结束)
												
	private int pageNo = 0; 	///< 页码，为０则不分页

	private int pageSize = 0; 	///< 每页行数

	private int count = 0; 		///< 总记录行数（输出参数）

	/**
	 * 生成Where, Order By子句串
	 */
	@Override
	public	String toString() {
		String retStr = new String();
        isCustomSort = false;

		int iter;
		for (iter = 0; iter < condictionList.size(); iter++) {
			retStr += " AND ";
			if ((condictionList.get(iter)).logic.equalsIgnoreCase("NOT")) {
				retStr = "NOT" + retStr;
			}
			if ((condictionList.get(iter)).operater.equalsIgnoreCase("LIKE")) {
				retStr += ((condictionList.get(iter)).fieldName
						+ " LIKE '"
						+ (condictionList.get(iter)).lowerLimit + "'");
			} else if (((condictionList.get(iter)).operater.equalsIgnoreCase("NULL"))
					|| ((condictionList.get(iter)).operater.equalsIgnoreCase("NOT NULL"))) {
				retStr += ((condictionList.get(iter)).fieldName
						+ " IS " + (condictionList.get(iter)).lowerLimit);
			} else {
				if ((condictionList.get(iter)).fieldType.equalsIgnoreCase("C"))
					retStr += ((condictionList.get(iter)).fieldName
							+ (condictionList.get(iter)).operater
							+ "'"
							+ (condictionList.get(iter)).lowerLimit + "'");
				else if ((condictionList.get(iter)).fieldType.equalsIgnoreCase("D"))
					retStr += ((condictionList.get(iter)).fieldName
							+ (condictionList.get(iter)).operater
							+ " TIMESTAMP '"
							+ (condictionList.get(iter)).lowerLimit + "'");
				else if ((condictionList.get(iter)).fieldType.equalsIgnoreCase("I"))
					retStr += ((condictionList.get(iter)).fieldName
							+ (condictionList.get(iter)).operater + (condictionList
							.get(iter)).lowerLimit);
			}

		}

		for (int iter_keys = 0; iter_keys < keyss.size(); iter_keys++) {
		    ArrayList<KeyCmd> keys = keyss.get(iter_keys);
		    int iter1;
		    /** **OR 关系 *** */
		    for (iter1 = 0; iter1 < keys.size(); iter1++) {

		        if (iter1 == 0) {
		            retStr += " AND ( ";
		        } else {
		            retStr += " OR ";
		        }
		        if (((keys.get(iter1)).operater == "LIKE")
		                || ((keys.get(iter1)).operater == "like")) {
		            retStr += ((keys.get(iter1)).keyName + " LIKE '"
		                    + (keys.get(iter1)).limit + "'");
		        } else if (((keys.get(iter1)).operater == "NULL")
		                || ((keys.get(iter1)).operater == "NOT NULL")) {
		            retStr += ((keys.get(iter1)).keyName + " IS " + (keys
		                    .get(iter1)).limit);
		        } else {
		            if ((keys.get(iter1)).fieldType == "C")
		                retStr += ((keys.get(iter1)).keyName
		                        + (keys.get(iter1)).operater + "'"
		                        + (keys.get(iter1)).limit + "'");
		            else if ((keys.get(iter1)).fieldType == "D")
		                retStr += ((keys.get(iter1)).keyName
		                        + (keys.get(iter1)).operater
		                        + " TIMESTAMP '" + (keys.get(iter1)).limit + "'");
		            else if ((keys.get(iter1)).fieldType == "I")
		                retStr += ((keys.get(iter1)).keyName
		                        + (keys.get(iter1)).operater + (keys
		                                .get(iter1)).limit);
		        }

		        if (iter1 == (keys.size() - 1)) {
		            retStr += " ) ";
		        }
		    }
		}
		int sort;
		for (sort = 0; sort < orders.size(); sort++) {
            isCustomSort = true;
			if (sort == 0) {
				retStr += " ORDER BY ";
			} else {
				retStr += " , ";
			}
			retStr += (orders.get(sort)).fieldName + " "
					+ (orders.get(sort)).order;
		}
        if (priorSql != "") {
            return priorSql;
        }
		return retStr;
	}

	private void jbInit() throws Exception {
	}

	public String getPriorSql() {
		return priorSql;
	}

	public void setPriorSql(String priorSql) {
		this.priorSql = priorSql;
	}

    public boolean isCustomSort() {
        return isCustomSort;
    }

    public void setCustomSort(boolean isCustomSort) {
        this.isCustomSort = isCustomSort;
    }

    public ArrayList<ArrayList<KeyCmd>> getKeyss() {
        return keyss;
    }

    public void setKeyss(ArrayList<ArrayList<KeyCmd>> keyss) {
        this.keyss = keyss;
    }
};
