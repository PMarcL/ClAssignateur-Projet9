package org.ClAssignateur.domain.demandes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.demandes.Demande.STATUT_DEMANDE;

import java.util.UUID;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.demandes.Demande;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final String TITRE_REUNION = "Mon titre";
	private final String COURRIEL_ORGNISATEUR = "courriel";
	private final Employe ORGANISATEUR = new Employe(COURRIEL_ORGNISATEUR);
	private final Employe RESPONSABLE = new Employe("courriel@hotmail.com");
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());
	private final int NOMBRE_DE_PARTICIPANTS = 10;
	private final int NOMBRE_DE_PARTICIPANTS_DANS_GROUPE_PAR_DEFAUT = 0;
	private final int CAPACITE_SALLE = 15;
	private final String NOM_SALLE = "salle";
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();
	private final UUID UN_ID = UUID.randomUUID();

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(UN_ID, GROUPE, TITRE_REUNION);
	}

	@Test
	public void demandePossedeInitialementLeChampsId() {
		Demande demandeAvecIdAleatoire = new Demande(GROUPE, TITRE_REUNION);
		UUID id = demandeAvecIdAleatoire.getID();
		assertNotNull(id);
	}

	@Test
	public void demandePossedeInitialementLeChampsIdCommeDefiniDansLeConstructeur() {
		UUID id = demande.getID();
		assertEquals(UN_ID, id);
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
		assertTrue(demande.estAussiPrioritaire(autreDemande));
	}

	@Test
	public void demandeEstInitialementEnAttente() {
		STATUT_DEMANDE resultat = demande.getEtat();
		assertEquals(STATUT_DEMANDE.EN_ATTENTE, resultat);
	}

	@Test
	public void demandePossedeInitialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_MOYENNE);

		assertTrue(demandeAvecPriorite.estAussiPrioritaire(demandeAvecPriorite));
	}

	@Test
	public void demandeEstInitialementPasAssignee() {
		assertFalse(demande.estAssignee());
	}

	@Test
	public void demandePossedeIntialementLeChampsResponsableCommeDefiniDansGroupe() {
		Employe responsable = demande.getResponsable();
		assertTrue(RESPONSABLE.equals(responsable));
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantsCommeDefiniDansGroupe() {
		int nbParticipants = demande.getNbParticipants();
		assertEquals(NOMBRE_DE_PARTICIPANTS_DANS_GROUPE_PAR_DEFAUT, nbParticipants);
	}

	@Test
	public void demandePossedeIntialementLeChampsNbParticipantsCommeDefiniDansGroupeAvecPlusieursParticipants() {
		Groupe groupePlusieursParticipants = creerGroupePlusieursParticipants(NOMBRE_DE_PARTICIPANTS);
		Demande demandeAvecPlusiseursParticipants = new Demande(groupePlusieursParticipants, TITRE_REUNION);

		int nbParticipants = demandeAvecPlusiseursParticipants.getNbParticipants();

		assertEquals(NOMBRE_DE_PARTICIPANTS, nbParticipants);
	}

	@Test
	public void quandPlacerReservationDemandeEstAssignee() {
		Salle salle = new Salle(CAPACITE_SALLE, NOM_SALLE);
		demande.placerReservation(salle);
		assertTrue(demande.estAssignee());
		assertEquals(STATUT_DEMANDE.ACCEPTE, demande.getEtat());
	}

	@Test
	public void quandAnnulerReservationDemandeEstPasAssignee() {
		demande.annulerReservation();
		assertFalse(demande.estAssignee());
		assertEquals(STATUT_DEMANDE.REFUSE, demande.getEtat());
	}

	@Test
	public void deuxDemandesSontEgalesSiElleOnLeMemeId() {
		Demande demandeDifferenteAvecLeMemeId = faireUneDemandeDifferenteAvecId(UN_ID);
		Boolean demandesSontEgales = demandeDifferenteAvecLeMemeId.equals(demande);
		assertTrue(demandesSontEgales);
	}

	@Test
	public void deuxDemandesSontDifferentesSiElleNOnPasLeMemeId() {
		Demande demande = new Demande(UN_ID, GROUPE, TITRE_REUNION);
		Demande demandeDifferente = new Demande(UUID.randomUUID(), GROUPE, TITRE_REUNION);

		Boolean demandesSontEgales = demandeDifferente.equals(demande);

		assertFalse(demandesSontEgales);
	}

	@Test
	public void etantDonneUneDemandePasAssigneeQuandGetSalleAssigneeRetourneSalleNull() {
		Salle salleRecu = demande.getSalleAssignee();
		assertEquals(null, salleRecu);
	}

	@Test
	public void etantDonneUneDemandeAssigneeQuandGetSalleAssigneeRetournSalleAssignee() {
		Salle salleAssignee = new Salle(CAPACITE_SALLE, NOM_SALLE);
		demande.placerReservation(salleAssignee);

		Salle salleRecu = demande.getSalleAssignee();

		assertEquals(salleAssignee, salleRecu);
	}

	@Test
	public void etantDemandeAvecOrganisateurQuandGetCourrielOrganisateurAlorsDonneLeBonCourrielOrganisateur() {
		String courrielOrganisateurActuel = demande.getCourrielOrganisateur();
		assertEquals(COURRIEL_ORGNISATEUR, courrielOrganisateurActuel);
	}

	private Demande faireUneDemandeDifferenteAvecId(UUID id) {
		String titre = "une_deuxieme_demande";

		Employe organisateur_second = new Employe("deux@gmail.com");
		Employe reponsable_second = new Employe("deux@gmail.com");
		ArrayList<Employe> aucunParticipant = new ArrayList<Employe>();
		Groupe deuxieme_groupe = new Groupe(organisateur_second, reponsable_second, aucunParticipant);

		return new Demande(id, deuxieme_groupe, titre);
	}

	private Groupe creerGroupePlusieursParticipants(int nombreParticipants) {
		ArrayList<Employe> listeParticipants = new ArrayList<Employe>();

		for (int i = 0; i < nombreParticipants; i++) {
			Employe nouveauEmploye = mock(Employe.class);
			listeParticipants.add(nouveauEmploye);
		}

		return new Groupe(ORGANISATEUR, RESPONSABLE, listeParticipants);
	}
}
