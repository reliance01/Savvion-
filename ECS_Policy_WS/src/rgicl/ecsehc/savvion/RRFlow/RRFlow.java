package rgicl.ecsehc.savvion.RRFlow;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rgicl.ecsehc.savvion.policy.objectmodel.RequestObject;
import rgicl.ecsehc.savvion.policy.objectmodel.ResponseObject;
import rgicl.ecsehc.savvion.policy.objectmodel.WorkItemObject;
import rgicl.ecsehc.savvion.policy.util.UtilClass;

public class RRFlow {
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

   public RRFlow(String propertiesPath) {
   }

   public ResponseObject UpdateAndCompleteRRFlowTask(RequestObject[] reqObj) {
      String methodName = "UpdateAndCompleteRRFlowTask";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::::START");
      String sessionId = null;
      String responseCode = null;
      String responseMessage = null;
      HashMap hashMap = new HashMap();
      ResponseObject resObj = new ResponseObject();
      WorkItemObject[] wiObjects = null;
      String workItemCaseStatus = "Completed";
      String nextWorkItemCaseStatus = "New";
      boolean isInstanceCompleted = false;
      ResponseObject res = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      Realm usr = null;
      boolean isExistingUser = false;
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
         String action = (String)hashMap.get("ACTION");
         String userIdToAdd = (String)hashMap.get("USERIDTOCREATE");
         String[] groupNamesToAdd = (String[])hashMap.get("GROUPNAMESTOADD");
         String[] groupNamesToRemove = (String[])hashMap.get("GROUPNAMESTOREMOVE");

         try {
            Exception e;
            try {
               BLServer blserver = null;
               Session blsession = null;
               Session blsessionECSAdmin = null;
               usr = UserManager.getDefaultRealm();
               User sbmUser = usr.getUser(userIdToAdd);
               if (sbmUser != null) {
                  isExistingUser = true;
               }

               if (processInstanceName != null && !processInstanceName.equals("") && workItemName != null && !workItemName.equals("") && !uc.checkNull(workItemName)) {
                  if (userId != null && !userId.equals("")) {
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect(userId, uc.getUserPasswordECS(userId));
                     ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
                     blsessionECSAdmin = blserver.connect("ECSAdmin", uc.getUserPasswordECS("ECSAdmin"));
                     WorkItem wi = blserver.getWorkItem(blsessionECSAdmin, workItemName);
                     if (!WorkItem.isExist(blsessionECSAdmin, wi.getID())) {
                        responseCode = "5060";
                        responseMessage = "WINAME_NOT_FOUND";
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:: work item not found");
                     } else {
                        if (action != null && (action.equals("AddUser") || action.equals("EditUser")) && !uc.checkNull(hashMap.get("ISAPPOWNERACCEPTED")) && hashMap.get("ISAPPOWNERACCEPTED").toString().equals("Approve")) {
                           res = this.addOrRemoveUserECS(reqObj);
                           responseCode = res.getResponseCode();
                           responseMessage = res.getResponseMessage();
                        } else {
                           res.setResponseCode("5000");
                        }

                        if (action == null || res.getResponseCode() == "5000") {
                           HashMap hm = new HashMap();
                           if (!uc.checkNull(hashMap.get("ISRRMAKERACCEPTED"))) {
                              workItemCaseStatus = hashMap.get("ISRRMAKERACCEPTED").toString();
                              hm.put("isMakerAccepted", workItemCaseStatus);
                           }

                           String supervisor;
                           if (!uc.checkNull(hashMap.get("RRMAKERREMARKS"))) {
                              supervisor = (String)pi.getDataSlotValue("MakerRemarks");
                              if (supervisor != null && !supervisor.equalsIgnoreCase("<null>")) {
                                 supervisor = supervisor + "|" + hashMap.get("RRMAKERREMARKS").toString();
                              } else {
                                 supervisor = hashMap.get("RRMAKERREMARKS").toString();
                              }

                              hm.put("MakerRemarks", supervisor);
                           }

                           if (!uc.checkNull(hashMap.get("ISAPPOWNERACCEPTED"))) {
                              workItemCaseStatus = hashMap.get("ISAPPOWNERACCEPTED").toString();
                              hm.put("IsAppOwnerAccepted", workItemCaseStatus);
                           }

                           if (!uc.checkNull(hashMap.get("APPOWNERREMARKS"))) {
                              supervisor = (String)pi.getDataSlotValue("AppOwnerRemarks");
                              if (supervisor != null && !supervisor.equalsIgnoreCase("<null>")) {
                                 supervisor = supervisor + "|" + hashMap.get("APPOWNERREMARKS").toString();
                              } else {
                                 supervisor = hashMap.get("APPOWNERREMARKS").toString();
                              }

                              hm.put("AppOwnerRemarks", supervisor);
                           }

                           if (!uc.checkNull(hashMap.get("USERTYPE"))) {
                              hm.put("UserType", hashMap.get("USERTYPE").toString());
                           }

                           if (!uc.checkNull(hashMap.get("LOGINID"))) {
                              hm.put("LoginId", hashMap.get("LOGINID").toString());
                           }

                           if (!uc.checkNull(hashMap.get("CREATEDOREDITEDUSERNAME"))) {
                              hm.put("CreatedorEditedUserName", hashMap.get("CREATEDOREDITEDUSERNAME").toString());
                           }

                           if (!uc.checkNull(hashMap.get("CREATEDOREDITEDUSERROLE"))) {
                              hm.put("CreatedorEditedUserRole", hashMap.get("CREATEDOREDITEDUSERROLE").toString());
                           }

                           if (!uc.checkNull(hashMap.get("SUPERVISORID"))) {
                              hm.put("SupervisorId", hashMap.get("SUPERVISORID").toString());
                           }

                           if (blsession == null) {
                              responseCode = "5030";
                              responseMessage = "USER_ID_INVALID";
                           } else {
                              pi.updateSlotValue(hm);
                              pi.save();
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

                              try {
                                 blserver.getProcessInstance(blsession, processInstanceName);
                              } catch (Exception var49) {
                                 isInstanceCompleted = true;
                              }

                              if (workItemCaseStatus.equalsIgnoreCase("Completed") && !uc.checkNull(hashMap.get("SUPERVISORID"))) {
                                 nextWorkItemCaseStatus = "Locked";
                              } else if (workItemCaseStatus.equalsIgnoreCase("Completed") && uc.checkNull(hashMap.get("SUPERVISORID"))) {
                                 nextWorkItemCaseStatus = "New";
                              } else if (workItemCaseStatus.equalsIgnoreCase("SendBack")) {
                                 nextWorkItemCaseStatus = "Revised";
                              } else if (workItemCaseStatus.equalsIgnoreCase("Approve")) {
                                 nextWorkItemCaseStatus = "New";
                              } else if (workItemCaseStatus.equalsIgnoreCase("Reject")) {
                                 nextWorkItemCaseStatus = "";
                              }

                              wiObjects = uc.getNextAvailableTaskList(processInstanceName, nextWorkItemCaseStatus, workItemName, (String)null, (String)hashMap.get("SUPERVISORID"), (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null);
                              if (wiObjects != null && wiObjects.length != 0) {
                                 uc.checkUserNumbersInGroup(wiObjects);
                              }

                              if (!uc.checkNull(hashMap.get("SUPERVISORID"))) {
                                 automatictasklog.info("ECS Savvion: " + methodName + " Assigning task to supervisor");
                                 supervisor = (String)hashMap.get("SUPERVISORID");
                                 RequestObject[] reqObj1 = new RequestObject[]{new RequestObject(), null};
                                 reqObj1[0].setKey("WORKITEMNAME");
                                 reqObj1[0].setValue(wiObjects[0].getName());
                                 reqObj1[1] = new RequestObject();
                                 reqObj1[1].setKey("TOBEASSIGNEDUSERID");
                                 reqObj1[1].setValue(supervisor);
                                 automatictasklog.info("ECS Savvion: " + methodName + " :: workitem " + wiObjects[0].getName() + " :: supervisorId is ::  " + supervisor);
                                 ResponseObject resp = uc.assignOrKeepTask(reqObj1);
                                 if (resp.getResponseCode() == "5000") {
                                    automatictasklog.info("ECS Savvion: " + methodName + " :: Assigned task to supervisor ::  " + supervisor);
                                    wiObjects[0].setPerformer(supervisor);
                                    wiObjects[0].setCaseStatus("Locked");
                                 } else {
                                    automatictasklog.info("ECS Savvion: " + methodName + " :: unable to assign task to supervisor ::  " + supervisor);
                                    responseCode = "5002";
                                    responseMessage = "USER_NOT_AUTHORIZED";
                                 }
                              }
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
            } catch (Exception var50) {
               e = var50;
               responseCode = "5034";
               responseMessage = "DS_NAME_INVALID";
               var50.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service::::Catch Exception:::" + var50.getMessage());
               if (var50.getMessage().contains("Incorrect userName/password for")) {
                  try {
                     String userid = (String)hashMap.get("USERID");
                     if (e.getMessage().contains("ECSAdmin")) {
                        userid = "ECSAdmin";
                     }

                     String pass = uc.getUserPasswordECS(userid);
                     PService p = PService.self();
                     pass = p.encrypt(pass);
                     automatictasklog.info("ECS Savvion :: " + methodName + " userId " + userid + " encrypted " + pass);
                  } catch (Exception var48) {
                     automatictasklog.error("ECS Savvion :: " + methodName + " :: Incorrect userName Exception " + var48);
                  }
               }
            }
         } finally {
            if (responseCode != null && responseCode != "5000") {
               try {
                  if (action.equals("AddUser") && responseCode != null && responseCode != "5000" && res != null && res.getResponseCode() == "5000") {
                     usr = UserManager.getDefaultRealm();
                     User sbmUser = usr.getUser(userIdToAdd);
                     boolean reslt = false;
                     if (sbmUser != null && !isExistingUser) {
                        reslt = usr.removeUser(userIdToAdd);
                     }

                     if (sbmUser != null && !reslt) {
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + userIdToAdd + "Can't be removed");
                     }
                  }

                  if (action.equals("EditUser") && responseCode != null && responseCode != "5000" && res != null && res.getResponseCode() == "5000") {
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:: reverting the action" + action);
                     this.revertTransaction(userIdToAdd, groupNamesToRemove, groupNamesToAdd, (String)null, groupNamesToRemove.length, groupNamesToAdd.length);
                  }
               } catch (Exception var47) {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service::::Catch Exception while reverting action :::" + action + " :::: " + var47);
               }
            }

            try {
               uc.disconnect((String)sessionId);
            } catch (Exception var46) {
               var46.printStackTrace();
               automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception while disconnecting user ::" + var46.getMessage());
            }

         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         resObj.setResultworkItemArray(wiObjects);
         resObj.setWorkItemCaseStatus(workItemCaseStatus);
         resObj.setInstanceCompleted(isInstanceCompleted);
      }

      automatictasklog.info("ECS Savvion: " + methodName + " Service::::END " + responseCode + " Response Message:::::" + responseMessage + " for the WorkItemName " + (String)hashMap.get("WORKITEMNAME"));
      return resObj;
   }

   public ResponseObject addOrRemoveUserECS(RequestObject[] reqObj) {
      String methodName = "addOrRemoveUserECS";
      ResponseObject resObj = new ResponseObject();
      String responseCode = null;
      String responseMessage = null;
      String[] groupNamesToAdd = null;
      String[] groupNamesToRemove = null;
      new HashMap();
      boolean canAdd = true;
      boolean canRemove = false;
      boolean isGroupExist = false;
      User sbmUser = null;
      Realm usr = null;
      boolean isExistingUser = false;
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.debug("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String userId = (String)uc.getMap(reqObj).get("USERIDTOCREATE");
         String firstName = (String)uc.getMap(reqObj).get("FIRSTNAME");
         String lastName = (String)uc.getMap(reqObj).get("LASTNAME");
         String emailId = (String)uc.getMap(reqObj).get("EMAIL");
         String action = (String)uc.getMap(reqObj).get("ACTION");
         if (!uc.checkNull(hashMap.get("GROUPNAMESTOADD"))) {
            groupNamesToAdd = (String[])hashMap.get("GROUPNAMESTOADD");
            automatictasklog.info("ECS Savvion: addOrRemoveUserECS Service:::: input group name to ADD  are ::" + Arrays.toString(groupNamesToAdd));
         }

         if (!uc.checkNull(hashMap.get("GROUPNAMESTOREMOVE"))) {
            groupNamesToRemove = (String[])hashMap.get("GROUPNAMESTOREMOVE");
            automatictasklog.info("ECS Savvion: addOrRemoveUserECS Service:::: input group name to REMOVE  are ::" + Arrays.toString(groupNamesToRemove));
         }

         if (userId != null && !userId.equals("") && action != null && !action.equals("")) {
            if (groupNamesToAdd != null) {
               isGroupExist = this.isGroupExist(groupNamesToAdd);
               if (!isGroupExist) {
                  automatictasklog.info("ECS Savvion: addOrRemoveUserECS Service:::: input group name to add does not exist in Savvion");
                  responseCode = "5025";
                  responseMessage = "GROUPNAME_NOT_EXIST";
               }
            }

            RequestObject[] reqObjAddGroup;
            ResponseObject addResponse;
            if (action.equals("AddUser")) {
               try {
                  boolean result = false;
                  usr = UserManager.getDefaultRealm();
                  sbmUser = usr.getUser(userId);
                  if (sbmUser == null) {
                     result = usr.addUser(userId);
                     if (result) {
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User created in Savvion");
                        sbmUser = usr.getUser(userId);
                        sbmUser.setAttribute("password", userId);
                        sbmUser.setAttribute("firstname", firstName);
                        sbmUser.setAttribute("lastname", lastName);
                        sbmUser.setAttribute("email", emailId);
                     } else {
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User " + userId + " is not created in Savvion");
                        responseCode = "5024";
                        responseMessage = "USER_CREATION_FAILED";
                     }
                  } else {
                     result = true;
                     isExistingUser = true;
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: User already exist in savvion so proceed with mapping");
                  }

                  if (!result) {
                     responseCode = "5024";
                     responseMessage = "USER_CREATION_FAILED";
                  } else {
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user to ECS group Start");
                     reqObjAddGroup = new RequestObject[]{new RequestObject()};
                     reqObjAddGroup[0].setKey("USERID");
                     reqObjAddGroup[0].setValue(userId);
                     new ResponseObject();
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: map user to ECS group" + reqObjAddGroup[0].getKey() + " " + reqObjAddGroup[0].getValue());
                     addResponse = this.mapUserToECSGroup(reqObjAddGroup);
                     responseCode = addResponse.getResponseCode();
                     responseMessage = addResponse.getResponseMessage();
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user added to ECS group successfully and responseCode is " + responseCode);
                     if (groupNamesToAdd == null || !isGroupExist || responseCode != "5000" && responseCode != "5022") {
                        if (groupNamesToAdd == null) {
                           responseCode = "5000";
                           responseMessage = "SUCCESS";
                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: groupNamesToAdd is NULL so setting responseCode as SUCCESS");
                        } else if (groupNamesToAdd != null || isGroupExist || responseCode != "5000") {
                           responseCode = "5023";
                           responseMessage = "USER_NOT_MAPPED";
                           if (!isGroupExist) {
                              responseCode = "5025";
                              responseMessage = "GROUPNAME_NOT_EXIST";
                           }

                           try {
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: trying to delete " + userId + " form savvion");
                              User user = usr.getUser(userId);
                              boolean process = false;
                              if (user != null && !isExistingUser) {
                                 process = usr.removeUser(userId);
                                 if (process) {
                                    automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + userId + " deleted form savvion");
                                 }
                              } else {
                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + userId + " is an Existing User, so no need to delete form savvion");
                              }
                           } catch (Exception var30) {
                              automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured while deleting the user:::" + var30.getMessage());
                           }
                        }
                     } else {
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user loop START");
                        RequestObject[] reqObjMapToGroup = new RequestObject[]{new RequestObject(), null};
                        reqObjMapToGroup[0].setKey("USERID");
                        reqObjMapToGroup[0].setValue(userId);
                        reqObjMapToGroup[1] = new RequestObject();
                        reqObjMapToGroup[1].setKey("GROUPNAME");
                        new ResponseObject();
                        int addListLength = groupNamesToAdd.length;

                        for(int i = 0; i < addListLength; ++i) {
                           reqObjMapToGroup[1].setValue(groupNamesToAdd[i]);
                           ResponseObject response = this.mapUserToGroup(reqObjMapToGroup);
                           responseCode = response.getResponseCode();
                           responseMessage = response.getResponseMessage();
                           if (responseCode != "5000" && !isExistingUser) {
                              try {
                                 usr.removeUser(userId);
                              } catch (Exception var29) {
                                 automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception occured while deleting the user:::" + var29.getMessage());
                              }

                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
                              break;
                           }
                        }
                     }
                  }
               } catch (Exception var31) {
                  automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var31.getMessage());
                  responseCode = "5024";
                  responseMessage = "USER_CREATION_FAILED";
               }
            } else {
               try {
                  User user = UserManager.getUser(userId);
                  if (user == null) {
                     responseCode = "5030";
                     responseMessage = "USER_ID_INVALID";
                     automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + "Response code is ::" + responseCode + " ::Respones Message is :: " + responseMessage);
                  } else {
                     user.setAttribute("firstname", firstName);
                     user.setAttribute("lastname", lastName);
                     user.setAttribute("email", emailId);
                     int i;
                     if (groupNamesToRemove != null) {
                        int removeListLength = groupNamesToRemove.length;

                        for(int i = 0; i < removeListLength; ++i) {
                           if (!uc.checkUserGroup(userId, groupNamesToRemove[i])) {
                              canRemove = false;
                              canAdd = false;
                              break;
                           }

                           canRemove = true;
                        }

                        if (canRemove) {
                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Action is " + action + " and REMOVE user from group START");
                           RequestObject[] reqObjRemoveGroup = new RequestObject[]{new RequestObject(), null};
                           reqObjRemoveGroup[0].setKey("USERID");
                           reqObjRemoveGroup[0].setValue(userId);
                           reqObjRemoveGroup[1] = new RequestObject();
                           reqObjRemoveGroup[1].setKey("GROUPNAME");
                           new ResponseObject();
                           removeListLength = groupNamesToRemove.length;

                           for(i = 0; i < removeListLength; ++i) {
                              reqObjRemoveGroup[1].setValue(groupNamesToRemove[i]);
                              automatictasklog.info("ECS Savvion: " + methodName + " Service:::: user groups are " + groupNamesToRemove[i]);
                              ResponseObject removeResponse = this.removeUserFromGroup(reqObjRemoveGroup);
                              responseCode = removeResponse.getResponseCode();
                              responseMessage = removeResponse.getResponseMessage();
                              if (responseCode != "5000") {
                                 canAdd = false;
                                 this.revertTransaction(userId, groupNamesToRemove, (String[])null, "Remove", i, 0);
                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Remove loop :::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
                                 break;
                              }
                           }
                        } else {
                           responseCode = "5023";
                           responseMessage = "USER_NOT_MAPPED";
                        }
                     }

                     if (groupNamesToAdd != null && canAdd) {
                        if (isGroupExist) {
                           automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Action is " + action + " and ADD user to group START");
                           reqObjAddGroup = new RequestObject[]{new RequestObject(), null};
                           reqObjAddGroup[0].setKey("USERID");
                           reqObjAddGroup[0].setValue(userId);
                           reqObjAddGroup[1] = new RequestObject();
                           reqObjAddGroup[1].setKey("GROUPNAME");
                           new ResponseObject();
                           int addListLength = groupNamesToAdd.length;

                           for(i = 0; i < addListLength; ++i) {
                              reqObjAddGroup[1].setValue(groupNamesToAdd[i]);
                              addResponse = this.mapUserToGroup(reqObjAddGroup);
                              responseCode = addResponse.getResponseCode();
                              responseMessage = addResponse.getResponseMessage();
                              if (responseCode != "5000") {
                                 this.revertTransaction(userId, groupNamesToRemove, groupNamesToAdd, "Add", groupNamesToRemove.length, i);
                                 automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add loop :::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
                                 break;
                              }
                           }
                        } else {
                           responseCode = "5025";
                           responseMessage = "GROUPNAME_NOT_EXIST";
                        }
                     }

                     if (groupNamesToAdd == null && groupNamesToRemove == null) {
                        responseCode = "5000";
                        responseMessage = "SUCCESS";
                        automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Action is " + action + " Response code set as ::" + responseCode + " since Group Names to Add and Remove are empty");
                     }
                  }
               } catch (Exception var32) {
                  automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception:::" + var32.getMessage());
                  responseCode = "5050";
                  responseMessage = "FAILURE_EXCEPTION";
               }
            }
         } else {
            responseCode = "5001";
            responseMessage = "USER_ID_NULL";
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Input value is null:::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: " + "Response code is ::" + responseCode + " ::Respones Message is :: " + responseMessage);
      }

      return resObj;
   }

   private boolean isGroupExist(String[] groupNamesToAdd) {
      automatictasklog.info("ECS Savvion: isGroupExist Service:::: START");
      boolean isGroupExist = false;
      int addListLength = groupNamesToAdd.length;
      String[] groupNames = new String[50];
      groupNames = UserManager.getDefaultRealm().getGroupNames();

      for(int i = 0; i < addListLength; ++i) {
         for(int j = 0; j < groupNames.length; ++j) {
            if (groupNames[j].equals(groupNamesToAdd[i])) {
               isGroupExist = true;
               break;
            }

            isGroupExist = false;
         }

         if (!isGroupExist) {
            automatictasklog.info("ECS Savvion: isGroupExist Service:::: Group Name " + groupNamesToAdd[i] + " not exist");
            break;
         }
      }

      automatictasklog.info("ECS Savvion: isGroupExist Service:::: Ends & result is " + isGroupExist);
      return isGroupExist;
   }

   private void revertTransaction(String userId, String[] groupNamesToAdd, String[] groupNamesToRemove, String revertAction, int addCount, int removeCount) {
      String methodName = "revertTransaction";
      automatictasklog.info("ECS Savvion: " + methodName + " Service:::: START");
      RequestObject[] reqObj = new RequestObject[]{new RequestObject(), null};
      reqObj[0].setKey("USERID");
      reqObj[0].setValue(userId);
      reqObj[1] = new RequestObject();
      reqObj[1].setKey("GROUPNAME");
      int i;
      if (groupNamesToAdd != null) {
         for(i = 0; i <= addCount; ++i) {
            reqObj[1].setValue(groupNamesToRemove[i]);
            automatictasklog.info("ECS Savvion: revertTransaction Service:::: mapping the user to groups which we removed in this transaction earlier");
            this.mapUserToGroup(reqObj);
         }
      }

      for(i = 0; i <= removeCount; ++i) {
         reqObj[1].setValue(groupNamesToRemove[i]);
         automatictasklog.info("ECS Savvion: revertTransaction Service:::: removing the user from groups which were added in this transaction earlier");
         this.removeUserFromGroup(reqObj);
      }

   }

   private ResponseObject removeUserFromGroup(RequestObject[] reqObj) {
      String methodName = "removeUserFromGroup";
      boolean status = false;
      AdvanceGroup groupObj = null;
      String responseCode = null;
      String responseMessage = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.debug("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String groupName = (String)hashMap.get("GROUPNAME");
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Remove userId " + userId + " from the group " + groupName);

         try {
            if (!userId.equals("") && userId != null) {
               if (uc.checkUserGroup(userId, groupName)) {
                  groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
                  status = groupObj.removeUserMember(userId);
                  if (status) {
                     responseCode = "5000";
                     responseMessage = "SUCCESS";
                  } else {
                     responseCode = "5050";
                     responseMessage = "FAILURE_EXCEPTION";
                  }

                  automatictasklog.debug("ECS Savvion: removeUserFromGroup Service::::userId is " + userId + " status is " + status);
               } else {
                  responseCode = "5023";
                  responseMessage = "USER_NOT_MAPPED";
               }
            } else {
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }
         } catch (Exception var13) {
            responseCode = "5050";
            responseMessage = "FAILURE_EXCEPTION";
            automatictasklog.error("ECS Savvion: removeUserFromECS Service:::: Exception " + var13.getMessage());
         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
      }

      automatictasklog.debug("ECS Savvion: removeUserFromECS Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }

   public ResponseObject mapUserToGroup(RequestObject[] reqObj) {
      String methodName = "mapUserToGroup";
      boolean status = false;
      AdvanceGroup groupObj = null;
      String responseCode = null;
      String responseMessage = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");
         String groupName = (String)hashMap.get("GROUPNAME");
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName);

         try {
            if (!userId.equals("") && userId != null) {
               groupObj = (AdvanceGroup)UserManager.getGroup(groupName);
               status = groupObj.addUserMember(userId);
               if (status) {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName + " success");
                  responseMessage = "SUCCESS";
                  responseCode = "5000";
               } else {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Add user " + userId + " to group " + groupName + " failed");
                  responseCode = "5050";
                  responseMessage = "FAILURE_EXCEPTION";
               }
            } else {
               responseCode = "5001";
               responseMessage = "USER_ID_NULL";
            }
         } catch (Exception var13) {
            automatictasklog.error("ECS Savvion: " + methodName + " Service:::: Exception " + var13.getMessage());
            responseMessage = "GROUPNAME_NOT_EXIST";
            responseCode = "5025";
         }

         automatictasklog.info("ECS Savvion: " + methodName + " Service END ::: " + responseCode + " :: Response Message :: " + responseMessage);
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
      }

      return resObj;
   }

   public ResponseObject createOrRemoveGroupECS(RequestObject[] reqObj) {
      String methodName = "createGroupECS";
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      String[] groupToCreate = null;
      String[] groupToRemove = null;
      Realm usr = null;
      UtilClass uc = new UtilClass(SBM_HOME);
      String responseCode = null;
      String responseMessage = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.error("ECS Savvion: " + methodName + " Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         HashMap hashMap = uc.getMap(reqObj);
         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + hashMap);
         String userId = (String)uc.getMap(reqObj).get("USERID");
         String action = (String)uc.getMap(reqObj).get("ACTION");
         if (!uc.checkNull(hashMap.get("CREATEGROUPS"))) {
            groupToCreate = (String[])hashMap.get("CREATEGROUPS");
            automatictasklog.info("ECS Savvion: createGroupECS Service:::: input group name to ADD  are ::" + Arrays.toString(groupToCreate));
         }

         if (!uc.checkNull(hashMap.get("REMOVEGROUPS"))) {
            groupToRemove = (String[])hashMap.get("REMOVEGROUPS");
            automatictasklog.info("ECS Savvion: createGroupECS Service:::: input group name to REMOVE  are ::" + Arrays.toString(groupToRemove));
         }

         automatictasklog.info("ECS Savvion: " + methodName + " Service:::: INPUT VALUES " + "userId:: " + userId + " action ::" + action + "groupToCreate:: " + groupToCreate + " groupToRemove:: " + groupToRemove);
         if (action != null && !action.equals("")) {
            try {
               usr = UserManager.getDefaultRealm();
               int i;
               if (usr != null && action.equals("Create")) {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service :::  inside create group ");
                  if (groupToCreate != null && groupToCreate.length != 0) {
                     for(i = 0; i < groupToCreate.length; ++i) {
                        String[] groupName = new String[]{groupToCreate[i]};
                        boolean isGroupExist = this.isGroupExist(groupName);
                        if (!isGroupExist) {
                           boolean addResult = usr.addGroup(groupToCreate[i]);
                           if (addResult) {
                              responseCode = "5000";
                              responseMessage = "SUCCESS";
                           } else {
                              responseCode = "5026";
                              responseMessage = "GROUPCREATION_FAILED";
                              automatictasklog.debug("ECS Savvion: " + methodName + " Service:: group name " + groupToCreate[i] + " cannot be created and Response Message :: " + responseMessage);
                           }
                        } else {
                           automatictasklog.debug("ECS Savvion: " + methodName + " Service:: group name " + groupToCreate[i] + " already exist");
                        }
                     }
                  } else {
                     responseCode = "5021";
                     responseMessage = "INPUT_VALUE_IS_NULL";
                     automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
                  }
               }

               if (usr != null && action.equals("Remove")) {
                  automatictasklog.info("ECS Savvion: " + methodName + " Service :::  inside remove group ");
                  if (groupToRemove != null && groupToRemove.length != 0) {
                     if (this.isGroupExist(groupToRemove)) {
                        for(i = 0; i < groupToRemove.length; ++i) {
                           boolean removeResult = usr.removeGroup(groupToRemove[i]);
                           if (removeResult) {
                              responseCode = "5000";
                              responseMessage = "SUCCESS";
                           } else {
                              responseCode = "5027";
                              responseMessage = "GROUP_REMOVE_FAILED";
                              automatictasklog.debug("ECS Savvion: " + methodName + " Service:: group name " + groupToCreate[i] + " cannot be removed and Response Message :: " + responseMessage);
                           }
                        }
                     } else {
                        responseCode = "5025";
                        responseMessage = "GROUPNAME_NOT_EXIST";
                        automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
                     }
                  } else {
                     responseCode = "5021";
                     responseMessage = "INPUT_VALUE_IS_NULL";
                     automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
                  }
               }
            } catch (Exception var17) {
               responseCode = "5050";
               responseMessage = "FAILURE_EXCEPTION";
               automatictasklog.info("ECS Savvion :: " + methodName + " Exception :: " + var17);
            }
         } else {
            responseCode = "5021";
            responseMessage = "INPUT_VALUE_IS_NULL";
            automatictasklog.info("ECS Savvion: " + methodName + " Service:::: Input value is null:::" + "Response code ::" + responseCode + " ::Respones Message :: " + responseMessage);
         }

         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
      }

      automatictasklog.debug("ECS Savvion: " + methodName + " Service::: END " + responseCode + " :: Response Message :: " + responseMessage);
      return resObj;
   }

   private ResponseObject mapUserToECSGroup(RequestObject[] reqObj) {
      boolean status = false;
      AdvanceGroup groupObj = null;
      String responseCode = null;
      String responseMessage = null;
      new HashMap();
      ResponseObject resObj = new ResponseObject();
      UtilClass uc = new UtilClass(SBM_HOME);
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         resObj.setResponseMessage("REQUEST_OBJECT_IS_NULL");
         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service:: REQUEST_OBJECT_IS_NULL");
      } else {
         automatictasklog.info("ECS Savvion: mapUserToECSGroup Service:::: Map user START");
         HashMap hashMap = uc.getMap(reqObj);
         String userId = (String)hashMap.get("USERID");

         try {
            automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service START ::: INPUT VALUES are userId" + userId);
            if (!userId.equals("") && userId != null) {
               Realm realm = UserManager.getDefaultRealm();
               boolean result = realm.addUser(userId);
               if (result) {
                  User sbmUser = realm.getUser(userId);
                  sbmUser.setAttribute("password", userId);
               }

               if (!uc.checkUserECS(userId, "ECS")) {
                  groupObj = (AdvanceGroup)UserManager.getGroup("ECS");
                  status = groupObj.addUserMember(userId);
                  if (status) {
                     responseCode = "5000";
                     responseMessage = "SUCCESS";
                  } else {
                     responseCode = "5050";
                     responseMessage = "FAILURE_EXCEPTION";
                  }
               } else {
                  responseCode = "5022";
                  responseMessage = "USER_ALREADY_MAPPED";
               }
            } else {
               responseCode = "5021";
               responseMessage = "INPUT_VALUE_IS_NULL";
            }
         } catch (Exception var13) {
            automatictasklog.error("ECS Savvion: mapUserToECSGroup Service:::: Exception " + var13.getMessage());
         }

         automatictasklog.debug("ECS Savvion: mapUserToECSGroup Service END ::: " + responseCode + " :: Response Message :: " + responseMessage);
         resObj.setResponseCode(responseCode);
         resObj.setResponseMessage(responseMessage);
      }

      return resObj;
   }
}
