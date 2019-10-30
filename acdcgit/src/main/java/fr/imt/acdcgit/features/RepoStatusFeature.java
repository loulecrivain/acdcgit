package fr.imt.acdcgit.features;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import java.util.ArrayList;
import java.util.EnumMap;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.BranchTrackingStatus;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;


class RepoStatusFeatureFactory implements FeatureFactoryInterface<RepoStatusFeature> {
	public RepoStatusFeature getInstance(Git repoUsedByImplClass) {
		return new RepoStatusFeature(repoUsedByImplClass);
	}
}

public class RepoStatusFeature extends RepoListFeature {
	// can be used publicly
	public static enum RepoState {
		SYNCED,
		BEHIND,
		// not used rn
		//BEHIND_UNCLEAN, // work area has changes, cannot merge what was fetched
		AHEAD,
		// not used rn
		//AHEAD_INCONFLICT, // cannot merge in origin, conflicts
		UNKNOWN // unknown state case
	}
	protected static final String BRANCHNAME = "master";
	// remote name is not used because local main branch must
	// have a remote tracking branch set
	//protected static final String REMOTENAME = "origin";
	protected EnumMap<RepoState,String> stateToString;
	public static final FeatureFactoryInterface<RepoStatusFeature> FACTORY =
			new RepoStatusFeatureFactory();
	

	public RepoStatusFeature(Git repo) {
		super(repo);
		 stateToString = new EnumMap<RepoState,String>(RepoState.class);
		 stateToString.put(RepoState.SYNCED, "synchronized");
		 stateToString.put(RepoState.BEHIND, "behind");
		 // not used rn
		 // stateToString.put(RepoState.BEHIND_UNCLEAN, "behind with changes in working area");
		 stateToString.put(RepoState.AHEAD, "ahead");
		 // not used rn
		 // stateToString.put(RepoState.AHEAD_INCONFLICT, "ahead and in conflict with remote");
		 stateToString.put(RepoState.UNKNOWN, "unknown state !");
	}

	public String getStateStr() throws GitAPIException, Exception {
		return this.stateToString.get(getState());
	}
	
	// TODO
	// must call git-fetch before ?
	// it seems that yes :|
	public RepoState getState() throws GitAPIException, Exception {
		String mainBranchRef = this.getMainBranchStr();
		Repository localRepository = this.repo.getRepository();
		BranchTrackingStatus mainBranchTrackingStatus = 
				BranchTrackingStatus.of(localRepository, mainBranchRef);
		if(mainBranchTrackingStatus != null ) {
			/* mapping returns to cases: origin main branch points to:
			 * - commit which is not in our log: LATE
			 * - latest commit we have: SYNCED
			 * - a commit we have but which is not the last one: AHEAD
			 */
			if(mainBranchTrackingStatus.getBehindCount() >= 1) {
				return RepoState.BEHIND;
			} else if (mainBranchTrackingStatus.getAheadCount() >= 1) {
				return RepoState.AHEAD;
			} else if (mainBranchTrackingStatus.getAheadCount() == 0 &&
					   mainBranchTrackingStatus.getBehindCount() == 0) {
				return RepoState.SYNCED;
			}
		}
		// status is null or in an unknown state. In any way, we can't tell, return UNKNOWN.
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
