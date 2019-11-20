package fr.imt.acdcgit.features;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;

import java.io.File;
import java.io.IOException;

class RepoListFeatureFactory implements FeatureFactoryInterface<RepoListFeature> {
	public RepoListFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new RepoListFeature(repoUsedByImplClass,cp);
	}
}

public class RepoListFeature extends WorkingAreaFeature {
	protected enum DirMode {
		RELATIVE,
		CANONICAL,
		ABSOLUTE
	}
	
	public static final FeatureFactoryInterface<RepoListFeature> FACTORY = 
			new RepoListFeatureFactory();

	public RepoListFeature(Git repo, CredentialsProvider cp) {
		super(repo,cp);
	}
	
	public String getRelativeDir() {
		return getDirectory(DirMode.RELATIVE);
	}
	
	public String getCanonicalDir() {
		return getDirectory(DirMode.CANONICAL);
	}
	
	public String getAbsoluteDir() {
		return getDirectory(DirMode.ABSOLUTE);
	}
	
	protected String getDirectory(DirMode mode) {
		File directory = this.repo.getRepository().getDirectory();
		if (directory != null) {
			try {
				switch(mode) {
				case RELATIVE:
					return directory.getPath();
				case CANONICAL:
					return directory.getCanonicalPath();
				case ABSOLUTE:
					return directory.getAbsolutePath();
				default:
					return null;
				}
			} catch (IOException ioe) {
				return null;
			}
		} else {
			return null;
		}
	}
}