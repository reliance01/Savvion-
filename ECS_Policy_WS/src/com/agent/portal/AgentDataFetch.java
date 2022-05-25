package com.agent.portal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AgentDataFetch {
   public String getAgentInitiateData() {
      this.deleteOldData();
      ResultSet rset = null;
      Connection con = null;
      PreparedStatement pstmt = null;
      DBUtility obj = new DBUtility();

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         con = DriverManager.getConnection("jdbc:oracle:thin:@10.65.5.179:1765:SASMA", "cmdm", "cmdm");
         String query = " select AGENT_CODE,LICENSE_TYPE,CREATEDTIM,CREATEDBY,VINTAGE_MONTHS,FRESH_PER,POLICYGAP,SALES_CAT,TIME_LAST_POL,CLAIM_PER_CAT,CLAIM_TAT_CAT, PAYOUT_TAT_CAT,SM_STATUS,SM_LEVEL_VINTAGE,ATTRITION_PROB,BUSSINESS_LAST_12MNTHS,BUSSINESS_CATEGORY, ATTRITION_RISK,BRANCH_CODE,BRANCH_NAME,SM_CODE,SM_NAME,'NONE' AS ROLE from AGENT_RETENTION_WITH_BRANCH_SM ";
         con = obj.getDBConnection();
         pstmt = con.prepareCall(query);
         rset = pstmt.executeQuery();
         if (rset != null) {
            System.out.println("Got SAS report data sucessfully !!!");
            this.insertData(rset);
         }
      } catch (Exception var14) {
         System.out.println("error while getting AGent Initiate Data" + var14.getMessage());
      } finally {
         try {
            if (con != null) {
               con.close();
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (rset != null) {
               rset.close();
            }
         } catch (Exception var13) {
            var13.printStackTrace();
         }

      }

      return "Method Called Sucessfully!!!";
   }

   public void insertData(ResultSet rset) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      DBUtility obj = new DBUtility();

      try {
         connection = obj.getDBConnection();
         connection.setAutoCommit(false);
         if (rset != null) {
            String query = " INSERT INTO AGENT_RETENTION_PROCESS_DATA(AGENT_CODE,LICENSE_TYPE,CREATEDTIM,CREATEDBY,VINTAGE_MONTHS,FRESH_PER,POLICYGAP,SALES_CAT,TIME_LAST_POL,CLAIM_PER_CAT,CLAIM_TAT_CAT,PAYOUT_TAT_CAT, SM_STATUS,SM_LEVEL_VINTAGE,ATTRITION_PROB,BUSSINESS_LAST_12MNTHS,BUSSINESS_CATEGORY,ATTRITION_RISK,BRANCH_CODE,BRANCH_NAME,SM_CODE,SM_NAME,ROLE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareCall(query);

            while(rset.next()) {
               pstmt.setString(1, rset.getString(1));
               pstmt.setString(2, rset.getString(2));
               pstmt.setDate(3, rset.getDate(3));
               pstmt.setString(4, rset.getString(4));
               pstmt.setString(5, rset.getString(5));
               pstmt.setString(6, rset.getString(6));
               pstmt.setString(7, rset.getString(7));
               pstmt.setString(8, rset.getString(8));
               pstmt.setString(9, rset.getString(9));
               pstmt.setString(10, rset.getString(10));
               pstmt.setString(11, rset.getString(11));
               pstmt.setString(12, rset.getString(12));
               pstmt.setString(13, rset.getString(13));
               pstmt.setString(14, rset.getString(14));
               pstmt.setString(15, rset.getString(15));
               pstmt.setString(16, rset.getString(16));
               pstmt.setString(17, rset.getString(17));
               pstmt.setString(18, rset.getString(18));
               pstmt.setString(19, rset.getString(19));
               pstmt.setString(20, rset.getString(20));
               pstmt.setString(21, rset.getString(21));
               pstmt.setString(22, rset.getString(22));
               pstmt.setString(23, rset.getString(23));
               pstmt.addBatch();
            }

            pstmt.executeBatch();
            connection.commit();
         }

         System.out.println(" Data inserted into Custom table AGENT_RETENTION_PROCESS_DATA Sucessfully !!");
      } catch (Exception var14) {
         System.out.println("error occured in inserData Method -------" + var14.getMessage());
      } finally {
         try {
            if (connection != null) {
               connection.close();
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (rset != null) {
               rset.close();
            }
         } catch (Exception var13) {
            var13.printStackTrace();
         }

      }

   }

   public void deleteOldData() {
      Connection connection = null;
      CallableStatement cstmt = null;
      DBUtility obj = new DBUtility();

      try {
         String query = "{ call AGENT_RETENTION_UTILITY(?,?,?,?,?)}";
         connection = obj.getDBConnection();
         cstmt = connection.prepareCall(query);
         cstmt.setString(1, "DELETE_OLD_DATA");
         cstmt.setString(2, "DUMMY");
         cstmt.setString(3, "DUMMY");
         cstmt.setString(4, "DUMMY");
         cstmt.registerOutParameter(5, -10);
         cstmt.executeQuery();
         System.out.println("Old Data Deleted Successfully!!! ");
      } catch (Exception var13) {
         System.out.println("Error occured while deleting old Data -- Method Name - deletreOldData : " + var13.getMessage());
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
