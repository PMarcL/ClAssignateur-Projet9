package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import static org.junit.Assert.*;

import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireSallesEntrepot;

import org.ClAssignateur.domaine.salles.Salle;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;

public class EnMemoireSallesEntrepotTest {

	private final String NOM_SALLE = "PLT2770";
	private final int CAPACITE_SALLE = 100;
	private final int NB_SALLES_PERSISTEES = 4;

	private EnMemoireSallesEntrepot entrepot;

	@Before
	public void initialisation() {
		entrepot = new EnMemoireSallesEntrepot();
	}

	@Test
	public void entrepotContientInitiallementAucuneSalle() {
		Collection<Salle> sallesRetournees = entrepot.obtenirSalles();
		assertTrue(sallesRetournees.isEmpty());
	}

	@Test
	public void etantDonneUneSallePersisteeQuandObtenirSallesRetourneCollectionContenantLaSalle() {
		Salle salle = new Salle(CAPACITE_SALLE, NOM_SALLE);
		entrepot.persister(salle);

		Collection<Salle> sallesRetournees = entrepot.obtenirSalles();

		assertTrue(sallesRetournees.contains(salle));
	}

	@Test
	public void etantDonnePlusieursSallesPersisteesQuandObtenirSallesRetourneCollectionContenantToutesLesSalles() {
		ajouterSalles();
		Collection<Salle> sallesRetournees = entrepot.obtenirSalles();
		assertEquals(NB_SALLES_PERSISTEES, sallesRetournees.size());
	}

	private void ajouterSalles() {
		for (int i = 0; i < NB_SALLES_PERSISTEES; i++) {
			Salle salle = new Salle(CAPACITE_SALLE, NOM_SALLE);
			entrepot.persister(salle);
		}
	}

}
