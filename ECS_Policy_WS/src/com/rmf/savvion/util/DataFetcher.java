package com.rmf.savvion.util;

import com.rmf.savvion.bean.GroupItem;
import com.rmf.savvion.bean.QueueItem;
import com.rmf.savvion.bean.QueueMap;
import com.rmf.savvion.bean.RMFUser;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFetcher {
   public List getAddUserList() {
      DataInputStream dis = null;
      String record = null;
      String[] userInfo = (String[])null;
      ArrayList userList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.ADD_USERS_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            userInfo = record.split(",");
            RMFUser user = new RMFUser();
            user.setUserName(userInfo[0]);
            user.setFirstName(userInfo[1]);
            user.setLastName(userInfo[2]);
            user.setEmailId(userInfo[3]);
            user.setCompany(userInfo[4]);
            user.setDepartment(userInfo[5]);
            user.setDesignation(userInfo[6]);
            user.setDob(this.parseShortDate(userInfo[7], ResourceManager.DATE_FORMAT_SHORT));
            user.setPassword(this.parseShortDate(userInfo[7], ResourceManager.DATE_FORMAT_PASSWORD) + ResourceManager.PASSWORD_APPENDER);
            user.setDoj(this.parseShortDate(userInfo[8], ResourceManager.DATE_FORMAT_SHORT));
            user.setMobile(userInfo[9]);
            user.setKeyEmployee(userInfo[10]);
            user.setZone(userInfo[11]);
            user.setRegion(userInfo[12]);
            user.setLocation(userInfo[13]);
            user.setEmployeeType(userInfo[14]);
            user.setActive(userInfo[15]);
            userList.add(user);
         }
      } catch (IOException var15) {
         var15.printStackTrace();
      } finally {
         if (dis != null) {
            try {
               ((DataInputStream)dis).close();
            } catch (IOException var14) {
               var14.printStackTrace();
            }
         }

      }

      return userList;
   }

   public List<String> getRemoveUserList() {
      DataInputStream dis = null;
      String record = null;
      ArrayList userList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.REMOVE_USERS_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            userList.add(record);
         }
      } catch (IOException var13) {
         var13.printStackTrace();
      } finally {
         if (dis != null) {
            try {
               ((DataInputStream)dis).close();
            } catch (IOException var12) {
               var12.printStackTrace();
            }
         }

      }

      return userList;
   }

   public List<String> getRemoveGroupList() {
      DataInputStream dis = null;
      String record = null;
      ArrayList groupList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.REMOVE_GROUPS_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            groupList.add(record);
         }
      } catch (IOException var13) {
         var13.printStackTrace();
      } finally {
         if (dis != null) {
            try {
               ((DataInputStream)dis).close();
            } catch (IOException var12) {
               var12.printStackTrace();
            }
         }

      }

      return groupList;
   }

   public List getQueueList() {
      DataInputStream dis = null;
      String record = null;
      String[] queueInfo = (String[])null;
      ArrayList queueList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.ADD_QUEUES_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            queueInfo = record.split(",");
            QueueItem queueItem = new QueueItem();
            queueItem.setName(queueInfo[0]);
            queueItem.setDescription(queueInfo[1]);
            queueList.add(queueItem);
         }
      } catch (IOException var15) {
         System.out.println(var15.getMessage());
      } finally {
         if (dis != null) {
            try {
               ((DataInputStream)dis).close();
            } catch (IOException var14) {
               System.out.println(var14.getMessage());
            }
         }

      }

      return queueList;
   }

   public List getGroupList() {
      String record = null;
      String[] groupInfo = (String[])null;
      ArrayList groupList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.ADD_GROUPS_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            groupInfo = record.split(",");
            GroupItem groupItem = new GroupItem();
            groupItem.setName(groupInfo[0]);
            groupItem.setDescription(groupInfo[1]);
            groupList.add(groupItem);
         }
      } catch (IOException var7) {
         System.out.println(var7.getMessage());
      }

      return groupList;
   }

   public List getUserQueueMappings() {
      String record = null;
      String[] userQueueInfo = (String[])null;
      ArrayList userQueueMapList = new ArrayList();

      try {
         InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.USER_QUEUE_MAP_FILE);
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

         while((record = br.readLine()) != null) {
            userQueueInfo = record.split(",");

            for(int i = 1; i < userQueueInfo.length; ++i) {
               QueueMap queueMap = new QueueMap();
               queueMap.setUserName(userQueueInfo[0]);
               queueMap.setQueueName(userQueueInfo[1]);
               userQueueMapList.add(queueMap);
            }
         }
      } catch (IOException var8) {
         System.out.println(var8.getMessage());
      }

      return userQueueMapList;
   }

   public Map<String, String[]> getGroupMembershipMap() {
      String record = null;
      String[] members = (String[])null;
      Map<String, String[]> membershipMap = new HashMap();
      InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(ResourceManager.USER_GROUP_MAP_FILE);
      BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

      try {
         while((record = br.readLine()) != null) {
            members = record.split(",");
            membershipMap.put(members[0], members);
         }
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      return membershipMap;
   }

   public String parseShortDate(String dateStr, String format) {
      String formattedDate = null;
      if (dateStr != null && dateStr.trim().length() != 0) {
         String day = dateStr.substring(0, dateStr.indexOf("/"));
         String month = dateStr.substring(3, dateStr.indexOf("/", 3));
         String year = dateStr.substring(6, dateStr.length());
         Calendar cal = new GregorianCalendar();
         cal.clear();
         cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
         SimpleDateFormat sdf = new SimpleDateFormat(format);
         formattedDate = sdf.format(new Date(cal.getTimeInMillis()));
      }

      if (formattedDate == null) {
         formattedDate = "";
      }

      return formattedDate;
   }

   public static void main(String[] args) {
      String strDOB = new String("01/12/2008");
      DataFetcher df = new DataFetcher();
      System.out.println(df.parseShortDate(strDOB, ResourceManager.DATE_FORMAT_PASSWORD));
      System.exit(1);
      List users = null;
      System.out.println(((List)users).size());
   }
}
