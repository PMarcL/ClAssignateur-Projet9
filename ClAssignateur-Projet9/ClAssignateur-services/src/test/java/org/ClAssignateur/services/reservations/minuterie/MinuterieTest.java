package org.ClAssignateur.services.reservations.minuterie;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class MinuterieTest extends Minuterie {

	private MinuterieTest minuterie;

	@Before
	public void initialisation() {
		minuterie = new MinuterieTest();
	}

	@Test
	public void quandInstanciationDevraitAvoirDelaiUneMinuteParDefaut() {
		final Minute DELAI_PAR_DEFAUT = new Minute(1);
		assertTrue(DELAI_PAR_DEFAUT.equals(minuterie.delai));
	}

	@Test
	public void quandSetDelaiDevraitEnregistrerNouveauDelai() {
		final Minute AUTRE_DELAI = new Minute(5);
		minuterie.setDelai(AUTRE_DELAI);
		assertTrue(AUTRE_DELAI.equals(minuterie.delai));
	}

	@Test
	public void etantDonneUnObservateurSouscritQuandNotifierObservateursDevraitNotifierObservateurDelaiEcoule() {
		MinuterieObservateur observateur = souscrireObservateur();
		minuterie.notifierObservateurs();
		verify(observateur).notifierDelaiEcoule();
	}

	@Test
	public void etantDonnePlusieursObservateurSouscritsQuandNotifierObservateursDevraitNotifierChaqueObservateurDelaiEcoule() {
		MinuterieObservateur observateur1 = souscrireObservateur();
		MinuterieObservateur observateur2 = souscrireObservateur();

		minuterie.notifierObservateurs();

		verify(observateur1).notifierDelaiEcoule();
		verify(observateur2).notifierDelaiEcoule();
	}

	@Test
	public void etantDonneMinuteriePasDemarreeQuandDemarrerDevraitDemarrerImplementation() {
		minuterie.demarrer();
		assertEquals(1, minuterie.demarrerImplementationAppelCompte);
	}

	@Test
	public void etantDonneMinuterieDejaDemarreeQuandDemarrerDevraitPasDemarrerImplementation() {
		minuterie.demarrer();
		minuterie.demarrer();
		assertEquals(1, minuterie.demarrerImplementationAppelCompte);
	}

	@Test
	public void etantDonneMinuterieDemarreeQuandReinitialiserDevraitReinitialiserImplementation() {
		minuterie.demarrer();
		minuterie.reinitialiser();
		assertTrue(minuterie.reinitialiserImplementationAppel);
	}

	@Test
	public void etantDonneMinuteriePasDemarreeQuandReinitialiserDevraitPasReinitialiserImplementation() {
		minuterie.reinitialiser();
		assertFalse(minuterie.reinitialiserImplementationAppel);
	}

	private MinuterieObservateur souscrireObservateur() {
		MinuterieObservateur observateur = mock(MinuterieObservateur.class);
		minuterie.souscrire(observateur);
		return observateur;
	}

	private int demarrerImplementationAppelCompte;
	private boolean reinitialiserImplementationAppel;

	public MinuterieTest() {
		demarrerImplementationAppelCompte = 0;
		reinitialiserImplementationAppel = false;
	}

	protected void reinitialiserImplementation() {
		reinitialiserImplementationAppel = true;
	}

	protected void demarrerImplementation() {
		demarrerImplementationAppelCompte++;
	}

}
