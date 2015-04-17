package org.ClAssignateur.services.reservations.minuterie;

import java.util.ArrayList;
import java.util.List;

public abstract class Minuterie {
	private final int DELAI_PAR_DEFAULT = 1;

	private List<MinuterieObservateur> observateurs;
	private boolean enMarche;
	protected Minute delai;

	public Minuterie() {
		this.delai = new Minute(DELAI_PAR_DEFAULT);
		this.observateurs = new ArrayList<>();
		this.enMarche = false;
	}

	public void demarrer() {
		if (!this.enMarche) {
			demarrerImplementation();
			this.enMarche = true;
		}
	}

	protected abstract void demarrerImplementation();

	public void reinitialiser() {
		if (this.enMarche) {
			reinitialiserImplementation();
		}
	}

	protected abstract void reinitialiserImplementation();

	public void souscrire(MinuterieObservateur observateur) {
		if (!this.observateurs.contains(observateur))
			this.observateurs.add(observateur);
	}

	public void setDelai(Minute delai) {
		this.delai = delai;
	}

	protected void notifierObservateurs() {
		for (MinuterieObservateur observateur : this.observateurs)
			observateur.notifierDelaiEcoule();
	}

}
