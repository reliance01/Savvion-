package com.agent.portal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtility {
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	//private static String dataSourceName = "jdbc/SBMCommonDB";
	private static String dataSourceName = "jdbc/CustomDB";
	private static Connection connection = null;

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

}
