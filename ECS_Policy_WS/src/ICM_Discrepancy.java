import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import techdesk.email.EmailSender;

public class ICM_Discrepancy {
   private static BizLogicManager pak = null;
   private String ptName = "ICM_Discrepancy";
   private static Byte[] bytearray = new Byte[0];
   private String username = "ICM";
   private String password = "ICM";
   private String piName = "ICM_Discrepancy_Case";
   private String priority = "Medium";
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
   private DataSource ds = null;
   private Connection con = null;
   private CallableStatement proc_stmt = null;

   private Connection getConnectionToSQLDB() throws AxisFault {
      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         String dbip = "";
         String dbuser = "";
         String dbpass = "";
         if (ip.contains("10.65.15.")) {
            dbip = "rgiudb01.reliancegeneral.co.in";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         } else {
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         this.con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
         NLog.ws.info("ICM_Discrepancy::Got db connection:" + dbip);
      } catch (Exception var5) {
         throw new AxisFault("Error in getting a DB connection" + var5.getMessage());
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

   public ICM_Discrepancy() throws AxisFault {
      try {
         NLog.ws.info("ICM_Discrepancy service invoked");
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      } catch (Exception var2) {
         throw new AxisFault("ICM_discrepancy::Error in getting a DB connection" + var2.getMessage());
      }
   }

   private String getRhDetails(String branchCode, String ProductType, String ClsName, String ProductCode, String SMCode) throws AxisFault {
      NLog.ws.info("ICM_Discrepancy::getRhDetails(" + branchCode + "," + ProductType + "," + ClsName + "," + ProductCode + "," + SMCode + ")");
      String rhName = "";
      Connection con = null;
      CallableStatement proc_stmt = null;
      ResultSet rs = null;

      try {
         if (branchCode != null && ProductType != null) {
            NLog.ws.info("Getting RhDetails ");
            con = this.getConnectionToSQLDB();
            proc_stmt = con.prepareCall("{ call usp_GetRHDetailsNew(?,?,?,?,?) }");
            proc_stmt.setString(1, branchCode);
            proc_stmt.setString(2, ProductType);
            proc_stmt.setString(3, ClsName);
            proc_stmt.setString(4, ProductCode);
            proc_stmt.setString(5, SMCode);
            rs = proc_stmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  rhName = rs.getString("EMPLOYEE_CODE");
               }
            }

            NLog.ws.info("ICM_Discrepancy::Getting RhDetails " + rhName);
            if (rhName == null || rhName == "") {
               rhName = "rgicl";
            }

            if (proc_stmt != null) {
               proc_stmt.close();
            }

            if (rs != null) {
               rs.close();
            }

            if (con != null) {
               con.close();
            }
         }
      } catch (Exception var27) {
         NLog.ws.error("ICM_discrepancy::Error in getRhDetails " + var27.getMessage());
         throw new AxisFault("SBM Web services error :" + var27.getMessage());
      } finally {
         try {
            if (proc_stmt != null) {
               try {
                  proc_stmt.close();
               } catch (Exception var25) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var24) {
               }
            }

            if (con != null) {
               try {
                  con.close();
               } catch (Exception var23) {
               }
            }
         } catch (Exception var26) {
         }

      }

      return rhName;
   }

   public String START_ICM_DISCREPANCY_CASE(String AgentCode, String AgentName, String BASCode, String BASName, String BranchCode, String BranchName, String SMCode, String SMName, String ProductCode, String ProductName, String ProposerName, String PreviousPolicyNumber, String Proposal_CN_Number, String BusinessType, String DiscrepancyCategory, String DiscrepancySubCategory, String DiscrepancySubCode, String DocumentType, String InspectionDate, String InspectionID, String InspectionStatus, String ProposalInwardDate, String ProposalIssueDate, String RiskEndDate, String RiskStartDate, String status, String proposalAmount, String ProductType, String ClsName, String DiscrepancyRemarks) throws AxisFault {
      NLog.ws.info("ICM_Discrepancy::START_ICM_DISCREPANCY_CASE(" + AgentCode + "," + BranchCode + "," + SMCode + "," + ProductCode + "," + ProductCode + "," + Proposal_CN_Number + "," + status + ")");
      String rhName = "";
      String firstName = null;
      String email = null;
      String lastName = null;
      Connection con = null;
      CallableStatement proc_stmt = null;
      ResultSet rs = null;

      String var53;
      try {
         boolean proposalNumberExists = this.isProposalNumberExists(Proposal_CN_Number);
         if (status == null || !status.equalsIgnoreCase("I") || proposalNumberExists) {
            if (status != null && status.equalsIgnoreCase("U") && proposalNumberExists) {
               rhName = this.updateProposal(AgentCode, AgentName, BASCode, BASName, BranchCode, BranchName, SMCode, SMName, ProductCode, ProductName, ProposerName, PreviousPolicyNumber, Proposal_CN_Number, BusinessType, DiscrepancyCategory, DiscrepancySubCategory, DiscrepancySubCode, DocumentType, InspectionDate, InspectionID, InspectionStatus, ProposalInwardDate, ProposalIssueDate, RiskEndDate, RiskStartDate, proposalAmount, ProductType, ClsName, DiscrepancyRemarks);
               var53 = rhName;
               return var53;
            }

            if (status != null && status.equalsIgnoreCase("D") && proposalNumberExists) {
               rhName = this.removeProposal(Proposal_CN_Number);
               var53 = rhName;
               return var53;
            }

            if (status == null) {
               return "Either Status is null OR not valid, valid values are (I,U,D)";
            }

            if (!proposalNumberExists && (status.equalsIgnoreCase("D") || status.equalsIgnoreCase("U"))) {
               return "Proposal is null or Do not exists";
            }

            if (status.equalsIgnoreCase("I") && proposalNumberExists) {
               return "Proposal number already exists";
            }

            NLog.ws.info("ICM_DISCREPANCY_START method returning null because no condition matches");
            return null;
         }

         String IsClaimOnPreviousPolicy = "false";
         String IsClaimOnProposal = "false";
         String sessionId = this.connect(this.username, this.password);
         HashMap<String, String> dsTypeMap = new HashMap();
         dsTypeMap.put("DiscrepancyRemarks", "STRING");
         dsTypeMap.put("AgentCode", "STRING");
         dsTypeMap.put("AgentName", "STRING");
         dsTypeMap.put("BASCode", "STRING");
         dsTypeMap.put("BASName", "STRING");
         dsTypeMap.put("BranchCode", "STRING");
         dsTypeMap.put("BranchName", "STRING");
         dsTypeMap.put("BusinessType", "STRING");
         dsTypeMap.put("DiscrepancyCategory", "STRING");
         dsTypeMap.put("DiscrepancySubCategory", "STRING");
         dsTypeMap.put("DiscrepancySubCode", "STRING");
         dsTypeMap.put("DocumentType", "STRING");
         dsTypeMap.put("InspectionDate", "STRING");
         dsTypeMap.put("InspectionID", "STRING");
         dsTypeMap.put("InspectionStatus", "STRING");
         dsTypeMap.put("IsClaimOnPreviousPolicy", "STRING");
         dsTypeMap.put("IsClaimOnProposal", "STRING");
         dsTypeMap.put("PreviousPolicyNumber", "STRING");
         dsTypeMap.put("ProductCode", "STRING");
         dsTypeMap.put("ProductName", "STRING");
         dsTypeMap.put("Proposal_CN_Number", "STRING");
         dsTypeMap.put("ProposalInwardDate", "STRING");
         dsTypeMap.put("ProposalIssueDate", "STRING");
         dsTypeMap.put("ProposerName", "STRING");
         dsTypeMap.put("RiskEndDate", "STRING");
         dsTypeMap.put("RiskStartDate", "STRING");
         dsTypeMap.put("SMCode", "STRING");
         dsTypeMap.put("SMName", "STRING");
         dsTypeMap.put("Approver", "STRING");
         dsTypeMap.put("proposalAmount", "STRING");
         dsTypeMap.put("ProductType", "STRING");
         dsTypeMap.put("ClsName", "STRING");
         HashMap<String, String> dsValues = new HashMap();
         dsValues.put("DiscrepancyRemarks", DiscrepancyRemarks);
         System.out.println(DiscrepancyRemarks);
         dsValues.put("AgentCode", AgentCode);
         dsValues.put("AgentName", AgentName);
         dsValues.put("BASCode", BASCode);
         dsValues.put("BASName", BASName);
         dsValues.put("BranchCode", BranchCode);
         dsValues.put("BranchName", BranchName);
         dsValues.put("BusinessType", BusinessType);
         dsValues.put("DiscrepancyCategory", DiscrepancyCategory);
         dsValues.put("DiscrepancySubCategory", DiscrepancySubCategory);
         dsValues.put("DiscrepancySubCode", DiscrepancySubCode);
         dsValues.put("DocumentType", DocumentType);
         dsValues.put("InspectionDate", InspectionDate);
         dsValues.put("InspectionID", InspectionID);
         dsValues.put("InspectionStatus", InspectionStatus);
         dsValues.put("IsClaimOnPreviousPolicy", IsClaimOnPreviousPolicy);
         dsValues.put("IsClaimOnProposal", IsClaimOnProposal);
         dsValues.put("PreviousPolicyNumber", PreviousPolicyNumber);
         dsValues.put("ProductCode", ProductCode);
         dsValues.put("ProductName", ProductName);
         dsValues.put("Proposal_CN_Number", Proposal_CN_Number);
         dsValues.put("ProposalInwardDate", ProposalInwardDate);
         dsValues.put("ProposalIssueDate", ProposalIssueDate);
         dsValues.put("ProposerName", ProposerName);
         dsValues.put("RiskEndDate", RiskEndDate);
         dsValues.put("RiskStartDate", RiskStartDate);
         dsValues.put("SMCode", SMCode);
         dsValues.put("proposalAmount", proposalAmount);
         dsValues.put("ProductType", ProductType);
         dsValues.put("ClsName", ClsName);
         rhName = this.getRhDetails(BranchCode, ProductType, ClsName, ProductCode, SMCode);
         if (rhName != null && rhName.trim().length() > 0) {
            try {
               con = this.getConnectionToSQLDB();
               proc_stmt = con.prepareCall("{ call usp_GetEmployeeDetails(?) }");
               proc_stmt.setString(1, rhName);
               rs = proc_stmt.executeQuery();
               if (rs != null) {
                  while(rs.next()) {
                     firstName = rs.getString("First_Name");
                     lastName = rs.getString("Last_Name");
                     email = rs.getString("Email");
                  }
               }

               if (proc_stmt != null) {
                  proc_stmt.close();
               }

               if (rs != null) {
                  rs.close();
               }

               if (con != null) {
                  con.close();
               }
            } catch (Exception var94) {
               throw new RuntimeException("Error getting User Details from SQL DB" + var94.getMessage());
            }

            new UserManager();
            JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
            JDBCUser u = (JDBCUser)r.getUser(rhName);
            if (u == null) {
               Hashtable attrs = new Hashtable();
               attrs.put("userid", rhName);
               attrs.put("password", rhName);
               attrs.put("firstname", firstName);
               attrs.put("lastname", lastName);
               attrs.put("email", email);
               boolean bln = r.addUser(rhName, attrs);
               if (bln) {
                  StringBuffer sb = new StringBuffer();
                  sb.append("<html><style type=\"text/css\" ><!--.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}--><!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
                  sb.append("Dear " + firstName + ",");
                  sb.append("<br/><br/>Your login id has been created in Savvion System with below details,");
                  sb.append("<table class=\"tablecls\">");
                  sb.append("<tr><td class=\"tabletitle\">User Id : </td><td class=\"tablecell\">" + rhName + "</td></tr>");
                  sb.append("<tr><td class=\"tabletitle\">Password: </td><td class=\"tablecell\">" + rhName + "</td></tr>");
                  sb.append("<br/><br/>Click <b><a href=\"http://bpm.reliancegeneral.co.in:18793/sbm/bpmportal/login.jsp\" target=\"_blank\">here</a> </b>on this link to login into the system");
                  sb.append("<br/><br/>Thank you,");
                  sb.append("<br/>Reliance Application Support Team");
                  sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                  sb.append("<br/></body></html>");
                  HashMap hm1 = new HashMap();
                  hm1.put("FROM", "RGICLApplicationSupport@rcap.co.in");
                  hm1.put("TO", email);
                  hm1.put("SUBJECT", "Savvion Login ID Generated");
                  hm1.put("BODY", sb.toString());
                  EmailSender ems = new EmailSender();
                  ems.send(hm1);
               }
            }
         }

         NLog.ws.info("START_ICM_DISCREPANCY:: method RH Details are " + rhName);
         dsValues.put("Approver", rhName);
         Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
         this.createProcessInstance(sessionId, this.ptName, this.piName, this.priority, resolvedDSValues);
         this.disconnect(sessionId);
         rhName = Proposal_CN_Number + "~" + rhName;
         var53 = rhName;
      } catch (Exception var95) {
         NLog.ws.error("ICM_DISCREPANCY_START method error " + var95.getMessage());
         throw new AxisFault("SBM Web services error :" + var95.getMessage());
      } finally {
         try {
            if (proc_stmt != null) {
               try {
                  proc_stmt.close();
               } catch (Exception var92) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var91) {
               }
            }

            if (con != null) {
               try {
                  con.close();
               } catch (Exception var90) {
               }
            }
         } catch (Exception var93) {
         }

      }

      return var53;
   }

   private String removeProposal(String proposalId) throws AxisFault {
      NLog.ws.info("ICM_Discrepancy::removeProposal(" + proposalId + ")");
      String retVal = null;

      try {
         if (proposalId != null) {
            BLServer blServer = BLClientUtil.getBizLogicServer();
            Session blSession = blServer.connect(this.username, this.password);
            long piId = this.getPiIdFromProposalNumber(proposalId);
            if (blServer.isProcessInstanceExist(blSession, piId)) {
               ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
               NLog.ws.info("ICM_DISCREPANCY::removeProposal(" + proposalId + ")");
               pi.remove();
               retVal = proposalId + "~" + "Success";
            }

            blServer.disConnect(blSession);
         }

         return retVal;
      } catch (Exception var8) {
         NLog.ws.error("ICM_DISCREPANCY::removeproposal method error " + var8.getMessage());
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private String updateProposal(String AgentCode, String AgentName, String BASCode, String BASName, String BranchCode, String BranchName, String SMCode, String SMName, String ProductCode, String ProductName, String ProposerName, String PreviousPolicyNumber, String Proposal_CN_Number, String BusinessType, String DiscrepancyCategory, String DiscrepancySubCategory, String DiscrepancySubCode, String DocumentType, String InspectionDate, String InspectionID, String InspectionStatus, String ProposalInwardDate, String ProposalIssueDate, String RiskEndDate, String RiskStartDate, String proposalAmount, String ProductType, String ClsName, String DiscrepancyRemarks) throws AxisFault {
      String IsClaimOnPreviousPolicy = "false";
      String IsClaimOnProposal = "false";
      NLog.ws.info("ICM_Discrepancy::updateProposal method invoked for proposal" + Proposal_CN_Number);
      long piId = this.getPiIdFromProposalNumber(Proposal_CN_Number);
      String retVal = "";

      try {
         if (Proposal_CN_Number != null && piId != 0L) {
            HashMap<String, String> dsValues = new HashMap();
            dsValues.put("DiscrepancyRemarks", DiscrepancyRemarks);
            dsValues.put("AgentCode", AgentCode);
            dsValues.put("AgentName", AgentName);
            dsValues.put("BASCode", BASCode);
            dsValues.put("BASName", BASName);
            dsValues.put("BranchCode", BranchCode);
            dsValues.put("BranchName", BranchName);
            dsValues.put("BusinessType", BusinessType);
            dsValues.put("DiscrepancyCategory", DiscrepancyCategory);
            dsValues.put("DiscrepancySubCategory", DiscrepancySubCategory);
            dsValues.put("DiscrepancySubCode", DiscrepancySubCode);
            dsValues.put("DocumentType", DocumentType);
            dsValues.put("InspectionDate", InspectionDate);
            dsValues.put("InspectionID", InspectionID);
            dsValues.put("InspectionStatus", InspectionStatus);
            dsValues.put("PreviousPolicyNumber", PreviousPolicyNumber);
            dsValues.put("ProductCode", ProductCode);
            dsValues.put("ProductName", ProductName);
            dsValues.put("Proposal_CN_Number", Proposal_CN_Number);
            dsValues.put("ProposalInwardDate", ProposalInwardDate);
            dsValues.put("ProposalIssueDate", ProposalIssueDate);
            dsValues.put("ProposerName", ProposerName);
            dsValues.put("RiskEndDate", RiskEndDate);
            dsValues.put("RiskStartDate", RiskStartDate);
            dsValues.put("SMCode", SMCode);
            dsValues.put("SMName", SMName);
            dsValues.put("IsClaimOnPreviousPolicy", IsClaimOnPreviousPolicy);
            dsValues.put("IsClaimOnProposal", IsClaimOnProposal);
            dsValues.put("proposalAmount", proposalAmount);
            dsValues.put("ProductType", ProductType);
            dsValues.put("ClsName", ClsName);
            BLServer blServer = BLClientUtil.getBizLogicServer();
            Session blSession = blServer.connect(this.username, this.password);
            if (blServer.isProcessInstanceExist(blSession, piId)) {
               ProcessInstance pi = blServer.getProcessInstance(blSession, piId);
               pi.updateSlotValue(dsValues);
               pi.save();
               blServer.disConnect(blSession);
               retVal = Proposal_CN_Number + "~" + "Success";
               return retVal;
            } else {
               return retVal;
            }
         } else {
            String sessionId = this.connect(this.username, this.password);
            HashMap<String, String> dsTypeMap = new HashMap();
            dsTypeMap.put("AgentCode", "STRING");
            dsTypeMap.put("AgentName", "STRING");
            dsTypeMap.put("BASCode", "STRING");
            dsTypeMap.put("BASName", "STRING");
            dsTypeMap.put("BranchCode", "STRING");
            dsTypeMap.put("BranchName", "STRING");
            dsTypeMap.put("BusinessType", "STRING");
            dsTypeMap.put("DiscrepancyCategory", "STRING");
            dsTypeMap.put("DiscrepancySubCategory", "STRING");
            dsTypeMap.put("DiscrepancySubCode", "STRING");
            dsTypeMap.put("DocumentType", "STRING");
            dsTypeMap.put("InspectionDate", "STRING");
            dsTypeMap.put("InspectionID", "STRING");
            dsTypeMap.put("InspectionStatus", "STRING");
            dsTypeMap.put("IsClaimOnPreviousPolicy", "STRING");
            dsTypeMap.put("IsClaimOnProposal", "STRING");
            dsTypeMap.put("PreviousPolicyNumber", "STRING");
            dsTypeMap.put("ProductCode", "STRING");
            dsTypeMap.put("ProductName", "STRING");
            dsTypeMap.put("Proposal_CN_Number", "STRING");
            dsTypeMap.put("ProposalInwardDate", "STRING");
            dsTypeMap.put("ProposalIssueDate", "STRING");
            dsTypeMap.put("ProposerName", "STRING");
            dsTypeMap.put("RiskEndDate", "STRING");
            dsTypeMap.put("RiskStartDate", "STRING");
            dsTypeMap.put("SMCode", "STRING");
            dsTypeMap.put("SMName", "STRING");
            dsTypeMap.put("Approver", "STRING");
            dsTypeMap.put("proposalAmount", "STRING");
            dsTypeMap.put("ProductType", "STRING");
            dsTypeMap.put("ClsName", "STRING");
            dsTypeMap.put("DiscrepancyRemarks", "STRING");
            HashMap<String, String> dsValues1 = new HashMap();
            dsValues1.put("DiscrepancyRemarks", DiscrepancyRemarks);
            dsValues1.put("AgentCode", AgentCode);
            dsValues1.put("AgentName", AgentName);
            dsValues1.put("BASCode", BASCode);
            dsValues1.put("BASName", BASName);
            dsValues1.put("BranchCode", BranchCode);
            dsValues1.put("BranchName", BranchName);
            dsValues1.put("BusinessType", BusinessType);
            dsValues1.put("DiscrepancyCategory", DiscrepancyCategory);
            dsValues1.put("DiscrepancySubCategory", DiscrepancySubCategory);
            dsValues1.put("DiscrepancySubCode", DiscrepancySubCode);
            dsValues1.put("DocumentType", DocumentType);
            dsValues1.put("InspectionDate", InspectionDate);
            dsValues1.put("InspectionID", InspectionID);
            dsValues1.put("InspectionStatus", InspectionStatus);
            dsValues1.put("IsClaimOnPreviousPolicy", IsClaimOnPreviousPolicy);
            dsValues1.put("IsClaimOnProposal", IsClaimOnProposal);
            dsValues1.put("PreviousPolicyNumber", PreviousPolicyNumber);
            dsValues1.put("ProductCode", ProductCode);
            dsValues1.put("ProductName", ProductName);
            dsValues1.put("Proposal_CN_Number", Proposal_CN_Number);
            dsValues1.put("ProposalInwardDate", ProposalInwardDate);
            dsValues1.put("ProposalIssueDate", ProposalIssueDate);
            dsValues1.put("ProposerName", ProposerName);
            dsValues1.put("RiskEndDate", RiskEndDate);
            dsValues1.put("RiskStartDate", RiskStartDate);
            dsValues1.put("SMCode", SMCode);
            dsValues1.put("proposalAmount", proposalAmount);
            dsValues1.put("ProductType", ProductType);
            dsValues1.put("ClsName", ClsName);
            String rhName = this.getRhDetails(BranchCode, ProductType, ClsName, ProductCode, SMCode);
            dsValues1.put("Approver", rhName);
            Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues1);
            this.createProcessInstance(sessionId, this.ptName, this.piName, this.priority, resolvedDSValues);
            this.disconnect(sessionId);
            retVal = Proposal_CN_Number + "~" + rhName;
            return retVal;
         }
      } catch (Exception var40) {
         NLog.ws.error("ICM_DISCREPANCY::Update method error " + var40.getMessage());
         throw new AxisFault("SBM Web services error :" + var40.getMessage());
      }
   }

   private long getPiIdFromProposalNumber(String proposalId) throws AxisFault {
      NLog.ws.info("ICM_DISCREPANCY::getPiIdFromProposalNumber(" + proposalId + ")");
      long piId = 0L;

      try {
         if (proposalId != null) {
            String query = "";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
               if (proposalId != null) {
                  BLServer blServer = BLClientUtil.getBizLogicServer();
                  Session blSession = blServer.connect(this.username, this.password);
                  ProcessTemplate pt = blServer.getProcessTemplate(blSession, this.ptName);
                  String strPtId = (new Long(pt.getID())).toString();
                  query = "select process_instance_id from bizlogic_ds_" + strPtId + " where Proposal_CN_Number = ? order by process_instance_id desc fetch first row only";
                  this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
                  conn = this.ds.getConnection();
                  pstmt = conn.prepareStatement(query);
                  pstmt.setString(1, proposalId);

                  for(rs = pstmt.executeQuery(); rs.next(); piId = rs.getLong(1)) {
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
               }
            } catch (Exception var29) {
               NLog.ws.error("ICM_DISCREPANCY::getPiIdFromProposalNumber method error " + var29.getMessage());
               throw new AxisFault("SBM Web services error :" + var29.getMessage());
            } finally {
               try {
                  if (pstmt != null) {
                     try {
                        pstmt.close();
                     } catch (Exception var27) {
                     }
                  }

                  if (rs != null) {
                     try {
                        rs.close();
                     } catch (Exception var26) {
                     }
                  }

                  if (conn != null) {
                     try {
                        conn.close();
                     } catch (Exception var25) {
                     }
                  }
               } catch (Exception var28) {
               }

            }
         }

         return piId;
      } catch (Exception var31) {
         NLog.ws.error("ICM_DISCREPANCY::getPiIdFromProposalNumber method error " + var31.getMessage());
         throw new AxisFault("SBM Web services error :" + var31.getMessage());
      }
   }

   private boolean isProposalNumberExists(String proposalId) throws AxisFault {
      NLog.ws.info("ICM_DISCREPANCY::isProposalNumberExists(" + proposalId + ")");
      boolean isExists = false;
      int cnt = 0;
      String query = "";
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (proposalId != null) {
            BLServer blServer = BLClientUtil.getBizLogicServer();
            Session blSession = blServer.connect(this.username, this.password);
            ProcessTemplate pt = blServer.getProcessTemplate(blSession, this.ptName);
            String strPtId = (new Long(pt.getID())).toString();
            query = "select count(1) from bizlogic_ds_" + strPtId + " where Proposal_CN_Number = ?";
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            conn = this.ds.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, proposalId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
               cnt = rs.getInt(1);
               if (cnt > 0) {
                  isExists = true;
                  break;
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
         }
      } catch (Exception var28) {
         NLog.ws.error("ICM_DISCREPANCY::isProposalNumberExists method error " + var28.getMessage());
         throw new AxisFault("SBM Web services error :" + var28.getMessage());
      } finally {
         try {
            if (pstmt != null) {
               try {
                  pstmt.close();
               } catch (Exception var26) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var25) {
               }
            }

            if (conn != null) {
               try {
                  conn.close();
               } catch (Exception var24) {
               }
            }
         } catch (Exception var27) {
         }

      }

      return isExists;
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
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
         NLog.ws.error("Error in creating process instance " + var8.getMessage());
         throw new AxisFault("SBM Web services error in creating process instance:" + var8.getMessage());
      }
   }

   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
      try {
         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
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
}
