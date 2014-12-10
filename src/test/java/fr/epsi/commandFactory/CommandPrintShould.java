package fr.epsi.commandFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CommandPrintShould {
	
	AllCommands commandManager = new AllCommands();
	TypeCommand command;
	String path = "../../src/test/resources/";
	
	@Before
	public void initCommandDir(){
		TypeCommand command = CommandFactory.callCommand("cd", path);
		command.run();
	}
	
	@Test
	public void printFolderImages(){
		CommandFactory.callCommand("dir", "");
		command.run();
		String[] listFiles={"images","musiques","server-configuration.xml","users.xml"};
	}
	
	

}
