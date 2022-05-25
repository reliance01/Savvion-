package com.rel.techaudit.utilities;

import com.rel.db.DBUtility;
import com.rel.techaudit.beans.AuditPageBean;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.commons.beanutils.PropertyUtils;

import oracle.jdbc.OracleTypes;;

public class TechnicalAuditUtility {

	SimpleDateFormat formattor = null;

	public TechnicalAuditUtility() {
		formattor = new SimpleDateFormat("dd-MMM-yyyy");
	}

	/**
	 * Format the milisecond into specific date format.
	 * 
	 * @param dateInMiliSec
	 * @return
	 */
	public String getDateWithFormate(long dateInMiliSec) {
		return formattor.format(dateInMiliSec);
	}

	/**
	 * Update the Auditor and Auditee Report into Database. There is different
	 * database UPDATE query based on Workstep name.
	 * 
	 * @param piid
	 * @param selectedDataByAuditor
	 * @param workStepName
	 * @return
	 */

	public int updateInitialSelectedDataByAuditor(String piid,
			String[][] selectedDataByAuditor, String workStepName,
			String branchTyp) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sqlQuery = null;
		int countUpdatedRows = 0;
		try {
			if (selectedDataByAuditor.length != 0) {
				if (branchTyp != null && branchTyp.trim().length() > 0) {
					if (workStepName.equalsIgnoreCase("StartTechnicalAudit")) {
						if (branchTyp.equalsIgnoreCase("OD Claims")) {
							sqlQuery = "UPDATE INTERNAL_TECH_AUDIT SET CLAIM_CATEGORY = ?,CLAIM_STATUS = ?,SETTLEMENT_BASIS = ?,DEPARTMENT = ?,CLASS = ?,RISK_RATE = ?,DOCUMENTNO = ?,MIDDLE_PARA = ?,LAST_PARA = ?,RECOMMENDATION = ? WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
						} else if (branchTyp.equalsIgnoreCase("Legal Claims")) {
							sqlQuery = "UPDATE INTERNAL_TECH_AUDIT SET COURT_TYPE = ?,LOSS_NATURE = ?,CLAIM_STATUS = ?, DEPARTMENT = ?,CLASS = ?,RISK_RATE = ?,DOCUMENTNO = ?,MIDDLE_PARA = ?,LAST_PARA = ?,RECOMMENDATION = ? WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
						} else {
							sqlQuery = "UPDATE INTERNAL_TECH_AUDIT SET DEPARTMENT = ?,CLASS = ?,RISK_RATE = ?,DOCUMENTNO = ?,MIDDLE_PARA = ?,LAST_PARA = ?,RECOMMENDATION = ? WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
						}
					} else if (workStepName
							.equalsIgnoreCase("Auditee Reply Status")) {
						sqlQuery = "UPDATE INTERNAL_TECH_AUDIT SET REASON_FOR_DEVIATION = ?,CORRECTIVE_PREVENTIVE_ACTION = ?,TIMELINE_RESOLUTION = ?,STATUS_BY_AUDITEE = ? WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
					} else if (workStepName
							.equalsIgnoreCase("Closure Audit Observation")) {
						sqlQuery = "UPDATE INTERNAL_TECH_AUDIT SET REMARKS = ?,STATUS_BY_AUDITOR = ? WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
					}

					connection = DBUtility.getDBConnection();
					pstmt = connection.prepareStatement(sqlQuery);

					if (workStepName.equalsIgnoreCase("StartTechnicalAudit")) {
						/*
						 * for (int i = 0; i < selectedDataByAuditor[0].length;
						 * i++) { int temp = 1; for (int j = 0; j <
						 * (selectedDataByAuditor.length + 1); j++) { if (j ==
						 * 0) { pstmt.setString(selectedDataByAuditor.length,
						 * selectedDataByAuditor[j][i].trim()); } else if (j ==
						 * 1) { pstmt.setString( (selectedDataByAuditor.length +
						 * 1), selectedDataByAuditor[j][i].trim()); } else { if
						 * (j == selectedDataByAuditor.length) {
						 * pstmt.setLong(temp, Long.parseLong(piid .trim())); }
						 * else { pstmt.setString(temp,
						 * selectedDataByAuditor[j][i]); } temp++; } }
						 * countUpdatedRows += pstmt.executeUpdate(); }
						 */

						for (int i = 0; i < selectedDataByAuditor[0].length; i++) {
							pstmt.setString(1,
									selectedDataByAuditor[2][i].trim());
							pstmt.setString(2,
									selectedDataByAuditor[3][i].trim());
							pstmt.setString(3,
									selectedDataByAuditor[4][i].trim());
							pstmt.setString(4,
									selectedDataByAuditor[5][i].trim());
							pstmt.setString(5,
									selectedDataByAuditor[6][i].trim());
							pstmt.setString(6,
									selectedDataByAuditor[7][i].trim());
							pstmt.setString(7,
									selectedDataByAuditor[8][i].trim());
							if (branchTyp.equalsIgnoreCase("OD Claims")
									|| branchTyp
											.equalsIgnoreCase("Legal Claims")) {
								pstmt.setString(8,
										selectedDataByAuditor[9][i].trim());
								pstmt.setString(9,
										selectedDataByAuditor[10][i].trim());
								pstmt.setString(10,
										selectedDataByAuditor[11][i].trim());
								pstmt.setLong(11, Long.parseLong(piid.trim()));
								pstmt.setString(12,
										selectedDataByAuditor[0][i].trim());
								pstmt.setString(13,
										selectedDataByAuditor[1][i].trim());
							} else {
								pstmt.setLong(8, Long.parseLong(piid.trim()));
								pstmt.setString(9,
										selectedDataByAuditor[0][i].trim());
								pstmt.setString(10,
										selectedDataByAuditor[1][i].trim());
							}
							countUpdatedRows += pstmt.executeUpdate();
						}
						setAuditorStatus_Close(Long.parseLong(piid));
					} else if (workStepName
							.equalsIgnoreCase("Auditee Reply Status")) {
						for (int i = 0; i < selectedDataByAuditor[0].length; i++) {
							if (branchTyp.equalsIgnoreCase("OD Claims")
									|| branchTyp
											.equalsIgnoreCase("Legal Claims")) {
								pstmt.setString(1,
										selectedDataByAuditor[12][i].trim());
								pstmt.setString(2,
										selectedDataByAuditor[13][i].trim());
								pstmt.setString(3,
										selectedDataByAuditor[14][i].trim());
								pstmt.setString(4,
										selectedDataByAuditor[15][i].trim());
							} else {
								pstmt.setString(1,
										selectedDataByAuditor[9][i].trim());
								pstmt.setString(2,
										selectedDataByAuditor[10][i].trim());
								pstmt.setString(3,
										selectedDataByAuditor[11][i].trim());
								pstmt.setString(4,
										selectedDataByAuditor[12][i].trim());
							}
							pstmt.setLong(5, Long.parseLong(piid.trim()));
							pstmt.setString(6,
									selectedDataByAuditor[0][i].trim());
							pstmt.setString(7,
									selectedDataByAuditor[1][i].trim());
							countUpdatedRows += pstmt.executeUpdate();
						}
					} else if (workStepName
							.equalsIgnoreCase("Closure Audit Observation")) {
						if (!(selectedDataByAuditor.length == 0)) {
							for (int i = 0; i < selectedDataByAuditor[0].length; i++) {
								if (branchTyp.equalsIgnoreCase("OD Claims")
										|| branchTyp
												.equalsIgnoreCase("Legal Claims")) {
									pstmt.setString(1,
											selectedDataByAuditor[16][i].trim());
									pstmt.setString(2,
											selectedDataByAuditor[17][i].trim());
								} else {
									pstmt.setString(1,
											selectedDataByAuditor[13][i].trim());
									pstmt.setString(2,
											selectedDataByAuditor[14][i].trim());
								}
								pstmt.setLong(3, Long.parseLong(piid.trim()));
								pstmt.setString(4,
										selectedDataByAuditor[0][i].trim());
								pstmt.setString(5,
										selectedDataByAuditor[1][i].trim());
								countUpdatedRows += pstmt.executeUpdate();
							}
						}
					}
				}
			} else {
			}
		} catch (NumberFormatException ex) {
			System.out.println("Number Format Exception : " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("In updateInitialSelectedDataByAuditor()  Error is : "
							+ ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return countUpdatedRows;
	}

	/**
	 * This method set the AuditorStatus "CLOSE" when update the Risk Rate value
	 * is "No Audit Observation".
	 * 
	 * @param piid
	 * @return
	 */
	public int setAuditorStatus_Close(long piid) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE INTERNAL_TECH_AUDIT SET STATUS_BY_AUDITOR = ? WHERE RISK_RATE = ? AND PIID = ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "Close");
			pstmt.setString(2, "No Audit Observation");
			pstmt.setLong(3, piid);
			return pstmt.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
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

	/**
	 * This method return all the Parameter Id. This Parameter Id will become
	 * the ID of all Dynamic Created HTML Table's ID on Auditor and Audittee
	 * page. This Dynamic Created HTML Table shows when clicks on different Tabs
	 * on Auditor and Auditee page.
	 * 
	 * @param piid
	 * @return
	 */
	public ArrayList<String> getDynamicCreatedTableNames(Long piid) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> dynamicTableNames = null;
		try {
			connection = DBUtility.getDBConnection();
			String sqlQuery = "SELECT DISTINCT PARAMETER PARAMID FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND RISK_RATE != ? ORDER BY PARAMETER";
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setLong(1, piid);
			pstmt.setString(2, "No Audit Observation");
			rset = pstmt.executeQuery();
			dynamicTableNames = new ArrayList<String>();
			while (rset.next()) {
				dynamicTableNames.add("" + rset.getInt("PARAMID"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error is : " + ex.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return dynamicTableNames;
	}

	/**
	 * This method retrieve all the stored detail for specific Audit.
	 * 
	 * @param piid
	 * @param workStepName
	 * @return
	 */
	public ArrayList<AuditPageBean> retrieveSavedData(String piid,
			String workStepName) {
		System.out.println("Inside retrieveSavedData : " + piid + " : "
				+ workStepName);
		BLServer blServer = null;
		Session blSession = null;
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		Long piID = Long.parseLong(piid);
		ArrayList<AuditPageBean> data = null;
		String sqlQuery = "{call AUDIT_RETRIEVE_SAVEDDATA(?,?,?,?,?,?)}";
		try {
			if (piID != null) {
				blServer = BLClientUtil.getBizLogicServer();
				blSession = blServer.connect("rgicl", "rgicl");
				ProcessInstance pi = blServer.getProcessInstance(blSession,
						piID);
				String auditType = (String) pi.getDataSlotValue("branchType");
				if (auditType != null && auditType.trim().length() > 0) {
					connection = DBUtility.getDBConnection();
					cstmt = connection.prepareCall(sqlQuery);
					cstmt.setString(1, workStepName.trim());
					cstmt.setString(2, auditType.trim());
					cstmt.setLong(3, piID);
					cstmt.setString(4, "No Audit Observation");
					if (workStepName.equalsIgnoreCase("Auditee Reply Status")
							|| workStepName
									.equalsIgnoreCase("Closure Audit Observation")) {
						cstmt.setString(5, "Open");
					} else {
						cstmt.setString(5, "");
					}
					cstmt.registerOutParameter(6, OracleTypes.CURSOR);
					cstmt.executeQuery();
					rset = (ResultSet) cstmt.getObject(6);
					if (rset != null) {
						data = new ArrayList<AuditPageBean>();
						ResultSetMetaData rsmd = rset.getMetaData();
						while (rset.next()) {
							AuditPageBean obj = new AuditPageBean();
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								PropertyUtils.setProperty(obj,
										rsmd.getColumnName(i),
										rset.getString(rsmd.getColumnName(i)));
							}
							data.add(obj);
						}
					}
				}
			}
			/*
			 * for(int i = 0; i<data.size();i++){ System.out.println((String)
			 * PropertyUtils.getProperty(data.get(i), "SUBPARAMETER_VALUE")); }
			 */
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error is : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, cstmt, rset);
			try {
				if (blSession != null) {
					blServer.disConnect(blSession);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * By using this method we can get the Columns name by sql query.
	 * 
	 * @param connection
	 * @param pstmt
	 * @param rset
	 * @return
	 */
	public ArrayList<String> getColumnLableNamesByQuery(Connection connection,
			PreparedStatement pstmt, ResultSet rset) {
		ArrayList<String> columnLableNameList = new ArrayList<String>();
		try {
			ResultSetMetaData rsetMetaData = rset.getMetaData();
			int count = rsetMetaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				columnLableNameList.add(rsetMetaData.getColumnLabel(i));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			DBUtility.releaseResources(connection, pstmt, rset);
			System.out.println("Error in getColumnNamesWithSpecificWorktep : "
					+ ex.getMessage());
		}
		return columnLableNameList;
	}

	/**
	 * This method checks Process Instance Entries in INTERNAL_TECH_AUDIT table.
	 * 
	 * @param piid
	 * @return
	 */
	public int checkInstanceIdExist(String piid) {
		System.out.println("inside checkpiid exist : " + piid);
		int i = 0;
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM INTERNAL_TECH_AUDIT WHERE PIID = ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, Long.parseLong(piid));
			rset = pstmt.executeQuery();
			if (!rset.next()) {
				i = 0;
			} else {
				i = 1;
			}
			System.out.println("is exist : " + i);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return i;
	}

	/**
	 * This method Insert Default Values like Parameter,SubParameter,Branch etc.
	 * into INTERNAL_TECH_AUDIT table.
	 * 
	 * @param processInstanceId
	 * @param region
	 * @param branch
	 * @return
	 */
	public int insertBlankDetail(String processInstanceId, String region,
			String branch) {
		Long piid = Long.parseLong(processInstanceId.trim());
		int i = 0;
		Connection connection = null;
		ResultSet rset = null;
		CallableStatement cstmt = null;
		PreparedStatement pstmt1 = null;
		BLServer blServer = null;
		Session blSession = null;
		try {
			if (piid > 0 && branch != null && region != null) {
				blServer = BLClientUtil.getBizLogicServer();
				blSession = blServer.connect("rgicl", "rgicl");
				ProcessInstance pi = blServer.getProcessInstance(blSession,
						piid);
				String auditType = (String) pi.getDataSlotValue("branchType");
				String query = "{call AUDIT_GETPARAMETERS(?,?)}";
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(query);
				cstmt.setString(1, auditType);
				cstmt.registerOutParameter(2, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(2);
				if (rset != null) {
					while (rset.next()) {
						int id = rset.getInt("ID");
						int sub_id = rset.getInt("SUB_ID");

						String sql_ins = "insert into INTERNAL_TECH_AUDIT (PIID,REGION,BRANCH,PARAMETER,SUB_PARAMETER,PARAMETER_VALUE,SUBPARAMETER_VALUE,DEPARTMENT,CLASS,RISK_RATE,DOCUMENTNO,MIDDLE_PARA,LAST_PARA,RECOMMENDATION,REASON_FOR_DEVIATION,CORRECTIVE_PREVENTIVE_ACTION,TIMELINE_RESOLUTION,STATUS_BY_AUDITEE,REMARKS,STATUS_BY_AUDITOR,CLAIM_CATEGORY,CLAIM_STATUS,SETTLEMENT_BASIS,COURT_TYPE,LOSS_NATURE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

						try {
							pstmt1 = connection.prepareStatement(sql_ins);
							pstmt1.setLong(1, piid);
							pstmt1.setString(2, region);
							pstmt1.setString(3, branch);
							pstmt1.setInt(4, id);
							pstmt1.setInt(5, sub_id);
							pstmt1.setString(6, getParameterNameById(id));
							pstmt1.setString(
									7,
									getSubParamNameByParamAndSubParamId(id,
											sub_id));
							pstmt1.setString(8, "Select");
							pstmt1.setString(9, "Select");
							pstmt1.setString(10, "Select");
							pstmt1.setString(11, "");
							pstmt1.setString(12, "");
							pstmt1.setString(13, "");
							pstmt1.setString(14, "");
							pstmt1.setString(15, "");
							pstmt1.setString(16, "");
							pstmt1.setString(17, "");
							pstmt1.setString(18, "Open");
							pstmt1.setString(19, "");
							pstmt1.setString(20, "Open");
							pstmt1.setString(21, "Select");
							pstmt1.setString(22, "Select");
							pstmt1.setString(23, "Select");
							pstmt1.setString(24, "Select");
							pstmt1.setString(25, "Select");
							i = pstmt1.executeUpdate();
						} catch (Exception ex) {
							try {
								pstmt1 = connection
										.prepareStatement("delete from INTERNAL_TECH_AUDIT where PIID ="
												+ piid);
								pstmt1.executeUpdate();
								System.out
										.println("Error hence deleteing records;");
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (connection != null) {
									DBUtility.disConnect(connection);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (pstmt1 != null) {
									pstmt1.close();
								}
							} catch (Exception e) {
							}
							try {
								if (cstmt != null) {
									cstmt.close();
								}
							} catch (Exception e) {
							}
							try {
								if (rset != null) {
									rset.close();
								}
							} catch (Exception e) {
							}
							throw new RuntimeException(ex);
						}
					}
				}
				// }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e) {
			}
			try {
				if (connection != null) {
					DBUtility.disConnect(connection);
				}
			} catch (Exception e) {
			}
			try {
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (rset != null) {
					rset.close();
				}
			} catch (Exception e) {
			}
			throw new RuntimeException(ex);
		} finally {
			try {
				if (blServer != null) {
					blServer.disConnect(blSession);
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
			try {
				if (connection != null) {
					DBUtility.disConnect(connection);
				}
			} catch (Exception e) {
			}
			try {
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
			} catch (Exception e) {
			}
			try {
				if (rset != null) {
					rset.close();
				}
			} catch (Exception e) {
			}
		}
		System.out.println("Inserted blank detail by default.....");
		return i;
	}

	public String getParameterNameById(int parameterId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String parameterName = null;
		try {
			String sqlQuery = "SELECT PARAMETER FROM TBL_AUDIT_PARAMETER WHERE ID = ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1, parameterId);
			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					parameterName = rset.getString("PARAMETER");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("In getParameterNameById()  Error is : "
					+ ex.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return parameterName;
	}

	public int getParameterIdByName(String parameterName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int parameterId = 0;
		try {
			String sqlQuery = "SELECT ID FROM TBL_AUDIT_PARAMETER WHERE PARAMETER LIKE ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setString(1, "%" + parameterName.trim() + "%");
			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					parameterId = rset.getInt("ID");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("In getParameterIdByName()  Error is : "
					+ ex.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return parameterId;
	}

	public int getSubParamIdByParamIdAndSubParamName(int parameterId,
			String subParameterName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int subParameterId = 0;
		try {
			String sqlQuery = "SELECT SUB_ID FROM TBL_AUDIT_SUB_PARAMETER WHERE ID = ? AND PARAMETER LIKE ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1, parameterId);
			pstmt.setString(2, "%" + subParameterName.trim() + "%");
			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					subParameterId = rset.getInt("SUB_ID");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("In getSubParamIdByParamIdAndSubParamName()  Error is : "
							+ ex.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return subParameterId;
	}

	public String getSubParamNameByParamAndSubParamId(int parameterId,
			int subParameterId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String subParameterName = null;
		try {
			String sqlQuery = "SELECT PARAMETER FROM TBL_AUDIT_SUB_PARAMETER WHERE ID = ? AND SUB_ID = ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1, parameterId);
			pstmt.setInt(2, subParameterId);
			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					subParameterName = rset.getString("PARAMETER");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("In getSubParamNameByParamAndSubParamId()  Error is : "
							+ ex.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return subParameterName;
	}

	public int getOpenStatusOnAuditeeWS(String piid) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sqlQuery = null;
		int openStatus = 0;
		try {
			sqlQuery = "SELECT count(STATUS_BY_AUDITOR) OPENSTATUS FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND STATUS_BY_AUDITOR = ? AND RISK_RATE != ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setLong(1, Long.parseLong(piid));
			pstmt.setString(2, "Open");
			pstmt.setString(3, "No Audit Observation");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				openStatus = rset.getInt("OPENSTATUS");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return openStatus;
	}

	public String remove_amp_FromString(String value) {
		String Value = null;
		if (value.contains("&amp;")) {
			Value = value.replace("&amp;", "&");
			return Value;
		} else {
			return value;
		}
	}

	public int getParameterByValue(long piid, String parameterValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int parameter = 0;

		try {

			connection = DBUtility.getDBConnection();
			pstmt = connection
					.prepareStatement("SELECT distinct parameter FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND PARAMETER_VALUE = ?");
			pstmt.setLong(1, piid);
			pstmt.setString(2, parameterValue.trim());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				parameter = rset.getInt("parameter");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return parameter;
	}

	public int getMaxSubParameter(String parameterValue, long piid) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int maxSubParam = 0;
		try {
			connection = DBUtility.getDBConnection();
			pstmt = connection
					.prepareStatement("SELECT max(sub_parameter) maxsub FROM INTERNAL_TECH_AUDIT WHERE PARAMETER_VALUE = ? AND PIID = ?");
			pstmt.setString(1, parameterValue.trim());
			pstmt.setLong(2, piid);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				maxSubParam = rset.getInt("maxsub");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return maxSubParam;
	}

	public int addSubparameter(long piid, String subParameterValue,
			int subParameter, String parameterValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int value = 0;
		try {
			if (!checkDuplicateSbParam(piid, parameterValue, subParameterValue)) {
				String query = "update INTERNAL_TECH_AUDIT set subparameter_value = ? where piid = ? and parameter_value = ? and sub_parameter = ?";
				connection = DBUtility.getDBConnection();
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, subParameterValue.trim());
				pstmt.setLong(2, piid);
				pstmt.setString(3, parameterValue);
				pstmt.setInt(4, subParameter);
				value = pstmt.executeUpdate();
			}
			return value;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public int addRowForSubParameter(long piid, String region, String branch,
			String parameterValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int parameter = 0;
		int maxSubParameter = 0;
		int value = 0;
		try {
			parameter = getParameterByValue(piid, parameterValue);
			maxSubParameter = getMaxSubParameter(parameterValue, piid);
			String query = "INSERT INTO INTERNAL_TECH_AUDIT (piid,region,branch,parameter,sub_parameter,parameter_value,subparameter_value,department,"
					+ " class,risk_rate,documentno,middle_para,recommendation,last_para,reason_for_deviation,corrective_preventive_action,timeline_resolution,"
					+ " status_by_auditee,remarks,status_by_auditor,CLAIM_CATEGORY,CLAIM_STATUS,SETTLEMENT_BASIS,COURT_TYPE,LOSS_NATURE) VALUES"
					+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, piid);
			pstmt.setString(2, region.trim());
			pstmt.setString(3, branch.trim());
			pstmt.setInt(4, parameter);
			pstmt.setInt(5, (maxSubParameter + 1));
			pstmt.setString(6, parameterValue.trim());
			pstmt.setString(7, " ");
			pstmt.setString(8, "Select");
			pstmt.setString(9, "Select");
			pstmt.setString(10, "Select");
			pstmt.setString(11, "");
			pstmt.setString(12, "");
			pstmt.setString(13, "");
			pstmt.setString(14, "");
			pstmt.setString(15, "");
			pstmt.setString(16, "");
			pstmt.setString(17, "");
			pstmt.setString(18, "Open");
			pstmt.setString(19, "");
			pstmt.setString(20, "Open");
			pstmt.setString(21, "Select");
			pstmt.setString(22, "Select");
			pstmt.setString(23, "Select");
			pstmt.setString(24, "Select");
			pstmt.setString(25, "Select");
			value = pstmt.executeUpdate();
			if (value == 1) {
				return (maxSubParameter + 1);
			} else {
				throw new RuntimeException();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
	}

	public int deleteSubparameter(long piid, String parameter,
			String subParameterValue) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int value = 0;
		try {
			connection = DBUtility.getDBConnection();
			String query = null;
			int i = 0;
			if (subParameterValue.equals("") || subParameterValue.equals(" ")
					|| subParameterValue.equals(null)) {
				i = 1;
				query = "DELETE FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND PARAMETER_VALUE = ? AND trim(SUBPARAMETER_VALUE) is null";
			} else {
				query = "DELETE FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND PARAMETER_VALUE = ? AND SUBPARAMETER_VALUE = ?";
			}
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, piid);
			pstmt.setString(2, parameter.trim());
			if (i == 0) {
				pstmt.setString(3, subParameterValue);
			}
			value = pstmt.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return value;
	}

	public ArrayList<String> getAllNewAddedSubParameter(Long piid) {
		ArrayList<String> newSubParameters = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			conn = DBUtility.getDBConnection();
			String query = "SELECT DISTINCT subparameter_value FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND SUBPARAMETER_VALUE "
					+ "NOT IN(SELECT parameter FROM TBL_AUDIT_SUB_PARAMETER WHERE IS_ARCHIVE = 0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, piid);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				newSubParameters.add(rset.getString("subparameter_value"));
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResources(conn, pstmt, rset);
		}
		return newSubParameters;
	}

	private boolean checkDuplicateSbParam(long piid, String param,
			String sbParam) {
		boolean isDuplicat = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> sbParamList = new ArrayList<String>();
		try {
			String query = "SELECT SUBPARAMETER_VALUE FROM INTERNAL_TECH_AUDIT WHERE PIID = ? AND PARAMETER_VALUE = ?";
			connection = DBUtility.getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, piid);
			pstmt.setString(2, param);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				sbParamList.add(rset.getString(1).trim());
			}
			if (!sbParamList.isEmpty()) {
				if (sbParamList.contains(sbParam.trim())) {
					isDuplicat = true;
				}
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		} finally {
			DBUtility.releaseResources(connection, pstmt, rset);
		}
		return isDuplicat;
	}

}
