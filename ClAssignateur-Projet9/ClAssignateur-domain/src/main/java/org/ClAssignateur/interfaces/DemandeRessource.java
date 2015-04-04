package org.ClAssignateur.interfaces;

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
public class DemandeRessource {

	private ServiceDemande serviceDemande;
	private InformationsDemandeDTOAssembleur assembleur;

	public DemandeRessource() {
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		this.assembleur = new InformationsDemandeDTOAssembleur();
		this.serviceDemande = new ServiceDemande(demandeEntrepot);
	}

	public DemandeRessource(ServiceDemande service, InformationsDemandeDTOAssembleur assembleur) {
		this.serviceDemande = service;
		this.assembleur = assembleur;
	}

	@GET
	@Path("/{courriel}/{numero_demande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response afficherUneDemande(@PathParam(value = "courriel") String courriel,
			@PathParam(value = "numeroDemande") String numeroDemande) {
		try {
			UUID idDemande = UUID.fromString(numeroDemande);
			Demande demande = this.serviceDemande.getInfoDemandePourCourrielEtId(courriel, idDemande);
			InformationsDemandeDTO demandeDTO = assembleur.assemblerInformationsDemandeDTO(demande);
			return Response.ok(demandeDTO).build();
		} catch (DemandePasPresenteException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

	// @POST
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response ajouterDemande(@PathParam(value = "nombrePersonne") int
	// nombrePersonne,
	// @PathParam(value = "courrielOrganisateur") String courrielOrganisateur,
	// @PathParam(value = "priorite") int priorite) {
	// try {
	// InformationsDemandeDTO demandeDTO = new InformationsDemandeDTO();
	// demandeDTO.courrielOrganisateur = courrielOrganisateur;
	// demandeDTO.priorite = priorite;
	// demandeDTO.nombrePersonne = nombrePersonne;
	//
	// return Response.ok(demandeDTO).build();
	// } catch (DemandePasPresenteException ex) {
	// return Response.status(Status.NOT_FOUND).build();
	// } catch (Exception ex) {
	// return Response.serverError().build();
	// }
	// }

	@GET
	@Path("/{courriel}")
	public Response afficherDemandesPourOrganisateur(@PathParam(value = "courriel") String courrielOrganisateur) {
		try {
			List<Demande> demandesPourCourriel = this.serviceDemande.getDemandesPourCourriel(courrielOrganisateur);
			OrganisateurDemandesDTO demandes = assembleur.assemblerOrganisateurDemandesDTO(demandesPourCourriel);
			return Response.ok(demandes).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}