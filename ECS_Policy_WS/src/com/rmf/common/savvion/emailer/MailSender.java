package com.rmf.common.savvion.emailer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

public class MailSender {
   public static final int MAIL_SUCCESS = 1;
   public static final int MAIL_ARCHIVE_FAILED = 2;
   public static final int MAIL_ATTACHMENT_FAILED = 4;
   public static final int MAIL_FAILED = 8;
   private Logger logger = EmailSenderPropertyReader.getLogger();
   private boolean bAttachmentFailed = false;

   public int sendMail(String sTomailID, String sCCmailID, String sBCCmailID, Hashtable fromDetails, StringBuffer sbMessage, String sSubject, String[] sAttachmentFilePath, String sArchiveFilePath, boolean forceSendMail, boolean bRetry, String senderName) {
      System.out.println("IN SEND MAIL*******************");
      int returnVal = 0;
      MimeMessage msg = null;
      int iNumOfRetries = 0;
      boolean isMailSent = false;
      Session session = null;
      String sSenderMailID = null;
      this.logger.debug("is weblogic session" + EmailSenderPropertyReader.isWeblogicSession());
      if (EmailSenderPropertyReader.isWeblogicSession()) {
         try {
            Context jndiCntxt = new InitialContext();
            session = (Session)jndiCntxt.lookup(EmailSenderPropertyReader.getWeblogicJNDIName());
            this.logger.debug(" Got Session from WebLogic ---> " + session.toString());
         } catch (NamingException var20) {
            this.logger.debug("Failed to get Mail Session ... " + var20);
         }

         if (fromDetails == null) {
            sSenderMailID = EmailSenderPropertyReader.getSenderEmailID();
            this.logger.debug("MailSender.sendMail(): Sender Email Id = " + sSenderMailID);
         } else {
            sSenderMailID = (String)fromDetails.get("MAILID");
            this.logger.debug("MailSender.sendMail(): User Name = " + sSenderMailID);
         }
      } else {
         Properties props = new Properties();
         System.out.println("PROTOCOL********* " + EmailSenderPropertyReader.getMailSenderProtocol());
         props.setProperty("mail.transport.protocol", EmailSenderPropertyReader.getMailSenderProtocol());
         System.out.println("HOST********* " + EmailSenderPropertyReader.getSenderIPaddress());
         props.put("mail.smtp.host", EmailSenderPropertyReader.getSenderIPaddress());
         if (fromDetails == null) {
            System.out.println("UserName********* " + EmailSenderPropertyReader.getSenderUserName());
            props.setProperty("mail.user", EmailSenderPropertyReader.getSenderUserName());
            System.out.println("PWD********* " + EmailSenderPropertyReader.getSenderPassword());
            props.setProperty("mail.password", EmailSenderPropertyReader.getSenderPassword());
            sSenderMailID = EmailSenderPropertyReader.getSenderEmailID();
            this.logger.debug("MailSender.sendMail(): User Name = " + EmailSenderPropertyReader.getSenderUserName());
            this.logger.debug("MailSender.sendMail(): Sender Email Id = " + sSenderMailID);
         } else {
            sSenderMailID = (String)fromDetails.get("MAILID");
            if (fromDetails.containsKey("USERNAME") && fromDetails.containsKey("PASSWORD")) {
               props.setProperty("mail.user", (String)fromDetails.get("USERNAME"));
               props.setProperty("mail.password", (String)fromDetails.get("PASSWORD"));
               this.logger.debug("MailSender.sendMail(): User Name = " + (String)fromDetails.get("USERNAME"));
            }
         }

         this.logger.debug("EmailSenderPropertyReader.getSenderPortNumber().length() " + EmailSenderPropertyReader.getSenderPortNumber().length());
         if (EmailSenderPropertyReader.getSenderPortNumber() != null && EmailSenderPropertyReader.getSenderPortNumber().length() > 0) {
            props.setProperty("mail.smtp.port", EmailSenderPropertyReader.getSenderPortNumber());
            this.logger.debug("MailSender.sendMail(): PORT = " + EmailSenderPropertyReader.getSenderPortNumber());
         }

         this.logger.debug("MailSender.sendMail(): Sender Mail ID = " + sSenderMailID);
         this.logger.debug("Protocol = " + EmailSenderPropertyReader.getMailSenderProtocol());
         this.logger.debug("MailSender.sendMail(): Host = " + EmailSenderPropertyReader.getSenderIPaddress());
         session = Session.getInstance(props, (Authenticator)null);
         this.logger.debug("MailSender.sendMail(): Got Session = " + session.toString());
         this.logger.debug("MailSender.sendMail(): Is Session Debug ON = " + EmailSenderPropertyReader.isMailSessionDebug());
         this.logger.debug("MailSender.sendMail(): To mail ID" + sTomailID);
         System.out.println("MailSender.sendMail(): To mail ID" + sTomailID);
         this.logger.debug("MailSender.sendMail(): CC mail ID" + sCCmailID);
         session.setDebug(EmailSenderPropertyReader.isMailSessionDebug());
      }

      if (senderName != null && senderName.trim().length() != 0) {
         sSenderMailID = senderName + " <" + sSenderMailID + ">";
      }

      this.logger.debug("Value of sSenderMailID : " + sSenderMailID);

      do {
         try {
            ++iNumOfRetries;
            msg = new MimeMessage(session);
            msg.setSubject(sSubject);
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(sSenderMailID));
            msg.setHeader("Content-Transfert-Encoding", "16\tBit");
            InternetAddress[] Toaddress = InternetAddress.parse(sTomailID, true);
            msg.setRecipients(RecipientType.TO, Toaddress);
            InternetAddress[] BCCaddress;
            if (sCCmailID != null) {
               BCCaddress = InternetAddress.parse(sCCmailID, true);
               msg.setRecipients(RecipientType.CC, BCCaddress);
            }

            if (sBCCmailID != null) {
               BCCaddress = InternetAddress.parse(sBCCmailID, true);
               msg.setRecipients(RecipientType.BCC, BCCaddress);
            }

            msg.setContent(this.setFileAsAttachment(sAttachmentFilePath, sbMessage));
            msg.saveChanges();
            this.logger.debug("From Address from Message Object : " + msg.getFrom().toString());
            if (!this.bAttachmentFailed || forceSendMail) {
               this.logger.debug("\n\n\n\n Going to send mail...");
               Transport.send(msg);
            }

            returnVal = 1;
            isMailSent = true;
            System.out.println("MAIL STATUS************ " + returnVal);
         } catch (MessagingException var22) {
            this.logger.error("Mail could NOT be sent", var22);
            returnVal = 8;
         }
      } while(bRetry && !isMailSent && (iNumOfRetries < EmailSenderPropertyReader.getSenderRetryCount() || EmailSenderPropertyReader.getSenderRetryCount() == -1));

      if (returnVal < 4 && this.bAttachmentFailed) {
         returnVal = 4;
      }

      try {
         if (sArchiveFilePath != null) {
            File emailFile = new File(sArchiveFilePath);
            File dir = emailFile.getParentFile();
            if (!dir.exists()) {
               dir.mkdirs();
               this.logger.error("MailSender.sendMail(): Archival Directory not found. " + dir.getAbsolutePath());
            }

            if (emailFile.createNewFile()) {
               msg.writeTo(new DataOutputStream(new FileOutputStream(emailFile)));
            } else {
               this.logger.error("MailSender.sendMail(): Could NOT create email file. " + emailFile.getAbsolutePath());
            }
         }
      } catch (Exception var21) {
         this.logger.error("MailSender.sendMail(): Could not archive Email Message file " + sArchiveFilePath, var21);
         if (returnVal < 2) {
            returnVal = 2;
         }
      }

      return returnVal;
   }

   private Multipart setFileAsAttachment(String[] sAttachmentFilePath, StringBuffer sbMessage) throws MessagingException {
      Multipart mp = new MimeMultipart();
      MimeBodyPart p1 = new MimeBodyPart();
      p1.setContent(sbMessage.toString(), "text/plain");
      mp.addBodyPart(p1);
      if (sAttachmentFilePath != null) {
         for(int i = 0; i < sAttachmentFilePath.length; ++i) {
            try {
               MimeBodyPart p2 = new MimeBodyPart();
               FileDataSource fds = new FileDataSource(sAttachmentFilePath[i]);
               this.bAttachmentFailed = !fds.getFile().exists();
               if (!this.bAttachmentFailed) {
                  p2.setDataHandler(new DataHandler(fds));
                  p2.setFileName(fds.getName());
                  p2.setDisposition("attachment");
                  mp.addBodyPart(p2);
               } else {
                  this.logger.warn("MailSender.sendMail(): Cannot find the Attachment file.");
               }
            } catch (MessagingException var8) {
               this.bAttachmentFailed = true;
               this.logger.warn("MailSender.sendMail(): Could not make Attachement using URL =" + sAttachmentFilePath, var8);
            }
         }
      }

      return mp;
   }
}
