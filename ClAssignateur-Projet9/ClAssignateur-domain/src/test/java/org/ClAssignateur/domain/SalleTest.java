package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final Demande DEMANDE_AJOUTER = new Demande(creerDate(2015, 01, 28, 11, 0, 0), creerDate(2015, 01, 28, 12,
			0, 0), 100, "P-M");
	private final Demande DEMANDE_EN_CONFLIT = new Demande(creerDate(2015, 01, 28, 11, 0, 0), creerDate(2015, 01, 28,
			12, 0, 0), 100, "P-M");
	private final Demande DEMANDE_SANS_CONFLIT = new Demande(creerDate(2015, 02, 28, 11, 0, 0), creerDate(2015, 02, 28,
			12, 0, 0), 100, "P-M");
	private final String NOM_INITIALE = "nomSalle";
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANT_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANT_SUPERIEUR_A_CAPACITE = 150;

	private Salle salle;

	public static Calendar creerDate(int annee, int mois, int jour, int heure, int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void initialisation() {
		salle = new Salle(NOM_INITIALE, CAPACITE_INITIALE);
	}

	@Test
	public void UneNouvelleSalleRetourneSonNom() {
		String nomRecu = salle.getNom();

		assertEquals(NOM_INITIALE, nomRecu);
	}

	@Test
	public void UneSallePeutAccueillirUnNombreDeParticipantInferieurASaCapacite() {
		assertTrue(salle.peutAccueillir(NB_PARTICIPANT_INFERIEUR_A_CAPACITE));
	}

	@Test
	public void UneSalleNePeutPasAccueillirUnNombreDeParticipantSuperieurASaCapacite() {
		assertFalse(salle.peutAccueillir(NB_PARTICIPANT_SUPERIEUR_A_CAPACITE));
	}

	@Test
	public void UneNouvelleSalleSansReservationEstDisponible() {
		assertTrue(salle.estDisponible(DEMANDE_AJOUTER));
	}

	@Test
	public void UneNouvelleSalleApresReservationNEstPlusDisponibleALaDateDemande() {
		salle.placerReservation(DEMANDE_AJOUTER);
		assertFalse(salle.estDisponible(DEMANDE_EN_CONFLIT));
	}

	@Test
	public void SalleAvecUneReservationEstDisponibleAUneAutreDate() {
		salle.placerReservation(DEMANDE_AJOUTER);
		assertTrue(salle.estDisponible(DEMANDE_SANS_CONFLIT));
	}

	@Test(expected = IllegalArgumentException.class)
	public void SalleAvecUneReservationNePeutAjouterUneReservationALaMemeDate() {
		salle.placerReservation(DEMANDE_AJOUTER);
		salle.placerReservation(DEMANDE_AJOUTER);
	}

}
