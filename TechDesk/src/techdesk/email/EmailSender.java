package techdesk.email;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.PropertyConfigurator;

import com.savvion.sbm.util.logger.LoggerManager;
import com.savvion.sbm.util.logger.SBMLogger;

/**
 * Auto-generated
 */
public class EmailSender {
	
	public String host = "rgismtp.reliancegeneral.co.in";//10.65.8.45
	//public String host = "10.185.6.109";
	public String port = "25";
	public String user = null;
	public String passwd = null;
	
	private String jndiName = "jdbc/CustomDB";
	static SBMLogger log = null;
	
	static {
		try {
			PropertyConfigurator.configure(System.getProperty("sbm.home")
					+ "/conf/sbmlog.conf");
			log = LoggerManager.self().createLogger("WebService",
					"sbmlog.conf", "WebServiceMessages",
					EmailSender.class.getClassLoader());
		} catch (Exception ex) {
		}
	}

	public EmailSender() {
	}

	public Connection getDBConnection() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			//connection = ((DataSource) new InitialContext().lookup(jndiName)).getConnection();
			//String dbURL = "jdbc:oracle:thin:savvion2015/Rel1ance@RGIORCLDB:1863:EBMS12C";//10.65.5.175
			String dbURL = "jdbc:oracle:thin:@rgipsaviondb.proddbsubnet.rgiclvcnprod.oraclevcn.com:1521/ebms12c.proddbsubnet.rgiclvcnprod.oraclevcn.com";
			connection = DriverManager.getConnection(dbURL);
		} catch (Exception ex) {
			log.error("Error Getting DBConnection : " + ex.getMessage());			
			log.error(ex);
			//throw new RuntimeException(ex);
		}
		return connection;
	}

	private void insertMailLog(HashMap properties) {
		String to = null;
		// String from = null;
		// String subject = null;
		// String body = null;
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
				// from = properties.get("FROM").toString();
				// subject = properties.get("SUBJECT").toString();
				// body = properties.get("BODY").toString();
				PTID = ((Double)properties.get("PTID")).intValue();
				PIID = ((Double)properties.get("PIID")).intValue();
				workstep_name = properties.get("WORKSTEP_NAME").toString();
				workitem_name = properties.get("WORKITEM_NAME").toString();
				status = properties.get("STATUS").toString();
				msg = properties.get("RESPONSE").toString();

				String qry = "{call SP_EMAIL_LOG(?,?,?,?,?,?,?)}";
				connection = getDBConnection();
				cstmt = connection.prepareCall(qry);
				// cstmt.setString(1, userName.trim());
				cstmt.setInt("PROCESS_TEMPLATE_ID", PTID);
				cstmt.setInt("PROCESS_INSTANCE_ID", PIID);
				cstmt.setString("WORKSTEPNAME", workstep_name);
				cstmt.setString("WORKITEMNAME", workitem_name);
				cstmt.setString("TOEMAILID", to);
				cstmt.setString("MAILSTATUS", status);
				cstmt.setString("MSG", msg);
				// cstmt.registerOutParameter(2, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				cstmt.close();
				connection.close();
			}
		} catch (Exception ex) {
			log.error("Error Inserting email log : " + ex.getMessage());
			log.error(ex);
			//throw new RuntimeException(ex);
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ex1) {
			}
		}
	}

	public EmailSender(String host) {
		this.host = host;
	}
	public EmailSender(java.lang.String host, java.lang.String port,
			java.lang.String user, java.lang.String passwd) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.passwd = passwd;
	}

	public void send(HashMap properties) {
		SBMLogger log = null;
		String Mail_Status = null;
		long piid = 0;
		try {
			PropertyConfigurator.configure(System.getProperty("sbm.home")+"/conf/sbmlog.conf");
			log = LoggerManager.self().createLogger("WebService",
					"sbmlog.conf", "WebServiceMessages",
					this.getClass().getClassLoader());
			
			if (properties != null && !properties.isEmpty()) {
				String to = properties.get("TO").toString();
				String from = properties.get("FROM").toString();
				String subject = properties.get("SUBJECT").toString();
				String body = properties.get("BODY").toString();
				piid = ((Double)properties.get("PIID")).longValue();
				// Create properties for the Session
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				Session session = Session.getInstance(props);
				Transport bus = session.getTransport("smtp");
				bus.connect();
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = null;
				
				if(to!=null && to.contains(",")){
					String[] recipientList = to.split(",");
					address = new InternetAddress[recipientList.length];
					int counter = 0;
					for (String recipient : recipientList) {
						address[counter] = new InternetAddress(recipient.trim());
					    counter++;
					}
					msg.setRecipients(Message.RecipientType.TO, address);
				}
				else{
					address = new InternetAddress[1];
					address[0] = new InternetAddress(to);
					msg.setRecipients(Message.RecipientType.TO, address);
				}
				String CCIDs = "";
				if (properties.containsKey("CC")) {
					String concatEmailIds = properties.get("CC").toString();
					CCIDs = concatEmailIds;
					if (concatEmailIds != null) {
						String[] splitEmailIds = concatEmailIds.split(",");
						if (splitEmailIds != null) {
							for (int i = 0; i < splitEmailIds.length; i++) {
                             if(splitEmailIds[i]!=null && splitEmailIds[i].contains("@"))
                             {                            	 
                            	msg.addRecipients(Message.RecipientType.CC, splitEmailIds[i]);
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
				log.info("Message sent successfully " + to + "with CC "+ CCIDs +" ProcessInstanceId " + piid);
				Mail_Status = "Success";
				properties.put("STATUS", Mail_Status);
				properties.put("RESPONSE", "Success");
				this.insertMailLog(properties);
			}
		} catch (Exception mex) {
			//mex.printStackTrace();
			try {
				Mail_Status = "Error";
				properties.put("STATUS", Mail_Status);
				properties.put("RESPONSE", mex.getMessage());
				insertMailLog(properties);
				log.error("Error sending email " + mex.getMessage());				
				log.error(mex);
				//throw new RuntimeException(mex);
			} catch (Exception ex) {
			}
		}
	}

}
