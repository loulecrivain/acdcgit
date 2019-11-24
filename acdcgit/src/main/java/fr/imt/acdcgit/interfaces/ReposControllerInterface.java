package fr.imt.acdcgit.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Common controller to manage multiple repositories.
 * Repositories can be added from a file,
 * where the absolute paths for the repositories are
 * stored line-by-line, or from a search path.
 * 
 * Client class may then use the getRepos() method in
 * order to perform the desired operations on the repositories
 * which were found. Refer to RepoInterface for more information.
 * 
 * Exceptions are thrown in order to let the class using this
 * interface define its own error handling.
 */
public interface ReposControllerInterface extends RepositoryInterface, RepositoriesInterface {
	/**
     * Read file content and add found repos
     *
     * @param filePath the path to the file containing
     *         the list of repositories. Paths are absolute
     *         and may contain .git dir or not.
     */
    void addReposFromFile(String filePath);

    /**
     * Recursively search from the given directoryPath
     * entry point into the filesystem and add found repos
     * 
     * @param directoryPath    the path to the directory.
     */
    void addReposFromDirectory(String directoryPath);

    /**
     * Get all the stored repos. Client may then iterate
     * the list and call the appropriate methods. Or it may
     * use batch methods
     * 
     * @return  the list of repos.
     */
    List<String> getRepos();

    /* *** this is for "batch on all" *** */
    /**
     * Try to push all repositories known to the controller
     * without authentication
     * @return true for success, false else
     * @throws Exception
     */
    Map<String, Boolean> pushAll() throws Exception;

    /**
     * Try to pull all repositories known to the controller
     * without authentication
     * @return true for success, false else
     * @throws Exception
     */
    Map<String, Boolean> pullAll() throws Exception;
    /**
     * Get the states for all the repositories known to the controller.
     * Keys are the path to the repository. Values are their states.
     * @return List of states (please refer to getState documentation).
     * @throws Exception
     */
    Map<String, String> getAllStates() throws Exception;     // May take time as it requires network interaction and nothing is parallelized.


    /* batch with parameters and single-argument methods are
     * the same than in the previous interfaces. As we extend them,
     * there is no need to specify those methods again. Implementer
     * may use delegation to existing implementations */


    /**
     * Save repos to target file. Note that the file
     * is *overwritten*.
     * 
     * @return true for success, false for failure to create/write to file
     */
    boolean saveReposToFile(String targetedPath);
}
