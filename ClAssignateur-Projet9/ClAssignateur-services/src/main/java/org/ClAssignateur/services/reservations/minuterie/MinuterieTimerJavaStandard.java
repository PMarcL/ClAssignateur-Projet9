package org.ClAssignateur.services.reservations.minuterie;

import java.util.TimerTask;
import java.util.Timer;

public class MinuterieTimerJavaStandard extends Minuterie {

	private Timer minuterie;

	protected void demarrerImplementation() {
		this.minuterie = new Timer();
		int delaiMillisecondes = this.delai.getDelaiMillisecondes();
		TimerTask tache = creerTacheMinuterie();
		this.minuterie.scheduleAtFixedRate(tache, delaiMillisecondes, delaiMillisecondes);
	}

	private TimerTask creerTacheMinuterie() {
		TimerTask tache = new TimerTask() {
			public void run() {
				notifierObservateurs();
			}
		};

		return tache;
	}

	protected void reinitialiserImplementation() {
		this.minuterie.cancel();
		demarrerImplementation();
	}

}
