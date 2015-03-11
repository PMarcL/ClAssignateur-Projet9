package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageNotificationEchecTest {

	private MessageNotificationEchec message;

	@Before
	public void creerMessageNotificationEchec() {
		message = new MessageNotificationEchec();
	}

	@Test
	public void genereMessageDevraitAfficherLeBonNomDeSalle() {
		String messageDesire = "La demande n'a pas pu être assigné: aucune salle ne correspond à la demande";

		String messageGenere = message.genereMessage();

		assertEquals(messageDesire, messageGenere);
	}

}
