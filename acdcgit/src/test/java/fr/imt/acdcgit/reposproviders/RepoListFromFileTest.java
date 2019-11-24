package fr.imt.acdcgit.reposproviders;

import junit.framework.TestCase;
import fr.imt.acdcgit.reposproviders.RepoListFromFile;
import java.io.File;

public class RepoListFromFileTest extends TestCase {
	String file;
	RepoListFromFile rlff;
	
	protected void setUp() throws Exception {
		super.setUp();
		String fs = File.separator;
		this.file = "src"+fs+"test"+fs+"java"+fs+"fr"+fs+"imt"+fs+"acdcgit"+fs+"reposproviders"+fs+"repos.txt";
		this.rlff = new RepoListFromFile(this.file);
	}
	
	public void testGetRepos() {
		if(!(this.rlff.getRepos().size() >= 1)) {
			fail("Test git repository not found !");
		} else {
			try {
				assertTrue(rlff.getRepos().get(0).getCanonicalPath().contentEquals(new File("../.git").getCanonicalPath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
