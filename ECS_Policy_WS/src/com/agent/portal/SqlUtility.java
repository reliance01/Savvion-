package com.agent.portal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SqlUtility {
   private static InitialContext initialContext = null;
   private static DataSource dataSource = null;
   private static String dataSourceName = "jdbc/sqlDB";
   private static Connection connection = null;
   private ResultSet rs = null;
   private CallableStatement proc_stmt = null;

   public Connection getDBConnection() {
      try {
         initialContext = new InitialContext();
         dataSource = (DataSource)initialContext.lookup(dataSourceName);
         connection = dataSource.getConnection();
         return connection;
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

   public static void disConnect(Connection conn) {
      try {
         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var9) {
         throw new RuntimeException(var9);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException var8) {
         }

      }

   }

   public String getEmployeeDetails(String employeeCode) {
      String mailID = null;
      System.out.println("Employee ID is :" + employeeCode);

      try {
         if (employeeCode != null && employeeCode != "") {
            System.out.println("Employee ID is :" + employeeCode);
            connection = this.getDBConnection();
            System.out.println("connection is " + connection);
            this.proc_stmt = connection.prepareCall("{ call usp_GetEmployeeDetails(?) }");
            this.proc_stmt.setString(1, employeeCode);
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  mailID = this.rs.getString("Employee_Name") + ",";
                  mailID = mailID + this.rs.getString("EMail");
               }
            }
         }

         disConnect(connection);
      } catch (Exception var4) {
         System.out.println("Error in Getting Mail ID Frm SQL Server" + var4.getMessage());
         throw new RuntimeException("Error In getMailID Method " + var4.getMessage());
      }

      return mailID.trim();
   }

   public HashMap<String, String> getPerformerEscalationDetail(String performerId) {
      System.out.println("Inside getPerformerEscalationMatrix method !!!");
      HashMap performerEscalationMatrix = new HashMap();

      try {
         if (performerId != null && performerId != "") {
            connection = this.getDBConnection();
            this.proc_stmt = connection.prepareCall("{ call usp_GetAgentRetentionDetails(?,?,?,?) }");
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

         disConnect(connection);
         return performerEscalationMatrix;
      } catch (Exception var4) {
         System.out.println(var4.getMessage());
         throw new RuntimeException("Error in getPerformerEscalation Method : " + var4.getMessage());
      }
   }
}
