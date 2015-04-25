package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import java.util.LinkedList;
import java.util.Queue;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;

public class ConteneurDemandesFake extends ConteneurDemandes {

	private Queue<Demande> demandesTraitees;

	public ConteneurDemandesFake(DemandesEntrepot demandesEnAttente, DemandesEntrepot demandesArchivees) {
		super(demandesEnAttente, demandesArchivees);
		this.demandesTraitees = new LinkedList<>();
	}

	@Override
	public void archiverDemande(Demande demande) {
		super.archiverDemande(demande);
		this.demandesTraitees.add(demande);
	}

	public Demande retirerDemandeTraitee() {
		return this.demandesTraitees.poll();
	}
}
