package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final Demande DEMANDE_AJOUTER = new Demande(100, "P-M");
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANT_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANT_SUPERIEUR_A_CAPACITE = 150;
	private final long POURCENTAGE_OCCUPATION_DE_50_PARTICIPANT = (long) 0.5;

	private Salle salle;

	@Before
	public void initialisation() {
		salle = new Salle(CAPACITE_INITIALE);
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
	public void UneSalleCalculeSonPourcentageOccupationSelonUnNombreDeParticipants() {
		long resultat = salle.pourcentageOccupation(NB_PARTICIPANT_INFERIEUR_A_CAPACITE);
		assertEquals(POURCENTAGE_OCCUPATION_DE_50_PARTICIPANT, resultat);
	}

	@Test
	public void UneSalleContientInitialementAucuneReservation() {
		assertEquals(salle.getNbReservation(), 0);
	}

	@Test
	public void UneSalleApresReservationContientUneReservation() {
		salle.placerReservation(DEMANDE_AJOUTER);
		assertEquals(salle.getNbReservation(), 1);
	}
}
