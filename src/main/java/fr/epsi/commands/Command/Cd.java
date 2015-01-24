package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

public class Cd extends MasterCommand{
    public Cd(CommandData p_commandData){
        super(p_commandData);
    }

    @Override
    public void execCommand(){
        this.commandData().setLocationOfTheClientOnTheServer(this.destinationDirectory.toString());
    }

    @Override
    public String clientLocationAfterCommandExectution(){
        return this.destinationDirectory.toString();
    }
}
