package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domain.demandes.Demande.STATUT_DEMANDE;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleurTest {

	private static final String AUCUNE_SALLE = "Aucune salle";
	private static final String NOM_DE_SALLE = "nom_de_salle";
	private final int NB_PARTICIPANT = 12;
	private final String COURRIEL_ORGANISATEUR = "courriel@courriel.com";
	private final Employe ORGANISATEUR = new Employe(COURRIEL_ORGANISATEUR);
	private final STATUT_DEMANDE ETAT_DEMANDE = STATUT_DEMANDE.EN_ATTENTE;

	private Demande demande;
	private Salle salle;
	private DemandeDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		demande = mock(Demande.class);
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANT);
		given(demande.getOrganisateur()).willReturn(ORGANISATEUR);
		given(demande.getEtat()).willReturn(ETAT_DEMANDE);
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
		faireEnSorteQuuneSalleSoitAssigne();
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(salle.getNom(), demandeDTOResultat.salleAssigne);
	}

	@Test
	public void etantDonneUneDemandeAvecAucuneSalleAssigneeQuandAssembleDemandeAlorsRetourneDemandeDTOAvecSalleAssigneeAucuneSalle() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(AUCUNE_SALLE, demandeDTOResultat.salleAssigne);
	}

	@Test
	public void etantDonneUneDemandeAvecUnSatutXQuandAssembleDemandeAlorsRetourneDemandeDTOAvecStatutX() {
		DemandeDTO demandeDTOResultat = assembleur.assemblerDemandeDTO(demande);
		assertEquals(ETAT_DEMANDE, demandeDTOResultat.statutDemande);
	}

	private void faireEnSorteQuuneSalleSoitAssigne() {
		salle = new Salle(NB_PARTICIPANT, NOM_DE_SALLE);
		given(demande.estAssignee()).willReturn(true);
		given(demande.getSalleAssignee()).willReturn(salle);
	}
}
