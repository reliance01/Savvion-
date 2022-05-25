package com.microsoft.rel.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
   private Connection conn = null;
   private String DataSourceName = "jdbc/SBMCommonDB";
   private DataSource ds = null;
   private InitialContext ic = null;

   public Connection getConnection() {
      try {
         this.ic = new InitialContext();
         this.ds = (DataSource)this.ic.lookup(this.DataSourceName);
         this.conn = this.ds.getConnection();
         return this.conn;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void disConnect(Connection conn) {
      try {
         if (conn != null) {
            conn.close();
         }
      } catch (SQLException var10) {
         throw new RuntimeException(var10);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException var9) {
         }

      }

   }
}
