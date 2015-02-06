package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import java.util.Calendar;
import java.util.concurrent.Executor;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class ServiceReservationSalleTest {

	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";

	@Mock
	private AssignateurSalle assignSalleMock;

	@Mock
	private FileDemande fileDemandeMock;

	@Mock
	private EntrepotSalles entrepotSallesMock;

	@Mock
	private Executor executeMock;

	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure, int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {
		serviceReservation = new ServiceReservationSalle(assignSalleMock, fileDemandeMock, entrepotSallesMock,
				executeMock);

	}

	@Test
	public void quandSetLimiteDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setLimite(LIMITE_QUELCONQUE);
		verify(assignSalleMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandAjouteDemandeDevraitEtreDelegueAFileDemande() {
		serviceReservation.ajouterDemande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
		verify(fileDemandeMock).ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	}

	@Test
	public void lExecuteurDemarreSonTraitementALaCreationDuService() {
		verify(executeMock).execute(serviceReservation);
	}

	@Test
	public void lorsqueLeSystemeEstEnMarchLesDemandesSontAssignee() throws Exception {
		(new Thread(serviceReservation)).start();
		Thread.sleep(100);
		verify(assignSalleMock, atLeast(1)).assignerDemandeSalle(fileDemandeMock, entrepotSallesMock);
		serviceReservation.arreterService();
	}

}
