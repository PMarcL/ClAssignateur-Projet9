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

	private ConteneurDemandes conteneurDemandes;
	private EntrepotSalles entrepotSalles;
	private DemandesEntrepot demandesEntrepot;
	private Demande demandeSalleDisponible;
	private Demande demandeAAnnuler;
	private Salle salleDisponible;
	private Optional<Salle> salleDisponibleOptional;
	private Notificateur strategieNotification;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		conteneurDemandes = mock(ConteneurDemandes.class);
		entrepotSalles = mock(EntrepotSalles.class);
		demandesEntrepot = mock(DemandesEntrepot.class);
		demandeSalleDisponible = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		salleDisponible = mock(Salle.class);
		salleDisponibleOptional = Optional.of(salleDisponible);
		strategieNotification = mock(Notificateur.class);

		given(entrepotSalles.obtenirSalleRepondantDemande(demandeSalleDisponible)).willReturn(salleDisponibleOptional);
		given(demandeSalleDisponible.getGroupe()).willReturn(GROUPE);
		viderConteneurDemande();

		assignateur = new AssignateurSalle(conteneurDemandes, entrepotSalles, demandesEntrepot, strategieNotification);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDemandeDansConteneurDemandes() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(conteneurDemandes).ajouterDemande(demandeSalleDisponible);
	}

	@Test
	public void quandAnnulerDemandeDevraitRetirerDuConteneur() {
		assignateur.annulerDemandeEnAttente(demandeAAnnuler);
		verify(conteneurDemandes).retirerDemande(demandeAAnnuler);
	}

	@Test
	public void quandAnnulerDemandeDevraitArchiverDemande() {
		assignateur.annulerDemandeEnAttente(demandeAAnnuler);
		verify(demandesEntrepot).persisterDemande(demandeAAnnuler);
	}

	@Test
	public void quandAppelTacheMinuterieDevraitTenterAssignerChaqueDemandeAvantEffacerDemandes() {
		final int nombreDemandes = 3;
		ajouterDemandes(nombreDemandes, demandeSalleDisponible);

		assignateur.run();

		InOrder ordre = inOrder(entrepotSalles, conteneurDemandes);
		ordre.verify(entrepotSalles, times(nombreDemandes)).obtenirSalleRepondantDemande(demandeSalleDisponible);
		ordre.verify(conteneurDemandes).vider();
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
	public void etantDonneeImpossibiliteDePlaceeReservationNotifierEchecOrganisateur() {
		Demande demandeNePouvantPasEtreAssignee = new Demande(GROUPE, TITRE_REUNION);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(strategieNotification).notifier(any(MessageNotificationEchec.class), eq(ORGANISATEUR));
	}

	@Test
	public void etantDonneeImpossibiliteDePlaceeReservationNotifierEchecResponsable() {
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
		given(conteneurDemandes.iterator()).willReturn(iterateur);
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

		given(conteneurDemandes.iterator()).willReturn(iterateur);
		given(conteneurDemandes.contientAuMoins(intThat(estInferieurOuEgal(nombreDemandes)))).willReturn(
				contientAuMoinsNombreDemandes);
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
