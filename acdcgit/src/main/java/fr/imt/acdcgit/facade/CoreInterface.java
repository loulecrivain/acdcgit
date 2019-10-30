package fr.imt.acdcgit.facade;

import java.util.List;

public interface CoreInterface {
	/**
	 * Get the list of repositories dicovered
	 * @return list of repositories
	 */
	List<Repository> getRepos();
	
	/**
	 * Get a diff between main branch remote
	 * and main branch local for the given repository
	 * @param r the repository on which you wish to have a diff
	 */
	String getDiff(Repository r);
	
	/**
	 * Same than getDiff but with all known repositories
	 * @return List of diffs
	 */
	List<String> getAllDiffs();
	
	/**
	 * Tells if the given repository is synced with its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is synced, false else
	 */
	boolean isSynced(Repository r);
	
	/**
	 * Tells if the given repository is behind its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is behind, false else
	 */
	boolean isBehind(Repository r);
	
	/**
	 * Tells if the given repository is ahead its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is ahead, false else
	 */
	boolean isAhead(Repository r);
	
	/**
	 * Launches automatical synchronization, that is,
	 * synchronization where you don't need any interaction
	 * with the user to ask for credentials
	 * @param r repository you whish to sync
	 * @return false on failure to sync
	 */
	boolean launchAutoSyncOn(Repository r);
	
	/**
	 * Launches manual synchronization, that is,
	 * synchronization where you will need interaction
	 * with the user to ask for credentials
	 * @param r repository you whish to sync
	 * @return false on failure to sync
	 */
	boolean launchManualSyncOn(Repository r);
}
