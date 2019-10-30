package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;

class BaseFeatureFactory implements FeatureFactoryInterface<BaseFeature> {
	public BaseFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new BaseFeature(repoUsedByImplClass, cp);
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
	protected CredentialsProvider credsProvider;

	public BaseFeature(Git repo, CredentialsProvider cp) {
		this.repo = repo;
		this.credsProvider = cp;
	}
}
