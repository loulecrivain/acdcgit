package fr.imt.acdcgit.findutils;

import java.io.File;

enum FileType {
	DIRECTORY, FILE;
}

/**
 * Provides a way of testing Files against their type.
 * For portability reasons, the only provided types are 
 * "directory" and "file".
 */
public class FileTypeFilter extends AbstractFileFilter {
	public static FileType FTYPE_DIR = FileType.DIRECTORY;
	public static FileType FTYPE_FILE = FileType.FILE;
	
	protected FileType ft;		
	
	/**
	 * 
	 * @param ft filtered files must be of this type
	 */
	public FileTypeFilter(FileType ft) {
		this.ft = ft;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
