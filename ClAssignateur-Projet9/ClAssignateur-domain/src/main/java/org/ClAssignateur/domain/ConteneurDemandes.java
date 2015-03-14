package org.ClAssignateur.domain;

public interface ConteneurDemandes extends Iterable<Demande> {

	public abstract int taille();

	public abstract boolean contientAuMoins(int nombreDemandes);

	public abstract void ajouterDemande(Demande demandeAjoutee);

	public abstract void retirerDemande(Demande demandeARetirer);

	public abstract boolean contientDemande(Demande demande);

	public abstract void vider();

}