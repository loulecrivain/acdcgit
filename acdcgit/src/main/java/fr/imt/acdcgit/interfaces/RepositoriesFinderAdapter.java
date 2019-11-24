package fr.imt.acdcgit.interfaces;

import java.util.ArrayList;
import java.util.List;

import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import fr.imt.acdcgit.reposproviders.RepoListFromFile;
import fr.imt.acdcgit.reposproviders.RepoListProvider;

import java.io.File;

public class RepositoriesFinderAdapter implements RepositoriesFinderInterface {

	@Override
	public List<String> findRepositoriesInDirectory(String directoryPath) throws Exception {
		RepoListProvider<File> provider = new RepoFileListFromPath(directoryPath);
		List<String> ls = new ArrayList<String>();
		for(File f : provider.getRepos()) {
			ls.add(f.getCanonicalPath());
		}
		return ls;
	}

	@Override
	public List<String> findRepositoriesInFile(String filePath) throws Exception {
		RepoListProvider<File> provider = new RepoListFromFile(filePath);
		List<String> ls = new ArrayList<String>();
		for(File f : provider.getRepos()) {
			ls.add(f.getCanonicalPath());
		}
		return ls;
	}

}
