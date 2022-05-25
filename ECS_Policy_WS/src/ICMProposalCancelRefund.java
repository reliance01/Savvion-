import com.icm.excessrefund.ProposalBean;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.BizLogic.Server.PAKException;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.apache.axis.AxisFault;
import techdesk.email.EmailSender;

public class ICMProposalCancelRefund {
   private static BizLogicManager pak = null;
   private String ptName = "ICMProposalCancelRefund";
   private static Byte[] bytearray = new Byte[0];
   private String username = "ICM";
   private String password = "ICM";
   private String piName = "ICMProposalCancelRefundCase";
   private String priority = "medium";
   private Connection con = null;
   private CallableStatement proc_stmt = null;
   private ResultSet rs = null;

   static {
	      try {
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	      } catch (ClassNotFoundException var3) {
	         try {
	            throw new ClassNotFoundException();
	         } catch (ClassNotFoundException var2) {
	            var2.printStackTrace();
	         }
	      }

   }

   private Connection getConnectionToSQLDB() throws AxisFault {
      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         String dbip = "";
         String dbuser = "";
         String dbpass = "";
         if (ip.contains("10.65.15.")) {
            dbip = "rgiudb01.reliancegeneral.co.in";
            dbuser = "sa";
            dbpass = "sa123";
         } else {
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         this.con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new AxisFault("SBM Web services error : getConnectionToSQLDB ", var5);
      }

      return this.con;
   }

   private void closeResources() {
      if (this.rs != null) {
         try {
            this.rs.close();
         } catch (SQLException var4) {
         }
      }

      if (this.proc_stmt != null) {
         try {
            this.proc_stmt.close();
         } catch (SQLException var3) {
         }
      }

      if (this.con != null) {
         try {
            this.con.close();
         } catch (SQLException var2) {
         }
      }

   }

   private String getZOMDetails(String branchCode, String productCode) throws AxisFault {
      String zom = "";

      try {
         if (branchCode == null || productCode == null) {
            throw new AxisFault("SBM Web services error : Null Brach Code or refund Amount");
         }

         this.con = this.getConnectionToSQLDB();
         this.proc_stmt = this.con.prepareCall("{ call usp_GetProposalCancelRefund_Performer(?,?) }");
         this.proc_stmt.setString(1, branchCode);
         this.proc_stmt.setString(2, productCode);
         this.rs = this.proc_stmt.executeQuery();
         if (this.rs != null) {
            while(this.rs.next()) {
               zom = this.rs.getString("EMPLOYEE_CODE");
            }
         }

         this.closeResources();
      } catch (Exception var8) {
         throw new AxisFault("SBM Web services error : getZOMDetails ", var8);
      } finally {
         this.closeResources();
      }

      return zom;
   }

   public String initiateProposalCancelRefund(ProposalBean pb) throws AxisFault {
      String appr = null;

      try {
         long piid = this.getPiIdFromProposalNumber(pb.getProposalID());
         if (piid > 124L) {
            throw new AxisFault("SBM Web services error : Proposal already sent for Excess Refund Approval");
         } else {
            if (pb != null) {
               String txId = pb.getTransactionId();
               if (txId != null && txId.length() > 0) {
                  String sessionId = this.connect(this.username, this.password);
                  HashMap<String, String> dsTypeMap = new HashMap();
                  dsTypeMap.put("RequestedBy", "STRING");
                  dsTypeMap.put("RequestDate", "STRING");
                  dsTypeMap.put("RefundType", "STRING");
                  dsTypeMap.put("RefundAmount", "STRING");
                  dsTypeMap.put("ProposalID", "STRING");
                  dsTypeMap.put("Product", "STRING");
                  dsTypeMap.put("PrintLocation", "STRING");
                  dsTypeMap.put("PayTo", "STRING");
                  dsTypeMap.put("InsuredName", "STRING");
                  dsTypeMap.put("InstrumentFrom", "STRING");
                  dsTypeMap.put("ClassName", "STRING");
                  dsTypeMap.put("Branch", "STRING");
                  dsTypeMap.put("Approver", "STRING");
                  dsTypeMap.put("InstrumentId", "STRING");
                  dsTypeMap.put("PolicyNo", "STRING");
                  dsTypeMap.put("TransactionId", "STRING");
                  dsTypeMap.put("PayeeName", "STRING");
                  dsTypeMap.put("ICMRemarks", "STRING");
                  HashMap<String, String> dsValues = new HashMap();
                  dsValues.put("RequestedBy", this.ConvertNullToEmptyString(pb.getRequestedBy()));
                  dsValues.put("RequestDate", this.ConvertNullToEmptyString(pb.getRequestDate()));
                  dsValues.put("RefundType", this.ConvertNullToEmptyString(pb.getRefundType()));
                  dsValues.put("RefundAmount", this.ConvertNullToEmptyString(pb.getRefundAmount()));
                  dsValues.put("ProposalID", this.ConvertNullToEmptyString(pb.getProposalID()));
                  dsValues.put("Product", this.ConvertNullToEmptyString(pb.getProduct()));
                  dsValues.put("PrintLocation", this.ConvertNullToEmptyString(pb.getPrintLocation()));
                  dsValues.put("PayTo", this.ConvertNullToEmptyString(pb.getPayTo()));
                  dsValues.put("InsuredName", this.ConvertNullToEmptyString(pb.getInsuredName()));
                  dsValues.put("InstrumentFrom", this.ConvertNullToEmptyString(pb.getInstrumentFrom()));
                  dsValues.put("ClassName", this.ConvertNullToEmptyString(pb.getClassName()));
                  dsValues.put("Branch", this.ConvertNullToEmptyString(pb.getBranch()));
                  dsValues.put("PayeeName", this.ConvertNullToEmptyString(pb.getPayeeName()));
                  dsValues.put("ICMRemarks", this.ConvertNullToEmptyString(pb.getRemarks()));
                  String branch = this.ConvertNullToEmptyString(pb.getBranch());
                  String refundAmt = pb.getRefundAmount();
                  if (branch != null && branch.length() > 0) {
                     String[] arrBranch = branch.split("-");
                     branch = arrBranch[0];
                  }

                  String product = pb.getProduct();
                  if (product != null && product.length() > 0) {
                     String[] arrProduct = product.split("-");
                     product = arrProduct[0];
                  }

                  String approver = this.getZOMDetails(branch, product);
                  if (approver != null && approver.length() > 0) {
                     String[] arrapprover = approver.split("-");
                     approver = arrapprover[0];
                  }

                  HashMap hm1 = new HashMap();
                  if (approver != null && approver.trim().length() > 0) {
                     hm1.put("USERID", approver);
                     hm1.put("EMAIL", approver + "@rcap.co.in");
                     hm1.put("FIRSTNAME", approver);
                  }

                  ArrayList ls = new ArrayList();
                  ls.add(hm1);
                  this.createUser(ls);
                  if (approver == null || approver == "") {
                     approver = "rgicl";
                  }

                  dsValues.put("Approver", this.ConvertNullToEmptyString(approver));
                  dsValues.put("InstrumentId", this.ConvertNullToEmptyString(pb.getInstrumentId()));
                  dsValues.put("PolicyNo", this.ConvertNullToEmptyString(pb.getPolicyNo()));
                  dsValues.put("TransactionId", this.ConvertNullToEmptyString(pb.getTransactionId()));
                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
                  String pi = this.createProcessInstance(sessionId, this.ptName, this.piName, this.priority, resolvedDSValues);
                  if (pi != null) {
                     appr = txId + "-" + approver;
                  }

                  this.disconnect(sessionId);
               }
            }

            return appr;
         }
      } catch (Exception var17) {
         NLog.ws.error("ICM_DISCREPANCY_Update method error " + var17.getMessage());
         var17.printStackTrace();
         throw new AxisFault("SBM Web services error : initiateProposalCancelRefund ", var17);
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

   public String updateTxProposalData(ProposalBean pb) throws AxisFault {
      NLog.ws.info("updateProposal method invoked");
      long piId = this.getPiIdFromProposalNumber(pb.getTransactionId());
      String retVal = "";

      try {
         if (piId != 0L) {
            HashMap<String, String> dsValues = new HashMap();
            dsValues.put("RequestedBy", this.ConvertNullToEmptyString(pb.getRequestedBy()));
            dsValues.put("RequestDate", this.ConvertNullToEmptyString(pb.getRequestDate()));
            dsValues.put("RefundType", this.ConvertNullToEmptyString(pb.getRefundType()));
            dsValues.put("RefundAmount", this.ConvertNullToEmptyString(pb.getRefundAmount()));
            dsValues.put("ProposalID", this.ConvertNullToEmptyString(pb.getProposalID()));
            dsValues.put("Product", this.ConvertNullToEmptyString(pb.getProduct()));
            dsValues.put("PrintLocation", this.ConvertNullToEmptyString(pb.getPrintLocation()));
            dsValues.put("PayTo", this.ConvertNullToEmptyString(pb.getPayTo()));
            dsValues.put("InsuredName", this.ConvertNullToEmptyString(pb.getInsuredName()));
            dsValues.put("InstrumentFrom", this.ConvertNullToEmptyString(pb.getInstrumentFrom()));
            dsValues.put("ClassName", this.ConvertNullToEmptyString(pb.getClassName()));
            dsValues.put("Branch", this.ConvertNullToEmptyString(pb.getBranch()));
            String branch = pb.getBranch();
            String refundAmt = pb.getRefundAmount();
            if (branch != null && branch.length() > 0) {
               String[] arrBranch = branch.split("-");
               branch = arrBranch[0];
            }

            String approver = this.getZOMDetails(branch, refundAmt);
            if (approver != null && approver.length() > 0) {
               String[] arrapprover = approver.split("-");
               approver = arrapprover[0];
            }

            if (approver == null || approver == "") {
               approver = "rgicl";
            }

            dsValues.put("Approver", this.ConvertNullToEmptyString(approver));
            dsValues.put("InstrumentId", this.ConvertNullToEmptyString(pb.getInstrumentId()));
            dsValues.put("PolicyNo", this.ConvertNullToEmptyString(pb.getPolicyNo()));
            dsValues.put("TransactionId", this.ConvertNullToEmptyString(pb.getTransactionId()));
            BLServer blServer = BLClientUtil.getBizLogicServer();
            Session blSession = blServer.connect(this.username, this.password);
            if (blServer.isProcessInstanceExist(blSession, piId)) {
               ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
               pi.updateSlotValue(dsValues);
               pi.save();
               blServer.disConnect(blSession);
               retVal = pb.getTransactionId() + "~" + "Success";
               return retVal;
            }
         }

         return retVal;
      } catch (Exception var12) {
         NLog.ws.error("ICM_DISCREPANCY_Update method error " + var12.getMessage());
         throw new AxisFault("SBM Web services error :" + var12.getMessage());
      }
   }

   private long getPiIdFromProposalNumber(String proposalId) throws AxisFault {
      long piId = 0L;

      try {
         if (proposalId != null) {
            BLServer blServer = BLClientUtil.getBizLogicServer();
            Session blSession = blServer.connect(this.username, this.password);
            ProcessTemplate pt = blServer.getProcessTemplate(blSession, this.ptName);
            ProcessInstanceList piList = blServer.getProcessInstanceList(blSession, pt.getID());
            List ls = piList.getList();
            if (ls != null && !ls.isEmpty()) {
               for(int i = 0; i < ls.size(); ++i) {
                  ProcessInstance pi = (ProcessInstance)ls.get(i);
                  if (blServer.isProcessInstanceExist(blSession, pi.getID())) {
                     String dsProposalNum = (String)pi.getDataSlotValue("TransactionId");
                     if (dsProposalNum != null && dsProposalNum.equals(proposalId)) {
                        piId = pi.getID();
                        return piId;
                     }
                  }
               }
            }

            blServer.disConnect(blSession);
         }

         return piId;
      } catch (Exception var12) {
         NLog.ws.error("ICM_DISCREPANCY_START method error " + var12.getMessage());
         throw new AxisFault("SBM Web services error : getPiIdFromProposalNumber " + var12.getMessage());
      }
   }

   private String ConvertNullToEmptyString(String str) {
      try {
         if (str != null) {
            return str;
         }

         str = "";
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return str;
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

   public String connect(String userId, String password) throws AxisFault {
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

   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
      try {
         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   public boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   public String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   public String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, true);
   }

   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
      Object obj = null;

      try {
         obj = getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
      } catch (RemoteException var6) {
         throw new AxisFault("SBM Web services error :" + var6.getMessage());
      } catch (PAKException var7) {
         throw new AxisFault("SBM Web services error :" + var7.getMessage());
      }

      return obj instanceof String ? (String)obj : "NST";
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

   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
      QueryService qs = null;
      QSWorkItemRS wirs = null;

      try {
         Session sess = getBizLogicManager().getSession(sessionId);
         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
         qs = new QueryService(sess);
         QSWorkItem qswi = qs.getWorkItem();
         wifil.setFetchSize(400);
         if (available) {
            wirs = qswi.getAvailableList(wifil);
         } else {
            wirs = qswi.getAssignedList(wifil);
         }

         List wi = wirs.getSVOList();
         String[] winames = new String[wi.size()];

         for(int i = 0; i < wi.size(); ++i) {
            winames[i] = ((QSWorkItemData)wi.get(i)).getName();
         }

         String[] var12 = winames;
         return var12;
      } catch (Exception var19) {
         throw new AxisFault("SBM Web services error :" + var19.getMessage());
      } finally {
         try {
            wirs.close();
         } catch (Exception var18) {
         }

      }
   }
}
