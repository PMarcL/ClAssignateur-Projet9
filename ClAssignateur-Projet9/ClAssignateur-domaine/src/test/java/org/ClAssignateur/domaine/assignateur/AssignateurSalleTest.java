package org.ClAssignateur.domaine.assignateur;

import static org.junit.Assert.*;
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
	public void quandLancerAssignationDevraitTenterAssignerChaqueDemande() {
		ajouterDemandesEnAttente(NOMBRE_DEMANDES, demandeSalleDisponible);
		assignateur.lancerAssignation();
		verify(strategieSelectionSalle, times(NOMBRE_DEMANDES)).selectionnerSalle(eq(SALLES), any(Demande.class));
	}

	@Test
	public void etantDonneSalleRepondantDemandeTrouveeQuandLancerAssignationDevraitReserverSalle() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation();
		verify(demandeSalleDisponible).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandLancerAssignationNotifierSucces() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation();
		verify(notificateur).notifierSucces(demandeSalleDisponible, salleDisponible);
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesQuandLancerAssignationDevraitArchiverReservation() {
		configurerSalleRepondantADemande();
		assignateur.lancerAssignation();
		verify(conteneurDemandes).archiverDemande(demandeSalleDisponible);
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationQuandLancerAssignationDevraitNotifierEchec() {
		configurerSalleNeRepondantPasADemande();
		assignateur.lancerAssignation();
		verify(notificateur).notifierEchec(demandeAucuneSalleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandLancerAssignationDevraitNePasReserverSalle() {
		configurerSalleNeRepondantPasADemande();
		assignateur.lancerAssignation();
		verify(demandeAucuneSalleDisponible, never()).placerReservation(salleDisponible);
	}

	@Test
	public void quandGetNombreDemandesEnAttenteDevraitDemanderAConteneurDemandes() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(NOMBRE_DEMANDES);
		int nbDemandesEnAttente = assignateur.getNombreDemandesEnAttente();
		assertEquals(NOMBRE_DEMANDES, nbDemandesEnAttente);
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

	private void permettreTrouverDemandeAAnnuler() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_REUNION))
				.willReturn(Optional.of(demandeAAnnuler));
	}
}
