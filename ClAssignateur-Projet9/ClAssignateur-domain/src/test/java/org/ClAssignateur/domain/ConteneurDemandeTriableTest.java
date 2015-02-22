package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandeTriableTest {

	private final int TAILLE_INITIALE_VOULUE = 0;

	private final Calendar DATE_DEBUT = creerDate(2015, 07, 1, 12, 29, 0);
	private final Calendar DATE_FIN = creerDate(2015, 07, 1, 12, 30, 0);
	private final String ORGANISATEUR = "Simon";
	private final int NOMBRE_PARTICIPANT = 10;

	private final Calendar DATE_DEBUT_2 = creerDate(2015, 07, 3, 12, 29, 0);
	private final Calendar DATE_FIN_2 = creerDate(2015, 07, 4, 12, 30, 0);
	private final int NOMBRE_PARTICIPANT_2 = 11;

	private final Calendar DATE_DEBUT_3 = creerDate(2015, 07, 2, 12, 29, 0);
	private final Calendar DATE_FIN_3 = creerDate(2015, 07, 3, 12, 30, 0);
	private final int NOMBRE_PARTICIPANT_3 = 12;

	private final Demande DEMANDE = new Demande(DATE_DEBUT, DATE_FIN,
			NOMBRE_PARTICIPANT, ORGANISATEUR);
	private final Demande DEMANDE_2 = new Demande(DATE_DEBUT_2, DATE_FIN_2,
			NOMBRE_PARTICIPANT_2, ORGANISATEUR);
	private final Demande DEMANDE_3 = new Demande(DATE_DEBUT_3, DATE_FIN_3,
			NOMBRE_PARTICIPANT_3, ORGANISATEUR);

	private final Demande DEMANDE_PRIORITE_FORTE = new Demande(DATE_DEBUT_3,
			DATE_FIN_3, NOMBRE_PARTICIPANT_3, ORGANISATEUR, 3);
	private final Demande DEMANDE_PRIORITE_MOYENNE = new Demande(DATE_DEBUT_2,
			DATE_FIN_2, NOMBRE_PARTICIPANT_2, ORGANISATEUR, 2);
	private final Demande DEMANDE_PRIORITE_FAIBLE = new Demande(DATE_DEBUT,
			DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR, 1);
	private ConteneurDemandeTriable fileDemande;

	private Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void creerFileDemande() {
		fileDemande = new ConteneurDemandeTriable();
	}

	@Test
	public void fileDemandeEstInitialementVide() {
		boolean fileEstVide = fileDemande.estVide();
		assertTrue(fileEstVide);
	}

	@Test
	public void filePossedeInitalementTailleZero() {
		int tailleInitiale = fileDemande.taille();
		assertEquals(TAILLE_INITIALE_VOULUE, tailleInitiale);
	}

	@Test
	public void lorsqueOnAjouteUneDemandeAFileDemandeLaFileNestPlusVide() {
		fileDemande.ajouter(DEMANDE);

		boolean fileEstVide = fileDemande.estVide();

		assertFalse(fileEstVide);
	}

	@Test
	public void lorsqueOnAjouteUneDemandeAFileDemandeSaTailleAugmenteDeUn() {
		int tailleInitiale = fileDemande.taille();

		fileDemande.ajouter(DEMANDE);
		int tailleDeLaFile = fileDemande.taille();
		int tailleDesiree = tailleInitiale + 1;

		assertEquals(tailleDesiree, tailleDeLaFile);
	}

	@Test
	public void lorsqueOnVideLaFileElleDevientVide() {
		fileDemande.ajouter(DEMANDE);

		fileDemande.vider();
		boolean fileEstVide = fileDemande.estVide();

		assertTrue(fileEstVide);
	}

	@Test
	public void lorsqueOnAjouteUnElementEtQuOnLEnleveFileDemandeDevientVide()
			throws Throwable {
		fileDemande.ajouter(DEMANDE);

		fileDemande.retirer();
		boolean fileEstVide = fileDemande.estVide();

		assertTrue(fileEstVide);
	}

	@Test(expected = Exception.class)
	public void lorsqueLaFileEstVideRetirerLanceUneException() throws Throwable {
		fileDemande.retirer();
	}

	@Test
	public void lorsqueOnEnleveDesElementsLePremierArriveEstLePremierSortiPourUneMemePriorite()
			throws Throwable {
		ConteneurDemandeTriable fileDemandeTroisElement = new ConteneurDemandeTriable();

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
		ConteneurDemandeTriable fileDemandeTroisElement = new ConteneurDemandeTriable();

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

}
