package rgicl.ecs.savvion.policy.util.db;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
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
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecs.savvion.policy.objectmodel.WorkItemObject;

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
   private BLClientUtil blc;
   private BLServer blserver;

   public DBUtility(String propertiesPath) {
      try {
         this.sbm_home = propertiesPath;
         this.QUERY_PROPERTIES_FILE = propertiesPath + "/conf/ECS_SAVVION_PROPERTIES.properties";
         this.LOG_PROPERTIES_FILE = propertiesPath + "/conf/ECS_SAVVION_PHASE1A_LOG_PROPERTIES.properties";
         this.config = new Properties();
         this.logProperties = new Properties();
         this.logProperties.load(new FileInputStream(this.LOG_PROPERTIES_FILE));
         this.queryProperties = new Properties();
         this.queryProperties.load(new FileInputStream(this.QUERY_PROPERTIES_FILE));
         PropertyConfigurator.configure(this.logProperties);
         this.databaselog = Logger.getLogger("dbutils_Phase1A");
      } catch (Exception var3) {
      }

   }

   public void createConnection() {
      this.dbManager = new DBManager(this.sbm_home);
      this.databaselog.info("createConnection Service :: START");
      this.connection = this.dbManager.getDBConnection();
      this.databaselog.info("createConnection Service :: SUCCESS");
   }

   public String getProcessTemplateId(String s) {
      long l = 0L;

      try {
         if (s != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            Session session = this.blserver.connect("rgicl", "rgicl");
            ProcessTemplate processtemplate = this.blserver.getProcessTemplate(session, s);
            l = processtemplate.getID();
            if (session != null) {
               this.blserver.disConnect(session);
            }
         }
      } catch (Exception var6) {
      }

      return (new Long(l)).toString();
   }

   public String[] getCommonInboxTaskListUtility(String[] roleList, String[] locationList, String[] lobList, String TPAFlag) {
      this.databaselog.info("getCommonInboxTaskListUtility Service :: START");
      String userLocation = null;
      ArrayList pendingWorkItems = new ArrayList();
      String pendingWorkItemsArray = null;

      try {
         String rolequery = "";
         String rolequerylist = "";
         String rolelocation = "";
         String locationQuery = "";
         String locationQueryList = null;
         String paymentLocationQuery = "";
         String paymentLocationQueryList = null;
         String[] claimLobList = new String[3];
         ArrayList paymentlocationslist = new ArrayList();
         String claimLobQuery = null;
         boolean ramuser = false;
         int i = 0;
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: before For Loop - input" + roleList.length + locationList.length + lobList.length);

         int role;
         for(role = 0; role < lobList.length; ++role) {
            if (lobList[role] != null) {
               if (lobList[role].equals("HEALTH")) {
                  claimLobList[0] = "HEALTH";
                  break;
               }

               claimLobList[0] = "NONE";
            }
         }

         for(role = 0; role < lobList.length; ++role) {
            if (lobList[role] != null) {
               if (lobList[role].equals("TRAVEL")) {
                  claimLobList[1] = "TRAVEL";
                  break;
               }

               claimLobList[1] = "NONE";
            }
         }

         for(role = 0; role < lobList.length; ++role) {
            if (lobList[role] != null) {
               if (lobList[role].equals("PA")) {
                  claimLobList[2] = "PA";
                  break;
               }

               claimLobList[2] = "NONE";
            }
         }

         this.databaselog.debug("getCommonInboxTaskListUtility Service :: after For Loop ");
         if (claimLobList[0].equals("HEALTH") && claimLobList[1].equals("NONE") && claimLobList[2].equals("NONE")) {
            claimLobQuery = "'HEALTH'";
         }

         if (claimLobList[0].equals("HEALTH") && claimLobList[1].equals("TRAVEL") && claimLobList[2].equals("NONE")) {
            claimLobQuery = "'HEALTH','TRAVEL'";
         }

         if (claimLobList[0].equals("HEALTH") && claimLobList[1].equals("TRAVEL") && claimLobList[2].equals("PA")) {
            claimLobQuery = "'HEALTH','TRAVEL','PA'";
         }

         if (claimLobList[0].equals("NONE") && claimLobList[1].equals("TRAVEL") && claimLobList[2].equals("PA")) {
            claimLobQuery = "'TRAVEL','PA'";
         }

         if (claimLobList[0].equals("NONE") && claimLobList[1].equals("TRAVEL") && claimLobList[2].equals("NONE")) {
            claimLobQuery = "'TRAVEL'";
         }

         if (claimLobList[0].equals("NONE") && claimLobList[1].equals("NONE") && claimLobList[2].equals("PA")) {
            claimLobQuery = "'PA'";
         }

         if (claimLobList[0].equals("HEALTH") && claimLobList[1].equals("NONE") && claimLobList[2].equals("PA")) {
            claimLobQuery = "'HEALTH','PA'";
         }

         if (claimLobList[0].equals("NONE") && claimLobList[1].equals("NONE") && claimLobList[2].equals("NONE")) {
            claimLobQuery = "'NONE'";
         }

         this.databaselog.debug("getCommonInboxTaskListUtility Service :: claimLobQuery :" + claimLobQuery);

         for(role = 0; role < roleList.length; ++role) {
            this.databaselog.debug("getCommonInboxTaskListUtility Service :: inside Loop " + role + "-" + roleList.length + "-i" + i);
            if (roleList[role] != null) {
               if (roleList[role].equals("RAMMGR")) {
                  ramuser = true;
               }

               paymentlocationslist.add(locationList[role]);
               userLocation = this.getUserLocation(roleList[role]);
               locationQuery = this.getLocationQuery(locationList, userLocation);
               this.databaselog.debug("getCommonInboxTaskListUtility Service :: userLocation,locationQuery" + locationList + userLocation);
               if (roleList[role].equals("BCMMGR")) {
                  roleList[role] = "CLMMGR";
               }

               rolequery = " B.PERFORMER='" + roleList[role] + "'";
               if (role == 0) {
                  rolequerylist = rolequery;
                  locationQueryList = locationQuery;
               } else {
                  rolequerylist = rolequerylist + " OR " + rolequery;
                  locationQueryList = locationQueryList + " OR " + locationQuery;
               }

               this.databaselog.debug("getCommonInboxTaskListUtility Service :: rolequery " + rolequery);
            }
         }

         this.dbManager = new DBManager(this.sbm_home);
         String[] paymentlocations = new String[paymentlocationslist.size()];
         paymentlocationslist.toArray(paymentlocations);
         paymentLocationQueryList = this.getPaymentLocationQuery(paymentlocations, "POLICYREGIONLOCATION");
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: paymentlocationslist ,paymentlocations " + paymentlocationslist + paymentlocations.length);
         this.databaselog.info("getCommonInboxTaskListUtility Service :: userLocation   :: " + userLocation);
         this.query = "SELECT  b.WORKITEM_NAME FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND (" + rolequerylist + ") AND TPAFlag='" + TPAFlag + "' AND (" + locationQueryList + ") AND claimLOB IN(" + claimLobQuery + ") ";
         this.paymentQuery = "SELECT b.WORKITEM_NAME FROM  RGICL_ECSPAYMENT_FLOW P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='RAMMGR' AND TPAFlag='" + TPAFlag + "' AND (" + paymentLocationQueryList + ")";
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
                  pendingWorkItems.add(this.resultSet.getString("WORKITEM_NAME"));
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
                  pendingWorkItems.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         if (pendingWorkItems.size() == 0) {
            pendingWorkItems.add("COMMON_INBOX_EMPTY");
         }

         pendingWorkItemsArray = pendingWorkItems.toString();
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

         pendingWorkItems.add("DATABASE_ERROR");
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
      return pendingWorkItemsArray.split(",");
   }

   public String getUserLocation(String roleList) {
      String userLocation = "";
      if (roleList.equals("BCMMGR")) {
         userLocation = "CLAIMLOCATION";
      }

      if (roleList.equals("CLMMGR")) {
         userLocation = "CLAIMREGIONLOCATION";
      }

      if (roleList.equals("RCMMGR")) {
         userLocation = "CLAIMREGIONLOCATION";
      }

      if (roleList.equals("ZCMMGR")) {
         userLocation = "CLAIMZONELOCATION";
      }

      if (roleList.equals("CCMMGR")) {
         userLocation = "CORPORATELOCATION";
      }

      if (roleList.equals("BSMMGR")) {
         userLocation = "POLICYLOCATION";
      }

      if (roleList.equals("ROMMGR")) {
         userLocation = "POLICYREGIONLOCATION";
      }

      if (roleList.equals("ZOMMGR")) {
         userLocation = "POLICYZONELOCATION";
      }

      if (roleList.equals("COMMGR")) {
         userLocation = "CORPORATELOCATION";
      }

      if (roleList.equals("RAMMGR")) {
         userLocation = "POLICYREGIONLOCATION";
      }

      this.databaselog.debug("DB:getUserLocation method - getUserLocation" + userLocation);
      return userLocation;
   }

   public String getLocationQuery(String[] locationList, String userLocation) {
      String locationquery = "";
      String locationquerylist = "";

      for(int location = 0; location < locationList.length; ++location) {
         if (locationList[location] != null) {
            locationquery = " R." + userLocation + "='" + locationList[location] + "'";
            if (location == 0) {
               locationquerylist = locationquery;
            } else {
               locationquerylist = locationquerylist + " OR " + locationquery;
            }
         }
      }

      this.databaselog.debug("DB:getLocationQuery method locationquerylist :" + locationquerylist);
      return locationquerylist;
   }

   public String getPaymentLocationQuery(String[] locationList, String userLocation) {
      String locationquery = "";
      String locationquerylist = "";

      for(int location = 0; location < locationList.length; ++location) {
         if (locationList[location] != null) {
            locationquery = " P." + userLocation + "='" + locationList[location] + "'";
            if (location == 0) {
               locationquerylist = locationquery;
            } else {
               locationquerylist = locationquerylist + " OR " + locationquery;
            }
         }
      }

      this.databaselog.debug("DB:getPaymentLocationQuery method locationquerylist :" + locationquerylist);
      return locationquerylist;
   }

   public String[] viewPendingClaimsListECS(String userLocationType, String userLocationId, String userTeamType) {
      this.databaselog.info("viewPendingClaimsECS Service :: START");
      String userLocation = null;
      ArrayList pendingWorkItems = new ArrayList();
      String pendingWorkItemsArray = null;

      try {
         this.dbManager = new DBManager(this.sbm_home);
         if (userLocationType.equals("city")) {
            userLocation = "claimLocation";
         }

         if (userLocationType.equals("branch")) {
            userLocation = "policyLocation";
         }

         if (userLocationType.equals("region")) {
            if (userTeamType.equals("operations")) {
               userLocation = "policyRegionLocation";
            } else {
               userLocation = "claimRegionLocation";
            }
         }

         if (userLocationType.equals("zonal")) {
            if (userTeamType.equals("operations")) {
               userLocation = "policyzoneLocation";
            } else {
               userLocation = "claimZoneLocation";
            }
         }

         if (userLocationType.equals("corporate")) {
            userLocation = "corporateLocation";
         }

         this.databaselog.info("viewPendingClaimsECS Service :: userLocation   :: " + userLocation);
         this.query = "SELECT b.WORKITEM_NAME FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R." + userLocation + "='" + userLocationId + "' AND B.WORKSTEP_ID IN (SELECT E.WORKSTEP_ID FROM ECS_ACTIVITY_TYPE E WHERE E.ACTIVITY_TYPE=?)";
         this.databaselog.info("viewPendingClaimsECS Service :: query :" + this.query);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.prepareStatement.setString(1, userTeamType);
         if (!this.prepareStatement.execute()) {
            this.databaselog.info("viewPendingClaimsECS Service :: Query Execution failed");
         } else {
            this.databaselog.info("viewPendingClaimsECS Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               this.databaselog.info("viewPendingClaimsECS Service :: No pending claims avaiable");
               pendingWorkItems.add("PENDING_CLAIMS_EMPTY");
            } else {
               do {
                  pendingWorkItems.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         pendingWorkItemsArray = pendingWorkItems.toString();
      } catch (SQLException var17) {
         this.databaselog.error("Error occured in viewpendingclaimECS :: " + var17);
         pendingWorkItems.add("DATABASE_ERROR");
      } finally {
         try {
            this.resultSet.close();
            this.prepareStatement.close();
            this.connection.close();
         } catch (Exception var16) {
            this.databaselog.error("Error occured during closing prepare statement" + var16);
         }

      }

      this.databaselog.info("viewPendingClaimsECS Service :: END");
      return pendingWorkItemsArray.split(",");
   }

   public String insertECS_TASK_STATUS(String processInstanceName, String uploadDocsFlag, String reviseReserveFlag, String assignSPFlag, String performer, String claimRefNo) {
      if (!processInstanceName.equals("") && !uploadDocsFlag.equals("") && !reviseReserveFlag.equals("") && !assignSPFlag.equals("") && !performer.equals("") && !claimRefNo.equals("")) {
         this.databaselog.info("insertECS_TASK_STATUS :: START");

         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_TASK_STATUS_Query");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setString(1, processInstanceName);
            this.prepareStatement.setString(2, uploadDocsFlag);
            this.prepareStatement.setString(3, reviseReserveFlag);
            this.prepareStatement.setString(4, assignSPFlag);
            this.prepareStatement.setString(5, performer);
            this.prepareStatement.setTimestamp(6, new Timestamp((new Date()).getTime()));
            this.prepareStatement.setString(7, claimRefNo);
            this.prepareStatement.execute();
            this.databaselog.info("inserted insertECS_TASK_STATUS:: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var19) {
            this.databaselog.error("Error occured in insertECS_TASK_STATUS :: " + var19.getMessage());

            try {
               this.connection.close();
            } catch (Exception var18) {
               var18.printStackTrace();
               this.databaselog.error("Error occured in insertECS_TASK_STATUS:: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var17) {
               var17.printStackTrace();
               this.databaselog.error("Exception occured during closing prepare statement" + var17);
            }

         }

         return "FAILURE";
      } else {
         this.databaselog.info("------------- INPUT VALUE IS NULL for insertECS_TASK_STATUS------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_SUB(int PID, String PIName, String parentPID, String claimID, String current_status, String performer, String closed_By) {
      this.databaselog.info("inserted ECS_COMPLETEALLPI_SUB :: START");
      if (PID != 0 && !PIName.equals("") && !parentPID.equals("") && !claimID.equals("") && !current_status.equals("") && !performer.equals("") && !closed_By.equals("") && !PIName.equals((Object)null) && !parentPID.equals((Object)null) && !claimID.equals((Object)null) && !current_status.equals((Object)null) && !performer.equals((Object)null) && !closed_By.equals((Object)null)) {
         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_CompleteAllPi_SubQuery");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setInt(1, PID);
            this.prepareStatement.setString(2, PIName);
            this.prepareStatement.setString(3, parentPID);
            this.prepareStatement.setString(4, claimID);
            this.prepareStatement.setString(5, current_status);
            this.prepareStatement.setString(6, performer);
            this.prepareStatement.setString(7, closed_By);
            this.prepareStatement.setTimestamp(8, new Timestamp((new Date()).getTime()));
            this.prepareStatement.execute();
            this.databaselog.info("inserted ECS_COMPLETEALLPI_SUB :: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var20) {
            this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: " + var20.getMessage());

            try {
               this.connection.close();
            } catch (Exception var19) {
               this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var18) {
               this.databaselog.error("Exception occured during closing prepare statement" + var18);
            }

         }

         return "FAILURE";
      } else {
         this.databaselog.info("------------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_SUB -------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_PARENT(int PID, String PIName, String claimID, String current_status, String performer, String closed_By) {
      if (PID != 0 && !PIName.equals("") && !claimID.equals("") && !current_status.equals("") && !performer.equals("") && !closed_By.equals("")) {
         try {
            this.dbManager = new DBManager(this.sbm_home);
            this.query = this.queryProperties.getProperty("insertECS_CompleteAllPiParentQuery");
            this.databaselog.info("insert ECS_COMPLETEALLPI_PARENT  :: START");
            this.connection = this.dbManager.getDBConnection();
            this.prepareStatement = this.connection.prepareStatement(this.query);
            this.prepareStatement.setInt(1, PID);
            this.prepareStatement.setString(2, PIName);
            this.prepareStatement.setString(3, claimID);
            this.prepareStatement.setString(4, current_status);
            this.prepareStatement.setString(5, performer);
            this.prepareStatement.setString(6, closed_By);
            this.prepareStatement.setTimestamp(7, new Timestamp((new Date()).getTime()));
            this.prepareStatement.execute();
            this.databaselog.info("inserted ECS_COMPLETEALLPI_PARENT  :: SUCCESS");
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var19) {
            this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: " + var19.getMessage());

            try {
               this.connection.close();
            } catch (Exception var18) {
               this.databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var17) {
               this.databaselog.error("Exception occured during closing prepare statement" + var17);
            }

         }

         return "FAILURE";
      } else {
         this.databaselog.info("------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_PARENT ------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String[] getClaimTaskListStatus(int PID) {
      ArrayList taskResult = new ArrayList();
      String taskStatus = null;
      this.dbManager = new DBManager(this.sbm_home);

      try {
         this.resultHashMap = new HashMap();
         this.databaselog.info("getClaimTaskListStatus  :::: START");
         String query = "select DISTINCT(WORKSTEP_NAME),STATUS,END_TIME from WORKSTEP where process_instance_id=?  AND TYPE ='ATOMIC'";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(query);
         this.prepareStatement.setInt(1, PID);
         this.resultSet = this.prepareStatement.executeQuery();
         if (!this.resultSet.next()) {
            this.databaselog.info("getClaimTaskListStatus Service :: No tasks are  avaiable for the PID");
         } else {
            do {
               taskResult.add(this.resultSet.getString("WORKSTEP_NAME"));
               taskResult.add(this.resultSet.getString("STATUS"));
               taskResult.add(this.resultSet.getDate("END_TIME"));
               this.databaselog.debug(this.resultSet.getString("WORKSTEP_NAME") + this.resultSet.getString("STATUS") + this.resultSet.getDate("END_TIME"));
            } while(this.resultSet.next());
         }

         if (taskResult.size() == 0) {
            taskResult.add("NOAVAILABLETASKA");
         }

         taskStatus = taskResult.toString();
      } catch (SQLException var16) {
         this.databaselog.error("Error occured in getClaimTaskListStatus :: " + var16);

         try {
            this.connection.close();
         } catch (Exception var15) {
            var15.printStackTrace();
         }

         taskResult.add("DATABASE_ERROR");
      } finally {
         try {
            this.resultSet.close();
            this.statement.close();
            this.connection.close();
         } catch (Exception var14) {
            this.databaselog.error("Exception occured during closing resulsSet and prepare statement" + var14);
         }

      }

      taskStatus = taskResult.toString();
      return taskStatus.split(",");
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

   public ArrayList getMyInboxTaskObjects(String user, String ptName) {
      this.databaselog.info("getCommonInboxTaskObjects Service :: START");
      String userLocation = null;
      ArrayList pendingWorkItems = new ArrayList();
      ArrayList workItemList = new ArrayList();
      Object var6 = null;

      try {
         new WorkItemObject();
         this.dbManager = new DBManager(this.sbm_home);
         if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber,R.PMUser, R.PCHUser, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.isEDMCheckerAccepted, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, R.transactionType ,trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (P.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.databaselog.debug("getCommonInboxTaskListUtility Service :: " + this.prepareStatement);
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
                  WorkItemObject workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                  workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  workitem.setPiName(this.resultSet.getString("PINAME"));
                  workitem.setPolicyNumber(this.resultSet.getString("PolicyNumber"));
                  workitem.setPolicyStatus(this.resultSet.getString("PolicyStatus"));
                  workitem.setStartDate(this.resultSet.getString("StartDate"));
                  workitem.setEndDate(this.resultSet.getString("EndDate"));
                  workitem.setPolicyName(this.resultSet.getString("PolicyName"));
                  workitem.setProposalNumber(this.resultSet.getString("ProposalNumber"));
                  workitem.setBranch(this.resultSet.getString("Branch"));
                  workitem.setCity(this.resultSet.getString("City"));
                  String ageing = this.resultSet.getString("days") + "D:" + this.resultSet.getString("hours") + "H";
                  workitem.setAgeing(ageing);
                  if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW") || ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
                     workitem.setIsPolicyCheckerAccepted(this.resultSet.getString("isPolicyCheckerAccepted"));
                  }

                  long piid = this.resultSet.getLong("PROCESS_INSTANCE_ID");
                  BLServer blserver;
                  Session blsession;
                  ProcessInstance pi;
                  String dsv;
                  String[] arr;
                  if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("ECSAdmin", "ECSAdmin");
                     pi = blserver.getProcessInstance(blsession, piid);
                     dsv = (String)pi.getDataSlotValue("PolicyCheckerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("PolicyMakerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerRemark(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("PolicyMakerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerUser(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("PolicyCheckerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerUser(arr);
                     }

                     workitem.setPmuser(this.resultSet.getString("PMUser"));
                     workitem.setPchuser(this.resultSet.getString("PCHUser"));
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("ECSAdmin", "ECSAdmin");
                     pi = blserver.getProcessInstance(blsession, piid);
                     dsv = (String)pi.getDataSlotValue("PolicyCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("PolicyConfiguratorRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyConfiguratorRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("ECSAdmin", "ECSAdmin");
                     pi = blserver.getProcessInstance(blsession, piid);
                     dsv = (String)pi.getDataSlotValue("MakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
                     workitem.setIsEndorsementCheckerAccepted(this.resultSet.getString("isEDMCheckerAccepted"));
                     workitem.setTransactionType(this.resultSet.getString("transactiontype"));
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("ECSAdmin", "ECSAdmin");
                     pi = blserver.getProcessInstance(blsession, piid);
                     dsv = (String)pi.getDataSlotValue("EndorsementCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentCheckerRemarks(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("EndorsementMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEndorsementMakerRemarks(arr);
                     }

                     dsv = (String)pi.getDataSlotValue("EnrollmentMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  workItemList.add(workitem);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var26) {
         var26.printStackTrace();
         this.databaselog.error("getCommonInboxTaskObjects :: " + var26);
         this.databaselog.error("getCommonInboxTaskObjects :: " + var26.getMessage());

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
         } catch (Exception var25) {
            var25.printStackTrace();
            this.databaselog.error("getCommonInboxTaskObjects:: " + var25);
         }

         pendingWorkItems.add("DATABASE_ERROR");
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
         } catch (Exception var24) {
            this.databaselog.error("getCommonInboxTaskObjects :Error occured during closing prepare statement" + var24);
         }

      }

      this.databaselog.info("getCommonInboxTaskObjects Service :: END");
      return workItemList;
   }

   public ArrayList getMyInboxTaskObjects(String userId) {
      this.databaselog.info("getMyInboxTaskObjects Service :: START");
      String userLocation = null;
      ArrayList pendingWorkItems = new ArrayList();
      ArrayList workItemList = new ArrayList();
      Object var5 = null;

      try {
         new ArrayList();
         new WorkItemObject();
         boolean ramuser = false;
         this.dbManager = new DBManager(this.sbm_home);
         this.databaselog.info("getMyInboxTaskObjects Service :: Query Start   :: ");
         this.query = "SELECT B.WORKITEM_NAME,B.PROCESS_INSTANCE_ID,R.CLAIMNUMBER,SUBSTR(b.WORKITEM_NAME,0,INSTR(b.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER,R.CMESCCOUNTER,R.BSMESCCOUNTER FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.paymentQuery = "SELECT B.WORKITEM_NAME,P.PROCESS_INSTANCE_ID,P.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSPAYMENT_FLOW P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.spQuery = "SELECT B.WORKITEM_NAME,SP.PROCESS_INSTANCE_ID,SP.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSSP_FLOW SP, BIZLOGIC_WORKITEM B WHERE SP.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.supervisorQuery = "SELECT B.WORKITEM_NAME,SV.PROCESS_INSTANCE_ID,SV.CLAIMNUMBER,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSSUPERVISOR_FLOW SV, BIZLOGIC_WORKITEM B WHERE SV.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.databaselog.info("getMyInboxTaskObjects Service :: Query:" + this.query);
         this.databaselog.info("getMyInboxTaskObjects Service :: PaymentQuery:" + this.paymentQuery);
         this.databaselog.info("getMyInboxTaskObjects Service :: spQuery:" + this.spQuery);
         this.databaselog.info("getMyInboxTaskObjects Service :: supervisorQuery:" + this.supervisorQuery);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         this.spPrepareStatement = this.connection.prepareStatement(this.spQuery);
         this.supervisorPrepareStatement = this.connection.prepareStatement(this.supervisorQuery);
         WorkItemObject workitem;
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
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                  workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  workitem.setPiName(this.resultSet.getString("PINAME"));
                  workItemList.add(workitem);
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
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                  workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  workitem.setPiName(this.resultSet.getString("PINAME"));
                  workitem.setType("RGICL_PAYMENT_FLOW");
                  workItemList.add(workitem);
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
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                  workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  workitem.setPiName(this.resultSet.getString("PINAME"));
                  workitem.setType("RGICL_ECSSP_FLOW");
                  workItemList.add(workitem);
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
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                  workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                  workitem.setPiName(this.resultSet.getString("PINAME"));
                  workitem.setType("RGICL_ECSSUPERVISOR_FLOW");
                  workItemList.add(workitem);
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

         pendingWorkItems.add("DATABASE_ERROR");
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
      return workItemList;
   }
}
