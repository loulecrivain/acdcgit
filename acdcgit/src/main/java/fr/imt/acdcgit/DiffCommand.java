package fr.imt.acdcgit;

import org.eclipse.jgit.api.errors.TransportException;
import fr.imt.acdcgit.interfaces.*;


public class DiffCommand extends SubCommand {
	public final String NAME = "diff";
	public final String USAGE = this.NAME + super.USAGE;
	// ConsoleCredentialsProvider is not used
	protected final RepositoryAdapter repoAdapter = new RepositoryAdapter(new ConsoleCredentialsProvider());
	
	@Override
	public void launch(ReposControllerInterface controller) {
		for(String r : controller.getRepos()) {
			try {
				String diff = this.repoAdapter.diffs(r);
				System.out.println("*** beginning of diff for repo " + r + " ***");
				System.out.println(diff);
				System.out.println("*** end of diff for repo " + r + " ***");
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
