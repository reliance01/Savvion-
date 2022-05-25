package com.agent.portal;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

public class InitiateAgentProcess {
   private static BLServer blserver = null;
   private static Session blsession = null;
   private static String ptname = "AgentRetentionProcess";
   private static String user = "rgicl";
   private static String password = "rgicl";

   public void createProcessInstance(HashMap dataslots) {
      try {
         blserver = BLClientUtil.getBizLogicServer();
         blsession = blserver.connect(user, password);
         HashMap hmAttributes = new HashMap();
         hmAttributes.put("PROCESSINSTANCENAME", ptname);
         hmAttributes.put("PRIORITY", "Medium");
         hmAttributes.put("CREATOR", user);
         ProcessInstance pi = blserver.createProcessInstance(blsession, ptname, hmAttributes, dataslots, true);
         long id = pi.getProcessInstanceID();
         String processInstanceId = String.valueOf(id);
         String agentCode = pi.getDataSlot("agent_code").toString();
         String role = pi.getDataSlot("role").toString();
         System.out.println("Instance created sucess fully!!!" + id + " AgentCode is " + agentCode + " role is " + role);
         this.updateProcessInstanceFlag(processInstanceId, agentCode, role);
      } catch (Throwable var9) {
         System.out.println("Error while creating istance!!!");
         throw new RuntimeException(var9);
      }
   }

   public void getInstanceCreationData() {
      DBUtility obj = new DBUtility();
      Connection connection = null;
      ResultSet rset = null;
      CallableStatement cstmt = null;

      try {
         connection = obj.getDBConnection();
         String query = "{ call AGENT_INSTANCE_CREATION_DATA(?)}";
         cstmt = connection.prepareCall(query);
         cstmt.registerOutParameter(1, -10);
         cstmt.executeQuery();
         rset = (ResultSet)cstmt.getObject(1);
         String data = null;
         if (rset != null) {
            System.out.println("Got Instance Creation Data Sucessfully !!!");

            while(rset.next()) {
               HashMap<String, String> dataslots = new HashMap();
               dataslots.put("agent_code", rset.getString("AGENT_CODE"));
               System.out.println("Agent Code while getting from SP " + rset.getString("AGENT_CODE"));
               dataslots.put("licence_type", rset.getString("LICENSE_TYPE"));
               dataslots.put("vintage_month", rset.getString("VINTAGE_MONTHS"));
               dataslots.put("fresh_per", rset.getString("FRESH_PER"));
               dataslots.put("policy_gap", rset.getString("POLICYGAP"));
               dataslots.put("sales_category", rset.getString("SALES_CAT"));
               dataslots.put("time_last_pol", rset.getString("TIME_LAST_POL"));
               dataslots.put("claim_per_category", rset.getString("CLAIM_PER_CAT"));
               dataslots.put("claim_TAT_category", rset.getString("CLAIM_TAT_CAT"));
               dataslots.put("payout_TAT_category", rset.getString("PAYOUT_TAT_CAT"));
               dataslots.put("sm_status", rset.getString("SM_STATUS"));
               dataslots.put("sm_level_vintage", rset.getString("SM_LEVEL_VINTAGE"));
               dataslots.put("attrition_probability", rset.getString("ATTRITION_PROB"));
               dataslots.put("business_last_12month", rset.getString("BUSSINESS_LAST_12MNTHS"));
               dataslots.put("business_category", rset.getString("BUSSINESS_CATEGORY"));
               dataslots.put("attrition_risk", rset.getString("ATTRITION_RISK"));
               dataslots.put("branch_code", rset.getString("BRANCH_CODE"));
               dataslots.put("branch_name", rset.getString("BRANCH_NAME"));
               dataslots.put("sm_code", rset.getString("SM_CODE"));
               dataslots.put("sm_name", rset.getString("SM_NAME"));
               dataslots.put("role", rset.getString("ROLE"));
               this.createProcessInstance(dataslots);
            }

            this.updateNonCreationFlag();
         }
      } catch (Exception var11) {
         System.out.println("Error while getting insatance creation data : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, cstmt, rset);
      }

   }

   public void updateProcessInstanceFlag(String processInstanceId, String agentCode, String role) {
      Connection connection = null;
      CallableStatement cstmt = null;
      if (processInstanceId != null && agentCode != null) {
         DBUtility obj = new DBUtility();

         try {
            String query = "{ call AGENT_RETENTION_UTILITY(?,?,?,?,?)}";
            connection = obj.getDBConnection();
            cstmt = connection.prepareCall(query);
            cstmt.setString(1, "UPDATE_CREATION_FLAG");
            cstmt.setString(2, processInstanceId);
            cstmt.setString(3, role);
            cstmt.setString(4, agentCode);
            cstmt.registerOutParameter(5, -10);
            cstmt.executeQuery();
            System.out.println("Instance Creation Flag Updated Successfully!!! ");
         } catch (Exception var16) {
            System.out.println("Error occured while updating instance cretion flag-- Method-updateProcessInstanceFlag : " + var16.getMessage());
         } finally {
            try {
               if (connection != null) {
                  connection.close();
               }

               if (cstmt != null) {
                  cstmt.close();
               }
            } catch (Exception var15) {
               System.out.println("Error while closing the connetion " + var15.getMessage());
            }

         }
      }

   }

   public void updateNonCreationFlag() {
      Connection connection = null;
      CallableStatement cstmt = null;
      DBUtility obj = new DBUtility();

      try {
         String query = "{ call AGENT_RETENTION_UTILITY(?,?,?,?,?)}";
         connection = obj.getDBConnection();
         cstmt = connection.prepareCall(query);
         cstmt.setString(1, "UPDATE_NONCREATION_FLAG");
         cstmt.setString(2, "DUMMY");
         cstmt.setString(3, "DUMMY");
         cstmt.setString(4, "DUMMY");
         cstmt.registerOutParameter(5, -10);
         cstmt.executeQuery();
         System.out.println("Non-Instance Creation Flag Updated Successfully!!! ");
      } catch (Exception var13) {
         System.out.println("Error occured while updating non creation flag-- Method Name- updateNonCreationFlag : " + var13.getMessage());
      } finally {
         try {
            if (connection != null) {
               connection.close();
            }

            if (cstmt != null) {
               cstmt.close();
            }
         } catch (Exception var12) {
            System.out.println("Error while closing the connetion " + var12.getMessage());
         }

      }

   }
}
