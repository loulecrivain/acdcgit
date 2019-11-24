package fr.imt.acdcgit.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.transport.CredentialsProvider;

public class RepositoriesAdapter extends RepositoryAdapter implements RepositoriesInterface {

	public RepositoriesAdapter(CredentialsProvider cp) {
		super(cp);
	}

	protected Map<String,Boolean> pushOrPullBatch(boolean isPush, String...pathToRepositories) throws Exception {
		Map<String,Boolean> m = new HashMap<String,Boolean>();
		for (String pathToRepository : pathToRepositories) {
			boolean ok = false;
			if (isPush) {
				ok = super.push(pathToRepository);
			} else {
				ok = super.pull(pathToRepository);
			}
			m.put(pathToRepository, Boolean.valueOf(ok));
		}
		return m;		
	}
	
	@Override
	public Map<String, Boolean> push(String... pathToRepositories) throws Exception {
		return pushOrPullBatch(true, pathToRepositories);
	}

	@Override
	public Map<String, Boolean> pull(String... pathToRepositories) throws Exception {
		return pushOrPullBatch(false, pathToRepositories);
	}

	@Override
	public Map<String, String> getState(String... pathToRepositories) throws Exception {
		Map<String,String> m = new HashMap<String,String>();
		for (String pathToRepository : pathToRepositories) {
			m.put(pathToRepository, super.getState(pathToRepository));
		}
		return m;
	}

}
