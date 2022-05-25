package com.rgicl.marcom.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtility {
	//static String jndiName = "jdbc/SBMCommonDBXA";
	static String jndiName = "jdbc/CustomDB";
	public static Connection getDBConnection() {
		try {
			return ((DataSource) new InitialContext().lookup(jndiName))
					.getConnection();
		} catch (Exception ex) {
			System.out.println("Error Getting DBConnection : "
					+ ex.getMessage());
			throw new RuntimeException();
		}
	}

	public static void releaseResouce(ResultSet rset, PreparedStatement pstmt,
			Connection conn) {
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
			System.out.println("Error while ReleaseResouce : "
					+ ex.getMessage());
		}
	}

	public static void releaseResouce(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("Error while ReleaseResouce : "
					+ ex.getMessage());
		}
	}
}
