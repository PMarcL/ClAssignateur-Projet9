package org.ClAssignateur.persistences;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.groupe.Employe;

import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import java.util.UUID;
import org.ClAssignateur.domain.demandes.Demande;
import org.junit.Test;

public class EnMemoireDemandeEntrepotTest {

	private static final int TAILLE_INITIALE_DESIREE = 0;
	private static final Object TAILLE_APRES_AJOUT_INITIAL_DESIREE = 1;
	private static final Object TAILLE_APRES_SECOND_AJOUT_DESIREE = 2;
	private static final UUID UN_UUID = UUID.randomUUID();
	private static final String UN_TITRE_DISTINCT = "titre_distinc";
	private static final String UN_TITRE = "titre";
	private static final Employe UN_ORGANISATEUR_DISTINCT = new Employe("courriel");
	private static final Employe UN_ORGANISATEUR = new Employe("courriel");

	private Demande demande;

	private EnMemoireDemandeEntrepot entrepot;

	@Before
	public void initialement() {
		demande = mock(Demande.class);
		entrepot = new EnMemoireDemandeEntrepot();
	}

	@Test
	public void etantDonneUnEntrepotInitialeQuandTailleAlorsZeroEstRetourne() {
		int taille_obtenue = entrepot.taille();
		assertEquals(TAILLE_INITIALE_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotInitialeQuandPersisterAlorsTailleEgaleUn() {
		entrepot.persisterDemande(demande);
		int taille_obtenue = entrepot.taille();

		assertEquals(TAILLE_APRES_AJOUT_INITIAL_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecUnDemandePossedantIdXQuandPersiterLaDemandeExistanteAvecIdXAlorsTailleResteInchange() {
		faireEnSorteQuEntrepotPossedeUneDemande();

		entrepot.persisterDemande(demande);
		int taille_obtenue = entrepot.taille();

		assertEquals(TAILLE_APRES_AJOUT_INITIAL_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecUnDemandePossedantIdXQuandPersiterLaDemandeAvecIdYAlorsTailleAugmenteDeUn() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		Demande demandeY = mock(Demande.class);
		UUID UN_UUID_DIFFERENT = UUID.randomUUID();
		given(demandeY.getID()).willReturn(UN_UUID_DIFFERENT);

		entrepot.persisterDemande(demandeY);
		int taille_obtenue = entrepot.taille();

		assertEquals(TAILLE_APRES_SECOND_AJOUT_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandeSelonIdDoitRetournerLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterTroisDemandeALEntrepot();

		Demande demandeRetrouve = entrepot.obtenirDemandeSelonId(UN_UUID).get();

		assertEquals(demande, demandeRetrouve);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandeSelonTitreDoitRetournerLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterTroisDemandeALEntrepot();

		Demande demandeRetrouve = entrepot.obtenirDemandeSelonTitre(UN_TITRE_DISTINCT).get();

		assertEquals(demande, demandeRetrouve);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandesObtenirDemandesSelonOrganisateurDoitDonnerLesBonnesDemandes() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		Demande demande1 = ajouterUneDemandeALEntrepot();
		Demande demande2 = ajouterUneDemandeALEntrepot();

		List<Demande> demandes = entrepot.obtenirDemandesSelonOrganisateur(UN_ORGANISATEUR);

		assertFalse(demandes.contains(demande));
		assertTrue(demandes.contains(demande1));
		assertTrue(demandes.contains(demande2));
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandesDoitDonnerToutesLesDemandes() {
		Demande demande1 = ajouterUneDemandeALEntrepot();
		Demande demande2 = ajouterUneDemandeALEntrepot();
		Demande demande3 = ajouterUneDemandeALEntrepot();

		List<Demande> demandes = entrepot.obtenirDemandes();

		assertTrue(demandes.contains(demande1));
		assertTrue(demandes.contains(demande2));
		assertTrue(demandes.contains(demande3));
	}

	private void faireEnSorteQuEntrepotPossedeUneDemande() {
		given(demande.getID()).willReturn(UN_UUID);
		given(demande.getTitre()).willReturn(UN_TITRE_DISTINCT);
		given(demande.getOrganisateur()).willReturn(UN_ORGANISATEUR_DISTINCT);
		entrepot.persisterDemande(demande);
	}

	private void ajouterTroisDemandeALEntrepot() {
		ajouterUneDemandeALEntrepot();
		ajouterUneDemandeALEntrepot();
		ajouterUneDemandeALEntrepot();
	}

	private Demande ajouterUneDemandeALEntrepot() {
		Demande demande = mock(Demande.class);
		UUID UN_UUID = UUID.randomUUID();
		given(demande.getID()).willReturn(UN_UUID);
		given(demande.getTitre()).willReturn(UN_TITRE);
		given(demande.getOrganisateur()).willReturn(UN_ORGANISATEUR);
		entrepot.persisterDemande(demande);
		return demande;
	}
}
