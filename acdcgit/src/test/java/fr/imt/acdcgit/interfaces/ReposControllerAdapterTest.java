package fr.imt.acdcgit.interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jgit.awtui.AwtCredentialsProvider;

import junit.framework.TestCase;

public class ReposControllerAdapterTest extends TestCase {
	private ReposControllerInterface reposController;
	private static String fs = File.separator; 
	
	protected void setUp() throws Exception {
		// rc used 
		reposController = new ReposControllerAdapter(new AwtCredentialsProvider());
		// add this repo (shouldn't fail)
		reposController.addReposFromDirectory("..");
	}

	public void testAddReposFromFile() {
		ReposControllerInterface rc = new ReposControllerAdapter(new AwtCredentialsProvider());
		String path = "src"+fs+"test"+fs+"java"+fs+"fr"+fs+"imt"+fs+"acdcgit"+fs+"interfaces"+fs+"repos.txt";
		rc.addReposFromFile(path);
		assertTrue(rc.getRepos().size() > 0);
	}

	public void testAddReposFromDirectory() {
		String path = "..";
		ReposControllerInterface rc = new ReposControllerAdapter(new AwtCredentialsProvider());
		rc.addReposFromDirectory(path);
		assertTrue(rc.getRepos().size() > 0);
	}

	public void testSaveReposToFile() {
		try {
			File temp = File.createTempFile("acdcgitsav", "tmp");
			// save
			this.reposController.saveReposToFile(temp.getCanonicalPath());
			// then try to read contents and see if repo is added
			BufferedReader reader = new BufferedReader(new FileReader(temp));
			StringBuffer contents = new StringBuffer();
			String line;
			while((line = reader.readLine()) != null) {
				contents.append(line);
			}
			if(!(contents.toString().length() > 0)) {
				fail("repository not written to savefile");
			}
			reader.close();
		} catch (IOException e) {
			fail("Exception " + e.getClass() + " raised while testing");
		}
	}

	public void testGetAllStates() {
		try {
			Map<String,String> m = this.reposController.getAllStates();
			assertTrue(m.size() > 0);
			// repo should be synced
			assertTrue(m.values().toArray(new String[0])[0].contentEquals("UP_TO_DATE"));
		} catch (Exception e) {
			fail("Exception " + e.getClass() + " raised while testing");
		}
		
	}

}
