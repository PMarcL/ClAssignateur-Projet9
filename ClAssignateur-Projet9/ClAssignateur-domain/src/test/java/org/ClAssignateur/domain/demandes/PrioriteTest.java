package org.ClAssignateur.domain.demandes;

import static org.junit.Assert.*;

import org.ClAssignateur.domain.demandes.Priorite;
import org.junit.Before;
import org.junit.Test;

public class PrioriteTest {

	Priorite prioriteTresBasse;
	Priorite prioriteBasse;
	Priorite prioriteMoyenne;
	Priorite prioriteHaute;
	Priorite prioriteTresHaute;

	@Before
	public void creerPriorites() {
		prioriteTresBasse = Priorite.tresBasse();
		prioriteBasse = Priorite.basse();
		prioriteMoyenne = Priorite.moyenne();
		prioriteHaute = Priorite.haute();
		prioriteTresHaute = Priorite.tresHaute();
	}

	@Test
	public void prioriteBasseEstPlusPrioritaireQuePrioriteTresBasse() {
		assertTrue(prioriteBasse.estPlusPrioritaire(prioriteTresBasse));
	}

	@Test
	public void prioriteMoyenneEstPlusPrioritaireQuePrioriteBasse() {
		assertTrue(prioriteMoyenne.estPlusPrioritaire(prioriteBasse));
	}

	@Test
	public void prioriteHauteEstPlusPrioriteaireQuePrioriteMoyenne() {
		assertTrue(prioriteHaute.estPlusPrioritaire(prioriteMoyenne));
	}

	@Test
	public void prioriteTresHautePlusPrioritaireQuePrioriteHaute() {
		assertTrue(prioriteTresHaute.estPlusPrioritaire(prioriteHaute));
	}

	@Test
	public void deuxPrioriteEquivalentesNeSontPasPlusPrioritaire() {
		assertFalse(prioriteTresBasse.estPlusPrioritaire(prioriteTresBasse));
		assertFalse(prioriteBasse.estPlusPrioritaire(prioriteBasse));
		assertFalse(prioriteMoyenne.estPlusPrioritaire(prioriteMoyenne));
		assertFalse(prioriteHaute.estPlusPrioritaire(prioriteHaute));
		assertFalse(prioriteTresHaute.estPlusPrioritaire(prioriteTresHaute));
	}
}
