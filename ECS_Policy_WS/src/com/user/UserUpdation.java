package com.user;

import com.savvion.sbm.util.PService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserUpdation {
   private String dataSourceName = "jdbc/SBMCommonDB";

   public void updateUser(String performer, String[] oldAttrs, String[] newAttrs) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      HashMap<String, String> newAttrMap = null;
      HashMap oldAttrMap = null;

      try {
         if (performer != null && newAttrs != null && newAttrs.length > 0 && oldAttrs != null && oldAttrs.length > 0) {
            newAttrMap = new HashMap();
            oldAttrMap = new HashMap();

            int i;
            String attrVal;
            for(i = 0; i < newAttrs.length; ++i) {
               attrVal = newAttrs[i].split(":")[1].trim();
               if (attrVal == null || attrVal.trim().length() == 0) {
                  attrVal = "-";
               }

               newAttrMap.put(newAttrs[i].split(":")[0].trim(), attrVal.trim());
            }

            for(i = 0; i < oldAttrs.length; ++i) {
               attrVal = oldAttrs[i].split(":")[1].trim();
               if (attrVal == null || attrVal.trim().length() == 0) {
                  attrVal = "-";
               }

               oldAttrMap.put(oldAttrs[i].split(":")[0].trim(), attrVal.trim());
            }

            if (!newAttrMap.isEmpty() && !oldAttrMap.isEmpty()) {
               if (newAttrMap.get("PASSWORD") != null && oldAttrMap.get("PASSWORD") != null) {
                  newAttrMap.put("PASSWORD", this.getUserEncreptedPassword((String)newAttrMap.get("PASSWORD")));
                  oldAttrMap.put("PASSWORD", this.getUserEncreptedPassword((String)oldAttrMap.get("PASSWORD")));
               }

               String userID = (String)newAttrMap.get("USERNAME");
               Map<String, Boolean> comparisonResult = compareEntries(oldAttrMap, newAttrMap);
               connection = this.getDBConnection();
               Iterator var11 = comparisonResult.entrySet().iterator();

               while(var11.hasNext()) {
                  Entry<String, Boolean> entry = (Entry)var11.next();
                  if (!(Boolean)entry.getValue()) {
                     String qry = "INSERT INTO TBL_USER_CURD_OPERATION_DTL VALUES ('" + userID + "','" + (String)entry.getKey() + "','" + (String)oldAttrMap.get(entry.getKey()) + "','" + (String)newAttrMap.get(entry.getKey()) + "',GETUSERNAME('" + performer.trim() + "'),SYSDATE,'UPDATE')";
                     pstmt = connection.prepareStatement(qry);
                     pstmt.execute();
                  }
               }
            }
         }
      } catch (Exception var16) {
         System.out.println("Error in capturing User Updates : " + var16.getMessage());
      } finally {
         this.releaseResources(connection, pstmt);
      }

   }

   private static <K extends String, V> Map<String, Boolean> compareEntries(Map<K, V> map1, Map<K, V> map2) {
      Collection<String> allKeys = new HashSet();
      allKeys.addAll(map1.keySet());
      allKeys.addAll(map2.keySet());
      Map<String, Boolean> result = new TreeMap();
      Iterator var5 = allKeys.iterator();

      while(var5.hasNext()) {
         String key = (String)var5.next();
         result.put(key, map1.containsKey(key) == map2.containsKey(key) && Boolean.valueOf(equal(map1.get(key), map2.get(key))));
      }

      return result;
   }

   private static boolean equal(Object obj1, Object obj2) {
      return obj1 == obj2 || obj1 != null && obj1.equals(obj2);
   }

   private Connection getDBConnection() {
      try {
         return ((DataSource)(new InitialContext()).lookup(this.dataSourceName)).getConnection();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private void releaseResources(Connection conn, PreparedStatement pstmt) {
      try {
         if (pstmt != null) {
            pstmt.close();
         }

         if (conn != null) {
            conn.close();
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private String getUserEncreptedPassword(String pass) {
      String encrptedPass = "";
      if (pass != null && pass.trim().length() > 0) {
         try {
            PService p = PService.self();
            encrptedPass = p.encrypt(pass);
         } catch (Exception var4) {
            System.out.println("Error encrepting password : " + var4.getMessage());
         }
      }

      return encrptedPass;
   }
}
