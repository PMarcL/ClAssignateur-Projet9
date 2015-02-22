package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.concurrent.Executor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ServiceReservationSalleTest {

	private final int FREQUENCE_QUELCONQUE = 3;
	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";
	private final Demande DEMANDE = new Demande(DATE_DEBUT, DATE_FIN,
			NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	private final boolean ENTREPOT_SALLE_VIDE = true;
	private final boolean ENTREPOT_SALLE_NON_VIDE = false;

	@Mock
	private DeclencheurAssignationSalle declencheurAssignMock;

	@Mock
	private ConteneurDemandeTriable fileDemandeMock;

	@Mock
	private EntrepotSalles entrepotSallesMock;

	@Mock
	private Executor executeMock;

	@Mock
	private Demande demandeMock;

	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {

		declencheurAssignMock = mock(DeclencheurAssignationSalle.class);
		fileDemandeMock = mock(ConteneurDemandeTriable.class);
		entrepotSallesMock = mock(EntrepotSalles.class);
		executeMock = mock(Executor.class);
		willReturn(ENTREPOT_SALLE_NON_VIDE).given(entrepotSallesMock).estVide();
		serviceReservation = new ServiceReservationSalle(declencheurAssignMock,
				fileDemandeMock, entrepotSallesMock, executeMock);
	}

	@Test
	public void quandSetFrequenceDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(declencheurAssignMock).setFrequence(FREQUENCE_QUELCONQUE);
	}

	@Test
	public void quandAjouteDemandeDejaInstancieeDevraitEtreDelegueAFileDemande() {
		serviceReservation.ajouterDemande(demandeMock);
		verify(fileDemandeMock).ajouter(demandeMock);
	}

	@Test
	public void lExecuteurDemarreSonTraitementALaCreationDuService() {
		verify(executeMock).execute(serviceReservation);
	}

	@Test
	public void lorsqueLeSystemeEstEnMarchLesDemandesSontAssignee()
			throws Exception {
		(new Thread(serviceReservation)).start();
		Thread.sleep(100);
		verify(declencheurAssignMock, atLeast(1))
				.verifierConditionEtAssignerDemandeSalle(fileDemandeMock,
						entrepotSallesMock);
		serviceReservation.arreterService();
	}

	@Test
	public void quandSetLimiteDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setLimite(LIMITE_QUELCONQUE);
		verify(declencheurAssignMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandAjouteDemandeDevraitEtreDelegueAFileDemande() {
		serviceReservation.ajouterDemande(DEMANDE);
		verify(fileDemandeMock).ajouter(DEMANDE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void etantDonneEntrepotSalleRecuVideQuandNouveauServiceDevraitEnvoyerException() {
		given(entrepotSallesMock.estVide()).willReturn(ENTREPOT_SALLE_VIDE);
		serviceReservation = new ServiceReservationSalle(declencheurAssignMock,
				fileDemandeMock, entrepotSallesMock, executeMock);
	}

}
