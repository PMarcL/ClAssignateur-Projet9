package org.ClAssignateur.notificationCourriel.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FichierProprietesConfigurationSmtp implements ConfigurationSmtp {

	private final String CHEMIN_FICHIER_CONFIG = "smtpConfig.properties";
	private final String PROPRIETE_SERVEUR_SMTP = "serveur";
	private final String PROPRIETE_NOM_UTILISATEUR = "nomUtilisateur";
	private final String PROPRIETE_MOT_PASSE = "motPasse";

	private Properties proprietes;

	public FichierProprietesConfigurationSmtp() {
		proprietes = new Properties();
		chargerProprietes();
	}

	private void chargerProprietes() {
		InputStream fluxEntree = getClass().getClassLoader().getResourceAsStream(CHEMIN_FICHIER_CONFIG);
		try {
			proprietes.load(fluxEntree);
		} catch (IOException e) {
			throw new ChargementFichierConfigSmtpException();
		}
	}

	public String getAdresseServeurSmtp() {
		return proprietes.getProperty(PROPRIETE_SERVEUR_SMTP);
	}

	public String getNomUtilisateur() {
		return proprietes.getProperty(PROPRIETE_NOM_UTILISATEUR);
	}

	public String getMotDePasse() {
		return proprietes.getProperty(PROPRIETE_MOT_PASSE);
	}

}
