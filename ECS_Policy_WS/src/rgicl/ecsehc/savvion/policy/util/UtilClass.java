package rgicl.ecsehc.savvion.policy.util;
//
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
//import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
//import com.tdiinc.userManager.AdvanceGroup;
//import com.tdiinc.userManager.User;
//import com.tdiinc.userManager.UserManager;
//import java.io.FileInputStream;
//import java.math.BigDecimal;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Arrays;
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
//import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
//import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
//import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
//import rgicl.ecsehc.savvion.policy.util.db.DBUtility;
//
public class UtilClass {
	
}
//   Properties logProperties = null;
//   Properties config = null;
//   HashMap hashMap = null;
//   HashMap resultHashMap = null;
//   String sbm_home = null;
//   private static final String SBM_HOME = System.getProperty("sbm.home");
//   private static final String ECS_SAVVION_PROPERTIES;
//   private static final String ECS_SAVVION_LOG_PROPERTIES;
//   private static Properties propertiesECSSavvion;
//   private static Properties propertiesECSLog;
//   private static Logger automatictasklog;
//   private static BizLogicManager pak;
//   private static Byte[] bytearray;
//   private static String ECSADMIN;
//   private static Byte[] bytearrayECSADMIN;
//   DBUtility db;
//
//   static {
//      ECS_SAVVION_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_PROPERTIES.properties";
//      ECS_SAVVION_LOG_PROPERTIES = SBM_HOME + "/conf/ECS_SAVVION_LOG_PROPERTIES.properties";
//      automatictasklog = null;
//      pak = null;
//      bytearray = new Byte[0];
//      ECSADMIN = null;
//      bytearrayECSADMIN = new Byte[0];
//
//      try {
//         propertiesECSSavvion = new Properties();
//         propertiesECSSavvion.load(new FileInputStream(ECS_SAVVION_PROPERTIES));
//         propertiesECSLog = new Properties();
//         propertiesECSLog.load(new FileInputStream(ECS_SAVVION_LOG_PROPERTIES));
//         PropertyConfigurator.configure(propertiesECSLog);
//         automatictasklog = Logger.getLogger("Automatic_HCS");
//      } catch (Exception var1) {
//         throw new RuntimeException("Error HCS loading logger properties", var1);
//      }
//   }
//
//   public UtilClass(String propertiesPath) {
//      this.db = new DBUtility(SBM_HOME);
//   }
//
//   public String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
//      String pi = null;
//
//      try {
//         pi = this.getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
//         return pi;
//      } catch (RemoteException var8) {
//         throw new AxisFault("SBM Web services error :" + var8.getMessage());
//      }
//   }
//
//   public String getUserPasswordECS(String userId) {
//      String password = "";
//
//      try {
//         User userObject = UserManager.getUser(userId);
//         password = userObject.getAttribute("password");
//         PService p = PService.self();
//         password = p.decrypt(password);
//      } catch (Exception var5) {
//         automatictasklog.error("ECS Savvion UtilClass: getUserPasswordECS Service::ERROR:: USERID is " + userId + var5.getMessage());
//      }
//
//      return password;
//   }
//
//   public HashMap getMap(RequestObject[] reqObj) {
//      HashMap hm = new HashMap();
//      int arrayObjLength = reqObj.length;
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
//            automatictasklog.info("ECS Savvion UtilClass:getMap Utility Service::::Request Object Length :: " + arrayObjLength);
//         }
//      } catch (Exception var7) {
//         automatictasklog.error("ECS Savvion UtilClass: getMap Utility Service:::: Exception :" + var7);
//      }
//
//      return hm;
//   }
//
//   public boolean checkNull(Object dsValue) {
//      return dsValue == null || dsValue instanceof String && dsValue.equals("<NULL>");
//   }
//
//   public boolean disconnect(String sessionId) {
//      try {
//         this.getBizLogicManager().disconnect(sessionId);
//         return true;
//      } catch (Exception var3) {
//         return false;
//      }
//   }
//
//   public void assignWorkitem(String sessionId, String wiName) throws AxisFault {
//      try {
//         PAKClientWorkitem pwi = this.getBizLogicManager().getWorkitem(sessionId, wiName);
//         this.getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
//      } catch (RemoteException var4) {
//         throw new AxisFault("SBM Web services error :" + var4.getMessage());
//      }
//   }
//
//   public BizLogicManager getBizLogicManager() throws AxisFault {
//      if (pak == null) {
//         synchronized(bytearray) {
//            if (pak == null) {
//               try {
//                  String appserver = ServiceLocator.self().getAppServerID();
//                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
//                  pak = blmhome.create();
//               } catch (Throwable var4) {
//                  throw new AxisFault("SBM Web services error :" + var4.getMessage());
//               }
//            }
//         }
//      }
//
//      return pak;
//   }
//
//   public void completeWorkitem(String sessionId, String wiName) throws AxisFault {
//      try {
//         this.getBizLogicManager().completeWorkitem(sessionId, wiName);
//      } catch (RemoteException var4) {
//         throw new AxisFault("SBM Web services error :" + var4.getMessage());
//      }
//   }
//
//   public String getNextTaskStatus(String currentWorkItemStatus) {
//      automatictasklog.info("ECS Savvion UtilClass: getNextTaskStatus Service::::Starts  :: with currentWorkItemStatus as  :: " + currentWorkItemStatus);
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
//      automatictasklog.info("ECS Savvion UtilClass: getNextTaskStatus Service::::Ends  :: with nextWorkItemCaseStatus as  :: " + nextWorkItemCaseStatus);
//      return nextWorkItemCaseStatus;
//   }
//
//   public WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
//      WorkItemObject[] resultWorkitems = null;
//      int inboxSize = 400;
//      if (propertiesECSSavvion.getProperty("ECSInboxSize") != null) {
//         inboxSize = Integer.parseInt(propertiesECSSavvion.getProperty("ECSInboxSize"));
//      }
//
//      if (workitemlist.size() > inboxSize) {
//         automatictasklog.info("ECS getResultWorkitems :: Trimming WorkItemObject Array to : " + inboxSize + " actually workitemlist size is : " + workitemlist.size());
//         WorkItemObject[] inboxSizeWorkitems = new WorkItemObject[inboxSize];
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
//   public void checkUserNumbersInGroup(WorkItemObject[] wiObjects) {
//      String methodName = "checkUserNumbersInGroup";
//      ResponseObject resp = new ResponseObject();
//      AdvanceGroup groupObj = null;
//      resp.setResponseCode("5000");
//      if (wiObjects != null && wiObjects.length != 0) {
//         automatictasklog.info("ECS Savvion UtilClass " + methodName + " Service :: WorkItemObject length :: " + wiObjects.length);
//         WorkItemObject[] var8 = wiObjects;
//         int var7 = wiObjects.length;
//
//         for(int var6 = 0; var6 < var7; ++var6) {
//            WorkItemObject workItemObj = var8[var6];
//            String workItemName = workItemObj.getName();
//            String groupName = workItemObj.getQueue();
//            if (workItemName.contains("PMT")) {
//               groupName = "PMTMember";
//            }
//
//            if (workItemName.contains("EditorHold") || workItemName.contains("ApproverHold") || workItemName.contains("ApproverDummy")) {
//               groupName = "DummyQueue";
//            }
//
//            if (workItemName.contains("RelationshipManager")) {
//               groupName = "RelationshipManager";
//            }
//
//            if (workItemName.contains("CLAccountsNegative")) {
//               groupName = "CLAccounts";
//            }
//
//            automatictasklog.info("ECS Savvion UtilClass " + methodName + " Service :: workItemName :: " + workItemName + " groupName :: " + groupName);
//            groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
//            String[] groupMembers = groupObj.getAllUserMemberNames();
//            automatictasklog.info("ECS Savvion UtilClass " + methodName + " Service :: groupObj" + groupObj + " no of users in groupName :: " + groupName + " is " + groupMembers.length);
//            if (groupMembers.length < 2) {
//               RequestObject[] reqObj1 = new RequestObject[]{new RequestObject()};
//               reqObj1[0].setKey("WORKITEMNAME");
//               reqObj1[0].setValue(workItemName);
//               resp = this.assignOrKeepTask(reqObj1);
//            }
//         }
//      }
//
//      automatictasklog.info("ECS Savvion UtilClass " + methodName + " Service :: response is " + resp.getResponseCode());
//   }
//
//   public String connectUser(String userId) {
//      String sessionId = null;
//      boolean isMember = false;
//      String password = null;
//
//      try {
//         password = this.getUserPasswordECS(userId);
//         isMember = this.checkUserECS(userId, "ECS");
//         if (isMember) {
//            sessionId = this.getBizLogicManager().connect(userId, password);
//         } else {
//            automatictasklog.info("ECS Savvion: connectUser Service::::User " + userId + " Does not belongs to ECS");
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
//   public String connectECSADMIN() {
//      boolean valid = false;
//
//      try {
//         valid = this.getBizLogicManager().isSessionValid(ECSADMIN);
//      } catch (RemoteException var5) {
//         automatictasklog.error("ECS Savvion UtilClass: connectECSADMIN Service:::: Exception " + var5.getMessage());
//      }
//
//      synchronized(bytearrayECSADMIN) {
//         if (ECSADMIN == null || !valid) {
//            try {
//               ECSADMIN = this.getBizLogicManager().connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            } catch (Exception var4) {
//               ECSADMIN = "false";
//               automatictasklog.error("ECS Savvion UtilClass: connectECSADMIN Service:::: Exception " + var4.getMessage());
//            }
//         }
//      }
//
//      return ECSADMIN;
//   }
//
//   private String getWorkItemPerformerECS(String workItemName) {
//      String result = null;
//      String sessionId = null;
//
//      try {
//         if (workItemName != null && !workItemName.equals("")) {
//            sessionId = "false";
//
//            try {
//               sessionId = this.getBizLogicManager().connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            } catch (Exception var5) {
//               automatictasklog.error("ECS Savvion UtilClass: getWorkItemPerformerECS service connect ECSADMIN User:::: Exception " + var5.getMessage());
//            }
//
//            if (!sessionId.equals("false")) {
//               result = (String)this.getBizLogicManager().getWorkitemInfo(sessionId, workItemName).get("PERFORMER");
//               this.disconnect(sessionId);
//            }
//         } else {
//            automatictasklog.info("ECS Savvion UtilClass: getWorkItemPerformerECS Service::::processInstanceName is--- " + workItemName);
//            result = "5021";
//         }
//      } catch (Exception var6) {
//         automatictasklog.error("ECS Savvion UtilClass: getWorkItemPerformerECS:::: Exception:::" + var6.getMessage());
//         return "5059";
//      }
//
//      automatictasklog.info("ECS Savvion UtilClass: getWorkItemPerformerECSService::::::::END");
//      return result;
//   }
//
//   public ResponseObject assignOrKeepTask(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion UtilClass: assignOrKeepTask Service ::START");
//      String methodname = "assignOrKeepTask";
//      HashMap hashMap = null;
//      String workItemName = null;
//      String toBeAssignedUserId = null;
//      ResponseObject resObj = new ResponseObject();
//      String sessionId = null;
//      String responseCode = null;
//      String responseMessage = null;
//      String performer = "";
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion UtilClass: " + methodname + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            Exception e;
//            try {
//               BLServer blserver = null;
//               Session blsession = null;
//               hashMap = this.getMap(reqObj);
//               automatictasklog.info("ECS Savvion UtilClass: " + methodname + " Service:::: INPUT VALUES " + hashMap);
//               workItemName = (String)hashMap.get("WORKITEMNAME");
//               toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//               if (workItemName != null && !workItemName.equals("null")) {
//                  blserver = BLClientUtil.getBizLogicServer();
//                  blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                  WorkItem wi = blserver.getWorkItem(blsession, workItemName);
//                  ProcessInstance pi = blserver.getProcessInstance(blsession, wi.getProcessInstanceName());
//                  automatictasklog.info("ECS Savvion UtilClass: " + methodname + " Service:: Current Workitem :: " + wi + " Current ProcessInstance " + pi);
//                  if (toBeAssignedUserId != null && !toBeAssignedUserId.equals("")) {
//                     sessionId = this.connectUser(toBeAssignedUserId);
//                     if (sessionId.equals("false")) {
//                        responseCode = "5030";
//                        responseMessage = "USER_ID_INVALID";
//                        automatictasklog.error("ECS Savvion UtilClass: " + methodname + " Service::::user ID is not valid");
//                     } else if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
//                        boolean isUser = false;
//                        if (wi.getWorkStepName() != null && wi.getWorkStepName().contains("RRMakerSupervisor")) {
//                           isUser = this.checkUserGroup(toBeAssignedUserId, "RRMaker");
//                        } else if (wi.getWorkStepName() != null && wi.getWorkStepName().contains("CLAllowanceAccounts")) {
//                           isUser = this.checkUserGroup(toBeAssignedUserId, "CLAccounts");
//                        } else if (wi.getWorkStepName() != null && wi.getWorkStepName().contains("PMT")) {
//                           isUser = this.checkUserGroup(toBeAssignedUserId, "PMTMember");
//                        } else if (wi.getWorkStepName() != null && wi.getWorkStepName().contains("CLAccountsNegative")) {
//                           isUser = this.checkUserGroup(toBeAssignedUserId, "CLAccounts");
//                        } else {
//                           isUser = this.checkUserGroup(toBeAssignedUserId, wi.getWorkStepName());
//                        }
//
//                        if (isUser) {
//                           wi.assign(toBeAssignedUserId);
//                           wi.save();
//                           resObj.setWorkItemCaseStatus("Locked");
//                           responseCode = "5000";
//                           responseMessage = "SUCCESS";
//                           automatictasklog.warn("ECS Task Assigned to user");
//                        } else {
//                           responseCode = "5002";
//                           responseMessage = "USER_NOT_AUTHORIZED";
//                           automatictasklog.info("ECS Savvion UtilClass: " + methodname + " Service:::: User " + toBeAssignedUserId + " is not a member in " + wi.getWorkStepName() + " group");
//                        }
//                     } else if (wi.isAssigned()) {
//                        responseCode = "5048";
//                        responseMessage = "WI_ALREADY_LOCKED BY USER ID " + wi.getPerformer();
//                        automatictasklog.info("ECS Savvion UtilClass: " + methodname + " Service::::WorkItem " + workItemName + " is already locked to " + wi.getPerformer());
//                     } else {
//                        responseCode = "5059";
//                        responseMessage = "INVALID_WINAME";
//                        automatictasklog.error("ECS Savvion UtilClass: " + methodname + " Service::::WorkItem " + workItemName + " is not valid");
//                     }
//                  } else {
//                     performer = this.getWorkItemPerformerECS(workItemName);
//                     if (performer != null && !performer.equals("")) {
//                        automatictasklog.info("ECS Savvion UtilClass: " + methodname + " Service:: " + workItemName + " Already Acquired by " + performer);
//                     }
//
//                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
//                        automatictasklog.info("ECS task make available enterred");
//                        wi.makeAvailable();
//                        wi.save();
//                        resObj.setWorkItemCaseStatus("Kept");
//                        if (workItemName.contains("RR_FLOW") || workItemName.contains("SR_FLOW") || workItemName.contains("Accounts") || workItemName.contains("CLTPAQualityChecker")) {
//                           resObj.setWorkItemCaseStatus("New");
//                        }
//
//                        responseCode = "5000";
//                        responseMessage = "SUCCESS";
//                        automatictasklog.warn("ECS Task made available to the group");
//                     } else if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
//                        responseCode = "5000";
//                        responseMessage = "SUCCESS";
//                        automatictasklog.warn("ECS Task made available to the group already");
//                     } else {
//                        responseCode = "5059";
//                        responseMessage = "INVALID_WINAME";
//                        automatictasklog.error("ECS Savvion UtilClass: " + methodname + " Service::::WorkItem " + workItemName + " is NULL");
//                     }
//                  }
//               }
//
//               if (blsession != null && blserver.isSessionValid(blsession)) {
//                  blserver.disConnect(blsession);
//               }
//
//               e = null;
//            } catch (Exception var26) {
//               e = var26;
//               responseCode = "5050";
//               responseMessage = "FAILURE_EXCEPTION";
//               automatictasklog.error("ECS Savvion UtilClass: " + methodname + " Service:::: Catch Exception :::" + var26.getMessage());
//               if (var26.getMessage().contains("Incorrect userName/password for")) {
//                  try {
//                     String userid = (String)hashMap.get("TOBEASSIGNEDUSERID");
//                     if (e.getMessage().contains("ECSAdmin")) {
//                        userid = "ECSAdmin";
//                     }
//
//                     String pass = this.getUserPasswordECS(userid);
//                     PService p = PService.self();
//                     pass = p.encrypt(pass);
//                     automatictasklog.info("ECS Savvion :: UtilClass " + methodname + " userId " + userid + " encrypted " + pass);
//                  } catch (Exception var25) {
//                     automatictasklog.error("ECS Savvion UtilClass: " + methodname + " :: Exception " + var25);
//                  }
//               }
//            }
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var24) {
//               automatictasklog.error("ECS Savvion UtilClass: assignTask Service::::Finally Exception:::" + var24.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.info("============================ECS Savvion UtilClass: " + methodname + " Service :::: END " + responseCode + " :: Response Message :: " + responseMessage + "====================================");
//      return resObj;
//   }
//
//   public boolean checkUserECS(String userId, String groupName) {
//      boolean isMember = false;
//      String[] groupNames = new String[100];
//
//      try {
//         User usr = UserManager.getUser(userId);
//         groupNames = usr.getGroupNames();
//         List<String> list = new ArrayList();
//         Collections.addAll(list, groupNames);
//         list.removeAll(Collections.singleton((Object)null));
//         groupNames = (String[])list.toArray(new String[list.size()]);
//         automatictasklog.info("ECS Savvion: checkUserECS Service:::: user groupNames length :: " + groupNames.length);
//
//         for(int i = 0; i < groupNames.length; ++i) {
//            if (groupNames[i].equals(groupName)) {
//               isMember = true;
//            }
//         }
//
//         automatictasklog.info("ECS Savvion UtilClass: checkUserECS Service:::: userId is " + userId + " isMember of group name :: " + groupName + " :: " + isMember);
//      } catch (Exception var8) {
//         automatictasklog.error("ECS Savvion UtilClass: checkUserECS Service:::: Exception " + var8.getMessage());
//      }
//
//      return isMember;
//   }
//
//   public boolean checkUserGroup(String userId, String groupName) {
//      boolean isMember = false;
//      String methodName = "checkUserGroup";
//      String[] groupNames = new String[50];
//
//      try {
//         User usr = UserManager.getUser(userId);
//         groupNames = usr.getGroupNames();
//
//         for(int i = 0; i < groupNames.length; ++i) {
//            if (groupNames[i].equals(groupName)) {
//               isMember = true;
//            }
//         }
//
//         automatictasklog.info("ECS Savvion UtilClass: " + methodName + " Service:::: userId is " + userId + " isMember of group name :: " + groupName + " :: " + isMember);
//      } catch (Exception var8) {
//         automatictasklog.error("ECS Savvion UtilClass: " + methodName + " Service:::: Exception " + var8.getMessage());
//      }
//
//      return isMember;
//   }
//
//   public String connect(String userId, String password) throws AxisFault {
//      String sessionId = null;
//
//      try {
//         sessionId = this.getBizLogicManager().connect(userId, password);
//         return sessionId;
//      } catch (RemoteException var5) {
//         throw new AxisFault("SBM Web services error :" + var5.getMessage());
//      }
//   }
//
//   private WorkItemObject[] getNextAssignedTask(String userId, String processInstanceName, String processName) {
//      String methodName = "getNextAssignedTask";
//      WorkItemObject[] wiObjects = null;
//
//      try {
//         BLServer blserver = null;
//         Session blsession = null;
//         Session blsessionECSAdmin = null;
//         blserver = BLClientUtil.getBizLogicServer();
//         blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//         ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
//         WorkItemList wl = pi.getAssignedWorkItemList();
//         List assignedWIList = wl.getList();
//         wiObjects = new WorkItemObject[assignedWIList.size()];
//
//         for(int i = 0; i < assignedWIList.size(); ++i) {
//            WorkItem wi = (WorkItem)assignedWIList.get(i);
//            wiObjects[i] = new WorkItemObject();
//            wiObjects[i].setName(wi.getName());
//            wiObjects[i].setQueue(this.getQueueName(processName));
//            wiObjects[i].setCaseStatus("Locked");
//         }
//
//         if (blsession != null && blserver.isSessionValid(blsession)) {
//            blserver.disConnect(blsession);
//         }
//
//         if (blsessionECSAdmin != null && blserver.isSessionValid((Session)blsessionECSAdmin)) {
//            blserver.disConnect((Session)blsessionECSAdmin);
//         }
//
//         blserver = null;
//      } catch (Exception var14) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var14.getMessage());
//      }
//
//      return wiObjects;
//   }
//
//   private String getQueueName(String processName) {
//      String queue = "";
//      if (processName != null & processName.contains("RGICL_ECS_RR_FLOW")) {
//         queue = "RRRequester";
//      } else if (processName != null & processName.contains("RGICL_ECS_NETWORK_FLOW")) {
//         queue = "NetworMaker";
//      } else if (processName != null & processName.contains("RGICL_ECS_PRODUCT_FLOW")) {
//         queue = "ProductMaker";
//      } else if (processName != null & processName.contains("RGICL_ECS_AL_FLOW")) {
//         queue = "ALInwardDEO";
//      } else if (processName != null & processName.contains("RGICL_ECS_CL_FLOW")) {
//         queue = "CLInwardDEO";
//      } else if (processName != null & processName.contains("RGICL_ECS_SR_FLOW")) {
//         queue = "SRMaker";
//      }
//
//      return queue;
//   }
//
//   public void updateDataSlotValues(WorkItemObject[] completedWorkItem, String sessionId, String processInstanceName) {
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      String wiName = "";
//      String caseStatus = "RejectPullBack";
//
//      for(int i = 0; i < completedWorkItem.length; ++i) {
//         wiName = completedWorkItem[i].getName();
//         if (wiName.contains("AL_FLOW")) {
//            if (wiName.contains("NetworkDeviator")) {
//               dsTypeMap.put("HasNetworkDeviationAccepted", "STRING");
//               dsValues.put("HasNetworkDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("ServiceDeviator")) {
//               dsTypeMap.put("HasServiceDeviationAccepted", "STRING");
//               dsValues.put("HasServiceDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("PolicyDeviator")) {
//               dsTypeMap.put("HasPolicyDeviationAccepted", "STRING");
//               dsValues.put("HasPolicyDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("OtherDeviator")) {
//               dsTypeMap.put("HasOtherDeviationAccepted", "STRING");
//               dsValues.put("HasOtherDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("AmountApprover")) {
//               dsTypeMap.put("HasAmountApproverAccepted", "STRING");
//               dsValues.put("HasAmountApproverAccepted", caseStatus);
//            }
//         }
//
//         if (wiName.contains("CL_FLOW")) {
//            if (wiName.contains("EFTChecker")) {
//               dsTypeMap.put("HasEFTCheckerAccepted", "STRING");
//               dsValues.put("HasEFTCheckerAccepted", caseStatus);
//            }
//
//            if (wiName.contains("Approver")) {
//               dsTypeMap.put("HasCLApproverAccepted", "STRING");
//               dsValues.put("HasCLApproverAccepted", caseStatus);
//            }
//
//            if (wiName.contains("NetworkDeviator")) {
//               dsTypeMap.put("HasNetworkDeviationAccepted", "STRING");
//               dsValues.put("HasNetworkDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("ServiceDeviator")) {
//               dsTypeMap.put("HasServiceDeviationAccepted", "STRING");
//               dsValues.put("HasServiceDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("PolicyDeviator")) {
//               dsTypeMap.put("HasPolicyDeviationAccepted", "STRING");
//               dsValues.put("HasPolicyDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("OtherDeviator")) {
//               dsTypeMap.put("HasOtherDeviationAccepted", "STRING");
//               dsValues.put("HasOtherDeviationAccepted", caseStatus);
//            }
//
//            if (wiName.contains("AmountApprover")) {
//               dsTypeMap.put("HasCLAmountApproverAccepted", "STRING");
//               dsValues.put("HasCLAmountApproverAccepted", caseStatus);
//            }
//         }
//      }
//
//      try {
//         Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//         this.setProcessDataslotValues(sessionId, processInstanceName, resolvedDSValues);
//      } catch (Exception var9) {
//         automatictasklog.info("ECS Service :: updateDataSlotValues Exception occurs :: " + var9);
//      }
//
//   }
//
//   public Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
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
//   public void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
//      try {
//         this.getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
//         automatictasklog.info("ECS Service :: setProcessDataslotValues service ends....... ");
//      } catch (RemoteException var5) {
//         automatictasklog.error("ECS Service :: setProcessDataslotValues Exception occurs :: " + var5);
//         throw new AxisFault("SBM Web services error :" + var5.getMessage());
//      }
//   }
//
//   public ResponseObject completeAssociatedInstance(String userId, String associatedWIName, String associatedPIName) {
//      String methodName = "completeAssociatedInstance";
//      automatictasklog.info("=========================ECS Savvion: " + methodName + " Service:::: starts=========================");
//      ResponseObject response = new ResponseObject();
//      String responseCode = null;
//      String responseMessage = null;
//      boolean isInstanceCompleted = false;
//      HashMap hm = new HashMap();
//      String workItemCaseStatus = "Closed";
//
//      try {
//         BLServer blserver = null;
//         Session blsession = null;
//         Session blsessionECSAdmin = null;
//         blserver = BLClientUtil.getBizLogicServer();
//         blsession = blserver.connect(userId, this.getUserPasswordECS(userId));
//         ProcessInstance pi = blserver.getProcessInstance(blsession, associatedPIName);
//         if (associatedWIName != null && associatedWIName.contains("ALInward")) {
//            hm.put("isALInwardDEOAccepted", workItemCaseStatus);
//         }
//
//         if (associatedWIName != null && associatedWIName.contains("CLInward")) {
//            hm.put("isCLInwardDEOAccepted", workItemCaseStatus);
//         }
//
//         if (blsession == null) {
//            responseCode = "5030";
//            responseMessage = "USER_ID_INVALID";
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Invalid User Id");
//         } else {
//            pi.updateSlotValue(hm);
//            pi.save();
//            blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//            WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, associatedWIName);
//            if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//               this.assignWorkitem((new Long(blsession.getID())).toString(), associatedWIName);
//            } else {
//               responseCode = "5060";
//               responseMessage = "WINAME_NOT_FOUND";
//            }
//
//            this.completeWorkitem((new Long(blsession.getID())).toString(), associatedWIName);
//            automatictasklog.warn("ECS Savvion: " + methodName + " Service:::: Task Completed");
//
//            try {
//               blserver.getProcessInstance(blsession, associatedPIName);
//            } catch (Exception var17) {
//               responseCode = "5000";
//               responseMessage = "Constants.SUCCESS";
//               isInstanceCompleted = true;
//               response.setWorkItemCaseStatus(workItemCaseStatus);
//            }
//         }
//
//         if (blsession != null && blserver.isSessionValid(blsession)) {
//            blserver.disConnect(blsession);
//         }
//
//         if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
//            blserver.disConnect(blsessionECSAdmin);
//         }
//
//         blserver = null;
//      } catch (Exception var18) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured " + var18);
//         responseCode = "5034";
//         responseMessage = "DS_NAME_INVALID";
//      }
//
//      response.setInstanceCompleted(isInstanceCompleted);
//      response.setResponseCode(responseCode);
//      response.setResponseMessage(responseMessage);
//      automatictasklog.info("====================ECS Savvion: " + methodName + " Service::::END " + responseCode + " :: Response Message :: " + responseMessage + "=====================================");
//      return response;
//   }
//
//   public WorkItemObject[] completeMultipleTask(String processInstanceName, String workItemName, BLServer blserver, Session blsession, String workItemCaseStatus) {
//      String[] workItemNames = new String[50];
//      ArrayList workItemList = new ArrayList();
//      WorkItemObject[] completedWorkItem = null;
//      new WorkItemObject();
//      String methodName = "completeMultipleTask";
//      String caseStatus = "RejectPullBack";
//
//      try {
//         Session blsessionECSAdmin = null;
//         workItemNames = this.db.getNextAvailableTaskNames(processInstanceName);
//         if (workItemNames.length == 0) {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::there is no next WorkItem to complete");
//         } else {
//            List<String> list = new ArrayList();
//            Collections.addAll(list, workItemNames);
//            list.removeAll(Collections.singleton((Object)null));
//            list.remove(workItemName);
//            workItemNames = (String[])list.toArray(new String[list.size()]);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: workItemNames after removing the current workItem :" + Arrays.toString(workItemNames));
//
//            for(int index = 0; index < workItemNames.length; ++index) {
//               if (workItemNames[index] != null && !workItemNames[index].equals("null")) {
//                  if (!workItemName.contains("CLInvestigator") && !workItemName.contains("ALInvestigator") && (workItemNames[index].contains("Deviator") || workItemNames[index].contains("EditorHoldQueue") || workItemName.contains("CLEFTChecker") && workItemNames[index].contains("ApproverHold") || workItemNames[index].contains("VerticalHead") || workItemNames[index].contains("AmountApp") || workItemNames[index].contains("CLApprover") || workItemNames[index].contains("ALApprover") || workItemNames[index].contains("ApproverDummy") || workItemNames[index].contains("QualityChecker") || workItemNames[index].contains("EFTChecker") && !workItemName.contains("Deviator") && !workItemName.contains("AmountApp") && !workItemName.contains("VerticalHead") || workItemNames[index].contains("Investigator") && (workItemName.contains("CLApprover") || workItemName.contains("ALApprover")) && workItemCaseStatus.equals("Reject")) || workItemNames[index].contains("SRQueue") && workItemName.contains("CLInvestigator")) {
//                     WorkItemObject workitem = new WorkItemObject();
//                     blsessionECSAdmin = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemNames[index]);
//                     workitem.setPerformer(wi.getPerformer());
//                     if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                        this.assignWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                     }
//
//                     this.completeWorkitem((new Long(blsession.getID())).toString(), workItemNames[index]);
//                     automatictasklog.warn("ECS Savvion: " + methodName + " Service:::: multiple task completed");
//                     workitem.setName(workItemNames[index]);
//                     workitem.setCaseStatus(caseStatus);
//                     if (!workItemNames[index].contains("ApproverDummy") && !workItemNames[index].contains("ApproverHold")) {
//                        workItemList.add(workitem);
//                     }
//                  } else {
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::No WorkItem to complete forcefully");
//                  }
//               }
//            }
//         }
//
//         if (this.db != null) {
//            this.db.closeConnection();
//         }
//      } catch (Exception var16) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception" + var16);
//      }
//
//      completedWorkItem = this.getResultWorkitems(workItemList);
//      automatictasklog.info("ECS Savvion: " + methodName + " Service:: completedWorkItem array values are " + Arrays.toString(completedWorkItem));
//      return completedWorkItem;
//   }
//
//   public WorkItemObject[] getNextAvailableTaskList(String processInstanceName, String nextWorkItemCaseStatus, String currWorkItemName, String needsInvestigation, String supervisorId, String isFromSR, String needsPMT, String PMTHardStop, String needsSAPPR, String hasAllowance, String hasAccounts, String isExistingApprover) {
//      String methodName = "getNextAvailableTaskList";
//      automatictasklog.info("====================ECS Savvion: " + methodName + "  Service:::: START ==========================");
//      automatictasklog.info("processInstanceName ::" + processInstanceName + " workItemName:: " + currWorkItemName);
//      new ArrayList();
//      WorkItemObject[] workItemObject = null;
//
//      try {
//         ArrayList resultTaskList = this.db.getNextAvailableTaskObjects(processInstanceName, nextWorkItemCaseStatus, currWorkItemName, needsInvestigation, supervisorId, isFromSR, needsPMT, PMTHardStop, needsSAPPR, hasAllowance, hasAccounts, isExistingApprover);
//         if (resultTaskList != null && resultTaskList.size() != 0) {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Available Task List count is " + resultTaskList.size());
//            workItemObject = this.getResultWorkitems(resultTaskList);
//         } else {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::Available Task List count is " + resultTaskList.size());
//         }
//
//         if (this.db != null) {
//            this.db.closeConnection();
//         }
//      } catch (Exception var17) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service::Error Message::" + var17.getMessage());
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service :::: ENDS");
//      return workItemObject;
//   }
//
//   public ResponseObject CreateWorkFlow(RequestObject[] reqObj) {
//      String methodName = "CreateWorkFlow";
//      automatictasklog.info("=======================ECS Savvion: " + methodName + " Service:::: START==========================");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      String ptName = null;
//      String responseCode = null;
//      String responseMessage = null;
//      String priority = propertiesECSSavvion.getProperty("priority");
//      String isFromSR = "No";
//      String nextWorkItemCaseStatus = "Locked";
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      if (reqObj == null) {
//         responseCode = "5070";
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
//         String piName = ptName;
//         String needRM = null;
//         WorkItemObject[] wiObjects = null;
//
//         try {
//            String action;
//            String alCaseType;
//            try {
//               String userId = (String)inputMap.get("USERID");
//               action = (String)inputMap.get("ACTION");
//               alCaseType = (String)inputMap.get("ALCASETYPE");
//               String autoRelease = (String)inputMap.get("AUTORELEASE");
//               String clCaseType = (String)inputMap.get("CLCASETYPE");
//               String srType = (String)inputMap.get("SRTYPE");
//               String srNo = (String)inputMap.get("SRNO");
//               String srReopen = (String)inputMap.get("SRREOPEN");
//               if (!this.checkNull((String)inputMap.get("ISFROMSR"))) {
//                  isFromSR = (String)inputMap.get("ISFROMSR");
//               }
//
//               if (!this.checkNull((String)inputMap.get("NEEDRM"))) {
//                  needRM = (String)inputMap.get("NEEDRM");
//               }
//
//               String alInwardNo = (String)inputMap.get("ALINWARDNO");
//               String alNo = (String)inputMap.get("ALNO");
//               String clInwardNo = (String)inputMap.get("CLINWARDNO");
//               String clNo = (String)inputMap.get("CLNO");
//               String EFTReissue = (String)inputMap.get("EFTREISSUE");
//               String transactionId = (String)inputMap.get("TRANSACTIONID");
//               String transactionType = (String)inputMap.get("TRANSACTIONTYPE");
//               String isPaymentTPA = (String)inputMap.get("ISPAYMENTTPA");
//               String claimSource = (String)inputMap.get("CLAIMSOURCE");
//               String claimType = (String)inputMap.get("CLAIMTYPE");
//               if (userId != null && !userId.equals("")) {
//                  if (ptName != null && ptName.equals("RGICL_ECS_CL_FLOW") && (autoRelease == null || autoRelease.equals(""))) {
//                     responseCode = "5021";
//                     responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: Auto Release field is null");
//                  } else if (ptName != null && ptName.equals("RGICL_ECS_SR_FLOW") && (srType == null || srType.equals(""))) {
//                     responseCode = "5021";
//                     responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: srType field is null");
//                  } else if (ptName != null && ptName.equals("RGICL_ECS_FINANCEMASTER_FLOW") && (transactionId == null || transactionId.equals("")) && (transactionType == null || transactionType.equals(""))) {
//                     responseCode = "5021";
//                     responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: TransactionId or TransactionType field is null");
//                  } else if (ptName == null || !ptName.equals("RGICL_ECS_OPD_CL_FLOW") || claimType != null && !claimType.equals("") || claimSource != null && !claimSource.equals("")) {
//                     dsTypeMap.put("UserId", "STRING");
//                     dsValues.put("UserId", userId);
//                     dsTypeMap.put("Action", "STRING");
//                     dsValues.put("Action", action);
//                     if (ptName != null && ptName.equals("RGICL_ECS_NETWORK_FLOW")) {
//                        dsTypeMap.put("NeedRM", "STRING");
//                        dsValues.put("NeedRM", needRM);
//                     }
//
//                     if (ptName != null && ptName.equals("RGICL_ECS_AL_FLOW")) {
//                        dsTypeMap.put("ALCaseType", "STRING");
//                        dsValues.put("ALCaseType", alCaseType);
//                        dsTypeMap.put("SRReopen", "STRING");
//                        dsValues.put("SRReopen", srReopen);
//                        dsTypeMap.put("isFromSR", "STRING");
//                        dsValues.put("isFromSR", isFromSR);
//                        dsTypeMap.put("ALInwardNo", "STRING");
//                        dsValues.put("ALInwardNo", alInwardNo);
//                        dsTypeMap.put("ALNo", "STRING");
//                        dsValues.put("ALNo", alNo);
//                     }
//
//                     if (ptName != null && (ptName.equals("RGICL_ECS_CL_FLOW") || ptName.equals("RGICL_ECS_OPD_CL_FLOW"))) {
//                        dsTypeMap.put("AutoRelease", "STRING");
//                        dsValues.put("AutoRelease", autoRelease);
//                        dsTypeMap.put("CLCaseType", "STRING");
//                        dsValues.put("CLCaseType", clCaseType);
//                        dsTypeMap.put("SRReopen", "STRING");
//                        dsValues.put("SRReopen", srReopen);
//                        dsTypeMap.put("isFromSR", "STRING");
//                        dsValues.put("isFromSR", isFromSR);
//                        dsTypeMap.put("SRType", "STRING");
//                        dsValues.put("SRType", srType);
//                        dsTypeMap.put("CLInwardNo", "STRING");
//                        dsValues.put("CLInwardNo", clInwardNo);
//                        dsTypeMap.put("CLNo", "STRING");
//                        dsValues.put("CLNo", clNo);
//                        dsTypeMap.put("EFTReissue", "STRING");
//                        dsValues.put("EFTReissue", EFTReissue);
//                     }
//
//                     if (ptName != null && ptName.equals("RGICL_ECS_SR_FLOW")) {
//                        dsTypeMap.put("SRType", "STRING");
//                        dsValues.put("SRType", srType);
//                        dsTypeMap.put("SRNo", "STRING");
//                        dsValues.put("SRNo", srNo);
//                        isFromSR = "Yes";
//                     }
//
//                     if (ptName != null && ptName.equals("RGICL_ECS_FINANCEMASTER_FLOW")) {
//                        dsTypeMap.put("TransactionId", "STRING");
//                        dsValues.put("TransactionId", transactionId);
//                        dsTypeMap.put("TransactionType", "STRING");
//                        dsValues.put("TransactionType", transactionType);
//                     }
//
//                     if (ptName != null && ptName.equals("RGICL_ECS_TPA_CL_FLOW")) {
//                        dsTypeMap.put("IsPaymentTPA", "STRING");
//                        dsValues.put("IsPaymentTPA", isPaymentTPA);
//                     }
//
//                     if (ptName != null && ptName.equals("RGICL_ECS_OPD_CL_FLOW")) {
//                        dsTypeMap.put("ClaimSource", "STRING");
//                        dsValues.put("ClaimSource", claimSource);
//                        dsTypeMap.put("ClaimType", "STRING");
//                        dsValues.put("ClaimType", claimType);
//                     }
//                  } else {
//                     responseCode = "5021";
//                     responseMessage = "INPUT_VALUE_IS_NULL";
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: CLAIMTYPE or CLAIMSOURCE field is null");
//                  }
//               } else {
//                  responseCode = "5001";
//                  responseMessage = "USER_ID_NULL";
//                  automatictasklog.info("ECS Savvion: " + methodName + " :: Input Value is null");
//               }
//
//               if (responseCode == null) {
//                  ECSADMIN = "false";
//
//                  try {
//                     ECSADMIN = this.getBizLogicManager().connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//                  } catch (Exception var51) {
//                     ECSADMIN = "false";
//                     automatictasklog.error("ECS Savvion UtilClass: connectECSADMIN Service:::: Exception " + var51.getMessage());
//                  }
//
//                  sessionId = ECSADMIN;
//                  automatictasklog.info("ECS Savvion UtilClass: connectECSADMIN Service:::: " + ECSADMIN);
//                  User user = UserManager.getDefaultRealm().getUser(userId);
//                  if (!sessionId.equals("false") && user != null) {
//                     Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                     processInstanceName = this.createProcessInstance(sessionId, ptName, piName, priority, resolvedDSValues);
//                     automatictasklog.info("ECS Savvion: " + methodName + " :: Created ProcessInstanceName is ::  " + processInstanceName);
//                     responseCode = "5000";
//                     responseMessage = "SUCCESS";
//                     if (isFromSR != null && isFromSR.equals("Yes") || ptName.contains("CSG") || ptName.equals("RGICL_ECS_TPA_AL_FLOW") || ptName.equals("RGICL_ECS_TPA_CL_FLOW") || needRM != null) {
//                        nextWorkItemCaseStatus = "New";
//                     }
//
//                     if (isFromSR != null && isFromSR.equals("Yes") && ptName.equals("RGICL_ECS_TPA_CL_FLOW")) {
//                        nextWorkItemCaseStatus = "Reopen";
//                     }
//
//                     automatictasklog.info("NextWorkItemCaseStatus is " + nextWorkItemCaseStatus);
//                     wiObjects = this.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
//                     if (wiObjects != null && wiObjects.length != 0) {
//                        this.checkUserNumbersInGroup(wiObjects);
//                     }
//
//                     if (isFromSR != null && !isFromSR.equals("Yes") && !ptName.contains("CSG") && !ptName.equals("RGICL_ECS_TPA_AL_FLOW") && !ptName.equals("RGICL_ECS_TPA_CL_FLOW") || needRM == null && ptName.equals("RGICL_ECS_NETWORK_FLOW")) {
//                        RequestObject[] reqObj1 = new RequestObject[2];
//                        String[] workitemNamesArr = new String[]{wiObjects[0].getName()};
//                        reqObj1[0] = new RequestObject();
//                        reqObj1[0].setKey("WORKITEMNAMES");
//                        reqObj1[0].setValueArray(workitemNamesArr);
//                        reqObj1[1] = new RequestObject();
//                        reqObj1[1].setKey("TOBEASSIGNEDUSERID");
//                        reqObj1[1].setValue(userId);
//                        this.assignTaskToUser(reqObj1);
//                        wiObjects[0].setPerformer(userId);
//                     }
//                  } else {
//                     automatictasklog.info("ECS Savvion:  " + methodName + " USER_ID_INVALID ");
//                     responseCode = "5030";
//                     responseMessage = "USER_ID_INVALID";
//                  }
//               }
//            } catch (Exception var52) {
//               Exception e = var52;
//               automatictasklog.error("ECS Savvion: " + methodName + " :: Exception" + var52.getMessage());
//               if (var52.getMessage().contains("Incorrect userName/password for")) {
//                  try {
//                     action = (String)inputMap.get("USERID");
//                     if (e.getMessage().contains("ECSAdmin")) {
//                        action = "ECSAdmin";
//                     }
//
//                     alCaseType = this.getUserPasswordECS(action);
//                     PService p = PService.self();
//                     alCaseType = p.encrypt(alCaseType);
//                     automatictasklog.info("ECS Savvion UtilClass: " + methodName + " userId " + action + " encrypted " + alCaseType);
//                  } catch (Exception var50) {
//                     automatictasklog.error("ECS Savvion UtilClass: " + methodName + " :: Exception " + var50);
//                  }
//               }
//            }
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var49) {
//               automatictasklog.error("ECS Savvion: " + methodName + " :: Finally Exception:::" + var49.getMessage());
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
//      automatictasklog.info("====================ECS Savvion: " + methodName + " :: END " + responseCode + " :: Response Message:: " + responseMessage + "==========================");
//      return resObj;
//   }
//
//   public ResponseObject assignTaskToUser(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: assignTaskToUser Service ::START");
//      String methodname = "assignTaskToUser";
//      HashMap hashMap = new HashMap();
//      String[] workItemName = null;
//      String toBeAssignedUserId = null;
//      ResponseObject resObj = new ResponseObject();
//      String sessionId = null;
//      String responseCode = null;
//      String responseMessage = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodname + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            Exception e;
//            String userid;
//            String blsessionECSAdmin;
//            try {
//               e = null;
//               userid = null;
//               blsessionECSAdmin = null;
//               BLServer blserver = BLClientUtil.getBizLogicServer();
//               Session blsession = blserver.connect("ECSAdmin", this.getUserPasswordECS("ECSAdmin"));
//               hashMap = this.getMap(reqObj);
//               resObj.setWorkItemCaseStatus("New");
//               automatictasklog.info("ECS Savvion: " + methodname + " Service:::: INPUT VALUES " + hashMap);
//               workItemName = (String[])hashMap.get("WORKITEMNAMES");
//               toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//               if (toBeAssignedUserId != null && workItemName != null && !toBeAssignedUserId.equals("")) {
//                  sessionId = this.connectUser(toBeAssignedUserId);
//                  boolean isUser = false;
//                  if (sessionId.equals("false")) {
//                     responseCode = "5030";
//                     responseMessage = "USER_ID_INVALID";
//                  } else {
//                     int index = 0;
//
//                     while(true) {
//                        if (index >= workItemName.length) {
//                           responseCode = "5000";
//                           responseMessage = "SUCCESS";
//                           break;
//                        }
//
//                        if (workItemName[index] == null && workItemName[index].equals("null")) {
//                           automatictasklog.warn("ECS Savvion: " + methodname + " Service::::WorkItem[" + index + "] is NULL");
//                        } else {
//                           WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
//                           isUser = this.checkUserGroup(toBeAssignedUserId, wi.getWorkStepName());
//                           if (WorkItem.isExist(blsession, wi.getID()) && wi.isAvailable()) {
//                              if (isUser) {
//                                 wi.assign(toBeAssignedUserId);
//                                 wi.save();
//                                 resObj.setWorkItemCaseStatus("Locked");
//                                 automatictasklog.warn("ECS Task Assigned to user");
//                              } else {
//                                 automatictasklog.warn("User is not a member in group :: " + wi.getWorkStepName());
//                              }
//                           } else {
//                              automatictasklog.warn("Task is already in Locked Queue");
//                           }
//                        }
//
//                        ++index;
//                     }
//                  }
//               } else {
//                  responseCode = "5021";
//                  responseMessage = "INPUT_VALUE_IS_NULL";
//                  automatictasklog.info("ECS Savvion: " + methodname + " Service:: INPUT_VALUE_IS_NULL");
//               }
//
//               if (blsession != null && blserver.isSessionValid(blsession)) {
//                  blserver.disConnect(blsession);
//               }
//
//               if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
//                  blserver.disConnect(blsessionECSAdmin);
//               }
//
//               e = null;
//            } catch (Exception var26) {
//               e = var26;
//               responseCode = "5050";
//               responseMessage = "FAILURE_EXCEPTION";
//               if (var26.getMessage().contains("Incorrect userName/password for")) {
//                  try {
//                     userid = (String)hashMap.get("TOBEASSIGNEDUSERID");
//                     if (e.getMessage().contains("ECSAdmin")) {
//                        userid = "ECSAdmin";
//                     }
//
//                     blsessionECSAdmin = this.getUserPasswordECS(userid);
//                     PService p = PService.self();
//                     blsessionECSAdmin = p.encrypt(blsessionECSAdmin);
//                     automatictasklog.info("ECS Savvion Utilclass: assignTaskToUser userId " + userid + " encrypted " + blsessionECSAdmin);
//                  } catch (Exception var25) {
//                     automatictasklog.error("ECS Savvion Utilclass: assignTaskToUser :: Exception " + var25);
//                  }
//               }
//
//               automatictasklog.error("ECS Savvion: " + methodname + " Service:::: Catch Exception :::" + var26.getMessage());
//            }
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var24) {
//               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var24.getMessage());
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
//   public ResponseObject completeProcessInstance(RequestObject[] reqObj) {
//      String methodName = "completeProcessInstance";
//      automatictasklog.info("=============================ECS Savvion: " + methodName + " Service starts======================================");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      HashMap hm = new HashMap();
//      UtilClass uc = new UtilClass(SBM_HOME);
//      boolean isInstanceCompleted = false;
//      WorkItemObject[] workItemObject = null;
//      WorkItemObject[] completedWorkItem = null;
//      ArrayList workItemList = new ArrayList();
//      ArrayList resultTaskList = null;
//      ProcessInstance pi = null;
//      String ClosureStep = "";
//      String processInstanceName = "";
//      Boolean existingTagQ = false;
//      String workItemName = null;
//      WorkItemObject[] wiObjects = null;
//      WorkItemObject[] availableItemObject = null;
//      ArrayList availableItemList = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String piName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String SRType = (String)hashMap.get("SRTYPE");
//         String[] piNamesList = (String[])hashMap.get("PINAMESLIST");
//         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUE  piNamesList[] is :: " + Arrays.toString(piNamesList));
//
//         try {
//            BLServer blserver = null;
//            Session blsession = null;
//            Session blsessionECSAdmin = null;
//            blserver = BLClientUtil.getBizLogicServer();
//            blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
//            blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
//            if (piName != null && !piName.equals("") && piName.contains("SR_FLOW")) {
//               ProcessInstance srPI = blserver.getProcessInstance(blsession, piName);
//               SRType = (String)srPI.getDataSlotValue("SRType");
//            }
//
//            automatictasklog.info("ECS Savvion: " + methodName + " SRType is " + SRType);
//            if (piNamesList != null && piNamesList.length != 0) {
//               resultTaskList = this.db.getNextTaskByPIName(piNamesList);
//               automatictasklog.info("ECS Savvion: " + methodName + " resultTaskList size is " + resultTaskList.size());
//            }
//
//            if (resultTaskList != null && resultTaskList.size() != 0) {
//               workItemObject = this.getResultWorkitems(resultTaskList);
//               List<String> list = new ArrayList();
//               int i = 0;
//
//               while(true) {
//                  if (i >= workItemObject.length) {
//                     completedWorkItem = new WorkItemObject[workItemObject.length];
//                     completedWorkItem = this.getResultWorkitems(workItemList);
//                     automatictasklog.info("ECS Savvion: " + methodName + " completedWorkItem size is " + completedWorkItem.length);
//                     automatictasklog.info("resultTaskList :: " + Arrays.toString(workItemObject));
//                     boolean tag = false;
//                     boolean deviation = false;
//                     boolean pmt = false;
//                     boolean executive = false;
//                     boolean approver = false;
//                     boolean Query = false;
//                     WorkItemObject[] var37 = workItemObject;
//                     int var36 = workItemObject.length;
//
//                     WorkItemObject workitemObj;
//                     int var35;
//                     for(var35 = 0; var35 < var36; ++var35) {
//                        workitemObj = var37[var35];
//                        if (workitemObj.getName().contains("Tag")) {
//                           tag = true;
//                        }
//
//                        if (workitemObj.getName().contains("Deviator") || workitemObj.getName().contains("Amount") || workitemObj.getName().contains("Head")) {
//                           deviation = true;
//                        }
//
//                        if (workitemObj.getName().contains("PMT")) {
//                           pmt = true;
//                        }
//
//                        if (workitemObj.getName().contains("Executive")) {
//                           executive = true;
//                        }
//
//                        if (workitemObj.getName().contains("ALApprover")) {
//                           approver = true;
//                        }
//
//                        if (workitemObj.getName().contains("Query")) {
//                           Query = true;
//                        }
//                     }
//
//                     automatictasklog.info("Query :" + Query + ", tag : " + tag + ", deviation :" + deviation + ", pmt :" + pmt + ", executive :" + executive + ", approver :" + approver);
//                     WorkItem wi;
//                     String workItemCaseStatus;
//                     if (processInstanceName.contains("AL_FLOW") && SRType != null && (SRType.equals("ALClosure") || SRType.equals("ALTermination"))) {
//                        var37 = workItemObject;
//                        var36 = workItemObject.length;
//
//                        for(var35 = 0; var35 < var36; ++var35) {
//                           workitemObj = var37[var35];
//                           workItemName = workitemObj.getName();
//
//                           try {
//                              if (workItemName.contains("Investigator")) {
//                                 automatictasklog.info("Complete Investigation Task");
//                                 wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                 if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                    uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                 }
//
//                                 uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                 automatictasklog.info("Investigation completed");
//                              }
//                           } catch (Exception var46) {
//                              automatictasklog.error("Catch Exception in Complete Investigation Task:::" + var46);
//                           }
//                        }
//
//                        if (tag & pmt & !Query & !approver & !executive & !deviation) {
//                           automatictasklog.info("************tag & pmt & !Query & !approver & !executive & !deviation**************");
//                           this.completingTagTask(uc, workItemObject, blserver, blsession, blsessionECSAdmin, pi);
//                        }
//
//                        if (Query & pmt & !deviation & !approver & !executive & !tag) {
//                           automatictasklog.info("*********Query & pmt & !deviation & !approver & !executive & !tag************");
//
//                           try {
//                              this.completingTagTask(uc, workItemObject, blserver, blsession, blsessionECSAdmin, pi);
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Query")) {
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    hm.put("isALQuerryAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS COmplete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                    automatictasklog.info("Query Completed");
//                                 }
//                              }
//                           } catch (Exception var59) {
//                              automatictasklog.error("Catch Exception in Query & pmt & !deviation & !approver & !executive & !tag :::" + var59);
//                           }
//                        }
//
//                        if (deviation & pmt & !Query & !approver & !executive & !tag) {
//                           automatictasklog.info("*********deviation & pmt & !Query & !approver & !executive & !tag**********");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("PMT")) {
//                                    automatictasklog.info("Suspend PMT in Query & PMT");
//                                    workItemCaseStatus = "ALClosure";
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("PMTQueue")) {
//                                       hm.put("ClosureStep", workItemCaseStatus);
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("PMT Task Completed");
//                                 }
//                              }
//
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Deviator") || workItemName.contains("Amount") || workItemName.contains("Head")) {
//                                    automatictasklog.info("Complete Deviation/AmountApprover/VerticalHead in Deviation & PMT" + workItemName);
//                                    workItemName = workitemObj.getName();
//                                    workItemCaseStatus = "Approve";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Reject";
//                                    }
//
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("AmountApprover")) {
//                                       hm.put("HasAmountApproverAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("VerticalHead")) {
//                                       hm.put("HasVHAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("NetworkDeviator")) {
//                                       hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("ServiceDeviator")) {
//                                       hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("PolicyDeviator")) {
//                                       hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("OtherDeviator")) {
//                                       hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS COmplete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Query Completed");
//                                 }
//                              }
//                           } catch (Exception var58) {
//                              automatictasklog.error("Catch Exception in deviation & pmt & !Query & !approver & !executive & !tag :::" + var58);
//                           }
//                        }
//
//                        if (executive & pmt & !deviation & !Query & !approver & !tag) {
//                           automatictasklog.info("*********executive & pmt & !deviation & !Query &!approver & !tag**********");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("PMT")) {
//                                    automatictasklog.info("Suspend PMT in Query & PMT");
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("PMTQueue")) {
//                                       hm.put("ClosureStep", "ALClosure");
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("PMT Task Completed");
//                                 }
//                              }
//
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Executive")) {
//                                    automatictasklog.info("Complete Executive in Executive & PMT");
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    hm.put("isALExecutiveAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Executive Completed");
//                                 }
//                              }
//                           } catch (Exception var57) {
//                              automatictasklog.error("Catch Exception in executive & pmt & !deviation & !Query &!approver & !tag :::" + var57);
//                           }
//                        }
//
//                        if (approver & pmt & !deviation & !Query & !executive & !tag) {
//                           automatictasklog.info("***********approver & pmt & !deviation & !Query & !executive & !tag*************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("PMT")) {
//                                    automatictasklog.info("Suspend PMT in Query & PMT");
//                                    workItemCaseStatus = "ALClosure";
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("PMTQueue")) {
//                                       hm.put("ClosureStep", workItemCaseStatus);
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("PMT Task Completed");
//                                 }
//                              }
//
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("ALApprover")) {
//                                    automatictasklog.info("Complete ALApprover in ALApprover & PMT");
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    hm.put("isALApproverAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Approver Completed");
//                                 }
//                              }
//                           } catch (Exception var56) {
//                              automatictasklog.error("Catch Exception in approver & pmt & !deviation & !Query & !executive & !tag :::" + var56);
//                           }
//                        }
//
//                        if (pmt & !Query & !deviation & !executive & !approver & !tag) {
//                           automatictasklog.info("***********pmt & !Query & !deviation & !executive & !approver & !tag************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("PMT")) {
//                                    automatictasklog.info("Complete PMT incase no other task");
//                                    workItemCaseStatus = "ALClosure";
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("PMTQueue")) {
//                                       hm.put("ClosureStep", workItemCaseStatus);
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("PMT task Completed");
//                                 }
//                              }
//                           } catch (Exception var55) {
//                              automatictasklog.error("Catch Exception in pmt & !Query & !deviation & !executive & !approver & !tag :::" + var55);
//                           }
//                        }
//
//                        if (deviation & !pmt & !Query & !executive & !approver & !tag) {
//                           automatictasklog.info("***************deviation & !pmt & !Query & !executive & !approver & !tag********************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Deviator") || workItemName.contains("Amount") || workItemName.contains("Head")) {
//                                    automatictasklog.info("Complete Deviation/AmountApprover/VerticalHead incase no other task " + workItemName);
//                                    workItemCaseStatus = "Approve";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Reject";
//                                    }
//
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    if (workItemName != null && workItemName.contains("AmountApprover")) {
//                                       hm.put("HasAmountApproverAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("VerticalHead")) {
//                                       hm.put("HasVHAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("NetworkDeviator")) {
//                                       hm.put("HasNetworkDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("ServiceDeviator")) {
//                                       hm.put("HasServiceDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("PolicyDeviator")) {
//                                       hm.put("HasPolicyDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    if (workItemName != null && workItemName.contains("OtherDeviator")) {
//                                       hm.put("HasOtherDeviationAccepted", workItemCaseStatus);
//                                    }
//
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Complete Deviation/AmountApprover/VerticalHead incase no other task to complete");
//                                 }
//                              }
//                           } catch (Exception var54) {
//                              automatictasklog.error("Catch Exception in deviation & !pmt & !Query & !executive & !approver & !tag :::" + var54);
//                           }
//                        }
//
//                        if (Query & !pmt & !deviation & !executive & !approver & !tag) {
//                           automatictasklog.info("************Query & !pmt & !deviation & !executive & !approver & !tag**************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Query")) {
//                                    automatictasklog.info("Complete Query incase no other task");
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    hm.put("isALQuerryAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Query task completed");
//                                 }
//                              }
//                           } catch (Exception var53) {
//                              automatictasklog.error("Catch Exception in Query & !pmt & !deviation & !executive & !approver & !tag :::" + var53);
//                           }
//                        }
//
//                        if (approver & !executive & !pmt & !deviation & !Query & !tag) {
//                           automatictasklog.info("****************approver & !executive & !pmt & !deviation & !Query & !tag***************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("ALApprover")) {
//                                    automatictasklog.info("Complete Approver incase no other task");
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    String Datatype = (String)pi.getDataSlotValue("ALCaseType");
//                                    automatictasklog.info("ALDataType is" + Datatype);
//                                    if (Datatype.equals("DF") && SRType.equals("ALClosure")) {
//                                       workItemCaseStatus = "Approve";
//                                       automatictasklog.info("workItemCaseStatus is" + workItemCaseStatus);
//                                    }
//
//                                    hm = new HashMap();
//                                    hm.put("isALApproverAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Approver task completed");
//                                 }
//                              }
//                           } catch (Exception var52) {
//                              automatictasklog.error("Catch Exception in approver & !executive & !pmt & !deviation & !Query & !tag :::" + var52);
//                           }
//                        }
//
//                        if (executive & !pmt & !deviation & !Query & !approver & !tag) {
//                           automatictasklog.info("************executive & !pmt & !deviation & !Query & !approver & !tag********************");
//
//                           try {
//                              var37 = workItemObject;
//                              var36 = workItemObject.length;
//
//                              for(var35 = 0; var35 < var36; ++var35) {
//                                 workitemObj = var37[var35];
//                                 workItemName = workitemObj.getName();
//                                 automatictasklog.info("workitem processing " + workItemName);
//                                 if (workItemName.contains("Executive")) {
//                                    automatictasklog.info("Complete Executive incase no other task");
//                                    workItemCaseStatus = "ALClosure";
//                                    if (SRType.equals("ALTermination")) {
//                                       workItemCaseStatus = "Terminate";
//                                    }
//
//                                    wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//                                    pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//                                    hm = new HashMap();
//                                    hm.put("isALExecutiveAccepted", workItemCaseStatus);
//                                    pi.updateSlotValue(hm);
//                                    pi.save();
//                                    if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), workitemObj.getName());
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    automatictasklog.warn("ECS Complete Task enterred");
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                                    automatictasklog.info("Executive Task Completed");
//                                 }
//                              }
//                           } catch (Exception var51) {
//                              automatictasklog.error("Catch Exception in executive & !pmt & !deviation & !Query & !approver & !tag :::" + var51);
//                           }
//                        }
//                     }
//
//                     String[] piNames;
//                     int var72;
//                     int var82;
//                     if (processInstanceName.contains("AL_FLOW") && SRType != null && SRType.equals("ALClosure")) {
//                        automatictasklog.info("get available task and check if no other task except dummy, close dummy");
//                        automatictasklog.info("ECS savvion Service :: checking for DummyQueue task ");
//                        piNames = new String[5];
//
//                        try {
//                           Thread.sleep(3000L);
//                           piNames = this.db.getNextAvailableTaskNames(processInstanceName);
//                           List<String> workStepNames = new ArrayList();
//                           Collections.addAll(workStepNames, piNames);
//                           workStepNames.removeAll(Collections.singleton((Object)null));
//                           piNames = (String[])workStepNames.toArray(new String[workStepNames.size()]);
//                           automatictasklog.warn("ECS savvion " + methodName + " Service :: Available workItem array " + Arrays.toString(piNames) + "  :: workItemNames lenght is: " + piNames.length);
//                           if (piNames.length == 1) {
//                              String[] var77 = piNames;
//                              var82 = piNames.length;
//
//                              for(var72 = 0; var72 < var82; ++var72) {
//                                 String winame = var77[var72];
//                                 if (winame != null && winame != "" && winame.contains("Dummy")) {
//                                    automatictasklog.info("ECS Complete Dummy Queue Task enterred");
//                                    WorkItem win = blserver.getWorkItem(blsessionECSAdmin, winame);
//                                    if (WorkItem.isExist(blsessionECSAdmin, win.getID()) && win.isAvailable()) {
//                                       automatictasklog.warn("ECS AssignTask enterred");
//                                       uc.assignWorkitem((new Long(blsession.getID())).toString(), winame);
//                                       automatictasklog.warn("ECS Task Assigned to user");
//                                    }
//
//                                    uc.completeWorkitem((new Long(blsession.getID())).toString(), winame);
//                                    automatictasklog.info("ECS Dummy Queue Task Completed");
//                                 }
//                              }
//                           }
//                        } catch (Exception var47) {
//                           automatictasklog.info("ECS Savvion: " + methodName + " activatedworksteps array lenght is more than one");
//                        }
//
//                        automatictasklog.info("fetching next available tasks");
//                        if (!existingTagQ) {
//                           wiObjects = uc.getNextAvailableTaskList(processInstanceName, "New", processInstanceName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
//                           automatictasklog.info("ECS Savvion: " + methodName + " available wiObjects list to set result work item array " + Arrays.toString(wiObjects));
//                        }
//
//                        resObj.setResponseMessage("SUCCESS");
//                        resObj.setResponseCode("5000");
//
//                        try {
//                           automatictasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
//                           pi = blserver.getProcessInstance(blsession, processInstanceName);
//                           automatictasklog.warn("ECS " + processInstanceName + "  object is : " + pi);
//                        } catch (Exception var45) {
//                           isInstanceCompleted = true;
//                        }
//
//                        automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
//                        resObj.setInstanceCompleted(isInstanceCompleted);
//                        resObj.setWorkItemCaseStatus("Closed");
//                        if (wiObjects != null && wiObjects.length != 0) {
//                           uc.checkUserNumbersInGroup(wiObjects);
//                        }
//
//                        if (wiObjects != null) {
//                           resObj.setResultworkItemArray(wiObjects);
//                        }
//                     } else {
//                        String pName;
//                        if (processInstanceName.contains("AL_FLOW") && SRType != null && SRType.equals("ALTermination")) {
//                           automatictasklog.info("ECS Savvion :: " + methodName + " inside ALTermination block");
//                           String piname = null;
//                           pName = null;
//
//                           try {
//                              List<String> pilist = new ArrayList();
//                              WorkItemObject[] var67 = workItemObject;
//                              int var75 = workItemObject.length;
//
//                              WorkItemObject availableitemObj;
//                              for(var82 = 0; var82 < var75; ++var82) {
//                                 availableitemObj = var67[var82];
//                                 String winame = availableitemObj.getName();
//                                 if (!pilist.contains(availableitemObj.getPiName())) {
//                                    pilist.add(availableitemObj.getPiName());
//                                 }
//
//                                 if (winame.contains("Tag")) {
//                                    piname = availableitemObj.getPiName();
//                                 }
//                              }
//
//                              String[] piNametoComplete = (String[])pilist.toArray(new String[pilist.size()]);
//                              availableItemList = this.db.getNextTaskByPIName(piNamesList);
//                              automatictasklog.info("resultTaskList :: Available Task ");
//                              if (availableItemList != null && availableItemList.size() != 0) {
//                                 availableItemObject = this.getResultWorkitems(availableItemList);
//                                 var67 = availableItemObject;
//                                 var75 = availableItemObject.length;
//
//                                 for(var82 = 0; var82 < var75; ++var82) {
//                                    availableitemObj = var67[var82];
//                                    automatictasklog.info("Current PIName :: " + availableitemObj.getPiName() + " & Current WorkItem Name" + availableitemObj.getName());
//                                    if (!availableitemObj.getPiName().equals(piname) && !availableitemObj.getName().contains("Dummy")) {
//                                       automatictasklog.info("Terminating Remaining Available task");
//                                       wi = blserver.getWorkItem(blsessionECSAdmin, availableitemObj.getName());
//                                       pi = blserver.getProcessInstance(blsession, availableitemObj.getName().split("::")[0]);
//                                       hm = new HashMap();
//                                       if (availableitemObj.getName().contains("Executive")) {
//                                          hm.put("isALExecutiveAccepted", "Terminate");
//                                       }
//
//                                       if (availableitemObj.getName().contains("ALApprover")) {
//                                          hm.put("isALApproverAccepted", "Terminate");
//                                       }
//
//                                       pi.updateSlotValue(hm);
//                                       pi.save();
//                                       if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), availableitemObj.getName());
//                                       }
//
//                                       automatictasklog.warn("ECS Complete Task enterred ::" + availableitemObj.getName());
//                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), availableitemObj.getName());
//                                       automatictasklog.info("Task Completed :: " + availableitemObj.getName());
//                                    } else if (!availableitemObj.getPiName().equals(piname) && availableitemObj.getName().contains("Dummy")) {
//                                       automatictasklog.info("Terminating Dummy task");
//                                       wi = blserver.getWorkItem(blsessionECSAdmin, availableitemObj.getName());
//                                       if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                          uc.assignWorkitem((new Long(blsession.getID())).toString(), availableitemObj.getName());
//                                       }
//
//                                       automatictasklog.warn("ECS Complete Task enterred :: " + availableitemObj.getName());
//                                       uc.completeWorkitem((new Long(blsession.getID())).toString(), availableitemObj.getName());
//                                       automatictasklog.info("Task Completed :: " + availableitemObj.getName());
//                                    }
//                                 }
//                              }
//
//                              String[] var68 = piNametoComplete;
//                              var75 = piNametoComplete.length;
//
//                              String pitask;
//                              for(var82 = 0; var82 < var75; ++var82) {
//                                 pitask = var68[var82];
//                                 if (!pitask.equals(piname)) {
//                                    try {
//                                       pi = blserver.getProcessInstance(blsession, pitask);
//                                       pi.complete();
//                                    } catch (Exception var44) {
//                                       automatictasklog.info("Process Instance " + pitask + " Already closed");
//                                    }
//                                 }
//                              }
//
//                              automatictasklog.warn("ECE Savvion: " + methodName + " Service::: getting ProcessInstance details");
//
//                              try {
//                                 var68 = piNametoComplete;
//                                 var75 = piNametoComplete.length;
//                                 var82 = 0;
//
//                                 while(var82 < var75) {
//                                    pitask = var68[var82];
//
//                                    try {
//                                       blserver.getProcessInstance(blsession, pitask);
//                                       isInstanceCompleted = false;
//                                       break;
//                                    } catch (Exception var48) {
//                                       isInstanceCompleted = true;
//                                       ++var82;
//                                    }
//                                 }
//                              } catch (Exception var49) {
//                                 automatictasklog.warn("ECE Savvion: " + methodName + " Service::: Exception Occured while checking for isInstanceCompleted ");
//                              }
//
//                              automatictasklog.warn("ECS " + processInstanceName + " is completed?? : " + isInstanceCompleted);
//                              resObj.setInstanceCompleted(isInstanceCompleted);
//                              resObj.setWorkItemCaseStatus("Terminate");
//                              resObj.setResponseCode("5000");
//                              resObj.setResponseMessage("SUCCESS");
//                           } catch (Exception var50) {
//                              resObj.setResponseCode("5050");
//                              resObj.setResponseMessage("FAILURE_EXCEPTION");
//                              automatictasklog.error("ECS Savvion :: " + methodName + " Exception occured ::: " + var50);
//                           }
//                        } else {
//                           var37 = workItemObject;
//                           var36 = workItemObject.length;
//
//                           for(var35 = 0; var35 < var36; ++var35) {
//                              workitemObj = var37[var35];
//                              workItemCaseStatus = workitemObj.getName();
//                              wi = blserver.getWorkItem(blsession, workItemCaseStatus);
//                              blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
//                              if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                                 uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemCaseStatus);
//                              }
//
//                              wi.suspend();
//                           }
//
//                           piNames = (String[])list.toArray(new String[list.size()]);
//                           String[] var81 = piNames;
//                           var72 = piNames.length;
//
//                           for(var36 = 0; var36 < var72; ++var36) {
//                              pName = var81[var36];
//                              pi = blserver.getProcessInstance(blsession, pName);
//                              if (pi.isActivated()) {
//                                 if (userId.equalsIgnoreCase("ECSAdmin")) {
//                                    ClosureStep = "AutoBatchJob_Closure";
//                                 }
//
//                                 hm.put("ClosureStep", SRType);
//                                 pi.updateSlotValue(hm);
//                                 pi.save();
//                                 pi.complete();
//                                 automatictasklog.info("ECS Savvion: " + methodName + " processInstance :: " + pName + " is completed :: " + pi.isCompleted() + " with ClosureStep as " + SRType);
//                              }
//                           }
//
//                           resObj.setResponseCode("5000");
//                           resObj.setResponseMessage("SUCCESS");
//
//                           try {
//                              blserver.getProcessInstance(blsession, processInstanceName);
//                           } catch (Exception var43) {
//                              isInstanceCompleted = true;
//                              automatictasklog.warn("ECS processInstance " + processInstanceName + " got completed is " + isInstanceCompleted);
//                           }
//                        }
//                     }
//
//                     try {
//                        blserver.getProcessInstance(blsession, processInstanceName);
//                     } catch (Exception var42) {
//                        isInstanceCompleted = true;
//                        automatictasklog.warn("ECS processInstance " + processInstanceName + " got completed is " + isInstanceCompleted);
//                     }
//                     break;
//                  }
//
//                  WorkItemObject workitem = new WorkItemObject();
//                  workitem.setName(workItemObject[i].getName());
//                  processInstanceName = workItemObject[i].getPiName();
//                  workitem.setPiName(processInstanceName);
//                  pi = blserver.getProcessInstance(blsession, processInstanceName);
//                  if (SRType == null || SRType.equals("") || !SRType.equals("ALTermination") && !SRType.equals("CLTermination")) {
//                     if (SRType == null || SRType.equals("") || !SRType.equals("ChequeCancellationAndStopPayment") && SRType != "FullRecovery") {
//                        workitem.setCaseStatus("Closed");
//                     } else {
//                        workitem.setCaseStatus("Reject");
//                     }
//                  } else {
//                     workitem.setCaseStatus("Terminate");
//                  }
//
//                  if (processInstanceName.contains("AL_FLOW") && SRType != null && (SRType.equals("ALClosure") || SRType.equals("ALTermination")) && (workitem.getName().contains("ALTag") || workitem.getName().contains("Dummy"))) {
//                     workitem = null;
//                     existingTagQ = true;
//                  }
//
//                  if (workitem != null) {
//                     workItemList.add(workitem);
//                  }
//
//                  if ((!list.contains(processInstanceName) || !existingTagQ || !processInstanceName.contains("AL_FLOW")) && (!list.contains(processInstanceName) || !processInstanceName.contains("CL_FLOW"))) {
//                     list.add(processInstanceName);
//                  }
//
//                  ++i;
//               }
//            } else {
//               resObj.setResponseCode("5000");
//               resObj.setResponseMessage("SUCCESS");
//               automatictasklog.info("ECS Savvion :: " + methodName + " setting response as SUCCESS since piNamesList " + Arrays.toString(piNamesList) + " don't have active tasks");
//            }
//
//            if (blsession != null && blserver.isSessionValid(blsession)) {
//               blserver.disConnect(blsession);
//            }
//
//            if (blsessionECSAdmin != null && blserver.isSessionValid(blsessionECSAdmin)) {
//               blserver.disConnect(blsessionECSAdmin);
//            }
//
//            blserver = null;
//            if (this.db != null) {
//               this.db.closeConnection();
//            }
//         } catch (Exception var60) {
//            resObj.setResponseCode("5050");
//            resObj.setResponseMessage("FAILURE_EXCEPTION");
//            automatictasklog.error("ECS Savvion :: " + methodName + " Exception :: " + var60);
//         }
//      }
//
//      resObj.setInstanceCompleted(isInstanceCompleted);
//      if (completedWorkItem != null && completedWorkItem.length != 0) {
//         resObj.setCompletedWorkItemArray(completedWorkItem);
//      }
//
//      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END with response " + resObj);
//      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + resObj.getResponseCode() + " :: Response Message :: " + resObj.getResponseMessage());
//      return resObj;
//   }
//
//   public ResponseObject makeAvailableTaskECS(RequestObject[] reqObj) {
//      String methodName = "makeAvailableTaskECS";
//      automatictasklog.info("ECS Savvion: " + methodName + " Service ::START");
//      new HashMap();
//      UtilClass uc = new UtilClass(SBM_HOME);
//      String[] workItemName = null;
//      ResponseObject resObj = new ResponseObject();
//      String sessionId = null;
//      String performer = null;
//      String responseCode = null;
//      String responseMessage = null;
//      if (reqObj == null) {
//         responseCode = "5070";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
//         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            BLServer blserver = null;
//            Session blsession = null;
//            HashMap hashMap = uc.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
//            workItemName = (String[])hashMap.get("WORKITEMNAMES");
//            if (workItemName != null && !workItemName.equals("")) {
//               int index = 0;
//
//               while(true) {
//                  if (index >= workItemName.length) {
//                     responseCode = "5000";
//                     responseMessage = "SUCCESS";
//                     break;
//                  }
//
//                  if (workItemName[index] == null && !workItemName[index].equals("null")) {
//                     automatictasklog.warn("ECS Savvion: " + methodName + " Service::::WorkItem[" + index + "] is NULL");
//                  } else {
//                     performer = this.getWorkItemPerformerECS(workItemName[index]);
//                     blserver = BLClientUtil.getBizLogicServer();
//                     blsession = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
//                     WorkItem wi = blserver.getWorkItem(blsession, workItemName[index]);
//                     if (WorkItem.isExist(blsession, wi.getID()) && wi.isAssigned()) {
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
//               responseCode = "5021";
//               responseMessage = "INPUT_VALUE_IS_NULL";
//            }
//
//            if (blsession != null && blserver.isSessionValid(blsession)) {
//               blserver.disConnect(blsession);
//            }
//
//            blserver = null;
//         } catch (Exception var23) {
//            responseCode = "5050";
//            responseMessage = "FAILURE_EXCEPTION";
//            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Catch Exception :::" + var23.getMessage());
//         } finally {
//            try {
//               uc.disconnect((String)sessionId);
//            } catch (Exception var22) {
//               automatictasklog.error("ECS Savvion: assignTask Service::::Finally Exception:::" + var22.getMessage());
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
//   public ResponseObject getAssignedTaskList(RequestObject[] reqObj) {
//      String methodName = "getAssignedTaskList";
//      automatictasklog.info("===================ECS Savvion: " + methodName + "  Service:::: START=====================================");
//      String responseCode = null;
//      String responseMessage = null;
//      ResponseObject resObj = new ResponseObject();
//      new ArrayList();
//      WorkItemObject[] workItemObject = null;
//      new HashMap();
//      UtilClass uc = new UtilClass(SBM_HOME);
//      if (reqObj == null) {
//         responseCode = "5070";
//         responseMessage = "REQUEST_OBJECT_IS_NULL";
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//         automatictasklog.error("ECS Savvion: " + methodName + "  Service:::: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = uc.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//         String _ptName = (String)hashMap.get("PROCESSNAME");
//
//         try {
//            if (userId != null && !userId.equals("")) {
//               if (_ptName != null && !_ptName.equals("")) {
//                  ArrayList resultTaskList = this.db.getAssignedTasks(userId, _ptName);
//                  if (resultTaskList != null && resultTaskList.size() != 0) {
//                     workItemObject = this.getResultWorkitems(resultTaskList);
//                     automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject count is : " + workItemObject.length);
//                     resObj.setResultworkItemArray(workItemObject);
//                     responseCode = "5000";
//                     responseMessage = "SUCCESS";
//                  } else {
//                     responseCode = "5042";
//                     responseMessage = "ASSIGNED_INBOX_EMPTY";
//                  }
//
//                  if (this.db != null) {
//                     this.db.closeConnection();
//                  }
//               } else {
//                  responseCode = "5021";
//                  responseMessage = "INPUT_VALUE_IS_NULL";
//               }
//            } else {
//               responseCode = "5001";
//               responseMessage = "USER_ID_NULL";
//            }
//         } catch (Exception var13) {
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::Error Message::" + var13.getMessage());
//            responseCode = "5030";
//            responseMessage = "USER_ID_INVALID";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResponseMessage(responseMessage);
//      }
//
//      automatictasklog.info("==========================ECS Savvion: " + methodName + " Service :::: END :: " + responseCode + " :: Response Message :: " + responseMessage + "================================================");
//      return resObj;
//   }
//
//   public ResponseObject resultWorkItemArrayMerge(ResponseObject responseObj, ResponseObject resObjSR) {
//      String methodName = "ResultWorkItemArrayMerge";
//      int responseObjLength = 0;
//      int resObjSRLength = 0;
//      if (responseObj.getResultworkItemArray() != null) {
//         responseObjLength = responseObj.getResultworkItemArray().length;
//         automatictasklog.info("ECS service  " + methodName + " responseObj ResultworkItemArray " + Arrays.toString(responseObj.getResultworkItemArray()));
//      }
//
//      if (resObjSR.getResultworkItemArray() != null) {
//         resObjSRLength = resObjSR.getResultworkItemArray().length;
//         automatictasklog.info("ECS service  " + methodName + "  resObjSR ResultworkItemArray " + Arrays.toString(resObjSR.getResultworkItemArray()));
//      }
//
//      ArrayList<WorkItemObject> list = new ArrayList();
//
//      int i;
//      for(i = 0; i < responseObjLength; ++i) {
//         list.add(i, responseObj.getResultworkItemArray()[i]);
//      }
//
//      int l = list.size();
//
//      for(i = 0; i < resObjSRLength; ++i) {
//         list.add(l, resObjSR.getResultworkItemArray()[i]);
//         ++l;
//      }
//
//      responseObj.setResultworkItemArray(this.getResultWorkitems(list));
//      automatictasklog.info("ECS service  " + methodName + " close AL/CL method ResultworkItemArray" + Arrays.toString(responseObj.getResultworkItemArray()) + " :: responseObj ResultworkItemArray length at last " + responseObj.getResultworkItemArray().length);
//      return responseObj;
//   }
//
//   public ResponseObject findDuplicateEntryUsingPolicyNumber(String ptName, String policyNumber) {
//      String methodName = "findDuplicateEntryUsingPolicyNumber";
//      automatictasklog.info("==============ECS Savvion: " + methodName + " Service:::: START================================");
//      DBUtility db = new DBUtility(SBM_HOME);
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      new ArrayList();
//      WorkItemObject[] workItemObject = null;
//
//      try {
//         ArrayList resultTaskList = db.findDuplicateEntryUsingPolicyNumber(policyNumber, ptName);
//         automatictasklog.debug("ECS Savvion: " + methodName + " Service::resultTaskList size :: " + resultTaskList.size());
//         if (resultTaskList != null && resultTaskList.size() != 0) {
//            workItemObject = this.getResultWorkitems(resultTaskList);
//            automatictasklog.info("ECS Savvion: " + methodName + " Service::::workItemObject lenght : " + workItemObject.length);
//            resObj.setResultworkItemArray(workItemObject);
//            responseCode = "5039";
//         }
//      } catch (Exception var10) {
//         automatictasklog.error("ECS Savvion: " + methodName + " Service::Error Message::" + var10.getMessage());
//         responseCode = "5050";
//      }
//
//      resObj.setResponseCode(responseCode);
//      automatictasklog.info("==========================ECS Savvion: " + methodName + " Service :::: END" + responseCode + "====================================");
//      return resObj;
//   }
//
//   private void completingTagTask(UtilClass uc, WorkItemObject[] workItemObject, BLServer blserver, Session blsession, Session blsessionECSAdmin, ProcessInstance pi) {
//      HashMap hm = new HashMap();
//
//      try {
//         WorkItemObject[] var12 = workItemObject;
//         int var11 = workItemObject.length;
//
//         for(int var10 = 0; var10 < var11; ++var10) {
//            WorkItemObject workitemObj = var12[var10];
//            String workItemName = workitemObj.getName();
//            if (workItemName.contains("PMT")) {
//               automatictasklog.info("Complete PMT task entered");
//               String workItemCaseStatus = "ALClosure";
//               pi = blserver.getProcessInstance(blsession, workItemName.split("::")[0]);
//               if (workItemName != null && workItemName.contains("PMTQueue")) {
//                  hm.put("ClosureStep", workItemCaseStatus);
//               }
//
//               pi.updateSlotValue(hm);
//               pi.save();
//               WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workitemObj.getName());
//               if (WorkItem.isExist(blsessionECSAdmin, wi.getID()) && wi.isAvailable()) {
//                  automatictasklog.warn("ECS AssignTask enterred");
//                  uc.assignWorkitem((new Long(blsession.getID())).toString(), workItemName);
//                  automatictasklog.warn("ECS Task Assigned to user");
//               }
//
//               automatictasklog.warn("ECS Complete Task enterred");
//               uc.completeWorkitem((new Long(blsession.getID())).toString(), workItemName);
//               automatictasklog.info("PMT Task Completed");
//            }
//         }
//      } catch (Exception var15) {
//         automatictasklog.error("Catch Exception in tag & pmt & !Query & !approver & !executive & !deviation :::" + var15);
//      }
//
//   }
//}
