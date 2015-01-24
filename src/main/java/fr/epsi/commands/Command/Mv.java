package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

import java.io.IOException;
import java.nio.file.Files;


public class Mv extends MasterCommand {

	public Mv(CommandData commandData){
		super(commandData);
	}
	
	public void moveFileTo(){
		try {
			Files.move(sourcePath,destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void execCommand(){
		moveFileTo();
	}
}
