package com.rel.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSendDemoByWindowSchedular {
   private String host = "10.185.6.109";

   public void getMailTemplete() {
   }

   private void send(HashMap properties) {
      try {
         if (properties != null && !properties.isEmpty()) {
            String to = properties.get("TO").toString();
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
            msg.setRecipient(RecipientType.TO, new InternetAddress(to));
            InternetAddress[] addressCC = (InternetAddress[])null;
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
         }

      } catch (Exception var17) {
         throw new RuntimeException(var17);
      }
   }
}
