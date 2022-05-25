package com.rel.db;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtility {
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	//private static String dataSourceName = "jdbc/SBMCommonDB";
	private static String dataSourceName = "jdbc/CustomDB";
	private static Connection connection = null;

	public static Connection getDBConnection() {
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup(dataSourceName);
			connection = dataSource.getConnection();
			return connection;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void releaseResources(Connection conn, PreparedStatement pstmt,
			ResultSet rset) {
		try {
			if (rset != null) {
				rset.close();
			}
			if (pstmt != null) {
				pstmt.close();
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
