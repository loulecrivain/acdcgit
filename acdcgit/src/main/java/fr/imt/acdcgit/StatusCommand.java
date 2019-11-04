package fr.imt.acdcgit;

import java.util.EnumMap;

import org.eclipse.jgit.api.errors.TransportException;

import fr.imt.acdcgit.facade.ACDCGitFacade;
import fr.imt.acdcgit.facade.Repository;
import fr.imt.acdcgit.features.RepoFeatures;

public class StatusCommand extends SubCommand {
	public final String NAME = "status";
	public final String USAGE = this.NAME + super.USAGE;
	
	
	protected EnumMap<RepoFeatures.RepoState,String> stateToString;

	public StatusCommand() {
		 stateToString = new EnumMap<RepoFeatures.RepoState,String>(RepoFeatures.RepoState.class);
         stateToString.put(RepoFeatures.RepoState.SYNCED, "synchronized");
         stateToString.put(RepoFeatures.RepoState.BEHIND, "behind");
         stateToString.put(RepoFeatures.RepoState.AHEAD, "ahead");
         stateToString.put(RepoFeatures.RepoState.UNKNOWN, "unknown state !");
	}
	
	@Override
	public void launch(ACDCGitFacade facade) {
		try {
			for (Repository r: facade.getRepos()) {
				String status = stateToString.get(facade.getStatus(r));
				String path = r.getId();
				System.out.println(path + " has status " + status);
			}
		} catch (TransportException te) {
			System.err.println("Can't access remote to fetch status");
			this.printGenErrMessage(te);
		} catch (Exception e) {
			this.printGenErrMessage(e);
		}
	}

	public String getName() {
		return this.NAME;
	}

	public String getUsage() {
		return this.USAGE;
	}
}
