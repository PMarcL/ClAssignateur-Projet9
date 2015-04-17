package org.ClAssignateur.interfaces.ressources;

import javax.ws.rs.Consumes;

import java.net.URI;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.ClAssignateur.domaine.contacts.AdresseCourrielInvalideException;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.localisateur.ServiceIntrouvableException;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;

import java.util.UUID;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
public class AjoutDemandeRessource {

	private ServiceReservationSalle serviceReservation;

	public AjoutDemandeRessource() {
		try{
		this.serviceReservation = LocalisateurServices.getInstance().obtenir(ServiceReservationSalle.class);
		}catch (ServiceIntrouvableException ex){
			this.serviceReservation = new ServiceReservationSalle();
			LocalisateurServices.getInstance().enregistrer(ServiceReservationSalle.class, this.serviceReservation);
		}
	}

	public AjoutDemandeRessource(ServiceReservationSalle service) {
		this.serviceReservation = service;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response ajouterDemande(ReservationDemandeDTO demandeDTO) {
		try {
			UUID idDemande = serviceReservation.ajouterDemande(demandeDTO);
			URI emplacement = new URI("/demandes/" + demandeDTO.courrielOrganisateur + "/" + idDemande.toString());

			return Response.created(emplacement).build();
		} catch (AdresseCourrielInvalideException ex) {
			return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}