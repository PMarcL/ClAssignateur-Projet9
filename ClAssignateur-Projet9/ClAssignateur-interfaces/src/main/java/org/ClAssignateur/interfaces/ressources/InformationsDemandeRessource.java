package org.ClAssignateur.interfaces.ressources;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.ClAssignateur.services.infosDemandes.DemandeIntrouvableException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import java.util.UUID;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class InformationsDemandeRessource {

	private ServiceInformationsDemande serviceDemande;

	public InformationsDemandeRessource() {
		// TODO enlever code mort
		// DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		// new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		// this.serviceDemande = new
		// ServiceInformationsDemande(demandeEntrepot);
	}

	public InformationsDemandeRessource(ServiceInformationsDemande service) {
		this.serviceDemande = service;
	}

	@GET
	@Path("/{courriel}/{numero_demande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response afficherDemande(@PathParam(value = "courriel") String courriel,
			@PathParam(value = "numeroDemande") String numeroDemande) {
		try {
			UUID idDemande = UUID.fromString(numeroDemande);
			InformationsDemandeDTO infosDemande = this.serviceDemande.getInformationsDemande(courriel, idDemande);
			return Response.ok(infosDemande).build();
		} catch (DemandeIntrouvableException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{courriel}")
	public Response afficherDemandesOrganisateur(@PathParam(value = "courriel") String courrielOrganisateur) {
		try {
			OrganisateurDemandesDTO demandesOrganisateur = this.serviceDemande
					.getDemandesOrganisateur(courrielOrganisateur);
			return Response.ok(demandesOrganisateur).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}