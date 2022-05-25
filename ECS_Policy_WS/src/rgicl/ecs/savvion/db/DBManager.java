package rgicl.ecs.savvion.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class DBManager {
   String sbm_home = null;
   Connection connection = null;
   DBProperties dbProperties = null;
   Properties logProperties = null;
   Logger dblog = null;

   DBManager(String var1) {
      this.sbm_home = var1;
   }

   public Connection getDBConnection() {
      try {
         this.dbProperties = new DBProperties(this.sbm_home);
         String var1 = this.sbm_home + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
         this.logProperties = new Properties();
         this.logProperties.load(new FileInputStream(var1));
         PropertyConfigurator.configure(this.logProperties);
         this.dblog = Logger.getLogger("dbutils");
         this.dbProperties.databaselog.info("Establishing the connection");
         String var2 = null;
         var2 = this.dbProperties.getIIOPDataSource();
         this.dblog.debug("ECS DBManager::iiopURL " + var2);
         Hashtable var3 = new Hashtable();
         var3.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
         var3.put("java.naming.provider.url", var2);
         var3.put("java.naming.security.principal", "");
         var3.put("java.naming.security.credentials", "");
         InitialContext var4 = new InitialContext(var3);
         DataSource var5 = (DataSource)var4.lookup("jdbc/SBMCommonDB");
         this.connection = var5.getConnection();
         this.dblog.info("ECS DBManager::connection " + this.connection);
      } catch (Exception var6) {
         this.dblog.error("ECS DBManager:: Failed to establish the connection" + var6);
      }

      return this.connection;
   }
}
