package org.ClAssignateur.interfaces;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.demandes.Demande;

public class InformationsDemandeDTOAssembleur {

	private static final String AUCUNE_SALLE = null;

	public InformationsDemandeDTO assemblerInformationsDemandeDTO(Demande demande) {
		InformationsDemandeDTO dto = new InformationsDemandeDTO();
		dto.nombrePersonne = demande.getNbParticipants();
		dto.courrielOrganisateur = demande.getOrganisateur().courriel;
		setValeurSalleAssigne(demande, dto);
		dto.statutDemande = demande.getEtat();
		return dto;
	}

	private void setValeurSalleAssigne(Demande demande, InformationsDemandeDTO dto) {
		if (!demande.estAssignee()) {
			dto.salleAssigne = AUCUNE_SALLE;
		} else {
			dto.salleAssigne = demande.getSalleAssignee().getNom();
		}
	}

	public OrganisateurDemandesDTO assemblerOrganisateurDemandesDTO(List<Demande> demandes) {
		OrganisateurDemandesDTO dto = new OrganisateurDemandesDTO();
		dto.assignees = creerListeAssigne(demandes);
		dto.autres = creerListeAutres(demandes);
		return dto;
	}

	private ArrayList<InformationsDemandeDTO> creerListeAutres(List<Demande> demandes) {
		List<Demande> listeDemandeAutres = demandes.stream().filter(x -> !x.estAssignee()).collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAutres);
	}

	private ArrayList<InformationsDemandeDTO> creerListeAssigne(List<Demande> demandes) {
		List<Demande> listeDemandeAssignees = demandes.stream().filter(x -> x.estAssignee())
				.collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAssignees);
	}

	private ArrayList<InformationsDemandeDTO> creerListeDemandeDTOAPartirListeDemandes(List<Demande> listeDemandes) {
		ArrayList<InformationsDemandeDTO> listeDemandeDTO = new ArrayList<InformationsDemandeDTO>();

		for (Demande demande : listeDemandes) {
			InformationsDemandeDTO dto = this.assemblerInformationsDemandeDTO(demande);
			listeDemandeDTO.add(dto);
		}

		return listeDemandeDTO;
	}

}
