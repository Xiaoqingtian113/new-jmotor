package com.zhucq.mobile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelModel {
	public static final int EXCEL_STYLE_ESCAPING = 0;
	public static final int UNIX_STYLE_ESCAPING = 1;
	public static final String DEFAULT_SEPARATOR = ",";
	protected Workbook wb;

	public ExcelModel(File file) {
		try {
			FileInputStream ins = new FileInputStream(file);
			wb = WorkbookFactory.create(ins);
			ins.close();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 将Excel一个sheet的数据，转化为MapList结构的对象。
	 * 
	 * @param sheetName
	 * @return
	 */
	public List<HashMap<String, String>> getSheetAsMapList(String sheetName) {
		List<HashMap<String, String>> sheetData = new LinkedList<HashMap<String, String>>();
		Sheet sh = wb.getSheet(sheetName);
		if (sh == null)
			System.out.println("没有这个sheet：" + sheetName);
		Row titleRw = sh.getRow(0);
		for (int i = 1; i <= sh.getLastRowNum(); i++) {
			Row rw = sh.getRow(i);
			if (isRowEmpty(rw))
				continue;
			HashMap<String, String> mp = new HashMap<String, String>();
			for (int j = 0; j < rw.getLastCellNum(); j++) {
				Cell titleCl = titleRw.getCell(j);
				Cell cl = rw.getCell(j);
				mp.put(titleCl.getStringCellValue(), getCellValueAsString(cl));
			}
			sheetData.add(mp);
		}
		return sheetData;
	}

	public List<HashMap<String, String>> getSheetAsMapList(int index) {
		List<HashMap<String, String>> sheetData = new LinkedList<HashMap<String, String>>();
		Sheet sh = wb.getSheetAt(index);
		Row titleRw = sh.getRow(0);
		for (int i = 1; i <= sh.getLastRowNum(); i++) {
			Row rw = sh.getRow(i);
			if (isRowEmpty(rw))
				continue;
			HashMap<String, String> mp = new HashMap<String, String>();
			for (int j = 0; j < rw.getLastCellNum(); j++) {
				Cell titleCl = titleRw.getCell(j);
				Cell cl = rw.getCell(j);
				mp.put(titleCl.getStringCellValue(), getCellValueAsString(cl));
			}
			sheetData.add(mp);
		}
		return sheetData;
	}

	/**
	 * 得到Excel的一行的数据，转化为Map对象。
	 * 
	 * @param sheetName
	 * @param rowNum
	 * @return
	 */
	public HashMap<String, String> getRowAsMap(String sheetName, int rowNum) {
		List<HashMap<String, String>> sheetData = getSheetAsMapList(sheetName);
		return sheetData.get(rowNum);
	}

	public HashMap<String, String> getRowAsMap(int index, int rowNum) {
		List<HashMap<String, String>> sheetData = getSheetAsMapList(index);
		return sheetData.get(rowNum);
	}

	/**
	 * 将Excel的一个Sheet转化为CSV文件
	 * 
	 * @param csv
	 * @param sheetName
	 */
	public void ExcelToCSV(String csv, String sheetName) {
		ExcelToCSV(csv, sheetName, DEFAULT_SEPARATOR, EXCEL_STYLE_ESCAPING);
	}

	/**
	 * 将Excel的一个Sheet转化为CSV文件
	 * 
	 * @param csv
	 * @param sheetName
	 * @param separator
	 *            采用的分隔符
	 * @param formattingConvention
	 *            格式化转化类型，可以转化为Excel格式和Unix格式
	 */
	public void ExcelToCSV(String csv, String sheetName, String separator, int formattingConvention) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(csv)), "gb2312"));
			Sheet sh = wb.getSheet(sheetName);
			int maxRow = sh.getLastRowNum() + 1;
			int maxCol = 0;
			for (int i = 1; i < maxRow; i++) {
				Row rw = sh.getRow(i);
				int colSize = rw.getLastCellNum();
				maxCol = maxCol > colSize ? maxCol : colSize;
			}
			for (int i = 0; i < maxRow; i++) {
				Row rw = sh.getRow(i);
				for (int j = 0; j < maxCol; j++) {
					Cell cell = rw.getCell(j);
					String cv = getCellValueAsString1(cell);
					if (j < rw.getLastCellNum())
						out.append(escapeEmbeddedCharacters(cv, ",", formattingConvention));
					if (j < maxRow)
						out.append(separator);
				}
				out.newLine();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理Excel单元格中特殊字符，保存成csv文件时能正确显示
	 * 
	 * @param field
	 * @param separator
	 * @param formattingConvention
	 * @return
	 */
	private String escapeEmbeddedCharacters(String field, String separator, int formattingConvention) {
		StringBuffer buffer;
		if (formattingConvention == EXCEL_STYLE_ESCAPING) {
			if (field.contains("\"")) {
				buffer = new StringBuffer(field.replaceAll("\"", "\\\"\\\""));
				buffer.insert(0, "\"");
				buffer.append("\"");
			} else {
				buffer = new StringBuffer(field);
				if ((buffer.indexOf(separator)) > -1 || (buffer.indexOf("\n")) > -1) {
					buffer.insert(0, "\"");
					buffer.append("\"");
				}
			}
			return (buffer.toString().trim());
		} else {
			if (field.contains(separator)) {
				field = field.replaceAll(separator, ("\\\\" + separator));
			}
			if (field.contains("\n")) {
				field = field.replaceAll("\n", "\\\\\n");
			}
			return (field);
		}
	}

	/**
	 * 向Excel写入String数据，并且保存到path中
	 * 
	 * @param path
	 *            要保存的文件路径
	 * @param sheetName
	 *            Sheet名
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param data
	 *            待写入的String数据
	 */
	public void writeExcel(String path, String sheetName, int row, int col, String data) {
		Sheet sh = wb.getSheet(sheetName);
		try {
			sh.getRow(row).getCell(col).setCellValue(data);
		} catch (Exception e) {
			sh.createRow(row).createCell(col).setCellValue(data);
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("数据写入成功");
	}

	/**
	 * 传递一个bean的类型，将map转化为一个bean的对象。
	 * 
	 * @param mp
	 * @param t
	 * @return
	 */
	public <T> T mapToBean(HashMap<String, String> mp, Class<T> t) {
		T bean = null;
		try {
			bean = t.newInstance();
			BeanUtils.populate(bean, mp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 传递一个bean的类型，将MapList转化为一个bean的List。
	 * 
	 * @param mps
	 * @param t
	 * @return
	 */
	public <T> List<T> mapListToBeans(List<HashMap<String, String>> mps, Class<T> t) {
		List<T> beanList = new LinkedList<T>();
		for (HashMap<String, String> mp : mps) {
			T bean = mapToBean(mp, t);
			beanList.add(bean);
		}
		return beanList;
	}

	/**
	 * 判断一行是否为空
	 * 
	 * @param row
	 * @return
	 */
	public boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
				return false;
		}
		return true;
	}

	/**
	 * 将Excel文件单元格内容以String方式读出来。
	 * 
	 * @param cl
	 * @return
	 */
	public String getCellValueAsString(Cell cl) {
		if (cl == null)
			return "";
		switch (cl.getCellTypeEnum()) {
		case BLANK:
			return "";
		case NUMERIC:
			return new BigDecimal(Double.toString(cl.getNumericCellValue())).stripTrailingZeros().toPlainString();
		case STRING:
			return cl.getStringCellValue();
		case FORMULA:
			CellType type = cl.getCachedFormulaResultTypeEnum();
			String tmp = "";
			switch (type) {
			case NUMERIC:
				tmp = new BigDecimal(Double.toString(cl.getNumericCellValue())).stripTrailingZeros().toPlainString();
				break;
			case STRING:
				tmp = cl.getStringCellValue();
				break;
			default:
				break;
			}
			return tmp;
		default:
			return "";
		}
	}

	public String getCellValueAsString1(Cell cell) {
		String cv = "";
		DataFormatter formatter = new DataFormatter(true);
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		if (cell != null) {
			if (cell.getCellTypeEnum() != CellType.FORMULA)
				cv = formatter.formatCellValue(cell);
			else
				cv = formatter.formatCellValue(cell, evaluator);
		}
		return cv;
	}
}
