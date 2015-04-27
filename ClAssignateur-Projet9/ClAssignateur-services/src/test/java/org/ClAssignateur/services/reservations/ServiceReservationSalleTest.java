package org.ClAssignateur.services.reservations;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ServiceReservationSalleTest {

	private final Minute FREQUENCE_3_MINUTES = new Minute(3);
	private final int LIMITE_5_DEMANDES = 5;
	private final String TITRE_DEMANDE_ANNULEE = "Demande Annulee";
	private final UUID ID_DEMANDE = UUID.randomUUID();

	private ReservationDemandeDTO dto;
	private DeclencheurAssignateurSalle declencheur;
	private Demande demandeAjoutee;
	private ReservationDemandeDTOAssembleur assembleur;

	private ServiceReservationSalle serviceReservation;

	@Before
	public void creerServiceReservation() {
		declencheur = mock(DeclencheurAssignateurSalle.class);
		demandeAjoutee = mock(Demande.class);
		assembleur = mock(ReservationDemandeDTOAssembleur.class);

		given(assembleur.assemblerDemande(dto)).willReturn(demandeAjoutee);

		serviceReservation = new ServiceReservationSalle(declencheur, assembleur);
	}

	@Test
	public void quandSetFrequenceDevraitDeleguerAuDeclencheur() {
		serviceReservation.setFrequence(FREQUENCE_3_MINUTES);
		verify(declencheur).setFrequence(FREQUENCE_3_MINUTES);
	}

	@Test
	public void quandAjouterDemandeDevraitCreerDemandeAvecAssembleur() {
		serviceReservation.ajouterDemande(dto);
		verify(assembleur).assemblerDemande(dto);
	}

	@Test
	public void quandAjouterDemandeDevraitEnvoyerAuDeclencheur() {
		serviceReservation.ajouterDemande(dto);
		verify(declencheur).ajouterDemande(demandeAjoutee);
	}

	@Test
	public void quandAjouterDemandeRenvoieIDDemande() {
		given(demandeAjoutee.getID()).willReturn(ID_DEMANDE);
		UUID idRecu = serviceReservation.ajouterDemande(dto);
		assertEquals(ID_DEMANDE, idRecu);
	}

	@Test
	public void quandAnnulerDemandeDevraitDeleguerAuDeclencheur() {
		serviceReservation.annulerDemande(TITRE_DEMANDE_ANNULEE);
		verify(declencheur).annulerDemande(TITRE_DEMANDE_ANNULEE);
	}

	@Test
	public void quandAnnulerDemandeAvecIDDevraitDeleguerAuDeclencheur() {
		serviceReservation.annulerDemande(ID_DEMANDE);
		verify(declencheur).annulerDemande(ID_DEMANDE);
	}

	@Test
	public void quandSetLimiteDemandesAvantAssignationDevraitDeleguerAuDeclencheur() {
		serviceReservation.setLimiteDemandesAvantAssignation(LIMITE_5_DEMANDES);
		verify(declencheur).setLimiteDemandesAvantAssignation(LIMITE_5_DEMANDES);
	}

}
