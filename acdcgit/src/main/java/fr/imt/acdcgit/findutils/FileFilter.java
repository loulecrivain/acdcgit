package fr.imt.acdcgit.findutils;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;

public class FileFilter {
	
	private ArrayList<File> files;
	
	public ArrayList<File> getFiltered() {
		return this.files;
	}
	
	public FileFilter(ArrayList<File> fl) {
		this.files = fl;
	}
	
	protected Iterator<File> makeIterator() {
		return files.iterator();
	}

	public <E extends FileTestInterface> FileFilter by(E test) {
		ArrayList<File> newList = new ArrayList<File>();
		Iterator<File> i = this.makeIterator();
		
		while(i.hasNext()) {
			File f = i.next();
			if(test.testFile(f)) {
				newList.add(f);
			}
		}
		return new FileFilter(newList);
	}
}
