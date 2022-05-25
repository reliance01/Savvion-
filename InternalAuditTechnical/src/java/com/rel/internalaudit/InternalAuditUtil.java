package com.rel.internalaudit;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;

public class InternalAuditUtil {

	private String dataSourceName = "jdbc/SBMCommonDBXA";

	ArrayList<Date> holidayList = new ArrayList<Date>();
	java.text.SimpleDateFormat localDateFormat = new java.text.SimpleDateFormat(
			"HH:mm:ss");
	java.text.DateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
	final static int startWorkingHour = 10;
	final static int startWorkingMinute = 0;
	final static int startWorkingSecond = 0;
	final static int endWorkingHour = 18;
	final static int endWorkingMinute = 0;
	final static int endWorkingSecond = 0;
	final static int totalWorkingHours = 8;

	public Connection getDBConnection() {
		try {
			return ((DataSource) new InitialContext().lookup(dataSourceName))
					.getConnection();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void releaseResources(Connection conn, PreparedStatement pstmt,
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

	public void releaseResources(Connection conn, PreparedStatement pstmt) {
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

	public void addAuditHistoy(String piid, String performer, String wsName) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Long processInstanceId = Long.parseLong(piid);
			conn = getDBConnection();
			String sqlString1 = "insert into INTERNAL_AUDIT_HISTORY values(?,?,?,?)";
			ps = conn.prepareStatement(sqlString1);
			ps.setLong(1, processInstanceId);
			ps.setString(2, performer);
			ps.setString(3, wsName);
			ps.setTimestamp(4, new java.sql.Timestamp(new java.util.Date()
					.getTime()));
			ps.executeUpdate();
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error in adding Audit History and status \n"
							+ ex.getMessage(), ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException sqe) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqe) {
			}
		}
	}

	public void saveAuditInfo(long piID) {
		BLServer blServer = null;
		Session blSession = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect("rgicl", "rgicl");
			ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
			if (pi != null) {
				String auditType = (String) pi.getDataSlotValue("branchType");
				String branch = (String) pi.getDataSlotValue("branch");
				String region = (String) pi.getDataSlotValue("region");
				String auditor = (String) pi
						.getDataSlotValue("auditor_username");
				String auditee = (String) pi
						.getDataSlotValue("auditeeUserName");
				String regionalIncharge = (String) pi.getDataSlotValue("RH");
				String zonalIncharge = (String) pi
						.getDataSlotValue("zonalHead");
				String ercgHead = (String) pi.getDataSlotValue("ECRGHead");
				String emailSubject = (String) pi
						.getDataSlotValue("auditeeMailSubject");
				String emailContent = (String) pi
						.getDataSlotValue("auditeeMessage");
				String otherMailID = (String) pi
						.getDataSlotValue("othersMailId");
				Date processStartTime = new Date(pi.getStartTime());
				Date auditStartDate = new Date(((DateTime) pi
						.getDataSlotValue("auditStartDate")).getTime());
				Date auditEndDate = new Date(((DateTime) pi
						.getDataSlotValue("auditEndDate")).getTime());
				if (emailContent.length() > 3999) {
					emailContent = emailContent.substring(0, 3998);
				}
				if (otherMailID != null && otherMailID.trim().length() > 0) {
					if (otherMailID.length() > 3999) {
						otherMailID = otherMailID.substring(0, 3998);
					}
				} else {
					otherMailID = "-";
				}
				String query = "insert into internal_audit_clone "
						+ "(process_instance_id,audit_type,branch,region,auditor,"
						+ "auditee,regional_incharge,zonal_incharge,ercghead,email_subject,"
						+ "email_body,other_mail_id,process_status,process_starttime,"
						+ "audit_startdate,audit_enddate) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				connection = getDBConnection();
				pstmt = connection.prepareStatement(query);
				pstmt.setLong(1, piID);
				pstmt.setString(2, auditType);
				pstmt.setString(3, branch);
				pstmt.setString(4, region);
				pstmt.setString(5, auditor);
				pstmt.setString(6, auditee);
				pstmt.setString(7, regionalIncharge);
				pstmt.setString(8, zonalIncharge);
				pstmt.setString(9, ercgHead);
				pstmt.setString(10, emailSubject);
				pstmt.setString(11, emailContent);
				pstmt.setString(12, otherMailID);
				pstmt.setString(13, "PI_ACTIVATED");
				pstmt.setTimestamp(14, new java.sql.Timestamp(processStartTime
						.getTime()));
				pstmt.setTimestamp(15, new java.sql.Timestamp(auditStartDate
						.getTime()));
				pstmt.setTimestamp(16, new java.sql.Timestamp(auditEndDate
						.getTime()));
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Exception into saveAuditInfo() : "
					+ e.getMessage() + " for piid : " + piID);
		} finally {
			releaseResources(connection, pstmt);
			if (blSession != null) {
				try {
					blServer.disConnect(blSession);
				} catch (RemoteException e) {
					System.out
							.println("blserver closing error in saveAuditInfo() : "
									+ e.getMessage() + " for piID : " + piID);
				}
			}
		}
	}

	public void setAuditCloseFlag(long piID) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "update internal_audit_clone set process_status = ? where process_instance_id = ?";
		try {
			connection = getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,"PI_COMPLETED");
			pstmt.setLong(2, piID);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception into setAuditCloseFlag() : "
					+ e.getMessage() + " for piid :" + piID);
		} finally {
			releaseResources(connection, pstmt);
		}
	}

	public long dueDateOf_AuditeeReplyStatus_WS(long piID) {
		BLServer blServer = null;
		Session blSession = null;
		long totalDuration = 0;
		int days = 0;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect("rgicl", "rgicl");
			ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
			int cnt = Integer.valueOf(((Long) pi
					.getDataSlotValue("auditeeOverdueCount")).intValue());
			if (cnt == 1) {
				days = Integer.valueOf(((Long) pi
						.getDataSlotValue("leftTATofAuditee")).intValue());
			} else {
				days = 3;
			}
			totalDuration = days * 8 * 60 * 60 * 1000;
			long startDate = (new Date()).getTime();
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(startDate, totalDuration);
			Calendar initDuedate = sbmcal.getDueDate(bcal);
			return initDuedate.getTime().getTime();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (blSession != null) {
				try {
					blServer.disConnect(blSession);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void updateAuditeeTAT(long piID) {
		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect("rgicl", "rgicl");
			ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
			WorkStepInstance wsi = blServer.getWorkStepInstance(blSession,
					piID, "Auditee Reply Status");
			long activationTime = wsi.getActivationTime();
			long complitionTime = wsi.getCompleteTime();
			long consumeHrsByAuditee = getWorkingDaysBetweenTwoDates(new Date(
					activationTime), new Date(complitionTime));
			if (consumeHrsByAuditee == 0) {
				consumeHrsByAuditee = 1;
			} else if (consumeHrsByAuditee >= 5 && consumeHrsByAuditee < 8) {
				consumeHrsByAuditee = 8;
			}
			int leftTAT = Integer.valueOf(((Long) pi
					.getDataSlotValue("leftTATofAuditee")).intValue())
					- (((int) consumeHrsByAuditee) / 8);
			if (leftTAT <= 0) {
				leftTAT = 1;
			}
			pi.updateSlotValue("leftTATofAuditee", new Long(leftTAT));
			pi.save();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (blSession != null) {
				try {
					blServer.disConnect(blSession);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void getHolidays() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String qry = "SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS WHERE HOLIDAYYEAR = (SELECT TO_NUMBER(EXTRACT(YEAR FROM SYSDATE)) FROM DUAL)";
			connection = getDBConnection();
			pstmt = connection.prepareStatement(qry);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				holidayList.add(rset.getDate(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseResources(connection, pstmt, rset);
		}
	}

	private boolean checkHoliday(java.util.Date date) {
		boolean isHoliday = false;
		getHolidays();
		for (int i = 0; i < holidayList.size(); i++) {
			if (df.format(date).equals(df.format(holidayList.get(i)))) {
				isHoliday = true;
				break;
			}
		}
		return isHoliday;
	}

	private boolean checkSecondSaturday(java.util.Date date) {
		boolean isSecondSat = false;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
		if (dayOfWeek == java.util.Calendar.SATURDAY) {
			int weekOfMonth = c.get(java.util.Calendar.WEEK_OF_MONTH);
			if (weekOfMonth == 2 || weekOfMonth == 4) {
				isSecondSat = true;
			}
		}
		return isSecondSat;
	}

	private long exjectHours(java.util.Date startTime, java.util.Date endTime) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(java.util.Calendar.HOUR_OF_DAY, endWorkingHour);
		cal.set(java.util.Calendar.MINUTE, endWorkingMinute);
		cal.set(java.util.Calendar.SECOND, endWorkingSecond);
		java.util.Date startDayEndTime = cal.getTime();
		java.util.Calendar cal2 = java.util.Calendar.getInstance();
		cal2.setTime(endTime);
		cal2.set(java.util.Calendar.HOUR_OF_DAY, startWorkingHour);
		cal2.set(java.util.Calendar.MINUTE, startWorkingMinute);
		cal2.set(java.util.Calendar.SECOND, startWorkingSecond);
		java.util.Date endDayStartTime = cal2.getTime();
		long hours = (timeDiff(startTime, startDayEndTime) + timeDiff(
				endDayStartTime, endTime));
		if (hours < 0) {
			hours = 0;
		}
		return hours;
	}

	private long timeDiff(java.util.Date sDate, java.util.Date eDate) {
		long diff = eDate.getTime() - sDate.getTime();
		long diffHours = diff / (60 * 60 * 1000) % 24;
		if (diffHours < 0) {
			diffHours = 0;
		}
		return diffHours;

	}

	private long getTimeDifference(java.util.Date startTime,
			java.util.Date endTime, int totalDays)
			throws java.text.ParseException {
		long totalHours = 0;
		if (totalDays != 0) {
			if (totalDays == 1) {
				totalHours = timeDiff(startTime, endTime);
			}
			if (totalDays == 2) {
				totalHours = exjectHours(startTime, endTime);
			} else if (totalDays > 2) {
				totalHours = exjectHours(startTime, endTime)
						+ ((totalDays - 2) * totalWorkingHours);
			}
			return totalHours;

		} else {
			return totalHours;
		}

	}

	private long getWorkingDaysWithHours(
			java.util.ArrayList<java.util.Date> workingDaysList,
			java.util.Date inputStartDate, java.util.Date inputEndDate)
			throws java.text.ParseException {
		java.util.Date TATStartTime = null;
		java.util.Date TATEndTime = null;
		if (workingDaysList != null && !workingDaysList.isEmpty()) {
			if (df.format(inputStartDate).equals(
					df.format(workingDaysList.get(0)))) {
				TATStartTime = inputStartDate;
			} else {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.setTime(workingDaysList.get(0));
				cal.set(java.util.Calendar.HOUR_OF_DAY, startWorkingHour);
				cal.set(java.util.Calendar.MINUTE, startWorkingMinute);
				cal.set(java.util.Calendar.SECOND, startWorkingSecond);
				TATStartTime = cal.getTime();
			}
			if (df.format(inputEndDate).equals(
					df.format(workingDaysList.get(workingDaysList.size() - 1)))) {
				TATEndTime = inputEndDate;
			} else {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.setTime(workingDaysList.get(workingDaysList.size() - 1));
				cal.set(java.util.Calendar.HOUR_OF_DAY, endWorkingHour);
				cal.set(java.util.Calendar.MINUTE, endWorkingMinute);
				cal.set(java.util.Calendar.SECOND, endWorkingSecond);
				TATEndTime = cal.getTime();
			}
			return getTimeDifference(TATStartTime, TATEndTime, workingDaysList
					.size());
		}
		return 0;
	}

	private long getWorkingDaysBetweenTwoDates(java.util.Date startDate,
			java.util.Date endDate) throws java.text.ParseException {
		java.util.Calendar startCal = java.util.Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(java.util.Calendar.HOUR_OF_DAY, startWorkingHour);
		startCal.set(java.util.Calendar.MINUTE, startWorkingMinute);
		startCal.set(java.util.Calendar.SECOND, startWorkingSecond);

		java.util.Calendar endCal = java.util.Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.set(java.util.Calendar.HOUR_OF_DAY, endWorkingHour);
		endCal.set(java.util.Calendar.MINUTE, endWorkingMinute);
		endCal.set(java.util.Calendar.SECOND, endWorkingSecond);

		java.util.ArrayList<java.util.Date> workingDaysList = new java.util.ArrayList<java.util.Date>();

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}
		do {
			if (!checkHoliday(startCal.getTime())
					&& !checkSecondSaturday(startCal.getTime())
					&& startCal.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SUNDAY) {
				workingDaysList.add(startCal.getTime());
			}
			startCal.add(java.util.Calendar.DAY_OF_MONTH, 1);
		} while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
		return getWorkingDaysWithHours(workingDaysList, startDate, endDate);
	}
}
