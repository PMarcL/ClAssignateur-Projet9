package org.ClAssignateur.domain;

import java.util.Timer;

public class ServiceReservationSalle {

	private final int FREQUENCE_PAR_DEFAUT = 5;
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int MILLISECONDES_PAR_MINUTE = 60000;

	private AssignateurSalle assignateurSalle;
	private Timer minuterie;
	private int frequence;
	private int limiteDemandes;

	public ServiceReservationSalle(Timer minuterie, AssignateurSalle assignateurSalle) {
		this.assignateurSalle = assignateurSalle;
		this.minuterie = minuterie;
		this.frequence = FREQUENCE_PAR_DEFAUT;
		this.limiteDemandes = LIMITE_DEMANDES_PAR_DEFAUT;

		long delaiMilliseconde = delaiEnMilisecondes(frequence);
		this.minuterie.scheduleAtFixedRate(this.assignateurSalle, delaiMilliseconde, delaiMilliseconde);
	}

	public void setFrequence(int nbMinutes) {
		minuterie.cancel();

		long delaiMilisecondes = delaiEnMilisecondes(nbMinutes);
		minuterie.scheduleAtFixedRate(assignateurSalle, delaiMilisecondes, delaiMilisecondes);
	}

	private long delaiEnMilisecondes(int delaiEnMinutes) {
		return delaiEnMinutes * MILLISECONDES_PAR_MINUTE;
	}

	public void setLimiteDemandesAvantAssignation(int limite) {
		limiteDemandes = limite;
		assignerSiNecessaire();
	}

	public void annulerDemande(Demande demandeAAnnuler) {
		assignateurSalle.annulerDemande(demandeAAnnuler);
	}

	public void ajouterDemande(Demande nouvelleDemande) {
		assignateurSalle.ajouterDemande(nouvelleDemande);
		assignerSiNecessaire();
	}

	private void assignerSiNecessaire() {
		assignateurSalle.assignerDemandeSalleSiContientAuMoins(limiteDemandes);
	}

}
