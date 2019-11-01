package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;

import java.util.ArrayList;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.BranchTrackingStatus;

class RepoStatusFeatureFactory implements FeatureFactoryInterface<RepoStatusFeature> {
	public RepoStatusFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new RepoStatusFeature(repoUsedByImplClass, cp);
	}
}

public class RepoStatusFeature extends RepoListFeature {
	// can be used publicly
	public static enum RepoState {
		SYNCED, BEHIND, AHEAD, BEHIND_AHEAD, // has both unpushed commits and unpulled commits
		UNKNOWN // unknown state case
	}

	protected static final String BRANCHNAME = "master";
	public static final FeatureFactoryInterface<RepoStatusFeature> FACTORY = new RepoStatusFeatureFactory();
	protected String fetchMessages;

	public RepoStatusFeature(Git repo, CredentialsProvider cp) {
		super(repo, cp);
		fetchMessages = ""; // avoids having a null
	}

	protected String fetchForMainBranch() throws TransportException, GitAPIException { // uses default remote
		FetchResult result = this.repo.fetch()
				.setCredentialsProvider(this.credsProvider)
				.setCheckFetchedObjects(true)
				.call();
		// TODO add progress monitor
		return result.getMessages();
	}

	public String getFetchMessages() {
		return fetchMessages;
	}

	protected BranchTrackingStatus getMainBranchTrackingStatus() throws GitAPIException, Exception {
		return BranchTrackingStatus.of(this.repo.getRepository(), this.getMainBranchStr());
	}

	public RepoState getState(boolean fetchBefore) throws GitAPIException, Exception {
		BranchTrackingStatus mainBranchTrackingStatus = getMainBranchTrackingStatus();
		if (fetchBefore) {
			this.fetchMessages = this.fetchForMainBranch();
		}
		if (mainBranchTrackingStatus != null) {
			/*
			 * mapping returns to cases: origin main branch points to:
			 * - commit which is not in our log: LATE 
			 * - latest commit we have: SYNCED 
			 * - a commit we have but which is not the last one: AHEAD
			 */
			if (mainBranchTrackingStatus.getBehindCount() >= 1 && mainBranchTrackingStatus.getAheadCount() >= 1) {
				return RepoState.BEHIND_AHEAD;
			} else if (mainBranchTrackingStatus.getBehindCount() >= 1) {
				return RepoState.BEHIND;
			} else if (mainBranchTrackingStatus.getAheadCount() >= 1) {
				return RepoState.AHEAD;
			} else if (mainBranchTrackingStatus.getAheadCount() == 0
					&& mainBranchTrackingStatus.getBehindCount() == 0) {
				return RepoState.SYNCED;
			}
		}
		// status is null or in an unknown state. In any way, we can't tell, return
		// UNKNOWN.
		return RepoState.UNKNOWN;
	}

	public String getMainBranchStr() throws GitAPIException, Exception {
		return this.getMainBranch().getName();
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
			if (r.getName().contains(BRANCHNAME)) {
				returnRef = r;
			}
		}
		return returnRef;
	}
}
