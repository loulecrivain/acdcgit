package fr.imt.acdcgit;

import org.eclipse.jgit.api.errors.TransportException;
import fr.imt.acdcgit.facade.Repository;
import fr.imt.acdcgit.facade.ACDCGitFacade;

public class ManualSyncCommand extends SubCommand {
	public final String NAME = "manualsync";
	public final String USAGE = this.NAME + super.USAGE;
	
	@Override
	public void launch(ACDCGitFacade facade) {
		for(Repository r : facade.getRepos()) {
			try {
				boolean status = facade.launchManualSyncOn(r);
				if (status) {
					System.out.println("succesful sync on repo " + r.getId());
				} else {
					System.err.println("cannot sync on repo " + r.getId());
				}
			} catch (TransportException te) {
				System.err.println("Can't sync with online remote while offline");
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
