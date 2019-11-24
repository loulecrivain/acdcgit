package fr.imt.acdcgit.features;

import java.util.Map;

import org.eclipse.jgit.awtui.AwtCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class WorkingAreaFeatureTest extends TestCase {
	private CredentialsProvider credsProvider;
	private RepoFileListFromPath rFinder;
	private FeatureListFactory<WorkingAreaFeature> featureListFactory;
	private WorkingAreaFeature firstRepo;
	
	protected void setUp() throws Exception {
		super.setUp();
		rFinder = new RepoFileListFromPath("..");
		credsProvider = new AwtCredentialsProvider();
		featureListFactory = new FeatureListFactory<WorkingAreaFeature>(WorkingAreaFeature.FACTORY, rFinder.getRepos(),
				credsProvider);
		firstRepo = featureListFactory.get().get(0);
	}

	public void testGetWaStatus() {
		/* no test for now
		try {
			Map<String,String> m = this.firstRepo.getWAStatus();
			for(String k : m.keySet()) {
			}
		} catch (Exception e) {
			fail("Exception " + e.getClass() + " raised while testing");	
		} */
	}

}
