package com.rgicl.savvion.TechDesk;

import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.PropertyConfigurator;

import techdesk.email.EmailSender;
import bitly.services.SendBitly;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import com.savvion.sbm.util.logger.LoggerManager;
import com.savvion.sbm.util.logger.SBMLogger;
import com.tdiinc.userManager.JDBCGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;

public class ApproverUtil {
	private BLServer blserver = null;
	private BLClientUtil blc = null;
	private Session blsession = null;
	private static String bl_user = "rgicl";
	private static String bl_password = "rgicl";
	private Connection connection;

	public ApproverUtil() {
	}

	private Connection getCalldeskSQLDB() throws Exception {
		String dbip = "";
		String dbuser = "";
		String dbpass = "";
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (ip.contains("10.65.15.")) {
				dbip = "10.65.15.98";
				dbuser = "calldesk";
				dbpass = "calldesk";
			} else {
				dbip = "RGIRMSCDDB";
				dbuser = "calldesk";
				dbpass = "calldesk";
			}
			connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=CalldeskManagement", dbuser, dbpass);
		} catch (Exception e) {
			throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + e.getMessage(), e);
		}
		return connection;
	}

	public long[] getAllTat(String appName, String category, String subCatetory, String TicketNo) {
		long[] tats = new long[3];
		Connection connection = null;
		CallableStatement proc_stmt = null;
		ResultSet rs = null;
		// PreparedStatement pstmt = null;
		try {
			connection = getCalldeskSQLDB();
			proc_stmt = connection.prepareCall("{ call usp_Savvion_GetApplicationTAT(?,?,?,?) }");
			proc_stmt.setString(1, appName);
			proc_stmt.setString(2, category);
			proc_stmt.setString(3, subCatetory);
			proc_stmt.setString(4, TicketNo);
			rs = proc_stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					tats[0] = (rs.getLong("ApproverTAT")) * 60 * 60;
					tats[1] = rs.getLong("ApproverTAT") * 60 * 60;
					tats[2] = rs.getLong("AppsupportTAT") * 60 * 60;
				}
			}
			proc_stmt.close();
			rs.close();
			connection.close();
			// System.out.println("approver1Tat " + approver1Tat);
			// System.out.println("supportTeamTat " + supportTeamTat);
		} catch (Exception ex) {
			System.out.println("Error in getting TAT Details" + ex.toString());
		} finally {
			try {
				if (proc_stmt != null) {
					try {
						proc_stmt.close();
					} catch (Exception ex) {
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception ex) {
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {
			}
		}

		return tats;
	}

	public void sendAssignEmail(long piid, String wsName) {
		PropertyConfigurator.configure(System.getProperty("sbm.home") + "/conf/sbmlog.conf");
		SBMLogger log = LoggerManager.self().createLogger("WebService", "sbmlog.conf", "WebServiceMessages", this.getClass().getClassLoader());
		try {
			log.info("Before sending email Process Instance Id " + piid + " wsname " + wsName);
			blserver = blc.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			String approverName = "Approver";
			String mailToId = "";
			String ticketNo = (String) pi.getDataSlotValue("TicketNo");
			String appName = (String) pi.getDataSlotValue("ApplicationType");
			String callCategory = (String) pi.getDataSlotValue("IssueRequestId");
			String callSubCat = (String) pi.getDataSlotValue("IssueReqSubTypeID");
			String callTime = (String) pi.getDataSlotValue("CallLogDate");
			String callcreatedBy = (String) pi.getDataSlotValue("CallCreatedBy");
			String teamname = (String) pi.getDataSlotValue("TeamName");

			if (wsName != null && wsName.equalsIgnoreCase("Approver")) {
				String appID = (String) pi.getDataSlotValue("ApproverID");
				HashMap dsValues = new HashMap();
				dsValues.put("ApproverUserId", appID);
				dsValues.put("overdueflag", false);
				pi.updateSlotValue(dsValues);
				pi.save();

				if (wsName.equalsIgnoreCase("Approver")) {
					approverName = (String) pi.getDataSlotValue("ApproverName");
					mailToId = (String) pi.getDataSlotValue("ApproverEmailID");
				}

				if (wsName.equalsIgnoreCase("VerifyApproval")) {
					approverName = (String) pi.getDataSlotValue("Approver2Name");
					mailToId = (String) pi.getDataSlotValue("Approver2Email");
				}
			}
			String AppSupportGroupName = (String) pi.getDataSlotValue("AppSupportGroupName");

			if (wsName != null && wsName.equalsIgnoreCase("AppSupport") && appName != null && teamname != null && !teamname.equalsIgnoreCase("IT")) {
				approverName = " User";
				UserManager um = new UserManager();
				JDBCRealm r = (JDBCRealm) um.getDefaultRealm();
				JDBCGroup g = (JDBCGroup) r.getGroup(AppSupportGroupName);
				if (g != null) {
					String users[] = g.getAllUserMemberNames();
					if (users != null && users.length > 0) {
						for (int i = 0; i < users.length; i++) {
							JDBCUser u = (JDBCUser) r.getUser(users[i]);
							if (i != (users.length - 1)) {
								mailToId = mailToId + u.getAttribute("EMAIL") + ",";
							} else {
								mailToId = mailToId + u.getAttribute("EMAIL");
							}
						}
					}
				} else {
					JDBCUser u1 = (JDBCUser) r.getUser(AppSupportGroupName);
					if (u1 != null) {
						mailToId = mailToId + u1.getAttribute("EMAIL") + ",";
					}
				}
			}

			String userMailID = (String) pi.getDataSlotValue("UserEmailID");
			String mailFrom = "rgicl.applnsupport@reliancegeneral.co.in";

			long countEsc = ((Long) pi.getDataSlotValue("count")).longValue();

			// if (countEsc == 1) {
			StringBuffer sb = new StringBuffer();
			sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
			if (!wsName.equalsIgnoreCase("AppSupport")) {
				sb.append("Dear Approver " + approverName + ",");
				sb.append("<br/><br/>A call has been registered at CallDesk,  waiting for your approval.");
			} else {
				sb.append("Dear Team" + approverName + ",");
				sb.append("<br/><br/>Call is waiting for your Resolution/Processing");
			}
			sb.append("<br/><br/>Following are the call details:");
			sb.append("<table class=\"tablecls\"><tr><td class=\"tabletitle\">Ticket no: </td><td class=\"tablecell\">" + ticketNo + "</td></tr>");
			sb.append("<tr><td class=\"tabletitle\">Call Created By: </td><td class=\"tablecell\">" + callcreatedBy + "</td>");
			sb.append("<td class=\"tabletitle\">User Mail ID: </td><td class=\"tablecell\">" + userMailID + "</td></tr>");
			sb.append("<tr><td class=\"tabletitle\">Call Log Date: </td><td class=\"tablecell\">" + callTime + "</td></tr>");
			sb.append("<tr><td class=\"tabletitle\">Application : </td><td class=\"tablecell\">" + appName + "</td>");
			sb.append("<td class=\"tabletitle\">Category Name: </td><td class=\"tablecell\">" + callCategory + "</td></tr>");
			sb.append("<tr><td class=\"tabletitle\">Sub Category Name: </td><td class=\"tablecell\">" + callSubCat + "</td></tr></table>");
			sb.append("<br/><br/>Request you to do the needful. Click <b><a href=\"http://bpm.reliancegeneral.co.in/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
			sb.append("<br/><br/>Thank you,");
			sb.append("<br/>Reliance Application Support Team");
			sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("<br/></body></html>");

			HashMap hm = new HashMap();
			hm.put("TO", mailToId);
			hm.put("FROM", mailFrom);
			if (wsName.equalsIgnoreCase("AppSupport")) {
				hm.put("SUBJECT", "Calldesk Ticket #" + ticketNo + " your action required");
			} else {
				hm.put("SUBJECT", "Calldesk Ticket #" + ticketNo + " your approval required");
			}
			hm.put("BODY", sb.toString());
			hm.put("PTID", new Double(6308));
			hm.put("PIID", new Double(piid));
			hm.put("WORKSTEP_NAME", wsName);
			hm.put("WORKITEM_NAME", "AssignEmail");
			// ADDED BY DEEPAK FOR CC SUPRIYA
			if (appName != null && callCategory != null && appName.equals("IT Infra Requests") && callCategory.equals("Network Link")) {
				log.info("Before sending email adding CC supriya.chawan@relianceada.com Process Instance Id " + piid + " wsname " + wsName);
				hm.put("CC", "supriya.chawan@relianceada.com");
			}

			EmailSender ems = new EmailSender();
			// ems.send(hm);
			if (teamname != null && !teamname.toUpperCase().startsWith("CQR") && !teamname.startsWith("PolicyVetting")) {
				// ems.send(hm);
			}
			log.info("Process Instance Id " + piid + " wsname " + wsName + mailToId);
			if (wsName.equalsIgnoreCase("Approver") || wsName.equalsIgnoreCase("VerifyApproval")) {
				SendBitly sbitly = new SendBitly();
				// sbitly.sendBitlySMS(ticketNo, wsName);
				log.info("SMS Sent Successfully " + ticketNo);
			}
			// }
			blserver.disConnect(blsession);
		} catch (Exception e) {
			log.error("Error in class ApproverUtil method sendAssignEmail for piid:[" + piid + "] \n" + e.getMessage());
		}
	}

	public void sendOverDueEmail(long piid, String wsName) {
		PropertyConfigurator.configure(System.getProperty("sbm.home") + "/conf/sbmlog.conf");
		SBMLogger log = LoggerManager.self().createLogger("WebService", "sbmlog.conf", "WebServiceMessages", this.getClass().getClassLoader());
		try {
			blserver = blc.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);
			WorkStepInstance wsi = blserver.getWorkStepInstance(blsession, piid, wsName);
			WorkItemList wli = wsi.getWorkItemList();
			Vector wiList = (Vector) wli.getList();

			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			long cnt = ((Long) pi.getDataSlotValue("count")).longValue();

			String approverName = "Approver";
			String mailToId = "";
			String mgrMailID = "";

			if (wsName.equalsIgnoreCase("Approver")) {
				approverName = (String) pi.getDataSlotValue("ApproverName");
				mailToId = (String) pi.getDataSlotValue("ApproverEmailID");
				mgrMailID = (String) pi.getDataSlotValue("app1MgrMail");
			}

			if (wsName.equalsIgnoreCase("VerifyApproval")) {
				approverName = (String) pi.getDataSlotValue("Approver2Name");
				mailToId = (String) pi.getDataSlotValue("Approver2Email");
				mgrMailID = (String) pi.getDataSlotValue("app2MgrMail");
			}
			String teamName = (String) pi.getDataSlotValue("TeamName");
			String ticketNo = (String) pi.getDataSlotValue("TicketNo");
			String appName = (String) pi.getDataSlotValue("ApplicationType");
			String callCategory = (String) pi.getDataSlotValue("IssueRequestId");
			String callSubCat = (String) pi.getDataSlotValue("IssueReqSubTypeID");
			String userMailID = (String) pi.getDataSlotValue("UserEmailID");
			String mailFrom = "rgicl.applnsupport@reliancegeneral.co.in";

			String ccAddress = "";

			if (userMailID != null && userMailID.contains("@")) {
				ccAddress = userMailID;
			}

			if (mgrMailID != null && mgrMailID.contains("@")) {
				ccAddress = ccAddress + "," + mgrMailID;
			}

			if (cnt == 1) {
				long dtNow = (new java.util.Date()).getTime();
				long totalDuration = 14400000; // in 4 hours in milliseconds
				SBMCalendar.init(null);
				SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
				BizCalendar bcal = new BizCalendar(dtNow, totalDuration);
				Calendar initDuedate = sbmcal.getDueDate(bcal);
				Date dueDt = initDuedate.getTime();

				for (int i = 0; i < wiList.size(); i++) {
					WorkItem wi = (WorkItem) wiList.get(i);
					wi.setDueDate(dueDt.getTime());
					wi.save();
					wi.refresh();
				}
				cnt = cnt + 1L;
				pi.updateSlotValue("count", new Long(cnt));
				pi.save();

				StringBuffer sb = new StringBuffer();
				sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
				sb.append("Dear Approver " + approverName + ",");
				sb.append("<br/><br/>Your actual Approval TAT is going to finish within 4 hours for the call ticket number# " + ticketNo + ".");
				sb.append("<br/><br/>Following are the call details:");
				sb.append("<table class=\"tablecls\">");
				sb.append("<tr><td class=\"tabletitle\">Application : </td><td class=\"tablecell\">" + appName + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Category Name: </td><td class=\"tablecell\">" + callCategory + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Actual TAT: </td><td class=\"tablecell\">14 hours </td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Extended TAT: </td><td class=\"tablecell\"> 4 hours </td></tr></table>");
				sb.append("<br/><br/>Please act on the same ASAP. Click <b><a href=\"http://bpm.reliancegeneral.co.in/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
				sb.append("<br/><br/>Thank you,");
				sb.append("<br/>Reliance Application Support Team");
				sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append("<br/></body></html>");

				HashMap hm = new HashMap();
				hm.put("FROM", mailFrom);
				hm.put("TO", mailToId);
				// hm.put("CC", ccAddress);
				hm.put("SUBJECT", "CALLDESK Ticket# " + ticketNo + " will get AutoClosed after 4 Hours");
				hm.put("BODY", sb.toString());
				hm.put("PTID", new Double(6308));
				hm.put("PIID", new Double(piid));
				hm.put("WORKSTEP_NAME", wsName);
				hm.put("WORKITEM_NAME", "OverdueEmail");
				EmailSender ems = new EmailSender();
				// ems.send(hm);
				if (teamName != null && !teamName.toUpperCase().startsWith("CQR")) {
					// ems.send(hm);
				}
			} else {
				/*
				 * HashMap dsValues = new HashMap();
				 * dsValues.put("ApprovalFinalStatus", "AutoClosed");
				 * dsValues.put("AutoCloseRemark",
				 * "As Approver didn't act on the Ticket on defined TAT, the ticket got closed automatically"
				 * ); dsValues.put("skipAppSupport", true);
				 * dsValues.put("skipUpCallDeskData", true);
				 * dsValues.put("skipVerifyApproval", true);
				 * dsValues.put("skipApproverDocAdapter", true);
				 * dsValues.put("skipSupportDocAdapter", true);
				 * pi.updateSlotValue(dsValues); pi.save(); /* for (int i = 0; i
				 * < wiList.size(); i++) { WorkItem wi = (WorkItem)
				 * wiList.get(i); wi.complete(); } wsi.complete();
				 */

				if (cnt == 2) {
					StringBuffer sb = new StringBuffer();
					sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
					sb.append("Dear Approver " + approverName + ",");
					sb.append("<br/><br/>Please take necessary action on the call ticket number " + ticketNo + ".<br/>As the TAT for the call is finished.");
					sb.append("<br/><br/>Following are the call details:");
					sb.append("<table class=\"tablecls\">");
					sb.append("<tr><td class=\"tabletitle\">Application : </td><td class=\"tablecell\">" + appName + "</td></tr>");
					sb.append("<tr><td class=\"tabletitle\">Category Name: </td><td class=\"tablecell\">" + callCategory + "</td></tr>");
					sb.append("<tr><td class=\"tabletitle\">Actual TAT: </td><td class=\"tablecell\">14 hours </td></tr>");
					sb.append("<tr><td class=\"tabletitle\">Extended TAT: </td><td class=\"tablecell\"> 4 hours </td></tr></table>");
					sb.append("<br/><br/>Please act on the same ASAP. Click <b><a href=\"http://bpm.reliancegeneral.co.in/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
					sb.append("<br/><br/>Thank you,");
					sb.append("<br/>Reliance Application Support Team");
					sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					sb.append("<br/></body></html>");

					HashMap hm = new HashMap();
					hm.put("FROM", mailFrom);
					hm.put("TO", mailToId);
					// hm.put("CC", ccAddress);
					hm.put("SUBJECT", "CALLDESK Ticket# " + ticketNo + " TAT Finished");
					hm.put("BODY", sb.toString());
					hm.put("PTID", new Double(6308));
					hm.put("PIID", new Double(piid));
					hm.put("WORKSTEP_NAME", wsName);
					hm.put("WORKITEM_NAME", "OverdueEmail");

					EmailSender ems = new EmailSender();
					// ems.send(hm);
					if (teamName != null && !teamName.toUpperCase().startsWith("CQR")) {
						// ems.send(hm);
					}
				}

				/*
				 * QueryService qs = new QueryService(blsession);
				 * BSWorkItemFilter bfil = new BSWorkItemFilter("bfil");
				 * bfil.setFetchSize(QSFilter.ALL_DATA_ROWS); ArrayList
				 * usersList = new ArrayList();
				 * usersList.add(BSFilter.ALL_USERS); bfil.setUsers(usersList);
				 * StringBuffer sb = new StringBuffer();
				 * sb.append("BSWI.PROCESS_TEMPLATE_ID=");
				 * sb.append(blserver.getProcessTemplate(blsession, new
				 * String("TechDesk")).getID());
				 * sb.append(" AND BSWI.WORKSTEP_NAME='Approver'");
				 * bfil.setCondition(sb.toString()); BSWorkItem bswi =
				 * qs.getBSWorkItem(); BSWorkItemRS bsrs =
				 * bswi.getAssignedInstances(bfil); ArrayList ls =
				 * (ArrayList)bsrs.getList(); if(ls!=null && !ls.isEmpty()){
				 * for(int i=0; i<ls.size(); i++){ BSWorkItemData bwi =
				 * (BSWorkItemData)ls.get(i);
				 * if(blserver.isWorkItemExist(blsession, bwi.getID())){
				 * WorkItem wi = blserver.getWorkItem(blsession, bwi.getID());
				 * ProcessInstance _pi = blserver.getProcessInstance(blsession,
				 * wi.getProcessInstanceID()); long _cnt = ((Long)
				 * _pi.getDataSlotValue("count")).longValue(); long dueDt =
				 * wi.getDueDate(); Date now = new Date();
				 * if((now.getTime()>=dueDt) && _cnt>=2){ if(wi.isActivated()){
				 * HashMap _dsValues = new HashMap();
				 * _dsValues.put("ApprovalFinalStatus", "AutoClosed");
				 * _dsValues.put("AutoCloseRemark",
				 * "As Approver didn't act on the Ticket on defined TAT, the ticket got closed automatically"
				 * ); _dsValues.put("skipAppSupport", true);
				 * _dsValues.put("skipUpCallDeskData", true);
				 * _dsValues.put("skipVerifyApproval", true);
				 * _dsValues.put("skipApproverDocAdapter", true);
				 * _dsValues.put("skipSupportDocAdapter", true);
				 * _pi.updateSlotValue(_dsValues); _pi.save(); WorkStepInstance
				 * _wsi = blserver.getWorkStepInstance(blsession,
				 * wi.getProcessInstanceID(), wsName); _wsi.complete(); } } } }
				 * }
				 */
			}
			blserver.disConnect(blsession);
		} catch (Exception ex) {
			log.error("Error in class ApproverUtil method sendOverDueEmail for piid :[" + piid + "]\n" + ex.getMessage());
		}
	}

	public void sendAppSupportOverDueEmail(long piid) {
		PropertyConfigurator.configure(System.getProperty("sbm.home") + "/conf/sbmlog.conf");
		SBMLogger log = LoggerManager.self().createLogger("WebService", "sbmlog.conf", "WebServiceMessages", this.getClass().getClassLoader());
		try {
			log.info("TECHDESK::APPROVERUTIL::SENDAPPSUPPORTOVERDUEEMAIL");
			blserver = blc.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);
			long totalDuration = 4 * 14400000; // in 4 hours in milliseconds
			WorkStepInstance wsi = blserver.getWorkStepInstance(blsession, piid, "AppSupport");
			WorkItemList wli = wsi.getWorkItemList();
			Vector wiList = (Vector) wli.getList();

			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			long cnt = ((Long) pi.getDataSlotValue("appSupportCount")).longValue();

			String approverName = "";
			String mgrid = "";
			String mgrMailID = "";

			approverName = (String) pi.getDataSlotValue("supportMgrName");
			mgrid = (String) pi.getDataSlotValue("supportMgrID");
			mgrMailID = (String) pi.getDataSlotValue("supportMgrMail");
			String supportMgr2 = (String) pi.getDataSlotValue("supportMgr2");
			String ticketZone = (String) pi.getDataSlotValue("Zone");
			String ticketLoggedDate = (String) pi.getDataSlotValue("CallLogDate");
			String ticketAssignedToAppSupport = (String) pi.getDataSlotValue("AppSupportAssignedDt");

			String secondMgrEmail = "";
			String teamName = (String) pi.getDataSlotValue("TeamName");
			if (teamName != null && (teamName.contains("ZAM") || teamName.equalsIgnoreCase("Admin1") || teamName.equalsIgnoreCase("Admin3") || teamName.equalsIgnoreCase("Admin5") || teamName.equalsIgnoreCase("Admin6") || teamName.equalsIgnoreCase("Admin7") || teamName.equalsIgnoreCase("Admin4"))) {
				if (cnt > 1 && (teamName != null && (teamName.contains("ZAM") || teamName.equalsIgnoreCase("Admin1") || teamName.equalsIgnoreCase("Admin3") || teamName.equalsIgnoreCase("Admin5") || teamName.equalsIgnoreCase("Admin6") || teamName.equalsIgnoreCase("Admin7")))) {
					mgrMailID = "anindita.chakraborty@relianceada.com";
					approverName = "Anindita Chakraborty";
					mgrid = "70255921";
				} else if (cnt == 1 && teamName.equalsIgnoreCase("Admin4")) {
					mgrMailID = "mayur.satam@relianceada.com";
					approverName = "Mayur Satam";
					mgrid = "70265831";
					totalDuration = 48 * 3600000;
				} else if (cnt == 2 && teamName.equalsIgnoreCase("Admin4")) {
					mgrMailID = "sheldon.dsouza@relianceada.com";
					approverName = "Sheldon Dsouza";
					mgrid = "70261097";
					totalDuration = 24 * 3600000;
				}
			}
			/*
			 * if (teamName != null && teamName.equalsIgnoreCase("IT")) { if
			 * (cnt == 1) { mgrMailID = "Rgicl.Applnsupport@rcap.co.in";
			 * approverName = "Sumanth Sajra"; mgrid = "C001"; } if (cnt == 2) {
			 * mgrMailID = "supriya.kerkar@relianceada.com"; approverName =
			 * "supriya kerkar"; mgrid = "70251180"; } if (cnt == 3) { mgrMailID
			 * = "Neeta.Sawant@rcap.co.in"; approverName = "Neeta Sawant"; mgrid
			 * = "70008042"; } }
			 */
			if (teamName != null && teamName.equalsIgnoreCase("IT_infra04")) { // Created
																				// new
																				// condition
																				// for
																				// IT_infra04
				if (cnt == 1) {
					mgrMailID = "supriya.chawan@relianceada.com";// Santosh.O.Shukla@relianceada.com
					approverName = "Supriya Chawan";// Santosh Shukla
					mgrid = "supriyac";// 70263838
				}
				if (cnt == 2) {
					mgrMailID = "Pravin.Patil@relianceada.com";
					approverName = "Pravin Patil";
					mgrid = "70111919";
				}
			}
			if (teamName != null && (teamName.equalsIgnoreCase("IT_infra01") || teamName.equalsIgnoreCase("IT_infra02"))) {// removed
																															// 04
																															// deepakdaneva
				if (cnt == 1) {
					mgrMailID = "Santosh.O.Shukla@relianceada.com";
					approverName = "Santosh Shukla";
					mgrid = "70263838";
				}
				if (cnt == 2) {
					mgrMailID = "Pravin.Patil@relianceada.com";
					approverName = "Pravin Patil";
					mgrid = "70111919";
				}
			}
			if (teamName != null && teamName.equalsIgnoreCase("IT_infra03")) {
				if (cnt == 1) {
					mgrMailID = "Uday.Rane@relianceada.com";
					approverName = "Uday Rane";
					mgrid = "70261442";
				}
				if (cnt == 2) {
					mgrMailID = "Pravin.Patil@relianceada.com";
					approverName = "Pravin Patil";
					mgrid = "70111919";
				}
			}

			if (teamName != null && teamName.toUpperCase().startsWith("CQR")) {
				// log.info("TECHDESK::APPROVERUTIL::SENDAPPSUPPORTOVERDUEEMAIL::INSIDE_IF TEAM STARTS WITH CQR");
				if (cnt == 1) {
					if (teamName.equalsIgnoreCase("CQREAST") || teamName.equalsIgnoreCase("CQRWEST") || teamName.equalsIgnoreCase("CQRNORTH") || teamName.equalsIgnoreCase("CQRSOUTH") || teamName.equalsIgnoreCase("CQRMISC") || teamName.equalsIgnoreCase("CQRFIRE") || teamName.equalsIgnoreCase("CQRENG")) {
						mgrMailID = "SURESH.C.RAMACHANDRAN@relianceada.com";
						approverName = "Suresh R";
						mgrid = "10010097";
					} else if (teamName.equalsIgnoreCase("CQR") || teamName.equalsIgnoreCase("CQRCORPORATE")) {
						mgrMailID = "raj.srivastav@relianceada.com";
						approverName = "Raj Srivastav";
						mgrid = "70280521";
					}
				}
				if (cnt == 2) {
					if (teamName.equalsIgnoreCase("CQR") || teamName.equalsIgnoreCase("CQRCORPORATE")) {
						mgrMailID = "raj.srivastav@relianceada.com";
						approverName = "Raj Srivastav";
						mgrid = "70280521";
					} else if (teamName.equalsIgnoreCase("CQREAST") || teamName.equalsIgnoreCase("CQRWEST") || teamName.equalsIgnoreCase("CQRNORTH") || teamName.equalsIgnoreCase("CQRSOUTH") || teamName.equalsIgnoreCase("CQRMISC") || teamName.equalsIgnoreCase("CQRFIRE") || teamName.equalsIgnoreCase("CQRENG")) {
						mgrMailID = "SURESH.C.RAMACHANDRAN@relianceada.com";
						approverName = "Suresh R";
						mgrid = "10010097";
					} else {
						mgrMailID = "Richa.Pandey@relianceada.com";
						approverName = "Richa Pandey";
						mgrid = "70173953";
					}
				}
			}

			String ticketNo = (String) pi.getDataSlotValue("TicketNo");
			String appName = (String) pi.getDataSlotValue("ApplicationType");
			String callCategory = (String) pi.getDataSlotValue("IssueRequestId");
			String callSubCat = (String) pi.getDataSlotValue("IssueReqSubTypeID");
			String userMailID = (String) pi.getDataSlotValue("UserEmailID");
			String mailFrom = "rgicl.applnsupport@reliancegeneral.co.in";

			String ccAddress = "";

			if (userMailID != null && userMailID.contains("@")) {
				ccAddress = userMailID;
			}

			if (mgrMailID != null && mgrMailID.contains("@")) {
				ccAddress = ccAddress + "," + mgrMailID;
			}

			if (cnt >= 1 && cnt < 3) {
				// log.info("TECHDESK::APPROVERUTIL::SENDAPPSUPPORTOVERDUEEMAIL::INSIDE_IF_CONDITION::CNT>=1 && CNT<3");
				long dtNow = 0;

				SBMCalendar.init(null);
				SBMCalendar sbmcal = null;
				BizCalendar bcal = null;
				Calendar initDuedate = null;
				Date dueDt = null;

				if (teamName != null && !teamName.equalsIgnoreCase("Admin4")) {
					// log.info("TECHDESK::APPROVERUTIL::SENDAPPSUPPORTOVERDUEEMAIL::INSIDE_IF_CONDITION::!teamName.equalsIgnoreCase('Admin4')");
					if (teamName.toUpperCase().startsWith("CQR") || teamName.equalsIgnoreCase("IT_infra04")) { // Deepak
																												// Daneva
						// DO NOTHING
						log.info("Task is not assigned on escalation for team: " + teamName);
						// System.out.println("Task is not assigned on escalation for team");
						if (teamName.equalsIgnoreCase("IT_infra04") && callCategory.equalsIgnoreCase("Network Link")) {
							dtNow = (new java.util.Date()).getTime();
							sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
							bcal = new BizCalendar(dtNow, ((getAllTat(appName, callCategory, callSubCat, ticketNo)[2] * 25) / 100) * 1000);
							initDuedate = sbmcal.getDueDate(bcal);
							dueDt = initDuedate.getTime();
							/**
							 * we are only updating the overdue time below not
							 * reassigning it
							 */
							for (int i = 0; i < wiList.size(); i++) {
								WorkItem wi = (WorkItem) wiList.get(i);
								wi.setDueDate(dueDt.getTime());
								wi.save();
								wi.refresh();
							}
						}
					} else {
						dtNow = (new java.util.Date()).getTime();
						sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
						bcal = new BizCalendar(dtNow, totalDuration);
						initDuedate = sbmcal.getDueDate(bcal);
						dueDt = initDuedate.getTime();
						// log.info("TECHDESK::APPROVERUTIL::SENDAPPSUPPORTOVERDUEEMAIL::INSIDE_ELSE_CONDITION::Task is assigned to the CQR TEAM on escalation");
						for (int i = 0; i < wiList.size(); i++) {
							WorkItem wi = (WorkItem) wiList.get(i);
							wi.setDueDate(dueDt.getTime());
							if (wi.isAvailable()) {
								wi.assign(mgrid);
							}
							if (wi.isAssigned()) {
								wi.reAssign(mgrid);
							}
							wi.save();
							wi.refresh();
						}
					}
				}
				cnt = cnt + 1L;
				pi.updateSlotValue("appSupportCount", new Long(cnt));
				pi.save();

				String isAssignedString = " and now assigned to you.";
				if (teamName.toUpperCase().startsWith("CQR") || teamName.equalsIgnoreCase("IT_infra04")) {
					isAssignedString = "";
				}

				StringBuffer sb = new StringBuffer();
				sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
				sb.append("Dear Approver " + approverName + ",");
				sb.append("<br/><br/>The TAT for the call ticket number " + ticketNo + " assigned to Team:" + teamName + " has been finished and escalated (" + cnt + " )time(s)" + isAssignedString + ".");
				sb.append("<br/><br/>Following are the call details:");
				sb.append("<table class=\"tablecls\">");
				sb.append("<tr><td class=\"tabletitle\">Mail Originated On : </td><td class=\"tablecell\">" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Ticket Logged On : </td><td class=\"tablecell\">" + ticketLoggedDate + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Ticket Assigned To Support Team on : </td><td class=\"tablecell\">" + ticketAssignedToAppSupport + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Application : </td><td class=\"tablecell\">" + appName + "</td></tr>");
				sb.append("<tr><td class=\"tabletitle\">Category Name: </td><td class=\"tablecell\">" + callCategory + "</td></tr>");
				if (ticketZone != null && !ticketZone.trim().equals("")) {
					sb.append("<tr><td class=\"tabletitle\">Zone : </td><td class=\"tablecell\">" + ticketZone + "</td></tr>");
				}
				sb.append("<tr><td class=\"tabletitle\">Actual TAT: </td><td class=\"tablecell\">18 hours </td></tr>");
				sb.append("<br/><br/>Please act on the same ASAP. Click <b><a href=\"http://bpm.reliancegeneral.co.in/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
				sb.append("<br/><br/>Thank you,");
				sb.append("<br/>Reliance Application Support Team");
				sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append("<br/></body></html>");

				HashMap hm = new HashMap();
				hm.put("FROM", mailFrom);
				hm.put("TO", mgrMailID);
				if (teamName != null && teamName.equalsIgnoreCase("Admin4")) {
					hm.put("CC", "Divyansh.Rastogi@relianceada.com,Yogesh.Nikam@relianceada.com");
				}
				hm.put("SUBJECT", "CALLDESK Ticket# " + ticketNo + " has been escalated");
				hm.put("BODY", sb.toString());
				hm.put("PTID", new Double(6308));
				hm.put("PIID", new Double(piid));
				hm.put("WORKSTEP_NAME", "AppSupport");
				hm.put("WORKITEM_NAME", "OverdueEmail");

				EmailSender ems = new EmailSender();
				if (teamName != null && teamName.toUpperCase().startsWith("CQR")) {
					if (mgrid.equals("10010097")) {
						// ems.send(hm);
					}
				} else {
					// ems.send(hm);
				}
			}
			blserver.disConnect(blsession);
		} catch (Exception ex) {
			log.error("Error in class ApproverUtil method sendOverDueEmail for piid :[" + piid + "]\n" + ex.getMessage());
		}
	}
}
