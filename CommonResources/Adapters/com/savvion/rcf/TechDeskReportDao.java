package com.savvion.rcf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.savvion.rcf.bean.*;


public class TechDeskReportDao 
{
	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
	public final String KEY_DATA = "data";
	public final String KEY_REPORT_NAME = "name";
	public final String KEY_COLUMN = "columns";
	public final String KEY_EXCEL_COLUMN = "ExcelColumn";
		
	private static String COLUMN_TICKET_NO = "Ticket No #";
	private static String COLUMN_ISSUEREQUEST_TYPE = "Issue Request Type";
	private static String COLUMN_ESCALATIONSTATUS = "Escalation Status";	
	private static String COLUMN_APPLICATIONTYPE = "Application Type";

	private static String COLUMN_CALLCREATEDBY = "Call Registered by";
	private static String COLUMN_CALLTYPE = "Call Type";
	private static String COLUMN_CALLLOGDATE ="Call Date";
	private static String COLUMN_WORKSTEPNAME = "Suspended Item";
	private static String COLUMN_RESUME = "Resume Activity";
	private static String COLUMN_PID = "PID";
	
	private static String COLUMN_BRANCH = "Branch Name";
	private static String COLUMN_REOPENID = "Reopen ID";
	
	private static String COLUMN_CATEGORY="Category";
	private static String COLUMN_SUBCATEGORY="Sub-Category";
	private static String COLUMN_SUPPORTPERFORMER="Resolved By / Assigned To";
	private static String COLUMN_APPSUPPORTCLOSEDTSTRING="Resoved Date";
    
	private static String COLUMN_APPROVERNAME = "Approver Name";
	private static String COLUMN_APPROVERSTATUS	= "Approver Status";
	private static String COLUMN_APPROVEDDATESTRING = "Approved Date";
	private static String COLUMN_APPSUPPORTSTATUS	= "AppSupport Status";
	//private static String COLUMN_APPSUPPORTCLOSEDTSTRING = "Call Closed Date";
	private static String COLUMN_PI_START_TIME = "Start Time";
	private static String COLUMN_WI_END_TIME = "Closed Date";
	private static String COLUMN_ELAPSED_TIME = "Total Elapsed Time";
	private static String COLUMN_TAT_TIME = "Total TAT";
	private static String COLUMN_USERREMARK = "User Remark";
	private static String COLUMN_APPSUPPORTREMARK = "AppSupport Remark";
	private static String COLUMN_APPROVERREMARK = "Approver Remark";
	private static String COLUMN_STATUS = "PI Status"; 
	private static String COLUMN_PERFORMER = "Current Performer"; 
	private static String COLUMN_APPROVERID = "Approver ID";
	private static String COLUMN_APPROVERDSGN = "Approver Designation";
	
	public HashMap getCallStatusReport
	(String sbmUser, String startDate, String endDate, String ticketNo, String _status) throws Exception{		
		
		HashMap hm = new HashMap();
		ArrayList completeData = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DataSource ds = null;
		String reportName = "TechDesk_";
		
		try{
			StringBuffer sql = new StringBuffer();				
			
			if(_status.equalsIgnoreCase("open"))
			{			
				reportName = reportName + "OpenCallsReport";				
				sql.append("SELECT TD.TICKETNO, TD.CALLTYPE, TD.CALLCREATEDBY, TD.CALLLOGDATE, TD.BRANCH,");
			    sql.append("TD.APPLICATIONTYPE, TD.ISSUEREQUESTID, TD.ISSUEREQSUBTYPEID, TD.REOPENID, TD.APPROVALFINALSTATUS,");
				sql.append("TD.APPROVERFINALREMARK1, TD.FINALAPPROVALDATE, WI.PERFORMER, TD.APPSUPPORTFINALSTATUS, ");   			
				sql.append("TD.APPSUPPORTREMARK, TD.APPSUPPORTCLOSEDTSTRING FROM TECHDESK TD, BIZLOGIC_PROCESSINSTANCE PI, BIZLOGIC_WORKITEM WI");
				sql.append(" WHERE TD.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID AND TD.PROCESS_INSTANCE_ID = WI.PROCESS_INSTANCE_ID " +
					"AND TD.PROCESS_TEMPLATE_ID = PI.PROCESS_TEMPLATE_ID AND TD.PROCESS_TEMPLATE_ID = WI.PROCESS_TEMPLATE_ID " +
					"AND WI.WORKSTEP_NAME='AppSupport' AND (WI.STATUS=27 OR WI.STATUS=28)");
				
				if(sbmUser!=null && !sbmUser.equalsIgnoreCase("ALL")){
					sql.append(" AND WI.PERFORMER='" + sbmUser + "'");
				}
				
				if (startDate != null && endDate != null) 
	            {                              	
	                	SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
	                	Date d1 = format.parse(startDate);
	                	Date d2 = format.parse(endDate);
					    sql.append( " AND (WI.STARTTIME >=" + d1.getTime() + " AND " +
	                		"WI.STARTTIME <=" + d2.getTime() + ")");	                
	            }
				sql.append(" GROUP BY TD.TICKETNO, TD.CALLTYPE, TD.CALLCREATEDBY, TD.CALLLOGDATE, TD.BRANCH, TD.APPLICATIONTYPE, TD.ISSUEREQUESTID, TD.ISSUEREQSUBTYPEID, TD.REOPENID, TD.APPROVALFINALSTATUS, TD.APPROVERFINALREMARK1, TD.FINALAPPROVALDATE, WI.PERFORMER, TD.APPSUPPORTFINALSTATUS, TD.APPSUPPORTREMARK, TD.APPSUPPORTCLOSEDTSTRING, TD.PROCESS_INSTANCE_ID ORDER BY TD.PROCESS_INSTANCE_ID");				
				
			}
			else if(_status.equalsIgnoreCase("closed"))
			{
				reportName = reportName + "ClosedCallsReport";
				sql.append("SELECT TD.TICKETNO, TD.CALLTYPE, TD.CALLCREATEDBY, TD.CALLLOGDATE, TD.BRANCH,");
			    sql.append("TD.APPLICATIONTYPE, TD.ISSUEREQUESTID, TD.ISSUEREQSUBTYPEID, TD.REOPENID, TD.APPROVALFINALSTATUS,");
				sql.append("TD.APPROVERFINALREMARK1, TD.FINALAPPROVALDATE, WI.PERFORMER, TD.APPSUPPORTFINALSTATUS, ");   			
				sql.append("TD.APPSUPPORTREMARK, TD.APPSUPPORTCLOSEDTSTRING FROM TECHDESK TD, PROCESSINSTANCE PI, WORKITEM WI");
				sql.append(" WHERE TD.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID AND TD.PROCESS_INSTANCE_ID = WI.PROCESS_INSTANCE_ID " +
					"AND TD.PROCESS_TEMPLATE_ID = PI.PROCESS_TEMPLATE_ID AND TD.PROCESS_TEMPLATE_ID = WI.PROCESS_TEMPLATE_ID " +
					"AND WI.WORKSTEP_NAME='AppSupport' AND WI.STATUS='I_COMPLETED'");
				
				if(sbmUser!=null && !sbmUser.equalsIgnoreCase("ALL")){
					sql.append(" AND WI.PERFORMER='" + sbmUser + "'");
				}
				
				if (startDate != null && endDate != null) 
	            {                     	
	               	sql.append( " AND (WI.END_TIME >= to_date('" + startDate + "','MON dd, yyyy hh:mi AM') AND " +
	                    		"WI.END_TIME <= to_date('" + endDate + "','MON dd, yyyy hh:mi AM'))");	             
	            }
				sql.append(" GROUP BY TD.TICKETNO, TD.CALLTYPE, TD.CALLCREATEDBY, TD.CALLLOGDATE, TD.BRANCH, TD.APPLICATIONTYPE, TD.ISSUEREQUESTID, TD.ISSUEREQSUBTYPEID, TD.REOPENID, TD.APPROVALFINALSTATUS, TD.APPROVERFINALREMARK1, TD.FINALAPPROVALDATE, WI.PERFORMER, TD.APPSUPPORTFINALSTATUS, TD.APPSUPPORTREMARK, TD.APPSUPPORTCLOSEDTSTRING, TD.PROCESS_INSTANCE_ID ORDER BY TD.PROCESS_INSTANCE_ID");
				System.out.println(sql.toString());
			}    

			
			else if((ticketNo!=null || ticketNo!="") && !ticketNo.equalsIgnoreCase("ALL")){
				reportName = reportName + ticketNo;
				sql.append("SELECT TD.TICKETNO, TD.CALLTYPE, TD.CALLCREATEDBY, TD.CALLLOGDATE, TD.BRANCH,");
			    sql.append("TD.APPLICATIONTYPE, TD.ISSUEREQUESTID, TD.ISSUEREQSUBTYPEID, TD.REOPENID, TD.APPROVALFINALSTATUS,");
				sql.append("TD.APPROVERFINALREMARK1, TD.FINALAPPROVALDATE, WI.PERFORMER, TD.APPSUPPORTFINALSTATUS, ");   			
				sql.append("TD.APPSUPPORTREMARK, TD.APPSUPPORTCLOSEDTSTRING FROM TECHDESK TD, PROCESSINSTANCE PI, WORKITEM WI");
				sql.append(" WHERE TD.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID AND TD.PROCESS_INSTANCE_ID = WI.PROCESS_INSTANCE_ID " +
					"AND TD.PROCESS_TEMPLATE_ID = PI.PROCESS_TEMPLATE_ID AND TD.PROCESS_TEMPLATE_ID = WI.PROCESS_TEMPLATE_ID " +
					"AND WI.WORKSTEP_NAME='AppSupport'");
				sql.append(" AND TD.TICKETNO='" + ticketNo + "'");
			}
			else
			{				
			}			
			
			ds = ( DataSource ) new InitialContext().lookup(TREE_DATASOURCE );	
			conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( sql.toString() );
            
            while ( rs.next() )
            {
            	DetailedReportDataBean bean1 = new DetailedReportDataBean();
            	
            	bean1.setTICKETNO(rs.getString("TICKETNO"));
            	bean1.setCALLTYPE(rs.getString("CALLTYPE"));
            	bean1.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
            	bean1.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
            	bean1.setBRANCH(rs.getString("BRANCH"));
            	bean1.setAPPLICATIONTYPE(rs.getString("APPLICATIONTYPE"));
            	bean1.setISSUEREQUESTID(rs.getString("ISSUEREQUESTID"));
            	bean1.setISSUEREQUESTSUBID(rs.getString("ISSUEREQSUBTYPEID"));
            	bean1.setREOPENID(rs.getString("REOPENID"));
            	bean1.setAPPROVALFINALSTATUS(rs.getString("APPROVALFINALSTATUS"));
            	bean1.setAPPROVERFINALREMARK1(rs.getString("APPROVERFINALREMARK1"));
            	bean1.setFINALAPPROVALDATE(rs.getString("FINALAPPROVALDATE"));
            	bean1.setAPPSUPPORTPERFORMER(rs.getString("PERFORMER"));
            	bean1.setAPPSUPPORTFINALSTATUS(rs.getString("APPSUPPORTFINALSTATUS"));
            	bean1.setAPPSUPPORTREMARK(rs.getString("APPSUPPORTREMARK"));
            	bean1.setAPPSUPPORTCLOSEDTSTRING(rs.getString("APPSUPPORTCLOSEDTSTRING"));
            	            	
            	completeData.add(bean1);
            }
            
            ArrayList columnName = new ArrayList();
            
            columnName.add( COLUMN_TICKET_NO );
            columnName.add( COLUMN_CALLTYPE );
            columnName.add(	COLUMN_CALLCREATEDBY);
            columnName.add( COLUMN_CALLLOGDATE );
            columnName.add(	COLUMN_BRANCH);            
            columnName.add( COLUMN_APPLICATIONTYPE );
            columnName.add( COLUMN_CATEGORY );
            columnName.add( COLUMN_SUBCATEGORY );
            columnName.add( COLUMN_REOPENID );
            columnName.add( COLUMN_APPROVERSTATUS );
            columnName.add( COLUMN_APPROVERREMARK );
            columnName.add( COLUMN_APPROVEDDATESTRING );
            columnName.add( COLUMN_SUPPORTPERFORMER );
            columnName.add( COLUMN_APPSUPPORTSTATUS);
            columnName.add( COLUMN_APPSUPPORTREMARK );
            columnName.add( COLUMN_APPSUPPORTCLOSEDTSTRING );           

            
            LinkedHashMap columnsExcel = new LinkedHashMap();
            
            columnsExcel.put(COLUMN_TICKET_NO, COLUMN_TICKET_NO);
            columnsExcel.put(COLUMN_CALLTYPE,  COLUMN_CALLTYPE);
            columnsExcel.put(COLUMN_CALLCREATEDBY, COLUMN_CALLCREATEDBY);
            columnsExcel.put(COLUMN_CALLLOGDATE, COLUMN_CALLLOGDATE);
            columnsExcel.put(COLUMN_BRANCH, COLUMN_BRANCH);            
            columnsExcel.put(COLUMN_APPLICATIONTYPE,  COLUMN_APPLICATIONTYPE);
            columnsExcel.put(COLUMN_CATEGORY, COLUMN_CATEGORY);
            columnsExcel.put(COLUMN_SUBCATEGORY, COLUMN_SUBCATEGORY);
            columnsExcel.put(COLUMN_REOPENID, COLUMN_REOPENID);
            columnsExcel.put(COLUMN_APPROVERSTATUS, COLUMN_APPROVERSTATUS);
            columnsExcel.put(COLUMN_APPROVERREMARK, COLUMN_APPROVERREMARK);
            columnsExcel.put(COLUMN_APPROVEDDATESTRING, COLUMN_APPROVEDDATESTRING);
            columnsExcel.put(COLUMN_SUPPORTPERFORMER, COLUMN_SUPPORTPERFORMER);
            columnsExcel.put(COLUMN_APPSUPPORTSTATUS, COLUMN_APPSUPPORTSTATUS);
            columnsExcel.put(COLUMN_APPSUPPORTREMARK, COLUMN_APPSUPPORTREMARK);
            columnsExcel.put(COLUMN_APPSUPPORTCLOSEDTSTRING, COLUMN_APPSUPPORTCLOSEDTSTRING);    
                                              
            hm.put(KEY_COLUMN, columnName );
            hm.put(KEY_EXCEL_COLUMN, columnsExcel);
            hm.put(KEY_DATA, completeData );
            hm.put(KEY_REPORT_NAME, reportName);    
		}
		catch(Exception e){
			throw new RuntimeException("Error while getting getCompletedDataByUser", e);	
			}
			finally{
			if(stmt!=null){
				try{
					stmt.close();
				}catch(SQLException sqe){}				
			}
			if(rs!=null){
				try{
					rs.close();
				}catch(SQLException sqe){}				
			}
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException sqe){}				
			}
		}
		
		return hm;
	}
	

}
