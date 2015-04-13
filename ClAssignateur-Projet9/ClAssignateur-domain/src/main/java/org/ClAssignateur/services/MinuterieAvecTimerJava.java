package org.ClAssignateur.services;

import java.util.TimerTask;
import java.util.Timer;

public class MinuterieAvecTimerJava implements MinuterieStrategie {

	private Timer minuterie;

	public MinuterieAvecTimerJava() {
		this.minuterie = new Timer();
	}

	public void planifierAppelPeriodique(TimerTask classeAppelee, long delaiInitiale, long delaiPeriodique) {
		minuterie.scheduleAtFixedRate(classeAppelee, delaiInitiale, delaiPeriodique);
	}

	public void annulerAppelPeriodique() {
		minuterie.cancel();
	}

}
