package com.rgicl.marcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.rgicl.marcom.db.DBUtility;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;

public class FlowActivity {

	public boolean isValidVariable(String value) {
		boolean isValidVar = false;
		if (value != null && value.trim().length() > 0) {
			isValidVar = true;
		}
		return isValidVar;
	}

	public void captureFlowStatus(String tktNo, String prfmr, String rmks,
			String status, String wsName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		if (isValidVariable(tktNo) && isValidVariable(prfmr)
				&& isValidVariable(rmks) && isValidVariable(status)
				&& isValidVariable(wsName)) {
			try {
				String qry = "INSERT INTO MRC_ONL_FLOWSTATUS VALUES (?,?,?,?,?,?)";
				connection = DBUtility.getDBConnection();
				pstmt = connection.prepareStatement(qry);
				pstmt.setString(1, tktNo.trim());
				pstmt.setString(2, GeneralUtility.getNameOfUser(prfmr.trim()));
				pstmt.setString(3, rmks.trim());
				pstmt.setString(4, status.trim());
				pstmt.setString(5, wsName.trim());
				pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
				int i = pstmt.executeUpdate();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				DBUtility.releaseResouce(pstmt, connection);
			}
		}
	}

	public long setLegalIRDA_OverDue(long piID) {
		BLServer blserver = null;
		Session blsession = null;
		ProcessInstance pi = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			pi = blserver.getProcessInstance(blsession, piID);
			long time = Long.parseLong(pi.getDataSlotValue("circulationDate")
					.toString());
			long firstEsclateDurtn = 5 * 8 * 60 * 60 * 1000;
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(time, firstEsclateDurtn);
			Calendar initDuedate = sbmcal.getDueDate(bcal);
			long fixedTime = initDuedate.getTime().getTime();
			return fixedTime;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (blsession != null) {
					blserver.disConnect(blsession);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
