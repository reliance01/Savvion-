<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.savvion.sbm.bizsolo.util.SBMConf" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
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
   Long ChangedTATDate = 1493577000000L;	
    
%>  

<%
        String startDate = request.getParameter("startDate").toString();
	String endDate = request.getParameter("endDate").toString();
	String auditor = request.getParameter("auditor").toString();
                        getHolidays();
        		ArrayList<ArrayList<Object>> reportTATData = new ArrayList<ArrayList<Object>>();
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yy");
			java.util.Date date = sdf1.parse(startDate);
			java.util.Date date2 = sdf1.parse(endDate);
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
			java.sql.Date sqlEndDate =  new java.sql.Date(date2.getTime()); 
                         System.out.println("Run startDate" + sqlStartDate + " End date" + sqlEndDate + " Auditor "+  auditor );
			String query = "{call AUDIT_REPORT_DATA(?,?,?,?,?,?,?)}";
			connection = DBUtility.getDBConnection();
			cstmt = connection.prepareCall(query);
			cstmt.setDate(1,sqlStartDate);
			cstmt.setDate(2,sqlEndDate);	
			cstmt.setString(3, "null");

			cstmt.setString(4, auditor);
			cstmt.setString(5, "null");
                        cstmt.setString(6, "TAT");
			cstmt.registerOutParameter(7, OracleTypes.CURSOR);
                        cstmt.executeQuery();
                        rset = (ResultSet) cstmt.getObject(7);			
			Date closure_date = new Date();
                        if (rset != null) {

				while (rset.next()) {
					ArrayList<Object> callDetailList = new ArrayList<Object>();
					callDetailList.add(rset.getString("AUDITOR"));
					callDetailList.add(rset.getString("BRANCH"));
                                        
					callDetailList.add(rset.getString("AUDIT_TYPE"));
					
					callDetailList.add(rset.getString("AUDITEE"));
					
					callDetailList.add(rset.getString("STATUS"));
					
					
					callDetailList.add(df.format(rset.getDate("AUDIT_STARTDATE")));
					
					callDetailList.add(df.format(rset.getDate("AUDIT_ENDDATE")));
					
					callDetailList.add(df.format(rset.getDate("SUBMIT_TIME")));
					
					
						String d1 = df1.format(rset.getDate("AUDIT_ENDDATE"));
						String d2 = df1.format(rset.getDate("SUBMIT_TIME"));
						java.util.Date dd1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d1);
						java.util.Date dd2 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d2);
						String TAT = Integer.toString(getWorkingDaysBetweenTwoDates(dd1,
								dd2));	
						callDetailList.add(TAT);	
						
						if(rset.getDate("CLOSURE_DATE") != null ){
							callDetailList.add(sdf1.format(rset.getDate("CLOSURE_DATE")));
						} else {
							callDetailList.add("-");
						} 


						if(rset.getString("CLOSURE_TAT") == null){
							callDetailList.add("-");
						} else {
							callDetailList.add(rset.getString("CLOSURE_TAT"));
						}
						reportTATData.add(callDetailList);
				}
			}

        
                
		out.print(exportToExcelCDComplete(reportTATData));
%>

<%!

DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
ArrayList<java.util.Date> holidayList = new ArrayList<java.util.Date>();

private boolean vldt(String val)
{
    boolean ok = false;
    if(val != null && val.trim().length() > 0)
    {
       ok = true;
    }
    return ok;
}

private String exportToExcelCDComplete(ArrayList<ArrayList<Object>> callData)
{
       String reportName = null;
       FileOutputStream fos = null;
       HSSFWorkbook workbook = new HSSFWorkbook();

       String time =  new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new java.util.Date()).toString();
      try
       {
        HSSFSheet sheet = workbook.createSheet("Audit TAT Details");
   
       String[] HeaderList = { "AUDITOR", 
					"BRANCH", "AUDIT_TYPE","AUDITEE","STATUS","AUDIT_STARTDATE", "AUDIT_ENDDATE", "FIRST_SUBMISSION", "TAT","CLOSURE_DATE","CLOSURE_TAT" };


	

                  if (callData != null && !callData.isEmpty() && callData.size() > 0)
			{
		        setHeaderStyle(workbook);
				HSSFRow headerRow1 = sheet.createRow(1);
				for (int j = 0; j < HeaderList.length; j++) 
				{
					HSSFCell cell = headerRow1.createCell((short)j);
					cell.setCellValue(HeaderList[j]);
					cell.setCellStyle(cellStyleHeader);
				}
                               
				for (int i = 0; i < callData.size(); i++)
				{
			       		
					 HSSFRow rowA = sheet.createRow((i + 2));
					rowA.createCell((short)0).setCellValue((String)callData.get(i).get(0));
					rowA.createCell((short)1).setCellValue((String)callData.get(i).get(1));
					rowA.createCell((short)2).setCellValue((String)callData.get(i).get(2));
					rowA.createCell((short)3).setCellValue((String)callData.get(i).get(3));
					rowA.createCell((short)4).setCellValue((String)callData.get(i).get(4));
					rowA.createCell((short)5).setCellValue((String)callData.get(i).get(5));
					rowA.createCell((short)6).setCellValue((String)callData.get(i).get(6));
					rowA.createCell((short)7).setCellValue((String)callData.get(i).get(7));
					rowA.createCell((short)8).setCellValue((String)callData.get(i).get(8));
					rowA.createCell((short)9).setCellValue((String)callData.get(i).get(9));
					rowA.createCell((short)10).setCellValue((String)callData.get(i).get(10));
                                 }
				for(int i= 0;i<HeaderList.length;i++)
		                {
		             sheet.setColumnWidth((short)i,(short)5000);
		                }
 			     
                       }
                   

		 reportName = "AuditReportTAT_"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
                 fos = new FileOutputStream(SBMConf.SBM_WEBAPPDIR+ "/bpmportal/reports/AuditReport/" + reportName + ".xls");
				workbook.write(fos);
                                
                    
                    
	           


                 }catch (Exception ex) 
		{
			System.out.println("Error gererating report : "
					+ ex.getMessage());
		}
            
		finally 
	       {
		    if (fos != null) 
		    {
			    try
			    {
				    fos.flush();
					fos.close();
				}
				catch (Exception e) 
				{
				    e.printStackTrace();
				}
			}
		}
		
		System.out.println("Report name "+reportName);
        return reportName;
}




%>

<%!
        
      	public int getWorkingDaysBetweenTwoDates(java.util.Date startDate,
			java.util.Date endDate) {
                
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 1);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 1);

		int workDays = -1;

		if (df.format(startDate).equals(df.format(endDate))) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		try {
			do {
				      if(startCal.getTimeInMillis() >= ChangedTATDate)
					{
					if (!checkHoliday(startCal.getTime())
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					workDays++;
						}
					}else
					{
					if (!checkHoliday(startCal.getTime())
						&& !checkSecondSaturday(startCal.getTime())
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					workDays++;
						}

					}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			} while (startCal.getTime().before(endCal.getTime())
					|| startCal.getTime().equals(endCal.getTime()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (workDays == -1) {
			workDays = 0;
		}
		return workDays;
	}

	public boolean checkSecondSaturday(java.util.Date date) {
		boolean isSecondSat = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY) {
			int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
			if (weekOfMonth == 2 || weekOfMonth == 4) {
				isSecondSat = true;
			}
		}
		return isSecondSat;
	}

	public boolean checkHoliday(java.util.Date date) {
		boolean isHoliday = false;
		for (int i = 0; i < holidayList.size(); i++) {
                   
                    
			if (df.format(date).equals(df.format(holidayList.get(i)))) {
				isHoliday = true;
				break;
			}
		}
                
		return isHoliday;
	}

	public void getHolidays() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Calendar cal = Calendar.getInstance();
		DBUtility obj = new DBUtility();
		try {
			connection = obj.getDBConnection();
			pstmt = connection
					.prepareCall("SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				java.util.Date holiday = new java.util.Date(rset.getDate(1)
						.getTime());
				Calendar holidayCal = Calendar.getInstance();
				holidayCal.setTime(holiday);
				cal.set(Calendar.YEAR, holidayCal.get(Calendar.YEAR));
				cal.set(Calendar.MONTH, holidayCal.get(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH,
						holidayCal.get(Calendar.DAY_OF_MONTH));
				holidayList.add(cal.getTime());
			}
                 System.out.println(" holiday list " + holidayList.toString());
		} catch (Exception e) {
			System.out.println("Error in getting holiday list : "
					+ e.getMessage());
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}




        public void setHeaderStyle(HSSFWorkbook workbook) 
		{
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
		    fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		    cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyleHeader.setFont(fontHeader);
	   }
	   
	  public String getFormatedDate(long time) 
	  {
		  SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
		  return fmt.format(new java.util.Date(time));
	  }
 
%>
