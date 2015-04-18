package org.ClAssignateur.interfaces;

import org.ClAssignateur.contexte.ContexteProduction;
import org.ClAssignateur.contexte.developpement.ContexteDeveloppement;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

public class RESTMain {

	public static void main(String[] args) throws Exception {
		new ContexteProduction().appliquer();
		new RESTMain().demarrerServeur();
	}

	private void demarrerServeur() throws Exception {
		final int PORT_SERVEUR = 8080;

		Server server = new Server(PORT_SERVEUR);
		ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
		configurerJersey(servletContextHandler);
		server.start();
		server.join();
	}

	private void configurerJersey(ServletContextHandler servletContextHandler) {
		ServletContainer container = new ServletContainer(new ResourceConfig().packages("org.ClAssignateur.interfaces")
				.register(JacksonFeature.class));
		ServletHolder jerseyServletHolder = new ServletHolder(container);
		servletContextHandler.addServlet(jerseyServletHolder, "/*");
	}

}
