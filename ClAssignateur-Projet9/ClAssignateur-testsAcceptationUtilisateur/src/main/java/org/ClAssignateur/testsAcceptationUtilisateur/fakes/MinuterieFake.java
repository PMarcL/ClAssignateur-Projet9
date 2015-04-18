package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.services.reservations.minuterie.Minuterie;

public class MinuterieFake extends Minuterie {

	@Override
	protected void demarrerImplementation() {

	}

	@Override
	protected void reinitialiserImplementation() {

	}

	public void atteindreFrequence() {
		this.notifierObservateurs();
	}

}
