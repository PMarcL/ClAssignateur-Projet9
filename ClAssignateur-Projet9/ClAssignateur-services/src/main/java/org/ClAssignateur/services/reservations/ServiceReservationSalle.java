package org.ClAssignateur.services.reservations;

import java.util.UUID;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.services.reservations.minuterie.MinuterieObservateur;

public class ServiceReservationSalle implements MinuterieObservateur {

	private final Minute DELAI_MINUTERIE_PAR_DEFAUT = new Minute(1);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;

	private AssignateurSalle assignateurSalle;
	private Minuterie minuterie;
	private int limiteDemandes;
	private ReservationDemandeDTOAssembleur assembleur;

	public ServiceReservationSalle() {
		this.assignateurSalle = LocalisateurServices.getInstance().obtenir(AssignateurSalle.class);
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;
		this.assembleur = new ReservationDemandeDTOAssembleur();
		this.minuterie = LocalisateurServices.getInstance().obtenir(Minuterie.class);
		demarrerMinuterie();
	}

	private void demarrerMinuterie() {
		this.minuterie.setDelai(DELAI_MINUTERIE_PAR_DEFAUT);
		this.minuterie.souscrire(this);
		this.minuterie.demarrer();
	}

	public ServiceReservationSalle(Minuterie minuterie, AssignateurSalle assignateurSalle,
			ReservationDemandeDTOAssembleur assembleur) {
		this.assignateurSalle = assignateurSalle;
		this.assembleur = assembleur;
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;
		this.minuterie = minuterie;
		demarrerMinuterie();
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

	public UUID ajouterDemande(ReservationDemandeDTO dto) {
		Demande demande = assembleur.assemblerDemande(dto);
		assignateurSalle.ajouterDemande(demande);
		assignerSiNecessaire();
		return demande.getID();
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
