package fr.epsi.commands;

import java.net.Socket;

public class CommandData {
    public final String[] allowedCommands = {"ls", "mkdir"};
    private String commandType;
    private String commandParameter;
    private Socket clientSocket;

    public CommandData(String command, Socket socket){
        String defaultCommandType = "command not allowed";
        String defaultParameter = "";

        if (command == null){
            this.commandType = defaultCommandType;
            this.commandParameter = defaultParameter;
        }

        if(command.contains("-") && !command.isEmpty()){
            this.commandType = command.split("-")[0];
            this.commandParameter = command.substring(commandType.length()+1);
        }else{
            this.commandType = defaultCommandType;
            this.commandParameter = defaultParameter;
        }

        if (!commandExist()){
            this.commandType = defaultCommandType;
            this.commandParameter = defaultParameter;
        }

        clientSocket = socket;
    }

    public Socket clientSocket(){
        return clientSocket;
    }

    public String commandType(){
        return commandType;
    }

    public String commandParameter(){
        return commandParameter;
    }

    public boolean commandExist(){
        boolean commandExist = false;

        for (String command : allowedCommands){
            if (command.equals(commandType))
                commandExist = true;
        }

        return commandExist;
    }
}
