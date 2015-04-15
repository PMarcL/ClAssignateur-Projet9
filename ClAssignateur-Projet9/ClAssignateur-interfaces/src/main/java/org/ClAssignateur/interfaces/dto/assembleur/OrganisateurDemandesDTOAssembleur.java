package org.ClAssignateur.interfaces.dto.assembleur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.interfaces.dto.InformationsDemandeDTO;
import org.ClAssignateur.interfaces.dto.OrganisateurDemandesDTO;

public class OrganisateurDemandesDTOAssembleur {

	private InformationsDemandeDTOAssembleur assembleurInfosDemandesDTO;

	public OrganisateurDemandesDTOAssembleur(InformationsDemandeDTOAssembleur assembleurInfosDemandesDTO) {
		this.assembleurInfosDemandesDTO = assembleurInfosDemandesDTO;
	}

	public OrganisateurDemandesDTO assemblerOrganisateurDemandesDTO(List<Demande> demandes) {
		OrganisateurDemandesDTO dto = new OrganisateurDemandesDTO();
		dto.assignees = creerListeAssigne(demandes);
		dto.autres = creerListeAutres(demandes);
		return dto;
	}

	private ArrayList<InformationsDemandeDTO> creerListeAssigne(List<Demande> demandes) {
		List<Demande> listeDemandeAssignees = demandes.stream().filter(x -> x.estAssignee())
				.collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAssignees);
	}

	private ArrayList<InformationsDemandeDTO> creerListeAutres(List<Demande> demandes) {
		List<Demande> listeDemandeAutres = demandes.stream().filter(x -> !x.estAssignee()).collect(Collectors.toList());
		return creerListeDemandeDTOAPartirListeDemandes(listeDemandeAutres);
	}

	private ArrayList<InformationsDemandeDTO> creerListeDemandeDTOAPartirListeDemandes(List<Demande> listeDemandes) {
		ArrayList<InformationsDemandeDTO> listeDemandeDTO = new ArrayList<InformationsDemandeDTO>();

		for (Demande demande : listeDemandes) {
			InformationsDemandeDTO dto = this.assembleurInfosDemandesDTO.assemblerInformationsDemandeDTO(demande);
			listeDemandeDTO.add(dto);
		}

		return listeDemandeDTO;
	}
}
