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
	
	/**�滻�ַ����еĵ�����Ϊ����������
	 * Ŀ���Ǳ�ִ֤�в�ѯSQL ʱ�õ���ȷ�Ľ�����������쳣
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
	
	/* ͳһ������������д�����ÿ����ͨ��ԭ����Ϊһ����Ԫ��
	 * �������ݴ�����Ҫ����Ϊ����������һҳ����ʾ�����ʱ����Ǭ�����ű�����
	 * add by yangliwei
	 */
	public static void evaluatingStringProcess(RequestMap map, ReportDefine reportDefine)
	{
		// ��ȡ������Ŀ
		String[] content = map.getStringArray("content");
		List<String> contentList = new ArrayList<String>();
		// ��ȡ�Ƿ�����
//		String[] isEvaluating = map.getStringArray("isEvaluating");
		// ��ȡ�������
		String[] isPass = map.getStringArray("isPass");
		List<String> isPassList = new ArrayList<String>();
		// ��ȡ��ͨ��ԭ��
		String[] reason = map.getStringArray("reason");
		List<String> reasonList = new ArrayList<String>();
		int k = 4, r1,r2;
		// ������
		Integer rows = content.length + 4;
		// �����û������ָͨ��ԭ��
		for (int i = 4, j = rows; i < j; i++){
			String[] reasonArray = reason[i-4].split("\n");
			if(reasonArray.length > 2) { // ��ͨ��ԭ���ж��
				r1 = k; // �ϲ���Ԫ��Ŀ�ʼ
				r2 = k + reasonArray.length - 1; // �ϲ���Ԫ��Ľ���
				Area area1 = new Area(r1,(short)1,r2,(short)1);
				Area area2 = new Area(r1,(short)2,r2,(short)2);
				// ������Ϊreason�������������һ�������ǻس������Լ���1
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
			else { // ��ͨ��ԭ��ֻ��һ��
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
	 * ���ñ���߿�
	 */
	private static void setBoder(ReportDefine rd, int row, int col) {
		// ���ñ���߿�
		for (int j = 1; j < col; j++) {
			rd.setBBColor(row, (short) j, -8355712); // �趨�±߿���ɫ
			rd.setBBStyle(row, (short) j, INormalCell.LINE_SOLID); // �趨�±߿�����
			rd.setBBWidth(row, (short) j, (float) 0.75); // �趨�±߿��ߴ�
			// ��߿�
			rd.setLBColor(row, (short) j, -8355712);
			rd.setLBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setLBWidth(row, (short) j, (float) 0.75);
			// �ұ߿�
			rd.setRBColor(row, (short) j, -8355712);
			rd.setRBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setRBWidth(row, (short) j, (float) 0.75);
			// �ϱ߿�
			rd.setTBColor(row, (short) j, -8355712);
			rd.setTBStyle(row, (short) j, INormalCell.LINE_SOLID);
			rd.setTBWidth(row, (short) j, (float) 0.75);
		}
	}
	
	/**
	 * ���ñ���Ԫ��
	 */
	private static void setCell(ReportDefine rd, int row, int col, boolean bold, int back, byte align, String format,
			String value, String href, int fontSize, int foreColor, String exp, float indentValue, Area area) {
		// ��ȡ��Ԫ��ʽ
		INormalCell inc = rd.getCell(row, (short) col);
		// ���������Ƿ�Ӵ�
		inc.setBold(bold);
		// ��������
		inc.setIndent(indentValue);
		// ���ñ���ɫ
		inc.setBackColor(back);
		// ������ʾ��ʽ
		inc.setHAlign(align);
		// ������ʾ����
		inc.setValue(value);
		// ���������Զ�����
		inc.setTextWrap(true);
		// ���������С
		inc.setFontSize((short) fontSize);
		// ������ʾ��ʽ
		if (format != null && format.trim().length() > 0) {
			inc.setFormat(format);
		}
		// ���ó�����
		if (href != null && !href.equals("")) {
			inc.setHyperlink(href);
		}
		// ���ú�����뷽ʽ
		inc.setVAlign(INormalCell.VALIGN_MIDDLE);
		//����ǰ��ɫ
		inc.setForeColor(foreColor);
		//���õ�Ԫ�������ֵ���ʽ
		if (exp != null && exp.trim().length() > 0) {
			IByteMap map=new ByteMap();
			map.put(INormalCell.VALUE, exp);  
			inc.setExpMap(map);
		}
		// ��Ԫ���ҳ��ʾ
//		inc.setSplitted(true);
//		inc.setStretchWhenPaged(true);
		if(area != null) inc.setMergedArea(area);
	}
}
