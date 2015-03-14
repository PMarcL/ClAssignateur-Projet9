package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class SelectionSalleOptimaleStrategieTest {

	private final int NB_PARTICIPANTS = 100;
	private final boolean PEUT_ACCUEILLIR = true;
	private final float TAUX_OCCUPATION_TOTAL = 1.0f;

	private Salle salle;
	private Demande demande;
	private ArrayList<Salle> salles;

	private SelectionSalleStrategie selecteurSalle;

	@Before
	public void initialisation() {
		selecteurSalle = new SelectionSalleOptimaleStrategie();

		salle = mock(Salle.class);
		demande = mock(Demande.class);

		salles = new ArrayList<Salle>();
		salles.add(salle);
	}

	@Test
	public void quandRechercheSalleOnDoitObtenirUneSallePouvantContenirLeNbDeParticipants() {
		given(demande.getNbParticipant()).willReturn(NB_PARTICIPANTS);
		given(salle.peutAccueillir(NB_PARTICIPANTS)).willReturn(PEUT_ACCUEILLIR);
		given(salle.getPourcentageOccupation(NB_PARTICIPANTS)).willReturn(TAUX_OCCUPATION_TOTAL);

		Optional<Salle> salleObtenue = selecteurSalle.selectionnerSalle(salles, demande);

		assertEquals(salle, salleObtenue.get());
	}

	// @Test
	// public void quandRechercheSalle() {
	// Optional<Salle> resultat = selecteurSalle.selectionnerSalle(salles,
	// DEMANDE_NBPARTICIPANTS_10);
	//
	// assertTrue(resultat.isPresent());
	// assertEquals(SALLE_CAPACITE_10, resultat.get());
	// }
	//
	// @Test
	// public void
	// quandRechercheSalleSiNbParticipantEgaleCapaciteDeUneSalleOnObtient() {
	// Optional<Salle> resultat = selecteurSalle.selectionnerSalle(salles,
	// DEMANDE_NBPARTICIPANTS_10);
	//
	// assertTrue(resultat.isPresent());
	// assertEquals(SALLE_CAPACITE_10, resultat.get());
	// }
	//
	// @Test
	// public void
	// quandRechercheSalleSiAucunesSallesPeutAccueillirLesParticipantOnObtientRien()
	// {
	// Optional<Salle> resultat = selecteurSalle.selectionnerSalle(salles,
	// DEMANDE_NBPARTICIPANTS_200);
	//
	// assertFalse(resultat.isPresent());
	// }
}
