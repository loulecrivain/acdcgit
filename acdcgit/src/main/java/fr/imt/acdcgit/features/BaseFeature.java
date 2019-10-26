package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RemoteConfig;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class BaseFeatureFactory implements FeatureFactoryInterface<BaseFeature> {
	public BaseFeature getInstance(Git repoUsedByImplClass) {
		return new BaseFeature(repoUsedByImplClass);
	}
}

/**
 * This is the parent of all "features" offered by MyGit. By features we mean
 * various operations on a Git repository. Groups common operations used by more
 * than one feature.
 */
public class BaseFeature {
	// specifies the associated factory
	public static final FeatureFactoryInterface<BaseFeature> FACTORY = new BaseFeatureFactory();

	protected static final String BRANCHNAME = "master";
	protected static final String REMOTENAME = "origin";

	protected Git repo;

	public BaseFeature(Git repo) {
		this.repo = repo;
	}

	public String getName() {
		return this.getDirectory();
	}

	protected String getDirectory() {
		File directory = this.repo.getRepository().getDirectory();
		if (directory != null) {
			try {
				return directory.getCanonicalPath();
			} catch (IOException ioe) {
				return null;
			}
		} else {
			return null;
		}
	}

	protected RemoteConfig getMainRemote() throws GitAPIException, Exception {
		ArrayList<RemoteConfig> remotes = new ArrayList<RemoteConfig>();
		RemoteConfig returnConfig;

		remotes.addAll(this.repo.remoteList().call());
		if (remotes.size() >= 1) {
			returnConfig = remotes.get(0);
		} else {
			throw new Exception("Repository has no remotes at all");
		}
		for (RemoteConfig r : remotes) {
			if (r.getName().contentEquals(REMOTENAME)) {
				returnConfig = r;
			}
		}
		return returnConfig;
	}

	protected Ref getMainBranch() throws GitAPIException, Exception {
		ArrayList<Ref> branchesRefs = new ArrayList<Ref>();
		Ref returnRef;

		branchesRefs.addAll(this.repo.branchList().call());
		// take the 1st branch coming and overwrite with
		// main branch if found
		if (branchesRefs.size() >= 1) {
			returnRef = branchesRefs.get(0);
		} else {
			throw new Exception("Repository is not initialized");
		}
		for (Ref r : branchesRefs) {
			if (r.getName().contentEquals(BRANCHNAME)) {
				returnRef = r;
			}
		}
		return returnRef;
	}
}
