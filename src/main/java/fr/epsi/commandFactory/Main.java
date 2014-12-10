package fr.epsi.commandFactory;

public class Main {

	public static void main(String[] args) {
		TypeCommand command = CommandFactory.callCommand("dir");
		command.run();
	}

}
