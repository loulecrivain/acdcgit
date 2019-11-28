package fr.imt.acdcgit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Arrays;

import fr.imt.acdcgit.interfaces.*;

public class App {
	public static boolean fileExists(String path) {
		return new File(path).exists();
	}

	public static boolean isIndex(String path) {
		File f = new File(path);
		if (f.exists() && f.isFile())
			return true;
		else
			return false;
	}

	public static void printUsageAndExit(List<SubCommand> commands) {
		System.err.println("command usage : ");
		for (SubCommand ac : commands) {
			System.err.println(ac.getUsage());
		}
		System.exit(-1);
	}

	public static void main(String[] args) {
		List<SubCommand> commands = new ArrayList<SubCommand>();
		HashMap<String, SubCommand> cmdNameToCommand = new HashMap<String, SubCommand>();
		ListCommand listCommand = new ListCommand();
		commands.add(listCommand);
		cmdNameToCommand.put(listCommand.getName(), listCommand);
		AddCommand addCommand = new AddCommand();
		commands.add(addCommand);
		cmdNameToCommand.put(addCommand.getName(), addCommand);
		CommitCommand commitCommand = new CommitCommand();
		commands.add(commitCommand);
		cmdNameToCommand.put(commitCommand.getName(), commitCommand);
		DiffCommand diffCommand = new DiffCommand();
		commands.add(diffCommand);
		cmdNameToCommand.put(diffCommand.getName(), diffCommand);
		StateCommand stateCommand = new StateCommand();
		commands.add(stateCommand);
		cmdNameToCommand.put(stateCommand.getName(), stateCommand);
		StatusCommand statusCommand = new StatusCommand();
		commands.add(statusCommand);
		cmdNameToCommand.put(statusCommand.getName(), statusCommand);
		AutoSyncCommand autoSyncCommand = new AutoSyncCommand();
		commands.add(autoSyncCommand);
		cmdNameToCommand.put(autoSyncCommand.getName(), autoSyncCommand);
		ManualSyncCommand manualSyncCommand = new ManualSyncCommand();
		commands.add(manualSyncCommand);
		cmdNameToCommand.put(manualSyncCommand.getName(), manualSyncCommand);

		if (args.length < 2) {
			printUsageAndExit(commands);
		} else {
			// number of args OK
			String cmdName = args[0]; // could use args4j but that's too much effort for our simple usecase
			String fileName = args[1];
			SubCommand command = cmdNameToCommand.get(cmdName);
			if (command == null) {
				System.err.println("unknown command " + cmdName);
				printUsageAndExit(commands);
			}
			if (!fileExists(fileName)) {
				System.err.println("must provide valid path for command " + cmdName);
				printUsageAndExit(commands);
			}
			// everything ok, we can go now
			ReposControllerInterface controller = new ReposControllerAdapter(new ConsoleCredentialsProvider());
			if(isIndex(fileName)) {
				controller.addReposFromFile(fileName);
			} else {
				controller.addReposFromDirectory(fileName);
			}
			if (args.length > 2 ) { // if they are other args, take them
				command.setArgs(Arrays.copyOfRange(args, 2, args.length));
			}
			command.launch(controller);
			// let main end
		}
	}
}
