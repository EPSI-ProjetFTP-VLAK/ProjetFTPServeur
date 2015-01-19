package fr.epsi.commands;

import java.net.Socket;

public class CommandFactory {
    public static ICommand createCommand(String commandType, Socket socket){
        ICommand command = null;

        if(commandType.equals("ls")){
            command = new Ls(socket);
        }

        return command;
    }
}
