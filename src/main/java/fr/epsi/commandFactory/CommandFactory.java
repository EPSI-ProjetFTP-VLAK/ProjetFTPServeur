package fr.epsi.commandFactory;

public class CommandFactory {

	public static TypeCommand callCommand(String type) {
		TypeCommand command = null;
		
		switch (type) {
		case "upload":
			break;
		case "download":
			break;
		default:
			command = new WindowsCommand(type);
			break;
		}
		
		return command;
	}
}
