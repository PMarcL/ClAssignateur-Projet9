package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageNotificationEchecTest {

	private MessageNotificationEchec message;
	private final String MESSAGE_DESIRE = "La demande n'a pas pu être assigné: aucune salle ne correspond à la demande";

	@Before
	public void creerMessageNotificationEchec() {
		message = new MessageNotificationEchec();
	}

	@Test
	public void genereMessageDevraitAfficherLeBonMessage() {
		String messageGenere = message.genereMessage();
		assertEquals(MESSAGE_DESIRE, messageGenere);
	}

}
