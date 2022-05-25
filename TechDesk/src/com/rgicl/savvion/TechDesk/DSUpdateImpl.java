package com.rgicl.savvion.TechDesk;

import java.net.InetAddress;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import com.savvion.sbm.util.logger.LoggerManager;
import com.savvion.sbm.util.logger.SBMLogger;


import com.savvion.util.NLog;

//import org.apache.axis.client.Service;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

//import app.calldesk.*;
//import app.itools.*;

public class DSUpdateImpl {

	private BLServer blserver; 
	private BLClientUtil blc;
	private Session blsession;
	private static String bl_user = "rgicl";
	private static String bl_password = "rgicl";

	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sqlString = "SELECT GROUPNAME FROM CALLDESK_SUPPORTESC WHERE TEAMNAME=?";
	private String appSupportGroupName = "CDITGrp";
	//private static final String TREE_DATASOURCE = "jdbc/SBMCommonDBXA";
	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
	private DataSource ds = null;
	private String callDesk_WS_URL = "http://10.65.15.79:8080/Service.asmx?wsdl";
	private String iTools_WS_URL = "http://10.65.15.83:8090/RGICLHolidayService.asmx?wsdl";
	private long approver1Tat = 36000; // 10 hours
	private long approver2Tat = 36000;
	private long supportTeamTat = 64800; //2 days - 18 hrs - 9 hrs per day

	// private static Logger log = null;
	/*
	 * static { try { log =
	 * Logger.getLogger("com.rgicl.savvion.TechDesk.DSUpdateImpl.class");
	 * FileAppender fileappender = new FileAppender(new
	 * PatternLayout(),"/opt/SBM75/logs/output.txt");
	 * log.addAppender(fileappender); log.info("test"); } catch(Exception e) {
	 * throw new RuntimeException(e); } }
	 */

	/*
	 * public String getApproverBranchCode(String approver) { String
	 * branchCode=""; try { if(approver!=null && approver.length()>1) {
	 * ServiceSoap_BindingStub stb = new ServiceSoap_BindingStub(new
	 * URL(callDesk_WS_URL), new Service()); branchCode =
	 * stb.getUserBranchCode(approver); } }catch(Exception e) { throw new
	 * RuntimeException(e); } return branchCode; }
	 * 
	 * public int getHolidayCount(String branchCode, String startDate, String
	 * endDate) throws Exception { int cnt=0; try { if(startDate!=null &&
	 * endDate!=null) { RGICLHolidayServiceSoap_BindingStub rstb = new
	 * RGICLHolidayServiceSoap_BindingStub(new URL(iTools_WS_URL), new
	 * Service()); cnt = rstb.getHolidayCount(branchCode, startDate, endDate); }
	 * }catch(Exception e) { throw new RuntimeException(e); } return cnt; }
	 * 
	 * public long getApproverTAT(String user) throws Exception { long
	 * TAT=50400; try { String startDTFormmated = new
	 * SimpleDateFormat("yyyy/MM/dd").format(new Date()); String endDTFormmated
	 * = getBusinessDate("yyyy/MM/dd", new Date().getTime(), TAT*1000); int cnt
	 * = getHolidayCount(getApproverBranchCode(user), startDTFormmated,
	 * endDTFormmated); if(cnt>0) { TAT = TAT + cnt*32400; } }catch(Exception e)
	 * { throw new RuntimeException(e); } return TAT; }
	 * 
	 * public String getBusinessDate(String dateFormat, long fromDate, long
	 * duration) throws Exception { String businessDate=""; try {
	 * SBMCalendar.init(null); SBMCalendar sbmcal = new
	 * SBMCalendar("RGICL_CD_CALENDAR"); BizCalendar bcal = new
	 * BizCalendar(fromDate, duration); Calendar initDuedate =
	 * sbmcal.getDueDate(bcal); SimpleDateFormat Currdateformat1 = new
	 * SimpleDateFormat(dateFormat); businessDate =
	 * Currdateformat1.format(initDuedate.getTime()); }catch(Exception e) {
	 * throw new RuntimeException(e); } return businessDate; }
	 */
	private Connection getConnectionToSQLDB() throws Exception {
		String dbip = "";
		String dbuser = "";
		String dbpass = "";
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("ip: "+ip);
			if (ip.contains("10.62.182.42")) {
				dbip = "10.65.15.148";
				dbuser = "calldesk";
				dbpass = "calldesk";
			} else {
				dbip = "RGIRMSCDDB";//10.65.5.127
				dbuser = "calldesk";
				dbpass = "calldesk";
			}
			connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip
					+ ":7359;databaseName=CalldeskManagement", dbuser, dbpass);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error in getting a DB connection  for db " + dbip + " \n"
							+ e.getMessage(), e);
		}
		return connection;
	}

	public void setTAT(String appName, String category, String subCatetory, String TicketNo)
	{		
		Connection connection = null;
		CallableStatement proc_stmt = null;
		ResultSet rs = null;		
		//PreparedStatement pstmt = null;		
		try {
					connection = getConnectionToSQLDB();
					proc_stmt = connection.prepareCall("{ call usp_Savvion_GetApplicationTAT(?,?,?,?) }");
					proc_stmt.setString(1, appName);
					proc_stmt.setString(2, category);
					proc_stmt.setString(3, subCatetory);
					proc_stmt.setString(4, TicketNo);
					rs = proc_stmt.executeQuery();
					if (rs != null) {
						while (rs.next()) {
							approver1Tat = (rs.getLong("ApproverTAT"))*60*60;
							approver2Tat = rs.getLong("ApproverTAT")*60*60;
							supportTeamTat = rs.getLong("AppsupportTAT")*60*60;
							System.out.println("supportTeamTat::::"+supportTeamTat);
						}
					}					
					proc_stmt.close();
					rs.close();
					connection.close();					
		//System.out.println("approver1Tat " + approver1Tat);
		//System.out.println("supportTeamTat " + supportTeamTat);
		} catch (Exception ex) {
			System.out.println("Error in getting TAT Details" + ex.toString());
		} finally {
			try {
				if (proc_stmt != null) {
					try {
						proc_stmt.close();
					} catch (Exception ex) {
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception ex) {
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {}
		}
	
	}

	public void setDataSlotValues(long piid) throws Exception {
		try {
			blserver = blc.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);

			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);

			String reopenid = (String) pi.getDataSlotValue("ReopenId");
			String callType = (String) pi.getDataSlotValue("CallType");
			String teamTatStr = (String) pi.getDataSlotValue("TeamTAT");
			String approver1 = (String) pi.getDataSlotValue("ApproverID");
			String approver2 = (String) pi.getDataSlotValue("ApproverUserID2");
			String issueRequestId = (String) pi.getDataSlotValue("IssueRequestId");
			String applicationType = (String) pi.getDataSlotValue("ApplicationType");
			String ticket = (String) pi.getDataSlotValue("TicketNo");
			String IssueReqSubTypeID = (String) pi.getDataSlotValue("IssueReqSubTypeID");

			/*
			 * First and Second Approver have TAT(Turn Around Time of 14 hours)
			 * We set first overdue/TAT as 10 hours and on next overdue action
			 * extend the approver TAT 4 hours(10+4). approver1Tat = 50400; //14
			 * hours.
			 */
			setTAT(applicationType, issueRequestId, IssueReqSubTypeID, ticket);
			
			/**
			 * If this case is of Reopen then AppSupport TAT will be of 7 Days. i.e., 63 Hrs
			 * 
			 * */
		  /* if(reopenid != null && reopenid != "" && !reopenid.equals("<NULL>")){
				supportTeamTat = 63 * 60 * 60;
			}*/
			
			
			long totalDuration = 0;
			long totalCallTAT = 0;

			HashMap dsValues = new HashMap();
			dsValues.put("Approver1Tat", approver1Tat);
			dsValues.put("Approver2Tat", approver2Tat);

			if (callType.equalsIgnoreCase("Request")) {
				if (approver1 != null) {
					if (!approver1.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = approver1Tat;
					}
				}
				if (approver2 != null) {
					if (!approver2.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = totalCallTAT + approver2Tat;
					}
				}
				totalCallTAT = totalCallTAT + supportTeamTat;
			} else if (callType.equalsIgnoreCase("ISSUE") || callType.equalsIgnoreCase("Other")) {
				dsValues.put("skipApprover", true);
				dsValues.put("skipVerifyApproval", true);
				dsValues.put("skipApproverUpdate", true);
				dsValues.put("skipApprover2Update", true);
				dsValues.put("skipApproverDocAdapter", true);
				totalCallTAT = supportTeamTat;
			} else {
				if (approver1 != null) {
					if (!approver1.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = approver1Tat;
					}
				}
				if (approver2 != null) {
					if (!approver2.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = totalCallTAT + approver2Tat;
					}
				}
				totalCallTAT = totalCallTAT + supportTeamTat;
			}

			DateTime currentTime = new DateTime((new Date()).getTime());

			dsValues.put("CallDate", currentTime);
			String team = (String) pi.getDataSlotValue("TeamName");
			//Added if condition by Deepak Daneva 
			if(team != null && team.equalsIgnoreCase("IT_infra04") && issueRequestId.equalsIgnoreCase("Network Link")) {
				dsValues.put("AppSupportDuration", (supportTeamTat * 75)/100);
			} else {
				//default as previous
				dsValues.put("AppSupportDuration", supportTeamTat);
			}


			try {
				ds = (DataSource) new InitialContext().lookup(TREE_DATASOURCE);
				connection = ds.getConnection();
				pstmt = connection.prepareStatement(sqlString);
				pstmt.setString(1, team);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					appSupportGroupName = rs.getString("GROUPNAME");
				}
			} catch (Exception e) {
				throw new RuntimeException(
						"Error while getting MotorUW GetSCUIDetails", e);
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqe) {
				}

				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException sqe) {
				}

				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException sqe) {
				}
			}

			if (appSupportGroupName == null || appSupportGroupName.equals("<NULL>") || appSupportGroupName.equals("")) {
				appSupportGroupName = "CDITGrp";
			}

			dsValues.put("AppSupportGroupName", appSupportGroupName);

			if (reopenid != null && reopenid != "" && !reopenid.equals("<NULL>") && callType.equalsIgnoreCase("ISSUE")) {
				dsValues.put("AppSupportGroupName", (String) pi.getDataSlotValue("AppSupportPerformer"));
			}
			System.out.println("totalCallTAT ::: DSUpdateImpl ::: "+totalCallTAT);
			totalDuration = totalCallTAT * 1000;
			long startDate = (new Date()).getTime();
			DateTime currentTime1 = new DateTime((new Date()).getTime());
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(startDate, totalDuration);
			Calendar initDuedate = sbmcal.getDueDate(bcal);

			SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());
			System.out.println("expectedClosureDt ::::::::::::::::::::::::::: "+expectedClosureDt);

			dsValues.put("ExpectedCloseDate", expectedClosureDt);

			// Get the 1st Approval TAT
			long exptdApproval1Tat = approver1Tat * 1000;
			BizCalendar bcal1 = new BizCalendar(new Date().getTime(), exptdApproval1Tat);
			Calendar exApp1Dt = sbmcal.getDueDate(bcal1);
			String expectedAppr1Dt = Currdateformat1.format(exApp1Dt.getTime());

			// If 2nd Approver present, get the total TAT and set the date
			long exptdApprovalTat_2 = approver1Tat + approver2Tat;
			long exptdApproval2Tat = exptdApprovalTat_2 * 1000;
			BizCalendar bcal2 = new BizCalendar(new Date().getTime(), exptdApproval2Tat);
			Calendar exApp2Dt = sbmcal.getDueDate(bcal2);
			String expectedAppr2Dt = Currdateformat1.format(exApp2Dt.getTime());

			if (callType.equalsIgnoreCase("ISSUE")) {
				dsValues.put("ExpectedApproval_1_Dt", "NA");
				dsValues.put("ExpectedApproval_2_Dt", "NA");
				dsValues.put("ExpectedApprovalDate", "1947-01-01 00:00:00");
			} else {
				dsValues.put("ExpectedApproval_1_Dt", expectedAppr1Dt);

				if (approver2 == "<NULL>" || approver2 == "" || approver2 == null) {
					dsValues.put("ExpectedApproval_2_Dt", "NA");
					dsValues.put("skipVerifyApproval", true);
					dsValues.put("ExpectedApprovalDate", expectedAppr1Dt);
				} else {
					dsValues.put("ExpectedApproval_2_Dt", expectedAppr2Dt);
					dsValues.put("ExpectedApprovalDate", expectedAppr2Dt);
				}
			}

			dsValues.put("FinalCallTicketStatus", "Open"); 
			pi.updateSlotValue(dsValues);
			pi.save();
			blserver.disConnect(blsession);
		} catch (Exception e) {
			throw new RuntimeException("Error updating dataslot values for PiId " + "[" + piid + "]", e);
		}
	}
	
	public void reviseTAT(long process_instance_id) throws Exception {
		try {
			blserver = blc.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);

			ProcessInstance pi = blserver.getProcessInstance(blsession, process_instance_id);

			String callType = (String) pi.getDataSlotValue("CallType");
			String approver1 = (String) pi.getDataSlotValue("ApproverID");
			String approver2 = (String) pi.getDataSlotValue("ApproverUserID2");
			String issueRequestId = (String) pi.getDataSlotValue("IssueRequestId");
			String applicationType = (String) pi.getDataSlotValue("ApplicationType");
			String ticket = (String) pi.getDataSlotValue("TicketNo");
			String IssueReqSubTypeID = (String) pi.getDataSlotValue("IssueReqSubTypeID");

			setTAT(applicationType, issueRequestId, IssueReqSubTypeID, ticket);			
			
			long totalDuration = 0;
			long totalCallTAT = 0;

			HashMap dsValues = new HashMap();

			if (callType.equalsIgnoreCase("Request")) {
				if (approver1 != null) {
					if (!approver1.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = approver1Tat;
					}
				}
				if (approver2 != null) {
					if (!approver2.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = totalCallTAT + approver2Tat;
					}
				}
				totalCallTAT = totalCallTAT + supportTeamTat;
			} else if (callType.equalsIgnoreCase("ISSUE") || callType.equalsIgnoreCase("Other")) {
				totalCallTAT = supportTeamTat;
			} else {
				if (approver1 != null) {
					if (!approver1.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = approver1Tat;
					}
				}
				if (approver2 != null) {
					if (!approver2.equalsIgnoreCase("<NULL>")) {
						totalCallTAT = totalCallTAT + approver2Tat;
					}
				}
				totalCallTAT = totalCallTAT + supportTeamTat;
			}

			totalDuration = totalCallTAT * 1000;
			long startDate = (new Date()).getTime();
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(startDate, totalDuration);
			Calendar initDuedate = sbmcal.getDueDate(bcal);

			SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());

			dsValues.put("ExpectedCloseDate", expectedClosureDt);

			// If 2nd Approver present, get the total TAT and set the date
			long exptdApprovalTat_2 = approver1Tat + approver2Tat;
			long exptdApproval2Tat = exptdApprovalTat_2 * 1000;
			BizCalendar bcal2 = new BizCalendar(new Date().getTime(), exptdApproval2Tat);
			Calendar exApp2Dt = sbmcal.getDueDate(bcal2);
			String expectedAppr2Dt = Currdateformat1.format(exApp2Dt.getTime());

			if (!callType.equalsIgnoreCase("ISSUE")) {
				if (approver2 != null && !approver2.isEmpty() && Arrays.asList(pi.getCompletedWorkStepNames()).contains("VerifyApproval")) {
					dsValues.put("ExpectedApproval_2_Dt", expectedAppr2Dt);
					dsValues.put("ExpectedApprovalDate", expectedAppr2Dt);
				}
			}
			pi.updateSlotValue(dsValues);
			pi.save();
			blserver.disConnect(blsession);
		} catch (Exception e) {
			NLog.ws.debug("ERROR TECHDESK DSUPDATE the workstep name:::::::::::::::"+e.getMessage());
			throw new RuntimeException("Error updating dataslot values for PiId " + "[" + process_instance_id + "]", e);
		}
	}

}
