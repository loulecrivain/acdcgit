package fr.imt.acdcgit.reposproviders;

import java.io.File;
import java.util.ArrayList;

/**
 * This abstract class defines a super type for classes providing
 * a list of repositories in the form of a list of File objects
 * Child classes must also implement the RepoFileListProvider interface 
 * */
public abstract class AbstractRepoFileListProvider implements RepoListProvider<File> {
	protected String path;

	public abstract ArrayList<File> getRepos();
	
	/**
	 * Defining the meaning of the "path" variable is left to the
	 * child classes here
	 */
	public AbstractRepoFileListProvider(String path) {
		this.path = path;
	}
}
