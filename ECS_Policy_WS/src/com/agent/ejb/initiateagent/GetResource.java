package com.agent.ejb.initiateagent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GetResource {
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";

   public static Connection getDBConnection() {
      try {
         return ((DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB")).getConnection();
      } catch (Exception var1) {
         throw new RuntimeException("Error while getting getting DataSource Connection", var1);
      }
   }

   public static void releaseResource(Connection conn, Statement pstmt, ResultSet rset) {
      try {
         if (rset != null) {
            rset.close();
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (conn != null) {
            conn.close();
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static void releaseResource(Connection conn, PreparedStatement pstmt) {
      try {
         if (pstmt != null) {
            pstmt.close();
         }

         if (conn != null) {
            conn.close();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
