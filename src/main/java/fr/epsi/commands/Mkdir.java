package fr.epsi.commands;

import java.net.Socket;

public class Mkdir extends MasterCommand {
	
	private boolean createDirectory = false;

	public Mkdir(CommandData commandData){
		super(commandData);
	}

	 @Override
    public void execCommand(){
		if(!sourceDirectory().exists())
			 this.createDirectory = sourceDirectory().mkdir();
	 }
	 
	 public boolean getFile(){
		 return createDirectory;
	 }


}


