package com.agent.portal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SqlUtility {
	
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	//private static String dataSourceName = "jdbc/SBMCommonDB";
	private static String dataSourceName = "jdbc/sqlDB";
	private static Connection connection = null;
	

	private ResultSet rs = null;

	private CallableStatement proc_stmt = null;

	public Connection getDBConnection() {
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup(dataSourceName);
			connection = dataSource.getConnection();
			return connection;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public  void releaseResource(Connection conn, CallableStatement cstmt,
			ResultSet rset) {
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

	public static void disConnect(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
			}
		}
	}

	/**
	 *  This method will return name & Email id of Employee
	*/
	public String getEmployeeDetails(String employeeCode)
	{
		String mailID = null;
		System.out.println("Employee ID is :" + employeeCode);
		try{
			if(employeeCode != null && employeeCode != "")
			{
			System.out.println("Employee ID is :" + employeeCode);
			connection = getDBConnection();
			System.out.println("connection is " + connection);
			proc_stmt = connection.prepareCall("{ call usp_GetEmployeeDetails(?) }");
			proc_stmt.setString(1, employeeCode);
			
			rs = proc_stmt.executeQuery();
			if(rs != null)
			{
				while(rs.next())
				{
					mailID = rs.getString("Employee_Name")+",";
					mailID += rs.getString("EMail");
				}
				
			}
			}
			
			disConnect(connection);
		}catch(Exception e)
		{
			System.out.println("Error in Getting Mail ID Frm SQL Server" + e.getMessage());
			
			throw new RuntimeException("Error In getMailID Method " + e.getMessage());
		}
		return mailID.trim();
		
	}
	
	public HashMap<String, String> getPerformerEscalationDetail(String performerId) {
		System.out.println("Inside getPerformerEscalationMatrix method !!!");
		HashMap<String, String> performerEscalationMatrix = new HashMap<String, String>();
		
		try {
			if (performerId != null && performerId != "") {

				connection = getDBConnection();
				proc_stmt = connection
						.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
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
			disConnect(connection);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Error in getPerformerEscalation Method : "
					+ e.getMessage());
		}

		return performerEscalationMatrix;
	}
	
}
