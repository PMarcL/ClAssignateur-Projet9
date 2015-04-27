package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;

public class MinuterieFake extends Minuterie {

	private boolean reinitialisee;

	public MinuterieFake() {
		this.reinitialisee = false;
	}

	@Override
	protected void demarrerImplementation() {

	}

	@Override
	protected void reinitialiserImplementation() {
		this.reinitialisee = true;
	}

	public void atteindreFrequence() {
		this.notifierObservateurs();
	}

	public Minute getDelai() {
		return this.delai;
	}

	public boolean aEteReinitialisee() {
		return this.reinitialisee;
	}
}
