package fr.imt.acdcgit.features;

import java.io.IOException;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class FeatureListFactoryTest extends TestCase {
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<BaseFeature> featureListFactory;
	private CredentialsProvider credsProvider;
	
	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new UsernamePasswordCredentialsProvider("foo","bar");
		featureListFactory = new FeatureListFactory<BaseFeature>(BaseFeature.FACTORY, rFinder.getRepos(),credsProvider);
	}

	public void testGet() {
			// testing if lazy-loading does the job
			assertEquals(featureListFactory.get(), featureListFactory.get());
			// init feature with one repo ?
			assertEquals(featureListFactory.get().size(), 1); // normally there is only one git repo in ".." : ours
			// test if repo correctly initialized
			assertNotNull(featureListFactory.get().get(0).credsProvider);
			assertNotNull(featureListFactory.get().get(0).repo);
	}
}
