package org.ClAssignateur.services.reservations;

import java.util.Optional;
import java.util.UUID;

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
		synchronized (this.demandes) {
			this.demandes.mettreDemandeEnAttente(demandeAjoutee);
		}
		assignerSiNecessaire();
	}

	private void assignerSiNecessaire() {
		if (nombreDemandesEnAttente() >= this.limiteDemandes) {
			assigner();
			this.minuterie.reinitialiser();
		}
	}

	private int nombreDemandesEnAttente() {
		synchronized (this.demandes) {
			return this.demandes.getNombreDemandesEnAttente();
		}
	}

	private void assigner() {
		synchronized (this.demandes) {
			this.assignateur.lancerAssignation(this.demandes, this.notificateur);
		}
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		synchronized (this.demandes) {
			Optional<Demande> demandeAnnulee = this.demandes.trouverDemandeSelonTitreReunion(titreDemandeAnnulee);
			annulerReservation(demandeAnnulee);
		}
	}

	public void annulerDemande(UUID demandeID) {
		synchronized (this.demandes) {
			Optional<Demande> demandeAnnulee = this.demandes.obtenirDemandeSelonId(demandeID);
			annulerReservation(demandeAnnulee);
		}
	}

	private void annulerReservation(Optional<Demande> demandeAnnulee) {
		if (demandeAnnulee.isPresent()) {
			demandeAnnulee.get().annulerReservation();
			this.demandes.archiverDemande(demandeAnnulee.get());
			this.notificateur.notifierAnnulation(demandeAnnulee.get());
		}
	}

	public void setLimiteDemandesAvantAssignation(int limiteDemandes) {
		this.limiteDemandes = limiteDemandes;
		assignerSiNecessaire();
	}

}
