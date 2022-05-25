package com.agent.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GetResource {
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";

   public Connection getDBConnection() {
      try {
         return ((DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB")).getConnection();
      } catch (Exception var2) {
         throw new RuntimeException("Error while getting DataSource Connection", var2);
      }
   }

   public void releaseResource(Connection conn, Statement pstmt, ResultSet rset) {
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
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void releaseResource(Connection conn, PreparedStatement pstmt) {
      try {
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
}
