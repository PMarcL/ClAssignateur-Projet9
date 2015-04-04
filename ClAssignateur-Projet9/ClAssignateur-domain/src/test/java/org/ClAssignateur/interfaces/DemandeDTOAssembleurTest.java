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
	private static final int NB_DE_DEMANDE_ASSIGNEE = 5;
	private static final int NB_DE_DEMANDE_AUTRES = 10;
	private static final String AUCUNE_SALLE = null;
	private static final String NOM_DE_SALLE = "nom_de_salle";
	private final int NB_PARTICIPANT = 12;
	private final String COURRIEL_ORGANISATEUR = "courriel@courriel.com";
	private final Employe ORGANISATEUR = new Employe(COURRIEL_ORGANISATEUR);
	private final STATUT_DEMANDE ETAT_EN_ATTENTE = STATUT_DEMANDE.EN_ATTENTE;

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
		given(demande.getEtat()).willReturn(ETAT_EN_ATTENTE);
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
		assertEquals(ETAT_EN_ATTENTE, demandeDTOResultat.statutDemande);
	}

	@Test
	public void etantDonneUneListeVideDeDemandeAssembleDemandesPourCourrielDonneUnDTOAvecDeuxChampVide() {

		DemandesOrganisateurDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);

		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecUneDemandeDansAssigne() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		DemandesOrganisateurDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.assignees.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresVide() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		DemandesOrganisateurDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);

		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSallePasAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresUneDemandeDansAutres() {
		demandes.add(demande);

		DemandesOrganisateurDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.autres.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAutresAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAssignesVide() {
		demandes.add(demande);
		DemandesOrganisateurDTO demandePourCourrielDTO = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecPlusieursDemandesAssigneesAlorsAssembleDemandesPourCourrielRetourneBonNombreDemandesAssignees() {
		ajouterPlusieursDemandesAssignees();
		DemandesOrganisateurDTO resultat = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		assertEquals(NB_DE_DEMANDE_ASSIGNEE, resultat.assignees.size());

	}

	@Test
	public void etantDonneUneListeAvecPlusieursDemandesEnAttenteAlorsAssembleDemandesPourCourrielRetourneBonNombreDemandesAutres() {
		ajouterPlusieursDemandesAutres();
		DemandesOrganisateurDTO resultat = assembleur.assemblerDemandesPourCourrielDTO(demandes);
		assertEquals(NB_DE_DEMANDE_AUTRES, resultat.autres.size());
	}

	private void ajouterPlusieursDemandesAutres() {
		for (int i = 0; i < NB_DE_DEMANDE_AUTRES; i++) {
			demandes.add(demande);
		}
	}

	private void ajouterPlusieursDemandesAssignees() {
		salle = new Salle(NB_PARTICIPANT, NOM_DE_SALLE);
		for (int i = 0; i < NB_DE_DEMANDE_ASSIGNEE; i++) {
			Demande demandeAssignee = mock(Demande.class);
			given(demandeAssignee.getNbParticipants()).willReturn(NB_PARTICIPANT);
			given(demandeAssignee.getOrganisateur()).willReturn(ORGANISATEUR);
			given(demandeAssignee.estAssignee()).willReturn(true);
			given(demandeAssignee.getSalleAssignee()).willReturn(salle);
			demandes.add(demandeAssignee);
		}
	}

	private void faireEnSorteQuuneSalleSoitAssigne() {
		salle = new Salle(NB_PARTICIPANT, NOM_DE_SALLE);
		given(demande.estAssignee()).willReturn(true);
		given(demande.getSalleAssignee()).willReturn(salle);
	}
}
