package com.savvion.rcf;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.tdiinc.userManager.Group;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReassignTaskAppSupport {

	private static final String host = "10.185.6.109";
	private static final String from = "RGICLApplicationSupport@reliancegeneral.co.in";
	//private static final String jndiName = "jdbc/SBMCommonDB";
	private static final String jndiName = "jdbc/CustomDB";
	private static final String BL_USER = "rgicl";
	private static final String BL_PASSWORD = "rgicl";
	ArrayList holidayList;
	SimpleDateFormat localDateFormat;
	DateFormat df;
	static final int startWorkingHour = 10;
	static final int startWorkingMinute = 0;
	static final int startWorkingSecond = 0;
	static final int endWorkingHour = 18;
	static final int endWorkingMinute = 0;
	static final int endWorkingSecond = 0;
	static final int totalWorkingHours = 8;

	public ReassignTaskAppSupport() {
		holidayList = new ArrayList();
		localDateFormat = new SimpleDateFormat("HH:mm:ss");
		df = new SimpleDateFormat("dd-MM-yyyy");
	}

	public String reAssignTask(String systemName, String piID,
			String appSupportRmk, String changeStatus,String quotationAmount, String currentUser,
			String ipAddress) {//Added quotationAmount by Deepak
		BLServer blServer;
		com.savvion.sbm.bizlogic.util.Session blSession;
		Connection connection;
		CallableStatement cstmt;
		ResultSet rset;
		String output;
		blServer = null;
		blSession = null;
		connection = null;
		cstmt = null;
		rset = null;
		output = null;
		
		try {
			System.out.println("systemName: "+systemName);
			System.out.println("piID: "+piID);
			System.out.println("appSupportRmk: "+appSupportRmk);
			System.out.println("changeStatus: "+changeStatus);
			System.out.println("quotationAmount: "+quotationAmount);
			System.out.println("currentUser: "+currentUser);
			System.out.println("ipAddress: "+ipAddress);
			
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect("rgicl", "rgicl");
			if (validate(systemName) && validate(piID) && validate(currentUser)
					&& validate(ipAddress) && validate(changeStatus)
					&& validate(appSupportRmk)) {
				ProcessInstance pi = blServer.getProcessInstance(blSession,
						Long.parseLong(piID));
				WorkStepInstance wsi = pi.getWorkStepInstance("AppSupport");
				WorkItemList wli = wsi.getWorkItemList();
				Vector wiList = (Vector) wli.getList();
				Vector pfmrs = getPerformers(systemName);
				if (pfmrs != null && !pfmrs.isEmpty() && wiList != null
						&& !wiList.isEmpty()) {
					if (pfmrs.size() > 1) {
						for (int j = 0; j < wiList.size(); j++) {
							WorkItem wi = (WorkItem) wiList.get(j);
							wi.makeAvailable(pfmrs);
							wi.save();
						}
					} else {
						assignTasktoUser(blSession, wiList, (String) pfmrs
								.get(0));
					}
					String list = pfmrs.toString().replace("[", "").replace(
							"]", "").replace(", ", ",");
					if (validate(list)) {
						pi.updateSlotValue("AppsupportFinalStatus",
								changeStatus.trim());
						pi.updateSlotValue("AppSupportRemark", appSupportRmk
								.trim());
						if(quotationAmount != null) {
							pi.updateSlotValue("Quotation",quotationAmount.trim());
						}
						String qry = "call TECHDESK_ADDL2L3_DETAIL(?,?,?,?,?,?,?,?,?,?)";
						connection = getDBConnection();
						cstmt = connection.prepareCall(qry);
						cstmt.setLong(1, Long.parseLong(piID));
						cstmt.setString(2, (String) pi
								.getDataSlotValue("TicketNo"));
						cstmt.setString(3, currentUser);
						cstmt.setString(4, list.trim());
						cstmt.setString(5, changeStatus.trim());
						cstmt.setString(6, appSupportRmk.trim());
						cstmt.setString(7, ipAddress);
						cstmt.registerOutParameter(8, 12);
						cstmt.registerOutParameter(9, 12);
						cstmt.registerOutParameter(10, -10);
						cstmt.executeQuery();
						/*Calendar cal;
						for (rset = (ResultSet) cstmt.getObject(10); rset
								.next(); holidayList.add(cal.getTime())) {
							Date holiday = new Date(rset.getDate(1).getTime());
							Calendar holidayCal = Calendar.getInstance();
							cal = Calendar.getInstance();
							holidayCal.setTime(holiday);
							cal.set(1, holidayCal.get(1));
							cal.set(2, holidayCal.get(2));
							cal.set(5, holidayCal.get(5));
						}

						getUpdateAging(Long.parseLong(piID), new Date(cstmt
								.getString(9)), new Date(cstmt.getString(8)));*/
						Vector pfmrsMailIDs = getUsersMailID(pfmrs);
						if (pfmrsMailIDs != null && !pfmrsMailIDs.isEmpty()) {
							sendMail(pfmrsMailIDs, pi);
						}
						output = "ReAssign Successfully!";
					}
				}
			} else {
				output = "Unsuccessfully Assign Task.";
			}
		} catch (Exception e) {
			output = (new StringBuilder("Unsuccessfully Assign Task due to --"))
					.append(e.getMessage()).toString();
			throw new RuntimeException((new StringBuilder(
					"Unsuccessfully Assign Task due to ")).append(
					e.getMessage()).toString());
		} finally {
			if (blServer != null) {
				try {
					blServer.disConnect(blSession);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			releaseResouce(rset, cstmt, connection);
		}
		return output;
	}

	public Vector getPerformers(String systemName) {
		Connection connection;
		PreparedStatement pstmt;
		ResultSet rset;
		Vector pfmrs;
		connection = null;
		pstmt = null;
		rset = null;
		pfmrs = null;
		try {
			if (validate(systemName)) {
				Realm rlm = UserManager.getDefaultRealm();
				String systems[] = systemName.split(",");
				connection = getDBConnection();
				boolean isEnterdUserID = false;
				pfmrs = new Vector();
				for (int i = 0; i < systems.length; i++) {
					User u = rlm.getUser(systems[i].trim());
					if (u != null) {
						isEnterdUserID = true;
						pfmrs.add(systems[i].trim());
					}
				}

				if (!isEnterdUserID) {
					String systemQry = "SELECT SYSTEM_GROUPNAME FROM TECHDESK_L2GROUP WHERE UPPER(SYSTEM_NAME) = UPPER(?"
							+ ")";
					pstmt = connection.prepareStatement(systemQry);
					for (int i = 0; i < systems.length; i++) {
						if (validate(systems[i])) {
							String grpName = null;
							pstmt.setString(1, systems[i].trim());
							for (rset = pstmt.executeQuery(); rset.next();) {
								grpName = rset.getString(1);
							}

							if (validate(grpName)
									&& rlm.getGroup(grpName.trim()) != null) {
								String grpMembers[] = rlm.getGroup(
										grpName.trim()).getMemberNames();
								for (int ij = 0; ij < grpMembers.length; ij++) {
									User u = rlm.getUser(grpMembers[ij]);
									if (u != null) {
										pfmrs.add(grpMembers[ij]);
									}
								}

							}
						}
					}

				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			releaseResouce(rset, pstmt, connection);
		}
		return pfmrs;
	}

	private Vector getUsersMailID(Vector userIdsList) {
		Vector pfmrsMailIDs = null;
		if (userIdsList != null && !userIdsList.isEmpty()) {
			Realm rlm = UserManager.getDefaultRealm();
			pfmrsMailIDs = new Vector();
			for (int i = 0; i < userIdsList.size(); i++) {
				User u = rlm.getUser((String) userIdsList.get(i));
				if (u != null) {
					String mailID = u.getAttribute("email");
					if (validate(mailID) && mailID.trim().contains("@")) {
						pfmrsMailIDs.add(mailID);
					}
				}
			}

		}
		return pfmrsMailIDs;
	}

	private void assignTasktoUser(
			com.savvion.sbm.bizlogic.util.Session blSession, Vector wiList,
			String pfmr) throws Exception {
		List wiids = new ArrayList();
		boolean isAlreadyAssigned = false;
		for (int j = 0; j < wiList.size(); j++) {
			WorkItem wi = (WorkItem) wiList.get(j);
			isAlreadyAssigned = wi.isAssigned();
			wiids.add(Long.valueOf(wi.getID()));
		}

		if (isAlreadyAssigned) {
			WorkItem.reAssign(blSession, wiids, pfmr);
		} else {
			WorkItem.assign(blSession, wiids, pfmr, false);
		}
	}

	private String mailBody(ProcessInstance pi) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,s"
						+ "ans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:"
						+ "#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadd"
						+ "ing:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear Member,");
		sb.append("<br/><br/>Your Call details are as below -: ");
		sb.append("<table class=\"tablecls\">");
		sb
				.append((new StringBuilder(
						"<tr><td class=\"tabletitle\">Ticket no : </td><td class=\"tablecell\">"))
						.append((String) pi.getDataSlotValue("TicketNo"))
						.append("</td></tr>").toString());
		sb
				.append((new StringBuilder(
						"<tr><td class=\"tabletitle\">Application: </td><td class=\"tablecell\">"))
						.append((String) pi.getDataSlotValue("ApplicationType"))
						.append("</td></tr>").toString());
		sb
				.append((new StringBuilder(
						"<tr><td class=\"tabletitle\">Category: </td><td class=\"tablecell\">"))
						.append((String) pi.getDataSlotValue("IssueRequestId"))
						.append("</td></tr></table>").toString());
		sb
				.append("<br/><br/>Click <b><a href=\"http://bpm.reliancegeneral.co.in:18793/sbm/bpmporta"
						+ "l/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the syst"
						+ "em");
		sb.append("<br/><br/>Thank you,");
		sb.append("<br/>Reliance Application Support Team");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED "
						+ "MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nb"
						+ "sp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private void sendMail(Vector pfmrsMailIDs, ProcessInstance pi)
			throws Exception {
		String recipientTO[] = (String[]) pfmrsMailIDs
				.toArray(new String[pfmrsMailIDs.size()]);
		String mailBody = mailBody(pi);
		HashMap hm = new HashMap();
		hm.put("TO", recipientTO);
		hm.put("FROM", "RGICLApplicationSupport@reliancegeneral.co.in");
		hm.put("SUBJECT", (new StringBuilder("Ticket Number : ")).append(
				(String) pi.getDataSlotValue("TicketNo")).append(
				" assign to you.").toString());
		hm.put("BODY", mailBody);
		sendMutliple_To(hm);
	}

	private Connection getDBConnection() {
		Connection connection = null;
		try {
			connection = ((DataSource) (new InitialContext())
					.lookup("jdbc/SBMCommonDB")).getConnection();
		} catch (Exception ex) {
			System.out.println((new StringBuilder(
					"Error Getting DBConnection : ")).append(ex.getMessage())
					.toString());
		}
		return connection;
	}

	private void releaseResouce(ResultSet rset, PreparedStatement pstmt,
			Connection conn) {
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
			System.out.println((new StringBuilder(
					"Error while ReleaseResouce : ")).append(ex.getMessage())
					.toString());
		}
	}

	private void sendMutliple_To(HashMap properties) {
		try {
			if (properties != null && !properties.isEmpty()) {
				String to[] = (String[]) properties.get("TO");
				String from = properties.get("FROM").toString();
				String subject = properties.get("SUBJECT").toString();
				String body = properties.get("BODY").toString();
				Properties props = new Properties();
				props.put("mail.smtp.host", "10.185.6.109");
				Session session = Session.getInstance(props);
				Transport bus = session.getTransport("smtp");
				bus.connect();
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress addressTo[] = (InternetAddress[]) null;
				if (to.length > 0) {
					addressTo = new InternetAddress[to.length];
					int counter = 0;
					String as[];
					int j = (as = to).length;
					for (int i = 0; i < j; i++) {
						String recipient = as[i];
						if (recipient != null && recipient.contains("@")) {
							addressTo[counter] = new InternetAddress(recipient
									.trim());
							counter++;
						}
					}

					msg.setRecipients(javax.mail.Message.RecipientType.TO,
							addressTo);
					msg.setSubject(subject);
					msg.setSentDate(new Date());
					MimeMultipart multipart = new MimeMultipart("related");
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(body, "text/html");
					multipart.addBodyPart(htmlPart);
					msg.setContent(multipart);
					msg.saveChanges();
					bus.sendMessage(msg, msg.getAllRecipients());
					bus.close();
				}
			}
		} catch (Exception exception) {
			System.out.println((new StringBuilder("Mail Sent Exception : "))
					.append(exception.getMessage()).toString());
		}
	}

	private boolean validate(String val) {
		boolean isValid = false;
		if (val != null && val.trim().length() > 0) {
			isValid = true;
		}
		return isValid;
	}

	private void getUpdateAging(long piID, Date lastUpdateTime,
			Date currentUpdateTime) {
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rset;
		String query;
		conn = null;
		pstmt = null;
		rset = null;
		query = "UPDATE TECHDESK_REASSIGN_DTL SET AGING  = ? WHERE PIID = ? AND TO_CHAR(PROCESSTI"
				+ "ME,'DD-MM-YYYY HH24:MI:SS') = ?";
		try {
			if (piID != 0L && lastUpdateTime != null
					&& currentUpdateTime != null) {
				conn = getDBConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setLong(1, getWorkingDaysBetweenTwoDates(lastUpdateTime,
						currentUpdateTime));
				pstmt.setLong(2, piID);
				pstmt.setString(3,
						(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"))
								.format(new java.sql.Date(currentUpdateTime
										.getTime())));
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseResouce(rset, pstmt, conn);
		}
	}

	private boolean checkHoliday(Date date) {
		boolean isHoliday = false;
		for (int i = 0; i < holidayList.size(); i++) {
			if (!df.format(date).equals(df.format((Date) holidayList.get(i)))) {
				continue;
			}
			isHoliday = true;
			break;
		}

		return isHoliday;
	}

	private boolean checkSecondSaturday(Date date) {
		boolean isSecondSat = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(7);
		if (dayOfWeek == 7) {
			int weekOfMonth = c.get(4);
			if (weekOfMonth == 2 || weekOfMonth == 4) {
				isSecondSat = true;
			}
		}
		return isSecondSat;
	}

	private long exjectHours(Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(11, 18);
		cal.set(12, 0);
		cal.set(13, 0);
		Date startDayEndTime = cal.getTime();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endTime);
		cal2.set(11, 10);
		cal2.set(12, 0);
		cal2.set(13, 0);
		Date endDayStartTime = cal2.getTime();
		long hours = timeDiff(startTime, startDayEndTime)
				+ timeDiff(endDayStartTime, endTime);
		if (hours < 0L) {
			hours = 0L;
		}
		return hours;
	}

	private long timeDiff(Date sDate, Date eDate) {
		long diff = eDate.getTime() - sDate.getTime();
		long diffHours = (diff / 0x36ee80L) % 24L;
		if (diffHours < 0L) {
			diffHours = 0L;
		}
		return diffHours;
	}

	private long getTimeDifference(Date startTime, Date endTime, int totalDays)
			throws ParseException {
		long totalHours = 0L;
		if (totalDays != 0) {
			if (totalDays == 1) {
				totalHours = timeDiff(startTime, endTime);
			}
			if (totalDays == 2) {
				totalHours = exjectHours(startTime, endTime);
			} else if (totalDays > 2) {
				totalHours = exjectHours(startTime, endTime)
						+ (long) ((totalDays - 2) * 8);
			}
			return totalHours;
		} else {
			return totalHours;
		}
	}

	private long getWorkingDaysWithHours(ArrayList workingDaysList,
			Date inputStartDate, Date inputEndDate) throws ParseException {
		Date TATStartTime = null;
		Date TATEndTime = null;
		if (workingDaysList != null && !workingDaysList.isEmpty()) {
			if (df.format(inputStartDate).equals(
					df.format((Date) workingDaysList.get(0)))) {
				TATStartTime = inputStartDate;
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime((Date) workingDaysList.get(0));
				cal.set(11, 10);
				cal.set(12, 0);
				cal.set(13, 0);
				TATStartTime = cal.getTime();
			}
			if (df.format(inputEndDate).equals(
					df.format((Date) workingDaysList
							.get(workingDaysList.size() - 1)))) {
				TATEndTime = inputEndDate;
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime((Date) workingDaysList
						.get(workingDaysList.size() - 1));
				cal.set(11, 18);
				cal.set(12, 0);
				cal.set(13, 0);
				TATEndTime = cal.getTime();
			}
			return getTimeDifference(TATStartTime, TATEndTime, workingDaysList
					.size());
		} else {
			return 0L;
		}
	}

	private long getWorkingDaysBetweenTwoDates(Date startDate, Date endDate)
			throws ParseException {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(11, 10);
		startCal.set(12, 0);
		startCal.set(13, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.set(11, 18);
		endCal.set(12, 0);
		endCal.set(13, 0);
		ArrayList workingDaysList = new ArrayList();
		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}
		do {
			if (!checkHoliday(startCal.getTime())
					&& !checkSecondSaturday(startCal.getTime())
					&& startCal.get(7) != 1) {
				workingDaysList.add(startCal.getTime());
			}
			startCal.add(5, 1);
		} while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
		return getWorkingDaysWithHours(workingDaysList, startDate, endDate);
	}
}
