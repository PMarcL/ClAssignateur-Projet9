package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesTriableTest {

	private final int TAILLE_INITIALE_VOULUE = 0;
	private final int PRIORITE_FAIBLE = 1;
	private final int PRIORITE_MOYENNE = 3;
	private final int PRIORITE_FORTE = 5;

	private Demande DEMANDE = mock(Demande.class);
	private final Demande DEMANDE_2 = mock(Demande.class);
	private final Demande DEMANDE_3 = mock(Demande.class);
	private final Demande DEMANDE_PRIORITE_FORTE = mock(Demande.class);
	private final Demande DEMANDE_PRIORITE_MOYENNE = mock(Demande.class);
	private final Demande DEMANDE_PRIORITE_FAIBLE = mock(Demande.class);

	private ConteneurDemandesTriable conteneurDemandeTriable;

	@Before
	public void creerFileDemande() {
		conteneurDemandeTriable = new ConteneurDemandesTriable();
	}

	@Test
	public void ConteneurDemandeTriableEstInitialementVide() {
		boolean fileEstVide = conteneurDemandeTriable.estVide();
		assertTrue(fileEstVide);
	}

	@Test
	public void ConteneurDemandeTriablePossedeInitalementTailleZero() {
		int tailleInitiale = conteneurDemandeTriable.taille();
		assertEquals(TAILLE_INITIALE_VOULUE, tailleInitiale);
	}

	@Test
	public void lorsqueOnAjouteUneDemandeAConteneurDemandeTriableIlNestPlusVide() {
		conteneurDemandeTriable.ajouter(DEMANDE);

		boolean fileEstVide = conteneurDemandeTriable.estVide();

		assertFalse(fileEstVide);
	}

	@Test
	public void lorsqueOnAjouteUneDemandeAConteneurDemandeTriableSaTailleAugmenteDeUn() {
		int tailleInitiale = conteneurDemandeTriable.taille();

		conteneurDemandeTriable.ajouter(DEMANDE);
		int tailleDeLaFile = conteneurDemandeTriable.taille();
		int tailleDesiree = tailleInitiale + 1;

		assertEquals(tailleDesiree, tailleDeLaFile);
	}

	@Test
	public void lorsqueOnVideConteneurDemandeTriableIlDevientVide() {
		conteneurDemandeTriable.ajouter(DEMANDE);

		conteneurDemandeTriable.vider();
		boolean fileEstVide = conteneurDemandeTriable.estVide();

		assertTrue(fileEstVide);
	}

	@Test
	public void lorsqueOnAjouteUnElementEtQuOnLEnleveConteneurDemandeTriableDevientVide()
			throws Throwable {
		conteneurDemandeTriable.ajouter(DEMANDE);

		conteneurDemandeTriable.retirer();
		boolean fileEstVide = conteneurDemandeTriable.estVide();

		assertTrue(fileEstVide);
	}

	@Test(expected = Exception.class)
	public void lorsqueLaFileEstVideRetirerLanceUneException() {
		conteneurDemandeTriable.retirer();
	}

	@Test
	public void lorsqueOnEnleveDesElementsLePremierArriveEstLePremierSortiPourUneMemePriorite() {
		ConteneurDemandesTriable fileDemandeTroisElement = new ConteneurDemandesTriable();
		configurationDesDemande();
		fileDemandeTroisElement.ajouter(DEMANDE);
		fileDemandeTroisElement.ajouter(DEMANDE_2);
		fileDemandeTroisElement.ajouter(DEMANDE_3);

		Demande premierElementEnlever = fileDemandeTroisElement.retirer();
		Demande deuxiemeElementEnlever = fileDemandeTroisElement.retirer();
		Demande troisiemeElementEnlever = fileDemandeTroisElement.retirer();

		assertEquals(DEMANDE, premierElementEnlever);
		assertEquals(DEMANDE_2, deuxiemeElementEnlever);
		assertEquals(DEMANDE_3, troisiemeElementEnlever);
	}

	@Test
	public void fileDemandePrioriseLesDemandesPlusPrioritaire()
			throws Throwable {
		ConteneurDemandesTriable fileDemandeTroisElement = new ConteneurDemandesTriable();
		configurationDesDemandeAvecPriorite();
		fileDemandeTroisElement.ajouter(DEMANDE_PRIORITE_FAIBLE);
		fileDemandeTroisElement.ajouter(DEMANDE_PRIORITE_MOYENNE);
		fileDemandeTroisElement.ajouter(DEMANDE_PRIORITE_FORTE);

		Demande premierElementEnlever = fileDemandeTroisElement.retirer();
		Demande deuxiemeElementEnlever = fileDemandeTroisElement.retirer();
		Demande troisiemeElementEnlever = fileDemandeTroisElement.retirer();

		assertEquals(DEMANDE_PRIORITE_FORTE, premierElementEnlever);
		assertEquals(DEMANDE_PRIORITE_MOYENNE, deuxiemeElementEnlever);
		assertEquals(DEMANDE_PRIORITE_FAIBLE, troisiemeElementEnlever);
	}

	private void configurationDesDemande() {
		Date premierTemps = new Date(2010, 1, 1, 1, 1, 0);
		Date deuxiemeTemps = new Date(2010, 1, 1, 1, 2, 0);
		Date troisiemeTemps = new Date(2010, 1, 1, 1, 3, 0);
		willReturn(premierTemps).given(DEMANDE).getMomentDeCreation();
		willReturn(deuxiemeTemps).given(DEMANDE_2).getMomentDeCreation();
		willReturn(troisiemeTemps).given(DEMANDE_3).getMomentDeCreation();
	}

	private void configurationDesDemandeAvecPriorite() {
		Date premierTemps = new Date(2010, 1, 1, 1, 1, 0);
		Date deuxiemeTemps = new Date(2010, 1, 1, 1, 2, 0);
		Date troisiemeTemps = new Date(2010, 1, 1, 1, 3, 0);
		willReturn(premierTemps).given(DEMANDE_PRIORITE_FAIBLE)
				.getMomentDeCreation();
		willReturn(deuxiemeTemps).given(DEMANDE_PRIORITE_MOYENNE)
				.getMomentDeCreation();
		willReturn(troisiemeTemps).given(DEMANDE_PRIORITE_FORTE)
				.getMomentDeCreation();
		willReturn(PRIORITE_FAIBLE).given(DEMANDE_PRIORITE_FAIBLE)
				.getPriorite();
		willReturn(PRIORITE_MOYENNE).given(DEMANDE_PRIORITE_MOYENNE)
				.getPriorite();
		willReturn(PRIORITE_FORTE).given(DEMANDE_PRIORITE_FORTE).getPriorite();
	}

}
