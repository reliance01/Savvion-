package com.audit.email;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Auto-generated
 */
public class EmailSender {

	// public String host = "10.65.8.47";
	//public String host = "10.185.6.109";
	public String host = "rgismtp.reliancegeneral.co.in";
	// public String host = "10.8.50.117";
	public String port = "25";
	public String user = null;
	public String passwd = null;

	public EmailSender() {
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
		try {
			if (properties != null && !properties.isEmpty()) {
				String to = properties.get("TO").toString();
				String from = properties.get("FROM").toString();
				String subject = properties.get("SUBJECT").toString();
				String body = properties.get("BODY").toString();

				// Create properties for the Session
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				Session session = Session.getInstance(props);
				Transport bus = session.getTransport("smtp");
				bus.connect();
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
						to));
				InternetAddress[] addressCC = null;

				if (properties.containsKey("CC")) {
					String[] ccEmailIds = (String[]) properties.get("CC");
					if (ccEmailIds.length > 0) {
						addressCC = new InternetAddress[ccEmailIds.length];
						int counter = 0;
						for (String recipient : ccEmailIds) {
							if (recipient != null && recipient.contains("@")) {
								addressCC[counter] = new InternetAddress(
										recipient.trim());
								counter++;
							}
						}
						msg.setRecipients(Message.RecipientType.CC, addressCC);
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
		} catch (Exception mex) {
			throw new RuntimeException(mex);
		}
	}

	public void sendMutliple_To(HashMap properties) {
		try {
			if (properties != null && !properties.isEmpty()) {
				String[] to = (String[]) properties.get("TO");
				String from = properties.get("FROM").toString();
				String subject = properties.get("SUBJECT").toString();
				String body = properties.get("BODY").toString();
				// Create properties for the Session
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				Session session = Session.getInstance(props);
				Transport bus = session.getTransport("smtp");
				bus.connect();
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] addressTo = null;
				InternetAddress[] addressCC = null;
				if (to.length > 0) {
					addressTo = new InternetAddress[to.length];
					int counter = 0;
					for (String recipient : to) {
						if (recipient != null && recipient.contains("@")) {
							addressTo[counter] = new InternetAddress(recipient
									.trim());
							counter++;
						}
					}
					msg.setRecipients(Message.RecipientType.TO, addressTo);
				}
				if (properties.containsKey("CC")) {
					String[] ccEmailIds = (String[]) properties.get("CC");
					if (ccEmailIds.length > 0) {
						addressCC = new InternetAddress[ccEmailIds.length];
						int counter = 0;
						for (String recipient : ccEmailIds) {
							if (recipient != null && recipient.contains("@")) {
								addressCC[counter] = new InternetAddress(
										recipient.trim());
								counter++;
							}
						}
						msg.setRecipients(Message.RecipientType.CC, addressCC);
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
		} catch (Exception mex) {
			throw new RuntimeException(mex);
		}
	}

	public void sendMailWithAttachment(HashMap properties, String[] fileName) {
		try {
			if (properties != null && !properties.isEmpty()) {
				String[] to = (String[]) properties.get("TO");
				String from = properties.get("FROM").toString();
				String subject = properties.get("SUBJECT").toString();
				String body = properties.get("BODY").toString();
				// Create properties for the Session
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				Session session = Session.getInstance(props);
				Transport bus = session.getTransport("smtp");
				bus.connect();
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] addressTo = null;
				InternetAddress[] addressCC = null;
				if (to.length > 0) {
					addressTo = new InternetAddress[to.length];
					int counter = 0;
					for (String recipient : to) {
						if (recipient != null && recipient.contains("@")) {
							addressTo[counter] = new InternetAddress(recipient
									.trim());
							counter++;
						}
					}
					msg.setRecipients(Message.RecipientType.TO, addressTo);
				}
				if (properties.containsKey("CC")) {
					String[] ccEmailIds = (String[]) properties.get("CC");
					if (ccEmailIds.length > 0) {
						addressCC = new InternetAddress[ccEmailIds.length];
						int counter = 0;
						for (String recipient : ccEmailIds) {
							if (recipient != null && recipient.contains("@")) {
								addressCC[counter] = new InternetAddress(
										recipient.trim());
								counter++;
							}
						}
						msg.setRecipients(Message.RecipientType.CC, addressCC);
					}
				}
				msg.setSubject(subject);
				msg.setSentDate(new Date());
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(body, "text/html");
				MimeMultipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				File file = null;
				if (fileName != null && fileName.length > 0) {
					for (int i = 0; i < fileName.length; i++) {
						file = new File(fileName[i]);
						if (file.exists()) {
							EmailSender.addAttachment(multipart, fileName[i]);
						}
					}
				}
				msg.setContent(multipart);
				msg.saveChanges();
				bus.sendMessage(msg, msg.getAllRecipients());
				bus.close();
			}
		} catch (Exception mex) {
			throw new RuntimeException(mex);
		}
	}

	private static void addAttachment(Multipart multipart, String filename)
			throws Exception {
		MimeBodyPart attachPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(source));
		String[] name = filename.split("/");
		attachPart.setFileName(name[(name.length) - 1]);
		multipart.addBodyPart(attachPart);
	}

}
