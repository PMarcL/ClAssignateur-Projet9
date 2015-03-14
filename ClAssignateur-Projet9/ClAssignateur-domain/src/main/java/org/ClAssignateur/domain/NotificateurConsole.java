package org.ClAssignateur.domain;

public class NotificateurConsole implements Notificateur {
	
	public void notifier(MessageNotification message, Employe destinataire){
		System.out.println("Courriel : "+destinataire.getCourriel());
		System.out.println("Message : "+message.genereMessage());
	}
	
}
