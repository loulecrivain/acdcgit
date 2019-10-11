package fr.imt.acdcgit.findutils;

import java.io.File;

enum FileType {
	DIRECTORY, FILE;
}

public class FileTypeFilter implements FileTestInterface {
	protected FileType ft;		
	
	public FileTypeFilter(FileType ft) {
		this.ft = ft;
	}
	
	public boolean testFile(File a) {
		switch(this.ft) {
		case DIRECTORY:
			if(a.isDirectory()) { return true; }
			break;
		case FILE:
			if(a.isFile()) { return true; }
			break;
		}
		return false;
	}
}
