package org.ClAssignateur.domaine.demandes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.Demande.EtatDemande;
import org.ClAssignateur.domaine.salles.Salle;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final String TITRE_REUNION = "Titre de ma réunion";
	private final String NOM_SALLE = "salle";
	private final String COURRIEL_ORGANISATEUR = "organisateur@hotmail.com";
	private final String COURRIEL_RESPONSABLE = "responsable@hotmail.com";
	private final int NOMBRE_PARTICIPANTS = 10;
	private final int CAPACITE_SALLE = 15;
	private final Priorite PRIORITE_BASSE = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();
	private final UUID ID = UUID.randomUUID();

	private InformationsContact organisateur;
	private InformationsContact responsable;
	private ContactsReunion contacts;
	private Demande demande;

	@Before
	public void intialisation() {
		organisateur = new InformationsContact(COURRIEL_ORGANISATEUR);
		responsable = new InformationsContact(COURRIEL_RESPONSABLE);
		contacts = new ContactsReunion(organisateur, responsable, new ArrayList<InformationsContact>());

		demande = new Demande(ID, NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, PRIORITE_MOYENNE);
	}

	@Test
	public void genereAutomatiquementUnId() {
		Demande demandeAvecIdAleatoire = creerDemande();
		UUID id = demandeAvecIdAleatoire.getID();
		assertNotNull(id);
	}

	@Test
	public void demandePossedeInitialementLeChampsIdCommeDefiniDansLeConstructeur() {
		UUID id = demande.getID();
		assertEquals(ID, id);
	}

	@Test
	public void demandePossedeInitialementLeChampTitre() {
		String titre = demande.getTitre();
		assertEquals(TITRE_REUNION, titre);
	}

	@Test
	public void demandeEstInitialementEnAttente() {
		EtatDemande resultat = demande.getEtat();
		assertEquals(EtatDemande.EN_ATTENTE, resultat);
	}

	@Test
	public void demandePossedeInitialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = creerDemande();
		assertTrue(demandeAvecPriorite.estAussiPrioritaire(demandeAvecPriorite));
	}

	@Test
	public void demandeEstInitialementPasAssignee() {
		assertFalse(demande.estAssignee());
	}

	@Test
	public void demandePossedeInitialementLeChampResponsableCommeDefiniDansContactReunion() {
		InformationsContact responsableResultat = demande.getResponsable();
		assertEquals(responsable, responsableResultat);
	}

	@Test
	public void demandePossedeInitialementLeChampParticipantsCommeDefiniDansContactReunion() {
		List<InformationsContact> contactsDemande = demande.getParticipants();
		assertEquals(contacts.participants, contactsDemande);
	}

	@Test
	public void demandePossedeInitialementOrganisateurTelQueDefiniDansContactReunionAvecLeBonCourriel() {
		String courrielOrganisateur = demande.getCourrielOrganisateur();
		assertEquals(COURRIEL_ORGANISATEUR, courrielOrganisateur);
	}

	@Test
	public void quandGetNbParticipantsDevraitRetournerValeurFournieConstructeur() {
		int nbParticipants = demande.getNbParticipants();
		assertEquals(NOMBRE_PARTICIPANTS, nbParticipants);
	}

	@Test
	public void etantDonneDeuxDemandesQuandEstArriveeAvantSurPremiereDemandeRetourneVrai() {
		Demande premiereDemande = creerDemande();
		Demande deuxiemeDemande = creerDemande();

		assertTrue(premiereDemande.estAnterieureA(deuxiemeDemande));
	}

	@Test
	public void etantDonneDeuxDemandesQuandEstArriveeAvantSurDeuxiemeDemandeRetourneFaux() {
		Demande premiereDemande = creerDemande();
		Demande deuxiemeDemande = creerDemande();

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
		assertEquals(EtatDemande.ACCEPTEE, demande.getEtat());
	}

	@Test
	public void quandAnnulerReservationDevraitAvoirStatutAnnulee() {
		demande.annulerReservation();
		assertFalse(demande.estAssignee());
		assertEquals(EtatDemande.ANNULEE, demande.getEtat());
	}

	@Test
	public void deuxDemandesSontEgalesSiElleOnLeMemeId() {
		Demande demandeDifferenteAvecLeMemeId = faireUneDemandeDifferenteAvecId(ID);
		Boolean demandesSontEgales = demandeDifferenteAvecLeMemeId.equals(demande);
		assertTrue(demandesSontEgales);
	}

	@Test
	public void uneDemandeNEstPasEgalANull() {
		assertFalse(demande.equals(null));
	}

	@Test
	public void uneDemandeNEstPasEgalAUnAutreTypeObjet() {
		assertFalse(demande.equals(contacts));
	}

	@Test
	public void deuxDemandesSontDifferentesSiIdDifferents() {
		Demande demande = new Demande(ID, NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, PRIORITE_MOYENNE);
		Demande demandeDifferente = creerDemande();

		boolean demandesSontEgales = demandeDifferente.equals(demande);

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
		Demande demande = new Demande(NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, priorite);

		int niveauPrioriteResultat = demande.getNiveauPriorite();

		verify(priorite).getNiveauPriorite();
		assertEquals(NIVEAU_PRIORITE, niveauPrioriteResultat);
	}

	@Test
	public void etantDonneDeuxDemandesAvecLaMemePrioriteQuandEstAussiPrioritaireReturnTrue() {
		Demande demandeMemePriorite = creerDemande();
		assertTrue(demande.estAussiPrioritaire(demandeMemePriorite));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteDifferenteQuandEstAussiPrioritaireReturnFalse() {
		Demande demandePrioriteDifferente = new Demande(NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, PRIORITE_BASSE);
		assertFalse(demande.estAussiPrioritaire(demandePrioriteDifferente));
	}

	@Test
	public void etantDonneDemandeAvecOrganisateurQuandGetCourrielOrganisateurAlorsDonneLeBonCourrielOrganisateur() {
		String courrielOrganisateurActuel = demande.getOrganisateur().getAdresseCourriel();
		assertTrue(courrielOrganisateurActuel.equals(COURRIEL_ORGANISATEUR));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteDifferenteLorsqueEstPlusPrioritaireRetourneVrai() {
		Demande demandePrioriteElevee = creerDemande();
		Demande demandePrioriteBasse = new Demande(NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, PRIORITE_BASSE);

		assertTrue(demandePrioriteElevee.estPlusPrioritaire(demandePrioriteBasse));
	}

	@Test
	public void etantDonneDeuxDemandesAvecPrioriteIdentitqueLorsqueEstPlusPrioritaireRetourneFaux() {
		Demande demandeMemePriorite = creerDemande();
		assertFalse(demande.estPlusPrioritaire(demandeMemePriorite));
	}

	@Test
	public void quandRefuserDevraitIndiquerStatutResusee() {
		demande.refuser();
		assertEquals(Demande.EtatDemande.REFUSEE, demande.getEtat());
	}

	private Demande faireUneDemandeDifferenteAvecId(UUID id) {
		String titre = "une_deuxieme_demande";

		InformationsContact organisateur_second = mock(InformationsContact.class);
		InformationsContact reponsable_second = mock(InformationsContact.class);
		ArrayList<InformationsContact> aucunParticipant = new ArrayList<InformationsContact>();
		ContactsReunion deuxieme_groupe = new ContactsReunion(organisateur_second, reponsable_second, aucunParticipant);

		return new Demande(id, NOMBRE_PARTICIPANTS, deuxieme_groupe, titre, PRIORITE_MOYENNE);
	}

	private Demande creerDemande() {
		return new Demande(NOMBRE_PARTICIPANTS, contacts, TITRE_REUNION, PRIORITE_MOYENNE);
	}
}
