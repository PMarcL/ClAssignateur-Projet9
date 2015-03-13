package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class SelectionnerSalleLaPlusPetiteRepondantADemandeTest {
	private StrategieDeSelectionDeSalle selecteurSalle;

	private Salle SALLE_CAPACITE_10;
	private Salle SALLE_CAPACITE_100;

	private Demande DEMANDE_NBPARTICIPANTS_10;
	private Demande DEMANDE_NBPARTICIPANTS_50;
	private Demande DEMANDE_NBPARTICIPANTS_200;

	private ArrayList<Salle> salles;

	@Before
	public void initialisation() {
		selecteurSalle = new SelectionnerSalleLaPlusPetiteRepondantADemande();

		SALLE_CAPACITE_10 = mock(Salle.class);
		SALLE_CAPACITE_100 = mock(Salle.class);

		when(SALLE_CAPACITE_10.pourcentageOccupation(10)).thenReturn((long) 1.0);
		when(SALLE_CAPACITE_10.pourcentageOccupation(50)).thenReturn((long) 5.0);
		when(SALLE_CAPACITE_10.pourcentageOccupation(200)).thenReturn((long) 20.0);
		when(SALLE_CAPACITE_10.peutAccueillir(10)).thenReturn(true);
		when(SALLE_CAPACITE_10.peutAccueillir(50)).thenReturn(false);
		when(SALLE_CAPACITE_10.peutAccueillir(200)).thenReturn(false);

		when(SALLE_CAPACITE_100.pourcentageOccupation(10)).thenReturn((long) 0.1);
		when(SALLE_CAPACITE_100.pourcentageOccupation(50)).thenReturn((long) 0.5);
		when(SALLE_CAPACITE_100.pourcentageOccupation(200)).thenReturn((long) 2.0);
		when(SALLE_CAPACITE_100.peutAccueillir(10)).thenReturn(true);
		when(SALLE_CAPACITE_100.peutAccueillir(50)).thenReturn(true);
		when(SALLE_CAPACITE_100.peutAccueillir(200)).thenReturn(false);

		DEMANDE_NBPARTICIPANTS_10 = mock(Demande.class);
		DEMANDE_NBPARTICIPANTS_50 = mock(Demande.class);
		DEMANDE_NBPARTICIPANTS_200 = mock(Demande.class);

		when(DEMANDE_NBPARTICIPANTS_10.getNbParticipant()).thenReturn(10);
		when(DEMANDE_NBPARTICIPANTS_50.getNbParticipant()).thenReturn(50);
		when(DEMANDE_NBPARTICIPANTS_200.getNbParticipant()).thenReturn(200);

		salles = new ArrayList<Salle>();
		salles.add(SALLE_CAPACITE_10);
		salles.add(SALLE_CAPACITE_100);
	}

	@Test
	public void quandRechercheSalleOnDoitObtenirUneSallePouvantContenirLeNbDeParticipants() {
		Optional<Salle> resultat = selecteurSalle.appliquer(salles, DEMANDE_NBPARTICIPANTS_50);

		assertTrue(resultat.isPresent());
		assertEquals(SALLE_CAPACITE_100, resultat.get());
	}

	@Test
	public void quandRechercheSalleSiNbParticipantEgaleCapaciteDeUneSalleOnObtient() {
		Optional<Salle> resultat = selecteurSalle.appliquer(salles, DEMANDE_NBPARTICIPANTS_10);

		assertTrue(resultat.isPresent());
		assertEquals(SALLE_CAPACITE_10, resultat.get());
	}

	@Test
	public void quandRechercheSalleSiAucunesSallesPeutAccueillirLesParticipantOnObtientRien() {
		Optional<Salle> resultat = selecteurSalle.appliquer(salles, DEMANDE_NBPARTICIPANTS_200);

		assertFalse(resultat.isPresent());
	}
}
