package com.agent.ejb.initiateagent;

import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.agent.ejb.mail.Mailer;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;

public class AgentFlowProcess {
	// static Logger log = null;
	// static
	// {
	// log = AgentLogger.getAgentLogger();
	// }

	private final String blUser = "parshu";
	private final String blPassword = "Ebms123#";
	private ResultSet rs = null;
	private Connection con = null;
	private CallableStatement proc_stmt = null;

	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	private static String dataSourceName = "jdbc/sqlDB";

	BLServer blServer = null;
	Session blSession = null;

	PreparedStatement pstmt = null;
	GetResource rsc = null;

	public Connection getDBConnection() {
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup(dataSourceName);
			con = dataSource.getConnection();
			return con;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void releaseResource(Connection conn, CallableStatement cstmt, ResultSet rset) {
		try {
			if (rset != null) {
				rset.close();
			}
			if (cstmt != null) {
				cstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Connection getConnectionToSQLDB() {
		try {
			System.out.println("Inside Try");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("before IP");
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println(" ip is : " + ip);
			String dbip = "";
			String dbuser = "";
			String dbpass = "";
			if (ip.contains("10.65.15.")) {
				System.out.println("Inside if !!");

				dbip = "rgiudb01.reliancegeneral.co.in";
				dbuser = "sa";
				dbpass = "sa123";
			} else {
				System.out.println("Inside else !!");
				dbip = "RGINPRORMSDB.reliancegeneral.com";
				dbuser = "savvionapp";
				dbpass = "sav$123";
			}
			con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
		} catch (Exception e) {
			// throw new AxisFault("Error in getting a DB connection" +
			// e.getMessage());
			System.out.println("Error in getting connection : " + e.getMessage() + " -- StackTrace :" + e.getStackTrace());
		}
		return con;
	}

	private void closeResources() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (proc_stmt != null) {
			try {
				proc_stmt.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * This method will use to get BM details. this will return bmId.
	 * 
	 * @param branchCode
	 * @return
	 */
	public String getBmDetail(String branchCode) {

		System.out.println("In getBmMethod!!!!");

		String bmId = "";

		try {
			if (branchCode != null && branchCode != "") {

				con = getConnectionToSQLDB();
				proc_stmt = con.prepareCall("{ call usp_GetBMDetails_AgentRTN(?,?,?) }");
				proc_stmt.setString(1, branchCode);
				proc_stmt.setString(2, null);
				proc_stmt.setString(3, null);

				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						bmId = rs.getString("EMPLOYEE");
					}
				}
				if (bmId != null || bmId != "") {
					bmId = bmId.split("-")[0].trim();

				}

			}
			closeResources();

		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Error in getBm Method : " + e.getMessage());
		}
		return bmId;

	}

	/**
	 * This method will return BM/CM ,RM/RH, ZH/VH mail id,Name,EmployeeID
	 */
	public String getPerformerDetail(String role, String branchCode, String smCode) {
		System.out.println("Inside getPerformerDetail() ");
		String performerID = "";
		String fetchedId = "";
		try {
			if (branchCode != null && branchCode != "") {

				con = getDBConnection();
				proc_stmt = con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
				proc_stmt.setString(1, role);
				proc_stmt.setString(2, branchCode);
				proc_stmt.setString(3, smCode);
				proc_stmt.setString(4, "0");

				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						fetchedId = rs.getString("FApproverID");
					}
				}
				if (fetchedId != null && fetchedId != "") {
					performerID = fetchedId.trim();
					CreateSavvionUser obj = new CreateSavvionUser();
					obj.checkUserExists(performerID);

				} else {
					performerID = getMandatoryInfo().split(",")[1].trim();
				}

			}
			releaseResource(con, proc_stmt, rs);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Error in getBm Method : " + e.getMessage());
		}

		return performerID;
	}

	/**
	 * This method will return BM/CM ,RM/RH, ZH/VH mail id,Name,EmployeeID
	 */
	public HashMap<String, String> getPerformerEscalationDetail(String performerId) {
		System.out.println("Inside getPerformerEscalationMatrix method !!!");
		HashMap<String, String> performerEscalationMatrix = new HashMap<String, String>();

		try {
			if (performerId != null && performerId != "") {

				con = getConnectionToSQLDB();
				proc_stmt = con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
				proc_stmt.setString(1, "");
				proc_stmt.setString(2, "");
				proc_stmt.setString(3, performerId);
				proc_stmt.setString(4, "1");

				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {

						performerEscalationMatrix.put("firstName", rs.getString("FApproverName"));
						performerEscalationMatrix.put("firstMailId", rs.getString("FApproverEmail"));

						performerEscalationMatrix.put("secondName", rs.getString("SApproverName"));
						performerEscalationMatrix.put("secondIdMailId", rs.getString("SApproverEmail"));
					}
				}

			}
			closeResources();

		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Error in getPerformerEscalation Method : " + e.getMessage());
		}

		return performerEscalationMatrix;
	}

	public static void main(String args[]) {
		AgentFlowProcess obj = new AgentFlowProcess();
		obj.getEmployeeDetails("asdasd");
	}

	/**
	 * this method will return Immediate Manager of given Empcode.
	 * 
	 * @param employeeCode
	 * @return
	 */
	public String getImmediateMgr(String employeeCode) {

		System.out.println("In getImmediateMgr!!!!");

		// log.debug("This is debug : " + branchCode);

		String mgrId = "";

		try {
			if (employeeCode != null && employeeCode != "") {

				con = getDBConnection();
				proc_stmt = con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
				proc_stmt.setString(1, "");
				proc_stmt.setString(2, "");
				proc_stmt.setString(3, employeeCode);
				proc_stmt.setString(4, "1");

				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						mgrId = rs.getString("FApproverID");
					}

				}
				closeResources();

			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Error in getImmediateMgr Method !!! : " + e.getMessage());
		}
		return mgrId;

	}

	/**
	 * This method will return name & Email id of Employee
	 */
	public String getEmployeeDetails(String employeeCode) {
		String mailID = null;
		System.out.println("Employee ID is :" + employeeCode);
		try {
			if (employeeCode != null && employeeCode != "") {
				System.out.println("Employee ID is :" + employeeCode);
				con = getDBConnection();
				System.out.println("connection is " + con);
				proc_stmt = con.prepareCall("{ call usp_GetEmployeeDetails(?) }");
				proc_stmt.setString(1, employeeCode);

				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						mailID = rs.getString("Employee_Name") + ",";
						mailID += rs.getString("EMail");
					}

				}
			}

			closeResources();
		} catch (Exception e) {
			System.out.println("Error in Getting Mail ID Frm SQL Server" + e.getMessage());

			throw new RuntimeException("Error In getMailID Method " + e.getMessage());

		} finally {
			releaseResource(con, proc_stmt, rs);
		}
		return mailID.trim();

	}

	public String getMandatoryInfo() {
		String info = "";
		try {
			String query = " select DEFAULT_CC_MAIL_ID, DEFAULT_PERFORMER_ID from AGENT_RETENTION_MANDATORY_INFO ";
			Mailer obj = new Mailer();
			con = obj.getJDBCConnection();
			pstmt = con.prepareCall(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				info = rs.getString("DEFAULT_CC_MAIL_ID") + ",";
				info = info + rs.getString("DEFAULT_PERFORMER_ID");
			}
		} catch (Exception e) {
			System.out.println("Error while getting Mandatory Information" + e.getMessage());
		} finally {
			// GetResource.releaseResource(con, pstmt);
			try {
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				System.out.println("Error while closing the connetion -- method name = getMandatoryInfo() --" + e.getMessage());
			}
		}

		return info.trim();
	}

	/**
	 * This method will add agent's all details in a custom table.
	 * 
	 * @param piID
	 */
	public void addAgentDetail(long piID) {
		System.out.println("in addAgentDetail method : ");
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(blUser, blPassword);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
			// rsc = new GetResource();
			String query = "INSERT INTO AGENTRETENTIONMODEL_DETAIL " + "( PROCESS_INSTANCE_ID, SM_STATUS, VINTAGE_MONTH, LICENCE_TYPE, TIME_LAST_POL, BUSINESS_CATEGORY, ATTRITION_RISK, " + "CLAIM_TAT_CATEGORY, SALES_CATEGORY, BUSINESS_LAST_12MONTH, SM_LEVEL_VINTAGE, FRESH_PER, POLICY_GAP, CLAIM_PER_CATEGORY, " + "PAYOUT_TAT_CATEGORY, AGENT_CODE, ATTRITION_PROBABILITY, BRANCH_CODE, BRANCH_NAME, BM, START_DATE, STATUS, ROLE, SM_NAME, SM_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con = GetResource.getDBConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, piID);
			pstmt.setString(2, (String) pi.getDataSlotValue("sm_status"));
			pstmt.setString(3, pi.getDataSlotValue("vintage_month").toString());
			pstmt.setString(4, (String) pi.getDataSlotValue("licence_type"));
			pstmt.setString(5, pi.getDataSlotValue("time_last_pol").toString());
			pstmt.setString(6, (String) pi.getDataSlotValue("business_category"));
			pstmt.setString(7, (String) pi.getDataSlotValue("attrition_risk"));
			pstmt.setString(8, (String) pi.getDataSlotValue("claim_TAT_category"));
			pstmt.setString(9, (String) pi.getDataSlotValue("sales_category"));
			pstmt.setString(10, pi.getDataSlotValue("business_last_12month").toString());
			pstmt.setString(11, pi.getDataSlotValue("sm_level_vintage").toString());
			pstmt.setString(12, pi.getDataSlotValue("fresh_per").toString());
			pstmt.setString(13, pi.getDataSlotValue("policy_gap").toString());
			pstmt.setString(14, (String) pi.getDataSlotValue("claim_per_category"));
			pstmt.setString(15, (String) pi.getDataSlotValue("payout_TAT_category"));
			pstmt.setString(16, (String) pi.getDataSlotValue("agent_code"));
			pstmt.setString(17, pi.getDataSlotValue("attrition_probability").toString());
			pstmt.setString(18, (String) pi.getDataSlotValue("branch_code"));
			pstmt.setString(19, (String) pi.getDataSlotValue("branch_name"));
			pstmt.setString(20, (String) pi.getDataSlotValue("bm"));
			pstmt.setTimestamp(21, new java.sql.Timestamp(new java.util.Date().getTime()));
			pstmt.setString(22, "ACTIVATED");
			pstmt.setString(23, (String) pi.getDataSlotValue("role"));
			pstmt.setString(24, (String) pi.getDataSlotValue("sm_name"));
			pstmt.setString(25, (String) pi.getDataSlotValue("sm_code"));
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in adding Agent Detail : " + e.getMessage());
		} finally {
			try {
				if (blSession != null) {
					blServer.disConnect(blSession);
				}
			} catch (Exception e) {
				throw new RuntimeException("BLSession closing error : " + e.getMessage());
			}
			GetResource.releaseResource(con, pstmt);
		}
	}

}
