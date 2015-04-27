package org.ClAssignateur.services.infosDemandes.dto;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.ClAssignateur.domaine.demandes.Demande.EtatDemande;

@JsonInclude(Include.NON_NULL)
public class InformationsDemandeDTO {

	public int nombrePersonne;
	public String courrielOrganisateur;
	public EtatDemande statutDemande;
	public String salleAssigne;

}
