package org.ClAssignateur.domain;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class NotificateurCourriel implements Notificateur {
	
	
	
	private static final String UTILISATEUR_SMTP = "";
	private static final String MOT_DE_PASSE_SMTP = "";
	private static final String EXPEDITEUR_SMTP = "pascal.labb129@gmail.com";
	private static final String SERVEUR_SMTP = "smtp.gmail.com";
    
	public void notifier(MessageNotification message, Employe destinataire)  {
		try{
			Message messageMime = new MimeMessage(getSession());
			messageMime.addRecipient(RecipientType.TO, new InternetAddress(destinataire.getCourriel()));
			messageMime.addFrom(new InternetAddress[] { new InternetAddress(EXPEDITEUR_SMTP) });
			messageMime.setSubject("Notification ClAssignateur");
			messageMime.setContent(message.genereMessage(), "text/plain");
			
			Transport.send(messageMime);	
			
		 }catch (MessagingException messagingException) {
			 messagingException.printStackTrace();
	      }
	}

	
	
	private Session getSession() {
		
		Authentificateur authenticateur = new Authentificateur();
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.host", SERVEUR_SMTP);
		properties.put("mail.smtp.port", "587");

		return Session.getInstance(properties, authenticateur);
	}

	private class Authentificateur extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authentificateur() {
			authentication = new PasswordAuthentication(UTILISATEUR_SMTP, MOT_DE_PASSE_SMTP);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
	
}