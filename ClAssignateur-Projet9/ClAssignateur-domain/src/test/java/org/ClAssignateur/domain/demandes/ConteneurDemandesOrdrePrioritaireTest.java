package org.ClAssignateur.domain.demandes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import org.ClAssignateur.domain.groupe.Groupe;

import org.mockito.ArgumentMatcher;
import org.ClAssignateur.domain.demandes.ConteneurDemandesOrdrePrioritaire;
import org.ClAssignateur.domain.demandes.Demande;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesOrdrePrioritaireTest {

	private final int AUCUN_ELEMENT = 0;
	private final int UN_ELEMENT = 1;
	private final int DEUX_ELEMENTS = 2;
	private final Groupe GROUPE = mock(Groupe.class);
	private final Priorite PRIORITE_BASSE = Priorite.basse();
	private final Priorite PRIORITE_HAUTE = Priorite.haute();
	private final String TITRE_REUNION = "Titre";

	private Demande demandeFaiblePriorite;
	private Demande demandeHautePriorite;

	private ConteneurDemandesOrdrePrioritaire conteneurDemandes;

	@Before
	public void creerConteneurDemandes() {
		demandeFaiblePriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_BASSE);
		demandeHautePriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_HAUTE);
		conteneurDemandes = new ConteneurDemandesOrdrePrioritaire();
	}

	@Test
	public void devraitEtreInitialementVide() {
		assertEquals(AUCUN_ELEMENT, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurVideQuandAjouterDemandeDevraitContenirUneDemande() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		assertEquals(UN_ELEMENT, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurContenantDejaDemandesQuandAjouterDemandeDevraitContenirUneDemandeDePlus() {
		final int TAILLE_DESIREE = 3;
		ajouterDemandes(DEUX_ELEMENTS, demandeFaiblePriorite, conteneurDemandes);

		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);

		assertEquals(TAILLE_DESIREE, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurAvecUnElementQuandRetirerDemandeConteneurEstVide() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		conteneurDemandes.retirerDemande(demandeFaiblePriorite);
		assertEquals(AUCUN_ELEMENT, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurAvecDeuxElementQuandRetirerPremierElementDevraitContenirUnSeulElement() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		ajouterDemandes(UN_ELEMENT, demandeHautePriorite, conteneurDemandes);

		conteneurDemandes.retirerDemande(demandeHautePriorite);

		assertEquals(UN_ELEMENT, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurAvecUnElementQuandAppelContientDemandeAvecLaBonneDemandeDevraitRetournerTrue() {
		conteneurDemandes.ajouterDemande(demandeFaiblePriorite);
		assertTrue(conteneurDemandes.contientDemande(demandeFaiblePriorite));
	}

	@Test
	public void etantDonneConteneurAvecUnElementQuandAppelContientDemandeAvecMauvaiseDemandeDevraitRetournerFalse() {
		conteneurDemandes.ajouterDemande(demandeFaiblePriorite);
		assertFalse(conteneurDemandes.contientDemande(demandeHautePriorite));
	}

	@Test
	public void etantDonneConteneurContenantDemandesQuandViderDevraitNePlusContenirElement() {
		ajouterDemandes(DEUX_ELEMENTS, demandeFaiblePriorite, conteneurDemandes);
		conteneurDemandes.vider();
		assertEquals(AUCUN_ELEMENT, conteneurDemandes.taille());
	}

	@Test
	public void etantDonneConteneurNonVideQuandItereElementsDevraitTrierSelonOrdreDecroissantPriorite() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		ajouterDemandes(UN_ELEMENT, demandeHautePriorite, conteneurDemandes);

		assertThat(conteneurDemandes, estEnOrdrePrioritaireDecroissant());
	}

	@Test
	public void etantDonneConteneurContenantMoinsDeXElementsQuandContientAuMoinsXElementsDevraitRepondreFaux() {
		assertFalse(conteneurDemandes.contientAuMoins(DEUX_ELEMENTS));
	}

	@Test
	public void etantDonneConteneurContenantExactementXElementsQuandContientAuMoinsXElementDevraitRepondreVrai() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		assertTrue(conteneurDemandes.contientAuMoins(UN_ELEMENT));
	}

	@Test
	public void etantDonneConteneurContenantPlusDeXElementsQuandContientAuMoinsXElementDevraitRepondreVrai() {
		ajouterDemandes(DEUX_ELEMENTS, demandeFaiblePriorite, conteneurDemandes);
		assertTrue(conteneurDemandes.contientAuMoins(UN_ELEMENT));
	}

	private EstEnOrdrePrioritaireDecroissant estEnOrdrePrioritaireDecroissant() {
		return new EstEnOrdrePrioritaireDecroissant();
	}

	private class EstEnOrdrePrioritaireDecroissant extends ArgumentMatcher<ConteneurDemandesOrdrePrioritaire> {

		public boolean matches(Object conteneurDemandes) {

			Demande derniereDemande = null;
			for (Demande demandeCourante : (ConteneurDemandesOrdrePrioritaire) conteneurDemandes) {
				if (derniereDemande != null) {
					if (demandeCourante.estPlusPrioritaire(derniereDemande))
						return false;
				}

				derniereDemande = demandeCourante;
			}

			return true;
		}
	}

	private void ajouterDemandes(int nombreOccurances, Demande demande,
			ConteneurDemandesOrdrePrioritaire conteneurDemandes) {
		for (int i = 0; i < nombreOccurances; i++)
			conteneurDemandes.ajouterDemande(demande);
	}
}
