package fr.imt.acdcgit.features;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;

import java.io.IOException;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import fr.imt.acdcgit.features.RepoListFeature;
import junit.framework.TestCase;

public class RepoListFeatureTest extends TestCase {
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<RepoListFeature> featureListFactory;
	private CredentialsProvider credsProvider;
	
	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new UsernamePasswordCredentialsProvider("foo", "bar");
		featureListFactory = new FeatureListFactory<RepoListFeature>(RepoListFeature.FACTORY, rFinder.getRepos(),credsProvider);
	}

	public void testGetRelativeDir() {
		try {
			assertEquals(featureListFactory.get().size(),1);
			// TODO correct this test
			assertTrue(featureListFactory.get().get(0).getRelativeDir().contains(".git"));
		} catch(IOException ioe) {
			fail("unable to access repo !");
		}
	}
}
