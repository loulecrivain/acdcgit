package fr.imt.acdcgit;

import java.util.Map;

import org.eclipse.jgit.api.errors.TransportException;
import fr.imt.acdcgit.interfaces.ReposControllerInterface;


public class AutoSyncCommand extends SubCommand {
	public final String NAME = "autosync";
	public final String USAGE = this.NAME + super.USAGE;

	@Override
	public void launch(ReposControllerInterface controller) {
		try {
			Map<String, String> states = controller.getAllStates();
			for (String repo : states.keySet()) {
				String repostate = states.get(repo);
				if (repostate.contentEquals("UP_TO_DATE")) {
					System.out.println("repository " + repo + " is up to date");
				} else if (repostate.contentEquals("BEHIND")) {
					System.out.println("repository " + repo + " is behind, pulling...");
					if (controller.pull(repo)) {
						System.out.println("automatic pull successful " + repo);
					} else {
						System.out.println("automatic pull failed !");
					}
				} else if (repostate.contentEquals("AHEAD")) {
					System.out.println("repository " + repo + "is ahead, pushing...");
					if (controller.push(repo)) {
						System.out.println("automatic push successful");
					} else {
						System.out.println("automatic push failed !");
					}
				} else {
					// default case
					System.out.println("repository " + repo + " is in UNKNOWN state !");
				}
			}
		} catch (TransportException te) {
			System.err.println("Can't sync with online remote !");
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
