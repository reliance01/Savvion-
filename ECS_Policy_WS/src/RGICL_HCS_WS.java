import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.tdiinc.BizLogic.Server.PAKException;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.ALFlow.ALFlow;
import rgicl.ecsehc.savvion.AutoAdjudication.AutoAdjudicationALFlow;
import rgicl.ecsehc.savvion.AutoAdjudication.AutoAdjudicationCLFlow;
import rgicl.ecsehc.savvion.AutoAdjudication.AutoAdjudicationOPDCLFlow;
import rgicl.ecsehc.savvion.CLFlow.CLFlow;
import rgicl.ecsehc.savvion.FinanceMasterFlow.FinanceMaster;
import rgicl.ecsehc.savvion.Network_Flow.NetworkFlow;
import rgicl.ecsehc.savvion.OPD.OPD_ALFlow;
import rgicl.ecsehc.savvion.OPD.OPD_CLFlow;
import rgicl.ecsehc.savvion.ProductFlow.ProductFlow;
import rgicl.ecsehc.savvion.RRFlow.RRFlow;
import rgicl.ecsehc.savvion.SRFlow.SRFlow;
import rgicl.ecsehc.savvion.TPAFlow.ALTPAFlow;
import rgicl.ecsehc.savvion.TPAFlow.CLTPAFlow;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.UserRoleObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;
import rgicl.ecsehc.savvion.policy.util.db.DBUtility;

public class RGICL_HCS_WS {
   private static final String ptName = "RGICL_ECS_POLICY_FLOW";
   private static final String SBM_HOME = System.getProperty("sbm.home");
   private static final String ECS_SAVVION_PROPERTIES;
   private static final String ECS_SAVVION_LOG_PROPERTIES;
   private static Properties propertiesECSSavvion;
   private static Properties propertiesECSLog;
   private static Logger automatictasklog;
   private String responseCode = null;

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

   private String INITIATEFLOW(HashMap dsValues, String username, String password, String piName, String _ptName, String priority) throws AxisFault {
      UtilClass uc = new UtilClass(SBM_HOME);
      String pi = null;

      try {
         String sessionId = uc.connect(username, password);
         HashMap<String, String> dsTypeMap = new HashMap();
         dsTypeMap.put("PolicyNumber", "STRING");
         dsTypeMap.put("ProposalNumber", "STRING");
         dsTypeMap.put("Branch", "STRING");
         dsTypeMap.put("City", "STRING");
         dsTypeMap.put("PolicyName", "STRING");
         dsTypeMap.put("PolicyStatus", "STRING");
         dsTypeMap.put("StartDate", "STRING");
         dsTypeMap.put("EndDate", "STRING");
         if (_ptName.equalsIgnoreCase("RGICL_ECS_POLICY_FLOW") || _ptName.equalsIgnoreCase("RGICL_ECS_CONFIGURATION_FLOW")) {
            dsTypeMap.put("isPolicyCheckerAccepted", "STRING");
         }

         if (_ptName.equalsIgnoreCase("RGICL_ECS_ENDORSEMENT_FLOW")) {
            dsTypeMap.put("transactionType", "STRING");
            dsTypeMap.put("isServiceEndorsement", "STRING");
         }

         Hashtable resolvedDSValues = uc.getDSValues(dsTypeMap, dsValues);
         pi = uc.createProcessInstance(sessionId, _ptName, piName, priority, resolvedDSValues);
         uc.disconnect(sessionId);
      } catch (Exception var14) {
         Exception e = var14;
         if (var14.getMessage().contains("Incorrect userName/password for")) {
            try {
               String userid = username;
               if (e.getMessage().contains("ECSAdmin")) {
                  userid = "ECSAdmin";
               }

               String pass = uc.getUserPasswordECS(userid);
               PService p = PService.self();
               pass = p.encrypt(pass);
               automatictasklog.info("ECS Savvion RGICL_HCS_WS: INITIATEFLOW userId " + userid + " encrypted " + pass);
            } catch (Exception var13) {
               automatictasklog.error("ECS Savvion RGICL_HCS_WS: INITIATEFLOW :: Exception " + var13);
            }
         }
      }

      return pi;
   }

   private void POLICYCHECKER(String sessionId, String piName, String wiName) throws AxisFault {
      HashMap dsTypeMap = new HashMap();
      UtilClass uc = new UtilClass(SBM_HOME);
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = uc.getDSValues(dsTypeMap, dsValues);
      uc.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      uc.completeWorkitem(sessionId, wiName);
   }

   private void POLICYMAKER(String sessionId, String piName, String wiName) throws AxisFault {
      HashMap dsTypeMap = new HashMap();
      UtilClass uc = new UtilClass(SBM_HOME);
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = uc.getDSValues(dsTypeMap, dsValues);
      uc.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      uc.completeWorkitem(sessionId, wiName);
   }

   private void activateProcessInstance(String sessionId, String piName) throws AxisFault {
      UtilClass uc = new UtilClass(SBM_HOME);

      try {
         uc.getBizLogicManager().activateProcessInstance(sessionId, piName);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      } catch (PAKException var6) {
         throw new AxisFault("SBM Web services error :" + var6.getMessage());
      }
   }

   private String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   private String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, true);
   }

   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
      UtilClass uc = new UtilClass(SBM_HOME);
      Object obj = null;

      try {
         obj = uc.getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
      } catch (RemoteException var7) {
         throw new AxisFault("SBM Web services error :" + var7.getMessage());
      } catch (PAKException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }

      return obj instanceof String ? (String)obj : "NST";
   }

   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
      UtilClass uc = new UtilClass(SBM_HOME);
      QueryService qs = null;
      QSWorkItemRS wirs = null;

      try {
         Session sess = uc.getBizLogicManager().getSession(sessionId);
         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", "RGICL_ECS_POLICY_FLOW");
         qs = new QueryService(sess);
         QSWorkItem qswi = qs.getWorkItem();
         wifil.setFetchSize(400);
         if (available) {
            wirs = qswi.getAvailableList(wifil);
         } else {
            wirs = qswi.getAssignedList(wifil);
         }

         List wi = wirs.getSVOList();
         String[] winames = new String[wi.size()];

         for(int i = 0; i < wi.size(); ++i) {
            winames[i] = ((QSWorkItemData)wi.get(i)).getName();
         }

         String[] var13 = winames;
         return var13;
      } catch (Exception var20) {
         throw new AxisFault("SBM Web services error :" + var20.getMessage());
      } finally {
         try {
            wirs.close();
         } catch (Exception var19) {
         }

      }
   }

   public ResponseObject initiatePolicyFlowECS(RequestObject[] reqObj) throws AxisFault {
      automatictasklog.info("===================ECS Savvion: initiatePolicyFlowECS service :: STARTS ===============================");
      ResponseObject resObj = new ResponseObject();
      HashMap<String, String> hashMap = null;
      HashMap<String, String> dsValues = new HashMap();
      ArrayList<String> ls = new ArrayList();
      String policyNumber = "";
      String userId = "";
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            hashMap = uc.getMap(_reqObj);
            automatictasklog.info("ECS Savvion: initiatePolicyFlowECS Service :::: Input values : " + hashMap);
            if (hashMap != null && !hashMap.isEmpty()) {
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", Branch);
               }

               String City = (String)hashMap.get("CITY");
               if (hashMap.containsKey("CITY")) {
                  dsValues.put("City", City);
               }

               String isPolicyCheckerAccepted = (String)hashMap.get("ISPOLICYCHECKERACCEPTED");
               if (hashMap.containsKey("ISPOLICYCHECKERACCEPTED")) {
                  dsValues.put("isPolicyCheckerAccepted", isPolicyCheckerAccepted);
               }

               String PolicyName = (String)hashMap.get("POLICYNAME");
               if (hashMap.containsKey("POLICYNAME")) {
                  dsValues.put("PolicyName", PolicyName);
               }

               String StartDate = (String)hashMap.get("STARTDATE");
               if (hashMap.containsKey("STARTDATE")) {
                  dsValues.put("StartDate", StartDate);
               }

               String EndDate = (String)hashMap.get("ENDDATE");
               if (hashMap.containsKey("ENDDATE")) {
                  dsValues.put("EndDate", EndDate);
               }

               String Ageing = (String)hashMap.get("AGEING");
               if (hashMap.containsKey("AGEING")) {
                  dsValues.put("Ageing", Ageing);
               }

               String PolicyStatus = (String)hashMap.get("POLICYSTATUS");
               if (hashMap.containsKey("POLICYSTATUS")) {
                  dsValues.put("PolicyStatus", PolicyStatus);
               }
            }
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-POLICY-" + policyNumber + "-";
         String priority = propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", uc.getUserPasswordECS("ECSAdmin"), processInstanceName, "RGICL_ECS_POLICY_FLOW", priority);
               automatictasklog.info("ECS Savvion: initiatePolicyFlowECS Service :::: Created processInstanceName : " + processInstanceName);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            }
         } catch (Exception var19) {
            this.responseCode = "5050";
            automatictasklog.error("ECS Savvion: initiatePolicyFlowECS service :: Exception occured : " + var19.getMessage());
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      automatictasklog.info("==========================ECS Savvion: initiatePolicyFlowECS service :: ENDS :: responseCode ::" + this.responseCode + "=============================");
      return resObj;
   }

   public ResponseObject initiatePolicyConfigurationFlowECS(RequestObject[] reqObj) throws AxisFault {
      automatictasklog.info("==================ECS Savvion: initiatePolicyConfigurationFlowECS service :::: STARTS =================");
      ResponseObject resObj = new ResponseObject();
      HashMap<String, String> hashMap = null;
      HashMap<String, String> dsValues = new HashMap();
      ArrayList<String> ls = new ArrayList();
      UtilClass uc = new UtilClass(SBM_HOME);
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            hashMap = uc.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               automatictasklog.info("ECS Savvion: initiatePolicyConfigurationFlowECS service :: Input values : " + hashMap);
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", Branch);
               }

               String City = (String)hashMap.get("CITY");
               if (hashMap.containsKey("CITY")) {
                  dsValues.put("City", City);
               }

               String isPolicyCheckerAccepted = (String)hashMap.get("ISPOLICYCHECKERACCEPTED");
               if (hashMap.containsKey("ISPOLICYCHECKERACCEPTED")) {
                  dsValues.put("isPolicyCheckerAccepted", isPolicyCheckerAccepted);
               }

               String PolicyName = (String)hashMap.get("POLICYNAME");
               if (hashMap.containsKey("POLICYNAME")) {
                  dsValues.put("PolicyName", PolicyName);
               }

               String StartDate = (String)hashMap.get("STARTDATE");
               if (hashMap.containsKey("STARTDATE")) {
                  dsValues.put("StartDate", StartDate);
               }

               String EndDate = (String)hashMap.get("ENDDATE");
               if (hashMap.containsKey("ENDDATE")) {
                  dsValues.put("EndDate", EndDate);
               }

               String PolicyStatus = (String)hashMap.get("POLICYSTATUS");
               if (hashMap.containsKey("POLICYSTATUS")) {
                  dsValues.put("PolicyStatus", PolicyStatus);
               }
            }
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-CONFIGURATION-" + policyNumber + "-";
         String priority = propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", uc.getUserPasswordECS("ECSAdmin"), processInstanceName, "RGICL_ECS_CONFIGURATION_FLOW", priority);
               automatictasklog.info("ECS Savvion: initiatePolicyConfigurationFlowECS service :: Created Instance : " + processInstanceName);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            }
         } catch (Exception var18) {
            this.responseCode = "5050";
            automatictasklog.error("ECS Savvion: initiatePolicyConfigurationFlowECS service :: Exception occured : " + var18.getMessage());
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      automatictasklog.info("ECS Savvion: initiatePolicyConfigurationFlowECS service :: ENDS :: responseCode ::" + this.responseCode);
      return resObj;
   }

   public ResponseObject initiatePolicyEnrollmentFlowECS(RequestObject[] reqObj) throws AxisFault {
      automatictasklog.info("======================ECS Savvion: initiatePolicyEnrollmentFlowECS service :::: STARTS================");
      ResponseObject resObj = new ResponseObject();
      HashMap<String, String> hashMap = null;
      HashMap<String, String> dsValues = new HashMap();
      ArrayList<String> ls = new ArrayList();
      UtilClass uc = new UtilClass(SBM_HOME);
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            hashMap = uc.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               automatictasklog.info("ECS Savvion: initiatePolicyEnrollmentFlowECS service :: Input Values :: " + hashMap);
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", Branch);
               }

               String City = (String)hashMap.get("CITY");
               if (hashMap.containsKey("CITY")) {
                  dsValues.put("City", City);
               }

               String PolicyName = (String)hashMap.get("POLICYNAME");
               if (hashMap.containsKey("POLICYNAME")) {
                  dsValues.put("PolicyName", PolicyName);
               }

               String StartDate = (String)hashMap.get("STARTDATE");
               if (hashMap.containsKey("STARTDATE")) {
                  dsValues.put("StartDate", StartDate);
               }

               String EndDate = (String)hashMap.get("ENDDATE");
               if (hashMap.containsKey("ENDDATE")) {
                  dsValues.put("EndDate", EndDate);
               }

               String PolicyStatus = (String)hashMap.get("POLICYSTATUS");
               if (hashMap.containsKey("POLICYSTATUS")) {
                  dsValues.put("PolicyStatus", PolicyStatus);
               }
            }
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-ENROLLMENT-" + policyNumber + "-";
         String priority = propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", uc.getUserPasswordECS("ECSAdmin"), processInstanceName, "RGICL_ECS_ENROLLMENT_FLOW", priority);
               automatictasklog.info("ECS Savvion: initiatePolicyEnrollmentFlowECS service :: Created Instance :: " + processInstanceName);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            } else {
               this.responseCode = "5021";
            }
         } catch (Exception var17) {
            this.responseCode = "5050";
            automatictasklog.error("ECS Savvion: initiatePolicyConfigurationFlowECS service :: Exception occured : " + var17.getMessage());
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      automatictasklog.info("===================ECS Savvion: initiatePolicyEnrollmentFlowECS service :::: ENDS :: responseCode ::" + this.responseCode + "============================");
      return resObj;
   }

   public ResponseObject initiatePolicyEndorsementFlowECS(RequestObject[] reqObj) throws AxisFault {
      automatictasklog.info("ECS Savvion: initiatePolicyEndorsementFlowECS service :::: STARTS");
      ResponseObject resObj = new ResponseObject();
      HashMap<String, String> hashMap = null;
      HashMap<String, String> dsValues = new HashMap();
      ArrayList<String> ls = new ArrayList();
      UtilClass uc = new UtilClass(SBM_HOME);
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            hashMap = uc.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               automatictasklog.info("ECS Savvion: initiatePolicyEndorsementFlowECS service :: Input Valuse :: " + hashMap);
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", Branch);
               }

               String City = (String)hashMap.get("CITY");
               if (hashMap.containsKey("CITY")) {
                  dsValues.put("City", City);
               }

               String PolicyName = (String)hashMap.get("POLICYNAME");
               if (hashMap.containsKey("POLICYNAME")) {
                  dsValues.put("PolicyName", PolicyName);
               }

               String StartDate = (String)hashMap.get("STARTDATE");
               if (hashMap.containsKey("STARTDATE")) {
                  dsValues.put("StartDate", StartDate);
               }

               String EndDate = (String)hashMap.get("ENDDATE");
               if (hashMap.containsKey("ENDDATE")) {
                  dsValues.put("EndDate", EndDate);
               }

               String PolicyStatus = (String)hashMap.get("POLICYSTATUS");
               if (hashMap.containsKey("POLICYSTATUS")) {
                  dsValues.put("PolicyStatus", PolicyStatus);
               }

               String transactionType = (String)hashMap.get("TRANSACTIONTYPE");
               if (hashMap.containsKey("TRANSACTIONTYPE")) {
                  dsValues.put("transactionType", transactionType);
               }

               String isServiceEndorsement = (String)hashMap.get("ISSERVICEENDORSEMENT");
               if (hashMap.containsKey("ISSERVICEENDORSEMENT")) {
                  dsValues.put("isServiceEndorsement", isServiceEndorsement);
               }
            }
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-ENDORSEMENT-" + policyNumber + "-";
         String priority = propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", uc.getUserPasswordECS("ECSAdmin"), processInstanceName, "RGICL_ECS_ENDORSEMENT_FLOW", priority);
               automatictasklog.info("ECS Savvion:: initiatePolicyEndorsementFlowECS service :: Created Instance :: " + processInstanceName);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            } else {
               this.responseCode = "5021";
            }
         } catch (Exception var19) {
            this.responseCode = "5050";
            automatictasklog.error("ECS Savvion: initiatePolicyConfigurationFlowECS service :: Exception occured : " + var19.getMessage());
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      automatictasklog.info("ECS Savvion: initiatePolicyEndorsementFlowECS service :: ENDS :: ResponseCode :: " + this.responseCode);
      return resObj;
   }

   public ResponseObject getTaskListECS(RequestObject[] reqObj) {
      automatictasklog.info("==============ECS Savvion: getTaskListECS Service:::: START================================");
      DBUtility db = new DBUtility(SBM_HOME);
      String responseCode = null;
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = null;
      new HashMap();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = "RGICL_ECS_POLICY_FLOW";
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: getTaskListECS Service::resultTaskList size :: " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: getTaskListECS Service::::workItemObject lenght : " + workItemObject.length);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
               }
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: getTaskListECS Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("==========================ECS Savvion: getTaskListECS Service :::: END" + responseCode + "====================================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyConfigurationFlow(RequestObject[] reqObj) {
      automatictasklog.info("===================ECS Savvion: GetAssignedTaskListForPolicyConfigurationFlow Service:::: START===========================");
      DBUtility db = new DBUtility(SBM_HOME);
      String responseCode = null;
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      UtilClass uc = new UtilClass(SBM_HOME);
      WorkItemObject[] workItemObject = null;
      HashMap hashMap = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = "RGICL_ECS_POLICY_FLOW";
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: GetAssignedTaskListForPolicyConfigurationFlow Service::resultTaskList size : " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: GetAssignedTaskListForPolicyConfigurationFlow Service::::workItemObject length : " + workItemObject.length);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
               }
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: GetAssignedTaskListForPolicyConfigurationFlow Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("======================ECS Savvion: GetAssignedTaskListForPolicyConfigurationFlow Service :::: END" + responseCode + "==============================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyEnrollmentFlow(RequestObject[] reqObj) {
      String methodName = "GetAssignedTaskListForPolicyEnrollmentFlow";
      automatictasklog.info("====================ECS Savvion: " + methodName + " Service:::: START==========================");
      DBUtility db = new DBUtility(SBM_HOME);
      String responseCode = null;
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      UtilClass uc = new UtilClass(SBM_HOME);
      WorkItemObject[] workItemObject = null;
      HashMap hashMap = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = "RGICL_ECS_POLICY_FLOW";
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: " + methodName + "  Service::resultTaskList size : " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: " + methodName + "  Service::::workItemObject lenght : " + workItemObject.length);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
               }
            } else {
               responseCode = "5001";
            }
         } catch (Exception var13) {
            automatictasklog.error("ECS Savvion: " + methodName + "  Service::Error Message::" + var13.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("=======================ECS Savvion: " + methodName + "  Service :::: END" + responseCode + "============================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyEndorsementFlow(RequestObject[] reqObj) {
      String methodName = "GetAssignedTaskListForPolicyEndorsementFlow";
      automatictasklog.info("=====================ECS Savvion: " + methodName + " Service:::: START=========================");
      DBUtility db = new DBUtility(SBM_HOME);
      String responseCode = null;
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = null;
      HashMap hashMap = null;
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = "RGICL_ECS_POLICY_FLOW";
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: " + methodName + " Service::resultTaskList size : " + resultTaskList.size());
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject lenght : " + workItemObject.length);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
               }
            } else {
               responseCode = "5001";
            }
         } catch (Exception var13) {
            automatictasklog.error("ECS Savvion: " + methodName + " Service::Error Message::" + var13.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("========================ECS Savvion: " + methodName + " Service :::: END" + responseCode + "============================");
      return resObj;
   }

   public ResponseObject setDataSlotValueECS(RequestObject[] reqObj) {
      automatictasklog.info("==========================ECS Savvion: setDataSlotValueECS Service:::::START===========================");
      String sessionId = null;
      String responseCode = null;
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               if (processInstanceName != null && !processInstanceName.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     if (!uc.checkNull(hashMap.get("POLICYSTATUS"))) {
                        hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                     }

                     if (!uc.checkNull(hashMap.get("BRANCH"))) {
                        hm.put("Branch", hashMap.get("BRANCH").toString());
                     }

                     if (!uc.checkNull(hashMap.get("CITY"))) {
                        hm.put("City", hashMap.get("CITY").toString());
                     }

                     if (!uc.checkNull(hashMap.get("ISPOLICYCHECKERACCEPTED"))) {
                        hm.put("isPolicyCheckerAccepted", hashMap.get("ISPOLICYCHECKERACCEPTED").toString());
                     }

                     if (!uc.checkNull(hashMap.get("PMUSER"))) {
                        hm.put("PMUser", hashMap.get("PMUSER").toString());
                     }

                     if (!uc.checkNull(hashMap.get("PCHUSER"))) {
                        hm.put("PCHUser", hashMap.get("PCHUSER").toString());
                     }

                     String isComplete;
                     if (!uc.checkNull(hashMap.get("POLICYCHECKERREMARK"))) {
                        isComplete = (String)pi.getDataSlotValue("PolicyCheckerRemark");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("POLICYCHECKERREMARK").toString();
                        } else {
                           isComplete = hashMap.get("POLICYCHECKERREMARK").toString();
                        }

                        hm.put("PolicyCheckerRemark", isComplete);
                     }

                     if (!uc.checkNull(hashMap.get("POLICYMAKERREMARK"))) {
                        isComplete = (String)pi.getDataSlotValue("PolicyMakerRemark");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("POLICYMAKERREMARK").toString();
                        } else {
                           isComplete = hashMap.get("POLICYMAKERREMARK").toString();
                        }

                        hm.put("PolicyMakerRemark", isComplete);
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        if (!uc.checkNull(hashMap.get("ISCOMPLETE"))) {
                           isComplete = hashMap.get("ISCOMPLETE").toString();
                           if (!uc.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           }
                        }

                        responseCode = "5000";
                     } else {
                        responseCode = "5030";
                     }
                  } else {
                     responseCode = "5030";
                  }
               } else {
                  responseCode = "5021";
               }

               if (blsession != null) {
                  blserver.disConnect(blsession);
               }

               e = null;
            } catch (Exception var24) {
               e = var24;
               responseCode = "5034";
               automatictasklog.error("ECS Savvion: setDataSlotValueECS Service::::Catch Exception:::" + var24.getMessage());
               if (var24.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion RGICL_HCS_WS: setDataSlotValueECS userId" + userid + "encrypted " + pass);
                  } catch (Exception var23) {
                     automatictasklog.error("ECS Savvion RGICL_HCS_WS: setDataSlotValueECS :: Exception " + var23);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var22) {
               automatictasklog.error("ECS Savvion: setDataSlotValueECS Service::::Finally Exception:::" + var22.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("===================================ECS Savvion: setDataSlotValueECS Service::::END " + responseCode + " for WorkItemName " + (String)hashMap.get("WORKITEMNAME") + "===================================");
      return resObj;
   }

   public ResponseObject UpdatePolicyConfigurationSetUpDataSlots(RequestObject[] reqObj) {
      String methodName = "UpdatePolicyConfigurationSetUpDataSlots";
      automatictasklog.info("==========================ECS Savvion: " + methodName + " Service:::::START=====================");
      String sessionId = null;
      String responseCode = null;
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               if (processInstanceName != null && !processInstanceName.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     if (!uc.checkNull(hashMap.get("POLICYSTATUS"))) {
                        hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                     }

                     if (!uc.checkNull(hashMap.get("BRANCH"))) {
                        hm.put("Branch", hashMap.get("BRANCH").toString());
                     }

                     if (!uc.checkNull(hashMap.get("CITY"))) {
                        hm.put("City", hashMap.get("CITY").toString());
                     }

                     if (!uc.checkNull(hashMap.get("ISPOLICYCHECKERACCEPTED"))) {
                        hm.put("isPolicyCheckerAccepted", hashMap.get("ISPOLICYCHECKERACCEPTED").toString());
                     }

                     String isComplete;
                     if (!uc.checkNull(hashMap.get("POLICYCHECKERREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("PolicyCheckerRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("POLICYCHECKERREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("POLICYCHECKERREMARKS").toString();
                        }

                        hm.put("PolicyCheckerRemarks", isComplete);
                     }

                     if (!uc.checkNull(hashMap.get("POLICYCONFIGURATORREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("PolicyConfiguratorRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("POLICYCONFIGURATORREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("POLICYCONFIGURATORREMARKS").toString();
                        }

                        hm.put("PolicyConfiguratorRemarks", isComplete);
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        if (!uc.checkNull(hashMap.get("ISCOMPLETE"))) {
                           isComplete = hashMap.get("ISCOMPLETE").toString();
                           if (!uc.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           }
                        }

                        responseCode = "5000";
                     } else {
                        responseCode = "5030";
                     }
                  } else {
                     responseCode = "5030";
                  }
               } else {
                  responseCode = "5021";
               }

               if (blsession != null) {
                  blserver.disConnect(blsession);
               }

               e = null;
            } catch (Exception var25) {
               e = var25;
               responseCode = "5034";
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var25.getMessage());
               if (var25.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion RGICL_HCS_WS: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var24) {
                     automatictasklog.error("ECS Savvion RGICL_HCS_WS: " + methodName + " :: Exception " + var24);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var23) {
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var23.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("=========================ECS Savvion: " + methodName + " Service::::END " + responseCode + " for WorkItemName " + (String)hashMap.get("WORKITEMNAME") + "===================================");
      return resObj;
   }

   public ResponseObject UpdatePolicyEnrollmentSetUpDataSlots(RequestObject[] reqObj) {
      String methodName = "UpdatePolicyEnrollmentSetUpDataSlots";
      automatictasklog.info("=======================ECS Savvion: " + methodName + " Service:::::START ====================================");
      String sessionId = null;
      String responseCode = null;
      HashMap hashMap = null;
      UtilClass uc = new UtilClass(SBM_HOME);
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               if (processInstanceName != null && !processInstanceName.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     if (!uc.checkNull(hashMap.get("POLICYSTATUS"))) {
                        hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                     }

                     if (!uc.checkNull(hashMap.get("BRANCH"))) {
                        hm.put("Branch", hashMap.get("BRANCH").toString());
                     }

                     if (!uc.checkNull(hashMap.get("CITY"))) {
                        hm.put("City", hashMap.get("CITY").toString());
                     }

                     if (!uc.checkNull(hashMap.get("EMUSER"))) {
                        hm.put("Maker", hashMap.get("EMUSER").toString());
                     }

                     String isComplete;
                     if (!uc.checkNull(hashMap.get("ENROLLMENTMAKERREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("MakerRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                        }

                        hm.put("MakerRemarks", isComplete);
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        if (!uc.checkNull(hashMap.get("ISCOMPLETE"))) {
                           isComplete = hashMap.get("ISCOMPLETE").toString();
                           if (!uc.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           }
                        }

                        responseCode = "5000";
                     } else {
                        responseCode = "5030";
                     }
                  } else {
                     responseCode = "5030";
                  }
               } else {
                  responseCode = "5021";
               }

               if (blsession != null) {
                  blserver.disConnect(blsession);
               }

               e = null;
            } catch (Exception var25) {
               e = var25;
               responseCode = "5034";
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var25.getMessage());
               if (var25.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion RGICL_HCS_WS: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var24) {
                     automatictasklog.error("ECS Savvion RGICL_HCS_WS: " + methodName + " :: Exception " + var24);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var23) {
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var23.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("===============================ECS Savvion: " + methodName + " Service::::END " + responseCode + " for WorkItemName " + (String)hashMap.get("WORKITEMNAME") + "===================================");
      return resObj;
   }

   public ResponseObject UpdatePolicyEndorsementSetUpDataSlots(RequestObject[] reqObj) {
      String methodName = "UpdatePolicyEndorsementSetUpDataSlots";
      automatictasklog.info("=============================ECS Savvion: " + methodName + " Service:::::START===========================");
      String sessionId = null;
      String responseCode = null;
      HashMap hashMap = null;
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               if (processInstanceName != null && !processInstanceName.equals("")) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     HashMap hm = new HashMap();
                     if (!uc.checkNull(hashMap.get("POLICYSTATUS"))) {
                        hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                     }

                     if (!uc.checkNull(hashMap.get("BRANCH"))) {
                        hm.put("Branch", hashMap.get("BRANCH").toString());
                     }

                     if (!uc.checkNull(hashMap.get("CITY"))) {
                        hm.put("City", hashMap.get("CITY").toString());
                     }

                     if (!uc.checkNull(hashMap.get("TRANSACTIONTYPE"))) {
                        hm.put("transactionType", hashMap.get("TRANSACTIONTYPE").toString());
                     }

                     if (!uc.checkNull(hashMap.get("ISSERVICEENDORSEMENT"))) {
                        hm.put("isServiceEndorsement", hashMap.get("ISSERVICEENDORSEMENT").toString());
                     }

                     if (!uc.checkNull(hashMap.get("ISENDORSEMENTCHECKERACCEPTED"))) {
                        hm.put("isEDMCheckerAccepted", hashMap.get("ISENDORSEMENTCHECKERACCEPTED").toString());
                     }

                     String isComplete;
                     if (!uc.checkNull(hashMap.get("ENROLLMENTMAKERREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("EnrollmentMakerRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                        }

                        hm.put("EnrollmentMakerRemarks", isComplete);
                     }

                     if (!uc.checkNull(hashMap.get("ENDORSEMENTMAKERREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("EndorsementMakerRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("ENDORSEMENTMAKERREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("ENDORSEMENTMAKERREMARKS").toString();
                        }

                        hm.put("EnrollmentMakerRemarks", isComplete);
                     }

                     if (!uc.checkNull(hashMap.get("ENDORSEMENTCHECKERREMARKS"))) {
                        isComplete = (String)pi.getDataSlotValue("EndorsementCheckerRemarks");
                        if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                           isComplete = isComplete + "|" + hashMap.get("ENDORSEMENTCHECKERREMARKS").toString();
                        } else {
                           isComplete = hashMap.get("ENDORSEMENTCHECKERREMARKS").toString();
                        }

                        hm.put("EndorsementCheckerRemarks", isComplete);
                     }

                     if (blsession != null) {
                        pi.updateSlotValue(hm);
                        pi.save();
                        if (!uc.checkNull(hashMap.get("ISCOMPLETE"))) {
                           isComplete = hashMap.get("ISCOMPLETE").toString();
                           if (!uc.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                              uc.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                              uc.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           }
                        }

                        responseCode = "5000";
                     } else {
                        responseCode = "5030";
                     }
                  } else {
                     responseCode = "5030";
                  }
               } else {
                  responseCode = "5021";
               }

               if (blsession != null) {
                  blserver.disConnect(blsession);
               }

               e = null;
            } catch (Exception var25) {
               e = var25;
               responseCode = "5034";
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var25.getMessage());
               if (var25.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion RGICL_HCS_WS: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var24) {
                     automatictasklog.error("ECS Savvion RGICL_HCS_WS: " + methodName + " :: Exception " + var24);
                  }
               }
            }
         } finally {
            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var23) {
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var23.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("==============================ECS Savvion: " + methodName + " Service::::END " + responseCode + " for WorkItemName " + (String)hashMap.get("WORKITEMNAME") + "===================================");
      return resObj;
   }

   private void printUserRoleObject(UserRoleObject[] uro) {
      automatictasklog.debug("ECS Savvion:printUserRoleObject Utility Service::::START");

      try {
         automatictasklog.info("ECS printUserRoleObject  Service : :" + uro.length);

         for(int i = 0; i < uro.length; ++i) {
            automatictasklog.info("ECS printUserRoleObject  Service : :" + uro[i].getRole() + "::" + uro[i].getLocation() + "::" + uro[i].getLOB());
         }
      } catch (Exception var3) {
         automatictasklog.error("ECS Savvion:printUserRoleObject Utility Service::::FAIULIRE EXCEPTION ");
      }

      automatictasklog.info("ECS printUserRoleObject Utility Service::::END");
   }

   private WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
      WorkItemObject[] resultWorkitems = null;
      int inboxSize = 400;
      if (propertiesECSSavvion.getProperty("ECSInboxSize") != null) {
         inboxSize = Integer.parseInt(propertiesECSSavvion.getProperty("ECSInboxSize"));
      }

      if (workitemlist.size() > inboxSize) {
         WorkItemObject[] inboxSizeWorkitems = new WorkItemObject[inboxSize];
         automatictasklog.info("ECS Savvion: getResultWorkitems Service::::" + workitemlist.size());

         for(int i = 0; i < inboxSize; ++i) {
            inboxSizeWorkitems[i] = (WorkItemObject)workitemlist.get(i);
         }

         resultWorkitems = inboxSizeWorkitems;
      } else {
         resultWorkitems = new WorkItemObject[workitemlist.size()];
         workitemlist.toArray(resultWorkitems);
      }

      return resultWorkitems;
   }

   public ResponseObject makeAvailableTaskECS(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.makeAvailableTaskECS(reqObj);
   }

   public ResponseObject CreateWorkFlow(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.CreateWorkFlow(reqObj);
   }

   public ResponseObject getAssignedTaskList(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.getAssignedTaskList(reqObj);
   }

   public ResponseObject assignTaskToUser(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.assignTaskToUser(reqObj);
   }

   public ResponseObject UpdateAndCompleteRRFlowTask(RequestObject[] reqObj) {
      RRFlow rr = new RRFlow(SBM_HOME);
      return rr.UpdateAndCompleteRRFlowTask(reqObj);
   }

   public ResponseObject addOrRemoveUserECS(RequestObject[] reqObj) {
      RRFlow rr = new RRFlow(SBM_HOME);
      return rr.addOrRemoveUserECS(reqObj);
   }

   public ResponseObject UpdateAndCompleteNetworkFlowTask(RequestObject[] reqObj) {
      NetworkFlow nf = new NetworkFlow(SBM_HOME);
      return nf.UpdateAndCompleteNetworkFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteNetworkGroupFlowTask(RequestObject[] reqObj) {
      NetworkFlow nf = new NetworkFlow(SBM_HOME);
      return nf.UpdateAndCompleteNetworkGroupFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteProductFlowTask(RequestObject[] reqObj) {
      ProductFlow pf = new ProductFlow(SBM_HOME);
      return pf.UpdateAndCompleteProductFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteALFlowTask(RequestObject[] reqObj) {
      ALFlow al = new ALFlow(SBM_HOME);
      return al.UpdateAndCompleteALFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteCLFlowTask(RequestObject[] reqObj) {
      CLFlow cl = new CLFlow(SBM_HOME);
      return cl.UpdateAndCompleteCLFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteCSGFlowTask(RequestObject[] reqObj) {
      CLFlow cl = new CLFlow(SBM_HOME);
      return cl.UpdateAndCompleteCSGFlowTask(reqObj);
   }

   public ResponseObject assignOrKeepTask(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.assignOrKeepTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteSRFlowTask(RequestObject[] reqObj) {
      SRFlow sr = new SRFlow(SBM_HOME);
      return sr.UpdateAndCompleteSRFlowTask(reqObj);
   }

   public ResponseObject completeProcessInstance(RequestObject[] reqObj) {
      UtilClass uc = new UtilClass(SBM_HOME);
      return uc.completeProcessInstance(reqObj);
   }

   public ResponseObject UpdateAndCompleteFinanceMasterFlowTask(RequestObject[] reqObj) {
      FinanceMaster fm = new FinanceMaster(SBM_HOME);
      return fm.FinanceMasterFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteALTPAFlowTask(RequestObject[] reqObj) {
      ALTPAFlow altpa = new ALTPAFlow(SBM_HOME);
      return altpa.UpdateAndCompleteALTPAFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteCLTPAFlow(RequestObject[] reqObj) {
      CLTPAFlow cltpa = new CLTPAFlow(SBM_HOME);
      return cltpa.UpdateAndCompleteCLTPAFlow(reqObj);
   }

   public ResponseObject createAndCompleteOPDALFlowTask(RequestObject[] reqObj) {
      OPD_ALFlow opdAL = new OPD_ALFlow(SBM_HOME);
      return opdAL.createAndCompleteOPDALFlowTask(reqObj);
   }

   public ResponseObject UpdateAndCompleteOPDCLFlowTask(RequestObject[] reqObj) {
      OPD_CLFlow opdCL = new OPD_CLFlow(SBM_HOME);
      return opdCL.UpdateAndCompleteOPDCLFlowTask(reqObj);
   }

   public ResponseObject AutoAdjudicationALFlowTask(RequestObject[] reqObj) {
      AutoAdjudicationALFlow obj = new AutoAdjudicationALFlow(SBM_HOME);
      return obj.AutoAdjudicationALFlowTask(reqObj);
   }

   public ResponseObject AutoAdjudicationCLFlowTask(RequestObject[] reqObj) {
      AutoAdjudicationCLFlow obj = new AutoAdjudicationCLFlow(SBM_HOME);
      return obj.AutoAdjudicationCLFlowTask(reqObj);
   }

   public ResponseObject AutoAdjudicationOPDCLFlowTask(RequestObject[] reqObj) {
      AutoAdjudicationOPDCLFlow obj = new AutoAdjudicationOPDCLFlow(SBM_HOME);
      return obj.AutoAdjudicationOPDCLFlowTask(reqObj);
   }

   public ResponseObject getUserAttribute(String userId) {
      ResponseObject resObj = new ResponseObject();

      try {
         User userObject = UserManager.getUser(userId);
         resObj.setResponseCode(userObject.getAttribute("password"));
         PService p = PService.self();
         resObj.setResponseMessage(p.decrypt(userObject.getAttribute("password")));
      } catch (Exception var5) {
         automatictasklog.error("ECS Savvion WS class : getUserAttribute Service::ERROR:: USERID is " + userId + var5.getMessage());
      }

      return resObj;
   }

   public ResponseObject setPassword(String userId, String pass) {
      ResponseObject resObj = new ResponseObject();
      User sbmUser = null;
      Realm usr = null;

      try {
         usr = UserManager.getDefaultRealm();
         sbmUser = usr.getUser(userId);
         sbmUser.setAttribute("password", pass);
         User userObject = UserManager.getUser(userId);
         resObj.setResponseCode(userObject.getAttribute("password"));
         PService p = PService.self();
         resObj.setResponseMessage(p.decrypt(userObject.getAttribute("password")));
      } catch (Exception var8) {
         automatictasklog.error("ECS Savvion WS class: setPassword Service::ERROR:: USERID is " + userId + var8.getMessage());
      }

      return resObj;
   }

   public String getDecrypted(String pass) {
      PService p = PService.self();
      p.encrypt(pass);
      return p.decrypt(pass);
   }
}
