package fr.imt.acdcgit;

import org.eclipse.jgit.api.errors.TransportException;

import fr.imt.acdcgit.facade.ACDCGitFacade;
import fr.imt.acdcgit.facade.Repository;

public class DiffCommand extends SubCommand {
	public final String NAME = "diff";
	public final String USAGE = this.NAME + super.USAGE;
	
	@Override
	public void launch(ACDCGitFacade facade) {
		for(Repository r : facade.getRepos()) {
			try {
				System.out.println("*** beginning of diff for repo " + r.getId() + " ***");
				System.out.println(facade.getDiff(r));
				System.out.println("*** end of diff for repo " + r.getId() + " ***");
			} catch (TransportException te) {
				System.err.println("Can't diff relative to online remote while offline");
				this.printGenErrMessage(te);
			} catch (Exception e) {
				this.printGenErrMessage(e);
			}
		}
	}

	public String getName() {
		return this.NAME;
	}

	public String getUsage() {
		return this.USAGE;
	}
}
