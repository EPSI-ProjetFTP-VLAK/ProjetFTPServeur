package fr.epsi.commands;

public class Mkdir extends MasterCommand {
	
	private boolean createDirectory = false;
	
	 @Override
    public void execCommand(){
	if(!sourceDirectory().exists())
		 this.createDirectory = sourceDirectory().mkdir();
	 }
	 
	 public boolean getFile(){
		 return createDirectory;
	 }


}


