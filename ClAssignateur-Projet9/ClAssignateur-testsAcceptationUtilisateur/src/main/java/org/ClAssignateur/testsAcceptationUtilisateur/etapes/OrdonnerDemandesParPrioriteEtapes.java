package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.ConteneurDemandesFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.ReservationDemandeDTOConstructeur;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import java.util.UUID;

public class OrdonnerDemandesParPrioriteEtapes {

	private UUID idDemandePrioriteBasse;
	private UUID idDemandePrioriteHaute;
	private UUID idPremiereDemandeMemePriorite;
	private UUID idDeuxiemeDemandeMemePriorite;

	@Given("une demande à priorité basse en attente")
	public void givenUneDemandeAPrioriteBasseEnAttente() {
		ReservationDemandeDTO demandePrioriteBasse = creerDemandeAvecPriorite(Priorite.basse());
		this.idDemandePrioriteBasse = envoyerDemande(demandePrioriteBasse);
	}

	@Given("une demande à priorité haute en attente")
	public void givenUneDemandeAPrioriteHauteEnAttente() {
		ReservationDemandeDTO demandePrioriteHaute = creerDemandeAvecPriorite(Priorite.haute());
		this.idDemandePrioriteHaute = envoyerDemande(demandePrioriteHaute);
	}

	@Given("deux demandes de même priorité en attente")
	public void givenDeuxDemandesDeMemePrioriteEnAttente() {
		final Priorite PRIORITE = Priorite.basse();

		ReservationDemandeDTO premiereDemande = creerDemandeAvecPriorite(PRIORITE);
		this.idPremiereDemandeMemePriorite = envoyerDemande(premiereDemande);

		ReservationDemandeDTO deuxiemeDemande = creerDemandeAvecPriorite(PRIORITE);
		this.idDeuxiemeDemandeMemePriorite = envoyerDemande(deuxiemeDemande);
	}

	@Then("la demande à priorité haute est traitée avant celle à priorité basse")
	public void thenLaDemandeAPrioriteHauteEstTraiteeAvantCelleAPrioriteBasse() {
		Demande premiereDemandeTraitee = retirerDemandeTraitee();
		Demande deuxiemeDemandeTraitee = retirerDemandeTraitee();

		assertEquals(this.idDemandePrioriteHaute, premiereDemandeTraitee.getID());
		assertEquals(this.idDemandePrioriteBasse, deuxiemeDemandeTraitee.getID());
	}

	@Then("les demandes sont traitées selon leur ordre d'arrivée")
	public void thenLesDemandesSontTraiteesSelonLeurOrdreArrivee() {
		Demande premiereDemandeTraitee = retirerDemandeTraitee();
		Demande deuxiemeDemandeTraitee = retirerDemandeTraitee();

		assertEquals(this.idPremiereDemandeMemePriorite, premiereDemandeTraitee.getID());
		assertEquals(this.idDeuxiemeDemandeMemePriorite, deuxiemeDemandeTraitee.getID());
	}

	private ReservationDemandeDTO creerDemandeAvecPriorite(Priorite prioriteDemande) {
		return new ReservationDemandeDTOConstructeur().priorite(prioriteDemande).construireReservationDemandeDTO();
	}

	private UUID envoyerDemande(ReservationDemandeDTO demande) {
		return new ServiceReservationSalle().ajouterDemande(demande);
	}

	private Demande retirerDemandeTraitee() {
		ConteneurDemandesFake conteneurDemandes = (ConteneurDemandesFake) LocalisateurServices.getInstance().obtenir(
				ConteneurDemandes.class);
		return conteneurDemandes.retirerDemandeTraitee();
	}

}
