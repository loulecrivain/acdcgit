package fr.imt.acdcgit.features;

import java.io.IOException;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class FeatureListFactoryTest extends TestCase {
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<BaseFeature> featureListFactory;

	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		featureListFactory = new FeatureListFactory<BaseFeature>(BaseFeature.FACTORY, rFinder.getRepos());
	}

	public void testGet() {
		try {
			// testing if lazy-loading does the job
			assertEquals(featureListFactory.get(), featureListFactory.get());
			// init feature with one repo ?
			assertEquals(featureListFactory.get().size(), 1); // normally there is only one git repo in ".." : ours
		} catch (IOException ioe) {
			fail("Error while accessing test repo !");
		}

	}

}
