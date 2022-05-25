package com.savvion.rcf;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

//import com.rgic.icm.report.bean.SMReportDataBean;
import com.savvion.rcf.bean.*;

import com.savvion.sbm.bizsolo.util.SBMConf;

public class CalldeskExcelExport{
	
    private static final String COLUMNS = "columns";
    private static final String COLUMN_EXCEL = "ExcelColumn";
    private final String        NAME    = "name";

    private String[] catKey = {"1","2","3","4","5","6","7","8","9","10"};
    
    ArrayList catList = new ArrayList();
    ArrayList regionData = new ArrayList();
    ArrayList branchData = new ArrayList();


    
    public String exportToExcelCD( Map reports )
    {
    	ArrayList allData = new ArrayList();
    	ArrayList rowData = new ArrayList();
    	
    	if ( reports == null )
        {
            throw new RuntimeException( "Cannot Export <null> data to excel." );
        }    	
    	List< String > reportColumns = ( List< String > ) reports.get( COLUMNS );
    	String reportName = ( String ) reports.get( NAME )
        + new SimpleDateFormat( "dd-MM-yyyy HH-mm-ss" ).format( new Date() ) + ".xls";
    	
    	OutputStream out = null;
        HSSFWorkbook wb = null;
        
        try{
        	out = new BufferedOutputStream( new FileOutputStream(
                    SBMConf.SBM_WEBAPPDIR + "/bpmportal/CallDesk/Reports/"
                            + reportName ) );
        	
        	wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet( reportName );
            sheet.setDefaultColumnWidth( ( short ) 16 );

            // ***************** Font Format of Header************************//
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints( ( short ) 10 );
            font.setFontName( "Arial" );
            // font.setItalic(true);
            font.setColor( HSSFColor.DARK_BLUE.index );
            font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
            HSSFCellStyle colStyle = wb.createCellStyle();
            
            colStyle.setFillPattern(HSSFCellStyle.FINE_DOTS );
            colStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex()); 
            
            colStyle.setFont( font );
          

            // *************creating header for excel file******************//
            HSSFRow col = sheet.createRow( ( short ) 0 );
            int colIndex = 0;
            for ( String column : reportColumns )
            {
                createDataGridColumn( col, colStyle, colIndex++, column );
            }

            // *****Font Format of DataGrid*******************//
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints( ( short ) 10 );
            font1.setFontName( "Arial" );
            font1.setColor( HSSFColor.BLACK.index );
            HSSFCellStyle rowStyle1 = wb.createCellStyle();
           // rowStyle1.setFillPattern(HSSFCellStyle.FINE_DOTS );
            //rowStyle1.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
            rowStyle1.setFont( font1 );
            

            // ***********Creating Data for Excel Sheet ***********//
            HSSFRow row = null;
            short rowIndex = 0;
            String data = "";
            
            
            rowData = (ArrayList)reports.get("data");
            //System.out.println("rowData in Export Excel:----"+rowData);
            String esc = "";
            String irID = "";
            String appType = "";
            String callBy = "";
            String callDt = "";
            
            for ( Object obj : rowData )
			{
            	row = sheet.createRow( ( short ) ++rowIndex );
                int cIndex = 0;
            	if ( obj instanceof TATReportDataBean )
                {
            		TATReportDataBean cBean = ( TATReportDataBean ) obj;
            		//System.out.println("111111111-----"+cBean.getTICKETNO());
            		createDataGridColumn( row, rowStyle1, cIndex++, cBean.getTICKETNO());
            		if(cBean.getISSUEREQUESTID() == null){
            			irID = "NA";
            		}else irID = cBean.getISSUEREQUESTID();
            		System.out.println("2222222-----"+irID);
            		createDataGridColumn( row, rowStyle1, cIndex++, irID);
            		if(cBean.getAPPLICATIONTYPE() == null){
            			appType = "NA";
            		}else appType = cBean.getAPPLICATIONTYPE() ;
            		System.out.println("333333333-----"+appType);
            		createDataGridColumn( row, rowStyle1, cIndex++, appType);
            		if(cBean.getCALLCREATEDBY() == null){
            			callBy = "NA";
            		}else callBy = cBean.getCALLCREATEDBY();
            		System.out.println("44444444-----"+callBy);
            		createDataGridColumn( row, rowStyle1, cIndex++, callBy);
            		if(cBean.getCALLLOGDATE() == null){
            			callDt = "NA";
            		}else callDt = cBean.getCALLLOGDATE();
            		System.out.println("55555555-----"+callDt);
            		createDataGridColumn( row, rowStyle1, cIndex++, callDt);
            		
            		if(cBean.getESCALATIONSTATUS() == null){
            			esc = "No Escalation done";
            		}else esc = cBean.getESCALATIONSTATUS() ;
            		System.out.println("66666666-----"+esc);
            		createDataGridColumn( row, rowStyle1, cIndex++, esc);            		           		
                }
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( wb != null )
                {
                    wb.write( out );
                }
            }
            catch ( Exception ex )
            {
            }
            try
            {
                if ( out != null )
                {
                    out.close();
                }
            }
            catch ( Exception ex )
            {
            }
        }
    	return reportName;
    }   
    
    public String exportToExcelCDComplete( Map reports )
    {
    	ArrayList allData = new ArrayList();
    	ArrayList rowData = new ArrayList();
    	
    	if ( reports == null )
        {
            throw new RuntimeException( "Cannot Export <null> data to excel." );
        }    	
    	LinkedHashMap reportColumns = (LinkedHashMap)reports.get( COLUMN_EXCEL );
    	String reportName = ( String ) reports.get( NAME )
        + new SimpleDateFormat( "dd-MM-yyyy HH-mm-ss" ).format( new Date() ) + ".xls";
    	
    	OutputStream out = null;
        HSSFWorkbook wb = null;
        
        try{
        	out = new BufferedOutputStream( new FileOutputStream(
                    SBMConf.SBM_WEBAPPDIR + "/bpmportal/CallDesk/reports/"
                            + reportName ) );
        	
        	wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet( reportName );
            sheet.setDefaultColumnWidth( ( short ) 16 );

            // ***************** Font Format of Header************************//
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints( ( short ) 10 );
            font.setFontName( "Arial" );
            // font.setItalic(true);
            font.setColor( HSSFColor.DARK_BLUE.index );
            font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
            HSSFCellStyle colStyle = wb.createCellStyle();
            
            colStyle.setFillPattern(HSSFCellStyle.FINE_DOTS );
            colStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex()); 
            
            colStyle.setFont( font );
          

            // *************creating header for excel file******************//
            HSSFRow col = sheet.createRow( ( short ) 0 );
            int colIndex = 0;            
            Iterator it = reportColumns.entrySet().iterator();
            while (it.hasNext()) {         
            	Map.Entry pairs = (Map.Entry)it.next();         	        
            	createDataGridColumn( col, colStyle, colIndex++, pairs.getValue().toString() ); 
            }   
          
            // *****Font Format of DataGrid*******************//
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints( ( short ) 10 );
            font1.setFontName( "Arial" );
            font1.setColor( HSSFColor.BLACK.index );
            HSSFCellStyle rowStyle1 = wb.createCellStyle();
           // rowStyle1.setFillPattern(HSSFCellStyle.FINE_DOTS );
            //rowStyle1.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
            rowStyle1.setFont( font1 );
            

            // ***********Creating Data for Excel Sheet ***********//
            HSSFRow row = null;
            short rowIndex = 0;
            String data = "";
            
            
            rowData = (ArrayList)reports.get("data");
            //System.out.println("rowData in Export Excel:----"+rowData);
            String esc = "";
            String irID = "";
            String appType = "";
            String callBy = "";
            String callType = "";
            String callDt = "";
            String appName = "";
            String approverStatus = "";
            String approvedDt = "";
            String appStatus = "";
            String callcloseDt = "";
            String stTime = "";
            String endTime = "";
            String elapseTime  = "";
            String tatTime = "";
            String appSupportRemark = "";
            String usrRemark = "";
            String performerID = "";
            
            for ( Object obj : rowData )
			{
            	row = sheet.createRow( ( short ) ++rowIndex );
                int cIndex = 0;
            	if ( obj instanceof DetailedReportDataBean )
                {
            		DetailedReportDataBean cBean = ( DetailedReportDataBean ) obj;
            		//System.out.println("111111111-----"+cBean.getTICKETNO());
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, cBean.getTICKETNO());
            		
            		if(cBean.getWI_PERFORMER() == null){
            			performerID = "NA";
            		}else performerID = cBean.getWI_PERFORMER();
            		
            		if(cBean.getCALLTYPE() == null){
            			callType = "NA";
            		}else callType = cBean.getCALLTYPE();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, callType);
            		
            		//createDataGridColumn( row, rowStyle1, cIndex++, performerID);
            		
            		if(cBean.getCALLCREATEDBY() == null){
            			callBy = "NA";
            		}else callBy = cBean.getCALLCREATEDBY();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, callBy);
            		
            		if(cBean.getCALLLOGDATE() == null){
            			callDt = "NA";
            		}else callDt = cBean.getCALLLOGDATE();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, callDt);
            		
            		if(cBean.getBRANCH() == null){
            			irID = "NA";
            		}else irID = cBean.getBRANCH();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, irID);
            		
            		if(cBean.getAPPLICATIONTYPE() == null){
            			appType = "NA";
            		}else appType = cBean.getAPPLICATIONTYPE() ;
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, appType);
            		
            		String issTy ="";
        		
            		if(cBean.getISSUEREQUESTID() == null){
            			issTy = "NA";
            		}else issTy = cBean.getISSUEREQUESTID() ;
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, issTy);
            		
            		String issSub="";
            		if(cBean.getISSUEREQUESTSUBID() == null){
            			issSub = "NA";
            		}else issSub = cBean.getISSUEREQUESTSUBID() ;
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, issSub);
            		
            		String reopenId="";
            		if(cBean.getREOPENID() == null){
            			reopenId = "NA";
            		}else reopenId = cBean.getREOPENID() ;
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, reopenId);
            		
            		//if(cBean.getAPPROVERNAME() == null){
            		//	appName = "NA";
            		//}else appName = cBean.getAPPROVERNAME();
            		
            		//createDataGridColumn( row, rowStyle1, cIndex++, appName);
            		
            		if(cBean.getAPPROVERSTATUS()== null){
            			approverStatus = "NA";
            		}else approverStatus = cBean.getAPPROVERSTATUS();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, approverStatus);
            		
            		String appRemark="";
            		if(cBean.getAPPROVERFINALREMARK1() == null){
            			appRemark = "NA";
            		}else appRemark = cBean.getAPPROVERFINALREMARK1();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, appRemark);
            		            		
            		if(cBean.getAPPROVEDDATESTRING() == null){
            			approvedDt = "NA";
            		}else approvedDt = cBean.getAPPROVEDDATESTRING();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, approvedDt);
            		
            		String suppPerf = "";
            		if(cBean.getAPPSUPPORTPERFORMER() == null){
            			suppPerf = "NA";
            		}else suppPerf = cBean.getAPPSUPPORTPERFORMER();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, suppPerf);
            		
            		String suppStatus = "";
            		if(cBean.getAPPSUPPORTSTATUS() == null){
            			suppStatus = "NA";
            		}else suppStatus = cBean.getAPPSUPPORTSTATUS();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, suppStatus);
            		
            		String suppRemark = "";
            		if(cBean.getAPPSUPPORTREMARK() == null){
            			suppRemark = "NA";
            		}else suppRemark = cBean.getAPPSUPPORTREMARK();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, suppRemark);
            		
            		
            		String closeDt ="";
            		
            		if(cBean.getAPPSUPPORTCLOSEDTSTRING() == null){
            			closeDt = "NA";
            		}else closeDt = cBean.getAPPSUPPORTCLOSEDTSTRING();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, closeDt);
            		
            		
            		/*if(cBean.getAPPSUPPORTSTATUS() == null){
            			appStatus = "NA";
            		}else appStatus = cBean.getAPPSUPPORTSTATUS();*/
            		//
            		//createDataGridColumn( row, rowStyle1, cIndex++, appStatus);
            		
            		/*if(cBean.getEND_TIME() == null){
            			endTime = "NA";
            		}else endTime = cBean.getEND_TIME();
            		
            		//createDataGridColumn( row, rowStyle1, cIndex++, endTime);
            		
            		if(cBean.getSTART_TIME() == null){
            			stTime = "NA";
            		}else stTime = cBean.getSTART_TIME();*/
            		
            		//createDataGridColumn( row, rowStyle1, cIndex++, stTime);
            				
            		
            		/*if(cBean.getELAPSED_TIME() == null){
            			elapseTime = "NA";
            		}else elapseTime = cBean.getELAPSED_TIME() ;*/
            		
            		//createDataGridColumn( row, rowStyle1, cIndex++, elapseTime);   
            		
            		/*if(cBean.getTAT_TIME() == null){
            			tatTime = "NA";
            		}else tatTime = cBean.getTAT_TIME();
            		*/
            		//createDataGridColumn( row, rowStyle1, cIndex++, tatTime);
            		
            	/*	if(cBean.getUSERREMARK() == null){
            			usrRemark = "NA";
            		}else usrRemark = cBean.getUSERREMARK();
            	*/	
            		//createDataGridColumn( row, rowStyle1, cIndex++, usrRemark);
            		
            		/*if(cBean.getAPPSUPPORTREMARK() == null){
            			appSupportRemark = "NA";
            		}else appSupportRemark = cBean.getAPPSUPPORTREMARK();
            		
            		createDataGridColumn( row, rowStyle1, cIndex++, appSupportRemark);*/
            		
            		
                }
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( wb != null )
                {
                    wb.write( out );
                }
            }
            catch ( Exception ex )
            {
            }
            try
            {
                if ( out != null )
                {
                    out.close();
                }
            }
            catch ( Exception ex )
            {
            }
        }
    	return reportName;
    }   
    
    //Method for Active Call Status
    
    public String exportToExcelCDActive( Map reports )
    {
    	System.out.println("******************Start of exportToExcelCDActive() method****************");
    	ArrayList allData = new ArrayList();
    	ArrayList rowData = new ArrayList();
    	
    	if ( reports == null )
        {
            throw new RuntimeException( "Cannot Export <null> data to excel." );
        }    	
    	List< String > reportColumns = ( List< String > ) reports.get( COLUMN_EXCEL );
    	String reportName = ( String ) reports.get( NAME )
        + new SimpleDateFormat( "dd-MM-yyyy HH-mm-ss" ).format( new Date() ) + ".xls";
    	
    	OutputStream out = null;
        HSSFWorkbook wb = null;
        
        try{
        	out = new BufferedOutputStream( new FileOutputStream(
                    SBMConf.SBM_WEBAPPDIR + "/bpmportal/CallDesk/reports/"
                            + reportName ) );
        	
        	wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet( reportName );
            sheet.setDefaultColumnWidth( ( short ) 16 );

            // ***************** Font Format of Header************************//
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints( ( short ) 10 );
            font.setFontName( "Arial" );
            // font.setItalic(true);
            font.setColor( HSSFColor.DARK_BLUE.index );
            font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
            HSSFCellStyle colStyle = wb.createCellStyle();
            
            colStyle.setFillPattern(HSSFCellStyle.FINE_DOTS );
            colStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex()); 
            
            colStyle.setFont( font );
          

            // *************creating header for excel file******************//
            HSSFRow col = sheet.createRow( ( short ) 0 );
            int colIndex = 0;
            for ( String column : reportColumns )
            {
                createDataGridColumn( col, colStyle, colIndex++, column );
            }

            // *****Font Format of DataGrid*******************//
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints( ( short ) 10 );
            font1.setFontName( "Arial" );
            font1.setColor( HSSFColor.BLACK.index );
            HSSFCellStyle rowStyle1 = wb.createCellStyle();
           // rowStyle1.setFillPattern(HSSFCellStyle.FINE_DOTS );
            //rowStyle1.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
            rowStyle1.setFont( font1 );
            

            // ***********Creating Data for Excel Sheet ***********//
            HSSFRow row = null;
            short rowIndex = 0;
            String data = "";
            
            
            rowData = (ArrayList)reports.get("data");
            //System.out.println("rowData in Export Excel:----"+rowData);
            String ticket = "";
            String callcreatedby = "";
            String logdate = "";
            String calltype = "";
            String IssueReqtype = "";
            String appType = "";
            String branch = "";
            String reopenid = "";
            String approverID = "";
            String approverName = "";
            String approvrDsgn = "";            
            String appStatus = "";
            String appDate = "";
            String appSupportStatus = "";
            String approverRemark = "";
            String appsupportRemark = "";
            String userRemark = "";
            String escStatus ="";
            
            /*String esc = "";
            String irID = "";
            
            String callBy = "";
            String callDt = "";
            String appName = "";
            String approverStatus = "";
            String approvedDt = "";
            
            String callcloseDt = "";
            String stTime = "";
            String elapseTime  = "";
            String tatTime = "";
            String appSupportRemark = "";
            String usrRemark = "";*/
            
            for ( Object obj : rowData )
			{
            	row = sheet.createRow( ( short ) ++rowIndex );
                int cIndex = 0;
            	if ( obj instanceof DetailedReportDataBean )
                {
            		DetailedReportDataBean cBean = ( DetailedReportDataBean ) obj;
            		            		
            		ticket = cBean.getTICKETNO();
					if(ticket == null) ticket = "X";					
            		createDataGridColumn( row, rowStyle1, cIndex++, ticket );
            		
            		callcreatedby = cBean.getCALLCREATEDBY();
					if(callcreatedby == null) callcreatedby = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, callcreatedby);
					
					logdate = cBean.getCALLLOGDATE();
					if(logdate == null) logdate = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, logdate);
					
					calltype = cBean.getCALLTYPE();
					if(calltype == null) calltype = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, calltype );
					
					IssueReqtype = cBean.getISSUEREQUESTID();
					if(IssueReqtype == null) IssueReqtype = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, IssueReqtype);
					
					appType = cBean.getAPPLICATIONTYPE();
					if(appType == null) appType = "X";	
					createDataGridColumn( row, rowStyle1, cIndex++, appType);	
					
					branch = cBean.getBRANCH();
					if(branch == null) branch = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, branch);
					
					reopenid = cBean.getREOPENID();
					if(reopenid == null) reopenid = "X";	
					createDataGridColumn( row, rowStyle1, cIndex++, reopenid);
					
					approverID = cBean.getAPPROVERUSERID();
					if(approverID == null) approverID = "X";	
					createDataGridColumn( row, rowStyle1, cIndex++, approverID);
					
					approverName = cBean.getAPPROVERNAME();
					if(approverName == null) approverName = "X";	
					createDataGridColumn( row, rowStyle1, cIndex++, approverName);
					
					approvrDsgn = cBean.getAPPROVERDESIGNATION();
					if(approvrDsgn == null) approvrDsgn = "X";	
					createDataGridColumn( row, rowStyle1, cIndex++, approvrDsgn);
					
					appStatus = cBean.getAPPROVERSTATUS();
					if(appStatus == null) appStatus = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, appStatus);
					
					appDate = cBean.getAPPROVEDDATESTRING();
					if(appDate == null) appDate = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, appDate);
					
					appSupportStatus = cBean.getAPPSUPPORTSTATUS();
					if(appSupportStatus == null) appSupportStatus = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, appSupportStatus);
					
					userRemark = cBean.getUSERREMARK();
					if(userRemark == null) userRemark = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, userRemark);
					
					approverRemark = cBean.getAPPROVERREMARK();
					if(approverRemark == null) approverRemark = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, approverRemark);
					
					appsupportRemark = cBean.getAPPSUPPORTREMARK();
					if(appsupportRemark == null) appsupportRemark = "X";
					createDataGridColumn( row, rowStyle1, cIndex++, appsupportRemark);
					
					escStatus = cBean.getESCALATIONSTATUS();
					if(escStatus == null) escStatus = "Not Escalated";
					createDataGridColumn( row, rowStyle1, cIndex++, escStatus);            		
                }
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( wb != null )
                {
                    wb.write( out );
                }
            }
            catch ( Exception ex )
            {
            }
            try
            {
                if ( out != null )
                {
                    out.close();
                }
            }
            catch ( Exception ex )
            {
            }
        }
        System.out.println("******************End of exportToExcelCDActive() method****************");
    	return reportName;
    }   

    public void createDataGridColumn( HSSFRow row, HSSFCellStyle style,
            int columnIndex, String data )
    {
        HSSFCell cell = row.createCell( ( short ) columnIndex );
        cell.setCellValue( data );
        cell.setCellStyle( style );
    }
}
