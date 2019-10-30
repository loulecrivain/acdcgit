package fr.imt.acdcgit.features;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;

import java.io.IOException;

import fr.imt.acdcgit.features.RepoListFeature;
import junit.framework.TestCase;

public class RepoListFeatureTest extends TestCase {
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<RepoListFeature> featureListFactory;

	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		featureListFactory = new FeatureListFactory<RepoListFeature>(RepoListFeature.FACTORY, rFinder.getRepos());
	}

	public void testGetRelativeDir() {
		try {
			assertEquals(featureListFactory.get().size(),1);
			// TODO correct this test
			assertEquals(featureListFactory.get().get(0).getRelativeDir(), "..");
		} catch(IOException ioe) {
			fail("unable to access repo !");
		}
	}
}
