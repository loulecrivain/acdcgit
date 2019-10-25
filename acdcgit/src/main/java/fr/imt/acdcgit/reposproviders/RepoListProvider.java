package fr.imt.acdcgit.reposproviders;

import java.util.ArrayList;

/**
 * Classes implementing this interface
 * are providing a way of getting found
 * repositories. Choice of repository
 * representation is left to the implementing
 * class.
 * 
 * @param <E> Repository representation used (File, Git, String, etc)
 */
public interface RepoListProvider<E> {
	public ArrayList<E> getRepos();
}
