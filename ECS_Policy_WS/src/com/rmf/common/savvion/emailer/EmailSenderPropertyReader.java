package com.rmf.common.savvion.emailer;

import com.savvion.sbm.util.PService;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class EmailSenderPropertyReader {
   private static String xmlFileLocation;
   private static int senderRetryTime;
   private static int senderRetryCount;
   private static String senderEmailID;
   private static String mailSenderProtocol;
   private static String senderIPaddress;
   private static String senderPortNumber;
   private static String senderUserName;
   private static String senderPassword;
   private static String bizlogicUserName;
   private static String bizlogicPassword;
   private static boolean MailSessionDebug = false;
   private static boolean weblogicSession = false;
   private static String weblogicJNDIName;
   private static String dateFormat;
   private static String sNoValue;
   private static String propertiesFileName = "email.properties";
   private static Logger logger;
   private static String docserviceloc;
   private static String pdfLocationDirectory;
   private static boolean isPropertiesRead = false;

   static {
      readProperties();
   }

   private static void readProperties() {
      Properties logProp = new Properties();

      try {
         logProp.load(ClassLoader.getSystemResourceAsStream("RMFCustomSBMLogger.properties"));
         PropertyConfigurator.configure(logProp);
      } catch (IOException var7) {
         System.out.println("EmailSenderPropertyReader :: readProperties : [ E R R O R   W H I L E   L O A D I N G   R  M F C u s t o m S B M L o g g e r   P R O P E R T I E S ]");
         var7.printStackTrace();
      }

      logger = Logger.getLogger("RMFSBMEmailer");
      if (!isPropertiesRead) {
         logger.error("EmailSenderPropertyReader.readProperties(): Strart reading property file - " + propertiesFileName);
         Properties prop = new Properties();

         try {
            prop.load(ClassLoader.getSystemResourceAsStream(propertiesFileName));
         } catch (IOException var6) {
            logger.error("EmailSenderPropertyReader.readProperties() - Error reading configuration file " + propertiesFileName, var6);
         }

         xmlFileLocation = prop.getProperty("emailer.xml.file.location").trim();
         logger.debug("emailer.xml.file.location=" + xmlFileLocation);
         weblogicJNDIName = prop.getProperty("email.jndi.name").trim();
         String isWeblogicSession = prop.getProperty("email.isWeblogicSession").trim();
         if (isWeblogicSession.equalsIgnoreCase("true")) {
            weblogicSession = true;
         }

         logger.debug("email.isWeblogicSession=" + weblogicSession);

         try {
            senderRetryTime = new Integer(prop.getProperty("email.sender.retry.time").trim());
         } catch (NumberFormatException var5) {
            senderRetryTime = 15;
            logger.error("EmailSenderPropertyReader.readProperties() - Proterty - email.sender.retry.time - is not a number.", var5);
         }

         logger.debug("email.sender.retry.time=" + senderRetryTime);

         try {
            senderRetryCount = new Integer(prop.getProperty("mail.sender.retry.count").trim());
         } catch (NumberFormatException var4) {
            senderRetryCount = 3;
            logger.error("EmailSenderPropertyReader.readProperties() - Proterty - mail.receiver.reader.batch - is not a number.", var4);
         }

         logger.debug("mail.sender.retry.count=" + senderRetryCount);
         mailSenderProtocol = prop.getProperty("mail.sender.protocol").trim();
         logger.debug("mail.sender.protocol=" + mailSenderProtocol);
         senderIPaddress = prop.getProperty("sender.ipaddress").trim();
         logger.debug("sender.ipaddress=" + senderIPaddress);
         senderPortNumber = prop.getProperty("sender.portnumber").trim();
         logger.debug("sender.portnumber=" + senderPortNumber);
         senderEmailID = prop.getProperty("sender.mailid").trim();
         logger.debug("sender.mailid=" + senderEmailID);
         senderUserName = "";
         logger.debug("sender.username=" + senderUserName);
         senderPassword = "";
         logger.debug("sender.password=" + prop.getProperty("sender.password").trim());
         bizlogicUserName = prop.getProperty("bizlogic.username").trim();
         logger.debug("bizlogic.username=" + bizlogicUserName);
         bizlogicPassword = PService.self().decrypt(prop.getProperty("bizlogic.password").trim());
         String sessionDebug = prop.getProperty("mail.session.debug").trim();
         if (sessionDebug.equals("true")) {
            MailSessionDebug = true;
         }

         logger.debug("mail.session.debug=" + MailSessionDebug);
         dateFormat = prop.getProperty("email.date.format").trim();
         logger.debug("email.date.format=" + dateFormat);
         sNoValue = prop.getProperty("email.placeholder.value.not.available");
         if (sNoValue != null) {
            sNoValue = sNoValue.trim();
         }

         logger.debug("email.placeholder.value.not.available=" + sNoValue);
         docserviceloc = prop.getProperty("docservice.docstore.location");
         if (docserviceloc != null) {
            docserviceloc = docserviceloc.trim();
         }

         logger.debug("docservice.docstore.location=" + docserviceloc);
         pdfLocationDirectory = prop.getProperty("pdflocation.directory");
         if (pdfLocationDirectory != null) {
            pdfLocationDirectory = pdfLocationDirectory.trim();
         }

         logger.debug("pdflocation.directory=" + pdfLocationDirectory);
         isPropertiesRead = true;
         logger.info("EmailSenderPropertyReader.readProperties() - Property File Read");
      }
   }

   public static String getBizlogicPassword() {
      return bizlogicPassword;
   }

   public static String getBizlogicUserName() {
      return bizlogicUserName;
   }

   public static boolean isPropertiesRead() {
      return isPropertiesRead;
   }

   public static Logger getLogger() {
      if (logger == null) {
         logger = Logger.getLogger("RMFSBMEmailer");
      }

      return logger;
   }

   public static String getMailSenderProtocol() {
      return mailSenderProtocol;
   }

   public static boolean isMailSessionDebug() {
      return MailSessionDebug;
   }

   public static String getPropertiesFileName() {
      return propertiesFileName;
   }

   public static String getSenderEmailID() {
      return senderEmailID;
   }

   public static String getSenderIPaddress() {
      return senderIPaddress;
   }

   public static String getSenderPassword() {
      return senderPassword;
   }

   public static String getSenderPortNumber() {
      return senderPortNumber;
   }

   public static int getSenderRetryCount() {
      return senderRetryCount;
   }

   public static int getSenderRetryTime() {
      return senderRetryTime;
   }

   public static String getSenderUserName() {
      return senderUserName;
   }

   public static String getXmlFileLocation() {
      return xmlFileLocation;
   }

   public static String getDateFormat() {
      return dateFormat;
   }

   public static String getNoValueSubstitute() {
      return sNoValue;
   }

   public static String getDocserviceloc() {
      return docserviceloc;
   }

   public static String getPdfLocationDirectory() {
      return pdfLocationDirectory;
   }

   public static boolean isWeblogicSession() {
      return weblogicSession;
   }

   public static void setWeblogicSession(boolean weblogicSession) {
      EmailSenderPropertyReader.weblogicSession = weblogicSession;
   }

   public static String getWeblogicJNDIName() {
      return weblogicJNDIName;
   }
}
