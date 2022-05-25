package com.agent.ejb.initiateagent;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AgentFlowProcess {
   private final String blUser = "parshu";
   private final String blPassword = "Ebms123#";
   private ResultSet rs = null;
   private Connection con = null;
   private CallableStatement proc_stmt = null;
   private static InitialContext initialContext = null;
   private static DataSource dataSource = null;
   private static String dataSourceName = "jdbc/sqlDB";
   BLServer blServer = null;
   Session blSession = null;
   PreparedStatement pstmt = null;
   GetResource rsc = null;

   public Connection getDBConnection() {
      try {
         initialContext = new InitialContext();
         dataSource = (DataSource)initialContext.lookup(dataSourceName);
         this.con = dataSource.getConnection();
         return this.con;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void releaseResource(Connection conn, CallableStatement cstmt, ResultSet rset) {
      try {
         if (rset != null) {
            rset.close();
         }

         if (cstmt != null) {
            cstmt.close();
         }

         if (conn != null) {
            conn.close();
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private Connection getConnectionToSQLDB() {
      try {
         System.out.println("Inside Try");
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         System.out.println("before IP");
         String ip = InetAddress.getLocalHost().getHostAddress();
         System.out.println(" ip is : " + ip);
         String dbip = "";
         String dbuser = "";
         String dbpass = "";
         if (ip.contains("10.65.15.")) {
            System.out.println("Inside if !!");
            dbip = "rgiudb01.reliancegeneral.co.in";
            dbuser = "sa";
            dbpass = "sa123";
         } else {
            System.out.println("Inside else !!");
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         this.con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
      } catch (Exception var5) {
         System.out.println("Error in getting connection : " + var5.getMessage() + " -- StackTrace :" + var5.getStackTrace());
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

   public String getBmDetail(String branchCode) {
      System.out.println("In getBmMethod!!!!");
      String bmId = "";

      try {
         if (branchCode != null && branchCode != "") {
            this.con = this.getConnectionToSQLDB();
            this.proc_stmt = this.con.prepareCall("{ call usp_GetBMDetails_AgentRTN(?,?,?) }");
            this.proc_stmt.setString(1, branchCode);
            this.proc_stmt.setString(2, (String)null);
            this.proc_stmt.setString(3, (String)null);
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  bmId = this.rs.getString("EMPLOYEE");
               }
            }

            if (bmId != null || bmId != "") {
               bmId = bmId.split("-")[0].trim();
            }
         }

         this.closeResources();
         return bmId;
      } catch (Exception var4) {
         System.out.println(var4.getMessage());
         throw new RuntimeException("Error in getBm Method : " + var4.getMessage());
      }
   }

   public String getPerformerDetail(String role, String branchCode, String smCode) {
      System.out.println("Inside getPerformerDetail() ");
      String performerID = "";
      String fetchedId = "";

      try {
         if (branchCode != null && branchCode != "") {
            this.con = this.getDBConnection();
            this.proc_stmt = this.con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
            this.proc_stmt.setString(1, role);
            this.proc_stmt.setString(2, branchCode);
            this.proc_stmt.setString(3, smCode);
            this.proc_stmt.setString(4, "0");
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  fetchedId = this.rs.getString("FApproverID");
               }
            }

            if (fetchedId != null && fetchedId != "") {
               performerID = fetchedId.trim();
               CreateSavvionUser obj = new CreateSavvionUser();
               obj.checkUserExists(performerID);
            } else {
               performerID = this.getMandatoryInfo().split(",")[1].trim();
            }
         }

         this.releaseResource(this.con, this.proc_stmt, this.rs);
         return performerID;
      } catch (Exception var7) {
         System.out.println(var7.getMessage());
         throw new RuntimeException("Error in getBm Method : " + var7.getMessage());
      }
   }

   public HashMap<String, String> getPerformerEscalationDetail(String performerId) {
      System.out.println("Inside getPerformerEscalationMatrix method !!!");
      HashMap performerEscalationMatrix = new HashMap();

      try {
         if (performerId != null && performerId != "") {
            this.con = this.getConnectionToSQLDB();
            this.proc_stmt = this.con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
            this.proc_stmt.setString(1, "");
            this.proc_stmt.setString(2, "");
            this.proc_stmt.setString(3, performerId);
            this.proc_stmt.setString(4, "1");
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  performerEscalationMatrix.put("firstName", this.rs.getString("FApproverName"));
                  performerEscalationMatrix.put("firstMailId", this.rs.getString("FApproverEmail"));
                  performerEscalationMatrix.put("secondName", this.rs.getString("SApproverName"));
                  performerEscalationMatrix.put("secondIdMailId", this.rs.getString("SApproverEmail"));
               }
            }
         }

         this.closeResources();
         return performerEscalationMatrix;
      } catch (Exception var4) {
         System.out.println(var4.getMessage());
         throw new RuntimeException("Error in getPerformerEscalation Method : " + var4.getMessage());
      }
   }

   public static void main(String[] args) {
      AgentFlowProcess obj = new AgentFlowProcess();
      obj.getEmployeeDetails("asdasd");
   }

   public String getImmediateMgr(String employeeCode) {
      System.out.println("In getImmediateMgr!!!!");
      String mgrId = "";

      try {
         if (employeeCode != null && employeeCode != "") {
            this.con = this.getDBConnection();
            this.proc_stmt = this.con.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
            this.proc_stmt.setString(1, "");
            this.proc_stmt.setString(2, "");
            this.proc_stmt.setString(3, employeeCode);
            this.proc_stmt.setString(4, "1");
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  mgrId = this.rs.getString("FApproverID");
               }
            }

            this.closeResources();
         }

         return mgrId;
      } catch (Exception var4) {
         System.out.println(var4.getMessage());
         throw new RuntimeException("Error in getImmediateMgr Method !!! : " + var4.getMessage());
      }
   }

   public String getEmployeeDetails(String employeeCode) {
      String mailID = null;
      System.out.println("Employee ID is :" + employeeCode);

      try {
         if (employeeCode != null && employeeCode != "") {
            System.out.println("Employee ID is :" + employeeCode);
            this.con = this.getDBConnection();
            System.out.println("connection is " + this.con);
            this.proc_stmt = this.con.prepareCall("{ call usp_GetEmployeeDetails(?) }");
            this.proc_stmt.setString(1, employeeCode);
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  mailID = this.rs.getString("Employee_Name") + ",";
                  mailID = mailID + this.rs.getString("EMail");
               }
            }
         }

         this.closeResources();
      } catch (Exception var7) {
         System.out.println("Error in Getting Mail ID Frm SQL Server" + var7.getMessage());
         throw new RuntimeException("Error In getMailID Method " + var7.getMessage());
      } finally {
         this.releaseResource(this.con, this.proc_stmt, this.rs);
      }

      return mailID.trim();
   }

   public String getMandatoryInfo() {
      String info = "";

      try {
         String query = " select DEFAULT_CC_MAIL_ID, DEFAULT_PERFORMER_ID from AGENT_RETENTION_MANDATORY_INFO ";
         this.con = GetResource.getDBConnection();
         this.pstmt = this.con.prepareCall(query);

         for(this.rs = this.pstmt.executeQuery(); this.rs.next(); info = info + this.rs.getString("DEFAULT_PERFORMER_ID")) {
            info = this.rs.getString("DEFAULT_CC_MAIL_ID") + ",";
         }
      } catch (Exception var11) {
         System.out.println("Error while getting Mandatory Information" + var11.getMessage());
      } finally {
         GetResource.releaseResource(this.con, this.pstmt);

         try {
            if (this.rs != null) {
               this.rs.close();
            }
         } catch (Exception var10) {
            System.out.println("Error while closing the connetion -- method name = getMandatoryInfo() --" + var10.getMessage());
         }

      }

      return info.trim();
   }

   public void addAgentDetail(long piID) {
      System.out.println("in addAgentDetail method : ");

      try {
         this.blServer = BLClientUtil.getBizLogicServer();
         this.blSession = this.blServer.connect("parshu", "Ebms123#");
         ProcessInstance pi = this.blServer.getProcessInstance(this.blSession, piID);
         String query = "INSERT INTO AGENTRETENTIONMODEL_DETAIL ( PROCESS_INSTANCE_ID, SM_STATUS, VINTAGE_MONTH, LICENCE_TYPE, TIME_LAST_POL, BUSINESS_CATEGORY, ATTRITION_RISK, CLAIM_TAT_CATEGORY, SALES_CATEGORY, BUSINESS_LAST_12MONTH, SM_LEVEL_VINTAGE, FRESH_PER, POLICY_GAP, CLAIM_PER_CATEGORY, PAYOUT_TAT_CATEGORY, AGENT_CODE, ATTRITION_PROBABILITY, BRANCH_CODE, BRANCH_NAME, BM, START_DATE, STATUS, ROLE, SM_NAME, SM_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         this.con = GetResource.getDBConnection();
         this.pstmt = this.con.prepareStatement(query);
         this.pstmt.setLong(1, piID);
         this.pstmt.setString(2, (String)pi.getDataSlotValue("sm_status"));
         this.pstmt.setString(3, pi.getDataSlotValue("vintage_month").toString());
         this.pstmt.setString(4, (String)pi.getDataSlotValue("licence_type"));
         this.pstmt.setString(5, pi.getDataSlotValue("time_last_pol").toString());
         this.pstmt.setString(6, (String)pi.getDataSlotValue("business_category"));
         this.pstmt.setString(7, (String)pi.getDataSlotValue("attrition_risk"));
         this.pstmt.setString(8, (String)pi.getDataSlotValue("claim_TAT_category"));
         this.pstmt.setString(9, (String)pi.getDataSlotValue("sales_category"));
         this.pstmt.setString(10, pi.getDataSlotValue("business_last_12month").toString());
         this.pstmt.setString(11, pi.getDataSlotValue("sm_level_vintage").toString());
         this.pstmt.setString(12, pi.getDataSlotValue("fresh_per").toString());
         this.pstmt.setString(13, pi.getDataSlotValue("policy_gap").toString());
         this.pstmt.setString(14, (String)pi.getDataSlotValue("claim_per_category"));
         this.pstmt.setString(15, (String)pi.getDataSlotValue("payout_TAT_category"));
         this.pstmt.setString(16, (String)pi.getDataSlotValue("agent_code"));
         this.pstmt.setString(17, pi.getDataSlotValue("attrition_probability").toString());
         this.pstmt.setString(18, (String)pi.getDataSlotValue("branch_code"));
         this.pstmt.setString(19, (String)pi.getDataSlotValue("branch_name"));
         this.pstmt.setString(20, (String)pi.getDataSlotValue("bm"));
         this.pstmt.setTimestamp(21, new Timestamp((new Date()).getTime()));
         this.pstmt.setString(22, "ACTIVATED");
         this.pstmt.setString(23, (String)pi.getDataSlotValue("role"));
         this.pstmt.setString(24, (String)pi.getDataSlotValue("sm_name"));
         this.pstmt.setString(25, (String)pi.getDataSlotValue("sm_code"));
         this.pstmt.executeUpdate();
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new RuntimeException("Error in adding Agent Detail : " + var11.getMessage());
      } finally {
         try {
            if (this.blSession != null) {
               this.blServer.disConnect(this.blSession);
            }
         } catch (Exception var12) {
            throw new RuntimeException("BLSession closing error : " + var12.getMessage());
         }

         GetResource.releaseResource(this.con, this.pstmt);
      }

   }
}
