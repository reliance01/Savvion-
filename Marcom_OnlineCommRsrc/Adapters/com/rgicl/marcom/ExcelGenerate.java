package com.rgicl.marcom;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.rgicl.marcom.beans.TicketInitialInfo;
import com.savvion.sbm.bizsolo.util.SBMConf;

public class ExcelGenerate {
	static HSSFCellStyle cellStyleHeader = null;
	static Font fontHeader = null;
	static HSSFWorkbook workbook = null;

	public static String ticketDtlInExcel(String user) {
		String time = null;
		if (user != null && user.trim().length() > 0) {
			workbook = new HSSFWorkbook();
			time =  new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()).toString();
			HSSFSheet sheet = workbook.createSheet("Ticket Detail");
			String[] HeaderList1 = { "TICKET NUMBER", 
					"CATEGORY", "SUB CATEGORY","PRODUCT","APPROVAL DATE", "IRDA DATE", "UIN NUMBER",
					"CALL REGISTER TIME", "STATUS" };
			ArrayList<TicketInitialInfo> info = new Utility()
					.getTicketInitialInfo(user);
			if (!info.isEmpty() && info.size() > 0 && info != null) {
				setHeaderStyle();
				HSSFRow headerRow1 = sheet.createRow(1);
				for (int j = 0; j < HeaderList1.length; j++) {
					Cell cell = headerRow1.createCell(j);
					cell.setCellValue(HeaderList1[j]);
					cell.setCellStyle(cellStyleHeader);
				}
				for (int i = 0; i < info.size(); i++) {
					HSSFRow rowA = sheet.createRow((i + 3));
					rowA.createCell(0).setCellValue(info.get(i).getTicketNo());
					rowA.createCell(1).setCellValue(info.get(i).getCategory());
					rowA.createCell(2).setCellValue(
							info.get(i).getSubCategory());
					rowA.createCell(3).setCellValue(
							info.get(i).getProduct());
					if (new Date(info.get(i).getLegalApprovalTime()).getYear() != 70) {
						rowA.createCell(4).setCellValue(
								BasicUtility.getFormatedDate(info.get(i)
										.getLegalApprovalTime()));
					} else {
						rowA.createCell(4).setCellValue("-");
					}
					if (new Date(info.get(i).getIrdaDate()).getYear() != 70) {
						rowA.createCell(5).setCellValue(
								BasicUtility.getFormatedDate(info.get(i)
										.getIrdaDate()));
					} else {
						rowA.createCell(5).setCellValue("-");
					}
					rowA.createCell(6).setCellValue(info.get(i).getUinNo());
					rowA.createCell(7).setCellValue(
							BasicUtility.getFormatedDate(info.get(i)
									.getProcessTime()));
					rowA.createCell(8).setCellValue(
							String.valueOf(info.get(i).getStatus()));
				}
				for(int i= 0;i<HeaderList1.length;i++)
		        {
		          sheet.setColumnWidth((short)i,(short)5000);
		        }
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(SBMConf.SBM_WEBAPPDIR
							+ "/bpmportal/reports/MarcomReport/TrackHistory_"
							+ time + ".xls"));
					workbook.write(fos);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.flush();
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return time;
	}

	public static void setHeaderStyle() {
		cellStyleHeader = workbook.createCellStyle();
		fontHeader = workbook.createFont();
		cellStyleHeader.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader.setBottomBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader.setTopBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader.setRightBorderColor(IndexedColors.WHITE.getIndex());
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleHeader.setFont(fontHeader);
	}
}
