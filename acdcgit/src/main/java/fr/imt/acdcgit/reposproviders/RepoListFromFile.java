package fr.imt.acdcgit.reposproviders;

import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * All-In-One class for reading files containing list
 * of repos. Perform checks, provide list of repos.
 * File format is one repo by line, with absolute work tree path (does not includes .git)
 * C:\path\to\repo\in\..\strange\repodir
 * 
 */
public class RepoListFromFile extends AbstractRepoFileListProvider {
	private BufferedReader bufferedReader;
	private ArrayList<File> foundRepos;
	
	public RepoListFromFile(String path) {
		super(path);
		foundRepos = new ArrayList<File>();
		File confFile;
		String currentLine;
		
		confFile = new File(this.path);
		try {
			this.bufferedReader = new BufferedReader(new FileReader(confFile)); // using default charset
		} catch(FileNotFoundException fnfe) {
			System.err.println("file " + this.path + " not found. " + fnfe.toString());
		}
		try {
			while((currentLine = this.bufferedReader.readLine()) != null) {
				if(isRepo(currentLine)) {
					File tmp = (new File(currentLine + File.separator + ".git")).getCanonicalFile(); // canonical pathnames are unique
					if(!foundRepos.contains(tmp)) {
						foundRepos.add(tmp);
					}
				}
			}
		} catch(IOException ioe) {
			System.err.println("Error while reading " + this.path + ": " + ioe.getMessage());
		}
	}
	
	protected boolean isRepo(String path) {
		File repoFile = new File(path + File.separator + ".git");
		if(repoFile.isDirectory() && repoFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	// readLine was self-implemented before but replaced
	// by BufferedReader because of innefficiency
	
	@Override
	public ArrayList<File> getRepos() {
		return this.foundRepos;
	}
	
}
