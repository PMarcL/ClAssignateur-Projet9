package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private static final int NOMBRE_DE_PARTICIPANT = 10;
	private final int NOMBRE_DE_PARTICIPANT_DANS_GROUPE_PAR_DEFAUT = 0;
	private final int CAPACITE_SALLE = 15;
	private Employe ORGANISATEUR = mock(Employe.class);
	private Employe RESPONSABLE = mock(Employe.class);
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());;
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(GROUPE);
	}

	@Test
	public void demandePossedeIntialementLeChampsGroupeCommeDefiniDansLeConstructeur() {
		Groupe groupe = demande.getGroupe();
		assertEquals(GROUPE, groupe);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(GROUPE, PRIORITE_PAR_DEFAUT);
		assertTrue(demande.estAutantPrioritaire(autreDemande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(GROUPE, PRIORITE_MOYENNE);

		assertTrue(demandeAvecPriorite.estAutantPrioritaire(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.estAutantPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsOrganisateurCommeDefiniDansGroupe() {
		Employe organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}

	@Test
	public void demandePossedeIntialementLeChampsResponsableCommeDefiniDansGroupe() {
		Employe responsable = demande.getResponsable();
		assertEquals(RESPONSABLE, responsable);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansGroupe() {
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_DE_PARTICIPANT_DANS_GROUPE_PAR_DEFAUT, nbParticipant);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantCommeDefiniDansGroupeAvecPlusieursParticipant() {
		Groupe groupePlusieursParticipants = creerGroupePlusieursParticipants(NOMBRE_DE_PARTICIPANT);
		Demande demandeAvecPlusiseursParticipants = new Demande(groupePlusieursParticipants);

		int nbParticipant = demandeAvecPlusiseursParticipants.getNbParticipant();

		assertEquals(NOMBRE_DE_PARTICIPANT, nbParticipant);
	}

	@Test
	public void uneDemandeApresReservationContientUneReservation() {
		Salle SALLE_AJOUTER = new Salle(CAPACITE_SALLE);

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
