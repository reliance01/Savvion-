package rgicl.ecs.savvion.db;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.util.Session;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecs.savvion.objectmodel.WorkItemObject;

public class DBUtility {
   Connection connection = null;
   PreparedStatement prepareStatement = null;
   PreparedStatement paymentPrepareStatement = null;
   PreparedStatement spPrepareStatement = null;
   PreparedStatement supervisorPrepareStatement = null;
   Statement statement = null;
   ResultSet resultSet = null;
   String query = null;
   String paymentQuery = null;
   String spQuery = null;
   String supervisorQuery = null;
   Properties logProperties = null;
   Logger databaselog = null;
   Properties queryProperties = null;
   Properties config = null;
   HashMap hashMap = null;
   HashMap resultHashMap = null;
   String sbm_home = null;
   String QUERY_PROPERTIES_FILE = null;
   String LOG_PROPERTIES_FILE = null;
   DBManager dbManager = null;
   private BLClientUtil blc = null;
   private BLServer blserver = null;

   public DBUtility(String var1) {
      try {
         this.sbm_home = var1;
         this.QUERY_PROPERTIES_FILE = var1 + "/conf/ECS_SAVVION_PROPERTIES.properties";
         this.LOG_PROPERTIES_FILE = var1 + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
         this.config = new Properties();
         this.logProperties = new Properties();
         this.logProperties.load(new FileInputStream(this.LOG_PROPERTIES_FILE));
         this.queryProperties = new Properties();
         this.queryProperties.load(new FileInputStream(this.QUERY_PROPERTIES_FILE));
         PropertyConfigurator.configure(this.logProperties);
         this.databaselog = Logger.getLogger("dbutils");
      } catch (Exception var3) {
      }

   }

   public String getProcessTemplateId(String var1) {
      long var2 = 0L;

      try {
         if (var1 != null) {
            BLClientUtil var10001 = this.blc;
            this.blserver = BLClientUtil.getBizLogicServer();
            Session var4 = this.blserver.connect("rgicl", "rgicl");
            ProcessTemplate var5 = this.blserver.getProcessTemplate(var4, var1);
            var2 = var5.getID();
            if (var4 != null) {
               this.blserver.disConnect(var4);
            }
         }
      } catch (Exception var6) {
      }

      return (new Long(var2)).toString();
   }

   public void createConnection() {
      this.dbManager = new DBManager(this.sbm_home);
      this.databaselog.info("createConnection Service :: START");
      this.connection = this.dbManager.getDBConnection();
      this.databaselog.info("createConnection Service :: SUCCESS");
   }

   public String[] getCommonInboxTaskListUtility(String[] var1, String[] var2, String[] var3, String var4) {
      this.databaselog.info("getCommonInboxTaskListUtility Service :: START");
      String var5 = null;
      ArrayList var6 = new ArrayList();
      String var7 = null;

      try {
         String var8 = "";
         String var9 = "";
         String var10 = "";
         String var11 = "";
         String var12 = null;
         String var13 = "";
         String var14 = null;
         String[] var15 = new String[3];
         ArrayList var16 = new ArrayList();
         String var17 = null;
         boolean var18 = false;
         byte var19 = 0;
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: before For Loop - input" + var1.length + var2.length + var3.length);

         int var20;
         for(var20 = 0; var20 < var3.length; ++var20) {
            if (var3[var20] != null) {
               if (var3[var20].equals("HEALTH")) {
                  var15[0] = "HEALTH";
                  break;
               }

               var15[0] = "NONE";
            }
         }

         for(var20 = 0; var20 < var3.length; ++var20) {
            if (var3[var20] != null) {
               if (var3[var20].equals("TRAVEL")) {
                  var15[1] = "TRAVEL";
                  break;
               }

               var15[1] = "NONE";
            }
         }

         for(var20 = 0; var20 < var3.length; ++var20) {
            if (var3[var20] != null) {
               if (var3[var20].equals("PA")) {
                  var15[2] = "PA";
                  break;
               }

               var15[2] = "NONE";
            }
         }

         this.databaselog.debug("getCommonInboxTaskListUtility Service :: after For Loop ");
         if (var15[0].equals("HEALTH") && var15[1].equals("NONE") && var15[2].equals("NONE")) {
            var17 = "'HEALTH'";
         }

         if (var15[0].equals("HEALTH") && var15[1].equals("TRAVEL") && var15[2].equals("NONE")) {
            var17 = "'HEALTH','TRAVEL'";
         }

         if (var15[0].equals("HEALTH") && var15[1].equals("TRAVEL") && var15[2].equals("PA")) {
            var17 = "'HEALTH','TRAVEL','PA'";
         }

         if (var15[0].equals("NONE") && var15[1].equals("TRAVEL") && var15[2].equals("PA")) {
            var17 = "'TRAVEL','PA'";
         }

         if (var15[0].equals("NONE") && var15[1].equals("TRAVEL") && var15[2].equals("NONE")) {
            var17 = "'TRAVEL'";
         }

         if (var15[0].equals("NONE") && var15[1].equals("NONE") && var15[2].equals("PA")) {
            var17 = "'PA'";
         }

         if (var15[0].equals("HEALTH") && var15[1].equals("NONE") && var15[2].equals("PA")) {
            var17 = "'HEALTH','PA'";
         }

         if (var15[0].equals("NONE") && var15[1].equals("NONE") && var15[2].equals("NONE")) {
            var17 = "'NONE'";
         }

         this.databaselog.debug("getCommonInboxTaskListUtility Service :: claimLobQuery :" + var17);

         for(var20 = 0; var20 < var1.length; ++var20) {
            this.databaselog.debug("getCommonInboxTaskListUtility Service :: inside Loop " + var20 + "-" + var1.length + "-i" + var19);
            if (var1[var20] != null) {
               if (var1[var20].equals("RAMMGR")) {
                  var18 = true;
               }

               var16.add(var2[var20]);
               var5 = this.getUserLocation(var1[var20]);
               var11 = this.getLocationQuery(var2, var5);
               this.databaselog.debug("getCommonInboxTaskListUtility Service :: userLocation,locationQuery" + var2 + var5);
               if (var1[var20].equals("BCMMGR")) {
                  var1[var20] = "CLMMGR";
               }

               var8 = " B.PERFORMER='" + var1[var20] + "'";
               if (var20 == 0) {
                  var9 = var8;
                  var12 = var11;
               } else {
                  var9 = var9 + " OR " + var8;
                  var12 = var12 + " OR " + var11;
               }

               this.databaselog.debug("getCommonInboxTaskListUtility Service :: rolequery " + var8);
            }
         }

         this.dbManager = new DBManager(this.sbm_home);
         String[] var33 = new String[var16.size()];
         var16.toArray(var33);
         var14 = this.getPaymentLocationQuery(var33, "POLICYREGIONLOCATION");
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: paymentlocationslist ,paymentlocations " + var16 + var33.length);
         this.databaselog.info("getCommonInboxTaskListUtility Service :: userLocation   :: " + var5);
         this.query = "SELECT  b.WORKITEM_NAME FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECS_FLOW") + " R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND (" + var9 + ") AND TPAFlag='" + var4 + "' AND (" + var12 + ") AND claimLOB IN(" + var17 + ") ";
         this.paymentQuery = "SELECT b.WORKITEM_NAME FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECSPAYMENT_FLOW") + " P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='RAMMGR' AND TPAFlag='" + var4 + "' AND (" + var14 + ")";
         this.databaselog.info("getCommonInboxTaskListUtility Service :: Query:" + this.query);
         this.databaselog.info("getCommonInboxTaskListUtility Service :: PaymentQuery:" + this.paymentQuery);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: " + this.prepareStatement + this.paymentPrepareStatement);
         if (!this.prepareStatement.execute()) {
            this.databaselog.info("getCommonInboxTaskListUtility Service :: Query Execution failed");
         } else {
            this.databaselog.info("getCommonInboxTaskListUtility Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getCommonInboxTaskListUtility Service :: No claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var6.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         if (!this.paymentPrepareStatement.execute()) {
            this.databaselog.info("getCommonInboxTaskListUtility Service :: Payment Query Execution failed");
         } else {
            this.databaselog.info("getCommonInboxTaskListUtility Service :: Payment Query Executed Successfully");
            this.resultSet = this.paymentPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getCommonInboxTaskListUtility Service :: No payment claims avaiable");
            } else {
               do {
                  var6.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         if (var6.size() == 0) {
            var6.add("COMMON_INBOX_EMPTY");
         }

         var7 = var6.toString();
      } catch (Exception var31) {
         this.databaselog.error("Error occured in getCommonInboxTaskListUtility :: " + var31);
         this.databaselog.error("getCommonInboxTaskListUtility :: " + var31.getMessage());

         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var30) {
            this.databaselog.error("Error occured while closing the connection :: " + var30);
         }

         var6.add("DATABASE_ERROR");
      } finally {
         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var29) {
            this.databaselog.error("Error occured during closing prepare statement" + var29);
         }

      }

      this.databaselog.info("getCommonInboxTaskListUtility Service :: END");
      return var7.split(",");
   }

   public String getUserLocation(String var1) {
      String var2 = "";
      if (var1.equals("BCMMGR")) {
         var2 = "CLAIMLOCATION";
      }

      if (var1.equals("CLMMGR")) {
         var2 = "CLAIMREGIONLOCATION";
      }

      if (var1.equals("RCMMGR")) {
         var2 = "CLAIMREGIONLOCATION";
      }

      if (var1.equals("ZCMMGR")) {
         var2 = "CLAIMZONELOCATION";
      }

      if (var1.equals("CCMMGR")) {
         var2 = "CORPORATELOCATION";
      }

      if (var1.equals("BSMMGR")) {
         var2 = "POLICYLOCATION";
      }

      if (var1.equals("ROMMGR")) {
         var2 = "POLICYREGIONLOCATION";
      }

      if (var1.equals("ZOMMGR")) {
         var2 = "POLICYZONELOCATION";
      }

      if (var1.equals("COMMGR")) {
         var2 = "CORPORATELOCATION";
      }

      if (var1.equals("RAMMGR")) {
         var2 = "POLICYREGIONLOCATION";
      }

      this.databaselog.debug("DB:getUserLocation method - getUserLocation" + var2);
      return var2;
   }

   public String getLocationQuery(String[] var1, String var2) {
      String var3 = "";
      String var4 = "";

      for(int var5 = 0; var5 < var1.length; ++var5) {
         if (var1[var5] != null) {
            var3 = " R." + var2 + "='" + var1[var5] + "'";
            if (var5 == 0) {
               var4 = var3;
            } else {
               var4 = var4 + " OR " + var3;
            }
         }
      }

      this.databaselog.debug("DB:getLocationQuery method locationquerylist :" + var4);
      return var4;
   }

   public String getPaymentLocationQuery(String[] var1, String var2) {
      String var3 = "";
      String var4 = "";

      for(int var5 = 0; var5 < var1.length; ++var5) {
         if (var1[var5] != null) {
            var3 = " P." + var2 + "='" + var1[var5] + "'";
            if (var5 == 0) {
               var4 = var3;
            } else {
               var4 = var4 + " OR " + var3;
            }
         }
      }

      this.databaselog.debug("DB:getPaymentLocationQuery method locationquerylist :" + var4);
      return var4;
   }

   public String[] viewPendingClaimsListECS(String var1, String var2, String var3) {
      this.databaselog.info("viewPendingClaimsECS Service :: START");
      String var4 = null;
      ArrayList var5 = new ArrayList();
      String var6 = null;

      try {
         this.dbManager = new DBManager(this.sbm_home);
         if (var1.equals("city")) {
            var4 = "claimLocation";
         }

         if (var1.equals("branch")) {
            var4 = "policyLocation";
         }

         if (var1.equals("region")) {
            if (var3.equals("operations")) {
               var4 = "policyRegionLocation";
            } else {
               var4 = "claimRegionLocation";
            }
         }

         if (var1.equals("zonal")) {
            if (var3.equals("operations")) {
               var4 = "policyzoneLocation";
            } else {
               var4 = "claimZoneLocation";
            }
         }

         if (var1.equals("corporate")) {
            var4 = "corporateLocation";
         }

         this.databaselog.info("viewPendingClaimsECS Service :: userLocation   :: " + var4);
         this.query = "SELECT b.WORKITEM_NAME FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECS_FLOW") + " R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R." + var4 + "='" + var2 + "' AND B.WORKSTEP_ID IN (SELECT E.WORKSTEP_ID FROM ECS_ACTIVITY_TYPE E WHERE E.ACTIVITY_TYPE=?)";
         this.databaselog.info("viewPendingClaimsECS Service :: query :" + this.query);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.prepareStatement.setString(1, var3);
         if (!this.prepareStatement.execute()) {
            this.databaselog.info("viewPendingClaimsECS Service :: Query Execution failed");
         } else {
            this.databaselog.info("viewPendingClaimsECS Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("viewPendingClaimsECS Service :: No pending claims avaiable");
               var5.add("PENDING_CLAIMS_EMPTY");
            } else {
               do {
                  var5.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         var6 = var5.toString();
      } catch (SQLException var16) {
         this.databaselog.error("Error occured in viewpendingclaimECS :: " + var16);
         var5.add("DATABASE_ERROR");
      } finally {
         try {
            this.resultSet.close();
            this.prepareStatement.close();
            this.connection.close();
         } catch (Exception var15) {
            this.databaselog.error("Error occured during closing prepare statement" + var15);
         }

      }

      this.databaselog.info("viewPendingClaimsECS Service :: END");
      return var6.split(",");
   }

   public String insertECS_TASK_STATUS(String var1, String var2, String var3, String var4, String var5, String var6) {
      if (!var1.equals("") && !var2.equals("") && !var3.equals("") && !var4.equals("") && !var5.equals("") && !var6.equals("")) {
         this.databaselog.info("insertECS_TASK_STATUS :: START");

         String var8;
         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_TASK_STATUS_Query");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setString(1, var1);
            this.prepareStatement.setString(2, var2);
            this.prepareStatement.setString(3, var3);
            this.prepareStatement.setString(4, var4);
            this.prepareStatement.setString(5, var5);
            this.prepareStatement.setTimestamp(6, new Timestamp((new Date()).getTime()));
            this.prepareStatement.setString(7, var6);
            this.prepareStatement.execute();
            this.databaselog.info("inserted insertECS_TASK_STATUS:: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var20) {
            this.databaselog.error("Error occured in insertECS_TASK_STATUS :: " + var20.getMessage());

            try {
               this.connection.close();
            } catch (Exception var19) {
               var19.printStackTrace();
               this.databaselog.error("Error occured in insertECS_TASK_STATUS:: while closing the connecton");
            }

            var8 = "FAILURE";
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var18) {
               var18.printStackTrace();
               this.databaselog.error("Exception occured during closing prepare statement" + var18);
            }

         }

         return var8;
      } else {
         this.databaselog.info("------------- INPUT VALUE IS NULL for insertECS_TASK_STATUS------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_SUB(int var1, String var2, String var3, String var4, String var5, String var6, String var7) {
      this.databaselog.info("inserted ECS_COMPLETEALLPI_SUB :: START");
      if (var1 != 0 && !var2.equals("") && !var3.equals("") && !var4.equals("") && !var5.equals("") && !var6.equals("") && !var7.equals("") && !var2.equals((Object)null) && !var3.equals((Object)null) && !var4.equals((Object)null) && !var5.equals((Object)null) && !var6.equals((Object)null) && !var7.equals((Object)null)) {
         String var9;
         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_CompleteAllPi_SubQuery");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setInt(1, var1);
            this.prepareStatement.setString(2, var2);
            this.prepareStatement.setString(3, var3);
            this.prepareStatement.setString(4, var4);
            this.prepareStatement.setString(5, var5);
            this.prepareStatement.setString(6, var6);
            this.prepareStatement.setString(7, var7);
            this.prepareStatement.setTimestamp(8, new Timestamp((new Date()).getTime()));
            this.prepareStatement.execute();
            this.databaselog.info("inserted ECS_COMPLETEALLPI_SUB :: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var21) {
            this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: " + var21.getMessage());

            try {
               this.connection.close();
            } catch (Exception var20) {
               this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: while closing the connecton");
            }

            var9 = "FAILURE";
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var19) {
               this.databaselog.error("Exception occured during closing prepare statement" + var19);
            }

         }

         return var9;
      } else {
         this.databaselog.info("------------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_SUB -------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_PARENT(int var1, String var2, String var3, String var4, String var5, String var6) {
      if (var1 != 0 && !var2.equals("") && !var3.equals("") && !var4.equals("") && !var5.equals("") && !var6.equals("")) {
         String var8;
         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_CompleteAllPiParentQuery");
            this.databaselog.info("insert ECS_COMPLETEALLPI_PARENT  :: START");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setInt(1, var1);
            this.prepareStatement.setString(2, var2);
            this.prepareStatement.setString(3, var3);
            this.prepareStatement.setString(4, var4);
            this.prepareStatement.setString(5, var5);
            this.prepareStatement.setString(6, var6);
            this.prepareStatement.setTimestamp(7, new Timestamp((new Date()).getTime()));
            this.prepareStatement.execute();
            this.databaselog.info("inserted ECS_COMPLETEALLPI_PARENT  :: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var20) {
            this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: " + var20.getMessage());

            try {
               this.connection.close();
            } catch (Exception var19) {
               this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: while closing the connecton");
            }

            var8 = "FAILURE";
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var18) {
               this.databaselog.error("Exception occured during closing prepare statement" + var18);
            }

         }

         return var8;
      } else {
         this.databaselog.info("------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_PARENT ------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String[] getClaimTaskListStatus(int var1) {
      ArrayList var2 = new ArrayList();
      String var3 = null;
      this.dbManager = new DBManager(this.sbm_home);

      try {
         this.resultHashMap = new HashMap();
         this.databaselog.info("getClaimTaskListStatus  :::: START");
         String var4 = "select DISTINCT(WORKSTEP_NAME),STATUS,END_TIME from WORKSTEP where process_instance_id=?  AND TYPE ='ATOMIC'";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(var4);
         this.prepareStatement.setInt(1, var1);
         this.resultSet = this.prepareStatement.executeQuery();
         if (!this.resultSet.next()) {
            this.databaselog.info("getClaimTaskListStatus Service :: No tasks are  avaiable for the PID");
         } else {
            do {
               var2.add(this.resultSet.getString("WORKSTEP_NAME"));
               var2.add(this.resultSet.getString("STATUS"));
               var2.add(this.resultSet.getDate("END_TIME"));
               this.databaselog.debug(this.resultSet.getString("WORKSTEP_NAME") + this.resultSet.getString("STATUS") + this.resultSet.getDate("END_TIME"));
            } while(this.resultSet.next());
         }

         if (var2.size() == 0) {
            var2.add("NOAVAILABLETASKA");
         }

         var3 = var2.toString();
      } catch (SQLException var16) {
         this.databaselog.error("Error occured in getClaimTaskListStatus :: " + var16);

         try {
            this.connection.close();
         } catch (Exception var15) {
            var15.printStackTrace();
         }

         var2.add("DATABASE_ERROR");
      } finally {
         try {
            this.resultSet.close();
            this.statement.close();
            this.connection.close();
         } catch (Exception var14) {
            this.databaselog.error("Exception occured during closing resulsSet and prepare statement" + var14);
         }

      }

      var3 = var2.toString();
      return var3.split(",");
   }

   public void closeConnection() {
      try {
         this.databaselog.info("closeConnection Service :: START");
         this.connection.close();
         this.databaselog.info("closeConnection Service :: SUCCESS");
      } catch (Exception var2) {
         this.databaselog.error("Error occured while closing the Connection");
      }

   }

   public ArrayList getCommonInboxTaskObjects(String[] var1, String[] var2, String[] var3, String var4) {
      this.databaselog.info("getCommonInboxTaskObjects Service :: START");
      String var5 = null;
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      Object var8 = null;

      try {
         String var9 = "";
         String var10 = "";
         String var11 = "";
         String var12 = "";
         String var13 = null;
         String var14 = "";
         String var15 = null;
         String[] var16 = new String[3];
         ArrayList var17 = new ArrayList();
         HashSet var18 = new HashSet();
         Iterator var19 = null;
         new WorkItemObject();
         String var21 = null;
         boolean var22 = false;
         byte var23 = 0;
         this.databaselog.debug("getCommonInboxTaskObjects Service :: before For Loop - input" + var1.length + var2.length + var3.length);

         int var24;
         for(var24 = 0; var24 < var3.length; ++var24) {
            if (var3[var24] != null) {
               this.databaselog.debug("getCommonInboxTaskObjects Service :: lobList[j]" + var3[var24]);
               var18.add(var3[var24]);
            }
         }

         this.databaselog.debug("getCommonInboxTaskObjects Service :: lobset" + var18);

         for(var19 = var18.iterator(); var19.hasNext(); this.databaselog.debug("getCommonInboxTaskObjects Service :: claimLobQuery" + var21)) {
            if (var21 != null) {
               var21 = var21 + "'" + (String)var19.next() + "'" + ",";
            } else {
               var21 = "'" + (String)var19.next() + "'" + ",";
            }
         }

         var21 = var21.substring(0, var21.lastIndexOf(","));
         this.databaselog.debug("getCommonInboxTaskObjects Service :: claimLobQuery" + var21);

         for(var24 = 0; var24 < var1.length; ++var24) {
            this.databaselog.debug("getCommonInboxTaskListUtility Service :: inside Loop " + var24 + "-" + var1.length + "-i" + var23);
            if (var1[var24] != null) {
               if (var1[var24].equals("RAMMGR")) {
                  var22 = true;
               }

               var17.add(var2[var24]);
               var5 = this.getUserLocation(var1[var24]);
               var12 = this.getLocationQuery(var2, var5);
               this.databaselog.debug("getCommonInboxTaskListUtility Service :: userLocation,locationQuery" + var2 + var5);
               if (var1[var24].equals("BCMMGR")) {
                  var1[var24] = "CLMMGR";
               }

               var9 = " B.PERFORMER='" + var1[var24] + "'";
               if (var24 == 0) {
                  var10 = var9;
                  var13 = var12;
               } else {
                  var10 = var10 + " OR " + var9;
                  var13 = var13 + " OR " + var12;
               }

               this.databaselog.debug("getCommonInboxTaskObjects Service :: rolequery " + var9);
            }
         }

         this.dbManager = new DBManager(this.sbm_home);
         String[] var37 = new String[var17.size()];
         var17.toArray(var37);
         var15 = this.getPaymentLocationQuery(var37, "POLICYREGIONLOCATION");
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: paymentlocationslist ,paymentlocations " + var17 + var37.length);
         this.databaselog.info("getCommonInboxTaskListUtility Service :: userLocation   :: " + var5);
         this.query = "SELECT   B.WORKITEM_NAME,B.PROCESS_INSTANCE_ID,R.CLAIMNUMBER,SUBSTR(b.WORKITEM_NAME,0,INSTR(b.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER,R.CMESCCOUNTER,R.BSMESCCOUNTER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECS_FLOW") + " R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND (" + var10 + ") AND R.TPAFLAG='" + var4 + "' AND (" + var13 + ") AND R.CLAIMLOB IN(" + var21 + ") ";
         this.paymentQuery = "SELECT B.WORKITEM_NAME,P.PROCESS_INSTANCE_ID,P.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECSPAYMENT_FLOW") + " P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='RAMMGR' AND P.TPAFLAG='" + var4 + "' AND (" + var15 + ")";
         this.databaselog.info("getCommonInboxTaskListUtility Service :: Query:" + this.query);
         this.databaselog.info("getCommonInboxTaskListUtility Service :: PaymentQuery:" + this.paymentQuery);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: " + this.prepareStatement + this.paymentPrepareStatement);
         WorkItemObject var20;
         if (!this.prepareStatement.execute()) {
            this.databaselog.info("getCommonInboxTaskObjects Service :: Query Execution failed");
         } else {
            this.databaselog.info("getCommonInboxTaskListUtility Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getCommonInboxTaskObjects Service :: No claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var20 = new WorkItemObject();
                  var20.setName(this.resultSet.getString("WORKITEM_NAME"));
                  var20.setPerformer(this.resultSet.getString("PERFORMER"));
                  var20.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  var20.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  var20.setPiName(this.resultSet.getString("PINAME"));
                  var20.setClaimNumber(this.resultSet.getString("CLAIMNUMBER"));
                  var20.setType("RGICL_ECS_FLOW");
                  var20.setCMEscCounter(this.resultSet.getString("CMESCCOUNTER"));
                  var20.setBSMEscCounter(this.resultSet.getString("BSMESCCOUNTER"));
                  var7.add(var20);
               } while(this.resultSet.next());
            }
         }

         if (var22) {
            if (!this.paymentPrepareStatement.execute()) {
               this.databaselog.info("getCommonInboxTaskObjects Service :: Payment Query Execution failed");
            } else {
               this.databaselog.info("getCommonInboxTaskObjects Service :: Payment Query Executed Successfully");
               this.resultSet = this.paymentPrepareStatement.executeQuery();
               if (!this.resultSet.next()) {
                  this.databaselog.info("getCommonInboxTaskObjects Service :: No payment claims avaiable");
               } else {
                  do {
                     this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                     var20 = new WorkItemObject();
                     var20.setName(this.resultSet.getString("WORKITEM_NAME"));
                     var20.setPerformer(this.resultSet.getString("PERFORMER"));
                     var20.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                     var20.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                     var20.setPiName(this.resultSet.getString("PINAME"));
                     var20.setClaimNumber(this.resultSet.getString("CLAIMID"));
                     var20.setType("RGICL_PAYMENT_FLOW");
                     var7.add(var20);
                  } while(this.resultSet.next());
               }
            }
         }
      } catch (Exception var35) {
         this.databaselog.error("getCommonInboxTaskObjects :: " + var35);
         this.databaselog.error("getCommonInboxTaskObjects :: " + var35.getMessage());

         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var34) {
            this.databaselog.error("getCommonInboxTaskObjects:: " + var34);
         }

         var6.add("DATABASE_ERROR");
      } finally {
         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var33) {
            this.databaselog.error("getCommonInboxTaskObjects :Error occured during closing prepare statement" + var33);
         }

      }

      this.databaselog.info("getCommonInboxTaskObjects Service :: END");
      return var7;
   }

   public ArrayList getMyInboxTaskObjects(String var1) {
      this.databaselog.info("getMyInboxTaskObjects Service :: START");
      Object var2 = null;
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      Object var5 = null;

      try {
         new ArrayList();
         new WorkItemObject();
         boolean var8 = false;
         this.dbManager = new DBManager(this.sbm_home);
         this.databaselog.info("getMyInboxTaskObjects Service :: Query Start   :: ");
         this.query = "SELECT   B.WORKITEM_NAME,B.PROCESS_INSTANCE_ID,R.CLAIMNUMBER,SUBSTR(b.WORKITEM_NAME,0,INSTR(b.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER,R.CMESCCOUNTER,R.BSMESCCOUNTER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECS_FLOW") + " R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + var1 + "'";
         this.paymentQuery = "SELECT B.WORKITEM_NAME,P.PROCESS_INSTANCE_ID,P.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECSPAYMENT_FLOW") + " P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + var1 + "'";
         this.spQuery = "SELECT B.WORKITEM_NAME,SP.PROCESS_INSTANCE_ID,SP.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECSSP_FLOW") + " SP, BIZLOGIC_WORKITEM B WHERE SP.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + var1 + "'";
         this.supervisorQuery = "SELECT B.WORKITEM_NAME,SV.PROCESS_INSTANCE_ID,SV.CLAIMNUMBER,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM BIZLOGIC_DS_" + this.getProcessTemplateId("RGICL_ECSSUPERVISOR_FLOW") + " SV, BIZLOGIC_WORKITEM B WHERE SV.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + var1 + "'";
         this.databaselog.info("getMyInboxTaskObjects Service :: Query:" + this.query);
         this.databaselog.info("getMyInboxTaskObjects Service :: PaymentQuery:" + this.paymentQuery);
         this.databaselog.info("getMyInboxTaskObjects Service :: spQuery:" + this.spQuery);
         this.databaselog.info("getMyInboxTaskObjects Service :: supervisorQuery:" + this.supervisorQuery);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         this.spPrepareStatement = this.connection.prepareStatement(this.spQuery);
         this.supervisorPrepareStatement = this.connection.prepareStatement(this.supervisorQuery);
         WorkItemObject var7;
         if (!this.prepareStatement.execute()) {
            this.databaselog.info("getMyInboxTaskObjects Service :: Query Execution failed");
         } else {
            this.databaselog.info("getMyInboxTaskObjects Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getMyInboxTaskObjects Service :: No claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var7 = new WorkItemObject();
                  var7.setName(this.resultSet.getString("WORKITEM_NAME"));
                  var7.setPerformer(this.resultSet.getString("PERFORMER"));
                  var7.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  var7.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  var7.setPiName(this.resultSet.getString("PINAME"));
                  var7.setClaimNumber(this.resultSet.getString("CLAIMNUMBER"));
                  var7.setType("RGICL_ECS_FLOW");
                  var7.setCMEscCounter(this.resultSet.getString("CMESCCOUNTER"));
                  var7.setBSMEscCounter(this.resultSet.getString("BSMESCCOUNTER"));
                  var4.add(var7);
               } while(this.resultSet.next());
            }
         }

         if (!this.paymentPrepareStatement.execute()) {
            this.databaselog.info("getMyInboxTaskObjects Service :: Payment Query Execution failed");
         } else {
            this.databaselog.info("getMyInboxTaskObjects Service :: Payment Query Executed Successfully");
            this.resultSet = this.paymentPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getMyInboxTaskObjects Service :: No payment claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var7 = new WorkItemObject();
                  var7.setName(this.resultSet.getString("WORKITEM_NAME"));
                  var7.setPerformer(this.resultSet.getString("PERFORMER"));
                  var7.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  var7.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  var7.setPiName(this.resultSet.getString("PINAME"));
                  var7.setClaimNumber(this.resultSet.getString("CLAIMID"));
                  var7.setType("RGICL_PAYMENT_FLOW");
                  var4.add(var7);
               } while(this.resultSet.next());
            }
         }

         if (!this.spPrepareStatement.execute()) {
            this.databaselog.info("getMyInboxTaskObjects Service :: ServiceProvidor Query Execution failed");
         } else {
            this.databaselog.info("getMyInboxTaskObjects Service :: ServiceProvidor Query Executed Successfully");
            this.resultSet = this.spPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getMyInboxTaskObjects Service :: No ServiceProvidor claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var7 = new WorkItemObject();
                  var7.setName(this.resultSet.getString("WORKITEM_NAME"));
                  var7.setPerformer(this.resultSet.getString("PERFORMER"));
                  var7.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  var7.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  var7.setPiName(this.resultSet.getString("PINAME"));
                  var7.setClaimNumber(this.resultSet.getString("CLAIMID"));
                  var7.setType("RGICL_ECSSP_FLOW");
                  var4.add(var7);
               } while(this.resultSet.next());
            }
         }

         if (!this.supervisorPrepareStatement.execute()) {
            this.databaselog.info("getMyInboxTaskObjects Service :: Supervisor Query Execution failed");
         } else {
            this.databaselog.info("getMyInboxTaskObjects Service :: Supervisor Query Executed Successfully");
            this.resultSet = this.supervisorPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("getMyInboxTaskObjects Service :: No Supervisor claims avaiable");
            } else {
               do {
                  this.databaselog.debug("workitem -------" + this.resultSet.getString("WORKITEM_NAME"));
                  var7 = new WorkItemObject();
                  var7.setName(this.resultSet.getString("WORKITEM_NAME"));
                  var7.setPerformer(this.resultSet.getString("PERFORMER"));
                  var7.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  var7.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  var7.setPiName(this.resultSet.getString("PINAME"));
                  var7.setClaimNumber(this.resultSet.getString("CLAIMNUMBER"));
                  var7.setType("RGICL_ECSSUPERVISOR_FLOW");
                  var4.add(var7);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var19) {
         this.databaselog.error("getMyInboxTaskObjects :: " + var19);
         this.databaselog.error("getMyInboxTaskObjects :: " + var19.getMessage());

         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var18) {
            this.databaselog.error("getMyInboxTaskObjects:Error occured while closing the connection :: " + var18);
         }

         var3.add("DATABASE_ERROR");
      } finally {
         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var17) {
            this.databaselog.error("Error occured during closing prepare statement" + var17);
         }

      }

      this.databaselog.info("getMyInboxTaskObjects Service :: END");
      return var4;
   }
}
