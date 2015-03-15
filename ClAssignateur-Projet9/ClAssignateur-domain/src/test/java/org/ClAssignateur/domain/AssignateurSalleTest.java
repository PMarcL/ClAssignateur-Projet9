package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private final Employe ORGANISATEUR = new Employe("exemple@courriel.com");
	private final Employe RESPONSABLE = new Employe("exemple@gmail.com");
	private final ArrayList<Employe> EMPLOYES = new ArrayList<Employe>();
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, EMPLOYES);
	private final String TITRE_REUNION = "Un titre";
	private final boolean DEMANDE_PAS_DANS_CONTENEUR = false;
	private final int NOMBRE_DEMANDES = 5;
	private final Collection<Salle> SALLES = new ArrayList<Salle>();

	private ConteneurDemandes demandesEnAttente;
	private SallesEntrepot entrepotSalles;
	private DemandesEntrepotSansDoublon demandesArchivees;
	private SelectionSalleStrategie strategieSelectionSalle;
	private Demande demandeSalleDisponible;
	private Demande demandeAAnnuler;
	private Demande demandeAucuneSalleDisponible;
	private Salle salleDisponible;
	private Notificateur notificateur;
	private Optional<Salle> salleDisponibleOptional;
	private Optional<Demande> demandeAAnnulerOptional;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		configurerMocks();

		assignateur = new AssignateurSalle(demandesEnAttente, entrepotSalles, demandesArchivees, notificateur,
				strategieSelectionSalle);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDemandeDansConteneurDemandes() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(demandesEnAttente).ajouterDemande(demandeSalleDisponible);
	}

	@Test
	public void etantDonneDemandeDejaAssigneeQuandAnnulerDemandeDevraitAnnulerReservation() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void etantDonneDemandeDejaAssigneeQuandAnnulerDemandeDevraitMettreAJourDemande() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesArchivees).mettreAJourDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeDejaAssigneeQuandAnnulerDemandeDevraitNotifier() {
		given(demandeAAnnuler.getResponsable()).willReturn(RESPONSABLE);
		assignateur.annulerDemande(demandeAAnnuler);
		verify(notificateur).notifierAnnulation(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeEnAttenteQuandAnnulerDemandeDevraitRetirerDemandeDesDemandesEnAttente() {
		demandeAAnnulerEstPasDansArchive();
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesEnAttente).retirerDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeEnAttentQuandAnnulerDemandeDevraitArchiverDemandee() {
		demandeAAnnulerEstPasDansArchive();
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesArchivees).persisterDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeEnAttenteQuandAnnulerDemandeDevraitAnnulerReservation() {
		demandeAAnnulerEstPasDansArchive();
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void etantDonneDemandePasAssigneeEtPasEnAttenteQuandAnnulerDemandeDevraitPasAnnuler() {
		demandeAAnnulerEstPasDansArchive();
		given(demandesEnAttente.contientDemande(demandeAAnnuler)).willReturn(DEMANDE_PAS_DANS_CONTENEUR);
		verify(demandeAAnnuler, never()).annulerReservation();
	}

	@Test
	public void etantDonneDemandeEnAttenteQuandAnnulerDemandeDevraitNotifier() {
		demandeAAnnulerEstPasDansArchive();
		given(demandeAAnnuler.getResponsable()).willReturn(RESPONSABLE);

		assignateur.annulerDemande(demandeAAnnuler);

		verify(notificateur).notifierAnnulation(demandeAAnnuler);
	}

	@Test
	public void quandAppelTacheMinuterieDevraitTenterAssignerChaqueDemandeAvantEffacerDemandes() {
		ajouterDemandes(NOMBRE_DEMANDES, demandeSalleDisponible);

		assignateur.run();

		InOrder ordre = inOrder(strategieSelectionSalle, demandesEnAttente);
		ordre.verify(strategieSelectionSalle, times(NOMBRE_DEMANDES)).selectionnerSalle(SALLES, demandeSalleDisponible);
		ordre.verify(demandesEnAttente).vider();
	}

	@Test
	public void etantDonneSalleRepondantDemandeTrouveeQuandAppelTacheMinuterieDevraitReserverSalle() {
		configurerSalleRepondantADemande();
		assignateur.run();
		verify(demandeSalleDisponible).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandAssignerDemandeSalleDevraitNePasReserverSalle() {
		configurerSalleNeRepondantPasADemande();
		assignateur.run();
		verify(demandeAucuneSalleDisponible, never()).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesSuperieurOuEgalALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitAssigner() {
		ajouterDemandes(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.assignerDemandeSalleSiContientAuMoins(NOMBRE_DEMANDES);
		verify(strategieSelectionSalle, atLeast(1)).selectionnerSalle(anyListOf(Salle.class), any(Demande.class));
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesInferieurALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitNePasAssigner() {
		ajouterDemandes(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.assignerDemandeSalleSiContientAuMoins(NOMBRE_DEMANDES + 1);
		verify(strategieSelectionSalle, never()).selectionnerSalle(anyListOf(Salle.class), any(Demande.class));
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandAssignerDevraitNotifierSucces() {
		configurerSalleRepondantADemande();
		assignateur.run();
		verify(notificateur).notifierSucces(demandeSalleDisponible, salleDisponible);
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandAssignerArchiverReservation() {
		configurerSalleRepondantADemande();
		assignateur.run();
		verify(demandesArchivees).persisterDemande(demandeSalleDisponible);
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationQuandAssignerNotifierEchec() {
		configurerSalleNeRepondantPasADemande();
		assignateur.run();
		verify(notificateur).notifierEchec(demandeAucuneSalleDisponible);
	}

	private void configurerSalleRepondantADemande() {
		ajouterDemande(demandeSalleDisponible);
		salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);
	}

	private void configurerSalleNeRepondantPasADemande() {
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeAucuneSalleDisponible);
		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeAucuneSalleDisponible)).willReturn(aucuneSalle);
	}

	private void demandeAAnnulerEstPasDansArchive() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
	}

	private void viderConteneurDemande() {
		Iterator<Demande> iterateur = mock(Iterator.class);
		given(iterateur.hasNext()).willReturn(false);
		given(demandesEnAttente.iterator()).willReturn(iterateur);
	}

	private void ajouterDemande(Demande demande) {
		final int nombreDemande = 1;
		ajouterDemandes(nombreDemande, demande);
	}

	private void ajouterDemandes(int nombreDemandes, Demande demande) {
		final boolean contientAuMoinsNombreDemandes = true;

		Iterator<Demande> iterateur = mock(Iterator.class);

		if (nombreDemandes == 0) {
			viderConteneurDemande();
			return;
		} else {
			Boolean[] reponses = new Boolean[nombreDemandes];
			for (int i = 0; i < nombreDemandes - 1; i++) {
				reponses[i] = true;
			}
			reponses[nombreDemandes - 1] = false;
			given(iterateur.hasNext()).willReturn(true, reponses);
			given(iterateur.next()).willReturn(demande);
		}

		given(demandesEnAttente.iterator()).willReturn(iterateur);
		given(demandesEnAttente.contientAuMoins(intThat(estInferieurOuEgal(nombreDemandes)))).willReturn(
				contientAuMoinsNombreDemandes);
	}

	private void configurerMocks() {
		strategieSelectionSalle = mock(SelectionSalleStrategie.class);
		demandesEnAttente = mock(ConteneurDemandes.class);
		entrepotSalles = mock(SallesEntrepot.class);
		demandesArchivees = mock(DemandesEntrepotSansDoublon.class);
		demandeSalleDisponible = mock(Demande.class);
		demandeAucuneSalleDisponible = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		demandeAAnnulerOptional = Optional.of(demandeAAnnuler);
		salleDisponible = mock(Salle.class);
		salleDisponibleOptional = Optional.of(salleDisponible);
		notificateur = mock(Notificateur.class);

		given(entrepotSalles.obtenirSalles()).willReturn(SALLES);
		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeSalleDisponible)).willReturn(
				salleDisponibleOptional);
		viderConteneurDemande();
		given(demandeAAnnuler.estAssignee()).willReturn(true);
		given(demandeAAnnuler.getTitre()).willReturn(TITRE_REUNION);
		given(demandeSalleDisponible.getGroupe()).willReturn(GROUPE);
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(demandeAAnnulerOptional);
		given(demandesEnAttente.contientDemande(demandeAAnnuler)).willReturn(true);
		viderConteneurDemande();
	}

	private EstInferieurOuEgal estInferieurOuEgal(int valeur) {
		return new EstInferieurOuEgal(valeur);
	}

	private class EstInferieurOuEgal extends ArgumentMatcher<Integer> {

		private int reference;

		public EstInferieurOuEgal(int reference) {
			this.reference = reference;
		}

		public boolean matches(Object valeur) {
			return ((int) valeur <= reference);
		}
	}
}
