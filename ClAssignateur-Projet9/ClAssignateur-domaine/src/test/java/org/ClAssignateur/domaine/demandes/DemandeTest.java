package org.ClAssignateur.domaine.demandes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.Demande.StatutDemande;
import org.ClAssignateur.domaine.groupe.InformationsContact;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.domaine.salles.Salle;
import java.util.UUID;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final String TITRE_REUNION = "Titre de ma réunion";
	private final String NOM_SALLE = "salle";
	private final String COURRIEL_ORGANISATEUR = "organisateur@hotmail.com";
	private final int NOMBRE_DE_PARTICIPANTS = 10;
	private final int NOMBRE_DE_PARTICIPANTS_DANS_GROUPE_PAR_DEFAUT = 0;
	private final int CAPACITE_SALLE = 15;
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();
	private final UUID UN_ID = UUID.randomUUID();

	private InformationsContact organisateur;
	private InformationsContact responsable;
	private Groupe groupe;
	private Demande demande;

	@Before
	public void intialisation() {
		organisateur = new InformationsContact(COURRIEL_ORGANISATEUR);
		responsable = mock(InformationsContact.class);
		groupe = new Groupe(organisateur, responsable, new ArrayList<InformationsContact>());

		demande = new Demande(UN_ID, groupe, TITRE_REUNION);
	}

	@Test
	public void demandePossedeInitialementLeChampsId() {
		Demande demandeAvecIdAleatoire = new Demande(groupe, TITRE_REUNION);
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
		Groupe resultatGroupe = demande.getGroupe();
		assertEquals(groupe, resultatGroupe);
	}

	@Test
	public void demandePossedeInitialementLeChampTitre() {
		String titre = demande.getTitre();
		assertEquals(TITRE_REUNION, titre);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(groupe, TITRE_REUNION, PRIORITE_PAR_DEFAUT);
		assertTrue(demande.estAussiPrioritaire(autreDemande));
	}

	@Test
	public void demandeEstInitialementEnAttente() {
		StatutDemande resultat = demande.getEtat();
		assertEquals(StatutDemande.EN_ATTENTE, resultat);
	}

	@Test
	public void demandePossedeInitialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(groupe, TITRE_REUNION, PRIORITE_MOYENNE);
		assertTrue(demandeAvecPriorite.estAussiPrioritaire(demandeAvecPriorite));
	}

	@Test
	public void demandeEstInitialementPasAssignee() {
		assertFalse(demande.estAssignee());
	}

	@Test
	public void demandePossedeIntialementLeChampsResponsableCommeDefiniDansGroupe() {
		InformationsContact responsableResultat = demande.getResponsable();
		assertEquals(responsable, responsableResultat);
	}

	@Test
	public void demandePossedeInitialementLeChampsNbParticipantsCommeDefiniDansGroupe() {
		int nbParticipants = demande.getNbParticipants();
		assertEquals(NOMBRE_DE_PARTICIPANTS_DANS_GROUPE_PAR_DEFAUT, nbParticipants);
	}

	@Test
	public void demandePossedeInitialementLeChampsNbParticipantsCommeDefiniDansGroupeAvecPlusieursParticipants() {
		Groupe groupePlusieursParticipants = creerGroupePlusieursParticipants(NOMBRE_DE_PARTICIPANTS);
		Demande demandeAvecPlusiseursParticipants = new Demande(groupePlusieursParticipants, TITRE_REUNION);

		int nbParticipants = demandeAvecPlusiseursParticipants.getNbParticipants();

		assertEquals(NOMBRE_DE_PARTICIPANTS, nbParticipants);
	}

	@Test
	public void etantDonneDeuxDemandesQuandEstArriveeAvantSurPremiereDemandeRetourneVrai() {
		Demande premiereDemande = new Demande(groupe, TITRE_REUNION);
		Demande deuxiemeDemande = new Demande(groupe, TITRE_REUNION);

		assertTrue(premiereDemande.estAnterieureA(deuxiemeDemande));
	}

	@Test
	public void etantDonneDeuxDemandesQuandEstArriveeAvantSurDeuxiemeDemandeRetourneFaux() {
		Demande premiereDemande = new Demande(groupe, TITRE_REUNION);
		Demande deuxiemeDemande = new Demande(groupe, TITRE_REUNION);

		assertFalse(deuxiemeDemande.estAnterieureA(premiereDemande));
	}

	@Test
	public void etantDonneUneDemandeQuandEstArriveeAvantSurElleMemeRetourneFaux() {
		assertFalse(demande.estAnterieureA(demande));
	}

	@Test
	public void quandPlacerReservationDemandeEstAssignee() {
		Salle salle = new Salle(CAPACITE_SALLE, NOM_SALLE);
		demande.placerReservation(salle);
		assertTrue(demande.estAssignee());
		assertEquals(StatutDemande.ACCEPTEE, demande.getEtat());
	}

	@Test
	public void quandAnnulerReservationDemandeEstPasAssignee() {
		demande.annulerReservation();
		assertFalse(demande.estAssignee());
		assertEquals(StatutDemande.REFUSEE, demande.getEtat());
	}

	@Test
	public void deuxDemandesSontEgalesSiElleOnLeMemeId() {
		Demande demandeDifferenteAvecLeMemeId = faireUneDemandeDifferenteAvecId(UN_ID);
		Boolean demandesSontEgales = demandeDifferenteAvecLeMemeId.equals(demande);
		assertTrue(demandesSontEgales);
	}

	@Test
	public void deuxDemandesSontDifferentesSiElleNOnPasLeMemeId() {
		Demande demande = new Demande(UN_ID, groupe, TITRE_REUNION);
		Demande demandeDifferente = new Demande(UUID.randomUUID(), groupe, TITRE_REUNION);

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
	public void quandGetNiveauPrioriteDevraitDeleguerAPriorite() {
		final int NIVEAU_PRIORITE = 5;
		Priorite priorite = mock(Priorite.class);
		given(priorite.getNiveauPriorite()).willReturn(NIVEAU_PRIORITE);
		Demande demande = new Demande(groupe, TITRE_REUNION, priorite);

		int niveauPrioriteResultat = demande.getNiveauPriorite();

		verify(priorite).getNiveauPriorite();
		assertEquals(NIVEAU_PRIORITE, niveauPrioriteResultat);
	}

	@Test
	public void etantDonneDeuxDemandesAvecLaMemePrioriteQuandEstAussiPrioritaireReturnTrue() {
		Demande demandePrioriteBasse = new Demande(groupe, TITRE_REUNION, Priorite.basse());
		assertTrue(demande.estAussiPrioritaire(demandePrioriteBasse));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteDifferenteQuandEstAussiPrioritaireReturnFalse() {
		Demande demandePrioriteMoyenne = new Demande(groupe, TITRE_REUNION, Priorite.moyenne());
		assertFalse(demande.estAussiPrioritaire(demandePrioriteMoyenne));
	}

	@Test
	public void etantDonneDemandeAvecOrganisateurQuandGetCourrielOrganisateurAlorsDonneLeBonCourrielOrganisateur() {
		String courrielOrganisateurActuel = demande.getCourrielOrganisateur();
		assertTrue(courrielOrganisateurActuel.equals(COURRIEL_ORGANISATEUR));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteDifferenteLorsqueEstPlusPrioritaireRetourneVrai() {
		Demande demandePrioriteHaute = new Demande(groupe, TITRE_REUNION, Priorite.haute());
		Demande demandePrioriteBasse = new Demande(groupe, TITRE_REUNION, Priorite.basse());

		assertTrue(demandePrioriteHaute.estPlusPrioritaire(demandePrioriteBasse));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteIdentitqueLorsqueEstPlusPrioritaireRetourneFaux() {
		Demande demandePrioriteBasse = new Demande(groupe, TITRE_REUNION, Priorite.basse());
		assertFalse(demande.estPlusPrioritaire(demandePrioriteBasse));
	}

	private Demande faireUneDemandeDifferenteAvecId(UUID id) {
		String titre = "une_deuxieme_demande";

		InformationsContact organisateur_second = mock(InformationsContact.class);
		InformationsContact reponsable_second = mock(InformationsContact.class);
		ArrayList<InformationsContact> aucunParticipant = new ArrayList<InformationsContact>();
		Groupe deuxieme_groupe = new Groupe(organisateur_second, reponsable_second, aucunParticipant);

		return new Demande(id, deuxieme_groupe, titre);
	}

	private Groupe creerGroupePlusieursParticipants(int nombreParticipants) {
		ArrayList<InformationsContact> listeParticipants = new ArrayList<InformationsContact>();

		for (int i = 0; i < nombreParticipants; i++) {
			InformationsContact nouveauEmploye = mock(InformationsContact.class);
			listeParticipants.add(nouveauEmploye);
		}

		return new Groupe(organisateur, responsable, listeParticipants);
	}
}
