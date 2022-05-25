package com.rmf.savvion.db;

import com.savvion.sbm.util.ServiceLocator;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
   private static Context jndiCntx = null;
   private static String className = "ConnectionManager";

   static {
      System.out.println("Before initialContext");
      getInitialContext();
      System.out.println("After initialContext");
   }

   private static void getInitialContext() {
      String var0 = "getInitialContext()";

      try {
         System.out.println("Before jndiCntx");
         jndiCntx = ServiceLocator.self().getInitialContext();
         System.out.println("After jndiCntx");
         System.out.println("jndiCntx: " + jndiCntx);
      } catch (NamingException var2) {
         System.out.println("Exception inside catch");
         var2.printStackTrace();
      }

   }

   public static DataSource getDataSource(String dsName) throws NamingException {
      String methodName = "getDataSource(String ptName)";
      DataSource objds = null;
      String strdsJNDI = null;

      try {
         strdsJNDI = "jdbc/" + dsName.trim();
         if (jndiCntx == null) {
            getInitialContext();
         }

         objds = (DataSource)jndiCntx.lookup(strdsJNDI);
         return objds;
      } catch (NamingException var5) {
         var5.printStackTrace();
         return objds;
      }
   }

   public static Connection getDBConnection(String dsName) throws SQLException, NamingException {
      String methodName = "getDBConnection(String ptName)";
      Connection conn = null;

      try {
         conn = getDataSource(dsName).getConnection();
         return conn;
      } catch (SQLException var4) {
         throw var4;
      } catch (NamingException var5) {
         throw var5;
      }
   }

   public static void main(String[] args) {
      new ConnectionManager();

      try {
         Connection conn = getDBConnection("STD");
         System.out.println(conn);
      } catch (SQLException var3) {
         var3.printStackTrace();
      } catch (NamingException var4) {
         var4.printStackTrace();
      }

   }
}
