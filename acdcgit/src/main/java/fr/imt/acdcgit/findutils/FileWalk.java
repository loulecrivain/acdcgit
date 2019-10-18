package fr.imt.acdcgit.findutils;

import java.io.File;
import java.util.ArrayList;

/**
 * Provides a way of "walking" in the file system from an entry point (recursive listing) 
 * This class is meant to be used with FileFilters in the same package when you need
 * more precise search criterias.
 */
public class FileWalk {
	/**
	 * Search path (root) is provided as a string to avoid the use of a
	 * try/catch block for the calling method.
	 * May spit errors when not allowed to access given files. Unaccessible
	 * files are simply skipped.
	 * @param root entry point in the filesystem from which files are recursively listed 
	 * @return the list of found files
	 */
	public static ArrayList<File> walk(String root){
		try {
			return FileWalk.walk(new File(root));
		} catch(SecurityException e) {
			System.err.println("access denied: " + e.toString());
			return new ArrayList<File>(); // returning empty
		}
	}
	
	public static ArrayList<File> walk(File root){
		ArrayList<File> l = new ArrayList<File>();
		// add self first
		l.add(root);
		// then try to add leaves
		try {
			File[] dirlist = root.listFiles();
			if (dirlist!=null) {
				for (File pw: dirlist) {
					l.addAll(FileWalk.walk(pw));
				}
			}
		} catch(SecurityException e) {
			System.err.println("access denied: " + e.toString());
		}
		return l;
	}
}
