package com.savvion.rcf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;


import com.savvion.rcf.bean.*;


public class CallDeskReportDAO extends CalldeskDBResource{
	
	private static CallDeskReportDAO callDeskReportDAO = null;	
	
	public final String KEY_DATA = "data";
	public final String KEY_REPORT_NAME = "name";
	public final String KEY_COLUMN = "columns";
	public final String KEY_EXCEL_COLUMN = "ExcelColumn";
	
	Connection connection =null;
	Statement statement; 
	
	//*******************Query for TAT Report******************************
	private static String SELECT_TAT_DATA = "SELECT DISTINCT cd.process_instance_id,cd.TICKETNO,cd.ISSUEREQUESTID,cd.APPLICATIONTYPE,cd.CALLCREATEDBY,cd.CALLLOGDATE,cd.ESCALATIONSTATUS ";
	//private static String SELECT_TAT_FROM = " FROM CALLDESK cd, workstep ws, processtemplate pt,processinstance pi ";
	private static String SELECT_TAT_FROM = " FROM CALLDESK_V2 cd, workstep ws, processtemplate pt,processinstance pi ";
	private static String SELECT_TAT_WHERE = " WHERE cd.process_instance_id = ws.process_instance_id AND pt.process_template_name = 'CallDesk' AND ws.WORKSTEP_NAME = 'AppSupport' AND cd.process_instance_id = pi.process_instance_id AND pi.status = 'PI_ACTIVATED' ";
	
	//*******************Query for Active Status Report******************************
	private static String SELECT_ACTIVE_DATA = "SELECT DISTINCT cd.TICKETNO,cd.CALLCREATEDBY,cd.CALLLOGDATE,cd.CALLTYPE,cd.ISSUEREQUESTID,cd.APPLICATIONTYPE,cd.BRANCH,cd.REOPENID,cd.USERREMARK,cd.APPROVERUSERID,cd.APPROVERNAME,cd.APPROVERDESIGNATION,cd.APPROVERSTATUS,cd.APPROVEDDATESTRING,cd.APPROVERREMARK,cd.APPSUPPORTSTATUS,cd.APPSUPPORTREMARK,cd.ESCALATIONSTATUS ";
	//private static String SELECT_ACTIVE_FROM = " FROM CALLDESK cd, workstep ws, processtemplate pt,processinstance pi ";
	private static String SELECT_ACTIVE_FROM = " FROM CALLDESK_V2 cd, workstep ws, processtemplate pt,processinstance pi ";
	private static String SELECT_ACTIVE_WHERE = " WHERE cd.process_instance_id = ws.process_instance_id AND pt.process_template_name = 'CallDesk' AND cd.process_instance_id = pi.process_instance_id AND pi.status = 'PI_ACTIVATED' ";
	
	//Query for Suspended Items
	//private static String SELECT_CALLDESK_DATA  = "select TICKETNO,cd.PROCESS_INSTANCE_ID,APPLICATIONTYPE,CALLCREATEDBY,CALLTYPE,CALLLOGDATE,ws.workstep_name from calldesk cd,workstep ws where cd.PROCESS_INSTANCE_ID  in ( ";
	private static String SELECT_CALLDESK_DATA  = "select TICKETNO,cd.PROCESS_INSTANCE_ID,APPLICATIONTYPE,CALLCREATEDBY,CALLTYPE,CALLLOGDATE,ws.workstep_name from calldesk_V2 cd,workstep ws where cd.PROCESS_INSTANCE_ID  in ( ";
	private static String SELECT_WORKSTEP_DATA  = " select  PROCESS_INSTANCE_ID from workstep where status='W_SUSPENDED' and PROCESS_INSTANCE_ID in ( ";
	private static String SELECT_PI_DATA = " select PROCESS_INSTANCE_ID from processinstance pi,processtemplate pt where pi.PROCESS_TEMPLATE_ID=pt.PROCESS_TEMPLATE_ID and process_template_name='CallDesk' and pi.status ='PI_ACTIVATED' ";
	
	//Query for Complete Item
	private static String SELECT_CALLDESK_COMPLETE = " select TICKETNO,CALLCREATEDBY,BRANCH,APPLICATIONTYPE,APPLICATIONTYPE,CALLTYPE,CALLLOGDATE,APPROVERNAME,APPROVERSTATUS,APPROVEDDATESTRING,APPSUPPORTSTATUS,APPSUPPORTCLOSEDTSTRING,PI.START_TIME ";
	//private static String SELECT_COMPLETE_FROM =" from calldesk cd ,PROCESSINSTANCE PI,workitem wi where cd.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID and wi.PROCESS_INSTANCE_ID = cd.PROCESS_INSTANCE_ID and PI.PROCESS_TEMPLATE_ID = 101 and wi.status ='I_COMPLETED' ";
	private static String SELECT_COMPLETE_FROM =" from calldesk_v2 cd ,PROCESSINSTANCE PI,workitem wi where cd.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID and wi.PROCESS_INSTANCE_ID = cd.PROCESS_INSTANCE_ID and PI.PROCESS_TEMPLATE_ID = 101 and wi.status ='I_COMPLETED' ";
	
	//Query for Completed Items by User
	private static String SELECT_COMPLETE_DATA = "select cd.TICKETNO,cd.CALLTYPE,cd.CALLCREATEDBY,cd.CALLLOGDATE,cd.BRANCH,cd.APPLICATIONTYPE,cd.ISSUEREQUESTID,cd.ISSUEREQSUBTYPEID,cd.REOPENID,cd.APPROVALFINALSTATUS,cd.APPROVERFINALREMARK1,cd.FINALAPPROVALDATE,cd.APPSUPPORTPERFORMER,cd.APPSUPPORTFINALSTATUS,cd.APPSUPPORTREMARK,cd.APPSUPPORTCLOSEDTSTRING ";
	private static String SELECT_FROM_DATA = " from TechDesk cd ,PROCESSINSTANCE PI,workitem wi,processtemplate pt ";
	private static String SELECT_WHERE_DATA = " where cd.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID and wi.PROCESS_INSTANCE_ID = cd.PROCESS_INSTANCE_ID and pt.PROCESS_TEMPLATE_NAME='TechDesk' and pt.process_template_id = wi.process_template_id and pt.process_template_id = pi.process_template_id and PI.STATUS='PI_COMPLETED' and wi.status ='I_COMPLETED' ";
	private static String COMPLETE_GROUP_BY = " GROUP BY cd.TICKETNO,cd.CALLTYPE,cd.CALLCREATEDBY,cd.CALLLOGDATE,cd.BRANCH,cd.APPLICATIONTYPE,cd.ISSUEREQUESTID,cd.ISSUEREQSUBTYPEID,cd.REOPENID,cd.APPROVALFINALSTATUS,cd.APPROVERFINALREMARK1,cd.FINALAPPROVALDATE,cd.APPSUPPORTPERFORMER,cd.APPSUPPORTFINALSTATUS,cd.APPSUPPORTREMARK,cd.APPSUPPORTCLOSEDTSTRING ";
	
	
	//private static String SELECT_COMPLETE_DATA = " select TICKETNO,wi.PERFORMER,CALLCREATEDBY,BRANCH,APPLICATIONTYPE,CALLTYPE,CALLLOGDATE,APPROVERNAME,APPROVERSTATUS,APPROVEDDATESTRING,APPSUPPORTSTATUS,APPSUPPORTCLOSEDTSTRING,wi.END_TIME,sum(wi.ESTIMATED_DURATION),USERREMARK,APPSUPPORTREMARK ";
	//private static String SELECT_FROM_DATA = " from calldesk cd ,PROCESSINSTANCE PI,workitem wi,processtemplate pt ";
	//private static String SELECT_FROM_DATA = " from calldesk_v2 cd ,PROCESSINSTANCE PI,workitem wi,processtemplate pt ";
	//private static String SELECT_WHERE_DATA = " where cd.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID and wi.PROCESS_INSTANCE_ID = cd.PROCESS_INSTANCE_ID and pt.PROCESS_TEMPLATE_NAME='CallDesk' and pt.process_template_id = wi.process_template_id and pt.process_template_id = pi.process_template_id and PI.STATUS='PI_COMPLETED' and wi.status ='I_COMPLETED' ";
	//private static String COMPLETE_GROUP_BY = " GROUP BY TICKETNO,wi.performer,CALLCREATEDBY,BRANCH,APPLICATIONTYPE,APPLICATIONTYPE,CALLTYPE,CALLLOGDATE,APPROVERNAME,APPROVERSTATUS,APPROVEDDATESTRING,APPSUPPORTSTATUS,APPSUPPORTCLOSEDTSTRING,wi.END_TIME,USERREMARK,APPSUPPORTREMARK ";
	
	private static String SELECT_STATUS_DATA = " select cd.ticketno,cd.calltype,cd.CALLCREATEDBY,cd.CALLLOGDATE,cd.BRANCH,cd.APPLICATIONTYPE,pi.status,wi.performer ";
	//private static String STATUS_FROM_DATA = " from calldesk cd,processinstance pi,workitem wi ";
	private static String STATUS_FROM_DATA = " from calldesk_V2 cd,processinstance pi,workitem wi ";
	private static String STATUS_WHERE_DATA =" where cd.process_template_id = pi.process_template_id and cd.process_template_id = wi.process_template_id and cd.process_instance_id = pi.process_instance_id and cd.process_instance_id = wi.process_instance_id and wi.workstep_name <> 'ViewAppSupportData' ";
	
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
	private static String COLUMN_SUPPORTPERFORMER="Resolved By";
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
		 
	ResultSetMetaData rsMetadata ;
	
	private CallDeskReportDAO()
    {
        super();
    }
	public static synchronized CallDeskReportDAO getInstance()
    {
        if ( callDeskReportDAO == null )
        {
        	callDeskReportDAO = new CallDeskReportDAO();
        }
        return callDeskReportDAO;
    }
	
	public Map getReopenData(String ticketNo){
		Map detailMap = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			ArrayList<DetailedReportDataBean> reopenDataList = new ArrayList<DetailedReportDataBean>();
			
			//String query = "select callcreatedby,userremark,approvername,approveddatestring,approverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate from calldesk where ticketno='"+ticketNo+"' and reopenid is null";
			//String query = "select ticketno,callcreatedby,userremark,approvername,approveddatestring,approverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate,reopenid,uploadscreen from CallDesk cd, processinstance pi where pi.process_instance_id = cd.process_instance_id and ticketno='"+ticketNo+"' and pi.status = 'PI_COMPLETED'";
			//String query = "select ticketno,callcreatedby,userremark,approvername,approver2Name,Approver1DocLocation,Approver2DocLocation,AppSupportDocLocation,FinalApprovalDate,approverremark,VerifyApprovalRemark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate,reopenid,uploadscreen from CallDesk_V2 cd, processinstance pi where pi.process_instance_id = cd.process_instance_id and ticketno='"+ticketNo+"' and pi.status = 'PI_COMPLETED'";
			String query = "select ticketno,callcreatedby,userremark,approvername,approver2Name,Approver1DocLocation,Approver2DocLocation,AppSupportDocLocation,FinalApprovalDate,firstapproverremark,secondapproverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate,reopenid,uploadscreen from TechDesk cd, processinstance pi where pi.process_instance_id = cd.process_instance_id and ticketno='"+ticketNo+"' and pi.status = 'PI_COMPLETED'";
			
			
			conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( query.toString() );            
            while ( rs.next() )
            {
            	DetailedReportDataBean bean = new DetailedReportDataBean();
            	bean.setTICKETNO(rs.getString("ticketno"));                
                bean.setCALLCREATEDBY(rs.getString("callcreatedby"));
                bean.setUSERREMARK(rs.getString("userremark"));
                bean.setAPPROVERNAME(rs.getString("approvername"));
                bean.setAPPROVER2NAME(rs.getString("approver2name"));
                bean.setAPPROVER1DOCLOCATION(rs.getString("approver1doclocation"));
                bean.setAPPROVER2DOCLOCATION(rs.getString("approver2doclocation"));
                bean.setAPPSUPPORTDOCLOCATION(rs.getString("appsupportdoclocation"));
                bean.setFINALAPPROVALDATE(rs.getString("finalapprovaldate"));
                //bean.setAPPROVERFINALREMARK(rs.getString("approverfinalremark"));
                //bean.setAPPROVEDDATESTRING(rs.getString("approveddatestring"));
               bean.setAPPROVERREMARK(rs.getString("firstapproverremark"));
                bean.setVERIFYAPPROVALREMARK(rs.getString("secondapproverremark"));
                 bean.setTEAMNAME(rs.getString("teamname"));
                bean.setAPPSUPPORTPERFORMER(rs.getString("appsupportperformer"));
                bean.setAPPSUPPORTCLOSEDTSTRING(rs.getString("appsupportclosedtstring"));
                bean.setAPPSUPPORTREMARK(rs.getString("appsupportremark"));
                bean.setBRANCH(rs.getString("branch"));
                bean.setCALLLOGDATE(rs.getString("calllogdate"));
                bean.setREOPENID(rs.getString("reopenid"));
                bean.setUPLOADSCREEN(rs.getString("uploadscreen"));
                
                reopenDataList.add(bean);
            }
            
            detailMap.put("ReopenData", reopenDataList);
			
		}catch(Exception e){
			throw new RuntimeException("Error while getting getReopenData", e);	
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
		return detailMap;
	}
	public Map getCallStatus(String ticketNo){
		Map tktDtlsMap = new HashMap();
		String callStatus = "" ;
		String callType = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			//String query = "select ticketno,calltype,FinalCallTicketStatus from CallDesk where ticketno='"+ticketNo+"'";
			//String query = "select ticketno,calltype,FinalCallTicketStatus from CallDesk_V2 where ticketno='"+ticketNo+"'";
			String query = "select ticketno,calltype,FinalCallTicketStatus from TechDesk where ticketno='"+ticketNo+"'";
			
			conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( query.toString() );           
            
            while ( rs.next() )
            {
            	tktDtlsMap.put("tStatus", rs.getString("FinalCallTicketStatus"));
            	tktDtlsMap.put("cType", rs.getString("calltype"));
            	//callStatus = (String)rs.getString("FinalCallTicketStatus");
            	//callType = (String)rs.getString("calltype");
            }
            
		}catch(Exception e){
			throw new RuntimeException("Error while getting getCallStatus", e);
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
		return tktDtlsMap;
	}
	public Map getCallTicketData(String ticketNo){
		Map callTicketDetailMap = new HashMap();		
		String ticketStatus = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			ArrayList<DetailedReportDataBean> callDataList = new ArrayList<DetailedReportDataBean>();
			
			//String query = "select callcreatedby,userremark,approvername,approveddatestring,approverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate from calldesk where ticketno='"+ticketNo+"' and reopenid is null";
			//String query = "select ticketno,callcreatedby,userremark,approvername,approveddatestring,approverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate,reopenid,uploadscreen,FinalCallTicketStatus from TechDesk cd, processinstance pi where pi.process_instance_id = cd.process_instance_id and ticketno='"+ticketNo+"'"; // and pi.status = 'PI_COMPLETED'";
			String query = "select ticketno,callcreatedby,userremark,approvername,approveddatestring,approverremark," +
					"teamname,appsupportperformer,appsupportclosedtstring,appsupportremark," +
					"branch,calllogdate,reopenid,uploadscreen,FinalCallTicketStatus," +
					"wi.performer,wi.prev_performer " +
					"from TechDesk cd ,PROCESSINSTANCE PI,workitem wi,processtemplate pt " +
					"where cd.PROCESS_INSTANCE_ID=PI.PROCESS_INSTANCE_ID " +
					"and wi.PROCESS_INSTANCE_ID = cd.PROCESS_INSTANCE_ID " +
					"and pt.PROCESS_TEMPLATE_NAME='TechDesk' " +
					"and pt.process_template_id = wi.process_template_id " +
					"and pt.process_template_id = pi.process_template_id " +
					"and PI.STATUS='PI_ACTIVATED' " +
					"and wi.workstep_name='AppSupport' " +
					"and cd.ticketno='"+ticketNo+"'";
			
			conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( query.toString() );            
            while ( rs.next() )
            {
            	DetailedReportDataBean bean = new DetailedReportDataBean();
            	bean.setTICKETNO(rs.getString("ticketno"));     
            	//bean.setCALLTYPE(rs.getString("calltype"));
                bean.setCALLCREATEDBY(rs.getString("callcreatedby"));
                bean.setUSERREMARK(rs.getString("userremark"));
                bean.setAPPROVERNAME(rs.getString("approvername"));
                bean.setAPPROVEDDATESTRING(rs.getString("approveddatestring"));
                bean.setAPPROVERREMARK(rs.getString("approverremark"));
                bean.setTEAMNAME(rs.getString("teamname"));
                bean.setAPPSUPPORTPERFORMER(rs.getString("appsupportperformer"));
                bean.setAPPSUPPORTCLOSEDTSTRING(rs.getString("appsupportclosedtstring"));
                bean.setAPPSUPPORTREMARK(rs.getString("appsupportremark"));
                bean.setBRANCH(rs.getString("branch"));
                bean.setCALLLOGDATE(rs.getString("calllogdate"));
                bean.setREOPENID(rs.getString("reopenid"));
                bean.setUPLOADSCREEN(rs.getString("uploadscreen"));
                bean.setFINALCALLTICKETSTATUS(rs.getString("finalcallticketstatus"));
                bean.setWI_PERFORMER(rs.getString("performer"));
                bean.setWI_PREV_PERFORMER(rs.getString("prev_performer"));
                
                //ticketStatus = (String)rs.getString("finalcallticketstatus");
                callDataList.add(bean);
            }
            
            callTicketDetailMap.put("CallData", callDataList);
            //callTicketDetailMap.put("STATUS", ticketStatus);
		
			
		}catch(Exception e){
			throw new RuntimeException("Error while getting getCallTicketData", e);
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
		
		return callTicketDetailMap;
		
	}
	
	//
	
	public ArrayList getEscMgrDetails(String ApproverID) throws Exception{
		Map detailsMap = new HashMap();
		ArrayList mgrDetailsList = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			String query = "select escmanagerid,escmgrname,escmgremail from CallDesk_ApproverEsc where approverid='"+ApproverID+"'";
			conn = getConnection();
			stmt = conn.createStatement();
            rs = stmt.executeQuery( query );            
            while(rs.next()){
            	mgrDetailsList.add(0, rs.getString("escmanagerid"));
            	mgrDetailsList.add(1, rs.getString("escmgrname"));
            	mgrDetailsList.add(2, rs.getString("escmgremail"));
            }            
            detailsMap.put("detail", mgrDetailsList);
		}catch(Exception e){
			throw new RuntimeException("Error while getting EscMgrDetails", e); 
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
		return mgrDetailsList;
	}
	
	public ArrayList getSupportEscMgrDetails(String teamName){
		ArrayList mgrDetailsList = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;		
		try{
				String query  = "select TEAMNAME,GROUPNAME,ESCMANAGERID,ESCMGRNAME,ESCMGR_ROLE,ESCMGREMAIL, secondlevel, thirdlevel, SECONDLEVEL_MAIL, SECONDLEVEL_MGR_NAME, THIRDLEVEL_MGR_NAME, THIRDLEVEL_MAIL from CallDesk_SupportEsc where teamname='"+teamName+"'";			
		conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( query );
            
            while(rs.next()){
            	mgrDetailsList.add(0, rs.getString("GROUPNAME"));
            	mgrDetailsList.add(1, rs.getString("ESCMANAGERID"));
            	mgrDetailsList.add(2, rs.getString("ESCMGRNAME"));
            	mgrDetailsList.add(3, rs.getString("ESCMGREMAIL"));
            	mgrDetailsList.add(4, rs.getString("secondlevel"));
            	mgrDetailsList.add(5, rs.getString("thirdlevel"));
		mgrDetailsList.add(6, rs.getString("SECONDLEVEL_MAIL"));
            	mgrDetailsList.add(7, rs.getString("SECONDLEVEL_MGR_NAME"));
            	mgrDetailsList.add(8, rs.getString("THIRDLEVEL_MAIL"));
            	mgrDetailsList.add(9, rs.getString("THIRDLEVEL_MGR_NAME"));
           
            }            
		}catch(Exception e){
			throw new RuntimeException("Error while getting getSupportEscMgrDetails", e); 
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
		
		return mgrDetailsList;
	}
	
/*	public Map getReopenData(String ticketNo){
		Map detailMap = new HashMap();
		System.out.println("TicketNo in getReopenData(): "+ticketNo);
		
		try{
			ArrayList<DetailedReportDataBean> reopenDataList = new ArrayList();
			String reopenID = "";
			String queryReopenID = "Select reopenid from calldesk where ticketno='"+ticketNo+"' and reopenid is not null"; 
			
			Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( queryReopenID.toString() );
            System.out.println( "ResultSet: " + rs );
            DetailedReportDataBean bean = new DetailedReportDataBean();
            
            while ( rs.next() )
            {
            	reopenID = rs.getString("REOPENID");
            	System.out.println("ReopenID: "+reopenID);
            	bean = (DetailedReportDataBean)getReopenDetailsData(ticketNo,reopenID);
            	reopenDataList.add(bean);
            	System.out.println("reopenDataList: -------> "+reopenDataList);
            }
            //reopenDataList.size()
            detailMap.put("ReopenData", reopenDataList);
			
		}catch(Exception e){
			System.out.println("Exception Occured................."+e);
			e.printStackTrace();
		}
		
		return detailMap;
	}
	
	public DetailedReportDataBean getReopenDetailsData(String ticketNo,String reopenID){
		ArrayList<DetailedReportDataBean> reopenDataList = new ArrayList();
		DetailedReportDataBean bean = new DetailedReportDataBean();
		try{
			String query = "select ticketno,callcreatedby,userremark,approvername,approveddatestring,approverremark,teamname,appsupportperformer,appsupportclosedtstring,appsupportremark,branch,calllogdate from calldesk cd, processinstance pi  where ticketno='"+ticketNo+"' and reopenid is null"; // and reopenid='"+reopenID+"'";
			System.out.println("Reopen detail Query: "+query);
			
			Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( query.toString() );
            System.out.println( "ResultSet: " + rs );
            while ( rs.next() )
            {
            	
                
            	bean.setTICKETNO(rs.getString("ticketno"));
                bean.setCALLCREATEDBY(rs.getString("callcreatedby"));
                bean.setUSERREMARK(rs.getString("userremark"));
                bean.setAPPROVERNAME(rs.getString("approvername"));
                bean.setAPPROVEDDATESTRING(rs.getString("approveddatestring"));
                bean.setAPPROVERREMARK(rs.getString("approverremark"));
                bean.setTEAMNAME(rs.getString("teamname"));
                bean.setAPPSUPPORTPERFORMER(rs.getString("appsupportperformer"));
                bean.setAPPSUPPORTCLOSEDTSTRING(rs.getString("appsupportclosedtstring"));
                bean.setAPPSUPPORTREMARK(rs.getString("appsupportremark"));
                bean.setBRANCH(rs.getString("branch"));
                bean.setCALLLOGDATE(rs.getString("calllogdate"));
                bean.setREOPENID(reopenID);
                
                //reopenDataList.add(bean);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	*/
	
	public HashMap getActiveStatusData(String status,String startDate,String endDate){
    	HashMap hm = new HashMap();
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    		try{    			
    			ArrayList dataList = new ArrayList();                
    			
                StringBuffer sql = new StringBuffer( SELECT_ACTIVE_DATA );
                sql.append( SELECT_ACTIVE_FROM );
                sql.append( SELECT_ACTIVE_WHERE );
                 // For the 1st time the page loads, all the parameters are null
                if ( status == null || startDate == null || endDate == null )
                {                	
                	status = "0";
                }
                if ( status.equalsIgnoreCase( "0" ) )
                {
                    sql.append( "" );
                }
                else if(status.equalsIgnoreCase( "1" )){	//Pending with User                
                	sql.append( " AND ws.WORKSTEP_NAME = 'AppSupportWaiting' AND ws.status = 'W_ACTIVATED' " );
                }else if(status.equalsIgnoreCase( "2" )){
                	sql.append( " AND ws.WORKSTEP_NAME = 'Approver' AND ws.status = 'W_ACTIVATED' " );
                }else if(status.equalsIgnoreCase( "3" )){
                	sql.append( " AND ws.WORKSTEP_NAME = 'AppSupport' AND ws.status = 'W_ACTIVATED' " );
                }else if(status.equalsIgnoreCase( "4" )){
                	sql.append( " AND ws.WORKSTEP_NAME = 'AppSupport' AND ws.status = 'W_ACTIVATED' AND cd.escalationstatus is not null " );
                }else if(status.equalsIgnoreCase( "5" )){
                	sql.append( " AND cd.reopenid is not null " );
                }
                
                if ( startDate != null && endDate != null && startDate.length() != 0 && endDate.length() != 0 )
                {
                    sql.append( "AND ws.start_time between to_date( '" + startDate
                            + "', 'MM/DD/YYYY HH:MIAM') and to_date( '" + endDate
                            + "', 'MM/DD/YYYY HH:MIAM')" );
                }	
                sql.append(" ORDER BY TICKETNO " );                            
            
                conn = getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery( sql.toString() );                
                while ( rs.next() )
                {
                    DetailedReportDataBean bean = new DetailedReportDataBean();
                    
                    bean.setTICKETNO(rs.getString( "TICKETNO" ) ); 
                    bean.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
                    bean.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
                    bean.setCALLTYPE(rs.getString("CALLTYPE"));                    
                    bean.setISSUEREQUESTID(rs.getString("ISSUEREQUESTID"));
                    bean.setAPPLICATIONTYPE(rs.getString( "APPLICATIONTYPE" ));
                    bean.setBRANCH(rs.getString("BRANCH"));
                    bean.setREOPENID(rs.getString("REOPENID"));                                     
                    bean.setAPPROVERUSERID(rs.getString("APPROVERUSERID"));
                    bean.setAPPROVERNAME(rs.getString("APPROVERNAME"));
                    bean.setAPPROVERDESIGNATION(rs.getString("APPROVERDESIGNATION"));
                    bean.setAPPROVERSTATUS(rs.getString("APPROVERSTATUS"));
                    bean.setAPPROVEDDATESTRING(rs.getString("APPROVEDDATESTRING"));
                    bean.setAPPROVERREMARK(rs.getString("APPROVERREMARK"));     
                    bean.setAPPSUPPORTSTATUS(rs.getString("APPSUPPORTSTATUS"));
                    bean.setAPPSUPPORTREMARK(rs.getString("APPSUPPORTREMARK"));
                    bean.setUSERREMARK(rs.getString("USERREMARK"));   
                    bean.setESCALATIONSTATUS(rs.getString("ESCALATIONSTATUS"));
                    
                    dataList.add( bean );
                }
                
                ArrayList columnNames = new ArrayList();
                columnNames.add( COLUMN_TICKET_NO );
                columnNames.add( COLUMN_CALLCREATEDBY );
                columnNames.add( COLUMN_CALLLOGDATE );
                columnNames.add(COLUMN_CALLTYPE);
                columnNames.add( COLUMN_ISSUEREQUEST_TYPE );
                columnNames.add( COLUMN_APPLICATIONTYPE );
                columnNames.add(COLUMN_BRANCH);
                columnNames.add(COLUMN_REOPENID);
                columnNames.add(COLUMN_APPROVERID);
                columnNames.add(COLUMN_APPROVERNAME);
                columnNames.add(COLUMN_APPROVERDSGN);
                columnNames.add(COLUMN_APPROVERSTATUS);
                columnNames.add(COLUMN_APPROVEDDATESTRING);
                columnNames.add(COLUMN_APPSUPPORTSTATUS);
                //columnNames.add(COLUMN_APPROVERREMARK);
                //columnNames.add(COLUMN_APPSUPPORTREMARK);
                columnNames.add( COLUMN_ESCALATIONSTATUS );   
                
                ArrayList columnsExcel = new ArrayList();
                                
                columnsExcel.add( COLUMN_TICKET_NO );
                columnsExcel.add( COLUMN_CALLCREATEDBY );
                columnsExcel.add( COLUMN_CALLLOGDATE );
                columnsExcel.add(COLUMN_CALLTYPE);
                columnsExcel.add( COLUMN_ISSUEREQUEST_TYPE );
                columnsExcel.add( COLUMN_APPLICATIONTYPE );
                columnsExcel.add(COLUMN_BRANCH);
                columnsExcel.add(COLUMN_REOPENID);
                columnsExcel.add(COLUMN_APPROVERID);
                columnsExcel.add(COLUMN_APPROVERNAME);
                columnsExcel.add(COLUMN_APPROVERDSGN);
                columnsExcel.add(COLUMN_APPROVERSTATUS);
                columnsExcel.add(COLUMN_APPROVEDDATESTRING);
                columnsExcel.add(COLUMN_APPSUPPORTSTATUS);
                columnsExcel.add(COLUMN_USERREMARK);
                columnsExcel.add(COLUMN_APPROVERREMARK);
                columnsExcel.add(COLUMN_APPSUPPORTREMARK);
                columnsExcel.add( COLUMN_ESCALATIONSTATUS ); 

                
                // OK, Now create map
                hm.put( KEY_COLUMN, columnNames );
                hm.put(KEY_EXCEL_COLUMN, columnsExcel);
                hm.put( KEY_DATA, dataList );
                hm.put( KEY_REPORT_NAME, "ACTIVE REPROT_" );                
    		}
    		catch(Exception e){
    			throw new RuntimeException("Error while getting getActiveStatus", e);	
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
	
	public Map getTATData(String escStatus,String startDate,String endDate){
    	Map< String, Object > hm = new HashMap< String, Object >();
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;

    		try{
    			List< TATReportDataBean > tatDataList = new ArrayList< TATReportDataBean >();                
    			// ,  , 
                StringBuffer sql = new StringBuffer( SELECT_TAT_DATA );
                sql.append( SELECT_TAT_FROM );
                sql.append( SELECT_TAT_WHERE );
                
                if ( escStatus == null || startDate == null || endDate == null )
                {                	
                	escStatus = "0";
                }
                if ( escStatus.equalsIgnoreCase( "0" ) )
                {
                    sql.append( "" );
                }else if(escStatus.equalsIgnoreCase( "1" )){
                	sql.append( "AND cd.ESCALATIONSTATUS is null " );
                }else if(escStatus.equalsIgnoreCase( "2" )){
                	sql.append( "AND cd.ESCALATIONSTATUS is not null " );
                }
                if ( startDate != null && endDate != null && startDate.length() != 0 && endDate.length() != 0 )
                {
                    sql.append( "AND ws.start_time between to_date( '" + startDate
                            + "', 'MM/DD/YYYY HH:MIAM') and to_date( '" + endDate
                            + "', 'MM/DD/YYYY HH:MIAM')" );
                }	
                sql.append(" ORDER BY TICKETNO " );                
                
                conn = getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery( sql.toString() );
                
                while ( rs.next() )
                {
                    TATReportDataBean bean = new TATReportDataBean();
                    
                    bean.setTICKETNO(rs.getString( "TICKETNO" ) ); 
                    bean.setISSUEREQUESTID(rs.getString("ISSUEREQUESTID"));
                    bean.setAPPLICATIONTYPE(rs.getString( "APPLICATIONTYPE" ));
                    bean.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
                    bean.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
                    bean.setESCALATIONSTATUS(rs.getString( "ESCALATIONSTATUS" ));
                    
                    tatDataList.add( bean );
                }
                
                List< String > columnNames = new ArrayList< String >();
                columnNames.add( COLUMN_TICKET_NO );
                columnNames.add( COLUMN_ISSUEREQUEST_TYPE );
                columnNames.add( COLUMN_APPLICATIONTYPE );
                columnNames.add( COLUMN_CALLCREATEDBY );
                columnNames.add( COLUMN_CALLLOGDATE );
                columnNames.add( COLUMN_ESCALATIONSTATUS );                

                // OK, Now create map
                hm.put( KEY_COLUMN, columnNames );
                hm.put( KEY_DATA, tatDataList );
                hm.put( KEY_REPORT_NAME, "TAT REPROT_" );                
    		}
    		catch(Exception e){
    			throw new RuntimeException("Error while getting getTATData", e);	
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
	
	public HashMap getSuspendedDataByDate(String startDate, String endDate) throws Exception{
		
		HashMap hm = new HashMap();
		ArrayList suspendedData = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try{
			StringBuffer sql = new StringBuffer(SELECT_CALLDESK_DATA);
			sql.append(SELECT_WORKSTEP_DATA);
			sql.append(SELECT_PI_DATA);
			
			if ( startDate != null && endDate != null && startDate.length() != 0 && endDate.length() != 0 && startDate != "All" && endDate != "All")
            {
                sql.append( " and start_time between '"+startDate+"' and '"+endDate+"' )) order by 1" );
            }
			else if(startDate == null && endDate == null ){
				sql.append(" )) and ws.status='W_SUSPENDED' and cd.process_instance_id=ws.process_instance_id order by 1");
			}else if( startDate == "All" && endDate == "All"){
				sql.append(" )) and ws.status='W_SUSPENDED' and cd.process_instance_id=ws.process_instance_id order by 1");
			}					
						
			conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( sql.toString() );            
            
            while ( rs.next() )
            {
            	TATReportDataBean bean1 = new TATReportDataBean();
            	bean1.setTICKETNO(rs.getString("TICKETNO"));
            	bean1.setPid(rs.getInt("process_instance_id"));
            	bean1.setAPPLICATIONTYPE(rs.getString("APPLICATIONTYPE"));
            	bean1.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
            	bean1.setCALLTYPE(rs.getString("CALLTYPE"));
            	bean1.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
            	bean1.setWORKSTEPNAME(rs.getString("workstep_name"));
            	
            	
            	suspendedData.add(bean1);
            }            
            
            ArrayList columnName = new ArrayList();
            
            columnName.add( COLUMN_TICKET_NO );
            columnName.add( COLUMN_PID );
            columnName.add( COLUMN_APPLICATIONTYPE );
            columnName.add( COLUMN_CALLCREATEDBY );
            columnName.add( COLUMN_CALLTYPE );
            columnName.add( COLUMN_CALLLOGDATE );
            columnName.add( COLUMN_WORKSTEPNAME );
            columnName.add( COLUMN_RESUME );
                        
            
            hm.put( KEY_COLUMN, columnName );
            hm.put( KEY_DATA, suspendedData );
            hm.put( KEY_REPORT_NAME, "SUSPEND REPROT_" );    
			
            
		}
		catch(Exception e){
			throw new RuntimeException("Error while getting getSuspendedDataByDate", e);	
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
	
	//
	public HashMap getCompleteReportByDate(String startDate,String endDate) throws Exception{	
		
		HashMap hm = new HashMap();
		ArrayList completeData = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{//,
			StringBuffer sql = new StringBuffer(SELECT_CALLDESK_COMPLETE);
			sql.append(SELECT_COMPLETE_FROM);
			
			if ( startDate != null && endDate != null && startDate.length() != 0 && endDate.length() != 0 && startDate != "All" && endDate != "All")
            {
                sql.append( " and start_time between '"+startDate+"' and '"+endDate+"' )) order by 1" );
            }
			else if(startDate == null && endDate == null ){
				sql.append(" order by 1");
			}else if( startDate == "All" && endDate == "All"){
				sql.append(" order by 1");
			}					
			
			
			conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery( sql.toString() );            
            
            while ( rs.next() )
            {
            	DetailedReportDataBean bean1 = new DetailedReportDataBean();
            	
            	bean1.setTICKETNO(rs.getString("TICKETNO"));
            	bean1.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
            	bean1.setBRANCH(rs.getString("BRANCH"));
            	bean1.setAPPLICATIONTYPE(rs.getString("APPLICATIONTYPE"));
            	bean1.setCALLTYPE(rs.getString("CALLTYPE"));
            	bean1.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
            	bean1.setAPPROVERNAME(rs.getString("APPROVERNAME"));
            	bean1.setAPPROVERSTATUS(rs.getString("APPROVERSTATUS"));
            	bean1.setAPPROVEDDATESTRING(rs.getString("APPROVEDDATESTRING"));
            	bean1.setAPPSUPPORTSTATUS(rs.getString("APPSUPPORTSTATUS"));
            	bean1.setAPPSUPPORTCLOSEDTSTRING(rs.getString("APPSUPPORTCLOSEDTSTRING"));            	
            	bean1.setSTART_TIME(rs.getString("START_TIME"));            	
            	
            	completeData.add(bean1);
            }            
            
            ArrayList columnName = new ArrayList();
            
            columnName.add( COLUMN_TICKET_NO );
            columnName.add(COLUMN_CALLCREATEDBY);
            columnName.add(COLUMN_BRANCH);            
            columnName.add( COLUMN_APPLICATIONTYPE );
            columnName.add( COLUMN_CALLTYPE );
            columnName.add( COLUMN_CALLLOGDATE );
            columnName.add( COLUMN_APPROVERNAME );
            columnName.add( COLUMN_APPROVERSTATUS );
            columnName.add( COLUMN_APPROVEDDATESTRING );
            columnName.add( COLUMN_APPSUPPORTSTATUS );
            columnName.add( COLUMN_APPSUPPORTCLOSEDTSTRING );
            columnName.add( COLUMN_PI_START_TIME );
            
            
            hm.put( KEY_COLUMN, columnName );
            hm.put( KEY_DATA, completeData );
            hm.put( KEY_REPORT_NAME, "COMPLETE_REPROT_" );    
			
		}
		catch(Exception e){
			throw new RuntimeException("Error while getting getCompletedReportDataByDate", e);	
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
	
	public HashMap getCompleteReportByUser(String sbmUser,String startDate,String endDate,String ticketNo) throws Exception{		
			
		HashMap hm = new HashMap();
		ArrayList completeData = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{//,,,
			StringBuffer sql = new StringBuffer(SELECT_COMPLETE_DATA);
			sql.append(SELECT_FROM_DATA);
			sql.append(SELECT_WHERE_DATA);
			//sql.append(" and wi.workstep_name = 'AppSupport' ");			
			
			if(sbmUser.equals("rgicl") || sbmUser.equals("rgicbr")){
				sql.append(" and wi.performer = 'rgicl' ");
			}else if(sbmUser != null && !sbmUser.equals("admin")){
				sql.append(" and wi.performer = '"+sbmUser+"' ");				
			}else if(sbmUser.equals("admin")){
				sql.append("");
			}
			
			if(ticketNo == null || ticketNo.equalsIgnoreCase("All")){
				sql.append("");
			}else {
				sql.append(" and cd.ticketno = '"+ticketNo+"' ");
			}			
			
			if ( startDate != null && endDate != null && startDate.length() != 0 && endDate.length() != 0 && startDate != "All" && endDate != "All")
            {				
                sql.append( " and wi.end_time between to_date('"+startDate+"','yyyy/mm/dd:hh:miam') and to_date('"+endDate+"','yyyy/mm/dd:hh:miam') "+ COMPLETE_GROUP_BY +"order by 1" );
            }
			else if(startDate == null && endDate == null ){
				sql.append( COMPLETE_GROUP_BY + " order by 1");
			}else if( startDate == "All" && endDate == "All"){
				sql.append(COMPLETE_GROUP_BY + " order by 1");
			}	
				
			conn = getConnection();
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
            	bean1.setAPPSUPPORTPERFORMER(rs.getString("APPSUPPORTPERFORMER"));
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
            columnName.add( COLUMN_APPROVEDDATESTRING );
            columnName.add( COLUMN_SUPPORTPERFORMER );
            columnName.add( COLUMN_APPSUPPORTCLOSEDTSTRING );           

            
            ArrayList columnsExcel = new ArrayList();
            
            columnsExcel.add( COLUMN_TICKET_NO );
            columnsExcel.add( COLUMN_CALLTYPE );            
            columnsExcel.add(COLUMN_CALLCREATEDBY);
            columnsExcel.add( COLUMN_CALLLOGDATE );
            columnsExcel.add(COLUMN_BRANCH);            
            columnsExcel.add( COLUMN_APPLICATIONTYPE );
            columnsExcel.add( COLUMN_CATEGORY );
            columnsExcel.add( COLUMN_SUBCATEGORY );  
            columnsExcel.add( COLUMN_REOPENID );
            columnsExcel.add( COLUMN_APPROVERSTATUS );
            columnsExcel.add( COLUMN_APPROVERREMARK );            
            columnsExcel.add( COLUMN_APPROVEDDATESTRING );
            columnsExcel.add( COLUMN_SUPPORTPERFORMER );
            columnsExcel.add( COLUMN_APPSUPPORTSTATUS);
            columnsExcel.add( COLUMN_APPSUPPORTREMARK );
            columnsExcel.add( COLUMN_APPSUPPORTCLOSEDTSTRING );         
            
                                    
            hm.put( KEY_COLUMN, columnName );
            hm.put(KEY_EXCEL_COLUMN, columnsExcel);
            hm.put( KEY_DATA, completeData );
            hm.put( KEY_REPORT_NAME, "COMPLETEBYUSER_REPROT_" );    
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
	
	/*****  Status Report ******/
	
	public HashMap getStatusReportByTicket(String ticketNo) throws Exception{		
				
		HashMap hm = new HashMap();
		ArrayList reportData = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{ //,,select cd.ticketno,cd.calltype,pi.status,wi.performer
			StringBuffer sql = new StringBuffer(SELECT_STATUS_DATA);
			sql.append(STATUS_FROM_DATA);
			sql.append(STATUS_WHERE_DATA);
			sql.append(" and cd.ticketno = '"+ticketNo+"' ");	
 	
			conn = getConnection();
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
            	bean1.setPI_STATUS(rs.getString("STATUS"));
            	bean1.setWI_PERFORMER(rs.getString("PERFORMER"));
            	
                      	
            	/* Approver Status */
            	// approver wi status
      /*      	bean1.setAPPROVERSTATUS(rs.getString("APPROVERSTATUS"));
            	// approver performer
            	bean1.setAPPROVERNAME(rs.getString("APPROVERNAME"));
            	bean1.setAPPROVEDDATESTRING(rs.getString("APPROVEDDATESTRING"));*/
            	
            	/* Approver Status */
            	//AppSupport WI Status
            	// AppSupport Status: 
            	//AppSupport Waiting: WI
            	//bean1.setAPPSUPPORTCLOSEDTSTRING(rs.getString("APPSUPPORTCLOSEDTSTRING"));
            	            	
            	reportData.add(bean1);
            }
            
            ArrayList columnName = new ArrayList();
            
            columnName.add( COLUMN_TICKET_NO );
            columnName.add( COLUMN_CALLTYPE );
            columnName.add(COLUMN_CALLCREATEDBY);
            columnName.add( COLUMN_CALLLOGDATE );  
            columnName.add(COLUMN_BRANCH); 
            columnName.add( COLUMN_APPLICATIONTYPE );
            columnName.add(COLUMN_STATUS);
            columnName.add(COLUMN_PERFORMER);
            
                                    
            hm.put( KEY_COLUMN, columnName );
            hm.put( KEY_DATA, reportData );
            hm.put( KEY_REPORT_NAME, "STATUS_REPROT_" );    
		}
		catch(Exception e){
			throw new RuntimeException("Error while getting getStatusReportByTicket", e);	
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
