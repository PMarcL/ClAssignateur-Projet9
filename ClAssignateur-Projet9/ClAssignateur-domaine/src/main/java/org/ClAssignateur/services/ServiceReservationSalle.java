package org.ClAssignateur.services;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.AssignateurSalle;

public class ServiceReservationSalle implements MinuterieObservateur {

	private final Minute DELAI_MINUTERIE_PAR_DEFAUT = new Minute(5);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;

	private AssignateurSalle assignateurSalle;
	private Minuterie minuterie;
	private int limiteDemandes;

	public ServiceReservationSalle(Minuterie minuterie, AssignateurSalle assignateurSalle) {
		this.assignateurSalle = assignateurSalle;
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;

		this.minuterie = minuterie;
		this.minuterie.setDelai(DELAI_MINUTERIE_PAR_DEFAUT);
		this.minuterie.souscrire(this);
		this.minuterie.demarrer();
	}

	public void setFrequence(Minute nbMinutes) {
		minuterie.setDelai(nbMinutes);
		minuterie.reinitialiser();
	}

	public void setLimiteDemandesAvantAssignation(int limite) {
		limiteDemandes = limite;
		assignerSiNecessaire();
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		assignateurSalle.annulerDemande(titreDemandeAnnulee);
	}

	public void ajouterDemande(Demande nouvelleDemande) {
		assignateurSalle.ajouterDemande(nouvelleDemande);
		assignerSiNecessaire();
	}

	private void assignerSiNecessaire() {
		if (assignateurSalle.getNombreDemandesEnAttente() >= limiteDemandes) {
			assignateurSalle.lancerAssignation();
			minuterie.reinitialiser();
		}
	}

	public void notifierDelaiEcoule() {
		assignateurSalle.lancerAssignation();
	}

}
