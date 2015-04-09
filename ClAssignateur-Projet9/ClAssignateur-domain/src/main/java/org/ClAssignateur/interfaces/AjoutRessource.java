package org.ClAssignateur.interfaces;

import javax.ws.rs.Consumes;

import java.net.URI;
import javax.ws.rs.POST;
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
import java.util.UUID;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
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
	public Response ajouterDemande(ReservationDemandeDTO demandeDTO) {
		try {
			Demande demande = reservationDemandeAssembleur.assemblerDemande(demandeDTO);

			serviceDemande.ajouterDemande(demande);

			UUID idDemande = demande.getID();
			URI emplacement = new URI("/demandes/" + demandeDTO.courrielOrganisateur + "/" + idDemande.toString());

			return Response.created(emplacement).build();
		} catch (DemandePasPresenteException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}