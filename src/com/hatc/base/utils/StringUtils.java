package com.hatc.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.hatc.base.common.RequestMap;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Area;
import com.runqian.report4.usermodel.ByteMap;
import com.runqian.report4.usermodel.IByteMap;
import com.runqian.report4.usermodel.INormalCell;

public class StringUtils
{
	
	/**替换字符串中的单引号为两个单引号
	 * 目的是保证执行查询SQL 时得到正确的结果而不出现异常
	 * @param conditionString
	 * @return
	 */
	public static String replaceSqlString(String inValue)
	{
	    if(inValue==null)
	    	return "";
	    else
	    	return inValue.replace("'", "''");
	}
	
	/* 统一对评估报表进行处理，将每个不通过原因作为一个单元格
	 * 进行数据处理主要是因为数据条数在一页上显示不完的时候润乾将报脚本错误
	 * add by yangliwei
	 */
	public static void evaluatingStringProcess(RequestMap map, ReportDefine reportDefine)
	{
		// 获取评估项目
		String[] content = map.getStringArray("content");
		List<String> contentList = new ArrayList<String>();
		// 获取是否评估
//		String[] isEvaluating = map.getStringArray("isEvaluating");
		// 获取评估结果
		String[] isPass = map.getStringArray("isPass");
		List<String> isPassList = new ArrayList<String>();
		// 获取不通过原因
		String[] reason = map.getStringArray("reason");
		List<String> reasonList = new ArrayList<String>();
		int k = 4, r1,r2;
		// 总行数
		Integer rows = content.length + 4;
		// 这里用换行来分割不通过原因
		for (int i = 4, j = rows; i < j; i++){
			String[] reasonArray = reason[i-4].split("\n");
			if(reasonArray.length > 2) { // 不通过原因有多个
				r1 = k; // 合并单元格的开始
				r2 = k + reasonArray.length - 1; // 合并单元格的结束
				Area area1 = new Area(r1,(short)1,r2,(short)1);
				Area area2 = new Area(r1,(short)2,r2,(short)2);
				// 这里因为reason这个数组里的最后一个数据是回车符所以减掉1
				for(int h = 0; h < reasonArray.length; h++){
					contentList.add(content[i-4]);
					isPassList.add(isPass[i-4]);
					reasonList.add(reasonArray[h]);
					reportDefine.addRow();
					setBoder(reportDefine, k, (short)5);
	//				if(h == 0) {
						setCell(reportDefine, k, 1, false, -1, INormalCell.HALIGN_LEFT, null, contentList.get(k - 4), null, 12, -16777216, null, 2.0f, area1);
						setCell(reportDefine, k, 2, false, -1, INormalCell.HALIGN_CENTER, null, isPassList.get(k - 4), null, 12, -16777216, null, 0.0f, area2);
	//				}
					setCell(reportDefine, k, 3, false, -1, INormalCell.HALIGN_LEFT, null, reasonList.get(k - 4), null, 12, -16777216, null, 2.0f, null);
					k++;
				}
			}
			else { // 不通过原因只有一个
				contentList.add(content[i-4]);
				isPassList.add(isPass[i-4]);
				reasonList.add(reason[i-4]);
				reportDefine.addRow();
				setBoder(reportDefine, k, (short)5);
				setCell(reportDefine, k, 1, false, -1, INormalCell.HALIGN_LEFT, null, contentList.get(k - 4), null, 12, -16777216, null, 2.0f, null);
				setCell(reportDefine, k, 2, false, -1, INormalCell.HALIGN_CENTER, null, isPassList.get(k - 4), null, 12, -16777216, null, 0.0f, null);
				setCell(reportDefine, k, 3, false, -1, INormalCell.HALIGN_LEFT, null, reasonList.get(k - 4), null, 12, -16777216, null, 2.0f, null);
				k++;
			}
		}
	}
	/**
	 * 设置报表边框
	 */
	private static void setBoder(ReportDefine rd, int row, int col) {
		// 设置报表边框
		for (int j = 1; j < col; j++) {
			rd.setBBColor(row, (short) j, -8355712); // 设定下边框线色
			rd.setBBStyle(row, (short) j, INormalCell.LINE_SOLID); // 设定下边框类型
			rd.setBBWidth(row, (short) j, (float) 0.75); // 设定下边框线粗
			// 左边框
			rd.setLBColor(row, (short) j, -8355712);
			rd.setLBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setLBWidth(row, (short) j, (float) 0.75);
			// 右边框
			rd.setRBColor(row, (short) j, -8355712);
			rd.setRBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setRBWidth(row, (short) j, (float) 0.75);
			// 上边框
			rd.setTBColor(row, (short) j, -8355712);
			rd.setTBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setTBWidth(row, (short) j, (float) 0.75);
		}
	}
	
	/**
	 * 设置报表单元格
	 */
	private static void setCell(ReportDefine rd, int row, int col, boolean bold, int back, byte align, String format,
			String value, String href, int fontSize, int foreColor, String exp, float indentValue, Area area) {
		// 获取单元格式
		INormalCell inc = rd.getCell(row, (short) col);
		// 设置字体是否加粗
		inc.setBold(bold);
		// 设置缩进
		inc.setIndent(indentValue);
		// 设置背景色
		inc.setBackColor(back);
		// 设置显示方式
		inc.setHAlign(align);
		// 设置显示内容
		inc.setValue(value);
		// 设置内容自动换行
		inc.setTextWrap(true);
		// 设置字体大小
		inc.setFontSize((short) fontSize);
		// 设置显示格式
		if (format != null && format.trim().length() > 0) {
			inc.setFormat(format);
		}
		// 设置超链接
		if (href != null && !href.equals("")) {
			inc.setHyperlink(href);
		}
		// 设置横向对齐方式
		inc.setVAlign(INormalCell.VALIGN_MIDDLE);
		//设置前景色
		inc.setForeColor(foreColor);
		//设置单元格的数据值表达式
		if (exp != null && exp.trim().length() > 0) {
			IByteMap map=new ByteMap();
			map.put(INormalCell.VALUE, exp);  
			inc.setExpMap(map);
		}
		// 单元格分页显示
//		inc.setSplitted(true);
//		inc.setStretchWhenPaged(true);
		if(area != null) inc.setMergedArea(area);
	}
}
