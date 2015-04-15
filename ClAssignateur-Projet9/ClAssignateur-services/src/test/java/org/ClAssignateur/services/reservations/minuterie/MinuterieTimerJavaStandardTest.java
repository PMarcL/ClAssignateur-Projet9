package org.ClAssignateur.services.reservations.minuterie;

import static org.mockito.Mockito.*;

import org.ClAssignateur.services.reservations.minuterie.MinuterieObservateur;
import org.ClAssignateur.services.reservations.minuterie.MinuterieTimerJavaStandard;
import org.junit.Test;

public class MinuterieTimerJavaStandardTest {

	@Test
	public void etantDonneUnObservateurSouscritQuandRunDevraitNotifierObservateur() {
		MinuterieTimerJavaStandard minuterie = new MinuterieTimerJavaStandard();
		MinuterieObservateur observateur = mock(MinuterieObservateur.class);
		minuterie.souscrire(observateur);

		minuterie.run();

		verify(observateur).notifierDelaiEcoule();
	}
}
