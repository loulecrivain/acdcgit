package fr.imt.acdcgit.reposproviders;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import java.io.File;
import java.io.IOException;

/** Factory taking a File in input (path of an existing Git repo)
 * and outputting a new "Git" object.
 */
public class RepoFromFileFactory {
	public static Git repoFrom(File path) {
		try { 
			Repository repo = new FileRepositoryBuilder()
					.setGitDir(path)
					.readEnvironment()
					.findGitDir()
					.build();
			return new Git(repo);
		} catch (IOException ioe) {
			System.err.println("IOerror " + ioe.getMessage() +  " while adding repo at path " + path.toString());
		}
		return null; // failed to make repo
	}
}
