package org.ClAssignateur.domaine.assignateur;

import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleStrategie;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

public class AssignateurSalle {

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

	public void lancerAssignation() {
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

	public int getNombreDemandesEnAttente() {
		return this.conteneurDemandes.getNombreDemandesEnAttente();
	}

}
