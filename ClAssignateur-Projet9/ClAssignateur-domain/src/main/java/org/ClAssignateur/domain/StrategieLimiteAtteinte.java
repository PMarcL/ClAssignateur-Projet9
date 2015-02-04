package org.ClAssignateur.domain;

public class StrategieLimiteAtteinte implements
		IStrategieDeclenchementAssignation {

	@Override
	public boolean verifierConditionAtteinte(AssignateurSalle contexte) {
		return (contexte.getLimite() <= contexte
				.getNbDemandesAssignationCourantes());
	}

}
