package fr.imt.acdcgit.findutils;

import fr.imt.acdcgit.findutils.*;
import java.io.File;
import java.util.ArrayList;
import junit.framework.TestCase;

public class FileFilterTest extends TestCase {

	protected FileTypeFilter ftype_dir, ftype_file;
	protected FileNameFilter fname_dotjava;
	protected FileCtimeFilter fctime_gt_epoch;
	protected ArrayList<File> pwdList;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.ftype_dir = new FileTypeFilter(FileType.DIRECTORY);
		this.ftype_file = new FileTypeFilter(FileType.FILE);
		this.fname_dotjava = new FileNameFilter(".*\\.java");
		this.fctime_gt_epoch = new FileCtimeFilter(0,TimeComp.GT);
	}
	
	public void testBy() {
		// TODO penser à faire des répertoires de test avec fichiers bidon
		FileFilter ff = new FileFilter(FileWalk.walk(".")).by(this.fctime_gt_epoch)
		.by(this.ftype_file).by(this.fname_dotjava);
		fail("Not yet implemented");
	}

}
