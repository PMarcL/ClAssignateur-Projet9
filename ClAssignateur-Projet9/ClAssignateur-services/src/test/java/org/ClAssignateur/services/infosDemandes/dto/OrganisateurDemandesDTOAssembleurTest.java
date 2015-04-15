package org.ClAssignateur.services.infosDemandes.dto;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import org.junit.Before;

import java.util.List;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.Demande.StatutDemande;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;
import org.junit.Test;

public class OrganisateurDemandesDTOAssembleurTest {
	private final int NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE = 1;
	private final int NB_DE_DEMANDE_ASSIGNEE = 5;
	private final int NB_DE_DEMANDE_AUTRES = 10;
	private final StatutDemande STATUT_DEMANDE_EN_ATTENTE = StatutDemande.EN_ATTENTE;
	private final int NB_PARTICIPANT = 12;
	private final String NOM_SALLE = "NomDeSalle";

	private List<Demande> demandes;
	private Demande demande;
	private Salle salle;
	private Employe organisateur;
	private InformationsDemandeDTOAssembleur infosDemandesAssembleur;

	private OrganisateurDemandesDTOAssembleur assembleur;

	@Before
	public void initialisation() {
		demandes = new ArrayList<Demande>();
		configurerDemande();
		infosDemandesAssembleur = mock(InformationsDemandeDTOAssembleur.class);
		organisateur = mock(Employe.class);

		assembleur = new OrganisateurDemandesDTOAssembleur(infosDemandesAssembleur);
	}

	@Test
	public void etantDonneUneListeVideDeDemandeAssembleDemandesPourCourrielDonneUnDTOAvecDeuxChampVide() {

		OrganisateurDemandesDTO demandePourCourrielDTO = assembleur.assemblerOrganisateurDemandesDTO(demandes);

		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecUneDemandeDansAssigne() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		OrganisateurDemandesDTO demandePourCourrielDTO = assembleur.assemblerOrganisateurDemandesDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.assignees.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresVide() {
		faireEnSorteQuuneSalleSoitAssigne();
		demandes.add(demande);

		OrganisateurDemandesDTO demandePourCourrielDTO = assembleur.assemblerOrganisateurDemandesDTO(demandes);

		assertTrue(demandePourCourrielDTO.autres.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecUneSallePasAssigneAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAutresUneDemandeDansAutres() {
		demandes.add(demande);

		OrganisateurDemandesDTO demandePourCourrielDTO = assembleur.assemblerOrganisateurDemandesDTO(demandes);
		int nombre_de_demandes_actuel = demandePourCourrielDTO.autres.size();

		assertEquals(NOMBRE_DE_DEMANDE_VOULU_QUAND_UNE_SEULE_DEMANDE_DANS_LISTE, nombre_de_demandes_actuel);
	}

	@Test
	public void etantDonneUneListeAvecUneSalleAutresAlorsAssembleDemandesPourCourrielDonneUnDTOAvecAssignesVide() {
		demandes.add(demande);
		OrganisateurDemandesDTO demandePourCourrielDTO = assembleur.assemblerOrganisateurDemandesDTO(demandes);
		assertTrue(demandePourCourrielDTO.assignees.isEmpty());
	}

	@Test
	public void etantDonneUneListeAvecPlusieursDemandesAssigneesAlorsAssembleDemandesPourCourrielRetourneBonNombreDemandesAssignees() {
		ajouterPlusieursDemandesAssignees();
		OrganisateurDemandesDTO resultat = assembleur.assemblerOrganisateurDemandesDTO(demandes);
		assertEquals(NB_DE_DEMANDE_ASSIGNEE, resultat.assignees.size());

	}

	@Test
	public void etantDonneUneListeAvecPlusieursDemandesEnAttenteAlorsAssembleDemandesPourCourrielRetourneBonNombreDemandesAutres() {
		ajouterPlusieursDemandesAutres();
		OrganisateurDemandesDTO resultat = assembleur.assemblerOrganisateurDemandesDTO(demandes);
		assertEquals(NB_DE_DEMANDE_AUTRES, resultat.autres.size());
	}

	private void configurerDemande() {
		demande = mock(Demande.class);
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANT);
		given(demande.getOrganisateur()).willReturn(organisateur);
		given(demande.getEtat()).willReturn(STATUT_DEMANDE_EN_ATTENTE);
	}

	private void faireEnSorteQuuneSalleSoitAssigne() {
		salle = new Salle(NB_PARTICIPANT, NOM_SALLE);
		given(demande.estAssignee()).willReturn(true);
		given(demande.getSalleAssignee()).willReturn(salle);
	}

	private void ajouterPlusieursDemandesAssignees() {
		salle = new Salle(NB_PARTICIPANT, NOM_SALLE);
		for (int i = 0; i < NB_DE_DEMANDE_ASSIGNEE; i++) {
			Demande demandeAssignee = mock(Demande.class);
			given(demandeAssignee.getNbParticipants()).willReturn(NB_PARTICIPANT);
			given(demandeAssignee.getOrganisateur()).willReturn(organisateur);
			given(demandeAssignee.estAssignee()).willReturn(true);
			given(demandeAssignee.getSalleAssignee()).willReturn(salle);
			demandes.add(demandeAssignee);
		}
	}

	private void ajouterPlusieursDemandesAutres() {
		for (int i = 0; i < NB_DE_DEMANDE_AUTRES; i++) {
			demandes.add(demande);
		}
	}
}
