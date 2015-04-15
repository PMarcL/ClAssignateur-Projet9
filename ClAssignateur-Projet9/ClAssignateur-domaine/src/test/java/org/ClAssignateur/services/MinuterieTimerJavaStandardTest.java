package org.ClAssignateur.services;

import static org.mockito.Mockito.*;
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
