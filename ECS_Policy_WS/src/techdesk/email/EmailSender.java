package techdesk.email;

import com.savvion.sbm.util.logger.LoggerManager;
import com.savvion.sbm.util.logger.SBMLogger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
import oracle.jdbc.OracleDriver;
import org.apache.log4j.PropertyConfigurator;

public class EmailSender {
   public String host = "rgismtp.reliancegeneral.co.in";
   public String port = "25";
   public String user = null;
   public String passwd = null;
   private String jndiName = "jdbc/CustomDB";
   static SBMLogger log = null;

   static {
      try {
         PropertyConfigurator.configure(System.getProperty("sbm.home") + "/conf/sbmlog.conf");
         log = LoggerManager.self().createLogger("WebService", "sbmlog.conf", "WebServiceMessages", EmailSender.class.getClassLoader());
      } catch (Exception var1) {
      }

   }

   public EmailSender() {
   }

   public Connection getDBConnection() {
      Connection connection = null;

      try {
         DriverManager.registerDriver(new OracleDriver());
         String dbURL = "jdbc:oracle:thin:savvion2015/Rel1ance@RGIORCLDB:1863:EBMS12C";
         connection = DriverManager.getConnection(dbURL);
      } catch (Exception var3) {
         log.error("Error Getting DBConnection : " + var3.getMessage());
         log.error(var3);
      }

      return connection;
   }

   private void insertMailLog(HashMap properties) {
      String to = null;
      int PTID = 0;
      int PIID = 0;
      String workstep_name = null;
      String workitem_name = null;
      String status = null;
      String msg = null;
      Connection connection = null;
      CallableStatement cstmt = null;

      try {
         if (properties != null && !properties.isEmpty()) {
            to = properties.get("TO").toString();
            PTID = ((Double)properties.get("PTID")).intValue();
            PIID = ((Double)properties.get("PIID")).intValue();
            workstep_name = properties.get("WORKSTEP_NAME").toString();
            workitem_name = properties.get("WORKITEM_NAME").toString();
            status = properties.get("STATUS").toString();
            msg = properties.get("RESPONSE").toString();
            String qry = "{call SP_EMAIL_LOG(?,?,?,?,?,?,?)}";
            connection = this.getDBConnection();
            cstmt = connection.prepareCall(qry);
            cstmt.setInt("PROCESS_TEMPLATE_ID", PTID);
            cstmt.setInt("PROCESS_INSTANCE_ID", PIID);
            cstmt.setString("WORKSTEPNAME", workstep_name);
            cstmt.setString("WORKITEMNAME", workitem_name);
            cstmt.setString("TOEMAILID", to);
            cstmt.setString("MAILSTATUS", status);
            cstmt.setString("MSG", msg);
            cstmt.executeUpdate();
            cstmt.close();
            connection.close();
         }
      } catch (Exception var14) {
         log.error("Error Inserting email log : " + var14.getMessage());
         log.error(var14);

         try {
            if (cstmt != null) {
               cstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var13) {
         }
      }

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
      SBMLogger log = null;
      String Mail_Status = null;
      long piid = 0L;

      try {
         PropertyConfigurator.configure(System.getProperty("sbm.home") + "/conf/sbmlog.conf");
         log = LoggerManager.self().createLogger("WebService", "sbmlog.conf", "WebServiceMessages", this.getClass().getClassLoader());
         if (properties != null && !properties.isEmpty()) {
            String to = properties.get("TO").toString();
            String from = properties.get("FROM").toString();
            String subject = properties.get("SUBJECT").toString();
            String body = properties.get("BODY").toString();
            piid = ((Double)properties.get("PIID")).longValue();
            Properties props = new Properties();
            props.put("mail.smtp.host", this.host);
            Session session = Session.getInstance(props);
            Transport bus = session.getTransport("smtp");
            bus.connect();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = null;
            int i;
            if (to != null && to.contains(",")) {
               String[] recipientList = to.split(",");
               address = new InternetAddress[recipientList.length];
               int counter = 0;
               String[] var20 = recipientList;
               int var19 = recipientList.length;

               for(i = 0; i < var19; ++i) {
                  String recipient = var20[i];
                  address[counter] = new InternetAddress(recipient.trim());
                  ++counter;
               }

               msg.setRecipients(RecipientType.TO, address);
            } else {
               address = new InternetAddress[]{new InternetAddress(to)};
               msg.setRecipients(RecipientType.TO, address);
            }

            String CCIDs = "";
            if (properties.containsKey("CC")) {
               String concatEmailIds = properties.get("CC").toString();
               CCIDs = concatEmailIds;
               if (concatEmailIds != null) {
                  String[] splitEmailIds = concatEmailIds.split(",");
                  if (splitEmailIds != null) {
                     for(i = 0; i < splitEmailIds.length; ++i) {
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
            log.info("Message sent successfully " + to + "with CC " + CCIDs + " ProcessInstanceId " + piid);
            Mail_Status = "Success";
            properties.put("STATUS", Mail_Status);
            properties.put("RESPONSE", "Success");
            this.insertMailLog(properties);
         }
      } catch (Exception var22) {
         Exception mex = var22;

         try {
            Mail_Status = "Error";
            properties.put("STATUS", Mail_Status);
            properties.put("RESPONSE", mex.getMessage());
            this.insertMailLog(properties);
            log.error("Error sending email " + mex.getMessage());
            log.error(mex);
         } catch (Exception var21) {
         }
      }

   }
}
