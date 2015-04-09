package org.ClAssignateur.interfaces;

import java.net.URI;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.contexts.DemoDemandeEntrepotRemplisseur;
import org.ClAssignateur.services.DemandePasPresenteException;
import org.ClAssignateur.persistences.EnMemoireDemandeEntrepot;
import org.ClAssignateur.services.ServiceDemande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import java.util.List;
import java.util.UUID;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class AjoutRessource {

	private ServiceDemande serviceDemande;
	private ReservationDemandeAssembleur reservationDemandeAssembleur;

	public AjoutRessource() {
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		this.reservationDemandeAssembleur = new ReservationDemandeAssembleur();
		this.serviceDemande = new ServiceDemande(demandeEntrepot);
	}

	public AjoutRessource(ServiceDemande service, ReservationDemandeAssembleur reservationDemandeAssembleur) {
		this.serviceDemande = service;
		this.reservationDemandeAssembleur = reservationDemandeAssembleur;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response ajouterDemande(@PathParam(value = "nombrePersonne") int nombrePersonne, @PathParam(
			value = "courrielOrganisateur") String courrielOrganisateur, @PathParam(value = "priorite") int priorite,
			@PathParam(value = "participantsCourriels") List<String> participantsCourriels) {
		try {
			ReservationDemandeDTO demandeDTO = new ReservationDemandeDTO();
			demandeDTO.nombrePersonnes = nombrePersonne;
			demandeDTO.courrielOrganisateur = courrielOrganisateur;
			demandeDTO.priorite = priorite;
			demandeDTO.participantsCourriels = participantsCourriels;

			Demande demande = reservationDemandeAssembleur.assemblerDemande(demandeDTO);

			serviceDemande.ajouterDemande(demande);

			UUID idDemande = demande.getID();
			URI emplacement = new URI("/demandes/" + courrielOrganisateur + "/" + idDemande.toString());

			return Response.created(emplacement).build();
		} catch (DemandePasPresenteException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}