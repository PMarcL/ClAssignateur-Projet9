package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Demande.STATUT_DEMANDE;

public class DemandeDTO {

	public int nombrePersonne;
	public String courrielOrganisateur;
	public STATUT_DEMANDE statutDemande;
	public String salleAssigne;
	public int priorite;

}
