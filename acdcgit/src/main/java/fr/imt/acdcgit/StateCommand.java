package fr.imt.acdcgit;

import java.util.Map;

import org.eclipse.jgit.api.errors.TransportException;

import fr.imt.acdcgit.interfaces.*;

public class StateCommand extends SubCommand {
	public final String NAME = "state";
	public final String USAGE = this.NAME + super.USAGE;
	
	@Override
	public void launch(ReposControllerInterface controller) {
		try {
			Map<String,String> states = controller.getAllStates();
			for(String path : states.keySet()) {
				System.out.println(path + " [" + states.get(path) + "]");
			}
		} catch (TransportException te) {
			System.err.println("Can't access remote to fetch state");
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
