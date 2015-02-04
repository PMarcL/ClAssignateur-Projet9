package org.ClAssignateur.domain;

import java.util.Calendar;

public class AssignateurSalle {

	private IStrategieDeclenchementAssignation strategieDeclenchement;
	private int frequence;
	private int limite;
	private int nbDemandesAssignCourantes;
	private Calendar derniereAssignation;

	public AssignateurSalle(int frequence, int limite,
			IStrategieDeclenchementAssignation strategieDeclenchement) {
		this.frequence = frequence;
		this.limite = limite;
		this.strategieDeclenchement = strategieDeclenchement;
		this.nbDemandesAssignCourantes = 0;
		this.derniereAssignation = Calendar.getInstance();
	}

	public int getFrequence() {
		return 0;
	}

	public void setFrequence(int frequence) {
	}

	public int getLimite() {
		return 0;
	}

	public void setLimite(int limite) {
	}

	public int getNbDemandesAssignationCourantes() {
		return 0;
	}

	public Calendar getDerniereAssignation() {
		return Calendar.getInstance();
	}

	public void assignerDemandeSalle(FileDemande demandes, EntrepotSalles salles) {
	}

}
