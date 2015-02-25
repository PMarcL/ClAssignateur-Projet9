package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import org.junit.Before;
import org.junit.Test;

public class ComparateurDeDemandeTest {

	Demande demandeNonPrioritaireFaiteAvant = mock(Demande.class);
	Demande demandeNonPrioritaireFaiteApres = mock(Demande.class);
	Demande demandePrioritaireFaiteApres = mock(Demande.class);
	ComparateurDeDemande comparateur;

	@Before
	public void initialisation() {
		comparateur = new ComparateurDeDemande();

		Timestamp tempsPlusPetit = new Timestamp(0);
		Timestamp tempsPlusGrand = new Timestamp(1000);

		willReturn(tempsPlusPetit).given(demandeNonPrioritaireFaiteAvant)
				.getMomentDeCreation();
		willReturn(tempsPlusGrand).given(demandeNonPrioritaireFaiteApres)
				.getMomentDeCreation();
		willReturn(tempsPlusGrand).given(demandePrioritaireFaiteApres)
				.getMomentDeCreation();
	}

	@Test
	public void uneDemandeCreerAvantEstPlusPetiteQuuneDemandeCreerApres() {
		assertEquals(-1, comparateur.compare(demandeNonPrioritaireFaiteAvant,
				demandeNonPrioritaireFaiteApres));

	}

	@Test
	public void uneDemandeCreerApresEstPlusGrandeQuuneDemandeCreerAvant() {
		assertEquals(1, comparateur.compare(demandeNonPrioritaireFaiteApres,
				demandeNonPrioritaireFaiteAvant));

	}

	@Test
	public void uneDemandeCreerApresEstPlusPetiteQuuneDemandeCreerAvantSiElleEstPlusPrioritaire() {
		willReturn(Demande.PRIORITE_MINIMAL).given(
				demandeNonPrioritaireFaiteAvant).getPriorite();
		willReturn(Demande.PRIORITE_MAXIMALE).given(
				demandePrioritaireFaiteApres).getPriorite();

		assertEquals(-1, comparateur.compare(demandePrioritaireFaiteApres,
				demandeNonPrioritaireFaiteAvant));

	}
}
