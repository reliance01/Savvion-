package org.rgicl.motor.util;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

class DBManager {
   Connection connection = null;

   public Connection getDBConnection() {
      try {
         Context jndiCntx = new InitialContext();
         DataSource dataSource = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
         this.connection = dataSource.getConnection();
      } catch (Exception var3) {
         throw new RuntimeException("ECS DBManager:: Failed to establish the connection", var3);
      }

      return this.connection;
   }
}
