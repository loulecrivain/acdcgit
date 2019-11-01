package fr.imt.acdcgit.features;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;

import fr.imt.acdcgit.features.RepoStatusFeature.RepoState;
import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class RepoDiffFeatureTest extends TestCase {
	private CredentialsProvider credsProvider;
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<RepoDiffFeature> featureListFactory;
	private RepoDiffFeature firstRepo;

	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new UsernamePasswordCredentialsProvider("foo", "bar");
		featureListFactory = new FeatureListFactory<RepoDiffFeature>(RepoDiffFeature.FACTORY, rFinder.getRepos(),
				credsProvider);
		firstRepo = featureListFactory.get().get(0);
	}

	public void testGetDiff() {
		// testing is done after cloning repo
		// so normally the diff is empty
		try {
			if (firstRepo.getState(true).equals(RepoState.SYNCED)) {
				assertTrue("".contentEquals(firstRepo.getDiff()));
			} else {
				assertTrue(!"".contentEquals(firstRepo.getDiff()));
			}
		} catch (Exception e) {
			fail("Exception " + e.getClass() + " raised while testing");
		}
	}

}
