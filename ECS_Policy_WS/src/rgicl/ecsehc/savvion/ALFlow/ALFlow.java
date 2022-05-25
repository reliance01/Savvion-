package rgicl.ecsehc.savvion.ALFlow;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.HcsLogObject;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;
import rgicl.ecsehc.savvion.policy.util.db.DBUtility;

public class ALFlow {
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

   public ALFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteALFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteALFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
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
      ResponseObject associatedresObj = null;
      boolean isQueriedAL = false;
      DBUtility db = new DBUtility(SBM_HOME);
      UtilClass uc = new UtilClass(SBM_HOME);
      String[] resultArray = new String[2];
      String associatedPIName = "";
      String associatedWIName = "";
      String isALApproverAccepted = "";
      String hasOpenTask = "No";
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
         String parentALInwardNo = (String)hashMap.get("PARENTALINWARDNO");
         String parentCLInwardNo = (String)hashMap.get("PARENTCLINWARDNO");
         String isQueried = (String)hashMap.get("ISQUERIED");
         String transactionId = (String)hashMap.get("TRANSACTIONID");
         String transactionType = (String)hashMap.get("TRANSACTIONTYPE");
         if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
            workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
         }

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               Session blsessionECSAdmin = null;

               String winame;
               try {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Checking if already completed case " + workItemName);
                  long _pid = 0L;
                  String wsname = null;
                  if (workItemName != null && workItemName.contains("#") && workItemName.contains(":")) {
                     String[] s1 = workItemName.split("#");
                     if (s1 != null && s1.length > 0) {
                        String[] s2 = s1[1].split("::");
                        _pid = Long.parseLong(s2[0]);
                        wsname = s2[1];
                     }
                  }

                  if (wsname != null && _pid > 0L) {
                     winame = db.getWorkitemStatus(wsname, _pid);
                     automatictasklog.info("ECS Savvion: workitem is " + workItemName + " Status is " + winame);
                     if (winame != null && winame.equalsIgnoreCase("completed")) {
                        HcsLogObject obj = new HcsLogObject();
                        obj.setPROCESSINSTANCE_NAME(processInstanceName);
                        obj.setUSER_ID(userId);
                        obj.setWORKITEM_NAME(workItemName);
                        obj.setAPPROVER_DECISION(workItemCaseStatus);
                        resObj = db.getHcsLogObject(obj);
                        automatictasklog.info("ECS Savvion: Response object for already completed is " + resObj);
                        ResponseObject var43 = resObj;
                        return var43;
                     }
                  }
               } catch (Exception var65) {
                  automatictasklog.error("Error in getting Already completed task details ", var65);
               }

               if (workItemName != null && workItemCaseStatus != null && workItemName.contains("InwardDEO") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && uc.checkNull(hashMap.get("ALINWARDNO"))) {
                  automatictasklog.info("ECS Savvion " + methodName + " Service:::: ALINWARDNO is null or empty");
                  responseCode = "5021";
                  responseMessage = "INPUT_VALUE_IS_NULL";
               }

               if (workItemName != null && workItemCaseStatus != null && workItemName.contains("Executive") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && !workItemCaseStatus.equals("SendBack") && uc.checkNull(hashMap.get("ALNO"))) {
                  automatictasklog.info("ECS Savvion " + methodName + " Service:::: ALNO is null or empty");
                  responseCode = "5021";
                  responseMessage = "INPUT_VALUE_IS_NULL";
               }

               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
                  if (userId != null && !userId.equals("")) {
                     if (responseCode == null) {
                        automatictasklog.info(" ECS Savvion " + methodName + " Service:::: begins to complete the task");
                        if (isQueried != null && isQueried.equals("Yes") && parentALInwardNo != null && !parentALInwardNo.equals("")) {
                           automatictasklog.info(" ECS Savvion " + methodName + " Service:::: checking for Query Response task");
                           associatedresObj = new ResponseObject();
                           resultArray = db.getQueryQueueTaskName(parentALInwardNo, parentCLInwardNo, processInstanceName);

                           try {
                              associatedPIName = resultArray[0];
                              associatedWIName = resultArray[1];
                              automatictasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
                              workItemName = workItemName + associatedWIName;
                              associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
                              workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
                              processInstanceName = processInstanceName + associatedPIName;
                              associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
                              processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
                              associatedresObj = uc.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
                              automatictasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
                           } catch (Exception var64) {
                              associatedresObj.setResponseCode("5047");
                              associatedresObj.setResponseMessage("INVALID_REQUEST");
                              automatictasklog.error("ECS Savvion " + methodName + " Service:::: Catch Exception while getting Query Queue task" + var64.getMessage());
                           }
                        }

                        if (associatedresObj != null && associatedresObj.getResponseCode() != "5000") {
                           automatictasklog.info("ECS Savvion: " + methodName + " Service::: issue with associated (Inward) work item");
                           responseCode = associatedresObj.getResponseCode();
                           responseMessage = associatedresObj.getResponseMessage();
                        } else {
                           if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query") || workItemCaseStatus.equals("Query")) {
                              if (workItemName.contains("Query")) {
                                 workItemCaseStatus = "Closed";
                              }

                              automatictasklog.info("ECS Savvion " + methodName + " setting isQueriedAL flag as true");
                              hm.put("isQueriedAL", true);
                           }

                           blserver = BLClientUtil.getBizLogicServer();
                           blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                           ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                           hm.put("HasOpenTask", hasOpenTask);
                           if (!uc.checkNull(hashMap.get("ALCASETYPE"))) {
                              hm.put("ALCaseType", hashMap.get("ALCASETYPE").toString());
                           }

                           if (!uc.checkNull(hashMap.get("EMPLOYEEID"))) {
                              hm.put("EmployeeId", hashMap.get("EMPLOYEEID").toString());
                           }

                           if (!uc.checkNull(hashMap.get("HOSPITALNAME"))) {
                              hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
                           }

                           if (workItemName != null && workItemName.contains("InwardDEO")) {
                              automatictasklog.info("workItemCaseStatus when ALInwardDEO submits task " + workItemCaseStatus);
                              hm.put("isALInwardDEOAccepted", workItemCaseStatus);
                              if (!uc.checkNull(hashMap.get("ALINWARDNO"))) {
                                 hm.put("ALInwardNo", hashMap.get("ALINWARDNO").toString());
                              }
                           }

                           if (workItemName != null && workItemName.contains("Executive")) {
                              hm.put("isALExecutiveAccepted", workItemCaseStatus);
                              if (!uc.checkNull(hashMap.get("ALNO"))) {
                                 hm.put("ALNo", hashMap.get("ALNO").toString());
                              }
                           }

                           if (workItemName != null && workItemName.contains("ALApprover")) {
                              hm.put("isALApproverAccepted", workItemCaseStatus);
                              if (workItemCaseStatus.equals("Approve")) {
                                 automatictasklog.info("ECS Savvion " + methodName + " resetting isQueriedAL flag as false");
                                 hm.put("isQueriedAL", false);
                                 isALApproverAccepted = workItemCaseStatus;
                              }

                              if (workItemCaseStatus.equals("Reject")) {
                                 isALApproverAccepted = workItemCaseStatus;
                              }
                           }

                           if (workItemName != null && workItemName.contains("TagChecker")) {
                              hm.put("HasALTagCheckerAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("AmountApprover")) {
                              hm.put("HasAmountApproverAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("VerticalHead")) {
                              hm.put("HasVHAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("NetworkDeviator")) {
                              hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("ServiceDeviator")) {
                              hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("PolicyDeviator")) {
                              hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
                           }

                           if (workItemName != null && workItemName.contains("OtherDeviator")) {
                              hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
                           }

                           if (!uc.checkNull(hashMap.get("NEEDSAMOUNTAPPROVAL"))) {
                              hm.put("NeedsAmountApproval", hashMap.get("NEEDSAMOUNTAPPROVAL").toString());
                           }

                           if (!uc.checkNull(hashMap.get("NEEDSVERTICALHEAD"))) {
                              hm.put("NeedsVerticalHead", hashMap.get("NEEDSVERTICALHEAD").toString());
                           }

                           if (!uc.checkNull(hashMap.get("NEEDSVHLOOP"))) {
                              hm.put("NeedsVHLoop", hashMap.get("NEEDSVHLOOP").toString());
                           }

                           if (!uc.checkNull(hashMap.get("ISALREJECTED"))) {
                              hm.put("IsALRejected", hashMap.get("ISALREJECTED").toString());
                           }

                           if (!uc.checkNull(hashMap.get("HASNETWORKDEVIATION"))) {
                              hm.put("HasNetworkDeviation", hashMap.get("HASNETWORKDEVIATION").toString());
                           }

                           if (!uc.checkNull(hashMap.get("HASSERVICEDEVIATION"))) {
                              hm.put("HasServiceDeviation", hashMap.get("HASSERVICEDEVIATION").toString());
                           }

                           if (!uc.checkNull(hashMap.get("HASPOLICYDEVIATION"))) {
                              hm.put("HasPolicyDeviation", hashMap.get("HASPOLICYDEVIATION").toString());
                           }

                           if (!uc.checkNull(hashMap.get("HASOTHERDEVIATION"))) {
                              hm.put("HasOtherDeviation", hashMap.get("HASOTHERDEVIATION").toString());
                           }

                           if (!uc.checkNull(hashMap.get("NEEDSINVESTIGATION"))) {
                              hm.put("NeedsInvestigation", hashMap.get("NEEDSINVESTIGATION").toString());
                              if (hashMap.get("NEEDSINVESTIGATION").toString().equals("Yes")) {
                                 hasOpenTask = "Yes";
                                 hm.put("HasOpenTask", hasOpenTask);
                                 automatictasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSINVESTIGATION is Yes");
                              }
                           }

                           if (!uc.checkNull(hashMap.get("NEEDSPMT"))) {
                              hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
                              if (hashMap.get("NEEDSPMT").toString().equals("Yes")) {
                                 hasOpenTask = "Yes";
                                 hm.put("HasOpenTask", hasOpenTask);
                                 automatictasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSPMT is Yes");
                              }
                           }

                           if (!uc.checkNull(hashMap.get("PATIENTNAME"))) {
                              hm.put("PatientName", hashMap.get("PATIENTNAME").toString());
                           }

                           if (!uc.checkNull(hashMap.get("PATIENTUHID"))) {
                              hm.put("PatientUHID", hashMap.get("PATIENTUHID").toString());
                           }

                           if (!uc.checkNull(hashMap.get("POLICYNAME"))) {
                              hm.put("PolicyName", hashMap.get("POLICYNAME").toString());
                           }

                           if (!uc.checkNull(hashMap.get("POLICYNO"))) {
                              hm.put("PolicyNo", hashMap.get("POLICYNO").toString());
                           }

                           int var39;
                           String[] var40;
                           int var83;
                           if (!workItemName.contains("Inward") && !workItemName.contains("Executive") && !workItemName.contains("Query")) {
                              String[] workItemNames = new String[7];
                              workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              List<String> list = new ArrayList();
                              Collections.addAll(list, workItemNames);
                              list.removeAll(Collections.singleton((Object)null));
                              workItemNames = (String[])list.toArray(new String[list.size()]);
                              automatictasklog.warn("ECS savvion " + methodName + " Service :: Available TaskNames to set hasOpenTask " + Arrays.toString(workItemNames));
                              var40 = workItemNames;
                              var39 = workItemNames.length;

                              for(var83 = 0; var83 < var39; ++var83) {
                                 winame = var40[var83];
                                 if (winame != null && winame != "" && (winame.contains("ALInvestigator") || winame.contains("PMT")) && !winame.equals(workItemName)) {
                                    automatictasklog.warn("ECS savvion " + methodName + " Service :Investigator task is available");
                                    hasOpenTask = "Yes";
                                    break;
                                 }
                              }

                              hm.put("HasOpenTask", hasOpenTask);
                           }

                           if (blsession != null) {
                              pi.updateSlotValue(hm);
                              pi.save();
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName :" + workItemName + " ::: " + workItemCaseStatus);
                              blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                              WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                              if (!wi.isActivated()) {
                                 responseCode = "5034";
                                 responseMessage = "DS_NAME_INVALID";
                              } else {
                                 if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp") || workItemName.contains("VerticalHead") || workItemName.contains("Approver")) && workItemCaseStatus.equals("Reject")) {
                                    automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
                                    completedWorkItem = uc.completeMultipleTask(processInstanceName, workItemName, blserver, blsession, workItemCaseStatus);
                                    sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                                    uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                                 }

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
                                 if ((workItemName.contains("Investigator") || workItemName.contains("PMTRevert") || workItemName.contains("TagChecker")) && hasOpenTask.equals("No")) {
                                    automatictasklog.info("ECS savvion Service :: checking for DummyQueue task ");
                                    String[] workItemNames = new String[5];
                                    workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                                    automatictasklog.warn("ECS savvion " + methodName + " Service :: Available workItem array " + Arrays.toString(workItemNames));
                                    var40 = workItemNames;
                                    var39 = workItemNames.length;

                                    for(var83 = 0; var83 < var39; ++var83) {
                                       winame = var40[var83];
                                       if (winame != null && winame != "" && winame.contains("Dummy")) {
                                          automatictasklog.info("ECS Complete Dummy Queue Task enterred");
                                          WorkItem win = blserver.getWorkItem(blsessionECSAdmin, winame);
                                          if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                             automatictasklog.warn("ECS AssignTask enterred");
                                             uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
                                             automatictasklog.warn("ECS Task Assigned to user");
                                          }

                                          uc.completeWorkitem((new Long(blsession.getID())).toString(), winame);
                                          automatictasklog.info("ECS Dummy Queue Task Completed");
                                       }
                                    }
                                 }

                                 nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                                 if ((workItemCaseStatus.equals("Approve") || workItemCaseStatus.equals("Completed") && !workItemName.contains("Tag") || workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack") && !workItemName.contains("TagChecker")) && !isALApproverAccepted.equals("Approve")) {
                                    try {
                                       isQueriedAL = (Boolean)pi.getDataSlotValue("isQueriedAL");
                                       if (isQueriedAL && nextWorkItemCaseStatus.equals("New")) {
                                          nextWorkItemCaseStatus = "Queried";
                                       }
                                    } catch (Exception var63) {
                                       automatictasklog.warn("ECS savvion " + methodName + " Service :isQueriedAL is " + isQueriedAL + " and instance is closed.");
                                    }
                                 }

                                 if (isALApproverAccepted.equals("Reject")) {
                                    nextWorkItemCaseStatus = "New";
                                 }

                                 if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator") || workItemName.contains("VerticalHead")) {
                                    try {
                                       Thread.sleep(5000L);
                                    } catch (InterruptedException var62) {
                                       var62.printStackTrace();
                                    }
                                 }

                                 wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)null, (String)null, (String)null, (String)null, (String)null);
                                 automatictasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
                                 if (wiObjects != null && wiObjects.length != 0) {
                                    uc.checkUserNumbersInGroup(wiObjects);
                                 }

                                 if (wiObjects != null && wiObjects.length != 0) {
                                    for(int x = 0; x < wiObjects.length; ++x) {
                                       if (wiObjects[x].getName() != null && wiObjects[x].getName().contains("PMTRevert")) {
                                          RequestObject[] reqsObj = new RequestObject[]{new RequestObject(), null};
                                          reqsObj[0].setKey("WORKITEMNAME");
                                          reqsObj[0].setValue(wiObjects[x].getName());
                                          reqsObj[1] = new RequestObject();
                                          reqsObj[1].setKey("TOBEASSIGNEDUSERID");
                                          reqsObj[1].setValue(userId);
                                          automatictasklog.info("ECS Savvion: " + methodName + " ::Creating request Object for assignOrKeepTask method for PMTRevert queue task " + Arrays.toString(reqsObj));
                                          ResponseObject respObj = uc.assignOrKeepTask(reqsObj);
                                          automatictasklog.info("ECS Savvion: " + methodName + " :: Assigned task to user ::  " + userId + " and response id  " + respObj.getResponseMessage());
                                          if (respObj.getResponseCode().equals("5000")) {
                                             automatictasklog.info("ECS Savvion: " + methodName + " :: Setting CaseStatus as Locked for PMTRevert Queue Task");
                                             wiObjects[x].setCaseStatus("Locked");
                                             wiObjects[x].setPerformer(userId);
                                          }
                                       }
                                    }
                                 }

                                 if (workItemName.contains("Query")) {
                                    wiObjects[0].setCaseStatus("Queried");
                                    wiObjects[0].setTransactionId(transactionId);
                                    wiObjects[0].setTransactionType(transactionType);
                                 }

                                 try {
                                    automatictasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
                                    pi = blserver.getProcessInstance(blsession, processInstanceName);
                                    automatictasklog.warn("ECS " + processInstanceName + "  object is : " + pi);
                                 } catch (Exception var61) {
                                    isInstanceCompleted = true;
                                 }

                                 automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
                              }
                           } else {
                              responseCode = "5030";
                              responseMessage = "USER_ID_INVALID";
                           }
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

               e = null;
            } catch (Exception var66) {
               e = var66;
               if (!isInstanceCompleted) {
                  responseCode = "5034";
                  responseMessage = "DS_NAME_INVALID";
               }

               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var66);
               if (var66.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion ALFlow " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var60) {
                     automatictasklog.error("ECS Savvion ALFlow: " + methodName + " :: Exception " + var60);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect(sessionId);
            } catch (Exception var58) {
               var58.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var58.getMessage());
            }

         }

         if (associatedPIName != null && !associatedPIName.equals("") && associatedWIName != null && !associatedWIName.equals("") && associatedresObj.getResponseCode() == "5000" && responseCode == "5000") {
            ArrayList workItemList = new ArrayList();
            WorkItemObject workitem = new WorkItemObject();
            workitem.setName(workItemName);
            workitem.setCaseStatus("Completed");
            workItemList.add(workitem);
            completedWorkItem = uc.getResultWorkitems(workItemList);
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
         } catch (Exception var59) {
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Custom Error " + var59);
         }
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response " + resObj);
      automatictasklog.info("**************************ECS Savvion: " + methodName + " Service::::END ***************************");
      return resObj;
   }
}
