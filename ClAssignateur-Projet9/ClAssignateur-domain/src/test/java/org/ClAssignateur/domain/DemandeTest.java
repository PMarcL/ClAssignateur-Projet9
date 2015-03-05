package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.RangeException;

public class DemandeTest {

	private final String ORGANISATEUR = "Simon";
	private final int NOMBRE_PARTICIPANT = 10;
	private final int NOMBRE_PARTICIPANT_INCORRECTE = 0;
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansLeConstructeur() {
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void demandePossedeIntialementLeChampsOrganisateurCommeDefiniDansLeConstructeur() {
		String organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_PAR_DEFAUT);
		assertTrue(demande.estAutantPrioritaire(autreDemande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_MOYENNE);

		assertTrue(demandeAvecPriorite.estAutantPrioritaire(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.estAutantPrioritaire(demande));
	}

	@Test(expected = RangeException.class)
	public void demandeDoitAvoirUneNombreDeParticipantsPositif() {
		new Demande(NOMBRE_PARTICIPANT_INCORRECTE, ORGANISATEUR);
	}
}
