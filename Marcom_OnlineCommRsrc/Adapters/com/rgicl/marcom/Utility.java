package com.rgicl.marcom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import oracle.jdbc.OracleTypes;

import com.rgicl.marcom.beans.*;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.BSProcessInstance;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDataSlotData;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDocumentDS;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;

public class Utility {
	public LinkedHashMap<Integer, String> getCategory(String appType) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		LinkedHashMap<Integer, String> ctgry = null;
		String qry = "{call GETMRC_ONLCTGRY(?,?)}";
		try {
			if (appType != null && appType.trim().length() > 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.setString(1, appType.trim());
				cstmt.registerOutParameter(2, OracleTypes.CURSOR);
				cstmt.execute();
				rset = (ResultSet) cstmt.getObject(2);
				ctgry = new LinkedHashMap<Integer, String>();
				while (rset.next()) {
					ctgry.put(rset.getInt(1), rset.getString(2));
				}
			}
			return ctgry;
		} catch (Exception ex) {
			System.out.println("Error getCategory : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
	}

	public LinkedHashMap<Integer, String> getSubCategoryList(String appType,
			int categryID) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		LinkedHashMap<Integer, String> subCategrylist = null;
		String qry = "{call GETMRC_ONLSUBCATEGRY(?,?,?)}";
		try {
			if (appType != null && appType.trim().length() > 0
					&& categryID != 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.setString(1, appType.trim());
				cstmt.setInt(2, categryID);
				cstmt.registerOutParameter(3, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(3);
				subCategrylist = new LinkedHashMap<Integer, String>();
				while (rset.next()) {
					subCategrylist.put(rset.getInt(1), rset.getString(2));
				}
			}
			return subCategrylist;
		} catch (Exception ex) {
			System.out.println("Error getSubCategoryList : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
	}

	public ArrayList<TicketInitialInfo> getTicketInitialInfo(String userName) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		ArrayList<TicketInitialInfo> ticketInfo = null;
		String qry = "{call GETINFO_MRC_ONL_FLOW(?,?,?)}";
		try {
			if (userName != null && userName.trim().length() > 0) {
				String grpMember = BasicUtility.getUserGrpName(userName.trim());
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.setString(1, userName.trim());
				cstmt.setString(2, grpMember.trim());
				cstmt.registerOutParameter(3, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(3);
				ticketInfo = new ArrayList<TicketInitialInfo>();
				while (rset.next()) {
					TicketInitialInfo bean = new TicketInitialInfo();
					bean.setTicketNo(rset.getString(1));
					bean.setCategory(rset.getString(2));
					bean.setSubCategory(rset.getString(3));
					bean.setProduct(rset.getString(4));
					bean.setBusinessUser(rset.getString(5));
					bean.setLegalApprovalTime(rset.getTimestamp(6).getTime());
					bean.setIrdaDate(rset.getDate(7).getTime());
					bean.setUinNo(rset.getString(8));
					bean.setProcessTime(rset.getLong(9));
					bean.setStatus(rset.getString(10));
					ticketInfo.add(bean);
				}
			}
			return ticketInfo;
		} catch (Exception ex) {
			System.out.println("Error getTicketInitialInfo : "
					+ ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
	}

	public ArrayList<TicketInitialInfo> getTicketInfo(String userName,
			String ticketNo, String uinNo) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		ArrayList<TicketInitialInfo> ticketInfo = null;
		String qry = "{call GETTICKETINFO_MRC_ONL_FLOW(?,?,?,?,?)}";
		try {
			if (userName != null
					&& userName.trim().length() > 0
					&& (ticketNo != null && ticketNo.trim().length() > 0 || uinNo != null
							&& uinNo.trim().length() > 0)) {
				connection = DBUtility.getDBConnection();
				String grpMember = BasicUtility.getUserGrpName(userName.trim());
				cstmt = connection.prepareCall(qry);
				if (ticketNo == null || ticketNo.trim().length() == 0) {
					ticketNo = " ";
				}
				if (uinNo == null || uinNo.trim().length() == 0) {
					uinNo = " ";
				}
				cstmt.setString(1, userName.trim());
				cstmt.setString(2, grpMember.trim());
				cstmt.setString(3, ticketNo);
				cstmt.setString(4, uinNo);
				cstmt.registerOutParameter(5, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(5);
				ticketInfo = new ArrayList<TicketInitialInfo>();
				while (rset.next()) {
					TicketInitialInfo bean = new TicketInitialInfo();
					bean.setTicketNo(rset.getString(1));
					bean.setCategory(rset.getString(2));
					bean.setSubCategory(rset.getString(3));
					bean.setProduct(rset.getString(4));
					bean.setLegalApprovalTime(rset.getTimestamp(5).getTime());
					bean.setIrdaDate(rset.getDate(6).getTime());
					bean.setUinNo(rset.getString(7));
					bean.setProcessTime(rset.getLong(8));
					bean.setStatus(rset.getString(9));
					ticketInfo.add(bean);
				}
			}
			return ticketInfo;
		} catch (Exception ex) {
			System.out.println("Error getTicketInfo : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
	}

	/*
	 * public ArrayList<OnlineAppTicketInfo> getOnlineTicketDetail(String
	 * ticketNo) { Connection connection = null; CallableStatement cstmt = null;
	 * ResultSet rset = null; ArrayList<OnlineAppTicketInfo> ticketInfo = null;
	 * String qry = "{call GETONL_TICKETINFO(?,?)}"; try { if (ticketNo != null
	 * && ticketNo.trim().length() > 0) { connection =
	 * DBUtility.getDBConnection(); cstmt = connection.prepareCall(qry);
	 * cstmt.setString(1, ticketNo.trim()); cstmt.registerOutParameter(2,
	 * OracleTypes.CURSOR); cstmt.executeUpdate(); rset = (ResultSet)
	 * cstmt.getObject(2); ticketInfo = new ArrayList<OnlineAppTicketInfo>();
	 * while (rset.next()) { OnlineAppTicketInfo bean = new
	 * OnlineAppTicketInfo(); bean.setPRIORTY(rset.getString(1));
	 * bean.setONLINETEAMRMKS(rset.getString(2));
	 * bean.setUWREMARKS(rset.getString(3));
	 * bean.setUWAPPROVALSTATUS(rset.getString(4));
	 * bean.setLEGALREMARKS(rset.getString(5));
	 * bean.setLEGALAPPROVALSTATUS(rset.getString(6)); bean.setPROCESSTIME("" +
	 * rset.getString(7)); ticketInfo.add(bean); } } return ticketInfo; } catch
	 * (Exception ex) { System.out.println("Error getOnlineTicketDetail : " +
	 * ex.getMessage()); throw new RuntimeException(ex); } finally {
	 * DBUtility.releaseResouce(rset, cstmt, connection); } }
	 */

	/*
	 * public ArrayList<MarcomTicketInfo> getMarcomTicketDetail(String ticketNo)
	 * { Connection connection = null; CallableStatement cstmt = null; ResultSet
	 * rset = null; ArrayList<MarcomTicketInfo> marcomTicketInfo = null; String
	 * qry = "{call GETMRC_TICKETINFO(?,?)}"; try { if (ticketNo != null &&
	 * ticketNo.trim().length() > 0) { connection = DBUtility.getDBConnection();
	 * cstmt = connection.prepareCall(qry); cstmt.setString(1, ticketNo.trim());
	 * cstmt.registerOutParameter(2, OracleTypes.CURSOR); cstmt.executeUpdate();
	 * rset = (ResultSet) cstmt.getObject(2); marcomTicketInfo = new
	 * ArrayList<MarcomTicketInfo>(); while (rset.next()) { MarcomTicketInfo
	 * bean = new MarcomTicketInfo(); bean.setPRIORITY(rset.getString(1));
	 * bean.setMARCOM_RMKS(rset.getString(2));
	 * bean.setBUSINESSTEAM_RMKS(rset.getString(3));
	 * bean.setBUSINESSTEAM_APPROVAL(rset.getString(4));
	 * bean.setUW_REMARKS(rset.getString(5));
	 * bean.setUW_APPROVALSTATUS(rset.getString(6));
	 * bean.setLEGAL_REMARKS(rset.getString(7));
	 * bean.setLEGAL_APPROVALSTATUS(rset.getString(8));
	 * bean.setMRC_WTRMRKRMK(rset.getString(9));
	 * bean.setMRC_LEGALFILINGRMKS(rset.getString(10));
	 * bean.setMRC_SENDTOPROCRMKS(rset.getString(11));
	 * bean.setMRC_PROCRMKS(rset.getString(12));
	 * bean.setMRC_BUPROCRMKS(rset.getString(13)); bean.setPROCESS_TIME("" +
	 * rset.getString(14)); marcomTicketInfo.add(bean); } } return
	 * marcomTicketInfo; } catch (Exception ex) {
	 * System.out.println("Error getMarcomTicketDetail : " + ex.getMessage());
	 * throw new RuntimeException(ex); } finally {
	 * DBUtility.releaseResouce(rset, cstmt, connection); } }
	 */

	public String[] getWaterMarkDocs(long piID, String processName) {
		System.out.println("inside getWaterMark : "+piID+" : "+processName);
		BLServer blserver = null;
		Session blsession = null;
		String[] fileNamesArr = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			System.out.println("blsession : "+blsession);
			QueryService qService = new QueryService(blsession);
			BSDataSlotData bsDataSlotData = null;
			if (processName != null && processName.trim().length() > 0) {
				if (processName.equalsIgnoreCase("ONL")) {
					bsDataSlotData = new BSProcessInstance(qService)
							.getProcessInstance(piID).getDataSlotList().get(
									"CreativeDocByOnlineTeam");
				} else if (processName.equalsIgnoreCase("MRC")) {
					bsDataSlotData = new BSProcessInstance(qService)
							.getProcessInstance(piID).getDataSlotList().get(
									"mrcWaterMark");
				}
				BSDocumentDS bsDocumentDS = (BSDocumentDS) bsDataSlotData;
				List<String> fileNameList = bsDocumentDS.getDocumentNames();
				if (!fileNameList.isEmpty() && fileNameList.size() > 0) {
					fileNamesArr = new String[fileNameList.size()];
					fileNamesArr = fileNameList.toArray(fileNamesArr);
				}
			}
			System.out.println("fileName : "+fileNamesArr);
		} catch (Exception ex) {
			System.out
					.println("Error in getWaterMarkDocs : " + ex.getMessage());
			ex.printStackTrace();
		}
		return fileNamesArr;
	}

	public boolean isUIN_Exist(String UINNumber) {
		boolean UIN_Exist = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> uinList = new ArrayList<String>();
		try {
			if (isValidVariable(UINNumber)) {
				//String qry = "SELECT UINNO FROM MARCOM_ONLINEFLOW WHERE UINNO IS NOT NULL";
				String qry = "SELECT UINNO FROM MARCOM_ONLINEFLOW MRC INNER JOIN PROCESSINSTANCE PI "
						+ "ON PI.PROCESS_INSTANCE_ID = MRC.PROCESS_INSTANCE_ID WHERE PI.STATUS != 'PI_REMOVED'"
						+ " AND UINNO IS NOT NULL;";
				connection = DBUtility.getDBConnection();
				pstmt = connection.prepareStatement(qry);
				rset = pstmt.executeQuery();
				while (rset.next()) {
					uinList.add(rset.getString(1));
				}
				if (!uinList.isEmpty() && uinList != null) {
					UIN_Exist = uinList.contains(UINNumber);
				}
			}
		} catch (Exception ex) {
			System.out.println("Error in isUIN_Exist : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, pstmt, connection);
		}
		return UIN_Exist;
	}

	public boolean isWSCompleted(long piID, String wsName) {
		boolean isComplete = false;
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			isComplete = WorkStepInstance.isCompleted(blsession, piID, wsName);
		} catch (Exception ex) {
			System.out.println("Error in isWSCompleted : " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				blserver.disConnect(blsession);
			} catch (Exception e) {
				System.out.println("Error In isWSCompleted Closing BLServer : "
						+ e.getMessage());
				throw new RuntimeException(e);
			}
		}
		return isComplete;
	}

	public boolean deleteTicket(String ticketNo, String user, String reason) {
		BLServer blserver = null;
		Session blsession = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		boolean isTicketDelete = false;
		if (ticketNo != null && ticketNo.trim().length() > 0 && user != null
				&& user.trim().length() > 0 && reason != null
				&& reason.trim().length() > 0) {
			long piID = Long.parseLong(ticketNo.split("#")[1].trim());
			try {
				blserver = BLClientUtil.getBizLogicServer();
				blsession = blserver.connect("rgicl", "rgicl");
				if (blsession != null) {
					if (ProcessInstance.isExist(blsession, piID)) {
						ProcessInstance pi = blserver.getProcessInstance(
								blsession, piID);
						pi.remove();
						if (!ProcessInstance.isExist(blsession, piID)) {
							isTicketDelete = true;
							String query = "insert into MRC_DELTICKET_REASON VALUES (?,?,?)";
							connection = DBUtility.getDBConnection();
							pstmt = connection.prepareStatement(query);
							pstmt.setString(1, ticketNo.trim());
							pstmt.setString(2, user.trim());
							pstmt.setString(3, reason.trim());
							pstmt.executeUpdate();
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				try {
					if (blsession != null) {
						blserver.disConnect(blsession);
					}
					DBUtility.releaseResouce(pstmt, connection);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return isTicketDelete;
	}

	public boolean isValidVariable(String value) {
		boolean isValidVar = false;
		if (value != null && value.trim().length() > 0) {
			isValidVar = true;
		}
		return isValidVar;
	}

	public ArrayList<FlowStatus> getFlowStatus(String tktNo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<FlowStatus> objList = null;
		if (isValidVariable(tktNo)) {
			try {
				String qry = "SELECT * FROM MRC_ONL_FLOWSTATUS WHERE TICKETNO = ? ORDER BY PROCESSTIME";
				connection = DBUtility.getDBConnection();
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, tktNo.trim());
				rset = pstmt.executeQuery();
				objList = new ArrayList<FlowStatus>();
				while (rset.next()) {
					FlowStatus obj = new FlowStatus();
					obj.setTktNo(rset.getString("TICKETNO"));
					obj.setPrfmr(rset.getString("PERFORMER"));
					obj.setRmks(rset.getString("REMARK"));
					obj.setProcessTime(rset.getTimestamp("PROCESSTIME")
							.toString());
					obj.setStatus(rset.getString("STATUS"));
					objList.add(obj);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				DBUtility.releaseResouce(rset, pstmt, connection);
			}
		}
		return objList;
	}

}
