package org.ClAssignateur.testsAcceptationUtilisateur.contexte;

import org.ClAssignateur.contexte.Contexte;
import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;
import org.ClAssignateur.persistance.EnMemoireSallesEntrepot;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.ConteneurDemandesFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.MinuterieFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.NotificationStrategieSilencieuse;

public class ContexteTestAcceptation extends Contexte {

	private SallesEntrepot sallesEntrepot;
	private ConteneurDemandesFake conteneurDemandes;
	private Minuterie minuterie;

	public ContexteTestAcceptation() {
		initialisation();
	}

	private void initialisation() {
		this.sallesEntrepot = new EnMemoireSallesEntrepot();
		this.conteneurDemandes = new ConteneurDemandesFake(new EnMemoireDemandeEntrepot(),
				new EnMemoireDemandeEntrepot());
		this.minuterie = new MinuterieFake();
	}

	@Override
	protected void enregistrerServices() {
		LocalisateurServices.getInstance().enregistrer(SallesEntrepot.class, this.sallesEntrepot);
		LocalisateurServices.getInstance().enregistrer(ConteneurDemandes.class, this.conteneurDemandes);
		LocalisateurServices.getInstance().enregistrer(Minuterie.class, this.minuterie);
		LocalisateurServices.getInstance().enregistrer(DeclencheurAssignateurSalle.class,
				creerDeclencheurAssignateurSalle());
	}

	private DeclencheurAssignateurSalle creerDeclencheurAssignateurSalle() {
		Notificateur notificateur = new Notificateur(new NotificationStrategieSilencieuse());

		AssignateurSalle assignateur = new AssignateurSalle(this.sallesEntrepot, new SelectionSalleOptimaleStrategie());
		return new DeclencheurAssignateurSalle(this.minuterie, this.conteneurDemandes, assignateur, notificateur);
	}

	@Override
	protected void injecterDonnees() {
	}

	public void reinitialiser() {
		initialisation();
		LocalisateurServices.getInstance().remettreAZero();
		appliquer();
	}

}
