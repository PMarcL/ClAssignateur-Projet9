package org.ClAssignateur.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.UUID;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import javax.xml.ws.http.HTTPException;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class DemandeRessource {

	private DemandesEntrepot demandeEntrepot;
	private DemandeResultatAssembleur assembleur;

	public DemandeRessource(DemandesEntrepot demandeEntrepot, DemandeResultatAssembleur assembleur) {
		this.demandeEntrepot = demandeEntrepot;
		this.assembleur = assembleur;
	}

	@POST
	@Path("/afficher")
	@Consumes(MediaType.APPLICATION_JSON)
	public String afficherDemande(String id) {
		UUID idDemande = UUID.fromString(id);
		Optional<Demande> demande = demandeEntrepot.obtenirDemandeSelonId(idDemande);
		if (demande.isPresent()) {
			DemandeResultat demandeResultat = assembleur.assemblerDemande(demande.get());
			return creerJsonAPartirDe(demandeResultat);
		} else {
			throw new HTTPException(404);
		}
	}

	private String creerJsonAPartirDe(DemandeResultat demandeResultat) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(demandeResultat);
		} catch (JsonProcessingException e) {
			throw new HTTPException(500);
		}
		return json;
	}
}