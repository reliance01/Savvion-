package com.rmf.savvion.util;

import com.savvion.sbm.util.PService;
import com.tdiinc.common.Emailer.SendEmail;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ForgotPassword {
   private static JDBCRealm jdbcRealm = new JDBCRealm();
   private static String mailServer = null;
   private static String adminEmail = null;
   private static String PROPERTIES_FILE = "sbm.conf";

   private static void readProperties() throws Exception {
      Properties prop = null;

      try {
         prop = new Properties();
         prop.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
         mailServer = prop.getProperty("sbm.smtp.host");
         adminEmail = prop.getProperty("sbm.admin.email.id");
      } catch (FileNotFoundException var2) {
         throw var2;
      } catch (IOException var3) {
         throw var3;
      }
   }

   private static String createMsgBody(String userName, String password) {
      String msgBody = null;
      StringBuffer sb = new StringBuffer();
      sb.append("Dear Savvion user,");
      sb.append("\n\n");
      sb.append("Username: " + userName);
      sb.append("\n");
      sb.append("Password: " + password);
      sb.append("\n\n");
      sb.append("This is a system generated mail. Please do not reply to this mail. \n\n");
      sb.append("Thanks and Regards, \n");
      sb.append("Administrator");
      msgBody = sb.toString();
      return msgBody;
   }

   public static boolean sendMail(String emailID, String subject, String userName, String passWord) throws Exception {
      String methodName = "sendMail";
      boolean result = false;
      readProperties();
      SendEmail sendEmail = new SendEmail(mailServer);
      String msgBody = createMsgBody(userName, passWord);
      sendEmail.setSubject(subject);
      sendEmail.appendText(msgBody);
      sendEmail.setMailFrom(adminEmail);
      sendEmail.addMailTo(emailID);

      try {
         sendEmail.send();
         result = true;
         return result;
      } catch (Exception var9) {
         throw var9;
      }
   }

   public static boolean sendPassword(String userID) throws Exception {
      String password = null;
      String decriptedPass = null;
      String methodName = "getPInfo";
      String email = null;
      boolean result = false;
      JDBCUser jdbcUser = (JDBCUser)jdbcRealm.getUser(userID);
      if (jdbcUser != null) {
         password = jdbcUser.getAttribute("PASSWORD");
         decriptedPass = PService.self().decrypt(password);
         email = jdbcUser.getAttribute("EMAIL");
         result = sendMail(email, "Savvion Portal Password", userID, decriptedPass);
         return result;
      } else {
         throw new RuntimeException("Not a valid user");
      }
   }

   public static void main(String[] args) throws Exception {
      sendPassword("ebmsa");
   }
}
