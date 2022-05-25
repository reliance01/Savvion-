package rgicl.ecsehc.savvion.TPAFlow;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;

public class ALTPAFlow {
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

   public ALTPAFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteALTPAFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteALTPAFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      String responseCode = null;
      String responseMessage = null;
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      String workItemCaseStatus = "Completed";
      boolean isInstanceCompleted = false;
      HashMap hm = new HashMap();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");
         if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
            workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
         }

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
               if (userId != null && !userId.equals("")) {
                  if (responseCode == null) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                     if (workItemName != null && workItemName.contains("ALTPAApprover")) {
                        hm.put("HasApproverAccepted", workItemCaseStatus);
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                        if (wi.isActivated()) {
                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           }

                           automatictasklog.warn("ECS CompleteTask enterred");
                           uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           automatictasklog.warn("ECS Task Completed");
                           responseCode = "5000";
                           responseMessage = "SUCCESS";

                           try {
                              automatictasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
                              pi = blserver.getProcessInstance(blsession, processInstanceName);
                              automatictasklog.warn("ECS " + processInstanceName + "  object is : " + pi);
                           } catch (Exception var20) {
                              isInstanceCompleted = true;
                           }

                           automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
                        } else {
                           responseCode = "5034";
                           responseMessage = "DS_NAME_INVALID";
                        }
                     } else {
                        responseCode = "5030";
                        responseMessage = "USER_ID_INVALID";
                     }
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

            blserver = null;
         } catch (Exception var21) {
            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var21);
         }
      }

      resObj.setResponseCode(responseCode);
      resObj.setResponseMessage(responseMessage);
      resObj.setWorkItemCaseStatus(workItemCaseStatus);
      resObj.setInstanceCompleted(isInstanceCompleted);
      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END for " + (String)hashMap.get("WORKITEMNAME") + " with response ::::: " + resObj);
      return resObj;
   }
}
