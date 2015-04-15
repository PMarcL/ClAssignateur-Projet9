package org.ClAssignateur.domain.demandes;

import static org.junit.Assert.*;

import org.ClAssignateur.domain.demandes.Priorite;
import org.junit.Before;
import org.junit.Test;

public class PrioriteTest {

	private Priorite prioriteTresBasse;
	private Priorite prioriteBasse;
	private Priorite prioriteMoyenne;
	private Priorite prioriteHaute;
	private Priorite prioriteTresHaute;

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

	@Test
	public void etantDonneNiveauPrioriteXQuandGetNiveauPrioriteDevraitRetournerX() {
		final int NIVEAU_PRIORITE = 50;
		Priorite priorite = new Priorite(NIVEAU_PRIORITE);
		assertEquals(NIVEAU_PRIORITE, priorite.getNiveauPriorite());
	}

	@Test(expected = NiveauPrioriteInvalideException.class)
	public void etantDonneNiveauPrioriteInferieurAUnQuandCreationDevraitLancerException() {
		final int NIVEAU_PRIORITE_INVALIDE = 0;
		new Priorite(NIVEAU_PRIORITE_INVALIDE);
	}

	@Test
	public void prioriteEstEgalAElleMeme() {
		assertTrue(prioriteBasse.equals(prioriteBasse));
	}

	@Test
	public void prioriteNEstPasEgalAPrioriteDifferente() {
		assertFalse(prioriteBasse.equals(prioriteHaute));
	}

	@Test
	public void prioriteNEstPasEgalANull() {
		assertFalse(prioriteHaute.equals(null));
	}
}
