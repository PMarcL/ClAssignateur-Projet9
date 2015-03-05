package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.RangeException;

public class DemandeTest {

	private final Employe ORGANISATEUR = new Employe();
	private final int NOMBRE_PARTICIPANT = 10;
	private final int NOMBRE_PARTICIPANT_INCORRECTE = 0;
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();
	private final StrategieNotification strategieNotification = mock(StrategieNotification.class);
	private final StrategieNotificationFactory strategieNotificationFactory = mock(StrategieNotificationFactory.class);

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, strategieNotificationFactory);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansLeConstructeur() {
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void demandePossedeIntialementLeChampsOrganisateurCommeDefiniDansLeConstructeur() {
		Employe organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_PAR_DEFAUT,
				strategieNotificationFactory);
		assertTrue(demande.estAutantPrioritaire(autreDemande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(NOMBRE_PARTICIPANT, ORGANISATEUR, PRIORITE_MOYENNE,
				strategieNotificationFactory);

		assertTrue(demandeAvecPriorite.estAutantPrioritaire(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.estAutantPrioritaire(demande));
	}

	@Test(expected = RangeException.class)
	public void demandeDoitAvoirUneNombreDeParticipantsPositif() {
		new Demande(NOMBRE_PARTICIPANT_INCORRECTE, ORGANISATEUR, strategieNotificationFactory);
	}

	@Test
	public void lorsqueNotifierEchecAlorsNotifierEchecEstAppele() {
		willReturn(strategieNotification).given(strategieNotificationFactory).creerStrategieNotification();

		demande.notifierEchecAssignation();

		verify(strategieNotification).notifierEchecAssignation(ORGANISATEUR);
	}

	@Test
	public void lorsqueNotiferSuccessAlorsNotifierSuccessEstAppele() {
		willReturn(strategieNotification).given(strategieNotificationFactory).creerStrategieNotification();
		Salle salleAssigne = mock(Salle.class);

		demande.notifierAssignation(salleAssigne);

		verify(strategieNotification).notifierAssignation(salleAssigne, ORGANISATEUR);
	}
}
