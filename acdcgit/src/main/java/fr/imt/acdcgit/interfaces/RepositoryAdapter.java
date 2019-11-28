package fr.imt.acdcgit.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import fr.imt.acdcgit.reposproviders.RepoListProvider;
import fr.imt.acdcgit.features.FeatureListFactory;
import fr.imt.acdcgit.features.RepoFeatures;
import fr.imt.acdcgit.features.RepoStatusFeature;

/*  *** Q&D *** */
public class RepositoryAdapter implements RepositoryInterface {
	protected Map<String,RepoFeatures> path2BO;
	protected CredentialsProvider cp;
	
	public RepositoryAdapter(CredentialsProvider cp) {
		this.cp = cp;
		this.path2BO = new HashMap<String,RepoFeatures>();
	}
	
	protected RepoFeatures rebuildBOWithOtherCPhackey(RepoFeatures rf1, CredentialsProvider credsProvider) {
		// creds quick hack, not my fault
		// the idea there is that you rebuild another BO with new credsprovider ...
		return getBOFromPath(rf1.getCanonicalDir(), credsProvider);
	}
	
	/* waring ! paths must be absolute ! 
	 * return may be null
	 * */
	protected RepoFeatures getBOFromPath(String path, CredentialsProvider credsProvider) {
		RepoListProvider<File> fileListProvider;
		FeatureListFactory<RepoFeatures> repoBOFactory;
		
		if(path2BO.get(path) == null) {
			// try to load if none
			fileListProvider = new RepoFileListFromPath(path);
			repoBOFactory = new FeatureListFactory<RepoFeatures>(RepoFeatures.FACTORY,
					fileListProvider.getRepos(),
					credsProvider);
			if (repoBOFactory.get() != null) {
				if (repoBOFactory.get().get(0) != null) {
					this.path2BO.put(path,repoBOFactory.get().get(0));
				}
			}
		} 
		return path2BO.get(path);
	}
	
	// may use this one instead of the previous
	protected RepoFeatures getBOFromPath(String path) {
		return getBOFromPath(path,this.cp);
	}
	
	@Override
	public void add(String pathToRepository, String... pathsToFiles) throws Exception {
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		if (rf != null) {
			for(String pathToFile : pathsToFiles) {
				rf.addWaFile2Commit(pathToFile);
			}
		}
	}

	@Override
	public void commit(String pathToRepository, String message) throws Exception {
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		if (rf != null) {
			rf.commitChanges(message);
		}
	}

	protected boolean pushOrPull(String pathToRepository, String username, String password, boolean isPush) throws Exception {
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		boolean ok = false;
		boolean authedMode = false;
		if(rf != null) {
			if (username != null && password != null) {
				// rebuild self with a credProvider with fixed username and password
				rf = this.rebuildBOWithOtherCPhackey(rf, new UsernamePasswordCredentialsProvider(username,password));
				// need to use authentication
				authedMode = true;
			} else {
				// rebuild self with empty credsprovider
				rf = this.rebuildBOWithOtherCPhackey(rf, null);
				authedMode = false;
			}
			if (isPush) {
				ok = rf.launchPush(authedMode);
			} else { // 2nd case: it's a pull
				ok = rf.launchPull(authedMode);
			}
		}
		return ok;
	}

	@Override
	public boolean push(String pathToRepository) throws Exception {
		return pushOrPull(pathToRepository, null, null, true);
	}
	
	@Override
	public boolean push(String pathToRepository, String username, String password) throws Exception {
		return this.pushOrPull(pathToRepository, username, password, true);
	}

	@Override
	public boolean pull(String pathToRepository) throws Exception {
		return this.pushOrPull(pathToRepository, null, null, false);
	}

	@Override
	public boolean pull(String pathToRepository, String username, String password) throws Exception {
		return this.pushOrPull(pathToRepository, username, password, false);
	}

	@Override
	public Map<String, String> status(String pathToRepository) throws Exception {
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		Map<String,String> m = new HashMap<String,String>(); // empty for default ret
		if(rf != null) {
			m.putAll(rf.getWAStatus());
		}
		return m;
	}

	@Override
	public List<String> getNotAddedFiles(String pathToRepository) throws Exception {
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		List<String> ls = new ArrayList<String>();
		Map<String,String> m;
		if(rf != null) {
			m = rf.getWAStatus();
			// sale
			for (String p : m.keySet()) {
				if(m.get(p).contentEquals("UNTRACKED")) {
					ls.add(p);
				}
			}
		}
		return ls;
	}

	@Override
	public String getState(String pathToRepository) throws Exception {
		// state relative to remote
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		String state = "UNKNOWN"; // please refer to inexistant type in interface
		if(rf != null) {
			RepoStatusFeature.RepoState rs = rf.getState(true);
			switch(rs) {
			case BEHIND:
				state = "BEHIND";
				break;
			case AHEAD:
				state = "AHEAD";
				break;
			case BEHIND_AHEAD: // idk , which should I put ?
				state = "BEHIND"; // say you pull first
				break;
			case SYNCED:
				state = "UP_TO_DATE";
				break;
			case UNKNOWN:
				state = "UNKNOWN"; // not in interface spec
				break;
			default:
				state = "UNKNOWN";
			}
		}
		return state;
	}

	@Override
	public String diffs(String pathToRepository) throws Exception {
		String diff = "";
		
		RepoFeatures rf = this.getBOFromPath(pathToRepository);
		if (rf != null) {
			diff = rf.getDiff();
		}
		return diff;
	}

}
