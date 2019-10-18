package fr.imt.acdcgit.findutils;

import fr.imt.acdcgit.findutils.*;
import java.io.File;
import java.util.ArrayList;
import junit.framework.TestCase;

public class FileFilterTest extends TestCase {

	protected FileTypeFilter ftype_dir, ftype_file;
	protected FileNameFilter fname_dottxt;
	protected FileCtimeFilter fctime_gt_epoch;
	protected ArrayList<File> pwdList;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.ftype_dir = new FileTypeFilter(FileType.DIRECTORY);
		this.ftype_file = new FileTypeFilter(FileType.FILE);
		this.fname_dottxt = new FileNameFilter("empty\\.txt");
		this.fctime_gt_epoch = new FileCtimeFilter(0,TimeComp.GT);
	}
	
	public void testFileFilter() {
		FileFilter ff = new FileFilter(FileWalk.walk(".")).by(this.fctime_gt_epoch)
		.by(this.ftype_file).by(this.fname_dottxt);

		// pwd is project root normally
		File realfile = new File("./src/test/java/fr/imt/acdcgit/findutils/testdir/empty.txt");
		if (!realfile.exists()) {
			fail("Test file could not be found");
		} else {
			// test file exists, should have found at least one file
			// other empty.txt could exist elsewhere also but we test only
			// one known location
			if(!(ff.getFiltered().size() >= 1)) {
				fail("Filter hasn't found test file");
			} else {
				assertTrue(ff.getFiltered().contains(realfile));
			}
		}
	}

}
