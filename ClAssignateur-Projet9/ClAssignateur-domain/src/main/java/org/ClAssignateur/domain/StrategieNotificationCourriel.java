package org.ClAssignateur.domain;

public class StrategieNotificationCourriel implements StrategieNotification {
	
	public void notifier(MessageNotification message, Employe destinataire){
		System.out.println("Courriel envoyé à "+destinataire.getCourriel()+".");
		System.out.println("Le message envoyé est : "+message.genereMessage()+".");
	}
	
}
