package org.ClAssignateur.persistance;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;
import org.ClAssignateur.domaine.demandes.Demande;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class EnMemoireDemandeEntrepotTest {

	private final int UNE_SEULE_DEMANDE = 1;
	private final int TAILLE_INITIALE_DESIREE = 0;
	private final Object TAILLE_APRES_AJOUT_INITIAL_DESIREE = 1;
	private final Object TAILLE_APRES_SECOND_AJOUT_DESIREE = 2;
	private final UUID UN_UUID = UUID.randomUUID();
	private final String UN_TITRE_DISTINCT = "titre_distinct";
	private final String UN_TITRE = "titre";
	private final String COURRIEL_ORGANISATEUR = "courriel@hotmail.com";
	private final String COURRIEL_NON_CORRESPONDANT = "courriel2@hotmail.com";

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
		ajouterPlusieursDemandesALEntrepot();

		Demande demandeRetrouve = entrepot.obtenirDemandeSelonId(UN_UUID).get();

		assertEquals(demande, demandeRetrouve);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandeSelonTitreDoitRetournerLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterPlusieursDemandesALEntrepot();

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
		ajouterPlusieursDemandesALEntrepot();

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
		ajouterPlusieursDemandesALEntrepot();
		entrepot.retirerDemande(demande);
	}

	@Test
	public void etantDonneUnEntrepotAvecMultipleDemandeApresRetirerDemandeObtenirDemandeDonneAucuneDemandePourLIdDeLaDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterPlusieursDemandesALEntrepot();

		boolean demandeEstPresenteAvant = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();
		entrepot.retirerDemande(demande);
		boolean demandeEstPresenteApres = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();

		assertTrue(demandeEstPresenteAvant);
		assertFalse(demandeEstPresenteApres);
	}

	@Test
	public void etantDonneUnEntrepotAvecUneDemandeDunOrganisateurLorsqueObtenirDemandeSelonCourrielDonneUneSeulDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();

		List<Demande> demandesRecus = entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		int taille_actuelle = demandesRecus.size();

		assertEquals(UNE_SEULE_DEMANDE, taille_actuelle);
	}

	@Test
	public void etantDonneUnEntrepotAvecUneDemandeDunOrganisateurLorsqueObtenirDemandeSelonOrganisateurRetourneLaDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		List<Demande> demandesRecus = entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		assertTrue(demandesRecus.contains(demande));
	}

	@Test
	public void etantDonneUnEntrepotAvecPlusieursDemandeQuiNAppartiennePasALorganisateurAlorsDonneUneListVide() {
		ajouterPlusieursDemandesALEntrepot();
		List<Demande> demandesRecus = entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		assertTrue(demandesRecus.isEmpty());
	}

	private void faireEnSorteQuEntrepotPossedeUneDemande() {
		given(demande.getID()).willReturn(UN_UUID);
		given(demande.getTitre()).willReturn(UN_TITRE_DISTINCT);
		given(demande.getCourrielOrganisateur()).willReturn(COURRIEL_ORGANISATEUR);
		entrepot.persisterDemande(demande);
	}

	private void ajouterPlusieursDemandesALEntrepot() {
		final int NB_DEMANDES_AJOUTEES = 5;
		for (int i = 0; i < NB_DEMANDES_AJOUTEES; i++) {
			ajouterUneDemandeALEntrepot();
		}
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
		given(demande.getCourrielOrganisateur()).willReturn(COURRIEL_NON_CORRESPONDANT);

		return demande;
	}
}
