package com.yogapay.boss.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import common.Logger;


/**
 * Excel工具类
 *
 * @author zts
 * @date 2013-12-6
 */
public class ExcelUtil {

	private final Logger log = Logger.getLogger(ExcelUtil.class);
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFCellStyle commonCellStyle;
	private HSSFCellStyle headCellStyle;
	private HSSFCellStyle dateCellStyle;

	/**
	 * 创建Excel文档
	 */
	public void createWorkBook() {
		this.wb = new HSSFWorkbook();
		this.createCellStyle();
	}

	/**
	 * 创建工作簿
	 *
	 * @param sheetName 工作簿名称
	 */
	public void createSheet(String sheetName) {
		this.sheet = this.wb.createSheet(sheetName);
	}

	public HSSFCell getCell(int rowIndex, int colIndex) {
		return this.sheet.getRow(rowIndex).getCell(colIndex);
	}

	public String getString(int rowIndex, int colIndex) {
		return this.sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue();
	}

	/**
	 * 创建单元格样式
	 */
	private void createCellStyle() {
		this.commonCellStyle = this.wb.createCellStyle();
		this.commonCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		this.commonCellStyle.setWrapText(true);
		this.commonCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.commonCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.commonCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.commonCellStyle.setBorderTop(CellStyle.BORDER_THIN);

		this.dateCellStyle = this.wb.createCellStyle();
		HSSFDataFormat format = this.wb.createDataFormat();
		this.dateCellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
		this.dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		this.dateCellStyle.setWrapText(true);
		this.dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);

		this.headCellStyle = this.wb.createCellStyle();
		this.headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		this.headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		HSSFFont font = this.wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.headCellStyle.setFont(font);
		this.headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headCellStyle.setBorderTop(CellStyle.BORDER_THIN);
	}

	/**
	 * 插入空行
	 *
	 * @param columnNum 空行的列数
	 */
	public void createEmptyRow(int columnNum) {
		//HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum()+1);
		HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum());
		for (int i = 0; i < columnNum; i++) {
			row.createCell(i);
		}
	}

	/**
	 * 使用表头样式 插入表头数据
	 *
	 * @param datas 数据的二元数组
	 */
	public void putHead(Object[][] datas) {
		for (int i = 0; i < datas.length; i++) {
			Object[] data = datas[i];
			//HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
			HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum());
			for (int j = 0; j < data.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(this.headCellStyle);
				cell.setCellValue(data[j].toString());
			}
		}
	}

	/**
	 * 使用数据样式 插入数据
	 *
	 * @param datas 数据的二元数组
	 */
	public void putData(Object[][] datas) {
		for (int i = 0; i < datas.length; i++) {
			Object[] data = datas[i];
			HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
			for (int j = 0; j < data.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(this.commonCellStyle);
				if (data[j] == null) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue("");
				} else if (data[j].getClass() == Integer.class) {
					cell.setCellValue((Integer) data[j]);
				} // else if (data[j].getClass() == Double.class) {
				// cell.setCellValue((Double) data[j]);
				// }
				// else if (data[j].getClass() == Float.class) {
				// cell.setCellValue((Long) data[j]);
				// }
				// else if (data[j].getClass() == Long.class) {
				// cell.setCellValue((Long) data[j]);
				// }
				else if (data[j].getClass() == Date.class) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(DateUtil.dateToString((Date) data[j], "yyyy-MM-dd HH:ss:mm"));
					cell.setCellStyle(this.dateCellStyle);
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(data[j].toString());
				}
			}
		}
	}

	/**
	 * 合并某一列上相邻内容相同的单元格
	 *
	 * @param columnIndex 要合并行的列的索引ID
	 */
	public void coalitionRow(int columnIndex) {
		this.coalitionCell(columnIndex, this.sheet.getFirstRowNum(), this.sheet.getLastRowNum());
	}

	/**
	 * 合并某一列上相邻内容相同的单元格(判断数据是否相等)
	 *
	 * @param columnIndex 要合并行的列的索引ID
	 * @param comIndex判断是否合并单元格的列的索引ID
	 */
	public void coalitionRow(int columnIndex, int comIndex) {
		this.coalitionCell(columnIndex, comIndex, this.sheet.getFirstRowNum(), this.sheet.getLastRowNum());
	}

	/**
	 * 合并某列上相邻、且值相同的单元格
	 *
	 * @param colIndex 列序号
	 * @param startRowIndex 起始行号
	 * @param endRowIndex 结束行号
	 */
	public void coalitionCell(int colIndex, int startRowIndex, int endRowIndex) {
		int m = startRowIndex;// 记录要合并的起始行
		for (int i = startRowIndex; i <= endRowIndex + 1; i++) {
			// System.out.print("[" + last_value + "," + now_value + "]");
			//System.out.println(this.compareColumn(colIndex, i, i - 1));
			if (this.compareColumn(colIndex, i, i - 1)) {
				// 相邻单元格值相同
				// log.debug(";值相同,不处理");
			} else {
				// 相邻单元格值不同
				// log.debug(" 值不同,需要处理");
				System.out.println("i-m=" + (i - m));
				if (i - m > 1) {
					// log.debug("--合并 从" + m + "到" + (i - 1));
					System.out.println("--合并 从" + m + "到" + (i - 1));
					this.sheet.addMergedRegion(new CellRangeAddress(m, i - 1, colIndex, colIndex));
				}
				m = i;
				// log.debug("改变m的值为:" + m);
			}
		}
	}

	/**
	 * 合并某列上相邻、且值相同的单元格
	 *
	 * @param colIndex 列序号
	 * @param startRowIndex 起始行号
	 * @param endRowIndex 结束行号
	 */
	public void coalitionCell(int colIndex, int comIndex, int startRowIndex, int endRowIndex) {
		int m = startRowIndex;// 记录要合并的起始行
		for (int i = startRowIndex; i <= endRowIndex + 1; i++) {
			if (this.compareColumn(colIndex, comIndex, i, i - 1)) {
				// 相邻单元格值相同
				// log.debug(";值相同,不处理");
			} else {
				// 相邻单元格值不同
				if (i - m > 1) {
					this.sheet.addMergedRegion(new CellRangeAddress(m, i - 1, colIndex, colIndex));
				}
				m = i;
			}
		}
	}

	/**
	 * 合并某行上值为空的单元格
	 *
	 * @param rowIndex 行号
	 */
	public void coalitionCell(int rowIndex) {
		HSSFRow row = this.sheet.getRow(rowIndex);
		if (row == null) {
			this.log.debug("要合并单元格的行不存在");
		}
		this.log.debug("*************合并空的单元格*************");
		short sCol = row.getFirstCellNum();// 开始列
		short eCol = row.getLastCellNum();// 结束列
		for (int i = sCol; i < eCol;) {
			int j = this.findNextEmptyCellInRow(row, i);
			if (j > i) {
				this.sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, i, j));
			}
			i = j + 1;
		}
	}

	/**
	 * 在某行从某列开始找最后一个空单元格位置
	 *
	 * @param row
	 * @param startCol
	 * @return
	 */
	public int findNextEmptyCellInRow(HSSFRow row, int startCol) {
		this.log.debug("判断第" + startCol + "列");
		HSSFCell cell = row.getCell(startCol + 1);
		if (cell == null) {
			return startCol;
		}
		String val = cell.getStringCellValue();
		if ((val != null) && "-".equals(val)) {
			// 如果为空，继续查找下一个
			return this.findNextEmptyCellInRow(row, startCol + 1);
		} else {
			// 不为空,返回
			return startCol;
		}
	}

	/**
	 * 比较某个列及之前列上两行的值是否相同
	 *
	 * @param colIndex 列序号
	 * @param rowIndex1 行序号1
	 * @param rowIndex2 行序号2
	 * @return true:都相同 false：有不同
	 */
	public boolean compareColumn(int colIndex, int comIndex, int rowIndex1, int rowIndex2) {
		HSSFRow row1 = this.sheet.getRow(rowIndex1);
		HSSFRow row2 = this.sheet.getRow(rowIndex2);

		if ((row1 == null) && (row2 == null)) {
			return true;
		}
		if ((row1 == null) || (row2 == null)) {
			return false;
		}
		HSSFCell cell3 = row1.getCell(comIndex);
		HSSFCell cell4 = row2.getCell(comIndex);
		String value3 = null;
		String value4 = null;
		if ((cell3 != null) && (cell4 != null)) {
			value3 = cell3.getStringCellValue();
			value4 = cell4.getStringCellValue();
		}

		for (int i = colIndex; i >= 0; i--) {
			HSSFCell cell1 = row1.getCell(i);
			HSSFCell cell2 = row2.getCell(i);

			String value1 = cell1.getStringCellValue();
			String value2 = cell2.getStringCellValue();
			// log.debug("----[" + value1 + "," + value2 + "]");
			if ((value2 != null) && !"".equals(value2) && value2.equals(value1) && value4.equals(value3)) {
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 比较某个列及之前列上两行的值是否相同
	 *
	 * @param colIndex 列序号
	 * @param rowIndex1 行序号1
	 * @param rowIndex2 行序号2
	 * @return true:都相同 false：有不同
	 */
	public boolean compareColumn(int colIndex, int rowIndex1, int rowIndex2) {
		HSSFRow row1 = this.sheet.getRow(rowIndex1);
		HSSFRow row2 = this.sheet.getRow(rowIndex2);

		if ((row1 == null) && (row2 == null)) {
			return true;
		}
		if ((row1 == null) || (row2 == null)) {
			return false;
		}
		for (int i = colIndex; i >= 0; i--) {
			HSSFCell cell1 = row1.getCell(i);
			HSSFCell cell2 = row2.getCell(i);
			String value1 = "";
			String value2 = "";
			if ((cell1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)) {
				value1 = (((int) cell1.getNumericCellValue()) + "").trim();
			} else {
				value1 = cell1.getStringCellValue().trim();
			}

			if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				value2 = (((int) cell2.getNumericCellValue()) + "").trim();
			} else {
				value2 = cell2.getStringCellValue().trim();
			}

			if ((value2 != null) && value2.equals(value1)) {
			} else {
				if (i == 0 || i == colIndex) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 删除某列 该列右边的数据左移
	 *
	 * @param colIndex 列序号
	 * @param rowIndex1 起始行，空则从fisrtRow开始
	 * @param rowIndex2 结束行，空则到lastrow结束
	 */
	public void delColumn(int colIndex, Integer rowIndex1, Integer rowIndex2) {
		if (rowIndex1 == null) {
			rowIndex1 = this.sheet.getFirstRowNum();
		}
		if (rowIndex2 == null) {
			rowIndex2 = this.sheet.getLastRowNum();
		}
		if (rowIndex1 > rowIndex2) {
			return;
		}
		// 遍历行
		this.log.debug("************删除第" + rowIndex1 + "行到第" + rowIndex2 + "行的第" + colIndex + "列**************");
		for (int i = rowIndex1; i <= rowIndex2; i++) {
			this.log.debug("处理第" + i + "行:");
			HSSFRow row = this.sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int cellCount = row.getLastCellNum();// 总列数
			this.log.debug("删除第" + colIndex + "列");
			System.out.println(row.getCell(colIndex - 2));
			System.out.println(row.getCell(colIndex - 1));
			System.out.println(row.getCell(colIndex));
			row.removeCell(row.getCell(colIndex));
			// 遍历删除列后的列
			for (int j = colIndex; j < cellCount; j++) {
				this.log.debug("把第" + (j + 1) + "列移到第" + j + "列");
				HSSFCell cell = row.createCell(j);
				HSSFCell next_cell = row.getCell(j + 1);
				if ((cell != null) && (next_cell != null)) {
					this.log.debug(next_cell.getRichStringCellValue().toString());
					cell.setCellValue(next_cell.getRichStringCellValue());
					cell.setCellStyle(next_cell.getCellStyle());
				}
			}
		}
	}

	/**
	 * 创建Excel
	 *
	 * @param sheetName
	 * @throws IOException
	 */
	public void createExcel(String sheetName) throws IOException {
		this.createWorkBook();
		this.createSheet(sheetName);
	}

	/**
	 * 获得Excel文档
	 *
	 * @param os 输出流
	 * @throws IOException
	 */
	public void getFile(OutputStream os) throws IOException {
		HSSFRow row = this.sheet.getRow(this.sheet.getLastRowNum());
		short firstNum = row.getFirstCellNum();
		short lastNum = row.getLastCellNum();
		for (short i = firstNum; i <= lastNum; i++) {
			this.sheet.setColumnWidth(i, 100 * 50);
		}
		this.wb.write(os);
	}

	public static void main(String[] args) throws IOException {
		List<Object[]> data = new ArrayList<Object[]>();
		Object[] a = {"2011-01-01", "湖南广电"};
		Object[] b = {"2011-01-01", "湖南广电"};
		data.add(a);
		data.add(b);
		Object[][] datas = {{new Date(123456789), 2, 3.25, 1234, 1234}, {new Date(123456788), 2, 3.25, 1234, 1234},
			{new Date(123456789), 2, 3.25, 1234, 1234}, {new Date(123456789), 4, 3.25, 1234, 1234},
			{new Date(123456788), 4, 3, 1234, 1234}, {"总计", "-", "-", "-", "-"}};
		Object[][] head = {{"统计范围", "-", "-", "统计字段", "-"}};
		Object[][] head2 = {{"起止日期", "服务商", "一级分类", "播放时间", "播放次数"}};
		Object[][] dataHead = {{"日期", "服务商", "一级分类", "播放时间", "播放次数"}};
		ExcelUtil eu = new ExcelUtil();
		eu.createWorkBook();

		eu.createSheet("统计");
		//eu.createExcel("统计");
		//eu.putHead(head);
		// eu.putData(head2);

		//eu.createEmptyRow(6);
		//eu.createEmptyRow(6);
		eu.putHead(dataHead);
		eu.putData(datas);
		eu.coalitionCell(1, 0, 7);
		//eu.coalitionCell(1, 0, 15);
		//eu.coalitionCell(2, 0, 15);
		//eu.delColumn(3, 5, 15);
		//eu.coalitionCell(1);
		//eu.coalitionCell(11);
		FileOutputStream fos = new FileOutputStream("D:\\test.xls");
		eu.getFile(fos);
		fos.close();
	}
}
