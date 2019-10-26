package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import java.io.File;
import java.io.IOException;

class RepoListFeatureFactory implements FeatureFactoryInterface<RepoListFeature> {
	public RepoListFeature getInstance(Git repoUsedByImplClass) {
		return new RepoListFeature(repoUsedByImplClass);
	}
}

public class RepoListFeature extends BaseFeature {
	public static final FeatureFactoryInterface<RepoListFeature> FACTORY = 
			new RepoListFeatureFactory();

	public RepoListFeature(Git repo) {
		super(repo);
	}
	// simply rely on inherited method getName
	// getting the repo directory is a feature offered
	// by all the my git feature classes
	// so we have no reason to do something special here
}
