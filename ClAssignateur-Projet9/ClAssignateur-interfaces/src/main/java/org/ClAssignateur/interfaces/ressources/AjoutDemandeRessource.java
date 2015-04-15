package org.ClAssignateur.interfaces.ressources;

import javax.ws.rs.Consumes;

import java.net.URI;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.ClAssignateur.contexte.DemoDemandeEntrepotRemplisseur;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireDemandeEntrepot;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import org.ClAssignateur.domaine.groupe.courriel.AdresseCourrielInvalideException;
import org.ClAssignateur.interfaces.dto.ReservationDemandeDTO;
import org.ClAssignateur.interfaces.dto.assembleur.ReservationDemandeDTOAssembleur;

import java.util.UUID;

@Path("/demandes")
@Consumes(MediaType.APPLICATION_JSON)
public class AjoutDemandeRessource {

	private ServiceInformationsDemande serviceDemande;
	private ReservationDemandeDTOAssembleur reservationDemandeAssembleur;

	public AjoutDemandeRessource() {
		// TODO revoir ici une fois contexte établi pour aller chercher service
		// à partir du service locator
		// TODO enlever référence vers JBehave dans dans pom.xml
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		this.reservationDemandeAssembleur = new ReservationDemandeDTOAssembleur();
		this.serviceDemande = new ServiceInformationsDemande(demandeEntrepot);
	}

	public AjoutDemandeRessource(ServiceInformationsDemande service,
			ReservationDemandeDTOAssembleur reservationDemandeAssembleur) {
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
		} catch (AdresseCourrielInvalideException ex) {
			String message = "Le courriel " + demandeDTO.courrielOrganisateur + " n'est pas valide";
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}