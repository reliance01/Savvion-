package com.rgicl.marcom.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import oracle.jdbc.OracleTypes;

import com.rgicl.marcom.AppConstant;
import com.rgicl.marcom.GeneralUtility;
import com.rgicl.marcom.db.DBUtility;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;

public class Mailer {

	private static final String FROM = "rgicl.applnsupport@reliancegeneral.co.in";
	private static final String ONL_MANDETORY_MAIL_CC = "Swati.S.Sharma@relianceada.com";

	public void mailTicketLogConfirmation(long piID) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String creator = pi.getCreator();
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			String name = GeneralUtility.getNameOfUser(creator);
			if (name != null && name.trim().length() > 0 && ticketNo != null
					&& ticketNo.trim().length() > 0) {
				name = name.split("-")[1].trim();
				HashMap<String, String> prop = new HashMap<String, String>();
				String mailID = new GeneralUtility().getEmailId(creator);
				if (mailID != null && mailID.trim().length() > 0
						&& mailID.contains("@")) {
					prop.put("TO", mailID.trim());
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "SAVVION Alert - Ticket Registered.");
					prop.put("BODY", ticketLoggedMailBody(name, ticketNo));
					EmailSender sender = new EmailSender();
					sender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piID,
							mailID.trim(), "-", "mailTicketLogConfirmation");
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "mailTicketLogConfirmation",
					ex.getMessage(), errors.toString());
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

	public void assignTaskAlert(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			String assignedGroupName = null;
			String subject = null;
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			if (ticketNo != null && ticketNo.trim().length() > 0
					&& wsName != null && wsName.trim().length() > 0) {
				if (wsName.equalsIgnoreCase("UnderWriterTeam")
						|| wsName.equalsIgnoreCase("UnderWriter Approval")) {
					subject = "SAVVION Alert - Ticket assigned for approval.";
					String UnderWriters = (String) pi
							.getDataSlotValue("underwriters");
					if (UnderWriters != null
							&& UnderWriters.trim().length() > 0) {
						sendMailToUsers(UnderWriters.split(","), piID,
								ticketNo, subject, false,
								assignTaskBody(ticketNo));
						return;
					}
				} else if (wsName.equalsIgnoreCase("Legal Team")
						|| wsName.equalsIgnoreCase("LegalTeam Approval")
						|| wsName.equalsIgnoreCase("Creative Filing")
						|| wsName.equalsIgnoreCase("LegalTeam IRDA Activity")) {
					assignedGroupName = (String) pi
							.getDataSlotValue("legalTeamGrp");
					if (wsName.equalsIgnoreCase("Creative Filing")
							|| wsName
									.equalsIgnoreCase("LegalTeam IRDA Activity")) {
						subject = "SAVVION Alert - Creative Filing Pending.";
					} else {
						subject = "SAVVION Alert - Ticket assigned for approval.";
					}
				} else if (wsName
						.equalsIgnoreCase("OnlineTeam Filing Creative")) {
					String creator = pi.getCreator();
					String creatorName = GeneralUtility.getNameOfUser(creator);
					String creatorMailID = new GeneralUtility()
							.getEmailId(creator.trim());
					if (creatorName != null && creatorName.trim().length() > 0) {
						creatorName = creatorName.split("-")[1].trim();
						if (creatorMailID != null
								&& creatorMailID.trim().length() > 0
								&& creatorMailID.contains("@")) {
							HashMap<String, Object> prop = new HashMap<String, Object>();
							prop.put("TO", creatorMailID);
							prop.put("FROM", FROM);
							prop
									.put("SUBJECT",
											"SAVVION Alert - Final Creative Required for filing.");
							prop.put("BODY", assignIndividualTaskBody(
									creatorName, ticketNo));
							EmailSender sender = new EmailSender();
							sender.send(prop);
							new ExceptionLogMaintain().mailSent_Log(piID,
									creatorMailID, "-", "assignTaskAlert");
							return;
						}
					}
				} else if (wsName.equalsIgnoreCase("MarcomTeam")) {
					assignedGroupName = (String) pi
							.getDataSlotValue("mrcTeamGrp");
					subject = "SAVVION Alert - New Ticket Assigned.";
				} else if (wsName
						.equalsIgnoreCase("Creative Procurement Activity")) {
					assignedGroupName = (String) pi
							.getDataSlotValue("mrcTeamGrp");
					subject = "SAVVION Alert - Final Creative Required for filing.";
				}
				if (assignedGroupName != null
						&& assignedGroupName.trim().length() > 0
						&& subject != null && subject.trim().length() > 0) {
					String[] grpMembers = GeneralUtility
							.getGroupMembers(assignedGroupName);
					if (grpMembers != null && grpMembers.length > 0) {
						sendMailToUsers(grpMembers, piID, ticketNo, subject,
								false, assignTaskBody(ticketNo));
					}
				}
				if (wsName.equalsIgnoreCase("Circulation")
						|| wsName.equalsIgnoreCase("BA Circulation")) {
					String creator = pi.getCreator();
					String[] users = { creator };
					String name = GeneralUtility.getNameOfUser(creator);
					if (creator != null && creator.trim().length() > 0) {
						name = name.split("-")[1].trim().toString();
						subject = "SAVVION Alert - Enter Circulation Date.";
						sendMailToUsers(users, piID, ticketNo, subject, false,
								assignTaskBody(ticketNo));
					}
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "assignTaskAlert", ex
							.getMessage(), errors.toString());
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

	public void reworkTaskAlert(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			String creator = pi.getCreator();
			String creatorName = GeneralUtility.getNameOfUser(creator);
			String creatorMailID = new GeneralUtility().getEmailId(creator
					.trim());
			if (creatorName != null && creatorName.trim().length() > 0
					&& ticketNo != null && ticketNo.trim().length() > 0) {
				creatorName = creatorName.split("-")[1].trim();
				if (creatorMailID != null && creatorMailID.trim().length() > 0
						&& creatorMailID.contains("@")) {
					HashMap<String, Object> prop = new HashMap<String, Object>();
					prop.put("TO", creatorMailID);
					prop.put("FROM", FROM);
					if (wsName.equalsIgnoreCase("UnderWriterTeam")
							|| wsName.equalsIgnoreCase("Legal Team")) {
						prop.put("CC", new String[] { ONL_MANDETORY_MAIL_CC });
					}
					prop.put("SUBJECT",
							"SAVVION Alert - Ticket Re-posted for Action.");
					if (wsName.equalsIgnoreCase("UnderWriterTeam")
							|| wsName.equalsIgnoreCase("UnderWriter Approval")) {
						prop.put("BODY", reworkTaskBody(creatorName, ticketNo,
								"UnderWriting Team"));
					} else if (wsName.equalsIgnoreCase("Legal Team")
							|| wsName.equalsIgnoreCase("LegalTeam Approval")) {
						prop.put("BODY", reworkTaskBody(creatorName, ticketNo,
								"Legal Team"));
					}
					EmailSender sender = new EmailSender();
					sender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piID,
							creatorMailID,
							convertToCommaDelimited((String[]) prop.get("CC")),
							"reworkTaskAlert");
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "reworkTaskAlert", ex
							.getMessage(), errors.toString());
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

	public void escalateAlert(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			String groupName = null;
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			if (ticketNo != null && ticketNo.trim().length() > 0
					&& wsName != null && wsName.trim().length() > 0) {
				String subject = "SAVVION Alert - Ticket Escalated.";
				String body = escalateBody(ticketNo);
				if (wsName.equalsIgnoreCase("UnderWriterTeam")
						|| wsName.equalsIgnoreCase("UnderWriter Approval")) {
					String[] userIDs = ((String) pi
							.getDataSlotValue("underwriters")).split(",");
					sendMailToUsers(userIDs, piID, ticketNo, subject, true,
							body);
					return;
				} else if (wsName.equalsIgnoreCase("Legal Team")
						|| wsName.equalsIgnoreCase("LegalTeam Approval")
						|| wsName.equalsIgnoreCase("LegalTeam IRDA Activity")
						|| wsName.equalsIgnoreCase("Creative Filing")) {
					groupName = (String) pi.getDataSlotValue("legalTeamGrp");
				} else if (wsName.equalsIgnoreCase("MarcomTeam")) {
					groupName = (String) pi.getDataSlotValue("mrcTeamGrp");
				} else if (wsName.equalsIgnoreCase("OnlineTeam")) {
					groupName = (String) pi.getDataSlotValue("onlineTeamGrp");
				}
				if (groupName != null && groupName.trim().length() > 0) {
					sendMailToUsers(GeneralUtility.getGroupMembers(groupName),
							piID, ticketNo, subject, true, body);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
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

	public void escalationNotify(long piID, String wsName, String remainingDays) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			WorkStepInstance wi = blserver.getWorkStepInstance(blsession, piID,
					wsName);
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			String wsActivateTime = new SimpleDateFormat(
					"dd/MMM/yyyy hh:mm:ss a").format(new Date(wi
					.getActivationTime()));
			String groupName = (String) pi.getDataSlotValue("legalTeamGrp");
			if (ticketNo != null && ticketNo.trim().length() > 0
					&& groupName != null && groupName.trim().length() > 0) {
				String[] grpMembers = GeneralUtility.getGroupMembers(groupName);
				if (grpMembers != null && grpMembers.length > 0) {
					sendMailToUsers(grpMembers, piID, ticketNo,
							"SAVVION Alert - Ticket Pending for approval since "
									+ wsActivateTime + ".", false,
							escalateNotifyBody(ticketNo, wsActivateTime,
									remainingDays));
					GeneralUtility.setDueDate(piID, wsName);
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "escalationNotify", ex
							.getMessage(), errors.toString());
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

	public void updatePostToBU(long piID) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String creator = pi.getCreator();
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			String name = GeneralUtility.getNameOfUser(creator);
			if (name != null && name.trim().length() > 0 && ticketNo != null
					&& ticketNo.trim().length() > 0) {
				name = name.split("-")[1].trim().toString();
				HashMap<String, String> prop = new HashMap<String, String>();
				String mailID = new GeneralUtility().getEmailId(creator);
				if (mailID != null && mailID.trim().length() > 0) {
					prop.put("TO", mailID.trim());
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Update posted on MARCOM Ticket No. "
							+ ticketNo + ".");
					prop.put("BODY", updatePostBUMailBody(name, ticketNo));
					EmailSender sender = new EmailSender();
					sender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piID,
							mailID.trim(), "-", "updatePostToBU");
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "updatePostToBU", ex
							.getMessage(), errors.toString());
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

	public void updatePostToMARCOM(long piID) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(AppConstant.BLUSERNAME,
					AppConstant.BLPASSWORD);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			String ticketNo = (String) pi.getDataSlotValue("ticketNo");
			String mrcGrpName = (String) pi.getDataSlotValue("mrcTeamGrp");
			if (mrcGrpName != null && mrcGrpName.trim().length() > 0
					&& ticketNo != null && ticketNo.trim().length() > 0) {
				HashMap<String, Object> prop = new HashMap<String, Object>();
				String[] grpMembers = GeneralUtility
						.getGroupMembers(mrcGrpName);
				if (grpMembers != null && grpMembers.length > 0) {
					String[] membersMailID = getMailIds(grpMembers);
					if (membersMailID != null && membersMailID.length > 0) {
						prop.put("TO", membersMailID);
						prop.put("FROM", FROM);
						prop.put("SUBJECT",
								"Update posted on MARCOM Ticket No. "
										+ ticketNo + ".");
						prop.put("BODY", updatePostMRCMailBody(ticketNo));
						EmailSender sender = new EmailSender();
						sender.sendMutliple_To(prop);
						new ExceptionLogMaintain().mailSent_Log(piID,
								convertToCommaDelimited(membersMailID), "-",
								"updatePostToMARCOM");
					}
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "updatePostToMARCOM", ex
							.getMessage(), errors.toString());
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

	public void closeTicketAlert(long piID, String wsName) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			if (wsName != null && wsName.trim().length() > 0) {
				blserver = BLClientUtil.getBizLogicServer();
				blsession = blserver.connect(AppConstant.BLUSERNAME,
						AppConstant.BLPASSWORD);
				ProcessInstance pi = blserver.getProcessInstance(blsession,
						piID);
				String ticketNo = (String) pi.getDataSlotValue("ticketNo");
				String creator = pi.getCreator();
				String responsibleGrp = null;
				if (wsName.equalsIgnoreCase("LegalTeam IRDA Activity")
						|| wsName
								.equalsIgnoreCase("OnlineTeam Filing Creative")) {
					responsibleGrp = (String) pi
							.getDataSlotValue("onlineTeamGrp");
				} else if (wsName.equalsIgnoreCase("Creative Filing")
						|| wsName
								.equalsIgnoreCase("Creative Procurement Activity")) {
					responsibleGrp = (String) pi.getDataSlotValue("mrcTeamGrp");
				}
				if (ticketNo != null && ticketNo.trim().length() > 0
						&& responsibleGrp != null
						&& responsibleGrp.trim().length() > 0) {
					HashMap<String, Object> prop = new HashMap<String, Object>();
					String[] grpMembers = GeneralUtility
							.getGroupMembers(responsibleGrp);
					String creatorMailID = new GeneralUtility()
							.getEmailId(creator);
					if (grpMembers != null && grpMembers.length > 0) {
						String[] membersMailID = getMailIds(grpMembers);
						if (creatorMailID != null
								&& creatorMailID.contains("@")) {
							prop.put("TO", creatorMailID);
							if (membersMailID != null
									&& membersMailID.length > 0) {
								prop.put("CC", membersMailID);
							}
							prop.put("FROM", FROM);
							prop
									.put("SUBJECT",
											"SAVVION Alert - Ticket Close.");
							prop.put("BODY", closeTicketBody(ticketNo,
									GeneralUtility.getNameOfUser(creator)
											.split("-")[1].trim()));
							EmailSender sender = new EmailSender();
							sender.send(prop);
							new ExceptionLogMaintain().mailSent_Log(piID,
									creatorMailID,
									convertToCommaDelimited((String[]) prop
											.get("CC")), "closeTicketAlert");
						}
					}
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), "closeTicketAlert", ex
							.getMessage(), errors.toString());
		} finally {
			try {
				blserver.disConnect(blsession);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private String ticketLoggedMailBody(String user, String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/> Ticket No. " + ticketNo
				+ " has been generated successfully.");
		sb.append("<br/>See below link to track your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String assignTaskBody(String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear Team,");
		sb.append("<br/><br/> Ticket No. " + ticketNo
				+ " has been assigned to your ID for action.");
		sb.append("<br/>See below link to access your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String assignIndividualTaskBody(String user, String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/> Ticket No. " + ticketNo
				+ " has been assigned to your ID for action.");
		sb.append("<br/>See below link to access your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String reworkTaskBody(String user, String ticketNo, String teamName) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb
				.append("<br/><br/> Ticket No. "
						+ ticketNo
						+ " has been re-assigned to your bucket for action/clarification by the "
						+ teamName + ".");
		sb.append("<br/>See below link for further detail of your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String escalateBody(String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear Manager,");
		sb
				.append("<br/><br/> Ticket No. " + ticketNo
						+ "  has been escalated.");
		sb.append("<br/>See below link for further detail of your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String escalateNotifyBody(String ticketNo, String assignTaskTime,
			String remainingDays) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear Team,");
		sb.append("<br/><br/> Ticket No. " + ticketNo
				+ "  is pending at your end for approval since "
				+ assignTaskTime + ".");
		sb.append("<br/>It has only " + remainingDays
				+ " days left to escalate.");
		sb.append("<br/>See below link for further detail of your ticket.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String updatePostBUMailBody(String name, String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + name + ",");
		sb
				.append("<br/><br/> An update has been posted by the MARCOM team on Ticket "
						+ ticketNo + ".");
		sb
				.append("<br/>Please log in to you your SAVVION id for more details.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String updatePostMRCMailBody(String ticketNo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear Team,");
		sb
				.append("<br/><br/> An update has been posted by the Business user on Ticket "
						+ ticketNo + ".");
		sb
				.append("<br/>Please log in to you your SAVVION id for more details.");
		sb
				.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	private String closeTicketBody(String ticketNo, String name) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + name + ",");
		sb.append("<br/><br/> Ticket No. " + ticketNo + "  has been closed.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> SAVVION TEAM");
		sb
				.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	public String[] getMGRIds(String[] userIDs) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		String[] MGRMailID = null;
		String qry = "{call MRC_GET_MGREMAIL(?,?)}";
		try {
			if (userIDs != null && userIDs.length > 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				StringBuilder nameBuilder = new StringBuilder();
				for (String n : userIDs) {
					int id = Integer.parseInt(n);
					nameBuilder.append(id).append(",");
					// nameBuilder.append("'").append(n.replace("'",
					// "''")).append("',");
				}
				nameBuilder.deleteCharAt(nameBuilder.length() - 1);
				cstmt.setString(1, nameBuilder.toString().trim());
				cstmt.registerOutParameter(2, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rset = (ResultSet) cstmt.getObject(2);
				ArrayList<String> mgrMailIDs = new ArrayList<String>();
				while (rset.next()) {
					String mailID = rset.getString(1);
					if (mailID != null && mailID.trim().length() > 0
							&& mailID.contains("@")) {
						mgrMailIDs.add(mailID);
					}
				}
				MGRMailID = new String[mgrMailIDs.size()];
				MGRMailID = mgrMailIDs.toArray(MGRMailID);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
		return MGRMailID;
	}

	private String[] getMailIds(String[] userArr) {
		String[] membersMailID = null;
		if (userArr != null && userArr.length > 0) {
			GeneralUtility generalUtility = new GeneralUtility();
			ArrayList<String> mailIDs = new ArrayList<String>();
			for (int i = 0; i < userArr.length; i++) {
				if (userArr[i] != null && userArr[i].trim().length() > 0) {
					String mailID = generalUtility
							.getEmailId(userArr[i].trim());
					if (mailID != null && mailID.trim().length() > 0
							&& mailID.contains("@")) {
						mailIDs.add(mailID);
					}
				}
			}
			membersMailID = new String[mailIDs.size()];
			membersMailID = mailIDs.toArray(membersMailID);
		}
		return membersMailID;
	}

	private void sendMailToUsers(String[] userIDs, long piID, String ticketNo,
			String subject, boolean isManagerNotify, String body) {
		BLServer blserver = null;
		Session blsession = null;
		try {
			if (userIDs != null && userIDs.length > 0 && subject != null
					&& subject.trim().length() > 0 && body != null
					&& body.trim().length() > 0 && ticketNo != null
					&& ticketNo.trim().length() > 0) {
				blserver = BLClientUtil.getBizLogicServer();
				blsession = blserver.connect(AppConstant.BLUSERNAME,
						AppConstant.BLPASSWORD);
				String[] userMailIDs = getMailIds(userIDs);
				if (userMailIDs != null && userMailIDs.length > 0) {
					HashMap<String, Object> prop = new HashMap<String, Object>();
					if (isManagerNotify == true) {
						String[] mgrMailIDs = getMGRIds(userIDs);
						if (mgrMailIDs != null && mgrMailIDs.length > 0) {
							prop.put("TO", mgrMailIDs);
							prop.put("CC", userMailIDs);
						}
					} else {
						prop.put("TO", userMailIDs);
					}
					prop.put("FROM", FROM);
					prop.put("SUBJECT", subject);
					prop.put("BODY", body);
					EmailSender sender = new EmailSender();
					sender.sendMutliple_To(prop);
					new ExceptionLogMaintain().mailSent_Log(piID,
							convertToCommaDelimited((String[]) prop.get("TO")),
							convertToCommaDelimited((String[]) prop.get("CC")),
							Thread.currentThread().getStackTrace()[3]
									.getMethodName());
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(ex), Thread.currentThread()
							.getStackTrace()[3].getMethodName(), ex
							.getMessage(), errors.toString());
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

	private String convertToCommaDelimited(String[] list) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; list != null && i < list.length; i++) {
			ret.append(list[i]);
			if (i < list.length - 1) {
				ret.append(',');
			}
		}
		return ret.toString();
	}

	private String getErrorGeneratorMethod(final Throwable cause) {
		Throwable rootCause = cause;
		if (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
			rootCause = rootCause.getCause();
		}
		if (rootCause != null) {
			return rootCause.getStackTrace()[0].getMethodName();
		} else
			return "";
	}
}
