package org.ClAssignateur.services.reservations;

import java.util.Optional;
import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.services.reservations.minuterie.MinuterieObservateur;

public class DeclencheurAssignateurSalle implements MinuterieObservateur {
	private final Minute DELAI_MINUTERIE_PAR_DEFAUT = new Minute(1);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 100;

	private int limiteDemandes;
	private Minuterie minuterie;
	private ConteneurDemandes demandes;
	private AssignateurSalle assignateur;
	private Notificateur notificateur;

	public DeclencheurAssignateurSalle(Minuterie minuterie, ConteneurDemandes conteneurDemandes,
			AssignateurSalle assignateur, Notificateur notificateur) {
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;
		this.demandes = conteneurDemandes;
		this.assignateur = assignateur;
		this.notificateur = notificateur;
		this.minuterie = minuterie;
		demarrerMinuterie();
	}

	private void demarrerMinuterie() {
		this.minuterie.setDelai(DELAI_MINUTERIE_PAR_DEFAUT);
		this.minuterie.souscrire(this);
		this.minuterie.demarrer();
	}

	@Override
	public void notifierDelaiEcoule() {
		assigner();
	}

	public void setFrequence(Minute nbMinutes) {
		this.minuterie.setDelai(nbMinutes);
		this.minuterie.reinitialiser();
	}

	public void ajouterDemande(Demande demandeAjoutee) {
		this.demandes.mettreDemandeEnAttente(demandeAjoutee);
		assignerSiNecessaire();
	}

	private void assignerSiNecessaire() {
		if (this.demandes.getNombreDemandesEnAttente() >= this.limiteDemandes) {
			assigner();
			this.minuterie.reinitialiser();
		}
	}

	private void assigner() {
		this.assignateur.lancerAssignation(this.demandes, this.notificateur);
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		Optional<Demande> demandeAnnulee = this.demandes.trouverDemandeSelonTitreReunion(titreDemandeAnnulee);

		if (demandeAnnulee.isPresent()) {
			annulerReservation(demandeAnnulee.get());
		}
	}

	private void annulerReservation(Demande demandeAnnulee) {
		demandeAnnulee.annulerReservation();
		this.demandes.archiverDemande(demandeAnnulee);
		this.notificateur.notifierAnnulation(demandeAnnulee);
	}

	public void setLimiteDemandesAvantAssignation(int limiteDemandes) {
		this.limiteDemandes = limiteDemandes;
		assignerSiNecessaire();
	}
}
