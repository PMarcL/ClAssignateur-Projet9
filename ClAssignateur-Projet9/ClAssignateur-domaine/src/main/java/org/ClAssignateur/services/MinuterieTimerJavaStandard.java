package org.ClAssignateur.services;

import java.util.TimerTask;
import java.util.Timer;

public class MinuterieTimerJavaStandard extends TimerTask implements Minuterie {

	private Timer minuterie;
	private Minute delai;
	private MinuterieObservateur observateur;

	public void souscrire(MinuterieObservateur observateur) {
		this.observateur = observateur;
	}

	public void setDelai(Minute delai) {
		this.delai = delai;
	}

	public void demarrer() {
		initialiserMinuterie();
	}

	private void initialiserMinuterie() {
		this.minuterie = new Timer();
		int delaiMillisecondes = this.delai.getDelaiMillisecondes();
		this.minuterie.scheduleAtFixedRate(this, delaiMillisecondes, delaiMillisecondes);
	}

	public void reinitialiser() {
		this.minuterie.cancel();
		initialiserMinuterie();
	}

	@Override
	public void run() {
		this.observateur.notifierDelaiEcoule();
	}

}
