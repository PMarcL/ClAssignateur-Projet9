package org.ClAssignateur.domain;

import java.util.Comparator;

public class ComparateurDeDemande implements Comparator<Demande> {

	@Override
	public int compare(Demande arg0, Demande arg1) {
		if (arg0.getPriorite() == arg1.getPriorite()) {
			return arg0.getMomentDeCreation().compareTo(
					arg1.getMomentDeCreation());
		} else {
			return -1 * Integer.compare(arg0.getPriorite(), arg1.getPriorite());
		}
	}

}
