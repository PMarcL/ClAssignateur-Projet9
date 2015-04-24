package org.ClAssignateur.services.reservations;

import java.util.Optional;
import java.util.UUID;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.services.reservations.minuterie.MinuterieObservateur;

public class ServiceReservationSalle implements MinuterieObservateur {

	private final Minute DELAI_MINUTERIE_PAR_DEFAUT = new Minute(1);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 100;

	private AssignateurSalle assignateurSalle;
	private Minuterie minuterie;
	private int limiteDemandes;
	private ReservationDemandeDTOAssembleur assembleur;
	private ConteneurDemandes conteneurDemandes;
	private Notificateur notificateur;

	public ServiceReservationSalle(Minuterie minuterie, ConteneurDemandes demandes, AssignateurSalle assignateurSalle,
			Notificateur notificateur, ReservationDemandeDTOAssembleur assembleur) {
		this.assembleur = assembleur;
		initialiser(minuterie, demandes, assignateurSalle, notificateur);
		demarrerMinuterie();
	}

	public ServiceReservationSalle(Minuterie minuterie, ConteneurDemandes demandes, AssignateurSalle assignateurSalle,
			Notificateur notificateur) {
		this.assembleur = new ReservationDemandeDTOAssembleur();
		initialiser(minuterie, demandes, assignateurSalle, notificateur);
		demarrerMinuterie();
	}

	private void initialiser(Minuterie minuterie, ConteneurDemandes demandes, AssignateurSalle assignateurSalle,
			Notificateur notificateur) {
		this.assignateurSalle = assignateurSalle;
		this.conteneurDemandes = demandes;
		this.notificateur = notificateur;
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;
		this.minuterie = minuterie;
	}

	private void demarrerMinuterie() {
		this.minuterie.setDelai(DELAI_MINUTERIE_PAR_DEFAUT);
		this.minuterie.souscrire(this);
		this.minuterie.demarrer();
	}

	public void setFrequence(Minute nbMinutes) {
		this.minuterie.setDelai(nbMinutes);
		this.minuterie.reinitialiser();
	}

	public void setLimiteDemandesAvantAssignation(int limite) {
		this.limiteDemandes = limite;
		assignerSiNecessaire();
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		synchronized (this.conteneurDemandes) {
			Optional<Demande> demandeAAnnuler = this.conteneurDemandes
					.trouverDemandeSelonTitreReunion(titreDemandeAnnulee);

			if (demandeAAnnuler.isPresent()) {
				annulerReservation(demandeAAnnuler.get());
			}
		}
	}

	private void annulerReservation(Demande demandeAAnnuler) {
		demandeAAnnuler.annulerReservation();
		this.conteneurDemandes.archiverDemande(demandeAAnnuler);
		this.notificateur.notifierAnnulation(demandeAAnnuler);
	}

	public UUID ajouterDemande(ReservationDemandeDTO dto) {
		Demande demande = this.assembleur.assemblerDemande(dto);

		synchronized (this.conteneurDemandes) {
			this.conteneurDemandes.mettreDemandeEnAttente(demande);
			assignerSiNecessaire();
		}

		return demande.getID();
	}

	private void assignerSiNecessaire() {
		if (this.conteneurDemandes.getNombreDemandesEnAttente() >= this.limiteDemandes) {
			assigner();
			this.minuterie.reinitialiser();
		}
	}

	private void assigner() {
		synchronized (this.conteneurDemandes) {
			this.assignateurSalle.lancerAssignation(this.conteneurDemandes, this.notificateur);
		}
	}

	public void notifierDelaiEcoule() {
		assigner();
	}

}
