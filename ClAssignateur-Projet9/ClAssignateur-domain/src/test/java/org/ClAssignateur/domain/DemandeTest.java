package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.RangeException;

public class DemandeTest {

	private static final Calendar DATE_DEBUT = creerDate(2015, 07, 1, 12, 29, 0);
	private static final Calendar DATE_FIN = creerDate(2015, 07, 1, 12, 30, 0);
	private static final Calendar DATE_DEBUT_ULTERIEUR = creerDate(2015, 8, 1,
			12, 29, 0);
	private static final Calendar DATE_FIN_ULTERIEUR = creerDate(2015, 8, 1,
			12, 30, 0);
	private static final String ORGANISATEUR = "Simon";
	private static final int NOMBRE_PARTICIPANT = 10;
	private static final int NOMBRE_PARTICIPANT_INCORRECTE = 0;

	private Demande demande;

	public static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void creerLaDemande() {
		demande = new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT,
				ORGANISATEUR);
	}

	@Test
	public void DemandePossedeIntialementLeChampsDebutCommeDefiniDansLeConstructeur() {
		Calendar dateDebut = demande.getDebut();
		assertEquals(DATE_DEBUT, dateDebut);
	}

	@Test
	public void DemandePossedeIntialementLeChampsFinCommeDefiniDansLeConstructeur() {
		Calendar dateFin = demande.getFin();
		assertEquals(DATE_FIN, dateFin);
	}

	@Test
	public void DemandePossedeIntialementLeChampsNbParticipantCommeDefiniDansLeConstructeur() {
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void DemandePossedeIntialementLeChampsOrganisateurCommeDefiniDansLeConstructeur() {
		String organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}

	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneDateDeDebutInferieurALaDateDeFin() {
		new Demande(DATE_FIN, DATE_DEBUT, NOMBRE_PARTICIPANT, ORGANISATEUR);
	}

	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneDateDeDebutStrictementInferieurALaDateDeFin() {
		new Demande(DATE_FIN, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
	}

	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneNombreDeParticipantsStrictementSuperieurAuNombreDeParticipantMinimum() {
		new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT_INCORRECTE,
				ORGANISATEUR);
	}

	@Test
	public void uneDemandeEstEnConflitAvecElleMeme() {
		boolean estEnConflit = demande.estEnConflitAvec(demande);
		assertTrue(estEnConflit);
	}

	@Test
	public void uneDemandeEstEnConflitAvecUneDemandeAyantLaMemeHeureDeDebutQueSonHeureDeFin() {
		Demande demandeEnConflit = new Demande(DATE_FIN, DATE_DEBUT_ULTERIEUR,
				NOMBRE_PARTICIPANT, ORGANISATEUR);
		boolean estEnConflit = demande.estEnConflitAvec(demandeEnConflit);
		assertTrue(estEnConflit);
	}

	@Test
	public void uneDemandeEstEnConflitAvecUneDemandeAyantLaMemeHeureDeFinQueSonHeureDeDebut() {
		Demande demandeEnConflit = new Demande(DATE_FIN, DATE_DEBUT_ULTERIEUR,
				NOMBRE_PARTICIPANT, ORGANISATEUR);
		boolean estEnConflit = demandeEnConflit.estEnConflitAvec(demande);
		assertTrue(estEnConflit);
	}

	@Test
	public void uneDemandeNEstPasEnConflitAvecUneDemandeQuiSeDerouleApresElle() {
		Demande demandeSansConflit = new Demande(DATE_DEBUT_ULTERIEUR,
				DATE_FIN_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);
		boolean estEnConflit = demande.estEnConflitAvec(demandeSansConflit);
		assertFalse(estEnConflit);
	}

	@Test
	public void uneDemandeNEstPasEnConflitAvecUneDemandeQuiSeDerouleAvantElle() {
		Demande demandeSansConflit = new Demande(DATE_DEBUT_ULTERIEUR,
				DATE_FIN_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);
		boolean estEnConflit = demandeSansConflit.estEnConflitAvec(demande);
		assertFalse(estEnConflit);
	}

	@Test
	public void uneDemandeEstEnConflitAvecUneDemandeQuiLaChevaucheEtQuiTermineApresElle() {
		Demande premiereDemandeEnConflit = new Demande(DATE_DEBUT,
				DATE_DEBUT_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);
		Demande deuxiemeDemandeEnConflit = new Demande(DATE_FIN,
				DATE_FIN_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);

		boolean estEnConflit = premiereDemandeEnConflit
				.estEnConflitAvec(deuxiemeDemandeEnConflit);

		assertTrue(estEnConflit);
	}

	@Test
	public void uneDemandeEstEnConflitAvecUneDemandeQuiLaChevaucheEtQuiTermineAvantElle() {
		Demande premiereDemandeEnConflit = new Demande(DATE_DEBUT,
				DATE_DEBUT_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);
		Demande deuxiemeDemandeEnConflit = new Demande(DATE_FIN,
				DATE_FIN_ULTERIEUR, NOMBRE_PARTICIPANT, ORGANISATEUR);

		boolean estEnConflit = deuxiemeDemandeEnConflit
				.estEnConflitAvec(premiereDemandeEnConflit);

		assertTrue(estEnConflit);
	}
}
