package com.agent.ejb.initiateagent;

import java.util.HashMap;

import com.agent.ejb.mail.EmailSender;

import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;

public class CreateSavvionUser {

	public void checkUserExists(String sapId) {

		if (sapId.length() > 0) {
			JDBCRealm r = (JDBCRealm) UserManager.getDefaultRealm();
			JDBCUser u = (JDBCUser) r.getUser(sapId);
			if (u == null) {
				AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
				String userDetails = objagentflowprocess
						.getEmployeeDetails(sapId);
				String userName = userDetails.split(",")[0].trim();
				String userMailId = userDetails.split(",")[1].trim();
				boolean isCreated = createUser(sapId, userName, userMailId);
				if (isCreated == true) {
					userCreationNotification(userName, userMailId, sapId);
				}

			}
		}
	}

	public boolean createUser(String sapId, String userName, String userMailId) {
		JDBCRealm jdbcRealm = new JDBCRealm();

		boolean isCreated = false;

		try {
			java.util.Hashtable attrs = new java.util.Hashtable();
			attrs.put("firstname", userName);
			attrs.put("lastname", userName);
			attrs.put("email", userMailId);
			attrs.put("password", sapId);

			boolean flag = jdbcRealm.addUser(sapId, attrs);
			if (flag == true) {
				System.out.println("User Account is created for" + userName);
				isCreated = true;
			}
		} catch (Exception ex) {
			isCreated = false;
			ex.printStackTrace();
			System.out.println("Error while creating user in savvion "
					+ ex.getMessage());
		}

		return isCreated;

	}

	public void userCreationNotification(String userName, String userMailId,
			String sapId) {
		System.out.println("Inside userCreationNotification method!!");
		try
		{
		StringBuffer sb = new StringBuffer();
		sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + userName + ",");
		sb.append("<br/><br/>Your login id has been created in Savvion System with below details,");
		sb.append("<table class=\"tablecls\">");
		sb.append("<tr><td class=\"tabletitle\">User Id : </td><td class=\"tablecell\">"
				+ sapId + "</td></tr>");
		sb.append("<tr><td class=\"tabletitle\">Password: </td><td class=\"tablecell\"> Your Windows Password </td></tr>");
		sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.158/sbm/bpmportal/login.jsp\" >here</a> </b>on this link to login into the system");
		sb.append("<br/><br/>Thank you,");
		sb.append("<br/>Reliance Application Support Team");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");

		HashMap<String, String> hm1 = new HashMap<String, String>();
		hm1.put("FROM", "RGICLApplicationSupport@rcap.co.in");
		hm1.put("TO", userMailId);
		// hm.put("CC", ccAddress);
		hm1.put("SUBJECT", "Savvion Login ID Generated");
		hm1.put("BODY", sb.toString());
		EmailSender ems = new EmailSender();
		ems.send(hm1);
		}
		catch(Exception e)
		{
			System.out.println("Error while sending User creation mail " + e.getMessage());
		}

	}
}
