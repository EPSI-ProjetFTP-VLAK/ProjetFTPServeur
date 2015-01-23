package fr.epsi.commands;

import fr.epsi.commands.CommandData;

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
