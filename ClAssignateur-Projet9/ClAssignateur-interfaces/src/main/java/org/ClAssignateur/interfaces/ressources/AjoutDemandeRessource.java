package org.ClAssignateur.interfaces.ressources;

import javax.ws.rs.Consumes;

import java.net.URI;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.domaine.groupe.courriel.AdresseCourrielInvalideException;

import java.util.UUID;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
public class AjoutDemandeRessource {

	private ServiceReservationSalle serviceDemande;

	public AjoutDemandeRessource() {
		// TODO revoir ici une fois contexte établi pour aller chercher service
		// à partir du service locator
		// TODO enlever référence vers JBehave dans dans pom.xml
		// DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		// new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		// this.reservationDemandeAssembleur = new
		// ReservationDemandeDTOAssembleur();
		// this.serviceDemande = new ServiceReservationSalle(demandeEntrepot);
	}

	public AjoutDemandeRessource(ServiceReservationSalle service) {
		this.serviceDemande = service;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response ajouterDemande(ReservationDemandeDTO demandeDTO) {
		try {
			UUID idDemande = serviceDemande.ajouterDemande(demandeDTO);
			URI emplacement = new URI("/demandes/" + demandeDTO.courrielOrganisateur + "/" + idDemande.toString());

			return Response.created(emplacement).build();
		} catch (AdresseCourrielInvalideException ex) {
			String message = "Le courriel " + demandeDTO.courrielOrganisateur + " n'est pas valide";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}