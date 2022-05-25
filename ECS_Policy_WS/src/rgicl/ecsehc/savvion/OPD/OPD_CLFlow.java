package rgicl.ecsehc.savvion.OPD;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
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

public class OPD_CLFlow {
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

   public OPD_CLFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteOPDCLFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteOPDCLFlowTask";
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
      WorkItemObject[] completedWorkItem = null;
      HashMap hm = new HashMap();
      ResponseObject associatedresObj = null;
      boolean isQueriedCL = false;
      DBUtility db = new DBUtility(SBM_HOME);
      UtilClass uc = new UtilClass(SBM_HOME);
      String[] resultArray = new String[6];
      String associatedPIName = "";
      String associatedWIName = "";
      String hasOpenTask = "No";
      String hasInvOpenTask = "No";
      String hasEFTOpenTask = "No";
      String needQCTask = "Yes";
      String isCLApproverAccepted = "";
      String pmtHardStop = "";
      String needsSAPPR = "Yes";
      String hasPMTHold = "";
      String HasPMTOpenTask = "";
      String needsHardCopy = "";
      String hasApproverHoldTask = "";
      String approverAfterInv = "Yes";
      String isExistingApprover = "No";
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
         String parentALInwardNo = (String)hashMap.get("PARENTALINWARDNO");
         String parentCLInwardNo = (String)hashMap.get("PARENTCLINWARDNO");
         String isQueried = (String)hashMap.get("ISQUERIED");
         String transactionId = (String)hashMap.get("TRANSACTIONID");
         String transactionType = (String)hashMap.get("TRANSACTIONTYPE");
         String EFTReissue = (String)hashMap.get("EFTREISSUE");
         String hardCopyReceived = (String)hashMap.get("HARDCOPYRECEIVED");
         if (!uc.checkNull(hashMap.get("APPROVERDECISION"))) {
            workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
         }

         automatictasklog.info("parentCLInwardNo : " + parentCLInwardNo + "hardCopyReceived: " + hardCopyReceived);

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
                     ResponseObject var66 = resObj;
                     return var66;
                  }
               }
            } catch (Exception var91) {
               automatictasklog.error("Error in getting Already completed task details ", var91);
            }

            blserver = BLClientUtil.getBizLogicServer();
            blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
            if (workItemName != null && workItemCaseStatus != null && workItemName.contains("InwardDEO") && !workItemCaseStatus.equals("Closed") && !workItemCaseStatus.equals("Terminate") && uc.checkNull(hashMap.get("CLINWARDNO"))) {
               automatictasklog.info("ECS Savvion " + methodName + " Service:::: CLINWARDNO is null or empty");
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }

            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
               if (userId != null && !userId.equals("")) {
                  if (responseCode == null) {
                     ProcessInstance pi;
                     if (isQueried != null && isQueried.equals("Yes") && parentCLInwardNo != null && !parentCLInwardNo.equals("")) {
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
                        resultArray = db.getQueryQueueTaskName(parentALInwardNo, parentCLInwardNo, processInstanceName);
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: resultArray is " + Arrays.toString(resultArray));
                        associatedPIName = resultArray[0];
                        associatedWIName = resultArray[1];
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: swaping workitem names for Query Response");
                        workItemName = workItemName + associatedWIName;
                        associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
                        workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
                        processInstanceName = processInstanceName + associatedPIName;
                        associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
                        processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
                        new ResponseObject();
                        associatedresObj = uc.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: Closed the associated instance and response is " + associatedresObj.getResponseCode());
                     } else if (hardCopyReceived != null && hardCopyReceived.equals("Yes") && parentCLInwardNo != null && !parentCLInwardNo.equals("")) {
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closing HardCopy Inward instance");
                        resultArray = db.getEditorHoldQueueTaskName(parentCLInwardNo, processInstanceName);
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: resultArray is " + Arrays.toString(resultArray));
                        if (resultArray != null && resultArray.length != 0) {
                           hasPMTHold = resultArray[2];
                           pmtHardStop = resultArray[3];
                           HasPMTOpenTask = resultArray[4];
                           hasInvOpenTask = resultArray[5];
                           pi = blserver.getProcessInstance(blsession, resultArray[0]);
                           hm.put("HardCopyReceived", hardCopyReceived);
                           needsHardCopy = "No";
                           hm.put("NeedsHardCopy", needsHardCopy);
                           pi.updateSlotValue(hm);
                           pi.save();
                           if ((hasPMTHold == null || hasPMTHold.equals("No")) && (HasPMTOpenTask == null || HasPMTOpenTask.equals("No")) && (hasInvOpenTask == null || hasInvOpenTask.equals("No"))) {
                              automatictasklog.info("ECS Savvion " + methodName + " Service:::: swaping workitem names");
                              associatedPIName = resultArray[0];
                              associatedWIName = resultArray[1];
                              workItemName = workItemName + associatedWIName;
                              associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
                              workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
                              processInstanceName = processInstanceName + associatedPIName;
                              associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
                              processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
                              new ResponseObject();
                              associatedresObj = uc.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
                              automatictasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
                           }
                        } else {
                           automatictasklog.info("ECS Savvion " + methodName + " Service:::: No EditorHoldQ task in ParentCL to close");
                        }
                     }

                     if (associatedresObj != null && associatedresObj.getResponseCode() != "5000") {
                        responseCode = "5060";
                        responseMessage = "WINAME_NOT_FOUND";
                     } else {
                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query") || workItemCaseStatus.equals("Query")) {
                           if (workItemName.contains("Query")) {
                              workItemCaseStatus = "Closed";
                           }

                           automatictasklog.info("ECS Savvion " + methodName + " setting isQueriedCL flag as true");
                           hm.put("isQueriedCL", true);
                        }

                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: getting sessionid for user to set the dataslots");
                        pi = blserver.getProcessInstance(blsession, processInstanceName);
                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: updating dataslot values ");
                        if (!uc.checkNull(hashMap.get("CLINWARDNO"))) {
                           hm.put("CLInwardNo", hashMap.get("CLINWARDNO").toString());
                        }

                        if (!uc.checkNull(hashMap.get("CLNO"))) {
                           hm.put("CLNo", hashMap.get("CLNO").toString());
                        }

                        hm.put("HasOpenTask", hasOpenTask);
                        if (!uc.checkNull(hashMap.get("CLCASETYPE"))) {
                           hm.put("CLCaseType", hashMap.get("CLCASETYPE").toString());
                        }

                        if (workItemName != null && workItemName.contains("EditorHold")) {
                           hm.put("HasPMTHold", "No");
                        }

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
                           automatictasklog.info("workItemCaseStatus when CLInwardDEO submits task " + workItemCaseStatus);
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
                              automatictasklog.info("ECS Savvion " + methodName + " resetting isQueriedCL flag as false");
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
                              automatictasklog.info("%%%%%%%%%%%% ApproverCaseStatus in PMTHARDSTOP %%%%%%%%%%%%%% PMTQueue");
                           }
                        }

                        if (!uc.checkNull(hashMap.get("ISCLREJECTED"))) {
                           hm.put("IsCLRejected", hashMap.get("ISCLREJECTED").toString());
                        }

                        if (!uc.checkNull(hashMap.get("HASPMTHOLD"))) {
                           hm.put("HasPMTHold", hashMap.get("HASPMTHOLD").toString());
                           if (hashMap.get("HASPMTHOLD").toString().equals("Yes")) {
                              hm.put("ApproverCaseStatus", "PMTQueue");
                              automatictasklog.info("%%%%%%%%%%%% ApproverCaseStatus in HASPMTHOLD %%%%%%%%%%%%%% PMTQueue");
                           }
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
                              hasInvOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              hm.put("HasInvOpenTask", hasInvOpenTask);
                              automatictasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask ???????????? " + hasOpenTask + " HasInvOpenTask ???? : " + hasInvOpenTask);
                           }
                        } else {
                           hm.put("NeedInvestigation", "No");
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSPMT"))) {
                           hm.put("NeedsPMT", hashMap.get("NEEDSPMT").toString());
                           if (hashMap.get("NEEDSPMT").toString().equals("Yes")) {
                              hasOpenTask = "Yes";
                              hm.put("HasOpenTask", hasOpenTask);
                              automatictasklog.info("ECS savvion " + methodName + " Service :: hasOpenTask ???????????? " + hasOpenTask);
                           }
                        }

                        if (!uc.checkNull(hashMap.get("NEEDSHARDCOPY"))) {
                           hm.put("NeedsHardCopy", hashMap.get("NEEDSHARDCOPY").toString());
                        }

                        String[] workItemNames = new String[10];
                        workItemNames = db.getNextAvailableTaskNames(processInstanceName);
                        List<String> list = new ArrayList();
                        Collections.addAll(list, workItemNames);
                        list.removeAll(Collections.singleton((Object)null));
                        workItemNames = (String[])list.toArray(new String[list.size()]);
                        automatictasklog.info("ECS savvion " + methodName + " Service :: available workItemNames array " + Arrays.toString(workItemNames));
                        String[] var53 = workItemNames;
                        int var52 = workItemNames.length;

                        int var103;
                        for(var103 = 0; var103 < var52; ++var103) {
                           winame = var53[var103];
                           if (winame != null && winame != "" && (winame.contains("CLInvestigator") || winame.contains("PMT") || winame.contains("CLAllowanceAccounts")) && !winame.equals(workItemName)) {
                              automatictasklog.warn("ECS savvion " + methodName + " Service :Investigator task is available");
                              hasOpenTask = "Yes";
                              break;
                           }

                           if (winame != null && winame != "" && winame.contains("CLApprover")) {
                              isExistingApprover = "Yes";
                              break;
                           }
                        }

                        hm.put("HasOpenTask", hasOpenTask);
                        var53 = workItemNames;
                        var52 = workItemNames.length;

                        for(var103 = 0; var103 < var52; ++var103) {
                           winame = var53[var103];
                           if (winame != null && winame != "" && winame.contains("CLInvestigator")) {
                              automatictasklog.warn("ECS savvion " + methodName + " Service :has Investigator open task");
                              hasInvOpenTask = "Yes";
                              break;
                           }
                        }

                        hm.put("HasInvOpenTask", hasInvOpenTask);
                        if (workItemName.contains("PMTRevert") && !uc.checkNull(hashMap.get("PMTHARDSTOP")) && hashMap.get("PMTHARDSTOP").toString().equals("Yes")) {
                           pmtHardStop = "Yes";
                        }

                        var53 = workItemNames;
                        var52 = workItemNames.length;

                        for(var103 = 0; var103 < var52; ++var103) {
                           winame = var53[var103];
                           if (winame != null && winame != "" && winame.contains("CLEFTChecker") && workItemName != null && !workItemName.contains("EFTChecker")) {
                              automatictasklog.warn("ECS savvion " + methodName + " Service :CLEFTChecker task is available");
                              hasEFTOpenTask = "Yes";
                              break;
                           }
                        }

                        hm.put("HasEFTOpenTask", hasEFTOpenTask);
                        var53 = workItemNames;
                        var52 = workItemNames.length;

                        for(var103 = 0; var103 < var52; ++var103) {
                           winame = var53[var103];
                           if (winame != null && winame != "" && !winame.contains("CLInvestigator") && !winame.contains("Quality") && !winame.contains("Accounts") && !winame.contains("Payment") && !winame.contains("Dummy") && !winame.equals(workItemName) && winame.contains("PMT") && pmtHardStop != null && pmtHardStop.equals("Yes")) {
                              needQCTask = "No";
                              break;
                           }
                        }

                        automatictasklog.warn("ECS savvion " + methodName + " Service :workItemNames available are to set NeedQCTask " + Arrays.toString(workItemNames) + " and NeedQCTask is " + needQCTask);
                        hm.put("NeedQCTask", needQCTask);
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

                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName ::: " + workItemName + " :: Approver Decision is " + workItemCaseStatus + " :: SRType is " + SRType + " :: EFTReissue is " + EFTReissue);
                           if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp") || workItemName.contains("VerticalHead")) && workItemCaseStatus.equals("Reject") || workItemName.contains("EFTChecker") && workItemCaseStatus.equals("SendBack") && EFTReissue != null && EFTReissue.equals("No") || workItemName.contains("CLApprover") && (workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack") || workItemCaseStatus.equals("Reject"))) {
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
                              completedWorkItem = uc.completeMultipleTask(processInstanceName, workItemName, blserver, blsession, workItemCaseStatus);
                              sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                              uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                           }

                           String ApproverCaseStatus;
                           int x;
                           int var57;
                           String[] var58;
                           if (workItemName.contains("EFTChecker") && workItemCaseStatus.equals("SendBack") && EFTReissue != null && EFTReissue.equals("Yes")) {
                              ArrayList workItemList = new ArrayList();
                              new WorkItemObject();
                              String[] nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames size:" + nextAvailableWorkItemNames.length);
                              if (nextAvailableWorkItemNames != null && nextAvailableWorkItemNames.length != 0) {
                                 var58 = nextAvailableWorkItemNames;
                                 var57 = nextAvailableWorkItemNames.length;

                                 for(x = 0; x < var57; ++x) {
                                    ApproverCaseStatus = var58[x];
                                    if (ApproverCaseStatus != null && ApproverCaseStatus.contains("CLInvestigator")) {
                                       WorkItemObject workitem = new WorkItemObject();
                                       workitem.setName(ApproverCaseStatus);
                                       workitem.setPiName(processInstanceName);
                                       workitem.setCaseStatus("RejectPullBack");
                                       workItemList.add(workitem);
                                       WorkItem wiInvest = blserver.getWorkItem(blsessionECSAdmin, ApproverCaseStatus);
                                       if (WorkItem.isExist(blsessionECSAdmin, wiInvest.getID()) && wiInvest.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), ApproverCaseStatus);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), ApproverCaseStatus);
                                       automatictasklog.warn("ECS Task Completed");
                                    }
                                 }

                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 completedWorkItem = uc.getResultWorkitems(workItemList);
                                 sessionId = uc.connect(userId, uc.getUserPasswordECS(userId));
                                 uc.updateDataSlotValues(completedWorkItem, sessionId, processInstanceName);
                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + completedWorkItem.length);
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           pi = blserver.getProcessInstance(blsession, processInstanceName);
                           needsHardCopy = (String)pi.getDataSlotValue("NeedsHardCopy");
                           automatictasklog.info("ECS Savvion: NeedsHardCopy is " + needsHardCopy);
                           HasPMTOpenTask = (String)pi.getDataSlotValue("HasPMTOpenTask");
                           pmtHardStop = (String)pi.getDataSlotValue("PMTHardStop");
                           hardCopyReceived = (String)pi.getDataSlotValue("HardCopyReceived");
                           hasApproverHoldTask = (String)pi.getDataSlotValue("HasApproverHoldTask");
                           if (workItemName.contains("CLInvestigator")) {
                              HashMap hmp = new HashMap();
                              String[] var117 = workItemNames;
                              int var115 = workItemNames.length;

                              for(int var110 = 0; var110 < var115; ++var110) {
                                 String winame = var117[var110];
                                 if (winame != null && winame != "" && (winame.contains("Query") || winame.contains("Executive") || winame.contains("Editor") || winame.contains("CLApprover") || winame.contains("PMT") && pmtHardStop != null && pmtHardStop.equals("Yes") || winame.contains("ApproverHold") || hasApproverHoldTask.equals("Yes") || winame.contains("Deviator") || winame.contains("Amount") || winame.contains("Vertical") || winame.contains("Payment") || winame.contains("Quality") || winame.contains("Accounts") || winame.contains("ApproverDummy") || winame.contains("EFTDummy") || winame.contains("SRQueue"))) {
                                    automatictasklog.warn("ECS savvion " + methodName + " Service :No need to open the flow at Approver's revised Q");
                                    approverAfterInv = "No";
                                    break;
                                 }
                              }

                              automatictasklog.warn("ECS savvion " + methodName + " ApproverAfterInv is " + approverAfterInv);
                              hmp.put("ApproverAfterInv", approverAfterInv);
                              pi.updateSlotValue(hmp);
                              pi.save();
                           }

                           if (workItemName.contains("EditorHoldQueue") && (HasPMTOpenTask == null || HasPMTOpenTask.equals("No")) && (needsHardCopy == null || needsHardCopy.equals("No")) || !workItemName.contains("EditorHoldQueue")) {
                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
                                 automatictasklog.warn("ECS AssignTask enterred");
                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
                                 automatictasklog.warn("ECS Task Assigned to user");
                              }

                              automatictasklog.warn("ECS CompleteTask enterred");
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
                              automatictasklog.warn("ECS Task Completed");
                           }

                           responseCode = "5000";
                           responseMessage = "SUCCESS";
                           String[] workItemNamelist = new String[10];
                           workItemNamelist = db.getNextAvailableTaskNames(processInstanceName);
                           List<String> list1 = new ArrayList();
                           Collections.addAll(list1, workItemNamelist);
                           list1.removeAll(Collections.singleton((Object)null));
                           workItemNamelist = (String[])list1.toArray(new String[list1.size()]);
                           HashMap hms = new HashMap();
                           automatictasklog.warn("ECS savvion " + methodName + " Service :: available workItemNames array " + Arrays.toString(workItemNamelist));
                           var58 = workItemNamelist;
                           var57 = workItemNamelist.length;

                           for(x = 0; x < var57; ++x) {
                              ApproverCaseStatus = var58[x];
                              if (ApproverCaseStatus != null && ApproverCaseStatus != "" && !ApproverCaseStatus.contains("CLInvestigator") && !ApproverCaseStatus.contains("Quality") && !ApproverCaseStatus.contains("Accounts") && !ApproverCaseStatus.contains("Payment") && !ApproverCaseStatus.contains("Dummy") && !ApproverCaseStatus.equals(workItemName) && ApproverCaseStatus.contains("PMT") && pmtHardStop != null && pmtHardStop.equals("Yes")) {
                                 needQCTask = "No";
                                 break;
                              }
                           }

                           automatictasklog.warn("ECS savvion " + methodName + " Service :workItemNames available are to set NeedQCTask " + Arrays.toString(workItemNamelist) + " and NeedQCTask is " + needQCTask);

                           try {
                              hms.put("NeedQCTask", needQCTask);
                              pi.updateSlotValue(hms);
                              pi.save();
                              automatictasklog.info("ECS savvion " + methodName + " Service : dataslot updated for NeedQCTask");
                           } catch (Exception var90) {
                              automatictasklog.info("ECS savvion " + methodName + " Service : instance not available to set NeedQCTask dataslot");
                           }

                           int var61;
                           ArrayList workItemList;
                           String[] nextAvailableWorkItemNames;
                           WorkItemObject wiObject;
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
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames :" + Arrays.toString(nextAvailableWorkItemNames));
                              if (nextAvailableWorkItemNames.length != 0) {
                                 nextTask = "";

                                 try {
                                    nextTask = (String)pi.getDataSlotValue("NeedQCTask");
                                 } catch (Exception var89) {
                                    automatictasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task and instance is not available");
                                 }

                                 String[] var63 = nextAvailableWorkItemNames;
                                 int var62 = nextAvailableWorkItemNames.length;

                                 for(var61 = 0; var61 < var62; ++var61) {
                                    String nextTask = var63[var61];
                                    automatictasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task " + nextTask);
                                    if (nextTask != null && nextTask.contains("EFTDummy") && nextTask != null && nextTask.equals("Yes")) {
                                       wiObject = new WorkItemObject();
                                       wiObject.setName(nextTask);
                                       wiObject.setPiName(processInstanceName);
                                       wiObject.setCaseStatus("Completed");
                                       workItemList.add(wiObject);
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       automatictasklog.warn("ECS Task Completed for EFTDummy Queue task");
                                    }
                                 }

                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           int var127;
                           int var128;
                           if (workItemName.contains("CLEFTChecker") && workItemCaseStatus.equals("Approve")) {
                              workItemList = new ArrayList();
                              new WorkItemObject();
                              nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames size:" + nextAvailableWorkItemNames.length);
                              if (nextAvailableWorkItemNames.length != 0) {
                                 String[] var132 = nextAvailableWorkItemNames;
                                 var127 = nextAvailableWorkItemNames.length;

                                 for(var128 = 0; var128 < var127; ++var128) {
                                    String nextTask = var132[var128];
                                    if (nextTask != null && (nextTask.contains("EFTDummy") && needQCTask.equals("Yes") || nextTask.contains("ApproverDummy"))) {
                                       wiObject = new WorkItemObject();
                                       wiObject.setName(nextTask);
                                       wiObject.setPiName(processInstanceName);
                                       wiObject.setCaseStatus("Completed");
                                       workItemList.add(wiObject);
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred for EFTDummy Queue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       automatictasklog.warn("ECS Task Completed for EFTDummy Queue task");
                                    }
                                 }

                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           int var126;
                           if (workItemName.contains("PMTRevert") && hasOpenTask.equals("No")) {
                              String[] nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              List<String> list2 = new ArrayList();
                              Collections.addAll(list2, nextAvailableWorkItemNames);
                              list2.removeAll(Collections.singleton((Object)null));
                              nextAvailableWorkItemNames = (String[])list2.toArray(new String[list2.size()]);
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: inside SRQueue task check nextAvailableWorkItemNames Arrays is :" + Arrays.toString(nextAvailableWorkItemNames));
                              if (nextAvailableWorkItemNames.length != 0 && nextAvailableWorkItemNames != null) {
                                 String[] var130 = nextAvailableWorkItemNames;
                                 var128 = nextAvailableWorkItemNames.length;

                                 for(var126 = 0; var126 < var128; ++var126) {
                                    String nextTask = var130[var126];
                                    if (nextTask != null && nextTask.contains("SRQueue")) {
                                       WorkItem win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred for SRQueue task");
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       automatictasklog.warn("ECS Task Completed for SRQueue task");
                                    }
                                 }

                                 responseCode = "5000";
                                 responseMessage = "SUCCESS";
                              }
                           }

                           String[] var136;
                           WorkItem win;
                           if (workItemName.contains("CLInvestigator")) {
                              workItemList = new ArrayList();
                              new WorkItemObject();
                              nextAvailableWorkItemNames = db.getNextAvailableTaskNames(processInstanceName);
                              list2 = new ArrayList();
                              Collections.addAll(list2, nextAvailableWorkItemNames);
                              list2.removeAll(Collections.singleton((Object)null));
                              nextAvailableWorkItemNames = (String[])list2.toArray(new String[list2.size()]);
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: getting nextAvailableWorkItemNames Arrays in closing ApproverHold in Investigation :" + Arrays.toString(nextAvailableWorkItemNames));
                              automatictasklog.info("ECS Savvion: HasPMTOpenTask is" + HasPMTOpenTask);
                              if (nextAvailableWorkItemNames.length != 0 && nextAvailableWorkItemNames != null) {
                                 var136 = nextAvailableWorkItemNames;
                                 var61 = nextAvailableWorkItemNames.length;

                                 for(var127 = 0; var127 < var61; ++var127) {
                                    nextTask = var136[var127];
                                    if (nextTask != null && (nextTask.contains("ApproverHold") && (HasPMTOpenTask == null || !HasPMTOpenTask.equals("Yes") || pmtHardStop == null || !pmtHardStop.equals("Yes")) || nextTask.contains("EditorHold") && (HasPMTOpenTask == null || HasPMTOpenTask.equals("No")) && (needsHardCopy == null || needsHardCopy.equals("No")))) {
                                       if (nextTask != null && nextTask.contains("EditorHoldQueue")) {
                                          automatictasklog.info("ECS Savvion: NeedsHardCopy is" + needsHardCopy);
                                          wiObject = new WorkItemObject();
                                          wiObject.setName(nextTask);
                                          wiObject.setPiName(processInstanceName);
                                          wiObject.setCaseStatus("Completed");
                                          workItemList.add(wiObject);
                                       }

                                       win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred in Investigator closure for " + nextTask);
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       automatictasklog.warn("ECS Task Completed ");
                                       if (workItemList.size() != 0) {
                                          automatictasklog.warn("ECS Service workItemList size " + workItemList.size());
                                          completedWorkItem = uc.getResultWorkitems(workItemList);
                                       }
                                    }
                                 }
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
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: nextAvailableWorkItemNames Arrays is :" + Arrays.toString(nextAvailableWorkItemNames));
                              automatictasklog.info("ECS Savvion: NeedsHardCopy is" + needsHardCopy);
                              if (nextAvailableWorkItemNames.length != 0 && nextAvailableWorkItemNames != null) {
                                 var136 = nextAvailableWorkItemNames;
                                 var61 = nextAvailableWorkItemNames.length;

                                 for(var127 = 0; var127 < var61; ++var127) {
                                    nextTask = var136[var127];
                                    if (nextTask != null && (nextTask.contains("ApproverHold") && (hasInvOpenTask == null || hasInvOpenTask.equals("No")) || nextTask.contains("EditorHoldQueue") && (needsHardCopy == null || needsHardCopy.equals("No")) && (hasInvOpenTask == null || hasInvOpenTask.equals("No")))) {
                                       if (nextTask != null && nextTask.contains("EditorHoldQueue")) {
                                          automatictasklog.info("ECS Savvion: NeedsHardCopy is" + needsHardCopy);
                                          wiObject = new WorkItemObject();
                                          wiObject.setName(nextTask);
                                          wiObject.setPiName(processInstanceName);
                                          wiObject.setCaseStatus("Completed");
                                          workItemList.add(wiObject);
                                       }

                                       win = blserver.getWorkItem(blsessionECSAdmin, nextTask);
                                       if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
                                          automatictasklog.warn("ECS AssignTask enterred");
                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                          automatictasklog.warn("ECS Task Assigned to user");
                                       }

                                       automatictasklog.warn("ECS CompleteTask enterred for " + nextTask);
                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), nextTask);
                                       automatictasklog.warn("ECS Task Completed ");
                                       if (workItemList.size() != 0) {
                                          automatictasklog.warn("ECS Service workItemList size " + workItemList.size());
                                          completedWorkItem = uc.getResultWorkitems(workItemList);
                                       }
                                    }
                                 }
                              }
                           }

                           nextWorkItemCaseStatus = uc.getNextTaskStatus(workItemCaseStatus);
                           if (workItemCaseStatus.equals("Approve") || workItemCaseStatus.equals("Completed") || workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("SendBack") || workItemCaseStatus.equals("Closed") && !isCLApproverAccepted.equals("Approve")) {
                              try {
                                 isQueriedCL = (Boolean)pi.getDataSlotValue("isQueriedCL");
                                 automatictasklog.warn("ECS savvion " + methodName + " Service :isQueriedCL is " + isQueriedCL + "::: nextWorkItemCaseStatus :" + nextWorkItemCaseStatus);
                                 if (isQueriedCL && (nextWorkItemCaseStatus.equals("New") || workItemCaseStatus.equals("Closed"))) {
                                    nextWorkItemCaseStatus = "Queried";
                                 }
                              } catch (Exception var92) {
                                 automatictasklog.warn("ECS savvion " + methodName + " Service :isQueriedCL is " + isQueriedCL + " and instance is closed.");
                              }
                           }

                           ApproverCaseStatus = "";

                           try {
                              ApproverCaseStatus = (String)pi.getDataSlotValue("ApproverCaseStatus");
                              automatictasklog.info("%%%%%%%%%%%% ApproverCaseStatus in DB  %%%%%%%%%%%%%% " + ApproverCaseStatus);
                           } catch (Exception var88) {
                              automatictasklog.warn("ECS Instance is not available to get ApproverCaseStatus");
                           }

                           if (workItemName.contains("EditorHold") && ApproverCaseStatus != null && ApproverCaseStatus.equals("PMTQueue") || workItemName.contains("PMTRevert") && pmtHardStop != null && pmtHardStop.equals("Yes")) {
                              nextWorkItemCaseStatus = "PMTQueue";
                           }

                           if (workItemName.contains("Accounts") && workItemCaseStatus.equals("Reject")) {
                              nextWorkItemCaseStatus = "SAPReject";
                           }

                           if (workItemName.contains("Investigator")) {
                              nextWorkItemCaseStatus = "Revised";
                           }

                           if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator") || workItemName.contains("VerticalHead")) {
                              try {
                                 Thread.sleep(3000L);
                              } catch (InterruptedException var87) {
                                 var87.printStackTrace();
                              }
                           }

                           wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null, (String)null, (String)hashMap.get("NEEDSPMT"), (String)hashMap.get("PMTHARDSTOP"), needsSAPPR, (String)null, "Yes", isExistingApprover);
                           automatictasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
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

                           if (wiObjects != null && isQueriedCL) {
                              WorkItemObject[] var134 = wiObjects;
                              var126 = wiObjects.length;

                              for(var57 = 0; var57 < var126; ++var57) {
                                 wiObject = var134[var57];
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
                              automatictasklog.warn("ECS processInstance " + pi);
                           } catch (Exception var86) {
                              isInstanceCompleted = true;
                           }

                           automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
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
         } catch (Exception var93) {
            if (!isInstanceCompleted) {
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
            }

            var93.printStackTrace();
            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var93);
         } finally {
            try {
               uc.disconnect(sessionId);
            } catch (Exception var83) {
               var83.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var83);
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
         } catch (Exception var85) {
            automatictasklog.error("ECS Savvion OPD_CLFlow: " + methodName + " :: Exception " + var85);
         }
      }

      try {
         automatictasklog.info("ECS Savvion: " + methodName + " Service::::END with response " + resObj);
      } catch (Exception var84) {
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: response " + resObj);
      automatictasklog.info("**************************ECS Savvion: " + methodName + " Service::::END ***************************");
      return resObj;
   }
}
