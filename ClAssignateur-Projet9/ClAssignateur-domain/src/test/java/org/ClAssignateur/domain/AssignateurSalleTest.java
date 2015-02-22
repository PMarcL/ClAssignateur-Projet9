package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private final Calendar DEMANDE_1_DATE_DEBUT = creerDate(2015, 02, 07, 12,
			14, 25);
	private final Calendar DEMANDE_1_DATE_FIN = creerDate(2015, 02, 07, 13, 30,
			30);
	private final int DEMANDE_1_NOMBRE_PARTICIPANTS = 12;
	private final String DEMANDE_1_NOM_ORGANISATEUR = "John Dow";
	private final Demande DEMANDE_1 = new Demande(DEMANDE_1_DATE_DEBUT,
			DEMANDE_1_DATE_FIN, DEMANDE_1_NOMBRE_PARTICIPANTS,
			DEMANDE_1_NOM_ORGANISATEUR);

	private final Calendar DEMANDE_2_DATE_DEBUT = creerDate(2015, 02, 07, 15,
			38, 42);
	private final Calendar DEMANDE_2_DATE_FIN = creerDate(2015, 02, 07, 17, 45,
			34);
	private final int DEMANDE_2_NOMBRE_PARTICIPANTS = 24;
	private final String DEMANDE_2_NOM_ORGANISATEUR = "Joe Bean";
	private final Demande DEMANDE_2 = new Demande(DEMANDE_2_DATE_DEBUT,
			DEMANDE_2_DATE_FIN, DEMANDE_2_NOMBRE_PARTICIPANTS,
			DEMANDE_2_NOM_ORGANISATEUR);

	private EntrepotSalles entrepotSalles;
	private AssignateurSalle assignateur;
	private ConteneurDemandeTriable fileDemandes;

	private Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void initialement() {
		entrepotSalles = mock(EntrepotSalles.class);
		assignateur = new AssignateurSalle();
		fileDemandes = new ConteneurDemandeTriable();
	}

	@Test
	public void quandAssigantionDevraitTenterAssignerChaqueDemande()
			throws AucunesSallesDisponiblesException {
		fileDemandes.ajouter(DEMANDE_1);
		fileDemandes.ajouter(DEMANDE_2);

		assignateur.assignerDemandeSalle(fileDemandes, entrepotSalles);

		verify(entrepotSalles).obtenirSalleRepondantADemande(DEMANDE_1);
		verify(entrepotSalles).obtenirSalleRepondantADemande(DEMANDE_2);
	}

	private Salle provoquerReservation()
			throws AucunesSallesDisponiblesException {
		fileDemandes.ajouter(DEMANDE_1);
		Salle salle = mock(Salle.class);
		given(entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_1))
				.willReturn(salle);
		return salle;
	}

	@Test
	public void etantDonneDemandeEtSalleDisponibleQuandAssignationDevraitReserverSalle()
			throws AucunesSallesDisponiblesException {
		Salle salle = provoquerReservation();

		assignateur.assignerDemandeSalle(fileDemandes, entrepotSalles);

		verify(salle).placerReservation(DEMANDE_1);
		assertTrue(fileDemandes.estVide());
	}

	@Test
	public void etantDonneDemandeEtSalleIndisponibleQuandAssignationDevraitRemettreDemandeDansFile()
			throws AucunesSallesDisponiblesException {
		fileDemandes.ajouter(DEMANDE_1);
		willThrow(AucunesSallesDisponiblesException.class)
				.given(entrepotSalles).obtenirSalleRepondantADemande(DEMANDE_1);

		int nombreDemandeAvantAssignation = fileDemandes.taille();
		assignateur.assignerDemandeSalle(fileDemandes, entrepotSalles);
		int nombreDemandeApresAssignation = fileDemandes.taille();

		assertEquals(nombreDemandeAvantAssignation,
				nombreDemandeApresAssignation);
	}

	@Test
	public void etantDonneDemandeEtSalleDisponibleQuandAssignationDevraitSauvegarderSalleDansEntrepot()
			throws AucunesSallesDisponiblesException {
		Salle salle = provoquerReservation();
		assignateur.assignerDemandeSalle(fileDemandes, entrepotSalles);
		verify(entrepotSalles).ranger(salle);
	}

}
