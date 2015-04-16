package org.ClAssignateur.notificationCourriel.configuration;

public class ConfigurationSmtpParDefaut implements ConfigurationSmtp {

	private final String SERVEUR = "smtp.google.com";
	private final String NOM_UTILISATEUR = "classignateur@gmail.com";
	private final String MOT_PASSE = "ul-glo-4002";

	public String getAdresseServeurSmtp() {
		return SERVEUR;
	}

	public String getNomUtilisateur() {
		return NOM_UTILISATEUR;
	}

	public String getMotDePasse() {
		return MOT_PASSE;
	}

}
