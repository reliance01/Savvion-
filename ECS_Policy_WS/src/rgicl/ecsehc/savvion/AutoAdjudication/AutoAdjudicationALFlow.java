package rgicl.ecsehc.savvion.AutoAdjudication;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.ALFlow.ALFlow;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;

public class AutoAdjudicationALFlow {
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
         Logger.getLogger("Automatic_HCS");
         automatictasklog = Logger.getLogger("Automatic_HCS");
      } catch (Exception var1) {
         throw new RuntimeException("Error HCS loading logger properties", var1);
      }
   }

   public AutoAdjudicationALFlow(String propertiesPath) {
   }

   public ResponseObject AutoAdjudicationALFlowTask(RequestObject[] reqObj) {
      String methodName = "autoAdjudicationALFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      ALFlow al = new ALFlow(SBM_HOME);
      new ResponseObject();
      WorkItemObject[] completedWorkItem = null;
      ArrayList workItemList = new ArrayList();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);

         try {
            automatictasklog.info("ECS Savvion: " + methodName + " entered to complete Inward task");
            ResponseObject res = al.UpdateAndCompleteALFlowTask(reqObj);
            if (res.getResponseCode() == "5000") {
               WorkItemObject workitem = new WorkItemObject();
               workitem.setName((String)hashMap.get("WORKITEMNAME"));
               workitem.setCaseStatus((String)hashMap.get("APPROVERDECISION"));
               workItemList.add(workitem);
            }

            WorkItemObject workitem;
            RequestObject[] objInput;
            if (res.getResponseCode() == "5000" && res.getResultworkItemArray()[0].getQueue().contains("ALExecutive")) {
               automatictasklog.info("ECS Savvion: " + methodName + " entered to complete Executive task");
               objInput = new RequestObject[5];
               objInput[0] = new RequestObject();
               objInput[0].setKey("PROCESSINSTANCENAME");
               objInput[0].setValue(res.getResultworkItemArray()[0].getPiName());
               objInput[1] = new RequestObject();
               objInput[1].setKey("WORKITEMNAME");
               objInput[1].setValue(res.getResultworkItemArray()[0].getName());
               objInput[2] = new RequestObject();
               objInput[2].setKey("USERID");
               objInput[2].setValue("ECSAdmin");
               objInput[3] = new RequestObject();
               objInput[3].setKey("APPROVERDECISION");
               objInput[3].setValue("Approve");
               objInput[4] = new RequestObject();
               objInput[4].setKey("ALNO");
               objInput[4].setValue((String)hashMap.get("ALNO"));
               res = al.UpdateAndCompleteALFlowTask(objInput);
               if (res.getResponseCode() == "5000") {
                  workitem = new WorkItemObject();
                  workitem.setName(objInput[1].getValue());
                  workitem.setCaseStatus(objInput[3].getValue());
                  workItemList.add(workitem);
               }
            }

            if (res.getResponseCode() == "5000" && res.getResultworkItemArray()[0].getQueue().contains("ALApprover")) {
               automatictasklog.info("ECS Savvion: " + methodName + " entered to complete Approver task");
               objInput = new RequestObject[5];
               objInput[0] = new RequestObject();
               objInput[0].setKey("PROCESSINSTANCENAME");
               objInput[0].setValue(res.getResultworkItemArray()[0].getPiName());
               objInput[1] = new RequestObject();
               objInput[1].setKey("WORKITEMNAME");
               objInput[1].setValue(res.getResultworkItemArray()[0].getName());
               objInput[2] = new RequestObject();
               objInput[2].setKey("USERID");
               objInput[2].setValue("ECSAdmin");
               objInput[3] = new RequestObject();
               objInput[3].setKey("APPROVERDECISION");
               objInput[3].setValue("Approve");
               objInput[4] = new RequestObject();
               objInput[4].setKey("ALNO");
               objInput[4].setValue((String)hashMap.get("ALNO"));
               res = al.UpdateAndCompleteALFlowTask(objInput);
               if (res.getResponseCode() == "5000") {
                  workitem = new WorkItemObject();
                  workitem.setName(objInput[1].getValue());
                  workitem.setCaseStatus(objInput[3].getValue());
                  workItemList.add(workitem);
               }
            }

            completedWorkItem = uc.getResultWorkitems(workItemList);
            resObj.setCompletedWorkItemArray(completedWorkItem);
            resObj.setResponseCode(res.getResponseCode());
            resObj.setResponseMessage(res.getResponseMessage());
            resObj.setInstanceCompleted(res.isInstanceCompleted());
            resObj.setWorkItemCaseStatus(res.getWorkItemCaseStatus());
            resObj.setResultworkItemArray(res.getResultworkItemArray());
         } catch (Exception var12) {
            automatictasklog.error(var12);
         }
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response --- " + resObj);
      return resObj;
   }
}
