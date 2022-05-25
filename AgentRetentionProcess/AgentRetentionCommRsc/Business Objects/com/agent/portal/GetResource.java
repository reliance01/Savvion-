package com.agent.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GetResource {

	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";

	public Connection getDBConnection() {
		try {
			return ((DataSource) new InitialContext().lookup(TREE_DATASOURCE))
					.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while getting DataSource Connection", e);
		}
	}

	public void releaseResource(Connection conn, Statement pstmt,
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
	
	public void releaseResource(Connection conn, PreparedStatement pstmt) {
		try {
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

}
