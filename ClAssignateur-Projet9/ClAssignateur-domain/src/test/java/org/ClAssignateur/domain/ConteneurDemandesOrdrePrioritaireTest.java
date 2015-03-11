package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import org.mockito.ArgumentMatcher;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesOrdrePrioritaireTest {

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
		assertTrue(conteneurDemandes.estVide());
	}

	@Test
	public void etantDonneConteneurVideQuandAjouterDemandeDevraitContenirUneDemande() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);

		assertTrue(conteneurDemandes.contientAuMoins(UN_ELEMENT));
		assertFalse(conteneurDemandes.contientAuMoins(DEUX_ELEMENTS));
	}

	@Test
	public void etantDonneConteneurAvecUnElementQuandRetirerDemandeConteneurEstVide() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		conteneurDemandes.retirerDemande(demandeFaiblePriorite);
		assertTrue(conteneurDemandes.estVide());
	}

	@Test
	public void etantDonneConteneurAvecDeuxElementQuandRetirerPremierElementDevraitContenirUnSeulElement() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		ajouterDemandes(UN_ELEMENT, demandeHautePriorite, conteneurDemandes);

		conteneurDemandes.retirerDemande(demandeHautePriorite);

		assertTrue(conteneurDemandes.contientAuMoins(UN_ELEMENT));
	}

	@Test
	public void etantDonneConteneurAvecUnElementQuandRetirerElementPasDansConteneurTailleInchangee() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		conteneurDemandes.retirerDemande(demandeHautePriorite);
		assertTrue(conteneurDemandes.contientAuMoins(UN_ELEMENT));
	}

	@Test
	public void etantDonneConteneurContenantDejaDemandesQuandAjouterDemandeDevraitContenirUneDemandeDePlus() {
		ajouterDemandes(DEUX_ELEMENTS, demandeFaiblePriorite, conteneurDemandes);
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);

		assertTrue(conteneurDemandes.contientAuMoins(DEUX_ELEMENTS));
		assertTrue(conteneurDemandes.contientAuMoins(DEUX_ELEMENTS + UN_ELEMENT));
		assertFalse(conteneurDemandes.contientAuMoins(DEUX_ELEMENTS + DEUX_ELEMENTS));
	}

	@Test
	public void etantDonneConteneurContenantDemandesQuandViderDevraitNePlusContenirElement() {
		ajouterDemandes(DEUX_ELEMENTS, demandeFaiblePriorite, conteneurDemandes);
		conteneurDemandes.vider();
		assertFalse(conteneurDemandes.contientAuMoins(UN_ELEMENT));
	}

	@Test
	public void etantDonneConteneurNonVideQuandItereElementsDevraitTrierSelonOrdreDecroissantPriorite() {
		ajouterDemandes(UN_ELEMENT, demandeFaiblePriorite, conteneurDemandes);
		ajouterDemandes(UN_ELEMENT, demandeHautePriorite, conteneurDemandes);

		assertThat(conteneurDemandes, estEnOrdrePrioritaireDecroissant());
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
