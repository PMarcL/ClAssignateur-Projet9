package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domain.notification.Notificateur;
import java.util.List;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.salles.SallesEntrepot;
import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import org.mockito.ArgumentMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private final String TITRE_REUNION = "Un titre";
	private final int NOMBRE_DEMANDES = 5;
	private final Collection<Salle> SALLES = new ArrayList<Salle>();

	private ConteneurDemandes conteneurDemandes;
	private SallesEntrepot entrepotSalles;
	private SelectionSalleStrategie strategieSelectionSalle;
	private Demande demandeSalleDisponible;
	private Demande demandeAAnnuler;
	private Demande demandeAucuneSalleDisponible;
	private Salle salleDisponible;
	private Notificateur notificateur;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		configurerMocks();

		assignateur = new AssignateurSalle(conteneurDemandes, entrepotSalles, notificateur, strategieSelectionSalle);
	}

	@Test
	public void quandAjouterDemandeDevraitMettreDemandeEnAttente() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(conteneurDemandes).mettreDemandeEnAttente(demandeSalleDisponible);
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitAnnulerReservation() {
		permettreTrouverDemandeAAnnuler();
		assignateur.annulerDemande(TITRE_REUNION);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitArchiver() {
		permettreTrouverDemandeAAnnuler();
		assignateur.annulerDemande(TITRE_REUNION);
		verify(conteneurDemandes).archiverDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitNotifierAnnulation() {
		permettreTrouverDemandeAAnnuler();
		assignateur.annulerDemande(TITRE_REUNION);
		verify(notificateur).notifierAnnulation(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeAAnnulerInexistanteQuandAnnulerDemandeDevraitNeRienFaire() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION)).willReturn(Optional.empty());
		assignateur.annulerDemande(TITRE_REUNION);
		verify(conteneurDemandes, never()).archiverDemande(any(Demande.class));
	}

	@Test
	public void quandAppelTacheMinuterieDevraitTenterTrouverSallePourChaqueDemande() {
		ajouterDemandesEnAttente(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.run();
		verify(strategieSelectionSalle, times(NOMBRE_DEMANDES)).selectionnerSalle(SALLES, demandeSalleDisponible);
	}

	@Test
	public void etantDonneSalleRepondantDemandeTrouveeQuandAppelTacheMinuterieDevraitReserverSalle() {
		configurerSalleRepondantADemande();
		assignateur.run();
		verify(demandeSalleDisponible).placerReservation(salleDisponible);
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
		verify(conteneurDemandes).archiverDemande(demandeSalleDisponible);
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationQuandAssignerNotifierEchec() {
		configurerSalleNeRepondantPasADemande();
		assignateur.run();
		verify(notificateur).notifierEchec(demandeAucuneSalleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandAssignerDemandeSalleDevraitNePasReserverSalle() {
		configurerSalleNeRepondantPasADemande();
		assignateur.run();
		verify(demandeAucuneSalleDisponible, never()).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesSuperieurOuEgalALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitAssigner() {
		ajouterDemandesEnAttente(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.assignerDemandeSalleSiContientAuMoins(NOMBRE_DEMANDES);
		verify(strategieSelectionSalle, atLeast(1)).selectionnerSalle(anyListOf(Salle.class), any(Demande.class));
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesInferieurALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitNePasAssigner() {
		ajouterDemandesEnAttente(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.assignerDemandeSalleSiContientAuMoins(NOMBRE_DEMANDES + 1);
		verify(strategieSelectionSalle, never()).selectionnerSalle(anyListOf(Salle.class), any(Demande.class));
	}

	private void configurerSalleRepondantADemande() {
		ajouterDemandeEnAttente(demandeSalleDisponible);
		salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);
	}

	private void configurerSalleNeRepondantPasADemande() {
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemandeEnAttente(demandeAucuneSalleDisponible);
		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeAucuneSalleDisponible)).willReturn(aucuneSalle);
	}

	private void ajouterDemandeEnAttente(Demande demande) {
		final int NOMBRE_DEMANDE = 1;
		ajouterDemandesEnAttente(NOMBRE_DEMANDE, demande);
	}

	private void ajouterDemandesEnAttente(int nombreDemandes, Demande demande) {
		final boolean CONTIENT_AU_MOINS_NOMBRE_DEMANDES = true;
		List<Demande> demandes = new ArrayList<Demande>();

		for (int i = 0; i < nombreDemandes; i++) {
			demandes.add(demande);
		}

		given(conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite()).willReturn(demandes);
		given(conteneurDemandes.contientAuMoinsEnAttente(intThat(estInferieurOuEgal(nombreDemandes)))).willReturn(
				CONTIENT_AU_MOINS_NOMBRE_DEMANDES);
	}

	private void configurerMocks() {
		strategieSelectionSalle = mock(SelectionSalleStrategie.class);
		conteneurDemandes = mock(ConteneurDemandes.class);
		entrepotSalles = mock(SallesEntrepot.class);
		demandeSalleDisponible = mock(Demande.class);
		demandeAucuneSalleDisponible = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		salleDisponible = mock(Salle.class);
		notificateur = mock(Notificateur.class);

		given(strategieSelectionSalle.selectionnerSalle(SALLES, demandeSalleDisponible)).willReturn(
				Optional.of(salleDisponible));
		given(demandeAAnnuler.estAssignee()).willReturn(true);
		given(demandeAAnnuler.getTitre()).willReturn(TITRE_REUNION);
		given(conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite()).willReturn(new ArrayList<Demande>());
	}

	private void permettreTrouverDemandeAAnnuler() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION))
				.willReturn(Optional.of(demandeAAnnuler));
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
