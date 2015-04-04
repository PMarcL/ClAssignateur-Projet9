package org.ClAssignateur.interfaces;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleur {

	private static final String AUCUNE_SALLE = null;

	public DemandeDTO assemblerDemandeDTO(Demande demande) {
		DemandeDTO dto = new DemandeDTO();
		dto.nombrePersonne = demande.getNbParticipants();
		dto.courrielOrganisateur = demande.getOrganisateur().courriel;
		setValeurSalleAssigne(demande, dto);
		dto.statutDemande = demande.getEtat();
		return dto;
	}

	private void setValeurSalleAssigne(Demande demande, DemandeDTO dto) {
		if (!demande.estAssignee()) {
			dto.salleAssigne = AUCUNE_SALLE;
		} else {
			dto.salleAssigne = demande.getSalleAssignee().getNom();
		}
	}

	public DemandesOrganisateurDTO assemblerDemandesPourCourrielDTO(List<Demande> demandes) {
		DemandesOrganisateurDTO dto = new DemandesOrganisateurDTO();
		dto.assignees = creerListeAssigne(demandes);
		dto.autres = creerListeAutres(demandes);
		return dto;
	}

	private ArrayList<DemandeDTO> creerListeAutres(List<Demande> demandes) {
		List<Demande> listeDemandeAutres = demandes.stream().filter(x -> !x.estAssignee()).collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAutres);
	}

	private ArrayList<DemandeDTO> creerListeAssigne(List<Demande> demandes) {
		List<Demande> listeDemandeAssignees = demandes.stream().filter(x -> x.estAssignee())
				.collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAssignees);
	}

	private ArrayList<DemandeDTO> creerListeDemandeDTOAPartirListeDemandes(List<Demande> listeDemandes) {
		ArrayList<DemandeDTO> listeDemandeDTO = new ArrayList<DemandeDTO>();

		for (Demande demande : listeDemandes) {
			DemandeDTO dto = this.assemblerDemandeDTO(demande);
			listeDemandeDTO.add(dto);
		}

		return listeDemandeDTO;
	}

}
