package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;

/**
 * Defines a feature factory interface which can be used to get instantiated
 * features. We assume here that features only have a git repo as a runtime
 * dependency.
 * 
 * @param <T>
 */
public interface FeatureFactoryInterface<T> {
	public T getInstance(Git repoUsedByImplClass);
}
