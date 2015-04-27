package org.ClAssignateur.contexte.production;

import static org.mockito.Mockito.*;

import org.ClAssignateur.contexte.production.ContexteProduction;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

public class ContexteProductionTest extends ContexteProduction {

	private SallesEntrepot sallesEntrepot;
	private ContexteProduction contexte;

	@Before
	public void initialisation() {
		sallesEntrepot = mock(SallesEntrepot.class);
		contexte = new ContexteProduction(sallesEntrepot);
	}

	@Test
	public void quandInjecterDonneesDevraitAjouterSallePLT3904() {
		final String NOM_SALLE = "PLT-3904";
		contexte.injecterDonnees();
		verify(sallesEntrepot).persister(argThat(salleAyantPourNom(NOM_SALLE)));
	}

	@Test
	public void quandInjecterDonneesDevraitAjouterSallePLT2551() {
		final String NOM_SALLE = "PLT-2551";
		contexte.injecterDonnees();
		verify(sallesEntrepot).persister(argThat(salleAyantPourNom(NOM_SALLE)));
	}

	@Test
	public void quandInjecterDonneesDevraitAjouterSalleVCH2860() {
		final String NOM_SALLE = "VCH-2860";
		contexte.injecterDonnees();
		verify(sallesEntrepot).persister(argThat(salleAyantPourNom(NOM_SALLE)));
	}

	private SalleAyantPourNom salleAyantPourNom(String nomSalle) {
		return new SalleAyantPourNom(nomSalle);
	}

	private class SalleAyantPourNom extends ArgumentMatcher<Salle> {

		private String nomSalle;

		public SalleAyantPourNom(String nomSalle) {
			this.nomSalle = nomSalle;
		}

		@Override
		public boolean matches(Object argument) {
			Salle salle = (Salle) argument;
			return salle.getNom().equals(nomSalle);
		}
	}
}
