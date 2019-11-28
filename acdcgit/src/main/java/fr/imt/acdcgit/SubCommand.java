package fr.imt.acdcgit;

import fr.imt.acdcgit.interfaces.ReposControllerInterface;

public abstract class SubCommand {
	public final String NAME = "noname";
	public final String USAGE = " <searchpath or indexfile>";
	protected String[] args = {};

	protected void printGenErrMessage(Exception e) {
		System.err.println("Exception: " + e.getClass() + "\n" + e.getMessage() + "\n");
		e.printStackTrace();
	}

	protected String charArrayConcat(char[] chrs) {
		StringBuilder sb = new StringBuilder();
		for (char s : chrs) {
			sb.append(s); // implicit String conversion by concat op
		}
		return sb.toString();
	}

	protected String strArrayConcat(String[] str) {
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			sb.append(s + " ");
		}
		return sb.toString();
	}

	protected boolean checkSoloCommandUsage(ReposControllerInterface controller) {
		if (controller.getRepos().size() != 1) { // must be exactly l
			System.out.println("this command works only with one repository. Found repositories were:");
			new ListCommand().launch(controller); // let it print repositories
			System.out.println(getUsage());
			return false;
		} else if (this.args.length != 1) { // must be exactly 1 also
			System.out.println("incorrect argument(s) " + strArrayConcat(args));
			System.out.println(getUsage());
			return false;
		}
		return true;
	}

	public void setArgs(String... args) {
		this.args = args;
	}

	public abstract void launch(ReposControllerInterface controller);

	public abstract String getName();

	public abstract String getUsage();
}
