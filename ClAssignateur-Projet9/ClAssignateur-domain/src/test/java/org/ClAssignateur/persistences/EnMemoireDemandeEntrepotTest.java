package org.ClAssignateur.persistences;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.demandes.Demande;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import java.util.UUID;
import org.junit.Test;

public class EnMemoireDemandeEntrepotTest {

	private static final int TAILLE_INITIALE_DESIREE = 0;
	private static final Object TAILLE_APRES_AJOUT_INITIAL_DESIREE = 1;
	private static final Object TAILLE_APRES_SECOND_AJOUT_DESIREE = 2;
	private static final UUID UN_UUID = UUID.randomUUID();
	private static final String UN_TITRE_DISTINCT = "titre_distinct";
	private static final String UN_TITRE = "titre";
	private static final String COURRIEL_ORGANISATEUR = "courriel@hotmail.com";
	private static final String COURRIEL_NON_CORRESPONDANT = "courriel2@hotmail.com";
	private static final Employe ORGANISATEUR = new Employe(COURRIEL_ORGANISATEUR);

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
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandesDoitDonnerToutesLesDemandes() {
		Demande demande1 = ajouterUneDemandeALEntrepot();
		Demande demande2 = ajouterUneDemandeALEntrepot();
		Demande demande3 = ajouterUneDemandeALEntrepot();

		List<Demande> demandes = entrepot.obtenirDemandes();

		assertTrue(demandes.contains(demande1));
		assertTrue(demandes.contains(demande2));
		assertTrue(demandes.contains(demande3));
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeApresViderTailleEgaleZero() {
		ajouterTroisDemandeALEntrepot();

		entrepot.vider();
		int tailleApresVider = entrepot.taille();

		assertEquals(TAILLE_INITIALE_DESIREE, tailleApresVider);
	}

	@Test
	public void etantDonneUnEntrepotAvecUneDemandeApresRetirerDemandeTailleEgaleZero() {
		faireEnSorteQuEntrepotPossedeUneDemande();

		entrepot.retirerDemande(demande);
		int tailleApresVider = entrepot.taille();

		assertEquals(TAILLE_INITIALE_DESIREE, tailleApresVider);
	}

	@Test
	public void etantDonneUnEntrepotAvecMultipleDemandeLorsqueRetirerUneDemandeQuiNExistePasAlorsNeLancePasException() {
		ajouterTroisDemandeALEntrepot();
		entrepot.retirerDemande(demande);
	}

	@Test
	public void etantDonneUnEntrepotAvecMultipleDemandeApresRetirerDemandeObtenirDemandeDonneAucuneDemandePourLIdDeLaDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterTroisDemandeALEntrepot();

		boolean demandeEstPresenteAvant = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();
		entrepot.retirerDemande(demande);
		boolean demandeEstPresenteApres = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();

		assertTrue(demandeEstPresenteAvant);
		assertFalse(demandeEstPresenteApres);
	}

	@Test
	public void etantDonneUnEntrepotAvecUneDemandeLorsqueObtenirDemandeSelonOrganisateurEtIdRetourneLaDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		Optional<Demande> demandeRecu = entrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR,
				UN_UUID);
		assertTrue(demandeRecu.isPresent());
	}

	@Test
	public void etantDonneUnEntrepotAvecUneDemandeLorsqueObtenirDemandeSelonOrganisateurEtIdAvecMauvaisCourrielRetourneOptionalVide() {
		faireEnSorteQuEntrepotPossedeUneDemande();

		Optional<Demande> demandeRecu = entrepot.obtenirDemandeSelonCourrielOrganisateurEtId(
				COURRIEL_NON_CORRESPONDANT, UN_UUID);

		assertFalse(demandeRecu.isPresent());
	}

	@Test
	public void etantDonneUnEntrepotAvecPlusieursDemandeLorsqueObtenirDemandeSelonOrganisateurEtIdRetourneLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterTroisDemandeALEntrepot();

		Optional<Demande> demandeRecu = entrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR,
				UN_UUID);

		assertEquals(demande, demandeRecu.get());
	}

	@Test
	public void etantDonneUnEntrepotAvecPlusieursDemandeLorsqueObtenirDemandeSelonOrganisateurEtIdAvecMauvaisCourrielRetourneOptionalVide() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterTroisDemandeALEntrepot();

		Optional<Demande> demandeRecu = entrepot.obtenirDemandeSelonCourrielOrganisateurEtId(
				COURRIEL_NON_CORRESPONDANT, UN_UUID);

		assertFalse(demandeRecu.isPresent());
	}

	private void faireEnSorteQuEntrepotPossedeUneDemande() {
		given(demande.getID()).willReturn(UN_UUID);
		given(demande.getTitre()).willReturn(UN_TITRE_DISTINCT);
		given(demande.getOrganisateur()).willReturn(ORGANISATEUR);
		entrepot.persisterDemande(demande);
	}

	private void ajouterTroisDemandeALEntrepot() {
		ajouterUneDemandeALEntrepot();
		ajouterUneDemandeALEntrepot();
		ajouterUneDemandeALEntrepot();
	}

	private Demande ajouterUneDemandeALEntrepot() {
		Demande demande = creerUneDemande();
		entrepot.persisterDemande(demande);
		return demande;
	}

	private Demande creerUneDemande() {
		Demande demande = mock(Demande.class);
		UUID unId = UUID.randomUUID();
		given(demande.getID()).willReturn(unId);
		given(demande.getTitre()).willReturn(UN_TITRE);
		return demande;
	}
}
