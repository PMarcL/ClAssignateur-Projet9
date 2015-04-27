package org.ClAssignateur.services.infosDemandes.dto;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.Demande.EtatDemande;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;

public class InformationsDemandeDTOAssembleurTest {
	private final String AUCUNE_SALLE = null;
	private final String NOM_SALLE = "NomDeSalle";
	private final String COURRIEL_ORGANISATEUR = "courriel@example.com";
	private final int NB_PARTICIPANT = 12;
	private final EtatDemande STATUT_DEMANDE_EN_ATTENTE = EtatDemande.EN_ATTENTE;

	private Demande demande;
	private Salle salle;
	private InformationsDemandeDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		configurerDemande();
		assembleur = new InformationsDemandeDTOAssembleur();
	}

	@Test
	public void etantDonneUneDemandeAvecNombreParticipantsXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecNombrePersonneX() {
		InformationsDemandeDTO demandeDTOResultat = assembleur.assemblerInformationsDemandeDTO(demande);
		assertEquals(NB_PARTICIPANT, demandeDTOResultat.nombrePersonne);
	}

	@Test
	public void etantDonneUneDemandeAvecOrganisateurXQuandAssembleDemandeAlorsRetournDemandeDTOAvecCourrielOrganisateurX() {
		InformationsDemandeDTO demandeDTOResultat = assembleur.assemblerInformationsDemandeDTO(demande);
		assertEquals(COURRIEL_ORGANISATEUR, demandeDTOResultat.courrielOrganisateur);
	}

	@Test
	public void etantDonneUneDemandeAvecUneSalleAssigneeXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleAssigneeX() {
		faireEnSorteQuuneSalleSoitAssigne();
		InformationsDemandeDTO demandeDTOResultat = assembleur.assemblerInformationsDemandeDTO(demande);
		assertEquals(salle.getNom(), demandeDTOResultat.salleAssigne);
	}

	@Test
	public void etantDonneUneDemandeAvecAucuneSalleAssigneeQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleAssigneeAucuneSalle() {
		InformationsDemandeDTO demandeDTOResultat = assembleur.assemblerInformationsDemandeDTO(demande);
		assertEquals(AUCUNE_SALLE, demandeDTOResultat.salleAssigne);
	}

	@Test
	public void etantDonneUneDemandeAvecUnSatutXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecStatutX() {
		InformationsDemandeDTO demandeDTOResultat = assembleur.assemblerInformationsDemandeDTO(demande);
		assertEquals(STATUT_DEMANDE_EN_ATTENTE, demandeDTOResultat.statutDemande);
	}

	private void configurerDemande() {
		demande = mock(Demande.class);
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANT);
		given(demande.getCourrielOrganisateur()).willReturn(COURRIEL_ORGANISATEUR);
		given(demande.getEtat()).willReturn(STATUT_DEMANDE_EN_ATTENTE);
	}

	private void faireEnSorteQuuneSalleSoitAssigne() {
		salle = new Salle(NB_PARTICIPANT, NOM_SALLE);
		given(demande.estAssignee()).willReturn(true);
		given(demande.getSalleAssignee()).willReturn(salle);
	}
}
