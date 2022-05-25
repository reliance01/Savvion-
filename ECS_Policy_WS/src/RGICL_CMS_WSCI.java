import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgiclcmsci.cms.wsci.savvion.dbUtil.DBUtilityWSCI;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.AuthenticationDetail;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObject;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectAllRequests;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectAllRequestsUpdate;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectAssignTaskToUser;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectClaimIntimation;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectClaimIntimationUpdate;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectCorrectionPayeeName;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectDOGeneration;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectOnAccountApprovalPayment;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.RequestObjectUpdate;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.ResponseObject;
import rgiclcmsci.cms.wsci.savvion.policy.objectmodel.WorkItemObject;

public class RGICL_CMS_WSCI {
   private final String AUTH_ID = "RGICL_CMS_USER";
   private final String AUTH_PASS = "RGICL_CMS_PASS";
   private static BizLogicManager pak = null;
   private static String ECSADMIN = null;
   private static Byte[] bytearray = new Byte[0];
   private static Byte[] bytearrayECSADMIN = new Byte[0];
   final String priority = "medium";
   final String ECSGroup = "ECS";
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
   private DataSource ds = null;
   private Connection connection = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   final int SUCCESS = 5000;
   final int INPUT_VALUE_IS_NULL = 5010;
   final int INPUT_VALUE_IS_NEGATIVE = 5009;
   final int USER_ID_INVALID = 5013;
   final int INVALID_WINAME = 5020;
   final int FAILURE_EXCEPTION = 5035;
   final int REQUEST_OBJECT_IS_NULL = 5038;
   final int NOT_AUTHENTICATED = 5040;
   final int USER_ALREADY_MAPPED = 5011;
   final int USER_NOT_MAPPED = 5012;
   final int USER_ALREADY_CREATED = 5014;
   final int USER_NOT_PRESENT = 5015;
   final int FIELD_NOT_FOUND = 5016;
   final int PROCESS_NAME_IS_NULL = 5039;
   static final String ECSAdminUserName = "rgicl";
   static final String ECSAdminPassword = "rgicl";
   private static final int UNMATCHED_OPERATION_TYPE = 5054;
   private static final int UNMATCHING_PROCESS_NAME = 5057;
   private static final int WORKITEM_ALREADY_UNASSIGNED = 5059;
   private static final int REJECTED = 5070;
   boolean isSavvionAuditEnable = false;
   int ThreadSleep = 0;
   private static Logger logger = null;
   final String SBM_HOME = System.getProperty("sbm.home");
   final String CMS_SAVVION_LOG_PROPERTIES;
   final String RGICL_CMS_PROPERTIES;
   Properties propertiesCMSSavvion;
   Properties propertiesCMSLog;
   Properties props_rgiclcms;

   public RGICL_CMS_WSCI() {
      this.CMS_SAVVION_LOG_PROPERTIES = this.SBM_HOME + "/conf/CMSLog4jProperties.properties";
      this.RGICL_CMS_PROPERTIES = this.SBM_HOME + "/conf/RGICL_CMS_WS.properties";

      try {
         this.propertiesCMSLog = new Properties();
         this.propertiesCMSLog.load(new FileInputStream(this.CMS_SAVVION_LOG_PROPERTIES));
         PropertyConfigurator.configure(this.propertiesCMSLog);
         this.props_rgiclcms = new Properties();
         this.props_rgiclcms.load(new FileInputStream(this.RGICL_CMS_PROPERTIES));
         this.isSavvionAuditEnable = Boolean.parseBoolean(this.props_rgiclcms.getProperty("SAVVION_SERVICE_AUDIT_ENABLE"));
         this.ThreadSleep = Integer.parseInt(this.props_rgiclcms.getProperty("ThreadSleepTime"));
         logger = Logger.getLogger("CMSLogger");
      } catch (Exception var2) {
         System.out.println("Error while configuring logger for CMS");
      }

   }

   public ResponseObject createWorkFlowForClaimIntimation(RequestObjectClaimIntimation reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Request Values " + reqObj.getClaimReferenceNumber() + "," + reqObj.getriskScoreLimitForOD() + "," + reqObj.getSurveyorType() + "," + reqObj.getIsCommon() + "," + reqObj.getProductId() + "," + reqObj.getHubId() + "," + reqObj.getZoneId() + "," + reqObj.getPolicy_no() + "," + reqObj.getRiskScore() + "," + reqObj.getIsPremiumVerificationRequired() + "," + reqObj.getIsSpotSurveyorRequired() + "," + reqObj.getProcessInstanceName() + "," + reqObj.getIsPolicyDetailsAvailable() + "," + reqObj.getIsOrphan() + ",");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      int responseCode = 0;
      WorkItemObject[] workItemObjects = null;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Request Object is null");
         } else if (processName == null) {
            logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Process Name is null");
            responseCode = 5010;
         } else {
            String sessionId = null;

            try {
               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Process Name is " + processName);
               resolvedDSValues = this.getHashTableFromClaimIntimationRequestObject(reqObj);
               sessionId = this.connectUser("rgicl");
               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() creating processInstance");
               processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() ProcessInstanceName" + processInstanceName);
               Thread.sleep((long)this.ThreadSleep);
               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() getting active workstep list");
               String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
               if (workStepList != null) {
                  workItemObjects = new WorkItemObject[workStepList.length];
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() workStepList length is " + workStepList.length);

                  for(int i = 0; i < workStepList.length; ++i) {
                     String workStepName = workStepList[i];
                     WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                     logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() workitemList is " + workitemList);
                     List wiList = workitemList.getList();
                     logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() wiList is " + wiList);
                     if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                        WorkItemObject wiObject = new WorkItemObject();
                        WorkItem wi = (WorkItem)wiList.get(0);
                        String wiName = wi.getName();
                        String performer = wi.getPerformer();
                        String workItemId = String.valueOf(wi.getID());
                        wiObject.setWorkStepName(workStepName);
                        wiObject.setWorkItemName(wiName);
                        wiObject.setPerformer(performer);
                        wiObject.setWorkItemId(workItemId);
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() workStepName " + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId);
                        workItemObjects[i] = wiObject;
                     }
                  }
               }

               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Got the activeWorkItemNameForInstance");
               responseCode = 5000;
            } catch (Exception var27) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() Exception " + var27.getMessage());
               resObj.setResponseCode(5035);
            } finally {
               this.disconnect(sessionId);
            }
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setStatus("WIP");
         resObj.setInstanceCompleted(false);
         logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "ClaimIntimation_V5", "Create", reqObj.getUserId());
               logger.info("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() SavvionAuditCount is " + rowcount);
            } catch (Exception var26) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForClaimIntimation() insertSavvionAuditData Exception" + var26.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForAllRequests(RequestObjectAllRequests reqObj, String processName, AuthenticationDetail authenticationDetail) {
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      boolean isInstanceCompleted = false;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String status = null;
        
         if (reqObj == null) {
            responseCode = 5038;
            logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("") && reqObj.getRequestTypeID() != null && !reqObj.getRequestTypeID().equals("")) {
            if (!processName.equals("AllRequests")) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() Unmatching ProccessName");
               responseCode = 5057;
            } else if (!reqObj.getRequestTypeID().equals("3") || reqObj.getPhysicalCheck() != null && reqObj.getReissueRequestId() != null) {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() processName is " + processName);
                  if (reqObj.getReissueRequestId() == null) {
                     reqObj.setReissueRequestId("0");
                  }

                  if (reqObj.getPhysicalCheck() == null) {
                     reqObj.setPhysicalCheck("0");
                  }

                  resolvedDSValues = this.getHashTableFromRequestObjectAllRequest(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() connecting as adminsessionId is" + sessionId);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  logger.trace("RGICL_CMS_WSCI.createWorkFlowForAllRequests() ProcessInstanceName" + processInstanceName);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() ProcessInstanceName" + processInstanceName);
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList == null) {
                     status = "Approve";
                  } else {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() workitemList is " + workitemList);
                        List wiList = workitemList.getList();
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() wiList is " + wiList);
                        if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                           WorkItemObject wiObject = new WorkItemObject();
                           WorkItem wi = (WorkItem)wiList.get(0);
                           String wiName = wi.getName();
                           String performer = wi.getPerformer();
                           String workItemId = String.valueOf(wi.getID());
                           String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                           wiObject.setWorkStepName(workStepName);
                           wiObject.setWorkItemName(wiName);
                           wiObject.setPerformer(performer);
                           wiObject.setWorkItemId(workItemId);
                           wiObject.setroleName(roleName);
                           logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " roleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }

                     status = "Pending For Approval";
                  }

                  responseCode = 5000;
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() Got the activeWorkItemNameForInstance");
                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus");
                     } else {
                        status = "Approved";
                        responseCode = 5000;
                     }
                  }
               } catch (Exception var32) {
                  logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() Exception " + var32.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            } else {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() input value is null");
               responseCode = 5010;
            }
         } else {
            logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setInstanceCompleted(isInstanceCompleted);
         logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "AllRequests", "Create", "");
               logger.info("RGICL_CMS_WSCI.createWorkFlowForAllRequests() SavvionAuditCount is " + rowcount);
            } catch (Exception var31) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForAllRequests() insertSavvionAuditData Exception" + var31.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForCorrectionPayeeName(RequestObjectCorrectionPayeeName reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      boolean isInstanceCompleted = false;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String status = null;
        
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getZoneId() != null && !reqObj.getZoneId().equals("") && reqObj.getProductId() != null && !reqObj.getProductId().equals("") && reqObj.getInitiatorRole() != null && !reqObj.getInitiatorRole().equals("") && reqObj.getInitiatorId() != null && !reqObj.getInitiatorId().equals("") && reqObj.getHubId() != null && !reqObj.getHubId().equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("")) {
            if (!processName.equals("CorrectionPayeeName")) {
               responseCode = 5057;
               logger.error("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() Process Name Doesnt Match");
            } else {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromCorrectionPayeeNameRequestObject(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() connecting as adminsessionId is" + sessionId);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        WorkItemObject wiObject = new WorkItemObject();
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        List wiList = workitemList.getList();
                        WorkItem wi = (WorkItem)wiList.get(i);
                        String wiName = wi.getName();
                        String performer = wi.getPerformer();
                        String workItemId = String.valueOf(wi.getID());
                        String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                        wiObject.setWorkStepName(workStepName);
                        wiObject.setWorkItemName(wiName);
                        wiObject.setPerformer(performer);
                        wiObject.setWorkItemId(workItemId);
                        wiObject.setroleName(roleName);
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() workStepName-" + workStepName + ", workItemName-" + wiName + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                        workItemObjects[i] = wiObject;
                     }

                     responseCode = 5000;
                     status = "Pending For Approval";
                     logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() Got the activeWorkItemNameForInstance");
                  } else {
                     responseCode = 5000;
                     status = "Approve";
                  }
               } catch (Exception var30) {
                  logger.error("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() Exception " + var30);
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
               }
            }
         } else {
            logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setInstanceCompleted(isInstanceCompleted);
         logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "CorrectionPayeeName", "Create", reqObj.getInitiatorId());
               logger.info("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() SavvionAuditCount is " + rowcount);
            } catch (Exception var29) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForCorrectionPayeeName() insertSavvionAuditData Exception" + var29.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForOnAccountApprovalPayment(RequestObjectOnAccountApprovalPayment reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String Status = null;
        
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getZoneId() != null && !reqObj.getZoneId().equals("") && reqObj.getProductId() != null && !reqObj.getProductId().equals("") && reqObj.getInitiatorRole() != null && !reqObj.getInitiatorRole().equals("") && reqObj.getInitiatorId() != null && !reqObj.getInitiatorId().equals("") && reqObj.getHubId() != null && !reqObj.getHubId().equals("") && reqObj.getExpenseType() != null && !reqObj.getExpenseType().equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("") && reqObj.getAmount() != null && !reqObj.getAmount().equals("") && reqObj.getApproverLimit() != null && !reqObj.getApproverLimit().equals("")) {
            if (!processName.equals("OnAccountApproval")) {
               responseCode = 5057;
               logger.error("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() Process Name Doesnt Match");
            } else {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromOnAccountApprovalPaymentRequestObject(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() workitemList is " + workitemList);
                        List wiList = workitemList.getList();
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() wiList is " + wiList);
                        if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                           WorkItemObject wiObject = new WorkItemObject();
                           WorkItem wi = (WorkItem)wiList.get(0);
                           String wiName = wi.getName();
                           String performer = wi.getPerformer();
                           String workItemId = String.valueOf(wi.getID());
                           String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                           wiObject.setWorkStepName(workStepName);
                           wiObject.setWorkItemName(wiName);
                           wiObject.setPerformer(performer);
                           wiObject.setWorkItemId(workItemId);
                           wiObject.setroleName(roleName);
                           logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() workStepName-" + workStepName + ", workItemName-" + wiName + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }

                     Status = "Pending For Approval";
                     responseCode = 5000;
                     logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() Got the activeWorkItemNameForInstance");
                  } else {
                     Status = "Approve";
                     responseCode = 5000;
                  }
               } catch (Exception var29) {
                  logger.error("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() Exception " + var29.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
               }
            }
         } else {
            logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(Status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setInstanceCompleted(false);
         logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "OnAccountApproval", "Create", reqObj.getInitiatorId());
               logger.info("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() SavvionAuditCount is " + rowcount);
            } catch (Exception var28) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForOnAccountApprovalPayment() insertSavvionAuditData Exception" + var28.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateCorrectionPayeeName(RequestObjectUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WSCI.updateCorrectionPayeeName() request object is null");
            } else {
               String approveDecision = reqObj.getApproveDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               processInstanceName = reqObj.getProcessInstanceName();
               status = approveDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                  if (userId != null && !userId.equals("")) {
                     if (UserManager.getUser(userId) == null) {
                        logger.error("RGICL_CMS_WSCI.updateCorrectionPayeeName() User id provided doesnt exists");
                        responseCode = 5015;
                     } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                        responseCode = 5054;
                        logger.error("RGICL_CMS_WSCI.updateCorrectionPayeeName() UnMatched Operation Type");
                        resObj.setResponseCode(responseCode);
                     } else {
                        ResponseObject var31;
                        if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           this.updateDecision(reqObj, resObj);
                           sessionId = this.connectUser("rgicl");
                           var31 = this.requestWorkItemObject(reqObj, sessionId, processInstanceName, resObj);
                           return var31;
                        }

                        if (approveDecision == null || approveDecision.length() == 0) {
                           responseCode = 5010;
                           resObj.setResponseCode(responseCode);
                           var31 = resObj;
                           return var31;
                        }

                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        HashMap hm = new HashMap();
                        hm.put("approverDecision", approveDecision);
                        hm.put("ApprovalRoleID", userId);
                        if (blsession != null) {
                           pi.updateSlotValue(hm);
                           pi.save();
                           blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                           WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                           if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() Task assigned to user");
                           }

                           logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() CompleteTask enterred");
                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() Task Completed");
                           Thread.sleep((long)this.ThreadSleep);
                           isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                           sessionId = this.connectUser("rgicl");
                           logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() isInstanceCompleted::" + isInstanceCompleted);
                           if (!isInstanceCompleted) {
                              String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                              logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() workStepList is " + workStepList + "Length " + workStepList.length);
                              String roleName = null;
                              if (workStepList != null && workStepList.length > 0) {
                                 logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() inside the active list of workitem");
                                 workItemObjects = new WorkItemObject[workStepList.length];

                                 for(int i = 0; i < workStepList.length; ++i) {
                                    String workStepName = workStepList[i];
                                    WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                                    List wiList = workitemList.getList();
                                    if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                       WorkItemObject wiObject = new WorkItemObject();
                                       wi = (WorkItem)wiList.get(0);
                                       String wiName = wi.getName();
                                       String performer = wi.getPerformer();
                                       String workItemId = String.valueOf(wi.getID());
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                                       wiObject.setWorkStepName(workStepName);
                                       wiObject.setWorkItemName(wiName);
                                       wiObject.setPerformer(performer);
                                       wiObject.setWorkItemId(workItemId);
                                       wiObject.setroleName(roleName);
                                       claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                       logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                       workItemObjects[i] = wiObject;
                                    }
                                 }
                              }

                              responseCode = 5000;
                              status = "Pending For Approval";
                           }

                           if (workItemObjects == null && reqObj.getApproveDecision().equals("Approve")) {
                              responseCode = 5000;
                              status = "Completed";
                              isInstanceCompleted = true;
                           } else if (workItemObjects == null) {
                              status = "Completed";
                              responseCode = 5070;
                              isInstanceCompleted = true;
                           }
                        }
                     }
                  } else {
                     logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var36) {
            logger.error("RGICL_CMS_WSCI.updateCorrectionPayeeName() Exception " + var36.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setResponseCode(responseCode);
         logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "CorrectionPayeeName", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WSCI.updateCorrectionPayeeName() SavvionAuditCount is " + rowcount);
            } catch (Exception var35) {
               logger.error("RGICL_CMS_WSCI.updateCorrectionPayeeName() insertSavvionAuditData Exception" + var35.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateOnAccountApprovalPayment(RequestObjectUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() request object is null");
            } else {
               String approveDecision = reqObj.getApproveDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               String AppLtd = reqObj.getApproverLtd();
               processInstanceName = reqObj.getProcessInstanceName();
               status = approveDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                  if (userId != null && !userId.equals("")) {
                     if (UserManager.getUser(userId) == null) {
                        logger.error("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() User id provided doesnt exists");
                        responseCode = 5015;
                     } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                        responseCode = 5054;
                        logger.error("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() UnMatched Operation Type");
                        resObj.setResponseCode(responseCode);
                     } else {
                        ResponseObject var32;
                        if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           this.updateDecision(reqObj, resObj);
                           sessionId = this.connectUser("rgicl");
                           var32 = this.requestWorkItemObject(reqObj, sessionId, processInstanceName, resObj);
                           return var32;
                        }

                        if (approveDecision == null || approveDecision.length() == 0) {
                           responseCode = 5010;
                           resObj.setResponseCode(responseCode);
                           var32 = resObj;
                           return var32;
                        }

                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        HashMap hm = new HashMap();
                        hm.put("approverDecision", approveDecision);
                        hm.put("ApprovalRoleID", userId);
                        hm.put("approverLimit", AppLtd);
                        if (blsession != null) {
                           pi.updateSlotValue(hm);
                           pi.save();
                           blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                           WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                           if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              logger.warn("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() Task assigned to user");
                           }

                           logger.warn("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() CompleteTask enterred");
                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.warn("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() Task Completed");
                           Thread.sleep((long)this.ThreadSleep);
                           isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                           sessionId = this.connectUser("rgicl");
                           logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() service isInstanceCompleted::" + isInstanceCompleted);
                           if (!isInstanceCompleted) {
                              String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                              logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() workStepList is " + workStepList);
                              String roleName = null;
                              if (workStepList != null && workStepList.length > 0) {
                                 workItemObjects = new WorkItemObject[workStepList.length];

                                 for(int i = 0; i < workStepList.length; ++i) {
                                    String workStepName = workStepList[i];
                                    WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                                    List wiList = workitemList.getList();
                                    if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                       WorkItemObject wiObject = new WorkItemObject();
                                       wi = (WorkItem)wiList.get(0);
                                       String wiName = wi.getName();
                                       String performer = wi.getPerformer();
                                       String workItemId = String.valueOf(wi.getID());
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                                       wiObject.setWorkStepName(workStepName);
                                       wiObject.setWorkItemName(wiName);
                                       wiObject.setPerformer(performer);
                                       wiObject.setWorkItemId(workItemId);
                                       wiObject.setroleName(roleName);
                                       claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                       logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                       workItemObjects[i] = wiObject;
                                    }
                                 }
                              }

                              responseCode = 5000;
                              status = "Pending For Approval";
                           }

                           if (workItemObjects == null && reqObj.getApproveDecision().equals("Approve")) {
                              responseCode = 5000;
                              status = "Completed";
                              isInstanceCompleted = true;
                           } else if (workItemObjects == null) {
                              status = "Completed";
                              responseCode = 5070;
                              isInstanceCompleted = true;
                           }
                        }
                     }
                  } else {
                     logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var37) {
            logger.error("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() error occured while updating");
            logger.error(var37.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setResponseCode(responseCode);
         logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "OnAccountApproval", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() SavvionAuditCount is " + rowcount);
            } catch (Exception var36) {
               logger.error("RGICL_CMS_WSCI.updateOnAccountApprovalPayment() insertSavvionAuditData Exception" + var36.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateAllRequestFlow(RequestObjectAllRequestsUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String Status = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      BLServer blserver = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      String claimRefNo = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            label226:
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WSCI.updateAllRequestFlow() request object is null");
            } else {
               String approveDecision = reqObj.getApproveDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserID();
               processInstanceName = reqObj.getProcessInstanceName();
               Status = approveDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                  if (userId != null && !userId.equals("")) {
                     if (UserManager.getUser(userId) == null) {
                        logger.error("RGICL_CMS_WSCI.updateAllRequestFlow() User id provided doesnt exists");
                        responseCode = 5015;
                     } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                        responseCode = 5054;
                        logger.error("RGICL_CMS_WSCI.updateAllRequestFlow() UnMatched Operation Type");
                        resObj.setResponseCode(responseCode);
                     } else {
                        ResponseObject var31;
                        if (reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           if (approveDecision != null && approveDecision.length() != 0) {
                              blserver = BLClientUtil.getBizLogicServer();
                              blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                              ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                              HashMap hm = new HashMap();
                              hm.put("approverDecision", approveDecision);
                              hm.put("ApprovalRoleID", userId);
                              if (blsession != null) {
                                 pi.updateSlotValue(hm);
                                 pi.save();
                                 blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                                 WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                                 if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                                    this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                    logger.warn("RGICL_CMS_WSCI.updateAllRequestFlow() Task assigned to user");
                                 }

                                 logger.warn("RGICL_CMS_WSCI.updateAllRequestFlow() CompleteTask enterred");
                                 this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 logger.warn("RGICL_CMS_WSCI.updateAllRequestFlow() Task Completed");
                                 Thread.sleep((long)this.ThreadSleep);
                                 isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                                 sessionId = this.connectUser("rgicl");
                                 logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() service isInstanceCompleted::" + isInstanceCompleted);
                                 if (!isInstanceCompleted) {
                                    String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                                    logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() workStepList is " + workStepList);
                                    String roleName = null;
                                    if (workStepList != null && workStepList.length > 0) {
                                       workItemObjects = new WorkItemObject[workStepList.length];

                                       for(int i = 0; i < workStepList.length; ++i) {
                                          String workStepName = workStepList[i];
                                          WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                                          List wiList = workitemList.getList();
                                          if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                             WorkItemObject wiObject = new WorkItemObject();
                                             wi = (WorkItem)wiList.get(0);
                                             String wiName = wi.getName();
                                             String performer = wi.getPerformer();
                                             String workItemId = String.valueOf(wi.getID());
                                             roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                                             wiObject.setWorkStepName(workStepName);
                                             wiObject.setWorkItemName(wiName);
                                             wiObject.setPerformer(performer);
                                             wiObject.setWorkItemId(workItemId);
                                             wiObject.setroleName(roleName);
                                             claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                             logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                             workItemObjects[i] = wiObject;
                                          }
                                       }

                                       Status = "Pending For Approval";
                                       responseCode = 5000;
                                    }
                                 } else if (reqObj.getApproveDecision().equals("Approve")) {
                                    responseCode = 5000;
                                    Status = "Approve";
                                 } else {
                                    Status = "Approve";
                                    responseCode = 5070;
                                 }
                              }
                              break label226;
                           }

                           responseCode = 5010;
                           resObj.setResponseCode(responseCode);
                           var31 = resObj;
                           return var31;
                        }

                        this.updateDecision(reqObj, resObj);
                        sessionId = this.connectUser("rgicl");
                        var31 = this.requestWorkItemObject(reqObj, sessionId, processInstanceName, resObj);
                        return var31;
                     }
                  } else {
                     logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var36) {
            logger.error("RGICL_CMS_WSCI.updateAllRequestFlow() Exception " + var36.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(Status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setResponseCode(responseCode);
         logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "AllRequests", "Update", reqObj.getUserID());
               logger.info("RGICL_CMS_WSCI.updateAllRequestFlow() SavvionAuditCount is " + rowcount);
            } catch (Exception var35) {
               logger.error("RGICL_CMS_WSCI.updateAllRequestFlow() insertSavvionAuditData Exception" + var35.getMessage());
            }
         }

         return resObj;
      }
   }

   private ResponseObject requestWorkItemObject(RequestObjectAllRequestsUpdate reqObj, String sessionId, String processInstanceName, ResponseObject resObj) throws AxisFault, RemoteException {
      WorkItemObject[] workItemObjects = null;
      BLServer blserver = null;
      Session blsession = null;
      WorkItem wi = null;

      try {
         blserver = BLClientUtil.getBizLogicServer();
         blsession = blserver.connect(reqObj.getUserID(), this.getUserPasswordECS(reqObj.getUserID()));
         boolean isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
         sessionId = this.connectUser("rgicl");
         logger.info("RGICL_CMS_WSCI.requestWorkItemObject() service isInstanceCompleted::" + isInstanceCompleted);
         if (!isInstanceCompleted) {
            String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
            String roleName = null;
            if (workStepList != null && workStepList.length > 0) {
               workItemObjects = new WorkItemObject[workStepList.length];

               for(int i = 0; i < workStepList.length; ++i) {
                  String workStepName = workStepList[i];
                  WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                  List wiList = workitemList.getList();
                  if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                     WorkItemObject wiObject = new WorkItemObject();
                     wi = (WorkItem)wiList.get(0);
                     String wiName = wi.getName();
                     String performer = wi.getPerformer();
                     String workItemId = String.valueOf(wi.getID());
                     roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                     wiObject.setWorkStepName(workStepName);
                     wiObject.setWorkItemName(wiName);
                     wiObject.setPerformer(performer);
                     wiObject.setWorkItemId(workItemId);
                     wiObject.setroleName(roleName);
                     logger.info("RGICL_CMS_WSCI.requestWorkItemObject() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                     workItemObjects[i] = wiObject;
                  }
               }

               resObj.setWorkItemObjects(workItemObjects);
            }
         }
      } catch (Exception var23) {
         logger.error("RGICL_CMS_WSCI.requestWorkItemObject() Exception " + var23.getMessage());
      } finally {
         this.disconnect(sessionId);
         this.disconnectBlSession(blsession);
      }

      return resObj;
   }

   private ResponseObject requestWorkItemObject(RequestObjectUpdate reqObj, String sessionId, String processInstanceName, ResponseObject resObj) throws AxisFault, RemoteException {
      WorkItemObject[] workItemObjects = null;
      BLServer blserver = null;
      Session blsession = null;
      WorkItem wi = null;

      try {
         blserver = BLClientUtil.getBizLogicServer();
         blsession = blserver.connect(reqObj.getUserId(), this.getUserPasswordECS(reqObj.getUserId()));
         boolean isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
         sessionId = this.connectUser("rgicl");
         logger.info("RGICL_CMS_WSCI.requestWorkItemObject() isInstanceCompleted " + isInstanceCompleted);
         if (!isInstanceCompleted) {
            String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
            String roleName = null;
            if (workStepList != null && workStepList.length > 0) {
               workItemObjects = new WorkItemObject[workStepList.length];

               for(int i = 0; i < workStepList.length; ++i) {
                  String workStepName = workStepList[i];
                  WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                  List wiList = workitemList.getList();
                  if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                     WorkItemObject wiObject = new WorkItemObject();
                     wi = (WorkItem)wiList.get(0);
                     String wiName = wi.getName();
                     String performer = wi.getPerformer();
                     String workItemId = String.valueOf(wi.getID());
                     roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                     wiObject.setWorkStepName(workStepName);
                     wiObject.setWorkItemName(wiName);
                     wiObject.setPerformer(performer);
                     wiObject.setWorkItemId(workItemId);
                     wiObject.setroleName(roleName);
                     logger.info("RGICL_CMS_WSCI.requestWorkItemObject() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                     workItemObjects[i] = wiObject;
                  }
               }

               resObj.setWorkItemObjects(workItemObjects);
            }
         }
      } catch (Exception var23) {
         logger.error("RGICL_CMS_WSCI.requestWorkItemObject() Exception is " + var23.getMessage());
      } finally {
         this.disconnect(sessionId);
         this.disconnectBlSession(blsession);
      }

      return resObj;
   }

   private void updateDecision(RequestObjectAllRequestsUpdate reqObj, ResponseObject resObj) {
      String userId = reqObj.getUserID();
      BLServer blserver = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      Pattern pattern = Pattern.compile("\\:\\:(.*)");
      Matcher matcher = pattern.matcher(workItemName);
      matcher.find();
      workItemName = matcher.group(1);
      if (workItemName.contains(":")) {
         workItemName = workItemName.substring(0, workItemName.indexOf(":"));
      }

      int responseCode = 0;
      Session blsession = null;

      try {
         blserver = BLClientUtil.getBizLogicServer();
         blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
         WorkStepInstance workStepInstance = blserver.getWorkStepInstance(blsession, processInstanceName, workItemName);
         WorkItemList wli = workStepInstance.getWorkItemList();
         Vector wiList = (Vector)wli.getList();

         for(int i = 0; i < wiList.size(); ++i) {
            WorkItem wi = (WorkItem)wiList.get(i);
            if (reqObj.getOperationType().equalsIgnoreCase("unassign")) {
               AdvanceGroup groupObj = (AdvanceGroup)UserManager.getGroup("OrphanPool");
               Vector userList = new Vector();
               userList.addAll(Arrays.asList(groupObj.getAllUserMemberNames()));
               if (wi.isAssigned()) {
                  wi.makeAvailable(userList);
                  wi.save();
                  wi.refresh();
                  responseCode = 5000;
               } else {
                  responseCode = 5059;
                  logger.error("RGICL_CMS_WSCI.updateDecision() Workitem is already assigned");
               }
            } else {
               String newUserId;
               User usr;
               if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                  newUserId = reqObj.getNewUserID();
                  if (newUserId != null && newUserId.length() != 0) {
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WSCI.updateDecision() new user " + newUserId + " is not present in savvion");
                        responseCode = 5015;
                     } else {
                        if (wi.isAssigned()) {
                           wi.reAssign(newUserId);
                           wi.save();
                           wi.refresh();
                        } else {
                           wi.assign(newUserId);
                           wi.save();
                           wi.refresh();
                        }

                        responseCode = 5000;
                     }
                  } else {
                     logger.error("RGICL_CMS_WSCI.updateDecision() Input value is null ");
                     responseCode = 5010;
                  }
               } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                  newUserId = reqObj.getNewUserID();
                  usr = UserManager.getUser(newUserId);
                  if (usr == null) {
                     logger.error("RGICL_CMS_WSCI.updateDecision() new user " + newUserId + " is not present in savvion");
                     responseCode = 5015;
                  } else {
                     if (wi.isAssigned()) {
                        wi.reAssign(newUserId);
                        wi.save();
                        wi.refresh();
                     } else {
                        wi.assign(newUserId);
                        wi.save();
                        wi.refresh();
                     }

                     responseCode = 5000;
                  }
               }
            }
         }
      } catch (RemoteException var21) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WSCI.updateDecision() Update claim intimation fails with error " + var21.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setProcessInstanceName(processInstanceName);
      resObj.setResponseCode(responseCode);
   }

   private void updateDecision(RequestObjectUpdate reqObj, ResponseObject resObj) {
      String userId = reqObj.getUserId();
      BLServer blserver = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      Pattern pattern = Pattern.compile("\\:\\:(.*)");
      Matcher matcher = pattern.matcher(workItemName);
      matcher.find();
      workItemName = matcher.group(1);
      if (workItemName.contains(":")) {
         workItemName = workItemName.substring(0, workItemName.indexOf(":"));
      }

      int responseCode = 0;
      Session blsession = null;

      try {
         blserver = BLClientUtil.getBizLogicServer();
         blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
         WorkStepInstance workStepInstance = blserver.getWorkStepInstance(blsession, processInstanceName, workItemName);
         WorkItemList wli = workStepInstance.getWorkItemList();
         Vector wiList = (Vector)wli.getList();

         for(int i = 0; i < wiList.size(); ++i) {
            WorkItem wi = (WorkItem)wiList.get(i);
            if (reqObj.getOperationType().equalsIgnoreCase("unassign")) {
               AdvanceGroup groupObj = (AdvanceGroup)UserManager.getGroup("OrphanPool");
               Vector userList = new Vector();
               userList.addAll(Arrays.asList(groupObj.getAllUserMemberNames()));
               if (wi.isAssigned()) {
                  wi.makeAvailable(userList);
                  wi.save();
                  wi.refresh();
                  responseCode = 5000;
               } else {
                  responseCode = 5059;
                  logger.error("RGICL_CMS_WSCI.updateDecision() Workitem is already assigned");
               }
            } else {
               String newUserId;
               User usr;
               if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                  newUserId = reqObj.getNewUserId();
                  if (newUserId != null && newUserId.length() != 0) {
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WSCI.updateDecision() new user " + newUserId + " is not present in savvion");
                        responseCode = 5015;
                     } else {
                        if (wi.isAssigned()) {
                           wi.reAssign(newUserId);
                           wi.save();
                           wi.refresh();
                        } else {
                           wi.assign(newUserId);
                           wi.save();
                           wi.refresh();
                        }

                        responseCode = 5000;
                     }
                  } else {
                     logger.error("RGICL_CMS_WSCI.updateDecision() Input value is null ");
                     responseCode = 5010;
                  }
               } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                  newUserId = reqObj.getNewUserId();
                  usr = UserManager.getUser(newUserId);
                  if (usr == null) {
                     logger.error("RGICL_CMS_WSCI.updateDecision() new user " + newUserId + " is not present in savvion");
                     responseCode = 5015;
                  } else {
                     if (wi.isAssigned()) {
                        wi.reAssign(newUserId);
                        wi.save();
                        wi.refresh();
                     } else {
                        wi.assign(newUserId);
                        wi.save();
                        wi.refresh();
                     }

                     responseCode = 5000;
                  }
               }
            }
         }
      } catch (RemoteException var21) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WSCI.updateDecision() Update fails with error " + var21.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setProcessInstanceName(processInstanceName);
      resObj.setResponseCode(responseCode);
   }

   public ResponseObject updateClaim(RequestObjectClaimIntimationUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateClaim() Called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      String Status = "";
      boolean isInstanceCompleted = false;
      String sessionId = null;
      WorkItemObject[] workItemObjects = null;
      String proccessInstanceName = null;
      BLServer blserver = null;
      Session blsession = null;
      String bl_user = "rgicl";
      String bl_password = "rgicl";
      String claimRefNo = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         if (reqObj == null) {
            responseCode = 5038;
            resObj.setResponseCode(responseCode);
            logger.error("RGICL_CMS_WSCI.updateClaim() Request Object is null");
         } else if (!reqObj.getOperationType().equals("assign") && !reqObj.getOperationType().equals("unassign") && !reqObj.getOperationType().equals("movetoclaimowner") && !reqObj.getOperationType().equals("movetoOrphanPool") && !reqObj.getOperationType().equals("reassign") && !reqObj.getOperationType().equals("hubreassign")) {
            logger.error("RGICL_CMS_WSCI.updateClaim() OperationType is unmatched");
            responseCode = 5054;
            resObj.setResponseCode(responseCode);
         } else {
            String currentHubId = reqObj.getCurrentHubId();
            String workItemName = reqObj.getWorkItemName();
            proccessInstanceName = reqObj.getProcessInstanceName();
            String newCMId = reqObj.getNewCMId();
            String newHubId = reqObj.getNewHubId();
            String userId = reqObj.getUserId();
            if (workItemName != null && proccessInstanceName != null && workItemName.length() != 0 && proccessInstanceName.length() != 0) {
               if (userId != null && !userId.equals("")) {
                  bl_user = userId;
                  bl_password = this.getUserPasswordECS(userId);
                  blserver = BLClientUtil.getBizLogicServer();

                  try {
                     blsession = blserver.connect(bl_user, bl_password);
                     WorkStepInstance workStepInstance = blserver.getWorkStepInstance(blsession, proccessInstanceName, workItemName);
                     WorkItemList wli = workStepInstance.getWorkItemList();
                     Vector wiList = (Vector)wli.getList();

                     for(int i = 0; i < wiList.size(); ++i) {
                        WorkItem wi = (WorkItem)wiList.get(i);
                        if (reqObj.getOperationType().equalsIgnoreCase("unassign")) {
                           logger.info("RGICL_CMS_WSCI.updateClaim() unassign called");
                           if (wi.isAssigned()) {
                              wi.makeAvailable();
                              wi.save();
                              wi.refresh();
                           }

                           wi.assign("OrphanUser");
                           wi.save();
                           wi.refresh();
                           Status = "Completed";
                           responseCode = 5000;
                        } else {
                           User usr;
                           if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                              logger.info("RGICL_CMS_WSCI.updateClaim() call reassign");
                              if (newCMId != null && newCMId.length() != 0) {
                                 usr = UserManager.getUser(newCMId);
                                 if (usr == null) {
                                    logger.error("RGICL_CMS_WSCI.updateClaim() " + newCMId + " is not present in savvion");
                                    responseCode = 5015;
                                 } else {
                                    if (wi.isAssigned()) {
                                       wi.reAssign(newCMId);
                                       wi.save();
                                       wi.refresh();
                                    } else {
                                       wi.assign(newCMId);
                                       wi.save();
                                       wi.refresh();
                                    }

                                    Status = "Completed";
                                    responseCode = 5000;
                                 }
                              } else {
                                 logger.error("RGICL_CMS_WSCI.updateClaim() Input value is null ");
                                 responseCode = 5010;
                              }
                           } else {
                              String wiName;
                              String performer;
                              if (reqObj.getOperationType().equalsIgnoreCase("movetoclaimowner")) {
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() 1.call movetoclaimowner");
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() 2.blsession" + (new Long(blsession.getID())).toString());
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() 3.workItemName:" + proccessInstanceName + "::" + workItemName);
                                 this.assignWorkitem((new Long(blsession.getID())).toString(), proccessInstanceName + "::" + workItemName);
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() CompleteTask enterred");
                                 this.completeWorkitem((new Long(blsession.getID())).toString(), proccessInstanceName + "::" + workItemName);
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() Task Completed");
                                 Thread.sleep((long)this.ThreadSleep);
                                 blserver = BLClientUtil.getBizLogicServer();
                                 blsession = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                                 ProcessInstance pi = blserver.getProcessInstance(blsession, proccessInstanceName);
                                 pi.refresh();
                                 isInstanceCompleted = pi.isCompleted();
                                 sessionId = this.connectUser("rgicl");
                                 logger.info("RGICL_CMS_WSCI.updateClaim() isInstanceCompleted::" + isInstanceCompleted);
                                 if (isInstanceCompleted) {
                                    Status = "Completed";
                                    responseCode = 5000;
                                 } else {
                                    sessionId = this.connectUser("rgicl");
                                    String[] workStepList = this.getActiveWorkStepList(sessionId, proccessInstanceName);
                                    logger.info("RGICL_CMS_WSCI.updateClaim() workStepList is " + workStepList + "Length " + workStepList.length);
                                    performer = null;
                                    if (workStepList != null && workStepList.length > 0) {
                                       logger.info("RGICL_CMS_WSCI.updateClaim() inside the active list of workitem");
                                       workItemObjects = new WorkItemObject[workStepList.length];

                                       for(int k = 0; k < workStepList.length; ++k) {
                                          String workStepName = workStepList[k];
                                          WorkItemList workitemList = this.getWorkItemList(sessionId, proccessInstanceName, workStepName);
                                          List wiiList = workitemList.getList();
                                          if (wiiList != null && !wiiList.isEmpty() && wiiList.size() > 0) {
                                             WorkItemObject wiObject = new WorkItemObject();
                                             wi = (WorkItem)wiiList.get(0);
                                             wiName = wi.getName();
                                             performer = wi.getPerformer();
                                             wiName = String.valueOf(wi.getID());
                                             performer = this.getDataSlotValue(sessionId, proccessInstanceName, "currentRoleName");
                                             wiObject.setWorkStepName(workStepName);
                                             wiObject.setWorkItemName(wiName);
                                             wiObject.setPerformer(performer);
                                             wiObject.setWorkItemId(wiName);
                                             wiObject.setroleName(performer);
                                             claimRefNo = this.getDataSlotValue(sessionId, proccessInstanceName, "claimReferenceNumber");
                                             logger.info("RGICL_CMS_WSCI.updateClaim() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + wiName + " ,roleName-" + performer);
                                             workItemObjects[k] = wiObject;
                                          }
                                       }
                                    }

                                    Status = "Completed";
                                    responseCode = 5000;
                                 }
                              } else if (reqObj.getOperationType().equalsIgnoreCase("movetoOrphanPool")) {
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() 1.call movetoOrphanPool");
                                 HashMap<String, String> hm = new HashMap();
                                 hm.put("currentRoleName", "ClaimOrphan");
                                 hm.put("numberOfCM", "0");
                                 BLServer blServer = BLClientUtil.getBizLogicServer();
                                 logger.info("RGICL_CMS_WSCI.updateClaim() get BL server");

                                 ProcessInstance pi;
                                 try {
                                    blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
                                    pi = blServer.getProcessInstance(blsession, proccessInstanceName);
                                    pi.updateSlotValue(hm);
                                    pi.save();
                                 } catch (RemoteException var56) {
                                    responseCode = 1;
                                    logger.error("RGICL_CMS_WSCI.updateClaim() Error while updating hub id details " + var56.getMessage());
                                 }

                                 logger.warn("RGICL_CMS_WSCI.updateClaim() data updated");
                                 this.assignWorkitem((new Long(blsession.getID())).toString(), proccessInstanceName + "::" + workItemName);
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() CompleteTask enterred");
                                 this.completeWorkitem((new Long(blsession.getID())).toString(), proccessInstanceName + "::" + workItemName);
                                 logger.warn("RGICL_CMS_WSCI.updateClaim() Task Completed");
                                 Thread.sleep((long)this.ThreadSleep);
                                 blserver = BLClientUtil.getBizLogicServer();
                                 blsession = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                                 pi = blserver.getProcessInstance(blsession, proccessInstanceName);
                                 pi.refresh();
                                 isInstanceCompleted = this.isInstanceCompleted(blsession, proccessInstanceName);
                                 sessionId = this.connectUser("rgicl");
                                 logger.info("RGICL_CMS_WSCI.updateClaim() isInstanceCompleted::" + isInstanceCompleted);
                                 if (isInstanceCompleted) {
                                    Status = "Completed";
                                    responseCode = 5000;
                                 } else {
                                    sessionId = this.connectUser("rgicl");
                                    String[] workStepList = this.getActiveWorkStepList(sessionId, proccessInstanceName);
                                    logger.info("RGICL_CMS_WSCI.updateClaim() workStepList is " + workStepList + "Length " + workStepList.length);
                                    String roleName = null;
                                    if (workStepList != null && workStepList.length > 0) {
                                       logger.info("RGICL_CMS_WSCI.updateClaim() inside the active list of workitem");
                                       workItemObjects = new WorkItemObject[workStepList.length];

                                       for(int k = 0; k < workStepList.length; ++k) {
                                          String workStepName = workStepList[k];
                                          WorkItemList workitemList = this.getWorkItemList(sessionId, proccessInstanceName, workStepName);
                                          List wiiList = workitemList.getList();
                                          if (wiiList != null && !wiiList.isEmpty() && wiiList.size() > 0) {
                                             WorkItemObject wiObject = new WorkItemObject();
                                             wi = (WorkItem)wiiList.get(0);
                                             wiName = wi.getName();
                                             performer = wi.getPerformer();
                                             String workItemId = String.valueOf(wi.getID());
                                             roleName = this.getDataSlotValue(sessionId, proccessInstanceName, "currentRoleName");
                                             wiObject.setWorkStepName(workStepName);
                                             wiObject.setWorkItemName(wiName);
                                             wiObject.setPerformer(performer);
                                             wiObject.setWorkItemId(workItemId);
                                             wiObject.setroleName(roleName);
                                             claimRefNo = this.getDataSlotValue(sessionId, proccessInstanceName, "claimReferenceNumber");
                                             logger.info("RGICL_CMS_WSCI.updateClaim() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                             workItemObjects[k] = wiObject;
                                          }
                                       }
                                    }

                                    Status = "Completed";
                                    responseCode = 5000;
                                 }
                              } else if (reqObj.getOperationType().equalsIgnoreCase("hubreassign")) {
                                 logger.info("RGICL_CMS_WSCI.updateClaim() hubreassign called");
                                 if (wi.isAssigned()) {
                                    wi.makeAvailable();
                                    wi.save();
                                    wi.refresh();
                                 }

                                 wi.assign("OrphanUser");
                                 wi.save();
                                 wi.refresh();
                                 Status = "Completed";
                                 responseCode = 5000;
                              } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                                 usr = UserManager.getUser(newCMId);
                                 if (usr == null) {
                                    logger.error("RGICL_CMS_WSCI.updateClaim()  new user " + newCMId + " is not present in savvion");
                                    responseCode = 5015;
                                 } else {
                                    if (wi.isAssigned()) {
                                       wi.reAssign(newCMId);
                                       wi.save();
                                       wi.refresh();
                                    } else {
                                       wi.assign(newCMId);
                                       wi.save();
                                       wi.refresh();
                                    }

                                    Status = "Completed";
                                    responseCode = 5000;
                                 }
                              }
                           }
                        }
                     }
                  } catch (RemoteException var57) {
                     responseCode = 5035;
                     logger.error("RGICL_CMS_WSCI.updateClaim() RemoteException " + var57.getMessage());
                  } catch (Exception var58) {
                     responseCode = 5035;
                     logger.error("RGICL_CMS_WSCI.updateClaim() Exception " + var58.getMessage());
                  } finally {
                     this.disconnect(sessionId);
                     this.disconnectBlSession(blsession);
                  }

                  if (reqObj.getOperationType().equalsIgnoreCase("hubreassign") || reqObj.getOperationType().equalsIgnoreCase("unassign")) {
                     HashMap<String, String> hm = new HashMap();
                     if (reqObj.getOperationType().equalsIgnoreCase("hubreassign")) {
                        hm.put("NewHubId", newHubId);
                        hm.put("CurrentHubId", currentHubId);
                        hm.put("currentRoleName", "ClaimOrphan");
                     } else if (reqObj.getOperationType().equalsIgnoreCase("unassign")) {
                        hm.put("currentRoleName", "ClaimOrphan");
                     }

                     BLServer blServer = BLClientUtil.getBizLogicServer();
                     logger.info("RGICL_CMS_WSCI.updateClaim() get BL server");

                     try {
                        blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
                        ProcessInstance pi = blServer.getProcessInstance(blsession, proccessInstanceName);
                        pi.updateSlotValue(hm);
                        pi.save();
                     } catch (RemoteException var54) {
                        responseCode = 5035;
                        logger.error("RGICL_CMS_WSCI.updateClaim() Error while updating hub id details " + var54.getMessage());
                     } finally {
                        this.disconnect(sessionId);
                        this.disconnectBlSession(blsession);
                     }
                  }
               } else {
                  responseCode = 5013;
               }
            } else {
               responseCode = 5010;
            }
         }

         resObj.setProcessInstanceName(proccessInstanceName);
         resObj.setStatus(Status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setResponseCode(responseCode);
         logger.info("RGICL_CMS_WSCI.updateClaim() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "ClaimIntimation_V5", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WSCI.updateClaim() SavvionAuditCount is " + rowcount);
            } catch (Exception var53) {
               logger.error("RGICL_CMS_WSCI.updateClaim() insertSavvionAuditData Exception" + var53.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject getWorkItemListNew(String piName) {
      logger.info("RGICL_CMS_WS.getWorkItemListNew () session::, piName::" + piName + ", wsName::");
      String[] workitemList = null;
      ResponseObject resObj = null;
      boolean isInstanceCompleted = false;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         String sessionId = this.connectUser("rgicl");
         isInstanceCompleted = this.isInstanceCompleted(blsession, piName);
         resObj = new ResponseObject();
         if (isInstanceCompleted) {
            resObj.setInstanceCompleted(isInstanceCompleted);
            resObj.setResponseCode(5000);
         } else {
            workitemList = this.getActiveWorkStepList(sessionId, piName);
            logger.info(String.valueOf(workitemList.length));
            WorkItemObject[] workItemObjects = new WorkItemObject[workitemList.length];

            for(int i = 0; i < workitemList.length; ++i) {
               WorkItemObject a = new WorkItemObject();
               a.setWorkItemName(piName + "::" + workitemList[i]);
               a.setWorkStepName(workitemList[i]);
               workItemObjects[i] = a;
            }

            resObj.setWorkItemObjects(workItemObjects);
            resObj.setProcessInstanceName(piName);
            resObj.setInstanceCompleted(isInstanceCompleted);
            resObj.setResponseCode(5000);
            resObj.setStatus("");
         }

         blserver.disConnect(blsession);
      } catch (AxisFault var11) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Axis fault Exception " + var11.getMessage());
      } catch (RemoteException var12) {
         logger.error("RGICL_CMS_WS.getWorkItemList() RemoteException " + var12.getMessage());
      } catch (EJBException var13) {
         logger.error("RGICL_CMS_WS.getWorkItemList() EJBException " + var13.getMessage());
      } catch (Exception var14) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Exception " + var14.getMessage());
      }

      return resObj;
   }

   public ResponseObject updateClaimIntimation(RequestObjectClaimIntimation reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateClaimIntimation() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = null;
      Session blsession = null;
      String claimRefNo = "";
      String status = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         if (reqObj == null) {
            responseCode = 5038;
            resObj.setResponseCode(responseCode);
            logger.error("RGICL_CMS_WSCI.updateClaimIntimation() request object is null");
         } else {
            String CurrentCMId = reqObj.getCurrentCMId();
            String NewHubId = reqObj.getNewHubId();
            String CurrentHubId = reqObj.getCurrentHubId();
            String workItemName = reqObj.getWorkItemName();
            String userId = reqObj.getUserId();
            processInstanceName = reqObj.getProcessInstanceName();

            try {
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && CurrentCMId != null && !CurrentCMId.equals("") && NewHubId != null && !NewHubId.equals("") && CurrentHubId != null && !CurrentHubId.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     BLServer blserver = BLClientUtil.getBizLogicServer();
                     logger.info("RGICL_CMS_WSCI.updateClaimIntimation() get BL server");
                     blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap<String, String> hm = new HashMap();
                     hm.put("CurrentCMId", CurrentCMId);
                     hm.put("NewHubId", NewHubId);
                     hm.put("CurrentHubId", CurrentHubId);
                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        logger.info("RGICL_CMS_WSCI.updateClaimIntimation() updateslotvalue");
                        Session blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                        WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                        logger.info("RGICL_CMS_WSCI.updateClaimIntimation() WorkItemAdmin");
                        if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.info("RGICL_CMS_WSCI.updateClaimIntimation() Task assigned to user");
                        }

                        logger.info("RGICL_CMS_WSCI.updateClaimIntimation() CompleteTask enterred");
                        this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        logger.info("RGICL_CMS_WSCI.updateClaimIntimation() Task Completed");
                        Thread.sleep((long)this.ThreadSleep);
                        pi.refresh();
                        isInstanceCompleted = pi.isCompleted();
                        if (!isInstanceCompleted) {
                           status = "Pending For Approval";
                        } else {
                           status = "Approve";
                        }

                        sessionId = this.connectUser("rgicl");
                        String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                        logger.info("RGICL_CMS_WSCI.updateClaimIntimation() workStepList is " + workStepList);
                        String roleName = null;
                        if (workStepList != null && workStepList.length > 0) {
                           workItemObjects = new WorkItemObject[workStepList.length];

                           for(int i = 0; i < workStepList.length; ++i) {
                              String workStepName = workStepList[i];
                              WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                              logger.info("RGICL_CMS_WSCI.updateClaimIntimation() sessionid-" + sessionId + ",processInstanceName-" + processInstanceName + ",workStepName-" + workStepName);
                              List wiList = workitemList.getList();
                              if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                 WorkItemObject wiObject = new WorkItemObject();
                                 wi = (WorkItem)wiList.get(0);
                                 String wiName = wi.getName();
                                 String performer = wi.getPerformer();
                                 String workItemId = String.valueOf(wi.getID());
                                 if (workStepName.equals("InvestigationByRCU")) {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameRCU");
                                 } else if (!workStepName.equals("ClaimInOrphanPool") && !workStepName.equals("ClaimOwner") && !workStepName.equals("ClaimCommonPool")) {
                                    if (workStepName.equals("ExternalSurveyor")) {
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameES");
                                    } else if (workStepName.equals("SpotSurveyor")) {
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameSS");
                                    } else if (workStepName.equals("BSMVerifiesPolicy")) {
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameBSM");
                                    } else if (workStepName.equals("RAMVerifiesPremium")) {
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameRAM");
                                    }
                                 } else {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameOwner");
                                 }

                                 claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                 logger.info("RGICL_CMS_WSCI.updateClaimIntimation() sessionid-" + sessionId + ",processInstanceName-" + processInstanceName);
                                 wiObject.setWorkStepName(workStepName);
                                 wiObject.setWorkItemName(wiName);
                                 wiObject.setPerformer(performer);
                                 wiObject.setWorkItemId(workItemId);
                                 wiObject.setroleName(roleName);
                                 logger.info("RGICL_CMS_WSCI.updateClaimIntimation() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                 workItemObjects[i] = wiObject;
                              }
                           }
                        }

                        responseCode = 5000;
                     }
                  } else {
                     responseCode = 5013;
                  }
               } else {
                  logger.info("RGICL_CMS_WSCI.updateClaimIntimation() input value is null");
                  responseCode = 5010;
               }
            } catch (Exception var37) {
               logger.error("RGICL_CMS_WSCI.updateClaimIntimation() Exception " + var37.getMessage());
               responseCode = 5035;
            } finally {
               this.disconnect(sessionId);
               this.disconnectBlSession(blsession);
            }

            resObj.setStatus(status);
            resObj.setProcessInstanceName(processInstanceName);
            resObj.setResponseCode(responseCode);
            resObj.setInstanceCompleted(isInstanceCompleted);
            resObj.setWorkItemObjects(workItemObjects);
            logger.info("RGICL_CMS_WSCI.updateClaimIntimation() isSavvionAuditEnable " + this.isSavvionAuditEnable);
            if (this.isSavvionAuditEnable) {
               try {
                  DBUtilityWSCI obj = new DBUtilityWSCI();
                  int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "ClaimIntimation_V5", "Update", reqObj.getUserId());
                  logger.info("RGICL_CMS_WSCI.updateClaimIntimation() SavvionAuditCount is " + rowcount);
               } catch (Exception var36) {
                  logger.error("RGICL_CMS_WSCI.updateClaimIntimation() insertSavvionAuditData Exception" + var36.getMessage());
               }
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForDOGeneration(RequestObjectDOGeneration reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() Called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      String processInstanceName = null;
      boolean isInstanceCompleted = false;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         WorkItemObject[] workItemObjects = null;
         String Status = null;
         int responseCode = 0;
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() request object is null");
         } else if (processName != null && !processName.equals("")) {
            if (!processName.equals("DOG")) {
               logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() Unmatching Proccess Name");
               responseCode = 5057;
            } else {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromRequestObjectDOGeneration(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() connecting as adminsessionId is" + sessionId);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  logger.trace("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() ProcessInstanceName" + processInstanceName);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() ProcessInstanceName" + processInstanceName);
                  logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() workitemList is " + workitemList);
                        List wiList = workitemList.getList();
                        logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() wiList is " + wiList);
                        if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                           WorkItemObject wiObject = new WorkItemObject();
                           WorkItem wi = (WorkItem)wiList.get(0);
                           String wiName = wi.getName();
                           String performer = wi.getPerformer();
                           String workItemId = String.valueOf(wi.getID());
                           String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                           wiObject.setWorkStepName(workStepName);
                           wiObject.setWorkItemName(wiName);
                           wiObject.setPerformer(performer);
                           wiObject.setWorkItemId(workItemId);
                           wiObject.setroleName(roleName);
                           logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " roleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }

                     responseCode = 5000;
                  }

                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        Status = "Pending For Approval";
                     } else {
                        Status = "Completed";
                     }
                  }
               } catch (Exception var32) {
                  logger.error("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() Exception " + var32.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            }
         } else {
            logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(Status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setInstanceCompleted(isInstanceCompleted);
         logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, "", "DOGeneration", "Create", "");
               logger.info("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() SavvionAuditCount is " + rowcount);
            } catch (Exception var31) {
               logger.error("RGICL_CMS_WSCI.createWorkFlowForDOGeneration() insertSavvionAuditData Exception" + var31.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateRequestObjectDOGeneration(RequestObjectUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      String claimRefNo = "";
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() request object is null");
            } else {
               String approveDecision = reqObj.getApproveDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               processInstanceName = reqObj.getProcessInstanceName();
               status = approveDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                  if (userId != null && !userId.equals("")) {
                     if (UserManager.getUser(userId) == null) {
                        logger.error("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() User id provided doesnt exists");
                        responseCode = 5015;
                     } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                        responseCode = 5054;
                        logger.error("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() UnMatched Operation Type");
                        resObj.setResponseCode(responseCode);
                     } else {
                        ResponseObject var31;
                        if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           this.updateDecision(reqObj, resObj);
                           sessionId = this.connectUser("rgicl");
                           var31 = this.requestWorkItemObject(reqObj, sessionId, processInstanceName, resObj);
                           return var31;
                        }

                        if (approveDecision == null || approveDecision.length() == 0) {
                           responseCode = 5010;
                           resObj.setResponseCode(responseCode);
                           var31 = resObj;
                           return var31;
                        }

                        BLServer blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        HashMap hm = new HashMap();
                        hm.put("approverDecision", approveDecision);
                        hm.put("ApprovalRoleID", userId);
                        if (blsession != null) {
                           pi.updateSlotValue(hm);
                           pi.save();
                           blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                           WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                           if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              logger.warn("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() Task assigned to user");
                           }

                           logger.warn("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() CompleteTask enterred");
                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.warn("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() Task Completed");
                           Thread.sleep((long)this.ThreadSleep);
                           isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                           sessionId = this.connectUser("rgicl");
                           logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() service isInstanceCompleted " + isInstanceCompleted);
                           if (!isInstanceCompleted) {
                              String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                              logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() workStepList is " + workStepList);
                              String roleName = null;
                              if (workStepList != null && workStepList.length > 0) {
                                 workItemObjects = new WorkItemObject[workStepList.length];

                                 for(int i = 0; i < workStepList.length; ++i) {
                                    String workStepName = workStepList[i];
                                    WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                                    List wiList = workitemList.getList();
                                    if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                       WorkItemObject wiObject = new WorkItemObject();
                                       wi = (WorkItem)wiList.get(0);
                                       String wiName = wi.getName();
                                       String performer = wi.getPerformer();
                                       String workItemId = String.valueOf(wi.getID());
                                       roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                                       wiObject.setWorkStepName(workStepName);
                                       wiObject.setWorkItemName(wiName);
                                       wiObject.setPerformer(performer);
                                       wiObject.setWorkItemId(workItemId);
                                       wiObject.setroleName(roleName);
                                       logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                       workItemObjects[i] = wiObject;
                                    }
                                 }
                              }

                              responseCode = 5000;
                              status = "Pending For Approval";
                           } else if (reqObj.getApproveDecision().equals("Approve")) {
                              responseCode = 5000;
                              status = "Completed";
                           } else {
                              status = "Completed";
                              responseCode = 5070;
                           }
                        }
                     }
                  } else {
                     logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var36) {
            logger.error("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() Exception " + var36);
            responseCode = 5035;
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setResponseCode(responseCode);
         logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtilityWSCI obj = new DBUtilityWSCI();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "DOGeneration", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() SavvionAuditCount is " + rowcount);
            } catch (Exception var35) {
               logger.error("RGICL_CMS_WSCI.updateRequestObjectDOGeneration() insertSavvionAuditData Exception" + var35.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject assignTaskToUser(RequestObjectAssignTaskToUser reqObj) {
      logger.info("RGICL_CMS_WSCI.assignTaskToUser() toBeAssignedUserId-" + reqObj.getToBeAssignedUserId() + ":workItemName-" + reqObj.getWorkitemName());
      ResponseObject resObj = new ResponseObject();
      String processInstanceName = null;
      String toBeAssignedUserId = null;
      String workItemName = null;
      String sessionId = null;
      BLServer blserver = null;
      Session blsession = null;
      int responseCode = 0;
      if (reqObj == null) {
         resObj.setResponseCode(5038);
         logger.info("RGICL_CMS_WSCI.assignTaskToUser() request object is null");
      } else {
         try {
            toBeAssignedUserId = reqObj.getToBeAssignedUserId();
            workItemName = reqObj.getWorkitemName();
            processInstanceName = reqObj.getProcessInstanceName();
            if (toBeAssignedUserId != null && workItemName != null && !toBeAssignedUserId.equals("") && !workItemName.equals("")) {
               sessionId = this.connectUser(toBeAssignedUserId);
               if (!sessionId.equals("false")) {
                  if (workItemName != null && !workItemName.equals("null")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                     WorkItem wi = blserver.getWorkItem(blsession, workItemName);
                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
                        wi.assign(toBeAssignedUserId);
                        wi.save();
                        logger.info("RGICL_CMS_WSCI.assignTaskToUser() workitem assigned to user successfully");
                     }
                  } else {
                     logger.info("RGICL_CMS_WSCI.assignTaskToUser() workitem is null or invalid");
                  }

                  blserver = BLClientUtil.getBizLogicServer();
                  blsession = blserver.connect(toBeAssignedUserId, this.getUserPasswordECS(toBeAssignedUserId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap<String, String> hm = new HashMap();
                  hm.put("currentApproverId", toBeAssignedUserId);
                  if (blsession != null) {
                     pi.updateSlotValue(hm);
                     pi.save();
                  }

                  responseCode = 5000;
               } else {
                  responseCode = 5013;
                  logger.info("RGICL_CMS_WSCI.assignTaskToUser() user is invalid or coudn't get session for user");
               }
            } else {
               responseCode = 5010;
               logger.info("RGICL_CMS_WSCI.assignTaskToUser() input value is null");
            }
         } catch (Exception var15) {
            responseCode = 5035;
            logger.error("RGICL_CMS_WSCI.assignTaskToUser() Exception " + var15.getMessage());
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
         }
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   private WorkItemList getWorkItemList(String sessionId, String piName, String wsName) {
      logger.info("RGICL_CMS_WSCI.getWorkItemList() session::" + sessionId + ", piName::" + piName + ", wsName::" + wsName);
      WorkItemList workitemList = null;

      try {
         workitemList = getBizLogicManager().getWorkItemList(sessionId, piName, wsName);
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WSCI.getWorkItemList() Axis Fault Exception " + var6.getMessage());
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WSCI.getWorkItemList() RemoteException " + var7.getMessage());
      } catch (Exception var8) {
         logger.error("RGICL_CMS_WSCI.getWorkItemList() Exception " + var8.getMessage());
      }

      return workitemList;
   }

   private String getWorkItemId(String session, String wiName) {
      logger.info("RGICL_CMS_WSCI.getWorkItemId() wiName is " + wiName);
      String workItemId = null;
      PAKClientWorkitem PAKClientWorkitem = null;

      try {
         PAKClientWorkitem = getBizLogicManager().getWorkitem(session, wiName);
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WSCI.getWorkItemId() AxisFault " + var6.getMessage());
         var6.printStackTrace();
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WSCI.getWorkItemId() RemoteException " + var7.getMessage());
         var7.printStackTrace();
      } catch (Exception var8) {
         logger.error("RGICL_CMS_WSCI.getWorkItemId() Exception " + var8.getMessage());
         var8.printStackTrace();
      }

      if (PAKClientWorkitem != null) {
         workItemId = String.valueOf(PAKClientWorkitem.getWorkItemID());
      }

      logger.info("RGICL_CMS_WSCI.getWorkItemId() workItemId is" + workItemId);
      return workItemId;
   }

   private String getDataslotValue(String sessionId, String processInstanceName, String dsName) {
      String dataSlotValue = null;
      logger.info("RGICL_CMS_WSCI.getDataslotValue() dsName is " + dsName);

      try {
         dataSlotValue = (String)getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dsName);
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WSCI.getDataslotValue() AxisFault " + var6.getMessage());
         var6.printStackTrace();
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WSCI.getDataslotValue() RemoteException " + var7.getMessage());
         var7.printStackTrace();
      } catch (Exception var8) {
         logger.error("RGICL_CMS_WSCI.getDataslotValue() Exception " + var8.getMessage());
         var8.printStackTrace();
      }

      return dataSlotValue;
   }

   private String[] getActiveWorkStepList(String sessionId, String piName) {
      String[] workStepList = null;
      logger.info("RGICL_CMS_WSCI.getActiveWorkStepList() called");

      try {
         workStepList = getBizLogicManager().getProcessInstanceActiveWorkstepList(sessionId, piName);
      } catch (AxisFault var5) {
         logger.error("RGICL_CMS_WSCI.getActiveWorkStepList() AxisFault Exception" + var5.getMessage());
      } catch (RemoteException var6) {
         logger.error("RGICL_CMS_WSCI.getActiveWorkStepList() RemoteException" + var6.getMessage());
      } catch (Exception var7) {
         logger.error("RGICL_CMS_WSCI.getActiveWorkStepList() Exception" + var7.getMessage());
      }

      return workStepList;
   }

   public ResponseObject makeAvailableTask(RequestObject[] reqObjs) {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      String[] workItemNames = null;
      BLServer blserver = null;
      Session blsession = null;
      int responseCode = 0;
     
      if (reqObjs == null) {
         responseCode = 5038;
         resObj.setResponseCode(responseCode);
      } else {
         try {
            HashMap hashMap = this.getMap(reqObjs);
            workItemNames = (String[])hashMap.get("WORKITEMNAMES");
            if (workItemNames != null && !workItemNames.equals("")) {
               for(int index = 0; index < workItemNames.length; ++index) {
                  if (workItemNames[index] != null && !workItemNames.equals("null")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                     WorkItem wi = blserver.getWorkItem(blsession, workItemNames[index]);
                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
                        wi.makeAvailable();
                        wi.save();
                     }
                  }
               }

               responseCode = 5000;
            } else {
               responseCode = 5010;
            }
         } catch (Exception var13) {
            logger.error("RGICL_CMS_WSCI.makeAvailableTask() Exception " + var13.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnectBlSession(blsession);
         }

         resObj.setResponseCode(responseCode);
      }

      return resObj;
   }

   public String getGreeting(String name) {
      String result = "Hello ! " + name;
      return result;
   }

   private boolean isInstanceCompleted(Session blsession, String processInstanceName) {
      boolean isInstanceCompleted = false;
      BLServer blserver = BLClientUtil.getBizLogicServer();

      try {
         boolean b = blserver.isProcessInstanceExist(blsession, processInstanceName);
         logger.info("RGICL_CMS_WSCI.isInstanceCompleted() is processInstanceName " + processInstanceName + " Exist " + b);
         if (!b) {
            isInstanceCompleted = true;
         }
      } catch (Exception var6) {
         logger.error("RGICL_CMS_WSCI.isInstanceCompleted() Exception in isinstancecompleted " + var6.getMessage());
         isInstanceCompleted = true;
      }

      return isInstanceCompleted;
   }

   private HashMap getMap(RequestObject[] reqObjs) {
      HashMap hm = new HashMap();
      int arrayObjLength = reqObjs.length;

      try {
         if (arrayObjLength != 0) {
            for(int i = 0; i < arrayObjLength; ++i) {
               String key = reqObjs[i].getKey();
               Object value = null;
               if (key != null) {
                  if (reqObjs[i].getValue() != null) {
                     value = reqObjs[i].getValue();
                  } else if (reqObjs[i].getValueArray() != null) {
                     value = reqObjs[i].getValueArray();
                  }
               }

               hm.put(key, value);
            }
         }
      } catch (Exception var7) {
         logger.error("RGICL_CMS_WSCI.getMap() Exception " + var7.getMessage());
      }

      return hm;
   }

   private static String connectECSADMIN() {
      boolean valid = false;

      try {
         valid = getBizLogicManager().isSessionValid(ECSADMIN);
      } catch (RemoteException var4) {
         logger.error("RGICL_CMS_WS.connectECSADMIN() RemoteException " + var4.getMessage());
      }

      synchronized(bytearrayECSADMIN) {
         if (ECSADMIN == null || !valid) {
            try {
               ECSADMIN = getBizLogicManager().connect("rgicl", "rgicl");
            } catch (Exception var3) {
               ECSADMIN = "false";
               logger.error("RGICL_CMS_WS.connectECSADMIN() Exception " + var3.getMessage());
            }
         }
      }

      return ECSADMIN;
   }

   private static BizLogicManager getBizLogicManager() throws AxisFault {
      if (pak == null) {
         synchronized(bytearray) {
            if (pak == null) {
               try {
                  String appserver = ServiceLocator.self().getAppServerID();
                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
                  pak = blmhome.create();
               } catch (Throwable var3) {
                  logger.error("RGICL_CMS_WSCI.getBizLogicManager() Exception " + var3);
                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         logger.info("RGICL_CMS_WSCI.createProcessInstance() sessionId-->" + sessionId + "ptName-->" + ptName + "piName-->" + piName + "priority-->" + priority + "h-->" + h);
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (Exception var8) {
         logger.error("RGICL_CMS_WSCI.createProcessInstance() Exception " + var8.getMessage());
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         logger.error("RGICL_CMS_WSCI.assignWorkitem() RemoteException " + var4.getMessage());
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private String connectUser(String userId) {
      String sessionId = "false";
      String password = null;

      try {
         password = this.getUserPasswordECS(userId);
         sessionId = getBizLogicManager().connect(userId, password);
      } catch (RemoteException var5) {
         sessionId = "false" + var5.getMessage();
      }

      return sessionId;
   }

   private String getUserPasswordECS(String userId) {
      String password = "";

      try {
         User userObject = UserManager.getUser(userId);
         password = userObject.getAttribute("password");
         PService p = PService.self();
         password = p.decrypt(password);
      } catch (Exception var5) {
         logger.error("RGICL_CMS_WSCI.getUserPasswordECS() Exception " + var5.getMessage());
      }

      return password;
   }

   private boolean checkUserECS(String userId, String groupName) {
      boolean isMember = false;

      try {
         User usr = UserManager.getUser(userId);
         String[] groupNames = usr.getAllGroupNames();

         for(int i = 0; i < groupNames.length; ++i) {
            if (groupNames[i].equals(groupName)) {
               isMember = true;
            }
         }
      } catch (Exception var7) {
         logger.error("RGICL_CMS_WSCI.checkUserECS() Exception " + var7);
      }

      return isMember;
   }

   private String getWorkItemPerformerECS(String workItemName) {
      String result = null;

      try {
         if (workItemName != null && !workItemName.equals("")) {
            result = (String)getBizLogicManager().getWorkitemInfo(this.connectUser("rgicl"), workItemName).get("PERFORMER");
         } else {
            result = Integer.valueOf(5010).toString();
         }

         return result;
      } catch (Exception var4) {
         logger.error("RGICL_CMS_WSCI.getWorkItemPerformerECS() Exception " + var4.getMessage());
         return Integer.valueOf(5020).toString();
      }
   }

   private Hashtable getHashTableFromOnAccountApprovalPaymentRequestObject(RequestObjectOnAccountApprovalPayment reqObj) {
      Hashtable resolvedValues = new Hashtable();
      logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() called");
      Field[] fields = RequestObjectOnAccountApprovalPayment.class.getFields();
      logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();
         logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() number of field name " + fieldName);

         try {
            fieldValue = (String)fields[i].get(reqObj);
         } catch (IllegalArgumentException var8) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() IllegalArgumentException " + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() IllegalAccessException " + var9.getMessage());
         } catch (Exception var10) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromOnAccountApprovalPaymentRequestObject() Exception " + var10.getMessage());
         }

         logger.info("Inside getHashTable method putting fielname and value as " + fieldName + " , " + fieldValue);
         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromCorrectionPayeeNameRequestObject(RequestObjectCorrectionPayeeName reqObj) {
      Hashtable resolvedValues = new Hashtable();
      logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() called");
      Field[] fields = RequestObjectCorrectionPayeeName.class.getFields();
      logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();
         logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() number of field name " + fieldName);

         try {
            fieldValue = (String)fields[i].get(reqObj);
            logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() number of field value " + fieldValue);
         } catch (IllegalArgumentException var8) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() IllegalArgumentException " + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() IllegalAccessException " + var9.getMessage());
         } catch (Exception var10) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromCorrectionPayeeNameRequestObject() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromRequestObjectDOGeneration(RequestObjectDOGeneration reqObj) {
      Hashtable resolvedValues = new Hashtable();
      logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() called");
      Field[] fields = RequestObjectDOGeneration.class.getFields();
      logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();
         logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() number of field name " + fieldName);

         try {
            fieldValue = (String)fields[i].get(reqObj);
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() number of field value " + fieldValue);
         } catch (IllegalArgumentException var8) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() IllegalArgumentException " + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() IllegalAccessException " + var9.getMessage());
         } catch (Exception var10) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectDOGeneration() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromRequestObjectAllRequest(RequestObjectAllRequests reqObj) {
      Hashtable resolvedValues = new Hashtable();
      logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() called");
      Field[] fields = RequestObjectAllRequests.class.getFields();
      logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();
         logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() number of field name " + fieldName);

         try {
            fieldValue = (String)fields[i].get(reqObj);
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() number of field value " + fieldValue);
         } catch (IllegalArgumentException var8) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() IllegalArgumentException " + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() IllegalAccessException " + var9.getMessage());
         } catch (Exception var10) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromRequestObjectAllRequest() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromClaimIntimationRequestObject(RequestObjectClaimIntimation reqObj) {
      Hashtable resolvedValues = new Hashtable();
      logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() called");
      Field[] fields = RequestObjectClaimIntimation.class.getFields();
      logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqObj);
            logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() field name " + fieldName + "  field value " + fieldValue);
         } catch (IllegalArgumentException var8) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() IllegalArgumentException " + var8);
         } catch (IllegalAccessException var9) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() IllegalAccessException " + var9);
         } catch (Exception var10) {
            logger.info("RGICL_CMS_WSCI.getHashTableFromClaimIntimationRequestObject() Exception " + var10);
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         logger.info("RGICL_CMS_WSCI.completeWorkitem() Exception " + var4);
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private boolean disconnect(String sessionId) {
      try {
         if (sessionId != null) {
            getBizLogicManager().disconnect(sessionId);
         }

         return true;
      } catch (Exception var3) {
         logger.error("RGICL_CMS_WSCI.disconnect() Exception " + var3.getMessage());
         return false;
      }
   }

   private boolean disconnectBlSession(Session blsession) {
      try {
         if (blsession != null) {
            BLServer blserver = BLClientUtil.getBizLogicServer();
            blserver.disConnect(blsession);
         }

         return true;
      } catch (Exception var3) {
         logger.error("RGICL_CMS_WSCI.disconnectBlSession() Exception " + var3.getMessage());
         return false;
      }
   }

   private String getDataSlotValue(String sessionId, String processInstanceName, String dataSlotName) throws AxisFault, RemoteException {
      String value = null;
      value = (String)getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dataSlotName);
      return value;
   }

   private boolean isAuthenticated(AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WSCI.isAuthenticated() called");
      logger.info("RGICL_CMS_WSCI.isAuthenticated() authenticationDetail is " + authenticationDetail);
      if (authenticationDetail != null) {
         if ("RGICL_CMS_USER".equals(authenticationDetail.getAuthenticationId()) && "RGICL_CMS_PASS".equals(authenticationDetail.getAuthenticationPassword())) {
            logger.info("RGICL_CMS_WSCI.isAuthenticated() authentication success");
            return true;
         } else {
            logger.info("RGICL_CMS_WSCI.isAuthenticated() authentication failure");
            return false;
         }
      } else {
         logger.info("RGICL_CMS_WSCI.isAuthenticated() authentication failure");
         return false;
      }
   }

   public ResponseObject getWorkItemListDB(String piName) {
      logger.info("RGICL_CMS_WS.getWorkItemListDB () session::, piName::" + piName + ", wsName::");
      String[] workitemList = null;
      ResponseObject resObj = null;
      boolean isInstanceCompleted = true;
      String sqlString = "Select workitem_name from bizlogic_workitem where process_instance_id=?";
      String sqlString1 = "Select process_instance_id from bizlogic_processinstance where process_instance_id=?";

      try {
         logger.info("RGICL_CMS_WS.getWorkItemListDB () In Try");
         if (piName != null && piName.contains("#")) {
            String[] strPidArr = piName.split("#");
            String strPiid = strPidArr[1];
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            this.connection = this.ds.getConnection();
            this.pstmt = this.connection.prepareStatement(sqlString);
            this.pstmt.setLong(1, Long.valueOf(strPiid));
            this.rs = this.pstmt.executeQuery();
            List<String> ls = new ArrayList();
            logger.info("RGICL_CMS_WS.getWorkItemListDB () OBJ Init");

            while(this.rs.next()) {
               ls.add(this.rs.getString("workitem_name"));
            }

            this.pstmt = this.connection.prepareStatement(sqlString1);
            this.pstmt.setLong(1, Long.valueOf(strPiid));
            this.rs = this.pstmt.executeQuery();
            logger.info("RGICL_CMS_WS.getWorkItemListDB () quert exicute");

            long pid;
            for(pid = 0L; this.rs.next(); pid = this.rs.getLong("process_instance_id")) {
            }

            if (ls.size() > 0 && pid > 0L) {
               isInstanceCompleted = false;
               workitemList = (String[])ls.toArray(new String[ls.size()]);
            }

            if (this.rs != null) {
               this.rs.close();
            }

            if (this.pstmt != null) {
               this.pstmt.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }

            resObj = new ResponseObject();
            if (isInstanceCompleted) {
               resObj.setInstanceCompleted(isInstanceCompleted);
               resObj.setResponseCode(5000);
            } else {
               logger.info(String.valueOf(workitemList.length));
               WorkItemObject[] workItemObjects = new WorkItemObject[workitemList.length];

               for(int i = 0; i < workitemList.length; ++i) {
                  WorkItemObject a = new WorkItemObject();
                  a.setWorkItemName(workitemList[i]);
                  String[] arr = workitemList[i].split("::");
                  a.setWorkStepName(arr[1]);
                  workItemObjects[i] = a;
                  logger.info("RGICL_CMS_WS.getWorkItemListDB () in for :" + workitemList[i]);
               }

               resObj.setWorkItemObjects(workItemObjects);
               resObj.setProcessInstanceName(piName);
               resObj.setInstanceCompleted(isInstanceCompleted);
               resObj.setResponseCode(5000);
               resObj.setStatus("");
            }

            logger.info("RGICL_CMS_WS.getWorkItemListDB () Done");
         }
      } catch (Exception var32) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Exception " + var32.getMessage());
      } finally {
         try {
            if (this.rs != null) {
               this.rs.close();
            }
         } catch (SQLException var31) {
         }

         try {
            if (this.pstmt != null) {
               this.pstmt.close();
            }
         } catch (SQLException var30) {
         }

         try {
            if (this.connection != null) {
               this.connection.close();
            }
         } catch (SQLException var29) {
         }

      }

      return resObj;
   }
}
