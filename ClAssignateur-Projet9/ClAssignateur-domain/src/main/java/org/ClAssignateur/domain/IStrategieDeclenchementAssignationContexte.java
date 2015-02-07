package org.ClAssignateur.domain;

import java.util.Calendar;

public interface IStrategieDeclenchementAssignationContexte {

	public int getLimite();

	public int getFrequence();

	public int getNbDemandesAssignationCourantes();

	public Calendar getDerniereAssignation();

}
