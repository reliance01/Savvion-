import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.savvion.webservice.bizlogic.BizLogicWSUtil;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.BizLogic.Server.PAKException;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecs.savvion.policy.objectmodel.RequestObject;
import rgicl.ecs.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecs.savvion.policy.objectmodel.UserRoleObject;
import rgicl.ecs.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecs.savvion.policy.util.db.DBUtility;

public class COPY_WS_RGICL_ECS_POLICY_FLOW {
   private static BizLogicManager pak = null;
   private String ptName = "RGICL_ECS_POLICY_FLOW";
   private static Byte[] bytearray = new Byte[0];
   final String SBM_HOME = "E:/SBM75";
   final String ECS_SAVVION_PROPERTIES = "E:/SBM75/conf/ECS_SAVVION_PROPERTIES.properties";
   final String ECS_SAVVION_LOG_PROPERTIES = "E:/SBM75/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
   Properties propertiesECSSavvion;
   Properties propertiesECSLog;
   static Logger automatictasklog = null;
   static Logger humantasklog = null;
   private static String ECSADMIN = null;
   private static Byte[] bytearrayECSADMIN = new Byte[0];
   private String responseCode = null;
   final String ECSConfigurationPTName = "RGICL_ECS_CONFIGURATION_FLOW";
   private static final String ECSProductPTName = "RGICL_ECS_PRODUCT_SETUP_FLOW";
   private static final String ECSProductInstanceName = "";
   final String ECSConfigurationInstanceName = "";
   final String priority = "1";
   final String ECSPaymentInstanceName = "";
   final String SUCCESS = "5000";
   final String USER_ID_NULL = "5001";
   final String USER_NOT_AUTHORIZED = "5002";
   final String USER_NAME_NULL = "5003";
   final String USER_ROLE_NULL = "5004";
   final String CLM_NUM_NULL = "5005";
   final String CLM_LOB_NULL = "5006";
   final String CLM_FLAG_NULL = "5007";
   final String CLM_TYPE_NULL = "5008";
   final String CLM_REOPEN_NULL = "5009";
   final String TPA_FLAG_NULL = "5010";
   final String STP_FLAG_NULL = "5011";
   final String PI_NAME_NULL = "5012";
   final String PI_ID_NULL = "5013";
   final String DS_NAME_NULL = "5014";
   final String DS_VALUE_NULL = "5015";
   final String WINAME_NULL = "5016";
   final String REASSGN_BSM_NULL = "5017";
   final String APPROVER_USER_ID_NULL = "5018";
   final String RESERVE_APPR_FLAG_NULL = "5019";
   final String PYMT_APPR_FLAG_NULL = "5020";
   final String INPUT_VALUE_IS_NULL = "5021";
   final String USER_ALREADY_MAPPED = "5022";
   final String USER_NOT_MAPPED = "5023";
   final String USER_ID_INVALID = "5030";
   final String USER_ROLE_INVALID = "5031";
   final String PI_ID_INVALID = "5032";
   final String PI_NAME_INVALID = "5033";
   final String DS_NAME_INVALID = "5034";
   final String DS_VALUE_INVALID = "5035";
   final String APPROVER_USER_ID_INVALID = "5036";
   final String CLM_FLAG_INVALID = "5037";
   final String INVALID_INPUT = "5038";
   final String COMMON_INBOX_EMPTY = "5041";
   final String ASSIGNED_INBOX_EMPTY = "5042";
   final String FAILURE_EXCEPTION = "5050";
   final String INVALID_WINAME = "5059";
   final String WINAME_NOT_FOUND = "5060";
   final String NEXT_WORKITEM_NOT_FOUND = "5043";
   final String PENDING_CLAIMS_EMPTY = "5044";
   final String DATABASE_ERROR = "5045";
   final String ECSGroup = "ECS";
   final String INVALID_REQUEST = "5047";
   final String REQUEST_OBJECT_IS_NULL = "5070";
   final String INVALID_LOB = "5046";
   final String SUB_PROCESSINSTANCES_EMPTY = "SUB_PROCESSINSTANCES_EMPTY";
   final String CLMMGRUser = "CLMMGR";
   final String BSMMGRUser = "BSMMGR";
   final String RCMMGRUser = "RCMMGR";
   final String ROMMGRUser = "ROMMGR";
   final String RAMMGRUser = "RAMMGR";
   final String COMMGRUser = "COMMGR";
   final String COINSURERUser = "COINSURER";

   public COPY_WS_RGICL_ECS_POLICY_FLOW() {
      try {
         NLog.ws.debug("COPY_WS_RGICL_ECS_POLICY_FLOW service invoked");
         this.propertiesECSSavvion = new Properties();
         this.propertiesECSSavvion.load(new FileInputStream("E:/SBM75/conf/ECS_SAVVION_PROPERTIES.properties"));
         this.propertiesECSLog = new Properties();
         this.propertiesECSLog.load(new FileInputStream("E:/SBM75/conf/ECS_SAVVION_LOG_PROPERTIES.properties"));
         PropertyConfigurator.configure(this.propertiesECSLog);
         automatictasklog = Logger.getLogger("Automatic");
         humantasklog = Logger.getLogger("Human");
      } catch (Exception var2) {
      }

   }

   private String INITIATEFLOW(HashMap dsValues, String username, String password, String piName, String _ptName, String priority) throws AxisFault {
      NLog.ws.debug("START method invoked");
      String sessionId = this.connect(username, password);
      HashMap dsTypeMap = new HashMap();
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
      }

      Set s = dsValues.entrySet();

      Entry var11;
      for(Iterator it = s.iterator(); it != null && it.hasNext(); var11 = (Entry)it.next()) {
      }

      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      String pi = this.createProcessInstance(sessionId, _ptName, piName, priority, resolvedDSValues);
      this.disconnect(sessionId);
      return pi;
   }

   private void POLICYCHECKER(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("POLICYCHECKER method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void POLICYMAKER(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("POLICYMAKER method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void activateProcessInstance(String sessionId, String piName) throws AxisFault {
      try {
         getBizLogicManager().activateProcessInstance(sessionId, piName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      } catch (PAKException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
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
                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private String connect(String userId, String password) throws AxisFault {
      String sessionId = null;

      try {
         sessionId = getBizLogicManager().connect(userId, password);
         return sessionId;
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
      try {
         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   private String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, true);
   }

   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
      Object obj = null;

      try {
         obj = getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
      } catch (RemoteException var6) {
         throw new AxisFault("SBM Web services error :" + var6.getMessage());
      } catch (PAKException var7) {
         throw new AxisFault("SBM Web services error :" + var7.getMessage());
      }

      return obj instanceof String ? (String)obj : "NST";
   }

   private boolean checkNull(Object dsValue) {
      return dsValue == null || dsValue instanceof String && dsValue.equals("<NULL>");
   }

   private Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
      Hashtable resolvedValues = new Hashtable();
      if (valueMap != null && valueMap.size() != 0) {
         Iterator sIterator = dsTypes.entrySet().iterator();

         while(true) {
            while(sIterator.hasNext()) {
               Entry sEntry = (Entry)sIterator.next();
               String key = (String)sEntry.getKey();
               String type = (String)sEntry.getValue();
               Object dsvalue = valueMap.get(key);
               if (this.checkNull(dsvalue)) {
                  resolvedValues.put(key, "<NULL>");
               } else if (!type.equals("DOCUMENT")) {
                  String[] data;
                  if (type.equals("LIST")) {
                     data = (String[])dsvalue;
                     int size = data.length;
                     Vector v = new Vector(size);

                     for(int i = 0; i < size; ++i) {
                        v.add(data[i]);
                     }

                     resolvedValues.put(key, v);
                  } else if (type.equals("XML")) {
                     XML xml = new XML((String)dsvalue);
                     resolvedValues.put(key, xml);
                  } else if (type.equals("DECIMAL")) {
                     Decimal dec = new Decimal((BigDecimal)dsvalue);
                     resolvedValues.put(key, dec);
                  } else if (type.equals("DATETIME")) {
                     Calendar cal = (Calendar)dsvalue;
                     DateTime dt = new DateTime(cal.getTimeInMillis());
                     resolvedValues.put(key, dt);
                  } else if (!type.equals("MAP")) {
                     resolvedValues.put(key, dsvalue);
                  } else {
                     data = (String[])dsvalue;
                     Map hm = new HashMap();

                     for(int i = 0; i < data.length; ++i) {
                        StringTokenizer st = new StringTokenizer(data[i], "=");
                        hm.put(st.nextToken(), st.nextToken());
                     }

                     resolvedValues.put(key, hm);
                  }
               }
            }

            return resolvedValues;
         }
      } else {
         return resolvedValues;
      }
   }

   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
      QueryService qs = null;
      QSWorkItemRS wirs = null;

      try {
         Session sess = getBizLogicManager().getSession(sessionId);
         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
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

         String[] var12 = winames;
         return var12;
      } catch (Exception var19) {
         throw new AxisFault("SBM Web services error :" + var19.getMessage());
      } finally {
         try {
            wirs.close();
         } catch (Exception var18) {
         }

      }
   }

   public boolean checkUserECS(String userId, String groupName) {
      boolean isMember = false;

      try {
         User usr = UserManager.getUser(userId);
         String[] groupNames = usr.getAllGroupNames();

         for(int i = 0; i < groupNames.length; ++i) {
            if (groupNames[i].equals(groupName)) {
               isMember = true;
            }
         }

         automatictasklog.debug("ECS Savvion: checkUserECS Service:::: userId is " + userId + " isMember" + isMember);
      } catch (Exception var7) {
         automatictasklog.error("ECS Savvion: checkUserECS Service:::: Exception " + var7.getMessage());
      }

      return isMember;
   }

   public ResponseObject mapUserToECSGroup(RequestObject[] reqObj) {
      String result = null;
      boolean status = false;
      AdvanceGroup groupObj = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");

         try {
            automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service START ::: INPUT VALUES are userId" + userId);
            if (!userId.equals("") && userId != null) {
               Realm r = UserManager.getDefaultRealm();
               boolean b = r.addUser(userId);
               if (b) {
                  User sbmUser = r.getUser(userId);
                  sbmUser.setAttribute("password", userId);
               }

               if (!this.checkUserECS(userId, "ECS")) {
                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
                  status = groupObj.addUserMember(userId);
                  if (status) {
                     responseCode = "5000";
                  } else {
                     responseCode = "5050";
                  }
               } else {
                  responseCode = "5022";
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: mapUserToECSGroup Service:::: Exception " + var12.getMessage());
         }

         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service END ::: " + responseCode);
         resObj.setResponseCode(responseCode);
      }

      return resObj;
   }

   public ResponseObject removeUserFromECSGroup(RequestObject[] reqObj) {
      String result = null;
      boolean status = false;
      AdvanceGroup groupObj = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");

         try {
            if (!userId.equals("") && userId != null) {
               if (this.checkUserECS(userId, "ECS")) {
                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
                  status = groupObj.removeUserMember(userId);
                  if (status) {
                     responseCode = "5000";
                  } else {
                     responseCode = "5050";
                  }

                  automatictasklog.debug("ECS Savvion: removeUserFromECS Service::::userId is " + userId + " status is " + status);
               } else {
                  responseCode = "5023";
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var10) {
            automatictasklog.error("ECS Savvion: removeUserFromECS Service:::: Exception " + var10.getMessage());
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.debug("ECS Savvion: removeUserFromECS Service::: END " + responseCode);
      return resObj;
   }

   private static String connectECSADMIN() {
      boolean valid = false;

      try {
         valid = getBizLogicManager().isSessionValid(ECSADMIN);
      } catch (RemoteException var4) {
         automatictasklog.error("ECS Savvion: connectECSADMIN Service:::: Exception " + var4.getMessage());
      }

      synchronized(bytearrayECSADMIN) {
         if (ECSADMIN == null || !valid) {
            try {
               ECSADMIN = getBizLogicManager().connect("ECSAdmin", "ECSAdmin");
            } catch (Exception var3) {
               automatictasklog.error("ECS Savvion: connectECSADMIN Service:::: Exception " + var3.getMessage());
            }
         }
      }

      return ECSADMIN;
   }

   public ResponseObject initiatePolicyFlowECS(RequestObject[] reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap dsValues = new HashMap();
      ArrayList ls = new ArrayList();
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            HashMap hashMap = this.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
                  System.out.println("Proposal Number is :" + (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", hashMap.get("BRANCH"));
                  System.out.println("Branch is :" + Branch);
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

            new HashMap();
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-POLICY-" + policyNumber + "-";
         String priority = this.propertiesECSSavvion.getProperty("priority");
         String[] workItemNames = (String[])null;
         WorkItemObject[] workItemObject = new WorkItemObject[100];
         String[] workItem = new String[100];
         RequestObject[] var24 = new RequestObject[3];

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               Set s = dsValues.entrySet();
               Iterator it = s.iterator();

               while(it != null && it.hasNext()) {
                  Entry m = (Entry)it.next();
                  System.out.println(m.getKey() + ":::" + m.getValue());
               }

               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", "ECSAdmin", processInstanceName, "RGICL_ECS_POLICY_FLOW", priority);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            }
         } catch (Exception var18) {
            this.responseCode = "5050";
            var18.printStackTrace();
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
            System.out.println("Test123" + resultArray[j]);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      return resObj;
   }

   public ResponseObject initiatePolicyConfigurationFlowECS(RequestObject[] reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap dsValues = new HashMap();
      ArrayList ls = new ArrayList();
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            HashMap hashMap = this.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
                  System.out.println("Proposal Number is :" + (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", hashMap.get("BRANCH"));
                  System.out.println("Branch is :" + Branch);
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

            new HashMap();
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-CONFIGURATION-" + policyNumber + "-";
         String priority = this.propertiesECSSavvion.getProperty("priority");
         String[] workItemNames = (String[])null;
         WorkItemObject[] workItemObject = new WorkItemObject[100];
         String[] workItem = new String[100];
         RequestObject[] var23 = new RequestObject[3];

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               Set s = dsValues.entrySet();
               Iterator it = s.iterator();

               while(it != null && it.hasNext()) {
                  Entry m = (Entry)it.next();
                  System.out.println(m.getKey() + ":::" + m.getValue());
               }

               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", "ECSAdmin", processInstanceName, "RGICL_ECS_CONFIGURATION_FLOW", priority);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            }
         } catch (Exception var17) {
            this.responseCode = "5050";
            var17.printStackTrace();
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
            System.out.println("Test123" + resultArray[j]);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      return resObj;
   }

   public ResponseObject initiatePolicyEnrollmentFlowECS(RequestObject[] reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap dsValues = new HashMap();
      ArrayList ls = new ArrayList();
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            HashMap hashMap = this.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
                  System.out.println("Proposal Number is :" + (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", hashMap.get("BRANCH"));
                  System.out.println("Branch is :" + Branch);
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

            new HashMap();
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-ENROLLMENT-" + policyNumber + "-";
         String priority = this.propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               Set s = dsValues.entrySet();
               Iterator it = s.iterator();

               while(it != null && it.hasNext()) {
                  Entry m = (Entry)it.next();
                  System.out.println(m.getKey() + ":::" + m.getValue());
               }

               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", "ECSAdmin", processInstanceName, "RGICL_ECS_ENROLLMENT_FLOW", priority);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            } else {
               this.responseCode = "5021";
            }
         } catch (Exception var16) {
            this.responseCode = "5050";
            var16.printStackTrace();
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      return resObj;
   }

   public ResponseObject initiatePolicyEndorsementFlowECS(RequestObject[] reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap dsValues = new HashMap();
      ArrayList ls = new ArrayList();
      String policyNumber = "";
      String userId = "";
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            HashMap hashMap = this.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               if (hashMap.containsKey("POLICYNUMBER")) {
                  policyNumber = (String)hashMap.get("POLICYNUMBER");
                  dsValues.put("PolicyNumber", (String)hashMap.get("POLICYNUMBER"));
               }

               if (hashMap.containsKey("PROPOSALNUMBER")) {
                  dsValues.put("ProposalNumber", (String)hashMap.get("PROPOSALNUMBER"));
                  System.out.println("Proposal Number is :" + (String)hashMap.get("PROPOSALNUMBER"));
               }

               String Branch = (String)hashMap.get("BRANCH");
               if (hashMap.containsKey("BRANCH")) {
                  dsValues.put("Branch", hashMap.get("BRANCH"));
                  System.out.println("Branch is :" + Branch);
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
            }

            new HashMap();
         }

         if (userId == "" || userId == null) {
            userId = "ECSAdmin";
         }

         String processInstanceName = "RGICL-ECS-ENDORSEMENT-" + policyNumber + "-";
         String priority = this.propertiesECSSavvion.getProperty("priority");

         try {
            if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               Set s = dsValues.entrySet();
               Iterator it = s.iterator();

               while(it != null && it.hasNext()) {
                  Entry m = (Entry)it.next();
                  System.out.println(m.getKey() + ":::" + m.getValue());
               }

               processInstanceName = this.INITIATEFLOW(dsValues, "ECSAdmin", "ECSAdmin", processInstanceName, "RGICL_ECS_ENDORSEMENT_FLOW", priority);
               this.responseCode = "5000";
               ls.add(processInstanceName);
            } else {
               this.responseCode = "5021";
            }
         } catch (Exception var17) {
            this.responseCode = "5050";
            var17.printStackTrace();
         }

         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         resObj.setResponseCode(this.responseCode);
      }

      return resObj;
   }

   public ResponseObject getTaskListECS(RequestObject[] reqObj) {
      automatictasklog.info("==================================================================================================");
      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
      DBUtility db = new DBUtility("E:/SBM75");
      String responseCode = null;
      String taskListArray = "5042";
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      new HashMap();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = this.ptName;
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service:: Objects Count " + resultTaskList.size());
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::workItemObject :" + workItemObject);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::resultTaskList.size" + resultTaskList.size());
               }

               System.out.println("Test123");
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
      automatictasklog.info("==================================================================================================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyConfigurationFlow(RequestObject[] reqObj) {
      automatictasklog.info("==================================================================================================");
      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
      DBUtility db = new DBUtility("E:/SBM75");
      String responseCode = null;
      String taskListArray = "5042";
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      new HashMap();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = this.ptName;
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service:: Objects Count " + resultTaskList.size());
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::workItemObject :" + workItemObject);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::resultTaskList.size" + resultTaskList.size());
               }

               System.out.println("Test123");
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
      automatictasklog.info("==================================================================================================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyEnrollmentFlow(RequestObject[] reqObj) {
      automatictasklog.info("==================================================================================================");
      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
      DBUtility db = new DBUtility("E:/SBM75");
      String responseCode = null;
      String taskListArray = "5042";
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      new HashMap();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = this.ptName;
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service:: Objects Count " + resultTaskList.size());
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::workItemObject :" + workItemObject);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::resultTaskList.size" + resultTaskList.size());
               }

               System.out.println("Test123");
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
      automatictasklog.info("==================================================================================================");
      return resObj;
   }

   public ResponseObject GetAssignedTaskListForPolicyEndorsementFlow(RequestObject[] reqObj) {
      automatictasklog.info("==================================================================================================");
      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
      DBUtility db = new DBUtility("E:/SBM75");
      String responseCode = null;
      String taskListArray = "5042";
      ResponseObject resObj = new ResponseObject();
      new ArrayList();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      new HashMap();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String _ptName = (String)hashMap.get("PROCESSNAME");
         if (_ptName == null) {
            _ptName = this.ptName;
         }

         try {
            if (userId != null && !userId.equals("")) {
               ArrayList resultTaskList = db.getMyInboxTaskObjects(userId, _ptName);
               responseCode = "5000";
               automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service:: Objects Count " + resultTaskList.size());
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::workItemObject :" + workItemObject);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
                  automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::resultTaskList.size" + resultTaskList.size());
               }

               System.out.println("Test123");
            } else {
               responseCode = "5001";
            }
         } catch (Exception var12) {
            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var12.getMessage());
            responseCode = "5030";
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
      automatictasklog.info("==================================================================================================");
      return resObj;
   }

   public ResponseObject assignTaskECS(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: assignTaskECS Service ::START");
      new HashMap();
      HashMap hm = new HashMap();
      String[] workItemName = (String[])null;
      String toBeAssignedUserId = null;
      ResponseObject resObj = new ResponseObject();
      String sessionId = null;
      String performer = null;
      String[] splitWorkItem = new String[5];
      String workStepName = null;
      String processInstanceName = null;
      String reAssignFlag = null;
      String responseCode = null;
      String plcStatus = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: assignTaskECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         try {
            HashMap hashMap = this.getMap(reqObj);
            automatictasklog.info("ECS Savvion: assignTaskECS Service:::: INPUT VALUES " + hashMap);
            workItemName = (String[])hashMap.get("WORKITEMNAMES");
            toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
            plcStatus = (String)hashMap.get("POLICYSTATUS");
            if (toBeAssignedUserId != null && workItemName != null && !toBeAssignedUserId.equals("") && !workItemName.equals("")) {
               sessionId = this.connectUser(toBeAssignedUserId);
               if (sessionId.equals("false")) {
                  responseCode = "5030";
               } else {
                  int index = 0;

                  while(true) {
                     if (index >= workItemName.length) {
                        responseCode = "5000";
                        break;
                     }

                     if (workItemName[index] == null && !workItemName[index].equals("null")) {
                        automatictasklog.warn("ECS Savvion: assignTaskECS Service::::WorkItem[" + index + "] is NULL");
                     } else {
                        performer = this.getWorkItemPerformerECS(workItemName[index]);
                        BLServer blserver = BLClientUtil.getBizLogicServer();
                        Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
                        WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
                        if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
                           automatictasklog.warn("ECS AssignTask enterred");
                           wi.assign(toBeAssignedUserId);
                           wi.save();
                           splitWorkItem = workItemName[index].split("::");
                           processInstanceName = splitWorkItem[0];
                           workStepName = splitWorkItem[1];
                           RequestObject[] reqo;
                           if (workStepName.equals("PolicyMaker")) {
                              hm.put("PROCESSINSTANCENAME", processInstanceName);
                              hm.put("DATASLOTNAME", "PolicyStatus");
                              hm.put("DATASLOTVALUE", plcStatus);
                              reqo = this.getRequestObject(hm);
                              this.setDataSlotValueECS(reqo);
                           } else if (workStepName.equals("PolicyChecker")) {
                              hm.put("PROCESSINSTANCENAME", processInstanceName);
                              hm.put("DATASLOTNAME", "PolicyStatus");
                              hm.put("DATASLOTVALUE", plcStatus);
                              reqo = this.getRequestObject(hm);
                              this.setDataSlotValueECS(reqo);
                           } else {
                              automatictasklog.warn("ECS Savvion: assignTaskECS Service:: " + workItemName[index] + " Already Acquired by " + performer);
                           }
                        }

                        if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
                           getBizLogicManager().reassignWorkitemPerformer(sessionId, workItemName[index], toBeAssignedUserId);
                        }
                     }

                     ++index;
                  }
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var28) {
            responseCode = "5050";
            automatictasklog.error("ECS Savvion: assignTaskECS Service:::: Catch Exception :::" + var28.getMessage());
         } finally {
            try {
               this.disconnect(sessionId);
            } catch (Exception var27) {
               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var27.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: assignTaskECS Service :::: END " + responseCode);
      return resObj;
   }

   public ResponseObject makeAvaibaleTaskECS(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: assignTaskECS Service ::START");
      new HashMap();
      HashMap hm = new HashMap();
      String[] workItemName = (String[])null;
      ResponseObject resObj = new ResponseObject();
      String sessionId = null;
      String performer = null;
      String[] splitWorkItem = new String[5];
      String workStepName = null;
      String processInstanceName = null;
      String responseCode = null;
      String plcStatus = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: assignTaskECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         try {
            HashMap hashMap = this.getMap(reqObj);
            automatictasklog.info("ECS Savvion: assignTaskECS Service:::: INPUT VALUES " + hashMap);
            workItemName = (String[])hashMap.get("WORKITEMNAMES");
            plcStatus = (String)hashMap.get("POLICYSTATUS");
            if (workItemName != null && !workItemName.equals("")) {
               int index = 0;

               while(true) {
                  if (index >= workItemName.length) {
                     responseCode = "5000";
                     break;
                  }

                  if (workItemName[index] == null && !workItemName[index].equals("null")) {
                     automatictasklog.warn("ECS Savvion: assignTaskECS Service::::WorkItem[" + index + "] is NULL");
                  } else {
                     performer = this.getWorkItemPerformerECS(workItemName[index]);
                     BLServer blserver = BLClientUtil.getBizLogicServer();
                     Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
                     WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
                        automatictasklog.warn("ECS AssignTask enterred");
                        wi.makeAvailable();
                        wi.save();
                        splitWorkItem = workItemName[index].split("::");
                        processInstanceName = splitWorkItem[0];
                        workStepName = splitWorkItem[1];
                        RequestObject[] reqo;
                        if (workStepName.equals("PolicyMaker")) {
                           hm.put("PROCESSINSTANCENAME", processInstanceName);
                           hm.put("DATASLOTNAME", "PolicyStatus");
                           hm.put("DATASLOTVALUE", plcStatus);
                           reqo = this.getRequestObject(hm);
                           this.setDataSlotValueECS(reqo);
                        } else if (workStepName.equals("PolicyChecker")) {
                           hm.put("PROCESSINSTANCENAME", processInstanceName);
                           hm.put("DATASLOTNAME", "PolicyStatus");
                           hm.put("DATASLOTVALUE", plcStatus);
                           reqo = this.getRequestObject(hm);
                           this.setDataSlotValueECS(reqo);
                        } else {
                           automatictasklog.warn("ECS Savvion: assignTaskECS Service:: " + workItemName[index] + " Already Acquired by " + performer);
                        }
                     }
                  }

                  ++index;
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var26) {
            responseCode = "5050";
            automatictasklog.error("ECS Savvion: assignTaskECS Service:::: Catch Exception :::" + var26.getMessage());
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var25) {
               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var25.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: assignTaskECS Service :::: END " + responseCode);
      return resObj;
   }

   public ResponseObject setDataSlotValueECS(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: setDataSlotValue Service:::::START");
      String result = null;
      String sessionId = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            if (processInstanceName != null && !processInstanceName.equals("")) {
               if (userId != null && !userId.equals("")) {
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  if (!this.checkNull(hashMap.get("POLICYSTATUS"))) {
                     hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                  }

                  if (!this.checkNull(hashMap.get("BRANCH"))) {
                     hm.put("Branch", hashMap.get("BRANCH").toString());
                  }

                  if (!this.checkNull(hashMap.get("CITY"))) {
                     hm.put("City", hashMap.get("CITY").toString());
                  }

                  if (!this.checkNull(hashMap.get("ISPOLICYCHECKERACCEPTED"))) {
                     hm.put("isPolicyCheckerAccepted", hashMap.get("ISPOLICYCHECKERACCEPTED").toString());
                  }

                  if (!this.checkNull(hashMap.get("PMUSER"))) {
                     hm.put("PMUser", hashMap.get("PMUSER").toString());
                  }

                  if (!this.checkNull(hashMap.get("PCHUSER"))) {
                     hm.put("PCHUser", hashMap.get("PCHUSER").toString());
                  }

                  String isComplete;
                  if (!this.checkNull(hashMap.get("POLICYCHECKERREMARK"))) {
                     isComplete = (String)pi.getDataSlotValue("PolicyCheckerRemark");
                     if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                        isComplete = isComplete + "|" + hashMap.get("POLICYCHECKERREMARK").toString();
                     } else {
                        isComplete = hashMap.get("POLICYCHECKERREMARK").toString();
                     }

                     hm.put("PolicyCheckerRemark", isComplete);
                  }

                  if (!this.checkNull(hashMap.get("POLICYMAKERREMARK"))) {
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
                     if (!this.checkNull(hashMap.get("ISCOMPLETE"))) {
                        isComplete = hashMap.get("ISCOMPLETE").toString();
                        if (!this.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           this.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
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
         } catch (Exception var22) {
            responseCode = "5034";
            var22.printStackTrace();
            automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var22.getMessage());
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var21) {
               var21.printStackTrace();
               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Finally Exception:::" + var21.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.error("ECS Savvion: setDataslotValue Service::::END " + responseCode);
      return resObj;
   }

   public ResponseObject UpdatePolicyConfigurationSetUpDataSlots(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: setDataSlotValue Service:::::START");
      String result = null;
      String sessionId = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            if (processInstanceName != null && !processInstanceName.equals("")) {
               if (userId != null && !userId.equals("")) {
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  if (!this.checkNull(hashMap.get("POLICYSTATUS"))) {
                     hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                  }

                  if (!this.checkNull(hashMap.get("BRANCH"))) {
                     hm.put("Branch", hashMap.get("BRANCH").toString());
                  }

                  if (!this.checkNull(hashMap.get("CITY"))) {
                     hm.put("City", hashMap.get("CITY").toString());
                  }

                  if (!this.checkNull(hashMap.get("ISPOLICYCHECKERACCEPTED"))) {
                     hm.put("isPolicyCheckerAccepted", hashMap.get("ISPOLICYCHECKERACCEPTED").toString());
                  }

                  String isComplete;
                  if (!this.checkNull(hashMap.get("POLICYCHECKERREMARKS"))) {
                     isComplete = (String)pi.getDataSlotValue("PolicyCheckerRemarks");
                     if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                        isComplete = isComplete + "|" + hashMap.get("POLICYCHECKERREMARKS").toString();
                     } else {
                        isComplete = hashMap.get("POLICYCHECKERREMARK").toString();
                     }

                     hm.put("PolicyCheckerRemarks", isComplete);
                  }

                  if (!this.checkNull(hashMap.get("POLICYCONFIGURATORREMARKS"))) {
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
                     if (!this.checkNull(hashMap.get("ISCOMPLETE"))) {
                        isComplete = hashMap.get("ISCOMPLETE").toString();
                        if (!this.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           this.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
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
         } catch (Exception var22) {
            responseCode = "5034";
            var22.printStackTrace();
            automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var22.getMessage());
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var21) {
               var21.printStackTrace();
               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Finally Exception:::" + var21.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.error("ECS Savvion: setDataslotValue Service::::END " + responseCode);
      return resObj;
   }

   public ResponseObject UpdatePolicyEnrollmentSetUpDataSlots(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: setDataSlotValue Service:::::START");
      String result = null;
      String sessionId = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            if (processInstanceName != null && !processInstanceName.equals("")) {
               if (userId != null && !userId.equals("")) {
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  if (!this.checkNull(hashMap.get("POLICYSTATUS"))) {
                     hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                  }

                  if (!this.checkNull(hashMap.get("BRANCH"))) {
                     hm.put("Branch", hashMap.get("BRANCH").toString());
                  }

                  if (!this.checkNull(hashMap.get("CITY"))) {
                     hm.put("City", hashMap.get("CITY").toString());
                  }

                  if (!this.checkNull(hashMap.get("EMUSER"))) {
                     hm.put("Maker", hashMap.get("EMUSER").toString());
                  }

                  String isComplete;
                  if (!this.checkNull(hashMap.get("ENROLLMENTMAKERREMARKS"))) {
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
                     if (!this.checkNull(hashMap.get("ISCOMPLETE"))) {
                        isComplete = hashMap.get("ISCOMPLETE").toString();
                        if (!this.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           this.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
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
         } catch (Exception var22) {
            responseCode = "5034";
            var22.printStackTrace();
            automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var22.getMessage());
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var21) {
               var21.printStackTrace();
               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Finally Exception:::" + var21.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.error("ECS Savvion: setDataslotValue Service::::END " + responseCode);
      return resObj;
   }

   public ResponseObject UpdatePolicyEndorsementSetUpDataSlots(RequestObject[] reqObj) {
      automatictasklog.info("ECS Savvion: setDataSlotValue Service:::::START");
      String result = null;
      String sessionId = null;
      String responseCode = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String userId = (String)hashMap.get("USERID");

         try {
            if (processInstanceName != null && !processInstanceName.equals("")) {
               if (userId != null && !userId.equals("")) {
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  if (!this.checkNull(hashMap.get("POLICYSTATUS"))) {
                     hm.put("PolicyStatus", hashMap.get("POLICYSTATUS").toString());
                  }

                  if (!this.checkNull(hashMap.get("BRANCH"))) {
                     hm.put("Branch", hashMap.get("BRANCH").toString());
                  }

                  if (!this.checkNull(hashMap.get("CITY"))) {
                     hm.put("City", hashMap.get("CITY").toString());
                  }

                  if (!this.checkNull(hashMap.get("TRANSACTIONTYPE"))) {
                     hm.put("transactionType", hashMap.get("TRANSACTIONTYPE").toString());
                  }

                  if (!this.checkNull(hashMap.get("ISENDORSEMENTCHECKERACCEPTED"))) {
                     hm.put("isEDMCheckerAccepted", hashMap.get("ISENDORSEMENTCHECKERACCEPTED").toString());
                  }

                  String isComplete;
                  if (!this.checkNull(hashMap.get("ENROLLMENTMAKERREMARKS"))) {
                     isComplete = (String)pi.getDataSlotValue("MakerRemarks");
                     if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                        isComplete = isComplete + "|" + hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                     } else {
                        isComplete = hashMap.get("ENROLLMENTMAKERREMARKS").toString();
                     }

                     hm.put("EnrollmentMakerRemarks", isComplete);
                  }

                  if (!this.checkNull(hashMap.get("ENDORSEMENTMAKERREMARKS"))) {
                     isComplete = (String)pi.getDataSlotValue("EndorsementMakerRemarks");
                     if (isComplete != null && !isComplete.equalsIgnoreCase("<null>")) {
                        isComplete = isComplete + "|" + hashMap.get("ENDORSEMENTMAKERREMARKS").toString();
                     } else {
                        isComplete = hashMap.get("ENDORSEMENTMAKERREMARKS").toString();
                     }

                     hm.put("EnrollmentMakerRemarks", isComplete);
                  }

                  if (!this.checkNull(hashMap.get("ENDORSEMENTCHECKERREMARKS"))) {
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
                     if (!this.checkNull(hashMap.get("ISCOMPLETE"))) {
                        isComplete = hashMap.get("ISCOMPLETE").toString();
                        if (!this.checkNull(hashMap.get("WORKITEMNAME")) && isComplete.equalsIgnoreCase(new String("1"))) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
                           this.completeWorkitem((new Long(blsession.getID())).toString(), hashMap.get("WORKITEMNAME").toString());
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
         } catch (Exception var22) {
            responseCode = "5034";
            var22.printStackTrace();
            automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var22.getMessage());
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var21) {
               var21.printStackTrace();
               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Finally Exception:::" + var21.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.error("ECS Savvion: setDataslotValue Service::::END " + responseCode);
      return resObj;
   }

   public ResponseObject getDataSlotValueECS(RequestObject[] reqObj) {
      automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::START");
      String dataslotValue = null;
      String responseCode = null;
      String result = "5034";
      String sessionId = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: getDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.debug("ECS Savvion: getDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
         automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::INPUT VALUES " + hashMap);
         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
         String dataslotName = (String)hashMap.get("DATASLOTNAME");

         try {
            if (processInstanceName != null && dataslotName != null && !processInstanceName.equals("") && !dataslotName.equals("")) {
               sessionId = connectECSADMIN();
               if (!sessionId.equals("false")) {
                  automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::" + sessionId + "::" + processInstanceName);
                  dataslotValue = getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dataslotName).toString();
                  responseCode = "5000";
                  if (dataslotValue != null) {
                     resObj.setResultStringArray(new String[]{dataslotValue});
                  }
               } else {
                  responseCode = "5030";
               }
            } else {
               automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::processInstanceName is--- " + processInstanceName + "--- or dataslotName is---" + dataslotName);
               responseCode = "5021";
            }
         } catch (Exception var19) {
            responseCode = "5034";
            automatictasklog.error("ECS Savvion: getDataslotValue Service::::Catch Exception:::" + var19.getMessage());
         } finally {
            try {
               this.disconnect(sessionId);
            } catch (Exception var18) {
               automatictasklog.error("ECS Savvion: getDataslotValu Service::::Finally Exception:::" + var18.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.debug("ECS Savvion: getDataSlotValue Service  :::: END " + responseCode);
      return resObj;
   }

   public ResponseObject getClaimStatusECS(RequestObject[] reqObj) {
      automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::START");
      String processInstanceName = null;
      String sessionId = null;
      String responseCode = null;
      ArrayList workItemNames = new ArrayList();
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: getClaimStatusECS Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: getClaimStatusECS Service:::: INPUT VALUES " + hashMap);
         String processInstanceValue = (String)hashMap.get("PROCESSINSTANCEVALUE");

         try {
            if (processInstanceValue != null && !processInstanceValue.equals("")) {
               long processInstanceId = Long.parseLong(processInstanceValue);
               BLServer blServer = BizLogicWSUtil.getBizLogic();
               Session blSession = blServer.connect("ECSAdmin", "ECSAdmin");
               sessionId = connectECSADMIN();
               ProcessInstance processInstance = ProcessInstance.get(blSession, processInstanceId);
               processInstanceName = processInstance.getName();
               String[] workStepNames = processInstance.getActivatedWorkStepNames();
               int i = 0;
               int ws = 0;

               while(true) {
                  if (ws >= workStepNames.length) {
                     responseCode = "5000";
                     break;
                  }

                  Vector workItemList = getBizLogicManager().getWorkitemList(sessionId, processInstanceName, workStepNames[ws]);

                  for(int k = 0; k < workItemList.size(); ++k) {
                     String[] a = (String[])workItemList.get(k);
                     workItemNames.add(a[0]);
                  }

                  ++ws;
               }
            } else {
               responseCode = "5013";
            }
         } catch (Exception var29) {
            responseCode = "5032";
            automatictasklog.error("ECS Savvion: getClaimStatusECS Service::::Catch Exception:::" + var29.getMessage());
         } finally {
            try {
               boolean session = this.disconnect(sessionId);
               automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::session flag ::" + session);
            } catch (Exception var28) {
               automatictasklog.error("ECS Savvion: getDataslotValu Service::::Finally Exception:::" + var28.getMessage());
            }

         }

         String[] totalWorkitems = new String[workItemNames.size()];
         workItemNames.toArray(totalWorkitems);
         workItemObject = this.getWorkItemObjectArray(totalWorkitems);
         resObj.setResponseCode(responseCode);
         resObj.setResultworkItemArray(workItemObject);
      }

      automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::END " + responseCode);
      return resObj;
   }

   private RequestObject[] getRequestObject(HashMap hm) {
      RequestObject[] reqObj = new RequestObject[hm.size()];
      Iterator it = hm.entrySet().iterator();
      int mapSize = hm.size();
      int counter = 0;

      try {
         if (mapSize != 0) {
            while(it.hasNext()) {
               reqObj[counter] = new RequestObject();
               Entry en = (Entry)it.next();
               System.out.println("GET KEY::" + en.getKey());
               System.out.println("GET VALUE-1::" + en.getValue());
               System.out.println("GET VALUE-2::" + en.getValue());
               reqObj[counter].setKey((String)en.getKey());
               reqObj[counter].setValue((String)en.getValue());
               reqObj[counter].setValue((String)en.getValue());
               ++counter;
            }
         } else {
            automatictasklog.error("ECS Savvion:getRequestObject Utility Service::::Request Object Length :: " + mapSize);
         }
      } catch (Exception var8) {
         automatictasklog.error("ECS Savvion:getRequestObject Utility Service::::FAIULIRE EXCEPTION ");
      }

      return reqObj;
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

      automatictasklog.debug("ECS printUserRoleObject Utility Service::::END");
   }

   private HashMap getMap(RequestObject[] reqObj) {
      int arrayObjLength = 0;
      HashMap hm = new HashMap();
      arrayObjLength = reqObj.length;
      automatictasklog.debug("ECS Savvion:getMap Utility Service::::Request Object Length :: " + arrayObjLength);

      try {
         if (arrayObjLength != 0) {
            for(int i = 0; i < arrayObjLength; ++i) {
               String key = reqObj[i].getKey();
               System.out.println("Key is :" + key);
               if (key != null) {
                  Object value = null;
                  if (reqObj[i].getValue() != null) {
                     value = reqObj[i].getValue();
                  }

                  if (reqObj[i].getValueArray() != null) {
                     value = reqObj[i].getValueArray();
                  }

                  if (reqObj[i].getUserRoleObj() != null) {
                     value = reqObj[i].getUserRoleObj();
                  }

                  System.out.println("Value is :" + value);
                  hm.put(key, value);
               }
            }
         } else {
            automatictasklog.error("ECS Savvion:getMap Utility Service::::Request Object Length :: " + arrayObjLength);
         }
      } catch (Exception var7) {
         automatictasklog.error("ECS Savvion:getMap Utility Service:::: Exception :" + var7);
      }

      return hm;
   }

   public String getUserPasswordECS(String userId) {
      automatictasklog.debug("ECS Savvion: getUserPasswordECS Service::START userId:: " + userId);
      String password = "";

      try {
         User userObject = UserManager.getUser(userId);
         password = userObject.getAttribute("password");
         PService p = PService.self();
         password = p.decrypt(password);
      } catch (Exception var5) {
         automatictasklog.error("ECS Savvion: getUserPasswordECS Service::ERROR:: USERID is " + userId + var5.getMessage());
      }

      automatictasklog.debug("ECS Savvion: getUserPasswordECS Service::END");
      return password;
   }

   public String connectUser(String userId) {
      String sessionId = null;
      boolean isMember = false;
      String password = null;

      try {
         password = this.getUserPasswordECS(userId);
         isMember = this.checkUserECS(userId, "ECS");
         if (isMember) {
            sessionId = getBizLogicManager().connect(userId, password);
         } else {
            automatictasklog.debug("ECS Savvion: connectUser Service::::User " + userId + " Does not belongs to ECS");
         }
      } catch (RemoteException var6) {
         automatictasklog.error("ECS Savvion: connectUser Service:::: Exception " + var6.getMessage());
         sessionId = "false" + var6.getMessage();
      }

      return sessionId;
   }

   public String getWorkItemPerformerECS(String workItemName) {
      String result = null;

      try {
         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::START");
         if (workItemName != null && !workItemName.equals("")) {
            result = (String)getBizLogicManager().getWorkitemInfo(connectECSADMIN(), workItemName).get("PERFORMER");
         } else {
            automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService Service::::processInstanceName is--- " + workItemName);
            result = "5021";
         }

         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::SUCCESS::::END" + result);
      } catch (Exception var4) {
         automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService:::: Exception:::" + var4.getMessage());
         return "5059";
      }

      automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::END");
      return result;
   }

   private WorkItemObject[] getWorkItemObjectArray(String[] workItems) {
      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START");
      int workItemArrayLength = 0;
      HashMap requestMap = new HashMap();
      HashMap inputMap = new HashMap();
      String[] wiNameInsub = (String[])null;
      workItemArrayLength = workItems.length;
      automatictasklog.info("ECS Savvion:getWorkItemObjectArray Utility Service::::workItemArrayLength " + workItemArrayLength);
      WorkItemObject[] woArray = new WorkItemObject[workItemArrayLength];
      String[] items = new String[10];
      String[] processInstancelist = new String[5];
      String sessionId = null;
      String[] result = new String[3];
      String processInstanceName = null;

      try {
         if (workItemArrayLength != 0) {
            sessionId = this.connectUser("ECSAdmin");

            for(int i = 0; i < workItemArrayLength; ++i) {
               if (workItems[i] != null) {
                  woArray[i] = new WorkItemObject();
                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::" + workItemArrayLength);
                  items = workItems[i].split("::");
                  processInstanceName = items[0].replace("[", "").replace("]", "").trim();
                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START1");
                  processInstancelist = items[0].split("#");
                  result = processInstancelist[0].split("-");
                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START2");
                  woArray[i].setName(workItems[i].trim());
                  woArray[i].setPiName(processInstanceName);
                  woArray[i].setWorkStepName(items[1]);
                  woArray[i].setPerformer(items[2]);
                  woArray[i].setPId(processInstancelist[1]);
                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START3");
                  woArray[i].setType(getBizLogicManager().getProcessTemplateName(sessionId, processInstanceName));
                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START4");
                  if (woArray[i].getType().equals("RGICL_ECS_FLOW")) {
                     requestMap.put("PROCESSINSTANCENAME", processInstanceName);
                     requestMap.put("DATASLOTNAME", "CMEscCounter");
                     RequestObject[] reqo1 = this.getRequestObject(requestMap);
                     ResponseObject reso1 = this.getDataSlotValueECS(reqo1);
                     wiNameInsub = reso1.getResultStringArray();
                     String cmeCounter = wiNameInsub[0];
                     inputMap.put("PROCESSINSTANCENAME", processInstanceName);
                     inputMap.put("DATASLOTNAME", "BSMEscCounter");
                     RequestObject[] reqo2 = this.getRequestObject(inputMap);
                     ResponseObject reso2 = this.getDataSlotValueECS(reqo2);
                     wiNameInsub = reso2.getResultStringArray();
                     String var18 = wiNameInsub[0];
                  }
               }

               automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END FOR " + i + "::" + workItems[i]);
            }
         } else {
            automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END  WorkItemObject Array :: " + woArray);
         }

         this.disconnect(sessionId);
      } catch (Exception var19) {
         automatictasklog.error("ECS Savvion:getWorkItemObjectArray Utility Service::::FAIULIRE EXCEPTION ");
      }

      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END");
      return woArray;
   }

   public WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
      WorkItemObject[] resultWorkitems = (WorkItemObject[])null;
      int inboxSize = 400;
      if (this.propertiesECSSavvion.getProperty("ECSInboxSize") != null) {
         inboxSize = Integer.parseInt(this.propertiesECSSavvion.getProperty("ECSInboxSize"));
      }

      automatictasklog.debug("ECS getResultWorkitems :: inboxSize = " + inboxSize);
      if (workitemlist.size() > inboxSize) {
         automatictasklog.info("ECS getResultWorkitems :: Trimming WorkItemObject Array to : " + inboxSize);
         WorkItemObject[] inboxSizeWorkitems = new WorkItemObject[inboxSize];

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

   public ResponseObject Create_WorkFlowForPolicyConfiguration(RequestObject[] reqObj) {
      humantasklog.info("ECS Savvion: Create_WorkFlowForPolicyConfiguration Service:::: START");
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      HashMap hm = new HashMap();
      HashMap inputMap = new HashMap();
      String responseCode = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: Create_WorkFlowForPolicyConfiguration Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: Create_WorkFlowForPolicyConfiguration Service:::: INPUT VALUES " + hashMap);
         String userId = (String)hashMap.get("USERID");
         String policyNumber = (String)hashMap.get("POLICYNUMBER");
         String branch = (String)hashMap.get("BRANCH");
         String region = (String)hashMap.get("REGION");
         String zone = (String)hashMap.get("ZONE");
         String corpOffice = (String)hashMap.get("CORPORATEOFFICE");
         String claimWorkItem = (String)hashMap.get("WORKITEMNAME");
         String processInstanceName = null;
         String sessionId = null;
         String result = null;
         String piName = policyNumber;
         String paymentVerificationUser = "RAMMGR";
         String mainProcessInstanceName = null;
         String[] items = new String[10];
         String TPAFlag = null;
         String policyRegionLocation = null;

         try {
            if (corpOffice != null && !"".equals(corpOffice) && zone != null && !"".equals(zone) && region != null && !"".equals(region) && branch != null && !"".equals(branch) && userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
               sessionId = this.connectUser(userId);
               if (!sessionId.equals("false")) {
                  items = claimWorkItem.split("::");
                  mainProcessInstanceName = items[0];
                  hm.put("PROCESSINSTANCENAME", mainProcessInstanceName);
                  hm.put("DATASLOTNAME", "TPAFlag");
                  RequestObject[] reqo = this.getRequestObject(hm);
                  ResponseObject reso = this.getDataSlotValueECS(reqo);
                  String[] wiNameInsub = reso.getResultStringArray();
                  TPAFlag = wiNameInsub[0];
                  inputMap.put("PROCESSINSTANCENAME", mainProcessInstanceName);
                  inputMap.put("DATASLOTNAME", "policyRegionLocation");
                  reqo = this.getRequestObject(inputMap);
                  reso = this.getDataSlotValueECS(reqo);
                  wiNameInsub = reso.getResultStringArray();
                  policyRegionLocation = wiNameInsub[0];
                  HashMap dsTypeMap = new HashMap();
                  dsTypeMap.put("PolicyNumber", "STRING");
                  dsTypeMap.put("PolicyRegion", "STRING");
                  dsTypeMap.put("PolicyZone", "STRING");
                  dsTypeMap.put("PolicyCorporateOffice", "STRING");
                  dsTypeMap.put("PolicyBranch", "STRING");
                  HashMap dsValues = new HashMap();
                  dsValues.put("PolicyNumber", policyNumber);
                  dsValues.put("PolicyRegion", region);
                  dsValues.put("PolicyZone", zone);
                  dsValues.put("PolicyCorporateOffice", corpOffice);
                  dsValues.put("PolicyBranch", branch);
                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
                  processInstanceName = this.createProcessInstance(sessionId, "RGICL_ECS_CONFIGURATION_FLOW", piName, "1", resolvedDSValues);
                  System.out.println("In Create_WorkFlowForPolicyConfiguration Method ProcessInstanceName :: " + processInstanceName);
                  responseCode = "5000";
               } else {
                  responseCode = "5030";
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var38) {
            humantasklog.info("ECS Savvion: Create_WorkFlowForPolicyConfiguration Service:::: Exception" + var38.getMessage());
         } finally {
            try {
               this.disconnect(sessionId);
            } catch (Exception var37) {
               humantasklog.error("ECS Savvion: Create_WorkFlowForPolicyConfiguration::::Finally Exception:::" + var37.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
         resObj.setResultStringArray(new String[]{processInstanceName});
      }

      humantasklog.info("ECS Savvion: Create_WorkFlowForPolicyConfiguration Service :::: END " + responseCode);
      return resObj;
   }

   public ResponseObject Create_WorkFlowForProductSetUp(RequestObject[] reqObj) {
      humantasklog.info("ECS Savvion: Create_WorkFlowForProductSetUp Service:::: START");
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      new HashMap();
      new HashMap();
      String responseCode = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         automatictasklog.error("ECS Savvion: Create_WorkFlowForProductSetUp Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         automatictasklog.info("ECS Savvion: Create_WorkFlowForProductSetUp Service:::: INPUT VALUES " + hashMap);
         String userId = (String)hashMap.get("USERID");
         String productNumber = (String)hashMap.get("PRODUCTYNUMBER");
         String processInstanceName = null;
         String sessionId = null;
         String result = null;
         String piName = productNumber;
         String paymentVerificationUser = "RAMMGR";
         String mainProcessInstanceName = null;
         String[] items = new String[10];
         String TPAFlag = null;
         Object var18 = null;

         try {
            if (userId != null && !userId.equals("") && productNumber != null && !productNumber.equals("")) {
               sessionId = this.connectUser(userId);
               if (!sessionId.equals("false")) {
                  HashMap dsTypeMap = new HashMap();
                  dsTypeMap.put("ProductNo", "STRING");
                  HashMap dsValues = new HashMap();
                  dsValues.put("ProductNo", productNumber);
                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
                  processInstanceName = this.createProcessInstance(sessionId, "RGICL_ECS_PRODUCT_SETUP_FLOW", piName, "1", resolvedDSValues);
                  System.out.println("In Create_WorkFlowForProductSetUp Method ProcessInstanceName :: " + processInstanceName);
                  responseCode = "5000";
               } else {
                  responseCode = "5030";
               }
            } else {
               responseCode = "5021";
            }
         } catch (Exception var30) {
            humantasklog.info("ECS Savvion: Create_WorkFlowForProductSetUp Service:::: Exception" + var30.getMessage());
         } finally {
            try {
               this.disconnect(sessionId);
            } catch (Exception var29) {
               humantasklog.error("ECS Savvion: Create_WorkFlowForProductSetUp::::Finally Exception:::" + var29.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
         resObj.setResultStringArray(new String[]{processInstanceName});
      }

      humantasklog.info("ECS Savvion: Create_WorkFlowForProductSetUp Service :::: END " + responseCode);
      return resObj;
   }
}
