package fr.imt.acdcgit;

import fr.imt.acdcgit.facade.ACDCGitFacade;
import fr.imt.acdcgit.facade.Repository;

public class ListCommand extends SubCommand {
	public final String NAME = "list";
	public final String USAGE =  this.NAME + super.USAGE;
	
	@Override
	public void launch(ACDCGitFacade facade) {
		for (Repository r : facade.getRepos()) {
			System.out.println(r.getId());
		}
	}

	public String getName() {
		return this.NAME;
	}

	public String getUsage() {
		return this.USAGE;
	}
}
