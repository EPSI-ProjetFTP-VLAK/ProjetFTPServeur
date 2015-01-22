package fr.epsi.commands;

import java.net.Socket;

public class CommandData {
    private final String[] allowedCommands = {"ls", "mkdir"};
    private final String parameterDelimiter = "::--::";
    private String defaultCommandType = "command not allowed" + parameterDelimiter;
    private String defaultParameter = "";

    private String commandType;
    private String commandParameter;
    private Socket clientSocket;
    private String locationOfTheClientOnTheServer;

    public CommandData(String command, String locationOfTheClientOnTheServer, Socket socket){
        if (command == null){
            setDefaultParameters();
        }

        if(command.contains(parameterDelimiter)){
            this.commandType = command.split(parameterDelimiter)[0];
            this.commandParameter = command.substring(commandType.length() + parameterDelimiter.length());
        }else{
            this.commandType = defaultCommandType;
            this.commandParameter = defaultParameter;
        }

        if (!commandExist()){
            setDefaultParameters();
        }

        clientSocket = socket;
        this.locationOfTheClientOnTheServer = locationOfTheClientOnTheServer;
    }

    private void setDefaultParameters(){
        this.commandType = defaultCommandType;
        this.commandParameter = defaultParameter;
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

    public String locationOfTheClientOnTheServer(){ return locationOfTheClientOnTheServer; }

    public boolean commandExist(){
        boolean commandExist = false;

        for (String command : allowedCommands){
            if (command.equals(commandType))
                commandExist = true;
        }

        return commandExist;
    }
}
