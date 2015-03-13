package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageNotificationFactoryTest {

	private final String NOM_SALLE = "nom-de-salle";
	private final int CAPACITE_SALLE = 0;
	private final Salle salleAjouter = new Salle(CAPACITE_SALLE, NOM_SALLE);

	private MessageNotificationFactory messageFactory;

	@Before
	public void creerMessageNotificationEchec() {
		messageFactory = new MessageNotificationFactory();
	}

	@Test
	public void genereNotificationSuccesRetourneMessageAvecBonneSalle() {
		MessageNotificationSucces messageSucces = (MessageNotificationSucces) messageFactory
				.genereNotificationSucces(salleAjouter);

		Salle salleDuMessage = messageSucces.getSalle();

		assertEquals(salleAjouter, salleDuMessage);
	}
}
