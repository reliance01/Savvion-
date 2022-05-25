package rgicl.ecsehc.savvion.OPD;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.HcsLogObject;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;
import rgicl.ecsehc.savvion.policy.util.db.DBUtility;

public class OPD_ALFlow {
   Properties logProperties = null;
   Properties config = null;
   HashMap hashMap = null;
   HashMap resultHashMap = null;
   String sbm_home = null;
   private static final String SBM_HOME = System.getProperty("sbm.home");
   private static final String ECS_SAVVION_PROPERTIES;
   private static final String ECS_SAVVION_LOG_PROPERTIES;
   private static Properties propertiesECSSavvion;
   private static Properties propertiesECSLog;
   private static Logger automatictasklog;
   DBUtility db;
   UtilClass uc;

   static {
      ECS_SAVVION_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_PROPERTIES.properties";
      ECS_SAVVION_LOG_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
      automatictasklog = null;

      try {
         propertiesECSSavvion = new Properties();
         propertiesECSSavvion.load(new FileInputStream(ECS_SAVVION_PROPERTIES));
         propertiesECSLog = new Properties();
         propertiesECSLog.load(new FileInputStream(ECS_SAVVION_LOG_PROPERTIES));
         PropertyConfigurator.configure(propertiesECSLog);
         automatictasklog = Logger.getLogger("Automatic_HCS");
      } catch (Exception var1) {
         throw new RuntimeException("Error HCS loading logger properties", var1);
      }
   }

   public OPD_ALFlow(String propertiesPath) {
      this.db = new DBUtility(SBM_HOME);
      this.uc = new UtilClass(SBM_HOME);
   }

   public ResponseObject createAndCompleteOPDALFlowTask(RequestObject[] reqObj) {
      String methodName = "createAndCompleteOPDALFlow";
      automatictasklog.info("ECS Savvion " + methodName + " Service ::::::::::::: START");
      String priority = propertiesECSSavvion.getProperty("priority");
      String sessionId = null;
      String responseCode = null;
      String responseMessage = null;
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] wiObjects = null;
      String workItemCaseStatus = "Completed";
      String nextWorkItemCaseStatus = "New";
      boolean isInstanceCompleted = false;
      WorkItemObject[] completedWorkItem = null;
      HashMap hm = new HashMap();
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      String processInstanceName;
      String userId;
      String workItemName;
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = this.uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         userId = (String)hashMap.get("USERID");
         workItemName = (String)hashMap.get("WORKITEMNAME");
         String action = (String)hashMap.get("ACTION");
         if (!this.uc.checkNull(hashMap.get("APPROVERDECISION"))) {
            workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
         }

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            if (userId == null || userId.equals("")) {
               responseCode = "5001";
               responseMessage = "USER_ID_NULL";
               automatictasklog.error("ECS Savvion: " + methodName + " :: USER_ID_NULL");
            }

            if (!this.uc.checkNull(action) && action.equals("Create")) {
               dsTypeMap.put("UserId", "STRING");
               dsValues.put("UserId", userId);
               sessionId = "false";

               try {
                  sessionId = this.uc.getBizLogicManager().connect("ECSAdmin", this.uc.getUserPasswordECS("ECSAdmin"));
               } catch (Exception var41) {
                  automatictasklog.error("ECS Savvion UtilClass: getWorkItemPerformerECS service connect ECSADMIN User:::: Exception " + var41.getMessage());
               }

               User user = UserManager.getDefaultRealm().getUser(userId);
               if (!sessionId.equals("false") && user != null) {
                  automatictasklog.info(" before resolvedDSValues");
                  Hashtable resolvedDSValues = this.uc.getDSValues(dsTypeMap, dsValues);
                  automatictasklog.info(" after resolvedDSValues");
                  processInstanceName = this.uc.createProcessInstance(sessionId, "RGICL_ECS_OPD_AL_FLOW", "RGICL_ECS_OPD_AL_FLOW", priority, resolvedDSValues);
                  automatictasklog.info("ECS Savvion: " + methodName + " :: Created ProcessInstanceName is ::  " + processInstanceName);
                  responseCode = "5000";
                  responseMessage = "SUCCESS";
                  wiObjects = this.uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                  workItemName = wiObjects[0].getName();
                  wiObjects = null;
               }
            }

            if (responseCode == null || responseCode.equals("5000")) {
               blserver = BLClientUtil.getBizLogicServer();
               blsession = blserver.connect(userId, this.uc.getUserPasswordECS(userId));
               ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
               if (workItemName != null && workItemName.contains("Approver")) {
                  hm.put("IsApproverAccepted", workItemCaseStatus);
               }

               if (!this.uc.checkNull(hashMap.get("NEEDSPMT"))) {
                  hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
               }

               if (blsession != null) {
                  pi.updateSlotValue(hm);
                  pi.save();
               }

               automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName :" + workItemName + " ::: " + workItemCaseStatus);
               blsessionECSAdmin = blserver.connect("ECSAdmin", this.uc.getUserPasswordECS("ECSAdmin"));
               WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
               if (wi.isActivated()) {
                  if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                     automatictasklog.warn("ECS AssignTask enterred");
                     this.uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                     automatictasklog.warn("ECS Task Assigned to user");
                  }

                  automatictasklog.warn("ECS CompleteTask enterred");
                  this.uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  automatictasklog.warn("ECS Task Completed");
                  responseCode = "5000";
                  responseMessage = "5000";
                  if (!this.uc.checkNull(action) && action.equals("Create")) {
                     WorkItemObject workitem = new WorkItemObject();
                     ArrayList workItemList = new ArrayList();
                     workitem.setName(workItemName);
                     workitem.setPiName(processInstanceName);
                     workitem.setCaseStatus(workItemCaseStatus);
                     workItemList.add(workitem);
                     completedWorkItem = this.uc.getResultWorkitems(workItemList);
                  }
               }

               wiObjects = this.uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
               automatictasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
               if (wiObjects != null && wiObjects.length != 0) {
                  this.uc.checkUserNumbersInGroup(wiObjects);
               }

               try {
                  blserver.getProcessInstance(blsession, processInstanceName);
               } catch (Exception var40) {
                  isInstanceCompleted = true;
               }

               automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
               if (blsession != null && blserver.isSessionValid(blsession)) {
                  blserver.disConnect(blsession);
               }

               if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
                  blserver.disConnect(blsessionECSAdmin);
               }

               blserver = null;
            }
         } catch (Exception var42) {
            if (!isInstanceCompleted) {
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
            }

            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var42);
         } finally {
            try {
               this.uc.disconnect(sessionId);
            } catch (Exception var38) {
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var38.getMessage());
            }

         }
      }

      resObj.setResponseCode(responseCode);
      resObj.setResponseMessage(responseMessage);
      resObj.setResultworkItemArray(wiObjects);
      if (completedWorkItem != null && completedWorkItem.length != 0) {
         resObj.setCompletedWorkItemArray(completedWorkItem);
      }

      resObj.setWorkItemCaseStatus(workItemCaseStatus);
      resObj.setInstanceCompleted(isInstanceCompleted);

      try {
         if (responseCode == "5000") {
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Custom logger Started ");
            processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
            userId = (String)hashMap.get("USERID");
            workItemName = (String)hashMap.get("WORKITEMNAME");
            DBUtility d = new DBUtility(SBM_HOME);
            int bln = 0;
            HcsLogObject obj = new HcsLogObject();
            obj.setPROCESSINSTANCE_NAME(processInstanceName);
            obj.setUSER_ID(userId);
            obj.setWORKITEM_NAME(workItemName);
            obj.setAPPROVER_DECISION(workItemCaseStatus);
            obj.setRESPONSE_CODE(responseCode);
            obj.setRESPONSE_MESSAGE(responseMessage);
            obj.setWORKITEM_CASESTATUS(workItemCaseStatus);
            if (isInstanceCompleted) {
               bln = 1;
            }

            obj.setISINSTANCE_COMPLETED(bln);
            d.insertHCSReqResLog(obj, wiObjects);
         }
      } catch (Exception var39) {
         automatictasklog.error("ECS Savvion OPDFlow: " + methodName + " :: Exception " + var39);
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END for " + (String)hashMap.get("WORKITEMNAME") + " with response code " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }
}
