package com.rgic.pqms.common.logger;

import com.rgic.pqms.common.exception.PQMSException;
import com.rgic.pqms.common.util.PropertyReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PQMSLogger {
   private static HashMap loggerHolder = new HashMap();
   public static final int DEBUG = 1;
   public static final int INFO = 2;
   public static final int WARN = 3;
   public static final int ERROR = 4;
   public static final int FATAL = 5;
   public static Logger logger;

   static {
      try {
         PropertyReader propertyReader = PropertyReader.getInstance();
         String filepath = propertyReader.getSYSProperty("rgic.log4j.path");
         System.out.println(PQMSLogger.class + " filepath  " + filepath);
         PropertyConfigurator.configure(readProperties(filepath));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static Logger getLogger() throws Exception {
      System.out.println("Get Logger called ");
      String loggerName = getLoggerName();
      System.out.println("Logger Name " + loggerName);
      logger = Logger.getLogger(loggerName);
      System.out.println("Returning Get Logger ");
      return logger;
   }

   public static Logger myLogger() {
      return logger;
   }

   public static Logger getLogger(String loggerName) {
      return Logger.getLogger("testicm");
   }

   public static Properties readProperties(String fileName) throws IOException {
      URL url = null;
      int forwardIndex = fileName.indexOf(47);
      int backwardIndex = fileName.indexOf(92);
      if (forwardIndex == -1 && backwardIndex == -1) {
         url = ClassLoader.getSystemResource(fileName);
      } else {
         if (backwardIndex != -1) {
            fileName = fileName.replace('\\', '/');
         }

         File f = new File(fileName);
         url = f.toURL();
      }

      InputStream is = null;
      Properties prop = null;

      try {
         prop = new Properties();
         is = url.openStream();
         prop.load(is);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception var11) {
            }
         }

      }

      return prop;
   }

   private static String getLoggerName() {
      return "pqms";
   }

   public static void log(String logMessage, int logMode) throws Exception {
      if (1 == logMode) {
         getLogger().debug(logMessage);
      } else if (2 == logMode) {
         getLogger().info(logMessage);
      } else if (3 == logMode) {
         getLogger().warn(logMessage);
      } else if (4 == logMode) {
         getLogger().error(logMessage);
      } else if (5 == logMode) {
         getLogger().fatal(logMessage);
      }

   }

   public static void debug(Object c, String logMessage) throws Exception {
      getLogger().debug(new Date() + "  " + c.getClass() + "  " + logMessage);
   }

   public static void printException(Exception exception) throws Exception, PQMSException {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      exception.printStackTrace(pw);
      System.out.println("Print Message");
      System.out.println(sw.getBuffer());
      getLogger().debug(sw.getBuffer());
      System.out.println("Print Message");
   }
}
