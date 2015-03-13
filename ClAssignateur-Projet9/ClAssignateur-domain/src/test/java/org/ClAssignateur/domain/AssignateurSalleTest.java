package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import java.util.Iterator;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private Employe ORGANISATEUR = mock(Employe.class);
	private Employe RESPONSABLE = mock(Employe.class);
	private Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());
	private final String TITRE_REUNION = "Un titre";

	private ConteneurDemandes demandesEnAttente;
	private EntrepotSalles entrepotSalles;
	private DemandesEntrepotSansDoublon demandesArchivees;
	private Demande demandeSalleDisponible;
	private Demande demandeAAnnuler;
	private Salle salleDisponible;
	private Optional<Salle> salleDisponibleOptional;
	private Notificateur strategieNotification;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		configurerMocks();

		assignateur = new AssignateurSalle(demandesEnAttente, entrepotSalles, demandesArchivees, strategieNotification);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDemandeDansConteneurDemandes() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(demandesEnAttente).ajouterDemande(demandeSalleDisponible);
	}

	@Test
	public void quandAnnulerDemandeDevraitAnnulerReservation() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void quandAnnulerDemandeDevraitRetirerDemandeDesDemandesEnAttente() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesEnAttente).retirerDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeNonPresenteDansDemandesArchiveesQuandAnnulerDemandeDevraitArchiverDemande() {
		assignateur.ajouterDemande(demandeAAnnuler);
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesArchivees).persisterDemande(demandeAAnnuler);
	}

	@Test
	public void quandAppelTacheMinuterieDevraitTenterAssignerChaqueDemandeAvantEffacerDemandes() {
		final int nombreDemandes = 3;
		ajouterDemandes(nombreDemandes, demandeSalleDisponible);

		assignateur.run();

		InOrder ordre = inOrder(entrepotSalles, demandesEnAttente);
		ordre.verify(entrepotSalles, times(nombreDemandes)).obtenirSalleRepondantDemande(demandeSalleDisponible);
		ordre.verify(demandesEnAttente).vider();
	}

	@Test
	public void etantDonneSalleRepondantDemandeTrouveeQuandAppelTacheMinuterieDevraitReserverSalle() {
		ajouterDemande(demandeSalleDisponible);
		Salle salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);

		assignateur.run();

		verify(demandeSalleDisponible).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandAssignerDemandeSalleDevraitNePasReserverSalle() {
		Demande demandeNePouvantPasEtreAssignee = mock(Demande.class);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(demandeNePouvantPasEtreAssignee, never()).placerReservation(salleDisponible);
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesSuperieurOuEgalALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitAssigner() {
		final int nombreDemandes = 5;
		ajouterDemandes(nombreDemandes, demandeSalleDisponible);

		assignateur.assignerDemandeSalleSiContientAuMoins(nombreDemandes);

		verify(entrepotSalles, atLeast(1)).obtenirSalleRepondantDemande(any(Demande.class));
	}

	@Test
	public void etantDonneNombreDemandesDansConteneurDemandesInferieurALimiteQuandAssignerSiContientAuMoinsUnNombreDeDemandesDevraitNePasAssigner() {
		final int nombreDemandes = 5;
		ajouterDemandes(nombreDemandes, demandeSalleDisponible);

		assignateur.assignerDemandeSalleSiContientAuMoins(nombreDemandes + 1);

		verify(entrepotSalles, never()).obtenirSalleRepondantDemande(any(Demande.class));
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesNotifierSuccesOrganisateur() {
		Demande demandeAvecSalleDisponible = new Demande(GROUPE, TITRE_REUNION);
		ajouterDemande(demandeAvecSalleDisponible);
		Salle salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeAvecSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);

		assignateur.run();

		verify(strategieNotification).notifier(any(MessageNotificationSucces.class), eq(ORGANISATEUR));
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesNotifierSuccesResponsable() {
		Demande demandeAvecSalleDisponible = new Demande(GROUPE, TITRE_REUNION);
		ajouterDemande(demandeAvecSalleDisponible);
		Salle salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeAvecSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);

		assignateur.run();

		verify(strategieNotification).notifier(any(MessageNotificationSucces.class), eq(RESPONSABLE));
	}

	@Test
	public void etantDonneeReservationPlaceeAvecSuccesReservationArchivee() {
		Demande demandeAvecSalleDisponible = new Demande(GROUPE, TITRE_REUNION);
		ajouterDemande(demandeAvecSalleDisponible);
		Salle salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeAvecSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);

		assignateur.run();

		verify(demandesArchivees).persisterDemande(demandeAvecSalleDisponible);
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationNotifierEchecOrganisateur() {
		Demande demandeNePouvantPasEtreAssignee = new Demande(GROUPE, TITRE_REUNION);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(strategieNotification).notifier(any(MessageNotificationEchec.class), eq(ORGANISATEUR));
	}

	@Test
	public void etantDonneeImpossibiliteDePlacerReservationNotifierEchecResponsable() {
		Demande demandeNePouvantPasEtreAssignee = new Demande(GROUPE, TITRE_REUNION);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(strategieNotification).notifier(any(MessageNotificationEchec.class), eq(RESPONSABLE));
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
		demandesEnAttente = mock(ConteneurDemandes.class);
		entrepotSalles = mock(EntrepotSalles.class);
		demandesArchivees = mock(DemandesEntrepotSansDoublon.class);
		demandeSalleDisponible = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		salleDisponible = mock(Salle.class);
		salleDisponibleOptional = Optional.of(salleDisponible);
		strategieNotification = mock(Notificateur.class);

		given(entrepotSalles.obtenirSalleRepondantDemande(demandeSalleDisponible)).willReturn(salleDisponibleOptional);
		given(demandeAAnnuler.estAssignee()).willReturn(true);
		given(demandeSalleDisponible.getGroupe()).willReturn(GROUPE);
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
