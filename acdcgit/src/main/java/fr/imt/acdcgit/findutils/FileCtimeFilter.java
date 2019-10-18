package fr.imt.acdcgit.findutils;

import java.io.File;
import fr.imt.acdcgit.findutils.AbstractFileFilter;

enum TimeComp {
	EQ,GT,LT;
}

/**
 * Filter File objects based on their creation time.
 * You can test if file is older, newer or equals the given time.
 */
public class FileCtimeFilter extends AbstractFileFilter {
	protected long ctime;
	protected TimeComp tc;
	
	public static TimeComp GREATER = TimeComp.GT;
	public static TimeComp LOWER = TimeComp.LT;
	public static TimeComp EQUAL = TimeComp.EQ;

	/**
	 * 
	 * @param ctime time you want to compare to 
	 * @param tc can be TimeComp.EQ, TimeComp.GT, TimeComp.LT to respectively \ 
	 * 		  test input file creation time for being equal, greater than or lower \
	 *        than the given time
	 */
	public FileCtimeFilter(long ctime, TimeComp tc) {
		this.ctime = ctime;
		this.tc = tc;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean testFile(File f) {
		switch(tc) {
		case EQ:
			if (f.lastModified() == ctime) { return true; }
			break;
		case GT:
			if (f.lastModified() >= ctime) { return true; }
			break;
		case LT:
			if (f.lastModified() <= ctime) { return true; }
			break;
		}
		return false;
	}
	
	
}
