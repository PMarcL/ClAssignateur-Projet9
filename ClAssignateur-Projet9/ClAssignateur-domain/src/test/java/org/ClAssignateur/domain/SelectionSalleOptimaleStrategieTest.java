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
	private final boolean PEUT_PAS_ACCUEILLIR = false;
	private final float TAUX_OCCUPATION_OPTIMAL = 1.0f;
	private final float TAUX_OCCUPATION_PAS_OPTIMAL = 0.5f;

	private Salle salleOptimale;
	private Salle sallePasOptimale;
	private Salle salleNePouvantPasAccueillirParticipants;
	private Demande demande;
	private ArrayList<Salle> salles;

	private SelectionSalleStrategie selecteurSalle;

	@Before
	public void initialisation() {
		selecteurSalle = new SelectionSalleOptimaleStrategie();

		salleOptimale = mock(Salle.class);
		sallePasOptimale = mock(Salle.class);
		salleNePouvantPasAccueillirParticipants = mock(Salle.class);
		demande = mock(Demande.class);
		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANTS);
		given(salleOptimale.peutAccueillir(NB_PARTICIPANTS)).willReturn(PEUT_ACCUEILLIR);
		given(salleOptimale.getTauxOccupation(NB_PARTICIPANTS)).willReturn(TAUX_OCCUPATION_OPTIMAL);
		given(sallePasOptimale.peutAccueillir(NB_PARTICIPANTS)).willReturn(PEUT_ACCUEILLIR);
		given(sallePasOptimale.getTauxOccupation(NB_PARTICIPANTS)).willReturn(TAUX_OCCUPATION_PAS_OPTIMAL);
		given(salleNePouvantPasAccueillirParticipants.peutAccueillir(NB_PARTICIPANTS)).willReturn(PEUT_PAS_ACCUEILLIR);

		salles = new ArrayList<Salle>();
	}

	@Test
	public void quandRechercheSalleOnDoitObtenirUneSallePouvantContenirLeNbDeParticipants() {
		salles.add(salleOptimale);
		salles.add(salleNePouvantPasAccueillirParticipants);
		Optional<Salle> salleObtenue = selecteurSalle.selectionnerSalle(salles, demande);
		assertTrue(salleObtenue.get().peutAccueillir(NB_PARTICIPANTS));
	}

	@Test
	public void etantDonnePlusieursSallesPouvantAccueillirNbParticipantsQuandRechercheSalleRetourneSalleOptimale() {
		salles.add(salleOptimale);
		salles.add(sallePasOptimale);

		Optional<Salle> salleObtenue = selecteurSalle.selectionnerSalle(salles, demande);

		assertEquals(salleOptimale, salleObtenue.get());
	}

	@Test
	public void etantDonneAucuneSalleNePouvantAccueillirNbParticipantsQuandRechercheSalleRetourneRien() {
		salles.add(salleNePouvantPasAccueillirParticipants);
		Optional<Salle> salleObtenue = selecteurSalle.selectionnerSalle(salles, demande);
		assertFalse(salleObtenue.isPresent());
	}

	@Test
	public void etantDonneDeuxSallesOptimalesRenvoieLaPremiereTrouvee() {
		Salle premiereSalleOptimale = mock(Salle.class);
		given(premiereSalleOptimale.peutAccueillir(NB_PARTICIPANTS)).willReturn(PEUT_ACCUEILLIR);
		given(premiereSalleOptimale.getTauxOccupation(NB_PARTICIPANTS)).willReturn(TAUX_OCCUPATION_OPTIMAL);
		salles.add(premiereSalleOptimale);
		salles.add(salleOptimale);

		Optional<Salle> salleObtenue = selecteurSalle.selectionnerSalle(salles, demande);

		assertEquals(premiereSalleOptimale, salleObtenue.get());
	}

}
