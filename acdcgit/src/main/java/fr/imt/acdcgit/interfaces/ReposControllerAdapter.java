package fr.imt.acdcgit.interfaces;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.transport.CredentialsProvider;

import fr.imt.acdcgit.features.FeatureListFactory;
import fr.imt.acdcgit.features.RepoFeatures;
import fr.imt.acdcgit.reposproviders.RepoFileListFromPath;
import fr.imt.acdcgit.reposproviders.RepoFromFileFactory;
import fr.imt.acdcgit.reposproviders.RepoListFromFile;
import fr.imt.acdcgit.reposproviders.RepoListProvider;

public class ReposControllerAdapter extends RepositoriesAdapter implements ReposControllerInterface {
	
	public ReposControllerAdapter(CredentialsProvider cp) {
		super(cp);
	}

	/* repos are added all at once */
	@Override
	public void addReposFromFile(String filePath) {
		RepoListProvider<File> fileListProvider = new RepoListFromFile(filePath);
		FeatureListFactory<RepoFeatures> repoBOFactory = new FeatureListFactory<RepoFeatures>(RepoFeatures.FACTORY,
				fileListProvider.getRepos(),
				this.cp);
		for(RepoFeatures rf : repoBOFactory.get()) {
			this.path2BO.put(rf.getCanonicalDir(),rf);
		}
	}

	@Override
	public void addReposFromDirectory(String directoryPath) {
		RepoListProvider<File> fileListProvider = new RepoFileListFromPath(directoryPath);
		FeatureListFactory<RepoFeatures> repoBOFactory = new FeatureListFactory<RepoFeatures>(RepoFeatures.FACTORY,
				fileListProvider.getRepos(),
				this.cp);
		for(RepoFeatures rf : repoBOFactory.get()) {
			this.path2BO.put(rf.getCanonicalDir(),rf);
		}
	}
	
	/* then the internal method for getting BOs is
	 * overrided
	 */
	@Override
	protected RepoFeatures getBOFromPath(String path, CredentialsProvider credsProvider) {
		// changing "cache" operation
		RepoFeatures rf = this.path2BO.get(path); // may be null, ok as it's handled by other methods
		if(rf != null) { // changing credsProvider
			// yes I have to build another BO in order to do that
			rf = RepoFeatures.FACTORY.getInstance(RepoFromFileFactory.repoFrom(new File(path)), credsProvider);
		}
		return rf;
	}	
	/* other interface methods are implemented by
	 * heritage, only the "cache" behaviour differs
	 * here.
	 */


	@Override
	public boolean saveReposToFile(String targetedPath) {
		boolean ok = false;
		// don't throw any exception, catch them and return false
		File outfile = new File(targetedPath);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			for (RepoFeatures rf: this.path2BO.values()) {
				writer.write(rf.getCanonicalDir());
				writer.newLine();
			}
			writer.close();
			ok = true;
		} catch (IOException e) {
			ok = false;
		}
		return ok;
	}

	@Override
	public List<String> getRepos() {
		List<String> l = new ArrayList<String>();
		for(String s: this.path2BO.keySet()) {
			l.add(s);
		}
		return l;
	}

	@Override
	public Map<String, Boolean> pushAll() throws Exception {
		return this.pushOrPullBatch(true, this.path2BO.keySet().toArray(new String[0]));
	}

	@Override
	public Map<String, Boolean> pullAll() throws Exception {
		return this.pushOrPullBatch(false, this.path2BO.keySet().toArray(new String[0]));
	}

	@Override
	public Map<String, String> getAllStates() throws Exception {
		return this.getState(this.path2BO.keySet().toArray(new String[0]));
	}
}
