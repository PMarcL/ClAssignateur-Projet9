package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final String TITRE_REUNION = "Mon titre";
	private Employe ORGANISATEUR = mock(Employe.class);
	private Employe RESPONSABLE = mock(Employe.class);
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());
	private final int NOMBRE_DE_PARTICIPANT = 10;
	private final int NOMBRE_DE_PARTICIPANT_DANS_GROUPE_PAR_DEFAUT = 0;
	private final int CAPACITE_SALLE = 15;
	private final String NOM_SALLE = "salle";
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(GROUPE, TITRE_REUNION);
	}

	@Test
	public void demandePossedeInitialementLeChampsGroupeCommeDefiniDansLeConstructeur() {
		Groupe groupe = demande.getGroupe();
		assertEquals(GROUPE, groupe);
	}

	@Test
	public void demandePossedeInitialementLeChampTitre() {
		String titre = demande.getTitre();
		assertEquals(TITRE_REUNION, titre);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(GROUPE, TITRE_REUNION, PRIORITE_PAR_DEFAUT);
		assertTrue(demande.aLeMemeNiveauDePriorite(autreDemande));
	}

	@Test
	public void demandePossedeInitialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_MOYENNE);

		assertTrue(demandeAvecPriorite.aLeMemeNiveauDePriorite(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeInitialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.aLeMemeNiveauDePriorite(demande));
	}

	@Test
	public void demandeEstInitialementDansLEtatEnAttente() {
		assertTrue(demande.estEnAttente());
	}

	@Test
	public void demandeApresReservationEstDansLEtatAssignee() {
		Salle salle = new Salle(CAPACITE_SALLE, NOM_SALLE);
		demande.placerReservation(salle);
		assertTrue(demande.estAssignee());
	}

	@Test
	public void demandeApresAnnulationEstDansLEtatAnnulee() {
		demande.annulerReservation();
		assertTrue(demande.estAnnulee());
	}

	@Test
	public void demandeEstInitialementPasAssignee() {
		assertFalse(demande.estAssignee());
	}

	@Test
	public void demandePossedeIntialementLeChampsResponsableCommeDefiniDansGroupe() {
		Employe responsable = demande.getResponsable();
		assertEquals(RESPONSABLE, responsable);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansGroupe() {
		int nbParticipant = demande.getNbParticipants();
		assertEquals(NOMBRE_DE_PARTICIPANT_DANS_GROUPE_PAR_DEFAUT, nbParticipant);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansGroupeAvecPlusieursParticipant() {
		Groupe groupePlusieursParticipants = creerGroupePlusieursParticipants(NOMBRE_DE_PARTICIPANT);
		Demande demandeAvecPlusiseursParticipants = new Demande(groupePlusieursParticipants, TITRE_REUNION);

		int nbParticipant = demandeAvecPlusiseursParticipants.getNbParticipants();

		assertEquals(NOMBRE_DE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void uneDemandeApresReservationContientUneReservation() {
		Salle SALLE_AJOUTER = new Salle(CAPACITE_SALLE, NOM_SALLE);

		demande.placerReservation(SALLE_AJOUTER);

		assertEquals(demande.getNbReservation(), 1);
	}

	@Test
	public void UneDemandeContientInitialementAucuneReservation() {
		assertEquals(demande.getNbReservation(), 0);
	}

	private Groupe creerGroupePlusieursParticipants(int nombreParticipant) {
		ArrayList<Employe> listeParticipant = new ArrayList<Employe>();
		for (int i = 0; i < nombreParticipant; i++) {
			Employe nouveauEmploye = mock(Employe.class);
			listeParticipant.add(nouveauEmploye);
		}
		Groupe groupeAvecParticipant = new Groupe(ORGANISATEUR, RESPONSABLE, listeParticipant);
		return groupeAvecParticipant;
	}
}
