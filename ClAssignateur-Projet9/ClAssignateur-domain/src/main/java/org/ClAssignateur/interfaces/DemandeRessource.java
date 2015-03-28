package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.groupe.Groupe;

import org.ClAssignateur.domain.demandes.Demande;
import java.util.ArrayList;
import org.ClAssignateur.domain.groupe.Employe;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import org.ClAssignateur.services.DemandePasPresenteException;
import javax.ws.rs.core.Response;
import org.ClAssignateur.persistences.EnMemoireDemandeEntrepot;
import org.ClAssignateur.services.ServiceDemande;
import java.util.UUID;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class DemandeRessource {

	private ServiceDemande serviceDemande;

	public DemandeRessource() {
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		DemandeDTOAssembleur demandeDTOAssembleur = new DemandeDTOAssembleur();
		this.serviceDemande = new ServiceDemande(demandeEntrepot, demandeDTOAssembleur);
	}

	public DemandeRessource(ServiceDemande service) {
		this.serviceDemande = service;
	}

	@GET
	@Path("/afficher/{courriel}/{numero_demande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response afficherDemande(@PathParam(value = "courriel") String courriel,
			@PathParam(value = "numero_demande") String numero_demande) {
		try {
			UUID idDemande = UUID.fromString(numero_demande);
			DemandeDTO demandeDTO = this.serviceDemande.getInfoDemandePourCourrielEtId(courriel, idDemande);
			return Response.ok(demandeDTO).build();
		} catch (DemandePasPresenteException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}