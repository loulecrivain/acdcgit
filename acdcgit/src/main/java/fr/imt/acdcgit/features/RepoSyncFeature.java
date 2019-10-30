package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;

// TODO this class is a stub !
public class RepoSyncFeature extends RepoDiffFeature {

	public RepoSyncFeature(Git repo, CredentialsProvider cp) {
		super(repo,cp);
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
