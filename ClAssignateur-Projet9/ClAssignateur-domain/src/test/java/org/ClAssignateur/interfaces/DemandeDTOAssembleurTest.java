package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleurTest {

	private final int NB_PARTICIPANT = 12;
	private final String COURRIEL_ORGANISATEUR = "courriel@courriel.com";
	private final Employe ORGANISATEUR = new Employe(COURRIEL_ORGANISATEUR);

	private Demande demande;
	private Salle salle;
	private DemandeDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		demande = mock(Demande.class);
		salle = mock(Salle.class);
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANT);
		given(demande.getOrganisateur()).willReturn(ORGANISATEUR);
		given(demande.getSalleAssignee()).willReturn(salle);
		assembleur = new DemandeDTOAssembleur();
	}

	@Test
	public void etantDonneUneDemandeAvecNombreParticipantsXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecNombrePersonneX() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(NB_PARTICIPANT, demandeDTOResultat.nombrePersonne);
	}

	@Test
	public void etantDonneUneDemandeAvecOrganisateurXQuandAssembleDemandeAlorsRetournDemandeDTOAvecCourrielOrganisateurX() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(COURRIEL_ORGANISATEUR, demandeDTOResultat.courrielOrganisateur);
	}

	@Test
	public void etantDonneUneDemandeAvecUneSalleAssigneeXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleAssigneeX() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(salle.getNom(), demandeDTOResultat.salleAssignee);
	}

	@Test
	public void etantDonneUneDemandeAvecSalleAssigneeXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleDemandeeX() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(salle.getNom(), demandeDTOResultat.salleDemandee);
	}
}
