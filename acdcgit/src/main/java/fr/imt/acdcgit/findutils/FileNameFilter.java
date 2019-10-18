package fr.imt.acdcgit.findutils;

import java.util.regex.Pattern;
import java.io.File;

/**
 * Provides a way of testing if a file matches a name
 * specification, which is in the form of PCRE
 */
public class FileNameFilter extends AbstractFileFilter {
	protected Pattern pattern;

	/**
	 * Builds the class with the name pattern specified as a Pattern object 
	 * @param p the pattern used on the name. See java.util.regex.Pattern doc.
	 */
	public FileNameFilter(Pattern p) {
		this.pattern = p;
	}
	
	/**
	 * Builds the class with the name pattern specified as a string
	 * @param pattern the pattern used on the name. See java.util.regex.Pattern doc.
	 */
	public FileNameFilter(String pattern) {
		this(Pattern.compile(pattern));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean testFile(File a) {
		return pattern.matcher(a.getName()).matches();
	}
}

