package org.ClAssignateur.domain;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.BDDMockito.*;

public class NotificateurTest {

	private static final String TITRE_DEMANDE = "RENCONTRE_COURS_QUALITE";
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
		given(demande.getTitre()).willReturn(TITRE_DEMANDE);
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
		String MESSAGE_DESIRE = "La salle: " + NOM_DE_SALLE + " a été réservée avec succès.";

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
		String MESSAGE_DESIRE = "Aucune salle n'a pu être assignée avec votre demande.";

		notificateur.notifierEchec(demande);

		verify(notificationStrategie, times(2)).notifier(eq(MESSAGE_DESIRE), any(Employe.class));
	}

	@Test
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecOrganisateur() {
		notificateur.notifierAnnulation(demande);
		verify(notificationStrategie).notifier(anyString(), eq(organisateur));
	}

	@Test
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecResponsable() {
		notificateur.notifierAnnulation(demande);
		verify(notificationStrategie).notifier(anyString(), eq(responsable));
	}

	@Test
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecTousLesParticipant() {
		Employe participant1 = mock(Employe.class);
		Employe participant2 = mock(Employe.class);
		ArrayList<Employe> participants = new ArrayList<Employe>();
		participants.add(participant1);
		participants.add(participant2);
		given(demande.getParticipants()).willReturn(participants);

		notificateur.notifierAnnulation(demande);

		verify(notificationStrategie).notifier(anyString(), eq(participant1));
		verify(notificationStrategie).notifier(anyString(), eq(participant2));
	}

	@Test
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecBonMessage() {
		String MESSAGE_DESIRE = "La demande nommée:" + TITRE_DEMANDE + " à été annulée.";

		notificateur.notifierAnnulation(demande);

		verify(notificationStrategie, times(2)).notifier(eq(MESSAGE_DESIRE), any(Employe.class));
	}
}
