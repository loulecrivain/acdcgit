package fr.imt.acdcgit.findutils;

import java.io.File;

enum TimeComp {
	EQ,GT,LT;
}

public class FileCtimeFilter implements FileTestInterface {
	protected long ctime;
	protected TimeComp tc;
	
	public FileCtimeFilter(long ctime, TimeComp tc) {
		this.ctime = ctime;
		this.tc = tc;
	}

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
