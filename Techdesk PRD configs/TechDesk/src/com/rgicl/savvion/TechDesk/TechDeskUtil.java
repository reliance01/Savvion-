package com.rgicl.savvion.TechDesk;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import com.tdiinc.userManager.JDBCGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;

public class TechDeskUtil {
	private static final String DATASOURCE = "jdbc/SBMCommonDB";

	public Calendar getAppSupportDueDate() {
		Calendar duedate = null;
		try {
			Date dt = new Date();
			long l = dt.getTime() + 24 * 60 * 60 * 1000;
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(dt.getTime(), l);
			duedate = sbmcal.getDueDate(bcal);
			return duedate;
		} catch (Throwable th) {
			System.out.println("Error while setting AppSupport duedate \n" + th.getMessage());
		}
		return duedate;
	}

	private Connection getConnectionToSQLDB() throws Exception {
		String dbip = "";
		String dbuser = "";
		String dbpass = "";
		Connection connection = null;
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (ip.contains("10.65.15.")) {
				// Changed by Sunil Jangir on 14-Sep-2020
				dbip = "rgiudevlopment.reliancecapital.com";
				// dbip = "10.65.15.82";
				dbuser = "savvionapp";
				dbpass = "sav$123";
			} else {
				dbip = "RGIRMSCDDB";
				dbuser = "savvionapp";
				dbpass = "sav$123";
			}
			connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
		} catch (Exception e) {
			throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + e.getMessage(), e);
		}
		return connection;
	}

	public String[] getEmployeeContanctDtl(String userID) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String qry = "select First_Name,isnull(Last_name,''),Contact_No from Employee_Master where employee_code = ? and Employee_ARC = 0";
		String[] userDtl = null;
		try {
			if (userID != null && userID.trim().length() > 0) {
				userDtl = new String[3];
				connection = getConnectionToSQLDB();
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, userID);
				rset = pstmt.executeQuery();
				while (rset.next()) {
					userDtl[0] = rset.getString(1);
					userDtl[1] = rset.getString(2);
					userDtl[2] = rset.getString(3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqe) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException sqe) {
			}
			try {
				if (rset != null) {
					rset.close();
				}
			} catch (SQLException sqe) {
			}

		}
		return userDtl;
	}

	public String getGrpMembers(String grpName) {
		String members = "";
		try {
			if (grpName != null && grpName.trim().length() > 0) {
				if (grpName.trim().equalsIgnoreCase("CDITGrp")) {
					members = grpName.trim();
				} else {
					UserManager um = new UserManager();
					JDBCRealm r = (JDBCRealm) um.getDefaultRealm();
					JDBCGroup g = (JDBCGroup) r.getGroup(grpName.trim());
					String users[] = g.getAllUserMemberNames();
					if (users != null && users.length > 0) {
						for (int i = 0; i < users.length; i++) {
							JDBCUser u = (JDBCUser) r.getUser(users[i]);
							members = members + u.userName + "-" + u.getAttribute("FIRSTNAME") + " " + u.getAttribute("LASTNAME") + ",";
						}
					}
					if (members.trim().length() > 0) {
						members = members.substring(0, members.length() - 1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("error in getting Grp members : " + e.getMessage());
		}
		return members;
	}

	// Generating the agent data and returning the Map
	public Map<String, Object> getAgentData(String agentCode, String callStatus, String startDate, String endDate) {

		boolean agentCodeExists = true;
		boolean callStatusExists = true;

		Map<String, Object> agentDataResponse = new HashMap<String, Object>();
		List<Map<String, String>> records = new ArrayList<Map<String, String>>();
		Map<String, String> record = new HashMap<String, String>();

		agentDataResponse.put("success", false);
		agentDataResponse.put("data", records);

		// Database query to run
		String sqlQuery = "";

		// Setting the flags
		if (agentCode == null || agentCode.equals("")) {
			agentCodeExists = false;
		}
		if (callStatus == null || callStatus.equals("")) {
			callStatusExists = false;
		}

		// If no startDate and endDate then simply break this method and return
		// the message
		if (startDate.equals("") || startDate == null || endDate.equals("") || endDate == null) {
			return agentDataResponse;
		}

		// Adding the time to the dates
		startDate += " 00:00:01";
		endDate += " 23:59:59";

		// Making the Query
		if (agentCodeExists) {
			if (callStatusExists) {
				sqlQuery = "select TICKETNO,CALLLOGDATE,CALLCREATEDBY,CALLTYPE,APPROVERID,APPROVALFINALSTATUS,APPSUPPORTSTATUS from TECHDESK where TICKETNO like 'R%' and PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from PROCESSINSTANCE where START_TIME between to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss') and to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss')) and CALLCREATEDBY = ? and APPSUPPORTSTATUS = ?";
			} else {
				sqlQuery = "select TICKETNO,CALLLOGDATE,CALLCREATEDBY,CALLTYPE,APPROVERID,APPROVALFINALSTATUS,APPSUPPORTSTATUS from TECHDESK where TICKETNO like 'R%' and PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from PROCESSINSTANCE where START_TIME between to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss') and to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss')) and CALLCREATEDBY = ?";
			}
		} else {
			if (callStatusExists) {
				sqlQuery = "select TICKETNO,CALLLOGDATE,CALLCREATEDBY,CALLTYPE,APPROVERID,APPROVALFINALSTATUS,APPSUPPORTSTATUS from TECHDESK where TICKETNO like 'R%' and PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from PROCESSINSTANCE where START_TIME between to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss') and to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss')) and APPSUPPORTSTATUS = ?";
			} else {
				sqlQuery = "select TICKETNO,CALLLOGDATE,CALLCREATEDBY,CALLTYPE,APPROVERID,APPROVALFINALSTATUS,APPSUPPORTSTATUS from TECHDESK where TICKETNO like 'R%' and PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from PROCESSINSTANCE where START_TIME between to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss') and to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss'))";
			}
		}

		Context initialContext = null;
		DataSource dataSource = null;
		Connection databaseConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup(DATASOURCE);
			databaseConnection = dataSource.getConnection();
			preparedStatement = databaseConnection.prepareStatement(sqlQuery);

			// Preparing the Query
			if (agentCodeExists) {
				if (callStatusExists) {
					preparedStatement.setString(1, startDate);
					preparedStatement.setString(2, endDate);
					preparedStatement.setString(3, agentCode);
					preparedStatement.setString(4, callStatus);
				} else {
					preparedStatement.setString(1, startDate);
					preparedStatement.setString(2, endDate);
					preparedStatement.setString(3, agentCode);
				}
			} else {
				if (callStatusExists) {
					preparedStatement.setString(1, startDate);
					preparedStatement.setString(2, endDate);
					preparedStatement.setString(4, callStatus);
				} else {
					preparedStatement.setString(1, startDate);
					preparedStatement.setString(2, endDate);
				}
			}

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				record = new HashMap<String, String>();

				record.put("ticketNumber", resultSet.getString(1));
				record.put("callLogDate", resultSet.getString(2));
				record.put("callCreatedBy", resultSet.getString(3));
				record.put("callType", resultSet.getString(4));
				record.put("approverID", resultSet.getString(5));
				record.put("approvalFinalStatus", resultSet.getString(6));
				record.put("appSupportStatus", resultSet.getString(7));

				records.add(record);
			}

			agentDataResponse = (Map<String, Object>) new HashMap<String, Object>();
			agentDataResponse.put("success", true);
			agentDataResponse.put("data", records);
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException(e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (databaseConnection != null) {
					databaseConnection.close();
				}
			} catch (Exception e) {
				e.getMessage();
				throw new RuntimeException(e);
			}
		}

		return agentDataResponse;
	}
}
