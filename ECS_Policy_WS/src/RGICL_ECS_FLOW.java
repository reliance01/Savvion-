//import com.savvion.ejb.bizlogic.manager.BizLogicManager;
//import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
//import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
//import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
//import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
//import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
//import com.savvion.sbm.bizlogic.server.ejb.BLServer;
//import com.savvion.sbm.bizlogic.server.svo.DateTime;
//import com.savvion.sbm.bizlogic.server.svo.Decimal;
//import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
//import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
//import com.savvion.sbm.bizlogic.server.svo.XML;
//import com.savvion.sbm.bizlogic.util.Session;
//import com.savvion.sbm.util.PService;
//import com.savvion.sbm.util.SBMHomeFactory;
//import com.savvion.sbm.util.ServiceLocator;
//import com.savvion.util.NLog;
//import com.savvion.webservice.bizlogic.BizLogicWSUtil;
//import com.tdiinc.BizLogic.Server.PAKException;
//import com.tdiinc.userManager.AdvanceGroup;
//import com.tdiinc.userManager.Realm;
//import com.tdiinc.userManager.User;
//import com.tdiinc.userManager.UserManager;
//import com.tdiinc.userManager.cache.UMCache;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.math.BigDecimal;
//import java.rmi.RemoteException;
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
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
//import rgicl.ecs.savvion.db.DBUtility;
//import rgicl.ecs.savvion.objectmodel.RequestObject;
//import rgicl.ecs.savvion.objectmodel.ResponseObject;
//import rgicl.ecs.savvion.objectmodel.TaskObject;
//import rgicl.ecs.savvion.objectmodel.UserRoleObject;
//import rgicl.ecs.savvion.objectmodel.WorkItemObject;
//
//
public class RGICL_ECS_FLOW {
	
}
//   private static BizLogicManager pak = null;
//   private String ptName = "RGICL_ECS_FLOW";
//   private static Byte[] bytearray = new Byte[0];
//   private static String CLMMGR = null;
//   private static Byte[] bytearrayCLMMGR = new Byte[0];
//   private static String BSMMGR = null;
//   private static Byte[] bytearrayBSMMGR = new Byte[0];
//   private static String RAMMGR = null;
//   private static Byte[] bytearrayRAMMGR = new Byte[0];
//   private static String ACCOUNTSMGR = null;
//   private static Byte[] bytearrayACCOUNTSMGR = new Byte[0];
//   private static String ROMMGR = null;
//   private static Byte[] bytearrayROMMGR = new Byte[0];
//   private static String RCMMGR = null;
//   private static Byte[] bytearrayRCMMGR = new Byte[0];
//   private static String ECSADMIN = null;
//   private static Byte[] bytearrayECSADMIN = new Byte[0];
//   private static String COMMGR = null;
//   private static Byte[] bytearrayCOMMGR = new Byte[0];
//   private String SBM_HOME = "";
//   private String ECS_SAVVION_PROPERTIES = "";
//   private String ECS_SAVVION_LOG_PROPERTIES = "";
//   Properties propertiesECSSavvion;
//   Properties propertiesECSLog;
//   static Logger automatictasklog = null;
//   static Logger humantasklog = null;
//   final String SUCCESS = "5000";
//   final String USER_ID_NULL = "5001";
//   final String USER_NOT_AUTHORIZED = "5002";
//   final String USER_NAME_NULL = "5003";
//   final String USER_ROLE_NULL = "5004";
//   final String CLM_NUM_NULL = "5005";
//   final String CLM_LOB_NULL = "5006";
//   final String CLM_FLAG_NULL = "5007";
//   final String CLM_TYPE_NULL = "5008";
//   final String CLM_REOPEN_NULL = "5009";
//   final String TPA_FLAG_NULL = "5010";
//   final String STP_FLAG_NULL = "5011";
//   final String PI_NAME_NULL = "5012";
//   final String PI_ID_NULL = "5013";
//   final String DS_NAME_NULL = "5014";
//   final String DS_VALUE_NULL = "5015";
//   final String WINAME_NULL = "5016";
//   final String REASSGN_BSM_NULL = "5017";
//   final String APPROVER_USER_ID_NULL = "5018";
//   final String RESERVE_APPR_FLAG_NULL = "5019";
//   final String PYMT_APPR_FLAG_NULL = "5020";
//   final String INPUT_VALUE_IS_NULL = "5021";
//   final String USER_ALREADY_MAPPED = "5022";
//   final String USER_NOT_MAPPED = "5023";
//   final String USER_ID_INVALID = "5030";
//   final String USER_ROLE_INVALID = "5031";
//   final String PI_ID_INVALID = "5032";
//   final String PI_NAME_INVALID = "5033";
//   final String DS_NAME_INVALID = "5034";
//   final String DS_VALUE_INVALID = "5035";
//   final String APPROVER_USER_ID_INVALID = "5036";
//   final String CLM_FLAG_INVALID = "5037";
//   final String INVALID_INPUT = "5038";
//   final String COMMON_INBOX_EMPTY = "5041";
//   final String ASSIGNED_INBOX_EMPTY = "5042";
//   final String FAILURE_EXCEPTION = "5050";
//   final String INVALID_WINAME = "5059";
//   final String WINAME_NOT_FOUND = "5060";
//   final String NEXT_WORKITEM_NOT_FOUND = "5043";
//   final String PENDING_CLAIMS_EMPTY = "5044";
//   final String DATABASE_ERROR = "5045";
//   final String ECSGroup = "ECS";
//   final String INVALID_REQUEST = "5047";
//   final String REQUEST_OBJECT_IS_NULL = "5070";
//   final String INVALID_LOB = "5046";
//   final String SUB_PROCESSINSTANCES_EMPTY = "SUB_PROCESSINSTANCES_EMPTY";
//   final String CLMMGRUser = "CLMMGR";
//   final String BSMMGRUser = "BSMMGR";
//   final String RCMMGRUser = "RCMMGR";
//   final String ROMMGRUser = "ROMMGR";
//   final String RAMMGRUser = "RAMMGR";
//   final String COMMGRUser = "COMMGR";
//   final String COINSURERUser = "COINSURER";
//
//   public RGICL_ECS_FLOW() {
//      try {
//         NLog.ws.debug("RGICL_ECS_FLOW service invoked");
//         this.SBM_HOME = System.getProperty("sbm.home");
//         String fileSep = System.getProperty("file.separator");
//         this.ECS_SAVVION_PROPERTIES = this.SBM_HOME + fileSep + "conf" + fileSep + "ECS_SAVVION_PROPERTIES.properties";
//         this.ECS_SAVVION_LOG_PROPERTIES = this.SBM_HOME + fileSep + "conf" + fileSep + "ECS_SAVVION_LOG_PROPERTIES.properties";
//         this.propertiesECSSavvion = new Properties();
//         this.propertiesECSSavvion.load(new FileInputStream(this.ECS_SAVVION_PROPERTIES));
//         this.propertiesECSLog = new Properties();
//         this.propertiesECSLog.load(new FileInputStream(this.ECS_SAVVION_LOG_PROPERTIES));
//         PropertyConfigurator.configure(this.propertiesECSLog);
//         automatictasklog = Logger.getLogger("Automatic");
//         humantasklog = Logger.getLogger("Human");
//      } catch (Exception var2) {
//         automatictasklog.error("Error loading properties file " + var2.getMessage());
//      }
//
//   }
//
//   public String INITIATEFLOW(String claimNumber, String claimFlag, String claimLOB, String claimReOpenFlag, String splitFlag, String TPAFlag, String claimType, String initiatedBy, String claimProcessor, String claimLocation, String policyLocation, String userId, String policyUpdateUser, String premiumCollectionUpdateUser, String approveReserveUser, String claimRegionLocation, String claimZoneLocation, String corporateLocation, String policyRegionLocation, String policyZoneLocation, String BSMEscUser1, String BSMEscUser2, String BSMEscUser3, String BSMEscUser4, String CMEscUser1, String CMEscUser2, String CMEscUser3, String CMEscUser4, String policyUpdationType, String username, String password, String piName, String priority, String coinsurerFlag, String subLOB) throws AxisFault {
//      NLog.ws.debug("INITIATEFLOW method invoked");
//      String sessionId = this.connect(username, password);
//      HashMap dsTypeMap = new HashMap();
//      dsTypeMap.put("claimNumber", "STRING");
//      dsTypeMap.put("claimFlag", "STRING");
//      dsTypeMap.put("claimLOB", "STRING");
//      dsTypeMap.put("claimReOpenFlag", "STRING");
//      dsTypeMap.put("splitFlag", "STRING");
//      dsTypeMap.put("TPAFlag", "STRING");
//      dsTypeMap.put("claimType", "STRING");
//      dsTypeMap.put("initiatedBy", "STRING");
//      dsTypeMap.put("claimProcessor", "STRING");
//      dsTypeMap.put("claimLocation", "STRING");
//      dsTypeMap.put("policyLocation", "STRING");
//      dsTypeMap.put("userId", "STRING");
//      dsTypeMap.put("policyUpdateUser", "STRING");
//      dsTypeMap.put("premiumCollectionUpdateUser", "STRING");
//      dsTypeMap.put("approveReserveUser", "STRING");
//      dsTypeMap.put("claimRegionLocation", "STRING");
//      dsTypeMap.put("claimZoneLocation", "STRING");
//      dsTypeMap.put("corporateLocation", "STRING");
//      dsTypeMap.put("policyRegionLocation", "STRING");
//      dsTypeMap.put("policyZoneLocation", "STRING");
//      dsTypeMap.put("BSMEscUser1", "STRING");
//      dsTypeMap.put("BSMEscUser2", "STRING");
//      dsTypeMap.put("BSMEscUser3", "STRING");
//      dsTypeMap.put("BSMEscUser4", "STRING");
//      dsTypeMap.put("CMEscUser1", "STRING");
//      dsTypeMap.put("CMEscUser2", "STRING");
//      dsTypeMap.put("CMEscUser3", "STRING");
//      dsTypeMap.put("CMEscUser4", "STRING");
//      dsTypeMap.put("policyUpdationType", "STRING");
//      dsTypeMap.put("coinsurerFlag", "STRING");
//      dsTypeMap.put("subLOB", "STRING");
//      HashMap dsValues = new HashMap();
//      dsValues.put("claimNumber", claimNumber);
//      dsValues.put("claimFlag", claimFlag);
//      dsValues.put("claimLOB", claimLOB);
//      dsValues.put("claimReOpenFlag", claimReOpenFlag);
//      dsValues.put("splitFlag", splitFlag);
//      dsValues.put("TPAFlag", TPAFlag);
//      dsValues.put("claimType", claimType);
//      dsValues.put("initiatedBy", initiatedBy);
//      dsValues.put("claimProcessor", claimProcessor);
//      dsValues.put("claimLocation", claimLocation);
//      dsValues.put("policyLocation", policyLocation);
//      dsValues.put("userId", userId);
//      dsValues.put("policyUpdateUser", policyUpdateUser);
//      dsValues.put("premiumCollectionUpdateUser", premiumCollectionUpdateUser);
//      dsValues.put("approveReserveUser", approveReserveUser);
//      dsValues.put("claimRegionLocation", claimRegionLocation);
//      dsValues.put("claimZoneLocation", claimZoneLocation);
//      dsValues.put("corporateLocation", corporateLocation);
//      dsValues.put("policyRegionLocation", policyRegionLocation);
//      dsValues.put("policyZoneLocation", policyZoneLocation);
//      dsValues.put("BSMEscUser1", BSMEscUser1);
//      dsValues.put("BSMEscUser2", BSMEscUser2);
//      dsValues.put("BSMEscUser3", BSMEscUser3);
//      dsValues.put("BSMEscUser4", BSMEscUser4);
//      dsValues.put("CMEscUser1", CMEscUser1);
//      dsValues.put("CMEscUser2", CMEscUser2);
//      dsValues.put("CMEscUser3", CMEscUser3);
//      dsValues.put("CMEscUser4", CMEscUser4);
//      dsValues.put("policyUpdationType", policyUpdationType);
//      dsValues.put("coinsurerFlag", coinsurerFlag);
//      dsValues.put("subLOB", subLOB);
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
//      this.disconnect(sessionId);
//      return pi;
//   }
//
//   public void COMPLETECLOSECLAIM(String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("CLOSECLAIM method invoked");
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETECOMPUTELIABILITY(String claimProcessor, String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("COMPLETECOMPUTELIABLITY method invoked");
//      HashMap dsTypeMap = new HashMap();
//      dsTypeMap.put("claimProcessor", "STRING");
//      HashMap dsValues = new HashMap();
//      dsValues.put("claimProcessor", claimProcessor);
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETEINITIATEPAYMENT(String claimProcessor, String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("COMPLETEINITIATEPAYMENT method invoked");
//      HashMap dsTypeMap = new HashMap();
//      dsTypeMap.put("claimProcessor", "STRING");
//      HashMap dsValues = new HashMap();
//      dsValues.put("claimProcessor", claimProcessor);
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETEPOLICYUPDATE(String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("COMPLETEPOLICYUPDATE method invoked");
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETEPREMIUMCOLLECTIONUPDATE(String sessionId, String piName, String wiName, String RAMToBSMFlag, String policyUpdationType) throws AxisFault {
//      NLog.ws.debug("COMPLETEPREMIUMCOLLECTIONUPDATE method invoked");
//      HashMap dsTypeMap = new HashMap();
//      dsTypeMap.put("RAMToBSMFlag", "STRING");
//      dsTypeMap.put("policyUpdationType", "STRING");
//      HashMap dsValues = new HashMap();
//      dsValues.put("RAMToBSMFlag", RAMToBSMFlag);
//      dsValues.put("policyUpdationType", policyUpdationType);
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETESUPERVISORAPPROVAL(String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("COMPLETESUPERVISORAPPROVAL method invoked");
//      HashMap dsTypeMap = new HashMap();
//      HashMap dsValues = new HashMap();
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
//   }
//
//   public void COMPLETEUPLOADDOCSANDRESERVE(String claimProcessor, String reAssignForPolicyUpdationFlag, String policyUpdationType, String sessionId, String piName, String wiName) throws AxisFault {
//      NLog.ws.debug("COMPLETEUPLOADDOCSANDREVISE method invoked");
//      HashMap dsTypeMap = new HashMap();
//      dsTypeMap.put("claimProcessor", "STRING");
//      dsTypeMap.put("reAssignForPolicyUpdationFlag", "STRING");
//      dsTypeMap.put("policyUpdationType", "STRING");
//      HashMap dsValues = new HashMap();
//      dsValues.put("claimProcessor", claimProcessor);
//      dsValues.put("reAssignForPolicyUpdationFlag", reAssignForPolicyUpdationFlag);
//      dsValues.put("policyUpdationType", policyUpdationType);
//      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
//      this.completeWorkitem(sessionId, wiName);
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
//   public String connect(String userId, String password) throws AxisFault {
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
//   public boolean disconnect(String sessionId) {
//      try {
//         getBizLogicManager().disconnect(sessionId);
//         return true;
//      } catch (Exception var3) {
//         return false;
//      }
//   }
//
//   public String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
//      return this.getWorkItemNames(sessionId, false);
//   }
//
//   public String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
//      return this.getWorkItemNames(sessionId, true);
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
//   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
//      QueryService qs = null;
//      QSWorkItemRS wirs = null;
//
//      try {
//         Session sess = getBizLogicManager().getSession(sessionId);
//         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", "RGICL_ECS_FLOW");
//         qs = new QueryService(sess);
//         QSWorkItem qswi = qs.getWorkItem();
//         wifil.setFetchSize(0);
//         if (available) {
//            wirs = qswi.getAvailableList(wifil);
//         } else {
//            wirs = qswi.getAssignedList(wifil);
//         }
//
//         List wi = wirs.getSVOList();
//         String[] winames = new String[wi.size()];
//
//         for(int i = 0; i < wi.size(); ++i) {
//            winames[i] = ((QSWorkItemData)wi.get(i)).getName();
//         }
//
//         String[] var12 = winames;
//         return var12;
//      } catch (Exception var19) {
//         throw new AxisFault("SBM Web services error :" + var19.getMessage());
//      } finally {
//         try {
//            wirs.close();
//         } catch (Exception var18) {
//         }
//
//      }
//   }
//
//   public boolean checkUserECS(String userId, String groupName) {
//      boolean isMember = false;
//
//      try {
//         User usr = UserManager.getUser(userId);
//         String[] groupNames = usr.getAllGroupNames();
//
//         for(int i = 0; i < groupNames.length; ++i) {
//            if (groupNames[i].equals(groupName)) {
//               isMember = true;
//            }
//         }
//
//         automatictasklog.debug("ECS Savvion: checkUserECS Service:::: userId is " + userId + " isMember" + isMember);
//      } catch (Exception var7) {
//         automatictasklog.error("ECS Savvion: checkUserECS Service:::: Exception " + var7.getMessage());
//      }
//
//      return isMember;
//   }
//
//   public ResponseObject mapUserToECSGroup(RequestObject[] reqObj) {
//      String result = null;
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         String userId = (String)hashMap.get("USERID");
//
//         try {
//            automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service START ::: INPUT VALUES are userId" + userId);
//            if (!userId.equals("") && userId != null) {
//               Realm r = UserManager.getDefaultRealm();
//               boolean b = r.addUser(userId);
//               if (b) {
//                  User sbmUser = r.getUser(userId);
//                  sbmUser.setAttribute("password", userId);
//               }
//
//               if (!this.checkUserECS(userId, "ECS")) {
//                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
//                  status = groupObj.addUserMember(userId);
//                  new UMCache();
//                  UMCache.invalidateAllCaches();
//                  if (status) {
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5050";
//                  }
//               } else {
//                  responseCode = "5022";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var12) {
//            automatictasklog.error("ECS Savvion: mapUserToECSGroup Service:::: Exception " + var12.getMessage());
//         }
//
//         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service END ::: " + responseCode);
//         resObj.setResponseCode(responseCode);
//      }
//
//      return resObj;
//   }
//
//   public ResponseObject removeUserFromECSGroup(RequestObject[] reqObj) {
//      String result = null;
//      boolean status = false;
//      AdvanceGroup groupObj = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
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
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5050";
//                  }
//
//                  automatictasklog.debug("ECS Savvion: removeUserFromECS Service::::userId is " + userId + " status is " + status);
//               } else {
//                  responseCode = "5023";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var10) {
//            automatictasklog.error("ECS Savvion: removeUserFromECS Service:::: Exception " + var10.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.debug("ECS Savvion: removeUserFromECS Service::: END " + responseCode);
//      return resObj;
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
//            sessionId = getBizLogicManager().connect(userId, password);
//         } else {
//            automatictasklog.debug("ECS Savvion: connectUser Service::::User " + userId + " Does not belongs to ECS");
//         }
//      } catch (RemoteException var6) {
//         automatictasklog.error("ECS Savvion: connectUser Service:::: Exception " + var6.getMessage());
//         sessionId = "false" + var6.getMessage();
//      }
//
//      return sessionId;
//   }
//
//   private static String connectCLMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(CLMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectCLMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayCLMMGR) {
//         if (CLMMGR == null || !valid) {
//            try {
//               CLMMGR = getBizLogicManager().connect("CLMMGR", "CLMMGR");
//            } catch (Exception var3) {
//               automatictasklog.error("ECS Savvion: connectCLMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return CLMMGR;
//   }
//
//   private static String connectBSMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(BSMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectBSMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayBSMMGR) {
//         if (BSMMGR == null || !valid) {
//            try {
//               BSMMGR = getBizLogicManager().connect("BSMMGR", "BSMMGR");
//            } catch (Exception var3) {
//               automatictasklog.error("ECS Savvion: connectBSMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return BSMMGR;
//   }
//
//   private static String connectRAMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(RAMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectRAMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayRAMMGR) {
//         if (RAMMGR == null || !valid) {
//            try {
//               RAMMGR = getBizLogicManager().connect("RAMMGR", "RAMMGR");
//            } catch (RemoteException var3) {
//               automatictasklog.error("ECS Savvion: connectRAMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return RAMMGR;
//   }
//
//   private static String connectROMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(ROMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectROMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayROMMGR) {
//         if (ROMMGR == null || !valid) {
//            try {
//               ROMMGR = getBizLogicManager().connect("ROMMGR", "ROMMGR");
//            } catch (RemoteException var3) {
//               automatictasklog.error("ECS Savvion: connectROMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return ROMMGR;
//   }
//
//   private static String connectRCMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(RCMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectRCMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayRCMMGR) {
//         if (RCMMGR == null || !valid) {
//            try {
//               RCMMGR = getBizLogicManager().connect("RCMMGR", "RCMMGR");
//            } catch (RemoteException var3) {
//               automatictasklog.error("ECS Savvion: connectRCMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return RCMMGR;
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
//               automatictasklog.error("ECS Savvion: connectECSADMIN Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return ECSADMIN;
//   }
//
//   private static String connectCOMMGR() {
//      boolean valid = false;
//
//      try {
//         valid = getBizLogicManager().isSessionValid(COMMGR);
//      } catch (RemoteException var4) {
//         automatictasklog.error("ECS Savvion: connectCOMMGR Service:::: Exception " + var4.getMessage());
//      }
//
//      synchronized(bytearrayCOMMGR) {
//         if (COMMGR == null || !valid) {
//            try {
//               COMMGR = getBizLogicManager().connect("COMMGR", "COMMGR");
//            } catch (Exception var3) {
//               automatictasklog.error("ECS Savvion: connectCOMMGR Service:::: Exception " + var3.getMessage());
//            }
//         }
//      }
//
//      return COMMGR;
//   }
//
//   public String connectAllGroups() {
//      String sessionId = null;
//
//      try {
//         sessionId = "";
//         sessionId = sessionId + "CLMMGR: " + connectCLMMGR();
//         sessionId = sessionId + "BSMMGR: " + connectBSMMGR();
//         sessionId = sessionId + "RAMMGR: " + connectRAMMGR();
//         sessionId = sessionId + "ROMMGR: " + connectROMMGR();
//         sessionId = sessionId + "RCMMGR: " + connectRCMMGR();
//         sessionId = sessionId + "ECSADMIN: " + connectECSADMIN();
//         sessionId = sessionId + "COMMGR: " + connectCOMMGR();
//         automatictasklog.debug("ECS Savvion: connectAllGroups Service:::: sessionId " + sessionId);
//      } catch (Exception var3) {
//         automatictasklog.error("ECS Savvion: connectAllGroups Service:::: Exception " + var3.getMessage());
//      }
//
//      return sessionId;
//   }
//
//   public ResponseObject getAssignedTaskListECS1(RequestObject[] reqObj) {
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: getAssignedTaskList Service:::: START");
//      String sessionId = null;
//      String sessionId1 = null;
//      String taskList = null;
//      String responseCode = null;
//      String taskListArray = "5042";
//      String[] responseList = (String[])null;
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getAssignedTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getAssignedTaskListECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//
//         try {
//            if (this.checkUserECS(userId, "ECS")) {
//               if (userId != null && !userId.equals("")) {
//                  sessionId = this.connectUser(userId);
//                  sessionId1 = this.connectUser(userId);
//                  if (!sessionId.equals("false")) {
//                     taskList = Arrays.asList(this.getAssignedWorkitemNames(sessionId)).toString();
//                     responseCode = "5000";
//                     String paymentTaskList = Arrays.asList(this.getAssignedTaskListAllECS(sessionId, this.propertiesECSSavvion.getProperty("ECSPaymentPTName"))).toString();
//                     String supervisorTaskList = Arrays.asList(this.getAssignedTaskListAllECS(sessionId1, this.propertiesECSSavvion.getProperty("ECSSupervisorPTName"))).toString();
//                     automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::paymentTaskList " + paymentTaskList);
//                     automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::supervisorTaskList " + supervisorTaskList);
//                     if (!paymentTaskList.equals("[]")) {
//                        if (!taskList.equals("[]")) {
//                           taskList = taskList + paymentTaskList;
//                        } else {
//                           taskList = paymentTaskList;
//                        }
//
//                        taskList = taskList.replace("][", ", ");
//                     }
//
//                     if (!supervisorTaskList.equals("[]")) {
//                        if (!taskList.equals("[]")) {
//                           taskList = taskList + supervisorTaskList;
//                        } else {
//                           taskList = supervisorTaskList;
//                        }
//
//                        taskList = taskList.replace("][", ", ");
//                     }
//
//                     if (taskList.equals("[]")) {
//                        responseCode = "5042";
//                     }
//
//                     taskListArray = taskList.replace("[", "").replace("]", "").trim();
//                     responseList = taskListArray.trim().split(",");
//                     workItemObject = this.getWorkItemObjectArray(responseList);
//                     resObj.setResultworkItemArray(workItemObject);
//                  } else {
//                     responseCode = "5050";
//                  }
//               } else {
//                  responseCode = "5001";
//               }
//            } else {
//               responseCode = "5002";
//            }
//
//            this.disconnect(sessionId);
//            this.disconnect(sessionId1);
//         } catch (Exception var14) {
//            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var14.getMessage());
//            responseCode = "5030";
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: getAssignedTaskList Service :::: END" + responseCode);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public ResponseObject initiateFlowECS(RequestObject[] reqObj) {
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: initiateFlowECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      new HashMap();
//      new HashMap();
//      HashMap coinsurerMap = new HashMap();
//      new HashMap();
//      String upLoadDocsworkStepName = null;
//      String responseCode = null;
//      String coinsurerBSMFlag = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: initiateFlowECS Service:::: INPUT VALUES" + hashMap);
//         HashMap getnextHM = new HashMap();
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//            responseCode = "5046";
//         } else {
//            String userId = (String)hashMap.get("USERID");
//            String claimNumber = (String)hashMap.get("CLAIMNUMBER");
//            String claimFlag = (String)hashMap.get("CLAIMFLAG");
//            String claimReOpenFlag = (String)hashMap.get("CLAIMREOPENFLAG");
//            String TPAFlag = (String)hashMap.get("TPAFLAG");
//            String claimType = (String)hashMap.get("CLAIMTYPE");
//            String lossLocation = (String)hashMap.get("LOSSLOCATION");
//            String policyIssuingOffice = (String)hashMap.get("POLICYISSUINGOFFICE");
//            String policyRegionLocation = (String)hashMap.get("POLICYREGIONLOCATION");
//            String PIOIdentificationFlag = (String)hashMap.get("PIOIDENTIFICATIONFLAG");
//            String policyUpdationType = (String)hashMap.get("POLICYUPDATIONTYPE");
//            String supervisorId = (String)hashMap.get("SUPERVISORID");
//            String claimRegionLocation = (String)hashMap.get("CLAIMREGIONLOCATION");
//            String claimZoneLocation = (String)hashMap.get("CLAIMZONELOCATION");
//            String corporateLocation = (String)hashMap.get("CORPORATELOCATION");
//            String policyZoneLocation = (String)hashMap.get("POLICYZONELOCATION");
//            String BSMEscUser1 = (String)hashMap.get("BSMESCUSER1");
//            String BSMEscUser2 = (String)hashMap.get("BSMESCUSER2");
//            String BSMEscUser3 = (String)hashMap.get("BSMESCUSER3");
//            String BSMEscUser4 = (String)hashMap.get("BSMESCUSER4");
//            String CMEscUser1 = (String)hashMap.get("CMESCUSER1");
//            String CMEscUser2 = (String)hashMap.get("CMESCUSER2");
//            String CMEscUser3 = (String)hashMap.get("CMESCUSER3");
//            String CMEscUser4 = (String)hashMap.get("CMESCUSER4");
//            String coinsurerFlag = (String)hashMap.get("COINSURERFLAG");
//            String paymentId = (String)hashMap.get("PAYMENTID");
//            String subLOB = (String)hashMap.get("SUBLOB");
//            String splitFlag = "false";
//            String processInstanceName = this.propertiesECSSavvion.getProperty("ECSProcessInstanceName") + claimNumber + "-";
//            String priority = this.propertiesECSSavvion.getProperty("priority");
//            String claimLocation = null;
//            String policyLocation = null;
//            String claimProcessor = null;
//            String policyUpdateUser = "BSMMGR";
//            String premiumCollectionUpdateUser = "RAMMGR";
//            boolean checkValidClaimFlag = true;
//            String[] workItemNames = (String[])null;
//            WorkItemObject[] workItemObject = new WorkItemObject[100];
//            String[] workItem = new String[100];
//            RequestObject[] reqo = new RequestObject[3];
//
//            try {
//               if (userId != null && !userId.equals("") && claimNumber != null && !claimNumber.equals("") && claimLOB != null && !claimLOB.equals("") && claimType != null && !claimType.equals("") && claimReOpenFlag != null && !claimReOpenFlag.equals("") && TPAFlag != null && !TPAFlag.equals("") && claimType != null && !claimType.equals("") && subLOB != null && !subLOB.equals("")) {
//                  if (this.checkUserECS(userId, "ECS")) {
//                     if (claimFlag.equals("Registered")) {
//                        if (PIOIdentificationFlag.equals("true")) {
//                           claimLocation = policyIssuingOffice;
//                           claimProcessor = "CLMMGR";
//                           policyLocation = policyIssuingOffice;
//                        }
//
//                        if (claimReOpenFlag.equals("true")) {
//                           claimProcessor = userId;
//                        }
//                     } else if (claimFlag.equals("Provisional")) {
//                        if (PIOIdentificationFlag.equals("true")) {
//                           splitFlag = "true";
//                           claimLocation = policyIssuingOffice;
//                           claimProcessor = "CLMMGR";
//                           policyLocation = policyIssuingOffice;
//                           policyUpdateUser = "BSMMGR";
//                        } else {
//                           claimLocation = lossLocation;
//                           claimProcessor = "CLMMGR";
//                        }
//                     } else {
//                        responseCode = "5037";
//                        checkValidClaimFlag = false;
//                     }
//
//                     if (checkValidClaimFlag) {
//                        if (coinsurerFlag == null) {
//                           coinsurerFlag = "false";
//                        }
//
//                        if (coinsurerFlag.equals("true")) {
//                           processInstanceName = this.INITIATEFLOW(claimNumber, claimFlag, claimLOB, claimReOpenFlag, splitFlag, TPAFlag, claimType, userId, claimProcessor, claimLocation, policyLocation, userId, policyUpdateUser, premiumCollectionUpdateUser, supervisorId, claimRegionLocation, claimZoneLocation, corporateLocation, policyRegionLocation, policyZoneLocation, BSMEscUser1, BSMEscUser2, BSMEscUser3, BSMEscUser4, CMEscUser1, CMEscUser2, CMEscUser3, CMEscUser4, policyUpdationType, userId, this.getUserPasswordECS(userId), processInstanceName, priority, coinsurerFlag, subLOB);
//                        } else {
//                           processInstanceName = this.INITIATEFLOW(claimNumber, claimFlag, claimLOB, claimReOpenFlag, splitFlag, TPAFlag, claimType, userId, claimProcessor, claimLocation, policyLocation, userId, policyUpdateUser, premiumCollectionUpdateUser, supervisorId, claimRegionLocation, claimZoneLocation, corporateLocation, policyRegionLocation, policyZoneLocation, BSMEscUser1, BSMEscUser2, BSMEscUser3, BSMEscUser4, CMEscUser1, CMEscUser2, CMEscUser3, CMEscUser4, policyUpdationType, userId, this.getUserPasswordECS(userId), processInstanceName, priority, coinsurerFlag, subLOB);
//                        }
//
//                        responseCode = "5000";
//                        automatictasklog.debug("ECS Savvion: initiateFlowECS Service::ProcessInstanceId is created::" + processInstanceName);
//                        String[] items = processInstanceName.split("#");
//                        getnextHM.put("PROCESSINSTANCEVALUE", items[1]);
//                        RequestObject[] reqObject = this.getRequestObject(getnextHM);
//                        ResponseObject resObject = this.getClaimStatusECS(reqObject);
//                        automatictasklog.debug("ECS Savvion: initiateFlowECS Service::getClaimStatusECS---- " + resObject.getResponseCode());
//                        workItemObject = resObject.getResultworkItemArray();
//                        workItemNames = new String[workItemObject.length];
//
//                        int i;
//                        for(i = 0; i < workItemObject.length; ++i) {
//                           workItemNames[i] = workItemObject[i].getPiName() + "::" + workItemObject[i].getWorkStepName() + "::" + workItemObject[i].getPerformer();
//                        }
//
//                        ResponseObject responseObject;
//                        RequestObject[] requestObject;
//                        if (coinsurerFlag.equals("true")) {
//                           requestObject = new RequestObject[]{new RequestObject(), null, null};
//                           requestObject[0].setKey("WORKITEMNAMES");
//                           if (workItemNames.length == 2) {
//                              requestObject[0].setValueArray(new String[]{workItemNames[1]});
//                           } else {
//                              requestObject[0].setValueArray(new String[]{workItemNames[0]});
//                           }
//
//                           requestObject[1] = new RequestObject();
//                           requestObject[1].setKey("TOBEASSIGNEDUSERID");
//                           requestObject[1].setValue("COINSURER");
//                           requestObject[2] = new RequestObject();
//                           requestObject[2].setKey("REASSIGNFLAG");
//                           requestObject[2].setValue("false");
//                           responseObject = this.assignTaskECS(requestObject);
//                           responseCode = responseObject.getResponseCode();
//                           automatictasklog.debug("ECS Savvion: initiateFlowECS Service::Coinurer :: assignTask::" + responseCode);
//                           if (workItemNames.length == 1) {
//                              automatictasklog.debug("ECS Savvion: initiateFlowECS Service:: coinsurer createPayment");
//                              if (paymentId != null || !paymentId.equals("")) {
//                                 coinsurerMap.put("USERID", userId);
//                                 coinsurerMap.put("CLAIMNUMBER", claimNumber);
//                                 coinsurerMap.put("PAYMENTID", paymentId);
//                                 coinsurerMap.put("APPROVEPAYMENTUSER", "false");
//                                 coinsurerMap.put("WORKITEMNAME", workItemNames[0]);
//                                 coinsurerMap.put("SUPERVISORAPPROVALFLAG", "false");
//                                 RequestObject[] ro = this.getRequestObject(coinsurerMap);
//                                 ResponseObject createPayment = this.createPaymentTaskECS(ro);
//                                 automatictasklog.debug("ECS Savvion: initiateFlowECS Service::coinsurer createpayment end " + createPayment.getResponseCode());
//                              }
//                           }
//                        }
//
//                        if (claimReOpenFlag.equals("true")) {
//                           automatictasklog.debug("ECS Savvion: initiateFlowECS Service:: Reopen claim ");
//                           if (supervisorId.equals("NONE")) {
//                              for(i = 0; i < workItemNames.length; ++i) {
//                                 String[] itemStep = workItemNames[i].split("::");
//                                 String wsName = itemStep[1];
//                                 if (wsName.equals("completeUploadDocsAndReserve")) {
//                                    upLoadDocsworkStepName = workItemNames[i];
//                                 }
//                              }
//
//                              reqo[0] = new RequestObject();
//                              reqo[0].setKey("WORKITEMNAMES");
//                              reqo[0].setValueArray(new String[]{upLoadDocsworkStepName});
//                              reqo[1] = new RequestObject();
//                              reqo[1].setKey("TOBEASSIGNEDUSERID");
//                              reqo[1].setValue(userId);
//                              reqo[2] = new RequestObject();
//                              reqo[2].setKey("REASSIGNFLAG");
//                              reqo[2].setValue("false");
//                              ResponseObject reso = this.assignTaskECS(reqo);
//                              responseCode = reso.getResponseCode();
//                              automatictasklog.debug("ECS Savvion: initiateFlowECS Service:: Reopen claim :: assignTask Response :: " + responseCode);
//                           } else {
//                              automatictasklog.debug("ECS Savvion: initiateFlowECS Service:: Reopen claim supervisorid EXISTS");
//                              requestObject = (RequestObject[])null;
//                              responseObject = null;
//                              HashMap createSupervisor = new HashMap();
//                              createSupervisor.put("USERID", userId);
//                              createSupervisor.put("SUPERVISORID", supervisorId);
//                              createSupervisor.put("WORKITEMNAME", workItemNames[0]);
//                              createSupervisor.put("CLAIMNUMBER", claimNumber);
//                              createSupervisor.put("TASKTYPE", "ReOpen");
//                              requestObject = this.getRequestObject(createSupervisor);
//                              responseObject = this.createSupervisorTaskECS(requestObject);
//                              responseCode = responseObject.getResponseCode();
//                              automatictasklog.debug("ECS Savvion: initiateFlowECS Service::ReOpened and assigned to " + supervisorId);
//                           }
//                        }
//                     }
//                  } else {
//                     responseCode = "5002";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } catch (Exception var60) {
//               responseCode = "5050";
//               automatictasklog.error("ECS Savvion: initiateFlowECS Service::::Catch Exception:::" + var60.getMessage());
//            }
//
//            resObj.setResultStringArray(new String[]{processInstanceName});
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: initiateFlowECS Service:::: END " + responseCode);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public ResponseObject completeUploadDocsAndReserveECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeUploadDocsAndReserveECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeUploadDocsAndReserveECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeUploadDocsAndReserveECS Service:::: INPUT VALUES " + hashMap);
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//            responseCode = "5046";
//         } else {
//            String userId = (String)hashMap.get("USERID");
//            String reassignForPolicyUpdationFlag = (String)hashMap.get("REASSIGNFORPOLICYUPDATION");
//            String workItemName = (String)hashMap.get("WORKITEMNAME");
//            String policyUpdationFlag = (String)hashMap.get("POLICYUPDATIONTYPE");
//            String sessionId = null;
//            String claimProcessor = null;
//            String[] splitWorkItem = new String[5];
//            String processInstanceName = null;
//            Object var14 = null;
//
//            try {
//               if (userId != null && !userId.equals("") && reassignForPolicyUpdationFlag != null && !reassignForPolicyUpdationFlag.equals("") && workItemName != null && !workItemName.equals("")) {
//                  splitWorkItem = workItemName.split("::");
//                  if (this.checkUserECS(userId, "ECS")) {
//                     sessionId = this.connectUser(userId);
//                     processInstanceName = splitWorkItem[0];
//                     if (!sessionId.equals("false")) {
//                        this.COMPLETEUPLOADDOCSANDRESERVE(userId, reassignForPolicyUpdationFlag, policyUpdationFlag, sessionId, processInstanceName, workItemName);
//                        responseCode = "5000";
//                        resObj.setResultStringArray(new String[]{"5000"});
//                     }
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } catch (Exception var24) {
//               responseCode = "5060";
//               humantasklog.error("ECS Savvion: completeUploadDocsECS Service::::Catch Exception:::" + var24.getMessage());
//            } finally {
//               try {
//                  this.disconnect(sessionId);
//               } catch (Exception var23) {
//                  humantasklog.error("ECS Savvion: completeUploadDocsECS::::Finally Exception:::" + var23.getMessage());
//               }
//
//            }
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeUploadDocsAndReserveECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completePolicyUpdateECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completePolicyUpdateECS-BSM Service:::: START");
//      String sessionId = null;
//      String[] splitWorkItem = new String[5];
//      String processInstanceName = null;
//      String result = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completePolicyUpdateECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completePolicyUpdateECS Service:::: INPUT VALUES " + hashMap);
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//            responseCode = "5046";
//         } else {
//            String userId = (String)hashMap.get("USERID");
//            String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//            try {
//               if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//                  splitWorkItem = workItemName.split("::");
//                  sessionId = this.connectUser(userId);
//                  processInstanceName = splitWorkItem[0];
//                  if (!sessionId.equals("false")) {
//                     this.COMPLETEPOLICYUPDATE(sessionId, processInstanceName, workItemName);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } catch (Exception var21) {
//               responseCode = "5060";
//               humantasklog.error("ECS Savvion: completePolicyUpdateECS-BSM Service::::Catch Exception:::" + var21.getMessage());
//            } finally {
//               try {
//                  this.disconnect(sessionId);
//               } catch (Exception var20) {
//                  humantasklog.error("ECS Savvion: completePolicyUpdateECS-BSM::::Finally Exception:::" + var20.getMessage());
//               }
//
//            }
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completePolicyUpdateECS-BSM Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completePremiumCollectionUpdateECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeUpdatePremiumCollectionECS-RAM Service:::: START");
//      String sessionId = null;
//      String[] splitWorkItem = new String[5];
//      String processInstanceName = null;
//      String result = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completePremiumCollectionUpdateECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completePremiumCollectionUpdateECS Service:::: INPUT VALUES " + hashMap);
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         String policyUpdationType = (String)hashMap.get("POLICYUPDATIONTYPE");
//         if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//            responseCode = "5046";
//         } else {
//            String userId = (String)hashMap.get("USERID");
//            String workItemName = (String)hashMap.get("WORKITEMNAME");
//            String RAMToBSMFlag = (String)hashMap.get("RAMTOBSMFLAG");
//
//            try {
//               if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//                  splitWorkItem = workItemName.split("::");
//                  sessionId = this.connectUser(userId);
//                  processInstanceName = splitWorkItem[0];
//                  if (!sessionId.equals("false")) {
//                     this.COMPLETEPREMIUMCOLLECTIONUPDATE(sessionId, processInstanceName, workItemName, RAMToBSMFlag, policyUpdationType);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } catch (Exception var23) {
//               responseCode = "5060";
//               humantasklog.error("ECS Savvion: completeUpdatePremiumCollectionECS-RAM Service::::Catch Exception:::" + var23.getMessage());
//            } finally {
//               try {
//                  this.disconnect(sessionId);
//               } catch (Exception var22) {
//                  humantasklog.error("ECS Savvion: completeUpdatePremiumCollectionECS-RAM::::Finally Exception:::" + var22.getMessage());
//               }
//
//            }
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeUpdatePremiumCollectionECS-RAM Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject assignTaskECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: assignTaskECS Service ::START");
//      new HashMap();
//      HashMap hm = new HashMap();
//      String[] workItemName = (String[])null;
//      String toBeAssignedUserId = null;
//      ResponseObject resObj = new ResponseObject();
//      String sessionId = null;
//      String performer = null;
//      String[] splitWorkItem = new String[5];
//      String workStepName = null;
//      String processInstanceName = null;
//      String reAssignFlag = null;
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: assignTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: assignTaskECS Service:::: INPUT VALUES " + hashMap);
//            workItemName = (String[])hashMap.get("WORKITEMNAMES");
//            toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//            reAssignFlag = (String)hashMap.get("REASSIGNFLAG");
//            if (toBeAssignedUserId != null && workItemName != null && !toBeAssignedUserId.equals("") && !workItemName.equals("") && !reAssignFlag.equals("") && reAssignFlag != null) {
//               sessionId = this.connectUser(toBeAssignedUserId);
//               if (sessionId.equals("false")) {
//                  responseCode = "5030";
//               } else {
//                  int index = 0;
//
//                  while(true) {
//                     if (index >= workItemName.length) {
//                        responseCode = "5000";
//                        break;
//                     }
//
//                     if (workItemName[index] == null && !workItemName[index].equals("null")) {
//                        automatictasklog.warn("ECS Savvion: assignTaskECS Service::::WorkItem[" + index + "] is NULL");
//                     } else {
//                        performer = this.getWorkItemPerformerECS(workItemName[index]);
//                        automatictasklog.warn("ECS AssignTask is false");
//                        if (reAssignFlag.equals("false")) {
//                           if (!performer.equals("BCMMGR") && !performer.equals("CLMMGR") && !performer.equals("BSMMGR") && !performer.equals("RAMMGR") && !performer.equals("ROMMGR") && !performer.equals("COMMGR") && !performer.equals("RCMMGR") && !performer.equals("ZCMMGR") && !performer.equals("CCMMGR") && !performer.equals("OHMGR") && !performer.equals("CHMGR") && !performer.equals("ZOMMGR")) {
//                              automatictasklog.warn("ECS Savvion: assignTaskECS Service:: " + workItemName[index] + " Already Acquired by " + performer);
//                           } else {
//                              automatictasklog.warn("ECS AssignTask enterred");
//                              getBizLogicManager().reassignWorkitemPerformer(sessionId, workItemName[index], toBeAssignedUserId);
//                              GregorianCalendar bsmcal = new GregorianCalendar();
//                              bsmcal.set(bsmcal.get(1) + 1, bsmcal.get(2), bsmcal.get(5), bsmcal.get(11), bsmcal.get(12), bsmcal.get(13));
//                              splitWorkItem = workItemName[index].split("::");
//                              processInstanceName = splitWorkItem[0];
//                              workStepName = splitWorkItem[1];
//                              getBizLogicManager().setWorkstepDuedate(sessionId, processInstanceName, workStepName, bsmcal.getTimeInMillis());
//                              RequestObject[] reqo;
//                              if (workStepName.equals("completePolicyUpdate")) {
//                                 hm.put("PROCESSINSTANCENAME", processInstanceName);
//                                 hm.put("DATASLOTNAME", "BSMAcquiredFlag");
//                                 hm.put("DATASLOTVALUE", "true");
//                                 reqo = this.getRequestObject(hm);
//                                 this.setDataSlotValueECS(reqo);
//                              }
//
//                              if (workStepName.equals("completeUploadDocsAndReserve")) {
//                                 hm.put("PROCESSINSTANCENAME", processInstanceName);
//                                 hm.put("DATASLOTNAME", "CMAcquiredFlag");
//                                 hm.put("DATASLOTVALUE", "true");
//                                 reqo = this.getRequestObject(hm);
//                                 this.setDataSlotValueECS(reqo);
//                              }
//                           }
//                        }
//
//                        if (reAssignFlag.equals("true")) {
//                           if (!performer.equals("BCMMGR") && !performer.equals("CLMMGR") && !performer.equals("BSMMGR") && !performer.equals("RAMMGR") && !performer.equals("ROMMGR") && !performer.equals("COMMGR") && !performer.equals("RCMMGR") && !performer.equals("ZCMMGR") && !performer.equals("CCMMGR") && !performer.equals("OHMGR") && !performer.equals("CHMGR") && !performer.equals("ZOMMGR")) {
//                              getBizLogicManager().reassignWorkitemPerformer(sessionId, workItemName[index], toBeAssignedUserId);
//                           } else {
//                              automatictasklog.warn("ECS Savvion: assignTaskECS Service:: " + workItemName[index] + " Can not be reassigned as it is Common Inbox of" + performer);
//                           }
//                        }
//                     }
//
//                     ++index;
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var25) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: assignTaskECS Service:::: Catch Exception :::" + var25.getMessage());
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
//      }
//
//      automatictasklog.info("ECS Savvion: assignTaskECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject setDataSlotValueECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: setDataSlotValue Service:::::START");
//      String result = null;
//      String sessionId = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: setDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: setDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String dataslotName = (String)hashMap.get("DATASLOTNAME");
//         String dataslotValue = (String)hashMap.get("DATASLOTVALUE");
//
//         try {
//            if (processInstanceName != null && dataslotName != null && dataslotValue != null && !processInstanceName.equals("") && !dataslotName.equals("") && !dataslotValue.equals("")) {
//               sessionId = connectECSADMIN();
//               if (!sessionId.equals("false")) {
//                  getBizLogicManager().setDataslotValue(sessionId, processInstanceName, dataslotName, dataslotValue);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var19) {
//            responseCode = "5034";
//            automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var19.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var18) {
//               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Finally Exception:::" + var18.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.error("ECS Savvion: setDataslotValue Service::::END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getDataSlotValueECS(RequestObject[] reqObj) {
//      automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::START");
//      String dataslotValue = null;
//      String responseCode = null;
//      String result = "5034";
//      String sessionId = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getDataSlotValueECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.debug("ECS Savvion: getDataSlotValueECS Service:::: INPUT VALUES " + hashMap);
//         automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String dataslotName = (String)hashMap.get("DATASLOTNAME");
//
//         try {
//            if (processInstanceName != null && dataslotName != null && !processInstanceName.equals("") && !dataslotName.equals("")) {
//               sessionId = connectECSADMIN();
//               if (!sessionId.equals("false")) {
//                  automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::" + sessionId + "::" + processInstanceName);
//                  dataslotValue = getBizLogicManager().getDataslotValue(sessionId, processInstanceName, dataslotName).toString();
//                  responseCode = "5000";
//                  if (dataslotValue != null) {
//                     resObj.setResultStringArray(new String[]{dataslotValue});
//                  }
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               automatictasklog.debug("ECS Savvion: getDataSlotValue Service::::processInstanceName is--- " + processInstanceName + "--- or dataslotName is---" + dataslotName);
//               responseCode = "5021";
//            }
//         } catch (Exception var19) {
//            responseCode = "5034";
//            automatictasklog.error("ECS Savvion: getDataslotValue Service::::Catch Exception:::" + var19.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var18) {
//               automatictasklog.error("ECS Savvion: getDataslotValu Service::::Finally Exception:::" + var18.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.debug("ECS Savvion: getDataSlotValue Service  :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getClaimStatusECS(RequestObject[] reqObj) {
//      automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::START");
//      String processInstanceName = null;
//      String sessionId = null;
//      String responseCode = null;
//      ArrayList workItemNames = new ArrayList();
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getClaimStatusECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getClaimStatusECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceValue = (String)hashMap.get("PROCESSINSTANCEVALUE");
//
//         try {
//            if (processInstanceValue != null && !processInstanceValue.equals("")) {
//               long processInstanceId = Long.parseLong(processInstanceValue);
//               BLServer blServer = BizLogicWSUtil.getBizLogic();
//               Session blSession = blServer.connect("ECSAdmin", "ECSAdmin");
//               sessionId = connectECSADMIN();
//               ProcessInstance processInstance = ProcessInstance.get(blSession, processInstanceId);
//               processInstanceName = processInstance.getName();
//               String[] workStepNames = processInstance.getActivatedWorkStepNames();
//               int ws = 0;
//
//               while(true) {
//                  if (ws >= workStepNames.length) {
//                     responseCode = "5000";
//                     break;
//                  }
//
//                  Vector workItemList = getBizLogicManager().getWorkitemList(sessionId, processInstanceName, workStepNames[ws]);
//
//                  for(int k = 0; k < workItemList.size(); ++k) {
//                     String[] a = (String[])workItemList.get(k);
//                     workItemNames.add(a[0]);
//                  }
//
//                  ++ws;
//               }
//            } else {
//               responseCode = "5013";
//            }
//         } catch (Exception var29) {
//            responseCode = "5032";
//            automatictasklog.error("ECS Savvion: getClaimStatusECS Service::::Catch Exception:::" + var29.getMessage());
//         } finally {
//            try {
//               boolean session = this.disconnect(sessionId);
//               automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::session flag ::" + session);
//            } catch (Exception var28) {
//               automatictasklog.error("ECS Savvion: getDataslotValu Service::::Finally Exception:::" + var28.getMessage());
//            }
//
//         }
//
//         String[] totalWorkitems = new String[workItemNames.size()];
//         workItemNames.toArray(totalWorkitems);
//         workItemObject = this.getWorkItemObjectArray(totalWorkitems);
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(workItemObject);
//      }
//
//      automatictasklog.debug("ECS Savvion: getClaimStatusECS Service::::END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeComputeLiabilityECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeComputeLiabilityECS Service:::: START");
//      new HashMap();
//      String sessionId = null;
//      String[] splitWorkItem = new String[5];
//      String processInstanceName = null;
//      String result = null;
//      String userId = null;
//      String workItemName = null;
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeComputeLiabilityECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: completeComputeLiabilityECS Service:::: INPUT VALUES " + hashMap);
//            String claimLOB = (String)hashMap.get("CLAIMLOB");
//            if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//               responseCode = "5046";
//            } else {
//               workItemName = (String)hashMap.get("WORKITEMNAME");
//               userId = (String)hashMap.get("USERID");
//               if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//                  splitWorkItem = workItemName.split("::");
//                  sessionId = this.connectUser(userId);
//                  processInstanceName = splitWorkItem[0];
//                  if (!sessionId.equals("false")) {
//                     this.COMPLETECOMPUTELIABILITY(userId, sessionId, processInstanceName, workItemName);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            }
//         } catch (Exception var20) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: completeComputeLiabilityECS Service::::Catch Exception:::" + var20.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var19) {
//               humantasklog.error("ECS Savvion: completeComputeLiabilityECS::::Finally Exception:::" + var19.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeComputeLiabilityECS Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject removeProcessInstanceECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: removeProcessInstanceECS Service:::: START");
//      String sessionId = null;
//      String result = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: removeProcessInstanceECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: removeProcessInstanceECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && userId != null && !userId.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  getBizLogicManager().removeProcessInstance(sessionId, processInstanceName);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var18) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: removeProcessInstanceECS Service::::Catch Exception:::" + var18.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var17) {
//               humantasklog.error("ECS Savvion: removeProcessInstanceECS::::Finally Exception:::" + var17.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: removeProcessInstanceECS Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeServiceProviderTaskECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeServiceProviderTaskECS Service :::: START");
//      String[] processInstanceName = new String[5];
//      String sessionId = null;
//      String responseCode = null;
//      String userId = null;
//      String workItemName = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeServiceProviderTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: completeServiceProviderTaskECS Service:::: INPUT VALUES " + hashMap);
//            userId = (String)hashMap.get("USERID");
//            workItemName = (String)hashMap.get("WORKITEMNAME");
//            sessionId = this.connectUser(userId);
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               if (!sessionId.equals("false")) {
//                  this.completeWorkitem(sessionId, workItemName);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var18) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: completeServiceProviderTaskECS Service Exception :::: " + var18.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var17) {
//               humantasklog.error("ECS Savvion: completeServiceProviderTaskECS Finally Exception :::: " + var17.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeServiceProviderTaskECS Service :::: END" + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeAllServiceProviderTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service:::: START");
//      String[] items = new String[10];
//      String[] subitems = new String[10];
//      String[] subProcessInstances = new String[10];
//      String[] processInstancelist = new String[5];
//      String workStepName = null;
//      String processInstanceName = null;
//      String processInstanceNameinSubProcess = null;
//      String workItemNameinSubProcess = null;
//      String result = null;
//      String sessionId = null;
//      int subPid = 0;
//      String pid = null;
//      String responseCode = null;
//      String claimId = null;
//      String[] subPiName = new String[5];
//      String subWorkStepName = null;
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      HashMap hm = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeAllServiceProviderTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service:1");
//               DBUtility dbutility = new DBUtility(this.SBM_HOME);
//               sessionId = this.connectUser(userId);
//               items = workItemName.split("::");
//               processInstanceName = items[0];
//               String[] pidlist = processInstanceName.split("#");
//               pid = pidlist[1];
//               subProcessInstances = getBizLogicManager().getProcessInstanceNameList(sessionId, this.propertiesECSSavvion.getProperty("ECSSPPTName"));
//               automatictasklog.debug("ECS Savvion: completeAllServiceProviderTasksECS Service:2" + subProcessInstances.length);
//               if (subProcessInstances.length == 0) {
//                  responseCode = "5000";
//               } else {
//                  for(int k = 0; k < subProcessInstances.length; ++k) {
//                     hm.put("PROCESSINSTANCENAME", subProcessInstances[k]);
//                     hm.put("DATASLOTNAME", "claimWorkItem");
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     ResponseObject reso = this.getDataSlotValueECS(reqo);
//                     automatictasklog.debug("ECS Savvion: completeAllServiceProviderTasksECS Service:3" + subProcessInstances.length);
//                     if (reso.getResponseCode().equals("5000")) {
//                        String[] wiNameInsub = reso.getResultStringArray();
//                        automatictasklog.debug("ECS Savvion: completeAllServiceProviderTasksECS Service:::: wiNameInsub");
//                        workItemNameinSubProcess = wiNameInsub[0];
//                        subitems = workItemNameinSubProcess.split("::");
//                        processInstanceNameinSubProcess = subitems[0];
//                        subWorkStepName = subitems[1];
//                        processInstancelist = processInstanceNameinSubProcess.split("#");
//                        subPid = Integer.parseInt(processInstancelist[1]);
//                        subPiName = processInstanceNameinSubProcess.split("-");
//                        claimId = subPiName[2];
//                        automatictasklog.info("Values of sub are : ---------------" + subWorkStepName + "-----" + subPid + "----" + pid + "----" + subPiName + "----" + claimId);
//                        if (processInstanceNameinSubProcess.equals(processInstanceName)) {
//                           dbutility.insertECS_COMPLETEALLPI_SUB(subPid, processInstanceNameinSubProcess, pid, claimId, subWorkStepName, userId, userId);
//                           automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service::INSERTION:: SUCCESS");
//                           getBizLogicManager().removeProcessInstance(sessionId, subProcessInstances[k]);
//                        }
//
//                        responseCode = "5000";
//                     }
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var37) {
//            responseCode = "5060";
//            automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service:::: Exception" + var37.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var36) {
//               humantasklog.error("ECS Savvion: removeProcessInstanceECS::::Finally Exception:::" + var36.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: completeAllServiceProviderTasksECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getAssignedSPTaskListECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getAssignedSPTaskListECS Service :::: START");
//      String userId = null;
//      String sessionId = null;
//      QueryService qs = null;
//      QSWorkItemRS wirs = null;
//      new HashMap();
//      new WorkItemObject();
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      String[] wiNames = new String[0];
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getAssignedSPTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: getAssignedSPTaskListECS Service:::: INPUT VALUES " + hashMap);
//            userId = (String)hashMap.get("USERID");
//            if (!userId.equals("") && userId != null) {
//               sessionId = this.connectUser(userId);
//               Session sess = getBizLogicManager().getSession(sessionId);
//               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.propertiesECSSavvion.getProperty("ECSSPPTName"));
//               qs = new QueryService(sess);
//               QSWorkItem qswi = qs.getWorkItem();
//               wifil.setFetchSize(0);
//               wirs = qswi.getAssignedList(wifil);
//               List wi = wirs.getSVOList();
//               wiNames = new String[wi.size()];
//
//               for(int i = 0; i < wi.size(); ++i) {
//                  wiNames[i] = ((QSWorkItemData)wi.get(i)).getName();
//               }
//
//               if (wi.size() == 0) {
//                  responseCode = "5042";
//               } else {
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5001";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var16) {
//            automatictasklog.error("ECS Savvion: getAssignedSPTaskListECS Service Catch :::: " + var16.getMessage());
//            responseCode = "5030";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(this.getWorkItemObjectArray(wiNames));
//      }
//
//      automatictasklog.info("ECS Savvion: getAssignedSPTaskListECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeInitiatePaymentECS(RequestObject[] reqObj) {
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeInitiatePaymentECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeInitiatePaymentECS Service:::: INPUT VALUES " + hashMap);
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         if (!claimLOB.equals("HEALTH") && !claimLOB.equals("PA")) {
//            responseCode = "5046";
//         } else {
//            String userId = (String)hashMap.get("USERID");
//            String workItemName = (String)hashMap.get("WORKITEMNAME");
//            String sessionId = null;
//            String[] splitWorkItem = new String[5];
//            String processInstanceName = null;
//
//            try {
//               humantasklog.info("ECS Savvion: completeInitiatePaymentECS Service:::: START" + hashMap);
//               if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//                  splitWorkItem = workItemName.split("::");
//                  sessionId = this.connectUser(userId);
//                  processInstanceName = splitWorkItem[0];
//                  if (!sessionId.equals("false")) {
//                     this.COMPLETEINITIATEPAYMENT(userId, sessionId, processInstanceName, workItemName);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } catch (Exception var20) {
//               responseCode = "5060";
//               humantasklog.error("ECS Savvion: completeInitiatePaymentECS Service::::Catch Exception:::" + var20.getMessage());
//            } finally {
//               try {
//                  this.disconnect(sessionId);
//               } catch (Exception var19) {
//                  humantasklog.error("ECS Savvion: completeInitiatePaymentECS::::Finally Exception:::" + var19.getMessage());
//               }
//
//            }
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeInitiatePaymentECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject createPaymentTaskECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: createPaymentTaskECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      HashMap hm = new HashMap();
//      HashMap inputMap = new HashMap();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: createPaymentTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: createPaymentTaskECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String claimNumber = (String)hashMap.get("CLAIMNUMBER");
//         String paymentId = (String)hashMap.get("PAYMENTID");
//         String approvePaymentUser = (String)hashMap.get("APPROVEPAYMENTUSER");
//         String claimWorkItem = (String)hashMap.get("WORKITEMNAME");
//         String supervisorApprovalFlag = (String)hashMap.get("SUPERVISORAPPROVALFLAG");
//         String claimId = claimNumber;
//         String processInstanceName = null;
//         String sessionId = null;
//         String result = null;
//         String piName = this.propertiesECSSavvion.getProperty("ECSPaymentInstanceName") + claimNumber + "-" + paymentId;
//         String paymentVerificationUser = "RAMMGR";
//         String mainProcessInstanceName = null;
//         String[] items = new String[10];
//         String TPAFlag = null;
//         String policyRegionLocation = null;
//
//         try {
//            if (userId != null && !userId.equals("") && claimNumber != null && !claimNumber.equals("") && paymentId != null && !paymentId.equals("") && approvePaymentUser != null && !approvePaymentUser.equals("") && claimWorkItem != null && !claimWorkItem.equals("") && supervisorApprovalFlag != null && !supervisorApprovalFlag.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  items = claimWorkItem.split("::");
//                  mainProcessInstanceName = items[0];
//                  hm.put("PROCESSINSTANCENAME", mainProcessInstanceName);
//                  hm.put("DATASLOTNAME", "TPAFlag");
//                  RequestObject[] reqo = this.getRequestObject(hm);
//                  ResponseObject reso = this.getDataSlotValueECS(reqo);
//                  String[] wiNameInsub = reso.getResultStringArray();
//                  TPAFlag = wiNameInsub[0];
//                  inputMap.put("PROCESSINSTANCENAME", mainProcessInstanceName);
//                  inputMap.put("DATASLOTNAME", "policyRegionLocation");
//                  reqo = this.getRequestObject(inputMap);
//                  reso = this.getDataSlotValueECS(reqo);
//                  wiNameInsub = reso.getResultStringArray();
//                  policyRegionLocation = wiNameInsub[0];
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("claimId", "STRING");
//                  dsTypeMap.put("paymentId", "STRING");
//                  dsTypeMap.put("approvePaymentUser", "STRING");
//                  dsTypeMap.put("paymentVerificationUser", "STRING");
//                  dsTypeMap.put("claimWorkItem", "STRING");
//                  dsTypeMap.put("parentPIName", "STRING");
//                  dsTypeMap.put("supervisorApprovalFlag", "STRING");
//                  dsTypeMap.put("TPAFlag", "STRING");
//                  dsTypeMap.put("policyRegionLocation", "STRING");
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("claimId", claimId);
//                  dsValues.put("paymentId", paymentId);
//                  dsValues.put("approvePaymentUser", approvePaymentUser);
//                  dsValues.put("paymentVerificationUser", paymentVerificationUser);
//                  dsValues.put("claimWorkItem", claimWorkItem);
//                  dsValues.put("parentPIName", mainProcessInstanceName);
//                  dsValues.put("supervisorApprovalFlag", supervisorApprovalFlag);
//                  dsValues.put("TPAFlag", TPAFlag);
//                  dsValues.put("policyRegionLocation", policyRegionLocation);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  processInstanceName = this.createProcessInstance(sessionId, this.propertiesECSSavvion.getProperty("ECSPaymentPTName"), piName, this.propertiesECSSavvion.getProperty("priority"), resolvedDSValues);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var37) {
//            humantasklog.info("ECS Savvion: createPaymentTaskECS Service:::: Exception" + var37.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var36) {
//               humantasklog.error("ECS Savvion: createPaymentTaskECS::::Finally Exception:::" + var36.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(new String[]{processInstanceName});
//      }
//
//      humantasklog.info("ECS Savvion: createPaymentTaskECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public String[] getAssignedTaskListAllECS(String sessionId, String processTemplateName) {
//      automatictasklog.info("ECS Savvion: getAssignedTaskListAllECS Service :::: START");
//      QueryService qs = null;
//      QSWorkItemRS wirs = null;
//      
//      String[] winames = new String[0];
//
//      try {
//         if (!sessionId.equals("") && sessionId != null) {
//            Session sess = getBizLogicManager().getSession(sessionId);
//            QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", processTemplateName);
//            qs = new QueryService(sess);
//            QSWorkItem qswi = qs.getWorkItem();
//            wifil.setFetchSize(0);
//            wirs = qswi.getAssignedList(wifil);
//            List wi = wirs.getSVOList();
//            winames = new String[wi.size()];
//
//            for(int i = 0; i < wi.size(); ++i) {
//               winames[i] = ((QSWorkItemData)wi.get(i)).getName();
//            }
//
//            if (wi.size() == 0) {
//               winames = new String[]{""};
//            }
//         } else {
//            winames = new String[]{"5001"};
//            automatictasklog.error("ECS Savvion: getAssignedTaskListAllECS Service::sessionId is NULL::");
//         }
//      } catch (Exception var19) {
//         automatictasklog.error("ECS Savvion: getAssignedTaskListAllECS Service::Catch::" + var19.getMessage());
//      } finally {
//         try {
//            this.disconnect(sessionId);
//            wirs.close();
//         } catch (Exception var18) {
//         }
//
//      }
//
//      automatictasklog.info("ECS Savvion: getAssignedTaskListAllECS Service :::: END");
//      return winames;
//   }
//
//   public ResponseObject createServiceProviderTaskECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion:: createServiceProviderTaskECS Service :::: START");
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      String processInstanceName = null;
//      String sessionId = null;
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: createServiceProviderTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: createServiceProviderTaskECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String claimId = (String)hashMap.get("CLAIMNUMBER");
//         String serviceProviderUser = (String)hashMap.get("SERVICEPROVIDERUSER");
//         String claimWorkItem = (String)hashMap.get("WORKITEMNAME");
//         String piName = this.propertiesECSSavvion.getProperty("ECSSPInstanceName") + claimId + "-";
//
//         try {
//            if (userId != null && !userId.equals("") && claimId != null && !claimId.equals("") && serviceProviderUser != null && !serviceProviderUser.equals("") && claimWorkItem != null && !claimWorkItem.equals("")) {
//               if (this.checkUserECS(userId, "ECS")) {
//                  sessionId = this.connectUser(userId);
//                  if (!sessionId.equals("false")) {
//                     HashMap dsTypeMap = new HashMap();
//                     dsTypeMap.put("claimId", "STRING");
//                     dsTypeMap.put("serviceProviderUser", "STRING");
//                     dsTypeMap.put("claimWorkItem", "STRING");
//                     HashMap dsValues = new HashMap();
//                     dsValues.put("claimId", claimId);
//                     dsValues.put("serviceProviderUser", serviceProviderUser);
//                     dsValues.put("claimWorkItem", claimWorkItem);
//                     Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                     processInstanceName = this.createProcessInstance(sessionId, this.propertiesECSSavvion.getProperty("ECSSPPTName"), piName, this.propertiesECSSavvion.getProperty("priority"), resolvedDSValues);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5030";
//                  }
//               } else {
//                  responseCode = "5002";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var15) {
//            humantasklog.error("ECS Savvion: createServiceProviderTaskECS Service:::: Exception " + var15.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(new String[]{processInstanceName});
//      }
//
//      humantasklog.info("ECS Savvion: createServiceProviderTaskECS Service :::: END" + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getNextWorkItemECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getNextWorkItemECS::::::::START");
//      ArrayList workItemNames = new ArrayList();
//      ArrayList userWorkItemNames = new ArrayList();
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getNextWorkItemECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getNextWorkItemECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceId = (String)hashMap.get("PROCESSINSTANCEID");
//         HashMap hm = new HashMap();
//         String[] workItemList = new String[0];
//         String[] totalWorkitems = (String[])null;
//
//         try {
//            if (userId != null && !userId.equals("") && processInstanceId != null && !processInstanceId.equals("")) {
//               hm.put("PROCESSINSTANCEVALUE", processInstanceId);
//               RequestObject[] reqo = this.getRequestObject(hm);
//               ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqo);
//               WorkItemObject[] workItemObject = resObjClaimStatus.getResultworkItemArray();
//               workItemList = new String[workItemObject.length];
//
//               int k;
//               for(k = 0; k < workItemObject.length; ++k) {
//                  workItemList[k] = workItemObject[k].getPiName() + "::" + workItemObject[k].getWorkStepName() + "::" + workItemObject[k].getPerformer();
//               }
//
//               workItemNames.addAll(Arrays.asList(workItemList));
//
//               for(k = 0; k < workItemNames.size(); ++k) {
//                  if (this.getWorkItemPerformerECS((String)workItemNames.get(k)).equals(userId)) {
//                     userWorkItemNames.add((String)workItemNames.get(k));
//                  }
//               }
//
//               if (userWorkItemNames.size() == 0) {
//                  responseCode = "5043";
//               } else {
//                  totalWorkitems = new String[userWorkItemNames.size()];
//                  userWorkItemNames.toArray(totalWorkitems);
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var16) {
//            responseCode = "5032";
//            automatictasklog.error("ECS Savvion: getNextWorkItemECS Service::::Catch Exception:::" + var16.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(totalWorkitems);
//      }
//
//      automatictasklog.info("ECS Savvion: getNextWorkItemECS::::::::END ");
//      return resObj;
//   }
//
//   public String getWorkItemPerformerECS(String workItemName) {
//      String result = null;
//
//      try {
//         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::START");
//         if (workItemName != null && !workItemName.equals("")) {
//            result = (String)getBizLogicManager().getWorkitemInfo(connectECSADMIN(), workItemName).get("PERFORMER");
//         } else {
//            automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService Service::::processInstanceName is--- " + workItemName);
//            result = "5021";
//         }
//
//         automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::SUCCESS::::END" + result);
//      } catch (Exception var4) {
//         automatictasklog.error("ECS Savvion: getWorkItemPerformerECSService:::: Exception:::" + var4.getMessage());
//         return "5059";
//      }
//
//      automatictasklog.info("ECS Savvion: getWorkItemPerformerECSService::::::::END");
//      return result;
//   }
//
//   public ResponseObject completeApprovePaymentECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeApprovePaymentECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeApprovePaymentECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeApprovePaymentECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String approvalFlag = (String)hashMap.get("APPROVALFLAG");
//         String sessionId = null;
//         String[] items = new String[1];
//         String processInstanceName = null;
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("") && approvalFlag != null && !approvalFlag.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  items = workItemName.split("::");
//                  processInstanceName = items[0];
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("approvalFlag", "STRING");
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("approvalFlag", approvalFlag);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  this.setProcessDataslotValues(sessionId, processInstanceName, resolvedDSValues);
//                  this.completeWorkitem(sessionId, workItemName);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var14) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: completeApprovePaymentECS Service:::: Exception" + var14.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeApprovePaymentECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeVerifyApprovedPaymentECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeVerifyApprovedPaymentECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      HashMap hm = new HashMap();
//      HashMap hmget = new HashMap();
//      HashMap hmgetlob = new HashMap();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeVerifyApprovedPaymentECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeVerifyApprovedPaymentECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String sessionId = null;
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               String[] items = workItemName.split("::");
//               String processInstanceName = items[0];
//               sessionId = this.connectUser(userId);
//               if (sessionId.equals("false")) {
//                  responseCode = "5030";
//               } else {
//                  hm.put("PROCESSINSTANCENAME", processInstanceName);
//                  hm.put("DATASLOTNAME", "parentPIName");
//                  RequestObject[] reqo = this.getRequestObject(hm);
//                  ResponseObject reso = this.getDataSlotValueECS(reqo);
//                  String[] dataSlotValue = reso.getResultStringArray();
//                  String parentPIName = dataSlotValue[0];
//                  humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::::" + parentPIName + "::");
//                  hmget.put("PROCESSINSTANCENAME", parentPIName);
//                  hmget.put("DATASLOTNAME", "coinsurerFlag");
//                  reqo = this.getRequestObject(hmget);
//                  reso = this.getDataSlotValueECS(reqo);
//                  dataSlotValue = reso.getResultStringArray();
//                  String coinsurer = dataSlotValue[0];
//                  humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::::" + coinsurer);
//                  hmgetlob.put("PROCESSINSTANCENAME", parentPIName);
//                  hmgetlob.put("DATASLOTNAME", "claimLOB");
//                  reqo = this.getRequestObject(hmgetlob);
//                  reso = this.getDataSlotValueECS(reqo);
//                  dataSlotValue = reso.getResultStringArray();
//                  String claimLob = dataSlotValue[0];
//                  humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::::" + claimLob);
//                  if (!coinsurer.equals("true")) {
//                     this.completeWorkitem(sessionId, workItemName);
//                     responseCode = "5000";
//                     humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::NORMALCLAIM ::: COMPLETED:: END");
//                  } else {
//                     String[] pid = parentPIName.split("#");
//                     this.completeWorkitem(sessionId, workItemName);
//                     hm.put("PROCESSINSTANCEVALUE", pid[1]);
//                     RequestObject[] reqObject = this.getRequestObject(hm);
//                     ResponseObject resObject = this.getClaimStatusECS(reqObject);
//                     humantasklog.info("ECS Savvion: completeVerifyApprovedPaymentECS Service::getClaimStatusECS---- " + resObject.getResponseCode());
//                     WorkItemObject[] workItemObject = resObject.getResultworkItemArray();
//                     String[] workItemNames = new String[workItemObject.length];
//
//                     for(int i = 0; i < workItemObject.length; ++i) {
//                        workItemNames[i] = workItemObject[i].getPiName() + "::" + workItemObject[i].getWorkStepName() + "::" + workItemObject[i].getPerformer();
//                     }
//
//                     humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::getClaimStatusECS---- " + workItemNames.length);
//                     ResponseObject completeUploadDocs;
//                     String var26;
//                     RequestObject[] ro;
//                     if (workItemNames.length == 1) {
//                        hm.put("CLAIMLOB", claimLob);
//                        hm.put("USERID", "COINSURER");
//                        hm.put("REASSIGNFORPOLICYUPDATION", "false");
//                        hm.put("WORKITEMNAME", workItemNames[0]);
//                        hm.put("POLICYUPDATIONTYPE", "NONE");
//                        ro = this.getRequestObject(hm);
//                        completeUploadDocs = this.completeUploadDocsAndReserveECS(ro);
//                        var26 = completeUploadDocs.getResponseCode();
//                     } else {
//                        hm = new HashMap();
//                        hm.put("CLAIMLOB", claimLob);
//                        hm.put("USERID", "COINSURER");
//                        hm.put("REASSIGNFORPOLICYUPDATION", "false");
//                        hm.put("WORKITEMNAME", workItemNames[1]);
//                        hm.put("POLICYUPDATIONTYPE", "NONE");
//                        ro = this.getRequestObject(hm);
//                        completeUploadDocs = this.completeUploadDocsAndReserveECS(ro);
//                        var26 = completeUploadDocs.getResponseCode();
//                     }
//
//                     responseCode = "5000";
//                     humantasklog.debug("ECS Savvion: completeVerifyApprovedPaymentECS Service::COINSURER ::: COMPLETED:: END");
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var35) {
//            responseCode = "5060";
//            humantasklog.info("ECS Savvion: completeVerifyApprovedPaymentECS Service:::: Exception" + var35.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var34) {
//               humantasklog.error("ECS Savvion: completeVerifyApprovedPaymentECS::::Finally Exception:::" + var34.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion: completeVerifyApprovedPaymentECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeAllPaymentTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeAllPaymentTasksECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeAllPaymentTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hm = new HashMap();
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeAllPaymentTasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] items = new String[10];
//         String[] subitems = new String[10];
//         String[] subProcessInstanceNames = new String[10];
//         String[] subProcessInstanceWorkItemNames = new String[10];
//         String[] processInstancelist = new String[5];
//         String workStepName = null;
//         String processInstanceName = null;
//         String processInstanceNameInSubProcess = null;
//         String workItemNameInSubProcess = null;
//         String sessionId = null;
//         int noOfSubProcessInstances = 0;
//         int subPid = 0;
//         String pid = null;
//         String claimId = null;
//         String[] subPiName = new String[5];
//         String result = null;
//         String subWorkStepName = null;
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               DBUtility dbutility = new DBUtility(this.SBM_HOME);
//               sessionId = this.connectUser(userId);
//               items = workItemName.split("::");
//               processInstanceName = items[0];
//               workStepName = items[1];
//               pid = items[2];
//               subProcessInstanceNames = getBizLogicManager().getProcessInstanceNameList(sessionId, this.propertiesECSSavvion.getProperty("ECSPaymentPTName"));
//               noOfSubProcessInstances = subProcessInstanceNames.length;
//               if (noOfSubProcessInstances <= 0) {
//                  responseCode = "SUB_PROCESSINSTANCES_EMPTY";
//               } else {
//                  for(int k = 0; k < noOfSubProcessInstances; ++k) {
//                     hm.put("PROCESSINSTANCENAME", subProcessInstanceNames[k]);
//                     hm.put("DATASLOTNAME", "claimWorkItem");
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     ResponseObject reso = this.getDataSlotValueECS(reqo);
//                     String[] wiNameInsub = reso.getResultStringArray();
//                     workItemNameInSubProcess = wiNameInsub[0];
//                     subitems = workItemNameInSubProcess.split("::");
//                     processInstanceNameInSubProcess = subitems[0];
//                     subWorkStepName = subitems[1];
//                     if (processInstanceNameInSubProcess.equals(processInstanceName)) {
//                        processInstancelist = processInstanceNameInSubProcess.split("#");
//                        subPid = Integer.parseInt(processInstancelist[1]);
//                        subPiName = processInstanceNameInSubProcess.split("-");
//                        claimId = subPiName[2];
//                        dbutility.insertECS_COMPLETEALLPI_SUB(subPid, processInstanceNameInSubProcess, pid, claimId, subWorkStepName, userId, userId);
//                        getBizLogicManager().removeProcessInstance(sessionId, subProcessInstanceNames[k]);
//                     }
//                  }
//
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var38) {
//            responseCode = "5060";
//            automatictasklog.error("ECS Savvion: completeAllPaymentTasksECS Service:::: Exception" + var38.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var37) {
//               humantasklog.error("ECS Savvion: completeAllPaymentTasksECS::::Finally Exception:::" + var37.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.error("ECS Savvion: completeAllPaymentTasksECS :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeAllTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeAllTasksECS Service:::: START");
//      String[] items = new String[10];
//      String[] processInstancelist = new String[5];
//      String[] subProcessInstanceNames = new String[10];
//      String[] subProcessInstanceWorkItemNames = new String[10];
//      String workStepName = null;
//      String processInstanceName = null;
//      String processInstanceNameInSubProcess = null;
//      String resultFromSP = null;
//      String resultFromPayment = null;
//      String resultFromSupervisor = null;
//      String result = null;
//      String sessionId = null;
//      String[] piName = new String[5];
//      new HashMap();
//      HashMap requestMap = new HashMap();
//      HashMap inputMap = new HashMap();
//      HashMap inputhm = new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      int pid = 0;
//      String claimId = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeAllTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeAllTasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               DBUtility dbutility = new DBUtility(this.SBM_HOME);
//               sessionId = this.connectUser(userId);
//               items = workItemName.split("::");
//               processInstanceName = items[0];
//               processInstancelist = processInstanceName.split("#");
//               workStepName = items[1];
//               automatictasklog.info("ECS Savvion: completeAllTasksECS");
//               pid = Integer.parseInt(processInstancelist[1]);
//               piName = processInstanceName.split("-");
//               claimId = piName[2];
//               requestMap.put("USERID", userId);
//               requestMap.put("WORKITEMNAME", workItemName);
//               RequestObject[] reqo = this.getRequestObject(requestMap);
//               ResponseObject reso = this.completeAllServiceProviderTasksECS(reqo);
//               resultFromSP = reso.getResponseCode();
//               inputMap.put("USERID", userId);
//               inputMap.put("WORKITEMNAME", workItemName);
//               RequestObject[] reqo1 = this.getRequestObject(inputMap);
//               ResponseObject reso1 = this.completeAllPaymentTasksECS(reqo1);
//               resultFromPayment = reso1.getResponseCode();
//               inputhm.put("USERID", userId);
//               inputhm.put("WORKITEMNAME", workItemName);
//               RequestObject[] reqo2 = this.getRequestObject(inputhm);
//               ResponseObject reso2 = this.completeAllPaymentTasksECS(reqo2);
//               resultFromSupervisor = reso2.getResponseCode();
//               automatictasklog.info("ECS Savvion: completeAllTasksECS Service::::" + resultFromSupervisor + resultFromPayment + resultFromSP);
//               if ((resultFromSP.equals("5000") || resultFromSP.equals("SUB_PROCESSINSTANCES_EMPTY")) && (resultFromPayment.equals("5000") || resultFromPayment.equals("SUB_PROCESSINSTANCES_EMPTY"))) {
//                  automatictasklog.info("ECS Savvion: completeAllTasksECS Service::::2" + resultFromSupervisor + resultFromPayment + resultFromSP);
//                  result = dbutility.insertECS_COMPLETEALLPI_PARENT(pid, processInstanceName, claimId, workStepName, userId, userId);
//                  automatictasklog.info("ECS Savvion: completeAllTasksECS Service:::: response from DB " + result);
//                  getBizLogicManager().removeProcessInstance(sessionId, processInstanceName);
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var40) {
//            responseCode = "5060";
//            automatictasklog.error("ECS Savvion: completeAllTasksECS Service:::: Exception ::" + var40.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var39) {
//               automatictasklog.error("ECS Savvion: completeAllPaymentTasksECS::::Finally Exception:::" + var39.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: completeAllTasksECS Service::COMPLETED:: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeCloseClaimECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: completeCloseClaimECS Service:::: START");
//      String sessionId = null;
//      ArrayList resultArrayList = new ArrayList();
//      String result = null;
//      String processInstanceName = null;
//      String workItemName = null;
//      String[] items = new String[2];
//      String[] workitemstokens = new String[5];
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeCloseClaimECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeCloseClaimECS Service:::: INPUT VALUES " + hashMap);
//         String[] processInstanceList = (String[])hashMap.get("PROCESSINSTANCELIST");
//
//         try {
//            if (processInstanceList.length == 0) {
//               responseCode = "5021";
//            } else {
//               humantasklog.debug("ECS Savvion: completeCloseClaimECS Service:::: processInstanceList.length  " + processInstanceList.length);
//
//               for(int pid = 0; pid < processInstanceList.length; ++pid) {
//                  try {
//                     processInstanceName = processInstanceList[pid];
//                     items = processInstanceName.split("#");
//                     long processInstanceId = Long.parseLong(items[1]);
//                     BLServer blServer = BizLogicWSUtil.getBizLogic();
//                     Session blSession = blServer.connect("ECSAdmin", "ECSAdmin");
//                     sessionId = connectECSADMIN();
//                     ProcessInstance processInstance = ProcessInstance.get(blSession, processInstanceId);
//                     String[] workStepNames = processInstance.getActivatedWorkStepNames();
//
//                     for(int ws = 0; ws < workStepNames.length; ++ws) {
//                        Vector workItemList = getBizLogicManager().getWorkitemList(sessionId, processInstanceName, workStepNames[ws]);
//
//                        for(int wi = 0; wi < workItemList.size(); ++wi) {
//                           String[] workList = (String[])workItemList.get(wi);
//                           workItemName = workList[0];
//                           humantasklog.info("ECS Savvion: completeCloseClaimECS Service:::: workItemName " + workItemName);
//                           workitemstokens = workItemName.split("::");
//                           if (workitemstokens[1].equals("completeCloseClaim")) {
//                              this.COMPLETECLOSECLAIM(sessionId, processInstanceName, workItemName);
//                              humantasklog.info("ECS Savvion: completeCloseClaimECS Service:::: CLOSED--- " + processInstanceName);
//                           } else {
//                              resultArrayList.add(processInstanceName);
//                              humantasklog.info("ECS Savvion: completeCloseClaimECS Service:::: UNCLOSED " + processInstanceName);
//                           }
//                        }
//                     }
//                  } catch (Exception var33) {
//                     resultArrayList.add(processInstanceName);
//                     humantasklog.error("ECS Savvion: completeCloseClaimECS Service::" + processInstanceName + var33);
//                  }
//               }
//
//               responseCode = "5000";
//            }
//         } catch (Exception var34) {
//            responseCode = "5050";
//            humantasklog.info("ECS Savvion: completeCloseClaimECS Service:::: Exception" + var34.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var32) {
//               humantasklog.error("ECS Savvion: completeCloseClaimECS::::Finally Exception:::" + var32.getMessage());
//            }
//
//         }
//
//         String[] unclosedClaims = new String[resultArrayList.size()];
//
//         for(int i = 0; i < unclosedClaims.length; ++i) {
//            humantasklog.info("ECS Savvion: completeCloseClaimECS Service :::: END :: unclosed " + unclosedClaims[0]);
//         }
//
//         resultArrayList.toArray(unclosedClaims);
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(unclosedClaims);
//      }
//
//      humantasklog.info("ECS Savvion: completeCloseClaimECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public String[] getAssignedAllWorkItemsECS(String sessionId) {
//      String taskList = null;
//
//      try {
//         automatictasklog.info("ECS Savvion: getAssignedAllWorkItemsECS Service::START");
//         taskList = Arrays.asList(this.getAssignedWorkitemNames(sessionId)).toString();
//         String paymentTaskList = Arrays.asList(this.getAssignedTaskListAllECS(sessionId, this.propertiesECSSavvion.getProperty("ECSPaymentPTName"))).toString();
//         if (!paymentTaskList.equals("[]")) {
//            if (!taskList.equals("[]")) {
//               taskList = taskList + paymentTaskList;
//            } else {
//               taskList = paymentTaskList;
//            }
//
//            taskList = taskList.replace("][", ", ");
//         }
//      } catch (Exception var4) {
//         automatictasklog.error("ECS Savvion: getAssignedAllWorkItemsECS::::Finally Exception:::" + var4.getMessage());
//      }
//
//      if (taskList.equals("[]")) {
//         taskList = "";
//      }
//
//      automatictasklog.info("ECS Savvion: getAssignedAllWorkItemsECS Service::ALL:: " + taskList);
//      taskList = taskList.replace("[", "");
//      taskList = taskList.replace("]", "");
//      taskList = taskList.replace(" ", "");
//      automatictasklog.info("ECS Savvion: getAssignedAllWorkItemsECS Service::END");
//      return taskList.split(",");
//   }
//
//   public String getUserPasswordECS(String userId) {
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
//   public ResponseObject reAssignAllTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: reassignAllTasksECS Service :::: START");
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      String result = null;
//      String sessionId = null;
//      String sessionIduser = null;
//      String fromUserId = null;
//      String toBeAssignedUserId = null;
//      String responseCode = null;
//      RequestObject[] reqo = new RequestObject[3];
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: reAssignAllTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: reAssignAllTasksECS Service:::: INPUT VALUES " + hashMap);
//            fromUserId = (String)hashMap.get("FROMUSERID");
//            toBeAssignedUserId = (String)hashMap.get("TOBEASSIGNEDUSERID");
//            automatictasklog.info("ECS Savvion:" + toBeAssignedUserId);
//            if (fromUserId != null && !fromUserId.equals("") && toBeAssignedUserId != null && !toBeAssignedUserId.equals("")) {
//               sessionId = this.connectUser(toBeAssignedUserId);
//               sessionIduser = this.connectUser(fromUserId);
//               automatictasklog.info("ECS Savvion:reassignAllTasksECS Service 1");
//               String[] workItemNames = this.getAssignedWorkitemNames(sessionIduser);
//               if (workItemNames != null && workItemNames.length != 0) {
//                  automatictasklog.info("ECS Savvion:reassignAllTasksECS Service workItemNames length is " + workItemNames.length);
//                  if (workItemNames[0].equals("5021") || workItemNames[0].equals("5050") || workItemNames[0].equals("5030") || workItemNames[0].equals("5042") && !sessionId.equals("false")) {
//                     responseCode = workItemNames[0];
//                  } else {
//                     String workItems = Arrays.asList(workItemNames).toString().replace(" ", "").replace("[", "").replace("]", "");
//                     automatictasklog.info("ECS Savvion:reassignAllTasksECS Service :::::: workItems " + workItems);
//                     workItemNames = workItems.split(",");
//                     reqo[0] = new RequestObject();
//                     reqo[0].setKey("TOBEASSIGNEDUSERID");
//                     reqo[0].setValue(toBeAssignedUserId);
//                     reqo[1] = new RequestObject();
//                     reqo[1].setKey("WORKITEMNAMES");
//                     reqo[1].setValueArray(workItemNames);
//                     reqo[2] = new RequestObject();
//                     reqo[2].setKey("REASSIGNFLAG");
//                     reqo[2].setValue("true");
//                     ResponseObject reso = this.assignTaskECS(reqo);
//                     responseCode = reso.getResponseCode();
//                     automatictasklog.info("ECS Savvion:reassignAllTasksECS Service :::::: workItemNames" + workItemNames);
//                  }
//               } else {
//                  responseCode = "5042";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//            automatictasklog.info("ECS Savvion:reassignAllTasksECS Service :::::: END" + result);
//         } catch (Exception var14) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: reassignAllTasksECS  Service::Exception:: " + var14.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: reassignAllTasksECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject viewPendingClaimsECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: viewPendingClaimsECS Service START");
//      String[] workItemList = new String[0];
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      String responseCode = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: viewPendingClaimsECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: viewPendingClaimsECS Service:::: INPUT VALUES " + hashMap);
//            String userLocationType = (String)hashMap.get("USERLOCATIONTYPE");
//            String userLocationId = (String)hashMap.get("USERLOCATIONID");
//            String userTeamType = (String)hashMap.get("USERTEAMTYPE");
//            if (userLocationType != null && !userLocationType.equals("") && userLocationId != null && !userLocationId.equals("") && userTeamType != null && !userTeamType.equals("")) {
//               workItemList = db.viewPendingClaimsListECS(userLocationType, userLocationId, userTeamType);
//               workItemList[0].replace("[", "");
//               if (workItemList[0].equals("[PENDING_CLAIMS_EMPTY]")) {
//                  automatictasklog.info("ECS Savvion: viewPendingClaimsECS Service : 5044");
//                  responseCode = "5044";
//               } else {
//                  resObj.setResultworkItemArray(this.getWorkItemObjectArray(workItemList));
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var11) {
//            automatictasklog.error("ECS Savvion: viewPendingClaimsECS Service EXCEPTION : " + var11);
//            responseCode = "5050";
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: viewPendingClaimsECS Service END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject updateClaimIntimationDetailsECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: updateClaimIntimationDetailsECS Service::::::::START");
//      ResponseObject resObj = new ResponseObject();
//      RequestObject[] initiateRequest = (RequestObject[])null;
//      String responseCode = null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: updateClaimIntimationDetailsECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: updateClaimIntimationDetailsECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String userId = (String)hashMap.get("USERID");
//         String claimNumber = (String)hashMap.get("CLAIMNUMBER");
//         String claimFlag = (String)hashMap.get("CLAIMFLAG");
//         String claimLOB = (String)hashMap.get("CLAIMLOB");
//         String claimReOpenFlag = (String)hashMap.get("CLAIMREOPENFLAG");
//         String TPAFlag = (String)hashMap.get("TPAFLAG");
//         String claimType = (String)hashMap.get("CLAIMTYPE");
//         String lossLocation = (String)hashMap.get("LOSSLOCATION");
//         String policyIssuingOffice = (String)hashMap.get("POLICYISSUINGOFFICE");
//         String policyRegionLocation = (String)hashMap.get("POLICYREGIONLOCATION");
//         String PIOIdentificationFlag = (String)hashMap.get("PIOIDENTIFICATIONFLAG");
//         String policyUpdationType = (String)hashMap.get("POLICYUPDATIONTYPE");
//         String supervisorId = (String)hashMap.get("SUPERVISORID");
//         String claimRegionLocation = (String)hashMap.get("CLAIMREGIONLOCATION");
//         String claimZoneLocation = (String)hashMap.get("CLAIMZONELOCATION");
//         String corporateLocation = (String)hashMap.get("CORPORATELOCATION");
//         String policyZoneLocation = (String)hashMap.get("POLICYZONELOCATION");
//         String BSMEscUser1 = (String)hashMap.get("BSMESCUSER1");
//         String BSMEscUser2 = (String)hashMap.get("BSMESCUSER2");
//         String BSMEscUser3 = (String)hashMap.get("BSMESCUSER3");
//         String BSMEscUser4 = (String)hashMap.get("BSMESCUSER4");
//         String CMEscUser1 = (String)hashMap.get("CMESCUSER1");
//         String CMEscUser2 = (String)hashMap.get("CMESCUSER2");
//         String CMEscUser3 = (String)hashMap.get("CMESCUSER3");
//         String CMEscUser4 = (String)hashMap.get("CMESCUSER4");
//         HashMap hm = new HashMap();
//         String sessionId = null;
//         String completionResult = null;
//         String[] workItemList = new String[100];
//         String[] processInfoList = new String[2];
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && userId != null && !userId.equals("") && claimNumber != null && !claimNumber.equals("") && claimLOB != null && !claimLOB.equals("") && claimType != null && !claimType.equals("") && claimReOpenFlag != null && !claimReOpenFlag.equals("") && TPAFlag != null && !TPAFlag.equals("") && claimType != null && !claimType.equals("") && lossLocation != null && !lossLocation.equals("") && PIOIdentificationFlag != null && !PIOIdentificationFlag.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (sessionId.equals("false")) {
//                  responseCode = "5030";
//               } else {
//                  processInfoList = processInstanceName.split("#");
//                  automatictasklog.debug("ECS Savvion: updateClaimIntimationDetailsECSe Service::::processInstanceValue" + processInfoList[1]);
//                  RequestObject[] reqObjClaimStatus = (RequestObject[])null;
//                  hm.put("PROCESSINSTANCEVALUE", processInfoList[1]);
//                  reqObjClaimStatus = this.getRequestObject(hm);
//                  new ResponseObject();
//                  ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqObjClaimStatus);
//                  automatictasklog.debug("ECS Savvion: updateClaimIntimationDetailsECSe Service::::getClaimStatus" + resObjClaimStatus.getResponseCode());
//                  WorkItemObject[] workItemObject = resObjClaimStatus.getResultworkItemArray();
//
//                  int index;
//                  for(index = 0; index < workItemObject.length; ++index) {
//                     workItemList[index] = workItemObject[index].getPiName() + "::" + workItemObject[index].getWorkStepName() + "::" + workItemObject[index].getPerformer();
//                  }
//
//                  for(index = 0; index < workItemList.length; ++index) {
//                     hm = new HashMap();
//                     hm.put("USERID", userId);
//                     hm.put("WORKITEMNAME", workItemList[index]);
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     ResponseObject reso = this.completeAllTasksECS(reqo);
//                     completionResult = reso.getResponseCode();
//                     if (completionResult.equals("5000")) {
//                        break;
//                     }
//                  }
//
//                  if (completionResult.equals("5000")) {
//                     resObj = this.initiateFlowECS(reqObj);
//                  } else {
//                     resObj.setResponseCode("5050");
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var43) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: updateClaimIntimationDetailsECS Service::::Catch Exception:::" + var43.getMessage());
//         }
//      }
//
//      automatictasklog.info("ECS Savvion: updateClaimIntimationDetailsECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject sendForPolicyUpdationECS(RequestObject[] reqObj) {
//      automatictasklog.error("ECS Savvion: sendForPolicyUpdationECS Service :::: START");
//      String responseCode = null;
//      new HashMap();
//      HashMap requestMap = new HashMap();
//      RequestObject[] reqObj1 = (RequestObject[])null;
//      ResponseObject resObj1 = null;
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: sendForPolicyUpdationECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: sendForPolicyUpdationECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String reAssignForPolicyUpdationFlag = (String)hashMap.get("REASSIGNFORPOLICYUPDATION");
//         String workItem = (String)hashMap.get("WORKITEMNAME");
//         String policyUpdationType = (String)hashMap.get("POLICYUPDATIONTYPE");
//         String policyUpdateUser = (String)hashMap.get("POLICYUPDATEUSER");
//         String completeUploadDocsResult = null;
//         String[] item = workItem.split("::");
//         String processInstanceName = item[0];
//         String sessionId = null;
//         HashMap hm = new HashMap();
//
//         try {
//            if (userId != null && !userId.equals("") && reAssignForPolicyUpdationFlag != null && !reAssignForPolicyUpdationFlag.equals("") && workItem != null && !workItem.equals("") && policyUpdationType != null && !policyUpdationType.equals("") && policyUpdateUser != null && !policyUpdateUser.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  String[] items = workItem.split("::");
//                  long BSMEscCounter = Long.parseLong("3");
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("policyUpdateUser", "STRING");
//                  if (policyUpdateUser.equals("COMMGR")) {
//                     dsTypeMap.put("BSMEscCounter", "LONG");
//                  }
//
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("policyUpdateUser", policyUpdateUser);
//                  if (policyUpdateUser.equals("COMMGR")) {
//                     dsValues.put("BSMEscCounter", BSMEscCounter);
//                  }
//
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  this.setProcessDataslotValues(sessionId, items[0], resolvedDSValues);
//                  requestMap.put("PROCESSINSTANCENAME", processInstanceName);
//                  requestMap.put("DATASLOTNAME", "claimLOB");
//                  reqObj1 = this.getRequestObject(requestMap);
//                  resObj1 = this.getDataSlotValueECS(reqObj1);
//                  String[] StatusResult1 = resObj1.getResultStringArray();
//                  String claimLob = StatusResult1[0];
//                  responseCode = "5000";
//                  hm.put("USERID", userId);
//                  hm.put("REASSIGNFORPOLICYUPDATION", reAssignForPolicyUpdationFlag);
//                  hm.put("WORKITEMNAME", workItem);
//                  hm.put("POLICYUPDATIONTYPE", policyUpdationType);
//                  hm.put("CLAIMLOB", claimLob);
//                  RequestObject[] ro = this.getRequestObject(hm);
//                  ResponseObject completeUploadDocs = this.completeUploadDocsAndReserveECS(ro);
//                  completeUploadDocsResult = completeUploadDocs.getResponseCode();
//                  resObj.setResultStringArray(new String[]{completeUploadDocsResult});
//                  automatictasklog.debug("ECS Savvion: sendForPolicyUpdationECS Service::" + completeUploadDocsResult);
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var28) {
//            automatictasklog.error("ECS Savvion: sendForPolicyUpdationECS Service::" + var28.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.error("ECS Savvion: sendForPolicyUpdationECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject updatePolicyIssuingOfficeDetailsECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: updatePolicyIssuingOfficeDetailsECS Service :::: START");
//      String sessionId = null;
//      String result = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: updatePolicyIssuingOfficeDetailsECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: updatePolicyIssuingOfficeDetailsECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String policyIssueOffice = (String)hashMap.get("POLICYISSUINGOFFICE");
//         String PIOIdentificationFlag = (String)hashMap.get("PIOIDENTIFICATIONFLAG");
//         String policyRegionLocation = (String)hashMap.get("POLICYREGIONLOCATION");
//         String policyZoneLocation = (String)hashMap.get("POLICYZONELOCATION");
//         String corporateLocation = (String)hashMap.get("CORPORATELOCATION");
//         String BSMEscUser1 = (String)hashMap.get("BSMESCUSER1");
//         String BSMEscUser2 = (String)hashMap.get("BSMESCUSER2");
//         String BSMEscUser3 = (String)hashMap.get("BSMESCUSER3");
//         String BSMEscUser4 = (String)hashMap.get("BSMESCUSER4");
//
//         try {
//            if (userId != null && !userId.equals("") && processInstanceName != null && !processInstanceName.equals("") && policyIssueOffice != null && !policyIssueOffice.equals("") && PIOIdentificationFlag != null && !PIOIdentificationFlag.equals("") && policyRegionLocation != null && !policyRegionLocation.equals("") && policyZoneLocation != null && !policyZoneLocation.equals("") && corporateLocation != null && !corporateLocation.equals("") && BSMEscUser1 != null && !BSMEscUser1.equals("") && BSMEscUser2 != null && !BSMEscUser2.equals("") && BSMEscUser3 != null && !BSMEscUser3.equals("") && BSMEscUser4 != null && !BSMEscUser4.equals("")) {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("policyLocation", "STRING");
//                  dsTypeMap.put("PIOIdentificationFlag", "STRING");
//                  dsTypeMap.put("policyRegionLocation", "STRING");
//                  dsTypeMap.put("policyZoneLocation", "STRING");
//                  dsTypeMap.put("corporateLocation", "STRING");
//                  dsTypeMap.put("BSMEscUser1", "STRING");
//                  dsTypeMap.put("BSMEscUser2", "STRING");
//                  dsTypeMap.put("BSMEscUser3", "STRING");
//                  dsTypeMap.put("BSMEscUser4", "STRING");
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("policyLocation", policyIssueOffice);
//                  dsValues.put("PIOIdentificationFlag", PIOIdentificationFlag);
//                  dsValues.put("policyRegionLocation", policyRegionLocation);
//                  dsValues.put("policyZoneLocation", policyZoneLocation);
//                  dsValues.put("corporateLocation", corporateLocation);
//                  dsValues.put("BSMEscUser1", BSMEscUser1);
//                  dsValues.put("BSMEscUser2", BSMEscUser2);
//                  dsValues.put("BSMEscUser3", BSMEscUser3);
//                  dsValues.put("BSMEscUser4", BSMEscUser4);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  this.setProcessDataslotValues(sessionId, processInstanceName, resolvedDSValues);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var21) {
//            automatictasklog.error("ECS Savvion: updatePolicyIssuingOfficeDetailsECS Service::" + var21.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: updatePolicyIssuingOfficeDetailsECS Service ::::END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getCommonInboxTaskListECS1(RequestObject[] reqObj) {
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: getCommonInboxTaskListUtility Service:::: START");
//      ArrayList taskList = new ArrayList();
//      new ArrayList();
//      String taskListArray = null;
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String[] taskListECS = new String[100];
//      String location = null;
//      String role = null;
//      String lob = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getCommonInboxTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.debug("ECS Savvion: getCommonInboxTaskListECS Service:::: INPUT VALUES " + hashMap);
//         String TPAFlag = (String)hashMap.get("TPAFLAG");
//         UserRoleObject[] uro = (UserRoleObject[])hashMap.get("USERROLEOBJECT");
//         ArrayList locationArrayList = new ArrayList();
//         ArrayList roleArrayList = new ArrayList();
//         ArrayList lobArrayList = new ArrayList();
//         this.printUserRoleObject(uro);
//         int uroLength = 0;
//
//         try {
//            for(int i = 0; i < uro.length; ++i) {
//               location = uro[i].getLocation();
//               role = uro[i].getRole();
//               lob = uro[i].getLOB();
//               automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::values " + location + "::" + role + "::" + lob);
//               if (location != null && role != null && lob != null) {
//                  if (location.length() != 0) {
//                     locationArrayList.add(location);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::location " + location);
//                  }
//
//                  if (role.length() != 0) {
//                     roleArrayList.add(role);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::role " + role);
//                  }
//
//                  if (lob.length() != 0) {
//                     lobArrayList.add(lob);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::lob " + lob);
//                  }
//
//                  if (location.length() != 0 && role.length() != 0 && lob.length() != 0) {
//                     ++uroLength;
//                  }
//               }
//            }
//
//            String[] locationList = new String[locationArrayList.size()];
//            String[] roleList = new String[roleArrayList.size()];
//            String[] lobList = new String[lobArrayList.size()];
//            locationArrayList.toArray(locationList);
//            roleArrayList.toArray(roleList);
//            lobArrayList.toArray(lobList);
//            automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service:::: RESULT" + locationList.length + " :: " + roleList.length + " :: " + lobList.length + " ::uroLength " + uroLength);
//            if (uro.length != 0 && TPAFlag != null && !TPAFlag.equals("") && locationList.length != 0 && roleList.length != 0 && lobList.length != 0) {
//               automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service:::: RESULT" + locationList.length + " :: " + roleList.length + " :: " + lobList.length + "::" + uroLength);
//               if (uroLength != 0) {
//                  taskList.addAll(Arrays.asList(db.getCommonInboxTaskListUtility(roleList, locationList, lobList, TPAFlag)));
//                  taskListArray = taskList.toString().replace("[[", "").replace("]]", "");
//                  if (taskListArray.toString().equals("COMMON_INBOX_EMPTY")) {
//                     responseCode = "5041";
//                  } else {
//                     taskListECS = taskListArray.trim().split(",");
//                     workItemObject = this.getWorkItemObjectArray(taskListECS);
//                     responseCode = "5000";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var23) {
//            automatictasklog.error("ECS Savvion: getCommonInboxTaskListUtilityECS Service:::: Exception FAILURE_EXCEPTION " + var23.getMessage());
//            responseCode = "5050";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(workItemObject);
//      }
//
//      automatictasklog.info("ECS Savvion: getCommonInboxTaskListUtility Service::::END " + responseCode);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public ResponseObject completeUploadDocsTPAECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service:::::: START");
//      ResponseObject resObj = new ResponseObject();
//      String completeUploadDocsTPAResult = null;
//      String completeUploadDocsWorkItem = null;
//      String completeComputeLiabilityWorkItem = null;
//      new HashMap();
//      HashMap hm = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeUploadDocsTPAECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String skipComputeLiabilityFlag = (String)hashMap.get("SKIPCOMPUTELIABILITYFLAG");
//         String[] totalWorkItems = (String[])null;
//         String[] totalWorkItems1 = (String[])null;
//         String sessionId = null;
//         HashMap requestMap = new HashMap();
//         HashMap inputMap = new HashMap();
//         RequestObject[] reqObj1 = (RequestObject[])null;
//         ResponseObject resObj1 = null;
//
//         try {
//            if (userId != null && !userId.equals("") && processInstanceName != null && !processInstanceName.equals("") && skipComputeLiabilityFlag != null && !skipComputeLiabilityFlag.equals("")) {
//               userId = userId.trim();
//               if (!skipComputeLiabilityFlag.equals("true")) {
//                  completeUploadDocsTPAResult = "5038";
//               } else {
//                  sessionId = this.connectUser(userId);
//                  if (sessionId.equals("false")) {
//                     completeUploadDocsTPAResult = "5030";
//                  } else {
//                     hm.put("PROCESSINSTANCENAME", processInstanceName);
//                     hm.put("DATASLOTNAME", "CMAcquiredFlag");
//                     hm.put("DATASLOTVALUE", "true");
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     this.setDataSlotValueECS(reqo);
//                     String[] processInstanceItems = processInstanceName.split("#");
//                     HashMap setData = new HashMap();
//                     setData.put("PROCESSINSTANCEVALUE", processInstanceItems[1]);
//                     RequestObject[] reqObjClaimStatus = this.getRequestObject(setData);
//                     new ResponseObject();
//                     ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqObjClaimStatus);
//                     WorkItemObject[] workItemObject = resObjClaimStatus.getResultworkItemArray();
//                     totalWorkItems = new String[workItemObject.length];
//
//                     int i;
//                     for(i = 0; i < workItemObject.length; ++i) {
//                        totalWorkItems[i] = workItemObject[i].getPiName() + "::" + workItemObject[i].getWorkStepName() + "::" + workItemObject[i].getPerformer();
//                     }
//
//                     if (totalWorkItems.length < 1) {
//                        completeUploadDocsTPAResult = "5033";
//                     } else {
//                        for(i = 0; i < totalWorkItems.length; ++i) {
//                           String[] items = totalWorkItems[i].split("::");
//                           if (items[1].equals("completeUploadDocsAndReserve")) {
//                              completeUploadDocsWorkItem = totalWorkItems[i];
//                           }
//                        }
//
//                        requestMap.put("PROCESSINSTANCENAME", processInstanceName);
//                        requestMap.put("DATASLOTNAME", "claimLOB");
//                        reqObj1 = this.getRequestObject(requestMap);
//                        resObj1 = this.getDataSlotValueECS(reqObj1);
//                        String[] StatusResult1 = resObj1.getResultStringArray();
//                        String claimLob = StatusResult1[0];
//                        requestMap.put("USERID", userId);
//                        requestMap.put("REASSIGNFORPOLICYUPDATION", "false");
//                        requestMap.put("WORKITEMNAME", completeUploadDocsWorkItem);
//                        requestMap.put("POLICYUPDATIONTYPE", "NONE");
//                        requestMap.put("CLAIMLOB", claimLob);
//                        RequestObject[] ro = this.getRequestObject(requestMap);
//                        ResponseObject completeUploadDocs = this.completeUploadDocsAndReserveECS(ro);
//                        completeUploadDocsTPAResult = completeUploadDocs.getResponseCode();
//                        automatictasklog.debug("ECS Savvion: completeUploadDocsTPAECS Service:5" + completeUploadDocsTPAResult);
//                        setData = new HashMap();
//                        setData.put("PROCESSINSTANCEVALUE", processInstanceItems[1]);
//                        RequestObject[] reqObjClaimStatus1 = this.getRequestObject(setData);
//                        new ResponseObject();
//                        ResponseObject resObjClaimStatus1 = this.getClaimStatusECS(reqObjClaimStatus1);
//                        WorkItemObject[] workItemObject1 = resObjClaimStatus1.getResultworkItemArray();
//                        totalWorkItems1 = new String[workItemObject1.length];
//                        automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service: ");
//
//                        
//                        for(i = 0; i < workItemObject1.length; ++i) {
//                           totalWorkItems1[i] = workItemObject1[i].getPiName() + "::" + workItemObject1[i].getWorkStepName() + "::" + workItemObject1[i].getPerformer();
//                        }
//
//                        if (totalWorkItems1.length >= 1 && completeUploadDocsTPAResult.equals("5000")) {
//                           automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service: **********");
//
//                           for(i = 0; i < totalWorkItems1.length; ++i) {
//                              String[] items1 = new String[3];
//                              items1 = totalWorkItems1[i].split("::");
//                              automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service: @@@@@@@]" + items1[1]);
//                              if (items1[1].equals("completeComputeLiability")) {
//                                 completeComputeLiabilityWorkItem = totalWorkItems1[i];
//                              }
//                           }
//
//                           inputMap.put("USERID", userId);
//                           inputMap.put("WORKITEMNAME", completeComputeLiabilityWorkItem);
//                           inputMap.put("CLAIMLOB", claimLob);
//                           RequestObject[] ro1 = this.getRequestObject(inputMap);
//                           ResponseObject completeUploadTPA = this.completeComputeLiabilityECS(ro1);
//                           completeUploadDocsTPAResult = completeUploadTPA.getResponseCode();
//                           automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service: 4");
//                           hm.put("PROCESSINSTANCENAME", processInstanceName);
//                           hm.put("DATASLOTNAME", "CMAcquiredFlag");
//                           hm.put("DATASLOTVALUE", "true");
//                           RequestObject[] reqo1 = this.getRequestObject(hm);
//                           this.setDataSlotValueECS(reqo1);
//                           completeUploadDocsTPAResult = "5000";
//                        }
//                     }
//                  }
//               }
//            } else {
//               completeUploadDocsTPAResult = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var34) {
//            completeUploadDocsTPAResult = "5050";
//            automatictasklog.error("ECS Savvion: completeUploadDocsTPAECS Service::" + var34.getMessage());
//         }
//
//         resObj.setResponseCode(completeUploadDocsTPAResult);
//      }
//
//      automatictasklog.info("ECS Savvion: completeUploadDocsTPAECS Service::::END " + completeUploadDocsTPAResult);
//      return resObj;
//   }
//
//   public ResponseObject completeInitiatePaymentTPAECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      HashMap requestMap = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeInitiatePaymentTPAECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         RequestObject[] reqObjwi = new RequestObject[2];
//         String sessionId = null;
//         String completeInitiatePaymentTPAResult = null;
//         String completeInitiatePaymentWorkItem = null;
//
//         try {
//            if (userId != null && !userId.equals("") && processInstanceName != null && !processInstanceName.equals("")) {
//               userId = userId.trim();
//               sessionId = this.connectUser(userId);
//               if (sessionId.equals("false")) {
//                  responseCode = "5030";
//               } else {
//                  String[] processInstanceItems = processInstanceName.split("#");
//                  String[] totalWorkItems = (String[])null;
//                  RequestObject[] reqObjClaimStatus = new RequestObject[1];
//                  HashMap setData = new HashMap();
//                  setData.put("PROCESSINSTANCEVALUE", processInstanceItems[1]);
//                  RequestObject[] reqObjClaimStatus1 = this.getRequestObject(setData);
//                  new ResponseObject();
//                  ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqObjClaimStatus1);
//                  WorkItemObject[] workItemObject = resObjClaimStatus.getResultworkItemArray();
//                  totalWorkItems = new String[workItemObject.length];
//
//                  int i;
//                  for(i = 0; i < workItemObject.length; ++i) {
//                     totalWorkItems[i] = workItemObject[i].getPiName() + "::" + workItemObject[i].getWorkStepName() + "::" + workItemObject[i].getPerformer();
//                  }
//
//                  if (totalWorkItems.length < 1) {
//                     responseCode = "5033";
//                  } else {
//                     for(i = 0; i < totalWorkItems.length; ++i) {
//                        String[] items = new String[100];
//                        items = totalWorkItems[i].split("::");
//                        if (items[1].equals("completeInitiatePayment")) {
//                           automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service :::: " + items[1]);
//                        }
//
//                        completeInitiatePaymentWorkItem = totalWorkItems[i];
//                     }
//
//                     HashMap setMapData = new HashMap();
//                     setMapData.put("USERID", userId);
//                     setMapData.put("WORKITEMNAME", completeInitiatePaymentWorkItem);
//                     requestMap.put("PROCESSINSTANCENAME", processInstanceName);
//                     requestMap.put("DATASLOTNAME", "claimLOB");
//                     RequestObject[] reqObj1 = this.getRequestObject(requestMap);
//                     ResponseObject resObj1 = this.getDataSlotValueECS(reqObj1);
//                     String[] StatusResult1 = resObj1.getResultStringArray();
//                     String claimLob = StatusResult1[0];
//                     setMapData.put("CLAIMLOB", claimLob);
//                     RequestObject[] reqObjwi1 = this.getRequestObject(setMapData);
//                     new ResponseObject();
//                     automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service :::: 1" + completeInitiatePaymentWorkItem);
//                     ResponseObject resObjClaimStatus1 = this.completeInitiatePaymentECS(reqObjwi1);
//                     automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service :::: 2");
//                     completeInitiatePaymentTPAResult = resObjClaimStatus1.getResponseCode();
//                     responseCode = completeInitiatePaymentTPAResult;
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var26) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: completeInitiatePaymentTPAECS Service::" + var26.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: completeInitiatePaymentTPAECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeAllTPATasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeAllTPATasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String sessionId = null;
//         String[] workItemList = (String[])null;
//         String[] piList = new String[2];
//
//         try {
//            if (userId != null && !userId.equals("") && processInstanceName != null && !processInstanceName.equals("")) {
//               userId = userId.trim();
//               sessionId = this.connectUser(userId);
//               piList = processInstanceName.split("#");
//               HashMap setData = new HashMap();
//               setData.put("PROCESSINSTANCEVALUE", piList[1]);
//               RequestObject[] reqObjClaimStatus = this.getRequestObject(setData);
//               new ResponseObject();
//               ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqObjClaimStatus);
//               if (!resObjClaimStatus.getResponseCode().equals("5000")) {
//                  responseCode = "5033";
//               } else {
//                  WorkItemObject[] workItemObject = new WorkItemObject[10];
//                  workItemObject = resObjClaimStatus.getResultworkItemArray();
//                  automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service:::: getclaimstatus success : " + workItemObject.length);
//                  workItemList = new String[workItemObject.length];
//
//                  int index;
//                  for(index = 0; index < workItemObject.length; ++index) {
//                     workItemList[index] = workItemObject[index].getPiName() + "::" + workItemObject[index].getWorkStepName() + "::" + workItemObject[index].getPerformer();
//                  }
//
//                  automatictasklog.debug("ECS Savvion: completeAllTPATasksECS Service:::: EXTRACT  " + workItemList.length);
//
//                  for(index = 0; index < workItemList.length; ++index) {
//                     HashMap hm = new HashMap();
//                     hm.put("USERID", userId);
//                     hm.put("WORKITEMNAME", workItemList[index]);
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     resObj = this.completeAllTasksECS(reqo);
//                     responseCode = resObj.getResponseCode();
//                     if (responseCode.equals("5000")) {
//                        break;
//                     }
//                  }
//               }
//            } else {
//               automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service::::processInstanceName is--- " + processInstanceName);
//               responseCode = "5021";
//            }
//         } catch (Exception var25) {
//            responseCode = "5033";
//            automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service:::: Exception" + var25.getMessage());
//         } finally {
//            try {
//               this.disconnect(sessionId);
//            } catch (Exception var24) {
//               humantasklog.error("ECS Savvion: completeAllTPATasksECS ::::Finally Exception:::" + var24.getMessage());
//            }
//
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: completeAllTPATasksECS Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getClaimStatusTPAECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service::::START");
//      String processInstanceName = null;
//      String responseCode = null;
//      String sessionId = null;
//      String TPAWorkitem = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getClaimStatusTPAECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceValue = (String)hashMap.get("PROCESSINSTANCEVALUE");
//         String[] workItem = new String[1];
//         String[] TPAResult = new String[1];
//
//         try {
//            if (processInstanceValue != null && !processInstanceValue.equals("")) {
//               long processInstanceId = Long.parseLong(processInstanceValue);
//               BLServer blServer = BizLogicWSUtil.getBizLogic();
//               Session blSession = blServer.connect("ECSAdmin", "ECSAdmin");
//               sessionId = connectECSADMIN();
//               ProcessInstance processInstance = ProcessInstance.get(blSession, processInstanceId);
//               processInstanceName = processInstance.getName();
//               String[] workStepNames = processInstance.getActivatedWorkStepNames();
//             
//               for(int ws = 0; ws < workStepNames.length; ++ws) {
//                  Vector workItemList = getBizLogicManager().getWorkitemList(sessionId, processInstanceName, workStepNames[ws]);
//
//                  for(int k = 0; k < workItemList.size(); ++k) {
//                     automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service::::START 10 ");
//                     TPAResult = (String[])workItemList.get(k);
//                     workItem[0] = new String(TPAResult[0].trim());
//                     automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service::::START 11" + workItem[0] + "KK" + workItem.length);
//                     responseCode = "5000";
//                  }
//               }
//            } else {
//               responseCode = "5013";
//            }
//         } catch (Exception var30) {
//            responseCode = "5032";
//            automatictasklog.error("ECS Savvion: getClaimStatusTPAECS Service::::Catch Exception:::" + var30.getMessage());
//         } finally {
//            try {
//               boolean session = this.disconnect(sessionId);
//               automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service::::session flag ::" + session);
//            } catch (Exception var29) {
//               automatictasklog.error("ECS Savvion: getDataslotValu Service::::Finally Exception:::" + var29.getMessage());
//            }
//
//         }
//
//         workItemObject = this.getWorkItemObjectArray(workItem);
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(workItemObject);
//      }
//
//      automatictasklog.info("ECS Savvion: getClaimStatusTPAECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getTaskStatusECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getTaskStatusECS Service:::: START");
//      String[] taskStatusResult = new String[3];
//      String sessionId = null;
//      new HashMap();
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getTaskStatusECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getTaskStatusECS Service:::: INPUT VALUES " + hashMap);
//         HashMap requestMap = new HashMap();
//         HashMap inputMap = new HashMap();
//         HashMap inputhm = new HashMap();
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         automatictasklog.info("ECS Savvion: getTaskStatusECS Service:::: START");
//         if (processInstanceName != null && !processInstanceName.equals("")) {
//            try {
//               sessionId = connectECSADMIN();
//               if (!sessionId.equals("false")) {
//                  requestMap.put("PROCESSINSTANCENAME", processInstanceName);
//                  requestMap.put("DATASLOTNAME", "uploadDocsFlag");
//                  RequestObject[] reqObj1 = this.getRequestObject(requestMap);
//                  ResponseObject resObj1 = this.getDataSlotValueECS(reqObj1);
//                  String[] StatusResult1 = resObj1.getResultStringArray();
//                  taskStatusResult[0] = StatusResult1[0];
//                  inputMap.put("PROCESSINSTANCENAME", processInstanceName);
//                  inputMap.put("DATASLOTNAME", "reviseReserveFlag");
//                  RequestObject[] reqObj2 = this.getRequestObject(inputMap);
//                  ResponseObject resObj2 = this.getDataSlotValueECS(reqObj2);
//                  String[] StatusResult2 = resObj2.getResultStringArray();
//                  taskStatusResult[1] = StatusResult2[0];
//                  inputhm.put("PROCESSINSTANCENAME", processInstanceName);
//                  inputhm.put("DATASLOTNAME", "assignSPFlag");
//                  RequestObject[] reqObj3 = this.getRequestObject(inputhm);
//                  ResponseObject resObj3 = this.getDataSlotValueECS(reqObj3);
//                  String[] StatusResult3 = resObj3.getResultStringArray();
//                  taskStatusResult[2] = StatusResult3[0];
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } catch (Exception var20) {
//               responseCode = "5033";
//               automatictasklog.error("ECS Savvion: getDataslotValue Service::::Catch Exception:::" + var20.getMessage());
//            }
//         } else {
//            responseCode = "5021";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(taskStatusResult);
//      }
//
//      automatictasklog.info("ECS Savvion: getTaskStatusECS Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject setTaskStatusECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: setTaskStatusECS Service:::: START");
//      String[] processInstanceList = new String[10];
//      String claimRefNo = null;
//      String performer = null;
//      String sessionId = null;
//      String[] workItemList = new String[10];
//      String responseCode = null;
//      new HashMap();
//      String result = null;
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: setTaskStatusECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hm = new HashMap();
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: setTaskStatusECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String uploadDocsFlag = (String)hashMap.get("UPLOADDOCSFLAG");
//         String reviseReserveFlag = (String)hashMap.get("REVISERESERVEFLAG");
//         String assignSPFlag = (String)hashMap.get("ASSIGNSPFLAG");
//         automatictasklog.info("ECS Savvion: setTaskStatusECS Service:::: START");
//         if (processInstanceName != null && !processInstanceName.equals("") && uploadDocsFlag != null && !uploadDocsFlag.equals("") && reviseReserveFlag != null && !reviseReserveFlag.equals("") && assignSPFlag != null && !assignSPFlag.equals("")) {
//            try {
//               sessionId = connectECSADMIN();
//               if (!sessionId.equals("false")) {
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("uploadDocsFlag", "STRING");
//                  dsTypeMap.put("reviseReserveFlag", "STRING");
//                  dsTypeMap.put("assignSPFlag", "STRING");
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("uploadDocsFlag", uploadDocsFlag);
//                  dsValues.put("reviseReserveFlag", reviseReserveFlag);
//                  dsValues.put("assignSPFlag", assignSPFlag);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  this.setProcessDataslotValues(sessionId, processInstanceName, resolvedDSValues);
//                  processInstanceList = processInstanceName.split("-");
//                  claimRefNo = processInstanceList[2];
//                  hm.put("PROCESSINSTANCENAME", processInstanceName);
//                  hm.put("DATASLOTNAME", "claimProcessor");
//                  RequestObject[] reqObj1 = this.getRequestObject(hm);
//                  ResponseObject resObj1 = this.getDataSlotValueECS(reqObj1);
//                  String[] StatusResult = resObj1.getResultStringArray();
//                  performer = StatusResult[0];
//                  DBUtility dbutility = new DBUtility(this.SBM_HOME);
//                  result = dbutility.insertECS_TASK_STATUS(processInstanceName, uploadDocsFlag, reviseReserveFlag, assignSPFlag, performer, claimRefNo);
//                  automatictasklog.debug("ECS Savvion: setTaskStatusECS Service:::: result" + result);
//                  if (result.equals("SUCCESS")) {
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5050";
//                  }
//               } else {
//                  responseCode = "5030";
//               }
//            } catch (Exception var23) {
//               responseCode = "5033";
//               automatictasklog.error("ECS Savvion: setDataslotValue Service::::Catch Exception:::" + var23.getMessage());
//            }
//         } else {
//            responseCode = "5021";
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: setTaskStatusECS Service:::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject sendToPreviousActivityECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: sendToPreviousActivityECS Service:: START");
//      String workStepName = null;
//      String policyUpdation = null;
//      String premiumCollectionUpdation = null;
//      String responseCode = null;
//      String uploadDocument = null;
//      String reviseReserve = null;
//      String assignServiceProvider = null;
//      String computeLiability = null;
//      String initiatePayment = null;
//      String activityName = null;
//      String[] items = new String[5];
//      String[] workItemArray = new String[10];
//      ResponseObject resObj = new ResponseObject();
//      new ResponseObject();
//      new HashMap();
//      HashMap setDSValue = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: sendToPreviousActivityECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: sendToPreviousActivityECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] activityNames = (String[])hashMap.get("ACTIVITYNAMES");
//         int activityNamesLength = activityNames.length;
//         RequestObject[] setDSReqObj = new RequestObject[2];
//         HashMap setDocs = new HashMap();
//         HashMap setPolicy = new HashMap();
//         new HashMap();
//         new HashMap();
//         HashMap setPolPrem = new HashMap();
//         HashMap completeHashMap = new HashMap();
//         HashMap splitMap = new HashMap();
//         HashMap setclaim = new HashMap();
//         items = workItemName.split("::");
//         String processInstanceName = items[0];
//         workStepName = items[1];
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("") && activityNamesLength != 0) {
//               if (!workStepName.equals("completePolicyUpdate") && !workStepName.equals("completePremiumCollectionUpdate") && !workStepName.equals("completeCloseClaim")) {
//                  setclaim.put("PROCESSINSTANCENAME", processInstanceName);
//                  setclaim.put("DATASLOTNAME", "claimProcessor");
//                  setclaim.put("DATASLOTVALUE", userId);
//                  setDSReqObj = this.getRequestObject(setclaim);
//                  this.setDataSlotValueECS(setDSReqObj);
//                  setclaim.put("PROCESSINSTANCENAME", processInstanceName);
//                  setclaim.put("DATASLOTNAME", "CMAcquiredFlag");
//                  setclaim.put("DATASLOTVALUE", "true");
//                  setDSReqObj = this.getRequestObject(setclaim);
//                  this.setDataSlotValueECS(setDSReqObj);
//                  policyUpdation = "DEFAULT";
//                  premiumCollectionUpdation = "DEFAULT";
//                  uploadDocument = "DEFAULT";
//                  reviseReserve = "DEFAULT";
//                  assignServiceProvider = "DEFAULT";
//                  computeLiability = "DEFAULT";
//                  initiatePayment = "DEFAULT";
//                  activityName = null;
//
//                  for(int i = 0; i < activityNamesLength; ++i) {
//                     if (activityNames[i].equals("POLICYUPDATION")) {
//                        policyUpdation = "POLICYUPDATION";
//                     }
//
//                     if (activityNames[i].equals("PREMIUMCOLLECTIONUPDATION")) {
//                        premiumCollectionUpdation = "PREMIUMCOLLECTIONUPDATION";
//                     }
//
//                     if (activityNames[i].equals("UPLOADDOCUMENTS")) {
//                        uploadDocument = "UPLOADDOCUMENTS";
//                     }
//
//                     if (activityNames[i].equals("REVISERESERVE")) {
//                        reviseReserve = "REVISERESERVE";
//                     }
//
//                     if (activityNames[i].equals("ASSIGNSERVICEPROVIDER")) {
//                        assignServiceProvider = "ASSIGNSERVICEPROVIDER";
//                     }
//
//                     if (activityNames[i].equals("COMPUTELIABILITY")) {
//                        computeLiability = "COMPUTELIABILITY";
//                     }
//
//                     if (activityNames[i].equals("INITIATEPAYMENT")) {
//                        initiatePayment = "INITIATEPAYMENT";
//                     }
//
//                     automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: END OF ASSIGNMENT" + policyUpdation + premiumCollectionUpdation + uploadDocument + reviseReserve + assignServiceProvider);
//                  }
//
//                  if (!uploadDocument.equals("UPLOADDOCUMENTS") && !reviseReserve.equals("REVISERESERVE") && !assignServiceProvider.equals("ASSIGNSERVICEPROVIDER")) {
//                     setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                     setDocs.put("DATASLOTNAME", "uploadDocsFlag");
//                     setDocs.put("DATASLOTVALUE", "open");
//                     setDSReqObj = this.getRequestObject(setDocs);
//                     this.setDataSlotValueECS(setDSReqObj);
//                     reviseReserve = "REVISERESERVE";
//                     setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                     setDocs.put("DATASLOTNAME", "reviseReserveFlag");
//                     setDocs.put("DATASLOTVALUE", "open");
//                     setDSReqObj = this.getRequestObject(setDocs);
//                     this.setDataSlotValueECS(setDSReqObj);
//                     setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                     setDocs.put("DATASLOTNAME", "assignSPFlag");
//                     setDocs.put("DATASLOTVALUE", "open");
//                     setDSReqObj = this.getRequestObject(setDocs);
//                     this.setDataSlotValueECS(setDSReqObj);
//                  } else {
//                     if (uploadDocument.equals("UPLOADDOCUMENTS")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG UPLOAD TRUE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "uploadDocsFlag");
//                        setDocs.put("DATASLOTVALUE", "open");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     } else {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG UPLOAD FALSE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "uploadDocsFlag");
//                        setDocs.put("DATASLOTVALUE", "close");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     }
//
//                     if (reviseReserve.equals("REVISERESERVE")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG REVISERESERVE TRUE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "reviseReserveFlag");
//                        setDocs.put("DATASLOTVALUE", "open");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     } else {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG REVISERESERVE FALSE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "reviseReserveFlag");
//                        setDocs.put("DATASLOTVALUE", "close");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     }
//
//                     if (assignServiceProvider.equals("ASSIGNSERVICEPROVIDER")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG ASSIGNSERVICEPROVIDER TRUE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "assignSPFlag");
//                        setDocs.put("DATASLOTVALUE", "open");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     } else {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: SET FLAG ASSIGNSERVICEPROVIDER FALSE");
//                        setDocs.put("PROCESSINSTANCENAME", processInstanceName);
//                        setDocs.put("DATASLOTNAME", "assignSPFlag");
//                        setDocs.put("DATASLOTVALUE", "close");
//                        setDSReqObj = this.getRequestObject(setDocs);
//                        this.setDataSlotValueECS(setDSReqObj);
//                     }
//                  }
//
//                  if (!uploadDocument.equals("UPLOADDOCUMENTS") && !reviseReserve.equals("REVISERESERVE") && !assignServiceProvider.equals("ASSIGNSERVICEPROVIDER")) {
//                     uploadDocument = "DEFAULT";
//                  } else {
//                     uploadDocument = "completeUploadDocs";
//                  }
//
//                  if (uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                     automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: ONLY TO DOCS");
//                     splitMap.put("PROCESSINSTANCENAME", processInstanceName);
//                     splitMap.put("DATASLOTNAME", "splitFlag");
//                     splitMap.put("DATASLOTVALUE", "false");
//                     setDSReqObj = this.getRequestObject(splitMap);
//                     this.setDataSlotValueECS(setDSReqObj);
//                  } else {
//                     automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: DOCS POLICY PREMIUM");
//                     splitMap.put("PROCESSINSTANCENAME", processInstanceName);
//                     splitMap.put("DATASLOTNAME", "splitFlag");
//                     splitMap.put("DATASLOTVALUE", "true");
//                     setDSReqObj = this.getRequestObject(splitMap);
//                     this.setDataSlotValueECS(setDSReqObj);
//                  }
//
//                  automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADDOCUMENTS VALUE" + uploadDocument + workStepName);
//                  RequestObject[] reqObj2;
//                  if (workStepName.equals("completeUploadDocsAndReserve")) {
//                     automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: completeupload docs to policy updation and premiumcollection");
//                     reqObj2 = (RequestObject[])null;
//                     HashMap sendBSMRAM = new HashMap();
//                     if (policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: BOTH");
//                        sendBSMRAM.put("USERID", userId);
//                        sendBSMRAM.put("REASSIGNFORPOLICYUPDATION", "true");
//                        sendBSMRAM.put("WORKITEMNAME", workItemName);
//                        sendBSMRAM.put("POLICYUPDATIONTYPE", "BOTH");
//                        sendBSMRAM.put("POLICYUPDATEUSER", "BSMMGR");
//                     }
//
//                     if (policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICYUPDATION");
//                        sendBSMRAM.put("USERID", userId);
//                        sendBSMRAM.put("REASSIGNFORPOLICYUPDATION", "true");
//                        sendBSMRAM.put("WORKITEMNAME", workItemName);
//                        sendBSMRAM.put("POLICYUPDATIONTYPE", "BSM");
//                        sendBSMRAM.put("POLICYUPDATEUSER", "BSMMGR");
//                     }
//
//                     if (!policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: PREMIUMCOLLECTIONUPDATION");
//                        sendBSMRAM.put("USERID", userId);
//                        sendBSMRAM.put("REASSIGNFORPOLICYUPDATION", "true");
//                        sendBSMRAM.put("WORKITEMNAME", workItemName);
//                        sendBSMRAM.put("POLICYUPDATIONTYPE", "RAM");
//                        sendBSMRAM.put("POLICYUPDATEUSER", "RAMMGR");
//                     }
//
//                     reqObj2 = this.getRequestObject(sendBSMRAM);
//                     this.sendForPolicyUpdationECS(reqObj2);
//                     HashMap set = new HashMap();
//                     set.put("PROCESSINSTANCENAME", processInstanceName);
//                     set.put("DATASLOTNAME", "uploadDocsFlag");
//                     set.put("DATASLOTVALUE", "open");
//                     RequestObject[] setReqObj = this.getRequestObject(set);
//                     this.setDataSlotValueECS(setReqObj);
//                     set.put("PROCESSINSTANCENAME", processInstanceName);
//                     set.put("DATASLOTNAME", "reviseReserveFlag");
//                     set.put("DATASLOTVALUE", "open");
//                     RequestObject[] setReqObj1 = this.getRequestObject(set);
//                     this.setDataSlotValueECS(setReqObj1);
//                     set.put("PROCESSINSTANCENAME", processInstanceName);
//                     set.put("DATASLOTNAME", "assignSPFlag");
//                     set.put("DATASLOTVALUE", "open");
//                     RequestObject[] setReqObj2 = this.getRequestObject(set);
//                     this.setDataSlotValueECS(setReqObj2);
//                     responseCode = "5000";
//                  } else {
//                     if (workStepName.equals("completeInitiatePayment")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: completeInitatepayment");
//                        if (computeLiability.equals("COMPUTELIABILITY") && !uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: COMPUTELIABILITY");
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to ComputeLiability ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeComputeLiability");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: COMPUTELIABILITY END");
//                           responseCode = "5000";
//                        }
//
//                        automatictasklog.info("ECS Savvion: sendToPreviousActivityECS Service:: COMPUTELIABILITY" + computeLiability + uploadDocument + policyUpdation + premiumCollectionUpdation);
//                        if (!computeLiability.equals("COMPUTELIABILITY") && uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADDOCUMENTS ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "NONE");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to completeUploadDocs ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADDOCUMENTS END");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && !uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICYPREMIUM ");
//                           setPolPrem.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolPrem.put("DATASLOTNAME", "policyUpdationType");
//                           setPolPrem.put("DATASLOTVALUE", "BOTH");
//                           setDSReqObj = this.getRequestObject(setPolPrem);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to policyUpdation and premiumCollection ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICYPREMIUM END");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && !uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICYUPDATION ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "BSM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to policyUpdation ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICYUPDATION END");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && !uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: PREMIUMCOLLECTIONUPDATION ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "RAM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to PremiumCollection ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: PREMIUMCOLLECTIONUPDATION END ");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICYPREMIUM ");
//                           setPolPrem.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolPrem.put("DATASLOTNAME", "policyUpdationType");
//                           setPolPrem.put("DATASLOTVALUE", "BOTH");
//                           setDSReqObj = this.getRequestObject(setPolPrem);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to policyUpdation and premiumCollection ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICYPREMIUM END");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICY ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "BSM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to policyUpdation ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICYPREMIUM ");
//                        }
//
//                        if (!computeLiability.equals("COMPUTELIABILITY") && uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPREMIUM ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "RAM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: sent Back from IntitatePayment to PremiumCollection ");
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPREMIUM END");
//                        }
//                     }
//
//                     if (workStepName.equals("completeComputeLiability")) {
//                        automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: completeComputeLiability");
//                        if (uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICYPREMIUM ");
//                           setPolPrem.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolPrem.put("DATASLOTNAME", "policyUpdationType");
//                           setPolPrem.put("DATASLOTVALUE", "BOTH");
//                           setDSReqObj = this.getRequestObject(setPolPrem);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICYPREMIUM ");
//                        }
//
//                        if (uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOAD ");
//                           setPolPrem.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolPrem.put("DATASLOTNAME", "policyUpdationType");
//                           setPolPrem.put("DATASLOTVALUE", "NONE");
//                           setDSReqObj = this.getRequestObject(setPolPrem);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOAD END ");
//                        }
//
//                        if (uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPREMIUM ");
//                           setPolPrem.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolPrem.put("DATASLOTNAME", "policyUpdationType");
//                           setPolPrem.put("DATASLOTVALUE", "BOTH");
//                           setDSReqObj = this.getRequestObject(setPolPrem);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPREMIUM END");
//                        }
//
//                        if (uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICY ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "BSM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: UPLOADPOLICY END ");
//                        }
//
//                        if (!uploadDocument.equals("completeUploadDocs") && policyUpdation.equals("POLICYUPDATION") && !premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICY ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "BSM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: POLICY END");
//                        }
//
//                        if (!uploadDocument.equals("completeUploadDocs") && !policyUpdation.equals("POLICYUPDATION") && premiumCollectionUpdation.equals("PREMIUMCOLLECTIONUPDATION")) {
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: PREMIUM ");
//                           setPolicy.put("PROCESSINSTANCENAME", processInstanceName);
//                           setPolicy.put("DATASLOTNAME", "policyUpdationType");
//                           setPolicy.put("DATASLOTVALUE", "RAM");
//                           setDSReqObj = this.getRequestObject(setPolicy);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           completeHashMap.put("USERID", userId);
//                           completeHashMap.put("WORKITEMNAME", workItemName);
//                           reqObj2 = this.getRequestObject(completeHashMap);
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service::" + completeHashMap);
//                           setDSValue.put("PROCESSINSTANCENAME", processInstanceName);
//                           setDSValue.put("DATASLOTNAME", "activityName");
//                           setDSValue.put("DATASLOTVALUE", "completeUploadDocs");
//                           setDSReqObj = this.getRequestObject(setDSValue);
//                           this.setDataSlotValueECS(setDSReqObj);
//                           this.completeInitiatePaymentECS(reqObj2);
//                           responseCode = "5000";
//                           automatictasklog.debug("ECS Savvion: sendToPreviousActivityECS Service:: PREMIUM END");
//                        }
//                     }
//                  }
//               } else {
//                  responseCode = "5047";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var38) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: sendToPreviousActivityECS Service ::::EXCEPTION " + var38);
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: sendToPreviousActivityECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject createSupervisorTaskECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion: createSupervisorTaskECS Service:::: START");
//      String processInstanceName = null;
//      String sessionId = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: createSupervisorTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: createSupervisorTaskECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String supervisorId = (String)hashMap.get("SUPERVISORID");
//         String claimWorkItem = (String)hashMap.get("WORKITEMNAME");
//         String claimNumber = (String)hashMap.get("CLAIMNUMBER");
//         String taskType = (String)hashMap.get("TASKTYPE");
//         String piName = this.propertiesECSSavvion.getProperty("ECSSupervisorInstanceName") + claimNumber + "-";
//         String ptName = this.propertiesECSSavvion.getProperty("ECSSupervisorPTName");
//         if (userId != null && !userId.equals("") && supervisorId != null && !supervisorId.equals("") && !claimWorkItem.equals("") && claimWorkItem != null && !claimNumber.equals("") && claimNumber != null && !taskType.equals("") && taskType != null) {
//            try {
//               sessionId = this.connectUser(userId);
//               if (!sessionId.equals("false")) {
//                  HashMap dsTypeMap = new HashMap();
//                  dsTypeMap.put("supervisorId", "STRING");
//                  dsTypeMap.put("claimWorkItem", "STRING");
//                  dsTypeMap.put("claimNumber", "STRING");
//                  dsTypeMap.put("taskType", "STRING");
//                  HashMap dsValues = new HashMap();
//                  dsValues.put("supervisorId", supervisorId);
//                  dsValues.put("claimWorkItem", claimWorkItem);
//                  dsValues.put("claimNumber", claimNumber);
//                  dsValues.put("taskType", taskType);
//                  Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//                  processInstanceName = this.createProcessInstance(sessionId, ptName, piName, this.propertiesECSSavvion.getProperty("priority"), resolvedDSValues);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//
//               this.disconnect(sessionId);
//            } catch (Exception var17) {
//               responseCode = "5050";
//               humantasklog.error("ECS Savvion:  createSupervisorTaskECS Service:::: Exception" + var17.getMessage());
//            }
//         } else {
//            responseCode = "5021";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultStringArray(new String[]{processInstanceName});
//      }
//
//      humantasklog.info("ECS Savvion: createSupervisorTaskECSService :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeSupervisorTaskECS(RequestObject[] reqObj) {
//      humantasklog.info("ECS Savvion:\tcompleteSupervisorTaskECS Service:::: START");
//      String[] processInstanceName = new String[5];
//      String sessionId = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeSupervisorTaskECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeSupervisorTaskECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//
//         try {
//            sessionId = this.connectUser(userId);
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               if (!sessionId.equals("false")) {
//                  this.completeWorkitem(sessionId, workItemName);
//                  responseCode = "5000";
//               } else {
//                  responseCode = "5030";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var10) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: completeSupervisorTaskECS Service:::: Exception" + var10.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      humantasklog.info("ECS Savvion:  completeSupervisorTaskECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getSupervisorTaskListECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getSupervisorTaskListECS Service::START::");
//      String sessionId = null;
//      QueryService qs = null;
//      QSWorkItemRS wirs = null;
//      String[] winames = new String[0];
//      new HashMap();
//      new WorkItemObject();
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      String userId = null;
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getSupervisorTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         try {
//            HashMap hashMap = this.getMap(reqObj);
//            automatictasklog.info("ECS Savvion: getSupervisorTaskListECS Service:::: INPUT VALUES " + hashMap);
//            userId = (String)hashMap.get("USERID");
//            if (!userId.equals("") && userId != null) {
//               sessionId = this.connectUser(userId);
//               Session sess = getBizLogicManager().getSession(sessionId);
//               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.propertiesECSSavvion.getProperty("ECSSupervisorPTName"));
//               qs = new QueryService(sess);
//               QSWorkItem qswi = qs.getWorkItem();
//               wifil.setFetchSize(0);
//               wirs = qswi.getAssignedList(wifil);
//               List wi = wirs.getSVOList();
//               winames = new String[wi.size()];
//
//               for(int i = 0; i < wi.size(); ++i) {
//                  winames[i] = ((QSWorkItemData)wi.get(i)).getName();
//               }
//
//               if (wi.size() == 0) {
//                  responseCode = "5042";
//               } else {
//                  responseCode = "5000";
//               }
//            } else {
//               winames = new String[]{"5001"};
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var16) {
//            responseCode = "5030";
//            automatictasklog.error("ECS Savvion: getSupervisorSPTaskListECS Service::Catch::" + var16.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(this.getWorkItemObjectArray(winames));
//      }
//
//      automatictasklog.info("ECS Savvion: getSupervisorTaskListECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject completeAllSupervisorTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: completeAllSupervisorTasksECS Service:::: START");
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      HashMap hm = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: completeAllSupervisorTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: completeAllSupervisorTasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String workItemName = (String)hashMap.get("WORKITEMNAME");
//         String[] items = new String[10];
//         String[] subitems = new String[10];
//         String[] subProcessInstanceNames = new String[10];
//         String[] subProcessInstanceWorkItemNames = new String[10];
//         String[] processInstancelist = new String[5];
//         String workStepName = null;
//         String processInstanceName = null;
//         String processInstanceNameInSubProcess = null;
//         String workItemNameInSubProcess = null;
//         String sessionId = null;
//         int noOfSubProcessInstances = 0;
//         int subPid = 0;
//         String pid = null;
//         String claimId = null;
//         String[] subPiName = new String[5];
//         String result = null;
//         String subWorkStepName = null;
//
//         try {
//            if (userId != null && !userId.equals("") && workItemName != null && !workItemName.equals("")) {
//               DBUtility dbutility = new DBUtility(this.SBM_HOME);
//               sessionId = this.connectUser(userId);
//               items = workItemName.split("::");
//               processInstanceName = items[0];
//               workStepName = items[1];
//               pid = items[2];
//               subProcessInstanceNames = getBizLogicManager().getProcessInstanceNameList(sessionId, this.propertiesECSSavvion.getProperty("ECSSupervisorPTName"));
//               noOfSubProcessInstances = subProcessInstanceNames.length;
//               if (noOfSubProcessInstances <= 0) {
//                  responseCode = "SUB_PROCESSINSTANCES_EMPTY";
//               } else {
//                  for(int k = 0; k < noOfSubProcessInstances; ++k) {
//                     automatictasklog.debug("ECS Savvion: completeAllSupervisorTasksECS Service:: " + subProcessInstanceNames[k]);
//                     hm.put("PROCESSINSTANCENAME", subProcessInstanceNames[k]);
//                     hm.put("DATASLOTNAME", "claimWorkItem");
//                     RequestObject[] reqo = this.getRequestObject(hm);
//                     ResponseObject reso = this.getDataSlotValueECS(reqo);
//                     String[] wiNameInsub = reso.getResultStringArray();
//                     workItemNameInSubProcess = wiNameInsub[0];
//                     subitems = workItemNameInSubProcess.split("::");
//                     processInstanceNameInSubProcess = subitems[0];
//                     subWorkStepName = subitems[1];
//                     automatictasklog.debug("completeAllSupervisorTasksECS processinstance name in subprocess :: " + processInstanceNameInSubProcess);
//                     if (processInstanceNameInSubProcess.equals(processInstanceName)) {
//                        processInstancelist = processInstanceNameInSubProcess.split("#");
//                        subPid = Integer.parseInt(processInstancelist[1]);
//                        subPiName = processInstanceNameInSubProcess.split("-");
//                        claimId = subPiName[2];
//                        automatictasklog.info("Values of sub are : ---------------" + subWorkStepName + "-----" + subPid + "----" + pid + "----" + subPiName + "----" + claimId);
//                        dbutility.insertECS_COMPLETEALLPI_SUB(subPid, processInstanceNameInSubProcess, pid, claimId, subWorkStepName, userId, userId);
//                        automatictasklog.debug("ECS Savvion: completeAllPaymentTasksECS Service::subProcessInstanceId::" + subProcessInstanceNames[k]);
//                        getBizLogicManager().removeProcessInstance(sessionId, subProcessInstanceNames[k]);
//                     }
//                  }
//
//                  responseCode = "5000";
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var30) {
//            responseCode = "5060";
//            automatictasklog.info("ECS Savvion: completeAllSupervisorTasksECS Service:::: Exception" + var30.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: completeAllSupervisorTasksECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject removeSPTasksECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion::removeAllSPTasksECS Service:::: START");
//      String[] processInstanceName = new String[5];
//      String sessionId = null;
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      new HashMap();
//      HashMap hm = new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: removeSPTasksECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: removeSPTasksECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//         String claimNumber = (String)hashMap.get("CLAIMNUMBER");
//         WorkItemObject[] workItemList = new WorkItemObject[50];
//         String[] processInstancelist = new String[5];
//         String workItemName = null;
//         String[] items = new String[10];
//         String[] var14 = new String[4];
//
//         try {
//            sessionId = this.connectUser(userId);
//            if (userId != null && !userId.equals("") && claimNumber != null && !claimNumber.equals("")) {
//               if (!sessionId.equals("false")) {
//                  hm.put("USERID", userId);
//                  RequestObject[] reqObj1 = this.getRequestObject(hm);
//                  ResponseObject resObj1 = this.getAssignedSPTaskListECS(reqObj1);
//                  workItemList = resObj1.getResultworkItemArray();
//                  if (workItemList.length == 0) {
//                     automatictasklog.info("ECS Savvion: viewPendingClaimsECS Service : 5044");
//                     responseCode = "5042";
//                  } else {
//                     String claimID = null;
//
//                     for(int i = 0; i < workItemList.length; ++i) {
//                        claimID = workItemList[i].getClaimNumber();
//                        workItemName = workItemList[i].getName();
//                        if (claimID.equals(claimNumber)) {
//                           getBizLogicManager().removeProcessInstance(sessionId, workItemName);
//                        }
//                     }
//
//                     responseCode = "SUCCESS";
//                  }
//               }
//            } else {
//               responseCode = "5021";
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var19) {
//            responseCode = "5060";
//            humantasklog.error("ECS Savvion: completeAllSupervisorTasksECS Service:::: Exception" + var19.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion::removeAllSPTasksECS Service :::: END " + responseCode);
//      return resObj;
//   }
//
//   public ResponseObject getClaimTaskListECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: getClaimTaskListECS Service:::: START");
//      String processInstanceId = null;
//      ResponseObject resObj = new ResponseObject();
//      String responseCode = null;
//      TaskObject[] taskStatus = new TaskObject[]{new TaskObject(), new TaskObject(), new TaskObject(), new TaskObject(), new TaskObject(), new TaskObject(), new TaskObject()};
//      int j = 0;
//      DBUtility dbutility = new DBUtility(this.SBM_HOME);
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getClaimTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getClaimTaskListECS Service:::: INPUT VALUES " + hashMap);
//         HashMap hm = new HashMap();
//         String[] result = new String[1];
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String[] processInstanceList = new String[2];
//         String sessionId = this.connectUser("ECSAdmin");
//
//         try {
//            if (!getBizLogicManager().isValidProcessInstance(sessionId, processInstanceName)) {
//               responseCode = "5033";
//            } else {
//               processInstanceList = processInstanceName.split("#");
//               result = dbutility.getClaimTaskListStatus(Integer.parseInt(processInstanceList[1]));
//
//               int i;
//               for(i = 0; i < result.length; i += 3) {
//                  result[i] = result[i].replace("[", "").replace("]", "").trim();
//                  if (result[i].equals("completePolicyUpdate")) {
//                     j = i + 1;
//                     result[j] = result[j].trim();
//                     if (result[j].equals("W_ACTIVATED")) {
//                        taskStatus[0].setName("POLICYUPDATION");
//                        taskStatus[0].setStatus("current");
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("future");
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("future");
//                        taskStatus[1].setName("PREMIUMCOLLECTIONUPDATION");
//                        taskStatus[1].setStatus("future");
//                        responseCode = "5000";
//                        break;
//                     }
//                  }
//               }
//
//               Date dt;
//               if (result[j].equals("W_COMPLETED")) {
//                  taskStatus[0].setName("POLICYUPDATION");
//                  taskStatus[0].setStatus("closed");
//                  result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                  dt = Date.valueOf(new String(result[j + 1]));
//                  taskStatus[0].setCompletedEndDate(dt);
//                  responseCode = "5000";
//               } else if (!result[j].equals("W_ACTIVATED")) {
//                  taskStatus[0].setName("POLICYUPDATION");
//                  taskStatus[0].setStatus("skipped");
//                  responseCode = "5000";
//               }
//
//               j = 0;
//
//               for(i = 0; i < result.length; i += 3) {
//                  result[i] = result[i].replace("[", "").replace("]", "").trim();
//                  if (result[i].equals("completePremiumCollectionUpdate")) {
//                     j = i + 1;
//                     result[j] = result[j].trim();
//                     if (result[j].equals("W_ACTIVATED")) {
//                        taskStatus[1].setName("PREMIUMCOLLECTIONUPDATION");
//                        taskStatus[1].setStatus("current");
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("future");
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("future");
//                        responseCode = "5000";
//                        break;
//                     }
//                  }
//               }
//
//               RequestObject[] reqo;
//               ResponseObject resObjClaimStatus;
//               WorkItemObject[] workItemObject;
//               HashMap requestMap;
//               if (result[j].equals("W_COMPLETED")) {
//                  requestMap = new HashMap();
//                  requestMap.put("PROCESSINSTANCEVALUE", processInstanceList[1]);
//                  reqo = this.getRequestObject(requestMap);
//                  resObjClaimStatus = this.getClaimStatusECS(reqo);
//                  workItemObject = resObjClaimStatus.getResultworkItemArray();
//
//                  for(i = 0; i < workItemObject.length; ++i) {
//                     if (workItemObject[i].getWorkStepName().equals("completePolicyUpdate")) {
//                        taskStatus[1].setName("PREMIUMCOLLECTIONUPDATION");
//                        taskStatus[1].setStatus("future");
//                        break;
//                     }
//
//                     taskStatus[1].setName("PREMIUMCOLLECTIONUPDATION");
//                     taskStatus[1].setStatus("closed");
//                     result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                     dt = Date.valueOf(new String(result[j + 1]));
//                     taskStatus[1].setCompletedEndDate(dt);
//                  }
//
//                  responseCode = "5000";
//               } else if (!result[j].equals("W_ACTIVATED")) {
//                  taskStatus[1].setName("PREMIUMCOLLECTIONUPDATION");
//                  taskStatus[1].setStatus("future");
//                  responseCode = "5000";
//               }
//
//               j = 0;
//
//               for(i = 0; i < result.length; i += 3) {
//                  result[i] = result[i].replace("[", "").replace("]", "").trim();
//                  if (result[i].equals("completeUploadDocsAndReserve")) {
//                     j = i + 1;
//                     result[j] = result[j].trim();
//                     if (result[j].equals("W_ACTIVATED")) {
//                        hm.put("PROCESSINSTANCENAME", processInstanceName);
//                        reqo = this.getRequestObject(hm);
//                        resObjClaimStatus = this.getTaskStatusECS(reqo);
//                        String[] result1 = resObjClaimStatus.getResultStringArray();
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("future");
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("future");
//                        if (result1[0].equals("open")) {
//                           taskStatus[2].setName("UPLOADDOCUMENTS");
//                           taskStatus[2].setStatus("current");
//                           responseCode = "5000";
//                        }
//
//                        if (result1[0].equals("close")) {
//                           taskStatus[2].setName("UPLOADDOCUMENTS");
//                           taskStatus[2].setStatus("closed");
//                           responseCode = "5000";
//                        }
//
//                        if (result1[1].equals("open")) {
//                           taskStatus[3].setName("REVISERESERVE");
//                           taskStatus[3].setStatus("current");
//                           responseCode = "5000";
//                        }
//
//                        if (result1[1].equals("close")) {
//                           taskStatus[3].setName("REVISERESERVE");
//                           taskStatus[3].setStatus("closed");
//                           responseCode = "5000";
//                        }
//
//                        if (result1[2].equals("open")) {
//                           taskStatus[4].setName("ASSIGNSERVICEPROVIDER");
//                           taskStatus[4].setStatus("current");
//                           responseCode = "5000";
//                        }
//
//                        if (result1[2].equals("close")) {
//                           taskStatus[4].setName("ASSIGNSERVICEPROVIDER");
//                           taskStatus[4].setStatus("closed");
//                           responseCode = "5000";
//                        }
//                        break;
//                     }
//                  }
//               }
//
//               if (result[j].equals("W_COMPLETED")) {
//                  taskStatus[2].setName("UPLOADDOCUMENTS");
//                  taskStatus[2].setStatus("closed");
//                  result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                  dt = Date.valueOf(new String(result[j + 1]));
//                  taskStatus[2].setCompletedEndDate(dt);
//                  taskStatus[3].setName("REVISERESERVE");
//                  taskStatus[3].setStatus("closed");
//                  result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                  Date dt1 = Date.valueOf(new String(result[j + 1]));
//                  taskStatus[3].setCompletedEndDate(dt1);
//                  taskStatus[4].setName("ASSIGNSERVICEPROVIDER");
//                  taskStatus[4].setStatus("closed");
//                  result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                  Date dt2 = Date.valueOf(new String(result[j + 1]));
//                  taskStatus[4].setCompletedEndDate(dt2);
//                  responseCode = "5000";
//               } else if (!result[j].equals("W_ACTIVATED")) {
//                  taskStatus[2].setName("UPLOADDOCUMENTS");
//                  taskStatus[2].setStatus("future");
//                  taskStatus[3].setName("REVISERESERVE");
//                  taskStatus[3].setStatus("future");
//                  taskStatus[4].setName("ASSIGNSERVICEPROVIDER");
//                  taskStatus[4].setStatus("future");
//                  responseCode = "5000";
//               }
//
//               j = 0;
//
//               for(i = 0; i < result.length; i += 3) {
//                  result[i] = result[i].replace("[", "").replace("]", "").trim();
//                  if (result[i].equals("completeComputeLiability")) {
//                     j = i + 1;
//                     result[j] = result[j].trim();
//                     if (result[j].equals("W_ACTIVATED")) {
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("current");
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("future");
//                        responseCode = "5000";
//                        break;
//                     }
//                  }
//               }
//
//               if (result[j].equals("W_COMPLETED")) {
//                  requestMap = new HashMap();
//                  requestMap.put("PROCESSINSTANCEVALUE", processInstanceList[1]);
//                  reqo = this.getRequestObject(requestMap);
//                  resObjClaimStatus = this.getClaimStatusECS(reqo);
//                  workItemObject = resObjClaimStatus.getResultworkItemArray();
//
//                  for(i = 0; i < workItemObject.length; ++i) {
//                     if (workItemObject[i].getWorkStepName().equals("completeInitiatePayment")) {
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("closed");
//                        result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                        dt = Date.valueOf(new String(result[j + 1]));
//                        taskStatus[5].setCompletedEndDate(dt);
//                     } else {
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("future");
//                     }
//                  }
//
//                  responseCode = "5000";
//               }
//
//               j = 0;
//
//               for(i = 0; i < result.length; i += 3) {
//                  result[i] = result[i].replace("[", "").replace("]", "").trim();
//                  if (result[i].equals("completeInitiatePayment")) {
//                     j = i + 1;
//                     result[j] = result[j].trim();
//                     if (result[j].equals("W_ACTIVATED")) {
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("current");
//                        taskStatus[5].setName("COMPUTELIABILITY");
//                        taskStatus[5].setStatus("closed");
//                        responseCode = "5000";
//                        break;
//                     }
//                  }
//               }
//
//               if (result[j].equals("W_COMPLETED")) {
//                  taskStatus[6].setName("INITIATEPAYMENT");
//                  hm = new HashMap();
//                  hm.put("PROCESSINSTANCEVALUE", processInstanceList[1]);
//                  reqo = this.getRequestObject(hm);
//                  resObjClaimStatus = this.getClaimStatusECS(reqo);
//                  workItemObject = new WorkItemObject[10];
//                  workItemObject = resObjClaimStatus.getResultworkItemArray();
//
//                  for(i = 0; i < workItemObject.length; ++i) {
//                     if (workItemObject[i].getWorkStepName().equals("completeCloseClaim")) {
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("closed");
//                        result[j + 1] = result[j + 1].replace("[", "").replace("]", "").trim();
//                        dt = Date.valueOf(new String(result[j + 1]));
//                        taskStatus[6].setCompletedEndDate(dt);
//                     } else {
//                        taskStatus[6].setName("INITIATEPAYMENT");
//                        taskStatus[6].setStatus("future");
//                     }
//                  }
//
//                  responseCode = "5000";
//               }
//            }
//
//            this.disconnect(sessionId);
//         } catch (Exception var20) {
//            responseCode = "5050";
//            automatictasklog.error("ECS Savvion: getClaimTaskListECS Service:::: Exception" + var20.getMessage());
//         }
//
//         resObj.setTaskStatusArray(taskStatus);
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: getClaimTaskListECS Service completed :::: END " + responseCode);
//      return resObj;
//   }
//
//   private HashMap getMap(RequestObject[] reqObj) {
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
//   private WorkItemObject[] getWorkItemObjectArray(String[] workItems) {
//      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START");
//      HashMap requestMap = new HashMap();
//      HashMap inputMap = new HashMap();
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
//                  woArray[i].setClaimNumber(result[2]);
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START3");
//                  woArray[i].setType(getBizLogicManager().getProcessTemplateName(sessionId, processInstanceName));
//                  automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::START4");
//                  if (woArray[i].getType().equals("RGICL_ECS_FLOW")) {
//                     requestMap.put("PROCESSINSTANCENAME", processInstanceName);
//                     requestMap.put("DATASLOTNAME", "CMEscCounter");
//                     RequestObject[] reqo1 = this.getRequestObject(requestMap);
//                     ResponseObject reso1 = this.getDataSlotValueECS(reqo1);
//                     wiNameInsub = reso1.getResultStringArray();
//                     String cmeCounter = wiNameInsub[0];
//                     woArray[i].setCMEscCounter(cmeCounter);
//                     inputMap.put("PROCESSINSTANCENAME", processInstanceName);
//                     inputMap.put("DATASLOTNAME", "BSMEscCounter");
//                     RequestObject[] reqo2 = this.getRequestObject(inputMap);
//                     ResponseObject reso2 = this.getDataSlotValueECS(reqo2);
//                     wiNameInsub = reso2.getResultStringArray();
//                     String bsmeCounter = wiNameInsub[0];
//                     woArray[i].setBSMEscCounter(bsmeCounter);
//                  }
//               }
//
//               automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END FOR " + i + "::" + workItems[i]);
//            }
//         } else {
//            automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END  WorkItemObject Array :: " + woArray);
//         }
//
//         this.disconnect(sessionId);
//      } catch (Exception var19) {
//         automatictasklog.error("ECS Savvion:getWorkItemObjectArray Utility Service::::FAIULIRE EXCEPTION ");
//      }
//
//      automatictasklog.debug("ECS Savvion:getWorkItemObjectArray Utility Service::::END");
//      return woArray;
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
//   private void printUserRoleObject(UserRoleObject[] uro) {
//      automatictasklog.debug("ECS Savvion:printUserRoleObject Utility Service::::START");
//
//      try {
//         automatictasklog.info("ECS printUserRoleObject  Service : :" + uro.length);
//
//         for(int i = 0; i < uro.length; ++i) {
//            automatictasklog.info("ECS printUserRoleObject  Service : :" + uro[i].getRole() + "::" + uro[i].getLocation() + "::" + uro[i].getLOB());
//         }
//      } catch (Exception var3) {
//         automatictasklog.error("ECS Savvion:printUserRoleObject Utility Service::::FAIULIRE EXCEPTION ");
//      }
//
//      automatictasklog.debug("ECS printUserRoleObject Utility Service::::END");
//   }
//
//   public ResponseObject getCommonInboxTaskListECS(RequestObject[] reqObj) {
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: getCommonInboxTaskObjects Service:::: START");
//      new ArrayList();
//      new ArrayList();
//      String taskListArray = null;
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String[] taskListECS = new String[100];
//      String location = null;
//      String role = null;
//      String lob = null;
//      String responseCode = null;
//      ResponseObject resObj = new ResponseObject();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getCommonInboxTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.debug("ECS Savvion: getCommonInboxTaskListECS Service:::: INPUT VALUES " + hashMap);
//         String TPAFlag = (String)hashMap.get("TPAFLAG");
//         UserRoleObject[] uro = (UserRoleObject[])hashMap.get("USERROLEOBJECT");
//         ArrayList locationArrayList = new ArrayList();
//         ArrayList roleArrayList = new ArrayList();
//         ArrayList lobArrayList = new ArrayList();
//         this.printUserRoleObject(uro);
//         int uroLength = 0;
//
//         try {
//            for(int i = 0; i < uro.length; ++i) {
//               location = uro[i].getLocation();
//               role = uro[i].getRole();
//               lob = uro[i].getLOB();
//               automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::values " + location + "::" + role + "::" + lob);
//               if (location != null && role != null && lob != null) {
//                  if (location.length() != 0) {
//                     locationArrayList.add(location);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::location " + location);
//                  }
//
//                  if (role.length() != 0 && roleArrayList.size() <= 1) {
//                     roleArrayList.add(role);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::role " + role);
//                  }
//
//                  if (lob.length() != 0) {
//                     lobArrayList.add(lob);
//                     automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service::::lob " + lob);
//                  }
//
//                  if (location.length() != 0 && role.length() != 0 && lob.length() != 0) {
//                     ++uroLength;
//                  }
//               }
//            }
//
//            String[] locationList = new String[locationArrayList.size()];
//            String[] roleList = new String[roleArrayList.size()];
//            String[] lobList = new String[lobArrayList.size()];
//            locationArrayList.toArray(locationList);
//            roleArrayList.toArray(roleList);
//            lobArrayList.toArray(lobList);
//            automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service:::: RESULT" + locationList.length + " :: " + roleList.length + " :: " + lobList.length + " ::uroLength " + uroLength);
//            if (uro.length != 0 && TPAFlag != null && !TPAFlag.equals("") && locationList.length != 0 && roleList.length != 0 && lobList.length != 0) {
//               automatictasklog.debug("ECS Savvion: getCommonInboxTaskListUtility Service:::: RESULT 2:" + locationList.length + " :: " + roleList.length + " :: " + lobList.length + "::" + uroLength);
//               if (uroLength != 0) {
//                  ArrayList resultTaskList = db.getCommonInboxTaskObjects(roleList, locationList, lobList, TPAFlag);
//                  automatictasklog.info("ECS Savvion: getCommonInboxTaskListUtility Service:::: DB Call::resultTaskList" + resultTaskList);
//                  if (resultTaskList != null && resultTaskList.size() != 0) {
//                     automatictasklog.info("ECS Savvion: getCommonInboxTaskListUtility Service::::resultTaskList.size" + resultTaskList.size());
//                     workItemObject = this.getResultWorkitems(resultTaskList);
//                     automatictasklog.info("ECS Savvion: getCommonInboxTaskListUtility Service::::workItemObject :" + workItemObject);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5041";
//                  }
//               } else {
//                  responseCode = "5021";
//               }
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var23) {
//            automatictasklog.error("ECS Savvion: getCommonInboxTaskListUtilityECS Service:::: Exception FAILURE_EXCEPTION " + var23.getMessage());
//            responseCode = "5050";
//         }
//
//         resObj.setResponseCode(responseCode);
//         resObj.setResultworkItemArray(workItemObject);
//      }
//
//      automatictasklog.info("ECS Savvion: getCommonInboxTaskObjects Service::::END " + responseCode);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public ResponseObject getAssignedTaskListECS(RequestObject[] reqObj) {
//      automatictasklog.info("==================================================================================================");
//      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service:::: START");
//      String sessionId = null;
//      String sessionId1 = null;
//      DBUtility db = new DBUtility(this.SBM_HOME);
//      String taskList = null;
//      String responseCode = null;
//      String taskListArray = "5042";
//      String[] responseList = (String[])null;
//      ResponseObject resObj = new ResponseObject();
//      new ArrayList();
//      WorkItemObject[] workItemObject = (WorkItemObject[])null;
//      new HashMap();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: getAssignedTaskListECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: getAssignedTaskListECS Service:::: INPUT VALUES " + hashMap);
//         String userId = (String)hashMap.get("USERID");
//
//         try {
//            if (userId != null && !userId.equals("")) {
//               if (this.checkUserECS(userId, "ECS")) {
//                  ArrayList resultTaskList = db.getMyInboxTaskObjects(userId);
//                  responseCode = "5000";
//                  automatictasklog.debug("ECS Savvion: getAssignedTaskList Service::resultTaskList " + resultTaskList);
//                  if (resultTaskList != null && resultTaskList.size() != 0) {
//                     automatictasklog.info("ECS Savvion: getAssignedTaskList Service:: Objects Count " + resultTaskList.size());
//                     workItemObject = this.getResultWorkitems(resultTaskList);
//                     automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::workItemObject :" + workItemObject);
//                     resObj.setResultworkItemArray(workItemObject);
//                     responseCode = "5000";
//                  } else {
//                     responseCode = "5042";
//                     automatictasklog.info("ECS Savvion: getAssignedTaskList Service::::resultTaskList.size" + resultTaskList.size());
//                  }
//               } else {
//                  responseCode = "5002";
//               }
//            } else {
//               responseCode = "5001";
//            }
//         } catch (Exception var15) {
//            automatictasklog.error("ECS Savvion: getAssignedTaskList Service::Error Message::" + var15.getMessage());
//            responseCode = "5030";
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: getAssignedTaskListObjects Service :::: END" + responseCode);
//      automatictasklog.info("==================================================================================================");
//      return resObj;
//   }
//
//   public long getObjectSize(Object obj) {
//      File f = null;
//      long length = 0L;
//      String fileName = null;
//
//      try {
//         fileName = String.valueOf(obj.hashCode());
//         f = new File("d://SavvionObjects/" + fileName + ".txt");
//         FileOutputStream fs = new FileOutputStream(f);
//         ObjectOutputStream oos = new ObjectOutputStream(fs);
//         oos.writeObject(obj);
//         oos.close();
//         fs.close();
//      } catch (Exception var8) {
//         automatictasklog.info("ECS getObjectSize  Service : :" + var8);
//      }
//
//      return f.length();
//   }
//
//   public WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
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
//   public ResponseObject updateTPAFlagECS(RequestObject[] reqObj) {
//      automatictasklog.info("ECS Savvion: updateTPAFlagECS Service :::: START");
//      String sessionId = null;
//      String result = null;
//      String responseCode = null;
//      new HashMap();
//      ResponseObject resObj = new ResponseObject();
//      if (reqObj == null) {
//         resObj.setResponseCode("5070");
//         automatictasklog.error("ECS Savvion: updateTPAFlagECS Service:: REQUEST_OBJECT_IS_NULL");
//      } else {
//         HashMap hashMap = this.getMap(reqObj);
//         automatictasklog.info("ECS Savvion: updateTPAFlagECS Service:::: INPUT VALUES " + hashMap);
//         String processInstanceName = (String)hashMap.get("PROCESSINSTANCENAME");
//         String tpaFlag = (String)hashMap.get("TPAFLAG");
//
//         try {
//            if (processInstanceName != null && !processInstanceName.equals("") && tpaFlag != null && !tpaFlag.equals("")) {
//               sessionId = connectECSADMIN();
//               HashMap dsTypeMap = new HashMap();
//               dsTypeMap.put("TPAFlag", "STRING");
//               dsTypeMap.put("CMAcquiredFlag", "STRING");
//               HashMap dsValues = new HashMap();
//               dsValues.put("TPAFlag", tpaFlag);
//               dsValues.put("CMAcquiredFlag", "false");
//               Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
//               this.setProcessDataslotValues(sessionId, processInstanceName, resolvedDSValues);
//               HashMap setData = new HashMap();
//               String[] pid = processInstanceName.split("#");
//               setData.put("PROCESSINSTANCEVALUE", pid[1]);
//               RequestObject[] reqObjClaimStatus = this.getRequestObject(setData);
//               new ResponseObject();
//               ResponseObject resObjClaimStatus = this.getClaimStatusECS(reqObjClaimStatus);
//               automatictasklog.info("ECS Savvion: updateTPAFlagECS Service :::: " + resObjClaimStatus);
//               WorkItemObject[] workItemObject = resObjClaimStatus.getResultworkItemArray();
//               String[] totalWorkItems = new String[workItemObject.length];
//
//               int i;
//               for(i = 0; i < workItemObject.length; ++i) {
//                  totalWorkItems[i] = workItemObject[i].getPiName() + "::" + workItemObject[i].getWorkStepName() + "::" + workItemObject[i].getPerformer();
//               }
//
//               if (totalWorkItems.length >= 1) {
//                  for(i = 0; i < totalWorkItems.length; ++i) {
//                     String[] items = totalWorkItems[i].split("::");
//                     if (items[1].equals("completeUploadDocsAndReserve")) {
//                        String completeUploadDocsWorkItem = totalWorkItems[i];
//                        this.completeWorkitem(connectECSADMIN(), completeUploadDocsWorkItem);
//                     }
//                  }
//               }
//
//               responseCode = "5000";
//            } else {
//               responseCode = "5021";
//            }
//         } catch (Exception var21) {
//            automatictasklog.error("ECS Savvion: updateTPAFlagECS Service::" + var21.getMessage());
//         }
//
//         resObj.setResponseCode(responseCode);
//      }
//
//      automatictasklog.info("ECS Savvion: updateTPAFlagECS Service ::::END " + responseCode);
//      return resObj;
//   }
//
//   public String addUserECS(RequestObject[] reqObj) {
//      String result = "";
//      String userId = (String)this.getMap(reqObj).get("USERID");
//
//      try {
//         Realm r = UserManager.getDefaultRealm();
//         boolean b = r.addUser(userId);
//         if (b) {
//            result = "added";
//            User sbmUser = r.getUser(userId);
//            sbmUser.setAttribute("password", userId);
//         } else {
//            result = "Not Added";
//         }
//      } catch (Exception var7) {
//      }
//
//      return result;
//   }
//}
