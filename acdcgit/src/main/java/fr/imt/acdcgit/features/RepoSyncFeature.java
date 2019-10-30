package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;

// TODO this class is a stub !
public class RepoSyncFeature extends RepoStatusFeature {

	public RepoSyncFeature(Git repo) {
		super(repo);
	}
	
	// TODO stubs below
	boolean launchAutoSync() {
		return true;
	}
	
	boolean launchManualSync() {
		return true;
	}
	
	String getSyncCompletion() {
		return "0";
	}
}
