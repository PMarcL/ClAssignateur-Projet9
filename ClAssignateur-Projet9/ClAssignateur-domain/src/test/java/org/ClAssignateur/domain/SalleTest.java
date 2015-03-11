package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final String NOM_SALLE = "salle";
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANT_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANT_SUPERIEUR_A_CAPACITE = 150;

	private Salle salle;

	@Before
	public void initialisation() {
		salle = new Salle(CAPACITE_INITIALE, NOM_SALLE);
	}

	@Test
	public void UneSalleRetourneSonNom() {
		String nom = salle.getNom();
		assertTrue(nom.equals(NOM_SALLE));
	}

	@Test
	public void UneSallePeutAccueillirUnNombreDeParticipantInferieurASaCapacite() {
		assertTrue(salle.peutAccueillir(NB_PARTICIPANT_INFERIEUR_A_CAPACITE));
	}

	@Test
	public void UneSalleNePeutPasAccueillirUnNombreDeParticipantSuperieurASaCapacite() {
		assertFalse(salle.peutAccueillir(NB_PARTICIPANT_SUPERIEUR_A_CAPACITE));
	}

}
