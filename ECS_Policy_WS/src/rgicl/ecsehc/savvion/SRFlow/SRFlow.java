package rgicl.ecsehc.savvion.SRFlow;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;
import rgicl.ecsehc.savvion.policy.util.db.DBUtility;

public class SRFlow {
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

   public SRFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteSRFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteSRFlowTask";
      automatictasklog.info("=======ECS Savvion: " + methodName + " Service:::::START========");
      UtilClass uc = new UtilClass(SBM_HOME);
      String sessionId = null;
      new HashMap();
      ResponseObject resObjSR = new ResponseObject();
      String workItemCaseStatus = "Approve";
      ResponseObject responseObj = new ResponseObject();
      if (reqObj == null) {
         resObjSR.setResponseCode("5070");
         resObjSR.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String srWorkItemName = (String)hashMap.get("WORKITEMNAME");
         String[] piNamesList = (String[])hashMap.get("PINAMESLIST");
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUE  piNamesList[] is :: " + Arrays.toString(piNamesList));

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsessionECSAdmin = null;
               if (srPIName != null && !srPIName.equals("") && srWorkItemName != null && !srWorkItemName.equals("") && !uc.checkNull(srWorkItemName)) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, srWorkItemName);
                     if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
                        workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
                     }

                     if (wi.isActivated()) {
                        automatictasklog.info("ECS savvion " + methodName + " service :: " + srWorkItemName + " ::is active");
                        if (workItemCaseStatus.equals("Approve")) {
                           if (piNamesList == null) {
                              responseObj.setResponseCode("5000");
                              responseObj.setResponseMessage("SUCCESS");
                           } else {
                              responseObj = this.closeOrReopenALCLTask(reqObj);
                           }

                           automatictasklog.info("ECS Savvion: " + methodName + " Service:: closeOrReopenALCLTask method response is ::" + responseObj.getResponseCode());
                           if (responseObj.getResponseCode() == "5000") {
                              resObjSR = this.completeSRTask(reqObj);
                           } else {
                              resObjSR = responseObj;
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: resObjSR = responseObj " + responseObj);
                           }
                        } else {
                           resObjSR = this.completeSRTask(reqObj);
                        }

                        responseObj = uc.resultWorkItemArrayMerge(responseObj, resObjSR);
                        responseObj.setInstanceCompleted(resObjSR.isInstanceCompleted());
                        responseObj.setWorkItemCaseStatus(resObjSR.getWorkItemCaseStatus());
                        responseObj.setResponseCode(resObjSR.getResponseCode());
                        responseObj.setResponseMessage(resObjSR.getResponseMessage());
                     } else {
                        responseObj.setResponseCode("5034");
                        responseObj.setResponseMessage("5034");
                        automatictasklog.info("ECS savvion " + methodName + " service :: " + srWorkItemName + " ::is not active");
                     }
                  } else {
                     resObjSR.setResponseCode("5001");
                     resObjSR.setResponseMessage("USER_ID_NULL");
                  }
               } else {
                  resObjSR.setResponseCode("5021");
                  resObjSR.setResponseMessage("INPUT_VALUE_IS_NULL");
               }

               if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
                  blserver.disConnect(blsessionECSAdmin);
               }

               e = null;
            } catch (Exception var27) {
               e = var27;
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var27.getMessage());
               responseObj.setResponseCode("5050");
               responseObj.setResponseMessage("FAILURE_EXCEPTION");
               if (var27.getMessage().contains("Can not find <ProcessInstance>")) {
                  responseObj.setResponseCode("5034");
                  responseObj.setResponseMessage("DS_NAME_INVALID");
               }

               if (var27.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion :: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var26) {
                     automatictasklog.error("ECS Savvion : " + methodName + " :: Exception " + var26);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var25) {
               var25.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var25.getMessage());
            }

         }
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response " + responseObj);
      automatictasklog.info("**************************ECS Savvion: " + methodName + " Service::::END ***************************");
      return responseObj;
   }

   private ResponseObject closeOrReopenALCLTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
      String methodName = "closeOrReopenALCLTask";
      automatictasklog.info("------------ECS Savvion: " + methodName + " Service starts-----------");
      String nextWorkItemCaseStatus = "New";
      ArrayList resultTaskList = null;
      WorkItemObject[] workItemObject = null;
      WorkItemObject[] wiObjects = null;
      HashMap hm = new HashMap();
      new HashMap();
      ResponseObject responseObj = new ResponseObject();
      String[] result = new String[6];
      ArrayList workItemList = new ArrayList();
      WorkItemObject workItemObj = null;
      WorkItemObject[] completedWorkItem = null;
      String isFromSR = "Yes";
      UtilClass uc = new UtilClass(SBM_HOME);
      String responseCode = null;
      String responseMessage = null;
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         responseObj.setResponseCode(responseCode);
         responseObj.setResponseMessage(responseMessage);
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String EFTReissue = (String)hashMap.get("EFTREISSUE");
         String isMigrated = (String)hashMap.get("ISMIGRATED");
         String[] piNamesList = (String[])hashMap.get("PINAMESLIST");

         Exception e;
         String userid;
         String pass;
         try {
            e = null;
            userid = null;
            pass = null;
            DBUtility db = new DBUtility(SBM_HOME);
            BLServer blserver = BLClientUtil.getBizLogicServer();
            Session blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            Session blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
            ProcessInstance srPI = blserver.getProcessInstance(blsession, srPIName);
            String SRType = (String)srPI.getDataSlotValue("SRType");
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: SRType is " + SRType + " ...INPUT VALUE  piNamesList[] is :: " + Arrays.toString(piNamesList));
            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosure") || SRType.equals("CLClosure") || SRType.equals("ALTermination") || SRType.equals("CLTermination") || SRType.equals("ChequeCancellationAndStopPayment"))) {
               responseObj = uc.completeProcessInstance(reqObj);
               automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response of  completeProcessInstance method is " + responseObj.getResponseMessage());
            }

            String SRReopen;
            String piName;
            boolean isCLPaidWithSettlement;
            String currWorkItemName;
            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosureReopen") || SRType.equals("ALRejectionReopen")) && piNamesList != null) {
               automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside AL Reopen & SRType is : " + SRType + " ::piNamesList[0] is " + piNamesList[0]);
               SRReopen = "Executive";
               result = db.getDataSlotValuesByInstanceId(piNamesList, SRType);
               automatictasklog.info("ECS dataslot values for the piNamesList " + piNamesList[0] + " is " + Arrays.toString(result));
               responseObj.setResponseCode("5000");
               responseObj.setResponseMessage("SUCCESS");
               piName = result[5];
               isCLPaidWithSettlement = false;
               if (SRType != null && !SRType.equals("") && SRType.equals("ALRejectionReopen")) {
                  isCLPaidWithSettlement = true;
                  automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside ALRejectionReopen and  can open a new AL Flow");
               }

               if (SRType != null && !SRType.equals("") && SRType.equals("ALClosureReopen") && (piName != null && piName.equals("1") || isMigrated != null && isMigrated.equals("Yes"))) {
                  isCLPaidWithSettlement = true;
                  automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside ALClosureReopen and  can open a new AL Flow");
               }

               if (isCLPaidWithSettlement) {
                  RequestObject[] objInput = new RequestObject[7];
                  currWorkItemName = piNamesList[0].split("#")[0];
                  if (result != null && result.length != 0) {
                     objInput[0] = new RequestObject();
                     objInput[0].setKey("PROCESSNAME");
                     objInput[0].setValue(currWorkItemName);
                     objInput[1] = new RequestObject();
                     objInput[1].setKey("USERID");
                     objInput[1].setValue(userId);
                     objInput[2] = new RequestObject();
                     objInput[2].setKey("ALCASETYPE");
                     objInput[2].setValue(result[0]);
                     if (result[0] == null) {
                        objInput[2].setValue("DF");
                     }

                     objInput[3] = new RequestObject();
                     objInput[3].setKey("SRREOPEN");
                     objInput[3].setValue(SRReopen);
                     objInput[4] = new RequestObject();
                     objInput[4].setKey("ISFROMSR");
                     objInput[4].setValue("Yes");
                     objInput[5] = new RequestObject();
                     objInput[5].setKey("ALINWARDNO");
                     objInput[5].setValue(result[2]);
                     objInput[6] = new RequestObject();
                     objInput[6].setKey("ALNO");
                     objInput[6].setValue(result[3]);
                     responseObj = uc.CreateWorkFlow(objInput);
                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside AL Reopen & response message is : " + responseObj.getResponseMessage() + " created work flow instance is " + responseObj.getResultworkItemArray()[0].getName());
                  } else {
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: unable to get the data slot values for the piNamesList :" + piNamesList[0]);
                  }
               }
            }

            if (SRType != null && !SRType.equals("") && (SRType.equals("CLClosureReopen") || SRType.equals("CLRejectionReopen")) && piNamesList != null) {
               SRReopen = "Editor";
               automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside CL Reopen & SRType is : " + SRType + " piNamesList[0] is " + piNamesList[0]);
               responseObj = this.createCLFlowFormSR(piNamesList, userId, db, SRType, SRReopen, EFTReissue);
            }

            int var34;
            WorkItemObject[] taskList;
            boolean isCLPaidWithSettlement;
            int var56;
            int var64;
            if (SRType != null && !SRType.equals("") && (SRType.equals("EFTRejectionReopen") || SRType.equals("EFTStopPaymentAndReissue")) && piNamesList != null) {
               SRReopen = "Editor";

               try {
                  hm.put("EFTReissue", EFTReissue);
                  if (SRType.equals("EFTRejectionReopen")) {
                     SRReopen = "Editor";
                     hm.put("SRType", "EFTRejectionReopen");
                     hm.put("SRReopen", SRReopen);
                  } else {
                     SRReopen = "PaymentRequestor";
                     if (piNamesList[0].contains("RGICL_ECS_TPA_CL_FLOW")) {
                        nextWorkItemCaseStatus = "Reopen";
                        SRReopen = "QualityChecker";
                     }

                     hm.put("SRType", "EFTStopPaymentAndReissue");
                     hm.put("SRReopen", SRReopen);
                  }

                  if (EFTReissue != null && !EFTReissue.equals("") && EFTReissue.equals("Yes")) {
                     SRReopen = "EFTChecker";
                  }

                  resultTaskList = db.getNextTaskByPIName(piNamesList);
                  automatictasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
                  isCLPaidWithSettlement = false;
                  isCLPaidWithSettlement = false;
                  if (resultTaskList != null && resultTaskList.size() != 0) {
                     workItemObject = uc.getResultWorkitems(resultTaskList);
                     automatictasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                     taskList = workItemObject;
                     var34 = workItemObject.length;

                     for(var56 = 0; var56 < var34; ++var56) {
                        WorkItemObject wObj = taskList[var56];
                        if (wObj.getName().contains("Accounts")) {
                           isCLPaidWithSettlement = true;
                        }

                        if (wObj.getName().contains("SRQueue")) {
                           isCLPaidWithSettlement = true;
                        }
                     }

                     int var36;
                     WorkItemObject[] var37;
                     WorkItem wi;
                     ProcessInstance clPI;
                     WorkItemObject wObj;
                     if (isCLPaidWithSettlement) {
                        clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                        automatictasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                        clPI.updateSlotValue(hm);
                        clPI.save();
                        automatictasklog.info("ECS Savvion: " + methodName + " SRType  is " + SRType + " : SRReopen at " + SRReopen);
                        currWorkItemName = "";
                        var37 = workItemObject;
                        var36 = workItemObject.length;

                        for(var64 = 0; var64 < var36; ++var64) {
                           wObj = var37[var64];
                           automatictasklog.info("ECS Savvion: " + methodName + " available workitem name  is " + wObj.getName());
                           if (wObj.getName().contains("Accounts")) {
                              currWorkItemName = wObj.getName();
                              automatictasklog.info("ECS workItem Name is " + currWorkItemName);
                              wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 automatictasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 automatictasklog.warn("ECS Task Assigned to user");
                              }

                              automatictasklog.warn("ECS Savvion :: " + methodName + SRType + " workitem:: " + currWorkItemName + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), currWorkItemName);
                              automatictasklog.warn("ECS Task Completed");
                              responseObj.setResponseCode("5000");
                              responseObj.setResponseMessage("SUCCESS");
                              workItemObj = new WorkItemObject();
                              workItemObj.setName(wObj.getName());
                              workItemObj.setPiName(wObj.getPiName());
                              workItemObj.setCaseStatus("Completed");
                              workItemList.add(workItemObj);
                           }
                        }

                        var37 = workItemObject;
                        var36 = workItemObject.length;

                        for(var64 = 0; var64 < var36; ++var64) {
                           wObj = var37[var64];
                           if (wObj.getName().contains("SRQueue")) {
                              currWorkItemName = wObj.getName();
                              automatictasklog.info("ECS workItem Name is " + currWorkItemName);
                              wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 automatictasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 automatictasklog.warn("ECS Task Assigned to user");
                              }

                              automatictasklog.warn("ECS Savvion :: " + methodName + " EFTRejectionReopen workitem:: " + currWorkItemName + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), currWorkItemName);
                              automatictasklog.warn("ECS Task Completed");
                           }
                        }

                        completedWorkItem = new WorkItemObject[workItemList.size()];
                        completedWorkItem = uc.getResultWorkitems(workItemList);
                        wiObjects = uc.getNextAvailableTaskList(workItemObject[0].getPiName(), nextWorkItemCaseStatus, currWorkItemName, (String)null, (String)null, isFromSR, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                        if (wiObjects != null && wiObjects.length != 0) {
                           uc.checkUserNumbersInGroup(wiObjects);
                        }

                        if (wiObjects != null && wiObjects.length != 0) {
                           responseObj.setResponseCode("5000");
                           responseObj.setResponseMessage("SUCCESS");
                        }

                        responseObj.setResultworkItemArray(wiObjects);
                        responseObj.setCompletedWorkItemArray(completedWorkItem);
                     } else if (!isCLPaidWithSettlement) {
                        responseObj.setResponseCode("5047");
                        responseObj.setResponseMessage("INVALID_REQUEST");
                        automatictasklog.info("ECS Savvion :: " + methodName + " :: can't process since Claim is not in paid/paid with settlement letter status");
                     } else {
                        clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                        automatictasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                        clPI.updateSlotValue(hm);
                        clPI.save();
                        automatictasklog.info("ECS Savvion: " + methodName + " SRType  is " + SRType + " : SRReopen is " + SRReopen);
                        currWorkItemName = "";
                        var37 = workItemObject;
                        var36 = workItemObject.length;

                        for(var64 = 0; var64 < var36; ++var64) {
                           wObj = var37[var64];
                           automatictasklog.info("ECS Savvion: " + methodName + " workitem name  is " + wObj.getName());
                           if (wObj.getName().contains("SRQueue")) {
                              currWorkItemName = wObj.getName();
                              automatictasklog.info("ECS workItem Name is " + wObj.getName());
                              wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 automatictasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 automatictasklog.warn("ECS Task Assigned to user");
                              }

                              automatictasklog.warn("ECS Savvion :: " + methodName + "  workitem:: " + wObj.getName() + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                              automatictasklog.warn("ECS Task Completed");
                              responseObj.setResponseCode("5000");
                              responseObj.setResponseMessage("SUCCESS");
                              workItemObj = new WorkItemObject();
                              workItemObj.setName(wObj.getName());
                              workItemObj.setPiName(wObj.getPiName());
                              workItemObj.setCaseStatus("Completed");
                              wiObjects = uc.getNextAvailableTaskList(workItemObject[0].getPiName(), nextWorkItemCaseStatus, currWorkItemName, (String)null, (String)null, isFromSR, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                              if (wiObjects != null && wiObjects.length != 0) {
                                 uc.checkUserNumbersInGroup(wiObjects);
                              }
                           }
                        }

                        workItemList.add(workItemObj);
                        completedWorkItem = new WorkItemObject[workItemList.size()];
                        completedWorkItem = uc.getResultWorkitems(workItemList);
                        responseObj.setResultworkItemArray(wiObjects);
                        responseObj.setCompletedWorkItemArray(completedWorkItem);
                     }
                  } else {
                     automatictasklog.info("ECS Savvion :: " + methodName + " :: Creating new CL Flow since the flow with piNamesList " + piNamesList[0] + " is not active");
                     responseObj = this.createCLFlowFormSR(piNamesList, userId, db, SRType, SRReopen, EFTReissue);
                  }
               } catch (Exception var46) {
                  responseObj.setResponseCode("5050");
                  responseObj.setResponseMessage("FAILURE_EXCEPTION");
                  automatictasklog.error("ECS Savvion :: " + methodName + " EFT Exception occurs :: " + var46);
               }
            }

            int var57;
            if (SRType != null && !SRType.equals("") && (SRType.equals("ChequeStopPaymentAndReissue") || SRType.equals("ChequeCancellationAndReissue")) && piNamesList != null) {
               hm.put("EFTReissue", EFTReissue);
               SRReopen = "Editor";
               isCLPaidWithSettlement = false;

               try {
                  if (SRType.equals("ChequeCancellationAndReissue")) {
                     hm.put("SRType", "ChequeCancellationAndReissue");
                     hm.put("SRReopen", "Editor");
                  } else if (SRType.equals("ChequeStopPaymentAndReissue")) {
                     hm.put("SRType", "ChequeStopPaymentAndReissue");
                     hm.put("SRReopen", "PaymentRequestor");
                     SRReopen = "PaymentRequestor";
                  }

                  if (EFTReissue != null && !EFTReissue.equals("") && EFTReissue.equals("Yes")) {
                     SRReopen = "EFTChecker";
                  }

                  resultTaskList = db.getNextTaskByPIName(piNamesList);
                  automatictasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
                  if (resultTaskList != null && resultTaskList.size() != 0) {
                     workItemObject = uc.getResultWorkitems(resultTaskList);
                     automatictasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                     WorkItemObject[] var63 = workItemObject;
                     var56 = workItemObject.length;

                     for(var57 = 0; var57 < var56; ++var57) {
                        WorkItemObject wObj = var63[var57];
                        automatictasklog.info("ECS Savvion: " + methodName + " Available workItemName  is " + wObj.getName());
                        if (wObj.getName().contains("SRQueue")) {
                           isCLPaidWithSettlement = true;
                        }
                     }

                     automatictasklog.info("ECS Savvion: " + methodName + " isCLPaidWithSettlement " + isCLPaidWithSettlement);
                     if (!isCLPaidWithSettlement) {
                        responseObj.setResponseCode("5047");
                        responseObj.setResponseMessage("INVALID_REQUEST");
                        automatictasklog.info("ECS Savvion :: " + methodName + " can't process since Claim is not in paid with settlement letter status");
                     } else {
                        ProcessInstance clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                        automatictasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                        clPI.updateSlotValue(hm);
                        clPI.save();
                        automatictasklog.info("ECS Savvion: " + methodName + " SRType  is " + SRType + " : SRReopen is " + SRReopen);
                        String currWorkItemName = "";
                        WorkItemObject[] var68 = workItemObject;
                        var64 = workItemObject.length;

                        for(var34 = 0; var34 < var64; ++var34) {
                           WorkItemObject wObj = var68[var34];
                           automatictasklog.info("ECS Savvion: " + methodName + " workitem name  is " + wObj.getName());
                           if (wObj.getName().contains("SRQueue")) {
                              currWorkItemName = wObj.getName();
                              automatictasklog.info("ECS workItem Name is " + wObj.getName());
                              WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 automatictasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 automatictasklog.warn("ECS Task Assigned to user");
                              }

                              automatictasklog.warn("ECS Savvion :: " + methodName + "  workitem:: " + wObj.getName() + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                              automatictasklog.warn("ECS Task Completed");
                              responseObj.setResponseCode("5000");
                              responseObj.setResponseMessage("SUCCESS");
                              workItemObj = new WorkItemObject();
                              workItemObj.setName(wObj.getName());
                              workItemObj.setPiName(wObj.getPiName());
                              workItemObj.setCaseStatus("Completed");
                              wiObjects = uc.getNextAvailableTaskList(workItemObject[0].getPiName(), nextWorkItemCaseStatus, currWorkItemName, (String)null, (String)null, isFromSR, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                              if (wiObjects != null && wiObjects.length != 0) {
                                 uc.checkUserNumbersInGroup(wiObjects);
                              }
                           }
                        }

                        workItemList.add(workItemObj);
                        completedWorkItem = new WorkItemObject[workItemList.size()];
                        completedWorkItem = uc.getResultWorkitems(workItemList);
                        responseObj.setResultworkItemArray(wiObjects);
                        responseObj.setCompletedWorkItemArray(completedWorkItem);
                     }
                  } else {
                     automatictasklog.info("ECS Savvion :: " + methodName + " :: Creating new CL Flow since the flow with piNamesList " + piNamesList[0] + " is not active");
                     responseObj = this.createCLFlowFormSR(piNamesList, userId, db, SRType, SRReopen, EFTReissue);
                  }
               } catch (Exception var45) {
                  responseObj.setResponseCode("5050");
                  responseObj.setResponseMessage("FAILURE_EXCEPTION");
                  automatictasklog.error("ECS Savvion :: " + methodName + SRType + " Exception occurs :: " + var45);
               }
            }

            if (SRType != null && SRType != "" && SRType.equals("Recovery") && piNamesList != null) {
               automatictasklog.info("ECS Savvion " + methodName + " Service :: SRType is " + SRType + " :: clInwardNo is " + Arrays.toString(piNamesList));
               resultTaskList = db.getNextTaskByPIName(piNamesList);
               automatictasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  String[] piNamesListArray = new String[1];
                  String[] var66 = piNamesList;
                  var57 = piNamesList.length;

                  for(int var59 = 0; var59 < var57; ++var59) {
                     piName = var66[var59];
                     piNamesListArray[0] = piName;
                     boolean isCLPaidWithSettlement = false;

                     try {
                        taskList = null;
                        ArrayList taskList = db.getNextTaskByPIName(piNamesListArray);
                        automatictasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + taskList.size() + " for piName " + piName);
                        if (taskList != null && taskList.size() != 0) {
                           workItemObject = uc.getResultWorkitems(taskList);
                           automatictasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                           WorkItemObject[] var39 = workItemObject;
                           int var74 = workItemObject.length;

                           for(int var73 = 0; var73 < var74; ++var73) {
                              WorkItemObject wObj = var39[var73];
                              if (wObj.getName().contains("SRQueue")) {
                                 isCLPaidWithSettlement = true;
                              }
                           }

                           if (isCLPaidWithSettlement) {
                              ProcessInstance clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                              automatictasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                              hm.put("SRType", "Recovery");
                              clPI.updateSlotValue(hm);
                              clPI.save();
                              String currWorkItemName = "";
                              WorkItemObject[] var41 = workItemObject;
                              int var40 = workItemObject.length;

                              for(int var76 = 0; var76 < var40; ++var76) {
                                 WorkItemObject wObj = var41[var76];
                                 automatictasklog.info("ECS Savvion: " + methodName + " workitem name  is " + wObj.getName());
                                 if (wObj.getName().contains("SRQueue") || wObj.getName().contains("CLInvestigator")) {
                                    currWorkItemName = wObj.getName();
                                    automatictasklog.info("ECS workItem Name is " + wObj.getName());
                                    WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                       automatictasklog.warn("ECS AssignTask enterred");
                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                       automatictasklog.warn("ECS Task Assigned to user");
                                    }

                                    automatictasklog.warn("ECS Savvion :: " + methodName + "  workitem:: " + wObj.getName() + " CompleteTask enterred");
                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                    automatictasklog.warn("ECS Task Completed");
                                    responseObj.setResponseCode("5000");
                                    responseObj.setResponseMessage("SUCCESS");
                                    workItemObj = new WorkItemObject();
                                    workItemObj.setName(wObj.getName());
                                    workItemObj.setPiName(wObj.getPiName());
                                    workItemObj.setCaseStatus("Reject");
                                    workItemList.add(workItemObj);
                                 }
                              }

                              wiObjects = uc.getNextAvailableTaskList(workItemObject[0].getPiName(), nextWorkItemCaseStatus, currWorkItemName, (String)null, (String)null, isFromSR, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                              if (wiObjects != null && wiObjects.length != 0) {
                                 uc.checkUserNumbersInGroup(wiObjects);
                              }

                              completedWorkItem = new WorkItemObject[workItemList.size()];
                              completedWorkItem = uc.getResultWorkitems(workItemList);
                              responseObj.setResultworkItemArray(wiObjects);
                              responseObj.setCompletedWorkItemArray(completedWorkItem);
                           } else {
                              automatictasklog.info("ECS Savvion :: " + methodName + " can't process the request since Claim with piName " + piName + " is not in the status of paid with settlement letter");
                           }
                        }
                     } catch (Exception var44) {
                        automatictasklog.error("ECS Savvion: " + methodName + " Service:: FullRecovery :: Exception:::" + var44);
                     }
                  }

                  if (responseObj.getCompletedWorkItemArray() != null && responseObj.getCompletedWorkItemArray().length != 0) {
                     responseObj.setResponseCode("5000");
                     responseObj.setResponseMessage("SUCCESS");
                  } else {
                     responseObj.setResponseCode("5047");
                     responseObj.setResponseMessage("INVALID_REQUEST");
                     automatictasklog.info("ECS Savvion :: " + methodName + " can't process the request since there are no Claim to process");
                  }
               } else {
                  automatictasklog.info("ECS Savvion :: " + methodName + " there are no Claim to process. So setting the response as SUCCESS");
                  responseObj.setResponseCode("5000");
                  responseObj.setResponseMessage("SUCCESS");
               }
            }

            if (blsession != null && blserver.isSessionValid(blsession)) {
               blserver.disConnect(blsession);
            }

            if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
               blserver.disConnect(blsessionECSAdmin);
            }

            e = null;
         } catch (Exception var47) {
            e = var47;
            responseObj.setResponseCode("5050");
            responseObj.setResponseMessage("FAILURE_EXCEPTION");
            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var47);
            if (var47.getMessage().contains("Incorrect userName/password for")) {
               try {
                  userid = (String)hashMap.get("USERID");
                  if (e.getMessage().contains("ECSAdmin")) {
                     userid = "ECSAdmin";
                  }

                  pass = uc.getUserPasswordECS(userid);
                  PService p = PService.self();
                  pass = p.encrypt(pass);
                  automatictasklog.info("ECS Savvion :: " + methodName + " userId " + userid + " encrypted " + pass);
               } catch (Exception var43) {
                  automatictasklog.error("ECS Savvion : " + methodName + " :: Exception " + var43);
               }
            }
         }
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END with response " + responseObj);
      automatictasklog.info("**************************ECS Savvion: " + methodName + " Service::::END ***************************");
      return responseObj;
   }

   private ResponseObject createCLFlowFormSR(String[] piNamesList, String userId, DBUtility db, String SRType, String SRReopen, String EFTReissue) {
      ResponseObject responseObj = null;
      String[] result = new String[3];
      UtilClass uc = new UtilClass(SBM_HOME);
      String methodName = "buildRequestToCreateCLFlow";
      String ptName = null;
      ptName = piNamesList[0].split("#")[0];
      result = db.getDataSlotValuesByInstanceId(piNamesList, SRType);
      automatictasklog.info("ECS buildRequestToCreateCLFlow inputs " + ptName);
      automatictasklog.info("ECS buildRequestToCreateCLFlow dataslot values for the clInwardNo " + piNamesList[0] + " is " + Arrays.toString(result));
      RequestObject[] objInput = new RequestObject[12];
      if (result != null && result.length != 0) {
         objInput[0] = new RequestObject();
         objInput[0].setKey("PROCESSNAME");
         objInput[0].setValue(ptName);
         objInput[1] = new RequestObject();
         objInput[1].setKey("USERID");
         objInput[1].setValue(userId);
         objInput[2] = new RequestObject();
         objInput[2].setKey("CLNO");
         objInput[2].setValue(result[4]);
         objInput[3] = new RequestObject();
         objInput[3].setKey("ISFROMSR");
         objInput[3].setValue("Yes");
         if (ptName.equals("RGICL_ECS_TPA_CL_FLOW")) {
            objInput[4] = new RequestObject();
            objInput[4].setKey("ISPAYMENTTPA");
            objInput[4].setValue(result[0]);
         } else {
            objInput[4] = new RequestObject();
            objInput[4].setKey("CLCASETYPE");
            objInput[4].setValue(result[0]);
            if (result[0] == null) {
               objInput[4].setValue("DF");
            }

            objInput[5] = new RequestObject();
            objInput[5].setKey("AUTORELEASE");
            objInput[5].setValue(result[1]);
            if (result[1] == null) {
               objInput[5].setValue("No");
            }

            objInput[6] = new RequestObject();
            objInput[6].setKey("SRREOPEN");
            objInput[6].setValue(SRReopen);
            objInput[7] = new RequestObject();
            objInput[7].setKey("SRTYPE");
            objInput[7].setValue(SRType);
            objInput[8] = new RequestObject();
            objInput[8].setKey("CLINWARDNO");
            objInput[8].setValue(result[3]);
            objInput[9] = new RequestObject();
            objInput[9].setKey("EFTREISSUE");
            objInput[9].setValue(EFTReissue);
            objInput[10] = new RequestObject();
            objInput[10].setKey("CLAIMSOURCE");
            objInput[10].setValue(result[5]);
            objInput[11] = new RequestObject();
            objInput[11].setKey("CLAIMTYPE");
            objInput[11].setValue(result[6]);
         }

         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Request Object for CreateWorkFlow method " + Arrays.toString(objInput));
         responseObj = uc.CreateWorkFlow(objInput);
         automatictasklog.info("ECS Savvion: " + methodName + " Service::::Inside CL Reopen & response message is : " + responseObj.getResponseMessage() + " created work flow instance is " + responseObj.getResultworkItemArray()[0].getName());
      } else {
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: unable to get the data slot values for the piNamesList :" + piNamesList[0]);
      }

      return responseObj;
   }

   private ResponseObject completeSRTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
      String methodName = "completeSRTask";
      automatictasklog.info("**********ECS Savvion: " + methodName + " Service starts **********");
      HashMap hm = new HashMap();
      new HashMap();
      ResponseObject responseObj = new ResponseObject();
      String workItemCaseStatus = "Approve";
      boolean isInstanceCompleted = false;
      UtilClass uc = new UtilClass(SBM_HOME);
      String nextWorkItemCaseStatus = "New";
      WorkItemObject[] wiObjects = null;
      if (reqObj == null) {
         responseObj.setResponseCode("5070");
         responseObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String piName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");

         Exception e;
         String userid;
         String pass;
         try {
            e = null;
            userid = null;
            pass = null;
            BLServer blserver = BLClientUtil.getBizLogicServer();
            Session blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            Session blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
            ProcessInstance pi = blserver.getProcessInstance(blsession, piName);
            WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: updating dataslot values ");
            if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
               workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
            }

            uc.checkNull(hashMap.get("ISINPAIDSTATUS"));
            if (workItemName != null && workItemName.contains("SRChecker")) {
               hm.put("HasSRCheckerAccepted", workItemCaseStatus);
            }

            if (workItemName != null && workItemName.contains("PaymentChecker")) {
               hm.put("HasPaymentCheckerAccepted", workItemCaseStatus);
            }

            if (workItemName != null && workItemName.contains("AccountsNegative")) {
               hm.put("HasCLAccountsAccepted", workItemCaseStatus);
               nextWorkItemCaseStatus = "SAPReject";
            }

            if (blsession != null) {
               pi.updateSlotValue(hm);
               pi.save();
               if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                  automatictasklog.warn("ECS AssignTask enterred");
                  uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  automatictasklog.warn("ECS Task Assigned to user");
               }

               automatictasklog.warn("ECS Complete SR Flow Task enterred");
               uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
               automatictasklog.warn("SR Flow ECS Task Completed");
               responseObj.setResponseCode("5000");
               responseObj.setResponseMessage("SUCCESS");
               wiObjects = uc.getNextAvailableTaskList(piName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
               automatictasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
               responseObj.setResultworkItemArray(wiObjects);
            }

            try {
               pi = blserver.getProcessInstance(blsession, piName);
               automatictasklog.warn("ECS processInstance " + pi);
            } catch (Exception var21) {
               isInstanceCompleted = true;
               automatictasklog.warn("ECS processInstance " + piName + " got completed is " + isInstanceCompleted);
            }

            if (blsession != null && blserver.isSessionValid(blsession)) {
               blserver.disConnect(blsession);
            }

            if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
               blserver.disConnect(blsessionECSAdmin);
            }

            e = null;
         } catch (Exception var22) {
            e = var22;
            responseObj.setResponseCode("5050");
            responseObj.setResponseMessage("FAILURE_EXCEPTION");
            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var22.getMessage());
            if (var22.getMessage().contains("Incorrect userName/password for")) {
               try {
                  userid = (String)hashMap.get("USERID");
                  if (e.getMessage().contains("ECSAdmin")) {
                     userid = "ECSAdmin";
                  }

                  pass = uc.getUserPasswordECS(userid);
                  PService p = PService.self();
                  pass = p.encrypt(pass);
                  automatictasklog.info("ECS Savvion :: " + methodName + " userId " + userid + " encrypted " + pass);
               } catch (Exception var20) {
                  automatictasklog.error("ECS Savvion : " + methodName + " :: Exception " + var20);
               }
            }
         }

         responseObj.setWorkItemCaseStatus(workItemCaseStatus);
         responseObj.setInstanceCompleted(isInstanceCompleted);
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response " + responseObj);
      automatictasklog.info("**************************ECS Savvion: " + methodName + " Service::::END ***************************");
      return responseObj;
   }

   private ResponseObject buildRequestToCreateSRFlow(String userId, String SRNo, String SRType) {
      String methodName = "buildRequestToCreateSRFlow";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:: Input values are :: UserId : " + userId + " SRNo : " + SRNo + " SRType : " + SRType);
      new ResponseObject();
      RequestObject[] objInput = new RequestObject[5];
      UtilClass uc = new UtilClass(SBM_HOME);
      objInput[0] = new RequestObject();
      objInput[0].setKey("PROCESSNAME");
      objInput[0].setValue("RGICL_ECS_SR_FLOW");
      objInput[1] = new RequestObject();
      objInput[1].setKey("USERID");
      objInput[1].setValue(userId);
      objInput[2] = new RequestObject();
      objInput[2].setKey("SRNO");
      objInput[2].setValue(SRNo);
      objInput[3] = new RequestObject();
      objInput[3].setKey("ISFROMSR");
      objInput[3].setValue("Yes");
      objInput[4] = new RequestObject();
      objInput[4].setKey("SRTYPE");
      objInput[4].setValue(SRType);
      ResponseObject resObj = uc.CreateWorkFlow(objInput);
      if (resObj.getResponseCode() == "5000") {
         automatictasklog.info("ECS Savvion: " + methodName + " Service::::Response message is : " + resObj.getResponseMessage() + " created work flow instance is " + resObj.getResultworkItemArray()[0].getName());
      }

      return resObj;
   }
}
