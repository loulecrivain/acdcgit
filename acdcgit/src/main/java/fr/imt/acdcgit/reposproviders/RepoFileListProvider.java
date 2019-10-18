package fr.imt.acdcgit.reposproviders;
import java.util.ArrayList;
import java.io.File;

/**
 * Classes implementing this interface are providing
 * a way of getting the list of found repositories
 */
public interface RepoFileListProvider {
	public ArrayList<File> getRepos();
}
