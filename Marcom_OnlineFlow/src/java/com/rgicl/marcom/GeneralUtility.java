package com.rgicl.marcom;

import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.UserManager;

import java.net.InetAddress;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.apache.axis.client.Service;

import com.rgicl.marcom.db.DBUtility;
import com.savvion.mom.*;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;

public class GeneralUtility {

	private String wsdlUrl = "";

	public GeneralUtility() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			if (ip.contains("10.65.15.")) {
				wsdlUrl = "http://10.65.15.160:8081/Service1.asmx?wsdl";
			} else {
				wsdlUrl = "http://10.65.5.158:8081/Service1.asmx?wsdl";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getNameOfUser(String userName) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		String qry = "{?=call getUserName(?)}";
		String name = null;
		try {
			if (userName != null && userName.trim().length() > 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
				cstmt.setString(2, userName.trim());
				cstmt.execute();
				name = cstmt.getString(1);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
		return name;
	}

	public String getEmailId(String empCode) {
		try {
			String email = "";
			if (empCode != null && empCode.trim().length() > 1) {
				Service1Soap_BindingStub sb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				email = sb.getEmployeeEmailId(empCode);
			}
			return email;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String[] getGroupMembers(String grpName) {
		String[] grpMemberName = null;
		if (grpName != null && grpName.trim().length() > 0) {
			grpMemberName = new JDBCRealm().getGroup(grpName).getMemberNames();
		}
		return grpMemberName;
	}

	public static void setDueDate(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			if (wsName != null && wsName.trim().length() > 0) {
				blserver = BLClientUtil.getBizLogicServer();
				blsession = blserver.connect(AppConstant.BLUSERNAME,
						AppConstant.BLPASSWORD);
				long timeDuration = 1 * 8 * 60 * 60 * 1000; // 1 Working days.
				long startDate = (new Date()).getTime();
				SBMCalendar.init(null);
				SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
				BizCalendar bcal = new BizCalendar(startDate, timeDuration);
				Calendar initDuedate = sbmcal.getDueDate(bcal);
				WorkStepInstance wsi = blserver.getWorkStepInstance(blsession,
						piID, wsName);
				WorkItemList wli = wsi.getWorkItemList();
				Vector wiList = (Vector) wli.getList();
				Date dueDt = initDuedate.getTime();
				for (int j = 0; j < wiList.size(); j++) {
					WorkItem wi = (WorkItem) wiList.get(j);
					wi.setDueDate(dueDt.getTime());
					wi.save();
					wi.refresh();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				blserver.disConnect(blsession);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public boolean isMRC_Member(String userName) {
		boolean isGrpMember = false;
		try {
			if (userName != null && userName.trim().length() > 0) {
				String[] grpMembers = ((AdvanceGroup) UserManager
						.getGroup("MarcomGrp")).getAllUserMemberNames();
				for (int j = 0; j < grpMembers.length; j++) {
					if (grpMembers[j].equalsIgnoreCase(userName.trim())) {
						isGrpMember = true;
						break;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isGrpMember;
	}

}
