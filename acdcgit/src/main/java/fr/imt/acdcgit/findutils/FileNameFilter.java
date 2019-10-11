package fr.imt.acdcgit.findutils;

import java.util.regex.Pattern;
import java.io.File;

class FileNameFilter implements FileTestInterface {
	protected Pattern pattern;

	public FileNameFilter(Pattern p) {
		this.pattern = p;
	}
	
	public FileNameFilter(String pattern) {
		this(Pattern.compile(pattern));
	}
	
	public boolean testFile(File a) {
		return pattern.matcher(a.getName()).matches();
	}
}

