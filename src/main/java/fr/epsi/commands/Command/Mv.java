package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

import java.io.IOException;
import java.nio.file.Files;


public class Mv extends MasterCommand {
	private String prefixAnswer = "mv : ";

	public Mv(CommandData commandData){
		super(commandData);
	}
	
	public void moveFileTo(){
		try {
			Files.move(sourceDirectory.toPath(),destinationDirectory.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void execCommand(){
		moveFileTo();
	}

	@Override
	public String result(){
		return fileAsBeenMoved();
	}

	public String fileAsBeenMoved() {
		return (destinationDirectory().exists() && !sourceDirectory().exists()) ? prefixAnswer + "OK" : prefixAnswer + "NOK";
	}
}
