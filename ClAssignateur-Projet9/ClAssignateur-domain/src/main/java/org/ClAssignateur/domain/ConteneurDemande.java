package org.ClAssignateur.domain;

public interface ConteneurDemande {

	public abstract int taille();

	public abstract boolean estVide();

	public abstract void ajouter(Demande demandeAjoutee);

	public abstract void vider();

	public abstract Demande retirer() throws Exception;

}