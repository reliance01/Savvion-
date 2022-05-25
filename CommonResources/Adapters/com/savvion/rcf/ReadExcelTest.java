package com.savvion.rcf;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ReadExcelTest {

	public void checkABA_Number()
	{
		ArrayList userList = new ArrayList();
		ArrayList hmNameList = new ArrayList();
		ArrayList emailList = new ArrayList();
		ArrayList desgnList = new ArrayList();
		
		ArrayList record = new ArrayList();
		ArrayList recordOfRecord = new ArrayList();
		
		try
		{
			FileInputStream fileInputStream = new FileInputStream("D:/Rajat/LLLL_Updated.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			short i1 = 0;
			/*HSSFRow sheetRow = sheet.getRow(0);
			
			HSSFCell cell = sheetRow.getCell(i1++);
			System.out.println("data....."+cell.getStringCellValue());
			
			cell = sheetRow.getCell(i1);
			System.out.println("data....."+cell.getStringCellValue());*/
			
			//int noOfRow = sheet.getLastRowNum();
			
			//sheet.rowIterator()
			
			HSSFCell cell = null;
			for(Iterator it=sheet.rowIterator();it.hasNext();){
				
				HSSFRow sheetRow = (HSSFRow)it.next(); 
				
				for(i1=0; i1<sheetRow.getLastCellNum();i1++){
					cell = sheetRow.getCell(i1);
					if(cell.getCellType() == 0){
						//System.out.println("data:....."+ i1+"... "+cell.getNumericCellValue());
						double dataNum = cell.getNumericCellValue();
						record.add(dataNum);
					}else{
						//System.out.println("data:....."+ i1+"... "+cell.getStringCellValue());
						String dataStr = cell.getStringCellValue();
						record.add(dataStr);
					}
					
				}
				recordOfRecord.add(record);
				record = new ArrayList();
			}
			System.out.println("record:......."+recordOfRecord);
			
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		System.err.println( e );
		}
		
	}

	public static void main(String[] args)
	{
		new ReadExcelTest().checkABA_Number();
	}
}
