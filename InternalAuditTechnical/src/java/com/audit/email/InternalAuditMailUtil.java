package com.audit.email;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.axis.client.Service;
import com.rel.internalaudit.InternalAuditUtil;
import com.savvion.mom.Service1Soap_BindingStub;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import com.savvion.sbm.dms.DSContext;
import com.savvion.sbm.dms.svo.Document;

public class InternalAuditMailUtil {
	private static String from = "RGICLApplicationSupport@reliancegeneral.co.in";
	private static String bl_user = "rgicl";
	private static String bl_password = "rgicl";
	private String wsdlUrl = "";
	public String mandetoryCCId = "PRADYUT.MULLICK@relianceada.com";
	public String nationalBranchOperationMgrMailId = "Raman.Arora@relianceada.com";
	public String nationalBranchOperationMgrName = "Raman Arora";
	public String nationalODClaimOperationMgrName = "Lakshmi Sunder";
	public String nationalLegalClaimOperationMgrName = "Randhir Singh";
	public String nationalODClaimOperationMgrMailId = "Lakshmi.Sundar@relianceada.com";
	public String nationalLegalClaimOperationMgrMailId = "Randhir.Singh@relianceada.com";

	public InternalAuditMailUtil() {
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

	public void disConnectToBLServer(BLServer blServer, Session blSession) {
		if (blSession != null) {
			try {
				blServer.disConnect(blSession);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getZonalManagerMailId(String region) {
		String zomEmailId = null;
		UsersInfo user = new UsersInfo();
		if ((region != null) && (region.trim().length() > 0)) {
			String zonalManager = user.getZonalHead(region.trim());
			if ((zonalManager != null) && (zonalManager.trim().length() > 0)
					&& (zonalManager.contains("-"))) {
				String zonalManagerUserId = zonalManager.split("-")[1].trim();
				if ((zonalManagerUserId != null)
						&& (zonalManagerUserId.trim().length() > 0)) {
					zomEmailId = user.getEmailId(zonalManagerUserId);
				}
			}
		}
		return zomEmailId;
	}

	public String getZonalManagerName(String region) {
		String zomName = null;
		UsersInfo user = new UsersInfo();
		if ((region != null) && (region.trim().length() > 0)) {
			String zonalManager = user.getZonalHead(region.trim());
			if ((zonalManager != null) && (zonalManager.trim().length() > 0)
					&& (zonalManager.contains("-"))) {
				zomName = zonalManager.split("-")[0].trim();
			}
		}
		return zomName;
	}

	public long getAuditReportSubmissionDate(ProcessInstance pi) {
		long activationTime = 0L;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		InternalAuditUtil obj = new InternalAuditUtil();
		try {
			if (pi != null) {
				String sql = "SELECT START_TIME FROM WORKITEM WHERE PROCESS_INSTANCE_ID = ? AND WORKSTEP_NAME = ? AND ROWNUM = 1 ORDER BY START_TIME";
				connection = obj.getDBConnection();
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, pi.getID());
				pstmt.setString(2, "Auditee Reply Status");
				rset = pstmt.executeQuery();
				while (rset.next()) {
					activationTime = rset.getDate(1).getTime();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			obj.releaseResources(connection, pstmt, rset);
		}
		return activationTime;
	}

	public HashMap getReceiptantMailOnSchedule(ProcessInstance pi) {
		HashMap receiptants = null;
		if (pi != null) {
			try {
				receiptants = new HashMap();
				Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				ArrayList<String> mailTOUsers = new ArrayList();
				ArrayList<String> mailCCUsers = new ArrayList();
				String region = (String) pi.getDataSlotValue("region");
				String othersMailId = (String) pi
						.getDataSlotValue("othersMailId");
				String rom = (String) pi.getDataSlotValue("RH");
				String ecrgHead = (String) pi.getDataSlotValue("ECRGHead");
				String auditee = (String) pi.getDataSlotValue("BSM");
				String auditor = (String) pi
						.getDataSlotValue("auditor_username");
				String zom = (String) pi.getDataSlotValue("zonalHead");
				String auditType = (String) pi.getDataSlotValue("branchType");
				String auditeeEmailId = null;
				if ((auditee != null) && (auditee.trim().length() > 0)) {
					String auditeeUserId = auditee.split("-")[0].trim();
					auditeeEmailId = sbb.getEmployeeEmailId(auditeeUserId)
							.trim();
					if ((auditeeEmailId != null)
							&& (auditeeEmailId.trim().length() > 0)
							&& (auditeeEmailId.contains("@"))) {
						mailTOUsers.add(auditeeEmailId);
					}
				}

				if ((rom != null) && (rom.trim().length() > 0)) {
					String romUserId = rom.split("-")[0].trim();
					String romEmailId = sbb.getEmployeeEmailId(romUserId)
							.trim();
					if ((romEmailId != null)
							&& (romEmailId.trim().length() > 0)
							&& (romEmailId.contains("@"))) {
						mailTOUsers.add(romEmailId);
					}
				}

				if ((zom != null) && (zom.trim().length() > 0)
						&& (zom.contains("-"))) {
					String zomUserId = zom.split("-")[1].trim();
					mailCCUsers.add(sbb.getEmployeeEmailId(zomUserId).trim());
				}

				if ((ecrgHead != null) && (ecrgHead.trim().length() > 0)) {
					String ecrgHeadUserId = ecrgHead.split("-")[0].trim();
					String ecrgHeadEmailId = sbb.getEmployeeEmailId(
							ecrgHeadUserId).trim();
					if ((ecrgHeadEmailId != null)
							&& (ecrgHeadEmailId.trim().length() > 0)
							&& (ecrgHeadEmailId.contains("@"))) {
						mailCCUsers.add(ecrgHeadEmailId);
					}
				}

				if ((auditor != null) && (auditor.trim().length() > 0)) {
					String auditorEmailId = sbb.getEmployeeEmailId(
							auditor.trim()).trim();
					if ((auditorEmailId != null)
							&& (auditorEmailId.trim().length() > 0)
							&& (auditorEmailId.contains("@"))) {
						mailCCUsers.add(auditorEmailId);
					}
				}

				if ((othersMailId != null)
						&& (othersMailId.trim().length() > 0)) {
					String[] othersMailArr = othersMailId.split(",");
					for (int i = 0; i < othersMailArr.length; i++) {
						if ((othersMailArr[i] != null)
								&& (othersMailArr[i].trim().length() > 0)
								&& (othersMailArr[i].contains("@"))) {
							mailCCUsers.add(othersMailArr[i].trim());
						}
					}
				}
				mailCCUsers.add(mandetoryCCId);
				receiptants.put("TO", mailTOUsers);
				receiptants.put("CC", mailCCUsers);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return receiptants;
	}

	public HashMap<String, String> getAuditeeExclationStuff(ProcessInstance pi) {
		HashMap<String, String> auditeeExclationStuff = new HashMap();
		if (pi != null) {
			try {
				String auditor = (String) pi.getDataSlotValue("auditor");
				String auditee = (String) pi.getDataSlotValue("BSM");
				String rom = (String) pi.getDataSlotValue("RH");
				String region = (String) pi.getDataSlotValue("region");
				String ecrgHead = (String) pi.getDataSlotValue("ECRGHead");
				String location = (String) pi.getDataSlotValue("branch");
				String zom = (String) pi.getDataSlotValue("zonalHead");
				String auditType = (String) pi.getDataSlotValue("branchType");
				Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				if ((auditor != null) && (auditor.trim().length() > 0)
						&& (auditor.contains("-"))) {
					String auditorUserId = auditor.split("-")[0].trim();
					String auditorName = auditor.split("-")[1].trim();
					if ((auditorName != null)
							&& (auditorName.trim().length() > 0)) {
						auditeeExclationStuff.put("AUDITOR NAME", auditorName);
					}
					String auditorEmailId = sbb
							.getEmployeeEmailId(auditorUserId);
					if ((auditorEmailId != null)
							&& (auditorEmailId.trim().length() > 0)
							&& (auditorEmailId.contains("@"))) {
						auditeeExclationStuff.put("AUDITOR EMAIL ID",
								auditorEmailId);
					}
				}
				if ((auditee != null) && (auditee.trim().length() > 0)
						&& (auditee.contains("-"))) {
					String auditeeUserId = auditee.split("-")[0].trim();
					String auditeeEmailId = sbb
							.getEmployeeEmailId(auditeeUserId);
					if ((auditeeEmailId != null)
							&& (auditeeEmailId.trim().length() > 0)
							&& (auditeeEmailId.contains("@"))) {
						auditeeExclationStuff.put("AUDITEE EMAIL ID",
								auditeeEmailId);
					}
				}
				if ((rom != null) && (rom.trim().length() > 0)
						&& (rom.contains("-"))) {
					String romName = rom.split("-")[1].trim();
					auditeeExclationStuff.put("ROM NAME", romName);
					String romUserId = rom.split("-")[0].trim();
					String romEmailId = sbb.getEmployeeEmailId(romUserId);
					if ((romEmailId != null)
							&& (romEmailId.trim().length() > 0)
							&& (romEmailId.contains("@"))) {
						auditeeExclationStuff.put("ROM EMAIL ID", romEmailId);
					}
				}

				if ((zom != null) && (zom.trim().length() > 0)
						&& (zom.contains("-"))) {
					String zomUserId = zom.split("-")[1].trim();
					String zonalName = zom.split("-")[0].trim();
					auditeeExclationStuff.put("ZOM NAME", zonalName);
					String zonalEmailId = sbb.getEmployeeEmailId(zomUserId);
					if ((zonalEmailId != null)
							&& (zonalEmailId.trim().length() > 0)
							&& (zonalEmailId.contains("@"))) {
						auditeeExclationStuff.put("ZOM EMAIL ID", zonalEmailId);
					}
				}

				if ((ecrgHead != null) && (ecrgHead.trim().length() > 0)
						&& (ecrgHead.contains("-"))) {
					String ecrgHeadName = ecrgHead.split("-")[1].trim();
					auditeeExclationStuff.put("ECRG HEAD NAME", ecrgHeadName);
					String ecrgHeadUserId = ecrgHead.split("-")[0].trim();
					String ecrgHeadEmailId = sbb
							.getEmployeeEmailId(ecrgHeadUserId);
					if ((ecrgHeadEmailId != null)
							&& (ecrgHeadEmailId.trim().length() > 0)
							&& (ecrgHeadEmailId.contains("@"))) {
						auditeeExclationStuff.put("ECRG HEAD EMAIL ID",
								ecrgHeadEmailId);
					}
				}
				if ((location != null) && (location.trim().length() > 0)) {
					auditeeExclationStuff.put("LOCATION", location);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return auditeeExclationStuff;
	}

	public String auditeeExclationMailBody(String user, String location,
			String auditReportSubmissionDate, String auditorName,
			String indicate) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>This is in reference to the Internal Audit Technical Conducted at "
				+ indicate
				+ " "
				+ location
				+ "."
				+ " Audit report submitted to the location manager on "
				+ auditReportSubmissionDate
				+ "."
				+ " Replies from the location manager not received till date. Your intervention is sought in order to get the finalization of audit report.");
		sb.append("<br/>See below link to access audit report using your domain ID.");
		sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link or go to URL Address : ");
		sb.append("http://10.65.5.55:18793/sbm/bpmportal/login.jsp to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/>" + auditorName);
		sb.append("<br/>Internal Audit Technical (ERCG)");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	public void sendMailOnAuditSchedule(long piId) {

		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
			HashMap receiptants_TO_CC = getReceiptantMailOnSchedule(pi);
			if ((receiptants_TO_CC != null) && (!receiptants_TO_CC.isEmpty())) {
				String mailSubject = (String) pi
						.getDataSlotValue("auditeeMailSubject");
				String mailBody = (String) pi
						.getDataSlotValue("auditeeMessage");
				mailBody = mailBody
						+ "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				String path = System.getProperty("sbm.home")
						+ "/ebmsapps/InternalAuditTechnical/Audit_SampleDocTemp/";
				DataSlot ds = blServer
						.getDataSlot(blSession, piId, "sampleDoc");
				DocumentDS documentDS = (DocumentDS) ds.getValue();
				List<Document> docList = documentDS.getDocuments();
				DSContext dsContext = new DSContext(bl_user, bl_password,
						new HashMap());
				String[] fileNamesArr = new String[docList.size()];
				for (int i = 0; i < docList.size(); i++) {
					Document doc = (Document) docList.get(i);
					doc.saveToFile(dsContext, path + doc.getName());
					fileNamesArr[i] = (path + doc.getName());
				}
				ArrayList<String> _receiptantTO = removeDupli((ArrayList) receiptants_TO_CC
						.get("TO"));
				ArrayList<String> _receiptantCC = removeDupli((ArrayList) receiptants_TO_CC
						.get("CC"));

				_receiptantTO.removeAll(Collections.singleton(null));
				_receiptantCC.removeAll(Collections.singleton(null));

				String[] recipientTO = null;
				String[] recipientCC = null;

				if ((_receiptantTO != null) && (_receiptantTO.size() > 0)) {
					recipientTO = new String[_receiptantTO.size()];
					recipientTO = (String[]) _receiptantTO.toArray(recipientTO);
				}
				if ((_receiptantCC != null) && (_receiptantCC.size() > 0)) {
					recipientCC = new String[_receiptantCC.size()];
					recipientCC = (String[]) _receiptantCC.toArray(recipientCC);
				}
				HashMap hm = new HashMap();
				hm.put("TO", recipientTO);
				hm.put("CC", recipientCC);
				hm.put("FROM", from);
				hm.put("SUBJECT", mailSubject.trim());
				hm.put("BODY", mailBody.trim());
				EmailSender ems = new EmailSender();
				ems.sendMailWithAttachment(hm, fileNamesArr);
				new ExceptionLogMaintain().mailSent_Log(piId,
						convertToCommaDelimited(recipientTO),
						convertToCommaDelimited(recipientCC),
						"sendMailOnAuditSchedule");
				for (int i = 0; i < docList.size(); i++) {
					File tempFile = new File(path
							+ ((Document) docList.get(i)).getName());
					if (tempFile.exists()) {
						tempFile.delete();
					}
				}
			}
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex), "sendMailOnAuditSchedule",
					ex.getMessage(), errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void sentMailOnReportSubmissionToAuditee(long piId) {
		HashMap receiptants_TO_CC = null;
		String auditorName = "";
		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);

			receiptants_TO_CC = getReceiptantMailOnSchedule(pi);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			String locationName = (String) pi.getDataSlotValue("branch");
			String bsm = (String) pi.getDataSlotValue("BSM");
			String bsmName = bsm.split("-")[1].trim();
			String auditor = (String) pi.getDataSlotValue("auditor");
			auditorName = auditor.split("-")[1].trim();
			long auditStartDate = Long.parseLong(pi
					.getDataSlot("auditStartDate").getValue().toString());
			long auditEndDate = Long.parseLong(pi.getDataSlot("auditEndDate")
					.getValue().toString());

			String _auditStartDate = formatter.format(Long
					.valueOf(auditStartDate));
			String _auditEndDate = formatter.format(Long.valueOf(auditEndDate));

			long totalDuration = 288000000L;
			long startDate = new java.util.Date().getTime();
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(startDate, totalDuration);
			Calendar initDuedate = sbmcal.getDueDate(bcal);

			String expectedClosureDt = formatter.format(initDuedate.getTime());
			StringBuffer sb = new StringBuffer();
			sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
			sb.append("Dear " + bsmName + ",");
			sb.append("<br/><br/>This is in ref. to the technical audit conducted from "
					+ _auditStartDate
					+

					" to "
					+ _auditEndDate
					+ ". You are requested to go through the audit report and submit your replies within 10 working days time , i.e., by "
					+ expectedClosureDt + ".");
			sb.append("<br/>Audit report has been uploaded on Internal Technical Audit System.");
			sb.append("<br/>see below link to access audit report using your domain ID.");
			sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link or go to URL Address : ");
			sb.append("http://10.65.5.55:18793/sbm/bpmportal/login.jsp to login into the system.");
			sb.append("<br/><br/>For any further clarification, please feel free to duscuss / write to the concerned auditor.");

			sb.append("<br/><br/>Regards,");
			sb.append("<br/>" + auditorName);
			sb.append("<br/>Internal Audit Technical (ERCG)");
			sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("<br/></body></html>");

			ArrayList<String> _receiptantTO = removeDupli((ArrayList) receiptants_TO_CC
					.get("TO"));
			ArrayList<String> _receiptantCC = removeDupli((ArrayList) receiptants_TO_CC
					.get("CC"));
			String[] recipientTO = null;
			String[] recipientCC = null;

			_receiptantTO.removeAll(Collections.singleton(null));
			_receiptantCC.removeAll(Collections.singleton(null));

			if ((_receiptantTO != null) && (_receiptantTO.size() > 0)) {
				recipientTO = new String[_receiptantTO.size()];
				recipientTO = (String[]) _receiptantTO.toArray(recipientTO);
			}
			if ((_receiptantCC != null) && (_receiptantCC.size() > 0)) {
				recipientCC = new String[_receiptantCC.size()];
				recipientCC = (String[]) _receiptantCC.toArray(recipientCC);
			}
			HashMap hm = new HashMap();
			hm.put("TO", recipientTO);
			hm.put("CC", recipientCC);
			hm.put("FROM", from);
			hm.put("SUBJECT", " Internal Audit Technical Report - ( "
					+ locationName + " )");
			hm.put("BODY", sb.toString());
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(recipientTO),
					convertToCommaDelimited(recipientCC),
					"sentMailOnReportSubmissionToAuditee");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex),
					"sentMailOnReportSubmissionToAuditee", ex.getMessage(),
					errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void sendMailOnAuditeeFirstExclation(long piId) {
		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);

			SimpleDateFormat Currdateformat1 = new SimpleDateFormat(
					"dd/MM/yyyy");
			String auditReportSubmissionDate = Currdateformat1.format(Long
					.valueOf(getAuditReportSubmissionDate(pi)));
			String auditType = (String) pi.getDataSlotValue("branchType");

			HashMap<String, String> prop = getAuditeeExclationStuff(pi);
			String mailBody = null;
			ArrayList<String> toUserList = new ArrayList();
			ArrayList<String> ccUserList = new ArrayList();
			String auditorName = "";
			String romName = "";
			String romEmailId = null;
			String location = "";
			String zomName = "";
			String zomEmailId = null;
			String auditorEmailId = null;
			String auditeeMailId = null;

			if (isProp(prop, "AUDITOR NAME")) {
				auditorName = (String) prop.get("AUDITOR NAME");
			}
			if (isProp(prop, "ROM NAME")) {
				romName = (String) prop.get("ROM NAME");
			}
			if (isProp(prop, "ROM EMAIL ID")) {
				romEmailId = (String) prop.get("ROM EMAIL ID");
			}
			if (isProp(prop, "LOCATION")) {
				location = (String) prop.get("LOCATION");
			}
			if (isProp(prop, "ZOM NAME")) {
				zomName = (String) prop.get("ZOM NAME");
			}
			if (isProp(prop, "ZOM EMAIL ID")) {
				zomEmailId = (String) prop.get("ZOM EMAIL ID");
			}
			if (isProp(prop, "AUDITOR EMAIL ID")) {
				auditorEmailId = (String) prop.get("AUDITOR EMAIL ID");
			}
			if (isProp(prop, "AUDITEE EMAIL ID")) {
				auditeeMailId = (String) prop.get("AUDITEE EMAIL ID");
			}

			if (auditType.trim().equalsIgnoreCase("Legal Claims")) {

				toUserList.add(zomEmailId);
				toUserList.add("deepak.nair@relianceada.com");
				ccUserList.add("Sanjay.G.Gupta@relianceada.com");
				ccUserList.add(auditorEmailId);
				ccUserList.add(auditeeMailId);
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					mailBody = auditeeExclationMailBody(zomName, location,
							auditReportSubmissionDate, auditorName,
							"Service Center -");
				}
			} else {
				toUserList.add(romEmailId);

				ccUserList.add(auditorEmailId);
				ccUserList.add(auditeeMailId);

				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					String indicate = "-";
					if (auditType.trim().equalsIgnoreCase("OD Claims")) {
						indicate = "Service Center -";
					} else if ((auditType.trim()
							.equalsIgnoreCase("Retail HUB Operation"))
							||

							(auditType.trim()
									.equalsIgnoreCase("Commercial HUB Operation"))) {
						indicate = "Hub Operations -";
					}
					mailBody = auditeeExclationMailBody(romName, location,
							auditReportSubmissionDate, auditorName, indicate);
				}
			}
			ccUserList.add(mandetoryCCId);

			toUserList.removeAll(Collections.singleton(null));
			ccUserList.removeAll(Collections.singleton(null));

			ArrayList<String> _toUserList = removeDupli(toUserList);
			ArrayList<String> _ccUserList = removeDupli(ccUserList);

			String[] toUsers = new String[_toUserList.size()];
			_toUserList.toArray(toUsers);
			String[] ccUsers = new String[_ccUserList.size()];
			_ccUserList.toArray(ccUsers);

			HashMap hm = new HashMap();
			hm.put("TO", toUsers);
			if (ccUsers.length > 0) {
				hm.put("CC", ccUsers);
			}
			hm.put("FROM", from);
			hm.put("SUBJECT", location + " audit replies delayed >10 days");
			hm.put("BODY", mailBody);
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(toUsers),
					convertToCommaDelimited(ccUsers),
					"sendMailOnAuditeeFirstExclation");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex),
					"sendMailOnAuditeeFirstExclation", ex.getMessage(),
					errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void sendMailOnAuditeeSecondExclation(long piId) {
		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);

			SimpleDateFormat Currdateformat1 = new SimpleDateFormat(
					"dd/MM/yyyy");
			String auditReportSubmissionDate = Currdateformat1.format(Long
					.valueOf(getAuditReportSubmissionDate(pi)));
			HashMap<String, String> prop = getAuditeeExclationStuff(pi);

			ArrayList<String> toUserList = new ArrayList();
			ArrayList<String> ccUserList = new ArrayList();
			String auditType = (String) pi.getDataSlotValue("branchType");
			String zomName = "";
			String zomEmailId = null;
			String auditorMailId = null;
			String auditeeMailId = null;
			String romMailId = null;
			String location = "";
			String mailBody = null;
			String auditorName = "";

			if (isProp(prop, "AUDITOR NAME")) {
				auditorName = (String) prop.get("AUDITOR NAME");
			}
			if (isProp(prop, "ZOM NAME")) {
				zomName = (String) prop.get("ZOM NAME");
			}
			if (isProp(prop, "ZOM EMAIL ID")) {
				zomEmailId = (String) prop.get("ZOM EMAIL ID");
			}
			if (isProp(prop, "LOCATION")) {
				location = (String) prop.get("LOCATION");
			}
			if (isProp(prop, "AUDITOR EMAIL ID")) {
				auditorMailId = (String) prop.get("AUDITOR EMAIL ID");
			}
			if (isProp(prop, "AUDITEE EMAIL ID")) {
				auditeeMailId = (String) prop.get("AUDITEE EMAIL ID");
			}
			if (isProp(prop, "ROM EMAIL ID")) {
				romMailId = (String) prop.get("ROM EMAIL ID");
			}

			if (auditType.trim().equalsIgnoreCase("Legal Claims")) {
				toUserList.add("Sanjay.G.Gupta@relianceada.com");
				ccUserList.add("deepak.nair@relianceada.com");
				ccUserList.add(zomEmailId);
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					mailBody = auditeeExclationMailBody("Sanjay Gupta",
							location, auditReportSubmissionDate, auditorName,
							"Service Center -");
				}
			} else {
				toUserList.add(zomEmailId);
				ccUserList.add(romMailId);
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					String indicate = "-";
					if (auditType.trim().equalsIgnoreCase("OD Claims")) {
						indicate = "Service Center -";
					} else if ((auditType.trim()
							.equalsIgnoreCase("Retail HUB Operation"))
							||

							(auditType.trim()
									.equalsIgnoreCase("Commercial HUB Operation"))) {
						indicate = "Hub Operations -";
					}
					mailBody = auditeeExclationMailBody(zomName, location,
							auditReportSubmissionDate, auditorName, indicate);
				}
			}
			ccUserList.add(auditorMailId);
			ccUserList.add(auditeeMailId);
			ccUserList.add(mandetoryCCId);

			toUserList.removeAll(Collections.singleton(null));
			ccUserList.removeAll(Collections.singleton(null));

			ArrayList<String> _toUserList = removeDupli(toUserList);
			ArrayList<String> _ccUserList = removeDupli(ccUserList);

			String[] toUsers = new String[_toUserList.size()];
			_toUserList.toArray(toUsers);
			String[] ccUsers = new String[_ccUserList.size()];
			_ccUserList.toArray(ccUsers);

			HashMap hm = new HashMap();
			hm.put("TO", toUsers);
			if (ccUsers.length > 0) {
				hm.put("CC", ccUsers);
			}
			hm.put("FROM", from);
			hm.put("SUBJECT", location + " audit replies delayed >10 days");
			hm.put("BODY", mailBody);
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(toUsers),
					convertToCommaDelimited(ccUsers),
					"sendMailOnAuditeeSecondExclation");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex),
					"sendMailOnAuditeeSecondExclation", ex.getMessage(),
					errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void sendMailOnAuditeeThirdExclation(long piId) {
		BLServer blServer = null;
		Session blSession = null;
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);

			SimpleDateFormat Currdateformat1 = new SimpleDateFormat(
					"dd/MM/yyyy");
			String auditReportSubmissionDate = Currdateformat1.format(Long
					.valueOf(getAuditReportSubmissionDate(pi)));

			HashMap<String, String> prop = getAuditeeExclationStuff(pi);
			ArrayList<String> toUserList = new ArrayList();
			ArrayList<String> ccUserList = new ArrayList();

			String auditType = (String) pi.getDataSlotValue("branchType");
			String ecrgHeadnameName = "";
			String ecrgHeadEmailId = null;
			String zomEmailId = null;
			String auditorMailId = null;
			String auditeeMailId = null;
			String romMailId = null;
			String location = "";
			String auditorName = "";
			String mailBody = null;

			if (isProp(prop, "AUDITOR NAME")) {
				auditorName = (String) prop.get("AUDITOR NAME");
			}
			if (isProp(prop, "ECRG HEAD NAME")) {
				ecrgHeadnameName = (String) prop.get("ECRG HEAD NAME");
			}
			if (isProp(prop, "ECRG HEAD EMAIL ID")) {
				ecrgHeadEmailId = (String) prop.get("ECRG HEAD EMAIL ID");
			}
			if (isProp(prop, "LOCATION")) {
				location = (String) prop.get("LOCATION");
			}
			if (isProp(prop, "ZOM EMAIL ID")) {
				zomEmailId = (String) prop.get("ZOM EMAIL ID");
			}
			if (isProp(prop, "AUDITOR EMAIL ID")) {
				auditorMailId = (String) prop.get("AUDITOR EMAIL ID");
			}
			if (isProp(prop, "AUDITEE EMAIL ID")) {
				auditeeMailId = (String) prop.get("AUDITEE EMAIL ID");
			}
			if (isProp(prop, "ROM EMAIL ID")) {
				romMailId = (String) prop.get("ROM EMAIL ID");
			}

			toUserList.add(ecrgHeadEmailId);
			if (auditType.trim().equalsIgnoreCase("OD Claims")) {
				toUserList.add(nationalODClaimOperationMgrMailId);
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					mailBody = auditeeExclationMailBody(ecrgHeadnameName
							+ " / " + nationalODClaimOperationMgrName,
							location, auditReportSubmissionDate, auditorName,
							"Service Center -");
				}
			} else if (auditType.trim().equalsIgnoreCase("Legal Claims")) {
				toUserList.add(nationalLegalClaimOperationMgrMailId);
				ccUserList.add("Sanjay.G.Gupta@relianceada.com");
				ccUserList.add("deepak.nair@relianceada.com");
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					mailBody = auditeeExclationMailBody(ecrgHeadnameName
							+ " / " + nationalLegalClaimOperationMgrName,
							location, auditReportSubmissionDate, auditorName,
							"Service Center -");
				}
			} else {
				toUserList.add(nationalBranchOperationMgrMailId);
				if ((chkStr(location)) && (chkStr(auditReportSubmissionDate))) {
					String indicate = "-";

					if ((auditType.trim()
							.equalsIgnoreCase("Retail HUB Operation"))
							||

							(auditType.trim()
									.equalsIgnoreCase("Commercial HUB Operation"))) {
						indicate = "Hub Operations -";
					}
					mailBody = auditeeExclationMailBody(ecrgHeadnameName
							+ " / " + nationalBranchOperationMgrName, location,
							auditReportSubmissionDate, auditorName, indicate);
				}
			}

			ccUserList.add(auditorMailId);
			ccUserList.add(auditeeMailId);
			if (!auditType.trim().equalsIgnoreCase("Legal Claims")) {
				ccUserList.add(romMailId);
			}
			ccUserList.add(zomEmailId);
			ccUserList.add(mandetoryCCId);

			toUserList.removeAll(Collections.singleton(null));
			ccUserList.removeAll(Collections.singleton(null));

			ArrayList<String> _toUserList = removeDupli(toUserList);
			ArrayList<String> _ccUserList = removeDupli(ccUserList);

			String[] toUsers = new String[_toUserList.size()];
			_toUserList.toArray(toUsers);
			String[] ccUsers = new String[_ccUserList.size()];
			_ccUserList.toArray(ccUsers);

			HashMap hm = new HashMap();
			hm.put("TO", toUsers);
			if (ccUsers.length > 0) {
				hm.put("CC", ccUsers);
			}
			hm.put("FROM", from);
			hm.put("SUBJECT", location + " audit replies delayed >10 days");
			hm.put("BODY", mailBody);
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(toUsers),
					convertToCommaDelimited(ccUsers),
					"sendMailOnAuditeeThirdExclation");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex),
					"sendMailOnAuditeeThirdExclation", ex.getMessage(),
					errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void notifyOnReplySubmission(long piId) {
		Service1Soap_BindingStub sbb = null;
		String auditorName = "";
		String auditorID = "";
		BLServer blServer = null;
		Session blSession = null;
		try {
			sbb = new Service1Soap_BindingStub(new URL(wsdlUrl), new Service());

			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
			String bsm = (String) pi.getDataSlotValue("BSM");
			String bsmName = bsm.split("-")[1].trim();
			String locationName = (String) pi.getDataSlotValue("branch");
			String auditor = (String) pi.getDataSlotValue("auditor");
			auditorName = auditor.split("-")[1].trim();
			auditorID = auditor.split("-")[0].trim();

			StringBuffer sb = new StringBuffer();
			sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
			sb.append("Dear " + auditorName + ",");
			sb.append("<br/><br/>This is in reference to the reply being submitted for the Audit report");
			sb.append(" for your kind perusal.</br>Please find our reply for the observations for your report.");
			sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link or go to URL Address : ");
			sb.append("http://10.65.5.55:18793/sbm/bpmportal/login.jsp to login into the system.");

			sb.append("<br/><br/>Regards,");
			sb.append("<br/>" + bsmName);

			sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("<br/></body></html>");

			ArrayList<String> _receiptantTO = new ArrayList();
			ArrayList<String> _receiptantCC = new ArrayList();
			_receiptantCC.add("-");
			if ((auditorID != null) && (auditorID.trim().length() > 0)) {
				String auditorEmailId = sbb
						.getEmployeeEmailId(auditorID.trim()).trim();
				if ((auditorEmailId != null)
						&& (auditorEmailId.trim().length() > 0)
						&& (auditorEmailId.contains("@"))) {
					_receiptantTO.add(auditorEmailId);
				}
			}

			String[] recipientTO = null;
			String[] recipientCC = null;

			_receiptantTO.removeAll(Collections.singleton(null));
			_receiptantCC.removeAll(Collections.singleton(null));

			if ((_receiptantTO != null) && (_receiptantTO.size() > 0)) {
				recipientTO = new String[_receiptantTO.size()];
				recipientTO = (String[]) _receiptantTO.toArray(recipientTO);
			}
			if ((_receiptantCC != null) && (_receiptantCC.size() > 0)) {
				recipientCC = new String[_receiptantCC.size()];
				recipientCC = (String[]) _receiptantCC.toArray(recipientCC);
			}
			HashMap hm = new HashMap();
			hm.put("TO", recipientTO);
			hm.put("FROM", from);
			hm.put("SUBJECT", locationName + " location audit replies");
			hm.put("BODY", sb.toString());
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(recipientTO),
					convertToCommaDelimited(recipientCC),
					"notifyOnReplySubmission");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex), "notifyOnReplySubmission",
					ex.getMessage(), errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	public void notifyOnAuditorSubmission(long piId) {
		Service1Soap_BindingStub sbb = null;
		String auditorName = "";
		String bsmID = "";
		String auditor = "";
		String locationName = "";
		String bsmName = "";
		String bsm = "";
		BLServer blServer = null;
		Session blSession = null;
		try {
			sbb = new Service1Soap_BindingStub(new URL(wsdlUrl), new Service());

			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_user, bl_password);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
			bsm = (String) pi.getDataSlotValue("BSM");
			bsmName = bsm.split("-")[1].trim();
			bsmID = bsm.split("-")[0].trim();
			locationName = (String) pi.getDataSlotValue("branch");
			auditor = (String) pi.getDataSlotValue("auditor");
			auditorName = auditor.split("-")[1].trim();

			StringBuffer sb = new StringBuffer();
			sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
			sb.append("Dear " + bsmName + ",");
			sb.append("<br/><br/>This is in reference to the re-submission of observation with \"Open\" status for which you need to submit the reply.<br/>");
			sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.55:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link or go to URL Address : ");
			sb.append("http://10.65.5.55:18793/sbm/bpmportal/login.jsp to login into the system.</br>");
			sb.append("For any further clarification, please feel free to discuss / write to the concerned auditor.");

			sb.append("<br/><br/>Regards,");
			sb.append("<br/>" + auditorName);
			sb.append("<br/>Internal Audit Technical (ERCG)");
			sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("<br/></body></html>");

			ArrayList<String> _receiptantTO = new ArrayList();
			ArrayList<String> _receiptantCC = new ArrayList();
			_receiptantCC.add("-");
			if ((bsmID != null) && (bsmID.trim().length() > 0)) {
				String BSMEmailId = sbb.getEmployeeEmailId(bsmID.trim()).trim();
				if ((BSMEmailId != null) && (BSMEmailId.trim().length() > 0)
						&& (BSMEmailId.contains("@"))) {
					_receiptantTO.add(BSMEmailId);
				}
			}

			String[] recipientTO = null;
			String[] recipientCC = null;

			_receiptantTO.removeAll(Collections.singleton(null));
			_receiptantCC.removeAll(Collections.singleton(null));

			if ((_receiptantTO != null) && (_receiptantTO.size() > 0)) {
				recipientTO = new String[_receiptantTO.size()];
				recipientTO = (String[]) _receiptantTO.toArray(recipientTO);
			}
			if ((_receiptantCC != null) && (_receiptantCC.size() > 0)) {
				recipientCC = new String[_receiptantCC.size()];
				recipientCC = (String[]) _receiptantCC.toArray(recipientCC);
			}
			HashMap hm = new HashMap();
			hm.put("TO", recipientTO);
			hm.put("FROM", from);
			hm.put("SUBJECT", "Internal Audit Technical Report-("
					+ locationName + ")");
			hm.put("BODY", sb.toString());
			EmailSender ems = new EmailSender();
			ems.sendMutliple_To(hm);
			new ExceptionLogMaintain().mailSent_Log(piId,
					convertToCommaDelimited(recipientTO),
					convertToCommaDelimited(recipientCC),
					"notifyOnAuditorSubmission");
		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			new ExceptionLogMaintain().mailSendingError_Log(piId,
					getErrorGeneratorMethod(ex), "notifyOnAuditorSubmission",
					ex.getMessage(), errors.toString());
		} finally {
			disConnectToBLServer(blServer, blSession);
		}
	}

	private String convertToCommaDelimited(String[] list) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; (list != null) && (i < list.length); i++) {
			ret.append(list[i]);
			if (i < list.length - 1) {
				ret.append(',');
			}
		}
		return ret.toString();
	}

	private String getErrorGeneratorMethod(Throwable cause) {
		Throwable rootCause = cause;
		if ((rootCause.getCause() != null)
				&& (rootCause.getCause() != rootCause)) {
			rootCause = rootCause.getCause();
		}
		if (rootCause != null) {
			return rootCause.getStackTrace()[0].getMethodName();
		}
		return "";
	}

	private boolean chkStr(String val) {
		boolean isValid = false;
		if ((val != null) && (val.trim().length() > 0)) {
			isValid = true;
		}
		return isValid;
	}

	private boolean isProp(HashMap<String, String> prop, String propVal) {
		boolean isExist = false;
		if ((prop != null) && (!prop.isEmpty()) && (prop.containsKey(propVal))) {
			isExist = true;
		}

		return isExist;
	}

	private ArrayList<String> removeDupli(ArrayList<String> obj) {
		ArrayList<String> distinctMailIds = null;
		if ((obj != null) && (!obj.isEmpty())) {
			Set<String> hs = new HashSet();
			distinctMailIds = new ArrayList();
			hs.addAll(obj);
			distinctMailIds.addAll(hs);
		}
		return distinctMailIds;
	}
}