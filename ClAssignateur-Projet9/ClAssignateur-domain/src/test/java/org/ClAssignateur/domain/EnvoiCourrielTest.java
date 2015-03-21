package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import org.junit.Test;

public class EnvoiCourrielTest {

	@Test
	public void testEnvoyerCourriel() throws Throwable {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.debug", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("classignateur@gmail.com", "ul-glo-4002");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("classignateur@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("classignateur@gmail.com"));
			message.setSubject("Prendre de test nouvelles!");
			message.setText("Bonjour toi, comment vas-tu aujourd'hui?");

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
