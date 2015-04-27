package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.ReservationDemandeDTOConstructeur;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.SalleConstructeur;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import java.util.Optional;
import java.util.UUID;

public class MaximiserLesPlacesDansSalleEtapes {

	private Salle salleX;
	private Salle salleY;
	private Salle salleZ;
	private UUID idDemande;

	@Given("une salle X avec une capacité de 25 participants")
	public void givenUneSalleXAvecUneCapaciteDe25Participants() {
		this.salleX = new SalleConstructeur().capacite(25).construireSalle();
		persisterSalle(this.salleX);
	}

	@Given("une salle Y avec une capacité de 15 participants")
	public void givenUneSalleYAvecUneCapaciteDe15Participants() {
		this.salleY = new SalleConstructeur().capacite(15).construireSalle();
		persisterSalle(this.salleY);
	}

	@Given("une salle Z avec une capacité de 10 participants")
	public void givenUneSalleZAvecUneCapaciteDe10Participants() {
		this.salleZ = new SalleConstructeur().capacite(10).construireSalle();
		persisterSalle(this.salleZ);
	}

	@Given("une salle X avec une capacité de 15 participants")
	public void givenUneSalleXAvecUneCapaciteDe15Participants() {
		this.salleX = new SalleConstructeur().capacite(15).construireSalle();
		persisterSalle(this.salleX);
	}

	@Given("une demande pour 13 participants")
	public void givenUneDemandePour13Participants() {
		ReservationDemandeDTO demande = new ReservationDemandeDTOConstructeur().nombreParticipants(13)
				.construireReservationDemandeDTO();
		this.idDemande = new ServiceReservationSalle().ajouterDemande(demande);
	}

	@Then("la salle Y est assignée à la demande")
	public void thenLaSalleYEstAssigneeALaDemande() {
		Demande demande = retrouverDemande();
		assertEquals(this.salleY, demande.getSalleAssignee());
	}

	@Then("la salle X ou la salle Y est assignée à la demande")
	public void thenLaSalleXOuLaSalleYEstAssigneeALaDemande() {
		Demande demande = retrouverDemande();
		Salle salleAssignee = demande.getSalleAssignee();
		assertTrue(salleAssignee.equals(this.salleX) || salleAssignee.equals(this.salleY));
	}

	private void persisterSalle(Salle salle) {
		LocalisateurServices.getInstance().obtenir(SallesEntrepot.class).persister(salle);
	}

	private Demande retrouverDemande() {
		ConteneurDemandes conteneurDemande = LocalisateurServices.getInstance().obtenir(ConteneurDemandes.class);
		Optional<Demande> demande = conteneurDemande.obtenirDemandeSelonId(this.idDemande);
		return demande.get();
	}
}
