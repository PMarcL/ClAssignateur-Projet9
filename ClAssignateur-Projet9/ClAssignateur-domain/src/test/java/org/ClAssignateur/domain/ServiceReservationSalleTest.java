package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;

import org.mockito.InOrder;

import java.util.Timer;
import java.util.Calendar;
import java.util.concurrent.Executor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ServiceReservationSalleTest {

	private final int MILLISECONDES_PAR_MINUTE = 60000;
	private final int FREQUENCE_PAR_DEFAUT = 5;
	private final int FREQUENCE_MINUTES = 3;

	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";
	private final Demande DEMANDE = new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	private final boolean ENTREPOT_SALLE_VIDE = true;
	private final boolean ENTREPOT_SALLE_NON_VIDE = false;

	private Timer minuterie;
	private AssignateurSalle assignateur;
	private Demande demande;
	private ConteneurDemandes conteneurDemandes;
	private EntrepotSalles entrepotSalles;

	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure, int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void creerServiceReservation() {
		minuterie = mock(Timer.class);
		assignateur = mock(AssignateurSalle.class);
		demande = mock(Demande.class);
		conteneurDemandes = mock(ConteneurDemandes.class);
		entrepotSalles = mock(EntrepotSalles.class);

		serviceReservation = new ServiceReservationSalle(conteneurDemandes, entrepotSalles, minuterie, assignateur);
	}

	@Test
	public void demarreMinuteriePendantDemarrageService() {
		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_PAR_DEFAUT);
		verify(minuterie).scheduleAtFixedRate(assignateur, delaiMillisecondes, delaiMillisecondes);
	}

	@Test
	public void quandSetFrequenceDevraitRedemarrerMinuterie() {
		serviceReservation.setFrequence(FREQUENCE_MINUTES);

		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_MINUTES);
		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).cancel();
		inOrder.verify(minuterie).scheduleAtFixedRate(assignateur, delaiMillisecondes, delaiMillisecondes);
	}

	@Test
	public void quandAjouterDemandeDevraitPlacerDansConteneurDemandes() {
		serviceReservation.ajouterDemande(demande);
		verify(conteneurDemandes).ajouter(demande);
	}

	// @Test
	// public void quandSetLimiteDevraitEtreModifieeDansAssignateurSalle() {
	// serviceReservation.setLimite(LIMITE_QUELCONQUE);
	// verify(declencheurAssignMock).setLimite(LIMITE_QUELCONQUE);
	// }
	//
	// @Test
	// public void quandAjouteDemandeDevraitEtreDelegueAFileDemande() {
	// serviceReservation.ajouterDemande(DEMANDE);
	// verify(fileDemandeMock).ajouter(DEMANDE);
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void
	// etantDonneEntrepotSalleRecuVideQuandNouveauServiceDevraitEnvoyerException()
	// {
	// given(entrepotSallesMock.estVide()).willReturn(ENTREPOT_SALLE_VIDE);
	// serviceReservation = new ServiceReservationSalle(declencheurAssignMock,
	// fileDemandeMock, entrepotSallesMock,
	// executeMock);
	// }

	private long delaiEnMillisecondes(int delaiEnMinutes) {
		return delaiEnMinutes * MILLISECONDES_PAR_MINUTE;
	}

}
