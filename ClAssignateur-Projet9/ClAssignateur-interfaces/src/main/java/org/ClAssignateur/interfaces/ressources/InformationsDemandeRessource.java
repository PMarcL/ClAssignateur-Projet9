package org.ClAssignateur.interfaces.ressources;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.contexte.DemoDemandeEntrepotRemplisseur;
import org.ClAssignateur.services.infosDemandes.DemandePasPresenteException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireDemandeEntrepot;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.ClAssignateur.interfaces.dto.InformationsDemandeDTO;
import org.ClAssignateur.interfaces.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.interfaces.dto.assembleur.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.interfaces.dto.assembleur.OrganisateurDemandesDTOAssembleur;

import java.util.List;
import java.util.UUID;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class InformationsDemandeRessource {

	private ServiceInformationsDemande serviceDemande;
	private InformationsDemandeDTOAssembleur infosDemandesAssembleur;
	private OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur;

	public InformationsDemandeRessource() {
		DemandesEntrepot demandeEntrepot = new EnMemoireDemandeEntrepot();
		new DemoDemandeEntrepotRemplisseur().remplir(demandeEntrepot);
		this.infosDemandesAssembleur = new InformationsDemandeDTOAssembleur();
		this.organisateurDemandesAssembleur = new OrganisateurDemandesDTOAssembleur(infosDemandesAssembleur);
		this.serviceDemande = new ServiceInformationsDemande(demandeEntrepot);
	}

	public InformationsDemandeRessource(ServiceInformationsDemande service,
			InformationsDemandeDTOAssembleur infosDemandesAssembleur,
			OrganisateurDemandesDTOAssembleur orgDemandesAssembleur) {
		this.serviceDemande = service;
		this.infosDemandesAssembleur = infosDemandesAssembleur;
		this.organisateurDemandesAssembleur = orgDemandesAssembleur;
	}

	@GET
	@Path("/{courriel}/{numero_demande}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response afficherDemande(@PathParam(value = "courriel") String courriel,
			@PathParam(value = "numeroDemande") String numeroDemande) {
		try {
			UUID idDemande = UUID.fromString(numeroDemande);
			Demande demande = this.serviceDemande.getInfoDemandePourCourrielEtId(courriel, idDemande);
			InformationsDemandeDTO demandeDTO = infosDemandesAssembleur.assemblerInformationsDemandeDTO(demande);
			return Response.ok(demandeDTO).build();
		} catch (DemandePasPresenteException ex) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{courriel}")
	public Response afficherDemandesPourOrganisateur(@PathParam(value = "courriel") String courrielOrganisateur) {
		try {
			List<Demande> demandesPourCourriel = this.serviceDemande.getDemandesPourCourriel(courrielOrganisateur);
			OrganisateurDemandesDTO demandes = organisateurDemandesAssembleur
					.assemblerOrganisateurDemandesDTO(demandesPourCourriel);
			return Response.ok(demandes).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}

}