package org.ClAssignateur.domain;

import java.util.Optional;

import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandes;
	private EntrepotSalles salles;
	private Notificateur notificateur;
	private MessageNotificationFactory messageNotificationFactory;

	public AssignateurSalle(ConteneurDemandes demandes, EntrepotSalles salles, Notificateur notificateur) {
		this.demandes = demandes;
		this.salles = salles;
		this.notificateur = notificateur;
		this.messageNotificationFactory = new MessageNotificationFactory();
	}

	public void ajouterDemande(Demande demande) {
		demandes.ajouterDemande(demande);
	}

	public void annulerDemande(Demande demandeAAnnuler) {
		demandes.retirerDemande(demandeAAnnuler);
	}

	public void assignerDemandeSalleSiContientAuMoins(int nombreDemandes) {
		if (demandes.contientAuMoins(nombreDemandes))
			assignerDemandeSalle();
	}

	@Override
	public void run() {
		assignerDemandeSalle();
	}

	private void assignerDemandeSalle() {
		for (Demande demandeCourante : demandes) {
			Optional<Salle> salle = salles.obtenirSalleRepondantDemande(demandeCourante);

			if (salle.isPresent()) {
				demandeCourante.placerReservation(salle.get());
				notifierSucces(demandeCourante, salle.get());
			} else {
				notifierEchec(demandeCourante);
			}
		}

		demandes.vider();
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
