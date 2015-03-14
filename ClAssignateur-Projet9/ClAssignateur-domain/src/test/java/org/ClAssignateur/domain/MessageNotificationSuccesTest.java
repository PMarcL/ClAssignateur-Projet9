package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class MessageNotificationSuccesTest {

	private final String NOM_SALLE = "nom-de-salle";
	private final int CAPACITE_SALLE = 0;
	private final Salle salleAjouter = new Salle(CAPACITE_SALLE, NOM_SALLE);
	private final String MESSAGE_DESIRE = "La salle: " + NOM_SALLE + " a été réservée avec succès";

	private MessageNotificationSucces message;

	@Before
	public void creerMessageNotificationSucces() {
		message = new MessageNotificationSucces(salleAjouter);
	}

	@Test
	public void getSalleRetourneSallePasseDansLeConstructeur() {
		Salle salle = message.getSalle();
		assertEquals(salleAjouter, salle);
	}

	@Test
	public void genereMessageDevraitAfficherLeBonNomDeSalle() {
		String messageGenere = message.genereMessage();
		assertEquals(MESSAGE_DESIRE, messageGenere);
	}
}
