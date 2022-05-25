package com.rmf.common.savvion.emailer;

import com.rmf.common.savvion.services.RMFServiceLocator;
import com.rmf.savvion.usermanagement.SBMUserManager;
import com.rmf.savvion.util.StaticFuncs;
import com.tdiinc.userManager.Group;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.util.HashMap;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

public class RMFEmailHandler {
   private static final String CLASS_NAME = "RMFEmailHandler";
   private static RMFEmailHandler eHandlerInstance = null;
   public static final String TO = "to";
   public static final String CC = "cc";
   public static final String BCC = "bcc";
   public static final String ATT_FILE_PATH = "attFPath";
   public static final String PROCESS_NAME = "processName";
   public static final String PIID = "piid";
   public static final String WORKSTEP_NAME = "workstepName";
   public static final String TEMPLATE_NUMBER = "templateNumber";
   public static final String SUBJECT = "subject";
   public static final String EMAIL_BODY = "emailBody";
   public static final String SENDER_NAME = "senderName";
   public static final String CUSTOM_ATTR = "customAttr";
   public static final String RMF_QC_FACTORY = "jms/RMFQueueConnectionFactory";
   public static final String RMF_EMAIL_QUEUE = "jms/RMFEmailQueue";
   private Logger logger = EmailSenderPropertyReader.getLogger();

   private RMFEmailHandler() {
   }

   public static RMFEmailHandler getInstance() {
      if (eHandlerInstance == null) {
         eHandlerInstance = new RMFEmailHandler();
      }

      return eHandlerInstance;
   }

   public void sendEmail(HashMap eMap) {
      String METHOD_NAME = "sendEmail";
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 1));
      RMFServiceLocator locator = RMFServiceLocator.getInstance();

      try {
         QueueConnectionFactory queueConnFactory = locator.getQueueConnectionFactory("jms/RMFQueueConnectionFactory");
         QueueConnection conn = queueConnFactory.createQueueConnection();
         conn.start();
         QueueSession session = conn.createQueueSession(false, 1);
         Queue queue = locator.getQueue("jms/RMFEmailQueue");
         QueueSender sender = session.createSender(queue);
         ObjectMessage message = session.createObjectMessage(eMap);
         this.logger.debug("BEFORE MESSAGE SEND****************");
         sender.send(message);
         this.logger.debug("AFTER MESSAGE SEND****************");
         this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 0, "Message sent to Queue with the following details : " + eMap));
      } catch (NamingException var10) {
         this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 0, "Error while sending email with the following details " + eMap), var10);
      } catch (JMSException var11) {
         this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 0, "Error while sending email with the following details " + eMap), var11);
      } catch (Exception var12) {
         this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 0, "Error while sending email with the following details :" + eMap), var12);
      }

      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmail", 2));
   }

   public void sendEmailD(HashMap eMap) {
      this.logger.debug("IN SEND EMAILID***********************");
      String METHOD_NAME = "sendEmailD";
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 1));
      this.logger.debug(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Message object found as : " + eMap));
      String[] to = (String[])eMap.get("to");
      String[] cc = (String[])eMap.get("cc");
      String[] bcc = (String[])eMap.get("bcc");
      String subject = (String)eMap.get("subject");
      String emailBody = (String)eMap.get("emailBody");
      String processName = (String)eMap.get("processName");
      String workstepName = (String)eMap.get("workstepName");
      Long piid = (Long)eMap.get("piid");
      String senderName = (String)eMap.get("senderName");
      String[] attFPath = (String[])eMap.get("attFPath");
      Integer templateNumber = (Integer)eMap.get("templateNumber");
      HashMap customAttr = (HashMap)((HashMap)eMap.get("customAttr"));
      String toEmailIds = this.getRecipientEmailIds(to);
      String ccEmailIds = this.getRecipientEmailIds(cc);
      String bccEmailIds = this.getRecipientEmailIds(bcc);
      this.logger.debug(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Mail Recipients Found as:\nTo : " + toEmailIds + "\nCC" + ccEmailIds + "\nBCC" + bccEmailIds));
      CustomEmailer cEmailer = new CustomEmailer();
      System.out.println("cEmailer******************* " + cEmailer);
      boolean initStatus = cEmailer.init(processName, piid, workstepName, templateNumber, customAttr);
      System.out.println("INIT STATUS********** " + initStatus);
      this.logger.debug(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Custom Emailer Init Status : " + initStatus));
      StringBuffer sb = null;
      if (initStatus) {
         if (emailBody != null && emailBody.trim().length() != 0) {
            sb = new StringBuffer(emailBody);
         } else {
            sb = cEmailer.getMailBody();
         }

         MailSender mSender = new MailSender();
         System.out.println("MAIL SENDER*************** " + mSender);
         int sendEmailStatus = mSender.sendMail(toEmailIds, ccEmailIds, bccEmailIds, (Hashtable)null, sb, subject, attFPath, (String)null, true, true, senderName);
         System.out.println("SENDEmailStatus************ " + sendEmailStatus);
         this.logger.debug(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Send email status ***********: " + sendEmailStatus));
         this.logger.debug(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Email Sent to **************: " + toEmailIds));
      } else {
         this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 0, "Custom Emailer not initialied..."));
      }

      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "sendEmailD", 2));
   }

   private String getGroupEmailIds(String grpName) {
      String METHOD_NAME = "getGroupEmailIds";
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getGroupEmailIds", 1));
      SBMUserManager sbmUserManager = new SBMUserManager();
      String[] users = sbmUserManager.getGroupUsers(grpName);
      StringBuilder emailIds = new StringBuilder();

      for(int i = 0; i < users.length; ++i) {
         User user = sbmUserManager.getSBMUser(users[i]);
         if (user != null) {
            if (i > 0) {
               emailIds.append(",");
            }

            emailIds.append(user.getAttribute("email"));
         }
      }

      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getGroupEmailIds", 0, "GROUP", grpName, "Email Ids", emailIds.toString()));
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getGroupEmailIds", 2));
      return emailIds.toString();
   }

   private String getRecipientEmailIds(String[] recipients) {
      String METHOD_NAME = "getRecipientEmailIds";
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getRecipientEmailIds", 1));
      StringBuilder emailIds = new StringBuilder();
      String recipientIds = null;
      if (recipients != null && recipients.length > 0) {
         for(int i = 0; i < recipients.length; ++i) {
            String recipient = recipients[i];
            if (this.isEmailId(recipient)) {
               emailIds.append(recipient);
            } else {
               Realm realm = UserManager.getDefaultRealm();
               Group grp = realm.getGroup(recipient);
               if (grp != null) {
                  recipientIds = this.getGroupEmailIds(recipient);
               } else {
                  User user = realm.getUser(recipient);
                  if (user != null) {
                     recipientIds = user.getAttribute("email");
                  }
               }

               if (recipientIds != null && recipientIds.trim().length() > 0) {
                  emailIds.append(recipientIds);
               }
            }

            if (i < recipients.length - 1) {
               emailIds.append(",");
            }
         }
      }

      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getRecipientEmailIds", 0, "Recipient Email Ids", emailIds.toString()));
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "getRecipientEmailIds", 2));
      return emailIds.toString();
   }

   private boolean isEmailId(String text) {
      String METHOD_NAME = "isEmailId";
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "isEmailId", 1));
      boolean result = false;
      if (text != null && text.trim().length() > 0) {
         try {
            new InternetAddress(text, true);
            result = true;
         } catch (AddressException var5) {
         }
      }

      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "isEmailId", 0, text, String.valueOf(result)));
      this.logger.error(StaticFuncs.buildLogStatement("RMFEmailHandler", "isEmailId", 2));
      return result;
   }
}
