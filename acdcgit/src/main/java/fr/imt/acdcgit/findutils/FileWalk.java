package fr.imt.acdcgit.findutils;

import java.io.File;
import java.util.ArrayList;

public class FileWalk {
	// évite à la fonction appellante de devoir utiliser un bloc try/catch
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
