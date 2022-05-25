import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSProcessInstance;
import com.savvion.sbm.bizlogic.client.queryservice.QSProcessInstanceFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSProcessInstanceRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.Application;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.server.svo.QSProcessInstanceData;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.BizLogicClientException;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.Realm;
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
import java.util.Iterator;
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
import rgiclcms.cms.savvion.RequestObjectTechnicalApprovalVS;
import rgiclcms.cms.savvion.dbUtil.DBUtil;
import rgiclcms.cms.savvion.dbUtil.DBUtility;
import rgiclcms.cms.savvion.policy.objectmodel.AuthenticationDetail;
import rgiclcms.cms.savvion.policy.objectmodel.FlowDetails;
import rgiclcms.cms.savvion.policy.objectmodel.Info;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObject;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectAutoboticsTechnicalFlow;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectAutoboticsTechnicalUpdate;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectFinancialApproval;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectFinancialUpdate;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectMakerChecker;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectSurveyorAppointmentApproval;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectSurveyorExpensePayment;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectSurveyorExpensePaymentUpdate;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectTechnicalApproval;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectTechnicalApprovalUpdate;
import rgiclcms.cms.savvion.policy.objectmodel.ResponseObject;
import rgiclcms.cms.savvion.policy.objectmodel.WorkItemObject;

public class RGICL_CMS_WS {
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
   private static final int SUCCESS = 5000;
   private static final int INPUT_VALUE_IS_NEGATIVE = 5009;
   private static final int INPUT_VALUE_IS_NULL = 5010;
   private static final int USER_ALREADY_MAPPED = 5011;
   private static final int USER_NOT_MAPPED = 5012;
   private static final int USER_ID_INVALID = 5013;
   private static final int USER_ALREADY_CREATED = 5014;
   private static final int USER_NOT_PRESENT = 5015;
   private static final int FIELD_NOT_FOUND = 5016;
   private static final int INVALID_WINAME = 5020;
   private static final int FAILURE_EXCEPTION = 5035;
   private static final int REQUEST_OBJECT_IS_NULL = 5038;
   private static final int NOT_AUTHENTICATED = 5040;
   private static final int GROUP_DOESNT_EXIST = 5041;
   private static final int USER_DOESNT_EXIST = 5042;
   private static final int EXSISTINGGROUP_AND_NEWGROUP_NAMES_ARE_SAME = 5043;
   private static final int UPDATING_USERID_NOT_ALLOWED = 5044;
   private static final int PENDING_FOR_APPROVAL = 5045;
   private static final int PENDING_FOR_APPROVAL_WITH_ZM = 5046;
   private static final int PENDING_FOR_APPROVAL_WITH_HM = 5047;
   private static final int PENDING_FOR_APPROVAL_WITH_CO = 5048;
   private static final int PENDING_FOR_APPROVAL_WITH_VH = 5049;
   private static final int PENDING_FOR_APPROVAL_WITH_CH = 5050;
   private static final int SURVEYOR_APPOINTMENT_SUCCESS = 5051;
   static final String ECSAdminUserName = "rgicl";
   static final String ECSAdminPassword = "rgicl";
   private static final int UNMATCHED_OPERATION_TYPE = 5054;
   private static final int WORKITEM_ALREADY_UNASSIGNED = 5055;
   private static final int SUVERYOR_UNAPPOINTED = 5056;
   private static final int UNMATCHING_PROCESS_NAME = 5057;
   private static final int INVALID_INITIATOR_ROLE = 5058;
   private static final int ASSIGNED_SUCCESSFULLY = 5065;
   private static final int REASSIGNED_SUCCESSFULLY = 5066;
   private static final int UNASSIGNED_SUCCESSFULLY = 5067;
   private static final int REJECTED = 5070;
   private static final int PENDING_FOR_APPROVAL_WITH_CHECKER = 5071;
   private static final int PENDING_FOR_APPROVAL_WITH_MAKER = 5072;
   private static final int SESSION_INVALID = 5073;
   boolean isSavvionAuditEnable = false;
   int ThreadSleep = 0;
   private static Logger logger = null;
   final String SBM_HOME = System.getProperty("sbm.home");
   final String CMS_SAVVION_LOG_PROPERTIES;
   final String RGICL_CMS_PROPERTIES;
   Properties propertiesCMSSavvion;
   Properties propertiesCMSLog;
   private String dshubId;
   private String dszoneId;
   private String dsCrn;
   private String dsProductId;
   Properties props_rgiclcms;

   public RGICL_CMS_WS() {
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
         logger.info("RGICL_CMS_WS.RGICL_CMS_WS() Exception " + var2.getMessage());
      }

   }

   public ResponseObject createWorkFlowForTechnicalApproval(RequestObjectTechnicalApproval reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() called");
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
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String status = null;
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() request object is null");
         } else if (processName != null && reqObj.getClaimReferenceNumber() != null && reqObj.getProductId() != null && reqObj.getHubId() != null && reqObj.getTeamId() != null && reqObj.getZoneId() != null && reqObj.getEstimatedValue() != null && reqObj.getTLCount() != null && !processName.equals("") && !reqObj.getClaimReferenceNumber().equals("") && !reqObj.getProductId().equals("") && !reqObj.getHubId().equals("") && !reqObj.getTeamId().equals("") && !reqObj.getTLCount().equals("") && !reqObj.getZoneId().equals("") && !reqObj.getEstimatedValue().equals("") && reqObj.getSurveyorType() != null && !reqObj.getSurveyorType().equals("") && reqObj.getApproverLimit() != null && !reqObj.getApproverLimit().equals("")) {
            if (!(Double.parseDouble(reqObj.getApproverLimit()) < 0.0D) && !(Double.parseDouble(reqObj.getEstimatedValue()) < 0.0D)) {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromObject(reqObj);
                  sessionId = this.connectUser("rgicl");
                  resolvedDSValues.put("TLCount", reqObj.getTLCount());
                  logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() creating processInstance resolvedDSValues " + resolvedDSValues);
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        List wiList = workitemList.getList();
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
                           wiObject.setRoleName(roleName);
                           wiObject.setClaimReferenceNumber(reqObj.getClaimReferenceNumber());
                           logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }
                  }

                  logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() Got the activeWorkItemNameForInstance");
                  responseCode = 5000;
                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "status");
                     } else {
                        status = "Approved";
                        responseCode = 5000;
                     }
                  }
               } catch (Exception var32) {
                  logger.error("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() exception occured" + var32.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            } else {
               logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() input value is negative");
               responseCode = 5009;
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "TechnicalApprovalFlow", "Create", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() SavvionAuditCount is " + rowcount);
            } catch (Exception var31) {
               logger.error("RGICL_CMS_WS.createWorkFlowForTechnicalApproval() insertSavvionAuditData Exception" + var31.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForVSTechnicalApproval(RequestObjectTechnicalApproval reqObj, String processName, AuthenticationDetail authenticationDetail) {
      System.out.println("vs flow called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      String processInstanceName = null;
      boolean isInstanceCompleted = false;
      String InitUserID = "";
      String InitRole = "";
      String VSUserID = "";
      String VSRole = "";
      String[] arrUser = reqObj.getInitiatorId().split(",");
      String[] arrRole = reqObj.getInitiatorRole().split(",");
      InitUserID = arrUser[0];
      if (arrUser.length > 1) {
         VSUserID = arrUser[1];
      }

      InitRole = arrRole[0];
      if (arrRole.length > 1) {
         VSRole = arrRole[1];
      }

      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String status = null;
         
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() request object is null");
         } else if (processName != null && reqObj.getClaimReferenceNumber() != null && reqObj.getProductId() != null && reqObj.getHubId() != null && reqObj.getTeamId() != null && reqObj.getZoneId() != null && reqObj.getEstimatedValue() != null && !processName.equals("") && !reqObj.getClaimReferenceNumber().equals("") && !reqObj.getProductId().equals("") && !reqObj.getHubId().equals("") && !reqObj.getTeamId().equals("") && !reqObj.getZoneId().equals("") && !reqObj.getEstimatedValue().equals("") && reqObj.getSurveyorType() != null && !reqObj.getSurveyorType().equals("") && reqObj.getApproverLimit() != null && !reqObj.getApproverLimit().equals("")) {
            if (!(Double.parseDouble(reqObj.getApproverLimit()) < 0.0D) && !(Double.parseDouble(reqObj.getEstimatedValue()) < 0.0D)) {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromObject(reqObj);
                  if (VSUserID != "") {
                     if (resolvedDSValues.containsKey("VSUserID")) {
                        resolvedDSValues.remove("VSUserID");
                     }

                     resolvedDSValues.put("VSUserID", VSUserID);
                  }

                  if (VSRole != "") {
                     if (resolvedDSValues.containsKey("VSRole")) {
                        resolvedDSValues.remove("VSRole");
                     }

                     resolvedDSValues.put("VSRole", VSRole);
                  }

                  resolvedDSValues.put("initiatorId", InitUserID);
                  resolvedDSValues.put("initiatorRole", InitRole);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() creating processInstance resolvedDSValues " + resolvedDSValues);
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        List wiList = workitemList.getList();
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
                           wiObject.setRoleName(roleName);
                           wiObject.setClaimReferenceNumber(reqObj.getClaimReferenceNumber());
                           logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }
                  }

                  logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() Got the activeWorkItemNameForInstance");
                  responseCode = 5000;
                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "status");
                     } else {
                        status = "Approved";
                        responseCode = 5000;
                     }
                  }
               } catch (Exception var38) {
                  logger.error("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() exception occured" + var38.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            } else {
               logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() input value is negative");
               responseCode = 5009;
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() input value is null");
            responseCode = 5010;
         }

         System.out.println("vs flow done");
         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "TechnicalApprovalFlow", "Create", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() SavvionAuditCount is " + rowcount);
            } catch (Exception var37) {
               System.out.println("vs flow e");
               logger.error("RGICL_CMS_WS.createWorkFlowForVSTechnicalApproval() insertSavvionAuditData Exception" + var37.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForAutoboticsTechnicalFlow(RequestObjectAutoboticsTechnicalFlow reqObj, String processName, AuthenticationDetail authenticationDetail) {
      System.out.println("Autobotics flow called");
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
         int responseCode = 0;
         WorkItemObject[] workItemObjects = null;
         String status = null;
         
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() request object is null");
         } else if (processName != null && reqObj.getClaimReferenceNumber() != null && reqObj.getProductId() != null && reqObj.getHubId() != null && reqObj.getTeamId() != null && reqObj.getZoneId() != null && reqObj.getEstimatedValue() != null && !processName.equals("") && !reqObj.getClaimReferenceNumber().equals("") && !reqObj.getProductId().equals("") && !reqObj.getHubId().equals("") && !reqObj.getTeamId().equals("") && !reqObj.getZoneId().equals("") && !reqObj.getEstimatedValue().equals("") && reqObj.getSurveyorType() != null && !reqObj.getSurveyorType().equals("") && reqObj.getApproverLimit() != null && !reqObj.getApproverLimit().equals("")) {
            if (!(Double.parseDouble(reqObj.getApproverLimit()) < 0.0D) && !(Double.parseDouble(reqObj.getEstimatedValue()) < 0.0D)) {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromObjectVS(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() creating processInstance resolvedDSValues " + resolvedDSValues);
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList != null) {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        List wiList = workitemList.getList();
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
                           wiObject.setRoleName(roleName);
                           wiObject.setClaimReferenceNumber(reqObj.getClaimReferenceNumber());
                           logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                           workItemObjects[i] = wiObject;
                        }
                     }
                  }

                  logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() Got the activeWorkItemNameForInstance");
                  responseCode = 5000;
                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "status");
                     } else {
                        status = "Approved";
                        responseCode = 5000;
                     }
                  }
               } catch (Exception var32) {
                  logger.error("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() exception occured" + var32.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            } else {
               logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() input value is negative");
               responseCode = 5009;
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() input value is null");
            responseCode = 5010;
         }

         System.out.println("Autobotics flow done");
         resObj.setStatus(status);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "TechnicalApprovalFlow", "Create", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() SavvionAuditCount is " + rowcount);
            } catch (Exception var31) {
               System.out.println("vs flow e");
               logger.error("RGICL_CMS_WS.createWorkFlowForAutoboticsTechnicalFlow() insertSavvionAuditData Exception" + var31.getMessage());
            }
         }

         return resObj;
      }
   }

   public WorkItemObject[] getWorkItemObjects(String processInstanceName) {
      String sessionId = null;
      String[] workStepList = null;
      WorkItemObject[] workItemObjects = null;

      try {
         sessionId = this.connectUser("rgicl");
         workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
         if (workStepList != null) {
            workItemObjects = new WorkItemObject[workStepList.length];

            for(int i = 0; i < workStepList.length; ++i) {
               String workStepName = workStepList[i];
               WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
               List wiList = workitemList.getList();
               if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                  WorkItemObject wiObject = new WorkItemObject();
                  WorkItem wi = (WorkItem)wiList.get(0);
                  String wiName = wi.getName();
                  String performer = wi.getPerformer();
                  String workItemId = String.valueOf(wi.getID());
                  String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                  String claimReferenceNumber;
                  String productId;
                  String hubId;
                  String zoneId;
                  if (processInstanceName.contains("FinancialApprovalFlow")) {
                     claimReferenceNumber = this.getDataSlotValue(sessionId, processInstanceName, "ClaimReferenceNumber");
                     productId = this.getDataSlotValue(sessionId, processInstanceName, "ProductId");
                     hubId = this.getDataSlotValue(sessionId, processInstanceName, "HubId");
                     zoneId = this.getDataSlotValue(sessionId, processInstanceName, "ZoneId");
                  } else {
                     claimReferenceNumber = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                     productId = this.getDataSlotValue(sessionId, processInstanceName, "productId");
                     hubId = this.getDataSlotValue(sessionId, processInstanceName, "hubId");
                     if (processInstanceName.contains("CorrectionPayeeName")) {
                        zoneId = this.getDataSlotValue(sessionId, processInstanceName, "ZoneId");
                     } else {
                        zoneId = this.getDataSlotValue(sessionId, processInstanceName, "zoneId");
                     }
                  }

                  wiObject.setWorkStepName(workStepName);
                  wiObject.setWorkItemName(wiName);
                  wiObject.setPerformer(performer);
                  wiObject.setWorkItemId(workItemId);
                  wiObject.setRoleName(roleName);
                  wiObject.setClaimReferenceNumber(claimReferenceNumber);
                  wiObject.setProductId(productId);
                  wiObject.setHubId(hubId);
                  wiObject.setZoneId(zoneId);
                  logger.info("RGICL_CMS_WS.getWorkItemObjects()  workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + ", userRoleName-" + roleName + ", claimReferenceNumber-" + claimReferenceNumber + ", productId-" + productId + ", hubId-" + hubId + ", zoneId-" + zoneId);
                  workItemObjects[i] = wiObject;
               }
            }
         }
      } catch (Exception var22) {
         logger.error("RGICL_CMS_WS.getWorkItemObjects() Exception " + var22.getMessage());
      } finally {
         this.disconnect(sessionId);
      }

      return workItemObjects;
   }

   public ResponseObject createWorkFlowForMakerChecker(RequestObjectMakerChecker reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      boolean isInstanceCompleted = false;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      WorkItemObject[] workItemObjects = null;
      String status = null;
      int responseCode = 0;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getMasterId() != null && !reqObj.getMasterId().equals("")) {
            if (!processName.equals("")) {
               logger.error("RGICL_CMS_WS.createWorkFlowForMakerChecker() Umatching Process Name");
               responseCode = 5054;
            } else {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() processName is " + processName);
                  resolvedDSValues = this.getHashTableFromMakerCheckerRequestObject(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() connecting as adminsessionId is" + sessionId);
                  logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() getting active workstep list");
                  String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                  if (workStepList == null) {
                     responseCode = 5000;
                  } else {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     String roleName;
                     for(int i = 0; i < workStepList.length; ++i) {
                        WorkItemObject wiObject = new WorkItemObject();
                        String workStepName = workStepList[i];
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        List wiList = workitemList.getList();
                        WorkItem wi = (WorkItem)wiList.get(i);
                        String wiName = wi.getName();
                        String performer = wi.getPerformer();
                        String workItemId = String.valueOf(wi.getID());
                        roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                        wiObject.setWorkStepName(workStepName);
                        wiObject.setWorkItemName(wiName);
                        wiObject.setPerformer(performer);
                        wiObject.setWorkItemId(workItemId);
                        wiObject.setRoleName(roleName);
                        logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() workStepName-" + workStepName + ", workItemName-" + wiName + ", performer-" + performer + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                        workItemObjects[i] = wiObject;
                     }

                     roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                     if (this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus").equals("PENDING FOR APPROVAL")) {
                        if (roleName.equals("checker")) {
                           responseCode = 5071;
                        } else if (roleName.equals("maker")) {
                           responseCode = 5072;
                        } else {
                           responseCode = 5045;
                        }

                        resObj.setWorkItemObjects(workItemObjects);
                     }
                  }

                  resObj.setStatus(status);
                  resObj.setProcessInstanceName(processInstanceName);
                  resObj.setResponseCode(responseCode);
                  resObj.setWorkItemObjects(workItemObjects);
                  logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() Got the activeWorkItemNameForInstance");
                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "status");
                     } else {
                        status = "approved";
                     }
                  }
               } catch (Exception var30) {
                  logger.error("RGICL_CMS_WS.createWorkFlowForMakerChecker() exception occured " + var30);
                  resObj.setResponseCode(5035);
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForMakerChecker() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         return resObj;
      }
   }

   public ResponseObject createWorkFlowForFinancialApproval(RequestObjectFinancialApproval reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() called");

      try {
         logger.info("uday");
         logger.info(reqObj.getApproverLimitFA().toString());
      } catch (Exception var34) {
         logger.info("udayerror");
         logger.info(var34.getMessage());
      }

      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         WorkItemObject[] workItemObjects = null;
         String status = null;
         String statusCVM = "";
         int responseCode = 0;
         boolean isInstanceCompleted = false;
         
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getFinalLossAmount() != null && !reqObj.getFinalLossAmount().equals("") && reqObj.getFinalLossAmountLimitForCA() != null && !reqObj.getFinalLossAmountLimitForCA().equals("") && reqObj.getFinalLossAmountLimitForFA() != null && !reqObj.getFinalLossAmountLimitForFA().equals("") && reqObj.getRiskScore() != null && !reqObj.getRiskScore().equals("") && reqObj.getTLCount() != null && !reqObj.getTLCount().equals("") && reqObj.getRiskScoreLimitForCA() != null && !reqObj.getRiskScoreLimitForCA().equals("") && reqObj.getRiskScoreLimitForFA() != null && !reqObj.getRiskScoreLimitForFA().equals("") && reqObj.getUserAppearsInCAMaster() != null && !reqObj.getUserAppearsInCAMaster().equals("") && reqObj.getUserAppearsInFAMaster() != null && !reqObj.getUserAppearsInFAMaster().equals("") && reqObj.getJobTypeFA() != null && !reqObj.getJobTypeFA().equals("") && reqObj.getJobTypeCA() != null && !reqObj.getJobTypeCA().equals("") && reqObj.getTeamId() != null && !reqObj.getTeamId().equals("")) {
            if (reqObj.getLastTechnicalApproverRole() != null && !reqObj.getLastTechnicalApproverRole().equals("") && reqObj.getInitiatorId() != null && !reqObj.getInitiatorId().equals("") && reqObj.getInitiatorRole() != null && !reqObj.getInitiatorRole().equals("") && reqObj.getZoneId() != null && !reqObj.getZoneId().equals("") && reqObj.getProductId() != null && !reqObj.getProductId().equals("") && reqObj.getHubId() != null && !reqObj.getHubId().equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("")) {
               if (!(Double.parseDouble(reqObj.getFinalLossAmount()) < 0.0D) && !(Double.parseDouble(reqObj.getFinalLossAmountLimitForCA()) < 0.0D) && !(Double.parseDouble(reqObj.getFinalLossAmountLimitForFA()) < 0.0D) && !(Double.parseDouble(reqObj.getRiskScore()) < 0.0D) && !(Double.parseDouble(reqObj.getRiskScoreLimitForCA()) < 0.0D) && !(Double.parseDouble(reqObj.getRiskScoreLimitForFA()) < 0.0D)) {
                  if (!processName.equals("FinancialApprovalFlow")) {
                     logger.error("RGICL_CMS_WS.createWorkFlowForFinancialApproval() Unmatching Proccess Name");
                     responseCode = 5057;
                  } else {
                     String sessionId = null;

                     try {
                        logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() processName is " + processName);
                        resolvedDSValues = this.getHashTableFromFinancialApprovalRequestObject(reqObj);
                        resolvedDSValues.put("approverLimitFA", reqObj.getApproverLimitFA().toString());
                        resolvedDSValues.put("approverLimitCA", reqObj.getApproverLimitCA().toString());
                        sessionId = this.connectUser("rgicl");
                        resolvedDSValues.put("TLCount", reqObj.getTLCount());
                        logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() connecting as adminsessionId is" + sessionId);
                        logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() creating processInstance");
                        processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                        Thread.sleep((long)this.ThreadSleep);
                        logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() getting active workstep list");
                        String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                        String roleName = null;
                        if (workStepList != null) {
                           workItemObjects = new WorkItemObject[workStepList.length];

                           for(int i = 0; i < workStepList.length; ++i) {
                              String workStepName = workStepList[i];
                              WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                              logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() workitemList is " + workitemList);
                              List wiList = workitemList.getList();
                              logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() wiList is " + wiList);
                              if (wiList != null && !wiList.isEmpty() && wiList.size() > 0) {
                                 WorkItemObject wiObject = new WorkItemObject();
                                 WorkItem wi = (WorkItem)wiList.get(0);
                                 String wiName = wi.getName();
                                 String performer = wi.getPerformer();
                                 String workItemId = String.valueOf(wi.getID());
                                 if (wiName.endsWith("Technical")) {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameTA");
                                 } else if (wiName.endsWith("Corporate")) {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameCA");
                                 } else if (wiName.endsWith("Financial")) {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameFA");
                                 } else if (wiName.contains("CVM")) {
                                    roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleNameCVM");
                                    statusCVM = "&" + this.getDataSlotValue(sessionId, processInstanceName, "statusCVM");
                                 }

                                 wiObject.setWorkStepName(workStepName);
                                 wiObject.setWorkItemName(wiName);
                                 wiObject.setPerformer(performer);
                                 wiObject.setWorkItemId(workItemId);
                                 wiObject.setRoleName(roleName);
                                 wiObject.setClaimReferenceNumber(reqObj.getClaimReferenceNumber());
                                 logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " roleName-" + roleName);
                                 workItemObjects[i] = wiObject;
                              }
                           }
                        }

                        status = "Pending For Approval";
                        responseCode = 5000;
                        blserver = BLClientUtil.getBizLogicServer();
                        blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                        if (responseCode == 5000 && blsessionCMSAdmin != null) {
                           isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                           if (!isInstanceCompleted) {
                              status = this.getDataSlotValue(sessionId, processInstanceName, "statusFA") + "&" + this.getDataSlotValue(sessionId, processInstanceName, "statusCA") + statusCVM;
                           } else {
                              status = "Approve&Approve" + statusCVM;
                           }
                        }

                        logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() Got the activeWorkItemNameForInstance");
                     } catch (Exception var35) {
                        logger.error("RGICL_CMS_WS.createWorkFlowForFinancialApproval() Exception" + var35);
                        responseCode = 5035;
                     } finally {
                        this.disconnect(sessionId);
                        this.disconnectBlSession(blsessionCMSAdmin);
                     }
                  }
               } else {
                  logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() input value is negative");
                  responseCode = 5009;
               }
            } else {
               logger.error("RGICL_CMS_WS.createWorkFlowForFinancialApproval() input value is null");
               responseCode = 5010;
            }
         } else {
            logger.error("RGICL_CMS_WS.createWorkFlowForFinancialApproval() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setWorkItemObjects(workItemObjects);
         resObj.setInstanceCompleted(isInstanceCompleted);
         logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "FinancialApprovalFlow", "Create", reqObj.getInitiatorId());
               logger.info("RGICL_CMS_WS.createWorkFlowForFinancialApproval() SavvionAuditCount is " + rowcount);
            } catch (Exception var33) {
               logger.error("RGICL_CMS_WS.createWorkFlowForFinancialApproval() insertSavvionAuditData Exception" + var33.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForSurveyorAppointmentApproval(RequestObjectSurveyorAppointmentApproval reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() called");
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
         WorkItemObject[] workItemObjects = null;
         String status = "";
         int responseCode = 0;
         if (reqObj == null) {
            responseCode = 5038;
            logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("")) {
            String sessionId = null;

            try {
               logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() processName is " + processName);
               resolvedDSValues = this.getHashTableFromSurveyorAppointmentApprovalRequestObject(reqObj);
               sessionId = this.connectUser("rgicl");
               logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() connecting as adminsessionId is" + sessionId);
               if (getBizLogicManager().isSessionValid(sessionId)) {
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  Thread.sleep((long)this.ThreadSleep);
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() getting active workstep list");
               } else {
                  responseCode = 5073;
               }

               String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
               if (workStepList == null) {
                  responseCode = 5051;
               } else {
                  workItemObjects = new WorkItemObject[workStepList.length];

                  String roleName;
                  for(int i = 0; i < workStepList.length; ++i) {
                     WorkItemObject wiObject = new WorkItemObject();
                     String workStepName = workStepList[i];
                     WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                     List wiList = workitemList.getList();
                     WorkItem wi = (WorkItem)wiList.get(i);
                     String wiName = wi.getName();
                     String performer = wi.getPerformer();
                     String workItemId = String.valueOf(wi.getID());
                     roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                     wiObject.setWorkStepName(workStepName);
                     wiObject.setWorkItemName(wiName);
                     wiObject.setPerformer(performer);
                     wiObject.setWorkItemId(workItemId);
                     wiObject.setRoleName(roleName);
                     logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() workStepName-" + workStepName + ", workItemName-" + wiName + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                     workItemObjects[i] = wiObject;
                  }

                  status = this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus");
                  resObj.setWorkItemObjects(workItemObjects);
                  roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                  if (this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus").equals("PENDING FOR APPROVAL")) {
                     if (roleName.equals("HM")) {
                        responseCode = 5047;
                     } else if (roleName.equals("ZM")) {
                        responseCode = 5046;
                     } else if (roleName.equals("CO")) {
                        responseCode = 5048;
                     } else if (roleName.equals("VH")) {
                        responseCode = 5049;
                     } else if (roleName.equals("CH")) {
                        responseCode = 5050;
                     } else {
                        responseCode = 5045;
                     }

                     resObj.setWorkItemObjects(workItemObjects);
                  }
               }

               logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() Got the activeWorkItemNameForInstance");
               blserver = BLClientUtil.getBizLogicServer();
               blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
               if (responseCode == 5000 || responseCode == 5051) {
                  isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                  if (!isInstanceCompleted) {
                     status = this.getDataSlotValue(sessionId, processInstanceName, "status");
                  } else {
                     status = "Approve";
                  }
               }
            } catch (Exception var32) {
               logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() Exception" + var32.getMessage());
               var32.printStackTrace();
               responseCode = 5035;
            } finally {
               this.disconnect(sessionId);
               this.disconnectBlSession(blsessionCMSAdmin);
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() input value is null");
            responseCode = 5010;
         }

         resObj.setStatus(status);
         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "SurveyorAppointment", "Create", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() SavvionAuditCount is " + rowcount);
            } catch (Exception var31) {
               logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorAppointmentApproval() insertSavvionAuditData Exception" + var31.getMessage());
            }
         }

         return resObj;
      }
   }

   public ResponseObject createWorkFlowForSurveyorExpensePaymentApproval(RequestObjectSurveyorExpensePayment reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() called");
      ResponseObject resObj = new ResponseObject();
      Hashtable resolvedDSValues = null;
      String ptName = null;
      String processInstanceName = null;
      WorkItemObject[] workItemObjects = null;
      String status = "";
      int responseCode = 0;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      boolean isInstanceCompleted = false;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         
         if (reqObj == null) {
            responseCode = 5038;
            logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() request object is null");
         } else if (processName != null && !processName.equals("") && reqObj.getInitiatorRole() != null && !reqObj.getInitiatorRole().equals("") && reqObj.getInitiatorId() != null && !reqObj.getInitiatorId().equals("") && reqObj.getClaimReferenceNumber() != null && !reqObj.getClaimReferenceNumber().equals("") && reqObj.getActualamount() != 0L) {
            if (!reqObj.getInitiatorRole().equals("CM") && !reqObj.getInitiatorRole().equals("TCM") && !reqObj.getInitiatorRole().equals("TL")) {
               logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() Invalid Initiator Role");
               responseCode = 5058;
            } else if (!processName.equals("SurveyorExpensePayment")) {
               logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() Unmatching Proccess Name");
               responseCode = 5057;
            } else {
               String sessionId = null;

               try {
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() processName is " + processName);
                  if (reqObj.getInitiatorRole().equals("TL")) {
                     reqObj.setCMApprovelimit(0L);
                  } else {
                     reqObj.setTLApprovelimit(0L);
                  }

                  resolvedDSValues = this.getHashTableFromSurveyorExpensePaymentRequestObject(reqObj);
                  sessionId = this.connectUser("rgicl");
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() connecting as adminsessionId is" + sessionId);
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() creating processInstance");
                  processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() getting active workstep list");
                  Thread.sleep((long)this.ThreadSleep);
                  Session blsession = null;
                  blserver = BLClientUtil.getBizLogicServer();
                  blsession = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  QueryService qs = new QueryService(blsession);
                  QSProcessInstanceFilter qsf = new QSProcessInstanceFilter("qfil");
                  qsf.setFetchSize(0);
                  qsf.setCondition("BLPI.PROCESS_INSTANCE_NAME='" + processInstanceName + "'");
                  QSProcessInstance qpi = qs.getProcessInstance();
                  QSProcessInstanceRS qpirs = qpi.getList(qsf);
                  List list = new ArrayList();
                  long piid = 0L;
                  if (qpirs != null) {
                     List ls = qpirs.getList();
                     if (ls != null && !ls.isEmpty()) {
                        for(int i = 0; i < ls.size(); ++i) {
                           QSProcessInstanceData qpid = (QSProcessInstanceData)ls.get(i);
                           String[] arr = qpid.getActivatedWorkStepNames();
                           piid = qpid.getProcessInstanceID();
                           list = Arrays.asList(arr);
                        }
                     }
                  }

                  String[] workStepList = (String[])((List)list).toArray();
                  logger.info("Abhay v7 cnt" + Integer.toString(workStepList.length));
                  String roleName = null;
                  if (workStepList == null) {
                     status = "Completed";
                     responseCode = 5000;
                  } else {
                     workItemObjects = new WorkItemObject[workStepList.length];

                     for(int i = 0; i < workStepList.length; ++i) {
                        WorkItemObject wiObject = new WorkItemObject();
                        logger.info("ck1");
                        String workStepName = workStepList[i];
                        logger.info("ck2");
                        WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                        logger.info("ck3");
                        List wiList = workitemList.getList();
                        logger.info("ck4" + wiList.size());
                        WorkItem wi = (WorkItem)wiList.get(i);
                        logger.info("ck5");
                        String wiName = wi.getName();
                        String performer = wi.getPerformer();
                        String workItemId = String.valueOf(wi.getID());
                        roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                        wiObject.setWorkStepName(workStepName);
                        wiObject.setWorkItemName(wiName);
                        wiObject.setPerformer(performer);
                        wiObject.setWorkItemId(workItemId);
                        wiObject.setRoleName(roleName);
                        wiObject.setClaimReferenceNumber(reqObj.getClaimReferenceNumber());
                        logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() workStepName-" + workStepName + ", workItemName-" + wiName + ", workItemId-" + workItemId + ", userRoleName-" + roleName);
                        workItemObjects[i] = wiObject;
                     }

                     status = "Pending for Approval";
                     responseCode = 5000;
                  }

                  blserver = BLClientUtil.getBizLogicServer();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  if (responseCode == 5000) {
                     isInstanceCompleted = this.isInstanceCompleted(blsessionCMSAdmin, processInstanceName);
                     if (!isInstanceCompleted) {
                        status = this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus");
                     } else {
                        status = "Approve";
                     }
                  }

                  logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() Got the activeWorkItemNameForInstance");
               } catch (Exception var40) {
                  System.out.println("SavvionError:" + var40.getMessage());
                  logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() Exception " + var40.getMessage());
                  responseCode = 5035;
               } finally {
                  this.disconnect(sessionId);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }
            }
         } else {
            logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() input value is null");
            responseCode = 5010;
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, reqObj.getClaimReferenceNumber(), "SurveyorExpensePayment", "Create", reqObj.getInitiatorId());
               logger.info("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() SavvionAuditCount is " + rowcount);
            } catch (Exception var39) {
               logger.error("RGICL_CMS_WS.createWorkFlowForSurveyorExpensePaymentApproval() insertSavvionAuditData Exception" + var39.getMessage());
            }
         }

         return resObj;
      }
   }

   private Hashtable getHashTableFromSurveyorExpensePaymentRequestObject(RequestObjectSurveyorExpensePayment reqObj) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectSurveyorExpensePayment.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromSurveyorExpensePaymentRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = "" + fields[i].get(reqObj);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorExpensePaymentRequestObject() IllegalArgumentException " + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorExpensePaymentRequestObject() IllegalAccessException " + var9.getMessage());
         } catch (Exception var10) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorExpensePaymentRequestObject() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromFinancialApprovalRequestObject(RequestObjectFinancialApproval reqObj) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectFinancialApproval.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromFinancialApprovalRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqObj);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromFinancialApprovalRequestObject() IllegalArgumentException" + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromFinancialApprovalRequestObject() IllegalAccessException" + var9.getMessage());
         } catch (Exception var10) {
            logger.error("RGICL_CMS_WS.getHashTableFromFinancialApprovalRequestObject() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromSurveyorAppointmentApprovalRequestObject(RequestObjectSurveyorAppointmentApproval reqObj) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectSurveyorAppointmentApproval.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromSurveyorAppointmentApprovalRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqObj);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorAppointmentApprovalRequestObject() IllegalArgumentException" + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorAppointmentApprovalRequestObject() IllegalAccessException" + var9.getMessage());
         } catch (Exception var10) {
            logger.error("RGICL_CMS_WS.getHashTableFromSurveyorAppointmentApprovalRequestObject() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromMakerCheckerRequestObject(RequestObjectMakerChecker reqObj) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectMakerChecker.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromMakerCheckerRequestObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqObj);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromMakerCheckerRequestObject() IllegalArgumentException" + var8.getMessage());
         } catch (IllegalAccessException var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromMakerCheckerRequestObject() IllegalAccessException" + var9.getMessage());
         } catch (Exception var10) {
            logger.error("RGICL_CMS_WS.getHashTableFromMakerCheckerRequestObject() Exception " + var10.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   public ResponseObject updateMakerCheckerFlow(RequestObjectMakerChecker reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateMakerCheckerFlow() called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WS.updateMakerCheckerFlow() request object is null");
            } else {
               String approverDecision = reqObj.getApproverDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               processInstanceName = reqObj.getProcessInstanceName();
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && approverDecision != null && !approverDecision.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     hm.put("approverDecision", approverDecision.toLowerCase());
                     hm.put("currentApproverId", userId);
                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                        WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                        if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.warn("RGICL_CMS_WS.updateMakerCheckerFlow() Task assigned to user");
                        }

                        logger.warn("RGICL_CMS_WS.updateMakerCheckerFlow() CompleteTask enterred");
                        this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        logger.warn("RGICL_CMS_WS.updateMakerCheckerFlow() Task Completed");
                        Thread.sleep((long)this.ThreadSleep);
                        sessionId = this.connectUser("rgicl");
                        String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                        logger.info("RGICL_CMS_WS.updateMakerCheckerFlow() workStepList is " + workStepList);
                        if (workStepList != null && workStepList.length > 0) {
                           workItemObjects = new WorkItemObject[workStepList.length];

                           for(int i = 0; i < workStepList.length; ++i) {
                              WorkItemObject wiObject = new WorkItemObject();
                              String workStepName = workStepList[i];
                              WorkItemList workitemList = this.getWorkItemList(sessionId, processInstanceName, workStepName);
                              if (workitemList != null) {
                                 List wiList = workitemList.getList();
                                 WorkItem wiNew = (WorkItem)wiList.get(i);
                                 String wiName = wiNew.getName();
                                 String performer = wiNew.getPerformer();
                                 String workItemId = String.valueOf(wiNew.getID());
                                 String roleName = this.getDataSlotValue(sessionId, processInstanceName, "currentRoleName");
                                 wiObject.setWorkStepName(workStepName);
                                 wiObject.setWorkItemName(wiName);
                                 wiObject.setPerformer(performer);
                                 wiObject.setWorkItemId(workItemId);
                                 wiObject.setRoleName(roleName);
                                 workItemObjects[i] = wiObject;
                                 logger.info("RGICL_CMS_WS.updateMakerCheckerFlow()  workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + ", roleName-" + roleName);
                              }
                           }
                        }

                        responseCode = 5000;
                        resObj.setProcessInstanceName(processInstanceName);
                        resObj.setStatus(status);
                        resObj.setResponseCode(responseCode);
                        resObj.setInstanceCompleted(isInstanceCompleted);
                        resObj.setWorkItemObjects(workItemObjects);
                     }
                  } else {
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }

            if (responseCode == 5000) {
               isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
               if (!isInstanceCompleted) {
                  status = this.getDataSlotValue(sessionId, processInstanceName, "status");
               } else {
                  status = "approved";
               }
            }
         } catch (Exception var33) {
            logger.error("RGICL_CMS_WS.updateMakerCheckerFlow() Exception " + var33.getMessage());
            responseCode = 5035;
            resObj.setResponseCode(responseCode);
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         return resObj;
      }
   }

   private boolean technicalValidateInputSuccess(RequestObjectTechnicalApprovalUpdate reqObj, AuthenticationDetail authenticationDetail, ResponseObject resObj) {
      int responseCode = 0;
      if (!this.isAuthenticated(authenticationDetail)) {
         responseCode = 5040;
         resObj.setResponseCode(responseCode);
      } else if (reqObj == null) {
         responseCode = 5038;
         resObj.setResponseCode(responseCode);
         logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() request object is null");
      } else if (reqObj.getProcessInstanceName() != null && reqObj.getWorkItemName() != null && reqObj.getUserId() != null && reqObj.getOperationType() != null && reqObj.getProcessInstanceName().length() != 0 && reqObj.getWorkItemName().length() != 0 && reqObj.getUserId().length() != 0 && reqObj.getOperationType().length() != 0) {
         if (reqObj.getUserId() != null && !reqObj.getUserId().equals("")) {
            if (UserManager.getUser(reqObj.getUserId()) == null) {
               logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() user " + reqObj.getUserId() + " is not present in savvion");
               responseCode = 5015;
               resObj.setResponseCode(responseCode);
            } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatetolerance") && !reqObj.getOperationType().equalsIgnoreCase("updateesimatedvalue") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
               responseCode = 5054;
               resObj.setResponseCode(responseCode);
            }
         } else {
            responseCode = 5013;
            resObj.setResponseCode(responseCode);
         }
      } else {
         responseCode = 5010;
         resObj.setResponseCode(responseCode);
         logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() Input value is NULL");
      }

      return responseCode == 0;
   }

   private boolean technicalValidateInputSuccess(RequestObjectAutoboticsTechnicalUpdate reqObj, AuthenticationDetail authenticationDetail, ResponseObject resObj) {
      int responseCode = 0;
      if (!this.isAuthenticated(authenticationDetail)) {
         responseCode = 5040;
         resObj.setResponseCode(responseCode);
      } else if (reqObj == null) {
         responseCode = 5038;
         resObj.setResponseCode(responseCode);
         logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() request object is null");
      } else if (reqObj.getProcessInstanceName() != null && reqObj.getWorkItemName() != null && reqObj.getUserId() != null && reqObj.getOperationType() != null && reqObj.getProcessInstanceName().length() != 0 && reqObj.getWorkItemName().length() != 0 && reqObj.getUserId().length() != 0 && reqObj.getOperationType().length() != 0) {
         if (reqObj.getUserId() != null && !reqObj.getUserId().equals("")) {
            if (UserManager.getUser(reqObj.getUserId()) == null) {
               logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() user " + reqObj.getUserId() + " is not present in savvion");
               responseCode = 5015;
               resObj.setResponseCode(responseCode);
            } else if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatetolerance") && !reqObj.getOperationType().equalsIgnoreCase("updateesimatedvalue") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
               responseCode = 5054;
               resObj.setResponseCode(responseCode);
            }
         } else {
            responseCode = 5013;
            resObj.setResponseCode(responseCode);
         }
      } else {
         responseCode = 5010;
         resObj.setResponseCode(responseCode);
         logger.error("RGICL_CMS_WS.technicalValidateInputSuccess() Input value is NULL");
      }

      return responseCode == 0;
   }

   private ResponseObject updateTechnicalUpdateDataSlot(RequestObjectTechnicalApprovalUpdate reqObj, ResponseObject resObj) {
      int responseCode = 0;
      HashMap<String, String> hm = new HashMap();
      String roleName = reqObj.getRoleName();
      String tolerance = roleName + "Tolerance";
      hm.put(tolerance, reqObj.getToleranceAmount());
      String proccessInstanceName = reqObj.getProcessInstanceName();
      BLServer blServer = null;
      Session blsession = null;
      String userId = reqObj.getUserId();

      
      try {
         blServer = BLClientUtil.getBizLogicServer();
         blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
         ProcessInstance pi = blServer.getProcessInstance(blsession, proccessInstanceName);
         pi.updateSlotValue(hm);
         pi.save();
         pi.refresh();
         responseCode = 5000;
      } catch (RemoteException var16) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateTechnicalUpdateDataSlot() RemoteException " + var16.getMessage());
      } catch (Exception var17) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateTechnicalUpdateDataSlot() Exception " + var17.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   private ResponseObject updateTechnicalUpdateDataSlot(RequestObjectAutoboticsTechnicalUpdate reqObj, ResponseObject resObj) {
      int responseCode = 0;
      HashMap<String, String> hm = new HashMap();
      String roleName = reqObj.getRoleName();
      String tolerance = roleName + "Tolerance";
      hm.put(tolerance, reqObj.getToleranceAmount());
      String proccessInstanceName = reqObj.getProcessInstanceName();
      BLServer blServer = null;
      Session blsession = null;
      String userId = reqObj.getUserId();

      
      try {
         blServer = BLClientUtil.getBizLogicServer();
         blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
         ProcessInstance pi = blServer.getProcessInstance(blsession, proccessInstanceName);
         pi.updateSlotValue(hm);
         pi.save();
         pi.refresh();
         responseCode = 5000;
      } catch (RemoteException var16) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateTechnicalUpdateDataSlot() RemoteException " + var16.getMessage());
      } catch (Exception var17) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateTechnicalUpdateDataSlot() Exception " + var17.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   private ResponseObject updateEstiMatedValueDataSlot(RequestObjectTechnicalApprovalUpdate reqObj, ResponseObject resObj) {
      int responseCode = 0;
      HashMap<String, String> hm = new HashMap();
      String estimatedValue = "estimatedValue";
      hm.put(estimatedValue, reqObj.getToleranceAmount());
      String proccessInstanceName = reqObj.getProcessInstanceName();
      BLServer blServer = null;
      Session blsession = null;
      String userId = reqObj.getUserId();

      
      try {
         blServer = BLClientUtil.getBizLogicServer();
         blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
         ProcessInstance pi = blServer.getProcessInstance(blsession, proccessInstanceName);
         pi.updateSlotValue(hm);
         pi.save();
         pi.refresh();
         responseCode = 5000;
      } catch (RemoteException var15) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateEstiMatedValueDataSlot() RemoteException " + var15.getMessage());
      } catch (Exception var16) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateEstiMatedValueDataSlot() Exception " + var16.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   private ResponseObject updateEstiMatedValueDataSlot(RequestObjectAutoboticsTechnicalUpdate reqObj, ResponseObject resObj) {
      int responseCode = 0;
      HashMap<String, String> hm = new HashMap();
      String estimatedValue = "estimatedValue";
      hm.put(estimatedValue, reqObj.getToleranceAmount());
      String proccessInstanceName = reqObj.getProcessInstanceName();
      BLServer blServer = null;
      Session blsession = null;
      String userId = reqObj.getUserId();

      
      try {
         blServer = BLClientUtil.getBizLogicServer();
         blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
         ProcessInstance pi = blServer.getProcessInstance(blsession, proccessInstanceName);
         pi.updateSlotValue(hm);
         pi.save();
         pi.refresh();
         responseCode = 5000;
      } catch (RemoteException var15) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateEstiMatedValueDataSlot() RemoteException " + var15.getMessage());
      } catch (Exception var16) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateEstiMatedValueDataSlot() Exception " + var16.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   public ResponseObject updateTechnicalWorkFlow(RequestObjectTechnicalApprovalUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() service called");
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() UserId " + reqObj.getUserId() + " WorkItem " + reqObj.getWorkItemName() + " ApprovalLimit " + reqObj.getApproverLimit() + " ToleranceAmount " + reqObj.getToleranceAmount() + " NewUserId " + reqObj.getNewUserId() + " ApproverDecision " + reqObj.getApproveDecision() + " RoleName " + reqObj.getRoleName());
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      Session blsession = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      String sessionId = null;
      String status = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      String approverLimit = reqObj.getApproverLimit();
      String toleranceAmount = reqObj.getToleranceAmount();
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      String userId = reqObj.getUserId();
      if (!this.technicalValidateInputSuccess(reqObj, authenticationDetail, resObj)) {
         return resObj;
      } else if (reqObj.getOperationType().equalsIgnoreCase("updatetolerance")) {
         return this.updateTechnicalUpdateDataSlot(reqObj, resObj);
      } else if (reqObj.getOperationType().equalsIgnoreCase("updateesimatedvalue")) {
         return this.updateEstiMatedValueDataSlot(reqObj, resObj);
      } else if (reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
         String approveDecision = reqObj.getApproveDecision();
         if (approveDecision != null && approveDecision.length() != 0 && approverLimit != null && !approverLimit.equals("") && approverLimit.length() != 0) {
            boolean var46;
            if (userId == null || userId.equals("")) {
               logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() user id either null or empty");
               var46 = true;
            }

            label663: {
               ResponseObject var56;
               try {
                  blserver = BLClientUtil.getBizLogicServer();
                  blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  hm.put("approverDecision", approveDecision);
                  hm.put("ApprovalRoleID", userId);
                  if (workItemName.contains("TL")) {
                     hm.put("TLApproveLimit", approverLimit);
                     hm.put("TLTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CM")) {
                     hm.put("CMApproveLimit", approverLimit);
                     hm.put("CMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("HM")) {
                     hm.put("HMApproveLimit", approverLimit);
                     hm.put("HMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("ZM")) {
                     hm.put("ZMApproveLimit", approverLimit);
                     hm.put("ZMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CO")) {
                     hm.put("COApproveLimit", approverLimit);
                     hm.put("COTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CH")) {
                     hm.put("CHApproveLimit", approverLimit);
                     hm.put("CHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("VH")) {
                     hm.put("VHApproveLimit", approverLimit);
                     hm.put("VHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CEO")) {
                     hm.put("CEOApproveLimit", approverLimit);
                     hm.put("CEOTolerance", toleranceAmount);
                  }

                  if (blsession == null) {
                     break label663;
                  }

                  pi.updateSlotValue(hm);
                  pi.save();
                  pi.refresh();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                  if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                     logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task assigned to user");
                  }

                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() CompleteTask enterred");
                  this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task Completed");
                  Thread.sleep((long)this.ThreadSleep);
                  resObj = this.getWorkItemListDB(processInstanceName);
                  var56 = resObj;
               } catch (Exception var42) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Exception " + var42.getMessage());
                  var46 = true;
                  break label663;
               } finally {
                  this.disconnect((String)sessionId);
                  this.disconnectBlSession(blsession);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }

               return var56;
            }

            logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
            if (this.isSavvionAuditEnable) {
               try {
                  DBUtility obj = new DBUtility();
                  int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
               } catch (Exception var40) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var40.getMessage());
               }
            }

            return resObj;
         } else {
            responseCode = 5010;
            resObj.setResponseCode(responseCode);
            return resObj;
         }
      } else {
         userId = reqObj.getUserId();
         String bl_user = userId;
         String bl_password = this.getUserPasswordECS(userId);
         blserver = BLClientUtil.getBizLogicServer();
         processInstanceName = reqObj.getProcessInstanceName();
         workItemName = reqObj.getWorkItemName();
         workItemName = workItemName.contains("HM") ? "HM" : workItemName;
         workItemName = workItemName.contains("ZM") ? "ZM" : workItemName;
         workItemName = workItemName.contains("CO") ? "CO" : workItemName;
         workItemName = workItemName.contains("CH") ? "CH" : workItemName;
         workItemName = workItemName.contains("VH") ? "VH" : workItemName;
         workItemName = workItemName.contains("CEO") ? "CEO" : workItemName;
         workItemName = workItemName.contains("CVM") ? "CVM" : workItemName;
         workItemName = workItemName.contains("TL") ? "TL" : workItemName;
         workItemName = workItemName.contains("Surveyor") ? "Surveyor" : workItemName;

         try {
            blsession = blserver.connect(bl_user, bl_password);
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
                     responseCode = 5055;
                     logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Workitem is already assigned");
                  }
               } else {
                  String newUserId;
                  User usr;
                  if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                     newUserId = reqObj.getNewUserId();
                     if (newUserId != null && newUserId.length() != 0) {
                        usr = UserManager.getUser(newUserId);
                        if (usr == null) {
                           logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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
                        logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Input value is null ");
                        responseCode = 5010;
                     }
                  } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                     newUserId = reqObj.getNewUserId();
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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

            if (responseCode == 5000) {
               isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
               if (!isInstanceCompleted) {
                  status = this.getDataSlotValue((String)sessionId, processInstanceName, "status");
               } else {
                  status = reqObj.getApproveDecision();
                  responseCode = 5000;
               }
            }
         } catch (Exception var44) {
            responseCode = 5035;
            logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Update Technical Flow fails with error " + var44.getMessage());
         } finally {
            this.disconnect((String)sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects((WorkItemObject[])workItemObjects);
         resObj.setStatus(status);
         logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
            } catch (Exception var41) {
               logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var41);
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateVSTechnicalWorkFlow(RequestObjectTechnicalApprovalUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() service called");
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() UserId " + reqObj.getUserId() + " WorkItem " + reqObj.getWorkItemName() + " ApprovalLimit " + reqObj.getApproverLimit() + " ToleranceAmount " + reqObj.getToleranceAmount() + " NewUserId " + reqObj.getNewUserId() + " ApproverDecision " + reqObj.getApproveDecision() + " RoleName " + reqObj.getRoleName());
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      Session blsession = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      String sessionId = null;
      String status = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      String approverLimit = reqObj.getApproverLimit();
      String toleranceAmount = reqObj.getToleranceAmount();
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      String userId = reqObj.getUserId();
      if (!this.technicalValidateInputSuccess(reqObj, authenticationDetail, resObj)) {
         return resObj;
      } else if (reqObj.getOperationType().equalsIgnoreCase("updatetolerance")) {
         return this.updateTechnicalUpdateDataSlot(reqObj, resObj);
      } else if (reqObj.getOperationType().equalsIgnoreCase("updateesimatedvalue")) {
         return this.updateEstiMatedValueDataSlot(reqObj, resObj);
      } else if (reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
         String approveDecision = reqObj.getApproveDecision();
         if (approveDecision != null && approveDecision.length() != 0 && approverLimit != null && !approverLimit.equals("") && approverLimit.length() != 0) {
            boolean var46;
            if (userId == null || userId.equals("")) {
               logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() user id either null or empty");
               var46 = true;
            }

            label689: {
               ResponseObject var56;
               try {
                  blserver = BLClientUtil.getBizLogicServer();
                  blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  hm.put("approverDecision", approveDecision);
                  hm.put("ApprovalRoleID", userId);
                  if (workItemName.contains("TL")) {
                     hm.put("TLApproveLimit", approverLimit);
                     hm.put("TLTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CM")) {
                     hm.put("CMApproveLimit", approverLimit);
                     hm.put("CMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("HM")) {
                     hm.put("HMApproveLimit", approverLimit);
                     hm.put("HMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("ZM")) {
                     hm.put("ZMApproveLimit", approverLimit);
                     hm.put("ZMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CO")) {
                     hm.put("COApproveLimit", approverLimit);
                     hm.put("COTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CH")) {
                     hm.put("CHApproveLimit", approverLimit);
                     hm.put("CHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("VH")) {
                     hm.put("VHApproveLimit", approverLimit);
                     hm.put("VHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CEO")) {
                     hm.put("CEOApproveLimit", approverLimit);
                     hm.put("CEOTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("TE")) {
                     hm.put("TEApproveLimit", approverLimit);
                     hm.put("TETolerance", toleranceAmount);
                  }

                  if (blsession == null) {
                     break label689;
                  }

                  pi.updateSlotValue(hm);
                  pi.save();
                  pi.refresh();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                  if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                     logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task assigned to user");
                  }

                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() CompleteTask enterred");
                  this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task Completed");
                  Thread.sleep((long)this.ThreadSleep);
                  resObj = this.getWorkItemListDB(processInstanceName);
                  var56 = resObj;
               } catch (Exception var42) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Exception " + var42.getMessage());
                  var46 = true;
                  break label689;
               } finally {
                  this.disconnect((String)sessionId);
                  this.disconnectBlSession(blsession);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }

               return var56;
            }

            logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
            if (this.isSavvionAuditEnable) {
               try {
                  DBUtility obj = new DBUtility();
                  int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
               } catch (Exception var40) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var40.getMessage());
               }
            }

            return resObj;
         } else {
            responseCode = 5010;
            resObj.setResponseCode(responseCode);
            return resObj;
         }
      } else {
         userId = reqObj.getUserId();
         String bl_user = userId;
         String bl_password = this.getUserPasswordECS(userId);
         blserver = BLClientUtil.getBizLogicServer();
         processInstanceName = reqObj.getProcessInstanceName();
         workItemName = reqObj.getWorkItemName();
         workItemName = workItemName.contains("HM") ? "HM" : workItemName;
         workItemName = workItemName.contains("ZM") ? "ZM" : workItemName;
         workItemName = workItemName.contains("CO") ? "CO" : workItemName;
         workItemName = workItemName.contains("CH") ? "CH" : workItemName;
         workItemName = workItemName.contains("VH") ? "VH" : workItemName;
         workItemName = workItemName.contains("CEO") ? "CEO" : workItemName;
         workItemName = workItemName.contains("CVM") ? "CVM" : workItemName;
         workItemName = workItemName.contains("TL") ? "TL" : workItemName;
         workItemName = workItemName.contains("TE") ? "TE" : workItemName;
         workItemName = workItemName.contains("Surveyor") ? "Surveyor" : workItemName;

         try {
            blsession = blserver.connect(bl_user, bl_password);
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
                     responseCode = 5055;
                     logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Workitem is already assigned");
                  }
               } else {
                  String newUserId;
                  User usr;
                  if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                     newUserId = reqObj.getNewUserId();
                     if (newUserId != null && newUserId.length() != 0) {
                        usr = UserManager.getUser(newUserId);
                        if (usr == null) {
                           logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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
                        logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Input value is null ");
                        responseCode = 5010;
                     }
                  } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                     newUserId = reqObj.getNewUserId();
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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

            if (responseCode == 5000) {
               isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
               if (!isInstanceCompleted) {
                  status = this.getDataSlotValue((String)sessionId, processInstanceName, "status");
               } else {
                  status = reqObj.getApproveDecision();
                  responseCode = 5000;
               }
            }
         } catch (Exception var44) {
            responseCode = 5035;
            logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Update Technical Flow fails with error " + var44.getMessage());
         } finally {
            this.disconnect((String)sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects((WorkItemObject[])workItemObjects);
         resObj.setStatus(status);
         logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
            } catch (Exception var41) {
               logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var41);
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateAutoboticsTechnicalWorkFlow(RequestObjectAutoboticsTechnicalUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() service called");
      logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() UserId " + reqObj.getUserId() + " WorkItem " + reqObj.getWorkItemName() + " ApprovalLimit " + reqObj.getApproverLimit() + " ToleranceAmount " + reqObj.getToleranceAmount() + " NewUserId " + reqObj.getNewUserId() + " ApproverDecision " + reqObj.getApproveDecision() + " RoleName " + reqObj.getRoleName());
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      Session blsession = null;
      BLServer blserver = null;
      Session blsessionCMSAdmin = null;
      String sessionId = null;
      String status = null;
      String processInstanceName = reqObj.getProcessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      String approverLimit = reqObj.getApproverLimit();
      String toleranceAmount = reqObj.getToleranceAmount();
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      String userId = reqObj.getUserId();
      if (!this.technicalValidateInputSuccess(reqObj, authenticationDetail, resObj)) {
         return resObj;
      } else if (reqObj.getOperationType().equalsIgnoreCase("updatetolerance")) {
         return this.updateTechnicalUpdateDataSlot(reqObj, resObj);
      } else if (reqObj.getOperationType().equalsIgnoreCase("updateesimatedvalue")) {
         return this.updateTechnicalUpdateDataSlot(reqObj, resObj);
      } else {
         int rowcount;
         WorkItem wi;
         if (reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
            String approveDecision = reqObj.getApproveDecision();
            String IsDeviation = reqObj.getIsDeviation();
            if (approveDecision != null && approveDecision.length() != 0 && approverLimit != null && !approverLimit.equals("") && approverLimit.length() != 0) {
               boolean var46;
               if (userId == null || userId.equals("")) {
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() user id either null or empty");
                  var46 = true;
               }

               try {
                  blserver = BLClientUtil.getBizLogicServer();
                  blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  hm.put("approverDecision", approveDecision);
                  hm.put("ApprovalRoleID", userId);
                  System.out.println("DevUpdate" + IsDeviation.toLowerCase());
                  if (IsDeviation.toLowerCase().contains("false")) {
                     hm.put("RoleValue", "0");
                     System.out.println("update");
                  }

                  System.out.println("DevDone");
                  if (workItemName.contains("TL")) {
                     hm.put("TLApproveLimit", approverLimit);
                     hm.put("TLTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CM")) {
                     hm.put("CMApproveLimit", approverLimit);
                     hm.put("CMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("HM")) {
                     hm.put("HMApproveLimit", approverLimit);
                     hm.put("HMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("ZM")) {
                     hm.put("ZMApproveLimit", approverLimit);
                     hm.put("ZMTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CO")) {
                     hm.put("COApproveLimit", approverLimit);
                     hm.put("COTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CH")) {
                     hm.put("CHApproveLimit", approverLimit);
                     hm.put("CHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("VH")) {
                     hm.put("VHApproveLimit", approverLimit);
                     hm.put("VHTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("CEO")) {
                     hm.put("CEOApproveLimit", approverLimit);
                     hm.put("CEOTolerance", toleranceAmount);
                  }

                  if (workItemName.contains("TE")) {
                     hm.put("TEApproveLimit", approverLimit);
                     hm.put("TETolerance", toleranceAmount);
                  }

                  if (blsession != null) {
                     pi.updateSlotValue(hm);
                     pi.save();
                     pi.refresh();
                     blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                     wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                     if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task assigned to user");
                     }

                     logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() CompleteTask enterred");
                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                     logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() Task Completed");
                     Thread.sleep((long)this.ThreadSleep);
                     resObj = this.getWorkItemListDB(processInstanceName);
                     ResponseObject var56 = resObj;
                     return var56;
                  }
               } catch (Exception var42) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Exception " + var42.getMessage());
                  var46 = true;
               } finally {
                  this.disconnect((String)sessionId);
                  this.disconnectBlSession(blsession);
                  this.disconnectBlSession(blsessionCMSAdmin);
               }

               logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
               if (this.isSavvionAuditEnable) {
                  try {
                     DBUtility obj = new DBUtility();
                     rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
                     logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
                  } catch (Exception var40) {
                     logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var40.getMessage());
                  }
               }

               return resObj;
            } else {
               responseCode = 5010;
               resObj.setResponseCode(responseCode);
               return resObj;
            }
         } else {
            userId = reqObj.getUserId();
            String bl_user = userId;
            String bl_password = this.getUserPasswordECS(userId);
            blserver = BLClientUtil.getBizLogicServer();
            processInstanceName = reqObj.getProcessInstanceName();
            workItemName = reqObj.getWorkItemName();
            workItemName = workItemName.contains("HM") ? "HM" : workItemName;
            workItemName = workItemName.contains("ZM") ? "ZM" : workItemName;
            workItemName = workItemName.contains("CO") ? "CO" : workItemName;
            workItemName = workItemName.contains("CH") ? "CH" : workItemName;
            workItemName = workItemName.contains("VH") ? "VH" : workItemName;
            workItemName = workItemName.contains("CEO") ? "CEO" : workItemName;
            workItemName = workItemName.contains("CVM") ? "CVM" : workItemName;
            workItemName = workItemName.contains("TL") ? "TL" : workItemName;
            workItemName = workItemName.contains("TE") ? "TE" : workItemName;
            workItemName = workItemName.contains("Surveyor") ? "Surveyor" : workItemName;

            try {
               blsession = blserver.connect(bl_user, bl_password);
               WorkStepInstance workStepInstance = blserver.getWorkStepInstance(blsession, processInstanceName, workItemName);
               WorkItemList wli = workStepInstance.getWorkItemList();
               Vector wiList = (Vector)wli.getList();

               for(rowcount = 0; rowcount < wiList.size(); ++rowcount) {
                  wi = (WorkItem)wiList.get(rowcount);
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
                        responseCode = 5055;
                        logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Workitem is already assigned");
                     }
                  } else {
                     String newUserId;
                     User usr;
                     if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                        newUserId = reqObj.getNewUserId();
                        if (newUserId != null && newUserId.length() != 0) {
                           usr = UserManager.getUser(newUserId);
                           if (usr == null) {
                              logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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
                           logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Input value is null ");
                           responseCode = 5010;
                        }
                     } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                        newUserId = reqObj.getNewUserId();
                        usr = UserManager.getUser(newUserId);
                        if (usr == null) {
                           logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() new user " + newUserId + " is not present in savvion");
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

               if (responseCode == 5000) {
                  isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                  if (!isInstanceCompleted) {
                     status = this.getDataSlotValue((String)sessionId, processInstanceName, "status");
                  } else {
                     status = reqObj.getApproveDecision();
                     responseCode = 5000;
                  }
               }
            } catch (Exception var44) {
               responseCode = 5035;
               logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() Update Technical Flow fails with error " + var44.getMessage());
            } finally {
               this.disconnect((String)sessionId);
               this.disconnectBlSession(blsession);
               this.disconnectBlSession(blsessionCMSAdmin);
            }

            resObj.setProcessInstanceName(processInstanceName);
            resObj.setResponseCode(responseCode);
            resObj.setInstanceCompleted(isInstanceCompleted);
            resObj.setWorkItemObjects((WorkItemObject[])workItemObjects);
            resObj.setStatus(status);
            logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
            if (this.isSavvionAuditEnable) {
               try {
                  DBUtility obj = new DBUtility();
                  rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "TechnicalApprovalFlow", "Update", reqObj.getUserId());
                  logger.info("RGICL_CMS_WS.updateTechnicalWorkFlow() SavvionAuditCount is" + rowcount);
               } catch (Exception var41) {
                  logger.error("RGICL_CMS_WS.updateTechnicalWorkFlow() insertSavvionAuditData Exception " + var41);
               }
            }

            return resObj;
         }
      }
   }

   public ResponseObject updateFinancialApprovalFlow(RequestObjectFinancialUpdate reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateFinancialApprovalFlow() called");
      logger.info("RGICL_CMS_WS.updateFinancialApprovalFlow() UserId " + reqObj.getUserId() + " WorkItem " + reqObj.getWorkItemName() + " ApprovalLimitFA " + reqObj.getApproverLimitFA() + " ApprovalLimitCA " + reqObj.getApproverLimitCA() + " NewUserId " + reqObj.getNewUserId() + " ApproverDecisionFA " + reqObj.getApproverDecisionFA() + " ApproverDecisionCA " + reqObj.getApproverDecisionCA());
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      String statusCVM = "";
      String claimRefNo = "";
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = null;
      BLServer blserver = null;
      Session blsession = null;
      Session blsessionCMSAdmin = null;
      if (!this.isAuthenticated(authenticationDetail)) {
         resObj.setResponseCode(5040);
         return resObj;
      } else {
         try {
            String approverDecisionFA;
            String approverLimitFA;
            if (reqObj == null) {
               responseCode = 5038;
               resObj.setResponseCode(responseCode);
               logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() request object is null");
            } else {
               approverDecisionFA = reqObj.getApproverDecisionFA();
               approverLimitFA = reqObj.getApproverLimitFA();
               String approverDecisionCA = reqObj.getApproverDecisionCA();
               String approverLimitCA = reqObj.getApproverLimitCA();
               String approverDecisionTA = reqObj.getApproverDecisionTA();
               String approverLimitTA = reqObj.getApproverLimitTA();
               String approverDecisionCVM = reqObj.getApproverDecisionCVM();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               processInstanceName = reqObj.getProcessInstanceName();
               if (processInstanceName == null || processInstanceName.equals("") || workItemName == null || workItemName.equals("") || approverLimitFA == null && approverLimitCA == null && approverLimitTA == null || "".equals(approverLimitFA) && "".equals(approverLimitCA) && "".equals(approverLimitTA)) {
                  responseCode = 5010;
                  logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() Input Value is Null");
               } else if ((approverLimitFA == null || approverLimitFA.equals("") || !(Double.parseDouble(approverLimitFA) < 0.0D)) && (approverLimitCA == null || approverLimitCA.equals("") || !(Double.parseDouble(approverLimitCA) < 0.0D)) && (approverLimitTA == null || approverLimitTA.equals("") || !(Double.parseDouble(approverLimitTA) < 0.0D))) {
                  if (reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                     if (userId != null && !userId.equals("")) {
                        if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           responseCode = 5054;
                           logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() UnMatched Operation Type");
                           resObj.setResponseCode(responseCode);
                        } else {
                           ResponseObject var28;
                           if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                              this.updateDecision(reqObj, resObj);
                              resObj = this.getWorkItemListDB(processInstanceName);
                              var28 = resObj;
                              return var28;
                           }

                           blserver = BLClientUtil.getBizLogicServer();
                           blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                           ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                           HashMap hm = new HashMap();
                           if (approverDecisionFA != null) {
                              hm.put("approverDecisionFA", approverDecisionFA.toLowerCase());
                              hm.put("approverLimitFA", approverLimitFA);
                           }

                           if (approverDecisionCA != null) {
                              hm.put("approverDecisionCA", approverDecisionCA.toLowerCase());
                              hm.put("approverLimitCA", approverLimitCA);
                           }

                           if (approverDecisionTA != null) {
                              hm.put("approverDecisionTA", approverDecisionTA.toLowerCase());
                              hm.put("approverLimitTA", approverLimitTA);
                           }

                           if (approverDecisionCVM != null) {
                              hm.put("approverDecisionCVM", approverDecisionCVM.toLowerCase());
                           }

                           hm.put("currentApproverId", userId);
                           if (blsession != null) {
                              pi.updateSlotValue(hm);
                              pi.save();
                              blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                              WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                              if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                                 this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 logger.warn("RGICL_CMS_WS.updateFinancialApprovalFlow() Task assigned to user");
                              }

                              logger.warn("RGICL_CMS_WS.updateFinancialApprovalFlow() CompleteTask enterred");
                              this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              logger.warn("RGICL_CMS_WS.updateFinancialApprovalFlow() Task Completed");
                              Thread.sleep((long)this.ThreadSleep);
                              resObj = this.getWorkItemListDB(processInstanceName);
                              var28 = resObj;
                              return var28;
                           }
                        }
                     } else {
                        logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() user id invalid");
                        responseCode = 5013;
                     }
                  } else {
                     responseCode = 5010;
                     logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() Input Value is Null");
                  }
               } else {
                  logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() input value is negative");
                  responseCode = 5009;
               }
            }

            if (responseCode == 5000) {
               isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
               if (!isInstanceCompleted) {
                  status = this.getDataSlotValue((String)sessionId, processInstanceName, "statusFA") + "&" + this.getDataSlotValue((String)sessionId, processInstanceName, "statusCA") + statusCVM;
               } else {
                  approverDecisionFA = "";
                  approverLimitFA = "";
                  if (reqObj.getApproverDecisionFA() != null) {
                     approverDecisionFA = reqObj.getApproverDecisionFA();
                  }

                  if (reqObj.getApproverDecisionFA() != null) {
                     approverLimitFA = reqObj.getApproverDecisionFA();
                  }

                  status = approverDecisionFA + "&" + approverLimitFA + statusCVM;
                  responseCode = 5000;
               }
            }
         } catch (Exception var33) {
            logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() Exception " + var33.getMessage());
            responseCode = 5035;
            resObj.setResponseCode(responseCode);
         } finally {
            this.disconnect((String)sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects((WorkItemObject[])workItemObjects);
         logger.info("RGICL_CMS_WS.updateFinancialApprovalFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "FinancialApprovalFlow", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.updateFinancialApprovalFlow() SavvionAuditCount is" + rowcount);
            } catch (Exception var32) {
               logger.error("RGICL_CMS_WS.updateFinancialApprovalFlow() insertSavvionAuditData Exception " + var32);
            }
         }

         return resObj;
      }
   }

   public ResponseObject updateSurveyorAppointmentApprovalFlow(RequestObjectSurveyorAppointmentApproval reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow called");
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String status = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = null;
      BLServer blserver = null;
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
               logger.error("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() request object is null");
            } else {
               String approverDecision = reqObj.getApproverDecision();
               String workItemName = reqObj.getWorkItemName();
               String UserId = reqObj.getUserId();
               processInstanceName = reqObj.getProcessInstanceName();
               status = approverDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && approverDecision != null && !approverDecision.equals("")) {
                  if (UserId != null && !UserId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(UserId, this.getUserPasswordECS(UserId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     hm.put("approverDecision", approverDecision.toLowerCase());
                     hm.put("currentApproverId", UserId);
                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                        WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                        if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() Task assigned to user");
                        }

                        logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() CompleteTask enterred");
                        this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() Task Completed");
                        Thread.sleep((long)this.ThreadSleep);
                        isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                        sessionId = this.connectUser("rgicl");
                        logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() service isInstanceCompleted::" + isInstanceCompleted);
                        if (!isInstanceCompleted) {
                           String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                           logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() workStepList is " + workStepList);
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
                                    wiObject.setRoleName(roleName);
                                    claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                    logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                    workItemObjects[i] = wiObject;
                                 }
                              }
                           }

                           resObj.setWorkItemObjects(workItemObjects);
                           status = this.getDataSlotValue(sessionId, processInstanceName, "CurrentUserStatus");
                        }

                        if (isInstanceCompleted) {
                           status = reqObj.getApproverDecision();
                        }

                        if (!approverDecision.equals("Reject")) {
                           responseCode = 5051;
                        } else {
                           responseCode = 5056;
                        }
                     }
                  } else {
                     logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var35) {
            logger.error("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() Exception " + var35);
            var35.printStackTrace();
            responseCode = 5035;
         } finally {
            this.disconnect(sessionId);
            this.disconnectBlSession(blsession);
            this.disconnectBlSession(blsessionCMSAdmin);
         }

         resObj.setProcessInstanceName(processInstanceName);
         resObj.setStatus(status);
         resObj.setResponseCode(responseCode);
         resObj.setInstanceCompleted(isInstanceCompleted);
         resObj.setWorkItemObjects(workItemObjects);
         logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "SurveyorAppointment", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() SavvionAuditCount is" + rowcount);
            } catch (Exception var34) {
               logger.error("RGICL_CMS_WS.updateSurveyorAppointmentApprovalFlow() insertSavvionAuditData Exception " + var34);
            }
         }

         return resObj;
      }
   }

   public ResponseObject getProcessInstance(String userId, String hubId, String zoneId, String claimRefNumber, String productId, String processName) {
      logger.info("RGICL_CMS_WS.getProcessInstance() called");
      BLServer blServer = null;
      Session blsession = null;
      String proccessInstanceName = null;
      int responseCode = 0;
      userId = "rgicl";
      ResponseObject resObj = new ResponseObject();
      if (processName == null) {
         logger.error("RGICL_CMS_WS.getProcessInstance() Input Value is NULL");
         resObj.setResponseCode(5010);
         return resObj;
      } else {
         try {
            blServer = BLClientUtil.getBizLogicServer();
            blsession = blServer.connect(userId, this.getUserPasswordECS(userId));
            ProcessInstanceList piList = blServer.getProcessInstanceList(blsession, userId);
            List lst = piList.getList();

            for(int j = 0; j < lst.size(); ++j) {
               ProcessInstance pi = (ProcessInstance)lst.get(j);
               if (pi.getProcessTemplateName().equals(processName)) {
                  DataSlot hubIdds = null;
                  DataSlot zoneIdds = null;
                  DataSlot claimRefds = null;
                  DataSlot productIdds = null;
                  Vector dsList = pi.getDataSlotNameList();
                  Iterator it = dsList.iterator();

                  while(it.hasNext()) {
                     String var = (String)it.next();
                     if (var.equalsIgnoreCase("hubId")) {
                        this.dshubId = var;
                     } else if (var.equalsIgnoreCase("zoneId")) {
                        this.dszoneId = var;
                     } else if (var.equalsIgnoreCase("claimRefNumber")) {
                        this.dsCrn = var;
                     } else if (var.equalsIgnoreCase("productId")) {
                        this.dsProductId = var;
                     }
                  }

                  if (hubId != null) {
                     hubIdds = pi.getDataSlot(this.dshubId);
                  }

                  if (zoneId != null) {
                     zoneIdds = pi.getDataSlot(this.dszoneId);
                  }

                  if (claimRefNumber != null) {
                     claimRefds = pi.getDataSlot(this.dsCrn);
                  }

                  if (productId != null) {
                     productIdds = pi.getDataSlot(this.dsProductId);
                  }

                  if ((hubId == null || hubIdds.getValue().equals(hubId)) && (zoneId == null || zoneIdds.getValue().equals(zoneId)) && (claimRefNumber == null || claimRefds.getValue().equals(claimRefNumber)) && (productId == null || productIdds.getValue().equals(productId))) {
                     if (proccessInstanceName == null) {
                        proccessInstanceName = pi.getProcessInstanceName();
                     } else {
                        proccessInstanceName = proccessInstanceName + "&&" + pi.getProcessInstanceName();
                     }
                  }
               }
            }

            responseCode = 5000;
            logger.info("RGICL_CMS_WS.getProcessInstance() Completed Proccess Instance Method ");
         } catch (RemoteException var26) {
            logger.error("RGICL_CMS_WS.getProcessInstance() Remote Exception while communicating with bizLogic Server" + var26.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnectBlSession(blsession);
         }

         resObj.setProcessInstanceName(proccessInstanceName);
         resObj.setResponseCode(responseCode);
         return resObj;
      }
   }

   public ResponseObject updateSurveyorExpensePaymentFlow(RequestObjectSurveyorExpensePaymentUpdate reqObj, AuthenticationDetail authenticationDetail) {
      ResponseObject resObj = new ResponseObject();
      int responseCode = 0;
      boolean isInstanceCompleted = false;
      String sessionId = null;
      String Status = null;
      WorkItemObject[] workItemObjects = null;
      String processInstanceName = reqObj.getProccessInstanceName();
      BLServer blserver = null;
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
               logger.error("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() request object is null");
            } else {
               String approveDecision = reqObj.getApproveDecision();
               String workItemName = reqObj.getWorkItemName();
               String userId = reqObj.getUserId();
               processInstanceName = reqObj.getProccessInstanceName();
               Status = approveDecision;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && reqObj.getOperationType() != null && !reqObj.getOperationType().equals("")) {
                  if (userId != null && !userId.equals("")) {
                     if (UserManager.getUser(userId) != null) {
                        if (!reqObj.getOperationType().equalsIgnoreCase("assign") && !reqObj.getOperationType().equalsIgnoreCase("unassign") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                           responseCode = 5054;
                           logger.error("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() UnMatched Operation Type");
                           resObj.setResponseCode(responseCode);
                        } else {
                           ResponseObject var32;
                           if (!reqObj.getOperationType().equalsIgnoreCase("updatedecision")) {
                              this.updateDecision(reqObj, resObj);
                              sessionId = this.connectUser("rgicl");
                              resObj = this.requestWorkItemObject(reqObj, sessionId, processInstanceName, resObj);
                              var32 = resObj;
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
                           String approver = null;
                           if (!workItemName.contains("CVM") && !workItemName.contains("CEO")) {
                              if (reqObj.getApproverLimit() == null || reqObj.getApproverLimit().equals("")) {
                                 responseCode = 5010;
                                 resObj.setResponseCode(responseCode);
                                 var32 = resObj;
                                 return var32;
                              }

                              approver = workItemName.contains("CO") ? "CO" : approver;
                              approver = workItemName.contains("CM") ? "CM" : approver;
                              approver = workItemName.contains("CH") ? "CH" : approver;
                              approver = workItemName.contains("HM") ? "HM" : approver;
                              approver = workItemName.contains("TL") ? "TL" : approver;
                              approver = workItemName.contains("VH") ? "VH" : approver;
                              approver = workItemName.contains("ZM") ? "ZM" : approver;
                              if (approver.equals("CO")) {
                                 hm.put("COApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("CM")) {
                                 hm.put("CMApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("HM")) {
                                 hm.put("HMApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("TL")) {
                                 hm.put("TLApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("VH")) {
                                 hm.put("VHApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("ZM")) {
                                 hm.put("ZMApprovelimit", reqObj.getApproverLimit());
                              } else if (approver.equals("CH")) {
                                 hm.put("CHApprovelimit", reqObj.getApproverLimit());
                              }
                           }

                           hm.put("approverDecision", approveDecision);
                           hm.put("ApprovalRoleID", userId);
                           if (blsession != null) {
                              pi.updateSlotValue(hm);
                              pi.save();
                              blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                              WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                              if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                                 this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 logger.warn("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() Task assigned to user");
                              }

                              logger.warn("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() CompleteTask enterred");
                              this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              logger.warn("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() Task Completed");
                              isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
                              sessionId = this.connectUser("rgicl");
                              logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() isInstanceCompleted::" + isInstanceCompleted);
                              if (!isInstanceCompleted) {
                                 String[] workStepList = this.getActiveWorkStepList(sessionId, processInstanceName);
                                 logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() workStepList is " + workStepList);
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
                                          wiObject.setRoleName(roleName);
                                          claimRefNo = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                                          logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                                          workItemObjects[i] = wiObject;
                                       }
                                    }
                                 }

                                 responseCode = 5000;
                                 Status = "Pending For Approval";
                              } else if (reqObj.getApproveDecision().equals("Approve")) {
                                 responseCode = 5000;
                                 Status = "Approve";
                              } else {
                                 responseCode = 5070;
                                 Status = "Reject";
                              }
                           }
                        }
                     } else {
                        logger.error("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() User id provided doesnt exists");
                        responseCode = 5015;
                     }
                  } else {
                     logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() user id either null or empty");
                     responseCode = 5013;
                  }
               } else {
                  responseCode = 5010;
               }
            }
         } catch (Exception var37) {
            logger.error("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() Exception " + var37);
            logger.error(var37.getMessage());
            responseCode = 5035;
            resObj.setResponseCode(responseCode);
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
         logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               DBUtility obj = new DBUtility();
               int rowcount = obj.insertSavvionAuditData(resObj, claimRefNo, "SurveyorExpensePayment", "Update", reqObj.getUserId());
               logger.info("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() SavvionAuditCount is" + rowcount);
            } catch (Exception var36) {
               logger.error("RGICL_CMS_WS.updateSurveyorExpensePaymentFlow() insertSavvionAuditData Exception " + var36);
            }
         }

         return resObj;
      }
   }

   private ResponseObject requestWorkItemObject(RequestObjectFinancialUpdate reqObj, String sessionId, String processInstanceName, ResponseObject resObj) throws AxisFault, RemoteException {
      WorkItemObject[] workItemObjects = null;
      BLServer blserver = BLClientUtil.getBizLogicServer();
      Session blsession = blserver.connect(reqObj.getUserId(), this.getUserPasswordECS(reqObj.getUserId()));
      WorkItem wi = null;
      boolean isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);
      sessionId = this.connectUser("rgicl");

      try {
         logger.info("RGICL_CMS_WS.requestWorkItemObject() service isInstanceCompleted::" + isInstanceCompleted);
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
                     wiObject.setRoleName(roleName);
                     logger.info("RGICL_CMS_WS.requestWorkItemObject() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                     workItemObjects[i] = wiObject;
                  }
               }

               resObj.setWorkItemObjects(workItemObjects);
            }
         }
      } catch (Exception var23) {
         logger.error("RGICL_CMS_WS.requestWorkItemObject() Exception " + var23.getMessage());
      } finally {
         this.disconnect(sessionId);
      }

      return resObj;
   }

   private void updateDecision(RequestObjectFinancialUpdate reqObj, ResponseObject resObj) {
      String userId = reqObj.getUserId();
      String bl_user = userId;
      String bl_password = this.getUserPasswordECS(userId);
      BLServer blserver = BLClientUtil.getBizLogicServer();
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
         blsession = blserver.connect(bl_user, bl_password);
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
                  responseCode = 5067;
               } else {
                  responseCode = 5055;
                  logger.error("RGICL_CMS_WS.updateDecision() Workitem is already assigned");
               }
            } else {
               String newUserId;
               User usr;
               if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                  newUserId = reqObj.getNewUserId();
                  if (newUserId != null && newUserId.length() != 0) {
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WS.updateDecision() new user " + newUserId + " is not present in savvion");
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

                        responseCode = 5066;
                     }
                  } else {
                     logger.error("RGICL_CMS_WS.updateDecision() Input value is null ");
                     responseCode = 5010;
                  }
               } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                  newUserId = reqObj.getNewUserId();
                  usr = UserManager.getUser(newUserId);
                  if (usr == null) {
                     logger.error("RGICL_CMS_WS.updateDecision() new user " + newUserId + " is not present in savvion");
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

                     responseCode = 5065;
                  }
               }
            }
         }
      } catch (RemoteException var24) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateDecision() RemoteException " + var24.getMessage());
      } catch (Exception var25) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateDecision() Exception " + var25.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setProcessInstanceName(processInstanceName);
      resObj.setResponseCode(responseCode);
   }

   private ResponseObject requestWorkItemObject(RequestObjectSurveyorExpensePaymentUpdate reqObj, String sessionId, String processInstanceName, ResponseObject resObj) throws AxisFault, RemoteException {
      WorkItemObject[] workItemObjects = null;
      BLServer blserver = BLClientUtil.getBizLogicServer();
      Session blsession = blserver.connect(reqObj.getUserId(), this.getUserPasswordECS(reqObj.getUserId()));
      WorkItem wi = null;
      boolean isInstanceCompleted = this.isInstanceCompleted(blsession, processInstanceName);

      try {
         sessionId = this.connectUser("rgicl");
         logger.info("RGICL_CMS_WS.requestWorkItemObject() service isInstanceCompleted::" + isInstanceCompleted);
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
                     wiObject.setRoleName(roleName);
                     logger.info("RGICL_CMS_WS.requestWorkItemObject() workStepName-" + workStepName + ", workItemName-" + wiName + ", perfomer-" + performer + ", workItemId-" + workItemId + " ,roleName-" + roleName);
                     workItemObjects[i] = wiObject;
                  }
               }

               resObj.setWorkItemObjects(workItemObjects);
            }
         }
      } catch (Exception var23) {
         logger.error("RGICL_CMS_WS.requestWorkItemObject() Exception " + var23.getMessage());
      } finally {
         this.disconnect(sessionId);
      }

      return resObj;
   }

   private void updateDecision(RequestObjectSurveyorExpensePaymentUpdate reqObj, ResponseObject resObj) {
      String userId = reqObj.getUserId();
      String bl_user = userId;
      String bl_password = this.getUserPasswordECS(userId);
      BLServer blserver = BLClientUtil.getBizLogicServer();
      String processInstanceName = reqObj.getProccessInstanceName();
      String workItemName = reqObj.getWorkItemName();
      workItemName = workItemName.contains("HM") ? "HM" : workItemName;
      workItemName = workItemName.contains("ZM") ? "ZM" : workItemName;
      workItemName = workItemName.contains("CO") ? "CO" : workItemName;
      workItemName = workItemName.contains("CH") ? "CH" : workItemName;
      workItemName = workItemName.contains("VH") ? "VH" : workItemName;
      workItemName = workItemName.contains("CEO") ? "CEO" : workItemName;
      workItemName = workItemName.contains("CVM") ? "CVM" : workItemName;
      workItemName = workItemName.contains("TL") ? "TL" : workItemName;
      int responseCode = 0;
      Session blsession = null;

      try {
         blsession = blserver.connect(bl_user, bl_password);
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
                  responseCode = 5055;
                  logger.error("RGICL_CMS_WS.updateDecision() Workitem is already assigned");
               }
            } else {
               String newUserId;
               User usr;
               if (reqObj.getOperationType().equalsIgnoreCase("reassign")) {
                  newUserId = reqObj.getNewUserId();
                  if (newUserId != null && newUserId.length() != 0) {
                     usr = UserManager.getUser(newUserId);
                     if (usr == null) {
                        logger.error("RGICL_CMS_WS.updateDecision() new user " + newUserId + " is not present in savvion");
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
                     logger.error("RGICL_CMS_WS.updateDecision() Input value is null ");
                     responseCode = 5010;
                  }
               } else if (reqObj.getOperationType().equalsIgnoreCase("assign")) {
                  newUserId = reqObj.getNewUserId();
                  usr = UserManager.getUser(newUserId);
                  if (usr == null) {
                     logger.error("RGICL_CMS_WS.updateDecision() new user " + newUserId + " is not present in savvion");
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
         logger.error("RGICL_CMS_WS.updateDecision() Exception " + var21.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setProcessInstanceName(processInstanceName);
      resObj.setResponseCode(responseCode);
   }

   private WorkItemList getWorkItemList(String session, String piName, String wsName) {
      logger.info("RGICL_CMS_WS.getWorkItemList() session::" + session + ", piName::" + piName + ", wsName::" + wsName);
      WorkItemList workitemList = null;

      try {
         workitemList = getBizLogicManager().getWorkItemList(session, piName, wsName);
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Axis fault Exception " + var6.getMessage());
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WS.getWorkItemList() RemoteException " + var7.getMessage());
      } catch (EJBException var8) {
         logger.error("RGICL_CMS_WS.getWorkItemList() EJBException " + var8.getMessage());
      } catch (Exception var9) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Exception " + var9.getMessage());
      }

      return workitemList;
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

   public ResponseObject getWorkItemListDB(String piName) {
      logger.info("RGICL_CMS_WS.getWorkItemListDB () session::, piName::" + piName + ", wsName::");
      String[] workitemList = null;
      ResponseObject resObj = null;
      boolean isInstanceCompleted = true;
      String sqlString = "Select workitem_name from bizlogic_workitem where process_instance_id=?";

      try {
         logger.info("RGICL_CMS_WS.getWorkItemListDB () In Try");
         if (piName != null && piName.contains("#")) {
            String[] strPidArr = piName.split("#");
            String strPiid = strPidArr[1];
            long pid = Long.parseLong(strPiid);
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

            if (ls.size() > 0 && pid > 0L) {
               isInstanceCompleted = false;
               workitemList = (String[])ls.toArray(new String[ls.size()]);
            }

            try {
               if (this.rs != null) {
                  this.rs.close();
               }

               if (this.pstmt != null) {
                  this.pstmt.close();
               }

               if (this.connection != null) {
                  this.connection.close();
               }
            } catch (Exception var32) {
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
                  a.setRoleName(arr[1]);
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
      } catch (Exception var33) {
         logger.error("RGICL_CMS_WS.getWorkItemList() Exception " + var33.getMessage());
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

   private String getWorkItemId(String session, String wiName) {
      logger.info("RGICL_CMS_WS.getWorkItemId() wiName is " + wiName);
      String workItemId = null;
      PAKClientWorkitem PAKClientWorkitem = null;

      try {
         PAKClientWorkitem = getBizLogicManager().getWorkitem(session, wiName);
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WS.getWorkItemId() Axis fault Exception " + var6.getMessage());
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WS.getWorkItemId() RemoteException " + var7.getMessage());
      } catch (EJBException var8) {
         logger.error("RGICL_CMS_WS.getWorkItemId() EJBException " + var8.getMessage());
      } catch (Exception var9) {
         logger.error("RGICL_CMS_WS.getWorkItemId() Exception " + var9.getMessage());
      }

      if (PAKClientWorkitem != null) {
         workItemId = String.valueOf(PAKClientWorkitem.getWorkItemID());
      }

      logger.info("RGICL_CMS_WS.getWorkItemId() workItemId is" + workItemId);
      return workItemId;
   }

   private String[] getActiveWorkStepList(String session, String piName) {
      String[] workStepList = null;
      logger.info("RGICL_CMS_WS.getActiveWorkStepList() called");
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         List<String> ls = new ArrayList();
         String sql = "select workstep_name from bizlogic_workstepinstance where status=18 and process_instance_id=?";
         if (piName != null && piName.contains("#")) {
            String[] strPidArr = piName.split("#");
            String strPiid = strPidArr[1];
            long pid = Long.parseLong(strPiid);
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            con = this.ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, Long.valueOf(strPiid));
            rs = pstmt.executeQuery();
            logger.info("RGICL_CMS_WS.getWorkItemListDB () OBJ Init");

            while(rs.next()) {
               ls.add(rs.getString("workstep_name"));
            }

            if (rs != null) {
               rs.close();
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (con != null) {
               con.close();
            }
         }

         if (ls.size() > 0) {
            workStepList = (String[])ls.toArray(new String[ls.size()]);
            return workStepList;
         }
      } catch (Exception var13) {
         logger.error("RGICL_CMS_WS.getActiveWorkStepList() Exception " + var13.getMessage());
      }

      return workStepList;
   }

   public ResponseObject makeAvailableTask(RequestObject[] reqObjs) {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      String[] workItemNames = null;
      String performer = null;
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
                     this.getWorkItemPerformerECS(workItemNames[index]);
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
         } catch (Exception var14) {
            logger.error("RGICL_CMS_WS.makeAvailableTask() Exception " + var14.getMessage());
            responseCode = 5035;
         } finally {
            this.disconnectBlSession(blsession);
         }

         resObj.setResponseCode(responseCode);
      }

      return resObj;
   }

   private boolean isInstanceCompleted(Session blsession, String processInstanceName) {
      boolean isInstanceCompleted = false;
      BLServer blserver = BLClientUtil.getBizLogicServer();

      try {
         boolean b = blserver.isProcessInstanceExist(blsession, processInstanceName);
         logger.info("RGICL_CMS_WS.isInstanceCompleted() is processInstanceName " + processInstanceName + " Exist " + b);
         if (!b) {
            isInstanceCompleted = true;
         }
      } catch (Exception var6) {
         logger.error("RGICL_CMS_WS.isInstanceCompleted() Exception in isinstancecompleted " + var6.getMessage());
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
         logger.error("RGICL_CMS_WS.getMap() Exception " + var7.getMessage());
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
                  logger.error("RGICL_CMS_WS.getBizLogicManager() Throwable " + var3);
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
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         logger.error("RGICL_CMS_WS.createProcessInstance() RemoteException " + var8.getMessage());
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private String connectUser(String userId) {
      String sessionId = "false";
      boolean isMember = true;
      String password = null;

      try {
         password = this.getUserPasswordECS(userId);
         if (isMember) {
            sessionId = getBizLogicManager().connect(userId, password);
         }
      } catch (RemoteException var6) {
         logger.error("RGICL_CMS_WS.connectUser() RemoteException " + var6.getMessage());
         sessionId = "false" + var6.getMessage();
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
         logger.error("RGICL_CMS_WS.getUserPasswordECS() Exception " + var5.getMessage());
      }

      return password;
   }

   private boolean checkUserInGroup(String userId, String groupName) {
      boolean isMember = false;

      try {
         logger.info("RGICL_CMS_WS.checkUserInGroup() private method checkUserInGroup, checking whether user " + userId + " is there in group " + groupName);
         User usr = UserManager.getUser(userId);
         String[] groupNames = usr.getGroupNames();

         for(int i = 0; i < groupNames.length; ++i) {
            logger.info("RGICL_CMS_WS.checkUserInGroup() user is in group " + groupNames[i]);
            if (groupNames[i].equals(groupName)) {
               isMember = true;
            }
         }
      } catch (Exception var7) {
         logger.error("RGICL_CMS_WS.checkUserInGroup() Exception Exception " + var7.getMessage());
      }

      return isMember;
   }

   private boolean checkGroupexists(String groupName) {
      Realm r = UserManager.getDefaultRealm();
      String[] groupNames = r.getGroupNames();

      for(int i = 0; i < groupNames.length; ++i) {
         if (groupNames[i].equals(groupName)) {
            return true;
         }
      }

      return false;
   }

   private boolean checkUserexists(String userName) {
      Realm r = UserManager.getDefaultRealm();
      String[] userNames = r.getUserNames();

      for(int i = 0; i < userNames.length; ++i) {
         if (userNames[i].equals(userName)) {
            return true;
         }
      }

      return false;
   }

   private String getWorkItemPerformerECS(String workItemName) {
      String result = null;

      try {
         if (workItemName != null && !workItemName.equals("")) {
            result = (String)getBizLogicManager().getWorkitemInfo(connectECSADMIN(), workItemName).get("PERFORMER");
         } else {
            result = Integer.valueOf(5010).toString();
         }

         return result;
      } catch (Exception var4) {
         logger.error("RGICL_CMS_WS.getWorkItemPerformerECS() Exception " + var4.getMessage());
         return Integer.valueOf(5020).toString();
      }
   }

   private Hashtable getHashTableFromObject(RequestObjectTechnicalApproval reqOb) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectTechnicalApproval.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqOb);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() IllegalArgumentException " + var8.getMessage());
         } catch (Exception var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() Exception " + var9.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromObjectVS(RequestObjectTechnicalApprovalVS reqOb) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectTechnicalApprovalVS.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqOb);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() IllegalArgumentException " + var8.getMessage());
         } catch (Exception var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() Exception " + var9.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private Hashtable getHashTableFromObjectVS(RequestObjectAutoboticsTechnicalFlow reqOb) {
      Hashtable resolvedValues = new Hashtable();
      Field[] fields = RequestObjectAutoboticsTechnicalFlow.class.getFields();
      logger.info("RGICL_CMS_WS.getHashTableFromObject() number of fields is " + fields.length);
      String fieldName = null;
      String fieldValue = null;

      for(int i = 0; i < fields.length; ++i) {
         fieldName = fields[i].getName();

         try {
            fieldValue = (String)fields[i].get(reqOb);
         } catch (IllegalArgumentException var8) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() IllegalArgumentException " + var8.getMessage());
         } catch (Exception var9) {
            logger.error("RGICL_CMS_WS.getHashTableFromObject() Exception " + var9.getMessage());
         }

         resolvedValues.put(fieldName, fieldValue);
      }

      return resolvedValues;
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         logger.error("RGICL_CMS_WS.completeWorkitem() Exception " + var4.getMessage());
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
         logger.error("RGICL_CMS_WS.disconnect() Exception " + var3.getMessage());
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
         logger.error("RGICL_CMS_WS.disconnectBlSession() Exception " + var3.getMessage());
         return false;
      }
   }

   private String getDataSlotValue(String sessionId, String processInstanceName, String dataSlotName) {
      String value = null;
      logger.info("RGICL_CMS_WS.getDataSlotValue() dataSlotName is " + dataSlotName);

      try {
         value = (String)getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dataSlotName);
         return value;
      } catch (AxisFault var6) {
         logger.error("RGICL_CMS_WS.getDataSlotValue() AxisFault Exception " + var6.getMessage());
         throw new RuntimeException(var6.getCause());
      } catch (RemoteException var7) {
         logger.error("RGICL_CMS_WS.getDataSlotValue() RemoteException " + var7.getMessage());
         throw new RuntimeException(var7.getCause());
      }
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         logger.error("RGICL_CMS_WS.assignWorkitem() RemoteException " + var4.getMessage());
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   public int changeUserGroup(String userId, String existingGroupName, String newGroupName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.changeUserGroup() called");
      logger.info("RGICL_CMS_WS.changeUserGroup()  userId is " + userId + ", existingGroupName is " + existingGroupName + ", newGroupName is " + newGroupName);
      if (existingGroupName.equals(newGroupName)) {
         logger.error("RGICL_CMS_WS.changeUserGroup() existingGroupName and newGroupName is the same" + existingGroupName);
         return 5043;
      } else {
         int responseCode = this.removeUserFromGroup(userId, existingGroupName, authenticationDetail);
         if (responseCode == 5000) {
            responseCode = this.addUserToGroup(userId, newGroupName, authenticationDetail);
            if (responseCode != 5000) {
               logger.error("RGICL_CMS_WS.changeUserGroup() Adding User " + userId + " to new group " + newGroupName + " fails");
               logger.info("RGICL_CMS_WS.changeUserGroup()Adding the user " + userId + " back to the existing group " + existingGroupName);
               this.addUserToGroup(userId, existingGroupName, authenticationDetail);
            } else {
               logger.info("RGICL_CMS_WS.changeUserGroup() Changing User Group is done successfully. User " + userId + " is removed from the group " + existingGroupName + " and added to the new group " + newGroupName);
            }

            return responseCode;
         } else {
            logger.error("RGICL_CMS_WS.changeUserGroup() Remove User " + userId + "from existing group " + existingGroupName + " fails");
            logger.error("RGICL_CMS_WS.changeUserGroup() Fails while removing user from existing group");
            return responseCode;
         }
      }
   }

   public int addUserToGroup(String userId, String groupName, AuthenticationDetail authenticationDetail) {
      int responseCode = 0;
      boolean status = false;
      AdvanceGroup groupObj = null;
      logger.info("RGICL_CMS_WS.addUserToGroup() called");
      logger.info("RGICL_CMS_WS.addUserToGroup() userId is " + userId + ", groupName is " + groupName);
      if (!this.isAuthenticated(authenticationDetail)) {
         return 5040;
      } else {
         
         if (userId != null && groupName != null) {
            try {
               if (!this.checkGroupexists(groupName)) {
                  logger.error("RGICL_CMS_WS.addUserToGroup() Group named " + groupName + " Doesn't exists");
                  return 5041;
               }

               if (!this.checkUserexists(userId)) {
                  logger.error("RGICL_CMS_WS.addUserToGroup() UserID " + userId + " Doesn't exists");
                  return 5042;
               }

               if (!this.checkUserInGroup(userId, groupName)) {
                  logger.info("RGICL_CMS_WS.addUserToGroup() adding user to group, since user is not in group");
                  groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
                  status = groupObj.addUserMember(userId);
                  if (status) {
                     logger.info("RGICL_CMS_WS.addUserToGroup() user " + userId + " successfully added to group");
                     responseCode = 5000;
                  } else {
                     logger.error("RGICL_CMS_WS.addUserToGroup() user " + userId + " could not be added to group");
                     responseCode = 5035;
                  }
               } else {
                  logger.error("RGICL_CMS_WS.addUserToGroup() user " + userId + " is already in group");
                  responseCode = 5011;
               }
            } catch (Exception var8) {
               logger.error("RGICL_CMS_WS.addUserToGroup() Exception " + var8.getMessage());
               responseCode = 5035;
            }
         } else {
            logger.info("RGICL_CMS_WS.addUserToGroup() INPUT_VALUE_IS_NULL");
            responseCode = 5010;
         }

         return responseCode;
      }
   }

   public int removeUserFromGroup(String userId, String groupName, AuthenticationDetail authenticationDetail) {
      int responseCode = 0;
      boolean status = false;
      AdvanceGroup groupObj = null;
      logger.info("RGICL_CMS_WS.removeUserFromGroup() called");
      logger.info("RGICL_CMS_WS.removeUserFromGroup() userId is " + userId + ", groupName is " + groupName);
      if (!this.isAuthenticated(authenticationDetail)) {
         return 5040;
      } else {
         
         if (userId != null && groupName != null) {
            try {
               if (!this.checkGroupexists(groupName)) {
                  logger.error("RGICL_CMS_WS.removeUserFromGroup() group " + groupName + " Doesn't exists");
                  return 5041;
               }

               if (this.checkUserInGroup(userId, groupName)) {
                  logger.info("RGICL_CMS_WS.removeUserFromGroup() remove user " + userId + " from group, since user is in group");
                  groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
                  status = groupObj.removeUserMember(userId);
                  if (status) {
                     logger.info("RGICL_CMS_WS.removeUserFromGroup() user " + userId + " successfully removed from group");
                     responseCode = 5000;
                  } else {
                     logger.error("RGICL_CMS_WS.removeUserFromGroup() user " + userId + " could not be removed from group");
                     responseCode = 5035;
                  }
               } else {
                  logger.error("RGICL_CMS_WS.removeUserFromGroup() user " + userId + " is not in group");
                  responseCode = 5012;
               }
            } catch (Exception var8) {
               logger.error("RGICL_CMS_WS.removeUserFromGroup() Exception " + var8.getMessage());
               responseCode = 5035;
            }
         } else {
            logger.error("RGICL_CMS_WS.removeUserFromGroup() INPUT_VALUE_IS_NULL");
            responseCode = 5010;
         }

         return responseCode;
      }
   }

   public int createUser(String userId, String firstName, String lastName, String email, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createUser() userId is " + userId);
      int responseCode = 0;
      
      if (this.isAuthenticated(authenticationDetail)) {
         logger.info("RGICL_CMS_WS.createUser() authenticated");
         if (userId == null) {
            logger.error("RGICL_CMS_WS.createUser() INPUT_VALUE_IS_NULL");
            responseCode = 5010;
         } else {
            try {
               Realm r = UserManager.getDefaultRealm();
               boolean b = r.addUser(userId);
               if (b) {
                  logger.info("RGICL_CMS_WS.createUser() new user " + userId + " added successfully to savvion");
                  User sbmUser = r.getUser(userId);
                  sbmUser.setAttribute("password", userId);
                  sbmUser.setAttribute("firstname", firstName);
                  sbmUser.setAttribute("lastname", lastName);
                  sbmUser.setAttribute("email", email);
                  responseCode = 5000;
               } else {
                  responseCode = 5014;
                  logger.warn("RGICL_CMS_WS.createUser() user " + userId + " already present in savvion");
               }
            } catch (Exception var10) {
               logger.error("RGICL_CMS_WS.createUser() Exception " + var10.getMessage());
               responseCode = 5035;
            }
         }
      } else {
         logger.error("RGICL_CMS_WS.createUser() authentication fails");
         responseCode = 5040;
      }

      return responseCode;
   }

   public int deleteUser(String userId, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.deleteUser() userId is " + userId);
      int responseCode = 0;
      
      if (this.isAuthenticated(authenticationDetail)) {
         logger.info("RGICL_CMS_WS.deleteUser() authenticated");
         if (userId == null) {
            logger.error("RGICL_CMS_WS.deleteUser() INPUT_VALUE_IS_NULL");
            responseCode = 5010;
         } else {
            try {
               Realm r = UserManager.getDefaultRealm();
               boolean b = r.removeUser(userId);
               if (b) {
                  logger.info("RGICL_CMS_WS.deleteUser() user " + userId + " removed from savvion");
                  responseCode = 5000;
               } else {
                  responseCode = 5015;
                  logger.error("RGICL_CMS_WS.deleteUser() user not present in savvion");
               }
            } catch (Exception var6) {
               logger.error("RGICL_CMS_WS.deleteUser() Exception " + var6.getMessage());
               responseCode = 5035;
            }
         }
      } else {
         logger.error("RGICL_CMS_WS.deleteUser() authentication fails");
         responseCode = 5040;
      }

      return responseCode;
   }

   public int updateUser(String userId, String fieldName, String fieldValue, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateUser() userId is " + userId);
      int responseCode = 0;
      
      if (this.isAuthenticated(authenticationDetail)) {
         logger.info("RGICL_CMS_WS.updateUser() authenticated");
         if (userId != null && fieldName != null && fieldValue != null) {
            if (fieldName.equals("userid")) {
               logger.error("RGICL_CMS_WS.updateUser() userid  field can't be updated");
               return 5044;
            }

            try {
               User usr = UserManager.getUser(userId);
               if (usr == null) {
                  logger.error("RGICL_CMS_WS.updateUser() user not is not present savvion");
                  responseCode = 5015;
               } else {
                  boolean b = false;
                  boolean isFieldValid = false;
                  String[] fieldNames = usr.getAttributeNames();

                  for(int i = 0; i < fieldNames.length; ++i) {
                     logger.info("RGICL_CMS_WS.updateUser() fieldNames" + fieldNames[i].toString());
                     if (fieldNames[i].equals(fieldName)) {
                        isFieldValid = true;
                        break;
                     }
                  }

                  if (isFieldValid) {
                     b = usr.setAttribute(fieldName, fieldValue);
                     if (b) {
                        logger.info("RGICL_CMS_WS.updateUser() value updated successfully");
                        responseCode = 5000;
                     } else {
                        logger.error("RGICL_CMS_WS.updateUser() value could not be updated");
                        responseCode = 5035;
                     }
                  } else {
                     logger.error("RGICL_CMS_WS.updateUser() value could not be updated as the field to be updated is  not found ");
                     responseCode = 5016;
                  }
               }
            } catch (Exception var11) {
               logger.error("RGICL_CMS_WS.updateUser() Exception " + var11.getMessage());
               responseCode = 5035;
            }
         } else {
            logger.error("RGICL_CMS_WS.updateUser() INPUT_VALUE_IS_NULL");
            responseCode = 5010;
         }
      } else {
         logger.error("RGICL_CMS_WS.updateUser() not authenticated");
         responseCode = 5040;
      }

      return responseCode;
   }

   public FlowDetails getDetailsForFlow(String flowName) {
      String sessionId = null;
      logger.info("RGICL_CMS_WS.getDetailsForFlow() called");
      Info[] infos = null;
      FlowDetails flowDetail = new FlowDetails();

      try {
         ProcessInstanceList processInstanceList = this.getActivePIList(flowName);
         logger.info("RGICL_CMS_WS.getDetailsForFlow() getting activePIList");
         if (processInstanceList != null) {
            logger.info("RGICL_CMS_WS.getDetailsForFlow() PIList is not null");
            sessionId = this.connectUser("rgicl");
            List instanceList = processInstanceList.getList();
            logger.info("RGICL_CMS_WS.getDetailsForFlow() getting List of instance");
            if (instanceList != null && instanceList.size() > 0 && !instanceList.isEmpty()) {
               logger.info("RGICL_CMS_WS.getDetailsForFlow() List of instances not empty");
               infos = new Info[instanceList.size()];

               for(int i = 0; i < instanceList.size(); ++i) {
                  Info info = new Info();
                  logger.info("RGICL_CMS_WS.getDetailsForFlow() Getting instance " + i);
                  ProcessInstance obj = (ProcessInstance)instanceList.get(i);
                  String processInstanceName = obj.getName();
                  logger.info("RGICL_CMS_WS.getDetailsForFlow() Getting instance name " + i);
                  logger.info("RGICL_CMS_WS.getDetailsForFlow() Getting dataslot value, parameters are: sessionId is " + sessionId + " and processInstanceName is " + processInstanceName);
                  String claimId = this.getDataSlotValue(sessionId, processInstanceName, "claimReferenceNumber");
                  String zoneId = this.getDataSlotValue(sessionId, processInstanceName, "zoneId");
                  String productId = this.getDataSlotValue(sessionId, processInstanceName, "productId");
                  String hubId = this.getDataSlotValue(sessionId, processInstanceName, "hubId");
                  logger.info("RGICL_CMS_WS.getDetailsForFlow() claimId" + claimId + "zoneId" + zoneId + "productId" + productId + "hubId" + hubId);
                  info.setClaimId(claimId);
                  info.setHubId(hubId);
                  info.setProductId(productId);
                  info.setZoneID(zoneId);
                  infos[i] = info;
               }

               flowDetail.setInfo(infos);
            }
         }
      } catch (Exception var18) {
         logger.info("RGICL_CMS_WS.getDetailsForFlow() Exception " + var18.getMessage());
         var18.printStackTrace();
      } finally {
         this.disconnect(sessionId);
      }

      logger.error("RGICL_CMS_WS.getDetailsForFlow() size of list is " + flowDetail.getInfo().length);
      return flowDetail;
   }

   private ProcessInstanceList getActivePIList(String flowName) throws AxisFault, BizLogicClientException, RemoteException {
      if (this.getActivePTVersion(flowName) != null) {
         ProcessInstanceList objPIList = this.getActivePTVersion(flowName).getActivatedProcessInstanceList();
         return objPIList;
      } else {
         return null;
      }
   }

   private ProcessTemplate getActivePTVersion(String flowName) throws AxisFault, BizLogicClientException, RemoteException {
      ProcessTemplate objProcessTemplate = null;
      BLServer blserver = BLClientUtil.getBizLogicServer();
      Session blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
      if (Application.isExist(blsessionCMSAdmin, flowName)) {
         objProcessTemplate = Application.getActiveVersion(blsessionCMSAdmin, flowName);
      }

      blserver.disConnect(blsessionCMSAdmin);
      return objProcessTemplate;
   }

   private boolean isAuthenticated(AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.isAuthenticated() called");
      logger.info("RGICL_CMS_WS.isAuthenticated() authenticationDetail is " + authenticationDetail);
      if (authenticationDetail != null) {
         if ("RGICL_CMS_USER".equals(authenticationDetail.getAuthenticationId()) && "RGICL_CMS_PASS".equals(authenticationDetail.getAuthenticationPassword())) {
            logger.info("RGICL_CMS_WS.isAuthenticated() authentication success");
            return true;
         } else {
            logger.error("RGICL_CMS_WS.isAuthenticated() authentication failure");
            return false;
         }
      } else {
         logger.error("RGICL_CMS_WS.isAuthenticated() authentication failure");
         return false;
      }
   }

   public String getGreetingNew(String name) {
      String result = "Hello ! " + name;
      return result;
   }

   public boolean isUserValid(String userId, String productId, String hubId, String zoneId) {
      logger.info("RGICL_CMS_WS.isUserValid() called");
      boolean result = false;
      DBUtil dbUtilObj = new DBUtil();
      logger.info("RGICL_CMS_WS.isUserValid() DBUtil obj created");
      result = dbUtilObj.isUserValid(userId, productId, zoneId, hubId);
      logger.info("RGICL_CMS_WS.isUserValid() DBUtil isUserValid method called, result is " + result);
      return result;
   }

   private void checkConnection() throws AxisFault {
      try {
         getBizLogicManager().checkConnection();
      } catch (Exception var2) {
         logger.error("RGICL_CMS_WS.checkConnection() Exception " + var2.getMessage());
         throw new AxisFault("SBM Web services error : " + var2.getMessage());
      }
   }

   private void getServerStatus() throws AxisFault {
      try {
         int i = getBizLogicManager().getStatus();
         logger.info("BizLogicServerStatus " + i);
      } catch (Exception var2) {
         logger.error("RGICL_CMS_WS.getServerStatus() Exception " + var2.getMessage());
         throw new AxisFault("SBM Web services error : " + var2.getMessage());
      }
   }
}
