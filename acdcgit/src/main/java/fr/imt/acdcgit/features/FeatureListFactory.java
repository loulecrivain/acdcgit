package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;

import fr.imt.acdcgit.reposproviders.*;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

// DONE transform this into something providing initialized features

// this factory needs to be instantiated
// because parameterized types cannot be
// used in static methods
public class FeatureListFactory<A extends BaseFeature> {
	private FeatureFactoryInterface<A> featureProvider;
	private ArrayList<File> repos;
	private ArrayList<A> initializedFeatures;

	public FeatureListFactory(FeatureFactoryInterface<A> ffi, ArrayList<File> repos) {
		this.featureProvider = ffi;
		this.repos = repos;
	}

	public ArrayList<A> get() throws IOException {
		// doing lazy-loading
		if (this.initializedFeatures == null) {
			Iterator<File> i = repos.iterator();
			this.initializedFeatures = new ArrayList<A>();
			while (i.hasNext()) {
				File tmpRepoFile = i.next();
				Git tmpRepoGit = RepoFromFileFactory.repoFrom(tmpRepoFile);
				this.initializedFeatures.add(this.featureProvider.getInstance(tmpRepoGit));
			}
		}
		return this.initializedFeatures;
	}
}
