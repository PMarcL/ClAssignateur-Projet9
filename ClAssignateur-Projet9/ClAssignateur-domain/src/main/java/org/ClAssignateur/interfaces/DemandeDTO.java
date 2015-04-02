package org.ClAssignateur.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.ClAssignateur.domain.demandes.Demande.STATUT_DEMANDE;

@JsonInclude(Include.NON_NULL)
public class DemandeDTO {

	public int nombrePersonne;
	public String courrielOrganisateur;
	public STATUT_DEMANDE statutDemande;
	public String salleAssigne;

}
