package fr.imt.acdcgit.reposproviders;

import java.io.File;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import junit.framework.TestCase;

public class RepoFileListFromPathTest extends TestCase {
	private RepoFileListFromPath rFinder;
	
	protected void setUp() throws Exception {
		this.rFinder = new RepoFileListFromPath("..");
	}

	public void testGetRepos() {
		// normally the repo of our project is present
		// so at least one repo is found in parent dir
		if(!(rFinder.getRepos().size() >= 1)) {
			fail("Test git repository not found !");
		} else {
			assertTrue(rFinder.getRepos().get(0).equals(new File("..")));
		}
	}

}
