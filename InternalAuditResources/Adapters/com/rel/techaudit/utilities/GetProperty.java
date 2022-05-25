package com.rel.techaudit.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.rel.db.DBUtility;

public class GetProperty {

	public String getRiskRateValue() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(RISK_RATE) FROM INTERNALAUDIT_RISKRATE ORDER BY RISK_RATE";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			StringBuilder nameBuilder = new StringBuilder();
			while (rset.next()) {
				nameBuilder.append(rset.getString(1)).append(",");
			}
			nameBuilder.deleteCharAt(nameBuilder.length() - 1);
			System.out.println("risk rate : "+nameBuilder);
			return nameBuilder.toString().trim();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getDepartmentValues() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(DEPARTMENT) FROM INTERNALAUDIT_DEPARTMENT ORDER BY CASE WHEN DEPARTMENT = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> departmentList = new ArrayList<String>();
			while (rset.next()) {
				departmentList.add(rset.getString(1));
			}
			return departmentList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getClassValues() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(CLASSES) FROM INTERNALAUDIT_CLASSES ORDER BY CASE WHEN CLASSES = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> classesList = new ArrayList<String>();
			while (rset.next()) {
				classesList.add(rset.getString(1));
			}
			return classesList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getOD_Legal_ClassValues() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(CLASSES) FROM INTERNALAUDIT_CLASS_OD_LEGAL ORDER BY CASE WHEN CLASSES = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> classesList = new ArrayList<String>();
			while (rset.next()) {
				classesList.add(rset.getString(1));
			}
			return classesList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getClaimCategory() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(CLAIM_CATEGORY) FROM INTERNALAUDIT_CLAIMCATEGORY ORDER BY CASE WHEN CLAIM_CATEGORY = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> claimCtgyList = new ArrayList<String>();
			while (rset.next()) {
				claimCtgyList.add(rset.getString(1));
			}
			return claimCtgyList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getOD_ClaimStatus() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(CLAIM_STATUS) FROM INTERNALAUDIT_OD_CLAIMSTATUS ORDER BY CASE WHEN CLAIM_STATUS = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> claimStatusList = new ArrayList<String>();
			while (rset.next()) {
				claimStatusList.add(rset.getString(1));
			}
			return claimStatusList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getSettlementBasis() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(SETTLEMENT_BASIS) FROM INTERNALAUDIT_SETTLEMENT_BASIS ORDER BY CASE WHEN SETTLEMENT_BASIS = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> settlementBsisList = new ArrayList<String>();
			while (rset.next()) {
				settlementBsisList.add(rset.getString(1));
			}
			return settlementBsisList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getCourtTypList() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT COURT_TYPE FROM INTERNALAUDIT_COURT_TYPE ORDER BY COURT_TYPE";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> courtTypList = new ArrayList<String>();
			while (rset.next()) {
				courtTypList.add(rset.getString(1));
			}
			return courtTypList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getNatureLossList() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(NATURE_LOSS) FROM INTERNALAUDIT_NATURE_LOSS ORDER BY CASE WHEN NATURE_LOSS = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> natureLossList = new ArrayList<String>();
			while (rset.next()) {
				natureLossList.add(rset.getString(1));
			}
			return natureLossList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public ArrayList<String> getLegal_ClaimStatus() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = DBUtility.getDBConnection();
			String query = "SELECT INITCAP(CLAIM_STATUS) FROM INTERNALAUDIT_LEGAL_CLAIMSTATS ORDER BY CASE WHEN CLAIM_STATUS = 'ALL' THEN 1 ELSE 2 END";
			pstmt = connection.prepareStatement(query);
			rset = pstmt.executeQuery();
			ArrayList<String> legalClaimStatusList = new ArrayList<String>();
			while (rset.next()) {
				legalClaimStatusList.add(rset.getString(1));
			}
			return legalClaimStatusList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}
}
