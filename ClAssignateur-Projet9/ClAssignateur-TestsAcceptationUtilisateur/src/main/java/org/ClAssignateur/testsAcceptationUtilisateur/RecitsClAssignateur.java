package org.ClAssignateur.testsAcceptationUtilisateur;

import static java.util.Arrays.*;
import static org.jbehave.core.io.CodeLocations.*;
import static org.jbehave.core.reporters.Format.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.ClAssignateur.testsAcceptationUtilisateur.etapes.AssignerEnLotSallesDemandesEtapes;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

public class RecitsClAssignateur extends JUnitStories {

	private Format[] formats = new Format[] { CONSOLE };
	private StoryReporterBuilder rapporteurBuilder = new StoryReporterBuilder()
			.withKeywords(new LocalizedKeywords(Locale.FRENCH))
			.withCodeLocation(codeLocationFromClass(RecitsClAssignateur.class)).withFailureTrace(true)
			.withFailureTraceCompression(true).withDefaultFormats().withFormats(formats)
			.withCrossReference(new CrossReference());
	private Embedder embedder = new Embedder();

	public static void main(String[] args) {
		new RecitsClAssignateur().embedder
				.runAsEmbeddables(Arrays.asList(RecitsClAssignateur.class.getCanonicalName()));
	}

	public RecitsClAssignateur() {
		useEmbedder(embedder);
	}

	@Override
	public Configuration configuration() {
		LocalizedKeywords motCles = new LocalizedKeywords(new Locale("fr"));
		return new MostUsefulConfiguration().useKeywords(motCles).usePendingStepStrategy(new FailingUponPendingStep())
				.useStoryParser(new RegexStoryParser(motCles))
				.useStoryLoader(new LoadFromClasspath(getClass().getClassLoader()))
				.useStoryReporterBuilder(rapporteurBuilder);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new AssignerEnLotSallesDemandesEtapes());
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(),
				asList("**/*.histoire", "*.histoire"), null);
	}

}
