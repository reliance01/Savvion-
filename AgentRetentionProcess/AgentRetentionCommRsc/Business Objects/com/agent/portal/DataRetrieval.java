package com.agent.portal;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.savvion.sbm.bizsolo.util.SBMConf;

public class DataRetrieval {

	public LinkedHashMap<String, String> getClaim() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> claimsMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call GETAGENTCLAIM}");
			while (rset.next()) {
				claimsMap.put(rset.getString(1), rset.getString(2));
			}
			return claimsMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting Claims : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public LinkedHashMap<String, String> getUW() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> uwMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call GETAGENTUW}");
			while (rset.next()) {
				uwMap.put(rset.getString(1), rset.getString(2));
			}
			return uwMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting UW : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public LinkedHashMap<String, String> getOps() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> opsMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call GETAGENTOPS}");
			while (rset.next()) {
				opsMap.put(rset.getString(1), rset.getString(2));
			}
			return opsMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting Ops : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public LinkedHashMap<String, String> getIT() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> itMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call GETAGENTIT}");
			while (rset.next()) {
				itMap.put(rset.getString(1), rset.getString(2));
			}
			return itMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting IT : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public LinkedHashMap<String, String> getAccounts() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> accountsMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call getAgentAccounts}");
			while (rset.next()) {
				accountsMap.put(rset.getString(1), rset.getString(2));
			}
			return accountsMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting Accounts : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public LinkedHashMap<String, String> getStrategy() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rset = null;
		LinkedHashMap<String, String> strategyMap = new LinkedHashMap<String, String>();
		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			stmt = connection.createStatement();
			rset = stmt.executeQuery("{call getAgentStrategy}");
			while (rset.next()) {
				strategyMap.put(rset.getString(1), rset.getString(2));
			}
			return strategyMap;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting Strategy : "
					+ e.getMessage());
		} finally {
			obj.releaseResource(connection, stmt, rset);
		}
	}

	public ArrayList<String> getClaimSpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select spocs from tbl_agentclaim_spocs where claim_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while getting Claim Spoc : "
						+ e.getCause() + " : " + e.getMessage() + " : "
						+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	public ArrayList<String> getUWSpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select uw_spoc from tbl_agentuw_spoc where uw_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while getting UW Spoc : "
						+ e.getCause() + " : " + e.getMessage() + " : "
						+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	public ArrayList<String> getOpsSpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select ops_spoc from tbl_agentops_spocs where ops_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while getting Ops Spoc : "
						+ e.getCause() + " : " + e.getMessage() + " : "
						+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	public ArrayList<String> getITSpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select it_spoc from tbl_agentit_spoc where it_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while getting IT Spoc : "
						+ e.getCause() + " : " + e.getMessage() + " : "
						+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	public ArrayList<String> getAccountsSpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select account_spoc from tbl_agentaccounts_spoc where account_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException(
						"Error while getting Account Spoc : " + e.getCause()
								+ " : " + e.getMessage() + " : "
								+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	public ArrayList<String> getStrategySpocs(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;
		if (id != null && id.trim().length() > 0) {
			GetResource obj = new GetResource();
			spocList = new ArrayList<String>();
			try {
				connection = obj.getDBConnection();
				String qry = "select strategy_spoc from tbl_agentstrategy_spoc where strategy_id in "
						+ "("
						+ "select to_number(regexp_substr("
						+ "?,'[^,]+', 1, level)) from dual "
						+ "connect by regexp_substr("
						+ "?, '[^,]+', 1, level) is not null)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, id.trim());
				pstmt.setString(2, id.trim());
				rset = pstmt.executeQuery();
				while (rset.next()) {
					spocList.add(rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException(
						"Error while getting Strategy Spoc : " + e.getCause()
								+ " : " + e.getMessage() + " : "
								+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return spocList;
	}

	/**
	 * This method is use to get BM remarks for Particular SPOC.
	 * 
	 * @param piid
	 * @param spocId
	 * @return
	 */

	public String getBmRemarks(String piID, String spocId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String bmRemark = null;
		if (spocId != null && spocId.trim().length() > 0) {
			GetResource obj = new GetResource();

			try {
				connection = obj.getDBConnection();
				String qry = " select remarks||'*123#'||reassignby from agentbmgrid where process_instance_id = ? "
						+ " and spoc like ? and spoc_remark='Pending' ";

				pstmt = connection.prepareStatement(qry);
				pstmt.setLong(1, Long.parseLong(piID.trim()));
				pstmt.setString(2, "%" + spocId.trim() + "%");
				rset = pstmt.executeQuery();
				while (rset.next()) {
					bmRemark = (rset.getString(1));
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while getting BM Remarks : "
						+ e.getCause() + " : " + e.getMessage() + " : "
						+ e.getStackTrace());
			} finally {
				obj.releaseResource(connection, pstmt, rset);
			}
		}
		return bmRemark;

	}

	/**
	 * This method will provide SPOC remarks. so that BM can see.
	 * 
	 * @param piID
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getSpocResolution(String piID) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		HashMap<String, String> map;
		ArrayList<HashMap<String, String>> spocResolutionList = null;

		GetResource obj = new GetResource();
		spocResolutionList = new ArrayList<HashMap<String, String>>();
		try {

			connection = obj.getDBConnection();
			String qry = " select CATEGORY, SUB_CATEGORY, SPOC, REMARKS,SPOC_REMARK,REASSIGNBY,SPOC_SUBMIT_DATE,BM_SUBMIT_DATE,STATUS  "
					+ " from agentbmgrid where process_instance_id = ? order by BM_SUBMIT_DATE";
			pstmt = connection.prepareStatement(qry);
			pstmt.setLong(1, Long.parseLong(piID));

			rset = pstmt.executeQuery();
			while (rset.next()) {
				map = new HashMap<String, String>();
				map.put("CATEGORY", rset.getString(1));
				map.put("SUB_CATEGORY", rset.getString(2));
				map.put("SPOC", rset.getString(3));
				map.put("REMARKS", rset.getString(4));
				map.put("SPOC_REMARK", rset.getString(5));
				map.put("REASSIGNBY", rset.getString(6));
				String spocDate = "";
				if (rset.getDate(7) != null) {
					spocDate = df.format(rset.getDate(7));
				} else {
					spocDate = "N/A";
				}
				map.put("SPOC_SUBMIT_DATE", spocDate);
				String bmDate = df.format(rset.getDate(8));
				map.put("BM_SUBMIT_DATE", bmDate);
				map.put("STATUS", rset.getString(9));
				spocResolutionList.add(map);

			}
		} catch (Exception e) {
			throw new RuntimeException("Error while getting SPOC Resolution : "
					+ e.getCause() + " : " + e.getMessage() + " : "
					+ e.getStackTrace());
		} finally {
			obj.releaseResource(connection, pstmt, rset);
		}

		return spocResolutionList;
	}

	/**
	 * This method will return all assigned SPOC list
	 */
	public ArrayList<String> getAssignedSpoc(String piID) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> spocList = null;

		GetResource obj = new GetResource();
		try {
			connection = obj.getDBConnection();
			spocList = new ArrayList<String>();
			String query = " select spoc from agentbmgrid where spoc_remark = 'Pending' and process_instance_id = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, Long.parseLong(piID.trim()));
			rset = pstmt.executeQuery();
			while (rset.next()) {
				spocList.add(rset.getString(1));
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while getting Strategy Spoc : "
					+ e.getCause() + " : " + e.getMessage() + " : "
					+ e.getStackTrace());
		} finally {
			obj.releaseResource(connection, pstmt, rset);
		}
		return spocList;
	}

	/**
	 * This method will get BM and Immediate Manager Remarks
	 */
	public ArrayList<HashMap<String, String>> getRemarks(String piID) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		HashMap<String, String> map;
		ArrayList<HashMap<String, String>> remarkList = null;

		GetResource obj = new GetResource();

		remarkList = new ArrayList<HashMap<String, String>>();
		try {
			connection = obj.getDBConnection();
			String query = "Select * from agent_immediatemanagerremark where process_instance_id = ? order by bm_submit_date";
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, Long.parseLong(piID));

			rs = pstmt.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("PROCESS_INSTANCE_ID", rs.getString(1));
				map.put("BM_REMARKS", rs.getString(2));
				map.put("IMMEDIATEMANAGER_REMARKS", rs.getString(3));
				String immediateManagerDate;
				if (rs.getDate(4) != null) {
					immediateManagerDate = df.format(rs.getDate(4));
				} else {
					immediateManagerDate = "N/A";
				}
				map.put("IM_SUBMIT_DATE", immediateManagerDate);
				map.put("BM_SUBMIT_DATE", df.format(rs.getDate(5)));
				remarkList.add(map);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while getting BM Remarks : "
					+ e.getCause() + " : " + e.getMessage() + " : "
					+ e.getStackTrace());

		} finally {
			obj.releaseResource(connection, pstmt, rs);
		}
		return remarkList;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return HashMap of all Report data
	 * @throws Exception
	 *             This method will use to get report data(Start to End)
	 */

	public HashMap getReportData(String startDate, String endDate, String status)
			throws Exception {
		Connection connection = null;
		CallableStatement cstmt = null;
		HashMap hm = new HashMap();
		ArrayList<ArrayList<Object>> callData = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> immediateMgrData = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> spocData = new ArrayList<ArrayList<Object>>();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultSet rset = null;
		DBUtility obj = new DBUtility();
		try {
			String query = "{call AGENT_OPEN_CLOSE_DTL(?,?,?,?,?,?)}";
			
			connection = obj.getDBConnection();

			cstmt = connection.prepareCall(query);
			
			cstmt.setString(1, startDate.trim());
			cstmt.setString(2, endDate.trim());

			cstmt.setString(3, status.trim());
			
			cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			cstmt.registerOutParameter(5, OracleTypes.CURSOR);
			cstmt.registerOutParameter(6, OracleTypes.CURSOR);

			cstmt.executeQuery();

			rset = (ResultSet) cstmt.getObject(4);

			if (rset != null) {

				while (rset.next()) {
					ArrayList<Object> callDetailList = new ArrayList<Object>();
					callDetailList.add(rset.getString(1));
					callDetailList.add(rset.getString(2));
					callDetailList.add(rset.getString(3));
					callDetailList.add(rset.getString(4));
					callDetailList.add(rset.getString(5));
					callDetailList.add(rset.getString(6));
					callDetailList.add(df.format(rset.getDate(7)));

					String stopdate = rset.getString(8);

					if (stopdate != null) {

						callDetailList.add(df.format(rset.getDate(8)));
					} else {
						callDetailList.add("-");
					}
					callDetailList.add(rset.getString(9));
					callDetailList.add(rset.getString(10));

					if (status.equals("CLOSE")) {
						String d1 = df1.format(rset.getDate(7));
						String d2 = df1.format(rset.getDate(8));
						java.util.Date dd1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d1);
						java.util.Date dd2 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d2);
						String TAT = Integer.toString(getWorkingDaysBetweenTwoDates(dd1,
								dd2));
						callDetailList.add(TAT);
					} else {
						callDetailList.add("-");
					}
					callData.add(callDetailList);
				}
			}

			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException sqe) {
					throw new RuntimeException((new StringBuilder(
							"Resultset not Closed..."))
							.append(sqe.getMessage()).toString());
				}
			}

			rset = (ResultSet) cstmt.getObject(5);
			if (rset != null) {
				while (rset.next()) {
					ArrayList<Object> immediateMgrSolution = new ArrayList<Object>();
					immediateMgrSolution.add(rset.getString(1));
					immediateMgrSolution.add(rset.getString(2));
					String date = rset.getString(3);
					if (date != null) {
						immediateMgrSolution.add(df.format(rset.getDate(3)));
					} else {
						immediateMgrSolution.add("-");
					}
					immediateMgrSolution.add(rset.getString(4));
					String date1 = rset.getString(5);
					if (date1 != null) {
						immediateMgrSolution.add(df.format(rset.getDate(5)));
					} else {
						immediateMgrSolution.add("-");
					}

					if (status.equals("CLOSE")) {
						String d1 = df1.format(rset.getDate(3));
						String d2 = df1.format(rset.getDate(5));
						java.util.Date dd1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d1);
						java.util.Date dd2 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d2);
						String TAT = Integer.toString(getWorkingDaysBetweenTwoDates(dd1,
								dd2));
						immediateMgrSolution.add(TAT);
					} else {
						immediateMgrSolution.add("-");
					}

					immediateMgrData.add(immediateMgrSolution);
				}
			}
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException sqe) {
					throw new RuntimeException((new StringBuilder(
							"Resultset not Closed..."))
							.append(sqe.getMessage()).toString());
				}
			}

			rset = (ResultSet) cstmt.getObject(6);
			if (rset != null) {
				while (rset.next()) {
					ArrayList<Object> spocSolution = new ArrayList<Object>();
					spocSolution.add(rset.getString(1));
					spocSolution.add(rset.getString(2));
					spocSolution.add(rset.getString(3));
					spocSolution.add(rset.getString(4));

					String date = rset.getString(5);
					if (date != null) {
						spocSolution.add(df.format(rset.getDate(5)));
					} else {
						spocSolution.add("-");
					}

					spocSolution.add(rset.getString(6));
					String spocRmk = rset.getString(7).trim();

					if (spocRmk.equals("Pending")) {
						spocSolution.add("-");
					} else {

						spocSolution.add(rset.getString(7));
					}
					String date1 = rset.getString(8);
					if (date1 != null) {
						spocSolution.add(df.format(rset.getDate(8)));
					} else {
						spocSolution.add("-");
					}

					spocSolution.add(rset.getString(9));
					if (status.equals("CLOSE")) {
						String d1 = df1.format(rset.getDate(5));
						String d2 = df1.format(rset.getDate(8));
						java.util.Date dd1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d1);
						java.util.Date dd2 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").parse(d2);
						String TAT = Integer.toString(getWorkingDaysBetweenTwoDates(dd1,
								dd2));
						spocSolution
								.add(TAT);
					} else {
						spocSolution.add("-");
					}

					spocData.add(spocSolution);
				}
			}
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException sqe) {
					throw new RuntimeException((new StringBuilder(
							"Resultset not Closed..."))
							.append(sqe.getMessage()).toString());
				}
			}

			hm.put("callData", callData);
			hm.put("immediateMgrData", immediateMgrData);
			hm.put("spocData", spocData);

		} catch (SQLException e) {
			throw new RuntimeException("Error while getting Report Data --- : "
					+ e.getCause() + " : " + e.getMessage() + " : "
					+ e.getStackTrace());

		}

		finally {
			obj.releaseResource(connection, cstmt, rset);
		}
		System.out.println(hm);
		return hm;
	}

	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	ArrayList<java.util.Date> holidayList = new ArrayList<java.util.Date>();

	public int getWorkingDaysBetweenTwoDates(java.util.Date startDate,
			java.util.Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 1);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 1);

		int workDays = -1;

		if (df.format(startDate).equals(df.format(endDate))) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		try {
			do {
				if (!checkHoliday(startCal.getTime())
						&& !checkSecondSaturday(startCal.getTime())
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					workDays++;
				}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			} while (startCal.getTime().before(endCal.getTime())
					|| startCal.getTime().equals(endCal.getTime()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (workDays == -1) {
			workDays = 0;
		}
		return workDays;
	}

	public boolean checkSecondSaturday(java.util.Date date) {
		boolean isSecondSat = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY) {
			int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
			if (weekOfMonth == 2 || weekOfMonth == 4) {
				isSecondSat = true;
			}
		}
		return isSecondSat;
	}

	public boolean checkHoliday(java.util.Date date) {
		boolean isHoliday = false;
		for (int i = 0; i < holidayList.size(); i++) {
			if (df.format(date).equals(df.format(holidayList.get(i)))) {
				isHoliday = true;
				break;
			}
		}
		return isHoliday;
	}

	public void getHolidays() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Calendar cal = Calendar.getInstance();
		DBUtility obj = new DBUtility();
		try {
			connection = obj.getDBConnection();
			pstmt = connection
					.prepareCall("SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS WHERE HOLIDAYYEAR = (SELECT TO_NUMBER(EXTRACT(YEAR FROM SYSDATE)) FROM DUAL)");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				java.util.Date holiday = new java.util.Date(rset.getDate(1)
						.getTime());
				Calendar holidayCal = Calendar.getInstance();
				holidayCal.setTime(holiday);
				cal.set(Calendar.YEAR, holidayCal.get(Calendar.YEAR));
				cal.set(Calendar.MONTH, holidayCal.get(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH,
						holidayCal.get(Calendar.DAY_OF_MONTH));
				holidayList.add(cal.getTime());
			}
		} catch (Exception e) {
			System.out.println("Error in getting holiday list : "
					+ e.getMessage());
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private String exportToExcelCDComplete(Map reports)
	{
	       String reportName = null;
	       FileOutputStream fos = null;
	       HSSFWorkbook workbook = new HSSFWorkbook();

	       String time =  new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new java.util.Date()).toString();
	      try
	       {
	        HSSFSheet sheet = workbook.createSheet("Ticket Detail");
	        HSSFSheet sheet1 = workbook.createSheet("Immediate Manager Resolution");
	        HSSFSheet sheet2 = workbook.createSheet("SPOC Resolution");
	       String[] HeaderList = { "REFERENCE NUMBER", 
						"AGENT CODE", "STATUS","BRANCH CODE","BRANCH NAME","BM", "START DATE", "END DATE",
						"CLOSUE REMARK", "PENDING WITH", "TAT" };
	       String[] HeaderList1 = { "REFERENCE NUMBER", 
						"BM REMARK", "BM SUBMIT DATE","IMMEDIATE MANAGER REMARK","IM SUBMIT DATE","TAT" };
	       String[] HeaderList2 = { "REFERENCE NUMBER", 
						"CATEGORY", "SUB CATEGORY","BM REMARKS","BM SUBMIT DATE","SPOC", "SPOC REMARK", "SPOC SUBMIT DATE",
						"STATUS", "TAT" };

	         ArrayList<ArrayList<Object>> callData = (ArrayList)reports.get("callData");
		 ArrayList<ArrayList<Object>> immediateMgrData= (ArrayList)reports.get("immediateMgrData");
		 ArrayList<ArrayList<Object>> spocData= (ArrayList)reports.get("spocData");

	                  if (callData != null && !callData.isEmpty() && callData.size() > 0)
				{
			        setHeaderStyle(workbook);
					HSSFRow headerRow1 = sheet.createRow(1);
					for (int j = 0; j < HeaderList.length; j++) 
					{
						HSSFCell cell = headerRow1.createCell((short)j);
						cell.setCellValue(HeaderList[j]);
						cell.setCellStyle(cellStyleHeader);
					}
	                               
					for (int i = 0; i < callData.size(); i++)
					{
				       		
						 HSSFRow rowA = sheet.createRow((i + 3));
						rowA.createCell((short)0).setCellValue((String)callData.get(i).get(0));
						rowA.createCell((short)1).setCellValue((String)callData.get(i).get(1));
						rowA.createCell((short)2).setCellValue((String)callData.get(i).get(2));
						rowA.createCell((short)3).setCellValue((String)callData.get(i).get(3));
						rowA.createCell((short)4).setCellValue((String)callData.get(i).get(4));
						rowA.createCell((short)5).setCellValue((String)callData.get(i).get(5));
						rowA.createCell((short)6).setCellValue((String)callData.get(i).get(6));
						rowA.createCell((short)7).setCellValue((String)callData.get(i).get(7));
						rowA.createCell((short)8).setCellValue((String)callData.get(i).get(8));
	                                        rowA.createCell((short)9).setCellValue((String)callData.get(i).get(9));
	                                        rowA.createCell((short)10).setCellValue((String)callData.get(i).get(10));
	                                 }
					for(int i= 0;i<HeaderList.length;i++)
			                {
			             sheet.setColumnWidth((short)i,(short)5000);
			                }
	 			     
	                       }
	                      if (immediateMgrData != null && !immediateMgrData.isEmpty() && immediateMgrData.size() > 0)
				{
			        setHeaderStyle(workbook);
					HSSFRow headerRow1 = sheet1.createRow(1);
					for (int j = 0; j < HeaderList1.length; j++) 
					{
						HSSFCell cell = headerRow1.createCell((short)j);
						cell.setCellValue(HeaderList1[j]);
						cell.setCellStyle(cellStyleHeader);
					}
	                               
					for (int i = 0; i < immediateMgrData.size(); i++)
					{
				       		
						 HSSFRow rowA = sheet1.createRow((i + 3));
						rowA.createCell((short)0).setCellValue((String)immediateMgrData.get(i).get(0));
						rowA.createCell((short)1).setCellValue((String)immediateMgrData.get(i).get(1));
						rowA.createCell((short)2).setCellValue((String)immediateMgrData.get(i).get(2));
						rowA.createCell((short)3).setCellValue((String)immediateMgrData.get(i).get(3));
						rowA.createCell((short)4).setCellValue((String)immediateMgrData.get(i).get(4));
						rowA.createCell((short)5).setCellValue((String)immediateMgrData.get(i).get(5));
					}	
					for(int i= 0;i<HeaderList1.length;i++)
			                {
			             sheet1.setColumnWidth((short)i,(short)5000);
			                }
	                          }

	                      if (spocData != null && !spocData.isEmpty() && spocData.size() > 0)
				{
			        setHeaderStyle(workbook);
					HSSFRow headerRow1 = sheet2.createRow(1);
					for (int j = 0; j < HeaderList2.length; j++) 
					{
						HSSFCell cell = headerRow1.createCell((short)j);
						cell.setCellValue(HeaderList2[j]);
						cell.setCellStyle(cellStyleHeader);
					}
	                               
					for (int i = 0; i < spocData.size(); i++)
					{
				       		
						 HSSFRow rowA = sheet2.createRow((i + 3));
						rowA.createCell((short)0).setCellValue((String)spocData.get(i).get(0));
						rowA.createCell((short)1).setCellValue((String)spocData.get(i).get(1));
						rowA.createCell((short)2).setCellValue((String)spocData.get(i).get(2));
						rowA.createCell((short)3).setCellValue((String)spocData.get(i).get(3));
						rowA.createCell((short)4).setCellValue((String)spocData.get(i).get(4));
						rowA.createCell((short)5).setCellValue((String)spocData.get(i).get(5));
						rowA.createCell((short)6).setCellValue((String)spocData.get(i).get(6));
						rowA.createCell((short)7).setCellValue((String)spocData.get(i).get(7));
						rowA.createCell((short)8).setCellValue((String)spocData.get(i).get(8));
						rowA.createCell((short)9).setCellValue((String)spocData.get(i).get(9));
					}	
					for(int i= 0;i<HeaderList2.length;i++)
			                {
			             sheet2.setColumnWidth((short)i,(short)5000);
			                }
	                          }

	                   


	                 fos = new FileOutputStream(new File(SBMConf.SBM_WEBAPPDIR
								+ "/bpmportal/AgentRetentionReport/reports/TrackHistory_"
								+ time + ".xls"));
					workbook.write(fos);
	                                
					reportName = "/sbm/bpmportal/AgentRetentionReport/reports/TrackHistory_"+time+".xls";
	                

	                 }catch (Exception ex) 
			{
				System.out.println("Error gererating report : "
						+ ex.getMessage());
			}
	            
			finally 
		       {
			    if (fos != null) 
			    {
				    try
				    {
					    fos.flush();
						fos.close();
					}
					catch (Exception e) 
					{
					    e.printStackTrace();
					}
				}
			}
			

	        return reportName;
	}


	HSSFCellStyle cellStyleHeader = null;
    HSSFFont fontHeader = null;
    public void setHeaderStyle(HSSFWorkbook workbook) 
	{
	    cellStyleHeader = workbook.createCellStyle();
	    fontHeader = workbook.createFont();
	    cellStyleHeader.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
	    cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyleHeader.setBottomBorderColor(IndexedColors.WHITE.getIndex());
	    cellStyleHeader.setTopBorderColor(IndexedColors.WHITE.getIndex());
	    cellStyleHeader.setLeftBorderColor(IndexedColors.WHITE.getIndex());
	    cellStyleHeader.setRightBorderColor(IndexedColors.WHITE.getIndex());
	    fontHeader.setColor(HSSFColor.WHITE.index);
	    fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
	    cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    cellStyleHeader.setFont(fontHeader);
   }
}
