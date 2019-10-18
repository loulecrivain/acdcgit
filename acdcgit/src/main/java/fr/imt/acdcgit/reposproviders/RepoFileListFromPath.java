package fr.imt.acdcgit.reposproviders;

import fr.imt.acdcgit.findutils.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Provides the list of repositories from an entry point in the file system
 * 
 */
public class RepoFileListFromPath extends AbstractRepoFileListProvider {
	FileNameFilter fname_dotgit;
	FileTypeFilter ftype_dir;
	FileFilter ff;
	/**
	 * The path variable in the constructor is used as the entry
	 * point in the file system for searching
	 */
	public RepoFileListFromPath (String path) {
		super(path);
		// building the appropriate filter
		this.fname_dotgit = new FileNameFilter("\\.git");
		this.ftype_dir = new FileTypeFilter(FileTypeFilter.FTYPE_DIR);
		// reading the file system is done once,
		// filtering is done lazily
		// this is useful when dynamically modifying filters (not the case here)
		ff = new FileFilter(FileWalk.walk(this.path));
	}

	public ArrayList<File> getRepos(){
		ArrayList<File> gitdirs = new ArrayList<File>();
		
		for(File f: ff.by(ftype_dir).by(fname_dotgit).getFiltered()) {
			gitdirs.add(f.getParentFile());
		}
		return gitdirs;
	}
}
