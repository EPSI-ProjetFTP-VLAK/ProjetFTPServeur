package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

public class Error extends MasterCommand {
    public Error(CommandData commandData){
        super(commandData);
    }

    @Override
    public void execCommand(){
        isExecuted = true;
    }
}
