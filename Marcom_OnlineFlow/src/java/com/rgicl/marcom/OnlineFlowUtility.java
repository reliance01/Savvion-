package com.rgicl.marcom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import oracle.jdbc.OracleTypes;

import com.rgicl.marcom.beans.TATInfo;
import com.rgicl.marcom.db.DBUtility;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;

public class OnlineFlowUtility {

	public void setTeamOverdue(long piID) {
		BLServer blserver = null;
		Session blsession = null;
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		TATInfo bean = null;
		String qry = "{call GETMRCONL_TAT(?,?,?,?)}";
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String appType = (String) pi.getDataSlotValue("application");
			String category = (String) pi.getDataSlotValue("category");
			String subCategory = (String) pi.getDataSlotValue("subCategory");
			if (appType != null && appType.trim().length() > 0
					&& category != null && category.trim().length() > 0
					&& subCategory != null && subCategory.trim().length() > 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.setString(1, appType.trim());
				cstmt.setString(2, category.trim());
				cstmt.setString(3, subCategory.trim());
				cstmt.registerOutParameter(4, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(4);
				while (rset.next()) {
					bean = new TATInfo();
					bean.setResponsibleTeamTAT(rset.getInt(1));
					bean.setLegalTAT(rset.getInt(2));
					bean.setUwTAT(rset.getInt(3));
				}
				if (appType.trim().equalsIgnoreCase("Marcom")) {
					pi.updateSlotValue("marcomTeamTAT", Long.valueOf(bean
							.getResponsibleTeamTAT()));
				} else if (appType.trim().equalsIgnoreCase(
						"Online Sales Management")) {
					pi.updateSlotValue("onlineTeamTAT", Long.valueOf(bean
							.getResponsibleTeamTAT()));
				}
				pi.updateSlotValue("legalTeamTAT", Long.valueOf(bean
						.getLegalTAT()));
				pi.updateSlotValue("uwTeamTAT", Long.valueOf(bean.getUwTAT()));
				pi.save();
				pi.refresh();
			}
		} catch (Exception ex) {
			System.out.println("Error setTeamOverdue : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
			try {
				blserver.disConnect(blsession);
			} catch (Exception ex) {
				System.out
						.println("Error setTeamOverdue closing blserver connection : "
								+ ex.getMessage());
				throw new RuntimeException(ex);
			}
		}
	}

	public long getTAT(int days) {
		long totalDuration = 0;
		Calendar initDuedate = null;
		if (days != 0) {
			totalDuration = days * 8 * 60 * 60 * 1000;
			long startDate = (new Date()).getTime();
			try {
				SBMCalendar.init(null);
				SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
				BizCalendar bcal = new BizCalendar(startDate, totalDuration);
				initDuedate = sbmcal.getDueDate(bcal);
			} catch (SQLException e) {
				System.out.println("Error in getting TAT : " + e.getMessage());
			}
		}
		return initDuedate.getTime().getTime();
	}

	public boolean wsCompleted(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		boolean isWSCompleted = false;
		try {
			if (wsName != null && wsName.trim().length() > 0) {
				blserver = BLClientUtil.getBizLogicServer();
				blsession = blserver.connect(AppConstant.BLUSERNAME,
						AppConstant.BLPASSWORD);
				ProcessInstance pi = blserver.getProcessInstance(blsession,
						piID);
				String[] completedWSArr = pi.getCompletedWorkStepNames();
				for (int i = 0; i < completedWSArr.length; i++) {
					if (completedWSArr[i].trim().equalsIgnoreCase(wsName)) {
						isWSCompleted = true;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error in getCompletedWSArr : "
					+ ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			try {
				blserver.disConnect(blsession);
			} catch (Exception ex) {
				System.out
						.println("Error CloseBLserver in getCompletedWSArr : "
								+ ex.getMessage());
				throw new RuntimeException(ex);
			}
		}
		return isWSCompleted;
	}
}
