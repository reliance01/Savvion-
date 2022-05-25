package com.audit.email;

import com.audit.sms.BasicHttpBinding_ISendMessageStub;
import com.audit.sms.SingleMessage;
import com.savvion.mom.Service1Soap_BindingStub;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import org.apache.axis.client.Service;

public class AuditEmailUtil {
   private BLServer blserver = null;
   private BLClientUtil blc = null;
   private Session blsession = null;
   private static String bl_user = "rgicl";
   private static String bl_password = "rgicl";
   private String wsdlUrl = "http://10.65.5.55:8081/Service1.asmx?wsdl";
   //private String sms_wsdlUrl = "http://relgeninsure3/services/SendMessage.svc?wsdl";
   private String sms_wsdlUrl = "http://rgisms.reliancegeneral.co.in/services/SendMessage.svc?wsdl";   
   private String department = new String("IT");
   private String message = null;
   private String mobileNumber = null;
   private String password = null;
   private String userName = null;

   public void sendAuditGeneratedEmail(String[] args, long piid) throws Exception {
      try {
         if (args != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            this.blsession = this.blserver.connect(bl_user, bl_password);
            ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
            String branchName = (String)pi.getDataSlotValue("Branch");
            String piName = pi.getName();

            for(int i = 0; i < args.length; ++i) {
               Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
               sbb.getEmployeeEmailId(args[i]);
               long totalDuration = 864000000L;
               long startDate = (new Date()).getTime();
               new DateTime((new Date()).getTime());
               SBMCalendar.init((Properties)null);
               SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
               BizCalendar bcal = new BizCalendar(startDate, totalDuration);
               Calendar initDuedate = sbmcal.getDueDate(bcal);
               SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());
               StringBuffer sb = new StringBuffer();
               sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
               sb.append("Dear User,");
               sb.append("<br/><br/>A new audit has been generated with details,");
               sb.append("<table class=\"tablecls\">");
               sb.append("<tr><td class=\"tabletitle\">Ticket no : </td><td class=\"tablecell\">" + piName + "</td></tr>");
               sb.append("<tr><td class=\"tabletitle\">Branch : </td><td class=\"tablecell\">" + branchName + "</td></tr>");
               sb.append("<tr><td class=\"tabletitle\">Branch : </td><td class=\"tablecell\">Please reply to the draft audit report by " + expectedClosureDt + " </td></tr>");
               sb.append("<br/><br/>Click <b><a href=\"http://10.65.15.108:8080/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
               sb.append("<br/><br/>Thank you,");
               sb.append("<br/>Reliance Audit Panel Team");
               sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
               sb.append("<br/></body></html>");
               HashMap hm = new HashMap();
               hm.put("TO", args[i]);
               hm.put("FROM", "RGICL_Auditor_Panel@rcap.co.in");
               hm.put("SUBJECT", "A new audit has been generated with ticket# " + piName);
               hm.put("BODY", sb.toString());
               EmailSender ems = new EmailSender();
               ems.send(hm);
               BasicHttpBinding_ISendMessageStub bms = new BasicHttpBinding_ISendMessageStub(new URL(this.sms_wsdlUrl), new Service());
               this.message = "Please reply to the draft audit report for branch " + branchName + " by " + expectedClosureDt;
               this.mobileNumber = sbb.getEmployeeContactNo(args[i]);
               SingleMessage smsMsg = new SingleMessage(this.department, this.message, this.mobileNumber, this.password, this.userName);
               bms.send(smsMsg);
            }
         }
      } catch (Exception var25) {
      }

   }

   public void sendAuditGeneratedEmailRH(String[] args, long piid) throws Exception {
      try {
         if (args != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            this.blsession = this.blserver.connect(bl_user, bl_password);
            ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
            String branchName = (String)pi.getDataSlotValue("Branch");
            String piName = pi.getName();

            for(int i = 0; i < args.length; ++i) {
               Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
               sbb.getEmployeeEmailId(args[i]);
               long totalDuration = 345600000L;
               long startDate = (new Date()).getTime();
               new DateTime((new Date()).getTime());
               SBMCalendar.init((Properties)null);
               SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
               BizCalendar bcal = new BizCalendar(startDate, totalDuration);
               Calendar initDuedate = sbmcal.getDueDate(bcal);
               SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());
               WorkStepInstance wsi = this.blserver.getWorkStepInstance(this.blsession, piid, "BSMTask");
               WorkItemList wli = wsi.getWorkItemList();
               Vector wiList = (Vector)wli.getList();
               Date dueDt = initDuedate.getTime();

               for(int j = 0; i < wiList.size(); ++j) {
                  WorkItem wi = (WorkItem)wiList.get(j);
                  wi.setDueDate(dueDt.getTime());
                  wi.save();
                  wi.refresh();
               }

               StringBuffer sb = new StringBuffer();
               sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
               sb.append("Dear User,");
               sb.append("<br/><br/>Escalation:-Level-2-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt);
               sb.append("<table class=\"tablecls\">");
               sb.append("<tr><td class=\"tabletitle\">Ticket no : </td><td class=\"tablecell\">" + piName + "</td></tr>");
               sb.append("<tr><td class=\"tabletitle\">Branch : </td><td class=\"tablecell\">" + branchName + "</td></tr>");
               sb.append("<br/><br/>Click <b><a href=\"http://10.65.15.108:8080/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
               sb.append("<br/><br/>Thank you,");
               sb.append("<br/>Reliance Audit Panel Team");
               sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
               sb.append("<br/></body></html>");
               HashMap hm = new HashMap();
               hm.put("TO", args[i]);
               hm.put("FROM", "RGICL_Auditor_Panel@rcap.co.in");
               hm.put("SUBJECT", "A new audit has been generated with ticket# " + piName);
               hm.put("BODY", sb.toString());
               EmailSender ems = new EmailSender();
               ems.send(hm);
               BasicHttpBinding_ISendMessageStub bms = new BasicHttpBinding_ISendMessageStub(new URL(this.sms_wsdlUrl), new Service());
               this.message = "Escalation:-Level-2-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt;
               this.mobileNumber = sbb.getEmployeeContactNo(args[i]);
               SingleMessage smsMsg = new SingleMessage(this.department, this.message, this.mobileNumber, this.password, this.userName);
               bms.send(smsMsg);
            }
         }
      } catch (Exception var29) {
      }

   }

   public void sendAuditGeneratedEmailForZH(String[] args, long piid) throws Exception {
      try {
         if (args != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            this.blsession = this.blserver.connect(bl_user, bl_password);
            ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
            String branchName = (String)pi.getDataSlotValue("Branch");
            String piName = pi.getName();

            for(int i = 0; i < args.length; ++i) {
               Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
               sbb.getEmployeeEmailId(args[i]);
               long totalDuration = 259200000L;
               long startDate = (new Date()).getTime();
               new DateTime((new Date()).getTime());
               SBMCalendar.init((Properties)null);
               SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
               BizCalendar bcal = new BizCalendar(startDate, totalDuration);
               Calendar initDuedate = sbmcal.getDueDate(bcal);
               SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());
               WorkStepInstance wsi = this.blserver.getWorkStepInstance(this.blsession, piid, "BSMTask");
               WorkItemList wli = wsi.getWorkItemList();
               Vector wiList = (Vector)wli.getList();
               Date dueDt = initDuedate.getTime();

               for(int j = 0; i < wiList.size(); ++j) {
                  WorkItem wi = (WorkItem)wiList.get(j);
                  wi.setDueDate(dueDt.getTime());
                  wi.save();
                  wi.refresh();
               }

               StringBuffer sb = new StringBuffer();
               sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
               sb.append("Dear User,");
               sb.append("<br/><br/>Escalation:-Level-3-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt);
               sb.append("<table class=\"tablecls\">");
               sb.append("<tr><td class=\"tabletitle\">Ticket no : </td><td class=\"tablecell\">" + piName + "</td></tr>");
               sb.append("<tr><td class=\"tabletitle\">Branch : </td><td class=\"tablecell\">" + branchName + "</td></tr>");
               sb.append("<br/><br/>Click <b><a href=\"http://10.65.15.108:8080/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
               sb.append("<br/><br/>Thank you,");
               sb.append("<br/>Reliance Audit Panel Team");
               sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
               sb.append("<br/></body></html>");
               HashMap hm = new HashMap();
               hm.put("TO", args[i]);
               hm.put("FROM", "RGICL_Auditor_Panel@rcap.co.in");
               hm.put("SUBJECT", "A new audit has been generated with ticket# " + piName);
               hm.put("BODY", sb.toString());
               EmailSender ems = new EmailSender();
               ems.send(hm);
               BasicHttpBinding_ISendMessageStub bms = new BasicHttpBinding_ISendMessageStub(new URL(this.sms_wsdlUrl), new Service());
               this.message = "Escalation:-Level-3-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt;
               this.mobileNumber = sbb.getEmployeeContactNo(args[i]);
               SingleMessage smsMsg = new SingleMessage(this.department, this.message, this.mobileNumber, this.password, this.userName);
               bms.send(smsMsg);
            }
         }
      } catch (Exception var29) {
      }

   }

   public void sendAuditGeneratedEmailForERCG(String[] args, long piid) throws Exception {
      try {
         if (args != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            this.blsession = this.blserver.connect(bl_user, bl_password);
            ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
            String branchName = (String)pi.getDataSlotValue("Branch");
            String piName = pi.getName();

            for(int i = 0; i < args.length; ++i) {
               Service1Soap_BindingStub sbb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
               sbb.getEmployeeEmailId(args[i]);
               long totalDuration = 259200000L;
               long startDate = (new Date()).getTime();
               new DateTime((new Date()).getTime());
               SBMCalendar.init((Properties)null);
               SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
               BizCalendar bcal = new BizCalendar(startDate, totalDuration);
               Calendar initDuedate = sbmcal.getDueDate(bcal);
               SimpleDateFormat Currdateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String expectedClosureDt = Currdateformat1.format(initDuedate.getTime());
               WorkStepInstance wsi = this.blserver.getWorkStepInstance(this.blsession, piid, "BSMTask");
               WorkItemList wli = wsi.getWorkItemList();
               Vector wiList = (Vector)wli.getList();
               Date dueDt = initDuedate.getTime();

               for(int j = 0; i < wiList.size(); ++j) {
                  WorkItem wi = (WorkItem)wiList.get(j);
                  wi.setDueDate(dueDt.getTime());
                  wi.save();
                  wi.refresh();
               }

               StringBuffer sb = new StringBuffer();
               sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
               sb.append("Dear User,");
               sb.append("<br/><br/>Escalation:-Level-3-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt);
               sb.append("<table class=\"tablecls\">");
               sb.append("<tr><td class=\"tabletitle\">Ticket no : </td><td class=\"tablecell\">" + piName + "</td></tr>");
               sb.append("<tr><td class=\"tabletitle\">Branch : </td><td class=\"tablecell\">" + branchName + "</td></tr>");
               sb.append("<br/><br/>Click <b><a href=\"http://10.65.15.108:8080/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
               sb.append("<br/><br/>Thank you,");
               sb.append("<br/>Reliance Audit Panel Team");
               sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
               sb.append("<br/></body></html>");
               HashMap hm = new HashMap();
               hm.put("TO", args[i]);
               hm.put("FROM", "RGICL_Auditor_Panel@rcap.co.in");
               hm.put("SUBJECT", "A new audit has been generated with ticket# " + piName);
               hm.put("BODY", sb.toString());
               EmailSender ems = new EmailSender();
               ems.send(hm);
               BasicHttpBinding_ISendMessageStub bms = new BasicHttpBinding_ISendMessageStub(new URL(this.sms_wsdlUrl), new Service());
               this.message = "Escalation:-Level-3-Please make sure that Br-" + branchName + "replies to draft audit report by " + expectedClosureDt;
               this.mobileNumber = sbb.getEmployeeContactNo(args[i]);
               SingleMessage smsMsg = new SingleMessage(this.department, this.message, this.mobileNumber, this.password, this.userName);
               bms.send(smsMsg);
            }
         }
      } catch (Exception var29) {
      }

   }
}
