package fr.imt.acdcgit;

import fr.imt.acdcgit.interfaces.ReposControllerInterface;

public class AddCommand extends SubCommand {
	public final String NAME = "add";
	public final String USAGE = this.NAME + super.USAGE + " pathspec/of/files/to/add";

	protected boolean checkArgs(String[] args) {
		if(args.length == 1) { // argument is mandatory
			// pathspec is relative to repository root
			// no check is done on path spec here (interface doesn't allow this)
			return true;
		}
		return false;
	}
	
	@Override
	public void launch(ReposControllerInterface controller) {
		// check args first
		if(checkSoloCommandUsage(controller)) {
			// do the real work
			try {
				controller.add(controller.getRepos().get(0), this.args);
				System.out.println("tried to add " + strArrayConcat(this.args) + ". new status is:\n");
				new StatusCommand().launch(controller); // print status
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
