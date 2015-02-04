package org.ClAssignateur.domain;

public class AssignateurSalle {

	private IStrategieDeclenchementAssignation strategieAssignation;
	private int frequence;
	private int limite;

	public AssignateurSalle(int frequence, int limite) {
		this.frequence = frequence;
		this.limite = limite;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public void assignerDemandeSalle(FileDemande demandes, EntrepotSalles salles) {
		strategieAssignation.verifierConditionAtteinte(this);
	}

	public void setStrategieDeclenchementAssignation(
			IStrategieDeclenchementAssignation strategie) {
		strategieAssignation = strategie;
	}
}
