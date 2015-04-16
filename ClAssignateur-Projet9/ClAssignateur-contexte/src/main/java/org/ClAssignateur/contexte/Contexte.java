package org.ClAssignateur.contexte;

public abstract class Contexte {

	public void appliquer() {
		enregistrerServices();
		injecterDonnees();
	}

	protected abstract void enregistrerServices();

	protected abstract void injecterDonnees();

}
