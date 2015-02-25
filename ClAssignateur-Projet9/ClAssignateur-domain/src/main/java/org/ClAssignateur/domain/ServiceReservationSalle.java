package org.ClAssignateur.domain;

import java.util.Timer;

public class ServiceReservationSalle {

	private final int FREQUENCE_PAR_DEFAUT = 5;
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int MILLISECONDES_PAR_MINUTE = 60000;

	private AssignateurSalle assignateurSalle;
	private ConteneurDemandes demandes;
	private EntrepotSalles salles;
	private Timer minuterie;
	private int frequence;
	private int limteDemandes;

	public ServiceReservationSalle(ConteneurDemandes demandes, EntrepotSalles salles, Timer minuterie,
			AssignateurSalle assignateurSalle) {
		this.assignateurSalle = assignateurSalle;
		this.demandes = demandes;
		this.salles = salles;
		this.minuterie = minuterie;
		this.frequence = FREQUENCE_PAR_DEFAUT;
		this.limteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;

		long delaiMilliseconde = delaiEnMilisecondes(frequence);
		this.minuterie.scheduleAtFixedRate(this.assignateurSalle, delaiMilliseconde, delaiMilliseconde);
	}

	private long delaiEnMilisecondes(int delaiEnMinutes) {
		return delaiEnMinutes * MILLISECONDES_PAR_MINUTE;
	}

	public void setFrequence(int nbMinutes) {
		minuterie.cancel();

		long delaiMilisecondes = delaiEnMilisecondes(nbMinutes);
		minuterie.scheduleAtFixedRate(assignateurSalle, delaiMilisecondes, delaiMilisecondes);
	}

	// public void setLimite(int limite) {
	// this.assignateurSalle.setLimite(limite);
	// }

	public void ajouterDemande(Demande nouvelleDemande) {
		demandes.ajouter(nouvelleDemande);
	}

	// public void arreterService() {
	// serviceEnFonction = false;
	// }

}
