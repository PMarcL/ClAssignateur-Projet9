package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import javax.xml.ws.http.HTTPException;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import java.util.UUID;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.junit.Test;

public class DemandeRessourceTest {

	private final UUID UN_UUID = UUID.randomUUID();

	private Demande demande;
	private DemandesEntrepot demandeEntrepot;
	private DemandeDTOAssembleur demandeResultatAssembleur;

	private DemandeRessource ressource;

	@Before
	public void initialement() {
		demande = mock(Demande.class);
		demandeEntrepot = mock(DemandesEntrepot.class);
		demandeResultatAssembleur = mock(DemandeDTOAssembleur.class);
		ressource = new DemandeRessource(demandeEntrepot, demandeResultatAssembleur);
	}

	@Test
	public void etantDonneDemandeRessourceQuelconqueQuandAfficherDemandeAlorsObtenirDemandeSelonIdEstAppeler() {
		faireEnSorteQueDemandeExiste();
		ressource.afficherDemande(UN_UUID.toString());
		verify(demandeEntrepot).obtenirDemandeSelonId(eq(UN_UUID));
	}

	@Test
	public void etantDonneDemandeExisteQuandAfficherDemandeAlorsAssemblerEstAppeler() {
		faireEnSorteQueDemandeExiste();
		ressource.afficherDemande(UN_UUID.toString());
		verify(demandeResultatAssembleur).assemblerDemandeDTO(demande);
	}

	@Test(expected = HTTPException.class)
	public void etantDonneDemandeNExistePasQuandAfficherDemandeAlorsHttp404ExceptionEstLancer() {
		Optional<Demande> demandeRetourne = Optional.empty();
		given(demandeEntrepot.obtenirDemandeSelonId(eq(UN_UUID))).willReturn(demandeRetourne);
		ressource.afficherDemande(UN_UUID.toString());
	}

	private void faireEnSorteQueDemandeExiste() {
		Optional<Demande> demandeRetourne = Optional.of(demande);
		given(demandeEntrepot.obtenirDemandeSelonId(eq(UN_UUID))).willReturn(demandeRetourne);
	}
}
