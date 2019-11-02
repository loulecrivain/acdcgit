package fr.imt.acdcgit.features;

import org.eclipse.jgit.awtui.AwtCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class RepoSyncFeatureTest extends TestCase {
	private CredentialsProvider credsProvider;
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<RepoSyncFeature> featureListFactory;
	private RepoSyncFeature firstRepo;
	
	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new AwtCredentialsProvider();
		featureListFactory = new FeatureListFactory<RepoSyncFeature>(RepoSyncFeature.FACTORY, rFinder.getRepos(),
				credsProvider);
		firstRepo = featureListFactory.get().get(0);
	}

	public void testLaunchAutoSync() {
		try {
			if(firstRepo.isAhead()) {
				// if repo is not synced and ahead, autosync should
				// fail and return false. This is normal
				// behaviour, as the transport protocol used
				// requires authentication for pushing
				assertEquals(false,firstRepo.launchAutoSync());
			} else  if (firstRepo.isBehind()) {
				// however, if there is upstream changes,
				// fetching them requires no authentication
				// so auto sync shouldn't fail here
				assertEquals(true,firstRepo.launchAutoSync());
			} else {
				// normally repo should be synced
				// so we have nothing to sync
				// and then we return true
				assertEquals(true,firstRepo.launchAutoSync());
			}
		} catch (Exception e) {
			fail("Exception " + e.getClass() + " raised while testing");
		}
	}

	public void testLaunchManualSync() {
		try {
			// manual sync should suceed anyway
			// except for ssh auth, which we don't support atm.
			assertEquals(true,firstRepo.launchManualSync());
		} catch (Exception e) {
			fail("Exception " + e.getClass() + " raised while testing");
		}
	}

	public void testGetSyncCompletion() {
		// idk how to use it
	}

}
