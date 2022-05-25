import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgiclcms.cms.savvion.dbUtil.DBUtil;
import rgiclcms.cms.savvion.dbUtil.DBUtility;
import rgiclcms.cms.savvion.policy.objectmodel.AuthenticationDetail;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObject;
import rgiclcms.cms.savvion.policy.objectmodel.RequestObjectOEOM;
import rgiclcms.cms.savvion.policy.objectmodel.ResponseObject;
import rgiclcms.cms.savvion.policy.objectmodel.WorkItemObject;

public class RGICL_CMS_OEOMService {
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
   private static final int INPUT_VALUE_IS_NULL = 5010;
   private static final int USER_ID_INVALID = 5013;
   private static final int USER_NOT_PRESENT = 5015;
   private static final int INVALID_WINAME = 5020;
   private static final int FAILURE_EXCEPTION = 5035;
   private static final int REQUEST_OBJECT_IS_NULL = 5038;
   private static final int NOT_AUTHENTICATED = 5040;
   private static final int UNMATCHED_OPERATION_TYPE = 5054;
   private static final int WORKITEM_ALREADY_UNASSIGNED = 5055;
   private static final int REJECTED = 5070;
   private static final int OPTION_NOT_ALLOW = 5071;
   static final String ECSAdminUserName = "rgicl";
   static final String ECSAdminPassword = "rgicl";
   boolean isSavvionAuditEnable = false;
   int ThreadSleep = 0;
   private static Logger logger = Logger.getLogger(RGICL_CMS_WS.class);
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

   public RGICL_CMS_OEOMService() {
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
         logger.info("RGICL_CMS_WS.OEOMS() Exception " + var2.getMessage());
      }

   }

   public ResponseObject createWorkFlowForOEOM(RequestObjectOEOM reqObj, String processName, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() called");
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
            responseCode = 1;
            logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() request object is null");
         } else if (processName != null && reqObj.getClaimReferenceNumber() != null && !processName.equals("") && !reqObj.getClaimReferenceNumber().equals("")) {
            String sessionId = null;

            try {
               logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() processName is " + processName);
               resolvedDSValues = new Hashtable();
               resolvedDSValues.put("InitiatorID", reqObj.getInitiatorId());
               resolvedDSValues.put("InitiatorRole", reqObj.getInitiatorRole());
               resolvedDSValues.put("ClaimRefNo", reqObj.getClaimReferenceNumber());
               resolvedDSValues.put("IsEliteCases", reqObj.getIsEliteCases());
               resolvedDSValues.put("HubID", reqObj.getHubID());
               resolvedDSValues.put("ZonID", reqObj.getZonID());
               resolvedDSValues.put("IsBillCheck", reqObj.getIsBillCheck());
               sessionId = connectECSADMIN();
               logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() creating processInstance resolvedDSValues " + resolvedDSValues);
               processInstanceName = this.createProcessInstance(sessionId, processName, processName, "medium", resolvedDSValues);
               Thread.sleep((long)this.ThreadSleep);
               resObj = this.getWorkItemListDB(processInstanceName);
            } catch (Exception var21) {
               logger.error("RGICL_CMS_WS.createWorkFlowForOEOM() exception occured" + var21.getMessage());
               responseCode = 1;
            } finally {
               this.disconnect(sessionId);
               this.disconnectBlSession((Session)blsessionCMSAdmin);
            }
         } else {
            logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() input value is null");
            responseCode = 1;
         }

         logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() isSavvionAuditEnable " + this.isSavvionAuditEnable);
         if (this.isSavvionAuditEnable) {
            try {
               new DBUtility();
               int rowcount = 0;
               logger.info("RGICL_CMS_WS.createWorkFlowForOEOM() SavvionAuditCount is " + rowcount);
            } catch (Exception var20) {
               logger.error("RGICL_CMS_WS.createWorkFlowForOEOM() insertSavvionAuditData Exception" + var20.getMessage());
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
         sessionId = connectECSADMIN();
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

   private boolean OEOMValidateInputSuccess(RequestObjectOEOM reqObj, AuthenticationDetail authenticationDetail, ResponseObject resObj) {
      int responseCode = 0;
      if (!this.isAuthenticated(authenticationDetail)) {
         responseCode = 5040;
         resObj.setResponseCode(responseCode);
      } else if (reqObj == null) {
         responseCode = 5038;
         resObj.setResponseCode(responseCode);
         logger.error("RGICL_CMS_WS.OEOMValidateInputSuccess() request object is null");
      } else if (reqObj.getProcessInstanceName() != null && reqObj.getWorkItemName() != null && reqObj.getUserId() != null && reqObj.getProcessInstanceName().length() != 0 && reqObj.getWorkItemName().length() != 0 && reqObj.getUserId().length() != 0) {
         if (reqObj.getUserId() != null && !reqObj.getUserId().equals("")) {
            if (UserManager.getUser(reqObj.getUserId()) == null) {
               logger.error("RGICL_CMS_WS.OEOMValidateInputSuccess() user " + reqObj.getUserId() + " is not present in savvion");
               responseCode = 5015;
               resObj.setResponseCode(responseCode);
            } else if (!reqObj.getOperationType().equalsIgnoreCase("Acquire") && !reqObj.getOperationType().equalsIgnoreCase("AssignToOE") && !reqObj.getOperationType().equalsIgnoreCase("reassign") && !reqObj.getOperationType().equalsIgnoreCase("Reject") && !reqObj.getOperationType().equalsIgnoreCase("Done") && !reqObj.getOperationType().equalsIgnoreCase("skip") && !reqObj.getOperationType().equalsIgnoreCase("SendBack") && !reqObj.getOperationType().equalsIgnoreCase("Approve")) {
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
         logger.error("RGICL_CMS_WS.OEOMValidateInputSuccess() Input value is NULL");
      }

      return responseCode == 0;
   }

   private ResponseObject updateOEOMUpdateDataSlot(RequestObjectOEOM reqObj, ResponseObject resObj) {
      int responseCode = 0;
      HashMap<String, String> hm = new HashMap();
      String roleName = reqObj.getInitiatorRole();
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
         logger.error("RGICL_CMS_WS.updateOEOMUpdateDataSlot() RemoteException " + var15.getMessage());
      } catch (Exception var16) {
         responseCode = 5035;
         logger.error("RGICL_CMS_WS.updateOEOMUpdateDataSlot() Exception " + var16.getMessage());
      } finally {
         this.disconnectBlSession(blsession);
      }

      resObj.setResponseCode(responseCode);
      return resObj;
   }

   public ResponseObject updateOEOMWorkFlow(RequestObjectOEOM reqObj, AuthenticationDetail authenticationDetail) {
      logger.info("RGICL_CMS_WS.updateOEOMWorkFlow() service called");
      logger.info("RGICL_CMS_WS.updateOEOMWorkFlow() UserId " + reqObj.getUserId() + " WorkItem " + reqObj.getWorkItemName() + " ApprovalLimit " + reqObj.getApproverDecision() + " ToleranceAmount " + reqObj.getInitiatorId() + " NewUserId " + reqObj.getInitiatorRole() + " ApproverDecision " + reqObj.getApproverDecision() + " RoleName " + reqObj.getInitiatorRole());
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
      WorkItemObject[] workItemObjects = null;
      String claimRefNo = "";
      String userId = reqObj.getUserId();
      if (!this.OEOMValidateInputSuccess(reqObj, authenticationDetail, resObj)) {
         return resObj;
      } else {
         if (reqObj.getOperationType().equalsIgnoreCase("Acquire") || reqObj.getOperationType().equalsIgnoreCase("AssignToOE") || reqObj.getOperationType().equalsIgnoreCase("Approve") || reqObj.getOperationType().equalsIgnoreCase("Done") || reqObj.getOperationType().equalsIgnoreCase("SendBack") || reqObj.getOperationType().equalsIgnoreCase("skip") || reqObj.getOperationType().equalsIgnoreCase("Reject")) {
            try {
               blserver = BLClientUtil.getBizLogicServer();
               blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
               ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
               HashMap hm = new HashMap();
               if (workItemName.contains("::OE")) {
                  hm.put("OEDecision", reqObj.getApproverDecision());
               } else if (!workItemName.contains("::CM") && !workItemName.contains("::TL") && !workItemName.contains("::TCM")) {
                  hm.put("IsOEOnline", false);
               } else {
                  hm.put("IsOEOnline", reqObj.getIsOEOnline());
                  hm.put("ApproveDecision", reqObj.getApproverDecision());
               }

               if (blsession != null) {
                  pi.updateSlotValue(hm);
                  pi.save();
                  pi.refresh();
                  blsessionCMSAdmin = blserver.connect("rgicl", this.getUserPasswordECS("rgicl"));
                  WorkItem wi = blserver.getWorkItem(blsessionCMSAdmin, workItemName);
                  if (WorkItem.isExist(blsessionCMSAdmin, wi.getID()) && wi.isAvailable()) {
                     this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                     logger.info("RGICL_CMS_WS.updateOEOMWorkFlow() Task assigned to user");
                  }

                  logger.info("RGICL_CMS_WS.updateOEOMWorkFlow() CompleteTask enterred");
                  this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  logger.info("RGICL_CMS_WS.updateOEOMWorkFlow() Task Completed");
                  Thread.sleep((long)this.ThreadSleep);
                  resObj = this.getWorkItemListDB(processInstanceName);
               }
            } catch (Exception var24) {
               logger.error("RGICL_CMS_WS.updateOEOMWorkFlow() Exception " + var24.getMessage());
               responseCode = 1;
            } finally {
               this.disconnect((String)sessionId);
               this.disconnectBlSession(blsession);
               this.disconnectBlSession(blsessionCMSAdmin);
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
         String sessionId = connectECSADMIN();
         isInstanceCompleted = this.isInstanceCompleted(blsession, piName);
         resObj = new ResponseObject();
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

      try {
         workStepList = getBizLogicManager().getProcessInstanceActiveWorkstepList(session, piName);
      } catch (AxisFault var5) {
         logger.error("RGICL_CMS_WS.getActiveWorkStepList() Axis fault Exception " + var5.getMessage());
      } catch (RemoteException var6) {
         logger.error("RGICL_CMS_WS.getActiveWorkStepList() RemoteException " + var6.getMessage());
      } catch (Exception var7) {
         logger.error("RGICL_CMS_WS.getActiveWorkStepList() Exception " + var7.getMessage());
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
