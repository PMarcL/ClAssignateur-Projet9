package org.ClAssignateur.services.reservations;

import java.util.UUID;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;

public class ServiceReservationSalle {

	private DeclencheurAssignateurSalle declencheur;
	private ReservationDemandeDTOAssembleur assembleur;

	public ServiceReservationSalle() {
		this.assembleur = new ReservationDemandeDTOAssembleur();
		this.declencheur = LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class);
	}

	public ServiceReservationSalle(DeclencheurAssignateurSalle declencheur, ReservationDemandeDTOAssembleur assembleur) {
		this.assembleur = assembleur;
		this.declencheur = declencheur;
	}

	public void setFrequence(Minute nbMinutes) {
		this.declencheur.setFrequence(nbMinutes);
	}

	public void setLimiteDemandesAvantAssignation(int limite) {
		this.declencheur.setLimiteDemandesAvantAssignation(limite);
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		this.declencheur.annulerDemande(titreDemandeAnnulee);
	}

	public void annulerDemande(UUID demandeID) {
		this.declencheur.annulerDemande(demandeID);
	}

	public UUID ajouterDemande(ReservationDemandeDTO dto) {
		Demande demande = this.assembleur.assemblerDemande(dto);
		this.declencheur.ajouterDemande(demande);
		return demande.getID();
	}
}
