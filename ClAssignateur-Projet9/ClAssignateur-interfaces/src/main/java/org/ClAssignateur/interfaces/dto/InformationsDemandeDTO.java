package org.ClAssignateur.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.ClAssignateur.domaine.demandes.Demande.StatutDemande;

@JsonInclude(Include.NON_NULL)
public class InformationsDemandeDTO {

	public int nombrePersonne;
	public String courrielOrganisateur;
	public StatutDemande statutDemande;
	public String salleAssigne;

}
