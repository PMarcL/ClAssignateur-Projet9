package org.ClAssignateur.domain.demandes;

public interface ConteneurDemandes extends Iterable<Demande> {

	public int taille();

	public boolean contientAuMoins(int nombreDemandes);

	public void ajouterDemande(Demande demandeAjoutee);

	public void retirerDemande(Demande demandeARetirer);

	public boolean contientDemande(Demande demande);

	public void vider();

}