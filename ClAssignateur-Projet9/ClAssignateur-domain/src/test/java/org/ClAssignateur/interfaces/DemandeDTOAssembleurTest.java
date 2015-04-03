package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import java.util.ArrayList;
import org.ClAssignateur.domain.demandes.Demande.STATUT_DEMANDE;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleurTest {

	private static final int NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE = 1;
	private static final String AUCUNE_SALLE = null;
	private static final String NOM_DE_SALLE = "nom_de_salle";
	private final int NB_PARTICIPANT = 12;
	private final String COURRIEL_ORGANISATEUR = "courriel@courriel.com";
	private final Employe ORGANISATEUR = new Employe(COURRIEL_ORGANISATEUR);
	private final STATUT_DEMANDE ETAT_DEMANDE = STATUT_DEMANDE.EN_ATTENTE;

	private Demande demande;
	private Salle salle;
	private List<Demande> demandes;
	private DemandeDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		demande = mock(Demande.class);
		demandes = new ArrayList<Demande>();
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

	@Test
	public void etantDonneUneListeVideDeDemandeAssembleDemandesPourCourrielDonneUnDTOAvecDeuxChampVide() {

		DemandesPourCourrielDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);

		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecUneDemandeDansAssigne() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		DemandesPourCourrielDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.assignees.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresVide() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		DemandesPourCourrielDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);

		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSallePasAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresUneDemandeDansAutres() {
		demandes.add(demande);

		DemandesPourCourrielDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.autres.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAutresAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAssignesVide() {
		demandes.add(demande);
		DemandesPourCourrielDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecPlusieursDemandesAssigneesAlorsAssembleDemandesPourCourrielRetourneBonNombreDemandesAssignees() {

	}

	private void faireEnSorteQuuneSalleSoitAssigne() {
		salle = new Salle(NB_PARTICIPANT, NOM_DE_SALLE);
		given(demande.estAssignee()).willReturn(true);
		given(demande.getSalleAssignee()).willReturn(salle);
	}
}
