package org.ClAssignateur.domain;

import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.salles.SallesEntrepot;
import org.ClAssignateur.domain.salles.SelectionSalleStrategie;

import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepotSansDoublon;
import java.util.Collection;
import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandesEnAttente;
	private SallesEntrepot entrepotSalles;
	private DemandesEntrepotSansDoublon demandesArchivees;
	private Notificateur notificateur;
	private SelectionSalleStrategie selectionSalleStrategie;

	public AssignateurSalle(ConteneurDemandes demandes, SallesEntrepot salles,
			DemandesEntrepotSansDoublon demandesArchivees, Notificateur notificateur,
			SelectionSalleStrategie strategieSelectionSalle) {
		this.demandesEnAttente = demandes;
		this.demandesArchivees = demandesArchivees;
		this.entrepotSalles = salles;
		this.notificateur = notificateur;
		this.selectionSalleStrategie = strategieSelectionSalle;
	}

	public void ajouterDemande(Demande demande) {
		this.demandesEnAttente.ajouterDemande(demande);
	}

	public void annulerDemande(Demande demandeAnnulee) {
		Optional<Demande> demandeCorrespondante = this.demandesArchivees.trouverDemandeSelonTitre(demandeAnnulee
				.getTitre());
		if (demandeCorrespondante.isPresent()) {
			annulerReservationArchivee(demandeCorrespondante);
		} else if (this.demandesEnAttente.contientDemande(demandeAnnulee)) {
			annulerDemandeEnAttente(demandeAnnulee);
		}
	}

	private void annulerReservationArchivee(Optional<Demande> demandeAAnnuler) {
		demandeAAnnuler.get().annulerReservation();
		this.demandesArchivees.mettreAJourDemande(demandeAAnnuler.get());
		this.notificateur.notifierAnnulation(demandeAAnnuler.get());
	}

	private void annulerDemandeEnAttente(Demande demandeAAnnuler) {
		demandeAAnnuler.annulerReservation();
		this.demandesEnAttente.retirerDemande(demandeAAnnuler);
		this.demandesArchivees.persisterDemande(demandeAAnnuler);
		this.notificateur.notifierAnnulation(demandeAAnnuler);
	}

	public void assignerDemandeSalleSiContientAuMoins(int nombreDemandes) {
		if (demandesEnAttente.contientAuMoins(nombreDemandes)) {
			assignerDemandeSalle();
		}
	}

	@Override
	public void run() {
		assignerDemandeSalle();
	}

	private void assignerDemandeSalle() {
		Collection<Salle> salles = entrepotSalles.obtenirSalles();

		for (Demande demandeCourante : demandesEnAttente) {
			Optional<Salle> salle = selectionSalleStrategie.selectionnerSalle(salles, demandeCourante);

			if (salle.isPresent()) {
				reserverSalle(demandeCourante, salle.get());
				this.notificateur.notifierSucces(demandeCourante, salle.get());
			} else {
				this.notificateur.notifierEchec(demandeCourante);
			}
		}

		demandesEnAttente.vider();
	}

	private void reserverSalle(Demande demandeCourante, Salle salle) {
		demandeCourante.placerReservation(salle);
		this.demandesArchivees.persisterDemande(demandeCourante);
	}

}
