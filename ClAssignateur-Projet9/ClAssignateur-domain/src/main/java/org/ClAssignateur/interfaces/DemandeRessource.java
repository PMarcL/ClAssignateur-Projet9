package org.ClAssignateur.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import org.ClAssignateur.contexts.DemoDemandeEntrepotRemplisseur;
import org.ClAssignateur.services.DemandePasPresenteException;
import org.ClAssignateur.persistences.EnMemoireDemandeEntrepot;
import org.ClAssignateur.services.ServiceDemande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import java.util.UUID;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class DemandeRessource {

	private ServiceDemande serviceDemande;

	public DemandeRessource() {
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
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

	@GET
	@Path("/{courriel}")
	public Response obtenirDemandesPourCourriel(@PathParam(value = "courriel") String courriel) {
		return Response.ok().build();
	}

}