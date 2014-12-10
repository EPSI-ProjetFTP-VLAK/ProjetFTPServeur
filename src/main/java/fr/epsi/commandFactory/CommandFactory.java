package fr.epsi.commandFactory;

public class CommandFactory {

	public static TypeCommand callCommand(String type) {
		TypeCommand command = null;

		if (type == "upload") {
		} else if (type == "download") {
		} else {
			command = new WindowsCommand(type);
		}

		return command;
	}
}
