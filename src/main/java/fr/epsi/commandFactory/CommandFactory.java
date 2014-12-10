package fr.epsi.commandFactory;

public class CommandFactory {

	public static TypeCommand callCommand(String type, String path) {
		TypeCommand command = null;

		if (type == "upload") {
		} else if (type == "download") {
		} else {
			command = new WindowsCommand(type,path);
		}

		return command;
	}
}
