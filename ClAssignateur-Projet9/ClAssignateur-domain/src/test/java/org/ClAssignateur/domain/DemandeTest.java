package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.RangeException;

public class DemandeTest {

	private final String ORGANISATEUR = "Simon";
	private final int NOMBRE_PARTICIPANT = 10;
	private final int NOMBRE_PARTICIPANT_INCORRECTE = 0;
	private final int PRIORITE_INITIALE = 1;
	private final int PRIORITE_CONSTRUCTEUR = 2;
	private final int PRIORITE_INCORRECT_INF = 0;
	private final int PRIORITE_INCORRECT_SUP = 6;

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR);
	}

	@Test
	public void DemandePossedeIntialementLeChampsNbParticipantCommeDefiniDansLeConstructeur() {
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void DemandePossedeIntialementLeChampsOrganisateurCommeDefiniDansLeConstructeur() {
		String organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}

	@Test
	public void DemandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_CONSTRUCTEUR);
		int priorite = demandeAvecPriorite.getPriorite();
		assertEquals(PRIORITE_CONSTRUCTEUR, priorite);
	}

	@Test(expected = RangeException.class)
	public void DemandeLanceUneExceptionSiLaPrioriteEstInferieurALaLimiteInferieur() {
		new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_INCORRECT_INF);
	}

	@Test(expected = RangeException.class)
	public void DemandeLanceUneExceptionSiLaPrioriteEstSuperieurALaLimiteSuperieur() {
		new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_INCORRECT_SUP);
	}

	@Test
	public void DemandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		int priorite = demande.getPriorite();
		assertEquals(PRIORITE_INITIALE, priorite);
	}

	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneNombreDeParticipantsStrictementSuperieurAuNombreDeParticipantMinimum() {
		new Demande(NOMBRE_PARTICIPANT_INCORRECTE, ORGANISATEUR);
	}
}
