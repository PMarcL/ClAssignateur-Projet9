package org.ClAssignateur.domain;

import java.util.Calendar;

public class DeclencheurAssignationSalle implements
		IStrategieDeclenchementAssignationContexte {

	private IStrategieDeclenchementAssignation strategieDeclenchement;
	private int frequence;
	private int limite;
	private int nbDemandesAssignCourantes;
	private Calendar derniereAssignation;
	private AssignateurSalle assignateur;

	public DeclencheurAssignationSalle(int frequence, int limite,
			IStrategieDeclenchementAssignation strategieDeclenchement,
			AssignateurSalle assignateur) {
		this.frequence = frequence;
		this.limite = limite;
		this.strategieDeclenchement = strategieDeclenchement;
		this.assignateur = assignateur;
		this.nbDemandesAssignCourantes = 0;
		this.derniereAssignation = Calendar.getInstance();
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getNbDemandesAssignationCourantes() {
		return nbDemandesAssignCourantes;
	}

	public Calendar getDerniereAssignation() {
		return derniereAssignation;
	}

	public void verifierConditionEtAssignerDemandeSalle(
			ConteneurDemandes demandes, EntrepotSalles salles) {
		nbDemandesAssignCourantes = demandes.taille();

		if (strategieDeclenchement
				.verifierConditionAtteinte((IStrategieDeclenchementAssignationContexte) this)) {
			derniereAssignation = Calendar.getInstance();

			if (demandes.estVide() || salles.estVide())
				return;

			assignateur.assignerDemandeSalle(demandes, salles);
		}
	}
}
