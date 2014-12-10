package fr.epsi.commandFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CommandOpenFolderShould {
	
	AllCommands commandManager = new AllCommands();
	TypeCommand command;
	String path = "/";
	
	@Before
	public void initCommandCD(){
		command = CommandFactory.callCommand("cd");
	}
	
	

}
