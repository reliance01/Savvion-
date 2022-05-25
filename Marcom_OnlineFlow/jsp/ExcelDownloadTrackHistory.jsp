<%@ page import="com.savvion.sbm.bizsolo.util.SBMConf" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCellStyle" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFRow" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.poi.hssf.util.HSSFColor" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCell" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFFont" %>
<%@ page import="org.apache.poi.ss.usermodel.IndexedColors" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<%@ page import="com.rgicl.marcom.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>


<%
    String user = request.getParameter("user");
    String fileName = null;
    if(user != null && user.trim().length()>0)
    { 
        Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
	    FileOutputStream fos = null;
	    HSSFWorkbook workbook = new HSSFWorkbook();
		String qry = "{call GETINFO_MRC_ONL_FLOW(?,?,?)}";
		try 
		{
		    String grpMember = BasicUtility.getUserGrpName(user.trim());
			connection = DBUtility.getDBConnection();
			cstmt = connection.prepareCall(qry);
			cstmt.setString(1, user.trim());
			cstmt.setString(2, grpMember.trim());
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			rset = (ResultSet) cstmt.getObject(3);
			ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
			while (rset.next()) 
			{
		        ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(rset.getString(1));
				obj.add(rset.getString(2));
				obj.add(rset.getString(3));
				obj.add(rset.getString(4));
				obj.add(rset.getString(5));
				obj.add(rset.getTimestamp(6).getTime());
				obj.add(rset.getDate(7).getTime());
				obj.add(rset.getString(8));
				obj.add(rset.getLong(9));
				obj.add(rset.getString(10));
				data.add(obj);
			}
			String time =  new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new java.util.Date()).toString();
			HSSFSheet sheet = workbook.createSheet("Ticket Detail");
			String[] HeaderList = { "TICKET NUMBER", 
					"CATEGORY", "SUB CATEGORY","PRODUCT","BUSINESS USER","APPROVAL DATE", "IRDA DATE", "UIN NUMBER",
					"CALL REGISTER TIME", "STATUS" };
			if (data != null && !data.isEmpty() && data.size() > 0)
			{
		        setHeaderStyle(workbook);
				HSSFRow headerRow1 = sheet.createRow(1);
				for (int j = 0; j < HeaderList.length; j++) 
				{
					HSSFCell cell = headerRow1.createCell((short)j);
					cell.setCellValue(HeaderList[j]);
					cell.setCellStyle(cellStyleHeader);
				}
				for (int i = 0; i < data.size(); i++)
				{
			        HSSFRow rowA = sheet.createRow((i + 3));
					rowA.createCell((short)0).setCellValue((String)data.get(i).get(0));
					rowA.createCell((short)1).setCellValue((String)data.get(i).get(1));
					rowA.createCell((short)2).setCellValue((String)data.get(i).get(2));
					rowA.createCell((short)3).setCellValue((String)data.get(i).get(3));
					rowA.createCell((short)4).setCellValue((String)data.get(i).get(4));
					if (new java.util.Date((Long)data.get(i).get(5)).getYear() != 70) 
					{
						rowA.createCell((short)5).setCellValue(BasicUtility.getFormatedDate((Long)data.get(i).get(5)));
					} else 
					{
						rowA.createCell((short)5).setCellValue("-");
					}
					if (new java.util.Date((Long)data.get(i).get(6)).getYear() != 70)
					{
						rowA.createCell((short)6).setCellValue(getFormatedDate((Long)data.get(i).get(6)));
					} else 
					{
						rowA.createCell((short)6).setCellValue("-");
					}
					rowA.createCell((short)7).setCellValue((String)data.get(i).get(7));
					rowA.createCell((short)8).setCellValue(BasicUtility.getFormatedDate((Long)data.get(i).get(8)));
					rowA.createCell((short)9).setCellValue((String)data.get(i).get(9));
				}
				for(int i= 0;i<HeaderList.length;i++)
		        {
		             sheet.setColumnWidth((short)i,(short)5000);
		        }
				fos = new FileOutputStream(new File(SBMConf.SBM_WEBAPPDIR
							+ "/bpmportal/reports/MarcomReport/TrackHistory_"
							+ time + ".xls"));
				workbook.write(fos);
				String filename = SBMConf.SBM_WEB_URL + "/sbm/bpmportal/reports/MarcomReport/TrackHistory_"+time+".xls";
                response.sendRedirect(filename);
			}
		}
		catch (Exception ex) 
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
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
	}	
  
%>


<%!
        
		HSSFCellStyle cellStyleHeader = null;
	    HSSFFont fontHeader = null;
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
