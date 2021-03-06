package org.ClAssignateur.persistance;

import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class EnMemoireSallesEntrepot implements SallesEntrepot {

	private List<Salle> salles;

	public EnMemoireSallesEntrepot() {
		this.salles = new ArrayList<Salle>();
	}

	public void persister(Salle salle) {
		salles.add(salle);
	}

	public Collection<Salle> obtenirSalles() {
		return salles;
	}

}
