package org.ClAssignateur.domain;

import java.util.Calendar;

public class StrategieDelaiDepasse implements IStrategieDeclenchementAssignation {

	@Override
	public boolean verifierConditionAtteinte(IStrategieDeclenchementAssignationContexte contexte) {
		Calendar prochaineAssignation = (Calendar) contexte.getDerniereAssignation().clone();
		prochaineAssignation.add(Calendar.MINUTE, contexte.getFrequence());

		Calendar maintenant = Calendar.getInstance();

		return (maintenant.compareTo(prochaineAssignation) >= 0);
	}

}
