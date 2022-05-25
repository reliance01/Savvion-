package rgicl.ecsehc.savvion.Network_Flow;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;

public class NetworkFlow {
   Properties logProperties = null;
   Properties config = null;
   HashMap hashMap = null;
   HashMap resultHashMap = null;
   private static final String SBM_HOME = System.getProperty("sbm.home");
   private static final String ECS_SAVVION_PROPERTIES;
   private static final String ECS_SAVVION_LOG_PROPERTIES;
   private static Properties propertiesECSSavvion;
   private static Properties propertiesECSLog;
   private static Logger automatictasklog;

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

   public NetworkFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteNetworkFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteNetworkFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      String sessionId = null;
      String responseCode = null;
      String responseMessage = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] wiObjects = null;
      String workItemCaseStatus = "Completed";
      String nextWorkItemCaseStatus = "New";
      boolean isInstanceCompleted = false;
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               Session blsessionECSAdmin = null;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
                        workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
                     }

                     HashMap hm = new HashMap();
                     if (workItemName != null && workItemName.contains("Relationship")) {
                        hm.put("isRMAccepted", workItemCaseStatus);
                     }

                     if (workItemName != null && workItemName.contains("NetworkMaker")) {
                        hm.put("isNetworkMakerAccepted", workItemCaseStatus);
                     }

                     String dsv;
                     if (workItemName != null && workItemName.contains("NetworkChecker")) {
                        hm.put("isNetworkCheckerAccepted", workItemCaseStatus);
                        if (!uc.checkNull(hashMap.get("APPROVERREMARKS"))) {
                           dsv = (String)pi.getDataSlotValue("NetworkCheckerRemarks");
                           if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                              dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
                           } else {
                              dsv = hashMap.get("APPROVERREMARKS").toString();
                           }

                           hm.put("NetworkCheckerRemarks", dsv);
                        }
                     }

                     if (workItemName != null && workItemName.contains("PaymentChecker")) {
                        hm.put("isPaymentCheckerAccepted", workItemCaseStatus);
                        if (!uc.checkNull(hashMap.get("APPROVERREMARKS"))) {
                           dsv = (String)pi.getDataSlotValue("PaymentCheckerRemarks");
                           if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                              dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
                           } else {
                              dsv = hashMap.get("APPROVERREMARKS").toString();
                           }

                           hm.put("PaymentCheckerRemarks", dsv);
                        }
                     }

                     if (!uc.checkNull(hashMap.get("TPANAME"))) {
                        hm.put("TPAName", hashMap.get("TPANAME").toString());
                     }

                     if (!uc.checkNull(hashMap.get("HOSPITALNAME"))) {
                        hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
                     }

                     if (!uc.checkNull(hashMap.get("HOSPITALCODE"))) {
                        hm.put("HospitalCode", hashMap.get("HOSPITALCODE").toString());
                     }

                     if (!uc.checkNull(hashMap.get("LOCATION"))) {
                        hm.put("Location", hashMap.get("LOCATION").toString());
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                        WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                        if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                           automatictasklog.warn("ECS AssignTask enterred");
                           uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           automatictasklog.warn("ECS Task Assigned to user");
                        }

                        automatictasklog.warn("ECS CompleteTask enterred");
                        uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        automatictasklog.warn("ECS Task Completed");
                        responseCode = "5000";
                        responseMessage = "SUCCESS";

                        try {
                           blserver.getProcessInstance(blsession, processInstanceName);
                        } catch (Exception var34) {
                           isInstanceCompleted = true;
                        }

                        automatictasklog.info("ECS Savvion: " + methodName + " isInstanceCompleted is " + isInstanceCompleted);
                        nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                        wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                        if (wiObjects != null && wiObjects.length != 0) {
                           uc.checkUserNumbersInGroup(wiObjects);
                        }
                     } else {
                        responseCode = "5030";
                        responseMessage = "USER_ID_INVALID";
                     }
                  } else {
                     responseCode = "5001";
                     responseMessage = "USER_ID_NULL";
                  }
               } else {
                  responseCode = "5021";
                  responseMessage = "INPUT_VALUE_IS_NULL";
               }

               if (blsession != null && blserver.isSessionValid(blsession)) {
                  blserver.disConnect(blsession);
               }

               if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
                  blserver.disConnect(blsessionECSAdmin);
               }

               e = null;
            } catch (Exception var35) {
               e = var35;
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
               var35.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var35.getMessage());
               if (var35.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion NetworkFlow: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var33) {
                     automatictasklog.error("ECS Savvion NetworkFlow: " + methodName + " :: Exception " + var33);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var32) {
               var32.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var32.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         resObj.setResultworkItemArray(wiObjects);
         resObj.setWorkItemCaseStatus(workItemCaseStatus);
         resObj.setInstanceCompleted(isInstanceCompleted);
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }

   public ResponseObject UpdateAndCompleteNetworkGroupFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteNetworkGroupFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      String sessionId = null;
      String responseCode = null;
      String responseMessage = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] wiObjects = null;
      String workItemCaseStatus = "Completed";
      String nextWorkItemCaseStatus = "New";
      UtilClass uc = new UtilClass(SBM_HOME);
      boolean isInstanceCompleted = false;
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               Session blsessionECSAdmin = null;
               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
                        workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
                     }

                     if (workItemName != null && workItemName.contains("NetworkChecker")) {
                        hm.put("isNetworkCheckerAccepted", workItemCaseStatus);
                        if (!uc.checkNull(hashMap.get("APPROVERREMARKS"))) {
                           String dsv = (String)pi.getDataSlotValue("NetworkCheckerRemarks");
                           if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
                              dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
                           } else {
                              dsv = hashMap.get("APPROVERREMARKS").toString();
                           }

                           hm.put("NetworkCheckerRemarks", dsv);
                        }
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                        WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                        if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                           automatictasklog.warn("ECS AssignTask enterred");
                           uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           automatictasklog.warn("ECS Task Assigned to user");
                        }

                        automatictasklog.warn("ECS CompleteTask enterred");
                        uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                        automatictasklog.warn("ECS Task Completed");
                        responseCode = "5000";
                        responseMessage = "SUCCESS";

                        try {
                           blserver.getProcessInstance(blsession, processInstanceName);
                        } catch (Exception var34) {
                           isInstanceCompleted = true;
                        }

                        nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                        wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                        if (wiObjects != null && wiObjects.length != 0) {
                           uc.checkUserNumbersInGroup(wiObjects);
                        }
                     } else {
                        responseCode = "5030";
                        responseMessage = "USER_ID_INVALID";
                     }
                  } else {
                     responseCode = "5001";
                     responseMessage = "USER_ID_NULL";
                  }
               } else {
                  responseCode = "5021";
                  responseMessage = "INPUT_VALUE_IS_NULL";
               }

               if (blsession != null && blserver.isSessionValid(blsession)) {
                  blserver.disConnect(blsession);
               }

               if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
                  blserver.disConnect(blsessionECSAdmin);
               }

               e = null;
            } catch (Exception var35) {
               e = var35;
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
               var35.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var35.getMessage());
               if (var35.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion NetworkFlow " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var33) {
                     automatictasklog.error("ECS Savvion NetworkFlow: " + methodName + " :: Exception " + var33);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var32) {
               var32.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var32.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         resObj.setResultworkItemArray(wiObjects);
         resObj.setWorkItemCaseStatus(workItemCaseStatus);
         resObj.setInstanceCompleted(isInstanceCompleted);
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }
}
