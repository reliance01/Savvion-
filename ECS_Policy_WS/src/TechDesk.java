import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.BSProcessInstance;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.filter.BSProcessInstanceFilter;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceData;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceRS;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.userManager.JDBCGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import services.techdesk.util.TechDeskObject;
import techdesk.email.EmailSender;

public class TechDesk {
   private static BizLogicManager pak = null;
   private String ptName = "TechDesk";
   private static Byte[] bytearray = new Byte[0];
   private static final String DATASOURCE = "jdbc/SBMCommonDB";
   private static final String userName = "rgicl";
   private static final String password = "rgicl";
   private String sqlString = "SELECT GROUPNAME FROM CALLDESK_SUPPORTESC WHERE TEAMNAME=?";

   public TechDesk() {
      NLog.ws.debug("TechDesk service invoked");
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

   public String getTeamName(String ticketNumber) throws AxisFault, Exception {
      String perf = "";
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DataSource ds = null;
      InitialContext jndiCntx = null;

      try {
         jndiCntx = new InitialContext();
         ds = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
         conn = ds.getConnection();
         pstmt = conn.prepareStatement("SELECT TEAMNAME FROM TECHDESK WHERE TICKETNO=? ORDER BY PROCESS_INSTANCE_ID DESC FETCH FIRST 1 ROWS ONLY");
         pstmt.setString(1, ticketNumber);
         rs = pstmt.executeQuery();
         if (rs != null) {
            while(rs.next()) {
               perf = rs.getString("TEAMNAME");
            }
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (conn != null) {
            conn.close();
         }
      } catch (Exception var9) {
         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (conn != null) {
            conn.close();
         }
      }

      return perf;
   }

   public boolean UpdateTicket(TechDeskObject obj) throws Exception {
      boolean bln = false;
      String ticketno = null;
      String appName = null;
      String issueReqId = null;
      String issueReqSubId = null;
      String teamName = null;
      String callType = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Session blsession = null;
      BLServer blserver = null;
      ProcessTemplate pt = null;
      DataSource ds = null;
      Context jndiCntx = null;
      long piid = 0L;

      try {
         if (obj != null) {
            ticketno = obj.getTicketNo();
            appName = obj.getApplicationType();
            issueReqId = obj.getIssueRequestId();
            issueReqSubId = obj.getIssueReqSubTypeID();
            teamName = obj.getTeamName();
            callType = obj.getCallType();
            if (ticketno != null && appName != null && issueReqId != null && issueReqSubId != null && teamName != null) {
               blserver = BLClientUtil.getBizLogicServer();
               blsession = blserver.connect("rgicl", "rgicl");
               pt = blserver.getProcessTemplate(blsession, this.ptName);
               String ptid = (new Long(pt.getID())).toString();
               jndiCntx = new InitialContext();
               ds = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
               conn = ds.getConnection();
               pstmt = conn.prepareStatement("SELECT PROCESS_INSTANCE_ID FROM BIZLOGIC_DS_" + ptid + " WHERE TICKETNO=?");
               pstmt.setString(1, ticketno);
               rs = pstmt.executeQuery();
               if (rs != null) {
                  while(rs.next()) {
                     piid = rs.getLong("PROCESS_INSTANCE_ID");
                  }
               }

               if (pstmt != null) {
                  pstmt.close();
               }

               if (rs != null) {
                  rs.close();
               }

               if (conn != null) {
                  conn.close();
               }

               if (piid > 0L) {
                  ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
                  String appSupportGroupName = null;
                  ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
                  conn = ds.getConnection();
                  pstmt = conn.prepareStatement(this.sqlString);
                  pstmt.setString(1, teamName);

                  for(rs = pstmt.executeQuery(); rs.next(); appSupportGroupName = rs.getString("GROUPNAME")) {
                  }

                  if (pstmt != null) {
                     pstmt.close();
                  }

                  if (rs != null) {
                     rs.close();
                  }

                  if (conn != null) {
                     conn.close();
                  }

                  if (appSupportGroupName != null) {
                     String wiName = null;
                     long wiid = 0L;
                     ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
                     conn = ds.getConnection();
                     pstmt = conn.prepareStatement("SELECT WORKITEM_NAME, WORKITEM_ID FROM BIZLOGIC_WORKITEM WHERE PROCESS_INSTANCE_ID=? and WORKSTEP_NAME='AppSupport'");
                     pstmt.setLong(1, piid);

                     for(rs = pstmt.executeQuery(); rs.next(); wiid = rs.getLong("WORKITEM_ID")) {
                        wiName = rs.getString("WORKITEM_NAME");
                     }

                     if (pstmt != null) {
                        pstmt.close();
                     }

                     if (rs != null) {
                        rs.close();
                     }

                     if (conn != null) {
                        conn.close();
                     }

                     if (blserver.isWorkItemExist(blsession, wiid)) {
                        WorkItem wi = blserver.getWorkItem(blsession, wiid);
                        HashMap dsValues;
                        Vector v;
                        JDBCRealm r1;
                        JDBCGroup g;
                        String[] group_members;
                        int z;
                        if (wi.isAvailable()) {
                           dsValues = new HashMap();
                           dsValues.put("ApplicationType", appName);
                           dsValues.put("IssueRequestId", issueReqId);
                           dsValues.put("IssueReqSubTypeID", issueReqSubId);
                           dsValues.put("TeamName", teamName);
                           dsValues.put("AppSupportGroupName", appSupportGroupName);
                           pi.updateSlotValue(dsValues);
                           pi.save();
                           v = new Vector();
                           r1 = (JDBCRealm)UserManager.getDefaultRealm();
                           if (r1 != null) {
                              g = (JDBCGroup)r1.getGroup(appSupportGroupName);
                              if (g != null) {
                                 group_members = g.getMemberNames();
                                 if (group_members != null && group_members.length > 0) {
                                    for(z = 0; z < group_members.length; ++z) {
                                       v.add(group_members[z]);
                                    }
                                 }
                              }
                           }

                           wi.makeAvailable(v);
                           wi.save();
                           bln = true;
                        }

                        if (wi.isAssigned()) {
                           dsValues = new HashMap();
                           dsValues.put("ApplicationType", appName);
                           dsValues.put("IssueRequestId", issueReqId);
                           dsValues.put("IssueReqSubTypeID", issueReqSubId);
                           dsValues.put("TeamName", teamName);
                           dsValues.put("AppSupportGroupName", appSupportGroupName);
                           pi.updateSlotValue(dsValues);
                           pi.save();
                           v = new Vector();
                           r1 = (JDBCRealm)UserManager.getDefaultRealm();
                           if (r1 != null) {
                              g = (JDBCGroup)r1.getGroup(appSupportGroupName);
                              if (g != null) {
                                 group_members = g.getMemberNames();
                                 if (group_members != null && group_members.length > 0) {
                                    for(z = 0; z < group_members.length; ++z) {
                                       v.add(group_members[z]);
                                    }
                                 }
                              }
                           }

                           wi.makeAvailable(v);
                           wi.save();
                           bln = true;
                        }
                     }

                     blserver.disConnect(blsession);
                  }
               }
            }
         }

         return bln;
      } catch (Exception var32) {
         var32.printStackTrace();
         return false;
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
                        sb.append("<br/><br/>Click <b><a href=\"http://bpm.reliancegeneral.co.in/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
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

   public String STARTCALLDESK(String UserRemark, String ApproverEmailID, String ApproverDesignation, String ApproverName, String UserEmailID, String UserContactNo, String ApplicationType, String ApproverID, String ReopenId, String Branch, String CallLogDate, String UploadScreen, String CallCreatedBy, String CallType, String IssueRequestId, String TicketNo, String ChannelSM, String TeamName, String IssueReqSubTypeID, String TeamTAT, String ApproverUserID2, String AppSupportPerformer, String Approver2Dsgn, String Approver2Email, String Approver2Name, String TicketValue, String username, String password, String piName, String priority, String miscNo, String totalSumInsured, String insuredName, String occupancy, String imdCodeAndName, String agentCodeAndName, String isGreenChannel) throws AxisFault {
      NLog.ws.debug("STARTCALLDESK method invoked");
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
      dsTypeMap.put("Zone", "STRING");
      dsTypeMap.put("MiscNo", "STRING");
      dsTypeMap.put("TotalSumInsured", "STRING");
      dsTypeMap.put("InsuredName", "STRING");
      dsTypeMap.put("Occupancy", "STRING");
      dsTypeMap.put("ImdCodeAndName", "STRING");
      dsTypeMap.put("AgentCodeAndName", "STRING");
      dsTypeMap.put("IsGreenChannel", "STRING");
      dsTypeMap.put("UserChannel", "STRING");
      dsTypeMap.put("ProcessPriority", "STRING");
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
      String zone = this.getZone(Branch);
      dsValues.put("Zone", zone);
      dsValues.put("MiscNo", miscNo);
      dsValues.put("TotalSumInsured", totalSumInsured);
      dsValues.put("InsuredName", insuredName);
      dsValues.put("Occupancy", occupancy);
      dsValues.put("ImdCodeAndName", imdCodeAndName);
      dsValues.put("AgentCodeAndName", agentCodeAndName);
      dsValues.put("IsGreenChannel", isGreenChannel);
      //Below line is commented by SUnil and hard coded value placed for Testing only.
      dsValues.put("UserChannel", this.getUserChannel(CallCreatedBy));
      //dsValues.put("UserChannel","RETAIL AGENCY & BROKING");
      dsValues.put("ProcessPriority", priority);
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
      this.disconnect(sessionId);
      return pi;
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

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private boolean checkNull(Object dsValue) {
      return dsValue == null || dsValue instanceof String && dsValue.equals("<NULL>");
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

   private String getZone(String branchName) {
      String zone = "";
      Connection databaseConnection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;

      try {
         String sqlQuery = "SELECT ZM.Zone_Name FROM BranchMaster BM JOIN ZoneMaster ZM ON BM.Zone_Code=ZM.Zone_Code WHERE BM.Branch_Name=?";
         //databaseConnection = this.getConnectionToSQLDB();
         databaseConnection = getOracleDBConnection();
         preparedStatement = databaseConnection.prepareStatement(sqlQuery);
         preparedStatement.setString(1, branchName.trim());

         for(resultSet = preparedStatement.executeQuery(); resultSet.next(); zone = resultSet.getString(1)) {
         }
      } catch (Exception var14) {
         var14.getMessage();
         throw new RuntimeException(var14);
      } finally {
         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (preparedStatement != null) {
               preparedStatement.close();
            }

            if (databaseConnection != null) {
               databaseConnection.close();
            }
         } catch (Exception var13) {
            var13.getMessage();
            throw new RuntimeException(var13);
         }

      }

      return zone;
   }

   private String getUserChannel(String user) {
      user = user.split("\\(")[1].split("\\)")[0];
      NLog.ws.debug("TechDesk Channel fetching for user:" + user);
      String channel = null;
      Connection databaseConnection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;

      try {
         String sqlQuery = "SELECT RIGHT(EMPLOYEE_HR_FUNCTION,LEN(EMPLOYEE_HR_FUNCTION)-CHARINDEX('-',EMPLOYEE_HR_FUNCTION)) EMPLOYEE_HR_FUNCTION FROM EMPLOYEE_MASTER WHERE EMPLOYEE_CODE = ?";
         databaseConnection = this.getConnectionToSQLDB();
         preparedStatement = databaseConnection.prepareStatement(sqlQuery);
         preparedStatement.setString(1, user.trim());

         for(resultSet = preparedStatement.executeQuery(); resultSet.next(); channel = resultSet.getString(1)) {
         }
      } catch (Exception var15) {
         NLog.ws.debug("TechDesk exception while fetching channel:" + var15.getMessage());
      } finally {
         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (preparedStatement != null) {
               preparedStatement.close();
            }

            if (databaseConnection != null) {
               databaseConnection.close();
            }
         } catch (Exception var14) {
            NLog.ws.debug("TechDesk Exception in finally block of channel fetching method");
         }

      }

      NLog.ws.debug("TechDesk User Channel Fetched:" + channel);
      return channel;
   }

   private Connection getConnectionToSQLDB() throws Exception {
      Connection con = null;
      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         String dbip = "";
         String dbuser = "";
         String dbpass = "";
         if (ip.contains("10.62.182.42")) {
            dbip = "10.65.15.148";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         } else {
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
         
         return con;
      } catch (Exception var6) {
         throw new RuntimeException("Error in getting a DB connection", var6);
      }
   }
   
   private Connection getOracleDBConnection () throws Exception {
	      Connection con = null;
	      InitialContext jndiCntx = null;
	      try {
             jndiCntx = new InitialContext();
             DataSource ds = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
             con = ds.getConnection();
	         return con;
	      } catch (Exception var6) {
	         throw new RuntimeException("Error in getting Oracle DB connection", var6);
	      }
	   }
}
