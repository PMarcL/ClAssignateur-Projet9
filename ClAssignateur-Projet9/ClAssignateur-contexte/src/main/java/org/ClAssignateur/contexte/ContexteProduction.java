package org.ClAssignateur.contexte;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.notification.NotificationStrategie;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.notificationCourriel.NotificationStrategieCourrielSsl;
import org.ClAssignateur.notificationCourriel.configuration.ChargementFichierConfigSmtpException;
import org.ClAssignateur.notificationCourriel.configuration.ConfigurationSmtp;
import org.ClAssignateur.notificationCourriel.configuration.ConfigurationSmtpParDefaut;
import org.ClAssignateur.notificationCourriel.configuration.FichierProprietesConfigurationSmtp;
import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;
import org.ClAssignateur.persistance.EnMemoireSallesEntrepot;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.services.reservations.minuterie.MinuterieTimerJavaStandard;

public class ContexteProduction extends Contexte {

	private final int CAPACITE_SALLE_PAR_DEFAUT = 99;

	private SallesEntrepot sallesEntrepot;
	private ConteneurDemandes conteneurDemandes;

	public ContexteProduction() {
		this.sallesEntrepot = new EnMemoireSallesEntrepot();
		intialiserConteneurDemandes();
	}

	public ContexteProduction(SallesEntrepot sallesEntrepot) {
		this.sallesEntrepot = sallesEntrepot;
		intialiserConteneurDemandes();
	}

	private void intialiserConteneurDemandes() {
		this.conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), new EnMemoireDemandeEntrepot());
	}

	@Override
	protected void enregistrerServices() {
		LocalisateurServices.getInstance().enregistrer(ConteneurDemandes.class, this.conteneurDemandes);
		LocalisateurServices.getInstance().enregistrer(Minuterie.class, new MinuterieTimerJavaStandard());
		LocalisateurServices.getInstance().enregistrer(AssignateurSalle.class, creerAssignateurSalle());
	}

	private AssignateurSalle creerAssignateurSalle() {
		AssignateurSalle assignateurSalle = new AssignateurSalle(this.conteneurDemandes, this.sallesEntrepot,
				creerNotificateur(), new SelectionSalleOptimaleStrategie());
		return assignateurSalle;
	}

	private Notificateur creerNotificateur() {
		NotificationStrategie strategie = new NotificationStrategieCourrielSsl(recupererConfigurationSmtp());
		Notificateur notificateur = new Notificateur(strategie);
		return notificateur;
	}

	private ConfigurationSmtp recupererConfigurationSmtp() {
		ConfigurationSmtp configSmtp;
		try {
			configSmtp = new FichierProprietesConfigurationSmtp();
		} catch (ChargementFichierConfigSmtpException ex) {
			configSmtp = new ConfigurationSmtpParDefaut();
		}

		return configSmtp;
	}

	@Override
	protected void injecterDonnees() {
		ajouterSalles();
	}

	private void ajouterSalles() {
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "PLT-3904"));
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "PLT-2551"));
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "VCH-2860"));
	}

}
