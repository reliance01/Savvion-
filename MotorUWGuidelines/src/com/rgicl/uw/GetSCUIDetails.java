package com.rgicl.uw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * GetSCUIDetails.java Class gets the details of a branch which further decides
 * the serviceCenter on the basis of Branch code.
 * 
 * @author RajatP
 * @param branchID
 * 
 */

public class GetSCUIDetails {
	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
	private DataSource ds = null;
	private String branchID;
	private String serviceCenterGrp;
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sqlString = "SELECT BRANCH_TYPE FROM MOTORUW_BRANCH_REGION_MAP WHERE BRANCH_ID = ?";

	public void execute() {
		try {
			ds = (DataSource) new InitialContext().lookup(TREE_DATASOURCE);
			connection = ds.getConnection();
			pstmt = connection.prepareStatement(sqlString);
			pstmt.setString(1, branchID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				serviceCenterGrp = rs.getString("BRANCH_TYPE");
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while getting MotorUW GetSCUIDetails", e);
		} finally {

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
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqe) {
			}
		}
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getBranchID() {
		return this.branchID;
	}

	public String getServiceCenterGrp() {
		return this.serviceCenterGrp;
	}
}
