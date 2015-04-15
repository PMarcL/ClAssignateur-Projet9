package org.ClAssignateur.domaine.notification;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.notification.NotificationStrategie;
import org.ClAssignateur.domaine.salles.Salle;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class NotificateurTest {
	private final String TITRE_DEMANDE = "RENCONTRE_COURS_QUALITE";
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
		demande = mock(Demande.class);
		organisateur = mock(Employe.class);
		responsable = mock(Employe.class);

		given(demande.getTitre()).willReturn(TITRE_DEMANDE);
		given(demande.getOrganisateur()).willReturn(organisateur);
		given(demande.getResponsable()).willReturn(responsable);

		notificateur = new Notificateur(notificationStrategie);
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
	public void quandNotifierSuccesDevraitAppelerNotifierAvecMessageContenantLeNomDeLaSalle() {
		notificateur.notifierSucces(demande, SALLE);
		verify(notificationStrategie, atLeast(1)).notifier(contains(NOM_DE_SALLE), any(Employe.class));
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
	public void quandNotifierEchecDevraitAppelerNotifierAvecMessageInformantQueAucuneSalleEstAssignee() {
		final String INFORMATION_NECESSAIRE = "Aucune salle";
		notificateur.notifierEchec(demande);
		verify(notificationStrategie, atLeast(1)).notifier(contains(INFORMATION_NECESSAIRE), any(Employe.class));
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
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecTousLesParticipants() {
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
	public void quandNotifierAnnulationDevraitAppelerNotifierAvecMessageContenantLeTitreDeLaDemande() {
		notificateur.notifierAnnulation(demande);
		verify(notificationStrategie, atLeast(1)).notifier(contains(TITRE_DEMANDE), any(Employe.class));
	}
}
