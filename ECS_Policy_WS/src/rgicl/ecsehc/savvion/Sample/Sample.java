package rgicl.ecsehc.savvion.Sample;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;
import rgicl.ecsehc.savvion.policy.util.db.DBUtility;

public class Sample {
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
   private static Logger humantasklog;

   static {
      ECS_SAVVION_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_PROPERTIES.properties";
      ECS_SAVVION_LOG_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
      humantasklog = null;

      try {
         propertiesECSSavvion = new Properties();
         propertiesECSSavvion.load(new FileInputStream(ECS_SAVVION_PROPERTIES));
         propertiesECSLog = new Properties();
         propertiesECSLog.load(new FileInputStream(ECS_SAVVION_LOG_PROPERTIES));
         PropertyConfigurator.configure(propertiesECSLog);
         humantasklog = Logger.getLogger("Human_HCS");
      } catch (Exception var1) {
         throw new RuntimeException("Error HCS loading logger properties", var1);
      }
   }

   public Sample(String propertiesPath) {
   }

   public ResponseObject SampleFlowTask(RequestObject[] reqObj) {
      String methodName = "SampleFlowTask";
      humantasklog.info("ECS Savvion: " + methodName + " Service:::::START");
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
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
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
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("InwardDEO") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && uc.checkNull(hashMap.get("ALINWARDNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: ALINWARDNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("Executive") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && !workItemCaseStatus.equals("SendBack") && uc.checkNull(hashMap.get("ALNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: ALNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
               if (userId != null && !userId.equals("")) {
                  if (responseCode == null) {
                     humantasklog.info(" ECS Savvion " + methodName + " Service:::: begins to complete the task");
                     if (isQueried != null && isQueried.equals("Yes") && parentALInwardNo != null && !parentALInwardNo.equals("")) {
                        humantasklog.info(" ECS Savvion " + methodName + " Service:::: checking for Query Response task");
                        resultArray = db.getQueryQueueTaskName(parentALInwardNo, parentCLInwardNo, processInstanceName);
                        associatedPIName = resultArray[0];
                        associatedWIName = resultArray[1];
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
                        workItemName = workItemName + associatedWIName;
                        associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
                        workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
                        processInstanceName = processInstanceName + associatedPIName;
                        associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
                        processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
                        new ResponseObject();
                        associatedresObj = uc.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
                     }

                     if (associatedresObj != null && associatedresObj.getResponseCode() != "5000") {
                        responseCode = associatedresObj.getResponseCode();
                        responseMessage = associatedresObj.getResponseMessage();
                     } else {
                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query") || workItemCaseStatus.equals("Query")) {
                           if (workItemName.contains("Query")) {
                              workItemCaseStatus = "Closed";
                           }

                           humantasklog.info("ECS Savvion " + methodName + " setting isQueriedAL flag as true");
                           hm.put("isQueriedAL", true);
                        }

                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        hm.put("HasOpenTask", hasOpenTask);
                        if (!uc.checkNull(hashMap.get("EMPLOYEEID"))) {
                           hm.put("EmployeeId", hashMap.get("EMPLOYEEID").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HOSPITALNAME"))) {
                           hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
                        }

                        if (workItemName != null && workItemName.contains("InwardDEO")) {
                           humantasklog.info("workItemCaseStatus when ALInwardDEO submits task " + workItemCaseStatus);
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
                              humantasklog.info("ECS Savvion " + methodName + " resetting isQueriedAL flag as false");
                              hm.put("isQueriedAL", false);
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
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSINVESTIGATION is Yes");
                           }
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSPMT"))) {
                           hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
                           if (hashMap.get("NEEDSPMT").toString().equals("Yes")) {
                              hasOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSPMT is Yes");
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

                        String winame;
                        int var38;
                        int var39;
                        String[] var40;
                        if (!workItemName.contains("Inward") && !workItemName.contains("Executive") && !workItemName.contains("Query")) {
                           String[] workItemNames = new String[7];
                           workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                           List<String> list = new ArrayList();
                           Collections.addAll(list, workItemNames);
                           list.removeAll(Collections.singleton((Object)null));
                           workItemNames = (String[])list.toArray(new String[list.size()]);
                           humantasklog.warn("ECS savvion " + methodName + " Service :: Available TaskNames to set hasOpenTask " + Arrays.toString(workItemNames));
                           var40 = workItemNames;
                           var39 = workItemNames.length;

                           for(var38 = 0; var38 < var39; ++var38) {
                              winame = var40[var38];
                              if (winame != null && winame != "" && (winame.contains("ALInvestigator") || winame.contains("PMT")) && !winame.equals(workItemName)) {
                                 humantasklog.warn("ECS savvion " + methodName + " Service :Investigator task is available");
                                 hasOpenTask = "Yes";
                                 break;
                              }
                           }

                           hm.put("HasOpenTask", hasOpenTask);
                        }

                        if (blsession != null) {
                           pi.updateSlotValue(hm);
                           pi.save();
                           humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName :" + workItemName + " ::: " + workItemCaseStatus);
                           blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                           if (!wi.isActivated()) {
                              responseCode = "5034";
                              responseMessage = "DS_NAME_INVALID";
                           } else {
                              if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp") || workItemName.contains("VerticalHead") || workItemName.contains("Approver")) && workItemCaseStatus.equals("Reject")) {
                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
                                 completedWorkItem = uc.completeMultipleTask(processInstanceName, workItemName, blserver, blsession, workItemCaseStatus);
                                 sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                                 uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                              }

                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 humantasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 humantasklog.warn("ECS Task Assigned to user");
                              }

                              humantasklog.warn("ECS CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              humantasklog.warn("ECS Task Completed");
                              responseCode = "5000";
                              responseMessage = "SUCCESS";
                              if ((workItemName.contains("Investigator") || workItemName.contains("PMTRevert")) && hasOpenTask.equals("No")) {
                                 humantasklog.info("ECS savvion Service :: checking for DummyQueue task ");
                                 String[] workItemNames = new String[5];
                                 workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                                 humantasklog.warn("ECS savvion " + methodName + " Service :: Available workItem array " + Arrays.toString(workItemNames));
                                 var40 = workItemNames;
                                 var39 = workItemNames.length;

                                 for(var38 = 0; var38 < var39; ++var38) {
                                    winame = var40[var38];
                                    if (winame != null && winame != "" && winame.contains("Dummy")) {
                                       humantasklog.info("ECS Complete Dummy Queue Task enterred");
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, winame);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), winame);
                                       humantasklog.info("ECS Dummy Queue Task Completed");
                                    }
                                 }
                              }

                              nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                              if ((workItemCaseStatus.equals("Approve") || workItemCaseStatus.equals("Completed") || workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack")) && !isALApproverAccepted.equals("Approve")) {
                                 try {
                                    isQueriedAL = (Boolean)pi.getDataSlotValue("isQueriedAL");
                                    if (isQueriedAL && nextWorkItemCaseStatus.equals("New")) {
                                       nextWorkItemCaseStatus = "Queried";
                                    }
                                 } catch (Exception var55) {
                                    humantasklog.warn("ECS savvion " + methodName + " Service :isQueriedAL is " + isQueriedAL + " and instance is closed.");
                                 }
                              }

                              if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator") || workItemName.contains("VerticalHead")) {
                                 try {
                                    Thread.sleep(5000L);
                                 } catch (InterruptedException var54) {
                                    var54.printStackTrace();
                                 }
                              }

                              wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)null, (String)null, (String)null, (String)null, (String)null);
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
                                       humantasklog.info("ECS Savvion: " + methodName + " ::Creating request Object for assignOrKeepTask method for PMTRevert queue task " + Arrays.toString(reqsObj));
                                       ResponseObject respObj = uc.assignOrKeepTask(reqsObj);
                                       humantasklog.info("ECS Savvion: " + methodName + " :: Assigned task to user ::  " + userId + " and response id  " + respObj.getResponseMessage());
                                       if (respObj.getResponseCode().equals("5000")) {
                                          humantasklog.info("ECS Savvion: " + methodName + " :: Setting CaseStatus as Locked for PMTRevert Queue Task");
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
                                 humantasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
                                 pi = blserver.getProcessInstance(blsession, processInstanceName);
                                 humantasklog.warn("ECS " + processInstanceName + "  object is : " + pi);
                              } catch (Exception var53) {
                                 isInstanceCompleted = true;
                              }

                              humantasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
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

            blserver = null;
         } catch (Exception var56) {
            if (!isInstanceCompleted) {
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
            }

            var56.printStackTrace();
            humantasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var56);
         } finally {
            try {
               uc.disconnect(sessionId);
            } catch (Exception var52) {
               var52.printStackTrace();
               humantasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var52.getMessage());
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
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END for " + (String)hashMap.get("WORKITEMNAME") + " with response code " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }

   public ResponseObject ALFlowTask(RequestObject[] reqObj) {
      String methodName = "ALFlowTask";
      humantasklog.info("ECS Savvion: " + methodName + " Service:::::START");
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
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
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
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("InwardDEO") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && uc.checkNull(hashMap.get("ALINWARDNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: ALINWARDNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("Executive") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && !workItemCaseStatus.equals("SendBack") && uc.checkNull(hashMap.get("ALNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: ALNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
               if (userId != null && !userId.equals("")) {
                  if (responseCode == null) {
                     humantasklog.info(" ECS Savvion " + methodName + " Service:::: begins to complete the task");
                     if (isQueried != null && isQueried.equals("Yes") && parentALInwardNo != null && !parentALInwardNo.equals("")) {
                        humantasklog.info(" ECS Savvion " + methodName + " Service:::: checking for Query Response task");
                        resultArray = db.getQueryQueueTaskName(parentALInwardNo, parentCLInwardNo, processInstanceName);
                        associatedPIName = resultArray[0];
                        associatedWIName = resultArray[1];
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
                        workItemName = workItemName + associatedWIName;
                        associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
                        workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
                        processInstanceName = processInstanceName + associatedPIName;
                        associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
                        processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
                        new ResponseObject();
                        associatedresObj = uc.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
                     }

                     if (associatedresObj != null && associatedresObj.getResponseCode() != "5000") {
                        responseCode = associatedresObj.getResponseCode();
                        responseMessage = associatedresObj.getResponseMessage();
                     } else {
                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query") || workItemCaseStatus.equals("Query")) {
                           if (workItemName.contains("Query")) {
                              workItemCaseStatus = "Closed";
                           }

                           humantasklog.info("ECS Savvion " + methodName + " setting isQueriedAL flag as true");
                           hm.put("isQueriedAL", true);
                        }

                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        hm.put("HasOpenTask", hasOpenTask);
                        if (!uc.checkNull(hashMap.get("EMPLOYEEID"))) {
                           hm.put("EmployeeId", hashMap.get("EMPLOYEEID").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HOSPITALNAME"))) {
                           hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
                        }

                        if (workItemName != null && workItemName.contains("InwardDEO")) {
                           humantasklog.info("workItemCaseStatus when ALInwardDEO submits task " + workItemCaseStatus);
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
                              humantasklog.info("ECS Savvion " + methodName + " resetting isQueriedAL flag as false");
                              hm.put("isQueriedAL", false);
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
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSINVESTIGATION is Yes");
                           }
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSPMT"))) {
                           hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
                           if (hashMap.get("NEEDSPMT").toString().equals("Yes")) {
                              hasOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask set to " + hasOpenTask + " since NEEDSPMT is Yes");
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

                        String winame;
                        int var38;
                        int var39;
                        String[] var40;
                        if (!workItemName.contains("Inward") && !workItemName.contains("Executive") && !workItemName.contains("Query")) {
                           String[] workItemNames = new String[7];
                           workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                           List<String> list = new ArrayList();
                           Collections.addAll(list, workItemNames);
                           list.removeAll(Collections.singleton((Object)null));
                           workItemNames = (String[])list.toArray(new String[list.size()]);
                           humantasklog.warn("ECS savvion " + methodName + " Service :: Available TaskNames to set hasOpenTask " + Arrays.toString(workItemNames));
                           var40 = workItemNames;
                           var39 = workItemNames.length;

                           for(var38 = 0; var38 < var39; ++var38) {
                              winame = var40[var38];
                              if (winame != null && winame != "" && (winame.contains("ALInvestigator") || winame.contains("PMT")) && !winame.equals(workItemName)) {
                                 humantasklog.warn("ECS savvion " + methodName + " Service :Investigator task is available");
                                 hasOpenTask = "Yes";
                                 break;
                              }
                           }

                           hm.put("HasOpenTask", hasOpenTask);
                        }

                        if (blsession != null) {
                           pi.updateSlotValue(hm);
                           pi.save();
                           humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName :" + workItemName + " ::: " + workItemCaseStatus);
                           blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                           if (!wi.isActivated()) {
                              responseCode = "5034";
                              responseMessage = "DS_NAME_INVALID";
                           } else {
                              if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp") || workItemName.contains("VerticalHead") || workItemName.contains("Approver")) && workItemCaseStatus.equals("Reject")) {
                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
                                 completedWorkItem = uc.completeMultipleTask(processInstanceName, workItemName, blserver, blsession, workItemCaseStatus);
                                 sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                                 uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                              }

                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 humantasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 humantasklog.warn("ECS Task Assigned to user");
                              }

                              humantasklog.warn("ECS CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              humantasklog.warn("ECS Task Completed");
                              responseCode = "5000";
                              responseMessage = "SUCCESS";
                              if ((workItemName.contains("Investigator") || workItemName.contains("PMTRevert")) && hasOpenTask.equals("No")) {
                                 humantasklog.info("ECS savvion Service :: checking for DummyQueue task ");
                                 String[] workItemNames = new String[5];
                                 workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                                 humantasklog.warn("ECS savvion " + methodName + " Service :: Available workItem array " + Arrays.toString(workItemNames));
                                 var40 = workItemNames;
                                 var39 = workItemNames.length;

                                 for(var38 = 0; var38 < var39; ++var38) {
                                    winame = var40[var38];
                                    if (winame != null && winame != "" && winame.contains("Dummy")) {
                                       humantasklog.info("ECS Complete Dummy Queue Task enterred");
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, winame);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), winame);
                                       humantasklog.info("ECS Dummy Queue Task Completed");
                                    }
                                 }
                              }

                              nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                              if ((workItemCaseStatus.equals("Approve") || workItemCaseStatus.equals("Completed") || workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack")) && !isALApproverAccepted.equals("Approve")) {
                                 try {
                                    isQueriedAL = (Boolean)pi.getDataSlotValue("isQueriedAL");
                                    if (isQueriedAL && nextWorkItemCaseStatus.equals("New")) {
                                       nextWorkItemCaseStatus = "Queried";
                                    }
                                 } catch (Exception var55) {
                                    humantasklog.warn("ECS savvion " + methodName + " Service :isQueriedAL is " + isQueriedAL + " and instance is closed.");
                                 }
                              }

                              if (workItemCaseStatus.equals("Reject") && workItemName.contains("ALApprover")) {
                                 nextWorkItemCaseStatus = "New";
                              }

                              if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator") || workItemName.contains("VerticalHead")) {
                                 try {
                                    Thread.sleep(5000L);
                                 } catch (InterruptedException var54) {
                                    var54.printStackTrace();
                                 }
                              }

                              wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)null, (String)null, (String)null, (String)null, (String)null);
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
                                       humantasklog.info("ECS Savvion: " + methodName + " ::Creating request Object for assignOrKeepTask method for PMTRevert queue task " + Arrays.toString(reqsObj));
                                       ResponseObject respObj = uc.assignOrKeepTask(reqsObj);
                                       humantasklog.info("ECS Savvion: " + methodName + " :: Assigned task to user ::  " + userId + " and response id  " + respObj.getResponseMessage());
                                       if (respObj.getResponseCode().equals("5000")) {
                                          humantasklog.info("ECS Savvion: " + methodName + " :: Setting CaseStatus as Locked for PMTRevert Queue Task");
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
                                 humantasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
                                 pi = blserver.getProcessInstance(blsession, processInstanceName);
                                 humantasklog.warn("ECS " + processInstanceName + "  object is : " + pi);
                              } catch (Exception var53) {
                                 isInstanceCompleted = true;
                              }

                              humantasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
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

            blserver = null;
         } catch (Exception var56) {
            if (!isInstanceCompleted) {
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
            }

            var56.printStackTrace();
            humantasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var56);
         } finally {
            try {
               uc.disconnect(sessionId);
            } catch (Exception var52) {
               var52.printStackTrace();
               humantasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var52.getMessage());
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
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }

   public ResponseObject SRFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteSRFlowTask";
      humantasklog.info("=======ECS Savvion: " + methodName + " Service:::::START========");
      UtilClass uc = new UtilClass(SBM_HOME);
      String sessionId = null;
      new HashMap();
      ResponseObject resObjSR = new ResponseObject();
      String workItemCaseStatus = "Approve";
      ResponseObject responseObj = new ResponseObject();
      if (reqObj == null) {
         resObjSR.setResponseCode("5070");
         resObjSR.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String srWorkItemName = (String)hashMap.get("WORKITEMNAME");
         String[] alInwardNo = (String[])hashMap.get("ALINWARDNO");
         String[] clInwardNo = (String[])hashMap.get("CLINWARDNO");
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUE  alInwardNo[] is :: " + Arrays.toString(alInwardNo) + " and clInwardNo[] is :: " + Arrays.toString(clInwardNo));

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

                  if (!wi.isActivated()) {
                     responseObj.setResponseCode("5034");
                     responseObj.setResponseMessage("5034");
                     humantasklog.info("ECS savvion " + methodName + " service :: " + srWorkItemName + " ::is not active");
                  } else {
                     humantasklog.info("ECS savvion " + methodName + " service :: " + srWorkItemName + " ::is active");
                     if (workItemCaseStatus.equals("Approve")) {
                        if (alInwardNo == null && clInwardNo == null) {
                           responseObj.setResponseCode("5000");
                           responseObj.setResponseMessage("SUCCESS");
                        } else {
                           responseObj = this.closeOrReopenALCLTask(reqObj);
                        }

                        humantasklog.info("ECS Savvion: " + methodName + " Service:: closeOrReopenALCLTask method response is ::" + responseObj.getResponseCode());
                        resObjSR.setResponseCode(responseObj.getResponseCode());
                        resObjSR.setResponseMessage(responseObj.getResponseMessage());
                        if (responseObj.getResponseCode() == "5000") {
                           resObjSR = this.completeSRTask(reqObj);
                        }
                     } else {
                        resObjSR = this.completeSRTask(reqObj);
                     }

                     resObjSR.setCompletedWorkItemArray(responseObj.getCompletedWorkItemArray());
                     resObjSR.setResultworkItemArray(responseObj.getResultworkItemArray());
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

            blserver = null;
         } catch (Exception var25) {
            humantasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var25.getMessage());
            resObjSR.setResponseCode("5050");
            resObjSR.setResponseMessage("FAILURE_EXCEPTION");
            if (var25.getMessage().contains("Can not find <ProcessInstance>")) {
               resObjSR.setResponseCode("5034");
               resObjSR.setResponseMessage("DS_NAME_INVALID");
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var24) {
               var24.printStackTrace();
               humantasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var24.getMessage());
            }

         }
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END " + resObjSR.getResponseCode() + " :: Response Message :: " + resObjSR.getResponseMessage());
      return resObjSR;
   }

   private ResponseObject closeOrReopenALCLTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
      String methodName = "closeOrReopenALCLTask";
      humantasklog.info("------------ECS Savvion: " + methodName + " Service starts-----------");
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
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String EFTReissue = (String)hashMap.get("EFTREISSUE");
         String isMigrated = (String)hashMap.get("ISMIGRATED");
         String[] piNamesList = (String[])hashMap.get("PINAMESLIST");

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            DBUtility db = new DBUtility(SBM_HOME);
            blserver = BLClientUtil.getBizLogicServer();
            blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
            ProcessInstance srPI = blserver.getProcessInstance(blsession, srPIName);
            String SRType = (String)srPI.getDataSlotValue("SRType");
            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosure") || SRType.equals("CLClosure") || SRType.equals("ALTermination") || SRType.equals("CLTermination") || SRType.equals("ChequeCancellationAndStopPayment"))) {
               responseObj = this.completePI(reqObj);
               humantasklog.info("ECS Savvion: " + methodName + " Service:::: response of  completeProcessInstance method is " + responseObj.getResponseMessage());
            }

            String SRReopen;
            String piName;
            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosureReopen") || SRType.equals("ALRejectionReopen")) && piNamesList != null) {
               humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside AL Reopen & SRType is : " + SRType + " ::piNamesList[0] is " + piNamesList[0]);
               SRReopen = "Executive";
               result = db.getDataSlotValuesByInstanceId(piNamesList, SRType);
               humantasklog.info("ECS dataslot values for the piNamesList " + piNamesList[0] + " is " + Arrays.toString(result));
               responseObj.setResponseCode("5000");
               responseObj.setResponseMessage("SUCCESS");
               piName = result[5];
               boolean canReopen = false;
               if (SRType != null && !SRType.equals("") && SRType.equals("ALRejectionReopen")) {
                  canReopen = true;
                  humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside ALRejectionReopen and  can open a new AL Flow");
               }

               if (SRType != null && !SRType.equals("") && SRType.equals("ALClosureReopen") && (piName != null && piName.equals("1") || isMigrated != null && isMigrated.equals("Yes"))) {
                  canReopen = true;
                  humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside ALClosureReopen and  can open a new AL Flow");
               }

               if (canReopen) {
                  RequestObject[] objInput = new RequestObject[7];
                  if (result != null && result.length != 0) {
                     objInput[0] = new RequestObject();
                     objInput[0].setKey("PROCESSNAME");
                     objInput[0].setValue("AL_FLOW");
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
                     humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside AL Reopen & response message is : " + responseObj.getResponseMessage() + " created work flow instance is " + responseObj.getResultworkItemArray()[0].getName());
                  } else {
                     humantasklog.info("ECS Savvion: " + methodName + " Service:::: unable to get the data slot values for the piNamesList :" + piNamesList[0]);
                  }
               }
            }

            if (SRType != null && !SRType.equals("") && (SRType.equals("CLClosureReopen") || SRType.equals("CLRejectionReopen")) && piNamesList != null) {
               SRReopen = "Editor";
               humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside CL Reopen & SRType is : " + SRType + " piNamesList[0] is " + piNamesList[0]);
               responseObj = this.createCLFlowFormSR(piNamesList, userId, db, SRType, SRReopen, EFTReissue, "CL_FLOW");
            }

            int var33;
            WorkItemObject[] var34;
            int var35;
            WorkItemObject[] var36;
            WorkItem wi;
            boolean isCLPaidWithSettlement;
            int var48;
            WorkItemObject wObj;
            ProcessInstance clPI;
            String currWorkItemName;
            WorkItemObject wObj;
            int var56;
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
                     hm.put("SRType", "EFTStopPaymentAndReissue");
                     hm.put("SRReopen", SRReopen);
                  }

                  if (EFTReissue != null && !EFTReissue.equals("") && EFTReissue.equals("Yes")) {
                     SRReopen = "EFTChecker";
                  }

                  resultTaskList = db.getNextTaskByPIName(piNamesList);
                  humantasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
                  isCLPaidWithSettlement = false;
                  if (resultTaskList != null && resultTaskList.size() != 0) {
                     workItemObject = uc.getResultWorkitems(resultTaskList);
                     humantasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                     var34 = workItemObject;
                     var33 = workItemObject.length;

                     for(var48 = 0; var48 < var33; ++var48) {
                        wObj = var34[var48];
                        if (wObj.getName().contains("Accounts")) {
                           isCLPaidWithSettlement = true;
                        }
                     }

                     if (!isCLPaidWithSettlement) {
                        responseObj.setResponseCode("5047");
                        responseObj.setResponseMessage("INVALID_REQUEST");
                        humantasklog.info("ECS Savvion :: " + methodName + "SRType is " + SRType + " can't process since Claim is not in paid status");
                     } else {
                        clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                        humantasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                        clPI.updateSlotValue(hm);
                        clPI.save();
                        humantasklog.info("ECS Savvion: " + methodName + " SRType  is " + SRType + " : SRReopen at " + SRReopen);
                        currWorkItemName = "";
                        var36 = workItemObject;
                        var35 = workItemObject.length;

                        for(var56 = 0; var56 < var35; ++var56) {
                           wObj = var36[var56];
                           humantasklog.info("ECS Savvion: " + methodName + " available workitem name  is " + wObj.getName());
                           if (wObj.getName().contains("Accounts")) {
                              currWorkItemName = wObj.getName();
                              humantasklog.info("ECS workItem Name is " + currWorkItemName);
                              wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 humantasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 humantasklog.warn("ECS Task Assigned to user");
                              }

                              humantasklog.warn("ECS Savvion :: " + methodName + " EFTRejectionReopen workitem:: " + currWorkItemName + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), currWorkItemName);
                              humantasklog.warn("ECS Task Completed");
                              responseObj.setResponseCode("5000");
                              responseObj.setResponseMessage("SUCCESS");
                              workItemObj = new WorkItemObject();
                              workItemObj.setName(wObj.getName());
                              workItemObj.setPiName(wObj.getPiName());
                              workItemObj.setCaseStatus("Completed");
                              workItemList.add(workItemObj);
                           }
                        }

                        completedWorkItem = new WorkItemObject[workItemList.size()];
                        completedWorkItem = uc.getResultWorkitems(workItemList);
                        wiObjects = uc.getNextAvailableTaskList(workItemObject[0].getPiName(), nextWorkItemCaseStatus, currWorkItemName, (String)null, (String)null, isFromSR, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                        if (wiObjects != null && wiObjects.length != 0) {
                           uc.checkUserNumbersInGroup(wiObjects);
                        }

                        responseObj.setResultworkItemArray(wiObjects);
                        responseObj.setCompletedWorkItemArray(completedWorkItem);
                     }
                  } else {
                     responseObj.setResponseCode("5047");
                     responseObj.setResponseMessage("INVALID_REQUEST");
                     humantasklog.info("ECS Savvion :: " + methodName + "SRType is " + SRType + " no workItem available to complete");
                  }
               } catch (Exception var45) {
                  responseObj.setResponseCode("5050");
                  responseObj.setResponseMessage("FAILURE_EXCEPTION");
                  humantasklog.error("ECS Savvion :: " + methodName + " EFTRejectionReopen Exception occurs :: " + var45);
               }
            }

            if (SRType != null && !SRType.equals("") && (SRType.equals("ChequeStopPaymentAndReissue") || SRType.equals("ChequeCancellationAndReissue")) && piNamesList != null && piNamesList.length != 0) {
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
                  humantasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
                  if (resultTaskList != null && resultTaskList.size() != 0) {
                     workItemObject = uc.getResultWorkitems(resultTaskList);
                     humantasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                     var34 = workItemObject;
                     var33 = workItemObject.length;

                     for(var48 = 0; var48 < var33; ++var48) {
                        wObj = var34[var48];
                        humantasklog.info("ECS Savvion: " + methodName + " Available workItemName  is " + wObj.getName());
                        if (wObj.getName().contains("SRQueue")) {
                           isCLPaidWithSettlement = true;
                        }
                     }

                     humantasklog.info("ECS Savvion: " + methodName + " isCLPaidWithSettlement " + isCLPaidWithSettlement);
                     if (!isCLPaidWithSettlement) {
                        responseObj.setResponseCode("5047");
                        responseObj.setResponseMessage("INVALID_REQUEST");
                        humantasklog.info("ECS Savvion :: " + methodName + " can't process since Claim is not in paid with settlement letter status");
                     } else {
                        clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                        humantasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                        clPI.updateSlotValue(hm);
                        clPI.save();
                        humantasklog.info("ECS Savvion: " + methodName + " SRType  is " + SRType + " : SRReopen is " + SRReopen);
                        currWorkItemName = "";
                        var36 = workItemObject;
                        var35 = workItemObject.length;

                        for(var56 = 0; var56 < var35; ++var56) {
                           wObj = var36[var56];
                           humantasklog.info("ECS Savvion: " + methodName + " workitem name  is " + wObj.getName());
                           if (wObj.getName().contains("SRQueue")) {
                              currWorkItemName = wObj.getName();
                              humantasklog.info("ECS workItem Name is " + wObj.getName());
                              wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 humantasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                 humantasklog.warn("ECS Task Assigned to user");
                              }

                              humantasklog.warn("ECS Savvion :: " + methodName + "  workitem:: " + wObj.getName() + " CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                              humantasklog.warn("ECS Task Completed");
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
                     humantasklog.error("ECS Savvion :: " + methodName + " :: Creating new CL Flow since the flow with clInwardNo " + piNamesList[0] + " is not active");
                     responseObj = this.createCLFlowFormSR(piNamesList, userId, db, SRType, SRReopen, EFTReissue, "CL_FLOW");
                  }
               } catch (Exception var44) {
                  responseObj.setResponseCode("5050");
                  responseObj.setResponseMessage("FAILURE_EXCEPTION");
                  humantasklog.error("ECS Savvion :: " + methodName + SRType + " Exception occurs :: " + var44);
               }
            }

            if (SRType != null && SRType != "" && SRType.equals("Recovery") && piNamesList != null && piNamesList.length != 0) {
               humantasklog.info("ECS Savvion " + methodName + " Service :: SRType is " + SRType + " :: clInwardNo is " + Arrays.toString(piNamesList));
               resultTaskList = db.getNextTaskByPIName(piNamesList);
               humantasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  String[] piNameArray = new String[1];
                  String[] var55 = piNamesList;
                  var48 = piNamesList.length;

                  for(int var54 = 0; var54 < var48; ++var54) {
                     piName = var55[var54];
                     piNameArray[0] = piName;
                     boolean isCLPaidWithSettlement = false;

                     try {
                        ArrayList taskList = null;
                        taskList = db.getNextTaskByPIName(piNamesList);
                        humantasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + taskList.size() + " for piName " + piName);
                        if (taskList != null && taskList.size() != 0) {
                           workItemObject = uc.getResultWorkitems(taskList);
                           humantasklog.info("ECS Savvion: " + methodName + " workItemObject size is " + workItemObject.length);
                           WorkItemObject[] var39 = workItemObject;
                           int var38 = workItemObject.length;

                           for(int var61 = 0; var61 < var38; ++var61) {
                              WorkItemObject wObj = var39[var61];
                              if (wObj.getName().contains("SRQueue")) {
                                 isCLPaidWithSettlement = true;
                              }
                           }

                           if (isCLPaidWithSettlement) {
                              ProcessInstance clPI = blserver.getProcessInstance(blsession, workItemObject[0].getPiName());
                              humantasklog.info("ECS Savvion: " + methodName + " ProcessInstance  is " + clPI.getProcessInstanceName());
                              hm.put("SRType", "Recovery");
                              clPI.updateSlotValue(hm);
                              clPI.save();
                              String currWorkItemName = "";
                              WorkItemObject[] var41 = workItemObject;
                              int var40 = workItemObject.length;

                              for(int var64 = 0; var64 < var40; ++var64) {
                                 WorkItemObject wObj = var41[var64];
                                 humantasklog.info("ECS Savvion: " + methodName + " workitem name  is " + wObj.getName());
                                 if (wObj.getName().contains("SRQueue") || wObj.getName().contains("CLInvestigator")) {
                                    currWorkItemName = wObj.getName();
                                    humantasklog.info("ECS workItem Name is " + wObj.getName());
                                    WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, currWorkItemName);
                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                       humantasklog.warn("ECS AssignTask enterred");
                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                       humantasklog.warn("ECS Task Assigned to user");
                                    }

                                    humantasklog.warn("ECS Savvion :: " + methodName + "  workitem:: " + wObj.getName() + " CompleteTask enterred");
                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), wObj.getName());
                                    humantasklog.warn("ECS Task Completed");
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
                              humantasklog.info("ECS Savvion :: " + methodName + " can't process the request since Claim with piName " + piName + " is not in the status of paid with settlement letter");
                           }
                        }
                     } catch (Exception var43) {
                        humantasklog.error("ECS Savvion: " + methodName + " Service:: FullRecovery :: Exception:::" + var43);
                     }
                  }

                  if (responseObj.getCompletedWorkItemArray() != null && responseObj.getCompletedWorkItemArray().length != 0) {
                     responseObj.setResponseCode("5000");
                     responseObj.setResponseMessage("SUCCESS");
                  } else {
                     responseObj.setResponseCode("5047");
                     responseObj.setResponseMessage("INVALID_REQUEST");
                     humantasklog.error("ECS Savvion :: " + methodName + " can't process the request since there are no Claim to process");
                  }
               } else {
                  humantasklog.error("ECS Savvion :: " + methodName + " there are no Claim to process. So setting the response as SUCCESS");
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

            blserver = null;
         } catch (Exception var46) {
            responseObj.setResponseCode("5050");
            responseObj.setResponseMessage("FAILURE_EXCEPTION");
            humantasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var46);
         }
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseObj.getResponseCode() + " :: Response Message :: " + responseObj.getResponseMessage());
      return responseObj;
   }

   private ResponseObject createCLFlowFormSR(String[] piNamesList, String userId, DBUtility db, String SRType, String SRReopen, String EFTReissue, String ptName) {
      ResponseObject responseObj = null;
      String[] result = new String[3];
      UtilClass uc = new UtilClass(SBM_HOME);
      String methodName = "buildRequestToCreateCLFlow";
      result = db.getDataSlotValuesByInstanceId(piNamesList, SRType);
      humantasklog.info("ECS dataslot values for the clInwardNo " + piNamesList[0] + " is " + Arrays.toString(result));
      RequestObject[] objInput = new RequestObject[10];
      if (result != null && result.length != 0) {
         objInput[0] = new RequestObject();
         objInput[0].setKey("PROCESSNAME");
         objInput[0].setValue(ptName);
         objInput[1] = new RequestObject();
         objInput[1].setKey("USERID");
         objInput[1].setValue(userId);
         objInput[2] = new RequestObject();
         objInput[2].setKey("CLCASETYPE");
         objInput[2].setValue(result[0]);
         if (result[0] == null) {
            objInput[2].setValue("DF");
         }

         objInput[3] = new RequestObject();
         objInput[3].setKey("AUTORELEASE");
         objInput[3].setValue(result[1]);
         if (result[1] == null) {
            objInput[3].setValue("No");
         }

         objInput[4] = new RequestObject();
         objInput[4].setKey("SRREOPEN");
         objInput[4].setValue(SRReopen);
         objInput[5] = new RequestObject();
         objInput[5].setKey("ISFROMSR");
         objInput[5].setValue("Yes");
         objInput[6] = new RequestObject();
         objInput[6].setKey("SRTYPE");
         objInput[6].setValue(SRType);
         objInput[7] = new RequestObject();
         objInput[7].setKey("CLINWARDNO");
         objInput[7].setValue(result[3]);
         objInput[8] = new RequestObject();
         objInput[8].setKey("CLNO");
         objInput[8].setValue(result[4]);
         objInput[9] = new RequestObject();
         objInput[9].setKey("EFTREISSUE");
         objInput[9].setValue(EFTReissue);
         responseObj = uc.CreateWorkFlow(objInput);
         humantasklog.info("ECS Savvion: " + methodName + " Service::::Inside CL Reopen & response message is : " + responseObj.getResponseMessage() + " created work flow instance is " + responseObj.getResultworkItemArray()[0].getName());
      } else {
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: unable to get the data slot values for the piNamesList :" + piNamesList[0]);
      }

      return responseObj;
   }

   private ResponseObject completeSRTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
      String methodName = "completeSRTask";
      humantasklog.info("**********ECS Savvion: " + methodName + " Service starts **********");
      HashMap hm = new HashMap();
      new HashMap();
      ResponseObject responseObj = new ResponseObject();
      String workItemCaseStatus = "Approve";
      boolean isInstanceCompleted = false;
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         responseObj.setResponseCode("5070");
         responseObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String piName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            blserver = BLClientUtil.getBizLogicServer();
            blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
            ProcessInstance pi = blserver.getProcessInstance(blsession, piName);
            WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
            humantasklog.info("ECS Savvion: " + methodName + " Service:::: updating dataslot values ");
            if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
               workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
            }

            if (workItemName != null && workItemName.contains("SRChecker")) {
               hm.put("HasSRCheckerAccepted", workItemCaseStatus);
            }

            if (workItemName != null && workItemName.contains("PaymentChecker")) {
               hm.put("HasPaymentCheckerAccepted", workItemCaseStatus);
            }

            if (blsession != null) {
               pi.updateSlotValue(hm);
               pi.save();
               if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                  humantasklog.warn("ECS AssignTask enterred");
                  uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                  humantasklog.warn("ECS Task Assigned to user");
               }

               humantasklog.warn("ECS Complete SR Flow Task enterred");
               uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
               humantasklog.warn("SR Flow ECS Task Completed");
               responseObj.setResponseCode("5000");
               responseObj.setResponseMessage("SUCCESS");
            }

            try {
               pi = blserver.getProcessInstance(blsession, piName);
               humantasklog.warn("ECS processInstance " + pi);
            } catch (Exception var18) {
               isInstanceCompleted = true;
               humantasklog.warn("ECS processInstance " + piName + " got completed is " + isInstanceCompleted);
            }

            if (blsession != null && blserver.isSessionValid(blsession)) {
               blserver.disConnect(blsession);
            }

            if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
               blserver.disConnect(blsessionECSAdmin);
            }

            blserver = null;
         } catch (Exception var19) {
            responseObj.setResponseCode("5050");
            responseObj.setResponseMessage("FAILURE_EXCEPTION");
            humantasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var19.getMessage());
         }

         responseObj.setWorkItemCaseStatus(workItemCaseStatus);
         responseObj.setInstanceCompleted(isInstanceCompleted);
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseObj.getResponseCode() + " :: Response Message :: " + responseObj.getResponseMessage() + " isInstanceCompleted is " + responseObj.isInstanceCompleted());
      return responseObj;
   }

   private ResponseObject buildRequestToCreateSRFlow(String userId, String SRNo, String SRType) {
      String methodName = "buildRequestToCreateSRFlow";
      humantasklog.info("ECS Savvion: " + methodName + " Service:: Input values are :: UserId : " + userId + " SRNo : " + SRNo + " SRType : " + SRType);
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
         humantasklog.info("ECS Savvion: " + methodName + " Service::::Response message is : " + resObj.getResponseMessage() + " created work flow instance is " + resObj.getResultworkItemArray()[0].getName());
      }

      return resObj;
   }

   public ResponseObject completePI(RequestObject[] reqObj) {
      String methodName = "completePI";
      humantasklog.info("............ECS Savvion: " + methodName + " Service starts...........");
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap hm = new HashMap();
      DBUtility db = new DBUtility(SBM_HOME);
      UtilClass uc = new UtilClass(SBM_HOME);
      boolean isInstanceCompleted = false;
      WorkItemObject[] workItemObject = null;
      WorkItemObject[] completedWorkItem = null;
      ArrayList workItemList = new ArrayList();
      ArrayList resultTaskList = null;
      ProcessInstance pi = null;
      String ClosureStep = "";
      String processInstanceName = "";
      String skipWorkStep = "";
      String wiName = null;
      WorkItemObject[] wiObjects = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String userId = (String)hashMap.get("USERID");
         String piName = (String)hashMap.get("PROCESSINSTANCENAME");
         String[] alInwardNo = (String[])hashMap.get("ALINWARDNO");
         String[] clInwardNo = (String[])hashMap.get("CLINWARDNO");
         String SRType = (String)hashMap.get("SRTYPE");
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUE  alInwardNo[] is :: " + Arrays.toString(alInwardNo) + " and clInwardNo[] is :: " + Arrays.toString(clInwardNo));

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            blserver = BLClientUtil.getBizLogicServer();
            blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            ProcessInstance resultList;
            if (piName != null && !piName.equals("") && piName.contains("SR_FLOW")) {
               resultList = blserver.getProcessInstance(blsession, piName);
               SRType = (String)resultList.getDataSlotValue("SRType");
            }

            humantasklog.info("ECS Savvion: " + methodName + " SRType is " + SRType);
            resultTaskList = db.getPINameByInwardNoDummy(alInwardNo, clInwardNo, SRType);
            ArrayList list;
            if (SRType != null && SRType.equals("CLClosure") && alInwardNo != null) {
               resultList = null;
               list = db.getPINameByInwardNo(alInwardNo, clInwardNo, "ALClosure");
               humantasklog.info("ECS Savvion: " + methodName + " resultList size is " + list.size());
               if (list != null && list.size() != 0) {
                  resultTaskList.addAll(list);
               }
            }

            humantasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
            if (resultTaskList != null && resultTaskList.size() != 0) {
               workItemObject = uc.getResultWorkitems(resultTaskList);
               list = new ArrayList();
               int i = 0;

               while(true) {
                  if (i >= workItemObject.length) {
                     completedWorkItem = new WorkItemObject[workItemObject.length];
                     completedWorkItem = uc.getResultWorkitems(workItemList);
                     humantasklog.info("ECS Savvion: " + methodName + " completedWorkItem size is " + completedWorkItem.length);
                     pi = blserver.getProcessInstance(blsession, processInstanceName);
                     int var30;
                     WorkItemObject[] var31;
                     String winame;
                     WorkItem wi;
                     WorkItemObject workItemObj;
                     int var39;
                     if (processInstanceName.contains("AL_FLOW") && pi.getDataSlotValue("ALCaseType").equals("DNF") && SRType != null && SRType.equals("ALClosure")) {
                        for(i = 0; i < workItemObject.length; ++i) {
                           wiName = workItemObject[i].getName();
                           if (wiName != null && wiName.contains("ALExecutive")) {
                              hm.put("isALExecutiveAccepted", "ALClosure");
                              skipWorkStep = wiName;
                           }

                           if (wiName != null && wiName.contains("ALApprover")) {
                              hm.put("isALApproverAccepted", "ALClosure");
                              skipWorkStep = wiName;
                           }

                           if (wiName != null && wiName.contains("ALQueryQueue")) {
                              hm.put("isALQuerryAccepted", "ALClosure");
                              skipWorkStep = wiName;
                           }
                        }

                        pi.updateSlotValue(hm);
                        pi.save();
                        var31 = workItemObject;
                        var30 = workItemObject.length;

                        for(var39 = 0; var39 < var30; ++var39) {
                           workItemObj = var31[var39];
                           winame = workItemObj.getName();
                           humantasklog.info("==================winame " + winame + " ---------skipWorkStep " + skipWorkStep);
                           if (!winame.contains(skipWorkStep)) {
                              humantasklog.info("inside ==================winame " + winame);
                              wi = blserver.getWorkItem(blsession, winame);
                              blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 humantasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
                              }

                              humantasklog.warn("ECS Savvion: " + methodName + " Suspend workstep  " + winame);
                              wi.suspend();
                           }
                        }

                        humantasklog.info("inside ==================wiName to complete " + skipWorkStep);
                        WorkItem wi = blserver.getWorkItem(blsession, skipWorkStep);
                        blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                        if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                           humantasklog.warn("ECS AssignTask enterred to complete task");
                           uc.assignWorkitem((new Long(blsession.getID())).toString(), skipWorkStep);
                        }

                        uc.completeWorkitem((new Long(blsession.getID())).toString(), skipWorkStep);
                        wiObjects = uc.getNextAvailableTaskList(processInstanceName, "New", skipWorkStep, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)hashMap.get("PMTHARDSTOP"), (String)null, (String)null, (String)null, (String)null);
                        humantasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
                        if (wiObjects != null && wiObjects.length != 0) {
                           uc.checkUserNumbersInGroup(wiObjects);
                        }

                        resObj.setResponseCode("5000");
                        resObj.setResponseMessage("SUCCESS");
                        break;
                     }

                     var31 = workItemObject;
                     var30 = workItemObject.length;

                     for(var39 = 0; var39 < var30; ++var39) {
                        workItemObj = var31[var39];
                        winame = workItemObj.getName();
                        wi = blserver.getWorkItem(blsession, winame);
                        blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                        if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                           humantasklog.warn("ECS AssignTask enterred");
                           uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
                        }

                        humantasklog.warn("ECS Savvion: " + methodName + "Suspend workstep  " + winame);
                        wi.suspend();
                     }

                     String[] piNames = (String[])list.toArray(new String[list.size()]);
                     String[] var43 = piNames;
                     int var42 = piNames.length;

                     for(var30 = 0; var30 < var42; ++var30) {
                        String pName = var43[var30];
                        pi = blserver.getProcessInstance(blsession, pName);
                        humantasklog.info("ECS Savvion: " + methodName + " processInstance :: " + pName + " isactive :: " + pi.isActivated());
                        if (pi.isActivated() && pi.isActivated()) {
                           if (userId.equalsIgnoreCase("ECSAdmin")) {
                              ClosureStep = "AutoBatchJob_Closure";
                           }

                           hm.put("ClosureStep", SRType);
                           pi.updateSlotValue(hm);
                           pi.save();
                           pi.complete();
                           humantasklog.info("ECS Savvion: " + methodName + " processInstance :: " + pName + " is completed :: " + pi.isCompleted() + " with ClosureStep as " + SRType);
                        }
                     }

                     resObj.setResponseCode("5000");
                     resObj.setResponseMessage("SUCCESS");

                     try {
                        pi = blserver.getProcessInstance(blsession, processInstanceName);
                        humantasklog.warn("ECS processInstance " + pi);
                     } catch (Exception var34) {
                        isInstanceCompleted = true;
                        humantasklog.warn("ECS processInstance " + processInstanceName + " got completed is " + isInstanceCompleted);
                     }
                     break;
                  }

                  WorkItemObject workitem = new WorkItemObject();
                  workitem.setName(workItemObject[i].getName());
                  processInstanceName = workItemObject[i].getPiName();
                  workitem.setPiName(processInstanceName);
                  if (SRType == null || SRType.equals("") || !SRType.equals("ALTermination") && !SRType.equals("CLTermination")) {
                     if (SRType == null || SRType.equals("") || !SRType.equals("ChequeCancellationAndStopPayment") && SRType != "FullRecovery") {
                        workitem.setCaseStatus("Closed");
                     } else {
                        workitem.setCaseStatus("Reject");
                     }
                  } else {
                     workitem.setCaseStatus("Terminate");
                  }

                  workItemList.add(workitem);
                  if (!list.contains(processInstanceName)) {
                     list.add(processInstanceName);
                  }

                  ++i;
               }
            } else {
               resObj.setResponseCode("5000");
               resObj.setResponseMessage("SUCCESS");
               humantasklog.info("ECS Savvion :: " + methodName + "  setting response as SUCCESS since AL Inward Numbers " + Arrays.toString(alInwardNo) + "  CL Inward Numbers" + Arrays.toString(clInwardNo) + " might not have open task to complete");
            }

            if (blsession != null && blserver.isSessionValid(blsession)) {
               blserver.disConnect(blsession);
            }

            if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
               blserver.disConnect(blsessionECSAdmin);
            }

            blserver = null;
            if (db != null) {
               humantasklog.info("ECS Savvion: " + methodName + " Service::::closing dbUtility connection");
               db.closeConnection();
            }
         } catch (Exception var35) {
            resObj.setResponseCode("5050");
            resObj.setResponseMessage("FAILURE_EXCEPTION");
            humantasklog.error("ECS Savvion :: " + methodName + " Exception :: " + var35);
         }
      }

      resObj.setInstanceCompleted(isInstanceCompleted);
      resObj.setResultworkItemArray(wiObjects);
      if (completedWorkItem != null && completedWorkItem.length != 0) {
         resObj.setCompletedWorkItemArray(completedWorkItem);
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END " + resObj.getResponseCode() + " :: Response Message :: " + resObj.getResponseMessage());
      return resObj;
   }

   public ResponseObject CLFlowTask(RequestObject[] reqObj) {
      String methodName = "CLFlowTask";
      humantasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      String sessionId = null;
      String responseCode = null;
      String responseMessage = null;
      HashMap hashMap = new HashMap();
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] wiObjects = null;
      String workItemCaseStatus = "Completed";
      String nextWorkItemCaseStatus = "New";
      boolean isInstanceCompleted = false;
      WorkItemObject[] completedWorkItem = null;
      HashMap hm = new HashMap();
      ResponseObject associatedresObj = null;
      boolean isQueriedCL = false;
      DBUtility db = new DBUtility(SBM_HOME);
      UtilClass uc = new UtilClass(SBM_HOME);
      String hasOpenTask = "No";
      String hasEFTOpenTask = "No";
      String needQCTask = "Yes";
      String isCLApproverAccepted = "";
      String pmtHardStop = "";
      String needsSAPPR = "Yes";
      if (reqObj == null) {
         responseCode = "5070";
         responseMessage = "REQUEST_OBJECT_IS_NULL";
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         humantasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         humantasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");
         String workItemName = (String)hashMap.get("WORKITEMNAME");
         String isQueried = (String)hashMap.get("ISQUERIED");
         String transactionId = (String)hashMap.get("TRANSACTIONID");
         String transactionType = (String)hashMap.get("TRANSACTIONTYPE");
         String EFTReissue = (String)hashMap.get("EFTREISSUE");
         String parentWIName = (String)hashMap.get("PARENTWINAME");
         String parentPIName = (String)hashMap.get("PARENTPINAME");
         if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
            workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
         }

         try {
            BLServer blserver = null;
            Session blsession = null;
            Session blsessionECSAdmin = null;
            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("InwardDEO") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && uc.checkNull(hashMap.get("CLINWARDNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: CLINWARDNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("Executive") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && !workItemCaseStatus.equals("SendBack") && uc.checkNull(hashMap.get("CLNO"))) {
               humantasklog.info("ECS Savvion " + methodName + " Service:::: CLNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
               if (userId != null && !userId.equals("")) {
                  if (responseCode == null) {
                     if (isQueried != null && isQueried.equals("Yes") && parentWIName != null && !parentWIName.equals("") && parentPIName != null && !parentPIName.equals("")) {
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: swaping workitem names");
                        workItemName = workItemName + parentWIName;
                        parentWIName = workItemName.substring(0, workItemName.length() - parentWIName.length());
                        workItemName = workItemName.substring(parentWIName.length(), workItemName.length());
                        processInstanceName = processInstanceName + parentPIName;
                        parentPIName = processInstanceName.substring(0, processInstanceName.length() - parentPIName.length());
                        processInstanceName = processInstanceName.substring(parentPIName.length(), processInstanceName.length());
                        new ResponseObject();
                        associatedresObj = uc.completeAssociatedInstance(userId, parentWIName, parentPIName);
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
                     }

                     if (associatedresObj == null || associatedresObj.getResponseCode() == "5000") {
                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query") || workItemCaseStatus.equals("Query")) {
                           if (workItemName.contains("Query")) {
                              workItemCaseStatus = "Closed";
                           }

                           humantasklog.info("ECS Savvion " + methodName + " setting isQueriedCL flag as true");
                           hm.put("isQueriedCL", true);
                        }

                        humantasklog.info("ECS Savvion " + methodName + " Service:::: getting sessionid for user to set the dataslots");
                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                        humantasklog.info("ECS Savvion " + methodName + " Service:::: updating dataslot values ");
                        if (!uc.checkNull(hashMap.get("CLNO"))) {
                           hm.put("CLNo", hashMap.get("CLNO").toString());
                        }

                        hm.put("HasOpenTask", hasOpenTask);
                        if (workItemName != null && workItemName.contains("CLPrePost")) {
                           hm.put("HasCLPrePostAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("ALDNF")) {
                           hm.put("HasALDNFAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("TagMaker")) {
                           hm.put("HasCLTagMakerAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("TagChecker")) {
                           hm.put("HasCLTagCheckerAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("InwardDEO")) {
                           humantasklog.info("workItemCaseStatus when CLInwardDEO submits task " + workItemCaseStatus);
                           hm.put("isCLInwardDEOAccepted", workItemCaseStatus);
                           if (!uc.checkNull(hashMap.get("CLINWARDNO"))) {
                              hm.put("CLInwardNo", hashMap.get("CLINWARDNO").toString());
                           }

                           if (!uc.checkNull(hashMap.get("CLCASETYPE"))) {
                              hm.put("CLCaseType", hashMap.get("CLCASETYPE").toString());
                           }
                        }

                        if (workItemName != null && workItemName.contains("Executive")) {
                           hm.put("HasCLExecutiveAccepted", workItemCaseStatus);
                           if (!uc.checkNull(hashMap.get("CLNO"))) {
                              hm.put("CLNo", hashMap.get("CLNO").toString());
                           }
                        }

                        if (workItemName != null && workItemName.contains("Editor")) {
                           hm.put("HasCLEditorAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("CLApprover")) {
                           hm.put("HasCLApproverAccepted", workItemCaseStatus);
                           if (workItemCaseStatus.equals("Approve")) {
                              humantasklog.info("ECS Savvion " + methodName + " resetting isQueriedCL flag as false");
                              hm.put("isQueriedCL", false);
                              isCLApproverAccepted = workItemCaseStatus;
                           }
                        }

                        if (workItemName != null && workItemName.contains("EFTChecker")) {
                           hm.put("HasEFTCheckerAccepted", workItemCaseStatus);
                           hm.put("EFTReissue", EFTReissue);
                        }

                        if (workItemName != null && workItemName.contains("AmountApprover")) {
                           hm.put("HasCLAmountApproverAccepted", workItemCaseStatus);
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

                        if (workItemName != null && workItemName.contains("QualityChecker")) {
                           hm.put("HasQualityCheckerAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("PaymentRequest")) {
                           hm.put("HasPaymentRequesterAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("CLAccounts")) {
                           hm.put("HasCLAccountsAccepted", workItemCaseStatus);
                        }

                        if (workItemName != null && workItemName.contains("AllowanceAccounts")) {
                           hm.put("HasCLAllowanceAccountsAccepted", workItemCaseStatus);
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSEFTCHECK"))) {
                           hm.put("NeedEFTCheck", hashMap.get("NEEDSEFTCHECK").toString());
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSAMOUNTAPPROVAL"))) {
                           hm.put("NeedAmountApproval", hashMap.get("NEEDSAMOUNTAPPROVAL").toString());
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSVERTICALHEAD"))) {
                           hm.put("NeedsVerticalHead", hashMap.get("NEEDSVERTICALHEAD").toString());
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSVHLOOP"))) {
                           hm.put("NeedsVHLoop", hashMap.get("NEEDSVHLOOP").toString());
                        }

                        if (!uc.checkNull(hashMap.get("PMTHARDSTOP"))) {
                           hm.put("PMTHardStop", hashMap.get("PMTHARDSTOP").toString());
                           if (workItemName.contains("PMTRevertQueue") && hashMap.get("PMTHARDSTOP").toString().equals("Yes")) {
                              hm.put("ApproverCaseStatus", "PMTQueue");
                           }
                        }

                        if (!uc.checkNull(hashMap.get("ISCLREJECTED"))) {
                           hm.put("IsCLRejected", hashMap.get("ISCLREJECTED").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HASPMTHOLD"))) {
                           hm.put("HasPMTHold", hashMap.get("HASPMTHOLD").toString());
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
                           hm.put("NeedInvestigation", hashMap.get("NEEDSINVESTIGATION").toString());
                           if (hashMap.get("NEEDSINVESTIGATION").toString().equals("Yes")) {
                              hasOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask ???????????? " + hasOpenTask);
                           }
                        } else {
                           hm.put("NeedInvestigation", "No");
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSPMT"))) {
                           hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
                           if (hashMap.get("NEEDSPMT").toString().equals("Yes")) {
                              hasOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              humantasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask ???????????? " + hasOpenTask);
                           }
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSTOFAX"))) {
                           hm.put("NeedToFax", hashMap.get("NEEDSTOFAX").toString());
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSTOPRINT"))) {
                           hm.put("NeedToPrint", hashMap.get("NEEDSTOPRINT").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HASALLOWANCE"))) {
                           hm.put("HasAllowance", hashMap.get("HASALLOWANCE").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HASACCOUNTS"))) {
                           hm.put("HasAccounts", hashMap.get("HASACCOUNTS").toString());
                        }

                        String[] workItemNames = new String[10];
                        workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                        List<String> list = new ArrayList();
                        Collections.addAll(list, workItemNames);
                        list.removeAll(Collections.singleton((Object)null));
                        workItemNames = (String[])list.toArray(new String[list.size()]);
                        humantasklog.info("ECS savvion " + methodName + " Service :: available workItemNames array " + Arrays.toString(workItemNames));
                        String[] var42 = workItemNames;
                        int var41 = workItemNames.length;

                        String winame;
                        int var40;
                        for(var40 = 0; var40 < var41; ++var40) {
                           winame = var42[var40];
                           if (winame != null && winame != "" && (winame.contains("CLInvestigator") || winame.contains("PMT")) && !winame.equals(workItemName)) {
                              humantasklog.warn("ECS savvion " + methodName + " Service :Investigator task is available");
                              hasOpenTask = "Yes";
                              break;
                           }
                        }

                        hm.put("HasOpenTask", hasOpenTask);
                        if (workItemName.contains("PMTRevert") && !uc.checkNull(hashMap.get("PMTHARDSTOP")) && hashMap.get("PMTHARDSTOP").toString().equals("Yes")) {
                           pmtHardStop = "Yes";
                        }

                        var42 = workItemNames;
                        var41 = workItemNames.length;

                        for(var40 = 0; var40 < var41; ++var40) {
                           winame = var42[var40];
                           if (winame != null && winame != "" && winame.contains("CLEFTChecker") && workItemName != null && !workItemName.contains("EFTChecker")) {
                              humantasklog.warn("ECS savvion " + methodName + " Service :CLEFTChecker task is available");
                              hasEFTOpenTask = "Yes";
                              break;
                           }
                        }

                        hm.put("HasEFTOpenTask", hasEFTOpenTask);
                        var42 = workItemNames;
                        var41 = workItemNames.length;

                        for(var40 = 0; var40 < var41; ++var40) {
                           winame = var42[var40];
                           if (winame != null && winame != "" && !winame.contains("CLInvestigator") && !winame.contains("Quality") && !winame.contains("Accounts") && !winame.contains("Payment") && !winame.contains("Dummy") && !winame.equals(workItemName) && winame.contains("PMT") && pmtHardStop != null && pmtHardStop.equals("Yes")) {
                              needQCTask = "No";
                              break;
                           }
                        }

                        humantasklog.warn("ECS savvion " + methodName + " Service :workItemNames available are to set NeedQCTask " + Arrays.toString(workItemNames) + " and NeedQCTask is " + needQCTask);
                        hm.put("NeedQCTask", needQCTask);
                        var42 = workItemNames;
                        var41 = workItemNames.length;

                        for(var40 = 0; var40 < var41; ++var40) {
                           winame = var42[var40];
                           if (winame != null && winame != "" && winame.contains("CLPaymentRequestor") && !winame.equals(workItemName)) {
                              humantasklog.warn("ECS savvion " + methodName + " Service :CLPaymentRequestor task is available, so needsSAPPR will be set to No");
                              needsSAPPR = "No";
                              break;
                           }
                        }

                        hm.put("NeedsSAPPR", needsSAPPR);
                        if (blsession == null) {
                           responseCode = "5030";
                           responseMessage = "USER_ID_INVALID";
                        } else {
                           pi.updateSlotValue(hm);
                           pi.save();
                           blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                           String SRType = (String)pi.getDataSlotValue("SRType");
                           if (EFTReissue == null || EFTReissue.equals("")) {
                              EFTReissue = (String)pi.getDataSlotValue("EFTReissue");
                           }

                           humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName ::: " + workItemName + " :: Approver Decision is " + workItemCaseStatus + " :: SRType is " + SRType + " :: EFTReissue is " + EFTReissue);
                           if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp") || workItemName.contains("VerticalHead")) && workItemCaseStatus.equals("Reject") || workItemName.contains("EFTChecker") && workItemCaseStatus.equals("SendBack") && EFTReissue != null && EFTReissue.equals("No") || workItemName.contains("CLApprover") && (workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack") || workItemCaseStatus.equals("Reject"))) {
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
                              completedWorkItem = uc.completeMultipleTask(processInstanceName, workItemName, blserver, blsession, workItemCaseStatus);
                              sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                              uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                           }

                           String ApproverCaseStatus;
                           int x;
                           int var46;
                           String[] var47;
                           if (workItemName.contains("EFTChecker") && workItemCaseStatus.equals("SendBack") && EFTReissue != null && EFTReissue.equals("Yes")) {
                              ArrayList workItemList = new ArrayList();
                              new WorkItemObject();
                              String[] nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames size:" + nextAvailableWorkItemNames.length);
                              if (nextAvailableWorkItemNames != null && nextAvailableWorkItemNames.length != 0) {
                                 var47 = nextAvailableWorkItemNames;
                                 var46 = nextAvailableWorkItemNames.length;

                                 for(x = 0; x < var46; ++x) {
                                    ApproverCaseStatus = var47[x];
                                    if (ApproverCaseStatus != null && ApproverCaseStatus.contains("CLInvestigator")) {
                                       WorkItemObject workitem = new WorkItemObject();
                                       workitem.setName(ApproverCaseStatus);
                                       workitem.setPiName(processInstanceName);
                                       workitem.setCaseStatus("RejectPullBack");
                                       workItemList.add(workitem);
                                       WorkItem wiInvest = blserver.getWorkItem(blsessionECSAdmin, ApproverCaseStatus);
                                       if (WorkItem.isExist(blsessionECSAdmin, wiInvest.getID()) && wiInvest.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), ApproverCaseStatus);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       humantasklog.warn("ECS CompleteTask enterred");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), ApproverCaseStatus);
                                       humantasklog.warn("ECS Task Completed");
                                    }
                                 }

                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 completedWorkItem = uc.getResultWorkitems(workItemList);
                                 sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                                 uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + completedWorkItem.length);
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                              humantasklog.warn("ECS AssignTask enterred");
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              humantasklog.warn("ECS Task Assigned to user");
                           }

                           humantasklog.warn("ECS CompleteTask enterred");
                           uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                           humantasklog.warn("ECS Task Completed");
                           responseCode = "5000";
                           responseMessage = "SUCCESS";
                           String[] workItemNamelist = new String[10];
                           workItemNamelist = db.getNextAvailableTaskNames(processInstanceName);
                           List<String> list1 = new ArrayList();
                           Collections.addAll(list1, workItemNamelist);
                           list1.removeAll(Collections.singleton((Object)null));
                           workItemNamelist = (String[])list1.toArray(new String[list1.size()]);
                           HashMap hms = new HashMap();
                           humantasklog.warn("ECS savvion " + methodName + " Service :: available workItemNames array " + Arrays.toString(workItemNamelist));
                           var47 = workItemNamelist;
                           var46 = workItemNamelist.length;

                           for(x = 0; x < var46; ++x) {
                              ApproverCaseStatus = var47[x];
                              if (ApproverCaseStatus != null && ApproverCaseStatus != "" && !ApproverCaseStatus.contains("CLInvestigator") && !ApproverCaseStatus.contains("Quality") && !ApproverCaseStatus.contains("Accounts") && !ApproverCaseStatus.contains("Payment") && !ApproverCaseStatus.contains("Dummy") && !ApproverCaseStatus.equals(workItemName) && ApproverCaseStatus.contains("PMT") && pmtHardStop != null && pmtHardStop.equals("Yes")) {
                                 needQCTask = "No";
                                 break;
                              }
                           }

                           humantasklog.warn("ECS savvion " + methodName + " Service :workItemNames available are to set NeedQCTask " + Arrays.toString(workItemNamelist) + " and NeedQCTask is " + needQCTask);

                           try {
                              hms.put("NeedQCTask", needQCTask);
                              pi.updateSlotValue(hms);
                              pi.save();
                              humantasklog.info("ECS savvion " + methodName + " Service : dataslot updated for NeedQCTask");
                           } catch (Exception var75) {
                              humantasklog.info("ECS savvion " + methodName + " Service : instance not available to set NeedQCTask dataslot");
                           }

                           int var50;
                           ArrayList workItemList;
                           WorkItemObject wiObject;
                           String[] nextAvailableWorkItemNames;
                           ArrayList list2;
                           String nextTask;
                           if ((workItemName.contains("CLApprover") || workItemName.contains("Deviator") || workItemName.contains("Amount") || workItemName.contains("Vertical")) && workItemCaseStatus.equals("Approve")) {
                              workItemList = new ArrayList();
                              new WorkItemObject();
                              nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              list2 = new ArrayList();
                              Collections.addAll(list2, nextAvailableWorkItemNames);
                              list2.removeAll(Collections.singleton((Object)null));
                              nextAvailableWorkItemNames = (String[])list2.toArray(new String[list2.size()]);
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames :" + Arrays.toString(nextAvailableWorkItemNames));
                              if (nextAvailableWorkItemNames.length != 0) {
                                 nextTask = "";

                                 try {
                                    nextTask = (String)pi.getDataSlotValue("NeedQCTask");
                                 } catch (Exception var74) {
                                    humantasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task and instance is not available");
                                 }

                                 String[] var52 = nextAvailableWorkItemNames;
                                 int var51 = nextAvailableWorkItemNames.length;

                                 for(var50 = 0; var50 < var51; ++var50) {
                                    String nextTask = var52[var50];
                                    humantasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task " + nextTask);
                                    if (nextTask != null && nextTask.contains("EFTDummy") && nextTask != null && nextTask.equals("Yes")) {
                                       wiObject = new WorkItemObject();
                                       wiObject.setName(nextTask);
                                       wiObject.setPiName(processInstanceName);
                                       wiObject.setCaseStatus("Completed");
                                       workItemList.add(wiObject);
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       humantasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       humantasklog.warn("ECS Task Completed for EFTDummy Queue task");
                                    }
                                 }

                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           int var98;
                           int var99;
                           if (workItemName.contains("CLEFTChecker") && workItemCaseStatus.equals("Approve")) {
                              workItemList = new ArrayList();
                              new WorkItemObject();
                              nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames size:" + nextAvailableWorkItemNames.length);
                              if (nextAvailableWorkItemNames.length != 0) {
                                 String[] var102 = nextAvailableWorkItemNames;
                                 var98 = nextAvailableWorkItemNames.length;

                                 for(var99 = 0; var99 < var98; ++var99) {
                                    String nextTask = var102[var99];
                                    if (nextTask != null && (nextTask.contains("EFTDummy") && needQCTask.equals("Yes") || nextTask.contains("ApproverDummy"))) {
                                       wiObject = new WorkItemObject();
                                       wiObject.setName(nextTask);
                                       wiObject.setPiName(processInstanceName);
                                       wiObject.setCaseStatus("Completed");
                                       workItemList.add(wiObject);
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       humantasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       humantasklog.warn("ECS Task Completed for EFTDummy Queue task");
                                    }
                                 }

                                 humantasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           int var96;
                           if ((workItemName.contains("CLInvestigator") || workItemName.contains("PMTRevert")) && hasOpenTask.equals("No")) {
                              String[] nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              List<String> list2 = new ArrayList();
                              Collections.addAll(list2, nextAvailableWorkItemNames);
                              list2.removeAll(Collections.singleton((Object)null));
                              nextAvailableWorkItemNames = (String[])list2.toArray(new String[list2.size()]);
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: inside SRQueue task check nextAvailableWorkItemNames Arrays is :" + Arrays.toString(nextAvailableWorkItemNames));
                              if (nextAvailableWorkItemNames.length != 0 && nextAvailableWorkItemNames != null) {
                                 String[] var101 = nextAvailableWorkItemNames;
                                 var99 = nextAvailableWorkItemNames.length;

                                 for(var96 = 0; var96 < var99; ++var96) {
                                    String nextTask = var101[var96];
                                    if (nextTask != null && nextTask.contains("SRQueue")) {
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       humantasklog.warn("ECS CompleteTask enterred for SRQueue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       humantasklog.warn("ECS Task Completed for SRQueue task");
                                    }
                                 }

                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           if (workItemName.contains("PMTRevert")) {
                              workItemList = new ArrayList();
                              new WorkItemObject();
                              nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              list2 = new ArrayList();
                              Collections.addAll(list2, nextAvailableWorkItemNames);
                              list2.removeAll(Collections.singleton((Object)null));
                              nextAvailableWorkItemNames = (String[])list2.toArray(new String[list2.size()]);
                              humantasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames Arrays is :" + Arrays.toString(nextAvailableWorkItemNames));
                              if (nextAvailableWorkItemNames.length != 0 && nextAvailableWorkItemNames != null) {
                                 String[] var106 = nextAvailableWorkItemNames;
                                 var50 = nextAvailableWorkItemNames.length;

                                 for(var98 = 0; var98 < var50; ++var98) {
                                    nextTask = var106[var98];
                                    if (nextTask != null && (nextTask.contains("ApproverHold") || nextTask.contains("EditorHoldQueue"))) {
                                       if (nextTask != null && nextTask.contains("EditorHoldQueue")) {
                                          wiObject = new WorkItemObject();
                                          wiObject.setName(nextTask);
                                          wiObject.setPiName(processInstanceName);
                                          wiObject.setCaseStatus("Completed");
                                          workItemList.add(wiObject);
                                       }

                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          humantasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          humantasklog.warn("ECS Task Assigned to user");
                                       }

                                       humantasklog.warn("ECS CompleteTask enterred for " + nextTask);
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       humantasklog.warn("ECS Task Completed ");
                                       if (workItemList.size() != 0) {
                                          humantasklog.warn("ECS Service workItemList size " + workItemList.size());
                                          completedWorkItem = uc.getResultWorkitems(workItemList);
                                       }
                                    }
                                 }
                              }
                           }

                           nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                           if (workItemCaseStatus.equals("Approve") || workItemCaseStatus.equals("Completed") || workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack") && !isCLApproverAccepted.equals("Approve")) {
                              try {
                                 isQueriedCL = (Boolean)pi.getDataSlotValue("isQueriedCL");
                                 if (isQueriedCL && nextWorkItemCaseStatus.equals("New")) {
                                    nextWorkItemCaseStatus = "Queried";
                                 }
                              } catch (Exception var73) {
                                 humantasklog.warn("ECS savvion " + methodName + " Service :isQueriedCL is " + isQueriedCL + " and instance is closed.");
                              }
                           }

                           ApproverCaseStatus = "";

                           try {
                              ApproverCaseStatus = (String)pi.getDataSlotValue("ApproverCaseStatus");
                           } catch (Exception var72) {
                              humantasklog.warn("ECS Instance is not available to get ApproverCaseStatus");
                           }

                           if (workItemName.contains("EditorHold") || workItemName.contains("PMTRevert") && pmtHardStop.equals("Yes") || workItemName.contains("Editor") && ApproverCaseStatus != null && ApproverCaseStatus.equals("PMTQueue")) {
                              nextWorkItemCaseStatus = "PMTQueue";
                           }

                           if (workItemName.contains("Accounts") && workItemCaseStatus.equals("Reject")) {
                              nextWorkItemCaseStatus = "SAPReject";
                           }

                           if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator") || workItemName.contains("VerticalHead")) {
                              try {
                                 Thread.sleep(3000L);
                              } catch (InterruptedException var71) {
                                 var71.printStackTrace();
                              }
                           }

                           try {
                              needsSAPPR = (String)pi.getDataSlotValue("NeedsSAPPR");
                           } catch (Exception var70) {
                              humantasklog.info("ECS Savvion: " + methodName + " instance is not available to get NeedsSAPPR dataslot value");
                           }

                           wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)hashMap.get("PMTHARDSTOP"), needsSAPPR, (String)hashMap.get("HASALLOWANCE"), (String)hashMap.get("HASACCOUNTS"), (String)null);
                           humantasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
                           if (wiObjects != null && wiObjects.length != 0) {
                              uc.checkUserNumbersInGroup(wiObjects);
                           }

                           if (wiObjects != null && wiObjects.length != 0) {
                              for(x = 0; x < wiObjects.length; ++x) {
                                 if (wiObjects[x].getName() != null && wiObjects[x].getName().contains("PMTRevert")) {
                                    RequestObject[] reqsObj = new RequestObject[]{new RequestObject(), null};
                                    reqsObj[0].setKey("WORKITEMNAME");
                                    reqsObj[0].setValue(wiObjects[x].getName());
                                    reqsObj[1] = new RequestObject();
                                    reqsObj[1].setKey("TOBEASSIGNEDUSERID");
                                    reqsObj[1].setValue(userId);
                                    humantasklog.info("ECS Savvion: " + methodName + " ::Creating request Object for assignOrKeepTask method for PMTRevert queue task " + Arrays.toString(reqsObj));
                                    ResponseObject respObj = uc.assignOrKeepTask(reqsObj);
                                    humantasklog.info("ECS Savvion: " + methodName + " :: Assigned task to user ::  " + userId + " and response id  " + respObj.getResponseMessage());
                                    if (respObj.getResponseCode().equals("5000")) {
                                       humantasklog.info("ECS Savvion: " + methodName + " :: Setting CaseStatus as Locked for PMTRevert Queue Task");
                                       wiObjects[x].setCaseStatus("Locked");
                                       wiObjects[x].setPerformer(userId);
                                    }
                                 }
                              }
                           }

                           if (wiObjects != null && isQueriedCL) {
                              WorkItemObject[] var103 = wiObjects;
                              var96 = wiObjects.length;

                              for(var46 = 0; var46 < var96; ++var46) {
                                 wiObject = var103[var46];
                                 if (wiObject.getName().contains("EFTChecker") && nextWorkItemCaseStatus.equals("Queried")) {
                                    wiObject.setCaseStatus("New");
                                 }
                              }
                           }

                           if (workItemName.contains("Query") && wiObjects != null) {
                              wiObjects[0].setCaseStatus("Queried");
                              wiObjects[0].setTransactionId(transactionId);
                              wiObjects[0].setTransactionType(transactionType);
                           }

                           try {
                              pi = blserver.getProcessInstance(blsession, processInstanceName);
                              humantasklog.warn("ECS processInstance " + pi);
                           } catch (Exception var69) {
                              isInstanceCompleted = true;
                           }

                           humantasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
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

            blserver = null;
         } catch (Exception var76) {
            if (!isInstanceCompleted) {
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
            }

            var76.printStackTrace();
            humantasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var76.getMessage());
         } finally {
            try {
               uc.disconnect(sessionId);
            } catch (Exception var68) {
               var68.printStackTrace();
               humantasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var68.getMessage());
            }

         }

         if (parentPIName != null && !parentPIName.equals("") && parentWIName != null && !parentWIName.equals("") && associatedresObj.getResponseCode() == "5000" && responseCode == "5000") {
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
      }

      humantasklog.info("ECS Savvion: " + methodName + " Service::::END for " + (String)hashMap.get("WORKITEMNAME") + " with response code " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }
}
