package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MessageNotificationAnnulationTest {

	private MessageNotificationAnnulation message;
	private Demande demande;
	private final String TITRE_DEMANDE = "Titre";
	private final String MESSAGE_DESIRE = "La demande " + TITRE_DEMANDE + " a été annulée.";

	@Before
	public void initialisation() {

		demande = mock(Demande.class);
		message = new MessageNotificationAnnulation(demande);
		given(demande.getTitre()).willReturn(TITRE_DEMANDE);
	}

	@Test
	public void genereMessageDevraitAfficherLeBonMessageAvecLeTitreDeLaDemande() {
		String messageRecu = message.genereMessage();
		assertEquals(MESSAGE_DESIRE, messageRecu);
	}
}
