package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import java.util.LinkedList;
import java.util.Queue;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;

public class ConteneurDemandesFake extends ConteneurDemandes {

	private Queue<Demande> demandesArchivees;

	public ConteneurDemandesFake(DemandesEntrepot demandesEnAttente, DemandesEntrepot demandesArchivees) {
		super(demandesEnAttente, demandesArchivees);
		this.demandesArchivees = new LinkedList<>();
	}

	@Override
	public void archiverDemande(Demande demande) {
		super.archiverDemande(demande);
		this.demandesArchivees.add(demande);
	}

	public Demande retirerDemandeTraitee() {
		return this.demandesArchivees.poll();
	}

	public boolean demandesEstArchivee(Demande demande) {
		return this.demandesArchivees.contains(demande);
	}
}
