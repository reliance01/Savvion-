package com.rmf.savvion.util;

import com.rmf.savvion.bean.GroupItem;
import com.rmf.savvion.bean.QueueItem;
import com.rmf.savvion.bean.QueueMap;
import com.rmf.savvion.bean.RMFUser;
import com.savvion.common.queue.Queue;
import com.savvion.common.queue.QueueManager;
import com.savvion.common.queue.QueueMember;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.Group;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.LDAPRealm;
import com.tdiinc.userManager.PAKRealm;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.UserManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ResourceManager {
   public static String CREATE_GROUPS = "createGroups";
   public static String CREATE_QUEUES = "createQueues";
   public static String ADD_USERS = "addUsers";
   public static String CREATE_GROUP = "createGroup";
   public static String CREATE_QUEUE = "createQueue";
   public static String CREATE_USER = "createUser";
   public static String ADD_QUEUE_MEMBERS = "addQueueMembers";
   public static String ADD_GROUP_MEMBERS = "addGroupMembers";
   public static String ADD_QUEUE_MEMBER = "addQueueMember";
   public static String ADD_GROUP_MEMBER = "addGroupMember";
   private static final String REMOVE_USERS = "removeUsers";
   private static final String REMOVE_GROUPS = "removeGroups";
   private static final String REMOVE_QUEUES = "removeQueues";
   public static String PROVIDER_URL = null;
   public static String PRINCIPALS = null;
   public static String CREDENTIALS = null;
   public static String INITIAL_CONTEXT_FACTORY = null;
   public static String DATA_SOURCE_NAME = null;
   private static DataSource DATA_SOURCE = null;
   public static String ADD_USERS_FILE = null;
   public static String ADD_QUEUES_FILE = null;
   public static String ADD_GROUPS_FILE = null;
   public static String REMOVE_USERS_FILE = null;
   public static String REMOVE_QUEUES_FILE = null;
   public static String REMOVE_GROUPS_FILE = null;
   public static String USER_QUEUE_MAP_FILE = null;
   public static String USER_GROUP_MAP_FILE = null;
   public static String DATE_FORMAT_SHORT = "MMM dd, yyyy";
   public static String DATE_FORMAT_LONG = "MMM dd, yyyy hh:mm a";
   public static String DATE_FORMAT_PASSWORD = "ddMMyyyy";
   public static final String USER_ATTR_DOB = "dob";
   public static final String USER_ATTR_DOJ = "doj";
   public static final String USER_ATTR_DEPARTMENT_ID = "department";
   public static final String USER_ATTR_COMPANY_ID = "company";
   public static final String USER_ATTR_DESIGNATION = "designation";
   public static final String USER_ATTR_ACTIVE = "active";
   public static final String USER_ATTR_KEY_EMPLOYEE = "keyemployee";
   public static final String USER_ATTR_MOBILE = "mobile";
   private static final String USER_ATTR_EMPLOYEE_TYPE = "employeetype";
   private static final String USER_ATTR_ZONE = "zone";
   private static final String USER_ATTR_LOCATION = "location";
   private static final String USER_ATTR_REGION = "region";
   public static final String GRP_ATTR_DESCRIPTION = "description";
   public static String PASSWORD_APPENDER = "p";
   public static String LANGUAGE_ENGLISH = "en";
   public static String COUNTRY_UNITED_STATES = "US";
   public static String FLAG_YES = "Y";
   public static String FLAG_NO = "N";
   public static final String FIELD_SEPARATOR = ",";
   public static final String DATE_FIELD_SEPARATOR = "/";
   private static final String DISPLAY_EXISTING_USERS = "displayExistingUsers";
   private static final String DISPLAY_EXISTING_GROUPS = "displayExistingGroups";
   private static final String DISPLAY_EXISTING_QUEUES = "displayExistingQueues";
   public static String SBM_HOME_PATH;

   private void init() {
      try {
         Properties rmProp = new Properties();
         Properties jndiProp = new Properties();
         rmProp.load(this.getClass().getClassLoader().getResourceAsStream("resourcemanager.conf"));
         jndiProp.load(this.getClass().getClassLoader().getResourceAsStream("sbmjndi.properties"));
         SBM_HOME_PATH = rmProp.getProperty("rm.sbm.home.path");
         ADD_USERS_FILE = rmProp.getProperty("rm.add.users.file");
         ADD_QUEUES_FILE = rmProp.getProperty("rm.add.queues.file");
         ADD_GROUPS_FILE = rmProp.getProperty("rm.add.groups.file");
         REMOVE_USERS_FILE = rmProp.getProperty("rm.remove.users.file");
         REMOVE_QUEUES_FILE = rmProp.getProperty("rm.remove.queues.file");
         REMOVE_GROUPS_FILE = rmProp.getProperty("rm.remove.groups.file");
         USER_QUEUE_MAP_FILE = rmProp.getProperty("rm.user.queue.map.file");
         USER_GROUP_MAP_FILE = rmProp.getProperty("rm.user.group.map.file");
         DATA_SOURCE_NAME = rmProp.getProperty("rm.data.source.name");
         PROVIDER_URL = jndiProp.getProperty("sbm.weblogic.provider.url");
         INITIAL_CONTEXT_FACTORY = jndiProp.getProperty("sbm.weblogic.factory.initial");
         PRINCIPALS = jndiProp.getProperty("sbm.weblogic.principal");
         CREDENTIALS = jndiProp.getProperty("sbm.weblogic.credentials");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void setDataSource() {
      Context ctx = null;
      Hashtable<String, String> ht = new Hashtable();
      ht.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
      ht.put("java.naming.provider.url", PROVIDER_URL);
      ht.put("java.naming.security.principal", PRINCIPALS);
      ht.put("java.naming.security.credentials", CREDENTIALS);

      try {
         ctx = new InitialContext(ht);
         DATA_SOURCE = (DataSource)ctx.lookup(DATA_SOURCE_NAME);
      } catch (NamingException var4) {
         System.out.println("Error while setting data source");
         var4.printStackTrace();
      }

   }

   private void createQueue(QueueItem qItem) {
      QueueManager queueManager = null;

      try {
         queueManager = new QueueManager(DATA_SOURCE);
         ArrayList<String> queueNames = new ArrayList();
         queueNames.add(qItem.getName());
         if (queueManager.hasQueue(queueNames)) {
            System.out.println("Queue with name : " + qItem.getName() + " already exists");
         } else {
            Queue queue = new Queue(qItem.getName());
            queue.setDescription(qItem.getDescription());
            long queueId = queueManager.create(queue);
            queue.save(DATA_SOURCE);
            System.out.println(queue.getName() + " Queue created with id : " + queueId);
         }
      } catch (RuntimeException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new RuntimeException(" Exception while creating the queue" + var8.getMessage());
      }
   }

   private void createQueues(List queues) throws Exception {
      int queueCount = queues.size();

      for(int i = 0; i < queueCount; ++i) {
         this.createQueue((QueueItem)queues.get(i));
      }

   }

   private Queue getQueue(String queueName) throws SQLException {
      Queue queue = null;
      QueueManager qm = new QueueManager(DATA_SOURCE);
      ArrayList queues = qm.search(queueName);
      if (queues != null || queues.size() != 0) {
         queue = (Queue)queues.get(0);
      }

      return queue;
   }

   private void addQueueMembers(List userQueueMapList) throws SQLException {
      if (userQueueMapList != null) {
         Queue queue = null;
         QueueMember qMember = null;

         for(int i = 0; i < userQueueMapList.size(); ++i) {
            QueueMap qMap = (QueueMap)userQueueMapList.get(i);
            String memberName = qMap.getUserName();
            String queueName = qMap.getQueueName();
            queue = this.getQueue(queueName);
            if (!this.isQMemberExist(queue, memberName)) {
               qMember = new QueueMember(memberName, false);
               queue.addMember(DATA_SOURCE, qMember);
               queue.save(DATA_SOURCE);
               System.out.println("Member " + qMember.getName() + " added to " + queue.getName());
            } else {
               System.out.println("Member " + memberName + " already exists in " + queue.getName());
            }
         }
      } else {
         System.out.println("User queue mapping not found...");
      }

   }

   private boolean isQMemberExist(Queue queue, String memberName) {
      boolean isExist = false;
      ArrayList members = queue.getMembers(DATA_SOURCE);
      if (members != null) {
         for(int i = 0; i < members.size(); ++i) {
            QueueMember qMember = (QueueMember)members.get(i);
            if (qMember != null && memberName.equals(qMember.getName())) {
               return true;
            }
         }
      }

      return isExist;
   }

   public void createGroup(GroupItem groupItem) {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         Group grp = jdbcRealm.getGroup(groupItem.getName());
         if (grp == null) {
            Hashtable<String, String> grpAttr = new Hashtable();
            grpAttr.put("description", groupItem.getDescription());
            result = jdbcRealm.addGroup(groupItem.getName(), grpAttr, (Hashtable)null);
            if (result) {
               System.out.println("Group [ " + groupItem.getName() + " ] created successfully.");
            } else {
               System.out.println("Group [ " + groupItem.getName() + " ] not created.");
            }
         } else {
            System.out.println("Group [ " + groupItem.getName() + " ] already exist.");
         }
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm ldapRealm = (LDAPRealm)realm;
         ldapRealm.addUser(groupItem.getName());
      } else if (realm instanceof PAKRealm) {
         PAKRealm pakRealm = (PAKRealm)realm;
         pakRealm.addUser(groupItem.getName());
      }

   }

   private boolean addUser(RMFUser user) {
      Hashtable<String, String> ht = new Hashtable();
      ht.put("password", user.getPassword());
      ht.put("firstname", user.getFirstName());
      ht.put("lastname", user.getLastName());
      ht.put("email", user.getEmailId());
      ht.put("language", LANGUAGE_ENGLISH);
      ht.put("country", COUNTRY_UNITED_STATES);
      ht.put("department", user.getDepartment());
      ht.put("company", user.getCompany());
      ht.put("dob", user.getDob());
      ht.put("doj", user.getDoj());
      ht.put("employeetype", user.getEmployeeType());
      ht.put("designation", user.getDesignation());
      ht.put("zone", user.getZone());
      ht.put("location", user.getLocation());
      ht.put("region", user.getRegion());
      ht.put("mobile", user.getMobile());
      ht.put("active", FLAG_YES);
      ht.put("keyemployee", user.getKeyEmployee());
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         result = jdbcRealm.addUser(user.getUserName(), ht);
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm ldapRealm = (LDAPRealm)realm;
         result = ldapRealm.addUser(user.getUserName());
      } else if (realm instanceof PAKRealm) {
         PAKRealm pakRealm = (PAKRealm)realm;
         result = pakRealm.addUser(user.getUserName());
      }

      if (result) {
         System.out.println("User [ " + user.getUserName() + " ] added successfully.");
      } else {
         System.out.println("User [ " + user.getUserName() + " ] not added.");
      }

      return result;
   }

   public void addUsers() {
      DataFetcher dFetcher = new DataFetcher();
      int successCount = 0;
      int failureCount = 0;
      boolean addStatus = false;
      List userList = dFetcher.getAddUserList();

      int i;
      for(i = 0; i < userList.size(); ++i) {
         addStatus = this.addUser((RMFUser)userList.get(i));
         if (addStatus) {
            ++successCount;
         } else {
            ++failureCount;
         }
      }

      System.out.println("\n\nTotal Users : [ " + i + "]");
      System.out.println("Successfully added " + successCount + " users.");
      System.out.println("Problem in adding " + failureCount + " users.");
   }

   public void removeUsers() {
      DataFetcher dFetcher = new DataFetcher();
      int successCount = 0;
      int failureCount = 0;
      boolean removeStatus = false;
      List<String> userList = dFetcher.getRemoveUserList();

      int i;
      for(i = 0; i < userList.size(); ++i) {
         removeStatus = this.removeUser((String)userList.get(i));
         if (removeStatus) {
            ++successCount;
         } else {
            ++failureCount;
         }
      }

      System.out.println("\n\nTotal Users : [ " + i + "]");
      System.out.println("Successfully added " + successCount + " users.");
      System.out.println("Problem in adding " + failureCount + " users.");
   }

   private boolean removeUser(String userName) {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         result = jdbcRealm.removeUser(userName);
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm var5 = (LDAPRealm)realm;
      } else if (realm instanceof PAKRealm) {
         PAKRealm var6 = (PAKRealm)realm;
      }

      if (result) {
         System.out.println("User [ " + userName + " ] removed successfully.");
      } else {
         System.out.println("User [ " + userName + " ] not removed.");
      }

      return result;
   }

   public void removeGroups() {
      DataFetcher dFetcher = new DataFetcher();
      List<String> groupList = dFetcher.getRemoveGroupList();

      for(int i = 0; i < groupList.size(); ++i) {
         this.removeGroup((String)groupList.get(i));
      }

   }

   private void removeGroup(String groupName) {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         result = jdbcRealm.removeGroup(groupName);
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm var5 = (LDAPRealm)realm;
      } else if (realm instanceof PAKRealm) {
         PAKRealm var6 = (PAKRealm)realm;
      }

      if (result) {
         System.out.println("Group [ " + groupName + " ] removed successfully.");
      } else {
         System.out.println("Group [ " + groupName + " ] not removed.");
      }

   }

   private void removeQueues() {
   }

   public void createGroups() {
      DataFetcher dFetcher = new DataFetcher();
      List groupList = dFetcher.getGroupList();
      boolean status = false;

      for(int i = 0; i < groupList.size(); ++i) {
         this.createGroup((GroupItem)groupList.get(i));
      }

   }

   public void createQueues() {
      DataFetcher dFetcher = new DataFetcher();

      try {
         List queueList = dFetcher.getQueueList();
         this.createQueues(queueList);
      } catch (NamingException var3) {
         var3.printStackTrace();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void addQueueMembers() {
      DataFetcher dFetcher = new DataFetcher();
      List userQueueMapList = dFetcher.getUserQueueMappings();

      try {
         this.addQueueMembers(userQueueMapList);
      } catch (SQLException var4) {
         System.out.println("Error while adding members...");
         var4.printStackTrace();
      }

   }

   public void addGroupsMembers() {
      DataFetcher dFetcher = new DataFetcher();
      Map<String, String[]> grpMemMap = dFetcher.getGroupMembershipMap();
      Iterator it = grpMemMap.entrySet().iterator();

      while(it.hasNext()) {
         Entry pairs = (Entry)((Entry)it.next());
         this.addGroupMembers((String)pairs.getKey(), (String[])pairs.getValue());
      }

   }

   private void addGroupMembers(String groupName, String[] userNames) {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         AdvanceGroup grp = (AdvanceGroup)jdbcRealm.getGroup(groupName);
         if (grp != null) {
            for(int i = 1; i < userNames.length; ++i) {
               result = grp.addUserMember(userNames[i]);
               if (result) {
                  System.out.println("User [ " + userNames[i] + " ] added to group [ " + groupName + " ]");
               } else {
                  System.out.println("User [ " + userNames[i] + " ] not added to group [ " + groupName + " ]");
               }
            }
         } else {
            System.out.println("Group [ " + groupName + " ] not available.");
         }
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm var8 = (LDAPRealm)realm;
      } else if (realm instanceof PAKRealm) {
         PAKRealm var9 = (PAKRealm)realm;
      }

   }

   public void displayExistingGroups() {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         String[] grpNames = jdbcRealm.getGroupNames();
         System.out.println("******************************");
         System.out.println("\tExisting Groups");
         System.out.println("******************************");

         for(int i = 0; i < grpNames.length; ++i) {
            System.out.println("\t" + (i + 1) + ". " + grpNames[i]);
         }

         System.out.println("******************************");
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm var6 = (LDAPRealm)realm;
      } else if (realm instanceof PAKRealm) {
         PAKRealm var7 = (PAKRealm)realm;
      }

   }

   public void displayExistingUsers() {
      Realm realm = UserManager.getDefaultRealm();
      boolean result = false;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         String[] userNames = jdbcRealm.getUserNames();
         System.out.println("******************************");
         System.out.println("\tExisting Users");
         System.out.println("******************************");

         for(int i = 0; i < userNames.length; ++i) {
            System.out.println("\t" + (i + 1) + ". " + userNames[i]);
         }

         System.out.println("******************************");
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm var6 = (LDAPRealm)realm;
      } else if (realm instanceof PAKRealm) {
         PAKRealm var7 = (PAKRealm)realm;
      }

   }

   private void addGroupMember() {
   }

   public static void main(String[] args) {
      if (args == null || args.length == 0) {
         System.out.println("Missing arguements...\nUtility terminated");
         System.exit(1);
      }

      ResourceManager rManager = new ResourceManager();
      rManager.init();
      if (ADD_USERS.equalsIgnoreCase(args[0])) {
         rManager.addUsers();
      } else if (CREATE_GROUPS.equalsIgnoreCase(args[0])) {
         rManager.createGroups();
      } else if (CREATE_QUEUES.equalsIgnoreCase(args[0])) {
         rManager.createQueues();
      } else if (ADD_QUEUE_MEMBERS.equalsIgnoreCase(args[0])) {
         System.out.println("Add members to queues...");
         rManager.addQueueMembers();
      } else if (ADD_GROUP_MEMBERS.equalsIgnoreCase(args[0])) {
         rManager.addGroupsMembers();
      } else if ("removeUsers".equalsIgnoreCase(args[0])) {
         rManager.removeUsers();
      } else if ("removeGroups".equalsIgnoreCase(args[0])) {
         rManager.removeGroups();
      } else if ("removeQueues".equalsIgnoreCase(args[0])) {
         rManager.removeQueues();
      } else if ("displayExistingUsers".equalsIgnoreCase(args[0])) {
         rManager.displayExistingUsers();
      } else if ("displayExistingGroups".equalsIgnoreCase(args[0])) {
         rManager.displayExistingGroups();
      } else {
         "displayExistingQueues".equalsIgnoreCase(args[0]);
      }

   }
}
