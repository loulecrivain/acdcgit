package fr.imt.acdcgit.facade;

import java.io.IOException;
import java.util.List;

public interface CoreInterface {
	/**
	 * Get the list of repositories dicovered
	 * @return list of repositories
	 * @throws IOException 
	 */
	List<Repository> getRepos();
	
	/**
	 * Get a diff between main branch remote
	 * and main branch local for the given repository
	 * @param r the repository on which you wish to have a diff
	 * @throws Exception 
	 */
	String getDiff(Repository r) throws Exception;
	
	/**
	 * Same than getDiff but with all known repositories
	 * @return List of diffs
	 * @throws Exception 
	 */
	List<String> getAllDiffs() throws Exception;
	
	/**
	 * Tells if the given repository is synced with its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is synced, false else
	 * @throws Exception 
	 */
	boolean isSynced(Repository r) throws Exception;
	
	/**
	 * Tells if the given repository is behind its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is behind, false else
	 * @throws Exception 
	 */
	boolean isBehind(Repository r) throws Exception;
	
	/**
	 * Tells if the given repository is ahead its
	 * remote on its main branch
	 * @param r the repository you whish to test
	 * @return true if the repository is ahead, false else
	 * @throws Exception 
	 */
	boolean isAhead(Repository r) throws Exception;
	
	/**
	 * Launches automatical synchronization, that is,
	 * synchronization where you don't need any interaction
	 * with the user to ask for credentials
	 * @param r repository you whish to sync
	 * @return false on failure to sync
	 */
	boolean launchAutoSyncOn(Repository r) throws Exception;
	
	/**
	 * Launches manual synchronization, that is,
	 * synchronization where you will need interaction
	 * with the user to ask for credentials
	 * @param r repository you whish to sync
	 * @return false on failure to sync
	 */
	boolean launchManualSyncOn(Repository r) throws Exception;
}
