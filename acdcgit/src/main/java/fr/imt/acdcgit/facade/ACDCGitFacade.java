package fr.imt.acdcgit.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import java.io.File;
import java.io.IOException;

import fr.imt.acdcgit.features.FeatureListFactory;
import fr.imt.acdcgit.features.RepoFeatures;
import fr.imt.acdcgit.features.RepoStatusFeature;
import fr.imt.acdcgit.reposproviders.*;





public class ACDCGitFacade extends AbstractCoreFacade {
	private RepoListProvider<File> fileListProvider;
	private FeatureListFactory<RepoFeatures> repoBOfactory;
	private List<RepoFeatures> repoBOList;
	private HashMap<Repository,RepoFeatures> repoData2BOMap;
	
	public ACDCGitFacade(String path, boolean isIndex, CredentialsProvider cp) {
		super(path, isIndex, cp);
		// create the file list provider
		if (isIndex) {
			this.fileListProvider = new RepoListFromFile(path);
		} else {
			this.fileListProvider = new RepoFileListFromPath(path);
		}
		this.repoBOfactory = new FeatureListFactory<RepoFeatures>(RepoFeatures.FACTORY,
				this.fileListProvider.getRepos(),
				this.credsProvider);
		this.repoData2BOMap = new HashMap<Repository,RepoFeatures>();
	}
	
	protected List<RepoFeatures> getReposBO() {
		if (repoBOList == null) {
			this.repoBOList = this.repoBOfactory.get();
			// if repoBOList is null then bidirData2BOMap is also empty,
			// populate it once
			for(RepoFeatures rf: repoBOList) {
				this.repoData2BOMap.put(new Repository(rf.getCanonicalDir()), rf);
			}
		}
		return this.repoBOList;
	}
	
	// TODO: find from where the IOException comes and catch it
	public List<Repository> getRepos() {
		getReposBO(); // lazy-load
		return new ArrayList<Repository>(this.repoData2BOMap.keySet());
	}

	public String getDiff(Repository r) throws Exception {
		getReposBO(); // lazy-load
		String diff = "";
		RepoFeatures rf = this.repoData2BOMap.get(r); 
		if (rf != null) {
			diff = rf.getDiff();
		}
		return diff; 
	}

	public List<String> getAllDiffs() throws Exception {
		getReposBO(); // lazy-load
		List<String> diffList = new ArrayList<String>();
		List<Object> diffListAsObjs = 
				this.repoData2BOMap.values().parallelStream().map(new Function<RepoFeatures, String>() {
					public String apply(RepoFeatures r) {
						try {
							return r.getRelativeDir() + ":\n" + r.getDiff();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}).collect(Collectors.toList());
		for(Object obj: diffListAsObjs) {
			diffList.add((String)obj);
		}
		return diffList;
	}

	public RepoStatusFeature.RepoState getStatus(Repository r) throws Exception {
		getReposBO();
		RepoFeatures rf = this.repoData2BOMap.get(r);
		if(rf != null) {
			return rf.getState(true);
		}
		return RepoStatusFeature.RepoState.UNKNOWN;
	}
	
	public boolean isSynced(Repository r) throws Exception {
		getReposBO();
		boolean synced = false;
		RepoFeatures rf = this.repoData2BOMap.get(r);
		if(rf != null) {
			synced = rf.isSynced();
		}
		return synced;
	}

	public boolean isBehind(Repository r) throws Exception {
		getReposBO();
		boolean behind = false;
		RepoFeatures rf = this.repoData2BOMap.get(r);
		if(rf != null) {
			behind = rf.isBehind();
		}
		return behind;
	}

	public boolean isAhead(Repository r) throws Exception {
		getReposBO();
		boolean ahead = false;
		RepoFeatures rf = this.repoData2BOMap.get(r);
		if(rf != null) {
			ahead = rf.isAhead();
		}
		return ahead;
	}

	protected boolean launchSyncOn(Repository r, boolean auto) throws Exception {
		getReposBO();
		boolean ok = false;
		RepoFeatures rf = this.repoData2BOMap.get(r);
		if (rf != null) {
			if(auto) {
				ok = rf.launchAutoSync();
			} else {
				ok = rf.launchManualSync();
			}
		}
		return ok;
	}

	public boolean launchAutoSyncOn(Repository r) throws Exception {
		getReposBO();
		return launchSyncOn(r,true);
	}

	public boolean launchManualSyncOn(Repository r) throws Exception {
		getReposBO();
		return launchSyncOn(r,false);
	}

}
