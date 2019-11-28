package fr.imt.acdcgit;

import fr.imt.acdcgit.interfaces.*;

public class ListCommand extends SubCommand {
	public final String NAME = "list";
	public final String USAGE =  this.NAME + super.USAGE;
	
	@Override
	public void launch(ReposControllerInterface controller) {
		for (String r : controller.getRepos()) {
			System.out.println(r);
		}
	}

	public String getName() {
		return this.NAME;
	}

	public String getUsage() {
		return this.USAGE;
	}
}
