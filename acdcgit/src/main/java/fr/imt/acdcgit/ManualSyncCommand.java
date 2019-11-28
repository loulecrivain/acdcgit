package fr.imt.acdcgit;

import java.io.Console;
import java.util.Map;

import org.eclipse.jgit.api.errors.TransportException;

import fr.imt.acdcgit.interfaces.ReposControllerInterface;


public class ManualSyncCommand extends SubCommand {
	public final String NAME = "manualsync";
	public final String USAGE = this.NAME + super.USAGE;

	/* Interactivity was previously provided in an async way
	 * in this class. Core interface specs forces synchronicity.
	 * Two TCP sessions may be opened in order to fetch/push
	 * remote objects.
	 */
	@Override
	public void launch(ReposControllerInterface controller) {
		Console console = System.console();
		try {
			// code smell: conditional complexity ;)
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
						System.out.println("automatic pull failed. Manually trying with credentials:");
						System.out.print("username: ");
						String username = console.readLine();
						System.out.print("password: ");
						String password = charArrayConcat(console.readPassword());
						if(controller.pull(repo, username, password)) {
							System.out.println("manual pull successful " + repo);
						} else {
							System.out.println("manual pull failed on " + repo + 
									". Either there was a network failure, either" + 
									" you provided wrong credentials or are not allowed to pull.");
						}
					}
				} else if (repostate.contentEquals("AHEAD")) {
					System.out.println("repository " + repo + " is behind, pushing...");
					if (controller.push(repo)) {
						System.out.println("automatic push successful " + repo);
					} else {
						System.out.println("automatic push failed. Manually trying with credentials:");
						System.out.print("username: ");
						String username = console.readLine();
						System.out.print("password: ");
						String password = charArrayConcat(console.readPassword());
						if(controller.push(repo, username, password)) {
							System.out.println("manual push successful " + repo);
						} else {
							System.out.println("manual push failed on " + repo + 
									". Either there was a network failure, either" + 
									" you provided wrong credentials or are not allowed to push.");
						}
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
