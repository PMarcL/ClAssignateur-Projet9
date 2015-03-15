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
	private ArrayList<Employe> EMPLOYE = new ArrayList<Employe>();
	private Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, EMPLOYE);
	private final String TITRE_REUNION = "Un titre";

	private ConteneurDemandes demandesEnAttente;
	private SallesEntrepot entrepotSalles;
	private DemandesEntrepotSansDoublon demandesArchivees;
	private Demande demandeSalleDisponible;
	private Demande demandeAAnnuler;
	private Salle salleDisponible;
	private Optional<Salle> salleDisponibleOptional;
	private Optional<Demande> demandeAAnnulerOptional;
	private Notificateur notificateur;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		configurerMocks();

		assignateur = new AssignateurSalle(demandesEnAttente, entrepotSalles, demandesArchivees, notificateur);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDemandeDansConteneurDemandes() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(demandesEnAttente).ajouterDemande(demandeSalleDisponible);
	}

	@Test
	public void quandAnnulerDemandeSiDemandeDejaAssigneeDevraitAnnulerReservation() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void quandAnnulerDemandeSiDemandeDejaAssigneeDevraitMettreAJourDemande() {
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesArchivees).mettreAJourDemande(demandeAAnnuler);
	}

	@Test
	public void quandAnnulerDemandeSiDemandeDejaAssigneeDevraitNotifier() {
		given(demandeAAnnuler.getResponsable()).willReturn(RESPONSABLE);
		assignateur.annulerDemande(demandeAAnnuler);
		verify(notificateur).notifierAnnulation(demandeAAnnuler);
	}

	@Test
	public void quandAnnulerDemandeSiDemandeEnAttenteDevraitRetirerDemandeDesDemandesEnAttente() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesEnAttente).retirerDemande(demandeAAnnuler);
	}

	@Test
	public void quandAnnulerDemandeSiDemandeEnAttentDevraitArchiverDemandee() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandesArchivees).persisterDemande(demandeAAnnuler);
	}

	@Test
	public void quandAnnulerDemandeSiDemandeEnAttenteDevraitAnnulerReservation() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		assignateur.annulerDemande(demandeAAnnuler);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void quandAnnulerDemandeSiDemandePasAssigneeEtPasEnAttenteDevraitPasAnnuler() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		given(demandesEnAttente.contientDemande(demandeAAnnuler)).willReturn(false);
		verify(demandeAAnnuler, never()).annulerReservation();
	}

	@Test
	public void quandAnnulerDemandeSiDemandeEnAttenteDevraitNotifier() {
		given(demandesArchivees.trouverDemandeSelonTitre(TITRE_REUNION)).willReturn(Optional.empty());
		given(demandeAAnnuler.getResponsable()).willReturn(RESPONSABLE);

		assignateur.annulerDemande(demandeAAnnuler);

		verify(notificateur).notifierAnnulation(demandeAAnnuler);
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
	public void etantDonneeReservationPlaceeAvecSuccesNotifierSucces() {
		Demande demandeAvecSalleDisponible = new Demande(GROUPE, TITRE_REUNION);
		ajouterDemande(demandeAvecSalleDisponible);
		Salle salleDisponible = mock(Salle.class);
		Optional<Salle> salleDisponibleOptionnelle = Optional.of(salleDisponible);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeAvecSalleDisponible)).willReturn(
				salleDisponibleOptionnelle);

		assignateur.run();

		verify(notificateur).notifierSucces(demandeAvecSalleDisponible, salleDisponible);
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
	public void etantDonneeImpossibiliteDePlacerReservationNotifierEchec() {
		Demande demandeNePouvantPasEtreAssignee = new Demande(GROUPE, TITRE_REUNION);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(notificateur).notifierEchec(demandeNePouvantPasEtreAssignee);
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
		entrepotSalles = mock(SallesEntrepot.class);
		demandesArchivees = mock(DemandesEntrepotSansDoublon.class);
		demandeSalleDisponible = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		demandeAAnnulerOptional = Optional.of(demandeAAnnuler);
		salleDisponible = mock(Salle.class);
		salleDisponibleOptional = Optional.of(salleDisponible);
		notificateur = mock(Notificateur.class);

		given(entrepotSalles.obtenirSalleRepondantDemande(demandeSalleDisponible)).willReturn(salleDisponibleOptional);
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
