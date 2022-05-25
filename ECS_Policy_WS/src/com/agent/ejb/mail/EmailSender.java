package com.agent.ejb.mail;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
   public String host = "rgismtp.reliancegeneral.co.in";
   public String port = "25";
   public String user = null;
   public String passwd = null;

   public EmailSender() {
   }

   public EmailSender(String host) {
      this.host = host;
   }

   public EmailSender(String host, String port, String user, String passwd) {
      this.host = host;
      this.port = port;
      this.user = user;
      this.passwd = passwd;
   }

   public void send(HashMap properties) {
      String to = "";
      String from = "";
      String subject = "";
      String body = "";

      try {
         if (properties != null && !properties.isEmpty()) {
            to = properties.get("TO").toString();
            from = properties.get("FROM").toString();
            subject = properties.get("SUBJECT").toString();
            body = properties.get("BODY").toString();
            Properties props = new Properties();
            props.put("mail.smtp.host", this.host);
            Session session = Session.getInstance(props);
            Transport bus = session.getTransport("smtp");
            bus.connect();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(RecipientType.TO, new InternetAddress(to));
            InternetAddress[] addressCC = null;
            if (properties.containsKey("CC")) {
               String[] ccEmailIds = (String[])properties.get("CC");
               if (ccEmailIds.length > 0) {
                  addressCC = new InternetAddress[ccEmailIds.length];
                  int counter = 0;
                  String[] var16 = ccEmailIds;
                  int var15 = ccEmailIds.length;

                  for(int var14 = 0; var14 < var15; ++var14) {
                     String recipient = var16[var14];
                     if (recipient != null && recipient.contains("@")) {
                        addressCC[counter] = new InternetAddress(recipient.trim());
                        ++counter;
                     }
                  }

                  msg.setRecipients(RecipientType.CC, addressCC);
               }
            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            MimeMultipart multipart = new MimeMultipart("related");
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(body, "text/html");
            multipart.addBodyPart(htmlPart);
            msg.setContent(multipart);
            msg.saveChanges();
            bus.sendMessage(msg, msg.getAllRecipients());
            bus.close();
            System.out.println("Mail Sent Sucessfully!!!");
         }
      } catch (Exception var17) {
         Mailer obj = new Mailer();
         obj.saveUnsentMail(to, "", subject, body, var17.getMessage());
         System.out.println("Error while sending mail EmailSender Class " + var17.getMessage());
      }

   }

   public void sendMutliple_To(HashMap properties) {
      try {
         if (properties != null && !properties.isEmpty()) {
            String[] to = (String[])properties.get("TO");
            String from = properties.get("FROM").toString();
            String subject = properties.get("SUBJECT").toString();
            String body = properties.get("BODY").toString();
            Properties props = new Properties();
            props.put("mail.smtp.host", this.host);
            Session session = Session.getInstance(props);
            Transport bus = session.getTransport("smtp");
            bus.connect();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] addressTo = null;
            InternetAddress[] addressCC = null;
            int var15;
            if (to.length > 0) {
               addressTo = new InternetAddress[to.length];
               int counter = 0;
               String[] var16 = to;
               var15 = to.length;

               for(int var14 = 0; var14 < var15; ++var14) {
                  String recipient = var16[var14];
                  if (recipient != null && recipient.contains("@")) {
                     addressTo[counter] = new InternetAddress(recipient.trim());
                     ++counter;
                  }
               }

               msg.setRecipients(RecipientType.TO, addressTo);
            }

            if (properties.containsKey("CC")) {
               String[] ccEmailIds = (String[])properties.get("CC");
               if (ccEmailIds.length > 0) {
                  addressCC = new InternetAddress[ccEmailIds.length];
                  int counter = 0;
                  String[] var17 = ccEmailIds;
                  int var24 = ccEmailIds.length;

                  for(var15 = 0; var15 < var24; ++var15) {
                     String recipient = var17[var15];
                     if (recipient != null && recipient.contains("@")) {
                        addressCC[counter] = new InternetAddress(recipient.trim());
                        ++counter;
                     }
                  }

                  msg.setRecipients(RecipientType.CC, addressCC);
               }
            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            MimeMultipart multipart = new MimeMultipart("related");
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(body, "text/html");
            multipart.addBodyPart(htmlPart);
            msg.setContent(multipart);
            msg.saveChanges();
            bus.sendMessage(msg, msg.getAllRecipients());
            bus.close();
         }

      } catch (Exception var18) {
         throw new RuntimeException(var18);
      }
   }

   public void sendMailWithAttachment(HashMap properties, String[] fileName) {
      try {
         if (properties != null && !properties.isEmpty()) {
            String[] to = (String[])properties.get("TO");
            String from = properties.get("FROM").toString();
            String subject = properties.get("SUBJECT").toString();
            String body = properties.get("BODY").toString();
            Properties props = new Properties();
            props.put("mail.smtp.host", this.host);
            Session session = Session.getInstance(props);
            Transport bus = session.getTransport("smtp");
            bus.connect();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] addressTo = null;
            InternetAddress[] addressCC = null;
            int i;
            if (to.length > 0) {
               addressTo = new InternetAddress[to.length];
               int counter = 0;
               String[] var17 = to;
               i = to.length;

               for(int var15 = 0; var15 < i; ++var15) {
                  String recipient = var17[var15];
                  if (recipient != null && recipient.contains("@")) {
                     addressTo[counter] = new InternetAddress(recipient.trim());
                     ++counter;
                  }
               }

               msg.setRecipients(RecipientType.TO, addressTo);
            }

            String recipient;
            if (properties.containsKey("CC")) {
               String[] ccEmailIds = (String[])properties.get("CC");
               if (ccEmailIds.length > 0) {
                  addressCC = new InternetAddress[ccEmailIds.length];
                  int counter = 0;
                  String[] var18 = ccEmailIds;
                  int var26 = ccEmailIds.length;

                  for(i = 0; i < var26; ++i) {
                     recipient = var18[i];
                     if (recipient != null && recipient.contains("@")) {
                        addressCC[counter] = new InternetAddress(recipient.trim());
                        ++counter;
                     }
                  }

                  msg.setRecipients(RecipientType.CC, addressCC);
               }
            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            recipient = null;
            if (fileName != null && fileName.length > 0) {
               for(i = 0; i < fileName.length; ++i) {
                  File file = new File(fileName[i]);
                  if (file.exists()) {
                     addAttachment(multipart, fileName[i]);
                  }
               }
            }

            msg.setContent(multipart);
            msg.saveChanges();
            bus.sendMessage(msg, msg.getAllRecipients());
            bus.close();
         }

      } catch (Exception var19) {
         throw new RuntimeException(var19);
      }
   }

   private static void addAttachment(Multipart multipart, String filename) throws Exception {
      MimeBodyPart attachPart = new MimeBodyPart();
      DataSource source = new FileDataSource(filename);
      attachPart.setDataHandler(new DataHandler(source));
      String[] name = filename.split("/");
      attachPart.setFileName(name[name.length - 1]);
      multipart.addBodyPart(attachPart);
   }
}
