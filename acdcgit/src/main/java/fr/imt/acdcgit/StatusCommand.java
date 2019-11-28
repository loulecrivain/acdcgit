package fr.imt.acdcgit;

import fr.imt.acdcgit.interfaces.ReposControllerInterface;
import java.util.List;
import java.util.Map;


public class StatusCommand extends SubCommand {
	public final String NAME = "status";
	public final String USAGE = this.NAME + super.USAGE;
	
	
	@Override
	public void launch(ReposControllerInterface controller) {
		try {
			List<String> repos = controller.getRepos();
			Map<String,String> status;
			for(String r : repos) {
				System.out.println("*** file tracking status in " + r + " ***");
				status = controller.status(r);
				for(String filePath : status.keySet()) {
					System.out.println("[" + status.get(filePath) + "] " + filePath);
				}
				System.out.println("*** end of file tracking status in " + r + " ***\n");
			}
		} catch (Exception e) {
			this.printGenErrMessage(e);
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
