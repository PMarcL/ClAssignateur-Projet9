package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final int CAPACITE_SALLE = 15;
	private Employe ORGANISATEUR = mock(Employe.class);
	private Employe RESPONSABLE = mock(Employe.class);
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();
	private final StrategieNotification strategieNotification = mock(StrategieNotification.class);

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(GROUPE, strategieNotification);
	}

	@Test
	public void demandePossedeIntialementLeChampsGroupeCommeDefiniDansLeConstructeur() {
		Groupe groupe = demande.getGroupe();
		assertEquals(GROUPE, groupe);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(GROUPE, PRIORITE_PAR_DEFAUT, strategieNotification);
		assertTrue(demande.estAutantPrioritaire(autreDemande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(GROUPE, PRIORITE_MOYENNE, strategieNotification);

		assertTrue(demandeAvecPriorite.estAutantPrioritaire(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.estAutantPrioritaire(demande));
	}

	@Test
	public void uneDemandeApresReservationContientUneReservation() {
		Salle SALLE_AJOUTER = new Salle(CAPACITE_SALLE);

		demande.placerReservation(SALLE_AJOUTER);

		assertEquals(demande.getNbReservation(), 1);
	}

	@Test
	public void UneDemandeContientInitialementAucuneReservation() {
		assertEquals(demande.getNbReservation(), 0);
	}

	@Test
	public void lorsquePlacerReservationAlorsNotifierSuccessOrganisateur() {
		Salle SALLE_AJOUTER = new Salle(CAPACITE_SALLE);

		demande.placerReservation(SALLE_AJOUTER);

		verify(strategieNotification).notifier(any(MessageNotificationSucces.class), eq(ORGANISATEUR));
	}

	@Test
	public void lorsquePlacerReservationAlorsNotifierSuccessResponsable() {
		Salle SALLE_AJOUTER = new Salle(CAPACITE_SALLE);

		demande.placerReservation(SALLE_AJOUTER);

		verify(strategieNotification).notifier(any(MessageNotificationSucces.class), eq(RESPONSABLE));
	}

	@Test
	public void lorsSignalerAucuneSalleCorrespondanteNotifierEchecOrganisateur() {
		demande.signalerAucuneDemandeCorrespondante();

		verify(strategieNotification).notifier(any(MessageNotificationEchec.class), eq(ORGANISATEUR));
	}

	@Test
	public void lorsSignalerAucuneSalleCorrespondanteNotifierEchecResponsable() {
		demande.signalerAucuneDemandeCorrespondante();

		verify(strategieNotification).notifier(any(MessageNotificationEchec.class), eq(RESPONSABLE));
	}
}
