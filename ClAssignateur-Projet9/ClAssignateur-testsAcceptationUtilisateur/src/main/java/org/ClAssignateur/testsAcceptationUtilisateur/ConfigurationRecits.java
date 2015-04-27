package org.ClAssignateur.testsAcceptationUtilisateur;

import org.ClAssignateur.testsAcceptationUtilisateur.contexte.ContexteTestAcceptation;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;

public class ConfigurationRecits {

	ContexteTestAcceptation contexte;

	@BeforeStories
	public void initialisationContexte() {
		this.contexte = new ContexteTestAcceptation();
		this.contexte.appliquer();
	}

	@BeforeScenario
	public void reinitialiserContexte() {
		this.contexte.reinitialiser();
	}

}
