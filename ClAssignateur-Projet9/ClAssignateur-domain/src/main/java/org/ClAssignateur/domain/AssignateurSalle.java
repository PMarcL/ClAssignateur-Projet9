package org.ClAssignateur.domain;

import java.util.Calendar;

public class AssignateurSalle implements IStrategieDeclenchementAssignationContexte {

	private IStrategieDeclenchementAssignation strategieDeclenchement;
	private int frequence;
	private int limite;
	private int nbDemandesAssignCourantes;
	private Calendar derniereAssignation;

	public AssignateurSalle(int frequence, int limite, IStrategieDeclenchementAssignation strategieDeclenchement) {
		this.frequence = frequence;
		this.limite = limite;
		this.strategieDeclenchement = strategieDeclenchement;
		this.nbDemandesAssignCourantes = 0;
		this.derniereAssignation = Calendar.getInstance();
	}

	public int getFrequence() {
		return this.frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public int getLimite() {
		return this.limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public void setStrategieDeclenchementAssignation(IStrategieDeclenchementAssignation strategie) {
		this.strategieDeclenchement = strategie;
	}

	public int getNbDemandesAssignationCourantes() {
		return this.nbDemandesAssignCourantes;
	}

	public Calendar getDerniereAssignation() {
		return Calendar.getInstance();
	}

	public void assignerDemandeSalle(FileDemande demandes, EntrepotSalles salles) {
		this.nbDemandesAssignCourantes = demandes.taille();

		if (demandes.estVide())
			return;

		strategieDeclenchement.verifierConditionAtteinte((IStrategieDeclenchementAssignationContexte) this);
	}
}
