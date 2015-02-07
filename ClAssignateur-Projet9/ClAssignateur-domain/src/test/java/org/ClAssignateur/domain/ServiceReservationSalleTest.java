package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;
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
	private final Demande DEMANDE = new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	private final boolean ENTREPOT_SALLE_VIDE = true;
	private final boolean ENTREPOT_SALLE_NON_VIDE = false;

	private DeclencheurAssignationSalle declencheurAssignMock;
	private FileDemande fileDemandeMock;
	private EntrepotSalles entrepotSallesMock;

	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure, int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {
		declencheurAssignMock = mock(DeclencheurAssignationSalle.class);
		fileDemandeMock = mock(FileDemande.class);
		entrepotSallesMock = mock(EntrepotSalles.class);
		given(entrepotSallesMock.estVide()).willReturn(ENTREPOT_SALLE_NON_VIDE);

		serviceReservation = new ServiceReservationSalle(declencheurAssignMock, fileDemandeMock, entrepotSallesMock);
	}

	@Test
	public void quandSetFrequenceDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(declencheurAssignMock).setFrequence(FREQUENCE_QUELCONQUE);
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
		serviceReservation = new ServiceReservationSalle(declencheurAssignMock, fileDemandeMock, entrepotSallesMock);
	}

}
