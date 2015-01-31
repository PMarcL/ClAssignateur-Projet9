package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final int FREQUENCE_QUELCONQUE = 3;
	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";

	private GestionnaireDemande gestionnaireDemandeMock;
	private ServiceReservationSalle serviceReservation;

	public static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {
		gestionnaireDemandeMock = mock(GestionnaireDemande.class);
		serviceReservation = new ServiceReservationSalle(
				gestionnaireDemandeMock);
	}

	@Test
	public void quandJeSetLaFrequenceAlorsElleDoitEtreModifieeParLeGestionnaireDemande() {
		serviceReservation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(gestionnaireDemandeMock).setFrequence(FREQUENCE_QUELCONQUE);
	}

	@Test
	public void quandJeSetLaLimiteAlorsElleDoitEtreModifieeParLeGestionnaireDemande() {
		serviceReservation.setLimite(LIMITE_QUELCONQUE);
		verify(gestionnaireDemandeMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandJAjouteDemandeAlorsElleDoitEtreAjouteeAuGestionnaireDemande() {
		serviceReservation.ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
		verify(gestionnaireDemandeMock).ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	}

}
