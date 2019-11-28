package fr.imt.acdcgit;

import fr.imt.acdcgit.interfaces.ReposControllerInterface;

public class CommitCommand extends SubCommand {
	public final String NAME = "commit";
	public final String USAGE = this.NAME + super.USAGE + " \"commit message\"";
	@Override
	public void launch(ReposControllerInterface controller) {
		if(checkSoloCommandUsage(controller)) {
			try {
				controller.commit(controller.getRepos().get(0), this.args[0]);
				System.out.println("committed changes with the following message:\n" + this.args[0]);
			} catch (Exception e) {
				this.printGenErrMessage(e);
			}
		}
	}
	@Override
	public String getName() {
		return this.NAME;
	}
	@Override
	public String getUsage() {
		return this.USAGE;
	}
}
