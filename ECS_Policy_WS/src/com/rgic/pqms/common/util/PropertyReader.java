package com.rgic.pqms.common.util;

import com.rgic.pqms.common.exception.PQMSException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyReader {
   private static PropertyReader propertyReader = null;
   private static ResourceBundle configurationBundleSYS = null;

   private PropertyReader() throws PQMSException, Exception {
      try {
         ClassLoader cLoad = this.getClass().getClassLoader();
         String IS_SYS_PROPERTIES_PATH = "rgi_pqms_config.properties";
         InputStream sysStream = cLoad.getResourceAsStream("rgi_pqms_config.properties");
         configurationBundleSYS = new PropertyResourceBundle(sysStream);
         System.out.println("Prining Keys value Property file name " + configurationBundleSYS.getKeys());
         Enumeration e = configurationBundleSYS.getKeys();

         while(e.hasMoreElements()) {
            System.out.println("Keys name : " + e.nextElement());
         }

      } catch (IOException var5) {
         var5.printStackTrace();
         throw new PQMSException(var5);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new PQMSException(var6);
      }
   }

   public static PropertyReader getInstance() throws PQMSException, Exception {
      try {
         if (propertyReader == null) {
            propertyReader = new PropertyReader();
         }

         return propertyReader;
      } catch (Exception var1) {
         throw new PQMSException(var1);
      }
   }

   public String getSYSProperty(String propertyKey) throws PQMSException, Exception {
      try {
         String strSYSpropertyValue = configurationBundleSYS.getString(propertyKey);
         return strSYSpropertyValue;
      } catch (Exception var3) {
         throw new PQMSException(var3);
      }
   }
}
