package org.ClAssignateur.notificationCourriel;

import org.ClAssignateur.domain.notification.NotificationException;
import org.ClAssignateur.domain.notification.NotificationStrategie;

import javax.mail.Authenticator;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.ClAssignateur.domain.groupe.AdresseCourriel;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.notificationCourriel.configuration.ConfigurationSmtp;

public class NotificationStrategieCourrielSsl implements NotificationStrategie {
	private final String PROPRIETE_PORT_SMTP = "mail.smtp.port";
	private final String PROPRIETE_SERVEUR_SMTP = "mail.smtp.host";
	private final String PROPRIETE_SOCKET_FACTORY = "mail.smtp.socketFactory.class";
	private final String PROPRIETE_AUTHENTIFICATION_ACTIVE = "mail.smtp.auth";
	private final String VALEUR_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private final Boolean VALEUR_AUTHENTIFICATION_ACTIVE = true;
	private final Integer VALEUR_PORT_SMTP_SSL = 465;
	private final String SUJET_COURRIEL = "Notification de la part de ClAssignateur";

	private ConfigurationSmtp configSmtp;

	public NotificationStrategieCourrielSsl(ConfigurationSmtp configSmtp) {
		this.configSmtp = configSmtp;
	}

	public void notifier(String contenu, Employe destinataire) throws NotificationException {
		try {
			envoyerCourriel(contenu, destinataire);
		} catch (MessagingException e) {
			throw new NotificationException("Une erreur est survenue lors de l'envoi du courriel.", e);
		}
	}

	private void envoyerCourriel(String contenu, Employe destinataire) throws MessagingException {
		Session sessionSmtp = configurerSessionSmtp();
		Message message = creerMessage(sessionSmtp, destinataire.courriel, contenu);
		Transport.send(message);
	}

	private Session configurerSessionSmtp() {
		Properties props = new Properties();
		props.put(PROPRIETE_SERVEUR_SMTP, configSmtp.getAdresseServeurSmtp());
		props.put(PROPRIETE_SOCKET_FACTORY, VALEUR_SOCKET_FACTORY);
		props.put(PROPRIETE_AUTHENTIFICATION_ACTIVE, VALEUR_AUTHENTIFICATION_ACTIVE.toString());
		props.put(PROPRIETE_PORT_SMTP, VALEUR_PORT_SMTP_SSL.toString());

		return Session.getDefaultInstance(props, creerInfosAuthentification());
	}

	private Authenticator creerInfosAuthentification() {
		return new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configSmtp.getNomUtilisateur(), configSmtp.getMotDePasse());
			}
		};
	}

	private Message creerMessage(Session sessionSmtp, AdresseCourriel courrielDestinataire, String contenu)
			throws MessagingException {
		Message message = new MimeMessage(sessionSmtp);
		message.setFrom(new InternetAddress(configSmtp.getNomUtilisateur()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(courrielDestinataire.toString()));
		message.setSubject(SUJET_COURRIEL);
		message.setText(contenu);

		return message;
	}

}
