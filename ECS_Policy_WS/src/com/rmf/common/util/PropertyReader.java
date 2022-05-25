package com.rmf.common.util;

import com.rmf.savvion.util.StaticFuncs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropertyReader {
   private static String CLASS_NAME = "PropertyReader";
   private static final String PROPERTIES_FILE = "rmfCommon.properties";
   private static Logger dlLogger;
   private static Logger blutilLogger;
   private static String bizlogicUserName;
   private static String bizlogicPassword;
   private static String dataSourceName;
   private static String dbUserName;
   private static String dbPassword;

   static {
      String METHOD_NAME = "Static Block";
      Properties prop = new Properties();

      try {
         prop.load(ClassLoader.getSystemResourceAsStream("RMFCustomSBMLogger.properties"));
         PropertyConfigurator.configure(prop);
         dlLogger = Logger.getLogger("dloader");
         dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "Static Block", 0, "DataLoader Logger Initialized."));
         blutilLogger = Logger.getLogger("blutil");
         blutilLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "Static Block", 0, "BLUtil Logger Initialized."));
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      readProperties();
   }

   public static String getBizlogicPassword() {
      return bizlogicPassword;
   }

   public static void setBizlogicPassword(String bizlogicPassword) {
      PropertyReader.bizlogicPassword = bizlogicPassword;
   }

   public static String getBizlogicUserName() {
      return bizlogicUserName;
   }

   public static void setBizlogicUserName(String bizlogicUserName) {
      PropertyReader.bizlogicUserName = bizlogicUserName;
   }

   public static String getClassName() {
      return CLASS_NAME;
   }

   public static void setClassName(String className) {
      CLASS_NAME = className;
   }

   public static String getDataSourceName() {
      return dataSourceName;
   }

   public static void setDataSourceName(String dataSourceName) {
      PropertyReader.dataSourceName = dataSourceName;
   }

   public static String getDbPassword() {
      return dbPassword;
   }

   public static void setDbPassword(String dbPassword) {
      PropertyReader.dbPassword = dbPassword;
   }

   public static String getDbUserName() {
      return dbUserName;
   }

   public static void setDbUserName(String dbUserName) {
      PropertyReader.dbUserName = dbUserName;
   }

   private static void readProperties() {
      String METHOD_NAME = "readProperties";
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 1));
      Properties prop = null;

      try {
         prop = new Properties();
         prop.load(ClassLoader.getSystemResourceAsStream("rmfCommon.properties"));
      } catch (FileNotFoundException var3) {
         dlLogger.error(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0), var3);
      } catch (IOException var4) {
         dlLogger.error(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0), var4);
      }

      bizlogicUserName = prop.getProperty("bizlogic.username");
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0, "Bizlogic User Name: ", bizlogicUserName));
      bizlogicPassword = prop.getProperty("bizlogic.password");
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0, "Bizlogic Password : -----"));
      dataSourceName = prop.getProperty("db.datasource");
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0, "Data Source Name : ", dataSourceName));
      dbUserName = prop.getProperty("db.username");
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0, "DB User Name : ", dbUserName));
      dbPassword = prop.getProperty("db.password");
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 0, "DB Password : ------"));
      dlLogger.debug(StaticFuncs.buildLogStatement(CLASS_NAME, "readProperties", 2));
   }

   public static Logger getDlLogger() {
      return dlLogger;
   }

   public static void setDlLogger(Logger dlLogger) {
      PropertyReader.dlLogger = dlLogger;
   }

   public static Logger getBlutilLogger() {
      return blutilLogger;
   }

   public static void setBlutilLogger(Logger blutilLogger) {
      PropertyReader.blutilLogger = blutilLogger;
   }
}
