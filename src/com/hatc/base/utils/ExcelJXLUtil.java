package com.hatc.base.utils;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;

import jxl.BooleanFormulaCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.DateFormulaCell;
import jxl.ErrorFormulaCell;
import jxl.LabelCell;
import jxl.NumberFormulaCell;
import jxl.Sheet;
import jxl.StringFormulaCell;
import jxl.Workbook;

public class ExcelJXLUtil {

	/**
	 * ��ȡExcel��Ԫ�����
	 * @param filePath �ļ�·��
	 * @param sheetIndex sheet���
	 * @param rowIndex �к�
	 * @param colIndex �к�
	 * @return ��Ԫ��Cell����
	 */
	public static Cell getCell(String filePath, int sheetIndex, int rowIndex, int colIndex) throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File(filePath));
		Sheet sheet = workbook.getSheet(sheetIndex);
		Cell cell = sheet.getCell(colIndex - 1, rowIndex - 1);
	    return cell;
	}
	/**
	 * ��ȡExcel��Ԫ����� �������ȡ
	 * @param sheet����
	 * @param rowIndex �к�
	 * @param colIndex �к�
	 * @return ��Ԫ��Cell����
	 */
	public static Cell getCell(Sheet sheet, int rowIndex, int colIndex) throws Exception{
		boolean b = true;
		Cell cell = null;
		try {
			cell = sheet.getCell(colIndex - 1, rowIndex - 1);
		} catch (Exception e) {
			b = false;
		}
		if (b) {
			return cell;
		} else {
			return null;
		}
	}
	/**
	 * ��ȡExcel��Ԫ��ĳ����ֵ��
	 * @param is �ļ�������
	 * @param sheetIndex sheet���
	 * @param from_x �к�
	 * @param from_y �к�
	 * @param to_x �к�
	 * @param to_y �к�
	 * @return String[][]��Ԫ��ֵ��
	 */
	public static String[][] getCellValues(InputStream is, int sheetIndex, int from_x, int from_y, int to_x, int to_y) throws Exception{
		String[][] target  = new String[(to_y - from_y) + 1][(to_x - from_x) + 1];
		Workbook workbook = Workbook.getWorkbook(is);
	    Sheet sheet = workbook.getSheet(sheetIndex);
		for(int y = from_y; y < to_y + 1; y++){
			for(int x = from_x; x < to_x + 1; x++){
				target[y - from_y][x - from_x] = getCellString(getCell(sheet, y, x));
			}
		}
		return target;
	}
	
	/**
	 * ��ȡExcelָ������Sheet��ָ���е�����
	 * @param is �ļ�������
	 * @param sheetIndex sheet���
	 * @param row_index �к�
	 * @param colomn_m �к�
	 * @param colomn_n �к�
	 * @return String[][]��Ԫ��ֵ��
	 */
	public static String[] getRowValues(InputStream is, int sheetIndex, int row_index, int colomn_m, int colomn_n) throws Exception{
		Workbook workbook = Workbook.getWorkbook(is);
	    Sheet sheet = workbook.getSheet(sheetIndex);
		return getRowValues(sheet, row_index, colomn_m, colomn_n);
	}
	
	/**
	 * ��ȡExcelָ������Sheet��ָ���е�����
	 * @param Sheet sheet ������
	 * @param row_index �к�
	 * @param colomn_m �к�
	 * @param colomn_n �к�
	 * @return String[][]��Ԫ��ֵ��
	 */
	public static String[] getRowValues(Sheet sheet, int row_index, int colomn_m, int colomn_n) throws Exception{
		String[] target  = new String[(colomn_n - colomn_m) + 1];
		for(int x = colomn_m; x < colomn_n + 1; x++){
			target[x - 1] = getCellString(getCell(sheet, row_index, x));
		}
		return target;
	}
	
	/**
	 * ��ȡExcel��Ԫ���ڵ�ֵ
	 * @param cell����
	 * @return String��Ԫ��ֵ
	 */
	public static String getCellString(Cell cell) throws Exception{
		if(cell == null)
			return "";
		CellType cell_type = cell.getType();
		String cell_content = "";
		if (cell_type == CellType.LABEL) {
			LabelCell lc = (LabelCell) cell;
			cell_content = lc.getString();
		} else if (cell_type == CellType.DATE) {
			DateCell dc = (DateCell) cell;
			cell_content = DateFormat.getDateInstance().format(dc.getDate());
		} else if (cell_type == CellType.DATE_FORMULA) {
			DateFormulaCell dfc = (DateFormulaCell) cell;
			cell_content = dfc.getFormula();
		} else if (cell_type == CellType.NUMBER_FORMULA) {
			NumberFormulaCell dfc = (NumberFormulaCell) cell;
			cell_content = dfc.getFormula();
		} else if (cell_type == CellType.STRING_FORMULA) {
			StringFormulaCell sfc = (StringFormulaCell) cell;
			cell_content = sfc.getFormula();
		} else if (cell_type == CellType.BOOLEAN_FORMULA) {
			BooleanFormulaCell bfc = (BooleanFormulaCell) cell;
			cell_content = bfc.getFormula();
		} else if (cell_type == CellType.FORMULA_ERROR) {
			ErrorFormulaCell efc = (ErrorFormulaCell) cell;
			cell_content = efc.getFormula();
		} else {
			cell_content = cell.getContents();
		}
		return cell_content;
	}
	/**
	 * ��ȡExcel��Ԫ���ڵ�ֵ
	 * @param is �ļ�������
	 * @param sheet Excel������
	 * @param x sheet�е�Ԫ�������
	 * @param y sheet�е�Ԫ��������
	 * @return String��Ԫ��ֵ
	 */
	public static String getCellString(Sheet sheet, int x, int y) throws Exception{
	    Cell cell = sheet.getCell(y - 1, x - 1);
		if(cell == null)
			return "";
		CellType cell_type = cell.getType();
		String cell_content = "";
		if (cell_type == CellType.LABEL) {
			LabelCell lc = (LabelCell) cell;
			cell_content = lc.getString();
		} else if (cell_type == CellType.DATE) {
			DateCell dc = (DateCell) cell;
			cell_content = DateFormat.getDateInstance().format(dc.getDate());
		} else if (cell_type == CellType.DATE_FORMULA) {
			DateFormulaCell dfc = (DateFormulaCell) cell;
			cell_content = dfc.getFormula();
		} else if (cell_type == CellType.NUMBER_FORMULA) {
			NumberFormulaCell dfc = (NumberFormulaCell) cell;
			cell_content = dfc.getFormula();
		} else if (cell_type == CellType.STRING_FORMULA) {
			StringFormulaCell sfc = (StringFormulaCell) cell;
			cell_content = sfc.getFormula();
		} else if (cell_type == CellType.BOOLEAN_FORMULA) {
			BooleanFormulaCell bfc = (BooleanFormulaCell) cell;
			cell_content = bfc.getFormula();
		} else if (cell_type == CellType.FORMULA_ERROR) {
			ErrorFormulaCell efc = (ErrorFormulaCell) cell;
			cell_content = efc.getFormula();
		} else {
			cell_content = cell.getContents();
		}
		return cell_content;
	}
}
