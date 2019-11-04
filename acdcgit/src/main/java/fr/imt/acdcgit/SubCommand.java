package fr.imt.acdcgit;

import fr.imt.acdcgit.facade.ACDCGitFacade;

public abstract class SubCommand {
	public final String NAME = "noname";
	public final String USAGE = " <searchpath or indexfile>";
	
	protected void printGenErrMessage(Exception e) {
		System.err.println("Exception: " + e.getClass() + "\n" + e.getMessage() + "\n");
		e.printStackTrace();
	}
	
	public abstract void launch(ACDCGitFacade facade);
	
	public abstract String getName();
	
	public abstract String getUsage(); 
}
