package fr.imt.acdcgit.findutils;
import java.io.File;

/**
 * Classes implementing this interface must provide
 * a way to know if a certain file matches a test criteria
 * via appropriate method
 */
public interface FileTestInterface {
	/**
	 * Tests if a file matches the criterias defined in the test
	 * @param a File object to test
	 * @return true if file passed the test, false else
	 */
	public boolean testFile(File a);
}
