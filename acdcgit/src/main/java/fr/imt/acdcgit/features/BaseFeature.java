package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;


class BaseFeatureFactory implements FeatureFactoryInterface<BaseFeature> {
	public BaseFeature getInstance(Git repoUsedByImplClass) {
		return new BaseFeature(repoUsedByImplClass);
	}
}

/**
 * This is the parent of all "features" offered by MyGit. By features we mean
 * various operations on a Git repository. Strategy is to "stack" enhancements
 * by inheritage.
 */
public class BaseFeature {
	// specifies the associated factory
	public static final FeatureFactoryInterface<BaseFeature> FACTORY = new BaseFeatureFactory();

	protected Git repo;

	public BaseFeature(Git repo) {
		this.repo = repo;
	}
}
