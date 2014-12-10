package fr.epsi.commandFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CommandOpenFolderShould {
	
	AllCommands commandManager = new AllCommands();
	TypeCommand command;
	String path = "/";
	
	@Before
	public void initCommandDir(){
		TypeCommand command = CommandFactory.callCommand("dir");
		command.run();
	}
	
	@Test
	public void openFolderImages(){
		
	}
	
	

}
