package org.ClAssignateur.domain.demandes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesTest {

	private final String TITRE_REUNION = "Titre";
	private final int NB_DEMANDES_EN_ATTENTE = 5;

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

	private void configurerDemandesPourQuilsAientLaMemePriorite(Demande premiereDemande, Demande secondeDemande) {
		given(premiereDemande.estAussiPrioritaire(secondeDemande)).willReturn(true);
		given(premiereDemande.estArriveeAvant(secondeDemande)).willReturn(true);
		given(premiereDemande.estPlusPrioritaire(secondeDemande)).willReturn(false);
		given(secondeDemande.estAussiPrioritaire(premiereDemande)).willReturn(true);
		given(secondeDemande.estArriveeAvant(premiereDemande)).willReturn(false);
		given(secondeDemande.estPlusPrioritaire(premiereDemande)).willReturn(false);
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
	public void etantDonneTailleDemandeEnAttenteInferieureQuantiteRequiseQuandContientAuMoinsEnAttenteDevraitRetournerFaux() {
		boolean resultat = conteneurDemandes.contientAuMoinsEnAttente(NB_DEMANDES_EN_ATTENTE + 1);
		assertFalse(resultat);
	}

	@Test
	public void etantDonneTailleDemandeEnAttenteSuperieureQuantiteRequiseQuandContientAuMoinsEnAttenteDevraitRetournerVrai() {
		boolean resultat = conteneurDemandes.contientAuMoinsEnAttente(NB_DEMANDES_EN_ATTENTE - 1);
		assertTrue(resultat);
	}

	@Test
	public void etantDonneTailleDemandeEnAttenteEgaleQuantiteRequisesQuandContientAuMoinsEnAttenteDevraitRetournerVrai() {
		boolean resultat = conteneurDemandes.contientAuMoinsEnAttente(NB_DEMANDES_EN_ATTENTE);
		assertTrue(resultat);
	}

	private void demandesArchiveesNeContientPasDemandeRechercheeMaisdemandesAttentesLaContient() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		given(demandesEnAttente.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));
	}

}
