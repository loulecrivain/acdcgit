package fr.imt.acdcgit.features;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class RepoStatusFeatureTest extends TestCase {
	private CredentialsProvider credsProvider;
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<RepoStatusFeature> featureListFactory;
	private RepoStatusFeature firstRepo;
	
	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new UsernamePasswordCredentialsProvider("foo","bar");
		featureListFactory = new FeatureListFactory<RepoStatusFeature>(RepoStatusFeature.FACTORY, rFinder.getRepos(),credsProvider);
		firstRepo = featureListFactory.get().get(0);
	}

	public void testGetState() {
		// testing is done after cloning repository
		// or when state is clean
		// so typically we should have a SYNCED state here
		try {
			assertTrue(firstRepo.getState().equals(RepoStatusFeature.RepoState.SYNCED));
		} catch (Exception e) {
			fail("Exception raised while tetsing");
		}
	}
	
	public void testGetMainBranchStr() {
		try {
			assertTrue(firstRepo.getMainBranchStr().contains("master"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Exception raised while testing");
		}
	}
}
