package services.techdesk.impl;

import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.BSProcessInstance;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.filter.BSProcessInstanceFilter;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceData;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceRS;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.BLConstants;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import org.apache.axis.AxisFault;
import services.techdesk.util.RequestObject;
import services.techdesk.util.ResponseObject;
import services.techdesk.util.TechDeskObject;
import services.techdesk.util.WorkItemObject;
import techdesk.email.EmailSender;

public class WS_TechDesk {
   private static BizLogicManager pak = null;
   private String ptName = "TechDesk";
   private static Byte[] bytearray = new Byte[0];

   public WS_TechDesk() {
      NLog.ws.debug("WS_TechDesk service invoked");
   }

   private String getName(String perf) throws AxisFault, Exception {
      String name = perf;
      JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
      JDBCUser usr = (JDBCUser)r.getUser(perf);
      if (usr != null) {
         name = usr.getAttribute("FIRSTNAME") + " " + usr.getAttribute("LASTNAME") + " (" + usr.getAttribute("EMAIL") + ")";
      }

      return name;
   }

   public String getPerformer(String ticketNumber, String performerType) throws AxisFault, Exception {
      String perf = "";
      BLServer blserver = BLClientUtil.getBizLogicServer();
      Session blsession = blserver.connect("rgicl", "rgicl");
      QueryService qs = new QueryService(blsession);
      BSProcessInstanceFilter bsf = new BSProcessInstanceFilter("Test", "TechDesk");
      bsf.setCondition("BSIDS.TICKETNO='" + ticketNumber + "'");
      bsf.setFetchSize(0);
      BSProcessInstance bsp = qs.getBSProcessInstance();
      BSProcessInstanceRS bsprs = bsp.getActivatedInstances(bsf);
      if (bsprs != null) {
         List lst = bsprs.getList();

         for(int i = 0; i < lst.size(); ++i) {
            BSProcessInstanceData bspd = (BSProcessInstanceData)lst.get(i);
            long piid = bspd.getProcessInstanceID();
            WorkStepInstance wsi = blserver.getWorkStepInstance(blsession, piid, performerType);
            WorkItemList wli = wsi.getWorkItemList();
            List wliList = wli.getList();
            if (wliList != null && !wliList.isEmpty()) {
               for(int j = 0; j < wliList.size(); ++j) {
                  WorkItem wi = (WorkItem)wliList.get(j);
                  perf = wi.getPerformer();
               }
            }
         }

         perf = this.getName(perf);
      }

      blserver.disConnect(blsession);
      return perf;
   }

   private boolean checkNull(Object dsValue) {
      return dsValue == null || dsValue instanceof String && (dsValue.equals("<NULL>") || dsValue.equals(""));
   }

   public boolean checkUsersExists(String userId) {
      boolean isExists = false;

      try {
         if (userId != null && userId.length() > 0) {
            JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
            JDBCUser usr = (JDBCUser)r.getUser(userId);
            if (usr != null) {
               isExists = true;
            }
         }
      } catch (Exception var5) {
      }

      return isExists;
   }

   private String getUserPassword(String userId) {
      String password = "";

      try {
         User userObject = UserManager.getUser(userId);
         password = userObject.getAttribute("password");
         PService p = PService.self();
         password = p.decrypt(password);
      } catch (Exception var5) {
      }

      return password;
   }

   private static BizLogicManager getBizLogicManager() throws AxisFault {
      if (pak == null) {
         synchronized(bytearray) {
            if (pak == null) {
               try {
                  String appserver = ServiceLocator.self().getAppServerID();
                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
                  pak = blmhome.create();
               } catch (Throwable var3) {
                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private String connect(String userId, String password) throws AxisFault {
      String sessionId = null;

      try {
         sessionId = getBizLogicManager().connect(userId, password);
         return sessionId;
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private String formatDate(long l) {
      String date = null;
      SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
      System.out.println(dt1.format(new Date(l)));
      return (String)date;
   }

   public ResponseObject CreateTicket(RequestObject reqObj) throws AxisFault {
      NLog.ws.debug("STARTCALLDESK method invoked");
      String UserRemark = null;
      String ApproverEmailID = null;
      String ApproverDesignation = null;
      String ApproverName = null;
      String UserEmailID = null;
      String UserContactNo = null;
      String ApplicationType = null;
      String ApproverID = null;
      String ReopenId = null;
      String Branch = null;
      String CallLogDate = null;
      String UploadScreen = null;
      String CallCreatedBy = null;
      String CallType = null;
      String IssueRequestId = null;
      String TicketNo = null;
      String ChannelSM = null;
      String TeamName = null;
      String IssueReqSubTypeID = null;
      String TeamTAT = null;
      String ApproverUserID2 = null;
      String AppSupportPerformer = null;
      String Approver2Dsgn = null;
      String Approver2Email = null;
      String Approver2Name = null;
      String TicketValue = null;
      String ApproverTAT = null;
      String AppSupportTAT = null;
      String username = null;
      String password = null;
      String piName = null;
      String priority = null;
      ResponseObject resObj = new ResponseObject();

      try {
         if (reqObj == null) {
            resObj.setResponseCode("5070");
            resObj.setMessage("Request Object is Null");
            return resObj;
         }

         TechDeskObject techdesk = reqObj.getTechdesk();
         if (techdesk == null) {
            resObj.setResponseCode("5070");
            resObj.setMessage("Techdesk Object is Null");
            return resObj;
         }

         UserRemark = techdesk.getUserRemark();
         ApproverEmailID = techdesk.getApproverEmailID();
         ApproverDesignation = techdesk.getApproverDesignation();
         ApproverName = techdesk.getApproverName();
         UserEmailID = techdesk.getUserEmailID();
         UserContactNo = techdesk.getUserContactNo();
         ApplicationType = techdesk.getApplicationType();
         ApproverID = techdesk.getApproverID();
         ReopenId = techdesk.getReopenId();
         Branch = techdesk.getBranch();
         CallLogDate = techdesk.getCallLogDate();
         UploadScreen = techdesk.getUploadScreen();
         CallCreatedBy = techdesk.getCallCreatedBy();
         CallType = techdesk.getCallType();
         IssueRequestId = techdesk.getIssueRequestId();
         TicketNo = techdesk.getTicketNo();
         ChannelSM = techdesk.getChannelSM();
         TeamName = techdesk.getTeamName();
         IssueReqSubTypeID = techdesk.getIssueReqSubTypeID();
         TeamTAT = techdesk.getTeamTAT();
         ApproverUserID2 = techdesk.getApproverUserID2();
         AppSupportPerformer = techdesk.getAppSupportPerformer();
         Approver2Dsgn = techdesk.getApprover2Dsgn();
         Approver2Email = techdesk.getApprover2Email();
         Approver2Name = techdesk.getApprover2Name();
         TicketValue = techdesk.getTicketValue();
         ApproverTAT = techdesk.getApproverTAT();
         AppSupportTAT = techdesk.getAppSupportTAT();
         username = techdesk.getUsername();
         password = techdesk.getPassword();
         piName = techdesk.getPiName();
         priority = techdesk.getPriority();
         HashMap hm1 = new HashMap();
         if (ApproverID != null && ApproverID.trim().length() > 0) {
            hm1.put("USERID", ApproverID);
            hm1.put("EMAIL", ApproverEmailID);
            hm1.put("FIRSTNAME", ApproverName);
         }

         HashMap hm2 = new HashMap();
         if (ApproverUserID2 != null && ApproverUserID2.trim().length() > 0) {
            hm2.put("USERID", ApproverUserID2);
            hm2.put("EMAIL", Approver2Email);
            hm2.put("FIRSTNAME", Approver2Name);
         }

         ArrayList ls = new ArrayList();
         ls.add(hm1);
         ls.add(hm2);
         this.createUser(ls);
         String sessionId = this.connect(username, password);
         HashMap dsTypeMap = new HashMap();
         dsTypeMap.put("UserRemark", "STRING");
         dsTypeMap.put("ApproverEmailID", "STRING");
         dsTypeMap.put("ApproverDesignation", "STRING");
         dsTypeMap.put("ApproverName", "STRING");
         dsTypeMap.put("UserEmailID", "STRING");
         dsTypeMap.put("UserContactNo", "STRING");
         dsTypeMap.put("ApplicationType", "STRING");
         dsTypeMap.put("ApproverID", "STRING");
         dsTypeMap.put("ReopenId", "STRING");
         dsTypeMap.put("Branch", "STRING");
         dsTypeMap.put("CallLogDate", "STRING");
         dsTypeMap.put("UploadScreen", "STRING");
         dsTypeMap.put("CallCreatedBy", "STRING");
         dsTypeMap.put("CallType", "STRING");
         dsTypeMap.put("IssueRequestId", "STRING");
         dsTypeMap.put("TicketNo", "STRING");
         dsTypeMap.put("ChannelSM", "STRING");
         dsTypeMap.put("TeamName", "STRING");
         dsTypeMap.put("IssueReqSubTypeID", "STRING");
         dsTypeMap.put("TeamTAT", "STRING");
         dsTypeMap.put("ApproverUserID2", "STRING");
         dsTypeMap.put("AppSupportPerformer", "STRING");
         dsTypeMap.put("Approver2Dsgn", "STRING");
         dsTypeMap.put("Approver2Email", "STRING");
         dsTypeMap.put("Approver2Name", "STRING");
         dsTypeMap.put("TicketValue", "STRING");
         HashMap dsValues = new HashMap();
         dsValues.put("UserRemark", UserRemark);
         dsValues.put("ApproverEmailID", ApproverEmailID);
         dsValues.put("ApproverDesignation", ApproverDesignation);
         dsValues.put("ApproverName", ApproverName);
         dsValues.put("UserEmailID", UserEmailID);
         dsValues.put("UserContactNo", UserContactNo);
         dsValues.put("ApplicationType", ApplicationType);
         dsValues.put("ApproverID", ApproverID);
         dsValues.put("ReopenId", ReopenId);
         dsValues.put("Branch", Branch);
         dsValues.put("CallLogDate", CallLogDate);
         dsValues.put("UploadScreen", UploadScreen);
         dsValues.put("CallCreatedBy", CallCreatedBy);
         dsValues.put("CallType", CallType);
         dsValues.put("IssueRequestId", IssueRequestId);
         dsValues.put("TicketNo", TicketNo);
         dsValues.put("ChannelSM", ChannelSM);
         dsValues.put("TeamName", TeamName);
         dsValues.put("IssueReqSubTypeID", IssueReqSubTypeID);
         dsValues.put("TeamTAT", TeamTAT);
         dsValues.put("ApproverUserID2", ApproverUserID2);
         dsValues.put("AppSupportPerformer", AppSupportPerformer);
         dsValues.put("Approver2Dsgn", Approver2Dsgn);
         dsValues.put("Approver2Email", Approver2Email);
         dsValues.put("Approver2Name", Approver2Name);
         dsValues.put("TicketValue", TicketValue);
         Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
         String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
         resObj.setResponseCode("5000");
         resObj.setMessage(pi);
         this.disconnect(sessionId);
      } catch (Exception var44) {
      }

      return resObj;
   }

   public ResponseObject getTaskList(RequestObject reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();

      try {
         String responseCode = null;
         ArrayList resultTaskList = new ArrayList();
         WorkItemObject[] workItemObject = (WorkItemObject[])null;
         QueryService qs = null;
         String sessionId = null;
         Session sess = null;
         if (reqObj == null) {
            resObj.setResponseCode("5070");
            resObj.setMessage("Request Object is Null");
            return resObj;
         } else {
            String userId = reqObj.getUserId();
            if (this.checkNull(userId)) {
               responseCode = "5001";
               resObj.setMessage("User Id is Null");
               return resObj;
            } else {
               sessionId = this.connect(userId, this.getUserPassword(userId));
               sess = getBizLogicManager().getSession(sessionId);
               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
               qs = new QueryService(sess);
               wifil.setFetchSize(0);
               QSWorkItem qswi = qs.getWorkItem();
               int[] var10000 = new int[2];
               BLConstants.single().getClass();
               var10000[0] = 27;
               BLConstants.single().getClass();
               var10000[1] = 28;
               int[] states = var10000;
               WorkItemList wiList = qswi.getList(wifil, states);
               if (wiList != null) {
                  List list = wiList.getList();
                  if (list != null && !list.isEmpty()) {
                     for(int i = 0; i < list.size(); ++i) {
                        WorkItem wi = (WorkItem)list.get(i);
                        String value = "";
                        WorkItemObject workitem = new WorkItemObject();
                        DataSlot ds = null;
                        ds = wi.getDataSlot("TicketNo");
                        value = (String)ds.getValue();
                        workitem.setTicketNo(value);
                        System.out.println("Ticket Number" + value);
                        value = wi.getPerformer();
                        workitem.setPerformer(value);
                        workitem.setAssignDate(this.formatDate(wi.getStartTime()));
                        workitem.setDueDate(this.formatDate(wi.getDueDate()));
                        workitem.setWorkStepName(wi.getWorkStepName());
                        resultTaskList.add(workitem);
                     }
                  }
               }

               getBizLogicManager().disconnect(sessionId);
               QueryService.clean();
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
                  resObj.setMessage("Tickets Listed in Inbox");
                  resObj.setResponseCode(responseCode);
                  return resObj;
               } else {
                  responseCode = "5042";
                  resObj.setMessage("No Tickets in Inbox");
                  resObj.setResponseCode(responseCode);
                  return resObj;
               }
            }
         }
      } catch (Exception var20) {
         var20.printStackTrace();
         resObj.setMessage("Error fetching inbox cases");
         resObj.setResponseCode("5050-Failure Exception");
         return resObj;
      }
   }

   private WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
      WorkItemObject[] resultWorkitems = (WorkItemObject[])null;
      resultWorkitems = new WorkItemObject[workitemlist.size()];
      workitemlist.toArray(resultWorkitems);
      return resultWorkitems;
   }

   public ResponseObject UpdateTicket(RequestObject reqObj) throws AxisFault {
      String sessionId = null;
      String responseCode = null;
      boolean isAvailable = true;
      String processInstanceName = null;
      String userId = null;
      String status = null;
      boolean bln = false;
      ResponseObject resObj = new ResponseObject();
      String workStepName = null;
      new WorkItemObject();
      String TicketNumber = null;
      String wsName = null;
      String substatus = null;
      String statusOther = null;
      String assigntouserid = null;
      byte[] filedata = (byte[])null;
      String wiName = null;
      String remarks = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         userId = reqObj.getUserId();
         status = reqObj.getStatus();
         TicketNumber = reqObj.getTicketNumber();
         substatus = reqObj.getStatus_Sub();
         statusOther = reqObj.getStatus_Other();
         remarks = reqObj.getRemarks();
         filedata = reqObj.getFileBytes();
         assigntouserid = reqObj.getAssignToUserId();

         try {
            if (!this.checkNull(TicketNumber) && !this.checkNull(userId) && !this.checkNull(status)) {
               if ((status.equalsIgnoreCase("Resend To L1") || status.equalsIgnoreCase("Forward To L2")) && assigntouserid == null) {
                  resObj.setResponseCode("6009");
                  resObj.setMessage("AssignToUserId User Id Invalid");
                  return resObj;
               }

               BLServer blserver = BLClientUtil.getBizLogicServer();
               Session blsession = null;
               QueryService qs = null;
               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
               wifil.setCondition("BLIDS.TICKETNO='" + TicketNumber + "'");
               wifil.setFetchSize(0);
               blsession = blserver.connect(userId, this.getUserPassword(userId));
               String toUser = reqObj.getAssignToUserId();
               qs = new QueryService(blsession);
               QSWorkItem qswi = qs.getWorkItem();
               int[] var10000 = new int[2];
               BLConstants.single().getClass();
               var10000[0] = 27;
               BLConstants.single().getClass();
               var10000[1] = 28;
               int[] states = var10000;
               WorkItemList wiList = qswi.getList(wifil, states);
               if (wiList == null || wiList.getList().size() <= 0) {
                  resObj.setResponseCode("5060");
                  resObj.setMessage("Ticket Number not Found");
                  return resObj;
               }

               List list = wiList.getList();
               System.out.println(list.size());
               WorkItem wi;
               if (list != null && !list.isEmpty()) {
                  for(int i = 0; i < list.size(); ++i) {
                     wi = (WorkItem)list.get(i);
                     wsName = wi.getWorkStepName();
                     wiName = wi.getName();
                     processInstanceName = wi.getProcessInstanceName();
                     if (wi.isAssigned()) {
                        isAvailable = false;
                     }
                  }
               }

               ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
               HashMap hm;
               if (wsName != null && wsName.equalsIgnoreCase("Approver") && status != null && (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Open at Second Approver") || status.equalsIgnoreCase("Send Back"))) {
                  hm = new HashMap();
                  hm.put("ApproverStatus", status);
                  hm.put("FirstApproverRemark", remarks);
                  pi.updateSlotValue(hm);
                  if (isAvailable) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                  }

                  this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                  resObj.setResponseCode("5000");
                  resObj.setMessage("Ticket " + status + " Successfully");
               }

               if (wsName != null && wsName.equalsIgnoreCase("VerifyApproval") && status != null && (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Send Back"))) {
                  hm = new HashMap();
                  hm.put("VerifyApprovalStatus", status);
                  hm.put("VerifyApprovalRemark", remarks);
                  pi.updateSlotValue(hm);
                  if (isAvailable) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                  }

                  this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                  resObj.setResponseCode("5000");
                  resObj.setMessage("Ticket " + status + " Successfully");
               }

               if (wsName != null && wsName.equalsIgnoreCase("AppSupport") && status != null && (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("Resend To L1") || status.equalsIgnoreCase("Forward To L2"))) {
                  hm = new HashMap();
                  hm.put("AppsupportFinalStatus", status);
                  hm.put("AppSupportRemark", remarks);
                  hm.put("AppSupportPhoneRemark", "<NULL>");
                  if (substatus != null) {
                     hm.put("CallStatus", substatus);
                  }

                  if (statusOther != null) {
                     hm.put("CallStatusOther", "<NULL>");
                  }

                  pi.updateSlotValue(hm);
                  if (isAvailable) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                  }

                  if (status.equalsIgnoreCase("Resolved")) {
                     this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                  }

                  if (status.equalsIgnoreCase("Resend To L1") || status.equalsIgnoreCase("Forward To L2")) {
                     wi = blserver.getWorkItem(blsession, wiName);
                     wi.reAssign(assigntouserid);
                     wi.save();
                  }

                  resObj.setResponseCode("5000");
                  resObj.setMessage("Ticket " + status + " Successfully");
               }

               blserver.disConnect(blsession);
            } else {
               if (this.checkNull(userId)) {
                  resObj.setResponseCode("5030-Null or Empty value passed for userId code");
                  resObj.setMessage("USer ID Invalid");
                  return resObj;
               }

               if (this.checkNull(TicketNumber)) {
                  resObj.setResponseCode("6009");
                  resObj.setMessage("Ticket Number Invalid");
                  return resObj;
               }

               if (this.checkNull(status)) {
                  resObj.setResponseCode("6009");
                  resObj.setMessage("Status Invalid");
                  return resObj;
               }
            }
         } catch (Exception var31) {
            resObj.setResponseCode("5050-Failure Exception");
            resObj.setMessage(var31.getMessage());
            var31.printStackTrace();
         }
      }

      return resObj;
   }

   private boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private void createUser(ArrayList ls) {
      try {
         if (ls != null && !ls.isEmpty()) {
            for(int i = 0; i < ls.size(); ++i) {
               HashMap hm = (HashMap)ls.get(i);
               if (hm != null && !hm.isEmpty() && hm.get("USERID") != null && hm.get("USERID").toString().trim().length() > 0 && !hm.get("USERID").toString().toLowerCase().contains("null")) {
                  String user = hm.get("USERID").toString();
                  new UserManager();
                  JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
                  JDBCUser u = (JDBCUser)r.getUser(user);
                  if (u == null) {
                     Hashtable attrs = new Hashtable();
                     attrs.put("userid", user);
                     attrs.put("password", user);
                     attrs.put("firstname", hm.get("FIRSTNAME").toString());
                     attrs.put("email", hm.get("EMAIL").toString());
                     boolean bln = r.addUser(user, attrs);
                     if (bln) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
                        sb.append("Dear Approver " + hm.get("FIRSTNAME").toString() + ",");
                        sb.append("<br/><br/>Your login id has been created in Savvion System with below details,");
                        sb.append("<table class=\"tablecls\">");
                        sb.append("<tr><td class=\"tabletitle\">User Id : </td><td class=\"tablecell\">" + user + "</td></tr>");
                        sb.append("<tr><td class=\"tabletitle\">Password: </td><td class=\"tablecell\">" + user + "</td></tr>");
                        sb.append("<br/><br/>Click <b><a href=\"http://bpm.reliancegeneral.co.in:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
                        sb.append("<br/><br/>Thank you,");
                        sb.append("<br/>Reliance Application Support Team");
                        sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                        sb.append("<br/></body></html>");
                        HashMap<String, String> hm1 = new HashMap();
                        hm1.put("FROM", "RGICLApplicationSupport@rcap.co.in");
                        hm1.put("TO", hm.get("EMAIL").toString());
                        hm1.put("SUBJECT", "Savvion Login ID Generated");
                        hm1.put("BODY", sb.toString());
                        EmailSender ems = new EmailSender();
                        ems.send(hm);
                     }
                  }
               }
            }
         }
      } catch (Throwable var13) {
         NLog.ws.debug("Error in creating user and sending email " + var13.getMessage());
      }

   }

   private Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
      Hashtable resolvedValues = new Hashtable();
      if (valueMap != null && valueMap.size() != 0) {
         Iterator sIterator = dsTypes.entrySet().iterator();

         while(true) {
            while(sIterator.hasNext()) {
               Entry sEntry = (Entry)sIterator.next();
               String key = (String)sEntry.getKey();
               String type = (String)sEntry.getValue();
               Object dsvalue = valueMap.get(key);
               if (this.checkNull(dsvalue)) {
                  resolvedValues.put(key, "<NULL>");
               } else if (!type.equals("DOCUMENT")) {
                  String[] data;
                  if (type.equals("LIST")) {
                     data = (String[])dsvalue;
                     int size = data.length;
                     Vector v = new Vector(size);

                     for(int i = 0; i < size; ++i) {
                        v.add(data[i]);
                     }

                     resolvedValues.put(key, v);
                  } else if (type.equals("XML")) {
                     XML xml = new XML((String)dsvalue);
                     resolvedValues.put(key, xml);
                  } else if (type.equals("DECIMAL")) {
                     Decimal dec = new Decimal((BigDecimal)dsvalue);
                     resolvedValues.put(key, dec);
                  } else if (type.equals("DATETIME")) {
                     Calendar cal = (Calendar)dsvalue;
                     DateTime dt = new DateTime(cal.getTimeInMillis());
                     resolvedValues.put(key, dt);
                  } else if (!type.equals("MAP")) {
                     resolvedValues.put(key, dsvalue);
                  } else {
                     data = (String[])dsvalue;
                     Map hm = new HashMap();

                     for(int i = 0; i < data.length; ++i) {
                        StringTokenizer st = new StringTokenizer(data[i], "=");
                        hm.put(st.nextToken(), st.nextToken());
                     }

                     resolvedValues.put(key, hm);
                  }
               }
            }

            return resolvedValues;
         }
      } else {
         return resolvedValues;
      }
   }

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }
}
