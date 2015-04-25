package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.jbehave.core.annotations.When;

public class EtapesCommunes {

	@When("les demandes sont traitées")
	public void whenLesDemandesSontTraitees() {
		LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class).notifierDelaiEcoule();
	}
}
