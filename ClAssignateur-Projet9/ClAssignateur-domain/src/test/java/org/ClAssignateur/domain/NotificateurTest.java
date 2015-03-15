package org.ClAssignateur.domain;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.BDDMockito.*;

public class NotificateurTest {

	private String NOM_DE_SALLE = "A100";
	private Salle SALLE = new Salle(100, NOM_DE_SALLE);

	private NotificationStrategie notificationStrategie;
	private Demande demande;
	private Employe organisateur;
	private Employe responsable;

	private Notificateur notificateur;

	@Before
	public void initialement() {
		notificationStrategie = mock(NotificationStrategie.class);
		notificateur = new Notificateur(notificationStrategie);
		organisateur = mock(Employe.class);
		demande = mock(Demande.class);
		given(demande.getOrganisateur()).willReturn(organisateur);
		responsable = mock(Employe.class);
		given(demande.getResponsable()).willReturn(responsable);
	}

	@Test
	public void quandNotifierSuccesDevraitAppelerNotifierAvecOrganisateur() {
		notificateur.notifierSucces(demande, SALLE);
		verify(notificationStrategie).notifier(anyString(), eq(organisateur));
	}

	@Test
	public void quandNotifierSuccesDevraitAppelerNotifierAvecResponsable() {
		notificateur.notifierSucces(demande, SALLE);
		verify(notificationStrategie).notifier(anyString(), eq(responsable));
	}

	@Test
	public void quandNotifierSuccesDevraitAppelerNotifierAvecBonMessage() {
		String MESSAGE_DESIRE = "La salle: " + NOM_DE_SALLE + " a été réservée avec succès";

		notificateur.notifierSucces(demande, SALLE);

		verify(notificationStrategie, times(2)).notifier(eq(MESSAGE_DESIRE), any(Employe.class));
	}

	@Test
	public void quandNotifierEchecDevraitAppelerNotifierAvecOrganisateur() {
		notificateur.notifierEchec(demande);
		verify(notificationStrategie).notifier(anyString(), eq(organisateur));
	}

	@Test
	public void quandNotifierEchecDevraitAppelerNotifierAvecResponsable() {
		notificateur.notifierEchec(demande);
		verify(notificationStrategie).notifier(anyString(), eq(responsable));
	}

	@Test
	public void quandNotifierEchecDevraitAppelerNotifierAvecBonMessage() {
		String MESSAGE_DESIRE = "La salle: " + NOM_DE_SALLE + " a été réservée avec succès";

		notificateur.notifierEchec(demande);
	}
}
