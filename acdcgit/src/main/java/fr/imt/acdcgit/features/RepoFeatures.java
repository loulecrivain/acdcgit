package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;


class RepoFeaturesFactory implements FeatureFactoryInterface<RepoFeatures> {

	public RepoFeatures getInstance(Git repoUsedByImplClass, CredentialsProvider credsProviderUsedByImplClass) {
		return new RepoFeatures(repoUsedByImplClass, credsProviderUsedByImplClass);
	}
	
}

/**
 * Groups all features provided under a handy name.
 * Does nothing else special for now, but may
 * be extended in the future.
 */
public class RepoFeatures extends RepoSyncFeature {
	public static final FeatureFactoryInterface<RepoFeatures> FACTORY = new RepoFeaturesFactory();
	
	public RepoFeatures(Git repo, CredentialsProvider cp) {
		super(repo, cp);
	}
	
}
