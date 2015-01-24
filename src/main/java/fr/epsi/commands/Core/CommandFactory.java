package fr.epsi.commands.Core;

import fr.epsi.commands.Command.*;
import fr.epsi.commands.Command.Error;

public class CommandFactory {
    public static ICommand createCommand(CommandData commandData){
        ICommand command = null;

        if(commandData.commandType().equals("ls")){
            command = new Ls(commandData);
        }else if(commandData.commandType().equals("mkdir")){
            command = new Mkdir(commandData);
        }else if(commandData.commandType().equals("command not allowed")){
            command = new Error(commandData);
        }

        return command;
    }
}
