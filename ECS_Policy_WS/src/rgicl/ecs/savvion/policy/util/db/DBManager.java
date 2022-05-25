package rgicl.ecs.savvion.policy.util.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
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

   DBManager(String propertiesPath) {
      this.sbm_home = propertiesPath;
   }

   public Connection getDBConnection() {
      try {
         this.dbProperties = new DBProperties(this.sbm_home);
         String LOG_PROPERTIES_FILE = this.sbm_home + "/conf/ECS_SAVVION_PHASE1A_LOG_PROPERTIES.properties";
         this.logProperties = new Properties();
         this.logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
         PropertyConfigurator.configure(this.logProperties);
         this.dblog = Logger.getLogger("dbutils_Phase1A");
         this.dbProperties.databaselog.info("Establishing the connection");
         String iiopURL = null;
         iiopURL = this.dbProperties.getIIOPDataSource();
         this.dblog.debug("ECS DBManager::iiopURL " + iiopURL);
         Hashtable env = new Hashtable();
         Context jndiCntx = new InitialContext(env);
         DataSource dataSource = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
         this.connection = dataSource.getConnection();
         this.dblog.info("ECS DBManager::connection " + this.connection);
      } catch (Exception var6) {
         this.dblog.error("ECS DBManager:: Failed to establish the connection" + var6);
      }

      return this.connection;
   }
}
