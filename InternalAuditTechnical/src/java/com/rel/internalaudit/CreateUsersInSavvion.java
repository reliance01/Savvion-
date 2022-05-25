package com.rel.internalaudit;

import java.net.InetAddress;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.axis.client.Service;

import com.audit.email.EmailSender;
import com.savvion.mom.Service1Soap_BindingStub;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;

public class CreateUsersInSavvion {
	private BLServer blserver = null;
	private Session blsession = null;
	private static String bl_user = "rgicl";
	private static String bl_password = "rgicl";
	private String wsdlUrl = "";
	private String AuditGroup = "Audit";
	AdvanceGroup groupObj = null;

	public CreateUsersInSavvion() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			if (ip.contains("10.65.15.")) {
				wsdlUrl = "http://10.65.15.160:8081/Service1.asmx?wsdl";
			} else {
				wsdlUrl = "http://10.65.5.158:8081/Service1.asmx?wsdl";
			}
		} catch (Exception e) {

		}
	}

	public void connectToBLServer() {
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(bl_user, bl_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disConnectToBLServer() {
		if (blsession != null) {
			try {
				blserver.disConnect(blsession);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkUsersExistance(long piid) {
		if (piid != 0L) {
			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				if (ip.contains("10.65.15.")) {
					wsdlUrl = "http://10.65.15.160:8081/Service1.asmx?wsdl";
				} else {
					wsdlUrl = "http://10.65.5.158:8081/Service1.asmx?wsdl";
				}
				connectToBLServer();
				ProcessInstance pi = blserver.getProcessInstance(blsession,
						piid);
				String auditor = (String) pi.getDataSlotValue("auditor");
				String auditee = (String) pi.getDataSlotValue("BSM");
				Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				if (auditor != null && auditor.trim().length() > 0
						&& auditor.contains("-")) {
					String auditorUserId = auditor.split("-")[0].trim();
					String auditorName = auditor.split("-")[1].trim();
					if (auditorUserId != null
							&& auditorUserId.trim().length() > 0
							&& auditorName != null
							&& auditorName.trim().length() > 0) {
						String auditorEmailId = sbb
								.getEmployeeEmailId(auditorUserId);
						if (auditorEmailId != null
								&& auditorEmailId.trim().length() > 0
								&& auditorEmailId.contains("@")) {
							ArrayList userDetail = new ArrayList();
							HashMap hm = new HashMap();
							hm.put("USERID", auditorUserId);
							hm.put("EMAIL", auditorEmailId);
							hm.put("FIRSTNAME", auditorName);
							userDetail.add(hm);
							createUser(userDetail);
						}
					}
				}
				if (auditee != null && auditee.trim().length() > 0
						&& auditee.contains("-")) {
					String auditeeUserId = auditee.split("-")[0].trim();
					String auditeeName = auditee.split("-")[1].trim();
					if (auditeeUserId != null
							&& auditeeUserId.trim().length() > 0
							&& auditeeName != null
							&& auditeeName.trim().length() > 0) {
						String auditeeEmailId = sbb
								.getEmployeeEmailId(auditeeUserId);
						if (auditeeEmailId != null
								&& auditeeEmailId.trim().length() > 0
								&& auditeeEmailId.contains("@")) {
							ArrayList userDetail = new ArrayList();
							HashMap hm = new HashMap();
							hm.put("USERID", auditeeUserId);
							hm.put("EMAIL", auditeeEmailId);
							hm.put("FIRSTNAME", auditeeName);
							userDetail.add(hm);
							createUser(userDetail);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnectToBLServer();
			}
		}
	}

	public void createUser(ArrayList ls) {
		{
			try {
				if (ls != null && !ls.isEmpty()) {
					for (int i = 0; i < ls.size(); i++) {
						HashMap hm = (HashMap) ls.get(i);
						if (hm != null && !hm.isEmpty()) {
							if (hm.get("USERID") != null
									&& hm.get("USERID").toString().trim()
											.length() > 0
									&& !(hm.get("USERID").toString()
											.toLowerCase().contains("null"))) {
								String user = hm.get("USERID").toString();
								UserManager um = new UserManager();
								JDBCRealm r = (JDBCRealm) um.getDefaultRealm();
								JDBCUser u = (JDBCUser) r.getUser(user);
								if (u == null) {
									Hashtable attrs = new Hashtable();
									attrs.put(Realm.USERID, user);
									attrs.put(Realm.PASSWD, user);
									attrs.put(Realm.FIRSTNAME, hm.get(
											"FIRSTNAME").toString());
									attrs.put(Realm.EMAIL, hm.get("EMAIL")
											.toString());
									boolean bln = r.addUser(user, attrs);
									if (bln) {
										addUserIntoGroup(user);
										StringBuffer sb = new StringBuffer();
										sb
												.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
										sb.append("Dear Approver "
												+ hm.get("FIRSTNAME")
														.toString() + ",");
										sb
												.append("<br/><br/>Your login id has been created in Savvion System with below details,");
										sb.append("<table class=\"tablecls\">");
										sb
												.append("<tr><td class=\"tabletitle\">User Id : </td><td class=\"tablecell\">"
														+ user + "</td></tr>");
										sb
												.append("<tr><td class=\"tabletitle\">Password: </td><td class=\"tablecell\">"
														+ user + "</td></tr>");
										sb
												.append("<br/><br/>Click <b><a href=\"http://bpm.reliancegeneral.co.in:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
										sb.append("<br/><br/>Thank you,");
										sb
												.append("<br/>Reliance Application Support Team");
										sb
												.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
										sb.append("<br/></body></html>");

										HashMap<String, String> hm1 = new HashMap<String, String>();
										hm1
												.put("FROM",
														"RGICLApplicationSupport@rcap.co.in");
										hm1.put("TO", hm.get("EMAIL")
												.toString());
										// hm.put("CC", ccAddress);
										hm1.put("SUBJECT",
												"Savvion Login ID Generated");
										hm1.put("BODY", sb.toString());
										EmailSender ems = new EmailSender();
										ems.send(hm);
									}
								} else {
									addUserIntoGroup(user);
								}
							}
						}
					}
				}
			} catch (Throwable th) {
				System.out.println("Error in creating Savvion User Account : "
						+ th.getMessage());
			}
		}
	}

	private void addUserIntoGroup(String user) {
		if (!(checkUserECS(user, AuditGroup))) {
			// Get Group Object
			groupObj = (AdvanceGroup) UserManager.getGroup(AuditGroup);
			// Add to Group
			groupObj.addUserMember(user);
		}
	}

	private boolean checkUserECS(String userId, String groupName) {
		boolean isMember = false;
		try {
			// Get User Object
			User usr = UserManager.getUser(userId);
			String groupNames[] = usr.getAllGroupNames();
			for (int i = 0; i < groupNames.length; i++) {
				// Check the Group Names
				if (groupNames[i].equals(groupName))
					isMember = true;
			}
		} catch (Exception e) {
			System.out.println("checkUserECS Service:::: Exception "
					+ e.getMessage());
		}
		return isMember;
	}
}
