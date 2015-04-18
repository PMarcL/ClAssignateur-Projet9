package org.ClAssignateur.persistance.hibernate;

import static org.junit.Assert.*;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateDemandeEntrepotTest {

	private static final int NB_PARTICIPANTS = 10;
	private static final int TAILLE_INITIALE_DESIREE = 0;
	private static final int NB_DEMANDES_AJOUTEES = 5;
	private static final Object TAILLE_APRES_AJOUT_INITIAL_DESIREE = 1;
	private static final Object TAILLE_APRES_SECOND_AJOUT_DESIREE = 2;
	private static final String UN_TITRE_DISTINCT = "titre_distinct";
	private static final String UN_TITRE = "titre";
	private static final String COURRIEL_ORGANISATEUR = "courriel@hotmail.com";
	private static final String COURRIEL_NON_CORRESPONDANT = "courriel2@hotmail.com";
	private static final int UNE_SEULE_DEMANDE = 1;
	private static final UUID UN_UUID = UUID.randomUUID();

	private HibernateDemandeEntrepot entrepot;
	private EntityManagerProvider entityManagerProvider;

	private InformationsContact organisateur = new InformationsContact(COURRIEL_ORGANISATEUR);
	private InformationsContact responsable = new InformationsContact(COURRIEL_NON_CORRESPONDANT);
	private ContactsReunion contactsReunion = new ContactsReunion(organisateur, responsable,
			new ArrayList<InformationsContact>());

	Demande demande;

	@Before
	public void initialement() {
		EntityManagerProvider.setEntityManager(EntityManagerFactoryProvider.getFactory().createEntityManager());
		entityManagerProvider = new EntityManagerProvider();
		entrepot = new HibernateDemandeEntrepot(entityManagerProvider);
	}

	@After
	public void apresEffacerEntityManager() {
		supprimerToutesLesDemandes();
		EntityManagerProvider.clearEntityManager();
	}

	private void supprimerToutesLesDemandes() {
		EntityManager em = entityManagerProvider.getEntityManager();

		em.getTransaction().begin();
		em.remove(organisateur);
		em.remove(responsable);
		em.remove(contactsReunion);
		String requete = "Delete From Demande";
		em.createQuery(requete).executeUpdate();
		em.getTransaction().commit();
	}

	@Test
	public void etantDonneUnEntrepotInitialeQuandTailleAlorsZeroEstRetourne() {
		int taille_obtenue = entrepot.taille();
		assertEquals(TAILLE_INITIALE_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecUnDemandePossedantIdXQuandPersiterLaDemandeExistanteAvecIdXAlorsTailleResteInchange() {
		faireEnSorteQuEntrepotPossedeUneDemande();

		entrepot.persisterDemande(demande);
		int taille_obtenue = entrepot.taille();

		assertEquals(TAILLE_APRES_AJOUT_INITIAL_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecUnDemandePossedantIdXQuandPersiterLaDemandeAvecIdYAlorsTailleAugmenteDeUn() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		Demande demandeY = creerUneDemande();

		entrepot.persisterDemande(demandeY);
		int taille_obtenue = entrepot.taille();

		assertEquals(TAILLE_APRES_SECOND_AJOUT_DESIREE, taille_obtenue);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandeSelonIdDoitRetournerLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterPlusieursDemandesALEntrepot();

		Demande demandeRetrouve = entrepot.obtenirDemandeSelonId(demande.getID()).get();

		assertEquals(demande, demandeRetrouve);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandeSelonTitreDoitRetournerLaBonneDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		ajouterPlusieursDemandesALEntrepot();

		Demande demandeRetrouve = entrepot.obtenirDemandeSelonTitre(UN_TITRE_DISTINCT).get();

		assertEquals(demande, demandeRetrouve);
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeObtenirDemandesDoitDonnerToutesLesDemandes() {
		Demande demande1 = ajouterUneDemandeALEntrepot();
		Demande demande2 = ajouterUneDemandeALEntrepot();
		Demande demande3 = ajouterUneDemandeALEntrepot();

		List<Demande> demandes = entrepot.obtenirDemandes();

		assertTrue(demandes.contains(demande1));
		assertTrue(demandes.contains(demande2));
		assertTrue(demandes.contains(demande3));
	}

	@Test
	public void etantDonneUnEntrepotAvecDeMultipleDemandeApresViderTailleEgaleZero() {
		ajouterPlusieursDemandesALEntrepot();

		entrepot.vider();
		int tailleApresVider = entrepot.taille();

		assertEquals(TAILLE_INITIALE_DESIREE, tailleApresVider);
	}

	@Test
	public void etantDonneUnEntrepotAvecMultipleDemandeApresRetirerDemandeObtenirDemandeDonneAucuneDemandePourLIdDeLaDemande() {
		faireEnSorteQuEntrepotPossedeUneDemande();
		// ajouterPlusieursDemandesALEntrepot();

		boolean demandeEstPresenteAvant = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();
		entrepot.retirerDemande(demande);
		boolean demandeEstPresenteApres = entrepot.obtenirDemandeSelonId(UN_UUID).isPresent();

		assertTrue(demandeEstPresenteAvant);
		assertFalse(demandeEstPresenteApres);
	}

	// @Test
	// public void
	// etantDonneUnEntrepotAvecUneDemandeDunOrganisateurLorsqueObtenirDemandeSelonCourrielDonneUneSeulDemande()
	// {
	// faireEnSorteQuEntrepotPossedeUneDemande();
	//
	// List<Demande> demandesRecus =
	// entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	// int taille_actuelle = demandesRecus.size();
	//
	// assertEquals(UNE_SEULE_DEMANDE, taille_actuelle);
	// }
	//
	// @Test
	// public void
	// etantDonneUnEntrepotAvecUneDemandeDunOrganisateurLorsqueObtenirDemandeSelonOrganisateurRetourneLaDemande()
	// {
	// faireEnSorteQuEntrepotPossedeUneDemande();
	// List<Demande> demandesRecus =
	// entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	// assertTrue(demandesRecus.contains(demande));
	// }
	//
	// @Test
	// public void
	// etantDonneUnEntrepotAvecPlusieursDemandeQuiNAppartiennePasALorganisateurAlorsDonneUneListVide()
	// {
	// ajouterPlusieursDemandesALEntrepot();
	// List<Demande> demandesRecus =
	// entrepot.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	// assertTrue(demandesRecus.isEmpty());
	// }

	private void faireEnSorteQuEntrepotPossedeUneDemande() {
		demande = new Demande(NB_PARTICIPANTS, contactsReunion, UN_TITRE_DISTINCT, Priorite.basse());
		entrepot.persisterDemande(demande);
	}

	private void ajouterPlusieursDemandesALEntrepot() {
		for (int i = 0; i < NB_DEMANDES_AJOUTEES; i++) {
			ajouterUneDemandeALEntrepot();
		}
	}

	private Demande ajouterUneDemandeALEntrepot() {
		Demande demande = creerUneDemande();
		entrepot.persisterDemande(demande);
		return demande;
	}

	private Demande creerUneDemande() {
		InformationsContact organisateur = new InformationsContact(COURRIEL_NON_CORRESPONDANT);
		InformationsContact responsable = new InformationsContact(COURRIEL_ORGANISATEUR);
		ArrayList<InformationsContact> participants = new ArrayList<InformationsContact>();
		ContactsReunion contactsReunion = new ContactsReunion(organisateur, responsable, participants);

		Demande demande = new Demande(NB_PARTICIPANTS, contactsReunion, UN_TITRE, Priorite.basse());

		return demande;
	}
}
