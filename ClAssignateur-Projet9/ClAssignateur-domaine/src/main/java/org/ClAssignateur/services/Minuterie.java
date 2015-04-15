package org.ClAssignateur.services;

public interface Minuterie {

	public void souscrire(MinuterieObservateur observateur);

	public void setDelai(Minute delai);

	public void reinitialiser();

	public void demarrer();
}
