package rgicl.ecs.savvion.db;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DBProperties {
   Properties dbProperties = null;
   Properties logProperties = null;
   Properties queryProperties = null;
   public Logger databaselog = null;
   String iiopDataSource = null;
   String DB_PROPERTIES_FILE = null;
   String QUERY_PROPERTIES_FILE = null;
   String LOG_PROPERTIES_FILE = null;

   public DBProperties(String var1) {
      try {
         this.DB_PROPERTIES_FILE = var1 + "/conf/ECS_DATASOURCE_PROPERTIES.properties";
         this.QUERY_PROPERTIES_FILE = var1 + "/conf/ECS_SAVVION_PROPERTIES.properties";
         this.LOG_PROPERTIES_FILE = var1 + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
         this.dbProperties = new Properties();
         this.logProperties = new Properties();
         this.queryProperties = new Properties();
         this.dbProperties.load(new FileInputStream(this.DB_PROPERTIES_FILE));
         this.queryProperties.load(new FileInputStream(this.QUERY_PROPERTIES_FILE));
         this.logProperties.load(new FileInputStream(this.LOG_PROPERTIES_FILE));
         PropertyConfigurator.configure(this.logProperties);
         this.databaselog = Logger.getLogger("Testing");
      } catch (Exception var3) {
         System.out.println(var3);
      }

   }

   public String getIIOPDataSource() {
      this.iiopDataSource = this.dbProperties.getProperty("IIOPDATASOURCE");
      return this.iiopDataSource;
   }
}
