package fr.imt.acdcgit.findutils;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * This class provides a way of matching a list of files
 * against one single test (e.g, class implementing the
 * FileTestInterface).
 * Example usage:
 * filteredListOfFiles = (new FileFilter(listOfFiles).by(fileTest1).by(fileTest2).getFiltered())
 */
public class FileFilter {
	private ArrayList<File> files;
	
	/**
	 * See class description for example usage of this method
	 * @return The list of files provided when constructing the class
	 */
	public ArrayList<File> getFiltered() {
		return this.files;
	}
	
	/**
	 * build a filter for a new list of files
	 * @param fl list of files you want to apply filter on
	 */
	public FileFilter(ArrayList<File> fl) {
		this.files = fl;
	}
	
	protected Iterator<File> makeIterator() {
		return files.iterator();
	}
	
	/**
	 * 
	 * @param test instance of a class implementing the FileTestInterface
	 * @return a new instance of this class containing the filtered files
	 */
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
