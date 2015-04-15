//package org.ClAssignateur.testsAcceptationUtilisateur;
//
//import static java.util.Arrays.*;
//import static org.jbehave.core.io.CodeLocations.*;
//import static org.jbehave.core.reporters.Format.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.ClAssignateur.testsAcceptationUtilisateur.etapes.AssignerEnLotSallesDemandesEtapes;
//import org.ClAssignateur.testsAcceptationUtilisateur.etapes.MaximiserLesPlacesDansSalleEtapes;
//import org.ClAssignateur.testsAcceptationUtilisateur.etapes.OrdonnerDemandesEtapes;
//import org.jbehave.core.configuration.Configuration;
//import org.jbehave.core.configuration.MostUsefulConfiguration;
//import org.jbehave.core.embedder.Embedder;
//import org.jbehave.core.failures.FailingUponPendingStep;
//import org.jbehave.core.io.LoadFromClasspath;
//import org.jbehave.core.io.StoryFinder;
//import org.jbehave.core.junit.JUnitStories;
//import org.jbehave.core.reporters.Format;
//import org.jbehave.core.reporters.StoryReporterBuilder;
//import org.jbehave.core.steps.InjectableStepsFactory;
//import org.jbehave.core.steps.InstanceStepsFactory;
//
//public class RecitsClAssignateur extends JUnitStories {
//
//	private Format[] formats = new Format[] { CONSOLE };
//	private StoryReporterBuilder rapporteurBuilder = new StoryReporterBuilder()
//			.withCodeLocation(codeLocationFromClass(RecitsClAssignateur.class)).withFailureTrace(true)
//			.withFailureTraceCompression(true).withDefaultFormats().withFormats(formats);
//	private Embedder embedder = new Embedder();
//
//	public static void main(String[] args) {
//		new RecitsClAssignateur().embedder
//				.runAsEmbeddables(Arrays.asList(RecitsClAssignateur.class.getCanonicalName()));
//	}
//
//	public RecitsClAssignateur() {
//		useEmbedder(embedder);
//	}
//
//	@Override
//	public Configuration configuration() {
//		return new MostUsefulConfiguration().usePendingStepStrategy(new FailingUponPendingStep())
//				.useStoryLoader(new LoadFromClasspath(getClass().getClassLoader()))
//				.useStoryReporterBuilder(rapporteurBuilder);
//	}
//
//	@Override
//	public InjectableStepsFactory stepsFactory() {
//		return new InstanceStepsFactory(configuration(), new AssignerEnLotSallesDemandesEtapes(),
//				new OrdonnerDemandesEtapes(), new MaximiserLesPlacesDansSalleEtapes());
//	}
//
//	@Override
//	protected List<String> storyPaths() {
//		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(),
//				asList("**/*.story", "*.story"), null);
//	}
//
// }
