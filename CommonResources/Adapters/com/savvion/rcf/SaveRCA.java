package com.savvion.rcf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SaveRCA {

	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	//private static String dataSourceName = "jdbc/SBMCommonDB";
	private static String dataSourceName = "jdbc/calldeskDB";
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
	
    public void setCalldeskRCA(String ticketNo, String rcaType, String rcaMember,
			String rcaDate, String rcaDetails, String createdBy)
    {
    	try {
			
    		connection = getDBConnection();
			proc_stmt = connection
					.prepareCall("{ call USP_InsertRCA(?,?,?,?,?,?) }");
			proc_stmt.setString(1, ticketNo.trim());
			proc_stmt.setString(2, rcaType.trim());
			proc_stmt.setString(3, rcaMember.trim());
			proc_stmt.setString(4, rcaDate.trim());
			proc_stmt.setString(5, rcaDetails.trim());
			proc_stmt.setString(6, createdBy);

			proc_stmt.execute();
			
         System.out.println("inside setCalldeskRCA Method");
		
		

	} catch (Exception e) {

		System.out.println(e.getMessage());
		throw new RuntimeException("Error in getBm Method : "
				+ e.getMessage());
	}finally {
		if (proc_stmt != null) {
			try {
				proc_stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
    	
    	
    }

	public String saveRCA(String ticketNo, String rcaType, String rcaMember,
			String rcaDate, String rcaDetails, String createdBy) {

		try {

			CalldeskDBResource obj = new CalldeskDBResource();
			connection = obj.getConnection();
			System.out.println("connection  -" + connection);
			String sql = "INSERT INTO TECHDESK_RCA VALUES (?,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(sql);
			System.out.println("after pstmt  -");
			pstmt.setString(1, ticketNo.trim());
			System.out.println("1");
			pstmt.setString(2, rcaType.trim());
			System.out.println("2");
			pstmt.setString(3, rcaMember.trim());
			System.out.println("3");
			pstmt.setDate(4, java.sql.Date.valueOf(rcaDate));
			System.out.println("4");
			pstmt.setString(5, rcaDetails.trim());
			System.out.println("5");
			pstmt.setString(6, createdBy.trim());
			System.out.println("6");
			pstmt.setTimestamp(7,
					new java.sql.Timestamp(new java.util.Date().getTime()));
			System.out.println("7");
			System.out.println("before Execute update");
			pstmt.executeUpdate();
			System.out.println("After Execute update");
			
			setCalldeskRCA(ticketNo,rcaType,rcaMember,rcaDate,rcaDetails,createdBy);

		} catch (Exception e) {
			throw new RuntimeException("Error while submitBMGrid : "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return "Saved";
	}

	
	
}
