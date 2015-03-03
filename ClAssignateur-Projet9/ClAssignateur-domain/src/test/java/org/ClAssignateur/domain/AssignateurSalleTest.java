package org.ClAssignateur.domain;

import static org.mockito.BDDMockito.*;

import org.mockito.InOrder;

import java.util.Iterator;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private ConteneurDemandes conteneurDemandes;
	private EntrepotSalles entrepotSalles;
	private Demande demandeSalleDisponible;
	private Salle salleDisponible;
	private Optional<Salle> salleDisponibleOptional;

	private AssignateurSalle assignateur;

	@Before
	public void creerAssignateur() {
		conteneurDemandes = mock(ConteneurDemandes.class);
		entrepotSalles = mock(EntrepotSalles.class);
		demandeSalleDisponible = mock(Demande.class);
		salleDisponible = mock(Salle.class);
		salleDisponibleOptional = Optional.of(salleDisponible);

		given(entrepotSalles.obtenirSalleRepondantDemande(demandeSalleDisponible)).willReturn(salleDisponibleOptional);

		assignateur = new AssignateurSalle(conteneurDemandes, entrepotSalles);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDemandeDansConteneurDemandes() {
		assignateur.ajouterDemande(demandeSalleDisponible);
		verify(conteneurDemandes).ajouterDemande(demandeSalleDisponible);
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

		verify(salleDisponible).placerReservation(demandeSalleDisponible);
	}

	@Test
	public void etantDonneAucuneSalleRepondantDemandeTrouveeQuandAssignerDemandeSalleDevraitNePasReserverSalle() {
		Demande demandeNePouvantPasEtreAssignee = mock(Demande.class);
		Optional<Salle> aucuneSalle = Optional.empty();
		ajouterDemande(demandeNePouvantPasEtreAssignee);
		given(entrepotSalles.obtenirSalleRepondantDemande(demandeNePouvantPasEtreAssignee)).willReturn(aucuneSalle);

		assignateur.run();

		verify(salleDisponible, never()).placerReservation(demandeNePouvantPasEtreAssignee);
	}

	// @Test
	// public void quandDemandeAssignationParMinuterieDevraitTenterAssignation()
	// {
	// assignateur.run();
	// verify(conteneurDemandes).vider();
	// }

	private void ajouterDemande(Demande demande) {
		final int nombreDemande = 1;
		ajouterDemandes(nombreDemande, demande);
	}

	private void ajouterDemandes(int nombreDemandes, Demande demande) {
		Iterator<Demande> iterateur = mock(Iterator.class);

		if (nombreDemandes == 0) {
			given(iterateur.hasNext()).willReturn(false);
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
	}
}
