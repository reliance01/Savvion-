package com.rgicl.savvion.motor.uw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class PropertyReader {
   private static PropertyResourceBundle resrcBundle;
   private static PropertyReader propertyReader = null;

   static {
      try {
         System.out.println("############### Inside Property reader");
         System.out.println("RESOURCE BUNDLE******************** " + PropertyReader.class.getClassLoader().getResourceAsStream("rgi_config_en.properties"));
         resrcBundle = new PropertyResourceBundle(PropertyReader.class.getClassLoader().getResourceAsStream("rgi_config_en.properties"));
         System.out.println("PropertyReader load class resrcBundle: " + resrcBundle);
      } catch (Exception var1) {
      }

   }

   private PropertyReader() {
   }

   public static PropertyReader getInstance() {
      if (propertyReader == null) {
         propertyReader = new PropertyReader();
      }

      return propertyReader;
   }

   public String getProperty(Locale selectedlanguage, String key) throws Exception {
      String propertyValue = "";

      try {
         propertyValue = (String)resrcBundle.getObject(key);
         return propertyValue;
      } catch (Exception var5) {
         var5.printStackTrace();
         throw var5;
      }
   }

   public String getPropertyByKey(String key) throws Exception {
      String propertyValue = "";

      try {
         propertyValue = (String)resrcBundle.getObject(key);
         return propertyValue;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw var4;
      }
   }

   public HashMap getPropertyByMap(HashMap map) throws Exception {
      String key = "";
      String value = "";
      Set keyset = map.keySet();
      Iterator itr = keyset.iterator();

      while(itr.hasNext()) {
         key = (String)itr.next();
         value = this.getPropertyByKey(key);
         map.put(key, value);
      }

      return map;
   }
}
