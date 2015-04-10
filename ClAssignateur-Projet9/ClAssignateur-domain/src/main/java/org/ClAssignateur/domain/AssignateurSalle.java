package org.ClAssignateur.domain;

import org.ClAssignateur.domain.notification.Notificateur;
import java.util.List;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.salles.SallesEntrepot;
import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import java.util.Collection;
import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes conteneurDemandes;
	private SallesEntrepot entrepotSalles;
	private Notificateur notificateur;
	private SelectionSalleStrategie selectionSalleStrategie;

	public AssignateurSalle(ConteneurDemandes conteneurDemandes, SallesEntrepot salles, Notificateur notificateur,
			SelectionSalleStrategie strategieSelectionSalle) {
		this.conteneurDemandes = conteneurDemandes;
		this.entrepotSalles = salles;
		this.notificateur = notificateur;
		this.selectionSalleStrategie = strategieSelectionSalle;
	}

	public void ajouterDemande(Demande demande) {
		this.conteneurDemandes.mettreDemandeEnAttente(demande);
	}

	public void annulerDemande(String titreDemandeAnnulee) {
		Optional<Demande> demandeAAnnuler = this.conteneurDemandes.trouverDemandeSelonTitreReunion(titreDemandeAnnulee);

		if (demandeAAnnuler.isPresent()) {
			annulerReservation(demandeAAnnuler.get());
		}
	}

	private void annulerReservation(Demande demandeAAnnuler) {
		demandeAAnnuler.annulerReservation();
		this.conteneurDemandes.archiverDemande(demandeAAnnuler);
		this.notificateur.notifierAnnulation(demandeAAnnuler);
	}

	public void assignerDemandeSalleSiContientAuMoins(int nombreDemandes) {
		if (conteneurDemandes.contientAuMoinsEnAttente(nombreDemandes)) {
			assignerDemandeSalle();
		}
	}

	@Override
	public void run() {
		assignerDemandeSalle();
	}

	private void assignerDemandeSalle() {
		Collection<Salle> salles = entrepotSalles.obtenirSalles();
		List<Demande> demandesEnAttente = conteneurDemandes.obtenirDemandesEnAttenteEnOrdreDePriorite();

		for (Demande demandeCourante : demandesEnAttente) {
			Optional<Salle> salle = selectionSalleStrategie.selectionnerSalle(salles, demandeCourante);

			if (salle.isPresent()) {
				reserverSalle(demandeCourante, salle.get());
				this.notificateur.notifierSucces(demandeCourante, salle.get());
			} else {
				this.notificateur.notifierEchec(demandeCourante);
			}
		}
	}

	private void reserverSalle(Demande demandeCourante, Salle salle) {
		demandeCourante.placerReservation(salle);
		this.conteneurDemandes.archiverDemande(demandeCourante);
	}

}
