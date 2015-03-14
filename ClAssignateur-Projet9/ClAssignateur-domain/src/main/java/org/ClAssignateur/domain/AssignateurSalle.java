package org.ClAssignateur.domain;

import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandesEnAttente;
	private EntrepotSalles salles;
	private DemandesEntrepotSansDoublon demandesArchivees;
	private Notificateur notificateur;
	private MessageNotificationFactory messageNotificationFactory;

	public AssignateurSalle(ConteneurDemandes demandes, EntrepotSalles salles,
			DemandesEntrepotSansDoublon demandesArchivees, Notificateur notificateur) {
		this.demandesEnAttente = demandes;
		this.demandesArchivees = demandesArchivees;
		this.salles = salles;
		this.notificateur = notificateur;
		this.messageNotificationFactory = new MessageNotificationFactory();
	}

	public void ajouterDemande(Demande demande) {
		demandesEnAttente.ajouterDemande(demande);
	}

	public void annulerDemande(Demande demandeAnnulee) {
		demandeAnnulee.annulerReservation();
		demandesEnAttente.retirerDemande(demandeAnnulee);
		demandesArchivees.persisterDemande(demandeAnnulee);
	}

	public void assignerDemandeSalleSiContientAuMoins(int nombreDemandes) {
		if (demandesEnAttente.contientAuMoins(nombreDemandes))
			assignerDemandeSalle();
	}

	@Override
	public void run() {
		assignerDemandeSalle();
	}

	private void assignerDemandeSalle() {
		for (Demande demandeCourante : demandesEnAttente) {
			Optional<Salle> salle = salles.obtenirSalleRepondantDemande(demandeCourante);

			if (salle.isPresent()) {
				demandeCourante.placerReservation(salle.get());
				notifierSucces(demandeCourante, salle.get());
				demandesArchivees.persisterDemande(demandeCourante);
			} else {
				notifierEchec(demandeCourante);
			}
		}

		demandesEnAttente.vider();
	}

	private void notifierSucces(Demande demande, Salle salleAssignee) {
		MessageNotification message = this.messageNotificationFactory.genereNotificationSucces(salleAssignee);

		notificateur.notifier(message, demande.getOrganisateur());
		notificateur.notifier(message, demande.getResponsable());
	}

	private void notifierEchec(Demande demande) {
		MessageNotification message = this.messageNotificationFactory.genereNotificationEchec();

		notificateur.notifier(message, demande.getOrganisateur());
		notificateur.notifier(message, demande.getResponsable());
	}

}
