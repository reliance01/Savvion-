package com.audit.email;

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

public class EmailSender {
   public String host = "10.65.8.47";
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
            InternetAddress[] address = new InternetAddress[]{new InternetAddress(to)};
            msg.setRecipients(RecipientType.TO, address);
            if (properties.containsKey("CC")) {
               String concatEmailIds = properties.get("CC").toString();
               if (concatEmailIds != null) {
                  String[] splitEmailIds = concatEmailIds.split(",");
                  if (splitEmailIds != null) {
                     for(int i = 0; i < splitEmailIds.length; ++i) {
                        if (splitEmailIds[i] != null && splitEmailIds[i].contains("@")) {
                           msg.addRecipients(RecipientType.CC, splitEmailIds[i]);
                        }
                     }
                  }
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
            bus.sendMessage(msg, address);
            bus.close();
         }
      } catch (Exception var14) {
      }

   }
}
