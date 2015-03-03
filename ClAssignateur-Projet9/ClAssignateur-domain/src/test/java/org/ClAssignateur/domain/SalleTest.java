package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final Demande DEMANDE_AJOUTER = new Demande(100, "P-M");
	private final String NOM_INITIALE = "nomSalle";
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANT_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANT_SUPERIEUR_A_CAPACITE = 150;

	private Salle salle;

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
	public void UneSalleContientInitialementAucuneReservation() {
		assertEquals(salle.getNbReservation(), 0);
	}

	@Test
	public void UneSalleApresReservationContientUneReservation() {
		salle.placerReservation(DEMANDE_AJOUTER);
		assertEquals(salle.getNbReservation(), 1);
	}
}
