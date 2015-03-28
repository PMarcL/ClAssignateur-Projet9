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
	private final Employe ORGANISATEUR = new Employe("courriel@courriel.com");
	private final Employe RESPONSABLE = new Employe("courriel2@courriel.com");
	private final UUID DEMANDE_ID = UUID.randomUUID();

	private Demande demande;
	private Salle salle;
	private DemandeDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		demande = mock(Demande.class);
		salle = mock(Salle.class);
		assembleur = new DemandeDTOAssembleur();
	}

	@Test
	public void etantDonneUneDemandeAvecNombreParticipantsXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecNombrePersonneX() {
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANT);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(NB_PARTICIPANT, demandeDTOResultat.nbParticipants);
	}

	@Test
	public void etantDonneUneDemandeAvecOrganisateurXQuandAssembleDemandeAlorsRetournDemandeDTOAvecOrganisateurX() {
		given(demande.getOrganisateur()).willReturn(ORGANISATEUR);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(ORGANISATEUR, demandeDTOResultat.organisateur);
	}

	@Test
	public void etantDonneUneDemandeAvecUnResponsableXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecResponsableX() {
		given(demande.getResponsable()).willReturn(RESPONSABLE);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(RESPONSABLE, demandeDTOResultat.responsable);
	}

	@Test
	public void etantDonneUneDemandeAvecUnIdantifiantXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecIdentifiantX() {
		given(demande.getID()).willReturn(DEMANDE_ID);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(DEMANDE_ID, demandeDTOResultat.identifiant);
	}

	@Test
	public void etantDonneUneDemandeAvecUneSalleAssigneeXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleAssigneeX() {
		given(demande.getSalleAssignee()).willReturn(salle);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(salle, demandeDTOResultat.salleAssignee);
	}

	@Test
	public void etantDonneUneDemandeSansSalleAssigneeQuandAssembleDemandeAlorsRetourneDemandeDTOSansSalleAssignee() {
		given(demande.getSalleAssignee()).willReturn(null);
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(null, demandeDTOResultat.salleAssignee);
	}
}
