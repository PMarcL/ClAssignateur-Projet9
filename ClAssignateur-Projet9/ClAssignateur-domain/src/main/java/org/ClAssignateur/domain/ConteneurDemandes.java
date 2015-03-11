package org.ClAssignateur.domain;

public interface ConteneurDemandes extends Iterable<Demande> {

	public abstract boolean contientAuMoins(int nombreDemandes);

	public abstract void ajouterDemande(Demande demandeAjoutee);

	public abstract void retirerDemande(Demande demandeARetirer);

	public abstract void vider();

}