<%@ page import="java.io.*" %>
<%@ page import="com.savvion.sbm.bizsolo.util.SBMConf" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.*" %>
<%@ page import="oracle.jdbc.OracleTypes"%>

<%@ page import="com.rel.db.DBUtility" %>
<%@ page import="com.rel.techaudit.utilities.TechnicalAuditUtility" %>

<%@ page import="org.apache.poi.hssf.usermodel.HSSFCell" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCellStyle" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFRow" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFFont" %>
<%@ page import="org.apache.poi.hssf.util.HSSFColor" %>
<%@ page import="org.apache.poi.ss.usermodel.Font" %>
<%@ page import="org.apache.poi.ss.usermodel.IndexedColors" %>
<%@ page import="org.apache.poi.ss.util.CellRangeAddress" %>
<%@ page import="org.apache.poi.hssf.util.Region" %>


<%!

   Connection connection = null;
   CallableStatement cstmt = null;
   ResultSet rset = null;
   String reportName = null;
   HSSFWorkbook wb = null;
   HSSFSheet sheet = null;
   HSSFCellStyle cellStyleHeader = null;
   HSSFCellStyle cellStyleHeader1 = null;
   HSSFCellStyle newSbPrmStyle = null;
   HSSFFont fontHeader = null;
   HSSFRow dataRow = null;
   HSSFCellStyle style = null;

    public short fontColorMapping(String val) {
   		if (val.equalsIgnoreCase("No Audit Observation")) {
   			val = "0";
   		}
   		short value = 0;
   		int argValue = Integer.parseInt(val);
   		switch (argValue) {
   		case 1:
   			value = HSSFColor.BLACK.index;
   			break;
   		case 2:
   			value = HSSFColor.BRIGHT_GREEN.index;
   			break;
   		case 3:
   			value = HSSFColor.GOLD.index;
   			break;
   		case 4:
   			value = HSSFColor.RED.index;
   			break;
   		case 5:
   			value = HSSFColor.BLACK.index;
   			break;
   		default:
   			value = HSSFColor.BLACK.index;
   			break;
   		}
   		return value;
   	}
   
   	public String idToNameMapping(String val) {
   		if (val.equalsIgnoreCase("No Audit Observation")) {
   			val = "0";
   		}
   		int argValue = Integer.parseInt(val);
   		String value = null;
   		switch (argValue) {
   		case 1:
   			value = "Recommendation Risk";
   			break;
   		case 2:
   			value = "Low Risk";
   			break;
   		case 3:
   			value = "Medium Risk";
   			break;
   		case 4:
   			value = "High Risk";
   			break;
   		case 5:
   			value = "No Audit Observation";
   			break;
   		default:
   			value = "No Audit Observation";
   			break;
   		}
   		return value;
	}
	

           private void setNewAddedSubParamStyle() 
           {
	        newSbPrmStyle = wb.createCellStyle();
		fontHeader = wb.createFont();
		newSbPrmStyle.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
		newSbPrmStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		newSbPrmStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		newSbPrmStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		newSbPrmStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		newSbPrmStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		newSbPrmStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());
		newSbPrmStyle.setTopBorderColor(IndexedColors.WHITE.getIndex());
		newSbPrmStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		newSbPrmStyle.setRightBorderColor(IndexedColors.WHITE.getIndex());
		newSbPrmStyle.setWrapText(true);
		newSbPrmStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
		newSbPrmStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		newSbPrmStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		newSbPrmStyle.setFont(fontHeader);
	  }

	public void setHeaderStyle() {
		cellStyleHeader = wb.createCellStyle();
		fontHeader = wb.createFont();
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
		cellStyleHeader.setWrapText(true);
		cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleHeader.setFont(fontHeader);
	}
	
	public void setHeaderStyle1() {
		cellStyleHeader1 = wb.createCellStyle();
		fontHeader = wb.createFont();
		cellStyleHeader1.setFillForegroundColor(HSSFColor.RED.index);
		cellStyleHeader1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleHeader1.setBottomBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader1.setTopBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader1.setLeftBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader1.setRightBorderColor(IndexedColors.WHITE.getIndex());
		cellStyleHeader1.setWrapText(true);
		cellStyleHeader1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyleHeader1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		cellStyleHeader1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleHeader1.setFont(fontHeader);
	}

	public void setRiskRateStyle(String value, HSSFCell cell) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = wb.createFont();
		font.setColor(fontColorMapping(value));
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(idToNameMapping(value));
        }  
    
%>   

<%
      FileOutputStream fileOut = null;
      try
      {
       int auditFlag = 0;
       String startDate = request.getParameter("startDate").toString();
	String endDate = request.getParameter("endDate").toString();
	String auditor = request.getParameter("auditor").toString();
	String auditType = request.getParameter("auditType").toString();
	String status = request.getParameter("status").toString();

         System.out.println(" Data is " + startDate + endDate + auditor + auditType + status );
	Long piId = 5022953L;
        ArrayList<String> newAddedSubParamList = new TechnicalAuditUtility().getAllNewAddedSubParameter(piId);

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yy");
			java.util.Date date = sdf1.parse(startDate);
			java.util.Date date2 = sdf1.parse(endDate);
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
			java.sql.Date sqlEndDate =  new java.sql.Date(date2.getTime()); 
                         System.out.println("Run startDate" + sqlStartDate + " End date" + sqlEndDate );
			String query = "{call AUDIT_REPORT_DATA(?,?,?,?,?,?,?)}";
			connection = DBUtility.getDBConnection();
			cstmt = connection.prepareCall(query);
			cstmt.setDate(1,sqlStartDate);
			cstmt.setDate(2,sqlEndDate);	
			cstmt.setString(3,auditType);
			cstmt.setString(4, auditor);
			cstmt.setString(5, status );
                        cstmt.setString(6, "CONSOLIDATE");
			cstmt.registerOutParameter(7, OracleTypes.CURSOR);
                        cstmt.executeQuery();
       rset = (ResultSet) cstmt.getObject(7);
		 System.out.println("Run1");
       ResultSetMetaData rsmd = rset.getMetaData();
       System.out.println("After rsmd");
       String[] firstHeaderArr = {"AUDITOR","AUDITEE","SCHEDULE ON","AUDIT START DATE","AUDIT END DATE","LOCATION TYPE","BRANCH","REGION"};
       String[] secondHeaderArr = {"AUDITOR","AUDITEE","LOCATION TYPE","BRANCH","REGION","SCHEDULE ON","AUDIT START DATE","AUDIT END DATE","PARAMETER","SUB PARAMETER","DEPARTMENT","CLASS","RISK RATE","DOCUMENT NO / ANNEXURE REF NO","AUDIT OBSERVATION","RISK IMPACT","RECOMMENDATION","REASON FOR DEVIATION","CORRECTIVE PREVENTIVE ACTION","TIMELINE RESOLUTION","AUDITEE STATUS","AUDITOR STATUS","AUDITOR REMARK"};
       String[] ODClaimHeaderArr = {"AUDITOR","AUDITEE","LOCATION TYPE","BRANCH","REGION","SCHEDULE ON","AUDIT START DATE","AUDIT END DATE","PARAMETER","SUB PARAMETER","CLAIM CATEGORY","CLAIM STATUS","BASIS OF SETTLEMENT","DEPARTMENT","CLASS","RISK RATE","DOCUMENT NO / ANNEXURE REF NO","AUDIT OBSERVATION","RISK IMPACT","RECOMMENDATION","REASON FOR DEVIATION","CORRECTIVE PREVENTIVE ACTION","TIMELINE RESOLUTION","AUDITEE STATUS","AUDITOR STATUS","AUDITOR REMARK"};
       String[] LegalClaimHeaderArr = {"AUDITOR","AUDITEE","LOCATION TYPE","BRANCH","REGION","SCHEDULE ON","AUDIT START DATE","AUDIT END DATE","PARAMETER","SUB PARAMETER","COURT TYPE","NATURE OF LOSS","CLAIM STATUS","DEPARTMENT","CLASS","RISK RATE","DOCUMENT NO / ANNEXURE REF NO","AUDIT OBSERVATION","RISK IMPACT","RECOMMENDATION","REASON FOR DEVIATION","CORRECTIVE PREVENTIVE ACTION","TIMELINE RESOLUTION","AUDITEE STATUS","AUDITOR STATUS","AUDITOR REMARK"};
       for (int x = 1; x <= rsmd.getColumnCount(); x++) 
       {
		System.out.println("inside loop" + rsmd.getColumnName(x));
           if ("settlement_basis".equalsIgnoreCase(rsmd.getColumnName(x))) 
           {
               auditFlag = 1;
		System.out.println("Flag1");
               break;
           }
           else if("court_type".equalsIgnoreCase(rsmd.getColumnName(x)))
           {
               auditFlag = 2;
		System.out.println("Flag2");
               break;
           }
       }
       System.out.println("before count");
       int rowCount = 0;
       fileOut = null;
       wb = new HSSFWorkbook();
       sheet = wb.createSheet("AuditReport");
       style = wb.createCellStyle();
       style.setWrapText(true); //Set wordwrap
       style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
       setHeaderStyle();
       setNewAddedSubParamStyle();
       boolean isNewSbParamRow = false;
       System.out.println("before rset");
       while (rset.next()) 
       {  
		System.out.println("inside while - "+ rowCount);
          if(newAddedSubParamList.contains(rset.getString(10)))
			{
	       isNewSbParamRow = true;
			}
          if(rowCount == 0)
        { 
		
               System.out.println("inside row count");

		HSSFRow headerRow2 = sheet.createRow(5);
		HSSFCell sectionCell = headerRow2.createCell((short)0);
		sectionCell.setCellValue("Auditor Section");  
		sectionCell.setCellStyle(cellStyleHeader);
		if(auditFlag == 1 || auditFlag == 2)
		{
		    sheet.addMergedRegion(new Region(5, (short)0, 5, (short)19));
		    System.out.println("In side 1st time");
		}
		else
		{
		    sheet.addMergedRegion(new Region(5, (short)0, 5, (short)16));
		System.out.println("In side 1sadasdasst time");
		}
		if(auditFlag == 1 || auditFlag == 2)
		{
		    HSSFCell sectionCell2 = headerRow2.createCell((short)20);
		    		sectionCell2.setCellValue("Auditee Section");  
		    		setHeaderStyle1();
		     sectionCell2.setCellStyle(cellStyleHeader1);
		    sheet.addMergedRegion(new Region(5, (short)20, 5, (short)23));
			System.out.println("In side 2nd time");
		}
		else
		{
		    HSSFCell sectionCell2 = headerRow2.createCell((short)17);
		    		sectionCell2.setCellValue("Auditee Section");  
		    		setHeaderStyle1();
		sectionCell2.setCellStyle(cellStyleHeader1);
		    sheet.addMergedRegion(new Region(5, (short)17, 5, (short)20));
		}
		
		if(auditFlag == 1 || auditFlag == 2)
		{
		   HSSFCell sectionCell3 = headerRow2.createCell((short)24);
		   		sectionCell3.setCellValue("Auditor Section");  
		sectionCell3.setCellStyle(cellStyleHeader);
                    sheet.addMergedRegion(new Region(5, (short)24, 5, (short)25));
		System.out.println("In side 3rd time");		
		}
		else
		{
		
		    HSSFCell sectionCell3 = headerRow2.createCell((short)21);
		    		sectionCell3.setCellValue("Auditor Section");  
		sectionCell3.setCellStyle(cellStyleHeader);
                    sheet.addMergedRegion(new Region(5, (short)21, 5, (short)22));		
		}
		
		HSSFRow headerRow1 = sheet.createRow(6);
		if(auditFlag == 1)
		{
                    System.out.println("In side 4th time");
		    for(int i=0;i<ODClaimHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = headerRow1.createCell((short)i);
		    		   cell.setCellValue(ODClaimHeaderArr[i]);  
		    		   cell.setCellStyle(cellStyleHeader);
		    		}
		    		HSSFRow dataRow = sheet.createRow(7);
		    		for(int i=0;i<ODClaimHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = dataRow.createCell((short)i);
		    		   if(i == 15)
		    		   {
		    		     setRiskRateStyle(rset.getString((i+1)),cell);
		    		     //cell.setCellValue(rset.getString((i+9)));
		    		   }
		    		   else
		    		   {
		    		     cell.setCellValue(rset.getString((i+1)));  
		    		     if(isNewSbParamRow)
		    		     {
		    			 cell.setCellStyle(newSbPrmStyle);
		    		     }
		    		     else
		    		     {
		    			 cell.setCellStyle(style);
		    		     }
		    		   }
		         }
		}
		else if(auditFlag == 2)
		{
		    for(int i=0;i<LegalClaimHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = headerRow1.createCell((short)i);
		    		   cell.setCellValue(LegalClaimHeaderArr[i]);  
		    		   cell.setCellStyle(cellStyleHeader);
		    		}
		    		HSSFRow dataRow = sheet.createRow(7);
		    		for(int i=0;i<LegalClaimHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = dataRow.createCell((short)i);
		    		   if(i == 15)
		    		   {
		    		     setRiskRateStyle(rset.getString((i+1)),cell);
		    		     //cell.setCellValue(rset.getString((i+9)));
		    		   }
		    		   else
		    		   {
		    		     cell.setCellValue(rset.getString((i+1)));  
		    		     if(isNewSbParamRow)
		    		     {
		    			 cell.setCellStyle(newSbPrmStyle);
		    		     }
		    		     else
		    		     {
		    			 cell.setCellStyle(style);
		    		     }
		    		   }
		         }
		}
		else
		{
		       for(int i=0;i<secondHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = headerRow1.createCell((short)i);
		    		   cell.setCellValue(secondHeaderArr[i]);  
		    		   cell.setCellStyle(cellStyleHeader);
		    		}
		    		HSSFRow dataRow = sheet.createRow(7);
		    		for(int i=0;i<secondHeaderArr.length;i++)
		    		{
		    		   HSSFCell cell = dataRow.createCell((short)i);
		    		   if(i == 12)
		    		   {
		    		     setRiskRateStyle(rset.getString((i+1)),cell);
		    		     //cell.setCellValue(rset.getString((i+9)));
		    		   }
		    		   else
		    		   {
		    		     cell.setCellValue(rset.getString((i+1)));  
		    		     if(isNewSbParamRow)
		    		     {
		    			 cell.setCellStyle(newSbPrmStyle);
		    		     }
		    		     else
		    		     {
		    			 cell.setCellStyle(style);
		    		     }
		    		   }
		           }
		}
		
             }
             else
             {
             
                HSSFRow datarow = sheet.createRow((rowCount+7));
                
                if(auditFlag == 1)
                {
                     System.out.println("In side 5th time");
                       for(int i=0;i<ODClaimHeaderArr.length;i++)
		       		  {
		       		   HSSFCell datacell = datarow.createCell((short)i);
		       		   if(i == 15)
		       		   {
		       		     setRiskRateStyle(rset.getString((i+1)),datacell);
		       		     //datacell.setCellValue(rset.getString((i+9)));
		       		   }
		       		   else
		       		   {
		       		     datacell.setCellValue(rset.getString((i+1)));  
		       		     if(isNewSbParamRow)
		       		     {
		       		         datacell.setCellStyle(newSbPrmStyle);
		       		     }
		       		     else
		       		     {
		       		         datacell.setCellStyle(style);
		       		     }
		       		   }
		       }
                 
                }
                else if(auditFlag == 2)
                {
                     for(int i=0;i<LegalClaimHeaderArr.length;i++)
		     		  {
		     		   HSSFCell datacell = datarow.createCell((short)i);
		     		   if(i == 15)
		     		   {
		     		     setRiskRateStyle(rset.getString((i+1)),datacell);
		     		     //datacell.setCellValue(rset.getString((i+9)));
		     		   }
		     		   else
		     		   {
		     		     datacell.setCellValue(rset.getString((i+1)));  
		     		     if(isNewSbParamRow)
		     		     {
		     		         datacell.setCellStyle(newSbPrmStyle);
		     		     }
		     		     else
		     		     {
		     		         datacell.setCellStyle(style);
		     		     }
		     		   }
		        }
                }
                else
                {
                  for(int i=0;i<secondHeaderArr.length;i++)
		  {
		   HSSFCell datacell = datarow.createCell((short)i);
		   if(i == 12)
		   {
		     setRiskRateStyle(rset.getString((i+1)),datacell);
		     //datacell.setCellValue(rset.getString((i+9)));
		   }
		   else
		   {
		     datacell.setCellValue(rset.getString((i+1)));  
		     if(isNewSbParamRow)
		     {
		         datacell.setCellStyle(newSbPrmStyle);
		     }
		     else
		     {
		         datacell.setCellStyle(style);
		     }
		   }
		}
	     }
         }
             isNewSbParamRow = false;
             rowCount++;
         }
        
        for(int i= 0;i<rsmd.getColumnCount();i++)
        {
          //sheet.autoSizeColumn(i,false);
          sheet.setColumnWidth((short)i,(short)10000);
        }  
        
        reportName = "AuditReport_"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
	fileOut = new FileOutputStream(SBMConf.SBM_WEBAPPDIR+ "/bpmportal/reports/AuditReport/" + reportName + ".xls");
	wb.write(fileOut);
        fileOut.flush();
 	fileOut.close();
        out.print(reportName);
        } 
	catch(Exception e)
	{
           out.println(e.getMessage());
	}
	finally
	{
           DBUtility.releaseResources(connection,cstmt,rset);
	}
      

%>