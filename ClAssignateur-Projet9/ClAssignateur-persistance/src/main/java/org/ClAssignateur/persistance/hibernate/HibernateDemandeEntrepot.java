package org.ClAssignateur.persistance.hibernate;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;

import java.math.BigInteger;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HibernateDemandeEntrepot implements DemandesEntrepot {

	private EntityManagerProvider entityManagerProvider;

	public HibernateDemandeEntrepot() {
		entityManagerProvider = new EntityManagerProvider();
	}

	public HibernateDemandeEntrepot(EntityManagerProvider entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	public void persisterDemande(Demande demande) {
		EntityManager em = entityManagerProvider.getEntityManager();
		if (em.find(Demande.class, demande.getID()) == null) {
			em.getTransaction().begin();
			em.persist(demande);
			em.getTransaction().commit();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Demande> obtenirDemandes() {
		EntityManager em = entityManagerProvider.getEntityManager();
		String requete = "Select entity from Demande entity";
		return (List<Demande>) em.createQuery(String.format(requete)).getResultList();
	}

	@Override
	public Optional<Demande> obtenirDemandeSelonId(UUID id) {
		EntityManager em = entityManagerProvider.getEntityManager();
		Demande demandeTrouve = em.find(Demande.class, id);
		return creeOptionalDemande(demandeTrouve);
	}

	private Optional<Demande> creeOptionalDemande(Demande demandeTrouve) {
		if (demandeTrouve == null) {
			return Optional.empty();
		} else {
			return Optional.of(demandeTrouve);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<Demande> obtenirDemandeSelonTitre(String titre) {
		EntityManager em = entityManagerProvider.getEntityManager();
		String requete = "Select entity from Demande entity where titre = '%s'";
		List<Demande> listeDemandes = (List<Demande>) em.createQuery(String.format(requete, titre)).getResultList();
		return creeOptionalDemande(listeDemandes);
	}

	private Optional<Demande> creeOptionalDemande(List<Demande> listeDemandes) {
		if (listeDemandes.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(listeDemandes.get(0));
		}
	}

	@Override
	public int taille() {
		EntityManager em = entityManagerProvider.getEntityManager();
		String requeteCompte = "Select Count(*) from Demande";
		Query requete = em.createNativeQuery(requeteCompte);
		return ((BigInteger) requete.getSingleResult()).intValue();
	}

	@Override
	public void retirerDemande(Demande demande) {
		EntityManager em = entityManagerProvider.getEntityManager();
		if (em.find(Demande.class, demande.getID()) != null) {
			em.getTransaction().begin();
			em.remove(demande);
			em.getTransaction().commit();
		}
	}

	@Override
	public void vider() {
		EntityManager em = entityManagerProvider.getEntityManager();
		String requete = "Delete From Demande";
		em.getTransaction().begin();
		em.createQuery(requete).executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public List<Demande> obtenirDemandesSelonCourrielOrganisateur(String courrielOrganisateur) {
		// TODO Auto-generated method stub
		return null;
	}

}
