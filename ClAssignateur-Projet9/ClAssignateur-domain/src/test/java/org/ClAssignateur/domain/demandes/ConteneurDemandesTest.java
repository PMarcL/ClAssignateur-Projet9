package org.ClAssignateur.domain.demandes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.groupe.Groupe;
import org.mockito.ArgumentMatcher;
import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import org.junit.Before;
import org.junit.Test;

public class ConteneurDemandesTest {

	private final Groupe GROUPE = mock(Groupe.class);
	private final Priorite PRIORITE_BASSE = Priorite.basse();
	private final Priorite PRIORITE_HAUTE = Priorite.haute();
	private final String TITRE_REUNION = "Titre";
	private final int NB_DEMANDES_EN_ATTENTE = 5;

	private Demande demandeFaiblePriorite;
	private Demande demandeHautePriorite;
	private DemandesEntrepot demandesEnAttente;
	private DemandesEntrepot demandesArchivees;

	private ConteneurDemandes conteneurDemandes;

	@Before
	public void creerConteneurDemandes() {
		demandeFaiblePriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_BASSE);
		demandeHautePriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_HAUTE);
		demandesEnAttente = mock(DemandesEntrepot.class);
		demandesArchivees = mock(DemandesEntrepot.class);

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
		conteneurDemandes.obtenirDemandesEnAttente();
		verify(demandesEnAttente).obtenirDemandes();
	}

	@Test
	public void quandObtenirDemandesEnAttenteDevraitTrierSelonOrdreDecroissantPriorite() {
		List<Demande> demandesAjoutees = new ArrayList<Demande>();
		demandesAjoutees.add(demandeFaiblePriorite);
		demandesAjoutees.add(demandeHautePriorite);
		given(demandesEnAttente.obtenirDemandes()).willReturn(demandesAjoutees);

		List<Demande> demandesObtenues = conteneurDemandes.obtenirDemandesEnAttente();

		assertThat(demandesObtenues, estEnOrdrePrioritaireDecroissant());
	}

	@Test
	public void etantDonneDemandeArchiveeTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerDemande() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));
		Optional<Demande> demandeObtenue = conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		assertEquals(demandeFaiblePriorite, demandeObtenue.get());
	}

	@Test
	public void etantDonneDemandeArchiveeNonTrouveeQuandTrouverDemandeSelonTitreDevraitChercherDansDemandesEnAttente() {
		demandesArchiveesNeContientPasDemandeRecherchee();
		conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);
		verify(demandesEnAttente).obtenirDemandeSelonTitre(TITRE_REUNION);
	}

	@Test
	public void etantDonneDemandeEnAttenteTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerDemande() {
		demandesArchiveesNeContientPasDemandeRecherchee();
		given(demandesEnAttente.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.of(demandeFaiblePriorite));

		Optional<Demande> demandeObtenue = conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION);

		assertEquals(demandeFaiblePriorite, demandeObtenue.get());
	}

	@Test
	public void etantDonneDemandeEnAttenteNonTrouveeQuandTrouverDemandeSelonTitreDevraitRetournerResultatVide() {
		demandesArchiveesNeContientPasDemandeRecherchee();
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

	private void demandesArchiveesNeContientPasDemandeRecherchee() {
		given(demandesArchivees.obtenirDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
	}

	private EstEnOrdrePrioritaireDecroissant estEnOrdrePrioritaireDecroissant() {
		return new EstEnOrdrePrioritaireDecroissant();
	}

	private class EstEnOrdrePrioritaireDecroissant extends ArgumentMatcher<List<Demande>> {

		public boolean matches(Object demandes) {

			List<Demande> listeDemandes = (List<Demande>) demandes;
			Demande derniereDemande = null;
			for (Demande demandeCourante : listeDemandes) {
				if (derniereDemande != null) {
					if (demandeCourante.estPlusPrioritaire(derniereDemande))
						return false;
				}

				derniereDemande = demandeCourante;
			}

			return true;
		}
	}
}
