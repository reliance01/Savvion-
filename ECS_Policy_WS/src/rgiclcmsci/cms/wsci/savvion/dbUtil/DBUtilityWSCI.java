package rgiclcmsci.cms.wsci.savvion.dbUtil;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.ResponseObject;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.WorkItemObject;

public class DBUtilityWSCI {
   private static Logger logger = Logger.getLogger(DBUtilityWSCI.class);
   final String SBM_HOME = System.getProperty("sbm.home");
   final String CMS_SAVVION_LOG_PROPERTIES;
   final String RGICL_CMS_PROPERTIES;
   Properties propertiesCMSLog;
   Properties props_rgiclcms;
   boolean isSavvionAuditEnable;

   public DBUtilityWSCI() {
      this.CMS_SAVVION_LOG_PROPERTIES = this.SBM_HOME + "/conf/CMSLog4jProperties.properties";
      this.RGICL_CMS_PROPERTIES = this.SBM_HOME + "/conf/RGICL_CMS_WS.properties";
      this.isSavvionAuditEnable = false;

      try {
         this.propertiesCMSLog = new Properties();
         this.propertiesCMSLog.load(new FileInputStream(this.CMS_SAVVION_LOG_PROPERTIES));
         PropertyConfigurator.configure(this.propertiesCMSLog);
         this.props_rgiclcms = new Properties();
         this.props_rgiclcms.load(new FileInputStream(this.RGICL_CMS_PROPERTIES));
         this.isSavvionAuditEnable = Boolean.parseBoolean(this.props_rgiclcms.getProperty("SAVVION_SERVICE_AUDIT_ENABLE"));
      } catch (Exception var2) {
         System.out.println("Error while configuring logger for DBUtility Class " + var2);
      }

   }

   public Connection getDBConnection() {
      System.out.println("isSavvionAuditEnable " + this.isSavvionAuditEnable);
      Connection con = null;

      try {
         logger.info("DBUtilityWSCI.getDBConnection() DBDriverClass " + this.props_rgiclcms.getProperty("DB_DRIVER_CLASS"));
         String userName = this.props_rgiclcms.getProperty("DB_USERNAME");
         String password = this.props_rgiclcms.getProperty("DB_PASSWORD");
         String url = this.props_rgiclcms.getProperty("DB_URL") + ";databaseName=" + this.props_rgiclcms.getProperty("DB_NAME");
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(url, userName, password);
         return con;
      } catch (Exception var5) {
         logger.error("DBUtilityWSCI.getDBConnection() Exception " + var5);
         throw new RuntimeException("DBUtility.getDBConnection() Exception " + var5.getMessage());
      }
   }

   public Connection getDataSourceDBConnection() {
      Properties props = new Properties();
      FileInputStream fis = null;
      SQLServerDataSource ds = new SQLServerDataSource();
      Connection con = null;
      logger.info("DBUtilityWSCI.getDataSourceDBConnection() isSavvionAuditEnable " + this.isSavvionAuditEnable);

      try {
         fis = new FileInputStream(this.RGICL_CMS_PROPERTIES);
         props.load(fis);
         ds.setServerName(props.getProperty("DB_SERVER"));
         ds.setPortNumber(Integer.parseInt(props.getProperty("DB_PORT")));
         ds.setDatabaseName(props.getProperty("DB_NAME"));
         ds.setUser(props.getProperty("DB_USERNAME"));
         ds.setPassword(props.getProperty("DB_PASSWORD"));
         con = ds.getConnection();
      } catch (Exception var6) {
         logger.error("DBUtilityWSCI.getDataSourceDBConnection() Exception " + var6);
         throw new RuntimeException("DBUtilityWSCI.getDataSourceDBConnection() Exception " + var6.getMessage());
      }

      logger.info("DBUtilityWSCI.getDataSourceDBConnection() Connection " + con);
      return con;
   }

   public int insertSavvionAuditData(ResponseObject resObj, String claimRefNo, String processName, String serviceType, String performerId) {
      Connection con = null;
      PreparedStatement pst = null;
      WorkItemObject[] wiObj = null;
      int isInsert = 0;

      try {
         con = this.getDataSourceDBConnection();
         wiObj = resObj.getWorkItemObjects();
         logger.info("DBUtilityWSCI.insertSavvionAuditData() WorkItemObject length " + wiObj);
         if (wiObj == null) {
            String query = "INSERT INTO dbo.Savvion_Service_Audit (ProcessName,Service_Type,ClaimRefNo,ProcessInstance,WorkStepName,WorkItemName,IsInstanceCompleted,Status,CurrentRoleName,ResponseCode,ResponseDesc,PerformerId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, processName);
            pst.setObject(2, serviceType);
            pst.setString(3, claimRefNo);
            pst.setString(4, resObj.getProcessInstanceName());
            pst.setString(5, "NA");
            pst.setString(6, "NA");
            pst.setBoolean(7, resObj.isInstanceCompleted());
            pst.setString(8, resObj.getStatus());
            pst.setString(9, "NA");
            pst.setInt(10, resObj.getResponseCode());
            pst.setString(11, this.props_rgiclcms.getProperty(String.valueOf(resObj.getResponseCode())));
            pst.setString(12, performerId);
            isInsert = pst.executeUpdate();
         } else {
            logger.info("DBUtilityWSCI.insertSavvionAuditData() WorkItemObject length " + wiObj.length);
            logger.info("DBUtilityWSCI.insertSavvionAuditData() ProcessInstanceName " + resObj.getProcessInstanceName() + " isInstanceCompleted " + resObj.isInstanceCompleted() + " Status " + resObj.getStatus() + " processName " + processName + " serviceType " + serviceType + " claimRefNo " + claimRefNo + " performerId " + performerId + " ResponseCode " + resObj.getResponseCode() + " ResDesc " + this.props_rgiclcms.getProperty(String.valueOf(resObj.getResponseCode())));

            for(int i = 0; i < wiObj.length; ++i) {
               if (wiObj[i] != null) {
                  logger.info("DBUtilityWSCI.insertSavvionAuditData() WorkStepName " + wiObj[i].getWorkStepName() + " WorkItem " + wiObj[i].getWorkItemName() + " RoleName " + wiObj[i].getroleName() + " processName " + processName);
                  String query = "INSERT INTO dbo.Savvion_Service_Audit (ProcessName,Service_Type,ClaimRefNo,ProcessInstance,WorkStepName,WorkItemName,IsInstanceCompleted,Status,CurrentRoleName,ResponseCode,ResponseDesc,PerformerId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                  pst = con.prepareStatement(query);
                  pst.setString(1, processName);
                  pst.setString(2, serviceType);
                  pst.setString(3, claimRefNo);
                  pst.setString(4, resObj.getProcessInstanceName());
                  pst.setString(5, wiObj[i].getWorkStepName());
                  pst.setString(6, wiObj[i].getWorkItemName());
                  pst.setBoolean(7, resObj.isInstanceCompleted());
                  pst.setString(8, resObj.getStatus());
                  pst.setString(9, wiObj[i].getroleName());
                  pst.setInt(10, resObj.getResponseCode());
                  pst.setString(11, this.props_rgiclcms.getProperty(String.valueOf(resObj.getResponseCode())));
                  pst.setString(12, performerId);
                  isInsert = pst.executeUpdate();
               }
            }
         }

         logger.info("DBUtilityWSCI.insertSavvionAuditData() RowCount " + isInsert);
      } catch (Exception var15) {
         logger.error("DBUtilityWSCI.insertSavvionAuditData() Exception " + var15);
         var15.printStackTrace();
      } finally {
         this.closeDBConnection(pst, con, (Statement)null);
      }

      return isInsert;
   }

   public void closeDBConnection(PreparedStatement pstmt, Connection con, Statement stmt) {
      logger.info("DBUtilityWSCI.closeDBConnection() Connection " + con);
      if (pstmt != null) {
         try {
            pstmt.close();
         } catch (Exception var7) {
            throw new RuntimeException("Error in DBUtility.insertCreateFlowData Activity " + var7.getMessage());
         }
      }

      if (stmt != null) {
         try {
            stmt.close();
         } catch (Exception var6) {
            throw new RuntimeException("Error in DBUtility.insertCreateFlowData Activity " + var6.getMessage());
         }
      }

      if (con != null) {
         try {
            logger.info("DBUtilityWSCI.closeDBConnection() Connection " + con + " Closed");
            con.close();
         } catch (Exception var5) {
         }
      }

   }

   public static void main(String[] args) {
   }
}
