//import com.savvion.ejb.bizlogic.manager.BizLogicManager;
//import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
//import com.savvion.sbm.bizlogic.client.BLClientUtil;
//import com.savvion.sbm.bizlogic.server.ejb.BLServer;
//import com.savvion.sbm.bizlogic.server.svo.DateTime;
//import com.savvion.sbm.bizlogic.server.svo.Decimal;
//import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
//import com.savvion.sbm.bizlogic.server.svo.WorkItem;
//import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
//import com.savvion.sbm.bizlogic.server.svo.XML;
//import com.savvion.sbm.bizlogic.util.Session;
//import com.savvion.sbm.util.PService;
//import com.savvion.sbm.util.SBMHomeFactory;
//import com.savvion.sbm.util.ServiceLocator;
//import com.savvion.util.NLog;
//import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
//import com.tdiinc.BizLogic.Server.PAKException;
//import com.tdiinc.userManager.AdvanceGroup;
//import com.tdiinc.userManager.Realm;
//import com.tdiinc.userManager.User;
//import com.tdiinc.userManager.UserManager;
//import java.io.FileInputStream;
//import java.math.BigDecimal;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.StringTokenizer;
//import java.util.Vector;
//import java.util.Map.Entry;
//import org.apache.axis.AxisFault;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
//import rgicl.ecsenhancement.savvion.policy.objectmodel.RequestObject;
//import rgicl.ecsenhancement.savvion.policy.objectmodel.ResponseObject;
//import rgicl.ecsenhancement.savvion.policy.objectmodel.WorkItemObject;
//import rgicl.ecsenhancement.savvion.policy.util.db.DBUtility;
//
//
public class RGICL_ECSEnhancements_WS {
	
}
//   private static BizLogicManager pak = null;
//   private static Byte[] bytearray = new Byte[0];
//   final String SBM_HOME = System.getProperty("sbm.home");
//   final String ECS_SAVVION_PROPERTIES;
//   final String ECS_SAVVION_LOG_PROPERTIES;
//   Properties propertiesECSSavvion;
//   Properties propertiesECSLog;
//   static Logger automatictasklog = null;
//   static Logger humantasklog = null;
//   private static String ECSADMIN = null;
//   private static Byte[] bytearrayECSADMIN = new Byte[0];
//   private int responseCode;
//   private String responseMessage;
//   private static final String ECSRRPTName = "RGICL_ECS_RR_FLOW";
//   private static final String ECSNetworkPTName = "RGICL_ECS_NETWORK_FLOW";
//   private static final String ECSALPTName = "RGICL_ECS_AL_FLOW";
//   private static final String ECSCLPTName = "RGICL_ECS_CL_FLOW";
//   private static final String ECSProductFlowPTName = "RGICL_ECS_PRODUCT_FLOW";
//   final String priority;
//   final int SUCCESS;
//   final int USER_ID_NULL;
//   final int USER_NOT_AUTHORIZED;
//   final int USER_NAME_NULL;
//   final int USER_ROLE_NULL;
//   final int PI_NAME_NULL;
//   final int PI_ID_NULL;
//   final int DS_NAME_NULL;
//   final int DS_VALUE_NULL;
//   final int WINAME_NULL;
//   final int INPUT_VALUE_IS_NULL;
//   final int USER_ALREADY_MAPPED;
//   final int USER_NOT_MAPPED;
//   final int USER_ID_INVALID;
//   final int USER_ROLE_INVALID;
//   final int PI_ID_INVALID;
//   final int PI_NAME_INVALID;
//   final int DS_NAME_INVALID;
//   final int DS_VALUE_INVALID;
//   final int INVALID_INPUT;
//   final int INVALID_WINAME;
//   final int WINAME_NOT_FOUND;
//   final int NEXT_WORKITEM_NOT_FOUND;
//   final int USER_CREATION_FAILED;
//   final int GROUPNAME_NOT_EXIST;
//   final int GROUP_CREATION_FAILED;
//   final int GROUP_REMOVE_FAILED;
//   final int COMMON_INBOX_EMPTY;
//   final int ASSIGNED_INBOX_EMPTY;
//   final int FAILURE_EXCEPTION;
//   final int DATABASE_ERROR;
//   final int INVALID_REQUEST;
//   final int REQUEST_OBJECT_IS_NULL;
//   final String ECSGroup;
//   static final String ECSAdminUserName = "ECSAdmin";
//   static final String ECSAdminPassword = "ECSAdmin";
//
//   public RGICL_ECSEnhancements_WS() {
//      this.ECS_SAVVION_PROPERTIES = this.SBM_HOME + "/conf/ECS_SAVVION_PROPERTIES.properties";
//      this.ECS_SAVVION_LOG_PROPERTIES = this.SBM_HOME + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
//      this.responseCode = 0;
//      this.responseMessage = "";
//      this.priority = "medium";
//      this.SUCCESS = 5000;
//      this.USER_ID_NULL = 5001;
//      this.USER_NOT_AUTHORIZED = 5002;
//      this.USER_NAME_NULL = 5003;
//      this.USER_ROLE_NULL = 5004;
//      this.PI_NAME_NULL = 5005;
//      this.PI_ID_NULL = 5006;
//      this.DS_NAME_NULL = 5007;
//      this.DS_VALUE_NULL = 5008;
//      this.WINAME_NULL = 5009;
//      this.INPUT_VALUE_IS_NULL = 5010;
//      this.USER_ALREADY_MAPPED = 5011;
//      this.USER_NOT_MAPPED = 5012;
//      this.USER_ID_INVALID = 5013;
//      this.USER_ROLE_INVALID = 5014;
//      this.PI_ID_INVALID = 5015;
//      this.PI_NAME_INVALID = 5016;
//      this.DS_NAME_INVALID = 5017;
//      this.DS_VALUE_INVALID = 5018;
//      this.INVALID_INPUT = 5019;
//      this.INVALID_WINAME = 5020;
//      this.WINAME_NOT_FOUND = 5021;
//      this.NEXT_WORKITEM_NOT_FOUND = 5022;
//      this.USER_CREATION_FAILED = 5023;
//      this.GROUPNAME_NOT_EXIST = 5024;
//      this.GROUP_CREATION_FAILED = 5026;
//      this.GROUP_REMOVE_FAILED = 5027;
//      this.COMMON_INBOX_EMPTY = 5033;
//      this.ASSIGNED_INBOX_EMPTY = 5034;
//      this.FAILURE_EXCEPTION = 5035;
//      this.DATABASE_ERROR = 5036;
//      this.INVALID_REQUEST = 5037;
//      this.REQUEST_OBJECT_IS_NULL = 5038;
//      this.ECSGroup = "ECS";
//
//      try {
//         NLog.ws.debug("RGICL_ECSEnhancements_WS service invoked");
//         this.propertiesECSSavvion = new Properties();
//         this.propertiesECSSavvion.load(new FileInputStream(this.ECS_SAVVION_PROPERTIES));
//         this.propertiesECSLog = new Properties();
//         this.propertiesECSLog.load(new FileInputStream(this.ECS_SAVVION_LOG_PROPERTIES));
//         PropertyConfigurator.configure(this.propertiesECSLog);
//         automatictasklog = Logger.getLogger("Automatic");
//         humantasklog = Logger.getLogger("Human");
//      } catch (Exception var2) {
//      }
//
//   }
//
//   private void activateProcessInstance(String sessionId, String piName) throws AxisFault {
//      try {
//         getBizLogicManager().activateProcessInstance(sessionId, piName);
//      } catch (RemoteException var4) {
//         throw new AxisFault("SBM Web services error :" + var4.getMessage());
//      } catch (PAKException var5) {
//         throw new AxisFault("SBM Web services error :" + var5.getMessage());
//      }
//   }
//
//   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
//      try {
//         getBizLogicManager().completeWorkitem(sessionId, wiName);
//      } catch (RemoteException var4) {
//         throw new AxisFault("SBM Web services error :" + var4.getMessage());
//      }
//   }
//
//   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
//      try {
//         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
//         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
//      } catch (RemoteException var4) {
//         throw new AxisFault("SBM Web services error :" + var4.getMessage());
//      }
//   }
//
//   private static BizLogicManager getBizLogicManager() throws AxisFault {
//      if (pak == null) {
//         synchronized(bytearray) {
//            if (pak == null) {
//               try {
//                  String appserver = ServiceLocator.self().getAppServerID();
//                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
//                  pak = blmhome.create();
//               } catch (Throwable var3) {
//                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
//               }
//            }
//         }
//      }
//
//      return pak;
//   }
//
//   private String connect(String userId, String password) throws AxisFault {
//      String sessionId = null;
//
//      try {
//         sessionId = getBizLogicManager().connect(userId, password);
//         return sessionId;
//      } catch (RemoteException var5) {
//         throw new AxisFault("SBM Web services error :" + var5.getMessage());
//      }
//   }
//
//   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
//      String pi = null;
//
//      try {
//         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
//         return pi;
//      } catch (RemoteException var8) {
//         throw new AxisFault("SBM Web services error :" + var8.getMessage());
//      }
//   }
//
//   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
//      try {
//         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
//      } catch (RemoteException var5) {
//         throw new AxisFault("SBM Web services error :" + var5.getMessage());
//      }
//   }
//
//   private boolean disconnect(String sessionId) {
//      try {
//         getBizLogicManager().disconnect(sessionId);
//         return true;
//      } catch (Exception var3) {
//         return false;
//      }
//   }
//
//   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
//      Object obj = null;
//
//      try {
//         obj = getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
//      } catch (RemoteException var6) {
//         throw new AxisFault("SBM Web services error :" + var6.getMessage());
//      } catch (PAKException var7) {
//         throw new AxisFault("SBM Web services error :" + var7.getMessage());
//      }
//
//      return obj instanceof String ? (String)obj : "NST";
//   }
//
//   private boolean checkNull(Object dsValue) {
//      return dsValue == null || dsValue instanceof String && dsValue.equals("<NULL>");
//   }
//
//   private Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
//      Hashtable resolvedValues = new Hashtable();
//      if (valueMap != null && valueMap.size() != 0) {
//         Iterator sIterator = dsTypes.entrySet().iterator();
//
//         while(true) {
//            while(sIterator.hasNext()) {
//               Entry sEntry = (Entry)sIterator.next();
//               String key = (String)sEntry.getKey();
//               String type = (String)sEntry.getValue();
//               Object dsvalue = valueMap.get(key);
//               if (this.checkNull(dsvalue)) {
//                  resolvedValues.put(key, "<NULL>");
//               } else if (!type.equals("DOCUMENT")) {
//                  String[] data;
//                  if (type.equals("LIST")) {
//                     data = (String[])dsvalue;
//                     int size = data.length;
//                     Vector v = new Vector(size);
//
//                     for(int i = 0; i < size; ++i) {
//                        v.add(data[i]);
//                     }
//
//                     resolvedValues.put(key, v);
//                  } else if (type.equals("XML")) {
//                     XML xml = new XML((String)dsvalue);
//                     resolvedValues.put(key, xml);
//                  } else if (type.equals("DECIMAL")) {
//                     Decimal dec = new Decimal((BigDecimal)dsvalue);
//                     resolvedValues.put(key, dec);
//                  } else if (type.equals("DATETIME")) {
//                     Calendar cal = (Calendar)dsvalue;
//                     DateTime dt = new DateTime(cal.getTimeInMillis());
//                     resolvedValues.put(key, dt);
//                  } else if (!type.equals("MAP")) {
//                     resolvedValues.put(key, dsvalue);
//                  } else {
//                     data = (String[])dsvalue;
//                     Map hm = new HashMap();
//
//                     for(int i = 0; i < data.length; ++i) {
//                        StringTokenizer st = new StringTokenizer(data[i], "=");
//                        hm.put(st.nextToken(), st.nextToken());
//                     }
//
//                     resolvedValues.put(key, hm);
//                  }
//               }
//            }
//
//            return resolvedValues;
//         }
//      } else {
//         return resolvedValues;
//      }
//   }
//
//   private boolean checkUserECS(String userId, String groupName) {
//      boolean isMember = false;
//      String[] groupNames = new String[50];
//
//      try {
//         automatictasklog.info("ECS Savvion: checkUserECS Service:::: userId is " + userId + " to check isMember of group name :: " + groupName);
//         User usr = UserManager.getUser(userId);
//         automatictasklog.info("ECS Savvion: checkUserECS Service:::: user object  is " + usr);
//         groupNames = usr.getGroupNames();
//         automatictasklog.info("ECS Savvion: checkUserECS Service:::: user groups are " + groupNames + " :: groupNames length :: " + groupNames.length);
//
//         for(int i = 0; i < groupNames.length; ++i) {
//            if (groupNames[i].equals(groupName)) {
//               isMember = true;
//            }
//         }
//
//         automatictasklog.info("ECS Savvion: checkUserECS Service:::: userId is " + userId + " isMember of group name :: " + groupName + " :: " + isMember);
//      } catch (Exception var7) {
//         automatictasklog.error("ECS Savvion: checkUserECS Service:::: Exception " + var7.getMessage());
//      }
//
//      return isMember;
//   }
//
//   private ResponseObject mapUserToECSGroup(RequestObject[] reqObj) {
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         automatictasklog.info("ECS Savvion: mapUserToECSGroup Service:::: Map user START");
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//
//         try {
//            automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service START ::: INPUT VALUES are userId" + userId);
//            if (!userId.equals("") && userId != null) {
//               Realm realm = UserManager.getDefaultRealm();
//               boolean result = realm.addUser(userId);
//               if (result) {
//                  User sbmUser = realm.getUser(userId);
//                  sbmUser.setAttribute("password", userId);
//               }
//
//               if (!this.checkUserECS(userId, "ECS")) {
//                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
//                  status = groupObj.addUserMember(userId);
//                  if (status) {
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                  } else {
//                     responseCode = 5035;
//                     responseMessage = "FAILURE_EXCEPTION";
//                  }
//               } else {
//                  responseCode = 5011;
//                  responseMessage = "USER_ALREADY_MAPPED";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var12) {
//            automatictasklog.error("ECS Savvion: mapUserToECSGroup Service:::: Exception " + var12.getMessage());
//         }
//
//         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service END ::: " + responseCode + " :: Response Message :: " + responseMessage);
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      return resObj;
//   }
//
//   public ResponseObject removeUserFromECSGroup(RequestObject[] reqObj) {
//      String result = null;
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//
//         try {
//            if (!userId.equals("") && userId != null) {
//               if (this.checkUserECS(userId, "ECS")) {
//                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
//                  status = groupObj.removeUserMember(userId);
//                  if (status) {
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                  } else {
//                     responseCode = 5035;
//                     responseMessage = "FAILURE_EXCEPTION";
//                  }
//
//                  automatictasklog.debug("ECS Savvion: removeUserFromECS Service::::userId is " + userId + " status is " + status);
//               } else {
//                  responseCode = 5012;
//                  responseMessage = "USER_NOT_MAPPED";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var11) {
//            automatictasklog.error("ECS Savvion: removeUserFromECS Service:::: Exception " + var11.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.debug("ECS Savvion: removeUserFromECS Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private static String connectECSADMIN() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(ECSADMIN);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectECSADMIN Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayECSADMIN) {
//         if (ECSADMIN == null || !valid) {
//            try {
//               ECSADMIN = getBizLogicManager().connect("ECSAdmin", "ECSAdmin");
//            } catch (Exception var3) {
//               ECSADMIN = "false";
//               automatictasklog.error("ECS Savvion: connectECSADMIN Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return ECSADMIN;
//   }
//
//   public ResponseObject makeAvailableTaskECS(RequestObject[] reqObj) {
//      String methodName = "makeAvailableTaskECS";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service ::START");
//      new HashMap();
//      new HashMap();
//      String[] workItemName = (String[])null;
//      ResponseObject resObj = new ResponseObject();
//      String sessionId = null;
//      String performer = null;
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      int responseCode = 0;
//      String responseMessage = "";
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//            workItemName = (String[])hashMap.get("WORKITEMNAMES");
//            if (workItemName != null && !workItemName.equals("")) {
//               int index = 0;
//
//               while(true) {
//                  if (index >= workItemName.length) {
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                     break;
//                  }
//
//                  if (workItemName[index] == null && !workItemName[index].equals("null")) {
//                     automatictasklog.warn("ECS Savvion: " + methodName + " Service::::WorkItem[" + index + "] is NULL");
//                  } else {
//                     performer = this.getWorkItemPerformerECS(workItemName[index]);
//                     BLServer blserver = BLClientUtil.getBizLogicServer();
//                     Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
//                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
//                        automatictasklog.warn("ECS Available Task enterred");
//                        wi.makeAvailable();
//                        wi.save();
//                        automatictasklog.warn("ECS Task made available to the group");
//                     } else {
//                        automatictasklog.warn("ECS Savvion: " + methodName + " Service:: " + workItemName[index] + " Already Acquired by " + performer);
//                     }
//                  }
//
//                  ++index;
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var24) {
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Catch Exception :::" + var24.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var23) {
//               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var23.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setWorkItemCaseStatus("Kept");
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service :::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private ResponseObject getDataSlotValueECS(RequestObject[] reqObj) {
//      automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::START");
//      String dataslotValue = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      String sessionId = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: getDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.debug("ECS Savvion: getDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String dataslotName = (String)hashMap.get("DATASLOTNAME");
//
//         try {
//            if (processInstanceName != null && dataslotName != null && !processInstanceName.equals("") && !dataslotName.equals("")) {
//               sessionId = connectECSADMIN();
//               if (!sessionId.equals("false")) {
//                  automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::" + sessionId + "::" + processInstanceName);
//                  dataslotValue = getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dataslotName).toString();
//                  responseCode = 5000;
//                  responseMessage = "SUCCESS";
//                  if (dataslotValue != null) {
//                     resObj.setResultStringArray(new String[]{dataslotValue});
//                  }
//               } else {
//                  responseCode = 5013;
//                  responseMessage = "USER_ID_INVALID";
//               }
//            } else {
//               automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::processInstanceName is--- " + processInstanceName + "--- or dataslotName is---" + dataslotName);
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var19) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            automatictasklog.error("ECS Savvion: getDataslotValue Service::::Catch Exception:::" + var19.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var18) {
//               automatictasklog.error("ECS Savvion: getDataslotValue Service::::Finally Exception:::" + var18.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.debug("ECS Savvion: getDataSlotValue Service  :::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private RequestObject[] getRequestObject(HashMap hm) {
//      RequestObject[] reqObj = new RequestObject[hm.size()];
//      Iterator it = hm.entrySet().iterator();
//      int mapSize = hm.size();
//      int counter = 0;
//
//      try {
//         if (mapSize != 0) {
//            while(it.hasNext()) {
//               reqObj[counter] = new RequestObject();
//               Entry en = (Entry)it.next();
//               automatictasklog.info("GET KEY::" + en.getKey());
//               automatictasklog.info("GET VALUE-1::" + en.getValue());
//               automatictasklog.info("GET VALUE-2::" + en.getValue());
//               reqObj[counter].setKey((String)en.getKey());
//               reqObj[counter].setValue((String)en.getValue());
//               reqObj[counter].setValue((String)en.getValue());
//               ++counter;
//            }
//         } else {
//            automatictasklog.error("ECS Savvion:getRequestObject Utility Service::::Request Object Length :: " + mapSize);
//         }
//      } catch (Exception var8) {
//         automatictasklog.error("ECS Savvion:getRequestObject Utility Service::::FAIULIRE EXCEPTION ");
//      }
//
//      return reqObj;
//   }
//
//   private HashMap getMap(RequestObject[] reqObj) {
//      
//      HashMap hm = new HashMap();
//      int arrayObjLength = reqObj.length;
//      automatictasklog.debug("ECS Savvion:getMap Utility Service::::Request Object Length :: " + arrayObjLength);
//
//      try {
//         if (arrayObjLength != 0) {
//            for(int i = 0; i < arrayObjLength; ++i) {
//               String key = reqObj[i].getKey();
//               if (key != null) {
//                  Object value = null;
//                  if (reqObj[i].getValue() != null) {
//                     value = reqObj[i].getValue();
//                  }
//
//                  if (reqObj[i].getValueArray() != null) {
//                     value = reqObj[i].getValueArray();
//                  }
//
//                  if (reqObj[i].getUserRoleObj() != null) {
//                     value = reqObj[i].getUserRoleObj();
//                  }
//
//                  hm.put(key, value);
//               }
//            }
//         } else {
//            automatictasklog.error("ECS Savvion:getMap Utility Service::::Request Object Length :: " + arrayObjLength);
//         }
//      } catch (Exception var7) {
//         automatictasklog.error("ECS Savvion:getMap Utility Service:::: Exception :" + var7);
//      }
//
//      return hm;
//   }
//
//   private String getUserPasswordECS(String userId) {
//      automatictasklog.debug("ECS Savvion: getUserPasswordECS Service::START userId:: " + userId);
//      String password = "";
//
//      try {
//         User userObject = UserManager.getUser(userId);
//         password = userObject.getAttribute("password");
//         PService p = PService.self();
//         password = p.decrypt(password);
//      } catch (Exception var5) {
//         automatictasklog.error("ECS Savvion: getUserPasswordECS Service::ERROR:: USERID is " + userId + var5.getMessage());
//      }
//
//      automatictasklog.debug("ECS Savvion: getUserPasswordECS Service::END");
//      return password;
//   }
//
//   private String connectUser(String userId) {
//      String sessionId = null;
//      boolean isMember = false;
//      String password = null;
//
//      try {
//         password = this.getUserPasswordECS(userId);
//         isMember = this.checkUserECS(userId, "ECS");
//         if (isMember) {
//            sessionId = getBizLogicManager().connect(userId, password);
//         } else {
//            automatictasklog.debug("ECS Savvion: connectUser Service::::User " + userId + " Does not belongs to ECS");
//            sessionId = "false";
//         }
//      } catch (RemoteException var6) {
//         automatictasklog.error("ECS Savvion: connectUser Service:::: Exception " + var6.getMessage());
//         sessionId = "false" + var6.getMessage();
//      }
//
//      return sessionId;
//   }
//
//   private String getWorkItemPerformerECS(String workItemName) {
//      String result = null;
//
//      try {
//         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::START");
//         if (workItemName != null && !workItemName.equals("")) {
//            result = (String)getBizLogicManager().getWorkitemInfo(connectECSADMIN(), workItemName).get("PERFORMER");
//         } else {
//            automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService Service::::workItemsName is--- " + workItemName);
//            result = Integer.valueOf(5010).toString();
//         }
//
//         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::SUCCESS::::END" + result);
//      } catch (Exception var4) {
//         automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService:::: Exception:::" + var4.getMessage());
//         return Integer.valueOf(5020).toString();
//      }
//
//      automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::END");
//      return result;
//   }
//
//   private WorkItemObject[] getWorkItemObjectArray(String[] workItems) {
//      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START");
//     
//      new HashMap();
//      new HashMap();
//      String[] wiNameInsub = (String[])null;
//      int workItemArrayLength = workItems.length;
//      automatictasklog.info("ECS Savvion:getWorkItemObjectArray Utility Service::::workItemArrayLength " + workItemArrayLength);
//      WorkItemObject[] woArray = new WorkItemObject[workItemArrayLength];
//      String[] items = new String[10];
//      String[] processInstancelist = new String[5];
//      String sessionId = null;
//      String[] result = new String[3];
//      String processInstanceName = null;
//
//      try {
//         if (workItemArrayLength != 0) {
//            sessionId = this.connectUser("ECSAdmin");
//
//            for(int i = 0; i < workItemArrayLength; ++i) {
//               if (workItems[i] != null) {
//                  woArray[i] = new WorkItemObject();
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::" + workItemArrayLength);
//                  items = workItems[i].split("::");
//                  processInstanceName = items[0].replace("[", "").replace("]", "").trim();
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START1");
//                  processInstancelist = items[0].split("#");
//                  result = processInstancelist[0].split("-");
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START2");
//                  woArray[i].setName(workItems[i].trim());
//                  woArray[i].setPiName(processInstanceName);
//                  woArray[i].setWorkStepName(items[1]);
//                  woArray[i].setPerformer(items[2]);
//                  woArray[i].setPId(processInstancelist[1]);
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START3");
//                  woArray[i].setType(getBizLogicManager().getProcessTemplateName(sessionId, processInstanceName));
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START4");
//               }
//
//               automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END FOR " + i + "::" + workItems[i]);
//            }
//         } else {
//            automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END  WorkItemObject Array :: " + woArray);
//         }
//
//         this.disconnect(sessionId);
//      } catch (Exception var13) {
//         automatictasklog.error("ECS Savvion:getWorkItemObjectArray Utility Service::::FAIULIRE EXCEPTION ");
//      }
//
//      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END");
//      return woArray;
//   }
//
//   private WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
//      WorkItemObject[] resultWorkitems = (WorkItemObject[])null;
//      int inboxSize = 400;
//      if (this.propertiesECSSavvion.getProperty("ECSInboxSize") != null) {
//         inboxSize = Integer.parseInt(this.propertiesECSSavvion.getProperty("ECSInboxSize"));
//      }
//
//      automatictasklog.debug("ECS getResultWorkitems :: inboxSize = " + inboxSize);
//      if (workitemlist.size() > inboxSize) {
//         automatictasklog.info("ECS getResultWorkitems :: Trimming WorkItemObject Array to : " + inboxSize);
//         WorkItemObject[] inboxSizeWorkitems = new WorkItemObject[inboxSize];
//         automatictasklog.info("ECS Savvion: getResultWorkitems Service::::" + workitemlist.size());
//
//         for(int i = 0; i < inboxSize; ++i) {
//            inboxSizeWorkitems[i] = (WorkItemObject)workitemlist.get(i);
//         }
//
//         resultWorkitems = inboxSizeWorkitems;
//      } else {
//         resultWorkitems = new WorkItemObject[workitemlist.size()];
//         workitemlist.toArray(resultWorkitems);
//      }
//
//      return resultWorkitems;
//   }
//
//   public ResponseObject CreateWorkFlow(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: CreateWorkFlow Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      String ptName = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      String methodName = "CreateWorkFlow";
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " :: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap inputMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " :: INPUT VALUES " + inputMap);
//         ptName = (String)inputMap.get("PROCESSNAME");
//         String processInstanceName = null;
//         String sessionId = null;
//         String result = null;
//         String piName = ptName;
//         WorkItemObject[] wiObjects = (WorkItemObject[])null;
//
//         try {
//            automatictasklog.info("ECS Savvion: " + methodName + " :: Inside Module ::  " + ptName);
//            String userId = (String)inputMap.get("USERID");
//            String action = (String)inputMap.get("ACTION");
//            String alCaseType = (String)inputMap.get("ALCASETYPE");
//            String autoRelease = (String)inputMap.get("AUTORELEASE");
//            String clCaseType = (String)inputMap.get("CLCASETYPE");
//            String clQueried = (String)inputMap.get("CLQUERIED");
//            String clAddition = (String)inputMap.get("CLADDITION");
//            String srType = (String)inputMap.get("SRTYPE");
//            automatictasklog.info("ECS Savvion: " + methodName + " :: userId: " + userId + " action: " + action + " alCaseType: " + alCaseType + " autoRelease: " + autoRelease + " clCaseType: " + clCaseType + " srType: " + srType);
//            if (userId != null && !userId.equals("")) {
//               if (ptName == null || !ptName.equals("RGICL_ECS_AL_FLOW") || alCaseType != null && !alCaseType.equals("")) {
//                  if (ptName == null || !ptName.equals("RGICL_ECS_CL_FLOW") || autoRelease != null && !autoRelease.equals("")) {
//                     if (ptName == null || !ptName.equals("RGICL_ECS_SR_FLOW") || srType != null && !srType.equals("")) {
//                        dsTypeMap.put("UserId", "STRING");
//                        dsValues.put("UserId", userId);
//                        dsTypeMap.put("Action", "STRING");
//                        dsValues.put("Action", action);
//                        if (ptName != null && ptName.equals("RGICL_ECS_AL_FLOW")) {
//                           dsTypeMap.put("ALCaseType", "STRING");
//                           dsValues.put("ALCaseType", alCaseType);
//                        }
//
//                        if (ptName != null && ptName.equals("RGICL_ECS_CL_FLOW")) {
//                           dsTypeMap.put("AutoRelease", "STRING");
//                           dsValues.put("AutoRelease", autoRelease);
//                           dsTypeMap.put("CLCaseType", "STRING");
//                           dsValues.put("CLCaseType", clCaseType);
//                        }
//
//                        if (ptName != null && ptName.equals("RGICL_ECS_SR_FLOW")) {
//                           automatictasklog.info("ECS Savvion: inside RGICL_ECS_SR_FLOW loop 2");
//                           dsTypeMap.put("SRType", "STRING");
//                           dsValues.put("SRType", srType);
//                        }
//                     } else {
//                        automatictasklog.info("ECS Savvion: inside RGICL_ECS_SR_FLOW loop");
//                        responseCode = 5010;
//                        responseMessage = "INPUT_VALUE_IS_NULL";
//                        automatictasklog.info("ECS Savvion: " + methodName + " :: Auto Release field is null");
//                     }
//                  } else {
//                     responseCode = 5010;
//                     responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: Auto Release field is null");
//                  }
//               } else {
//                  responseCode = 5010;
//                  responseMessage = "INPUT_VALUE_IS_NULL";
//                  automatictasklog.info("ECS Savvion: " + methodName + " :: AL Case Type is null");
//               }
//            } else {
//               responseCode = 5001;
//               responseMessage = "USER_ID_NULL";
//               automatictasklog.error("ECS Savvion: " + methodName + " :: Input Value is null");
//            }
//
//            automatictasklog.info("ECS Savvion: " + methodName + " :: Response Code :: " + responseCode + " :: Response Message :: " + responseMessage);
//            if (responseCode == 0 || responseCode != 5010) {
//               automatictasklog.info("ECS Savvion: inside if loop ");
//               sessionId = connectECSADMIN();
//               User user = UserManager.getDefaultRealm().getUser(userId);
//               if (!sessionId.equals("false") && user != null) {
//                  automatictasklog.info("ECS Savvion:  " + sessionId + " user: " + user);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  automatictasklog.info("ECS Savvion:  resolvedDSValues: " + resolvedDSValues);
//                  processInstanceName = this.createProcessInstance(sessionId, ptName, piName, "medium", resolvedDSValues);
//                  automatictasklog.info("In " + methodName + " Method :: Created ProcessInstanceName is :: " + processInstanceName);
//                  automatictasklog.info("ECS Savvion: " + methodName + " :: Created ProcessInstanceName is ::  " + processInstanceName);
//                  responseCode = 5000;
//                  responseMessage = "SUCCESS";
//                  wiObjects = this.getNextAvailableTaskList(processInstanceName, "Locked", (String)null, (String)null, (String)null);
//                  automatictasklog.info("ECS Savvion: " + methodName + " :: Assigning task  :: " + wiObjects[0].getName() + " to user ::  " + userId);
//                  RequestObject[] reqObj1 = new RequestObject[2];
//                  String[] workitemNamesArr = new String[]{wiObjects[0].getName()};
//                  reqObj1[0] = new RequestObject();
//                  reqObj1[0].setKey("WORKITEMNAMES");
//                  reqObj1[0].setValueArray(workitemNamesArr);
//                  automatictasklog.info("ECS Savvion: " + methodName + " ::Set workitemname in request Object 1 ");
//                  reqObj1[1] = new RequestObject();
//                  reqObj1[1].setKey("TOBEASSIGNEDUSERID");
//                  reqObj1[1].setValue(userId);
//                  automatictasklog.info("ECS Savvion: " + methodName + " ::Set TobeassignedUserId in request Object 2 ");
//                  this.assignTaskToUser(reqObj1);
//                  automatictasklog.info("ECS Savvion: " + methodName + " :: Assigned task to user ::  " + userId);
//                  wiObjects[0].setPerformer(userId);
//               } else {
//                  responseCode = 5013;
//                  responseMessage = "USER_ID_INVALID";
//               }
//            }
//         } catch (Exception var35) {
//            humantasklog.info("ECS Savvion: " + methodName + " :: Exception" + var35.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var34) {
//               humantasklog.error("ECS Savvion: " + methodName + " :: Finally Exception:::" + var34.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultStringArray(new String[]{processInstanceName});
//         resObj.setResultworkItemArray(wiObjects);
//      }
//
//      humantasklog.info("ECS Savvion: " + methodName + " :: END " + responseCode + " :: Response Message:: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject getAssignedTaskList(RequestObject[] reqObj) {
//      String methodName = "getAssignedTaskList";
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: " + methodName + "  Service:::: START");
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      int responseCode = 0;
//      String responseMessage = "";
//      ResponseObject resObj = new ResponseObject();
//      new ArrayList();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      new HashMap();
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//         String _ptName = (String)hashMap.get("PROCESSNAME");
//
//         try {
//            if (userId != null && !userId.equals("")) {
//               if (_ptName != null && !_ptName.equals("")) {
//                  ArrayList resultTaskList = db.getAssignedTasks(userId, _ptName);
//                  automatictasklog.debug("ECS Savvion: " + methodName + " Service::resultTaskList " + resultTaskList);
//                  if (resultTaskList != null && resultTaskList.size() != 0) {
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:: Objects Count " + resultTaskList.size());
//                     workItemObject = this.getResultWorkitems(resultTaskList);
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject :" + workItemObject);
//                     resObj.setResultworkItemArray(workItemObject);
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                  } else {
//                     responseCode = 5034;
//                     responseMessage = "ASSIGNED_INBOX_EMPTY";
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::resultTaskList.size" + resultTaskList.size());
//                  }
//               } else {
//                  responseCode = 5010;
//                  responseMessage = "INPUT_VALUE_IS_NULL";
//               }
//            } else {
//               responseCode = 5001;
//               responseMessage = "USER_ID_NULL";
//            }
//         } catch (Exception var13) {
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::Error Message::" + var13.getMessage());
//            responseCode = 5013;
//            responseMessage = "USER_ID_INVALID";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service :::: END :: " + responseCode + " :: Response Message :: " + responseMessage);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public ResponseObject assignTaskToUser(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: assignTaskToUser Service ::START");
//      String methodname = "assignTaskToUser";
//      new HashMap();
//      String[] workItemName = (String[])null;
//      String toBeAssignedUserId = null;
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodname + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: " + methodname + " Service:::: INPUT VALUES " + hashMap);
//            workItemName = (String[])hashMap.get("WORKITEMNAMES");
//            toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//            automatictasklog.info("ECS Savvion: " + methodname + " Service:: Input Workitemname array length :: " + workItemName.length);
//            if (toBeAssignedUserId != null && workItemName != null && !toBeAssignedUserId.equals("")) {
//               automatictasklog.info("ECS Savvion: " + methodname + " Service:: Inside else");
//               sessionId = this.connectUser(toBeAssignedUserId);
//               automatictasklog.info("ECS Savvion: " + methodname + " Service:: Session Id :: " + sessionId);
//               if (sessionId.equals("false")) {
//                  responseCode = 5013;
//                  responseMessage = "USER_ID_INVALID";
//               } else {
//                  int index = 0;
//
//                  while(true) {
//                     if (index >= workItemName.length) {
//                        responseCode = 5000;
//                        responseMessage = "SUCCESS";
//                        break;
//                     }
//
//                     automatictasklog.info("ECS Savvion: " + methodname + " Service:: Current Workitemname :: " + workItemName[index]);
//                     if (workItemName[index] == null && workItemName[index].equals("null")) {
//                        automatictasklog.warn("ECS Savvion: " + methodname + " Service::::WorkItem[" + index + "] is NULL");
//                     } else {
//                        BLServer blserver = BLClientUtil.getBizLogicServer();
//                        Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                        WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
//                        automatictasklog.info("ECS Savvion: " + methodname + " Service:: Current Workitem :: " + wi);
//                        if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
//                           automatictasklog.warn("ECS AssignTask enterred");
//                           wi.assign(toBeAssignedUserId);
//                           wi.save();
//                           automatictasklog.warn("ECS Task Assigned to user");
//                        }
//                     }
//
//                     ++index;
//                  }
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//               automatictasklog.error("ECS Savvion: " + methodname + " Service:: Input Value is Null");
//            }
//         } catch (Exception var23) {
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//            automatictasklog.error("ECS Savvion: " + methodname + " Service:::: Catch Exception :::" + var23.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var22) {
//               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var22.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setWorkItemCaseStatus("Locked");
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodname + " Service :::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteRRFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteRRFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      ResponseObject res = new ResponseObject();
//      Realm usr = null;
//      boolean isExistingUser = false;
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String action = (String)hashMap.get("ACTION");
//         String userIdToAdd = (String)hashMap.get("USERIDTOCREATE");
//         String[] groupNamesToAdd = (String[])hashMap.get("GROUPNAMESTOADD");
//         String[] groupNamesToRemove = (String[])hashMap.get("GROUPNAMESTOREMOVE");
//
//         try {
//            usr = UserManager.getDefaultRealm();
//            User sbmUser = usr.getUser(userIdToAdd);
//            if (sbmUser != null) {
//               isExistingUser = true;
//            }
//
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  if (action != null && (action.equals("AddUser") || action.equals("EditUser"))) {
//                     res = this.addOrRemoveUserECS(reqObj);
//                     responseCode = res.getResponseCode();
//                     responseMessage = res.getResponseMessage();
//                  }
//
//                  if (action != null && res.getResponseCode() != 5000) {
//                     try {
//                        if (action.equals("AddUser") && responseCode != 5000 && res.getResponseCode() == 5000) {
//                           usr = UserManager.getDefaultRealm();
//                           sbmUser = usr.getUser(userIdToAdd);
//                           boolean reslt = false;
//                           if (sbmUser != null && !isExistingUser) {
//                              reslt = usr.removeUser(userIdToAdd);
//                           }
//
//                           if (sbmUser != null && !reslt) {
//                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + userIdToAdd + "Can't be removed");
//                           }
//                        }
//
//                        if (action.equals("EditUser") && responseCode != 5000 && res.getResponseCode() == 5000) {
//                           this.revertTransaction(userIdToAdd, groupNamesToRemove, groupNamesToAdd, (String)null, groupNamesToRemove.length, groupNamesToAdd.length);
//                        }
//                     } catch (Exception var41) {
//                        automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var41.getMessage());
//                     }
//                  } else {
//                     BLServer blserver = BLClientUtil.getBizLogicServer();
//                     Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                     HashMap hm = new HashMap();
//                     if (!this.checkNull(hashMap.get("ISRRMAKERACCEPTED"))) {
//                        workItemCaseStatus = hashMap.get("ISRRMAKERACCEPTED").toString();
//                        hm.put("isMakerAccepted", workItemCaseStatus);
//                     }
//
//                     String dsv;
//                     if (!this.checkNull(hashMap.get("RRMAKERREMARKS"))) {
//                        dsv = (String)pi.getDataSlotValue("MakerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("RRMAKERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("RRMAKERREMARKS").toString();
//                        }
//
//                        hm.put("MakerRemarks", dsv);
//                     }
//
//                     if (!this.checkNull(hashMap.get("ISAPPOWNERACCEPTED"))) {
//                        workItemCaseStatus = hashMap.get("ISAPPOWNERACCEPTED").toString();
//                        hm.put("IsAppOwnerAccepted", workItemCaseStatus);
//                     }
//
//                     if (!this.checkNull(hashMap.get("APPOWNERREMARKS"))) {
//                        dsv = (String)pi.getDataSlotValue("AppOwnerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("APPOWNERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("APPOWNERREMARKS").toString();
//                        }
//
//                        hm.put("AppOwnerRemarks", dsv);
//                     }
//
//                     if (!this.checkNull(hashMap.get("USERTYPE"))) {
//                        hm.put("UserType", hashMap.get("USERTYPE").toString());
//                     }
//
//                     if (!this.checkNull(hashMap.get("LOGINID"))) {
//                        hm.put("LoginId", hashMap.get("LOGINID").toString());
//                     }
//
//                     if (!this.checkNull(hashMap.get("CREATEDOREDITEDUSERNAME"))) {
//                        hm.put("CreatedorEditedUserName", hashMap.get("CREATEDOREDITEDUSERNAME").toString());
//                     }
//
//                     if (!this.checkNull(hashMap.get("CREATEDOREDITEDUSERROLE"))) {
//                        hm.put("CreatedorEditedUserRole", hashMap.get("CREATEDOREDITEDUSERROLE").toString());
//                     }
//
//                     if (!this.checkNull(hashMap.get("SUPERVISORID"))) {
//                        hm.put("SupervisorId", hashMap.get("SUPERVISORID").toString());
//                     }
//
//                     if (blsession == null) {
//                        responseCode = 5013;
//                        responseMessage = "USER_ID_INVALID";
//                     } else {
//                        pi.updateSlotValue(hm);
//                        pi.save();
//                        Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                        WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                        if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                           automatictasklog.warn("ECS AssignTask enterred");
//                           this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                           automatictasklog.warn("ECS Task Assigned to user");
//                        }
//
//                        automatictasklog.warn("ECS CompleteTask enterred");
//                        this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                        automatictasklog.warn("ECS Task Completed");
//                        responseCode = 5000;
//                        responseMessage = "SUCCESS";
//
//                        try {
//                           blserver.getProcessInstance(blsession, processInstanceName);
//                        } catch (Exception var42) {
//                           isInstanceCompleted = true;
//                        }
//
//                        if (workItemCaseStatus.equalsIgnoreCase("Completed") && !this.checkNull(hashMap.get("SUPERVISORID"))) {
//                           nextWorkItemCaseStatus = "Locked";
//                        } else if (workItemCaseStatus.equalsIgnoreCase("Completed") && this.checkNull(hashMap.get("SUPERVISORID"))) {
//                           nextWorkItemCaseStatus = "New";
//                        } else if (workItemCaseStatus.equalsIgnoreCase("SendBack")) {
//                           nextWorkItemCaseStatus = "Revised";
//                        } else if (workItemCaseStatus.equalsIgnoreCase("Approve")) {
//                           nextWorkItemCaseStatus = "New";
//                        } else if (workItemCaseStatus.equalsIgnoreCase("Reject")) {
//                           nextWorkItemCaseStatus = "";
//                        }
//
//                        wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)hashMap.get("SUPERVISORID"));
//                     }
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var43) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var43.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var43.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var40) {
//               var40.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var40.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + "Error Message:::::" + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteNetworkFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteNetworkFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  HashMap hm = new HashMap();
//                  if (workItemName != null && workItemName.contains("NetworkMaker")) {
//                     hm.put("isNetworkMakerAccepted", workItemCaseStatus);
//                  }
//
//                  String dsv;
//                  if (workItemName != null && workItemName.contains("NetworkChecker")) {
//                     hm.put("isNetworkCheckerAccepted", workItemCaseStatus);
//                     if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                        dsv = (String)pi.getDataSlotValue("NetworkCheckerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("APPROVERREMARKS").toString();
//                        }
//
//                        hm.put("NetworkCheckerRemarks", dsv);
//                     }
//                  }
//
//                  if (workItemName != null && workItemName.contains("PaymentChecker")) {
//                     hm.put("isPaymentCheckerAccepted", workItemCaseStatus);
//                     if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                        dsv = (String)pi.getDataSlotValue("PaymentCheckerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("APPROVERREMARKS").toString();
//                        }
//
//                        hm.put("PaymentCheckerRemarks", dsv);
//                     }
//                  }
//
//                  if (!this.checkNull(hashMap.get("TPANAME"))) {
//                     hm.put("TPAName", hashMap.get("TPANAME").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("HOSPITALNAME"))) {
//                     hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("HOSPITALCODE"))) {
//                     hm.put("HospitalCode", hashMap.get("HOSPITALCODE").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("LOCATION"))) {
//                     hm.put("Location", hashMap.get("LOCATION").toString());
//                  }
//
//                  if (blsession != null) {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     }
//
//                     automatictasklog.warn("ECS CompleteTask enterred");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                     automatictasklog.warn("ECS Task Completed");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//
//                     try {
//                        blserver.getProcessInstance(blsession, processInstanceName);
//                     } catch (Exception var32) {
//                        isInstanceCompleted = true;
//                     }
//
//                     nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                     wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null);
//                  } else {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var33) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var33.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var33.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var31) {
//               var31.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var31.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteNetworkGroupFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteNetworkGroupFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  HashMap hm = new HashMap();
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  if (workItemName != null && workItemName.contains("NetworkChecker")) {
//                     hm.put("isNetworkCheckerAccepted", workItemCaseStatus);
//                     if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                        String dsv = (String)pi.getDataSlotValue("NetworkCheckerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("APPROVERREMARKS").toString();
//                        }
//
//                        hm.put("NetworkCheckerRemarks", dsv);
//                     }
//                  }
//
//                  if (blsession != null) {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     }
//
//                     automatictasklog.warn("ECS CompleteTask enterred");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                     automatictasklog.warn("ECS Task Completed");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//
//                     try {
//                        blserver.getProcessInstance(blsession, processInstanceName);
//                     } catch (Exception var32) {
//                        isInstanceCompleted = true;
//                     }
//
//                     nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                     wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null);
//                  } else {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "INPUT_VALUE_IS_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var33) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var33.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var33.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var31) {
//               var31.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var31.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteProductFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteProductFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  HashMap hm = new HashMap();
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  if (workItemName != null && workItemName.contains("Checker")) {
//                     hm.put("isProductCheckerAccepted", workItemCaseStatus);
//                     if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                        String dsv = (String)pi.getDataSlotValue("ProductCheckerRemarks");
//                        if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                           dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                        } else {
//                           dsv = hashMap.get("APPROVERREMARKS").toString();
//                        }
//
//                        hm.put("ProductCheckerRemarks", dsv);
//                     }
//                  }
//
//                  if (!this.checkNull(hashMap.get("PRODUCTNAME"))) {
//                     hm.put("ProductName", hashMap.get("PRODUCTNAME").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("PRODUCTCODE"))) {
//                     hm.put("ProductCode", hashMap.get("PRODUCTCODE").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("PRODUCTCOVERTYPE"))) {
//                     hm.put("ProductCoverType", hashMap.get("PRODUCTCOVERTYPE").toString());
//                  }
//
//                  if (!this.checkNull(hashMap.get("SERVICECATEGORY"))) {
//                     hm.put("ServiceCategory", hashMap.get("SERVICECATEGORY").toString());
//                  }
//
//                  if (blsession != null) {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     }
//
//                     automatictasklog.warn("ECS CompleteTask enterred");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                     automatictasklog.warn("ECS Task Completed");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//
//                     try {
//                        blserver.getProcessInstance(blsession, processInstanceName);
//                     } catch (Exception var32) {
//                        isInstanceCompleted = true;
//                     }
//
//                     nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                     wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null);
//                  } else {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var33) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var33.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var33.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var31) {
//               var31.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var31.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private String getNextTaskStatus(String currentWorkItemStatus) {
//      String nextWorkItemCaseStatus = "";
//      if (currentWorkItemStatus.equalsIgnoreCase("Completed")) {
//         nextWorkItemCaseStatus = "New";
//      } else if (!currentWorkItemStatus.equalsIgnoreCase("SendBack") && !currentWorkItemStatus.equalsIgnoreCase("Reject")) {
//         if (!currentWorkItemStatus.equalsIgnoreCase("Approve") && !currentWorkItemStatus.equalsIgnoreCase("Query")) {
//            if (currentWorkItemStatus.equalsIgnoreCase("Reject")) {
//               nextWorkItemCaseStatus = "Revised";
//            } else if (currentWorkItemStatus.equalsIgnoreCase("QueryCompleted")) {
//               nextWorkItemCaseStatus = "New";
//            } else if (currentWorkItemStatus.equalsIgnoreCase("Closed")) {
//               nextWorkItemCaseStatus = "New";
//            }
//         } else {
//            nextWorkItemCaseStatus = "New";
//         }
//      } else {
//         nextWorkItemCaseStatus = "Revised";
//      }
//
//      return nextWorkItemCaseStatus;
//   }
//
//   private WorkItemObject[] getNextAssignedTask(String userId, String processInstanceName, String processName) {
//      String methodName = "getNextAssignedTask";
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//
//      try {
//         BLServer blserver = BLClientUtil.getBizLogicServer();
//         Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//         ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//         WorkItemList wl = pi.getAssignedWorkItemList();
//         List assignedWIList = wl.getList();
//         automatictasklog.info("ECS Savvion: " + methodName + " Service::::Next assigned task list size:::" + assignedWIList.size());
//         wiObjects = new WorkItemObject[assignedWIList.size()];
//         automatictasklog.info("ECS Savvion: " + methodName + " Service::::Next assigned workItemObject length :::" + wiObjects.length);
//
//         for(int i = 0; i < assignedWIList.size(); ++i) {
//            WorkItem wi = (WorkItem)assignedWIList.get(i);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Next assigned workItem name :::" + wi.getName());
//            wiObjects[i] = new WorkItemObject();
//            wiObjects[i].setName(wi.getName());
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::wiObjects[i] name :::" + wiObjects[i].getName());
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Process name :::" + processName);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Queue name :::" + this.getQueueName(processName));
//            wiObjects[i].setQueue(this.getQueueName(processName));
//            wiObjects[i].setCaseStatus("Locked");
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Workiem object queue name :::" + wiObjects[i].getQueue());
//         }
//      } catch (Exception var13) {
//         var13.printStackTrace();
//         automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var13.getMessage());
//      }
//
//      return wiObjects;
//   }
//
//   private String getQueueName(String processName) {
//      String queue = "";
//      if (processName != null & processName.equals("RGICL_ECS_RR_FLOW")) {
//         queue = "RRRequester";
//      } else if (processName != null & processName.equals("RGICL_ECS_NETWORK_FLOW")) {
//         queue = "NetworMaker";
//      } else if (processName != null & processName.equals("RGICL_ECS_PRODUCT_FLOW")) {
//         queue = "ProductMaker";
//      } else if (processName != null & processName.equals("RGICL_ECS_AL_FLOW")) {
//         queue = "ALInwardDEO";
//      } else if (processName != null & processName.equals("RGICL_ECS_CL_FLOW")) {
//         queue = "CLInwardDEO";
//      } else if (processName != null & processName.equals("RGICL_ECS_SR_FLOW")) {
//         queue = "SRMaker";
//      }
//
//      return queue;
//   }
//
//   public WorkItemObject[] getNextAvailableTaskList(String processInstanceName, String nextWorkItemCaseStatus, String currWorkItemName, String needsInvestigation, String supervisorId) {
//      String methodName = "getNextAvailableTaskList";
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: " + methodName + "  Service:::: START");
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      new ArrayList();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//
//      try {
//         ArrayList resultTaskList = db.getNextAvailableTaskObjects(processInstanceName, nextWorkItemCaseStatus, currWorkItemName, needsInvestigation, supervisorId);
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service::resultTaskList " + resultTaskList);
//         if (resultTaskList != null && resultTaskList.size() != 0) {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:: Objects Count " + resultTaskList.size());
//            workItemObject = this.getResultWorkitems(resultTaskList);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject :" + workItemObject);
//         } else {
//            this.responseCode = 5034;
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::resultTaskList.size" + resultTaskList.size());
//         }
//      } catch (Exception var11) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service::Error Message::" + var11.getMessage());
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service :::: END ::");
//      automatictasklog.info("==================================================================================================");
//      return workItemObject;
//   }
//
//   public ResponseObject UpdateAndCompleteALFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteALFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      WorkItemObject[] completedWorkItem = (WorkItemObject[])null;
//      HashMap hm = new HashMap();
//      ResponseObject associatedresObj = null;
//      boolean isQueriedAL = false;
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String[] resultArray = new String[2];
//      String associatedPIName = "";
//      String associatedWIName = "";
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String alInwardNo = (String)hashMap.get("ALINWARDNO");
//         String clInwardNo = (String)hashMap.get("CLINWARDNO");
//         String isQueried = (String)hashMap.get("ISQUERIED");
//
//         try {
//            if (workItemName != null && workItemName.contains("InwardDEO") && this.checkNull(hashMap.get("ALINWARDNO"))) {
//               automatictasklog.info("ECS Savvion " + methodName + " Service:::: ALINWARDNO is null or empty");
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  if (responseCode == 0) {
//                     if (isQueried != null && isQueried.equals("Yes") && alInwardNo != null && !alInwardNo.equals("")) {
//                        resultArray = db.getQueryQueueTaskName(alInwardNo, clInwardNo);
//                        associatedPIName = resultArray[0];
//                        associatedWIName = resultArray[1];
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
//                        workItemName = workItemName + associatedWIName;
//                        associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
//                        workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
//                        processInstanceName = processInstanceName + associatedPIName;
//                        associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
//                        processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
//                        new ResponseObject();
//                        associatedresObj = this.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
//                     }
//
//                     if (associatedresObj != null && associatedresObj.getResponseCode() != 5000) {
//                        responseCode = associatedresObj.getResponseCode();
//                        responseMessage = associatedresObj.getResponseMessage();
//                     } else {
//                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query")) {
//                           workItemCaseStatus = "QueryCompleted";
//                           automatictasklog.info("ECS Savvion " + methodName + " setting isQueriedAL flag as true");
//                           hm.put("isQueriedAL", true);
//                        }
//
//                        BLServer blserver = BLClientUtil.getBizLogicServer();
//                        Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                        if (!this.checkNull(hashMap.get("ALNO"))) {
//                           hm.put("ALNo", hashMap.get("ALNO").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("EMPLOYEEID"))) {
//                           hm.put("EmployeeId", hashMap.get("EMPLOYEEID").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HOSPITALNAME"))) {
//                           hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                           workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                        }
//
//                        if (workItemName != null && workItemName.contains("InwardDEO")) {
//                           automatictasklog.info("workItemCaseStatus when ALInwardDEO submits task " + workItemCaseStatus);
//                           hm.put("isALInwardDEOAccepted", workItemCaseStatus);
//                           if (!this.checkNull(hashMap.get("ALINWARDNO"))) {
//                              hm.put("ALInwardNo", hashMap.get("ALINWARDNO").toString());
//                           }
//                        }
//
//                        String dsv;
//                        if (workItemName != null && workItemName.contains("Executive")) {
//                           hm.put("isALExecutiveAccepted", workItemCaseStatus);
//                           if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                              dsv = (String)pi.getDataSlotValue("ALExecutiveRemarks");
//                              if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                                 dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                              } else {
//                                 dsv = hashMap.get("APPROVERREMARKS").toString();
//                              }
//
//                              hm.put("ALExecutiveRemarks", dsv);
//                           }
//                        }
//
//                        if (workItemName != null && workItemName.contains("Approver")) {
//                           hm.put("isALApproverAccepted", workItemCaseStatus);
//                           if (workItemCaseStatus.equals("Approve")) {
//                              automatictasklog.info("ECS Savvion " + methodName + " resetting isQueriedAL flag as false");
//                              hm.put("isQueriedAL", false);
//                           }
//
//                           if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                              dsv = (String)pi.getDataSlotValue("ALApproverRemarks");
//                              if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                                 dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                              } else {
//                                 dsv = hashMap.get("APPROVERREMARKS").toString();
//                              }
//
//                              hm.put("ALApproverRemarks", dsv);
//                           }
//                        }
//
//                        if (workItemName != null && workItemName.contains("TagChecker")) {
//                           hm.put("HasALTagCheckerAccepted", workItemCaseStatus);
//                           if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                              dsv = (String)pi.getDataSlotValue("ALTagCheckerRemarks");
//                              if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                                 dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                              } else {
//                                 dsv = hashMap.get("APPROVERREMARKS").toString();
//                              }
//
//                              hm.put("ALTagCheckerRemarks", dsv);
//                           }
//                        }
//
//                        if (workItemName != null && workItemName.contains("AmountApprover")) {
//                           hm.put("HasAmountApproverAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("NetworkDeviator")) {
//                           hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("ServiceDeviator")) {
//                           hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("PolicyDeviator")) {
//                           hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("OtherDeviator")) {
//                           hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSAMOUNTAPPROVAL"))) {
//                           hm.put("NeedsAmountApproval", hashMap.get("NEEDSAMOUNTAPPROVAL").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASNETWORKDEVIATION"))) {
//                           hm.put("HasNetworkDeviation", hashMap.get("HASNETWORKDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASSERVICEDEVIATION"))) {
//                           hm.put("HasServiceDeviation", hashMap.get("HASSERVICEDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASPOLICYDEVIATION"))) {
//                           hm.put("HasPolicyDeviation", hashMap.get("HASPOLICYDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASOTHERDEVIATION"))) {
//                           hm.put("HasOtherDeviation", hashMap.get("HASOTHERDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSINVESTIGATION"))) {
//                           hm.put("NeedsInvestigation", hashMap.get("NEEDSINVESTIGATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("PATIENTNAME"))) {
//                           hm.put("PatientName", hashMap.get("PATIENTNAME").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("PATIENTUHID"))) {
//                           hm.put("PatientUHID", hashMap.get("PATIENTUHID").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("POLICYNAME"))) {
//                           hm.put("PolicyName", hashMap.get("POLICYNAME").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("POLICYNO"))) {
//                           hm.put("PolicyNo", hashMap.get("POLICYNO").toString());
//                        }
//
//                        if (blsession == null) {
//                           responseCode = 5013;
//                           responseMessage = "USER_ID_INVALID";
//                        } else {
//                           pi.updateSlotValue(hm);
//                           pi.save();
//                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName :" + workItemName + " ::: " + workItemCaseStatus);
//                           if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp")) && workItemCaseStatus.equals("Reject")) {
//                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
//                              completedWorkItem = this.completeMultipleTask(processInstanceName, workItemName, blserver, blsession);
//                              automatictasklog.info("completedWorkItem size is " + completedWorkItem.length);
//                           }
//
//                           Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                              automatictasklog.warn("ECS AssignTask enterred");
//                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                              automatictasklog.warn("ECS Task Assigned to user");
//                           }
//
//                           automatictasklog.warn("ECS CompleteTask enterred");
//                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                           automatictasklog.warn("ECS Task Completed");
//                           responseCode = 5000;
//                           responseMessage = "SUCCESS";
//
//                           try {
//                              pi = blserver.getProcessInstance(blsession, processInstanceName);
//                           } catch (Exception var44) {
//                              isInstanceCompleted = true;
//                           }
//
//                           nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                           isQueriedAL = (Boolean)pi.getDataSlotValue("isQueriedAL");
//                           if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator")) {
//                              try {
//                                 Thread.sleep(5000L);
//                              } catch (InterruptedException var43) {
//                                 var43.printStackTrace();
//                              }
//                           }
//
//                           wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null);
//                        }
//                     }
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var45) {
//            if (!isInstanceCompleted) {
//               responseCode = 5017;
//               responseMessage = "DS_NAME_INVALID";
//            }
//
//            var45.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var45.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var42) {
//               var42.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var42.getMessage());
//            }
//
//         }
//
//         if (associatedPIName != null && !associatedPIName.equals("") && associatedWIName != null && !associatedWIName.equals("") && associatedresObj.getResponseCode() == 5000 && responseCode == 5000) {
//            ArrayList workItemList = new ArrayList();
//            WorkItemObject workitem = new WorkItemObject();
//            workitem.setName(workItemName);
//            workitem.setCaseStatus("Completed");
//            workItemList.add(workitem);
//            completedWorkItem = this.getResultWorkitems(workItemList);
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         if (completedWorkItem != null && completedWorkItem.length != 0) {
//            resObj.setCompletedWorkItemArray(completedWorkItem);
//         }
//
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private ResponseObject completeAssociatedInstance(String userId, String associatedWIName, String associatedPIName) {
//      String methodName = "completeAssociatedInstance";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: starts");
//      ResponseObject response = new ResponseObject();
//      int responseCode = 0;
//      String responseMessage = "";
//      boolean isInstanceCompleted = false;
//      HashMap hm = new HashMap();
//      String workItemCaseStatus = "Closed";
//
//      try {
//         BLServer blserver = BLClientUtil.getBizLogicServer();
//         Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//         ProcessInstance pi = blserver.getProcessInstance(blsession, associatedPIName);
//         if (associatedWIName != null && associatedWIName.contains("ALInward")) {
//            hm.put("isALInwardDEOAccepted", workItemCaseStatus);
//         }
//
//         if (associatedWIName != null && associatedWIName.contains("CLInward")) {
//            hm.put("isCLInwardDEOAccepted", workItemCaseStatus);
//         }
//
//         if (blsession != null) {
//            pi.updateSlotValue(hm);
//            pi.save();
//            Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, associatedWIName);
//            if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//               automatictasklog.warn("ECS AssignTask enterred");
//               this.assignWorkitem((new Long(blsession.getID())).toString(), associatedWIName);
//               automatictasklog.warn("ECS Task Assigned to user");
//            } else {
//               responseCode = 5021;
//               responseMessage = "WINAME_NOT_FOUND";
//            }
//
//            automatictasklog.warn("ECS CompleteTask enterred");
//            this.completeWorkitem((new Long(blsession.getID())).toString(), associatedWIName);
//            automatictasklog.warn("ECS Task Completed");
//
//            try {
//               blserver.getProcessInstance(blsession, associatedPIName);
//            } catch (Exception var17) {
//               responseCode = 5000;
//               responseMessage = "SUCCESS";
//               isInstanceCompleted = true;
//               response.setWorkItemCaseStatus(workItemCaseStatus);
//            }
//         } else {
//            responseCode = 5013;
//            responseMessage = "USER_ID_INVALID";
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Invalid User Id");
//         }
//      } catch (Exception var18) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured " + var18);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      response.setInstanceCompleted(isInstanceCompleted);
//      response.setResponseCode(responseCode);
//      response.setResponseMessage(responseMessage);
//      return response;
//   }
//
//   private WorkItemObject[] completeMultipleTask(String processInstanceName, String workItemName, BLServer blserver, Session blsession) {
//      String[] workItemNames = new String[50];
//      ArrayList workItemList = new ArrayList();
//      WorkItemObject[] completedWorkItem = (WorkItemObject[])null;
//      new WorkItemObject();
//      String methodName = "completeMultipleTask";
//      String caseStatus = "RejectPullBack";
//      DBUtility db = new DBUtility(this.SBM_HOME);
//
//      try {
//         workItemNames = db.getNextAvailableTaskNames(processInstanceName);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemNames length before removing the current workItem :" + workItemNames.length);
//         if (workItemNames.length != 0) {
//            List<String> list = new ArrayList();
//            Collections.addAll(list, workItemNames);
//            list.remove(workItemName);
//            workItemNames = (String[])list.toArray(new String[list.size()]);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemNames length after removing the current workItem :" + workItemNames.length);
//
//            for(int index = 0; index < workItemNames.length; ++index) {
//               if (workItemNames[index] != null && !workItemNames[index].equals("null")) {
//                  if (!workItemNames[index].contains("Deviator") && !workItemNames[index].contains("AmountApp") && !workItemNames[index].contains("Approver") && !workItemNames[index].contains("QualityChecker") && (!workItemNames[index].contains("EFTChecker") || workItemName.contains("Deviator") || workItemName.contains("AmountApp"))) {
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::No WorkItem to complete forcefully");
//                  } else {
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName is : " + workItemNames[index]);
//                     WorkItemObject workitem = new WorkItemObject();
//                     Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemNames[index]);
//                     workitem.setPerformer(wi.getPerformer());
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: current performer of the workItemName is : " + wi.getPerformer());
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                     }
//
//                     automatictasklog.warn("ECS CompleteTask enterred complete Multiple Task");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                     automatictasklog.warn("ECS Task Completed : Multiple Task");
//                     workitem.setName(workItemNames[index]);
//                     workitem.setCaseStatus(caseStatus);
//                     workItemList.add(workitem);
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemList size:" + workItemList.size());
//                  }
//               }
//            }
//         } else {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::there is no next WorkItem to complete");
//         }
//      } catch (Exception var16) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception" + var16);
//      }
//
//      completedWorkItem = this.getResultWorkitems(workItemList);
//      return completedWorkItem;
//   }
//
//   public ResponseObject UpdateAndCompleteCLFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteCLFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      WorkItemObject[] completedWorkItem = (WorkItemObject[])null;
//      HashMap hm = new HashMap();
//      ResponseObject associatedresObj = null;
//      boolean isQueriedCL = false;
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String[] resultArray = new String[2];
//      String associatedPIName = "";
//      String associatedWIName = "";
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String alInwardNo = (String)hashMap.get("ALINWARDNO");
//         String clInwardNo = (String)hashMap.get("CLINWARDNO");
//         String isQueried = (String)hashMap.get("ISQUERIED");
//
//         try {
//            if (workItemName != null && workItemName.contains("InwardDEO") && this.checkNull(hashMap.get("CLINWARDNO"))) {
//               automatictasklog.info("ECS Savvion " + methodName + " Service:::: CLINWARDNO is null or empty");
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  if (responseCode == 0) {
//                     if (isQueried != null && isQueried.equals("Yes") && clInwardNo != null && !clInwardNo.equals("")) {
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closing the associated instance");
//                        resultArray = db.getQueryQueueTaskName(alInwardNo, clInwardNo);
//                        associatedPIName = resultArray[0];
//                        associatedWIName = resultArray[1];
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: swaping workitem names");
//                        workItemName = workItemName + associatedWIName;
//                        associatedWIName = workItemName.substring(0, workItemName.length() - associatedWIName.length());
//                        workItemName = workItemName.substring(associatedWIName.length(), workItemName.length());
//                        processInstanceName = processInstanceName + associatedPIName;
//                        associatedPIName = processInstanceName.substring(0, processInstanceName.length() - associatedPIName.length());
//                        processInstanceName = processInstanceName.substring(associatedPIName.length(), processInstanceName.length());
//                        new ResponseObject();
//                        associatedresObj = this.completeAssociatedInstance(userId, associatedWIName, associatedPIName);
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: closed the associated instance and response is " + associatedresObj.getResponseCode());
//                     }
//
//                     if (associatedresObj == null || associatedresObj.getResponseCode() == 5000) {
//                        if (workItemName != null && !workItemName.equals("") && workItemName.contains("Query")) {
//                           workItemCaseStatus = "QueryCompleted";
//                           automatictasklog.info("ECS Savvion " + methodName + " setting isQueriedCL flag as true");
//                           hm.put("isQueriedCL", true);
//                        }
//
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: getting sessionid for user to set the dataslots");
//                        BLServer blserver = BLClientUtil.getBizLogicServer();
//                        Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                        ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                        automatictasklog.info("ECS Savvion " + methodName + " Service:::: updating dataslot values ");
//                        if (!this.checkNull(hashMap.get("CLNO"))) {
//                           hm.put("CLNo", hashMap.get("CLNO").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                           workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                        }
//
//                        if (workItemName != null && workItemName.contains("InwardDEO")) {
//                           automatictasklog.info("workItemCaseStatus when CLInwardDEO submits task " + workItemCaseStatus);
//                           hm.put("isCLInwardDEOAccepted", workItemCaseStatus);
//                           if (!this.checkNull(hashMap.get("CLINWARDNO"))) {
//                              hm.put("CLInwardNo", hashMap.get("CLINWARDNO").toString());
//                           }
//
//                           if (!this.checkNull(hashMap.get("CLCASETYPE"))) {
//                              hm.put("CLCaseType", hashMap.get("CLCASETYPE").toString());
//                           }
//                        }
//
//                        if (workItemName != null && workItemName.contains("Executive")) {
//                           hm.put("HasCLExecutiveAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("Editor")) {
//                           hm.put("HasCLEditorAccepted", workItemCaseStatus);
//                        }
//
//                        String dsv;
//                        if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                           dsv = (String)pi.getDataSlotValue("CLEditorRemarks");
//                           if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                              dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                           } else {
//                              dsv = hashMap.get("APPROVERREMARKS").toString();
//                           }
//
//                           hm.put("CLEditorRemarks", dsv);
//                        }
//
//                        if (workItemName != null && workItemName.contains("Approver")) {
//                           hm.put("HasCLApproverAccepted", workItemCaseStatus);
//                           if (workItemCaseStatus.equals("Approve")) {
//                              automatictasklog.info("ECS Savvion " + methodName + " resetting isQueriedCL flag as false");
//                              hm.put("isQueriedCL", false);
//                           }
//                        }
//
//                        if (!this.checkNull(hashMap.get("APPROVERREMARKS"))) {
//                           dsv = (String)pi.getDataSlotValue("CLApproverRemarks");
//                           if (dsv != null && !dsv.equalsIgnoreCase("<null>")) {
//                              dsv = dsv + "|" + hashMap.get("APPROVERREMARKS").toString();
//                           } else {
//                              dsv = hashMap.get("APPROVERREMARKS").toString();
//                           }
//
//                           hm.put("CLApproverRemarks", dsv);
//                        }
//
//                        if (workItemName != null && workItemName.contains("EFTChecker")) {
//                           hm.put("HasEFTCheckerAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("PaymentRequester")) {
//                           hm.put("HasPaymentRequesterAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("AmountApprover")) {
//                           hm.put("HasCLAmountApproverAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("NetworkDeviator")) {
//                           hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("ServiceDeviator")) {
//                           hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("PolicyDeviator")) {
//                           hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("OtherDeviator")) {
//                           hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("QualityChecker")) {
//                           hm.put("HasQualityCheckerAccepted", workItemCaseStatus);
//                        }
//
//                        if (workItemName != null && workItemName.contains("TagChecker")) {
//                           hm.put("HasCLTagCheckerAccepted", workItemCaseStatus);
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSAMOUNTAPPROVAL"))) {
//                           hm.put("NeedAmountApproval", hashMap.get("NEEDSAMOUNTAPPROVAL").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASNETWORKDEVIATION"))) {
//                           hm.put("HasNetworkDeviation", hashMap.get("HASNETWORKDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASSERVICEDEVIATION"))) {
//                           hm.put("HasServiceDeviation", hashMap.get("HASSERVICEDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASPOLICYDEVIATION"))) {
//                           hm.put("HasPolicyDeviation", hashMap.get("HASPOLICYDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASOTHERDEVIATION"))) {
//                           hm.put("HasOtherDeviation", hashMap.get("HASOTHERDEVIATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSINVESTIGATION"))) {
//                           hm.put("NeedInvestigation", hashMap.get("NEEDSINVESTIGATION").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSEFTCHECK"))) {
//                           hm.put("NeedEFTCheck", hashMap.get("NEEDSEFTCHECK").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSTOFAX"))) {
//                           hm.put("NeedToFax", hashMap.get("NEEDSTOFAX").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("NEEDSTOPRINT"))) {
//                           hm.put("NeedToPrint", hashMap.get("NEEDSTOPRINT").toString());
//                        }
//
//                        if (!this.checkNull(hashMap.get("HASALLOWANCE"))) {
//                           hm.put("HasAllowance", hashMap.get("HASALLOWANCE").toString());
//                        }
//
//                        if (blsession == null) {
//                           responseCode = 5013;
//                           responseMessage = "USER_ID_INVALID";
//                        } else {
//                           pi.updateSlotValue(hm);
//                           pi.save();
//                           Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName ::: " + workItemName + " ::: " + workItemCaseStatus);
//                           if ((workItemName.contains("Deviator") || workItemName.contains("AmountApp")) && workItemCaseStatus.equals("Reject") || workItemName.contains("EFTChecker") && workItemCaseStatus.equals("SendBack") || workItemName.contains("Approver") && (workItemCaseStatus.equals("Query") || workItemCaseStatus.equals("Reject") || workItemCaseStatus.equals("SendBack"))) {
//                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemName for completeMultipleTask :" + workItemName);
//                              completedWorkItem = this.completeMultipleTask(processInstanceName, workItemName, blserver, blsession);
//                              automatictasklog.info("completedWorkItem size is " + completedWorkItem.length);
//                           }
//
//                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                              automatictasklog.warn("ECS AssignTask enterred");
//                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                              automatictasklog.warn("ECS Task Assigned to user");
//                           }
//
//                           automatictasklog.warn("ECS CompleteTask enterred");
//                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                           automatictasklog.warn("ECS Task Completed");
//                           responseCode = 5000;
//                           responseMessage = "SUCCESS";
//
//                           try {
//                              pi = blserver.getProcessInstance(blsession, processInstanceName);
//                              automatictasklog.warn("ECS processInstance " + pi);
//                           } catch (Exception var44) {
//                              isInstanceCompleted = true;
//                              automatictasklog.warn("ECS processInstance " + processInstanceName + " got completed is " + isInstanceCompleted);
//                           }
//
//                           nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                           isQueriedCL = (Boolean)pi.getDataSlotValue("isQueriedCL");
//                           if (workItemName.contains("AmountApprover") || workItemName.contains("Deviator")) {
//                              try {
//                                 Thread.sleep(3000L);
//                              } catch (InterruptedException var43) {
//                                 var43.printStackTrace();
//                              }
//                           }
//
//                           wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)hashMap.get("NEEDSINVESTIGATION"), (String)null);
//                        }
//                     }
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var45) {
//            if (!isInstanceCompleted) {
//               responseCode = 5017;
//               responseMessage = "DS_NAME_INVALID";
//            }
//
//            var45.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var45.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var42) {
//               var42.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var42.getMessage());
//            }
//
//         }
//
//         if (associatedPIName != null && !associatedPIName.equals("") && associatedWIName != null && !associatedWIName.equals("") && associatedresObj.getResponseCode() == 5000 && responseCode == 5000) {
//            ArrayList workItemList = new ArrayList();
//            WorkItemObject workitem = new WorkItemObject();
//            workitem.setName(workItemName);
//            workitem.setCaseStatus("Completed");
//            workItemList.add(workitem);
//            completedWorkItem = this.getResultWorkitems(workItemList);
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         if (completedWorkItem != null && completedWorkItem.length != 0) {
//            resObj.setCompletedWorkItemArray(completedWorkItem);
//         }
//
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteCSGFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteCSGFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  HashMap hm = new HashMap();
//                  if (workItemName != null && workItemName.contains("Approver")) {
//                     hm.put("HasCSGApproverAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemName != null && workItemName.contains("Payment")) {
//                     hm.put("HasPaymentRequesterAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemName != null && workItemName.contains("Account")) {
//                     hm.put("HasCLAccountsAccepted", workItemCaseStatus);
//                  }
//
//                  if (blsession != null) {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     }
//
//                     automatictasklog.warn("ECS CompleteTask enterred");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                     automatictasklog.warn("ECS Task Completed");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//
//                     try {
//                        blserver.getProcessInstance(blsession, processInstanceName);
//                     } catch (Exception var32) {
//                        isInstanceCompleted = true;
//                     }
//
//                     nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                     wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)null);
//                  } else {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var33) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var33.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var33.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var31) {
//               var31.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var31.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject mapUserToGroup(RequestObject[] reqObj) {
//      String result = null;
//      String methodName = "mapUserToGroup";
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//         String groupName = (String)hashMap.get("GROUPNAME");
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName);
//
//         try {
//            automatictasklog.debug("ECS Savvion: " + methodName + " Service START ::: INPUT VALUES are userId" + userId);
//            if (!userId.equals("") && userId != null) {
//               groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
//               status = groupObj.addUserMember(userId);
//               if (status) {
//                  automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName + " success");
//                  responseMessage = "SUCCESS";
//                  responseCode = 5000;
//               } else {
//                  automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName + " failed");
//                  responseCode = 5035;
//                  responseMessage = "FAILURE_EXCEPTION";
//               }
//            } else {
//               responseCode = 5001;
//               responseMessage = "USER_ID_NULL";
//            }
//         } catch (Exception var13) {
//            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception " + var13.getMessage());
//         }
//
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service END ::: " + responseCode + " :: Response Message :: " + responseMessage);
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      return resObj;
//   }
//
//   public ResponseObject completeALFlowMultipleTasks(RequestObject[] reqObj) {
//      String methodName = "completeALFlowMultipleTasks";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      String[] workItemNames = (String[])null;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         workItemNames = (String[])hashMap.get("WORKITEMNAMES");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemNames != null) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  HashMap hm = new HashMap();
//                  if (workItemNames[0] != null && workItemNames[0].contains("AmountApprover")) {
//                     hm.put("HasAmountApproverAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("NetworkDeviator")) {
//                     hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("ServiceDeviator")) {
//                     hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("PolicyDeviator")) {
//                     hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("OtherDeviator")) {
//                     hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (blsession == null) {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  } else {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     int index = 0;
//
//                     while(true) {
//                        if (index >= workItemNames.length) {
//                           responseCode = 5000;
//                           responseMessage = "SUCCESS";
//
//                           try {
//                              blserver.getProcessInstance(blsession, processInstanceName);
//                           } catch (Exception var34) {
//                              isInstanceCompleted = true;
//                           }
//
//                           nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                           if (workItemNames[0].contains("AmountApprover") || workItemNames[0].contains("Deviator")) {
//                              try {
//                                 Thread.sleep(5000L);
//                              } catch (InterruptedException var33) {
//                                 var33.printStackTrace();
//                              }
//                           }
//
//                           wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemNames[0], (String)null, (String)null);
//                           break;
//                        }
//
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:: Current Workitemname :: " + workItemNames[index]);
//                        if (workItemNames[index] == null && workItemNames[index].equals("null")) {
//                           automatictasklog.warn("ECS Savvion: " + methodName + " Service::::WorkItem[" + index + "] is NULL");
//                        } else {
//                           Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemNames[index]);
//                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                              automatictasklog.warn("ECS AssignTask enterred");
//                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemNames[0]);
//                              automatictasklog.warn("ECS Task Assigned to user");
//                           }
//
//                           automatictasklog.warn("ECS CompleteTask enterred");
//                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                           automatictasklog.warn("ECS Task Completed");
//                        }
//
//                        ++index;
//                     }
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var35) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var35.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var35.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var32) {
//               var32.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var32.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject completeCLFlowMultipleTasks(RequestObject[] reqObj) {
//      String methodName = "completeCLFlowMultipleTasks";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      String result = null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Completed";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      String[] workItemNames = (String[])null;
//      
//      if (reqObj == null) {
//         responseCode = 5038;
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         workItemNames = (String[])hashMap.get("WORKITEMNAMES");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && workItemNames != null) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  HashMap hm = new HashMap();
//                  if (workItemNames[0] != null && workItemNames[0].contains("AmountApprover")) {
//                     hm.put("HasCLAmountApproverAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("NetworkDeviator")) {
//                     hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("ServiceDeviator")) {
//                     hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("PolicyDeviator")) {
//                     hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("OtherDeviator")) {
//                     hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("EFTChecker")) {
//                     hm.put("HasEFTCheckerAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("CLApprover")) {
//                     hm.put("HasCLApproverAccepted", workItemCaseStatus);
//                  }
//
//                  if (workItemNames[0] != null && workItemNames[0].contains("Editor")) {
//                     hm.put("HasCLEditorAccepted", workItemCaseStatus);
//                  }
//
//                  if (blsession == null) {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                  } else {
//                     pi.updateSlotValue(hm);
//                     pi.save();
//                     int index = 0;
//
//                     while(true) {
//                        if (index >= workItemNames.length) {
//                           responseCode = 5000;
//                           responseMessage = "SUCCESS";
//
//                           try {
//                              blserver.getProcessInstance(blsession, processInstanceName);
//                           } catch (Exception var34) {
//                              isInstanceCompleted = true;
//                           }
//
//                           nextWorkItemCaseStatus = this.getNextTaskStatus(workItemCaseStatus);
//                           if (workItemNames[0].contains("AmountApprover") || workItemNames[0].contains("Deviation")) {
//                              try {
//                                 Thread.sleep(3000L);
//                              } catch (InterruptedException var33) {
//                                 var33.printStackTrace();
//                              }
//                           }
//
//                           wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemNames[0], (String)null, (String)null);
//                           break;
//                        }
//
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:: Current Workitemname :: " + workItemNames[index]);
//                        if (workItemNames[index] == null && workItemNames[index].equals("null")) {
//                           automatictasklog.warn("ECS Savvion: " + methodName + " Service::::WorkItem[" + index + "] is NULL");
//                        } else {
//                           Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                           WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemNames[index]);
//                           if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                              automatictasklog.warn("ECS AssignTask enterred");
//                              this.assignWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                              automatictasklog.warn("ECS Task Assigned to user");
//                           }
//
//                           automatictasklog.warn("ECS CompleteTask enterred");
//                           this.completeWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                           automatictasklog.warn("ECS Task Completed");
//                        }
//
//                        ++index;
//                     }
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var35) {
//            responseCode = 5017;
//            responseMessage = "DS_NAME_INVALID";
//            var35.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var35.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var32) {
//               var32.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var32.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         resObj.setResultworkItemArray(wiObjects);
//         resObj.setWorkItemCaseStatus(workItemCaseStatus);
//         resObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.error("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject assignOrKeepTask(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: assignTaskToUser Service ::START");
//      String methodname = "assignOrKeepTask";
//      new HashMap();
//      String workItemName = null;
//      String toBeAssignedUserId = null;
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      String performer = "";
//      boolean isQueriedAL = false;
//      boolean isQueriedCL = false;
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodname + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: " + methodname + " Service:::: INPUT VALUES " + hashMap);
//            workItemName = (String)hashMap.get("WORKITEMNAME");
//            toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//            automatictasklog.info("ECS Savvion: " + methodname + " Service:: Input Workitem name  :: " + workItemName);
//            if (workItemName != null || !workItemName.equals("null")) {
//               BLServer blserver = BLClientUtil.getBizLogicServer();
//               Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//               WorkItem wi = blserver.getWorkItem(blsession, workItemName);
//               ProcessInstance pi = blserver.getProcessInstance(blsession, wi.getProcessInstanceName());
//               automatictasklog.info("ECS Savvion: " + methodname + " Service:: Current Workitem :: " + wi + " Current ProcessInstance " + pi);
//               if (toBeAssignedUserId != null && !toBeAssignedUserId.equals("")) {
//                  automatictasklog.info("ECS Savvion: " + methodname + " Service:: Assign task to user");
//                  sessionId = this.connectUser(toBeAssignedUserId);
//                  automatictasklog.info("ECS Savvion: " + methodname + " Service:: Session Id :: " + sessionId);
//                  if (!sessionId.equals("false")) {
//                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        wi.assign(toBeAssignedUserId);
//                        wi.save();
//                        resObj.setWorkItemCaseStatus("Locked");
//                        responseCode = 5000;
//                        responseMessage = "SUCCESS";
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     } else {
//                        responseCode = 5020;
//                        responseMessage = "INVALID_WINAME";
//                        automatictasklog.warn("ECS Savvion: " + methodname + " Service::::WorkItem " + workItemName + " is not valid");
//                     }
//                  } else {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                     automatictasklog.warn("ECS Savvion: " + methodname + " Service::::user ID is not valid");
//                  }
//               } else {
//                  performer = this.getWorkItemPerformerECS(workItemName);
//                  if (performer != null && !performer.equals("")) {
//                     automatictasklog.info("ECS Savvion: " + methodname + " Service:: " + workItemName + " Already Acquired by " + performer);
//                  }
//
//                  if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
//                     automatictasklog.warn("ECS Available Task enterred");
//                     wi.makeAvailable();
//                     wi.save();
//                     resObj.setWorkItemCaseStatus("Kept");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                     automatictasklog.warn("ECS Task made available to the group");
//                  } else {
//                     responseCode = 5020;
//                     responseMessage = "INVALID_WINAME";
//                     automatictasklog.warn("ECS Savvion: " + methodname + " Service::::WorkItem " + workItemName + " is NULL");
//                  }
//               }
//            }
//         } catch (Exception var26) {
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//            automatictasklog.error("ECS Savvion: " + methodname + " Service:::: Catch Exception :::" + var26.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var25) {
//               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var25.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodname + " Service :::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   public ResponseObject addOrRemoveUserECS(RequestObject[] reqObj) {
//      String methodName = "addOrRemoveUserECS";
//      ResponseObject resObj = new ResponseObject();
//      int responseCode = 0;
//      String responseMessage = null;
//      AdvanceGroup groupObj = null;
//      boolean status = false;
//      String[] groupNamesToAdd = (String[])null;
//      String[] groupNamesToRemove = (String[])null;
//      new HashMap();
//      boolean canAdd = true;
//      boolean canRemove = false;
//      boolean isGroupExist = false;
//      User sbmUser = null;
//      Realm usr = null;
//      boolean isExistingUser = false;
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)this.getMap(reqObj).get("USERIDTOCREATE");
//         String firstName = (String)this.getMap(reqObj).get("FIRSTNAME");
//         String lastName = (String)this.getMap(reqObj).get("LASTNAME");
//         String emailId = (String)this.getMap(reqObj).get("EMAIL");
//         String action = (String)this.getMap(reqObj).get("ACTION");
//         if (!this.checkNull(hashMap.get("GROUPNAMESTOADD"))) {
//            groupNamesToAdd = (String[])hashMap.get("GROUPNAMESTOADD");
//         }
//
//         if (!this.checkNull(hashMap.get("GROUPNAMESTOREMOVE"))) {
//            groupNamesToRemove = (String[])hashMap.get("GROUPNAMESTOREMOVE");
//         }
//
//         if (userId != null && !userId.equals("") && action != null && !action.equals("")) {
//            if (groupNamesToAdd != null) {
//               isGroupExist = this.isGroupExist(groupNamesToAdd);
//               if (!isGroupExist) {
//                  automatictasklog.info("ECS Savvion: isGroupExist Service:::: input group name to add does not exist in Savvion");
//                  responseCode = 5024;
//                  responseMessage = "GROUPNAME_NOT_EXIST";
//               }
//            }
//
//            RequestObject[] reqObjAddGroup;
//            ResponseObject addResponse;
//            if (action.equals("AddUser")) {
//               try {
//                  boolean result = false;
//                  usr = UserManager.getDefaultRealm();
//                  sbmUser = usr.getUser(userId);
//                  if (sbmUser == null) {
//                     result = usr.addUser(userId);
//                     if (result) {
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User created in Savvion");
//                        sbmUser = usr.getUser(userId);
//                        sbmUser.setAttribute("password", userId);
//                        sbmUser.setAttribute("firstname", firstName);
//                        sbmUser.setAttribute("lastname", lastName);
//                        sbmUser.setAttribute("email", emailId);
//                     } else {
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User " + userId + " is not created in Savvion");
//                        int responseCode = true;
//                        responseMessage = "USER_CREATION_FAILED";
//                     }
//                  } else {
//                     result = true;
//                     isExistingUser = true;
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User already exist in savvion so proceed with mapping");
//                  }
//
//                  if (!result) {
//                     responseCode = 5023;
//                     responseMessage = "USER_CREATION_FAILED";
//                  } else {
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user to ECS group Start");
//                     reqObjAddGroup = new RequestObject[]{new RequestObject()};
//                     reqObjAddGroup[0].setKey("USERID");
//                     reqObjAddGroup[0].setValue(userId);
//                     new ResponseObject();
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: map user to ECS group" + reqObjAddGroup[0].getKey() + " " + reqObjAddGroup[0].getValue());
//                     addResponse = this.mapUserToECSGroup(reqObjAddGroup);
//                     responseCode = addResponse.getResponseCode();
//                     responseMessage = addResponse.getResponseMessage();
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user added to ECS group successfully and responseCode is " + responseCode);
//                     if (groupNamesToAdd == null || !isGroupExist || responseCode != 5000 && responseCode != 5011) {
//                        if (groupNamesToAdd != null || isGroupExist || responseCode != 5000) {
//                           responseCode = 5012;
//                           responseMessage = "USER_NOT_MAPPED";
//                           if (!isGroupExist) {
//                              responseCode = 5024;
//                              responseMessage = "GROUPNAME_NOT_EXIST";
//                           }
//
//                           if (groupNamesToAdd == null) {
//                              responseCode = 5010;
//                              responseMessage = "INPUT_VALUE_IS_NULL";
//                           }
//
//                           try {
//                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: trying to delete " + userId + " form savvion");
//                              User user = usr.getUser(userId);
//                              boolean process = false;
//                              if (user != null && !isExistingUser) {
//                                 process = usr.removeUser(userId);
//                              }
//
//                              if (user != null && !process) {
//                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + userId + "can't be deleted form savvion");
//                              }
//                           } catch (Exception var30) {
//                              automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured while deleting the user:::" + var30.getMessage());
//                           }
//                        }
//                     } else {
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user loop START");
//                        RequestObject[] reqObjMapToGroup = new RequestObject[]{new RequestObject(), null};
//                        reqObjMapToGroup[0].setKey("USERID");
//                        reqObjMapToGroup[0].setValue(userId);
//                        reqObjMapToGroup[1] = new RequestObject();
//                        reqObjMapToGroup[1].setKey("GROUPNAME");
//                        new ResponseObject();
//                        int addListLength = groupNamesToAdd.length;
//
//                        for(int i = 0; i < addListLength; ++i) {
//                           reqObjMapToGroup[1].setValue(groupNamesToAdd[i]);
//                           ResponseObject response = this.mapUserToGroup(reqObjMapToGroup);
//                           responseCode = response.getResponseCode();
//                           responseMessage = response.getResponseMessage();
//                           if (responseCode != 5000 && !isExistingUser) {
//                              try {
//                                 usr.removeUser(userId);
//                              } catch (Exception var31) {
//                                 automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured while deleting the user:::" + var31.getMessage());
//                              }
//
//                              automatictasklog.error("ECS Savvion: " + methodName + " Service:::: " + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
//                              break;
//                           }
//                        }
//                     }
//                  }
//               } catch (Exception var32) {
//                  automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var32.getMessage());
//                  responseCode = 5023;
//                  responseMessage = "USER_CREATION_FAILED";
//               }
//            } else {
//               try {
//                  User user = UserManager.getUser(userId);
//                  if (user == null) {
//                     responseCode = 5013;
//                     responseMessage = "USER_ID_INVALID";
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + "Response code is ::" + responseCode + " ::Respones Message is :: " + responseMessage);
//                  } else {
//                     int i;
//                     if (groupNamesToRemove != null) {
//                        int removeListLength = groupNamesToRemove.length;
//
//                        for(int i = 0; i < removeListLength; ++i) {
//                           if (!this.checkUserGroup(userId, groupNamesToRemove[i])) {
//                              canRemove = false;
//                              canAdd = false;
//                              break;
//                           }
//
//                           canRemove = true;
//                        }
//
//                        if (canRemove) {
//                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Action is " + action + " and REMOVE user from group START");
//                           RequestObject[] reqObjRemoveGroup = new RequestObject[]{new RequestObject(), null};
//                           reqObjRemoveGroup[0].setKey("USERID");
//                           reqObjRemoveGroup[0].setValue(userId);
//                           reqObjRemoveGroup[1] = new RequestObject();
//                           reqObjRemoveGroup[1].setKey("GROUPNAME");
//                           new ResponseObject();
//                           removeListLength = groupNamesToRemove.length;
//
//                           for(i = 0; i < removeListLength; ++i) {
//                              reqObjRemoveGroup[1].setValue(groupNamesToRemove[i]);
//                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user groups are " + groupNamesToRemove[i]);
//                              ResponseObject removeResponse = this.removeUserFromGroup(reqObjRemoveGroup);
//                              responseCode = removeResponse.getResponseCode();
//                              responseMessage = removeResponse.getResponseMessage();
//                              if (responseCode != 5000) {
//                                 canAdd = false;
//                                 this.revertTransaction(userId, groupNamesToRemove, (String[])null, "Remove", i, 0);
//                                 automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Remove loop :::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
//                                 break;
//                              }
//                           }
//                        } else {
//                           responseCode = 5012;
//                           responseMessage = "USER_NOT_MAPPED";
//                        }
//                     }
//
//                     if (groupNamesToAdd != null && canAdd) {
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Action is " + action + " and ADD user to group START");
//                        if (isGroupExist) {
//                           reqObjAddGroup = new RequestObject[]{new RequestObject(), null};
//                           reqObjAddGroup[0].setKey("USERID");
//                           reqObjAddGroup[0].setValue(userId);
//                           reqObjAddGroup[1] = new RequestObject();
//                           reqObjAddGroup[1].setKey("GROUPNAME");
//                           new ResponseObject();
//                           int addListLength = groupNamesToAdd.length;
//
//                           for(i = 0; i < addListLength; ++i) {
//                              reqObjAddGroup[1].setValue(groupNamesToAdd[i]);
//                              addResponse = this.mapUserToGroup(reqObjAddGroup);
//                              responseCode = addResponse.getResponseCode();
//                              responseMessage = addResponse.getResponseMessage();
//                              if (responseCode != 5000) {
//                                 this.revertTransaction(userId, groupNamesToRemove, groupNamesToAdd, "Add", groupNamesToRemove.length, i);
//                                 automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Add loop :::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
//                                 break;
//                              }
//                           }
//                        } else {
//                           responseCode = 5024;
//                           responseMessage = "GROUPNAME_NOT_EXIST";
//                        }
//                     }
//
//                     if (groupNamesToAdd == null && groupNamesToRemove == null) {
//                        responseCode = 5010;
//                        responseMessage = "INPUT_VALUE_IS_NULL";
//                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
//                     }
//                  }
//               } catch (Exception var33) {
//                  automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var33.getMessage());
//                  responseCode = 5035;
//                  responseMessage = "FAILURE_EXCEPTION";
//               }
//            }
//         } else {
//            responseCode = 5001;
//            responseMessage = "USER_ID_NULL";
//            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Input value is null:::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      return resObj;
//   }
//
//   private boolean isGroupExist(String[] groupNamesToAdd) {
//      automatictasklog.info("ECS Savvion: isGroupExist Service:::: START");
//      boolean isGroupExist = false;
//      int addListLength = groupNamesToAdd.length;
//      String[] groupNames = new String[50];
//      groupNames = UserManager.getDefaultRealm().getGroupNames();
//
//      for(int i = 0; i < addListLength; ++i) {
//         for(int j = 0; j < groupNames.length; ++j) {
//            if (groupNames[j].equals(groupNamesToAdd[i])) {
//               isGroupExist = true;
//               break;
//            }
//
//            isGroupExist = false;
//         }
//      }
//
//      automatictasklog.info("ECS Savvion: isGroupExist Service:::: Ends & result is " + isGroupExist);
//      return isGroupExist;
//   }
//
//   private void revertTransaction(String userId, String[] groupNamesToAdd, String[] groupNamesToRemove, String revertAction, int addCount, int removeCount) {
//      String methodName = "revertTransaction";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: START");
//      RequestObject[] reqObj = new RequestObject[]{new RequestObject(), null};
//      reqObj[0].setKey("USERID");
//      reqObj[0].setValue(userId);
//      reqObj[1] = new RequestObject();
//      reqObj[1].setKey("GROUPNAME");
//      new ResponseObject();
//      int i;
//      if (groupNamesToAdd != null) {
//         for(i = 0; i <= addCount; ++i) {
//            reqObj[1].setValue(groupNamesToRemove[i]);
//            automatictasklog.info("ECS Savvion: revertTransaction Service:::: mapping the user to groups which we removed in this transaction earlier");
//            this.mapUserToGroup(reqObj);
//         }
//      }
//
//      for(i = 0; i <= removeCount; ++i) {
//         reqObj[1].setValue(groupNamesToRemove[i]);
//         automatictasklog.info("ECS Savvion: revertTransaction Service:::: removing the user from groups which were added in this transaction earlier");
//         this.removeUserFromGroup(reqObj);
//      }
//
//   }
//
//   private ResponseObject removeUserFromGroup(RequestObject[] reqObj) {
//      String result = null;
//      String methodName = "removeUserFromGroup";
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//         String groupName = (String)hashMap.get("GROUPNAME");
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Remove userId " + userId + " from the group " + groupName);
//
//         try {
//            if (!userId.equals("") && userId != null) {
//               if (this.checkUserGroup(userId, groupName)) {
//                  groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
//                  status = groupObj.removeUserMember(userId);
//                  if (status) {
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                  } else {
//                     responseCode = 5035;
//                     responseMessage = "FAILURE_EXCEPTION";
//                  }
//
//                  automatictasklog.debug("ECS Savvion: removeUserFromGroup Service::::userId is " + userId + " status is " + status);
//               } else {
//                  responseCode = 5012;
//                  responseMessage = "USER_NOT_MAPPED";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var13) {
//            automatictasklog.error("ECS Savvion: removeUserFromECS Service:::: Exception " + var13.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.debug("ECS Savvion: removeUserFromECS Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObj;
//   }
//
//   private boolean checkUserGroup(String userId, String groupName) {
//      boolean isMember = false;
//      String methodName = "checkUserGroup";
//      String groupNameList = "";
//      String[] groupNames = new String[50];
//
//      try {
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: userId is " + userId + " to check isMember of group name :: " + groupName);
//         User usr = UserManager.getUser(userId);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user object  is " + usr);
//         groupNames = usr.getGroupNames();
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user groups are " + groupNames + " :: groupNames length :: " + groupNames.length);
//
//         int i;
//         for(i = 0; i < groupNames.length; ++i) {
//            if (groupNameList == "") {
//               groupNameList = groupNames[i];
//            } else {
//               groupNameList = groupNameList + ", " + groupNames[i];
//            }
//         }
//
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user " + userId + " mapped to groups " + groupNameList);
//
//         for(i = 0; i < groupNames.length; ++i) {
//            if (groupNames[i].equals(groupName)) {
//               isMember = true;
//               automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user's " + i + " group Name is " + groupNames[i] + " :: isMember :: " + isMember);
//            }
//         }
//
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: userId is " + userId + " isMember of group name :: " + groupName + " :: " + isMember);
//      } catch (Exception var9) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception " + var9.getMessage());
//      }
//
//      return isMember;
//   }
//
//   public ResponseObject createOrRemoveGroupECS(RequestObject[] reqObj) {
//      String methodName = "createGroupECS";
//      AdvanceGroup groupObj = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      String[] groupToCreate = (String[])null;
//      String[] groupToRemove = (String[])null;
//      Realm usr = null;
//      if (reqObj == null) {
//         resObj.setResponseCode(5038);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)this.getMap(reqObj).get("USERID");
//         String action = (String)this.getMap(reqObj).get("ACTION");
//         if (!this.checkNull(hashMap.get("CREATEGROUPS"))) {
//            groupToCreate = (String[])hashMap.get("CREATEGROUPS");
//         }
//
//         if (!this.checkNull(hashMap.get("REMOVEGROUPS"))) {
//            groupToRemove = (String[])hashMap.get("REMOVEGROUPS");
//         }
//
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + "userId:: " + userId + " action ::" + action + "groupToCreate:: " + groupToCreate + " groupToRemove:: " + groupToRemove);
//         if (action != null && !action.equals("")) {
//            try {
//               usr = UserManager.getDefaultRealm();
//               int i;
//               if (action.equals("Create")) {
//                  automatictasklog.info("ECS Savvion: " + methodName + " Service :::  inside create group ");
//                  if (groupToCreate != null && groupToCreate.length != 0) {
//                     for(i = 0; i < groupToCreate.length; ++i) {
//                        String[] groupName = new String[]{groupToCreate[i]};
//                        boolean isGroupExist = this.isGroupExist(groupName);
//                        if (!isGroupExist) {
//                           boolean addResult = usr.addGroup(groupToCreate[i]);
//                           if (addResult) {
//                              this.responseCode = 5000;
//                              this.responseMessage = "SUCCESS";
//                           } else {
//                              this.responseCode = 5026;
//                              this.responseMessage = "GROUPCREATION_FAILED";
//                              automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//                           }
//                        }
//                     }
//                  } else {
//                     this.responseCode = 5010;
//                     this.responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//                  }
//               }
//
//               if (action.equals("Remove")) {
//                  automatictasklog.info("ECS Savvion: " + methodName + " Service :::  inside remove group ");
//                  if (groupToRemove != null && groupToRemove.length != 0) {
//                     if (this.isGroupExist(groupToRemove)) {
//                        for(i = 0; i < groupToRemove.length; ++i) {
//                           boolean removeResult = usr.removeGroup(groupToRemove[i]);
//                           if (removeResult) {
//                              this.responseCode = 5000;
//                              this.responseMessage = "SUCCESS";
//                           } else {
//                              this.responseCode = 5027;
//                              this.responseMessage = "GROUP_REMOVE_FAILED";
//                              automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//                           }
//                        }
//                     } else {
//                        this.responseCode = 5024;
//                        this.responseMessage = "GROUPNAME_NOT_EXIST";
//                        automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//                     }
//                  } else {
//                     this.responseCode = 5010;
//                     this.responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//                  }
//               }
//            } catch (Exception var15) {
//               automatictasklog.info("ECS Savvion :: " + methodName + " Exception :: " + var15);
//               this.responseCode = 5035;
//               this.responseMessage = "FAILURE_EXCEPTION";
//               automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + this.responseCode + " :: Response Message :: " + this.responseMessage);
//            }
//         } else {
//            this.responseCode = 5010;
//            this.responseMessage = "INPUT_VALUE_IS_NULL";
//            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Input value is null:::" + "Response code ::" + this.responseCode + " ::Respones Message :: " + this.responseMessage);
//         }
//
//         resObj.setResponseCode(this.responseCode);
//         resObj.setResponseMessage(this.responseMessage);
//      }
//
//      return resObj;
//   }
//
//   public ResponseObject UpdateAndCompleteSRFlowTask(RequestObject[] reqObj) {
//      String methodName = "UpdateAndCompleteSRFlowTask";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
//      new DBUtility(this.SBM_HOME);
//      String sessionId = null;
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      ResponseObject resObjSR = new ResponseObject();
//      WorkItemObject[] wiObjects = (WorkItemObject[])null;
//      String workItemCaseStatus = "Approve";
//      String nextWorkItemCaseStatus = "New";
//      boolean isInstanceCompleted = false;
//      new HashMap();
//      new ArrayList();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      String ClosureState = "";
//      new ResponseObject();
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObjSR.setResponseCode(responseCode);
//         resObjSR.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] alInwardNo = (String[])hashMap.get("ALINWARDNO");
//         String[] var22 = (String[])hashMap.get("CLINWARDNO");
//
//         try {
//            if (srPIName != null && !srPIName.equals("") && workItemName != null && !workItemName.equals("") && !this.checkNull(workItemName)) {
//               if (userId != null && !userId.equals("")) {
//                  BLServer blserver = BLClientUtil.getBizLogicServer();
//                  Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//                  blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                  blserver.getProcessInstance(blsession, srPIName);
//                  if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//                     workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//                  }
//
//                  if (workItemCaseStatus.equals("Approve")) {
//                     ResponseObject responseObj = this.completeALCLTask(reqObj);
//                     if (responseObj.getResponseCode() == 5000) {
//                        resObjSR = this.completeSRTask(reqObj);
//                     }
//                  } else {
//                     resObjSR = this.completeSRTask(reqObj);
//                  }
//               } else {
//                  responseCode = 5001;
//                  responseMessage = "USER_ID_NULL";
//               }
//            } else {
//               responseCode = 5010;
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//         } catch (Exception var35) {
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//            var35.printStackTrace();
//            automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var35.getMessage());
//         } finally {
//            try {
//               this.disconnect((String)sessionId);
//            } catch (Exception var34) {
//               var34.printStackTrace();
//               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Finally Exception:::" + var34.getMessage());
//            }
//
//         }
//
//         resObjSR.setResponseCode(responseCode);
//         resObjSR.setResponseMessage(responseMessage);
//         resObjSR.setResultworkItemArray(wiObjects);
//         resObjSR.setWorkItemCaseStatus(workItemCaseStatus);
//         resObjSR.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage);
//      return resObjSR;
//   }
//
//   private ResponseObject completeALCLTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
//      String methodName = "completeALCLTask";
//      int responseCode = 0;
//      String responseMessage = "";
//      String ClosureState = "";
//      ArrayList resultTaskList = null;
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      HashMap hm = new HashMap();
//      new HashMap();
//      ResponseObject responseObj = new ResponseObject();
//      ArrayList workItemList = new ArrayList();
//      WorkItemObject workitem = new WorkItemObject();
//      WorkItemObject[] completedWorkItem = new WorkItemObject[20];
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         responseObj.setResponseCode(responseCode);
//         responseObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String srPIName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] alInwardNo = (String[])hashMap.get("ALINWARDNO");
//         String[] clInwardNo = (String[])hashMap.get("CLINWARDNO");
//
//         try {
//            DBUtility db = new DBUtility(this.SBM_HOME);
//            BLServer blserver = BLClientUtil.getBizLogicServer();
//            Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//            Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            ProcessInstance srPI = blserver.getProcessInstance(blsession, srPIName);
//            String SRType = (String)srPI.getDataSlotValue("SRType");
//            resultTaskList = db.getPINameByInwardNo(alInwardNo, clInwardNo, SRType);
//            automatictasklog.debug("ECS Savvion: " + methodName + " Service::resultTaskList size is  " + resultTaskList.size());
//            if (resultTaskList == null && resultTaskList.size() == 0) {
//               automatictasklog.error("ECS Savvion: " + methodName + " Service:: Objects Count " + resultTaskList.size());
//            } else {
//               workItemObject = this.getResultWorkitems(resultTaskList);
//               automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject :" + workItemObject);
//            }
//
//            ProcessInstance newPI = null;
//            String ALCASETYPE;
//            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosure") || SRType.equals("CLClosure") || SRType.equals("ALTermination") || SRType.equals("CLTermination"))) {
//               for(int i = 0; i < workItemObject.length; ++i) {
//                  newPI = blserver.getProcessInstance(blsession, workItemObject[i].getPiName());
//                  ALCASETYPE = workItemObject[i].getWorkStepName();
//                  this.getWorkFlowClosureState(workItemObject);
//                  WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemObject[i].getName());
//                  if (blsession != null) {
//                     newPI.updateSlotValue(hm);
//                     newPI.save();
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        automatictasklog.warn("ECS AssignTask enterred");
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemObject[i].getName());
//                        automatictasklog.warn("ECS Task Assigned to user");
//                     }
//
//                     automatictasklog.warn("ECS Complete SR Flow Task enterred");
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemObject[i].getName());
//                     automatictasklog.warn("SR Flow ECS Task Completed");
//                     responseCode = 5000;
//                     responseMessage = "SUCCESS";
//                  }
//
//                  workitem.setName(workItemObject[i].getName());
//                  workitem.setCaseStatus("Closed");
//                  if (SRType.equals("ALTermination") || SRType.equals("CLTermination")) {
//                     workitem.setCaseStatus("Terminated");
//                  }
//
//                  workItemList.add(workitem);
//               }
//            }
//
//            if (SRType != null && !SRType.equals("") && (SRType.equals("ALClosureReopen") || SRType.equals("ALRejectionReopen"))) {
//               String[] reOpenStep = (String[])hashMap.get("ClosureState");
//               ALCASETYPE = (String)newPI.getDataSlotValue("ALCaseType");
//               RequestObject[] objInput = new RequestObject[]{new RequestObject(), null, null};
//               objInput[0].setKey("PROCESSNAME");
//               objInput[0].setValue("RGICL_ECS_AL_FLOW");
//               objInput[1] = new RequestObject();
//               objInput[1].setKey("USERID");
//               objInput[1].setValue(userId);
//               objInput[2] = new RequestObject();
//               objInput[2].setKey(ALCASETYPE);
//               objInput[2].setValue("DF");
//               this.CreateWorkFlow(objInput);
//            }
//         } catch (Exception var30) {
//            var30.printStackTrace();
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//         }
//
//         completedWorkItem = this.getResultWorkitems(workItemList);
//         responseObj.setResponseCode(responseCode);
//         responseObj.setResponseMessage(responseMessage);
//         responseObj.setCompletedWorkItemArray(completedWorkItem);
//      }
//
//      return responseObj;
//   }
//
//   private String getWorkFlowClosureState(WorkItemObject[] workItemObject) {
//      String closureState = "";
//
//      for(int i = 0; i < workItemObject.length; ++i) {
//         if (workItemObject[i].getName().contains("ALExecutive")) {
//            closureState = "ALExecutive";
//         }
//
//         if (workItemObject[i].getName().contentEquals("AL_FLOW") && (workItemObject[i].getName().contains("ALApprover") || workItemObject[i].getName().contains("Deviator") || workItemObject[i].getName().contains("Amount") || workItemObject[i].getName().contains("Investigator"))) {
//            closureState = "ALApprover";
//         }
//
//         if (workItemObject[i].getName().contains("TagMaker")) {
//            closureState = "TagMaker";
//         }
//
//         if (workItemObject[i].getName().contains("TagChecker")) {
//            closureState = "TagChecker";
//         }
//      }
//
//      return closureState;
//   }
//
//   private ResponseObject completeSRTask(RequestObject[] reqObj) throws RemoteException, AxisFault {
//      String methodName = "completeSRTask";
//      int responseCode = 0;
//      String responseMessage = "";
//      String ClosureState = "";
//      ArrayList resultTaskList = null;
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      HashMap hm = new HashMap();
//      new HashMap();
//      ResponseObject responseObj = new ResponseObject();
//      new ArrayList();
//      String workItemCaseStatus = "Approve";
//      boolean isInstanceCompleted = false;
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         responseObj.setResponseCode(responseCode);
//         responseObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String piName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            BLServer blserver = BLClientUtil.getBizLogicServer();
//            Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//            Session blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            ProcessInstance pi = blserver.getProcessInstance(blsession, piName);
//            WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
//            if (!this.checkNull(hashMap.get("APPROVERDECISION"))) {
//               workItemCaseStatus = hashMap.get("APPROVERDECISION").toString();
//            }
//
//            if (workItemName != null && workItemName.contains("SRChecker")) {
//               hm.put("HasSRCheckerAccepted", workItemCaseStatus);
//            }
//
//            if (workItemName != null && workItemName.contains("PaymentChecker")) {
//               hm.put("HasPaymentCheckerAccepted", workItemCaseStatus);
//            }
//
//            if (blsession != null) {
//               pi.updateSlotValue(hm);
//               pi.save();
//               if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                  automatictasklog.warn("ECS AssignTask enterred");
//                  this.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                  automatictasklog.warn("ECS Task Assigned to user");
//               }
//
//               automatictasklog.warn("ECS Complete SR Flow Task enterred");
//               this.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//               automatictasklog.warn("SR Flow ECS Task Completed");
//               responseCode = 5000;
//               responseMessage = "SUCCESS";
//            }
//
//            try {
//               pi = blserver.getProcessInstance(blsession, piName);
//               automatictasklog.warn("ECS processInstance " + pi);
//            } catch (Exception var24) {
//               isInstanceCompleted = true;
//               automatictasklog.warn("ECS processInstance " + piName + " got completed is " + isInstanceCompleted);
//            }
//         } catch (Exception var25) {
//            var25.printStackTrace();
//            responseCode = 5035;
//            responseMessage = "FAILURE_EXCEPTION";
//         }
//
//         responseObj.setResponseCode(responseCode);
//         responseObj.setResponseMessage(responseMessage);
//         responseObj.setWorkItemCaseStatus(workItemCaseStatus);
//         responseObj.setInstanceCompleted(isInstanceCompleted);
//      }
//
//      return responseObj;
//   }
//
//   public ResponseObject completePI(RequestObject[] reqObj) {
//      ResponseObject resObj = new ResponseObject();
//      int responseCode = 0;
//      String responseMessage = "";
//      new HashMap();
//      String nextWorkItemCaseStatus = "New";
//      HashMap hm = new HashMap();
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String methodName = "completePI";
//      boolean isInstanceCompleted = false;
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      WorkItemObject[] completedWorkItem = (WorkItemObject[])null;
//      ArrayList resultTaskList = null;
//      ArrayList workItemList = new ArrayList();
//      if (reqObj == null) {
//         responseCode = 5038;
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] alInwardNo = (String[])hashMap.get("ALINWARDNO");
//         String[] clInwardNo = (String[])hashMap.get("CLINWARDNO");
//         String SRType = (String)hashMap.get("SRTYPE");
//
//         try {
//            BLServer blserver = BLClientUtil.getBizLogicServer();
//            Session blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//            ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//            if (!this.checkNull(hashMap.get("EMPLOYEEID"))) {
//               hm.put("EmployeeId", hashMap.get("EMPLOYEEID").toString());
//            }
//
//            if (!this.checkNull(hashMap.get("HOSPITALNAME"))) {
//               hm.put("HospitalName", hashMap.get("HOSPITALNAME").toString());
//            }
//
//            resultTaskList = db.getPINameByInwardNo(alInwardNo, clInwardNo, SRType);
//            automatictasklog.info("resultTaskList size is " + resultTaskList.size());
//            workItemObject = this.getResultWorkitems(resultTaskList);
//            completedWorkItem = new WorkItemObject[workItemObject.length];
//
//            for(int i = 0; i < workItemObject.length; ++i) {
//               WorkItemObject workitem = new WorkItemObject();
//               workitem.setName(workItemObject[i].getName());
//               workitem.setCaseStatus("Completed");
//               workItemList.add(workitem);
//            }
//
//            completedWorkItem = this.getResultWorkitems(workItemList);
//            automatictasklog.info("completedWorkItem size is " + completedWorkItem.length);
//            pi.updateSlotValue(hm);
//            pi.complete();
//            responseCode = 5000;
//            responseMessage = "SUCCESS";
//
//            try {
//               pi = blserver.getProcessInstance(blsession, processInstanceName);
//               automatictasklog.warn("ECS processInstance " + pi);
//            } catch (Exception var26) {
//               isInstanceCompleted = true;
//               automatictasklog.warn("ECS processInstance " + processInstanceName + " got completed is " + isInstanceCompleted);
//            }
//         } catch (Exception var27) {
//            automatictasklog.info("ECS Savvion :: " + methodName + " Exception :: " + var27);
//         }
//      }
//
//      resObj.setResponseCode(responseCode);
//      resObj.setResponseMessage(responseMessage);
//      resObj.setInstanceCompleted(isInstanceCompleted);
//      resObj.setCompletedWorkItemArray(completedWorkItem);
//      return resObj;
//   }
//}
