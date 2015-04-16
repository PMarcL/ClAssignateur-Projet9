package org.ClAssignateur.domaine.demandes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.UUID;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import java.util.Random;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesTest {

	private final String TITRE_REUNION = "Titre";
	private final int NB_DEMANDES_EN_ATTENTE = 5;
	private final String COURRIEL_ORGANISATEUR = "courriel@domaine.com";
	private final UUID ID_DEMANDE = UUID.randomUUID();

	private Demande demandeFaiblePriorite;
	private Demande demandeHautePriorite;
	private DemandesEntrepot demandesEnAttente;
	private DemandesEntrepot demandesArchivees;

	private ConteneurDemandes conteneurDemandes;

	@Before
	public void creerConteneurDemandes() {
		demandeFaiblePriorite = mock(Demande.class);
		demandeHautePriorite = mock(Demande.class);
		demandesEnAttente = mock(DemandesEntrepot.class);
		demandesArchivees = mock(DemandesEntrepot.class);

		given(demandeHautePriorite.estPlusPrioritaire(demandeFaiblePriorite)).willReturn(true);
		given(demandesEnAttente.taille()).willReturn(NB_DEMANDES_EN_ATTENTE);

		conteneurDemandes = new ConteneurDemandes(demandesEnAttente, demandesArchivees);
	}

	@Test
	public void quandMettreDemandeEnAttenteDevraitAjouterEntrepotDemandesEnAttente() {
		conteneurDemandes.mettreDemandeEnAttente(demandeFaiblePriorite);
		verify(demandesEnAttente).persisterDemande(demandeFaiblePriorite);
	}

	@Test
	public void quandObtenirDemandesEnAttenteDevraitObtenirDemandesEnAttenteEntrepot() {
		conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();
		verify(demandesEnAttente).obtenirDemandes();
	}

	@Test
	public void quandObtenirDemandesEnAttenteDevraitViderEntrepot() {
		conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();
		verify(demandesEnAttente).vider();
	}

	@Test
	public void quandObtenirDemandesAvecDeuxDemandesDejaOrdonneesDevraitTrierSelonOrdreDecroissantPriorite() {
		List<Demande> demandesAjoutees = new ArrayList<Demande>();
		demandesAjoutees.add(demandeFaiblePriorite);
		demandesAjoutees.add(demandeHautePriorite);
		given(demandesEnAttente.obtenirDemandes()).willReturn(demandesAjoutees);

		List<Demande> demandesObtenues = conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();

		assertTrue(demandesObtenues.get(0).estPlusPrioritaire(demandesObtenues.get(1)));
	}

	@Test
	public void quandObtenirDemandesAvecDeuxDemandesPasOrdonneesDevraitTrierSelonOrdreDecroissantPriorite() {
		List<Demande> demandesAjoutees = new ArrayList<Demande>();
		demandesAjoutees.add(demandeHautePriorite);
		demandesAjoutees.add(demandeFaiblePriorite);
		given(demandesEnAttente.obtenirDemandes()).willReturn(demandesAjoutees);

		List<Demande> demandesObtenues = conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();

		assertTrue(demandesObtenues.get(0).estPlusPrioritaire(demandesObtenues.get(1)));
	}

	@Test
	public void quandObtenirDemandesEnAttenteDevraitPasChangerOrdreDemandesSiMemePrioriteEtPremiereDemandeCreerEnPremier() {
		List<Demande> demandesAjoutees = new ArrayList<Demande>();
		Demande premiereDemandeHautePriorite = mock(Demande.class);
		configurerDemandesPourQuilsAientLaMemePriorite(premiereDemandeHautePriorite, demandeHautePriorite);
		demandesAjoutees.add(premiereDemandeHautePriorite);
		demandesAjoutees.add(demandeHautePriorite);
		given(demandesEnAttente.obtenirDemandes()).willReturn(demandesAjoutees);

		List<Demande> demandesObtenues = conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();

		assertEquals(premiereDemandeHautePriorite, demandesObtenues.get(0));
	}

	@Test
	public void quandObtenirDemandesEnAttenteDevraitChangerOrdreDemandesSiMemePrioriteEtPremiereDemandeCreerEnDeuxieme() {
		List<Demande> demandesAjoutees = new ArrayList<Demande>();
		Demande secondeDemandeHautePriorite = mock(Demande.class);
		configurerDemandesPourQuilsAientLaMemePriorite(demandeHautePriorite, secondeDemandeHautePriorite);
		demandesAjoutees.add(demandeHautePriorite);
		demandesAjoutees.add(secondeDemandeHautePriorite);
		given(demandesEnAttente.obtenirDemandes()).willReturn(demandesAjoutees);

		List<Demande> demandesObtenues = conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();

		assertEquals(secondeDemandeHautePriorite, demandesObtenues.get(1));
	}

	@Test
	public void etantDonneDemandeArchiveeTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerDemande() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));
		Optional<Demande> demandeObtenue = conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		assertEquals(demandeFaiblePriorite, demandeObtenue.get());
	}

	@Test
	public void etantDonneDemandeArchiveeTrouveeQuandTrouverDemandeSelonTitreDevraitRetirerDansDemandesArchivees() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));
		conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		verify(demandesArchivees).retirerDemande(demandeFaiblePriorite);
	}

	@Test
	public void etantDonneDemandeArchiveeNonTrouveeQuandTrouverDemandeSelonTitreDevraitChercherDansDemandesEnAttente() {
		demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient();
		conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		verify(demandesEnAttente).obtenirDemandeSelonTitre(TITRE_REUNION);
	}

	@Test
	public void etantDonneDemandeArchiveeNonTrouveeQuandTrouverDemandeSelonTitreDevraitRetirerDansDemandesEnAttente() {
		demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient();
		conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		verify(demandesEnAttente).retirerDemande(demandeFaiblePriorite);
	}

	@Test
	public void etantDonneDemandeEnAttenteTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerDemande() {
		demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient();
		given(demandesEnAttente.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));

		Optional<Demande> demandeObtenue = conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);

		assertEquals(demandeFaiblePriorite, demandeObtenue.get());
	}

	@Test
	public void etantDonneDemandeEnAttenteNonTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerResultatVide() {
		demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient();
		given(demandesEnAttente.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());

		Optional<Demande> demandeObtenue = conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);

		assertFalse(demandeObtenue.isPresent());
	}

	@Test
	public void quandArchiverDemandeDevraitPersisterDemandeDansDemandesArchivees() {
		conteneurDemandes.archiverDemande(demandeFaiblePriorite);
		verify(demandesArchivees).persisterDemande(demandeFaiblePriorite);
	}

	@Test
	public void etantDonneXDemandesEnAttenteQuandGetNombreDemandesEnAttenteDevraitRetournerX() {
		final int NOMBRE_DEMANDES = mettreUnNombreAleatoireDeDemandesEnAttente();
		int nbDemandesEnAttente = conteneurDemandes.getNombreDemandesEnAttente();
		assertEquals(NOMBRE_DEMANDES, nbDemandesEnAttente);
	}

	@Test
	public void quandObtenirDemandesSelonCourrielOrganisateurDevraitRecupererDemandesDansDemandesEnAttente() {
		conteneurDemandes.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		verify(demandesEnAttente).obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	}

	@Test
	public void quandObtenirDemandesSelonCourrielOrgansiateurDevraitRecupereremandesDansDemandesArchivees() {
		conteneurDemandes.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		verify(demandesArchivees).obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	}

	@Test
	public void quandObtenirDemandesSelonCourrielOrganisateurDevraitRetournerEnsembleDemandesEnAttenteEtArchivees() {
		List<Demande> demandesEnAttenteOrganisateur = creerListePlusieursDemandes();
		given(demandesEnAttente.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR)).willReturn(
				demandesEnAttenteOrganisateur);
		List<Demande> demandesArchiveesOrganisateur = creerListePlusieursDemandes();
		given(demandesArchivees.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR)).willReturn(
				demandesArchiveesOrganisateur);

		List<Demande> demandesOrganisateur = conteneurDemandes
				.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);

		assertTrue(demandesOrganisateur.containsAll(demandesEnAttenteOrganisateur));
		assertTrue(demandesOrganisateur.containsAll(demandesArchiveesOrganisateur));
	}

	@Test
	public void quandObtenirDemandeSelonCourrielOrganisateurEtIdDevraitChercherDansDemandesEnAttente() {
		conteneurDemandes.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, ID_DEMANDE);
		verify(demandesEnAttente).obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	}

	@Test
	public void quandObtenirDemandeSelonCourrielOrganisateurEtIdDevraitChercherDansDemandesArchivees() {
		conteneurDemandes.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, ID_DEMANDE);
		verify(demandesArchivees).obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	}

	@Test
	public void etantDonneDemandeAvecIdInexistanteQuandObtenirDemandeSelonCourrielOrganisateurEtIdDevraitRetournerOptionalVide() {
		Optional<Demande> resultat = conteneurDemandes.obtenirDemandeSelonCourrielOrganisateurEtId(
				COURRIEL_ORGANISATEUR, ID_DEMANDE);
		assertFalse(resultat.isPresent());
	}

	@Test
	public void etantDonneDemandeAvecIdExistanteQuandObtenirDemandeSelonCourrielOrganisateurEtIdDevraitRetournerDemande() {
		Demande demandeAvecId = ajouterDemandeAvecId();
		Optional<Demande> resultat = conteneurDemandes.obtenirDemandeSelonCourrielOrganisateurEtId(
				COURRIEL_ORGANISATEUR, ID_DEMANDE);
		assertEquals(demandeAvecId, resultat.get());
	}

	private Demande ajouterDemandeAvecId() {
		Demande demandeAvecId = mock(Demande.class);
		given(demandeAvecId.getID()).willReturn(ID_DEMANDE);
		List<Demande> demandesEnAttenteOrganisateur = new ArrayList<>();
		demandesEnAttenteOrganisateur.add(demandeAvecId);
		given(demandesEnAttente.obtenirDemandesSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR)).willReturn(
				demandesEnAttenteOrganisateur);
		return demandeAvecId;
	}

	private List<Demande> creerListePlusieursDemandes() {
		final int NOMBRE_DEMANDES = 5;
		List<Demande> demandes = new ArrayList<Demande>();
		for (int i = 0; i < NOMBRE_DEMANDES; i++)
			demandes.add(mock(Demande.class));

		return demandes;
	}

	private void configurerDemandesPourQuilsAientLaMemePriorite(Demande premiereDemande, Demande secondeDemande) {
		given(premiereDemande.estAussiPrioritaire(secondeDemande)).willReturn(true);
		given(premiereDemande.estAnterieureA(secondeDemande)).willReturn(true);
		given(premiereDemande.estPlusPrioritaire(secondeDemande)).willReturn(false);
		given(secondeDemande.estAussiPrioritaire(premiereDemande)).willReturn(true);
		given(secondeDemande.estAnterieureA(premiereDemande)).willReturn(false);
		given(secondeDemande.estPlusPrioritaire(premiereDemande)).willReturn(false);
	}

	private int mettreUnNombreAleatoireDeDemandesEnAttente() {
		final int BORNE_SUPERIEURE = 99;
		int nbDemandes = new Random().nextInt(BORNE_SUPERIEURE) + 1;
		given(demandesEnAttente.taille()).willReturn(nbDemandes);
		return nbDemandes;
	}

	private void demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		given(demandesEnAttente.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));
	}
}
