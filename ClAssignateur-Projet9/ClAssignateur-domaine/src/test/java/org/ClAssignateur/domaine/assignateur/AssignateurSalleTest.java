package org.ClAssignateur.domaine.assignateur;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleStrategie;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import java.util.List;
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

		assignateur = new AssignateurSalle(entrepotSalles, strategieSelectionSalle);
	}

	@Test
	public void quandLancerAssignationDevraitTenterAssignerChaqueDemande() {
		ajouterDemandesEnAttente(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(strategieSelectionSalle, times(NOMBRE_DEMANDES)).selectionnerSalle(eq(SALLES), any(Demande.class));
	}

	@Test
	public void etantDonneSalleRepondantDemandeTrouveeQuandLancerAssignationDevraitReserverSalle() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(demandeSalleDisponible).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandLancerAssignationNotifierSucces() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(notificateur).notifierSucces(demandeSalleDisponible, salleDisponible);
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandLancerAssignationDevraitArchiverReservation() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(conteneurDemandes).archiverDemande(demandeSalleDisponible);
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationQuandLancerAssignationDevraitNotifierEchec() {
		configurerSalleNeRepondantPasADemande();
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(notificateur).notifierEchec(demandeAucuneSalleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandLancerAssignationDevraitNePasReserverSalle() {
		configurerSalleNeRepondantPasADemande();
		assignateur.lancerAssignation(conteneurDemandes, notificateur);
		verify(demandeAucuneSalleDisponible, never()).placerReservation(salleDisponible);
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
		List<Demande> demandes = new ArrayList<Demande>();

		for (int i = 0; i < nombreDemandes; i++) {
			demandes.add(demande);
		}

		given(conteneurDemandes.obtenirDemandesEnAttenteOrdrePrioritaire()).willReturn(demandes);
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(nombreDemandes);
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
		given(conteneurDemandes.obtenirDemandesEnAttenteOrdrePrioritaire()).willReturn(new ArrayList<Demande>());
	}
}
