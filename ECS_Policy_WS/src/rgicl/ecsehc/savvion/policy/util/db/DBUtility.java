package rgicl.ecsehc.savvion.policy.util.db;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.HcsLogObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;

public class DBUtility {
   private Connection connection = null;
   private PreparedStatement prepareStatement = null;
   private PreparedStatement paymentPrepareStatement = null;
   private PreparedStatement spPrepareStatement = null;
   private PreparedStatement supervisorPrepareStatement = null;
   private Statement statement = null;
   private ResultSet resultSet = null;
   private String query = null;
   private String paymentQuery = null;
   private String spQuery = null;
   private String supervisorQuery = null;
   private static Properties logProperties = null;
   private static Logger databaselog = null;
   private static Properties queryProperties = null;
   private DBManager dbManager = null;
   private BLClientUtil blc;
   private BLServer blserver;
   private static final String SBM_HOME = System.getProperty("sbm.home");
   private static final String QUERY_PROPERTIES_FILE;
   private static final String LOG_PROPERTIES_FILE;

   static {
      QUERY_PROPERTIES_FILE = SBM_HOME + "/conf/ECS_SAVVION_PROPERTIES.properties";
      LOG_PROPERTIES_FILE = SBM_HOME + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";

      try {
         logProperties = new Properties();
         logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
         queryProperties = new Properties();
         queryProperties.load(new FileInputStream(QUERY_PROPERTIES_FILE));
         PropertyConfigurator.configure(logProperties);
         databaselog = Logger.getLogger("dbutils_HCS");
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public DBUtility(String propertiesPath) {
   }

   public void createConnection() {
      this.dbManager = new DBManager(SBM_HOME);
      this.connection = this.dbManager.getDBConnection();
   }

   public String getProcessTemplateId(String processTemplateName) throws Exception {
      UtilClass uc = new UtilClass(SBM_HOME);
      long ptId = 0L;

      try {
         if (processTemplateName != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            Session session = this.blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
            ProcessTemplate processtemplate = this.blserver.getProcessTemplate(session, processTemplateName);
            ptId = processtemplate.getID();
            if (session != null) {
               this.blserver.disConnect(session);
            }
         }
      } catch (Exception var12) {
         String userid = "ECSAdmin";
         String pass = uc.getUserPasswordECS(userid);
         PService p = PService.self();
         pass = p.encrypt(pass);
         databaselog.info("ECS Savvion dbutility:: getProcesstemplateId userId " + userid + " encrypted " + pass);
         throw new Exception(var12);
      } finally {
         if (uc != null) {
            uc = null;
         }

      }

      return Long.valueOf(ptId).toString();
   }

   public String[] getCommonInboxTaskListUtility(String[] roleList, String[] locationList, String[] lobList, String TPAFlag) {
      databaselog.info("getCommonInboxTaskListUtility Service :: START");
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

         for(role = 0; role < roleList.length; ++role) {
            if (roleList[role] != null) {
               if (roleList[role].equals("RAMMGR")) {
                  ramuser = true;
               }

               paymentlocationslist.add(locationList[role]);
               userLocation = this.getUserLocation(roleList[role]);
               locationQuery = this.getLocationQuery(locationList, userLocation);
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
            }
         }

         this.dbManager = new DBManager(SBM_HOME);
         String[] paymentlocations = new String[paymentlocationslist.size()];
         paymentlocationslist.toArray(paymentlocations);
         paymentLocationQueryList = this.getPaymentLocationQuery(paymentlocations, "POLICYREGIONLOCATION");
         this.query = "SELECT  b.WORKITEM_NAME FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND (" + rolequerylist + ") AND TPAFlag='" + TPAFlag + "' AND (" + locationQueryList + ") AND claimLOB IN(" + claimLobQuery + ") ";
         this.paymentQuery = "SELECT b.WORKITEM_NAME FROM  RGICL_ECSPAYMENT_FLOW P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='RAMMGR' AND TPAFlag='" + TPAFlag + "' AND (" + paymentLocationQueryList + ")";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         if (!this.prepareStatement.execute()) {
            databaselog.info("getCommonInboxTaskListUtility Service :: Query Execution failed");
         } else {
            databaselog.info("getCommonInboxTaskListUtility Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getCommonInboxTaskListUtility Service :: No claims avaiable");
            } else {
               do {
                  pendingWorkItems.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         if (!this.paymentPrepareStatement.execute()) {
            databaselog.info("getCommonInboxTaskListUtility Service :: Payment Query Execution failed");
         } else {
            databaselog.info("getCommonInboxTaskListUtility Service :: Payment Query Executed Successfully");
            this.resultSet = this.paymentPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getCommonInboxTaskListUtility Service :: No payment claims avaiable");
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
         databaselog.error("getCommonInboxTaskListUtility :: " + var31.getMessage());

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
            databaselog.error("Error occured while closing the connection :: " + var30);
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
            databaselog.error("Error occured during closing prepare statement" + var29);
         }

      }

      databaselog.info("getCommonInboxTaskListUtility Service :: END");
      return pendingWorkItemsArray.split(",");
   }

   private String getUserLocation(String roleList) {
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

      return userLocation;
   }

   private String getLocationQuery(String[] locationList, String userLocation) {
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

      return locationquerylist;
   }

   private String getPaymentLocationQuery(String[] locationList, String userLocation) {
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

      return locationquerylist;
   }

   public String[] viewPendingClaimsListECS(String userLocationType, String userLocationId, String userTeamType) {
      databaselog.info("viewPendingClaimsECS Service :: START");
      String userLocation = null;
      ArrayList pendingWorkItems = new ArrayList();
      String pendingWorkItemsArray = null;

      try {
         this.dbManager = new DBManager(SBM_HOME);
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

         this.query = "SELECT b.WORKITEM_NAME FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R." + userLocation + "='" + userLocationId + "' AND B.WORKSTEP_ID IN (SELECT E.WORKSTEP_ID FROM ECS_ACTIVITY_TYPE E WHERE E.ACTIVITY_TYPE=?)";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.prepareStatement.setString(1, userTeamType);
         if (!this.prepareStatement.execute()) {
            databaselog.info("viewPendingClaimsECS Service :: Query Execution failed");
         } else {
            databaselog.info("viewPendingClaimsECS Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("viewPendingClaimsECS Service :: No pending claims avaiable");
               pendingWorkItems.add("PENDING_CLAIMS_EMPTY");
            } else {
               do {
                  pendingWorkItems.add(this.resultSet.getString("WORKITEM_NAME"));
               } while(this.resultSet.next());
            }
         }

         pendingWorkItemsArray = pendingWorkItems.toString();
      } catch (SQLException var17) {
         databaselog.error("Error occured in viewpendingclaimECS :: " + var17);
         pendingWorkItems.add("DATABASE_ERROR");
      } finally {
         try {
            this.resultSet.close();
            this.prepareStatement.close();
            this.connection.close();
         } catch (Exception var16) {
            databaselog.error("Error occured during closing prepare statement" + var16);
         }

      }

      databaselog.info("viewPendingClaimsECS Service :: END");
      return pendingWorkItemsArray.split(",");
   }

   public String insertECS_TASK_STATUS(String processInstanceName, String uploadDocsFlag, String reviseReserveFlag, String assignSPFlag, String performer, String claimRefNo) {
      if (!processInstanceName.equals("") && !uploadDocsFlag.equals("") && !reviseReserveFlag.equals("") && !assignSPFlag.equals("") && !performer.equals("") && !claimRefNo.equals("")) {
         try {
            this.dbManager = new DBManager(SBM_HOME);
            this.query = queryProperties.getProperty("insertECS_TASK_STATUS_Query");
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
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var19) {
            databaselog.error("Error occured in insertECS_TASK_STATUS :: " + var19.getMessage());

            try {
               this.connection.close();
            } catch (Exception var18) {
               var18.printStackTrace();
               databaselog.error("Error occured in insertECS_TASK_STATUS:: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var17) {
               var17.printStackTrace();
               databaselog.error("Exception occured during closing prepare statement" + var17);
            }

         }

         return "FAILURE";
      } else {
         databaselog.info("------------- INPUT VALUE IS NULL for insertECS_TASK_STATUS------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_SUB(int PID, String PIName, String parentPID, String claimID, String current_status, String performer, String closed_By) {
      databaselog.info("inserted ECS_COMPLETEALLPI_SUB :: START");
      if (PID != 0 && !PIName.equals("") && !parentPID.equals("") && !claimID.equals("") && !current_status.equals("") && !performer.equals("") && !closed_By.equals("") && !PIName.equals((Object)null) && !parentPID.equals((Object)null) && !claimID.equals((Object)null) && !current_status.equals((Object)null) && !performer.equals((Object)null) && !closed_By.equals((Object)null)) {
         try {
            this.dbManager = new DBManager(SBM_HOME);
            this.query = queryProperties.getProperty("insertECS_CompleteAllPi_SubQuery");
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
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var20) {
            databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: " + var20.getMessage());

            try {
               this.connection.close();
            } catch (Exception var19) {
               databaselog.error("Error occured in insertECS_COMPLETEALLPI_SUB :: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var18) {
               databaselog.error("Exception occured during closing prepare statement" + var18);
            }

         }

         return "FAILURE";
      } else {
         databaselog.info("------------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_SUB -------------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String insertECS_COMPLETEALLPI_PARENT(int PID, String PIName, String claimID, String current_status, String performer, String closed_By) {
      if (PID != 0 && !PIName.equals("") && !claimID.equals("") && !current_status.equals("") && !performer.equals("") && !closed_By.equals("")) {
         try {
            this.dbManager = new DBManager(SBM_HOME);
            this.query = queryProperties.getProperty("insertECS_CompleteAllPiParentQuery");
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
            this.connection.commit();
            return "SUCCESS";
         } catch (SQLException var19) {
            databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: " + var19.getMessage());

            try {
               this.connection.close();
            } catch (Exception var18) {
               databaselog.error("Error occured in insertECS_COMPLETEALLPI_PARENT :: while closing the connecton");
            }
         } finally {
            try {
               this.prepareStatement.close();
               this.connection.close();
            } catch (Exception var17) {
               databaselog.error("Exception occured during closing prepare statement" + var17);
            }

         }

         return "FAILURE";
      } else {
         databaselog.info("------- INPUT VALUE IS NULL for insertECS_COMPLETEALLPI_PARENT ------");
         return "INPUT_VALUE_IS_NULL";
      }
   }

   public String[] getClaimTaskListStatus(int PID) {
      ArrayList taskResult = new ArrayList();
      String taskStatus = null;
      this.dbManager = new DBManager(SBM_HOME);

      try {
         databaselog.info("getClaimTaskListStatus  :::: START");
         String query = "select DISTINCT(WORKSTEP_NAME),STATUS,END_TIME from WORKSTEP where process_instance_id=?  AND TYPE ='ATOMIC'";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(query);
         this.prepareStatement.setInt(1, PID);
         this.resultSet = this.prepareStatement.executeQuery();
         if (!this.resultSet.next()) {
            databaselog.info("getClaimTaskListStatus Service :: No tasks are  avaiable for the PID");
         } else {
            do {
               taskResult.add(this.resultSet.getString("WORKSTEP_NAME"));
               taskResult.add(this.resultSet.getString("STATUS"));
               taskResult.add(this.resultSet.getDate("END_TIME"));
            } while(this.resultSet.next());
         }

         if (taskResult.size() == 0) {
            taskResult.add("NOAVAILABLETASKA");
         }

         taskStatus = taskResult.toString();
      } catch (SQLException var16) {
         databaselog.error("Error occured in getClaimTaskListStatus :: " + var16);

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
            databaselog.error("Exception occured during closing resulsSet and prepare statement" + var14);
         }

      }

      taskStatus = taskResult.toString();
      return taskStatus.split(",");
   }

   public void closeConnection() {
      try {
         this.connection.close();
      } catch (Exception var2) {
         databaselog.error("Error occured while closing the Connection");
      }

   }

   public ArrayList getMyInboxTaskObjects(String user, String ptName) {
      databaselog.info("====================ECS DBUtility class:: getCommonInboxTaskObjects Service :: START=========================");
      ArrayList workItemList = new ArrayList();

      try {
         long startTime = System.currentTimeMillis();
         WorkItemObject workitem = null;
         this.dbManager = new DBManager(SBM_HOME);
         if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber,R.PMUser, R.PCHUser, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc( (( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 )-5), 24) HOURS, mod( trunc( (( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 )-30), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS , R.POLICYCHECKERREMARK, R.POLICYMAKERREMARK, R.POLICYMAKERUSER, R.POLICYCHECKERUSER FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + "DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc((( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 )-5), 24) HOURS, mod( trunc( (( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 )-30), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, R.MakerRemarks FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + "DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.isEDMCheckerAccepted, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, R.transactionType, R.isServiceEndorsement ,trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc(( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 )-5), 24) HOURS, mod( trunc(( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 )-30), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, R.EndorsementCheckerRemarks, R.EndorsementMakerRemarks, R.EnrollmentMakerRemarks FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P," + "DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc(( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 )-5), 24) HOURS, mod( trunc( (( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 )-30), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, R.PolicyCheckerRemarks, R.PolicyConfiguratorRemarks FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + "DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info("getCommonInboxTaskObjects Service :: Query Execution failed");
         } else {
            this.resultSet = this.prepareStatement.executeQuery();
            long quriedTime = System.currentTimeMillis();
            long queriedTime = quriedTime - startTime;
            databaselog.info("getCommonInboxTaskListUtility Service :: Query Executed Successfully and time taken " + queriedTime + " ms");
            if (!this.resultSet.next()) {
               databaselog.info("getCommonInboxTaskObjects Service :: No claims avaiable");
            } else {
               long mappingStarts = System.currentTimeMillis();

               do {
                  workitem = new WorkItemObject();
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

                  String dsv;
                  String[] arr;
                  if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
                     dsv = this.resultSet.getString("PolicyCheckerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyMakerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyMakerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerUser(arr);
                     }

                     dsv = this.resultSet.getString("PolicyCheckerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerUser(arr);
                     }

                     workitem.setPmuser(this.resultSet.getString("PMUser"));
                     workitem.setPchuser(this.resultSet.getString("PCHUser"));
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
                     dsv = this.resultSet.getString("PolicyCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyConfiguratorRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyConfiguratorRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
                     dsv = this.resultSet.getString("MakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
                     workitem.setIsEndorsementCheckerAccepted(this.resultSet.getString("isEDMCheckerAccepted"));
                     workitem.setTransactionType(this.resultSet.getString("transactiontype"));
                     workitem.setIsServiceEndorsement(this.resultSet.getString("isServiceEndorsement"));
                     dsv = this.resultSet.getString("EndorsementCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentCheckerRemarks(arr);
                     }

                     dsv = this.resultSet.getString("EndorsementMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEndorsementMakerRemarks(arr);
                     }

                     dsv = this.resultSet.getString("EnrollmentMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  workItemList.add(workitem);
               } while(this.resultSet.next());

               long mappingEnds = System.currentTimeMillis();
               long mappingTime = mappingEnds - mappingStarts;
               databaselog.info("getCommonInboxTaskListUtility Service :: WorkItem List created Successfully and time taken " + mappingTime + " ms");
            }
         }
      } catch (Exception var27) {
         var27.printStackTrace();
         databaselog.error("getCommonInboxTaskObjects :: " + var27);
         databaselog.error("getCommonInboxTaskObjects :: " + var27.getMessage());

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
         } catch (Exception var26) {
            var26.printStackTrace();
            databaselog.error("getCommonInboxTaskObjects:: " + var26);
         }
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
         } catch (Exception var25) {
            databaselog.error("getCommonInboxTaskObjects :Error occured during closing prepare statement" + var25);
         }

      }

      databaselog.info("========================ECS DBUtility class :: getCommonInboxTaskObjects Service :: END=========================");
      return workItemList;
   }

   public ArrayList getMyInboxTaskObjects(String userId) {
      databaselog.info("getMyInboxTaskObjects Service :: START");
      ArrayList workItemList = new ArrayList();

      try {
         WorkItemObject workitem = null;
         this.dbManager = new DBManager(SBM_HOME);
         this.query = "SELECT B.WORKITEM_NAME,B.PROCESS_INSTANCE_ID,R.CLAIMNUMBER,SUBSTR(b.WORKITEM_NAME,0,INSTR(b.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER,R.CMESCCOUNTER,R.BSMESCCOUNTER FROM  RGICL_ECS_FLOW R, BIZLOGIC_WORKITEM B WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.paymentQuery = "SELECT B.WORKITEM_NAME,P.PROCESS_INSTANCE_ID,P.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSPAYMENT_FLOW P, BIZLOGIC_WORKITEM B WHERE P.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.spQuery = "SELECT B.WORKITEM_NAME,SP.PROCESS_INSTANCE_ID,SP.CLAIMID ,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSSP_FLOW SP, BIZLOGIC_WORKITEM B WHERE SP.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.supervisorQuery = "SELECT B.WORKITEM_NAME,SV.PROCESS_INSTANCE_ID,SV.CLAIMNUMBER,SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME,B.PERFORMER FROM  RGICL_ECSSUPERVISOR_FLOW SV, BIZLOGIC_WORKITEM B WHERE SV.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + userId + "'";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.paymentPrepareStatement = this.connection.prepareStatement(this.paymentQuery);
         this.spPrepareStatement = this.connection.prepareStatement(this.spQuery);
         this.supervisorPrepareStatement = this.connection.prepareStatement(this.supervisorQuery);
         if (!this.prepareStatement.execute()) {
            databaselog.info("getMyInboxTaskObjects Service :: Query Execution failed");
         } else {
            databaselog.info("getMyInboxTaskObjects Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getMyInboxTaskObjects Service :: No claims avaiable");
            } else {
               do {
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
            databaselog.info("getMyInboxTaskObjects Service :: Payment Query Execution failed");
         } else {
            databaselog.info("getMyInboxTaskObjects Service :: Payment Query Executed Successfully");
            this.resultSet = this.paymentPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getMyInboxTaskObjects Service :: No payment claims avaiable");
            } else {
               do {
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
            databaselog.info("getMyInboxTaskObjects Service :: ServiceProvidor Query Execution failed");
         } else {
            databaselog.info("getMyInboxTaskObjects Service :: ServiceProvidor Query Executed Successfully");
            this.resultSet = this.spPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getMyInboxTaskObjects Service :: No ServiceProvidor claims avaiable");
            } else {
               do {
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
            databaselog.info("getMyInboxTaskObjects Service :: Supervisor Query Execution failed");
         } else {
            databaselog.info("getMyInboxTaskObjects Service :: Supervisor Query Executed Successfully");
            this.resultSet = this.supervisorPrepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getMyInboxTaskObjects Service :: No Supervisor claims avaiable");
            } else {
               do {
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
      } catch (Exception var15) {
         databaselog.error("getMyInboxTaskObjects :: " + var15.getMessage());

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
         } catch (Exception var14) {
            databaselog.error("getMyInboxTaskObjects:Error occured while closing the connection :: " + var14);
         }
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
         } catch (Exception var13) {
            databaselog.error("Error occured during closing prepare statement" + var13);
         }

      }

      databaselog.info("getMyInboxTaskObjects Service :: END");
      return workItemList;
   }

   public ArrayList getAssignedTasks(String user, String ptName) {
      String methodName = "getAssignedTasks";
      databaselog.info(methodName + "  Service :: START");
      ArrayList workItemList = new ArrayList();

      try {
         WorkItemObject workitem = null;
         this.dbManager = new DBManager(SBM_HOME);
         if (ptName.contains("RGICL_ECS_RR_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.contains("RGICL_ECS_NETWORK_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         if (ptName.contains("RGICL_ECS_PRODUCT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PERFORMER='" + user + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info("getAssignedTasks Service :: Query Execution failed");
         } else {
            databaselog.info("getAssignedTasks Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info("getCommonInboxTaskObjects Service :: No claims avaiable");
            } else {
               do {
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
      } catch (Exception var17) {
         var17.printStackTrace();
         databaselog.error(methodName + " :: " + var17.getMessage());

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
         } catch (Exception var16) {
            databaselog.error(methodName + ":: " + var16.getMessage());
         }
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
         } catch (Exception var15) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var15);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return workItemList;
   }

   public ArrayList getNextAvailableTaskObjects(String processInstanceName, String nextWorkItemCaseStatus, String currWorkItemName, String needsInvestigation, String supervisorId, String isFromSR, String needsPMT, String PMTHardStop, String needsSAPPR, String hasAllowance, String hasAccounts, String isExistingApprover) {
      String methodName = "getNextAvailableTaskObjects";
      databaselog.info("-----------" + methodName + "  Service :: START for workitem " + currWorkItemName + " nextWorkItemCaseStatus = " + nextWorkItemCaseStatus + "----------");
      ArrayList workItemList = new ArrayList();
      String nextworkItemName = null;

      try {
         WorkItemObject workitem = null;
         this.dbManager = new DBManager(SBM_HOME);
         this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME, b.workstep_name AS WORKSTEPNAME, B.PERFORMER FROM BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE P.PROCESS_INSTANCE_NAME='" + processInstanceName + "' AND B.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  nextworkItemName = this.resultSet.getString("WORKITEM_NAME");
                  if (currWorkItemName != null && processInstanceName.contains("RGICL_ECS_AL_FLOW") && (currWorkItemName.contains("Query") && (nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT")) || nextworkItemName.contains("Dummy") || currWorkItemName.contains("Investigator") || currWorkItemName.contains("Deviator") && (nextworkItemName.contains("Deviator") || nextworkItemName.contains("AmountApp") || nextworkItemName.contains("VerticalHead") || nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT")) || currWorkItemName.contains("AmountApp") && (nextworkItemName.contains("Deviator") || nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT")) || currWorkItemName.contains("PMT") && !nextworkItemName.contains("PMT") || currWorkItemName.contains("VerticalHead") && (nextworkItemName.contains("Deviator") || nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT") || nextworkItemName.contains("EFT")) || currWorkItemName.contains("Tag") && (nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT")) || currWorkItemName.contains("Executive") && (nextworkItemName.contains("Investigator") || nextworkItemName.contains("PMT")) || currWorkItemName.contains("Approve") && nextworkItemName.contains("Investigator") && (needsInvestigation == null || needsInvestigation.equals("No")) || currWorkItemName.contains("Approve") && nextworkItemName.contains("PMT") && (needsPMT == null || needsPMT.equals("No")))) {
                     databaselog.info(methodName + " Service :: Not adding this task :: " + nextworkItemName + " to nextTaskList as this task is a parallel task and it has been returned earlier.");
                  } else if ((currWorkItemName == null || !processInstanceName.contains("RGICL_ECS_CL_FLOW") && !processInstanceName.contains("RGICL_ECS_OPD_CL_FLOW") || (!currWorkItemName.contains("Query") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("EFT") && !nextworkItemName.contains("PMT")) && !nextworkItemName.contains("SRQueue") && (!currWorkItemName.contains("Accounts") || !nextworkItemName.contains("PaymentRequestor") || needsSAPPR == null || !needsSAPPR.equals("No")) && (!currWorkItemName.contains("Investigator") || nextworkItemName.contains("CLApprover") && isExistingApprover.equals("No")) && (!currWorkItemName.contains("Deviator") || !nextworkItemName.contains("Deviator") && !nextworkItemName.contains("AmountApp") && !nextworkItemName.contains("VerticalHead") && !nextworkItemName.contains("EFT") && !nextworkItemName.contains("Investigator") && (!nextworkItemName.contains("PMT") || PMTHardStop.equals("Yes")) && !nextworkItemName.contains("ApproverDummy")) && (!currWorkItemName.contains("AmountApp") || !nextworkItemName.contains("Deviator") && !nextworkItemName.contains("EFT") && !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("ApproverDummy") && (!nextworkItemName.contains("PMT") || PMTHardStop.equals("Yes"))) && (!currWorkItemName.contains("VerticalHead") || !nextworkItemName.contains("Deviator") && !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("ApproverDummy") && !nextworkItemName.contains("EFT") && (!nextworkItemName.contains("PMT") || PMTHardStop.equals("Yes"))) && (!currWorkItemName.contains("Executive") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT")) && (!currWorkItemName.contains("EditorHold") || !nextworkItemName.contains("EFT") && !nextworkItemName.contains("EditorHold")) && (!currWorkItemName.contains("Approve") || !nextworkItemName.contains("EFT") && !nextworkItemName.contains("ApproverDummy") && !nextworkItemName.contains("ApproverHold")) && (!currWorkItemName.contains("Approve") || !nextworkItemName.contains("Investigator") || needsInvestigation != null && !needsInvestigation.equals("No")) && (!currWorkItemName.contains("Approve") || !nextworkItemName.contains("PMT") || needsPMT != null && !needsPMT.equals("No")) && (!currWorkItemName.contains("Editor") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT")) && (!currWorkItemName.contains("QualityCheck") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT") && !nextworkItemName.contains("EFT")) && (!currWorkItemName.contains("Payment") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT") && !nextworkItemName.contains("EFT") && (!nextworkItemName.contains("CLAccounts") || hasAccounts != null && !hasAccounts.equals("No")) && (!nextworkItemName.contains("Allowance") || hasAllowance != null && !hasAllowance.equals("No"))) && (!currWorkItemName.contains("Account") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT") && !nextworkItemName.contains("AllowanceAccounts")) && (!currWorkItemName.contains("EFT") || nextworkItemName.contains("QualityCheck") || nextworkItemName.contains("PaymentRequest") || nextworkItemName.contains("Account") || nextworkItemName.contains("Executive") || nextworkItemName.contains("OPDCLEditor")) && (!currWorkItemName.contains("PMT") || nextworkItemName.contains("PMT") || nextworkItemName.contains("CLApprover")) && (!currWorkItemName.contains("AllowanceAccounts") || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT") && !nextworkItemName.contains("Account"))) && (isFromSR == null || isFromSR != "Yes" || !nextworkItemName.contains("Investigator") && !nextworkItemName.contains("PMT"))) {
                     workitem = new WorkItemObject();
                     workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                     if (workitem.getName().contains("RelationshipManager") && processInstanceName.contains("NETWORK_FLOW") && this.resultSet.getString("PERFORMER").contains("RelationshipManager")) {
                        workitem.setPerformer("RMApprover");
                     } else {
                        workitem.setPerformer(this.resultSet.getString("PERFORMER"));
                     }

                     if (currWorkItemName != null && currWorkItemName.contains("Requester") && processInstanceName.contains("RR_FLOW") && supervisorId != null) {
                        workitem.setQueue("RRMaker");
                     } else if (currWorkItemName != null && this.resultSet.getString("WORKITEM_NAME").contains("AllowanceAccounts") && processInstanceName.contains("CL_FLOW")) {
                        workitem.setQueue("CLAccounts");
                     } else if (workitem.getName().contains("RelationshipManager") && processInstanceName.contains("NETWORK_FLOW")) {
                        workitem.setQueue("RMApprover");
                     } else {
                        workitem.setQueue(this.resultSet.getString("WORKSTEPNAME"));
                     }

                     workitem.setPId(this.resultSet.getString("PROCESS_INSTANCE_ID"));
                     workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
                     workitem.setPiName(this.resultSet.getString("PINAME"));
                     workitem.setCaseStatus(nextWorkItemCaseStatus);
                     if (workitem.getWorkStepName().contains("Investigator") || workitem.getWorkStepName().contains("PMT") || workitem.getWorkStepName().contains("CLEFTChecker") || workitem.getWorkStepName().contains("EditorHold")) {
                        workitem.setCaseStatus("New");
                     }

                     long piid = this.resultSet.getLong("PROCESS_INSTANCE_ID");
                     workItemList.add(workitem);
                  } else {
                     databaselog.info(methodName + " Service :: Not adding this task :: " + nextworkItemName + " to nextTaskList as this task is a parallel task and it has been returned earlier.");
                  }
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var29) {
         var29.printStackTrace();
         databaselog.error(methodName + " :: " + var29);
         databaselog.error(methodName + " :: " + var29.getMessage());

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
         } catch (Exception var28) {
            var28.printStackTrace();
            databaselog.error(methodName + ":: " + var28);
         }
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
         } catch (Exception var27) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var27);
         }

      }

      databaselog.info("-------------" + methodName + " Service :: END-----------");
      return workItemList;
   }

   public String[] getNextAvailableTaskNames(String processInstanceName) {
      String methodName = "getNextAvailableTaskNames";
      databaselog.info("**********ECS service " + methodName + "  :: START************");
      String[] workItemNames = new String[50];
      int index = 0;

      try {
         this.dbManager = new DBManager(SBM_HOME);
         this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER FROM BIZLOGIC_WORKITEM B, bizlogic_processinstance P, DUAL WHERE P.PROCESS_INSTANCE_NAME='" + processInstanceName + "' AND B.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  workItemNames[index] = this.resultSet.getString("WORKITEM_NAME");
                  ++index;
               } while(this.resultSet.next());
            }

            databaselog.info("ECS Service " + methodName + " workItemNames array size is " + index);
         }
      } catch (Exception var17) {
         databaselog.error(methodName + " :: " + var17.getMessage());

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
         } catch (Exception var16) {
            databaselog.error(methodName + ":: " + var16);
         }
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
         } catch (Exception var15) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var15);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return workItemNames;
   }

   public String getPINameByTrannsactionId(String transactionId, String sRType, String processInstanceName) {
      String methodName = "getPINameByTrannsactionId";
      databaselog.info(methodName + "  Service :: START");
      String piName = "";

      try {
         this.dbManager = new DBManager(SBM_HOME);
         if (sRType != null && sRType.contains("AL")) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME from " + processInstanceName + " A, PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = A.PROCESS_INSTANCE_ID and A.TRANSACTIONID = '" + transactionId + "'";
         }

         if (sRType != null && sRType.contains("CL")) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME from " + processInstanceName + " A, PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = A.PROCESS_INSTANCE_ID and A.TRANSACTIONID = '" + transactionId + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               piName = this.resultSet.getString("PROCESS_INSTANCE_NAME");
               databaselog.info(methodName + "  Service :: piName is " + piName);
            }
         }
      } catch (Exception var18) {
         databaselog.error(methodName + " :: " + var18.getMessage());

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
            databaselog.error(methodName + ":: " + var17);
         }
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
         } catch (Exception var16) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var16);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return piName;
   }

   public String[] getQueryQueueTaskName(String alInwardNo, String clInwardNo, String piName) {
      String methodName = "getQueryQueueTaskName";
      databaselog.info("========================================" + methodName + "  Service :: START==================================");
      String[] result = new String[2];
      String ptName = piName.split("#")[0];
      databaselog.info("Input values :: alInwardNo is " + alInwardNo + " ::: clInwardNo is " + clInwardNo);

      try {
         this.dbManager = new DBManager(SBM_HOME);
         if (alInwardNo != null && !alInwardNo.equals("")) {
            this.query = "select distinct W.WORKITEM_NAME, P.PROCESS_INSTANCE_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P, BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " DS where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID and P.PROCESS_INSTANCE_ID = DS.PROCESS_INSTANCE_ID" + " and W.WORKSTEP_NAME ='ALQueryQueue' and  DS.ALINWARDNO  = '" + alInwardNo + "'";
         }

         if (clInwardNo != null && !clInwardNo.equals("")) {
            this.query = "select distinct W.WORKITEM_NAME, P.PROCESS_INSTANCE_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P, BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " DS where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID = DS.PROCESS_INSTANCE_ID " + " and W.WORKSTEP_NAME ='CLQueryQueue' and DS.CLINWARDNO  = '" + clInwardNo + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  result[0] = this.resultSet.getString("PROCESS_INSTANCE_NAME");
                  result[1] = this.resultSet.getString("WORKITEM_NAME");
                  databaselog.info("ECS :: " + methodName + "  Service :: piName is " + result[0] + " ::: WorkItem Name is " + result[1]);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var19) {
         databaselog.error(methodName + " :: " + var19.getMessage());

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
            var18.printStackTrace();
            databaselog.error(methodName + ":: " + var18);
         }
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
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var17);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return result;
   }

   public String[] getEditorHoldQueueTaskName(String clInwardNo, String piName) {
      String methodName = "getQueryQueueTaskName";
      databaselog.info("========================================" + methodName + "  Service :: START==================================");
      String[] result = new String[6];
      String ptName = piName.split("#")[0];
      databaselog.info("Input value :: clInwardNo is " + clInwardNo + " ptName: " + ptName);

      try {
         this.dbManager = new DBManager(SBM_HOME);
         if (clInwardNo != null && !clInwardNo.equals("")) {
            this.query = "select distinct W.WORKITEM_NAME, P.PROCESS_INSTANCE_NAME, DS.HASPMTHOLD, DS.PMTHARDSTOP, DS.HASPMTOPENTASK, DS.HASINVOPENTASK from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P, BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " DS where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID = DS.PROCESS_INSTANCE_ID" + " and W.WORKSTEP_NAME ='EditorHoldQueue' and DS.CLINWARDNO  = '" + clInwardNo + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  result[0] = this.resultSet.getString("PROCESS_INSTANCE_NAME");
                  result[1] = this.resultSet.getString("WORKITEM_NAME");
                  result[2] = this.resultSet.getString("HASPMTHOLD");
                  result[3] = this.resultSet.getString("PMTHARDSTOP");
                  result[4] = this.resultSet.getString("HASPMTOPENTASK");
                  result[5] = this.resultSet.getString("HASINVOPENTASK");
                  databaselog.info("ECS :: " + methodName + "  Service :: piName is " + result[0] + " ::: WorkItem Name is " + result[1] + " ::: HasPMTHold is " + result[2] + " ::: PMTHardStop is " + result[3] + " ::: HasPMTOpenTask is " + result[4] + " ::: HasInvOpenTask is " + result[5]);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var18) {
         databaselog.error(methodName + " :: " + var18.getMessage());

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
            var17.printStackTrace();
            databaselog.error(methodName + ":: " + var17);
         }
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
         } catch (Exception var16) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var16);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return result;
   }

   public String[] getDataSlotValues(String[] alInwardNo, String[] clInwardNo, String sRType) {
      String methodName = "getDataSlotValues";
      databaselog.info("===================" + methodName + "  Service :: START==============");
      String[] result = new String[6];
      databaselog.info("Input values :: alInwardNo is " + alInwardNo + " ::: clInwardNo is " + clInwardNo);

      try {
         this.dbManager = new DBManager(SBM_HOME);
         if (sRType.contains("AL")) {
            this.query = "select ALCASETYPE, CLOSURESTEP, ALINWARDNO, ALNO, ISALAPPROVERACCEPTED, ISQUERIEDAL from RGICL_ECS_AL_FLOW where ALINWARDNO  = '" + alInwardNo[0] + "'";
         }

         if (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT")) {
            this.query = "select CLCASETYPE, AUTORELEASE, CLOSURESTEP, CLINWARDNO, CLNO from RGICL_ECS_CL_FLOW where CLINWARDNO = '" + clInwardNo[0] + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  if (sRType.contains("AL")) {
                     result[0] = this.resultSet.getString("ALCASETYPE");
                     result[1] = this.resultSet.getString("CLOSURESTEP");
                     result[2] = this.resultSet.getString("ALINWARDNO");
                     result[3] = this.resultSet.getString("ALNO");
                     result[4] = this.resultSet.getString("ISALAPPROVERACCEPTED");
                     result[5] = this.resultSet.getString("ISQUERIEDAL");
                     databaselog.info("ECS :: " + methodName + "  Service :: ALCASETYPE is " + result[0] + " CLOSURESTEP is " + result[1] + " ALINWARDNO is " + result[2] + " ALNO is " + result[3] + " ISALAPPROVERACCEPTED is " + result[4] + " ISQUERIEDAL is " + result[5]);
                  }

                  if (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT")) {
                     result[0] = this.resultSet.getString("CLCASETYPE");
                     result[1] = this.resultSet.getString("AUTORELEASE");
                     result[2] = this.resultSet.getString("CLOSURESTEP");
                     result[3] = this.resultSet.getString("CLINWARDNO");
                     result[4] = this.resultSet.getString("CLNO");
                     databaselog.info("ECS :: " + methodName + "  Service :: CLCASETYPE is " + result[0] + "AUTORELEASE ::" + result[1] + " CLOSURESTEP is " + result[2] + " CLINWARDNO is " + result[3] + " CLNO is " + result[4]);
                  }
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var18) {
         databaselog.error(methodName + " :: " + var18.getMessage());

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
            databaselog.error(methodName + ":: " + var17);
         }
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
         } catch (Exception var16) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var16);
         }

      }

      databaselog.info("===================" + methodName + " Service :: ENDS===================");
      return result;
   }

   public ArrayList getPINameByInwardNo(String[] alInwardNo, String[] clInwardNo, String sRType) {
      String methodName = "getPINameByInwardNo";
      databaselog.info("================== " + methodName + "  Service :: START====================");
      ArrayList workItemList = new ArrayList();
      WorkItemObject workitem = null;
      String inwardNo = "";

      try {
         this.dbManager = new DBManager(SBM_HOME);
         int i;
         if (sRType != null && sRType.contains("AL")) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME, W.WORKITEM_NAME, W.WORKSTEP_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from RGICL_ECS_AL_FLOW where ALINWARDNO  in (";

            for(i = 0; i < alInwardNo.length; ++i) {
               if (inwardNo.equals("")) {
                  inwardNo = "'" + alInwardNo[i] + "'";
               } else {
                  inwardNo = inwardNo + ",'" + alInwardNo[i] + "'";
               }
            }

            this.query = this.query + inwardNo + "))";
         }

         if (sRType != null && (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT") || sRType.contains("Recovery"))) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME, W.WORKITEM_NAME, W.WORKSTEP_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from RGICL_ECS_CL_FLOW where CLINWARDNO  in (";

            for(i = 0; i < clInwardNo.length; ++i) {
               if (inwardNo.equals("")) {
                  inwardNo = "'" + clInwardNo[i] + "'";
               } else {
                  inwardNo = inwardNo + ",'" + clInwardNo[i] + "'";
               }
            }

            this.query = this.query + inwardNo + "))";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPiName(this.resultSet.getString("PROCESS_INSTANCE_NAME"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEP_NAME"));
                  workItemList.add(workitem);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var20) {
         var20.printStackTrace();
         databaselog.error(methodName + " :: " + var20);
         databaselog.error(methodName + " :: " + var20.getMessage());

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
         } catch (Exception var19) {
            var19.printStackTrace();
            databaselog.error(methodName + ":: " + var19);
         }
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
         } catch (Exception var18) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var18);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return workItemList;
   }

   public String[] getDataSlotValuesByInstanceId(String[] piNamesList, String sRType) {
      String methodName = "getDataSlotValuesByInstanceId";
      databaselog.info("===================" + methodName + "  Service :: START==============");
      String ptName = piNamesList[0].split("#")[0];
      String processInstanceId = piNamesList[0].split("#")[1];
      String[] result = new String[8];
      databaselog.info("Input values :: piNamesList is " + piNamesList + " ::: sRType is " + sRType);

      try {
         this.dbManager = new DBManager(SBM_HOME);
         if (sRType.contains("AL")) {
            this.query = "select ALCASETYPE, CLOSURESTEP, ALINWARDNO, ALNO, ISALAPPROVERACCEPTED, ISQUERIEDAL from  " + ptName + " A where PROCESS_INSTANCE_ID  = '" + processInstanceId + "'";
         }

         if (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT")) {
            if (ptName.contains("RGICL_ECS_OPD_CL_FLOW")) {
               this.query = "select CLCASETYPE, AUTORELEASE, CLOSURESTEP, CLINWARDNO, CLNO, CLAIMTYPE, CLAIMSOURCE from " + ptName + " A where PROCESS_INSTANCE_ID = '" + processInstanceId + "'";
            } else if (ptName.contains("RGICL_ECS_TPA_CL_FLOW")) {
               this.query = "select ISPAYMENTTPA, CLNO from " + ptName + " A where PROCESS_INSTANCE_ID = '" + processInstanceId + "'";
            } else {
               this.query = "select CLCASETYPE, AUTORELEASE, CLOSURESTEP, CLINWARDNO, CLNO from " + ptName + " A where PROCESS_INSTANCE_ID = '" + processInstanceId + "'";
            }
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         databaselog.debug(methodName + " Service :: " + this.prepareStatement);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            databaselog.info("resultSet " + this.resultSet.toString());
            databaselog.info("resultSet " + this.resultSet + " resultSet.size " + this.resultSet.getFetchSize());
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  if (sRType.contains("AL")) {
                     result[0] = this.resultSet.getString("ALCASETYPE");
                     result[1] = this.resultSet.getString("CLOSURESTEP");
                     result[2] = this.resultSet.getString("ALINWARDNO");
                     result[3] = this.resultSet.getString("ALNO");
                     result[4] = this.resultSet.getString("ISALAPPROVERACCEPTED");
                     result[5] = this.resultSet.getString("ISQUERIEDAL");
                     databaselog.info("ECS :: " + methodName + "  Service :: ALCASETYPE is " + result[0] + " CLOSURESTEP is " + result[1] + " ALINWARDNO is " + result[2] + " ALNO is " + result[3] + " ISALAPPROVERACCEPTED is " + result[4] + " ISQUERIEDAL is " + result[5]);
                  }

                  if (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT")) {
                     if (ptName.contains("RGICL_ECS_TPA_CL_FLOW")) {
                        result[0] = this.resultSet.getString("ISPAYMENTTPA");
                        result[4] = this.resultSet.getString("CLNO");
                     } else {
                        result[0] = this.resultSet.getString("CLCASETYPE");
                        result[1] = this.resultSet.getString("AUTORELEASE");
                        result[2] = this.resultSet.getString("CLOSURESTEP");
                        result[3] = this.resultSet.getString("CLINWARDNO");
                        result[4] = this.resultSet.getString("CLNO");
                        if (ptName.contains("RGICL_ECS_OPD_CL_FLOW")) {
                           result[5] = this.resultSet.getString("CLAIMSOURCE");
                           result[6] = this.resultSet.getString("CLAIMTYPE");
                        }

                        databaselog.info("ECS :: " + methodName + "  Service :: CLCASETYPE is " + result[0] + "AUTORELEASE ::" + result[1] + " CLOSURESTEP is " + result[2] + " CLINWARDNO is " + result[3] + " CLNO is " + result[4]);
                     }
                  }
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         databaselog.error(methodName + " :: " + var19);
         databaselog.error(methodName + " :: " + var19.getMessage());

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
            var18.printStackTrace();
            databaselog.error(methodName + ":: " + var18);
         }
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
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var17);
         }

      }

      databaselog.info("===================" + methodName + " Service :: ENDS===================");
      return result;
   }

   public ArrayList getNextTaskByPIName(String[] processInstanceName) {
      String methodName = "getNextAvailableTaskNames";
      databaselog.info("**********ECS service " + methodName + "  :: START************");
      ArrayList workItemList = new ArrayList();
      WorkItemObject workitem = null;
      int index = 0;
      String piName = "";

      try {
         this.dbManager = new DBManager(SBM_HOME);
         this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME ,B.WORKSTEP_NAME , B.PERFORMER FROM BIZLOGIC_WORKITEM B, BIZLOGIC_PROCESSINSTANCE P, DUAL WHERE P.PROCESS_INSTANCE_NAME IN (";

         for(int i = 0; i < processInstanceName.length; ++i) {
            if (piName.equals("")) {
               piName = "'" + processInstanceName[i] + "'";
            } else {
               piName = piName + ",'" + processInstanceName[i] + "'";
            }
         }

         this.query = this.query + piName + ") AND B.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID";
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         databaselog.debug(methodName + " Service :: " + this.prepareStatement);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPiName(this.resultSet.getString("PROCESS_INSTANCE_NAME"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEP_NAME"));
                  workItemList.add(workitem);
               } while(this.resultSet.next());
            }

            databaselog.info("ECS Service " + methodName + " workItemNames array size is " + index);
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         databaselog.error(methodName + " :: " + var19);
         databaselog.error(methodName + " :: " + var19.getMessage());

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
            var18.printStackTrace();
            databaselog.error(methodName + ":: " + var18);
         }
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
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var17);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return workItemList;
   }

   public ArrayList getPINameByInwardNoDummy(String[] alInwardNo, String[] clInwardNo, String sRType) {
      String methodName = "getPINameByInwardNoDummy";
      databaselog.info("================== " + methodName + "  Service :: START====================");
      ArrayList workItemList = new ArrayList();
      WorkItemObject workitem = null;
      String inwardNo = "";

      try {
         this.dbManager = new DBManager(SBM_HOME);
         int i;
         if (sRType != null && sRType.contains("AL")) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME, W.WORKITEM_NAME, W.WORKSTEP_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from AL_FLOW where ALINWARDNO  in (";

            for(i = 0; i < alInwardNo.length; ++i) {
               if (inwardNo.equals("")) {
                  inwardNo = "'" + alInwardNo[i] + "'";
               } else {
                  inwardNo = inwardNo + ",'" + alInwardNo[i] + "'";
               }
            }

            this.query = this.query + inwardNo + "))";
         }

         if (sRType != null && (sRType.contains("CL") || sRType.contains("Cheque") || sRType.contains("EFT") || sRType.contains("Recovery"))) {
            this.query = "SELECT P.PROCESS_INSTANCE_NAME, W.WORKITEM_NAME, W.WORKSTEP_NAME from BIZLOGIC_WORKITEM W, BIZLOGIC_PROCESSINSTANCE P where P.PROCESS_INSTANCE_ID = W.PROCESS_INSTANCE_ID  and P.PROCESS_INSTANCE_ID in (select PROCESS_INSTANCE_ID from CL_FLOW where CLINWARDNO  in (";

            for(i = 0; i < clInwardNo.length; ++i) {
               if (inwardNo.equals("")) {
                  inwardNo = "'" + clInwardNo[i] + "'";
               } else {
                  inwardNo = inwardNo + ",'" + clInwardNo[i] + "'";
               }
            }

            this.query = this.query + inwardNo + "))";
         }

         databaselog.info("query = " + this.query);
         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         databaselog.debug(methodName + " Service :: " + this.prepareStatement);
         if (!this.prepareStatement.execute()) {
            databaselog.info(methodName + " Service :: Query Execution failed");
         } else {
            databaselog.info(methodName + " Service :: Query Executed Successfully");
            this.resultSet = this.prepareStatement.executeQuery();
            if (!this.resultSet.next()) {
               databaselog.info(methodName + " Service :: No tasks avaiable");
            } else {
               do {
                  workitem = new WorkItemObject();
                  workitem.setName(this.resultSet.getString("WORKITEM_NAME"));
                  workitem.setPiName(this.resultSet.getString("PROCESS_INSTANCE_NAME"));
                  workitem.setWorkStepName(this.resultSet.getString("WORKSTEP_NAME"));
                  workItemList.add(workitem);
               } while(this.resultSet.next());
            }
         }
      } catch (Exception var20) {
         var20.printStackTrace();
         databaselog.error(methodName + " :: " + var20);
         databaselog.error(methodName + " :: " + var20.getMessage());

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
         } catch (Exception var19) {
            var19.printStackTrace();
            databaselog.error(methodName + ":: " + var19);
         }
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
         } catch (Exception var18) {
            databaselog.error(methodName + " :Error occured during closing prepare statement" + var18);
         }

      }

      databaselog.info(methodName + " Service :: END");
      return workItemList;
   }

   public ArrayList findDuplicateEntryUsingPolicyNumber(String policyNumber, String ptName) {
      databaselog.info("====================ECS DBUtility class:: getCommonInboxTaskObjects Service :: START=========================");
      ArrayList workItemList = new ArrayList();

      try {
         long startTime = System.currentTimeMillis();
         WorkItemObject workitem = null;
         this.dbManager = new DBManager(SBM_HOME);
         if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber,R.PMUser, R.PCHUser, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS , F.PolicyCheckerRemark, F.PolicyMakerRemark, F.PolicyMakerUser, F.PolicyCheckerUser FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + ptName + " F, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND F.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND R.POLICYNUMBER='" + policyNumber + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, F.MakerRemarks FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + ptName + " F, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND F.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND R.POLICYNUMBER='" + policyNumber + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER, R.PolicyNumber, R.isEDMCheckerAccepted, R.PolicyStatus, R.StartDate, R.EndDate, R.Branch, R.City, R.PolicyName, R.ProposalNumber, R.transactionType ,trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, F.EndorsementCheckerRemarks, F.EndorsementMakerRemarks, F.EnrollmentMakerRemarks FROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P," + ptName + " F, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND  F.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND R.POLICYNUMBER='" + policyNumber + "'";
         }

         if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, P.PROCESS_INSTANCE_NAME AS PINAME,b.workstep_name AS WORKSTEPNAME, B.PERFORMER,R.PolicyNumber,R.PolicyStatus,  R.StartDate, R.EndDate,R.Branch,R.City, R.isPolicyCheckerAccepted,R.PolicyName,R.ProposalNumber, trunc(SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000))) days, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 24 ), 24) HOURS, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000)) ) * 1440 ), 60 ) MINUTES, mod( trunc( ( SYSDATE-(TO_DATE('1970-01-01','YYYY-MM-DD') + (B.starttime/ 86400000) )) * 86400 ), 60 ) SECONDS, F.PolicyCheckerRemarks,F.PolicyConfiguratorRemarks\tFROM  BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, bizlogic_processinstance P, " + ptName + " F, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND F.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND R.PROCESS_INSTANCE_ID=P.PROCESS_INSTANCE_ID AND R.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND B.PROCESS_TEMPLATE_ID=P.PROCESS_TEMPLATE_ID AND R.POLICYNUMBER='" + policyNumber + "'";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         if (!this.prepareStatement.execute()) {
            databaselog.info("getCommonInboxTaskObjects Service :: Query Execution failed");
         } else {
            this.resultSet = this.prepareStatement.executeQuery();
            long quriedTime = System.currentTimeMillis();
            long queriedTime = quriedTime - startTime;
            databaselog.info("getCommonInboxTaskListUtility Service :: Query Executed Successfully and time taken " + queriedTime + " ms");
            if (!this.resultSet.next()) {
               databaselog.info("getCommonInboxTaskObjects Service :: No claims avaiable");
            } else {
               long mappingStarts = System.currentTimeMillis();

               do {
                  workitem = new WorkItemObject();
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

                  String dsv;
                  String[] arr;
                  if (ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW")) {
                     dsv = this.resultSet.getString("PolicyCheckerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyMakerRemark");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyMakerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyMakerUser(arr);
                     }

                     dsv = this.resultSet.getString("PolicyCheckerUser");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerUser(arr);
                     }

                     workitem.setPmuser(this.resultSet.getString("PMUser"));
                     workitem.setPchuser(this.resultSet.getString("PCHUser"));
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
                     dsv = this.resultSet.getString("PolicyCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyCheckerRemark(arr);
                     }

                     dsv = this.resultSet.getString("PolicyConfiguratorRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setPolicyConfiguratorRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENROLLMENT_FLOW")) {
                     dsv = this.resultSet.getString("MakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  if (ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
                     workitem.setIsEndorsementCheckerAccepted(this.resultSet.getString("isEDMCheckerAccepted"));
                     workitem.setTransactionType(this.resultSet.getString("transactiontype"));
                     dsv = this.resultSet.getString("EndorsementCheckerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentCheckerRemarks(arr);
                     }

                     dsv = this.resultSet.getString("EndorsementMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEndorsementMakerRemarks(arr);
                     }

                     dsv = this.resultSet.getString("EnrollmentMakerRemarks");
                     if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                        arr = dsv.split("|");
                        workitem.setEnrollmentMakerRemarks(arr);
                     }
                  }

                  workItemList.add(workitem);
               } while(this.resultSet.next());

               long mappingEnds = System.currentTimeMillis();
               long mappingTime = mappingEnds - mappingStarts;
               databaselog.info("getCommonInboxTaskListUtility Service :: WorkItem List created Successfully and time taken " + mappingTime + " ms");
            }
         }
      } catch (Exception var27) {
         var27.printStackTrace();
         databaselog.error("getCommonInboxTaskObjects :: " + var27);
         databaselog.error("getCommonInboxTaskObjects :: " + var27.getMessage());

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
         } catch (Exception var26) {
            var26.printStackTrace();
            databaselog.error("getCommonInboxTaskObjects:: " + var26);
         }
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
         } catch (Exception var25) {
            databaselog.error("getCommonInboxTaskObjects :Error occured during closing prepare statement" + var25);
         }

      }

      databaselog.info("========================ECS DBUtility class :: getCommonInboxTaskObjects Service :: END=========================");
      return workItemList;
   }

   public String getWorkitemStatus(String workstepName, long piid) {
      String status = null;
      ResultSet resultSet = null;
      CallableStatement cstmt = null;
      String bstatus = null;
      String bs_status = null;
      String stat1 = null;
      String stat2 = null;
      Connection connection = null;

      try {
         this.dbManager = new DBManager(SBM_HOME);
         connection = this.dbManager.getDBConnection();
         databaselog.info("**** getWorkitemStatus Got DB Connection *******");
         String command = "{call SP_HCS_GETWORKITEMSTATUS(?,?,?)}";
         cstmt = connection.prepareCall(command);
         cstmt.setString(1, workstepName);
         cstmt.setLong(2, piid);
         cstmt.registerOutParameter(3, -10);

         for(resultSet = cstmt.executeQuery(); resultSet.next(); bs_status = resultSet.getString("status")) {
            bstatus = resultSet.getString("bizlogic_status");
         }

         databaselog.info(" bizlogic status =" + bstatus + " bizstore status = " + bs_status);
         if (bstatus != null && bstatus.length() > 0) {
            if (!bstatus.equalsIgnoreCase("28") && !bstatus.equalsIgnoreCase("27")) {
               stat1 = "not completed";
            } else {
               stat1 = "active";
            }
         }

         if (bs_status != null && bs_status.length() > 0) {
            if (bs_status.equalsIgnoreCase("I_COMPLETED")) {
               stat2 = "completed";
               if (bstatus == null) {
                  stat2 = "completed";
               }
            }

            if (bs_status.equalsIgnoreCase("I_ASSIGNED") || bs_status.equalsIgnoreCase("I_AVAILABLE")) {
               stat2 = "active";
               if (bstatus == null) {
                  stat2 = "completed";
               }
            }
         }

         if (stat1 == null && (stat2 != null || stat2.equalsIgnoreCase("completed"))) {
            status = "completed";
         }

         databaselog.info("Got Result 2 stat1 " + stat1 + " stat2 " + stat2);

         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var23) {
         }
      } catch (Exception var24) {
         status = null;
         databaselog.error("getWorkitemStatus :Error occured " + var24);
      } finally {
         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var22) {
         }

      }

      return status;
   }

   public void insertHCSReqResLog(HcsLogObject obj, WorkItemObject[] wi) {
      CallableStatement cstmt = null;
      Connection connection = null;
      PreparedStatement pst = null;
      ResultSet rs = null;
      String sqlIdentifier = "select HCS_RES_WIARRAY_SEQ.NEXTVAL from dual";
      long seqId = 0L;

      try {
         databaselog.info(" ***** START of method insertHCSReqResLog ********");
         this.dbManager = new DBManager(SBM_HOME);
         connection = this.dbManager.getDBConnection();
         pst = connection.prepareStatement(sqlIdentifier);
         rs = pst.executeQuery();
         if (rs != null && rs.next()) {
            seqId = rs.getLong(1);
         }

         String command = "{call HCS_REQ_RES_INSERT(?,?,?,?,?,?,?,?,?,?)}";
         cstmt = connection.prepareCall(command);
         cstmt.setString(1, obj.getPROCESSINSTANCE_NAME());
         cstmt.setString(2, obj.getUSER_ID());
         cstmt.setString(3, obj.getWORKITEM_NAME());
         cstmt.setString(4, obj.getAPPROVER_DECISION());
         cstmt.setString(5, obj.getRESPONSE_CODE());
         cstmt.setString(6, obj.getRESPONSE_MESSAGE());
         cstmt.setLong(7, seqId);
         cstmt.setLong(8, seqId);
         cstmt.setString(9, obj.getWORKITEM_CASESTATUS());
         cstmt.setInt(10, obj.getISINSTANCE_COMPLETED());
         cstmt.execute();
         databaselog.info("*** Store proc executed HCS_REQ_RES_INSERT *******");
         if (wi != null && wi.length > 0) {
            for(int i = 0; i < wi.length; ++i) {
               WorkItemObject w = wi[i];
               command = "{call HCS_INSERT_WIARRAY(?,?,?,?,?,?,?,?)}";
               cstmt = connection.prepareCall(command);
               cstmt.setLong(1, seqId);
               cstmt.setString(2, w.getName());
               cstmt.setString(3, w.getPiName());
               cstmt.setString(4, w.getWorkStepName());
               cstmt.setString(5, w.getPerformer());
               cstmt.setString(6, w.getQueue());
               cstmt.setString(7, w.getCaseStatus());
               cstmt.setLong(8, 0L);
               cstmt.execute();
               databaselog.info("*** Store proc executed HCS_INSERT_WIARRAY *******");
            }
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (Exception var22) {
         }
      } catch (Exception var23) {
         databaselog.error("Error in insertHCSReqResLog ********", var23);
      } finally {
         try {
            if (pst != null) {
               pst.close();
            }

            if (cstmt != null) {
               cstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var21) {
         }

      }

   }

   public ResponseObject getHcsLogObject(HcsLogObject obj) {
      ResultSet resultSet = null;
      CallableStatement cstmt = null;
      Connection connection = null;
      ResponseObject resObj = new ResponseObject();

      try {
         databaselog.info("**** START getHcsLogObject ***********");
         this.dbManager = new DBManager(SBM_HOME);
         connection = this.dbManager.getDBConnection();
         String command = "{call HCS_GETREQRESLOG(?,?,?,?,?)}";
         cstmt = connection.prepareCall(command);
         cstmt.setString(1, obj.getPROCESSINSTANCE_NAME());
         cstmt.setString(2, obj.getUSER_ID());
         cstmt.setString(3, obj.getWORKITEM_NAME());
         cstmt.setString(4, obj.getAPPROVER_DECISION());
         cstmt.registerOutParameter(5, -10);
         resultSet = cstmt.executeQuery();
         databaselog.info("Got ResultSet for Storproc HCS_GETREQRESLOG ******");

         boolean bln;
         for(; resultSet.next(); resObj.setInstanceCompleted(bln)) {
            resObj.setResponseCode(resultSet.getString("RESPONSECODE"));
            resObj.setResponseMessage(resultSet.getString("RESPONSEMESSAGE"));
            resObj.setResultworkItemArray(this.getHcsWiArray(resultSet.getLong("SETRESULTWORKITEMARRAY")));
            resObj.setCompletedWorkItemArray((WorkItemObject[])null);
            resObj.setWorkItemCaseStatus(resultSet.getString("WORKITEMCASESTATUS"));
            int pstatus = resultSet.getInt("ISINSTANCECOMPLETED");
            bln = false;
            if (pstatus == 1) {
               bln = true;
            }
         }

         databaselog.info("Response Obj****** " + resObj);

         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var18) {
         }
      } catch (Exception var19) {
         databaselog.error("Error in getHcsLogObject  : ", var19);
      } finally {
         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var17) {
         }

      }

      return resObj;
   }

   public WorkItemObject[] getHcsWiArray(long id) {
      ResultSet resultSet = null;
      CallableStatement cstmt = null;
      Connection connection = null;
      List<WorkItemObject> al = new ArrayList();
      WorkItemObject[] wiArray = null;

      try {
         databaselog.info("***** Start getHcsWiArray Method ******");
         this.dbManager = new DBManager(SBM_HOME);
         connection = this.dbManager.getDBConnection();
         String command = "{call HCS_GETWIARRAY(?,?)}";
         cstmt = connection.prepareCall(command);
         cstmt.setLong(1, id);
         cstmt.registerOutParameter(2, -10);
         resultSet = cstmt.executeQuery();
         databaselog.info("Got Result Set for Store Proc : HCS_GETWIARRAY****");

         while(resultSet.next()) {
            WorkItemObject w = new WorkItemObject();
            w.setName(resultSet.getString("WORKITEM_NAME"));
            w.setPiName(resultSet.getString("PINAME"));
            w.setWorkStepName(resultSet.getString("WORKSTEPNAME"));
            w.setPerformer(resultSet.getString("PERFORMER"));
            w.setQueue(resultSet.getString("QUEUE"));
            w.setCaseStatus(resultSet.getString("CASESTATUS"));
            al.add(w);
         }

         databaselog.info("Size of WI ARRAY :: " + al.size());
         wiArray = new WorkItemObject[al.size()];

         for(int i = 0; i < al.size(); ++i) {
            wiArray[i] = (WorkItemObject)al.get(i);
         }

         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var19) {
         }
      } catch (Exception var20) {
         databaselog.error("Error in getHcsWiArray method ", var20);
      } finally {
         try {
            if (resultSet != null) {
               resultSet.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var18) {
         }

      }

      return wiArray;
   }
}
