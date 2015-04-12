package org.ClAssignateur.services;

import java.util.TimerTask;

public interface MinuterieStrategie {

	public void planifierAppelPeriodique(TimerTask classeAppelee, long delaiInitiale, long delaiPeriodique);

	public void annulerAppelPeriodique();
}
