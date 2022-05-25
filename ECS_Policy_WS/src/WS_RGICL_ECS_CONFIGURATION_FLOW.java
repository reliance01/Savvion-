import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.BizLogic.Server.PAKException;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
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
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import rgicl.ecs.savvion.policy.objectmodel.RequestObject;
import rgicl.ecs.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecs.savvion.policy.objectmodel.WorkItemObject;

public class WS_RGICL_ECS_CONFIGURATION_FLOW {
   private static BizLogicManager pak = null;
   private String ptName = "RGICL_ECS_CONFIGURATION_FLOW";
   private static Byte[] bytearray = new Byte[0];
   Properties propertiesECSSavvion;
   Properties propertiesECSLog;
   static Logger automatictasklog = null;
   private String responseCode = null;
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

   public WS_RGICL_ECS_CONFIGURATION_FLOW() {
      NLog.ws.debug("RGICL_ECS_CONFIGURATION_FLOW service invoked");
   }

   public String INITIATEFLOW(String policyNumber, String username, String password, String piName, String priority) throws AxisFault {
      NLog.ws.debug("START method invoked");
      String sessionId = this.connect(username, password);
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
      this.disconnect(sessionId);
      return pi;
   }

   public void POLICY_CHECKER(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("POLICY_CHECKER method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   public void POLICY_CONFIGURATOR(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("POLICY_CONFIGURATOR method invoked");
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

   public String connect(String userId, String password) throws AxisFault {
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

   public boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   public String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   public String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
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

   public ResponseObject Create_WorkFlowForPolicyConfiguration(RequestObject[] reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();
      new HashMap();
      ArrayList ls = new ArrayList();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         for(int j = 0; j < reqObj.length; ++j) {
            RequestObject[] _reqObj = new RequestObject[]{reqObj[j]};
            HashMap hashMap = this.getMap(_reqObj);
            if (hashMap != null && !hashMap.isEmpty()) {
               String policyNumber = (String)hashMap.get("POLICYNUMBER");
               String userId = (String)hashMap.get("USERID");
               if (userId == "" || userId == null) {
                  userId = "ECSAdmin";
               }

               String processInstanceName = this.propertiesECSSavvion.getProperty("ECSProcessInstanceName") + policyNumber + "-";
               String priority = this.propertiesECSSavvion.getProperty("priority");
               String[] workItemNames = (String[])null;
               WorkItemObject[] workItemObject = new WorkItemObject[100];
               String[] workItem = new String[100];
               RequestObject[] var14 = new RequestObject[3];

               try {
                  if (userId != null && !userId.equals("") && policyNumber != null && !policyNumber.equals("")) {
                     if (this.checkUserECS(userId, "ECS")) {
                        processInstanceName = this.INITIATEFLOW(policyNumber, userId, this.getUserPasswordECS(userId), processInstanceName, priority);
                     }

                     System.out.println("ProcessInstanceName :: " + processInstanceName);
                     this.responseCode = "5000";
                     ls.add(processInstanceName);
                  }
               } catch (Exception var16) {
                  this.responseCode = "5050";
                  var16.printStackTrace();
               }
            }
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

   public ResponseObject Get_AssignedTaskLists(RequestObject[] reqObj) {
      automatictasklog.info("==================================================================================================");
      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
      String sessionId = null;
      String sessionId1 = null;
      String taskList = null;
      String responseCode = null;
      String taskListArray = "5042";
      String[] responseList = (String[])null;
      ResponseObject resObj = new ResponseObject();
      ArrayList resultTaskList = new ArrayList();
      WorkItemObject[] workItemObject = (WorkItemObject[])null;
      new HashMap();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
      } else {
         HashMap hashMap = this.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         System.out.println("User id sent is" + userId);
         String _ptName = (String)hashMap.get("PROCESS_NAME");
         if (_ptName == null) {
            _ptName = this.ptName;
         }

         try {
            if (userId != null && !userId.equals("")) {
               if (this.checkUserECS(userId, "ECS")) {
                  System.out.println("Test123");
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
                  blserver.getProcessTemplate(blsession, _ptName);
                  QueryService qs = new QueryService(blsession);
                  QSWorkItemFilter wfil = new QSWorkItemFilter("Test", _ptName);
                  wfil.setFetchSize(0);
                  QSWorkItem qsw = qs.getWorkItem();
                  WorkItemList wlist = qsw.getList(wfil);
                  List wis = wlist.getList();
                  System.out.println("Size of wiList" + wis.size());

                  for(int j = 0; j < wis.size(); ++j) {
                     WorkItem wi = (WorkItem)wis.get(j);
                     System.out.println("Class Type" + wi.getClass().getName());
                     WorkItemObject wiOb = new WorkItemObject();
                     wiOb.setName(wi.getName());
                     wiOb.setPerformer(wi.getPerformer());
                     wiOb.setPId((new Long(wi.getProcessInstanceID())).toString());
                     wiOb.setWorkStepName(wi.getWorkStepName());
                     wiOb.setPiName(wi.getProcessInstanceName());
                     DataSlot ds = wi.getDataSlot("PolicyNumber");
                     wiOb.setPolicyNumber((String)ds.getValue());
                     ds = wi.getDataSlot("PolicyStatus");
                     wiOb.setPolicyStatus((String)ds.getValue());
                     resultTaskList.add(wiOb);
                  }

                  responseCode = "5000";
                  automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
                  blserver.disConnect(blsession);
               }

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
         } catch (Exception var26) {
            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var26.getMessage());
            responseCode = "5030";
            var26.printStackTrace();
         }

         resObj.setResponseCode(responseCode);
      }

      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
      automatictasklog.info("==================================================================================================");
      return resObj;
   }

   private HashMap getMap(RequestObject[] reqObj) {
      
      HashMap hm = new HashMap();
      int arrayObjLength = reqObj.length;
      automatictasklog.debug("ECS Savvion:getMap Utility Service::::Request Object Length :: " + arrayObjLength);

      try {
         if (arrayObjLength != 0) {
            for(int i = 0; i < arrayObjLength; ++i) {
               String key = reqObj[i].getKey();
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
}
