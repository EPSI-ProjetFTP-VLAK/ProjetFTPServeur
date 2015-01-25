package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;

public class Pwd extends MasterCommand{
    public Pwd(CommandData p_commandData) {
        super(p_commandData);
    }

    @Override
    public String result(){
        return this.commandData().locationOfTheClientOnTheServer();
    }
}
