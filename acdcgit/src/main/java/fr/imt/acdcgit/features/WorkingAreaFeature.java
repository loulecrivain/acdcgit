package fr.imt.acdcgit.features;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.transport.CredentialsProvider;

class WorkingAreaFeatureFactory implements FeatureFactoryInterface<WorkingAreaFeature> {
	public WorkingAreaFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new WorkingAreaFeature(repoUsedByImplClass, cp);
	}
}

public class WorkingAreaFeature extends BaseFeature {
	public static final FeatureFactoryInterface<WorkingAreaFeature> FACTORY = new WorkingAreaFeatureFactory(); 
	
	public WorkingAreaFeature(Git repo, CredentialsProvider cp) {
		super(repo, cp);
	}

	protected Status waStatus() throws Exception {
		return this.repo.status().call();
	}
	
	public String commitChanges(String message) throws Exception {
		return this.repo.commit().setMessage(message).call().getId().toString();
	}
	
	public void addWaFile2Commit(String waFilePath) throws GitAPIException {
		try {
			this.repo.add().addFilepattern(waFilePath).call();
		} catch (NoFilepatternException nofpe) {
			// do nothing, wrong file pattern is not a problem
			// but don't catch other exceptions
		}
	}
	
	private Map<String,String> pathSet2Map(Set<String> pathSet, String status) {
		HashMap<String,String> m = new HashMap<String,String>();
		for(String path: pathSet) {
			m.put(path, status);
		}
		return m;
	}
	
	public Map<String,String> getWAStatus() throws Exception {
		Map<String,String> name2Status = new HashMap<String,String>();
		Status status = this.waStatus();
		name2Status.putAll(pathSet2Map(status.getAdded(), "ADDED"));
		name2Status.putAll(pathSet2Map(status.getMissing(), "DELETED"));
		name2Status.putAll(pathSet2Map(status.getModified(), "MODIFIED"));
		name2Status.putAll(pathSet2Map(status.getUntracked(), "UNTRACKED"));
		return name2Status;
	}
}
